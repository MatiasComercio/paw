'use strict';

define(['paw', 'services/modalFactory', 'services/navDataService'], function(paw) {
  paw.controller('InscriptionsController',
    ['modalFactory', '$log', 'navDataService',
    function (modalFactory, $log, navDataService) {
      var modalTemplateUrl = 'views/modals/inscriptions.html';
      var onSuccess = function(url) {
        navDataService.set('inscriptionsEnabled', !navDataService.get('inscriptionsEnabled'));
        $log.info('POST ' + url + ' {enable: ' + navDataService.get('inscriptionsEnabled') + '}');
      };

      var onFailure = function(msg) {
        $log.info(msg);
      };

      this.open = function (size, url) {
        var resolve = getResolveBasedOnCurrentValue(navDataService.get('inscriptionsEnabled'), url);
        modalFactory.create(size, 'InscriptionsControllerModalInstance', modalTemplateUrl, resolve, onSuccess, onFailure);
      };
    }]);

    paw.controller('InscriptionsControllerModalInstance', function ($uibModalInstance, title, url) {
      var _this = this;
      _this.title = title;

      _this.ok = function () {
        $uibModalInstance.close(url);
      };

      _this.cancel = function () {
        $uibModalInstance.dismiss('Inscriptions modal dismissed at: ' + new Date());
      };
    });

    function getResolveBasedOnCurrentValue(inscriptionsEnabled, url) {
      return {
        // title that will be displayed in the modal
        title: function () {
          if (inscriptionsEnabled) {
            return 'i18nDisableInscriptionsModalTitle';
          }
          return 'i18nEnableInscriptionsModalTitle';
        },
        url: function() {
          return url;
        }
      };
    };
  }
);
