'use strict';

define(['paw'], function(paw) {
  paw.controller('StudentsNewCtrl', ['$window', function($window) {
    this.new = function() {
      // Make API call
    };
    this.cancel = function() {
      $window.history.back();
    };
  }]);
});
