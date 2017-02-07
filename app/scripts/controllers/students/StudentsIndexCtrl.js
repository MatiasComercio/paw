'use strict';
define(['paw','services/Students', 'services/Paths', 'services/navDataService'], function(paw) {
  paw.controller('StudentsIndexCtrl',
  ['$routeParams', 'Students', '$log', 'Paths', 'navDataService',
  function($routeParams, Students, $log, Paths, navDataService) {
    var _this = this;
    this.filter = {
      docket: $routeParams.docket,
      firstName: $routeParams.firstName,
      lastName: $routeParams.lastName
    };

    this.resetSearch = function() {
      this.filter = {};
    };

    var getUserCallback = function() {
      _this.user = navDataService.get('user');
    };
    navDataService.registerObserverCallback('user', getUserCallback);
    getUserCallback();

    Students.getList().then(function(students) {
      _this.students = students;
    });

    this.getStudentPath = function(docket) {
      return Paths.get().students({docket: docket}).path;
    };
  }]);
});
