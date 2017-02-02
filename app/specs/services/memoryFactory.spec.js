'use strict';

define(['paw', 'angular-mocks'], function() {
  describe('Memory Factory', function() {
    beforeEach(module('paw'));

    var memory;
    var validKey = 'key';
    var invalidKey = 'invalid-key';
    var expectedValue = 'value';

    var callbackValue;
    var callback = function() {
      callbackValue = memory.get(validKey);
    };

    // register an expected callback
    beforeEach(inject(function(memoryFactory) {
      callbackValue = undefined;
      memory = memoryFactory.newMemory();
      memory.registerObserverCallback(validKey, callback);
    }));

    describe('when saving a value with the valid key', function() {
      beforeEach(inject(function(memoryFactory) {
        memory.set(validKey, expectedValue);
      }));

      it('is possible to retrieve the value from the callback', function() {
        expect(callbackValue).toEqual(expectedValue);
      });
    });

    describe('when saving a value with a different key', function() {
      beforeEach(inject(function(memoryFactory) {
        memory.set(invalidKey, expectedValue);
      }));

      it('callback function is never called', function() {
        expect(callbackValue).toBeUndefined();
      });
    });


    describe('when getting a value with the same key', function() {
      beforeEach(inject(function(memoryFactory) {
        memory.set(validKey, expectedValue);
      }));

      it('returns the expected value', function() {
        expect(memory.get(validKey)).toEqual(expectedValue);
      });
    });

    describe('when getting a value with a different key', function() {
      beforeEach(inject(function(memoryFactory) {
        memory.set(invalidKey, expectedValue);
      }));

      it('returns undefined', function() {
        expect(memory.get(validKey)).toBeUndefined();
      });
    });

    describe('when removing a value with a valid key', function() {
      beforeEach(inject(function(memoryFactory) {
        memory.set(validKey, expectedValue);
        memory.remove(validKey);
      }));

      it('does not remove the element', function() {
        expect(memory.get(validKey)).toBeUndefined();
      });
    });

    describe('when removing a value with a different key', function() {
      beforeEach(inject(function(memoryFactory) {
        memory.set(validKey, expectedValue);
        memory.remove(invalidKey);
      }));

      it('does not remove the element', function() {
        expect(memory.get(validKey)).toEqual(expectedValue);
      });
    });
  });
});
