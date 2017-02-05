'use strict';

define(['paw', 'restangular', 'services/Authentication',
'services/Paths', 'services/navDataService', 'services/flashMessages'],
function(paw) {
  paw.controller('LoginCtrl', ['Paths', '$log', 'Authentication', 'navDataService',
  'flashMessages', '$route',
  function(Paths, $log, Authentication, navDataService, flashMessages, $route) {
    var _this = this;

    this.login = function(user) {
      // user would never be undefined as the form has to be valid before calling this method
      // and that means that the user has to have some value defined
      Authentication.login(user).then(function(authToken) {
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
