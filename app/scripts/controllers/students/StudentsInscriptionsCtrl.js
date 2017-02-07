'use strict';
define(['paw','services/Students','services/Paths', 'services/navDataService',
'controllers/modals/EnrollController'], function(paw) {
  paw.controller('StudentsInscriptionsCtrl',
  ['$routeParams', 'Students', '$log', 'Paths', 'navDataService',
  function($routeParams, Students, $log, Paths, navDataService) {
    var _this = this;
    var docket = $routeParams.docket; // For future Service calls

    var getUserCallback = function() {
      _this.user = navDataService.get('user');
      if (!_this.user) {
        return;
      }
      if (!_this.user.authorities.addInscription) {
        Paths.get().index().go();
      }
    };
    navDataService.registerObserverCallback('user', getUserCallback);
    getUserCallback();

    navDataService.saveUserTo(_this);

    this.filter = {
      course: {}
    };
    this.resetSearch = function() {
      this.filter.course = {};
    };

    Students.get(docket).then(function(student) {
      _this.student = student;
      Students.setOnSubSidebar(student);
      _this.student.all('courses').customGET('available').then(function(courses) {
        _this.student.availableCourses = courses;
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
