'use strict';

define(['paw'], function(paw) {
  paw.controller('UserChangePasswordCtrl', ['$window',function($window) {
    var _this = this;

    this.cancel = function() {
      $window.history.back();
    };

    this.changePassword = function(passwordForm) {
      // Change Password
    };
  }]);
});
