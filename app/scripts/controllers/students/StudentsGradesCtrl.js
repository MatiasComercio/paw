'use strict';
define(
  [
    'paw',
    'services/Students',
    'services/Paths',
    'controllers/modals/EditCourseGradeController',
    'services/navDataService'
  ], function(paw) {
    paw.controller('StudentsGradesCtrl',
    ['$routeParams',
    'Students',
    '$log',
    'Paths',
    'navDataService',
    function($routeParams, Students, $log, Paths, navDataService) {
      var _this = this;

      navDataService.saveUserTo(_this);

      Students.get($routeParams.docket).then(function(student) {
        _this.student = student;
        Students.setOnSubSidebar(student);
        _this.student.getList('grades').then(function(transcript) {
          _this.student.transcript = transcript;
          if (transcript.totalCredits > 0) {
            _this.carreerPrecentage = transcript.currentCredits / transcript.totalCredits * 100;
          }
          var average = 0;
          var counter = 0;
          angular.forEach(transcript, function(semester) {
            angular.forEach(semester.semester, function(study) {
              angular.forEach(study.finalGrades, function(finalGrade) { // eslint-disable-line
                if (finalGrade.grade >= 4) {
                  average += finalGrade.grade;
                  counter++;
                }
              });
            });
          });
          if (counter <= 0) {
            _this.student.average = 0;
          } else {
            _this.student.average = average / counter;
          }
        });
      });

      this.getCoursePath = function(courseId) {
        return Paths.get().courses({courseId: courseId}).path;
      };
    }]);
  }
);
