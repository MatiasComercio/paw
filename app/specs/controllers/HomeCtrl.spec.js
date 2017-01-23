// disable max-nested-callbacks linter for this test
// it is good to have a good separation of contexts inside tests

/* eslint-disable max-nested-callbacks */

'use strict';

define(['paw',
'angular-mocks',
'controllers/HomeCtrl'],
function() {
  describe('Home Ctrl', function() {
    beforeEach(module('paw'));

    var $controller, $rootScope, controller, navDataServiceSpy;

    beforeEach(inject(function(_$controller_, _$rootScope_, navDataService) {
      $controller = _$controller_;
      $rootScope = _$rootScope_;
      navDataServiceSpy = navDataService;
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
  });
});
