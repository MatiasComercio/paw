'use strict';

define(['paw','services/Admins','services/Paths'], function(paw) {
  paw.controller('AdminsIndexCtrl', ['$routeParams', 'Admins', '$log', 'Paths', function($routeParams, Admins, $log, Paths) {

    var _this = this;
    this.filter = {
      dni: $routeParams.dni,
      firstName: $routeParams.firstName,
      lastName: $routeParams.lastName
    };

    this.resetSearch = function() {
      this.filter = {};
    };

    Admins.getList().then(function(admins) {
      _this.admins = admins;
    }, function(response) {
      $log.info('Response status: ' + response.status);
      if (response.status === 404) {
        Paths.get().notFound().go();
      }
    });
  }]);
});
