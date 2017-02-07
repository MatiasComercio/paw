'use strict';

define(['paw', 'services/modalFactory', 'services/navDataService', 'services/Admins'], function(paw) {
  paw.controller('InscriptionsController',
    ['modalFactory', '$log', 'navDataService', 'Admins',
    function (modalFactory, $log, navDataService, Admins) {
      var modalTemplateUrl = 'views/modals/inscriptions.html';
      var onSuccess = function() {
        navDataService.set('inscriptionsEnabled', !navDataService.get('inscriptionsEnabled'));
        Admins.inscriptions({enabled: navDataService.get('inscriptionsEnabled')});
      };

      var onFailure = function(msg) {
        $log.info(msg);
      };

      this.open = function (size) {
        var resolve = getResolveBasedOnCurrentValue(navDataService.get('inscriptionsEnabled'));
        modalFactory.create(size, 'InscriptionsControllerModalInstance', modalTemplateUrl, resolve, onSuccess, onFailure);
      };
    }]);

    paw.controller('InscriptionsControllerModalInstance', function ($uibModalInstance, title) {
      var _this = this;
      _this.title = title;

      _this.ok = function () {
        $uibModalInstance.close();
      };

      _this.cancel = function () {
        $uibModalInstance.dismiss('Inscriptions modal dismissed at: ' + new Date());
      };
    });

    function getResolveBasedOnCurrentValue(inscriptionsEnabled) {
      return {
        // title that will be displayed in the modal
        title: function () {
          if (inscriptionsEnabled) {
            return 'i18nDisableInscriptionsModalTitle';
          }
          return 'i18nEnableInscriptionsModalTitle';
        }
      };
    };
  }
);
