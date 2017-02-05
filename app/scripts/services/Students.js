'use strict';

define(['paw', 'services/AuthenticatedRestangular', 'services/navDataService'], function(paw) {
  paw.service('Students', ['AuthenticatedRestangular', 'Paths', 'navDataService',
  function(AuthenticatedRestangular, Paths, navDataService) {
    // this is needed as an array is expected from Restangular own methods
    // not needed if we are going to use Restangular's custom* methods
    var rest = AuthenticatedRestangular.withConfig(function(RestangularConfigurer) {
      RestangularConfigurer.addResponseInterceptor(
        function(data, operation, what, url, response, deferred) {
          if (operation === 'getList') {
            if (what === 'students') {
              return data.students;
            }
            if (what === 'courses') {
              return data.courses;
            }
            if (what === 'finalInscriptions') {
              return data.finalInscriptions;
            }
            if (what === 'grades') {
              var transcript = data.transcript;
              transcript.currentCredits = data.currentCredits;
              transcript.totalCredits = data.totalCredits;
              return transcript;
            }
            return data;
          } else if (operation === 'get') {
            if (what === 'available') {
              return data.courses || data.finalInscriptions;
            }
            return data;
          } else if (operation === 'post') {
            return response;
          } else {
            return data;
          }
        }
      );
      RestangularConfigurer.setRestangularFields({
        id: 'docket'
      });
      RestangularConfigurer.extendModel('students', function(student) {
        student.fullName = student.firstName + ' ' + student.lastName;
        student.profileUrl = Paths.get().students(student).path;
        return student;
      });
    });

    var subject = rest.all('students');

    // add own methods as follows
    rest.setOnSubSidebar = function(student) {
      var _this = {};
      var getUserCallback = function() {
        // get the user
        _this.user = navDataService.get('user');
        if (!_this.user) {
          return; // nothing to set
        }

        var subSidebar = {};
        subSidebar.student = student;
        subSidebar.student.actions = Paths.getStudentActions(student, _this.user);

        // register the current sidebar
        navDataService.set('subSidebar', subSidebar);
      };
      navDataService.registerObserverCallback('user', getUserCallback);
      getUserCallback();
    };

    rest.getList = function() {
      return subject.getList();
    };

    rest.get = function(docket) {
      return subject.get(docket);
    };

    rest.updateGrade = function(docket, gradeId, courseId, newGrade) {
      var body = {
        courseId: courseId,
        grade: newGrade
      };
      return rest.one('students', docket).one('grades', gradeId).customPOST(body);
    };

    rest.new = function(student) {
      return rest.all('students').post(student);
    };

    rest.update = function(student) {
      return student.customPOST(student);
    };

    rest.enroll = function(docket, courseId) {
      var body = {
        courseId: courseId
      };
      return rest.one('students', docket).all('courses').customPOST(body);
    };

    rest.unenroll = function(docket, courseId) {
      return rest.one('students', docket).one('courses', courseId).customDELETE();
    };

    rest.finalEnroll = function(docket, id) {
      console.log(id);
      return rest.one('students', docket).one('finalInscriptions', id).customPOST();
    };

    rest.finalUnenroll = function(docket, id) {
      return rest.one('students', docket).one('finalInscriptions', id).customDELETE();
    };

    return rest;
  }]);
});
