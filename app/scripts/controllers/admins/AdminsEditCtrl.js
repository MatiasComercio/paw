'use strict';

define(['paw'], function(paw) {
  paw.controller('AdminsEditCtrl', [
    '$routeParams',
    '$log',
    '$window',
    'Paths',
    function($routeParams, $log, $window, Paths) {
      var _this = this;

      var dni = $routeParams.adminDni;

      this.admin = {
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

      this.editedAdmin = angular.copy(this.admin);

      this.cancel = function() {
        $window.history.back();
      };

      this.update = function(editedAdmin) {
        var path = Paths.get().admins(_this.admin);
        $log.info('POST ' + path.absolutePath() + ' ' + JSON.stringify(editedAdmin));
        path.go();
      };
    }]);
  });
