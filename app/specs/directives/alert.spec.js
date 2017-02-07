'use strict';

define(['paw', 'angular-mocks', 'services/navDataService', 'services/windowSize',
'directives/alert', 'alert-template'], function() {
  describe('Alert Directive', function() {
    beforeEach(module('paw'));
    beforeEach(module('directive-templates'));

    var element = angular.element("<xalert msg='Hello' type='success'></xalert>");
    var $compile, directiveElement, $rootScope, scope, alert, controller;

    beforeEach(inject(function(_$compile_, _$rootScope_, navDataService, windowSize) {
      $compile = _$compile_;
      $rootScope = _$rootScope_.$new();

      scope = $rootScope.$new();
      alert = compileDirective(scope);

      controller = alert.controller('xalert');
    }));

    function compileDirective(thisScope) {
      var compiledElement = $compile(element)(thisScope);
      thisScope.$digest();
      return compiledElement.find('.alert');
    };

    it('expects to correctly bind the msg', function() {
      expect(controller.msg).toEqual('Hello');
    });

    it('expects to correctly bind the type', function() {
      expect(controller.type).toEqual('success');
    });

    it('expects to set show on true by default', function() {
      expect(alert.scope().show).toBe(true);
    });
  });
});
