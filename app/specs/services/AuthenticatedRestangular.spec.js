// disable max-nested-callbacks linter for this test
// it is good to have a good separation of contexts inside tests

/* eslint-disable max-nested-callbacks */

'use strict';

define(['paw', 'spec-utils', 'api-responses', 'services/AuthenticatedRestangular'], function() {
  describe('Authenticated Restangular Service', function() {
    beforeEach(module('paw'));

    var $rootScope;
    var $httpBackend;
    var apiResponsesService;
    var AuthenticationService;
    var AuthenticatedRestangularService;

    beforeEach(inject(function(_$rootScope_, _$httpBackend_,
      apiResponses, Authentication, AuthenticatedRestangular) {

      $rootScope = _$rootScope_;
      $httpBackend = _$httpBackend_;
      apiResponsesService = apiResponses;
      AuthenticationService = Authentication;
      AuthenticatedRestangularService = AuthenticatedRestangular;
    }));

    describe('when doing a request', function() {
      afterEach(function () {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
      });

      it("contains the token's header with the correct value", function () {
        $httpBackend.expectGET('/api/v1/', apiResponsesService.header).respond('data');
        AuthenticatedRestangularService.all('/').customGET().then(function() {});
        $httpBackend.flush();
        $rootScope.$apply();
      });
    });
  });
});
