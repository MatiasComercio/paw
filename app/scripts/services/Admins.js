'use strict';

define(['paw', 'services/AuthenticatedRestangular'], function(paw) {
  paw.service('Admins', ['AuthenticatedRestangular', 'Paths',
  function(AuthenticatedRestangular, Paths) {
    // this is needed as an array is expected from Restangular own methods
    // not needed if we are going to use Restangular's custom* methods
    var rest = AuthenticatedRestangular.withConfig(function(RestangularConfigurer) {
      RestangularConfigurer.addResponseInterceptor(
        function(data, operation, what, url, response, deferred) {
          if (operation === 'getList') {
            if (what === 'admins') {
              return data.admins;
            }
            return data;
          } else if (operation === 'post') {
            return response;
          } else {
            return data;
          }
        }
      );
      RestangularConfigurer.setRestangularFields({
        id: 'dni'
      });
      RestangularConfigurer.extendModel('admins', function(admin) {
        admin.fullName = admin.firstName + ' ' + admin.lastName;
        admin.profileUrl = Paths.get().admins(admin).path;
        return admin;
      });
    });

    var subject = rest.all('admins');

    // add own methods as follows
    rest.getList = function() {
      return subject.getList();
    };

    rest.get = function(dni) {
      return subject.get(dni);
    };

    return rest;
  }]);
});
