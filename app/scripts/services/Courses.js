'use strict';

define(['paw', 'services/AuthenticatedRestangular'], function(paw) {
  paw.service('Courses', ['AuthenticatedRestangular', 'Paths',
  function(AuthenticatedRestangular, Paths) {
    // this is needed as an array is expected from Restangular own methods
    // not needed if we are going to use Restangular's custom* methods
    var rest = AuthenticatedRestangular.withConfig(function(RestangularConfigurer) {
      RestangularConfigurer.addResponseInterceptor(
        function(data, operation, what, url, response, deferred) {
          if (operation === 'getList') {
            if (what === 'courses') {
              return data.courses;
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

    rest.unenroll = function(docket, courseId) {
      return rest.one('students', docket).one('courses', courseId).customDELETE();
    };

    return rest;
  }]);
});
