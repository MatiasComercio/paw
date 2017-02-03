'use strict';

define(['paw', 'services/Paths'], function(paw) {
  paw.controller('CoursesEditCtrl', [
    '$routeParams',
    '$log',
    '$window',
    'Paths',
    function($routeParams, $log, $window, Paths) {
      var _this = this;

      this.course = {
        courseId: '72.03',
        credits: 3,
        name: 'Introducción a Informática',
        semester: 1
      };

      this.editedCourse = angular.copy(this.course);

      this.update = function(editedCourse) {
        var path = Paths.get().courses(_this.course);
        $log.info('POST ' + path.absolutePath() + ' ' + JSON.stringify(editedCourse));
        path.go();
      };

      this.cancel = function() {
        $window.history.back();
      };
  }]);
});
