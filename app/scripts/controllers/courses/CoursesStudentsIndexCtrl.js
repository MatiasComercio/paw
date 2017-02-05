'use strict';

define(['paw','services/Courses','services/Paths', 'controllers/modals/UnenrollController'], function(paw) {
  paw.controller('CoursesStudentsIndexCtrl', ['$routeParams', 'Courses', '$log', 'Paths', function($routeParams, Courses, $log, Paths) {
    var _this = this;
    var courseId = $routeParams.courseId;

    this.filter = {
      docket: $routeParams.docket,
      firstName: $routeParams.firstName,
      lastName: $routeParams.lastName
    };

    this.resetSearch = function() {
      this.filter = {};
    };

    Courses.get(courseId).then(function(course) {
      _this.course = course;
      _this.course.getList('students').then(function(students) {
        _this.course.students = students;
      });
    }, function(response) {
      $log.info('Response status: ' + response.status);
      if (response.status === 404) {
        Paths.get().notFound().go();
      }
    });

    this.getStudentPath = function(docket) {
      return Paths.get().students({docket: docket}).path;
    };
  }]);
});
