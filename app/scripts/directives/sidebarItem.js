'use strict';

define(['paw'], function(paw) {
  paw.directive('xsidebarItem', function() {
    return {
      restrict: 'E',
      scope: {},
      bindToController: {
        xtitle: '@',
        icon: '@',
        item: '='
      },
      controller: function() {},
      controllerAs: 'controller',
      transclude: true,
      templateUrl: 'views/directives/sidebar_item.html',
      link: function(scope, element, attrs) {
        scope.select = function() {
          scope.opened = !scope.opened;
        };
      }
    };
  });
});
