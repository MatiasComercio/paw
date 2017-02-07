'use strict';

define([], function() {
  // we define this 'service' as a function returning the
  // authentication service itself so as to be able to use it
  // when the 'paw' module hasn't been yet acquired by requirejs
  // This function should be called on the paw's module initialization
  // (paw.js file)
  return function(Restangular, $cookies) {
    // note that we should return this object, which is the service itself
    // all Authentication variables and methods should be assigned to this object
    var authenticationService = {};
    var tokenKey = 'ur';

    var rest = Restangular.withConfig(function(RestangularConfigurer) {
      RestangularConfigurer.addResponseInterceptor(
        function(data, operation, what, url, response, deferred) {
          return response.headers()['x-auth-token'];
        }
      );
    });

    authenticationService.setToken = function(token) {
      $cookies.put(tokenKey, token);
    };

    authenticationService.getToken = function() {
      return $cookies.get(tokenKey);
    };

    authenticationService.getHeader = function() {
      return {'x-auth-token': authenticationService.getToken()};
    };

    authenticationService.login = function(user) {
      return rest.all('login').customPOST(user);
    };

    authenticationService.isLoggedIn = function() {
      return authenticationService.getToken() !== undefined;
    };

    authenticationService.logout = function() {
      return $cookies.remove(tokenKey);
    };

    return authenticationService;
  };
});
