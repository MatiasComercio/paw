'use strict';

define(['paw'], function(paw) {
  paw.controller('CoursesFinalInscriptionShowCtrl', ['$routeParams', function($routeParams) {
    var _this = this;
    var courseId = $routeParams.courseId;
    var inscriptionId = $routeParams.inscriptionId;

    this.formatDate = function(finalExamDate) {
      var date = finalExamDate.split('T')[0];
      var time = finalExamDate.split('T')[1];
      return date.split('-')[2] + '/' + date.split('-')[1] + '/' + date.split('-')[0] + ' ' + time;
    };

    this.course = {
      courseId: '72.03',
      credits: 3,
      name: 'Introducción a Informática',
      semester: 1,
      inscription: {
        id: '1',
        courseId: '93.42',
        vacancy: '12',
        maxVacancy: '50',
        finalExamDate: '2017-01-20T18:45',
        state: 'OPEN',
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
          }
        ]
      }
    };
  }]);
});
