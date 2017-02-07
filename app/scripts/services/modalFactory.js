'use strict';

define(['paw'], function(paw) {
  paw.service('modalFactory', function($uibModal, $document) {
    this.create = function (size, modalCtrl, modalTemplateUrl, resolve, onSuccess, onFailure) {
      var parentElem = angular.element($document[0].querySelector('.modal-parent'));
      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: modalTemplateUrl,
        controller: modalCtrl,
        controllerAs: 'controller',
        size: size,
        appendTo: parentElem,
        resolve: resolve
      });

      modalInstance.result.then(onSuccess, onFailure);
    };
  });
});
