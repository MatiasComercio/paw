'use strict';

define(['paw', 'directives/navbar'], function(paw) {
  paw.controller('BodyController', ['$scope', function($scope) {
    this.bodyControllerText = 'bodyControllerText';
  }]);
});
