'use strict';

define(['paw', 'services/Paths'], function(paw) {
  paw.controller('CoursesNewCtrl', ['$window', function($window) {
      var _this = this;

      this.new = function(course) {
        // Make API call
      };

      this.cancel = function() {
        $window.history.back();
      };

  }]);
});
