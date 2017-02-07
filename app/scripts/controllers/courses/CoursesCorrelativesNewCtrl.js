'use strict';

define(['paw','services/Courses','services/Paths', 'controllers/modals/AddCorrelativeController'], function(paw) {
  paw.controller('CoursesCorrelativesNewCtrl', ['$routeParams', 'Courses', '$log', 'Paths', function($routeParams, Courses, $log, Paths) {
    var _this = this;
    var courseId = $routeParams.courseId;

    this.filter = {
      course: {}
    };
    this.resetSearch = function() {
      this.filter.course = {};
    };

    Courses.get(courseId).then(function(course) {
      _this.course = course;
      Courses.setOnSubSidebar(course);
      _this.course.all('correlatives').customGET('available').then(function(correlatives) {
        _this.course.availableCorrelatives = correlatives;
      });
    });

    this.getCoursePath = function(courseId) {
      return Paths.get().courses({courseId: courseId}).path;
    };
  }]);
});
