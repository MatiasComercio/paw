'use strict';

define(['paw','services/Courses','services/Paths'], function(paw) {
  paw.controller('CoursesIndexCtrl', ['$routeParams', 'Courses', '$log', 'Paths', function($routeParams, Courses, $log, Paths) {
    var _this = this;
    this.filter = {
      courseId: $routeParams.courseId,
      courseName: $routeParams.courseName
    };
    this.resetSearch = function() {
      this.filter = {};
    };

    Courses.getList().then(function(courses) {
      _this.courses = courses;
    }, function(response) {
      $log.info('Response status: ' + response.status);
      if (response.status === 404) {
        Paths.get().notFound().go();
      }
    });
  }]);
});
