// disable max-nested-callbacks linter for this test
// it is good to have a good separation of contexts inside tests

/* eslint-disable max-nested-callbacks */

'use strict';

define(['paw',
'angular-mocks',
'api-responses',
'services/navDataService',
'controllers/BodyCtrl'],
function() {
  describe('Body Controller', function() {
    beforeEach(module('paw'));

    var $controller, $rootScope, controller, navDataServiceService, expectedUser;

    beforeEach(inject(function(_$controller_, _$rootScope_, apiResponses, navDataService) {
      $controller = _$controller_;
      $rootScope = _$rootScope_;
      expectedUser = apiResponses.currentAdmin;
      navDataServiceService = navDataService;
      controller = $controller('BodyCtrl');
    }));

    describe('when checking subscriptions', function() {
      var expecteduser;

      beforeEach(function() {
        $rootScope.$apply(function() {
          controller.user = undefined; // clean any possible data
          navDataServiceService.set('user', expectedUser);
        });
      });

      it('is subscribed to user changes event', function() {
        expect(controller.user).toEqual(expectedUser);
      });
    });
  });
});
