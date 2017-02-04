'use strict';

define(['paw'], function(paw) {
  paw.controller('AdminsNewCtrl', ['$window', function($window) {
    this.new = function() {
      // Make API call
    };
    this.cancel = function() {
      $window.history.back();
    };
  }]);
});
