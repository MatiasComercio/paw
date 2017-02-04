'use strict';

define(['paw'], function(paw) {
  paw.directive('xalert', function () {
    return {
      link: link,
      restrict: 'E',
      scope: {},
      bindToController: {
        msg: '@',
        type: '@'
      },
      templateUrl: 'views/directives/alert.html',
      controller: function() {},
      controllerAs: 'controller'
    };

    function link(scope, element, attrs) {
      scope.show = true;
    }
  });
});
