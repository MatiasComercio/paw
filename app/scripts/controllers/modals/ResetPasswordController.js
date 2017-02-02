'use strict';

define(['paw', 'services/modalFactory'], function(paw) {
  paw.controller('ResetPasswordController',
    ['modalFactory', '$log',
    function (modalFactory, $log) {
      var modalTemplateUrl = 'views/modals/reset_password.html';
      var onSuccess = function(url) {
        $log.info('POST ' + url);
      };

      var onFailure = function(msg) {
        $log.info(msg);
      };

      this.open = function (size, url, dni, firstName, lastName) {
        var resolve = getResolve(url, dni, firstName, lastName);
        modalFactory.create(size, 'ResetPasswordModalInstanceController', modalTemplateUrl, resolve, onSuccess, onFailure);
      };
    }]);

    paw.controller('ResetPasswordModalInstanceController', function ($uibModalInstance, url, dni, firstName, lastName) {
      this.dni = dni;
      this.firstName = firstName;
      this.lastName = lastName;

      this.ok = function () {
        $uibModalInstance.close(url);
      };

      this.cancel = function () {
        $uibModalInstance.dismiss('Reset Password modal dismissed at: ' + new Date());
      };
    });

    function getResolve(url, dni, firstName, lastName) {
      return {
        url: function () {
          return url;
        },
        dni: function () {
          return dni;
        },
        firstName: function () {
          return firstName;
        },
        lastName: function () {
          return lastName;
        }
      };
    };
  }
);
