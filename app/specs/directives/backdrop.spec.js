'use strict';

define(['paw', 'angular-mocks', 'services/navDataService', 'services/windowSize', 'directives/backdrop', 'backdrop-template'], function() {
  describe('Backdrop Directive', function() {
    beforeEach(module('paw'));
    beforeEach(module('directive-templates'));

    var element = angular.element('<xbackdrop></xbackdrop>');
    var $compile, directiveElement, $rootScope, scope, backdrop,
    windowSizeService, navDataServiceService, sidebarOpen;

    beforeEach(inject(function(_$compile_, _$rootScope_, navDataService, windowSize) {
      $compile = _$compile_;
      $rootScope = _$rootScope_.$new();

      navDataServiceService = navDataService;
      windowSizeService = windowSize;

      scope = $rootScope.$new();
      backdrop = compileDirective(scope);

      // register this callback to receive info when the backdrop is clicked
      var sidebarOpenCallback = function() {
        sidebarOpen = navDataServiceService.get('sidebarOpen');
      };
      navDataServiceService.registerObserverCallback('sidebarOpen', sidebarOpenCallback);
    }));

    function compileDirective(thisScope) {
      var compiledElement = $compile(element)(thisScope);
      thisScope.$digest();
      return compiledElement.find('.backdrop');
    };

    function backdropEvent(windowIsMobile, sidebarOpen) {
      windowSizeService.set('windowIsMobile', windowIsMobile);
      navDataServiceService.set('sidebarOpen', sidebarOpen);
      scope.$digest();
    };

    function expectToHideBackdrop(bool) {
      expect(backdrop.hasClass('ng-hide')).toBe(bool);
    };

    describe('when the page renders', function() {
      it('contains the backdrop element', function() {
        expect(backdrop[0]).toBeDefined();
      });
    });

    describe('when on mobile size and sidebar is open', function() {
      beforeEach(function() {
        backdropEvent(true, true);
      });

      it('shows the backdrop', function() {
        expectToHideBackdrop(false);
      });

      describe('when clicking the backdrop', function() {
        beforeEach(function() {
          backdrop.triggerHandler('click');
        });

        it('hides itself', function() {
          expectToHideBackdrop(true);
        });

        it('closes the sidebar', function() {
          expect(sidebarOpen).toBe(false);
        });
      });
    });

    describe('when on mobile size but sidebar is closed', function() {
      beforeEach(function() {
        backdropEvent(true, false);
      });

      it('hides the backdrop', function() {
        expectToHideBackdrop(true);
      });
    });

    describe('when not on mobile size but sidebar is open', function() {
      beforeEach(function() {
        backdropEvent(false, true);
      });

      it('hides the backdrop', function() {
        expectToHideBackdrop(true);
      });
    });

    describe('when not on mobile size and sidebar is closed', function() {
      beforeEach(function() {
        backdropEvent(false, false);
      });

      it('hides the backdrop', function() {
        expectToHideBackdrop(true);
      });
    });
  });
});
