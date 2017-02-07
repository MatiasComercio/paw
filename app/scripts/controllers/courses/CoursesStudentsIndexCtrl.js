'use strict';

define(['paw','services/Courses','services/Paths',
'controllers/modals/UnenrollController', 'services/flashMessages'], function(paw) {
  paw.controller('CoursesStudentsIndexCtrl',
  ['$routeParams', 'Courses', '$log', 'Paths', '$route', 'flashMessages', 'navDataService',
  function($routeParams, Courses, $log, Paths, $route, flashMessages, navDataService) {
    var _this = this;
    var courseId = $routeParams.courseId;

    this.filter = {
      docket: $routeParams.docket,
      firstName: $routeParams.firstName,
      lastName: $routeParams.lastName
    };

    this.resetSearch = function() {
      this.filter = {};
    };

    navDataService.saveUserTo(_this);

    this.qualifyAll = false;
    this.qualify = function() {
      var qualifiedStudents = _this.course.students.map(function(student) {
          if (student.grade) {
              return {docket: student.docket, grade: student.grade};
          }
      }).filter(function(s) {
          return s !== undefined;
      });

      if (qualifiedStudents.length === 0) {
        _this.qualifyAll = !_this.qualifyAll;
        return;
      }

      Courses.qualify(_this.course, qualifiedStudents).then(function(response) {
        flashMessages.setSuccess('i18nQualifySuccessfully');
        $route.reload();
      }, function(response) {
        flashMessages.setError('i18nFormErrors');
        $log.warn('[ERROR] - Response: ' + JSON.stringify(response));
        $route.reload();
      });
    };

    Courses.get(courseId).then(function(course) {
      _this.course = course;
      Courses.setOnSubSidebar(course);
      _this.course.getList('students').then(function(students) {
        _this.course.students = students;
      });
    });

    this.getStudentPath = function(docket) {
      return Paths.get().students({docket: docket}).path;
    };
  }]);
});
