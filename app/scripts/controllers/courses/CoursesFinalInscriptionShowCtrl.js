'use strict';

define(['paw','services/Courses','services/Paths'], function(paw) {
  paw.controller('CoursesFinalInscriptionShowCtrl', ['$routeParams', 'Courses', '$log', 'Paths', function($routeParams, Courses, $log, Paths) {

    var _this = this;
    var courseId = $routeParams.courseId;
    var inscriptionId = $routeParams.inscriptionId;

    Courses.get(courseId).then(function(course) {
      _this.course = course;
      _this.course.all('finalInscriptions').get(inscriptionId).then(function(finalInscription) {
        _this.course.inscription = finalInscription;
        _this.course.inscription.students = finalInscription.history;
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
