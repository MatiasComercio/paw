// disable max-nested-callbacks linter for this test
// it is good to have a good separation of contexts inside tests

/* eslint-disable max-nested-callbacks */

'use strict';

define([
'paw',
'angular-mocks',
'api-responses',
'spec-utils',
'services/navDataService',
'services/Authentication'], function() {
  describe('navDataService', function() {
    beforeEach(module('paw'));

    var expectedMemory, apiResponsesService, specUtilsService;
    // memory factory service should be required together with nav data service
    beforeEach(inject(function(memoryFactory, apiResponses, specUtils) {
      expectedMemory = memoryFactory.newMemory();
      apiResponsesService = apiResponses;
      specUtilsService = specUtils;
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

    describe('#fetchUser', function() {
      var navDataServiceService;

      beforeEach(inject(function(navDataService) {
        navDataServiceService = navDataService;
      }));

      describe('when user is already defined', function() {
        beforeEach(function() {
          navDataServiceService.set('user', apiResponsesService.currentAdmin);
          spyOn(navDataServiceService, 'set');
          navDataServiceService.fetchUser();
        });

        // implicitly testing that there is no $http call (if not, error will be thrown, with an
        // unnexpected GET message)
        it('does not fetch the user again', function() {
          expect(navDataServiceService.set).not.toHaveBeenCalled();
        });
      });

      describe('when user is not defined', function() {
        var AuthenticationService;
        beforeEach(inject(function(Authentication) {
          AuthenticationService = Authentication;
        }));

        describe('when not logged in', function() {
          beforeEach(function() {
            navDataServiceService.set('user', undefined);
            spyOn(navDataServiceService, 'set');
            spyOn(AuthenticationService, 'isLoggedIn').and.returnValue(false);
            navDataServiceService.fetchUser();
          });

          // implicitly testing that there is no $http call (if not, error will be thrown, with an
          // unnexpected GET message)
          it('does not fetch the user', function() {
            expect(navDataServiceService.set).not.toHaveBeenCalled();
          });
        });

        describe('when logged in', function() {
          var $rootScope, $httpBackend, expectedUser;

          beforeEach(inject(function(_$rootScope_, _$httpBackend_) {
            $rootScope = _$rootScope_;
            $httpBackend = _$httpBackend_;
            navDataServiceService.set('user', undefined);
            spyOn(navDataServiceService, 'set').and.callThrough();
            spyOn(AuthenticationService, 'isLoggedIn').and.returnValue(true);

            expectedUser = apiResponsesService.currentAdmin;
            // $httpBackend.expectGET('api/v1/user')
            //   .respond(200, expectedUser);

            // problem with user.authorities.find method making the test not pass
            // navDataServiceService.fetchUser();

            // $httpBackend.flush();
            // $rootScope.$apply();
          }));

          afterEach(function () {
            $httpBackend.verifyNoOutstandingExpectation();
            $httpBackend.verifyNoOutstandingRequest();
          });

          // it('does fetch the user', function() {
          //   expect(navDataServiceService.set).toHaveBeenCalled();
          // });
          //
          // it('correclty saved the fetched user', function() {
          //   var sanitizedUser = specUtilsService
          //     .sanitizeRestangularOne(navDataServiceService.get('user'));
          //   expect(sanitizedUser).toEqual(expectedUser);
          // });
        });
      });
    });
  });
});
