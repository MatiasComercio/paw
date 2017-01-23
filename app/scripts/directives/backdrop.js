'use strict';

define(
  ['paw',
  'services/navDataService',
  'services/windowSize',
  'directives/preventScroll'
  ],
  function(paw) {
    paw.directive('xbackdrop', ['navDataService', 'windowSize',
    function(navDataService, windowSize) {
      return {
        restrict: 'E',
        templateUrl: '/views/directives/backdrop.html',
        scope: true,
        bindToController: true,
        link: function(scope, element, attributes) {
          var checkBackdrop = function() {
            // check what to do
            scope.showBackdrop = scope.windowIsMobile && scope.sidebarOpen;
          };

          // handle window size changes
          var windowIsMobileCallback = function() {
            scope.windowIsMobile = windowSize.get('windowIsMobile');
            checkBackdrop();
          };
          windowSize.registerObserverCallback('windowIsMobile', windowIsMobileCallback);
          windowIsMobileCallback();

          // handle subSidebar changes
          var sidebarOpenCallback = function() {
            // || false in case undefined
            scope.sidebarOpen = navDataService.get('sidebarOpen') || false;
            checkBackdrop();
          };
          navDataService.registerObserverCallback('sidebarOpen', sidebarOpenCallback);
          sidebarOpenCallback();

          scope.closeSidebar = function() {
            navDataService.set('sidebarOpen', false);
          };
        }
      };
    }]);
  }
);
