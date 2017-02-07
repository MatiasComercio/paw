// disable max-nested-callbacks linter for this test
// it is good to have a good separation of contexts inside tests

/* eslint-disable max-nested-callbacks */

'use strict';

define(['paw',
'angular-mocks',
'api-responses',
'spec-utils',
'controllers/students/StudentsShowCtrl'],
function() {
  describe('Students Show Ctrl', function() {
    beforeEach(module('paw'));

    var expectedStudent;
    var expectedDocket;
    var $controller, controller, $routeParams, $rootScope;

    beforeEach(inject(
      function(_$controller_, _$routeParams_, apiResponses, Students, specUtils, _$rootScope_) {
        $controller = _$controller_;
        $routeParams = _$routeParams_;
        expectedStudent = apiResponses.student;
        expectedDocket = expectedStudent.docket;
        specUtils.resolvePromise(Students, 'get', expectedStudent);
        $rootScope = _$rootScope_;
        controller = $controller('StudentsShowCtrl', {$routeParams: expectedDocket});
        $rootScope.$apply();
      }
    ));

    it('correctly fetch the student from the Students Service', function() {
      expect(controller.student).toEqual(expectedStudent);
    });
  });
});
