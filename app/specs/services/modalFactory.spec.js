'use strict';

define(['paw', 'angular-mocks', 'services/modalFactory'], function() {
  describe('Modal Factory', function() {
    beforeEach(module('paw'));
    // services
    var uibModal;

    // testing variables
    var ariaLabelledBy = 'modal-title';
    var ariaDescribedBy = 'modal-body';
    var modalTemplateUrl = '/views/directives/navbar.html';
    var modalCtrl = function() {
      var ctrl = {controller: true};
    };
    var controllerAs = 'controller';
    var size = 'size';

    // needs to be filled with the modal-parent div element
    var appendTo = 'element';

    var resolve = {};
    var success = undefined;
    var onSuccess = function() {
      success = true;
    };
    var onFailure = function() {
      success = false;
    };

    var modalInstance;
    var deferred, promise;
    var $scope;
    beforeEach(inject(function(_$uibModal_, _$rootScope_, _$q_) {
      uibModal = _$uibModal_;
      $scope = _$rootScope_.$new();

      // we use the $q service to create a mock instance of defer
      deferred = _$q_.defer();

      modalInstance = {
        result: deferred.promise
      };

      spyOn(angular, 'element').and.returnValue('element');
      spyOn(uibModal, 'open').and.returnValue(modalInstance);
      spyOn(modalInstance.result, 'then').and.callThrough();
    }));

    var expectedUibModalOpenParams = {
      ariaLabelledBy: ariaLabelledBy,
      ariaDescribedBy: ariaDescribedBy,
      templateUrl: modalTemplateUrl,
      controller: modalCtrl,
      controllerAs: controllerAs,
      size: size,
      appendTo: appendTo,
      resolve: resolve
    };

    describe('when creating a new modal', function() {
      beforeEach(inject(function(modalFactory) {
        modalFactory.create(size, modalCtrl, modalTemplateUrl, resolve, onSuccess, onFailure);
      }));

      it('creates a modal instance with the correct parameters', function() {
        expect(uibModal.open).toHaveBeenCalledWith(expectedUibModalOpenParams);
      });

      it('resolves the modal instance promise passing the expected functions', function() {
        expect(modalInstance.result.then).toHaveBeenCalledWith(onSuccess, onFailure);
      });

      it('uses the onSuccess function if promise success', function() {
        // check documentation:
        // - https://docs.angularjs.org/guide/unit-testing#testing-promises
        // - https://docs.angularjs.org/api/ng/service/$q#testing

        // simulate resolving the promise
        deferred.resolve();

        // propagate promise resolution to 'then' functions using $apply().
        $scope.$apply();

        expect(success).toBe(true);
      });

      it('uses the onFailure function if promise does not success', function() {
        // check comments on the above case
        deferred.reject();
        $scope.$apply();
        expect(success).toBe(false);
      });
    });
  });
});
