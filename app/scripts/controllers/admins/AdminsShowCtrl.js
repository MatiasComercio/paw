'use strict';

define(['paw','services/Admins','services/Paths'], function(paw) {
  paw.controller('AdminsShowCtrl', ['$routeParams', 'Admins', '$log', 'Paths', function($routeParams, Admins, $log, Paths) {
    var _this = this;
    var dni = $routeParams.adminDni;

    Admins.get(dni).then(function(admin) {
      _this.admin = admin;
      Admins.setOnSubSidebar(admin);
    });
  }]);
});
