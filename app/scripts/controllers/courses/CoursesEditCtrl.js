'use strict';

define(['paw', 'services/Courses', 'services/flashMessages'], function(paw) {
  paw.controller('CoursesEditCtrl',
  ['$window', 'Paths', 'Courses', 'flashMessages', '$route', '$log', '$filter', '$routeParams',
  function($window, Paths, Courses, flashMessages, $route, $log, $filter, $routeParams) {
    var _this = this;

    var courseId = $routeParams.courseId;

    Courses.get(courseId).then(function(course) {
      _this.course = course;
      Courses.setOnSubSidebar(course);
      // Restangular method to clone the current course object
      _this.editedCourse = Courses.copy(course);
    });

    this.update = function(course) {
      _this.errors = [];
      Courses.update(_this.course, course).then(function(response) {
        flashMessages.setSuccess('i18nCourseSuccessfullyUpdated');
        // redirect to the new course, possibly with new Id
        Paths.get().courses({courseId: course.courseId}).go();
      }, function(response) {
        if (response.status === 409) { // course id repeated
          _this.errors = [
            'i18nCourseWithCourseIdAlreadyExists'
          ];
          return;
        }
        $log.warn(JSON.stringify(response));
        flashMessages.setError('i18nFormErrors');
        $route.reload();
      });
    };

    this.cancel = function() {
      $window.history.back();
    };
  }]);
});
