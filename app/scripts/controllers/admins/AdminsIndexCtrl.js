'use strict';

define(['paw'], function(paw) {
  paw.controller('AdminsIndexCtrl', ['$routeParams', function($routeParams) {

    var _this = this;
    this.filter = {
      dni: $routeParams.dni,
      firstName: $routeParams.firstName,
      lastName: $routeParams.lastName
    };

    this.resetSearch = function() {
      this.filter = {};
    };

    this.admins = [
      {
        firstName: 'Matías',
        lastName: 'Mercado',
        dni: '12345685'
      },
      {
        firstName: 'Facundo',
        lastName: 'Mercado',
        dni: '32775545'
      },
      {
        firstName: 'Gibar',
        lastName: 'Sin',
        dni: '32365545'
      },
      {
        firstName: 'Obi Wan',
        lastName: 'Kenobi',
        dni: '45645682'
      },
      {
        firstName: 'Darth',
        lastName: 'Vader',
        dni: '12345685'
      },
      {
        firstName: 'Luke',
        lastName: 'Skywalker',
        dni: '56345686'
      }];
    }]);
  });
