'use strict';
define(['paw','services/Students','services/Paths'], function(paw) {
  paw.controller('StudentsShowCtrl', ['$routeParams', 'Students', '$log', 'Paths', function($routeParams, Students, $log, Paths) {
    var _this = this;

    var docket = $routeParams.docket; // For future Service calls

    Students.get(docket).then(function(student) {
      _this.student = student;
      Students.setOnSubSidebar(student);
    });
  }]);
});
