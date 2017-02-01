// disable max-nested-callbacks linter for this test
// it is good to have a good separation of contexts inside tests

/* eslint-disable max-nested-callbacks */

'use strict';

define(['paw',
'angular-mocks',
'spec-utils',
'api-responses',
'services/Paths',
'controllers/LoginCtrl'],
function() {
  describe('Login Ctrl', function() {
    beforeEach(module('paw'));

    var $controller, $rootScope, $location, $log, controller,
        specUtilsService, apiResponsesService, AuthenticationService;

    beforeEach(inject(
    function(_$controller_, _$rootScope_, _$location_, _$log_, specUtils, apiResponses, Authentication) {
      $controller = _$controller_;
      $rootScope = _$rootScope_;
      $location = _$location_;
      $log = _$log_;
      specUtilsService = specUtils;
      apiResponsesService = apiResponses;
      AuthenticationService = Authentication;
      controller = $controller('LoginCtrl');
    }));

    describe('when logging in', function() {
      var expectedToken, token, user;
      beforeEach(function() {
        user = {'dni': 12345678, 'password': 'pass'};
        expectedToken = apiResponsesService.token;
      });

      describe('successfully', function() {
        beforeEach(function() {
          spyOn($location, 'path');
          spyOn(AuthenticationService, 'setToken');
          specUtilsService.resolvePromise(AuthenticationService, 'login', expectedToken);
          controller.login(user);
          $rootScope.$apply();
        });

        it('calls the Authentication Service login function with the given user', function() {
          expect(AuthenticationService.login).toHaveBeenCalledWith(user);
        });

        it('sets the token on the Authentication Service', function() {
          expect(AuthenticationService.setToken).toHaveBeenCalledWith(expectedToken);
        });

        it('redirects to index', inject(function(Paths) {
          expect($location.path).toHaveBeenCalledWith(Paths.get().index().absolutePath());
        }));
      });

      describe('unsuccessfully', function() {
        var response, msg;
        beforeEach(function() {
          response = apiResponsesService.invalidLoginResponse;
          msg = 'Response status: ' + response.status;
          spyOn($log, 'info');
          spyOn($location, 'path');
          spyOn(AuthenticationService, 'setToken');
          specUtilsService.rejectPromise(AuthenticationService, 'login', response);
          controller.login(user);
          $rootScope.$apply();
        });

        it('calls the Authentication Service login function with the given user', function() {
          expect(AuthenticationService.login).toHaveBeenCalledWith(user);
        });

        it('logs the response status', function() {
          expect($log.info).toHaveBeenCalledWith(msg);
        });
      });
    });
  });
});
