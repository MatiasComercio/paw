// disable max-nested-callbacks linter for this test
// it is good to have a good separation of contexts inside tests

/* eslint-disable max-nested-callbacks */

'use strict';

define(['paw', 'angular-mocks', 'services/windowSize'], function() {
  describe('Window Size Service', function() {
    beforeEach(module('paw'));

    describe('when window is resized', function() {
      // mock angular.element($window) to later assign different values to innerWidth
      var $window, $rootScope, windowSizeService;

      var bootstrapWindowSize;
      var windowIsMobile;

      var bootstrapWindowSizeCallback = function() {
        bootstrapWindowSize = windowSizeService.get('bootstrapWindowSize');
      };

      var windowIsMobileCallback = function() {
        windowIsMobile = windowSizeService.get('windowIsMobile');
      };

      beforeEach(inject(function(_$window_, _$rootScope_, windowSize) {
        $window = _$window_;
        $rootScope = _$rootScope_;
        windowSizeService = windowSize;

        // we are implicitly testing that the windowSize implements the observer pattern
        windowSizeService.registerObserverCallback('bootstrapWindowSize',
                                                   bootstrapWindowSizeCallback);
        windowSizeService.registerObserverCallback('windowIsMobile', windowIsMobileCallback);

        spyOn($rootScope, '$digest').and.callThrough();
      }));

      describe('when using bootstrap thresholds', function() {
        describe('when resizing a window', function() {
          var windowWidth = 1000;
          beforeEach(function() {
            $window.innerWidth = windowWidth;
            angular.element($window).triggerHandler('resize');
          });

          it('triggers a $rootScope.$digest() cycle', function() {
            expect($rootScope.$digest).toHaveBeenCalled();
          });
        });

        describe('when $window.innerWidth < 768', function() {
          var windowWidth = 500;
          var expectedBootstrapWindowSize = 'xs';
          var expectedWindowIsMobile = true;

          beforeEach(function() {
            $window.innerWidth = windowWidth;
            angular.element($window).triggerHandler('resize');
          });

          it("sets bootstrapWindowSize to 'xs'", function() {
            expect(bootstrapWindowSize).toEqual(expectedBootstrapWindowSize);
          });

          it('sets windowIsMobile to true', function() {
            expect(windowIsMobile).toEqual(expectedWindowIsMobile);
          });
        });

        describe('when 992 > $window.innerWidth >= 768', function() {
          var windowWidth = 800;
          var expectedBootstrapWindowSize = 'sm';
          var expectedWindowIsMobile = false;

          beforeEach(function() {
            $window.innerWidth = windowWidth;
            angular.element($window).triggerHandler('resize');
          });

          it("sets bootstrapWindowSize to 'sm'", function() {
            expect(bootstrapWindowSize).toEqual(expectedBootstrapWindowSize);
          });

          it('sets windowIsMobile to false', function() {
            expect(windowIsMobile).toEqual(expectedWindowIsMobile);
          });
        });

        describe('when 1200 > $window.innerWidth >= 992', function() {
          var windowWidth = 1100;
          var expectedBootstrapWindowSize = 'md';
          var expectedWindowIsMobile = false;

          beforeEach(function() {
            $window.innerWidth = windowWidth;
            angular.element($window).triggerHandler('resize');
          });

          it("sets bootstrapWindowSize to 'md'", function() {
            expect(bootstrapWindowSize).toEqual(expectedBootstrapWindowSize);
          });

          it('sets windowIsMobile to false', function() {
            expect(windowIsMobile).toEqual(expectedWindowIsMobile);
          });
        });

        describe('when $window.innerWidth >= 1200', function() {
          var windowWidth = 1300;
          var expectedBootstrapWindowSize = 'lg';
          var expectedWindowIsMobile = false;

          beforeEach(function() {
            $window.innerWidth = windowWidth;
            angular.element($window).triggerHandler('resize');
          });

          it("sets bootstrapWindowSize to 'lg'", function() {
            expect(bootstrapWindowSize).toEqual(expectedBootstrapWindowSize);
          });

          it('sets windowIsMobile to false', function() {
            expect(windowIsMobile).toEqual(expectedWindowIsMobile);
          });
        });
      });
    });
  });
});
