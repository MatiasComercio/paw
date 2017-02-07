'use strict';

define(['paw', 'services/AuthenticatedRestangular', 'services/navDataService'], function(paw) {
  paw.service('Courses', ['AuthenticatedRestangular', 'Paths', 'navDataService',
  function(AuthenticatedRestangular, Paths, navDataService) {
    // this is needed as an array is expected from Restangular own methods
    // not needed if we are going to use Restangular's custom* methods
    var rest = AuthenticatedRestangular.withConfig(function(RestangularConfigurer) {
      RestangularConfigurer.addResponseInterceptor(
        function(data, operation, what, url, response, deferred) {
          if (operation === 'getList') {
            if (what === 'courses') {
              return data.courses;
            }
            if (what === 'students') {
              return data.students;
            }
            if (what === 'correlatives') {
              return data.courses;
            }
            if (what === 'finalInscriptions') {
              return data.finalInscriptions;
            }
            return data;
          } else if (operation === 'get') {
            if (what === 'available') {
              return data.courses;
            }
            if (what === 'passed') {
              return data.students;
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
        id: 'courseId'
      });

      RestangularConfigurer.extendModel('courses', function(course) {
        course.showUrl = Paths.get().courses(course).path;
        return course;
      });
    });

    var subject = rest.all('courses');

    rest.setOnSubSidebar = function(course) {
      var _this = {};
      var getUserCallback = function() {
        // get the user
        _this.user = navDataService.get('user');
        if (!_this.user) {
          return; // nothing to set
        }

        var subSidebar = {};
        subSidebar.course = course;
        subSidebar.course.actions = Paths.getCourseActions(course, _this.user);

        // register the current sidebar
        navDataService.set('subSidebar', subSidebar);
      };
      navDataService.registerObserverCallback('user', getUserCallback);
      getUserCallback();
    };

    // add own methods as follows
    rest.getList = function() {
      return subject.getList();
    };

    rest.get = function(courseId) {
      return subject.get(courseId);
    };

    rest.addCorrelative = function(courseId, correlativeId) {
      var body = {
        correlativeId: correlativeId
      };
      return rest.one('courses', courseId).all('correlatives').customPOST(body);
    };

    rest.deleteCorrelative = function(courseId, correlativeId) {
      return rest.one('courses', courseId).one('correlatives', correlativeId).remove();
    };

    rest.new = function(course) {
      return rest.all('courses').post(course);
    };

    rest.update = function(originalCourse, editedCourse) {
      return originalCourse.customPOST(editedCourse);
    };

    rest.newFinalInscription = function(courseId, finalInscription) {
      var body = {
        finalExamDate: finalInscription.finalExamDate,
        maxVacancy: finalInscription.vacancy
      };
      return rest.one('courses', courseId).all('finalInscriptions').customPOST(body);
    };

    rest.deleteFinalInscription = function(courseId, id) {
      return rest.one('courses', courseId).one('finalInscriptions', id).customDELETE();
    };

    rest.closeFinalInscription = function(courseId, id) {
      return rest.one('courses', courseId).one('finalInscriptions', id).all('close').customPOST();
    };

    rest.remove = function(course) {
      return course.remove();
    };

    rest.qualify = function(course, qualifiedStudents) {
      return course.all('students').all('qualify').customPOST(qualifiedStudents);
    };

    rest.qualifyFinalInscription = function(course, inscriptionId, qualifiedStudents) {
      return course.one('finalInscriptions', inscriptionId).all('qualify').customPOST(qualifiedStudents);
    };

    return rest;
  }]);
});
