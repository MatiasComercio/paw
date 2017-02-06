'use strict';

define(['paw', 'services/Students', 'services/flashMessages'], function(paw) {
  paw.controller('StudentsEditCtrl',
  ['$window', 'Paths', 'Students', 'flashMessages', '$route', '$log', '$filter', '$routeParams',
  function($window, Paths, Students, flashMessages, $route, $log, $filter, $routeParams) {
    var _this = this;

    var docket = $routeParams.docket;

    this.maxDate = new Date();

    Students.get(docket).then(function(student) {
      _this.student = student;
      Students.setOnSubSidebar(student);
      _this.student.birthday = new Date(_this.student.birthday) || new Date();
      // Restangular method to clone the current student object
      _this.editedStudent = Students.copy(student);
    });

    this.update = function(student) {
      var editedStudent = Students.copy(student);
      editedStudent.birthday = $filter('date')(student.birthday, 'yyyy-MM-dd');
      Students.update(editedStudent).then(function(response) {
        flashMessages.setSuccess('i18nStudentSuccessfullyUpdated');
        Paths.get().students(_this.student).go();
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
