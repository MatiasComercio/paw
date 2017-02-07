'use strict';

define(['paw', 'services/modalFactory', 'services/Courses',
'services/Paths', 'services/flashMessages'], function(paw) {
  paw.controller('DeleteCourseController',
    ['modalFactory', '$log', 'Courses', 'Paths', 'flashMessages', '$route',
    function (modalFactory, $log, Courses, Paths, flashMessages, $route) {
      var modalTemplateUrl = 'views/modals/delete_course.html';
      var onSuccess = function(course) {
        Courses.remove(course).then(function() {
          flashMessages.setSuccess('i18nCourseSuccessfullyDeleted');
          Paths.get().courses().go();
        }, function() {
          flashMessages.setError('i18nCourseDeleteHasAssociatedInformation');
          $route.reload();
        });
      };

      var onFailure = function(msg) {
        $log.info(msg);
      };

      this.open = function (size, course) {
        var resolve = getResolve(course);
        modalFactory.create(size, 'DeleteCourseModalInstanceController', modalTemplateUrl, resolve, onSuccess, onFailure);
      };
    }]);

    paw.controller('DeleteCourseModalInstanceController',
    function ($uibModalInstance, course) {
      this.id = course.courseId;
      this.name = course.name;

      this.ok = function () {
        $uibModalInstance.close(course);
      };

      this.cancel = function () {
        $uibModalInstance.dismiss('Delete Course modal dismissed at: ' + new Date());
      };
    });

    function getResolve(course) {
      return {
        course: function () {
          return course;
        }
      };
    };
  }
);
