'use strict';

define(['paw', 'services/memoryFactory'], function(paw) {
  paw.service('navDataService', ['memoryFactory', function(memoryFactory) {
    return memoryFactory.newMemory();
  }]);
});
