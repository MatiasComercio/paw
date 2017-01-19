'use strict';

define(['paw'], function(paw) {
  paw.controller('NavbarController', ['$scope', function NavbarController($scope) {
    this.user = {
      'fullName': 'Matías Nicolás Comercio Vázquez asdasdasdasdasdasdasdasdadasdasdas',
      'profileUrl': '/users/1'
    };
  }])
  .directive('xnavbar', function() {
    return {
      restrict: 'E',
      templateUrl: '/views/navbar.html',
      controller: 'NavbarController',
      controllerAs: 'controller'
    };
  });
});
