'use strict';

define(['paw', 'services/Paths', 'services/modalFactory', 'services/Students', 'services/flashMessages'], function(paw) {
  paw.controller('EnrollController',
    ['modalFactory', '$log', 'Students', '$route', 'flashMessages', 'Paths',
    function (modalFactory, $log, Students, $route, flashMessages, Paths) {
      var _this = this;
      var modalTemplateUrl = 'views/modals/enroll.html';
      var onSuccess = function(result) {
        Students.enroll(result.docket, result.courseId)
          .then(function(response) {
            flashMessages.setSuccess('i18nEnrollSuccess');
            $route.reload();
          }, function(response) {
            if (response.status === 409) { // Didn't pass all correlatives
              flashMessages.setError('i18nEnrollError');
              $route.reload();
              return;
            }
            flashMessages.setError('i18nFormErrors');
            $log.warn('[ERROR] - Response: ' + JSON.stringify(response));
            $route.reload();
          });
      };

      var onFailure = function(msg) {
        $log.info(msg);
      };

      this.open = function (size, title, docket, fullName, courseName, courseId) {
        var resolve = getResolve(title, docket, fullName, courseId, courseName);
        modalFactory.create(size, 'EnrollModalInstanceController', modalTemplateUrl, resolve, onSuccess, onFailure);
      };
    }]);

    paw.controller('EnrollModalInstanceController',
    function($uibModalInstance, title, docket, fullName, courseId, courseName) {
      this.title = title;
      this.fullName = fullName;
      this.docket = docket;
      this.courseId = courseId;
      this.courseName = courseName;

      var _this = this;

      this.ok = function () {
        $uibModalInstance.close({
          title: title,
          docket: docket,
          fullName: fullName,
          courseId: courseId,
          courseName: courseName
        });
      };

      this.cancel = function () {
        $uibModalInstance.dismiss('Enroll modal dismissed at: ' + new Date());
      };
    });

    function getResolve(title, docket, fullName, courseId, courseName) {
      return {
        title: function() {
          return title;
        },
        docket: function () {
          return docket;
        },
        fullName: function() {
          return fullName;
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
