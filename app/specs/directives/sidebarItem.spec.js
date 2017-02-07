// disable max-nested-callbacks linter for this test
// it is good to have a good separation of contexts inside tests

/* eslint-disable max-nested-callbacks */

'use strict';

define(['paw',
  'angular-mocks',
  'directives/sidebarItem', 'sidebar-item-template'],
  function() {
  describe('Sidebar Item Directive', function() {
    beforeEach(module('paw'));
    beforeEach(module('directive-templates'));

    var $compile, $rootScope, scope, sidebarItem, controller;
    var element = angular.element('<xsidebar-item></xsidebar-item>');

    beforeEach(inject(function(_$compile_, _$rootScope_) {
      $compile = _$compile_;
      $rootScope = _$rootScope_;
      scope = $rootScope.$new();
      sidebarItem = compileDirective(scope);
      controller = sidebarItem.controller('xsidebarItem');
    }));

    function compileDirective(thisScope) {
      if (sidebarItem) {
        return sidebarItem;
      }
      var compiledElement = $compile(element)(thisScope);
      thisScope.$digest();
      return compiledElement.find('.sidebar-item');
    };

    describe('', function() {
      it('has an ng-transclude directive that is only showed if the sidebar item is opened', function () {
        expect(sidebarItem.find("div[ng-transclude][ng-show='opened']").length).toBe(1);
      });

      it('has a link with the ng-click triggering the select() event', function() {
        expect(sidebarItem.find("a[ng-click='select()']").length).toBe(1);
      });
    });

    describe('when triggering a click event on it', function() {
      describe('when item is opened', function() {
        beforeEach(function() {
          scope.$apply(function() {
            sidebarItem.scope().opened = true;
          });
          sidebarItem.find("a[ng-click='select()']").triggerHandler('click');
        });

        it('closes it', function() {
          expect(sidebarItem.scope().opened).toBe(false);
        });
      });

      describe('when item is closed', function() {
        beforeEach(function() {
          scope.$apply(function() {
            sidebarItem.scope().opened = false;
          });
          sidebarItem.find("a[ng-click='select()']").triggerHandler('click');
        });

        it('opens it', function() {
          expect(sidebarItem.scope().opened).toBe(true);
        });
      });
    });
  });
});
