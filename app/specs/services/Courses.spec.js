// disable max-nested-callbacks linter for this test
// it is good to have a good separation of contexts inside tests

/* eslint-disable max-nested-callbacks */

'use strict';

define(['paw', 'spec-utils', 'api-responses',
'services/Courses'], function() {
  describe('Courses Service', function() {
    beforeEach(module('paw'));

    var $rootScope;
    var $httpBackend;
    var apiResponsesService;
    var CoursesService;
    var PathsService;
    var navDataServiceService;

    beforeEach(inject(function(_$rootScope_, _$httpBackend_,
      apiResponses, Courses, Paths, navDataService) {

      $rootScope = _$rootScope_;
      $httpBackend = _$httpBackend_;
      apiResponsesService = apiResponses;
      CoursesService = Courses;
      PathsService = Paths;
      navDataServiceService = navDataService;
    }));

    describe('#setOnSubSidebar', function() {
      var course;
      var expectedCourse;
      var expectedSubSidebar;
      var expectedActions = true;

      beforeEach(function() {
        course = apiResponsesService.course;
        expectedCourse = course;
        expectedCourse.actions = expectedCourse;
        expectedSubSidebar = {
          course: expectedCourse
        };
        spyOn(PathsService, 'getCourseActions').and.returnValue(expectedActions);
      });

      describe('when user is not defined', function() {
        beforeEach(function() {
          navDataServiceService.set('user', undefined);
          spyOn(navDataServiceService, 'set');
          CoursesService.setOnSubSidebar(course);
        });

        it('is expected not to set anything on the subSidebar', function() {
          expect(navDataServiceService.set).not.toHaveBeenCalled();
        });
      });

      describe('when user is defined', function() {
        beforeEach(function() {
          navDataServiceService.set('user', apiResponsesService.admin);
          spyOn(navDataServiceService, 'set');
          CoursesService.setOnSubSidebar(course);
        });

        it('correctly sets the subSidebar with the current user', function() {
          expect(navDataServiceService.set).toHaveBeenCalledWith('subSidebar', expectedSubSidebar);
        });
      });
    });

    describe('when doing an API call', function() {
      afterEach(function () {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
      });

      describe('when not doing getList with courses or students resources', function() {
        it('returns the default data', function() {
          $httpBackend.expectGET('api/v1/admins', apiResponsesService.header).respond([]);
          CoursesService.all('admins').getList();
          $httpBackend.flush();
          $rootScope.$apply();
        });
        it('returns the default data', function() {
          $httpBackend.expectGET('api/v1/students', apiResponsesService.header).respond([]);
          CoursesService.all('students').getList();
          $httpBackend.flush();
          $rootScope.$apply();
        });
        it('returns the default data', function() {
          $httpBackend.expectGET('api/v1/correlatives', apiResponsesService.header).respond([]);
          CoursesService.all('correlatives').getList();
          $httpBackend.flush();
          $rootScope.$apply();
        });
        it('returns the default data', function() {
          $httpBackend.expectGET('api/v1/finalInscriptions', apiResponsesService.header).respond([]);
          CoursesService.all('finalInscriptions').getList();
          $httpBackend.flush();
          $rootScope.$apply();
        });
      });

      describe('when not doing get with courses or students resources', function() {
        it('returns the default data', function() {
          $httpBackend.expectGET('api/v1/available', apiResponsesService.header).respond([]);
          CoursesService.all('available').customGET();
          $httpBackend.flush();
          $rootScope.$apply();
        });
        it('returns the default data', function() {
          $httpBackend.expectGET('api/v1/passed', apiResponsesService.header).respond([]);
          CoursesService.all('passed').customGET();
          $httpBackend.flush();
          $rootScope.$apply();
        });
      });

      describe('GET #getList', function() {
        var expectedCourses;

        beforeEach(function() {
          expectedCourses = apiResponsesService.courses;
        });

        it('is expected to fetch the courses list from the API', function() {
          $httpBackend.expectGET('api/v1/courses', apiResponsesService.header).respond(expectedCourses);
          CoursesService.getList().then(function() {});
          $httpBackend.flush();
          $rootScope.$apply();
        });
      });

      describe('GET #get', function() {
        var expectedCourse;

        beforeEach(function() {
          expectedCourse = apiResponsesService.course;
        });

        it('is expected to fetch the course with the given courseId from the API', function() {
          $httpBackend.expectGET('api/v1/courses/123', apiResponsesService.header).respond(expectedCourse);
          CoursesService.get(123).then(function() {});
          $httpBackend.flush();
          $rootScope.$apply();
        });
      });

      describe('POST #new', function() {
        it('is expected to create a new course on the API', function() {
          var body = apiResponsesService.course;
          $httpBackend.expectPOST('api/v1/courses', body, apiResponsesService.header).respond(201, '');
          CoursesService.new(body).then(function() {});
          $httpBackend.flush();
          $rootScope.$apply();
        });
      });

      describe('POST #update', function() {
        var originalCourse;
        var decoratedCourse;

        beforeEach(function() {
          originalCourse = jasmine.createSpyObj('originalCourse', ['customPOST']);
          decoratedCourse = {};
          CoursesService.update(originalCourse, decoratedCourse);
        });

        it('is expected to update the given admin on the API', function() {
          expect(originalCourse.customPOST).toHaveBeenCalledWith(decoratedCourse);
        });
      });

      describe('POST #addCorrelative', function() {
        it('is expected to add a new correlative on the API', function() {
          var expectedBody = {
            correlativeId: 456
          };
          $httpBackend.expectPOST('api/v1/courses/123/correlatives', expectedBody, apiResponsesService.header).respond(201, '');
          CoursesService.addCorrelative(123, 456);
          $httpBackend.flush();
          $rootScope.$apply();
        });
      });

      describe('DELETE #deleteCorrelative', function() {
        it('is expected to delete the given correlative on the API', function() {
          $httpBackend.expectDELETE('api/v1/courses/123/correlatives/456', apiResponsesService.header).respond(201, '');
          CoursesService.deleteCorrelative(123, 456);
          $httpBackend.flush();
          $rootScope.$apply();
        });
      });

      describe('POST #newFinalInscription', function() {
        it('is expected to delete the given correlative on the API', function() {
          var finalInscription = {
            finalExamDate: '2017-01-20T18:45:00',
            vacancy: 25
          };
          var expectedFinalInscription = {
            finalExamDate: '2017-01-20T18:45:00',
            maxVacancy: 25
          };
          var courseId = 123;

          $httpBackend.expectPOST('api/v1/courses/123/finalInscriptions', expectedFinalInscription, apiResponsesService.header).respond(201, '');
          CoursesService.newFinalInscription(courseId, finalInscription);
          $httpBackend.flush();
          $rootScope.$apply();
        });
      });

      describe('DELETE #deleteFinalInscription', function() {
        it('is expected to delete the given admin on the API', function() {
          var courseId = 123;
          var finalInscriptionId = 456;

          $httpBackend.expectDELETE('api/v1/courses/123/finalInscriptions/456', apiResponsesService.header).respond(204, '');
          CoursesService.deleteFinalInscription(courseId, finalInscriptionId);
          $httpBackend.flush();
          $rootScope.$apply();
        });
      });

      describe('POST #closeFinalInscription', function() {
        it('is expected to close the given final inscription on the API', function() {
          var courseId = 123;
          var finalInscriptionId = 456;

          $httpBackend.expectPOST('api/v1/courses/123/finalInscriptions/456/close', apiResponsesService.header).respond(204, '');
          CoursesService.closeFinalInscription(courseId, finalInscriptionId);
          $httpBackend.flush();
          $rootScope.$apply();
        });
      });

      describe('DELETE #remove', function() {
        var decoratedCourse;

        beforeEach(function() {
          decoratedCourse = jasmine.createSpyObj('decoratedCourse', ['remove']);
          CoursesService.remove(decoratedCourse);
        });

        it('is expected to delete the given admin on the API', function() {
          expect(decoratedCourse.remove).toHaveBeenCalled();
        });
      });
    });
  });
});
