'use strict';

define(['paw', 'services/Paths', 'services/navDataService'],
function(paw) {
  paw.controller('ServerErrorCtrl',
  ['Paths', 'navDataService',
  function(Paths, navDataService) {
    navDataService.remove('user');
    this.indexPath = Paths.get().index().path;
  }]);
});
