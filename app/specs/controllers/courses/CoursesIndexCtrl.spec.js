// disable max-nested-callbacks linter for this test
// it is good to have a good separation of contexts inside tests

/* eslint-disable max-nested-callbacks */

'use strict';

define(['paw',
'angular-mocks',
'controllers/courses/CoursesIndexCtrl'],
function() {
  describe('Courses Index Ctrl', function() {
    beforeEach(module('paw'));

    var expectedParams = {
      courseId: 123,
      name: 'Hello'
    };

    var expectedCourses;
    var $controller, controller, $routeParams, $rootScope;

    beforeEach(inject(
      function(_$controller_, _$routeParams_, apiResponses, Courses, specUtils, _$rootScope_) {
        $controller = _$controller_;
        $routeParams = _$routeParams_;
        expectedCourses = apiResponses.courses;
        specUtils.resolvePromise(Courses, 'getList', expectedCourses);
        $rootScope = _$rootScope_;
        controller = $controller('CoursesIndexCtrl', {$routeParams: expectedParams});
        $rootScope.$apply();
      }
    ));

    it('correctly fetch the student from the Courses Service', function() {
      expect(controller.courses).toEqual(expectedCourses);
    });

    it('correctly fetch the courses', function() {
      expect(controller.courses).toEqual(expectedCourses);
    });

    describe('when initializing the filter values', function() {
      it('correctly fetch the course id', function() {
        expect(controller.filter.courseId).toEqual(expectedParams.courseId);
      });

      it('correctly fetch the course name', function() {
        expect(controller.filter.courseName).toEqual(expectedParams.courseName);
      });
    });

    describe('when reset button is clicked', function() {
      beforeEach(function() {
        controller.filter = {};
        controller.filter.courseId = '72.';
        controller.filter.courseName = 'Introducción a';
        controller.resetSearch();
      });

      it('clears the course id search input', function() {
        expect(controller.filter).toEqual({});
      });

      it('clears the course name search input', function() {
        expect(controller.filter).toEqual({});
      });
    });
  });
});
