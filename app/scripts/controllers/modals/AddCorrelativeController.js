'use strict';

define(['paw', 'services/modalFactory', 'services/Courses', 'services/flashMessages'], function(paw) {
  paw.controller('AddCorrelativeController',
    ['modalFactory', '$log', 'Courses', '$route', 'flashMessages',
    function (modalFactory, $log, Courses, $route, flashMessages) {
      var modalTemplateUrl = 'views/modals/add_correlative.html';
      var onSuccess = function(result) {
        Courses.addCorrelative(result.courseId, result.correlativeId)
          .then(function(response) {
            flashMessages.setSuccess('i18nAddCorrelativeSuccess');
            $route.reload();
          }, function(response) {
            if (response.status === 409) { // Correlativity Loop
              flashMessages.setError('i18nAddCorrelativeError');
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

      this.open = function (size, courseId, courseName, correlativeId, correlativeName) {
        var resolve = getResolve(courseId, courseName, correlativeId, correlativeName);
        modalFactory.create(size, 'AddCorrelativeModalInstanceController', modalTemplateUrl, resolve, onSuccess, onFailure);
      };
    }]);

    paw.controller('AddCorrelativeModalInstanceController',
    function($uibModalInstance, courseId, courseName, correlativeId, correlativeName) {
      this.courseId = courseId;
      this.courseName = courseName;
      this.correlativeId = correlativeId;
      this.correlativeName = correlativeName;

      var _this = this;

      this.ok = function () {
        $uibModalInstance.close({
          courseId: courseId,
          courseName: courseName,
          correlativeId: correlativeId,
          correlativeName: correlativeName
        });
      };

      this.cancel = function () {
        $uibModalInstance.dismiss('Add correlative modal dismissed at: ' + new Date());
      };
    });

    function getResolve(courseId, courseName, correlativeId, correlativeName) {
      return {
        courseId: function () {
          return courseId;
        },
        courseName: function () {
          return courseName;
        },
        correlativeId: function () {
          return correlativeId;
        },
        correlativeName: function () {
          return correlativeName;
        }
      };
    };
  }
);
