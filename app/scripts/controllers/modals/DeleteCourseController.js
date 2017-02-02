'use strict';

define(['paw', 'services/modalFactory'], function(paw) {
  paw.controller('DeleteCourseController',
    ['modalFactory', '$log',
    function (modalFactory, $log) {
      var modalTemplateUrl = 'views/modals/delete_course.html';
      var onSuccess = function(url) {
        $log.info('POST ' + url);
      };

      var onFailure = function(msg) {
        $log.info(msg);
      };

      this.open = function (size, url, id, name) {
        var resolve = getResolve(url, id, name);
        modalFactory.create(size, 'DeleteCourseModalInstanceController', modalTemplateUrl, resolve, onSuccess, onFailure);
      };
    }]);

    paw.controller('DeleteCourseModalInstanceController',
    function ($uibModalInstance, url, id, name) {
      this.id = id;
      this.name = name;

      this.ok = function () {
        $uibModalInstance.close(url);
      };

      this.cancel = function () {
        $uibModalInstance.dismiss('Delete Course modal dismissed at: ' + new Date());
      };
    });

    function getResolve(url, id, name) {
      return {
        url: function () {
          return url;
        },
        id: function () {
          return id;
        },
        name: function () {
          return name;
        }
      };
    };
  }
);
