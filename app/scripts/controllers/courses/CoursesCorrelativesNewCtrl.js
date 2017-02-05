'use strict';

define(['paw','services/Courses','services/Paths', 'controllers/modals/AddCorrelativeController'], function(paw) {
  paw.controller('CoursesCorrelativesNewCtrl', ['$routeParams', 'Courses', '$log', 'Paths', function($routeParams, Courses, $log, Paths) {
    var _this = this;
    var courseId = $routeParams.courseId;

    this.filter = {
      id: $routeParams.id,
      name: $routeParams.name
    };
    this.resetSearch = function() {
      this.filter = {};
    };

    Courses.get(courseId).then(function(course) {
      _this.course = course;
      _this.course.all('correlatives').customGET('available').then(function(correlatives) {
        _this.course.availableCorrelatives = correlatives;
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
