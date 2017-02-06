'use strict';

define(['paw','services/Courses','services/Paths', 'controllers/modals/DeleteCorrelativeController', 'controllers/modals/DeleteFinalController', 'controllers/modals/CloseFinalController'], function(paw) {
  paw.controller('CoursesShowCtrl', ['$routeParams', 'Courses', '$log', 'Paths', function($routeParams, Courses, $log, Paths) {
    var _this = this;
    var courseId = $routeParams.courseId;

    Courses.get(courseId).then(function(course) {
      _this.course = course;
      Courses.setOnSubSidebar(course);
      _this.course.getList('correlatives').then(function(correlatives) {
        _this.course.correlatives = correlatives;
      });
      _this.course.getList('finalInscriptions').then(function(finalInscriptions) {
        _this.course.finalInscriptions = finalInscriptions;
      });
    }, function(response) {
      $log.info('Response status: ' + response.status);
      if (response.status === 404) {
        Paths.get().notFound().go();
      }
    });

    this.getCorrelativePath = function(correlativeId) {
      return Paths.get().courses({courseId: correlativeId}).path;
    };
    this.getFinalInscriptionPath = function(finalInscriptionId) {
      return Paths.get().courses({courseId: courseId}).finals({inscriptionId: finalInscriptionId}).path;
    };

  }]);
});
