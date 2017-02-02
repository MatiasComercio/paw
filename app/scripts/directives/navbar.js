'use strict';

define(['paw', 'services/navDataService', 'services/Paths'],
function(paw) {
  paw.directive('xnavbar', ['navDataService', 'Paths', 'Authentication',
  function(navDataService, Paths, Authentication) {
    function controller() {
      var _this = this;

      var fetchUser = function () {
        _this.user = navDataService.get('user');
      };

      // If the user might change, we fetch it again
      navDataService.registerObserverCallback('user', fetchUser);

      // Try to force navDataService to fetch the user
      navDataService.fetchUser();

      // Actually fetch the user
      fetchUser();
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

        scope.logout = function() {
          navDataService.remove('user');
          Authentication.logout();
          Paths.get().login().go();
        };
      }
    };
  }]);
});
