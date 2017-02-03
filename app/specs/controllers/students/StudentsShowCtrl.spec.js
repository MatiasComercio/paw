// disable max-nested-callbacks linter for this test
// it is good to have a good separation of contexts inside tests

/* eslint-disable max-nested-callbacks */

'use strict';

define(['paw',
'angular-mocks',
'controllers/students/StudentsShowCtrl'],
function() {
  describe('Students Show Ctrl', function() {
    beforeEach(module('paw'));

    var expectedStudent = {
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

    var expectedDocket = {docket: '55019'};

    var $controller, controller, $routeParams;

    beforeEach(inject(
      function(_$controller_, _$routeParams_) {
        $controller = _$controller_;
        $routeParams = _$routeParams_;
        controller = $controller('StudentsShowCtrl', {$routeParams: expectedDocket});
      }));

      it('correctly fetch the student', function() {
        expect(controller.student).toEqual(expectedStudent);
      });
    });
  });
