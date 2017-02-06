'use strict';

define(['paw', 'services/Courses', 'services/flashMessages'], function(paw) {
  paw.controller('CoursesFinalInscriptionNewCtrl',
  ['$routeParams', 'Courses', '$window', '$filter', 'flashMessages', '$log', '$route', 'Paths', 'navDataService',
  function($routeParams, Courses, $window, $filter, flashMessages, $log, $route, Paths, navDataService) {
      var _this = this;
      this.courseId = $routeParams.courseId;

      navDataService.checkUserIsAdmin();

      this.currentDate = new Date();

      Courses.get(this.courseId).then(function(course) {
        _this.course = course;
        Courses.setOnSubSidebar(course);
      }, function(response) {
        $log.info('Response status: ' + response.status);
        if (response.status === 404) {
          Paths.get().notFound().go();
        }
      });

      this.new = function(finalInscription) {
        _this.errors = [];
        var newFinalInscription = angular.copy(finalInscription);
        newFinalInscription.finalExamDate = $filter('date')(finalInscription.finalExamDate, 'yyyy-MM-ddTHH:mm:ss');
        Courses.newFinalInscription(this.courseId, newFinalInscription).then(function(response) {
          flashMessages.setSuccess('i18nFinalSuccessfullyCreated');
          Paths.get().courses(_this.course).go();
        }, function(response) {

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
