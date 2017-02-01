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

    // Hardcoded data until Service call is tested
    var expectedCourses = [
      {
        courseId: '72.03',
        credits: 3,
        name: 'Introducción a Informática',
        semester: 1
      },
      {
        courseId: '94.21',
        credits: 6,
        name: 'Formacion General I',
        semester: 5
      }
    ];

    var $controller, $rootScope, controller;

    var expectedParams = {
      courseId: '72.',
      courseName: 'Introducción a'
    };

    beforeEach(inject(
      function(_$controller_, _$rootScope_) {
        $controller = _$controller_;
        $rootScope = _$rootScope_;
        controller = $controller('CoursesIndexCtrl', {$routeParams: expectedParams});
      }));

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
