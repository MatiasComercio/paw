'use strict';

define(['paw'], function(paw) {
  paw.controller('CoursesFinalInscriptionNewCtrl', ['$routeParams', '$window', '$filter', function($routeParams, $window, $filter) {
      var _this = this;
      var courseId = $routeParams.courseId;
      this.new = function(finalInscription) {
        // Make Api request
        var parsedDate = $filter('date')(finalInscription.finalExamDate, 'yyyy-MM-ddTHH:mm:ss');
      };

      this.cancel = function() {
        $window.history.back();
      };

      this.currentDate = new Date();

      this.course = {
        courseId: '72.03',
        credits: 3,
        name: 'Introducción a Informática',
        semester: 1
      };
  }]);
});
