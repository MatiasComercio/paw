'use strict';

define(['paw'], function(paw) {
  paw.controller('AdminsShowCtrl', ['$routeParams', function($routeParams) {
    var _this = this;
    var dni = $routeParams.adminDni; // For future Service calls

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
  }]);
});
