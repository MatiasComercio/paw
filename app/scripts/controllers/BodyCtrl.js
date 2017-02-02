'use strict';

define(
  ['paw',
  'directives/navbar',
  'directives/sidebar',
  'services/navDataService'],
  function(paw) {
    paw.controller('BodyCtrl', ['navDataService', function(navDataService) {
      var _this = this;
      var getUser = function() {
        _this.user = navDataService.get('user');
      };
      navDataService.registerObserverCallback('user', getUser);
      getUser();
    }]);
  }
);
