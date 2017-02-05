'use strict';

define(['paw','services/Students','services/Paths', 'controllers/modals/UnenrollController'], function(paw) {
  paw.controller('StudentsCoursesIndexCtrl', ['$routeParams', 'Students', '$log', 'Paths', function($routeParams, Students, $log, Paths) {
    var _this = this;
    var docket = $routeParams.docket; // For future Service calls

    this.filter = {
      id: $routeParams.id,
      name: $routeParams.name
    };
    this.resetSearch = function() {
      this.filter = {};
    };

    Students.get(docket).then(function(student) {
      _this.student = student;

      _this.student.getList('courses').then(function(courses) {
        _this.student.courses = courses;
      });
    }, function(response) {
      $log.info('Response status: ' + response.status);
      if (response.status === 404) {
        Paths.get().notFound().go();
      }
    });

    this.getCoursePath = function(courseId) {
      return Paths.get().courses({courseId: courseId}).path;
    };

  }]);
});
