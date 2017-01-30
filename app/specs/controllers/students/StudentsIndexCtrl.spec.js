// disable max-nested-callbacks linter for this test
// it is good to have a good separation of contexts inside tests

/* eslint-disable max-nested-callbacks */

'use strict';

define(['paw',
'angular-mocks',
'controllers/students/StudentsIndexCtrl'],
function() {
  describe('Students Index Ctrl', function() {
    beforeEach(module('paw'));

    // Hardcoded data until Service call is tested
    var expectedStudents = [
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

      var $controller, $rootScope, controller;

      var expectedParams = {
        docket: 123,
        firstName: 'Hello',
        lastName: 'Test'
      };

      beforeEach(inject(
        function(_$controller_, _$rootScope_) {
          $controller = _$controller_;
          $rootScope = _$rootScope_;
          controller = $controller('StudentsIndexCtrl', {$routeParams: expectedParams});
        }));

        it('correctly fetch the students', function() {
          expect(controller.students).toEqual(expectedStudents);
        });

        describe('when initializing the filter values', function() {
          it('correctly fetch the docket', function() {
            expect(controller.filter.docket).toEqual(expectedParams.docket);
          });

          it('correctly fetch the first name', function() {
            expect(controller.filter.firstName).toEqual(expectedParams.firstName);
          });

          it('correctly fetch the last name', function() {
            expect(controller.filter.lastName).toEqual(expectedParams.lastName);
          });
        });

        describe('when reset button is clicked', function() {
          beforeEach(function() {
            controller.filter = {};
            controller.filter.docket = 12345;
            controller.filter.firstName = 'John';
            controller.filter.lastName = 'Doe';
            controller.resetSearch();
          });

          it('clears the docket search input', function() {
            expect(controller.filter).toEqual({});
          });

          it('clears the firstName search input', function() {
            expect(controller.filter).toEqual({});
          });

          it('clears the lastName search input', function() {
            expect(controller.filter).toEqual({});
          });
        });
      });
    });
