// disable max-nested-callbacks linter for this test
// it is good to have a good separation of contexts inside tests

/* eslint-disable max-nested-callbacks */

// Many thanks to:
// http://stackoverflow.com/questions/21214868/mocking-modal-in-angularjs-unit-tests
// http://stackoverflow.com/questions/24373220/testing-angularui-bootstrap-modal-instance-controller
// Some code of this tests was adapted from those answers

'use strict';

define(['paw',
'angular-mocks'],
function() {
  describe('Inscriptions Controller', function() {
    beforeEach(module('paw'));

    var fakeModalInstance = {
      result: {
        then: function(confirmCallback, cancelCallback) {
          // Store the callbacks for later when the user clicks on the
          // OK or Cancel button of the dialog
          this.confirmCallBack = confirmCallback;
          this.cancelCallback = cancelCallback;
        }
      },
      close: function(url) {
        // The user clicked OK on the modal dialog,
        // call the stored confirm callback with the specified parameters
        this.result.confirmCallBack(url);
      },
      dismiss: function(msg) {
        // The user clicked cancel on the modal dialog, call the stored cancel callback
        this.result.cancelCallback(msg);
      }
    };

    var size = 'size';
    var url = 'testing_url';
    var title = 'title';

    var $controller, $log, controller;

    beforeEach(inject(function(_$controller_, _$log_) {
      $controller = _$controller_;
      $log = _$log_;
      controller = $controller('InscriptionsController');
    }));

    // test that the open method passes the correct parameters
    describe('when calling the open method', function() {
      var modalFactorySpy;

      beforeEach(inject(function(modalFactory) {
        modalFactorySpy = modalFactory;
        spyOn(modalFactorySpy, 'create').and.callThrough();
      }));

      it('creates a modal using the modal factory create method', function() {
        controller.open(size, url);
        expect(modalFactorySpy.create).toHaveBeenCalledTimes(1);
      });

      describe('and it is expected that', function() {
        var modalTemplateUrl = '/views/modals/inscriptions.html';
        var args;

        beforeEach(function() {
          controller.open(size, url);
          args = modalFactorySpy.create.calls.argsFor(0);
        });

        it('uses the specified size', function() {
          expect(args[0]).toEqual(size);
        });

        it('uses the inscriptions modal view', function() {
          expect(args[2]).toEqual(modalTemplateUrl);
        });
      });

      describe('and the resolve object that has been passed', function() {
        describe('when inscriptions are disable', function() {
          var inscriptionsEnabledTitle = 'i18nEnableInscriptionsModalTitle';

          var args, resolve;

          beforeEach(inject(function(navDataService) {
            navDataService.set('inscriptionsEnabled', false);
            controller.open(size, url);
            args = modalFactorySpy.create.calls.argsFor(0);
            resolve = args[3];
          }));

          it('has correctly set the url', function() {
            expect(resolve.url()).toEqual(url);
          });

          it('sets the title to inscriptions enable text untranslated', function() {
            expect(resolve.title()).toEqual(inscriptionsEnabledTitle);
          });
        });

        describe('when inscriptions are enabled', function() {
          var inscriptionsDisabledTitle = 'i18nDisableInscriptionsModalTitle';

          var args, resolve;

          beforeEach(inject(function(navDataService) {
            navDataService.set('inscriptionsEnabled', true);
            controller.open(size, url);
            args = modalFactorySpy.create.calls.argsFor(0);
            resolve = args[3];
          }));

          it('has correctly set the url', function() {
            expect(resolve.url()).toEqual(url);
          });

          it('sets the title to inscriptions enable text untranslated', function() {
            expect(resolve.title()).toEqual(inscriptionsDisabledTitle);
          });
        });
      });
    });
    // \ test that the open method passes the correct parameters

    // test that the modal instance controller behaves as expected
    describe('when executing the modal instance controller', function() {
      var $uibModal, modalInstanceController;
      beforeEach(inject(function(_$uibModal_) {
        $uibModal = _$uibModal_;

        // make the $uibModal being called somewhere (by the modal factory)
        // return a fake modal instance
        // this is needed so as to be able to check how the modal instance controller behaves
        spyOn($uibModal, 'open').and.returnValue(fakeModalInstance);

        // this will be used to check current sucess and failure callback actions when
        // resolving the fake modal instance
        spyOn($log, 'info').and.callThrough();

        // create the modal instance itself
        // this will be using the fakeModalInstance as it has been spied
        controller.open(size, url);

        // get the modal instance controller passing the needed arguments
        // (among them, the fakeModalInstance whose close and dismiss methods should
        // be called somewhen)
        modalInstanceController =
          $controller('InscriptionsControllerModalInstance', {
            $uibModalInstance: fakeModalInstance,
            url: url,
            title: title
          });
      }));

      describe('when the ok method is called', function() {
        var navDataServiceSpy;
        var intialInscriptionsEnabled = false;
        var expectedInscriptionsEnabled = true;

        beforeEach(inject(function(navDataService) {
          navDataServiceSpy = navDataService;
          navDataService.set('inscriptionsEnabled', intialInscriptionsEnabled);
          spyOn(navDataServiceSpy, 'set').and.callThrough();
          spyOn(fakeModalInstance, 'close').and.callThrough();
          modalInstanceController.ok();
        }));

        it('calls the close method of the fake modal instance with the correct parameters',
        function() {
          expect(fakeModalInstance.close).toHaveBeenCalledWith(url);
        });

        it('notifies the navDataService about the inscriptionsEnabled update', function() {
          expect(navDataServiceSpy.set).toHaveBeenCalledWith('inscriptionsEnabled',
            expectedInscriptionsEnabled);
        });

        it('logs the expected message', function() {
          expect($log.info).toHaveBeenCalledWith('POST ' + url +
            ' {enable: ' + expectedInscriptionsEnabled + '}');
        });
      });

      describe('when the cancel event is triggered', function() {
        var msg = jasmine.stringMatching(/Inscriptions modal dismissed at: .*/);

        beforeEach(function() {
          spyOn(fakeModalInstance, 'dismiss').and.callThrough();
          modalInstanceController.cancel();
        });

        it('calls the dismiss method of the fake modal instance with the correct parameters',
        function() {
          expect(fakeModalInstance.dismiss.calls.argsFor(0)).toEqual(msg);
        });

        it('logs the expected message', function() {
          expect($log.info).toHaveBeenCalledWith(msg);
        });
      });
    });
    // \ test that the modal instance controller behaves as expected
  });
});