// disable max-nested-callbacks linter for this test
// it is good to have a good separation of contexts inside tests

/* eslint-disable max-nested-callbacks */

'use strict';

define(['paw',
'angular-mocks',
'spec-utils',
'api-responses',
'controllers/HomeCtrl'],
function() {
  describe('Home Ctrl', function() {
    beforeEach(module('paw'));

    var $controller, $rootScope, controller, navDataServiceSpy, specUtilsService,
    apiResponsesService, $location;

    beforeEach(inject(
    function(_$controller_, _$rootScope_, navDataService, specUtils, apiResponses, _$location_) {
      $controller = _$controller_;
      $rootScope = _$rootScope_;
      navDataServiceSpy = navDataService;
      specUtilsService = specUtils;
      apiResponsesService = apiResponses;
      $location = _$location_;
      controller = $controller('HomeCtrl');
    }));

    describe('when checking its tasks', function() {
      // we are implicitly testing that it is subscribed to navDataService 'user'
      describe('when user does not exists', function() {
        beforeEach(function() {
          navDataServiceSpy.set('user', undefined);
          spyOn(navDataServiceSpy, 'get').and.callThrough();
          spyOn($location, 'path');
          // initialize the controller
          $controller('HomeCtrl');
        });

        it('gets the user from the navDataService', function() {
          expect(navDataServiceSpy.get).toHaveBeenCalled();
        });

        it('does not redirect', function() {
          expect($location.path).not.toHaveBeenCalled();
        });
      });

      describe('when user exists', function() {
        var user;
        var thisController;
        beforeEach(function() {
          user = apiResponsesService.currentAdmin;
          navDataServiceSpy.set('user', user);
          spyOn(navDataServiceSpy, 'get').and.callThrough();
          spyOn($location, 'path');
          // initialize the controller
          thisController = $controller('HomeCtrl');
        });

        it('gets the user from the navDataService', function() {
          expect(navDataServiceSpy.get).toHaveBeenCalled();
        });

        it('saves the user', function() {
          expect(thisController.user).toEqual(user);
        });

        it('redirect to its location url', function() {
          expect($location.path).toHaveBeenCalledWith(user.locationUrl);
        });
      });
    });
  });
});
