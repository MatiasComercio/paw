'use strict';

define(['paw', 'services/modalFactory', 'services/Courses', 'services/flashMessages'], function(paw) {
  paw.controller('UnenrollController',
    ['modalFactory', '$log', 'Courses', '$route', 'flashMessages',
    function (modalFactory, $log, Courses, $route, flashMessages) {
      var modalTemplateUrl = 'views/modals/unenroll.html';
      var onSuccess = function(result) {
        Courses.unenroll(result.docket, result.courseId)
          .then(function(response) {
            flashMessages.setSuccess('i18nUnenrollSuccess');
            $route.reload();
          }, function(response) {
            flashMessages.setError('i18nFormErrors');
            $log.warn('[ERROR] - Response: ' + JSON.stringify(response));
            $route.reload();
          });
      };

      var onFailure = function(msg) {
        $log.info(msg);
      };

      this.open = function (size, docket, courseName, courseId) {
        var resolve = getResolve(docket, courseId, courseName);
        modalFactory.create(size, 'UnenrollModalInstanceController', modalTemplateUrl, resolve, onSuccess, onFailure);
      };
    }]);

    paw.controller('UnenrollModalInstanceController',
    function($uibModalInstance, docket, courseId, courseName) {
      this.docket = docket;
      this.courseId = courseId;
      this.courseName = courseName;

      var _this = this;

      this.ok = function () {
        $uibModalInstance.close({
          docket: docket,
          courseId: courseId,
          courseName: courseName
        });
      };

      this.cancel = function () {
        $uibModalInstance.dismiss('Delete correlative modal dismissed at: ' + new Date());
      };
    });

    function getResolve(docket, courseId, courseName) {
      return {
        docket: function () {
          return docket;
        },
        courseId: function () {
          return courseId;
        },
        courseName: function () {
          return courseName;
        }
      };
    };
  }
);
