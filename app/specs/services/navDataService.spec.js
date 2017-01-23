'use strict';

define(['paw', 'angular-mocks', 'services/navDataService'], function() {
  describe('navDataService', function() {
    beforeEach(module('paw'));

    var expectedMemory;
    // memory factory service should be required together with nav data service
    beforeEach(inject(function(memoryFactory) {
      expectedMemory = memoryFactory.newMemory();
      spyOn(memoryFactory, 'newMemory').and.returnValue(expectedMemory);
    }));

    it('gets a new memory from the memory factory',
      inject(function(navDataService, memoryFactory) {
        // navDataService injection is what causes the call to new memory
        expect(memoryFactory.newMemory).toHaveBeenCalled();
      }
    ));

    it('is indeed the memory created with memory factory service',
      inject(function(navDataService) {
        expect(navDataService).toEqual(expectedMemory);
      }
    ));
  });
});
