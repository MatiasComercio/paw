'use strict';

define(
  ['paw',
  'directives/navbar',
  'directives/sidebar',
  'directives/alert',
  'services/navDataService',
  'services/flashMessages'],
  function(paw) {
    paw.controller('BodyCtrl', ['$rootScope', 'navDataService', 'flashMessages', '$location',
    function($rootScope, navDataService, flashMessages) {
      var _this = this;
      var getUser = function() {
        _this.user = navDataService.get('user');
      };
      navDataService.registerObserverCallback('user', getUser);
      getUser();

      this.flashMessages = {};

      $rootScope.$on('$routeChangeStart', function (event, next, current) {
        // flash messages
        _this.flashMessages = {};
        _this.flashMessages.errors = flashMessages.getErrors();
        _this.flashMessages.successes = flashMessages.getSuccesses();
        flashMessages.clear();
      });
    }]);
  }
);
