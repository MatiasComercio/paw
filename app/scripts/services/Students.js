'use strict';

define(['paw', 'services/AuthenticatedRestangular'], function(paw) {
  paw.service('Students', ['AuthenticatedRestangular',
  function(AuthenticatedRestangular) {
    // this is needed as an array is expected from Restangular own methods
    // not needed if we are going to use Restangular's custom* methods
    var rest = AuthenticatedRestangular.withConfig(function(RestangularConfigurer) {
      RestangularConfigurer.addResponseInterceptor(
        function(data, operation, what, url, response, deferred) {
          if (operation === 'getList') {
            if (what === 'students') {
              return data.students;
            }
            if (what === 'grades') {
              var transcript = data.transcript;
              transcript.currentCredits = data.currentCredits;
              transcript.totalCredits = data.totalCredits;
              return transcript;
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
        return student;
      });
    });

    var subject = rest.all('students');

    // add own methods as follows
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

    return rest;
  }]);
});
