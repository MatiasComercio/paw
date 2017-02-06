'use strict';

define(['paw', 'services/navDataService', 'services/flashMessages', 'services/Users',
'services/Admins', 'services/Students'], function(paw) {
  paw.controller('UserChangePasswordCtrl',
  ['$window', 'navDataService', 'flashMessages', 'Users', '$route', 'Admins', 'Students',
  function($window, navDataService, flashMessages, Users, $route, Admins, Students) {
    var _this = this;
    _this.errors = [];

    var getUserCallback = function() {
      _this.user = navDataService.get('user');
      if (_this.user.admin) {
        Admins.setOnSubSidebar(_this.user);
      } else {
        Students.setOnSubSidebar(_this.user);
      }
    };
    navDataService.registerObserverCallback('user', getUserCallback);
    getUserCallback();

    this.changePassword = function(passwordForm) {
      Users.updatePassword(passwordForm, _this.user.dni).then(function(response) {
        flashMessages.setSuccess('i18nPasswordSuccessfullyUpdated');
        $route.reload();
      }, function(response) {
        if (response.status === 409) { // dni repeated
          _this.errors = [
            'i18nWrongOriginalPassword'
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
