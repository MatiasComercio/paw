'use strict';

define(['paw', 'services/modalFactory', 'services/flashMessages', 'services/Users'], function(paw) {
  paw.controller('ResetPasswordController',
    ['modalFactory', '$log', 'flashMessages', 'Users', '$route',
    function (modalFactory, $log, flashMessages, Users, $route) {
      var modalTemplateUrl = 'views/modals/reset_password.html';
      var onSuccess = function(dni) {
        Users.resetPassword(dni).then(function() {
          flashMessages.setSuccess('i18nPasswordResetSuccess');
          $route.reload();
        }, function() {
          Paths.get().serverError().go();
        });
      };

      var onFailure = function(msg) {
        $log.info(msg);
      };

      this.open = function (size, dni, firstName, lastName) {
        var resolve = getResolve(dni, firstName, lastName);
        modalFactory.create(size, 'ResetPasswordModalInstanceController', modalTemplateUrl, resolve, onSuccess, onFailure);
      };
    }]);

    paw.controller('ResetPasswordModalInstanceController', function ($uibModalInstance, dni, firstName, lastName) {
      this.dni = dni;
      this.firstName = firstName;
      this.lastName = lastName;

      this.ok = function () {
        $uibModalInstance.close(dni);
      };

      this.cancel = function () {
        $uibModalInstance.dismiss('Reset Password modal dismissed at: ' + new Date());
      };
    });

    function getResolve(dni, firstName, lastName) {
      return {
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
