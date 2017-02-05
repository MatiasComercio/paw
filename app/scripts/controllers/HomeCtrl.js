'use strict';

define(['paw',
'services/navDataService'],
function(paw) {
  paw.controller('HomeCtrl',
    ['navDataService', '$location',
    function(navDataService, $location) {
      var _this = this;

      var getUserCallback = function() {
        _this.user = navDataService.get('user');
        if (_this.user) {
          $location.path(_this.user.locationUrl);
        }
      };
      navDataService.registerObserverCallback('user', getUserCallback);
      getUserCallback();
    }]
  );
});
