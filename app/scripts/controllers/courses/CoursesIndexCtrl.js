'use strict';
define(['paw'], function(paw) {
  paw.controller('CoursesIndexCtrl', ['$routeParams', function($routeParams) {
    var _this = this;
    this.filter = {
      courseId: $routeParams.courseId,
      courseName: $routeParams.courseName
    };
    this.resetSearch = function() {
      this.filter = {};
    };
    this.courses = [
      {
        courseId: '72.03',
        credits: 3,
        name: 'Introducción a Informática',
        semester: 1
      },
      {
        courseId: '94.21',
        credits: 6,
        name: 'Formacion General I',
        semester: 5
      }
    ];
  }]);
});
