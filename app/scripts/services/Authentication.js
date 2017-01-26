'use strict';

define(['paw', 'restangular'], function(paw) {
  paw.service('Authentication', ['Restangular', '$cookies',
  function(Restangular, $cookies) {
    var _this = this;
    var tokenKey = 'ur';

    var rest = Restangular.withConfig(function(RestangularConfigurer) {
      RestangularConfigurer.addResponseInterceptor(
        function(data, operation, what, url, response, deferred) {
          return response.headers()['x-auth-token'];
        }
      );
    });

    this.setToken = function(token) {
      $cookies.put(tokenKey, token);
    };

    this.getToken = function(token) {
      return $cookies.get(tokenKey);
    };

    this.getHeader = function() {
      return {'x-auth-token': _this.getToken()};
    };

    this.login = function(user) {
      return rest.all('login').customPOST(user);
    };
  }]);
});
