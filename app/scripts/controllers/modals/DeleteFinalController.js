'use strict';

define(['paw', 'services/modalFactory', 'services/Courses', 'services/flashMessages'], function(paw) {
  paw.controller('DeleteFinalController',
    ['modalFactory', '$log', 'Courses', '$route', 'flashMessages', 'Paths',
    function (modalFactory, $log, Courses, $route, flashMessages, Paths) {
      var modalTemplateUrl = 'views/modals/delete_final.html';
      var onSuccess = function(result) {
        Courses.deleteFinalInscription(result.courseId, result.id)
          .then(function(response) {
            flashMessages.setSuccess('i18nDeleteFinalSuccess');
            Paths.get().courses({courseId: result.courseId}).go();
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
        modalFactory.create(size, 'DeleteFinalModalInstanceController', modalTemplateUrl, resolve, onSuccess, onFailure);
      };
    }]);

    paw.controller('DeleteFinalModalInstanceController',
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
        $uibModalInstance.dismiss('Delete Final modal dismissed at: ' + new Date());
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
