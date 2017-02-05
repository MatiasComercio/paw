'use strict';

define(['paw', 'services/modalFactory', 'services/Students', 'services/flashMessages'], function(paw) {
  paw.controller('FinalUnenrollController',
    ['modalFactory', '$log', 'Students', '$route', 'flashMessages',
    function (modalFactory, $log, Students, $route, flashMessages) {
      var modalTemplateUrl = 'views/modals/final_enroll.html';
      var onSuccess = function(result) {
        Students.finalUnenroll(result.docket, result.id)
          .then(function(response) {
            flashMessages.setSuccess('i18nFinalUnenrollSuccess');
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

      this.open = function (size, title, docket, fullName, courseName, finalExamDate, id) {
        var resolve = getResolve(title, docket, fullName, courseName, finalExamDate, id);
        modalFactory.create(size, 'FinalUnenrollModalInstanceController', modalTemplateUrl, resolve, onSuccess, onFailure);
      };
    }]);

    paw.controller('FinalUnenrollModalInstanceController',
    function($uibModalInstance, title, docket, fullName, courseName, finalExamDate, id) {
      this.title = title;
      this.docket = docket;
      this.fullName = fullName;
      this.courseName = courseName;
      this.finalExamDate = finalExamDate;
      this.id = id;

      var _this = this;

      this.ok = function () {
        $uibModalInstance.close({
          docket: docket,
          fullName: fullName,
          courseName: courseName,
          finalExamDate: finalExamDate,
          id: id
        });
      };

      this.cancel = function () {
        $uibModalInstance.dismiss('Final inscription unenroll modal dismissed at: ' + new Date());
      };
    });

    function getResolve(title, docket, fullName, courseName, finalExamDate, id) {
      return {
        title: function() {
          return title;
        },
        docket: function () {
          return docket;
        },
        fullName: function () {
          return fullName;
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
