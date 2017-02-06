'use strict';

define(['paw', 'services/Admins', 'services/flashMessages'], function(paw) {
  paw.controller('AdminsEditCtrl',
  ['$window', 'Paths', 'Admins', 'flashMessages', '$route', '$log', '$filter', '$routeParams',
  function($window, Paths, Admins, flashMessages, $route, $log, $filter, $routeParams) {
    var _this = this;

    var dni = $routeParams.adminDni;

    this.maxDate = new Date();

    Admins.get(dni).then(function(admin) {
      _this.admin = admin;
      Admins.setOnSubSidebar(admin);
      _this.admin.birthday = new Date(_this.admin.birthday);
      // Restangular method to clone the current admin object
      _this.editedAdmin = Admins.copy(admin);
    });

    this.update = function(admin) {
      var editedAdmin = Admins.copy(admin);
      editedAdmin.birthday = $filter('date')(admin.birthday, 'yyyy-MM-dd');
      Admins.update(editedAdmin).then(function(response) {
        flashMessages.setSuccess('i18nAdminSuccessfullyUpdated');
        Paths.get().admins(_this.admin).go();
      }, function(response) {
        $log.warn(JSON.stringify(response));
        flashMessages.setError('i18nFormErrors');
        $route.reload();
      });
    };

    this.cancel = function() {
      $window.history.back();
    };
  }]);
});
