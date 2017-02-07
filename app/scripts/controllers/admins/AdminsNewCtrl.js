'use strict';

define(['paw', 'services/Admins', 'services/flashMessages', 'services/navDataService'
], function(paw) {
  paw.controller('AdminsNewCtrl',
  ['$window', 'Paths', 'Admins', 'flashMessages', '$route', '$log', '$filter', 'navDataService',
  function($window, Paths, Admins, flashMessages, $route, $log, $filter, navDataService) {
    var _this = this;
    _this.student = {};

    _this.maxDate = new Date();

    navDataService.checkUserIsAdmin();

    this.new = function(admin) {
      _this.errors = [];
      var newAdmin = angular.copy(admin);
      newAdmin.birthday = $filter('date')(admin.birthday, 'yyyy-MM-dd');
      Admins.new(newAdmin).then(function(response) {
        flashMessages.setSuccess('i18nAdminSuccessfullyCreated');
        Paths.get().admins().go();
      }, function(response) {
        if (response.status === 409) { // dni repeated
          _this.errors = [
            'i18nUserWithGIvenDNIExists'
          ];
          return;
        }
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
