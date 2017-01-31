// disable max-nested-callbacks linter for this test
// it is good to have a good separation of contexts inside tests

/* eslint-disable max-nested-callbacks */

'use strict';

define(['paw',
'angular-mocks',
'controllers/students/StudentsEditCtrl'],
function() {
  describe('Students Update Ctrl', function() {
    beforeEach(module('paw'));

    // Hardcoded data until Service call is tested
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
    var docket = 123;

    var $controller, $rootScope, $log, $window, $location, controller;

    var expectedDocket = {
      docket: docket
    };

    beforeEach(inject(
      function(_$controller_, _$rootScope_, _$log_, _$window_, _$location_) {
        $controller = _$controller_;
        $rootScope = _$rootScope_;
        $log = _$log_;
        spyOn($log, 'info');
        $window = _$window_;
        spyOn($window.history, 'back');
        $location = _$location_;
        spyOn($location, 'path');
        controller = $controller('StudentsEditCtrl', {
          $routeParams: expectedDocket,
          $log: $log,
          $window: $window,
          $location: $location
        });
      }));

      it('correctly fetch the student', function() {
        expect(controller.student).toEqual(expectedStudent);
      });

      it('creates a copy of the fetched student', function() {
        expect(controller.editedStudent).toEqual(expectedStudent);
      });

      describe('when calling the cancel function', function() {
        beforeEach(function() {
          controller.cancel();
        });

        it('is expected to go back on window history', function() {
          expect($window.history.back).toHaveBeenCalled();
        });
      });

      describe('when calling the update function', function() {
        var editedStudent, expectedLog;
        beforeEach(function() {
          editedStudent = expectedStudent;
          editedStudent.firstName = 'Other name';
          controller.update(editedStudent);

          expectedLog = 'POST /students/' + docket + ' ' + JSON.stringify(editedStudent);
        });

        it('is expected to correctly log the post', function() {
          expect($log.info).toHaveBeenCalledWith(expectedLog);
        });

        it('is expected to redirect to student show view', function() {
          expect($location.path).toHaveBeenCalledWith('/students/' + docket);
        });
      });
    });
  });
