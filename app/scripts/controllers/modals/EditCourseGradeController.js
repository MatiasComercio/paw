'use strict';

define(['paw', 'services/modalFactory', 'services/Students', 'services/flashMessages'], function(paw) {
  paw.controller('EditCourseGradeController',
    ['modalFactory', '$log', 'Students', '$route', 'flashMessages',
    function (modalFactory, $log, Students, $route, flashMessages) {
      var modalTemplateUrl = 'views/modals/edit_course_grade.html';
      var onSuccess = function(result) {
        Students.updateGrade(result.docket, result.gradeId, result.courseId, result.newGrade)
          .then(function(response) {
            flashMessages.setSuccess('i18nEditSuccess');
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

      this.open = function (size, docket, fullName, courseId, courseName, grade, gradeId) {
        var resolve = getResolve(docket, fullName, courseId, courseName, grade, gradeId);
        modalFactory.create(size, 'EditCourseGradeModalInstanceController', modalTemplateUrl, resolve, onSuccess, onFailure);
      };
    }]);

    paw.controller('EditCourseGradeModalInstanceController',
    function($uibModalInstance, docket, fullName, courseId, courseName, grade, gradeId) {
      this.docket = docket;
      this.fullName = fullName;
      this.courseId = courseId;
      this.courseName = courseName;
      this.grade = grade;
      this.gradeId = gradeId;

      var _this = this;

      this.ok = function () {
        $uibModalInstance.close({
          docket: docket,
          gradeId: gradeId,
          courseId: courseId,
          newGrade: _this.grade
        });
      };

      this.cancel = function () {
        $uibModalInstance.dismiss('Edit course grade modal dismissed at: ' + new Date());
      };
    });

    function getResolve(docket, fullName, courseId, courseName, grade, gradeId) {
      return {
        docket: function () {
          return docket;
        },
        fullName: function () {
          return fullName;
        },
        courseId: function () {
          return courseId;
        },
        courseName: function () {
          return courseName;
        },
        grade: function () {
          return grade;
        },
        gradeId: function () {
          return gradeId;
        }
      };
    };
  }
);
