'use strict';

define(['paw', 'services/Paths'],
function(paw) {
  paw.controller('NotFoundCtrl',
  ['Paths',
  function(Paths) {
    this.indexPath = Paths.get().index().path;
  }]);
});
