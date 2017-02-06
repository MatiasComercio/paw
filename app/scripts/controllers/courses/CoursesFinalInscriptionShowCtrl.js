'use strict';

define(['paw','services/Courses','services/Paths', 'services/flashMessages',
'controllers/modals/DeleteFinalController',
'controllers/modals/CloseFinalController'],
function(paw) {
  paw.controller('CoursesFinalInscriptionShowCtrl',
  ['$routeParams', 'Courses', '$log', 'Paths', 'flashMessages', '$route', 'navDataService',
  function($routeParams, Courses, $log, Paths, flashMessages, $route, navDataService) {
    var _this = this;
    var courseId = $routeParams.courseId;
    var inscriptionId = $routeParams.inscriptionId;

    navDataService.saveUserTo(_this);

    this.qualifyAll = false;
    this.qualify = function() {
      var qualifiedStudents = _this.course.inscription.students.map(function(student) {
          if (student.grade) {
              return {docket: student.docket, grade: student.grade};
          }
      }).filter(function(s) {
          return s !== undefined;
      });

      if (qualifiedStudents.length === 0) {
        _this.qualifyAll = !_this.qualifyAll;
        return;
      }

      Courses.qualifyFinalInscription(_this.course, inscriptionId, qualifiedStudents).then(function(response) {
        flashMessages.setSuccess('i18nQualifySuccessfully');
        if (response.status === 204) {
          Paths.get().courses(_this.course).finalInscriptions().go();
        } else {
          $route.reload();
        }
      }, function(response) {
        flashMessages.setError('i18nFormErrors');
        $log.warn('[ERROR] - Response: ' + JSON.stringify(response));
        $route.reload();
      });
    };

    Courses.get(courseId).then(function(course) {
      _this.course = course;
      _this.course.all('finalInscriptions').get(inscriptionId).then(function(finalInscription) {
        _this.course.inscription = finalInscription;
        _this.course.inscription.students = finalInscription.history;
      });
    });

    this.getStudentPath = function(docket) {
      return Paths.get().students({docket: docket}).path;
    };
  }]);
});
