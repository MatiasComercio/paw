// disable max-nested-callbacks linter for this test
// it is good to have a good separation of contexts inside tests

/* eslint-disable max-nested-callbacks */

'use strict';

define(['paw', 'spec-utils', 'api-responses'], function() {
  describe('Courses Service', function() {
    beforeEach(module('paw'));

    var $rootScope;
    var $httpBackend;
    var specUtilsService;
    var apiResponsesService;
    var CoursesService;
    var expectedCourses;

    beforeEach(inject(function(_$rootScope_, _$httpBackend_, specUtils, apiResponses, Courses) {
      $rootScope = _$rootScope_;
      $httpBackend = _$httpBackend_;
      specUtilsService = specUtils;
      apiResponsesService = apiResponses;
      CoursesService = Courses;
      expectedCourses = apiResponsesService.courses;
    }));

    describe('when getting all courses', function() {
      var courses;

      beforeEach(function() {
        $httpBackend.expectGET('api/v1/courses').respond(JSON.stringify({courses: expectedCourses}));
        CoursesService.getList().then(function(apiCourses) {
          courses = specUtilsService.sanitizeRestangularAll(apiCourses);
        });
        $httpBackend.flush();
        $rootScope.$apply();
      });

      afterEach(function () {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
      });

      it('is not necessary neither to specify nor to unwrapped the resource', function () {
        expect(courses).toEqual(expectedCourses);
      });
    });

    describe('when getting a course', function() {

      var expectedCourseInfo;

      var courseInfo;

      beforeEach(function() {
        expectedCourseInfo = apiResponsesService.course;
        $httpBackend.expectGET('api/v1/courses').respond(JSON.stringify({courses: expectedCourses}));
        $httpBackend.expectGET('api/v1/courses/72.03').respond(JSON.stringify(expectedCourseInfo));
        CoursesService.getList().then(function(apiCourses) {
          var course = apiCourses[0];
          course.get().then(function(apiCourseInfo) {
            courseInfo = specUtilsService.sanitizeRestangularOne(apiCourseInfo);
          });
        });
        $httpBackend.flush();
        $rootScope.$apply();
      });

      it('is not necessary to specify the id if the item is restangularized', function() {
        expect(courseInfo).toEqual(expectedCourseInfo);
      });
    });
  });
});
