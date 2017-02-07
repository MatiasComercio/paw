'use strict';

define(['paw','services/Students','services/Paths',
'services/navDataService',
'controllers/modals/UnenrollController'], function(paw) {
  paw.controller('StudentsCoursesIndexCtrl',
  ['$routeParams', 'Students', '$log', 'Paths', 'navDataService',
  function($routeParams, Students, $log, Paths, navDataService) {
    var _this = this;
    var docket = $routeParams.docket; // For future Service calls

    this.filter = {
      courseId: $routeParams.id,
      name: $routeParams.name
    };
    this.resetSearch = function() {
      this.filter = {};
    };

    navDataService.saveUserTo(_this);

    Students.get(docket).then(function(student) {
      _this.student = student;
      Students.setOnSubSidebar(student);

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
