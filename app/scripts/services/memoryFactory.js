'use strict';

// thanks for the advise!
// http://stackoverflow.com/questions/12576798/angularjs-how-to-watch-service-variables
//
// This service offers a function for creating a new
// generic purpose memory manager
define(['paw'], function(paw) {
  paw.service('memoryFactory', function() {
    this.newMemory = function() {
      return {
        // Object mapping key-value
        memory: {},
        // Observers per key
        observers: {},

        // register an observer
        registerObserverCallback: function(key, callback) {
          this.observers[key] = this.observers[key] || [];
          this.observers[key].push(callback);
        },

        // call this when you know 'foo' has been changed
        notifyObservers: function(key) {
          angular.forEach(this.observers[key], function(callback) {
            callback();
          });
        },

        set: function(key, value) {
          this.memory[key] = value;
          this.notifyObservers(key);
        },

        get: function(key) {
          return this.memory[key];
        }
      };
    };
  });
});
