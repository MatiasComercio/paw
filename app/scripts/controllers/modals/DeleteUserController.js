'use strict';

define(['paw', 'services/modalFactory',
'services/Students', 'services/Admins', 'services/flashMessages', 'services/Paths'], function(paw) {
  paw.controller('DeleteUserController',
    ['modalFactory', '$log', 'Students', 'Admins', 'flashMessages', 'Paths',
    function (modalFactory, $log, Students, Admins, flashMessages, Paths) {
      var modalTemplateUrl = 'views/modals/delete_user.html';
      var onSuccess = function(user) {
        if (user.admin) {
          Admins.remove(user).then(function() {
            flashMessages.setSuccess('i18nAdminSuccessfullyDeleted');
            Paths.get().admins().go();
          });
        } else {
          Students.remove(user).then(function() {
            flashMessages.setSuccess('i18nStudentSuccessfullyDeleted');
            Paths.get().students().go();
          });
        }
      };

      var onFailure = function(msg) {
        $log.info(msg);
      };

      this.open = function (size, user) {
        var resolve = getResolve(user);
        modalFactory.create(size, 'DeleteUserModalInstanceController', modalTemplateUrl, resolve, onSuccess, onFailure);
      };
    }]);

    paw.controller('DeleteUserModalInstanceController',
    function($uibModalInstance, user) {
      this.dni = user.dni;
      this.firstName = user.firstName;
      this.lastName = user.lastName;

      this.ok = function () {
        $uibModalInstance.close(user);
      };

      this.cancel = function () {
        $uibModalInstance.dismiss('Delete User modal dismissed at: ' + new Date());
      };
    });

    function getResolve(user) {
      return {
        user: function () {
          return user;
        }
      };
    };
  }
);
