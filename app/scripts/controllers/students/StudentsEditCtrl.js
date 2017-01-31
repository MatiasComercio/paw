'use strict';

define(['paw'], function(paw) {
  paw.controller('StudentsEditCtrl', [
    '$routeParams',
    '$log',
    '$window',
    '$location',
    function($routeParams, $log, $window, $location) {

      var _this = this;

      var docket = $routeParams.docket;

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
        }
      };

      this.editedStudent = angular.copy(this.student);

      this.cancel = function() {
        $window.history.back();
      };

      this.update = function(editedStudent) {
        $log.info('POST /students/' + docket + ' ' + JSON.stringify(editedStudent));
        $location.path('/students/' + docket);
      };
    }]);
  });
