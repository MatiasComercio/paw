// disable max-nested-callbacks linter for this test
// it is good to have a good separation of contexts inside tests

/* eslint-disable max-nested-callbacks */

'use strict';

define(['paw', 'spec-utils', 'api-responses', 'services/Authentication'],
function() {
  describe('Authentication Configuration', function() {
    beforeEach(module('paw'));

    var paths = {
      login: function() {
        return '/login';
      },
      index: function() {
        return '/';
      },
      unauthorized: function() {
        return '/unauthorized';
      }
    };

    var $rootScope;
    var $httpBackend;
    var $location;
    var specUtilsService;
    var apiResponsesService;
    var AuthenticationService;
    var RestangularService;

    beforeEach(inject(function(_$rootScope_, _$httpBackend_, _$location_, specUtils,
      apiResponses, Authentication, Restangular) {
        $rootScope = _$rootScope_;
        $httpBackend = _$httpBackend_;
        $location = _$location_;
        spyOn($location, 'path');
        specUtilsService = specUtils;
        apiResponsesService = apiResponses;
        AuthenticationService = Authentication;
        RestangularService = Restangular;
      }));

      describe('when not logged in', function() {
        beforeEach(function() {
          spyOn(AuthenticationService, 'isLoggedIn').and.returnValue(false);
          // Important: location changes should enter the $digest cycle
          $rootScope.$apply(function() {
            $location.path(paths.index());
          });
        });

        // only testing one path case
        it('should redirect to login page', function() {
          expect($location.path).toHaveBeenCalledWith(paths.login());
        });
      });

      describe('when logged in', function() {
        beforeEach(function() {
          spyOn(AuthenticationService, 'isLoggedIn').and.returnValue(true);
          // Important: location changes should enter the $digest cycle
          $rootScope.$apply(function() {
            $location.path(paths.index());
          });
        });

        // only testing one path case
        it('should not redirect to login page', function() {
          expect($location.path).not.toHaveBeenCalledWith(paths.login());
        });

        it('should redirect to index page', function() {
          expect($location.path).toHaveBeenCalledWith(paths.index());
        });
      });

      describe('when authentication token has expired', function() {
        // only testing one path case
        beforeEach(function() {
          $httpBackend.expectGET('api/v1/students').respond(401);
          RestangularService.all('/').customGET('students');
          $httpBackend.flush();
          $rootScope.$apply();
        });

        it('should redirect to login page', function() {
          expect($location.path).toHaveBeenCalledWith(paths.login());
        });
      });

      describe('when not having enough priviledges', function() {
        // only testing one path case
        beforeEach(function() {
          $httpBackend.expectGET('api/v1/students').respond(403);
          RestangularService.all('/').customGET('students');
          $httpBackend.flush();
          $rootScope.$apply();
        });

        it('should redirect to login page', function() {
          expect($location.path).toHaveBeenCalledWith(paths.unauthorized());
        });
      });
    });
  });
