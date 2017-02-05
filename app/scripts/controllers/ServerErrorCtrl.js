'use strict';

define(['paw', 'services/Paths'],
function(paw) {
  paw.controller('ServerErrorCtrl',
  ['Paths',
  function(Paths) {
    this.indexPath = Paths.get().index().path;
  }]);
});
