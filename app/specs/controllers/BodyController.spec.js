// disable max-nested-callbacks linter for this test
// it is good to have a good separation of contexts inside tests

/* eslint-disable max-nested-callbacks */

'use strict';

define(['paw',
'angular-mocks',
'controllers/BodyController'],
function() {
  describe('Body Controller', function() {
    beforeEach(module('paw'));

    var $controller, controller;

    beforeEach(inject(function(_$controller_) {
      $controller = _$controller_;
      controller = $controller('BodyController');
    }));

    it('exists', function() {
      expect(controller).toBeDefined();
    });
  });
});
