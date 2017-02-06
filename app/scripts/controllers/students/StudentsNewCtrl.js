'use strict';

define(['paw', 'services/Students', 'services/flashMessages',
'services/navDataService'
], function(paw) {
  paw.controller('StudentsNewCtrl',
  ['$window', 'Paths', 'Students', 'flashMessages', '$route', '$log', '$filter', 'navDataService',
  function($window, Paths, Students, flashMessages, $route, $log, $filter, navDataService) {
    var _this = this;
    _this.student = {};

    _this.maxDate = new Date();

    navDataService.checkUserIsAdmin();

    this.new = function(student) {
      _this.errors = [];
      var newStudent = angular.copy(student);
      newStudent.birthday = $filter('date')(student.birthday, 'yyyy-MM-dd');
      Students.new(newStudent).then(function(response) {
        flashMessages.setSuccess('i18nStudentSuccessfullyCreated');
        Paths.get().students().go();
      }, function(response) {
        if (response.status === 409) { // dni repeated
          _this.errors = [
            'i18nUserWithGIvenDNIExists'
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
