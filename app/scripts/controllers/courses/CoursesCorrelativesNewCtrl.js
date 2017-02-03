'use strict';

define(['paw'], function(paw) {
  paw.controller('CoursesCorrelativesNewCtrl', ['$routeParams', function($routeParams) {
    var _this = this;

    this.course = {
      courseId: '72.03',
      credits: 3,
      name: 'Introducción a Informática',
      semester: 1,
      availableCorrelatives: [
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
        },
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
      ]
    };

    this.filter = {
      id: $routeParams.id,
      name: $routeParams.name
    };
    this.resetSearch = function() {
      this.filter = {};
    };

  }]);
});
