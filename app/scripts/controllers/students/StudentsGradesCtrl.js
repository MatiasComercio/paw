'use strict';
define(
  [
    'paw',
    'services/Students',
    'services/Paths',
    'controllers/modals/EditCourseGradeController'
  ], function(paw) {
    paw.controller('StudentsGradesCtrl',
    ['$routeParams',
    'Students',
    '$log',
    'Paths',
    function($routeParams, Students, $log, Paths) {
      var _this = this;

      Students.get($routeParams.docket).then(function(student) {
        _this.student = student;
        _this.student.getList('grades').then(function(transcript) {
          _this.student.transcript = transcript;
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
  }
);
