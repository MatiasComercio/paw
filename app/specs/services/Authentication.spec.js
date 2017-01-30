// disable max-nested-callbacks linter for this test
// it is good to have a good separation of contexts inside tests

/* eslint-disable max-nested-callbacks */

'use strict';

define(['paw', 'spec-utils', 'api-responses', 'services/Authentication'],
function() {
  describe('Authentication Service', function() {
    beforeEach(module('paw'));

    var $rootScope;
    var $httpBackend;
    var $cookies;
    var apiResponsesService;
    var AuthenticationService;
    var expectedToken;
    var expectedTokenKey = 'ur';

    beforeEach(inject(function(_$rootScope_, _$httpBackend_, _$cookies_,
      apiResponses, Authentication) {
      $rootScope = _$rootScope_;
      $httpBackend = _$httpBackend_;
      $cookies = _$cookies_;
      apiResponsesService = apiResponses;
      AuthenticationService = Authentication;
      expectedToken = apiResponsesService.token;
    }));

    describe('when logging in a user', function() {
      var token;
      beforeEach(function() {
        var user = {'dni': 12345678, 'password': 'pass'};
        $httpBackend.expectPOST('api/v1/login').respond(200, '', {'x-auth-token': expectedToken});
        AuthenticationService.login(user).then(function(apiToken) {
          token = apiToken;
        });
        $httpBackend.flush();
        $rootScope.$apply();
      });

      afterEach(function () {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
      });

      it('returns the token after request has finished', function () {
        expect(token).toEqual(expectedToken);
      });
    });

    describe('when setting the token', function() {
      beforeEach(function() {
        spyOn($cookies, 'put').and.callThrough();
        AuthenticationService.setToken(expectedToken);
      });

      it('saves it on the $cookies services with the expected key', function () {
        expect($cookies.put).toHaveBeenCalledWith(expectedTokenKey, expectedToken);
      });
    });

    describe('when getting the token', function() {
      var token;

      beforeEach(function() {
        $cookies.put(expectedTokenKey, 'cleaning-value');
        $cookies.put(expectedTokenKey, expectedToken);
        spyOn($cookies, 'get').and.callThrough();
        token = AuthenticationService.getToken();
      });

      it('calls the $cookies services using the correct key', function () {
        expect($cookies.get).toHaveBeenCalledWith(expectedTokenKey);
      });

      it('returns the token', function () {
        expect(token).toEqual(expectedToken);
      });
    });

    describe('when getting the token header', function() {
      var header, expectedHeader;
      beforeEach(function() {
        expectedHeader = {'x-auth-token': expectedToken};
        $cookies.put(expectedTokenKey, expectedToken);
        header = AuthenticationService.getHeader();
      });

      it('retrieves the correct header with the correct value', function () {
        expect(header).toEqual(expectedHeader);
      });
    });
  });
});
