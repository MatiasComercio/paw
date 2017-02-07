// disable max-nested-callbacks linter for this test
// it is good to have a good separation of contexts inside tests

/* eslint-disable max-nested-callbacks */

'use strict';

define(['paw',
'angular-mocks',
'api-responses',
'spec-utils',
'services/Paths',
'controllers/students/StudentsEditCtrl'],
function() {
  describe('Students Edit Ctrl', function() {
    beforeEach(module('paw'));

    var $controller, $rootScope, $log, $window, $location, controller, $routeParams,
    StudentsService, specUtilsService, PathsService, $route;

    var expectedStudent;
    var expectedDocket;

    beforeEach(inject(
      function(_$controller_, _$routeParams_, apiResponses, Students,
        specUtils, _$rootScope_, _$log_, _$window_, _$location_, Paths,
        _$route_) {
        $controller = _$controller_;
        $routeParams = _$routeParams_;
        expectedStudent = apiResponses.student;
        expectedDocket = {
          docket: expectedStudent.docket
        };
        StudentsService = Students;
        specUtilsService = specUtils;
        specUtilsService.resolvePromise(StudentsService, 'get', expectedStudent);
        $rootScope = _$rootScope_;
        PathsService = Paths;

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

        $route = _$route_;

        $rootScope.$apply();
      }
    ));

    it('correctly fetch the student from the Students Service', function() {
      expect(controller.student).toEqual(expectedStudent);
    });

    it('correctly fetch the student', function() {
      expect(controller.student).toEqual(expectedStudent);
    });

    it('creates a copy of the fetched student', function() {
      expect(specUtilsService.sanitizeRestangularOne(controller.editedStudent)).toEqual(expectedStudent);
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
      var editedStudent;
      beforeEach(function() {
        editedStudent = expectedStudent;
        editedStudent.firstName = 'Other name';
      });

      describe('when successfully updated', function() {
        var expectedPath;
        beforeEach(function() {
          specUtilsService.resolvePromise(StudentsService, 'update', {status: 201});
          expectedPath = PathsService.get().students({docket: expectedStudent.docket}).absolutePath();
          controller.update(editedStudent);
          $rootScope.$apply();
        });

        it('is expected to redirect to student show view', function() {
          expect($location.path).toHaveBeenCalledWith(expectedPath);
        });
      });

      describe('when updated with failure', function() {
        var expectedPath;
        beforeEach(function() {
          specUtilsService.rejectPromise(StudentsService, 'update', {status: 409});
          spyOn($route, 'reload');
          controller.update(editedStudent);
          $rootScope.$apply();
        });

        it('is expected to redirect to student show view', function() {
          expect($route.reload).toHaveBeenCalled();
        });
      });
    });
  });
});
