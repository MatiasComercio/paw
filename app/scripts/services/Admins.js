'use strict';

define(['paw', 'services/AuthenticatedRestangular', 'services/navDataService'], function(paw) {
  paw.service('Admins', ['AuthenticatedRestangular', 'Paths', 'navDataService',
  function(AuthenticatedRestangular, Paths, navDataService) {
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
    rest.setOnSubSidebar = function(admin) {
      var _this = {};
      var getUserCallback = function() {
        // get the user
        _this.user = navDataService.get('user');
        if (!_this.user) {
          return; // nothing to set
        }

        var subSidebar = {};
        subSidebar.admin = admin;
        subSidebar.admin.actions = Paths.getAdminActions(admin, _this.user);

        // register the current sidebar
        navDataService.set('subSidebar', subSidebar);
      };
      navDataService.registerObserverCallback('user', getUserCallback);
      getUserCallback();
    };

    rest.getList = function() {
      return subject.getList();
    };

    rest.get = function(dni) {
      return subject.get(dni);
    };

    rest.inscriptions = function(body) {
      return subject.all('inscriptions').customPOST(body);
    };

    rest.new = function(admin) {
      return rest.all('admins').post(admin);
    };

    rest.update = function(admin) {
      return admin.customPOST(admin);
    };

    return rest;
  }]);
});
