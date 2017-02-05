'use strict';

define(['paw','services/Courses','services/Paths', 'controllers/modals/DeleteCorrelativeController'], function(paw) {
  paw.controller('CoursesShowCtrl', ['$routeParams', 'Courses', '$log', 'Paths', function($routeParams, Courses, $log, Paths) {
    var _this = this;
    var courseId = $routeParams.courseId;

    this.formatDate = function(finalExamDate) {
      var date = finalExamDate.split('T')[0];
      var time = finalExamDate.split('T')[1];
      return date.split('-')[2] + '/' + date.split('-')[1] + '/' + date.split('-')[0] + ' ' + time;
    };

    this.getCorrelativePath = function(correlativeId) {
      return Paths.get().courses({courseId: correlativeId}).path;
    };
    this.getFinalInscriptionPath = function(finalInscriptionId) {
      return Paths.get().courses({courseId: courseId}).finals({inscriptionId: finalInscriptionId}).path;
    };

    Courses.get(courseId).then(function(course) {
      _this.course = course;
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

    // this.getFinalInscriptionPath = function(finalInscriptionId) {
    //   return Paths.get().courses({courseId: courseId}).path;
    // };
  }]);
});
