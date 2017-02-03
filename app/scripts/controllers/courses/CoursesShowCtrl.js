'use strict';

define(['paw'], function(paw) {
  paw.controller('CoursesShowCtrl', ['$routeParams', function($routeParams) {
    var _this = this;
    var courseId = $routeParams.courseId; // For future Service calls

    this.formatDate = function(finalExamDate) {
      var date = finalExamDate.split('T')[0];
      var time = finalExamDate.split('T')[1];
      return date.split('-')[2] + '/' + date.split('-')[1] + '/' + date.split('-')[0] + ' ' + time;
    };

    this.course = {
      courseId: '93.42',
      credits: 6,
      name: 'Física II',
      semester: 3,
      correlatives: [
        {
          'courseId': '93.41',
          'credits': 6,
          'name': 'Física I',
          'semester': 2
        },
        {
          'courseId': '93.17',
          'credits': 6,
          'name': 'Matemática I',
          'semester': 1
        }
      ],
      finalInscriptions: [
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
