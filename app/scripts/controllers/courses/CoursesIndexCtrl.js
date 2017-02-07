'use strict';

define(['paw','services/Courses','services/Paths'], function(paw) {
  paw.controller('CoursesIndexCtrl', ['$routeParams', 'Courses', '$log', 'Paths', function($routeParams, Courses, $log, Paths) {
    var _this = this;
    this.filter = {
      courseId: $routeParams.courseId,
      name: $routeParams.courseName
    };
    this.resetSearch = function() {
      this.filter = {};
    };

    Courses.getList().then(function(courses) {
      _this.courses = courses;
    });
  }]);
});
