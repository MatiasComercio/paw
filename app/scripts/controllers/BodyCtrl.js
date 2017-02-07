'use strict';

define(
  ['paw',
  'directives/navbar',
  'directives/sidebar',
  'directives/alert',
  'services/Authentication',
  'services/navDataService',
  'services/flashMessages'],
  function(paw) {
    paw.controller('BodyCtrl', ['$rootScope', 'navDataService', 'flashMessages', 'Authentication',
    function($rootScope, navDataService, flashMessages, Authentication) {
      var _this = this;
      var getUser = function() {
        _this.user = navDataService.get('user');
      };
      navDataService.registerObserverCallback('user', getUser);
      getUser();

      this.flashMessages = {};

      $rootScope.$on('$routeChangeStart', function (event, next, current) {
        // if token has changed, reload user
        if (Authentication.getToken() !== navDataService.get('token')) {
          navDataService.remove('user');
          navDataService.set('token', Authentication.getToken());
          navDataService.fetchUser();
        }

        if (Authentication.isLoggedIn() && navDataService.get('user') === undefined) {
          navDataService.fetchUser();
        }

        // flash messages
        _this.flashMessages = {};
        _this.flashMessages.errors = flashMessages.getErrors();
        _this.flashMessages.successes = flashMessages.getSuccesses();
        flashMessages.clear();

        // restart subSidebar
        navDataService.remove('subSidebar');
      });
    }]);
  }
);
