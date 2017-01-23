'use strict';

define(['paw', 'angular-mocks', 'directives/navbar', 'navbar-template'], function() {
  describe('NavbarDirective', function() {
    beforeEach(module('paw'));
    beforeEach(module('directive-templates'));

    var $compile, $rootScope, scope, navbar, controller;
    var element = angular.element('<xnavbar></xnavbar>');

    // for now, hardcoded data (when API integration is ready ==> replace this)
    var user = {
      fullName: 'Matías Nicolás Comercio Vázquez asdasdasdasdasdasdasdasdadasdasdas',
      profileUrl: '/users/1',
      authorities: {
        admins: true,
        students: true,
        courses: true
      }
    };

    beforeEach(inject(function(_$compile_, _$rootScope_) {
      $compile = _$compile_;
      $rootScope = _$rootScope_;
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

    it('correctly fetch the user', function() {
      expect(controller.user).toEqual(user);
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
    });

    describe('when user exists on controller', function() {
      beforeEach(function() {
        scope.$apply(function() {
          controller.user = user;
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
