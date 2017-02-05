'use strict';

define(['paw', 'services/AuthenticatedRestangular'], function(paw) {
  paw.service('Users', ['AuthenticatedRestangular',
  function(AuthenticatedRestangular) {
    // this is needed as an array is expected from Restangular own methods
    // not needed if we are going to use Restangular's custom* methods
    var rest = AuthenticatedRestangular.withConfig(function(RestangularConfigurer) {
      RestangularConfigurer.setRestangularFields({
        id: 'dni'
      });
    });

    // add own methods as follows
    rest.resetPassword = function(dni) {
      return rest.one('users', dni).all('password').all('reset').customPOST();
    };

    return rest;
  }]);
});
