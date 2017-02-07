'use strict';

define(['paw'], function(paw) {
  paw.service('flashMessages', [
  function() {
    var memory = this;

    memory.getErrors = function() {
      return memory.errors;
    };

    memory.setError = function(error) {
      memory.errors.push({msg: error, show: true});
    };

    memory.getSuccesses = function() {
      return memory.successes;
    };

    memory.setSuccess = function(success) {
      memory.successes.push({msg: success, show: true});
    };

    memory.clear = function() {
      memory.errors = [];
      memory.successes = [];
    };

    // initialize errors and successes arrays
    memory.clear();
  }]);
});
