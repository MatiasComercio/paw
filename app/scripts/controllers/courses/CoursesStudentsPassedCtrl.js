'use strict';

define(['paw','services/Courses','services/Paths'], function(paw) {
  paw.controller('CoursesStudentsPassedCtrl',
  ['$routeParams', 'Courses', '$log', 'Paths', 'navDataService',
  function($routeParams, Courses, $log, Paths, navDataService) {
    var _this = this;
    var courseId = $routeParams.courseId;

    navDataService.saveUserTo(_this);

    this.filter = {
      student: {
        docket: $routeParams.docket,
        firstName: $routeParams.firstName,
        lastName: $routeParams.lastName
      }
    };

    this.resetSearch = function() {
      this.filter.student = {};
    };

    Courses.get(courseId).then(function(course) {
      _this.course = course;
      Courses.setOnSubSidebar(course);
      _this.course.all('students').customGET('passed').then(function(students) {
        _this.course.passedStudents = students;
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
