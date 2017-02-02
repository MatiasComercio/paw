'use strict';

define([
'paw',
'angular-mocks',
'directives/navbar',
'navbar-template',
'api-responses',
'services/navDataService'], function() {
  describe('NavbarDirective', function() {
    beforeEach(module('paw'));
    beforeEach(module('directive-templates'));

    var $compile, $rootScope, scope, navbar, controller, navDataServiceService,
        apiResponsesService;
    var element = angular.element('<xnavbar></xnavbar>');

    beforeEach(inject(function(_$compile_, _$rootScope_, navDataService, apiResponses) {
      $compile = _$compile_;
      $rootScope = _$rootScope_;
      navDataServiceService = navDataService;
      apiResponsesService = apiResponses;
      spyOn(navDataServiceService, 'fetchUser');
      scope = $rootScope.$new();
      navbar = compileDirective(scope);
      controller = navbar.controller('xnavbar');
    }));

    function compileDirective(thisScope) {
      var compiledElement = $compile(element)(thisScope);
      thisScope.$digest();
      return compiledElement.find('nav.navbar');
    };

    it('contains the navbar brand', function() {
      expect(navbar.find('.navbar-brand')[0]).toBeDefined();
    });

    it('contains the user menu', function() {
      expect(navbar.find('.top-nav')[0]).toBeDefined();
    });

    describe('when testing how the user is fetched', function() {
      var expectedUser;
      beforeEach(function() {
        expectedUser = apiResponsesService.currentAdmin;
        scope.$apply(function() {
          controller.user = undefined;
          navDataServiceService.set('user', expectedUser);
        });
      });

      it('fetchs the user from a callback on navDataService', function() {
        expect(controller.user).toEqual(expectedUser);
      });
    });

    describe('when user does not exist on controller', function() {
      beforeEach(function() {
        scope.$apply(function() {
          controller.user = undefined;
        });
      });

      it('does not show the user menu', function() {
        expect(navbar.find('.top-nav').hasClass('ng-hide')).toBe(true);
      });

      it('does not show the sandwich-icon', function() {
        expect(navbar.find('.sandwich-icon').hasClass('ng-hide')).toBe(true);
      });
    });

    describe('when user exists on controller', function() {
      beforeEach(function() {
        scope.$apply(function() {
          controller.user = apiResponsesService.currentAdmin;
        });
      });

      it('does not show the user menu', function() {
        expect(navbar.find('.top-nav').hasClass('ng-hide')).toBe(false);
      });
    });


    describe('when clicking the Sandwich icon', function() {
      var sidebarOpen, navDataServiceService;

      // navDataService should be already required by the directive
      beforeEach(inject(function(navDataService) {
        navDataServiceService = navDataService;

        // set callback to check if the value is the expected one, if ever called
        var sidebarOpenCallback = function() {
          sidebarOpen = navDataServiceService.get('sidebarOpen');
        };
        navDataServiceService.registerObserverCallback('sidebarOpen', sidebarOpenCallback);
      }));

      describe('when sidebar is opened', function() {
        beforeEach(function() {
          navDataServiceService.set('sidebarOpen', true);
          navbar.find('.sandwich-icon').triggerHandler('click');
        });

        it('sets variable to close the sidebar', function() {
          expect(sidebarOpen).toBe(false);
        });
      });

      describe('when sidebar is closed', function() {
        beforeEach(function() {
          navDataServiceService.set('sidebarOpen', false);
          navbar.find('.sandwich-icon').triggerHandler('click');
        });

        it('sets variables to open the sidebar', function() {
          expect(sidebarOpen).toBe(true);
        });
      });
    });
  });
});
