'use strict';
define(['paw'], function(paw) {
  paw.controller('StudentsIndexCtrl', ['$routeParams', function($routeParams) {
    var _this = this;
    this.filter = {
      docket: $routeParams.docket,
      firstName: $routeParams.firstName,
      lastName: $routeParams.lastName
    };

    this.resetSearch = function() {
      this.filter = {};
    };

    this.students = [
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
      }];
    }]);
  });