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

    var expectedParams = {
      docket: 123,
      firstName: 'Hello',
      lastName: 'Test'
    };

    var expectedStudents;
    var $controller, controller, $routeParams, $rootScope;

    beforeEach(inject(
      function(_$controller_, _$routeParams_, apiResponses, Students, specUtils, _$rootScope_) {
        $controller = _$controller_;
        $routeParams = _$routeParams_;
        expectedStudents = apiResponses.students;
        specUtils.resolvePromise(Students, 'getList', expectedStudents);
        $rootScope = _$rootScope_;
        controller = $controller('StudentsIndexCtrl', {$routeParams: expectedParams});
        $rootScope.$apply();
      }));

      it('correctly fetch the student from the Students Service', function() {
        expect(controller.students).toEqual(expectedStudents);
      });

      describe('when testing the getStudentPath', function() {
        var expectedPath;
        beforeEach(inject(function(Paths) {
          expectedPath = Paths.get().students({docket: 1}).path;
        }));

        it('correctly returns the path', function() {
          expect(controller.getStudentPath(1)).toEqual(expectedPath);
        });
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
