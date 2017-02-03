'use strict';

define(['paw'], function(paw) {
  paw.controller('CoursesStudentsPassedCtrl', ['$routeParams', function($routeParams) {
    var _this = this;
    var courseId = $routeParams.courseId; // For future Service calls

    this.course = {
      courseId: '72.03',
      name: 'Introducción a Informática',
      credits: 3,
      semester: 1,
      passedStudents: [
        {
          grade: '4',
          firstName: 'Matías',
          lastName: 'Mercado',
          docket: '55019'
        },
        {
          grade: '10',
          firstName: 'Facundo',
          lastName: 'Mercado',
          docket: '51202'
        },
        {
          grade: '9.50',
          firstName: 'Gibar',
          lastName: 'Sin',
          docket: '54655'
        },
        {
          grade: '8.50',
          firstName: 'Obi Wan',
          lastName: 'Kenobi',
          docket: '12000'
        },
        {
          grade: '10',
          firstName: 'Darth',
          lastName: 'Vader',
          docket: '66666'
        },
        {
          grade: '8',
          firstName: 'Luke',
          lastName: 'Skywalker',
          docket: '53222'
        }]
      };

      this.filter = {
        docket: $routeParams.docket,
        firstName: $routeParams.firstName,
        lastName: $routeParams.lastName
      };

      this.resetSearch = function() {
        this.filter = {};
      };
    }]);
  });
