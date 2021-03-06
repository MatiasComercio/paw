'use strict';

define(['paw', 'services/modalFactory', 'services/Courses', 'services/flashMessages'], function(paw) {
  paw.controller('CloseFinalController',
    ['modalFactory', '$log', 'Courses', '$route', 'flashMessages',
    function (modalFactory, $log, Courses, $route, flashMessages) {
      var modalTemplateUrl = 'views/modals/close_final.html';
      var onSuccess = function(result) {
        Courses.closeFinalInscription(result.courseId, result.id)
          .then(function(response) {
            flashMessages.setSuccess('i18nCloseFinalSuccess');
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
      this.open = function (size, title, courseId, courseName, finalExamDate, id) {
        var resolve = getResolve(title, courseId, courseName, finalExamDate, id);
        modalFactory.create(size, 'CloseFinalModalInstanceController', modalTemplateUrl, resolve, onSuccess, onFailure);
      };
    }]);

    paw.controller('CloseFinalModalInstanceController',
    function($uibModalInstance, title, courseId, courseName, finalExamDate, id) {
      this.title = title;
      this.courseId = courseId;
      this.courseName = courseName;
      this.finalExamDate = finalExamDate;
      this.id = id;

      var _this = this;

      this.ok = function () {
        $uibModalInstance.close({
          courseId: courseId,
          courseName: courseName,
          finalExamDate: finalExamDate,
          id: id
        });
      };

      this.cancel = function () {
        $uibModalInstance.dismiss('Close Final modal dismissed at: ' + new Date());
      };
    });

    function getResolve(title, courseId, courseName, finalExamDate, id) {
      return {
        title: function() {
          return title;
        },
        courseId: function () {
          return courseId;
        },
        courseName: function () {
          return courseName;
        },
        finalExamDate: function () {
          return finalExamDate;
        },
        id: function() {
          return id;
        }
      };
    };
  }
);
