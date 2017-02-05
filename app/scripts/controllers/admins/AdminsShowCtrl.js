'use strict';

define(['paw','services/Admins','services/Paths'], function(paw) {
  paw.controller('AdminsShowCtrl', ['$routeParams', 'Admins', '$log', 'Paths', function($routeParams, Admins, $log, Paths) {
    var _this = this;
    var dni = $routeParams.adminDni; // For future Service calls

    Admins.get(dni).then(function(admin) {
      _this.admin = admin;
    }, function(response) {
      $log.info('Response status: ' + response.status);
      if (response.status === 404) {
        Paths.get().notFound().go();
      }
    });
  }]);
});
