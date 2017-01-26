// disable max-nested-callbacks linter for this test
// it is good to have a good separation of contexts inside tests

/* eslint-disable max-nested-callbacks */

'use strict';

define(['paw',
'angular-mocks',
'spec-utils',
'api-responses',
'controllers/HomeCtrl'],
function() {
  describe('Home Ctrl', function() {
    beforeEach(module('paw'));

    var $controller, $rootScope, controller, navDataServiceSpy, specUtilsService,
    apiResponsesService;

    beforeEach(inject(
    function(_$controller_, _$rootScope_, navDataService, specUtils, apiResponses) {
      $controller = _$controller_;
      $rootScope = _$rootScope_;
      navDataServiceSpy = navDataService;
      specUtilsService = specUtils;
      apiResponsesService = apiResponses;
      spyOn(navDataServiceSpy, 'set').and.callThrough();
      controller = $controller('HomeCtrl');
    }));

    it('sets the subSidebar on the navDataService', function() {
      expect(navDataServiceSpy.set).toHaveBeenCalled();
    });

    describe('when admin is required', function() {
      var subSidebarContent;
      beforeEach(function() {
        subSidebarContent = {admin: controller.fetchAdmin()};

        $rootScope.$apply(function() {
          controller.subSidebar = {};
          controller.subSidebarUpdate(subSidebarContent);
        });

        var admin = subSidebarContent.admin;
        subSidebarContent.admin.fullName = admin.firstName + ' ' + admin.lastName;
      });

      it('loads the sub sidebar with the given admin', function() {
        expect(controller.subSidebar).toEqual(subSidebarContent);
      });
    });

    describe('when student is required', function() {
      var subSidebarContent;
      beforeEach(function() {
        subSidebarContent = {student: controller.fetchStudent()};

        $rootScope.$apply(function() {
          controller.subSidebar = {};
          controller.subSidebarUpdate(subSidebarContent);
        });

        var student = subSidebarContent.student;
        subSidebarContent.student.fullName = student.firstName + ' ' + student.lastName;
      });

      it('loads the sub sidebar with the given student', function() {
        expect(controller.subSidebar).toEqual(subSidebarContent);
      });
    });

    describe('when course is required', function() {
      var subSidebarContent;
      beforeEach(function() {
        subSidebarContent = {course: controller.fetchCourse()};

        $rootScope.$apply(function() {
          controller.subSidebar = {};
          controller.subSidebarUpdate(subSidebarContent);
        });
      });

      it('loads the sub sidebar with the given course', function() {
        expect(controller.subSidebar).toEqual(subSidebarContent);
      });
    });

    describe('when toggle courses', function() {
      var CoursesService;

      beforeEach(inject(function(Courses) {
        CoursesService = Courses;
      }));

      describe('when toggling all courses', function() {
        describe('when courses array is empty', function() {
          var expectedCourses;

          beforeEach(function() {
            expectedCourses = apiResponsesService.courses;
            controller.courses = [];
            specUtilsService.resolvePromise(CoursesService, 'getList', expectedCourses);
            controller.toggleCourses();
            $rootScope.$apply();
          });

          it('fills the array with the requested data', function() {
            expect(controller.courses).toEqual(expectedCourses);
          });
        });

        describe('when courses array is not empty', function() {
          beforeEach(function() {
            controller.courses = apiResponsesService.courses;
            controller.toggleCourses();
          });

          it('empties the array', function() {
            expect(controller.courses).toEqual([]);
          });
        });
      });

      describe('when toggling one course', function() {
        var expectedCourse;
        beforeEach(function() {
          expectedCourse = apiResponsesService.courses;
          var mockCourse = {
            get: function() {}
          };
          specUtilsService.resolvePromise(mockCourse, 'get', expectedCourse);
          controller.toggleCourse(mockCourse);
          $rootScope.$apply();
        });

        it('fills the array with the requested data', function() {
          expect(controller.course).toEqual(expectedCourse);
        });
      });
    });

    describe('when toggle students', function() {
      var StudentsService;

      beforeEach(inject(function(Students) {
        StudentsService = Students;
      }));

      describe('when toggling all students', function() {
        describe('when students array is empty', function() {
          var expectedStudents;

          beforeEach(function() {
            expectedStudents = apiResponsesService.students;
            controller.students = [];
            specUtilsService.resolvePromise(StudentsService, 'getList', expectedStudents);
            controller.toggleStudents();
            $rootScope.$apply(); // for promises to be resolved
          });

          it('fills the array with the requested data', function() {
            expect(controller.students).toEqual(expectedStudents);
          });
        });

        describe('when students array is not empty', function() {
          beforeEach(function() {
            controller.students = apiResponsesService.students;
            controller.toggleStudents();
          });

          it('empties the array', function() {
            expect(controller.students).toEqual([]);
          });
        });
      });

      describe('when toggling one student', function() {
        var expectedStudent;
        beforeEach(function() {
          expectedStudent = apiResponsesService.student;
          var mockStudent = {
            get: function() {}
          };
          specUtilsService.resolvePromise(mockStudent, 'get', expectedStudent);
          controller.toggleStudent(mockStudent);
          $rootScope.$apply(); // for promises to be resolved
        });

        it('fills the array with the requested data', function() {
          expect(controller.student).toEqual(expectedStudent);
        });
      });
    });
  });
});
