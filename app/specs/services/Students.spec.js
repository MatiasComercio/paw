// disable max-nested-callbacks linter for this test
// it is good to have a good separation of contexts inside tests

/* eslint-disable max-nested-callbacks */

'use strict';

define(['paw', 'spec-utils', 'api-responses'], function() {
  describe('Students Service', function() {
    beforeEach(module('paw'));

    function addFullName(students) {
      var studentsArray = students.constructor === Array ? students : [students];
      angular.forEach(studentsArray, function(student) {
        student.fullName = student.firstName + ' ' + student.lastName;
      });
    };

    var $rootScope;
    var $httpBackend;
    var specUtilsService;
    var apiResponsesService;
    var StudentsService;
    var expectedStudents;

    beforeEach(inject(function(_$rootScope_, _$httpBackend_, specUtils, apiResponses, Students) {
      $rootScope = _$rootScope_;
      $httpBackend = _$httpBackend_;
      specUtilsService = specUtils;
      apiResponsesService = apiResponses;
      StudentsService = Students;
      expectedStudents = apiResponsesService.students;
    }));

    describe('when getting all students', function() {
      var students;

      beforeEach(function() {
        $httpBackend.expectGET('/api/v1/students').respond(JSON.stringify({students: expectedStudents}));
        StudentsService.getList().then(function(apiStudents) {
          students = specUtilsService.sanitizeRestangularAll(apiStudents);
        });
        $httpBackend.flush();
        $rootScope.$apply();

        // we are implicitly testing that the full name is added by restangular
        addFullName(expectedStudents);
      });

      afterEach(function () {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
      });

      it('is not necessary neither to specify nor to unwrapped the resource', function () {
        expect(students).toEqual(expectedStudents);
      });
    });

    describe('when getting a student', function() {
      var expectedStudentInfo;
      var studentInfo;

      beforeEach(function() {
        expectedStudentInfo = apiResponsesService.student;
        $httpBackend.expectGET('/api/v1/students').respond(JSON.stringify({students: expectedStudents}));
        $httpBackend.expectGET('/api/v1/students/5').respond(JSON.stringify(expectedStudentInfo));
        StudentsService.getList().then(function(apiStudents) {
          var student = apiStudents[0];
          student.get().then(function(apiStudentInfo) {
            studentInfo = specUtilsService.sanitizeRestangularOne(apiStudentInfo);
          });
        });
        $httpBackend.flush();
        $rootScope.$apply();

        // we are implicitly testing that the full name is added by restangular
        addFullName(expectedStudentInfo);
      });

      it('is not necessary to specify the id if the item is restangularized', function() {
        expect(studentInfo).toEqual(expectedStudentInfo);
      });
    });
  });
});
