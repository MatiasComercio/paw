'use strict';

define(['paw', 'angular-mocks', 'directives/navbar', 'navbar-template'], function() {
  beforeEach(module('paw'));

  describe('NavbarController', function() {
    var $controller;

    beforeEach(inject(function(_$controller_) {
      $controller = _$controller_;
    }));

    describe('controller.user', function() {
      var $scope, controller;
      // for now, hardcoded data (when API integration is ready ==> replace this)
      var user = {
        'fullName': 'Matías Nicolás Comercio Vázquez asdasdasdasdasdasdasdasdadasdasdas',
        'profileUrl': '/users/1'
      };

      beforeEach(function() {
        $scope = {};
        controller = $controller('NavbarController', {$scope: $scope});
      });

      it('correctly sets the user', function() {
        expect(controller.user).toEqual(user);
      });
    });
  });

  describe('NavbarDirective', function() {
    beforeEach(module('directive-templates'));

    var $compile, $rootScope, element, scope;

    beforeEach(inject(function(_$compile_, _$rootScope_) {
      $compile = _$compile_;
      $rootScope = _$rootScope_;
    }));

    beforeEach(function() {
      element = angular.element('<xnavbar></xnavbar>');
      $compile(element)($rootScope);
      $rootScope.$digest();
    });

    it('contains the navbar brand', function() {
      expect(element.find('.navbar-brand')[0]).toBeDefined();
    });

    it('is defined', function() {
      expect(element.find('.top-nav')[0]).toBeDefined();
    });
  });
});
