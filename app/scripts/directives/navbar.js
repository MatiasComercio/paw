'use strict';

define(['paw', 'services/navDataService'], function(paw) {
  paw.directive('xnavbar', ['navDataService',function(navDataService) {
    function controller() {
      this.user = {
        fullName: 'Matías Nicolás Comercio Vázquez asdasdasdasdasdasdasdasdadasdasdas',
        profileUrl: '/users/1',
        authorities: {
          admins: true,
          students: true,
          courses: true
        }
      };

      navDataService.set('user', this.user);
    };

    return {
      restrict: 'E',
      templateUrl: '/views/directives/navbar.html',
      controller: controller,
      controllerAs: 'controller',
      scope: {},
      bindToController: true,
      link: function(scope, element, attributes) {
        scope.sidebarOpen = function() {
          navDataService.set('sidebarOpen', !navDataService.get('sidebarOpen'));
        };
      }
    };
  }]);
});
