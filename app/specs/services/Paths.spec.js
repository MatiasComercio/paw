// disable max-nested-callbacks linter for this test
// it is good to have a good separation of contexts inside tests

/* eslint-disable max-nested-callbacks */

'use strict';

define(['paw', 'spec-utils', 'api-responses', 'services/Paths'],
function() {
  describe('Paths Service', function() {
    beforeEach(module('paw'));

    var $rootScope;
    var $location;
    var apiResponsesService;
    var AuthenticationService;
    var PathsService;

    beforeEach(inject(function(_$rootScope_, _$location_, apiResponses, Authentication, Paths) {
      $rootScope = _$rootScope_;
      $location = _$location_;
      apiResponsesService = apiResponses;
      AuthenticationService = Authentication;
      PathsService = Paths;
    }));

    describe("when getting admin's actions", function() {
      var admin;
      var expectedActions;
      var currentUser;

      beforeEach(function() {
        currentUser = apiResponsesService.currentAdmin;
        admin = apiResponsesService.admin;
        expectedActions = {
          show: {
            path: '#!/admins/' + admin.dni
          },
          edit: {
            path: '#!/admins/' + admin.dni + '/edit'
          },
          resetPassword: true,
          // delete admin endpoint is not ready yet
          delete: false,
          editPassword: {
            path: '#!/users/change_password'
          }
        };
      });

      it('returns the expected actions', function () {
        expect(PathsService.getAdminActions(admin, currentUser)).toEqual(expectedActions);
      });
    });

    describe("when getting student's actions", function() {
      var student;
      var expectedActions;
      var currentUser;

      beforeEach(function() {
        currentUser = apiResponsesService.currentStudent;
        student = apiResponsesService.currentStudent;
        expectedActions = {
          show: {
            path: '#!/students/' + student.docket
          },
          edit: {
            path: '#!/students/' + student.docket + '/edit'
          },
          resetPassword: true,
          courses: {
            path: '#!/students/' + student.docket + '/courses'
          },
          grades: {
            path: '#!/students/' + student.docket + '/grades'
          },
          inscriptions: {
            path: '#!/students/' + student.docket + '/inscriptions'
          },
          finals: {
            path: '#!/students/' + student.docket + '/final_inscriptions'
          },
          delete: true,
          editPassword: {
            path: '#!/users/change_password'
          }
        };
      });

      it('returns the expected actions', function () {
        expect(PathsService.getStudentActions(student, currentUser)).toEqual(expectedActions);
      });
    });

    describe("when getting course's actions", function() {
      var course;
      var expectedActions;
      var currentUser;

      beforeEach(function() {
        course = apiResponsesService.course;
        currentUser = apiResponsesService.currentAdmin;
        expectedActions = {
          show: {
            path: '#!/courses/' + course.courseId
          },
          edit: {
            path: '#!/courses/' + course.courseId + '/edit'
          },
          students: {
            path: '#!/courses/' + course.courseId + '/students'
          },
          approved: {
            path: '#!/courses/' + course.courseId + '/students/passed'
          },
          addCorrelative: {
            path: '#!/courses/' + course.courseId + '/correlatives/new'
          },
          addFinal: {
            path: '#!/courses/' + course.courseId + '/final_inscriptions/new'
          },
          delete: true
        };
      });

      it('returns the expected actions', function () {
        expect(PathsService.getCourseActions(course, currentUser)).toEqual(expectedActions);
      });
    });

    describe('when getting a custom path builder', function() {
      var expectedPath = '#!/login/logout/unauthorized/admins/students/new/edit' +
      '/change_password/grades/inscriptions/final_inscriptions/courses/students/passed' +
      '/correlatives/new/';

      var newPath;

      beforeEach(function() {
        newPath = PathsService.get().login().logout().unauthorized().admins().students()
        .new().edit().editPassword().grades().inscriptions().finals().courses().approved()
        .addCorrelative().index();
      });

      it('correctly builds the path', function() {
        expect(newPath.path).toEqual(expectedPath);
      });

      describe('when calling its go function', function() {
        beforeEach(function() {
          spyOn($location, 'path');
          newPath.go();
        });

        it('redirects to the builded path', function() {
          expect($location.path).toHaveBeenCalledWith(newPath.absolutePath());
        });
      });
    });
  });
});
