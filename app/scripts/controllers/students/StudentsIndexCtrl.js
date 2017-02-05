'use strict';
define(['paw','services/Students','services/Paths'], function(paw) {
  paw.controller('StudentsIndexCtrl', ['$routeParams', 'Students', '$log', 'Paths', function($routeParams, Students, $log, Paths) {
    var _this = this;
    this.filter = {
      docket: $routeParams.docket,
      firstName: $routeParams.firstName,
      lastName: $routeParams.lastName
    };

    this.resetSearch = function() {
      this.filter = {};
    };

    Students.getList().then(function(students) {
      _this.students = students;
    }, function(response) {
      $log.info('Response status: ' + response.status);
      if (response.status === 404) {
        Paths.get().notFound().go();
      }
    });

    this.getStudentPath = function(docket) {
      return Paths.get().students({docket: docket}).path;
    };
  }]);
});
