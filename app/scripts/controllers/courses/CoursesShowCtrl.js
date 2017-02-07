'use strict';

define(['paw','services/Courses','services/Paths',
'controllers/modals/DeleteCorrelativeController',
'controllers/modals/CloseFinalController'], function(paw) {
  paw.controller('CoursesShowCtrl',
  ['$routeParams', 'Courses', '$log', 'Paths', 'navDataService',
  function($routeParams, Courses, $log, Paths, navDataService) {
    var _this = this;
    var courseId = $routeParams.courseId;

    navDataService.saveUserTo(_this);

    Courses.get(courseId).then(function(course) {
      _this.course = course;
      Courses.setOnSubSidebar(course);
      _this.course.getList('correlatives').then(function(correlatives) {
        _this.course.correlatives = correlatives;
      });
      _this.course.getList('finalInscriptions').then(function(finalInscriptions) {
        _this.course.finalInscriptions = finalInscriptions;
      });
    });

    this.getCorrelativePath = function(correlativeId) {
      return Paths.get().courses({courseId: correlativeId}).path;
    };
    this.getFinalInscriptionPath = function(finalInscriptionId) {
      return Paths.get().courses({courseId: courseId}).finals({inscriptionId: finalInscriptionId}).path;
    };

  }]);
});
