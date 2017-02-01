'use strict';

define(['paw', 'services/navDataService', 'services/Paths'], function(paw) {
  paw.directive('xnavbar', ['navDataService', 'Paths', function(navDataService, Paths) {
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
      templateUrl: 'views/directives/navbar.html',
      controller: controller,
      controllerAs: 'controller',
      scope: {},
      bindToController: true,
      link: function(scope, element, attributes) {
        scope.paths = {
          index: Paths.get().index().path,
          logout: Paths.get().logout().path
        };

        scope.sidebarOpen = function() {
          navDataService.set('sidebarOpen', !navDataService.get('sidebarOpen'));
        };
      }
    };
  }]);
});
