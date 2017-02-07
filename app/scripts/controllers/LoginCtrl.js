'use strict';

define(['paw', 'restangular', 'services/Authentication',
'services/Paths', 'services/navDataService', 'services/flashMessages'],
function(paw) {
  paw.controller('LoginCtrl', ['Paths', '$log', 'Authentication', 'navDataService',
  'flashMessages', '$route',
  function(Paths, $log, Authentication, navDataService, flashMessages, $route) {
    var _this = this;

    navDataService.remove('user');

    this.login = function(user) {
      // user would never be undefined as the form has to be valid before calling this method
      // and that means that the user has to have some value defined
      //

      // the following is for cases where the user opens a second session simulatneously on a new tab
      if (Authentication.isLoggedIn()) {
        // fetch the current user in case it is not already fetched
        navDataService.fetchUser();
        // redirect to current index
        Paths.get().index().go();
        return;
      }

      Authentication.login(user).then(function(authToken) {
        navDataService.remove('user');
        Authentication.setToken(authToken);
        navDataService.fetchUser();
        Paths.get().index().go();
      }, function(response) {
        // here we should handle any issue and show a nice error message
        $log.info('Response status: ' + response.status);
        flashMessages.setError('i18nInvalidUsernameOrPassword');
        $route.reload();
      });
    };
  }]);
});
