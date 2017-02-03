'use strict';

define(['paw'], function(paw) {
  paw.controller('StudentsFinalInscriptionsCtrl', ['$routeParams', function($routeParams) {
    var _this = this;
    var docket = $routeParams.docket; // For future Service calls

    this.formatDate = function(finalExamDate) {
      var date = finalExamDate.split('T')[0];
      var time = finalExamDate.split('T')[1];
      return date.split('-')[2] + '/' + date.split('-')[1] + '/' + date.split('-')[0] + ' ' + time;
    };

    this.student = {
      docket: '55019',
      firstName: 'Matías',
      lastName: 'Mercado',
      email: 'mmercado@itba.edu.ar',
      genre: 'Masculino',
      dni: '38917403',
      birthday: '1995-05-04',
      address: {
        country: 'Argentina',
        city: 'Buenos Aires',
        neighborhood: 'Almagro',
        number: '682',
        street: 'Corrientes',
        floor: '2',
        door: 'A',
        telephone: '1544683390',
        zipCode: '1100'
      },
      finalInscriptionsAvailable: [
        {
          id: '1',
          courseId: '93.42',
          vacancy: '12',
          maxVacancy: '50',
          finalExamDate: '2017-01-20T18:45',
          state: 'OPEN'
        },
        {
          id: '2',
          courseId: '93.42',
          vacancy: '6',
          maxVacancy: '50',
          finalExamDate: '2017-02-15T14:00',
          state: 'CLOSED'
        }
      ],
      finalInscriptionsTaken: [
        {
          id: '1',
          courseId: '93.42',
          vacancy: '12',
          maxVacancy: '50',
          finalExamDate: '2017-01-20T18:45',
          state: 'OPEN'
        },
        {
          id: '2',
          courseId: '93.42',
          vacancy: '6',
          maxVacancy: '50',
          finalExamDate: '2017-02-15T14:00',
          state: 'CLOSED'
        }
      ]
    };
  }]);
});
