'use strict';

define(['paw', 'services/Paths', 'services/Courses', 'services/flashMessages', 'services/navDataService'], function(paw) {
  paw.controller('CoursesNewCtrl',
  ['$window', 'Paths', 'Courses', '$log', '$route', 'flashMessages', 'navDataService',
  function($window, Paths, Courses, $log, $route, flashMessages, navDataService) {
      var _this = this;

      navDataService.checkUserIsAdmin();

      this.new = function(course) {
        _this.errors = [];
        Courses.new(course).then(function(response) {
          flashMessages.setSuccess('i18nCourseSuccessfullyCreated');
          Paths.get().courses().go();
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
