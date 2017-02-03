'use strict';

define(['paw'], function(paw) {
  paw.controller('CoursesStudentsIndexCtrl', ['$routeParams', function($routeParams) {
    var _this = this;
    var courseId = $routeParams.courseId; // For future Service calls

    this.course = {
      courseId: '72.03',
      name: 'Introducción a Informática',
      credits: 3,
      semester: 1,
      students: [
        {
          firstName: 'Matías',
          lastName: 'Mercado',
          docket: '55019'
        },
        {
          firstName: 'Facundo',
          lastName: 'Mercado',
          docket: '51202'
        },
        {
          firstName: 'Gibar',
          lastName: 'Sin',
          docket: '54655'
        },
        {
          firstName: 'Obi Wan',
          lastName: 'Kenobi',
          docket: '12000'
        },
        {
          firstName: 'Darth',
          lastName: 'Vader',
          docket: '66666'
        },
        {
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
