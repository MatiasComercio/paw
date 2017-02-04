'use strict';

define(['paw', 'services/Admins'], function(paw) {
  paw.controller('AdminsIndexCtrl', ['$routeParams', 'Admins',
  function($routeParams, Admins) {

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
    });
  }]);
});
