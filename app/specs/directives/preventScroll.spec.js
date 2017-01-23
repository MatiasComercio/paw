'use strict';

define(['paw', 'angular-mocks', 'directives/preventScroll'], function() {
  describe('Prevent Scroll Directive', function() {
    beforeEach(module('paw'));
    // template adapted from http://www.w3schools.com/jsref/tryit.asp?filename=tryjsref_element_scrollheight
    var testTemplate =
    "<div style='height: 250px; width: 250px; overflow: auto;'><div prevent-scroll style='height: 800px; width: 2000px;'>Some content..</div></div>";

    var $compile, $rootScope, scope, directiveElement;
    var element = angular.element(testTemplate);

    beforeEach(inject(function(_$compile_, _$rootScope_) {
      $compile = _$compile_;
      $rootScope = _$rootScope_;
      scope = $rootScope.$new();
      directiveElement = compileDirective(scope);
    }));

    function compileDirective(thisScope) {
      var compiledElement = $compile(element)(thisScope);
      thisScope.$digest();
      return compiledElement.find('[prevent-scroll]');
    };

    describe('when scrolling down and the end has been reach', function() {
      var scrollDownEvent;
      beforeEach(function() {
        // Create a new jQuery.Event object with specified event properties
        scrollDownEvent = $.Event('mousewheel', {deltaY: -1}); // eslint-disable-line new-cap
        directiveElement.triggerHandler(scrollDownEvent);
      });

      it('should prevent default', function() {
        expect(scrollDownEvent.isDefaultPrevented()).toBe(true);
      });
    });

    describe('when scrolling up and the beginning has been reach', function() {
      var scrollUpEvent;
      beforeEach(function() {
        // Create a new jQuery.Event object with specified event properties
        scrollUpEvent = $.Event('mousewheel', {deltaY: 1}); // eslint-disable-line new-cap
        directiveElement.triggerHandler(scrollUpEvent);
      });

      it('should prevent default', function() {
        expect(scrollUpEvent.isDefaultPrevented()).toBe(true);
      });
    });

    describe('when scrolling but any end has been reach', function() {
      var scrollUpEvent;
      beforeEach(function() {
        // Create a new jQuery.Event object with specified event properties
        scrollUpEvent = $.Event('mousewheel'); // eslint-disable-line new-cap
        directiveElement.triggerHandler(scrollUpEvent);
      });

      it('should prevent default', function() {
        expect(scrollUpEvent.isDefaultPrevented()).toBe(false);
      });
    });
  });
});
