// disable max-nested-callbacks linter for this test
// it is good to have a good separation of contexts inside tests

/* eslint-disable max-nested-callbacks */

'use strict';

define(['paw', 'spec-utils', 'api-responses',
'services/Admins'], function() {
  describe('Admins Service', function() {
    beforeEach(module('paw'));

    var $rootScope;
    var $httpBackend;
    var apiResponsesService;
    var AdminsService;
    var PathsService;
    var navDataServiceService;

    beforeEach(inject(function(_$rootScope_, _$httpBackend_,
      apiResponses, Admins, Paths, navDataService) {

      $rootScope = _$rootScope_;
      $httpBackend = _$httpBackend_;
      apiResponsesService = apiResponses;
      AdminsService = Admins;
      PathsService = Paths;
      navDataServiceService = navDataService;
    }));

    describe('#setOnSubSidebar', function() {
      var admin;
      var expectedAdmin;
      var expectedSubSidebar;
      var expectedActions = true;

      beforeEach(function() {
        admin = apiResponsesService.admin;
        expectedAdmin = admin;
        expectedAdmin.actions = expectedActions;
        expectedSubSidebar = {
          admin: expectedAdmin
        };
        spyOn(PathsService, 'getAdminActions').and.returnValue(expectedActions);
      });

      describe('when user is not defined', function() {
        beforeEach(function() {
          navDataServiceService.set('user', undefined);
          spyOn(navDataServiceService, 'set');
          AdminsService.setOnSubSidebar(admin);
        });

        it('is expected not to set anything on the subSidebar', function() {
          expect(navDataServiceService.set).not.toHaveBeenCalled();
        });
      });

      describe('when user is defined', function() {
        beforeEach(function() {
          navDataServiceService.set('user', apiResponsesService.admin);
          spyOn(navDataServiceService, 'set');
          AdminsService.setOnSubSidebar(admin);
        });

        it('correctly sets the subSidebar with the current user', function() {
          expect(navDataServiceService.set).toHaveBeenCalledWith('subSidebar', expectedSubSidebar);
        });
      });
    });

    describe('when doing an API call', function() {
      afterEach(function () {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
      });

      describe('when not doing getList with admins resource', function() {
        it('returns the default data', function() {
          $httpBackend.expectGET('api/v1/students', apiResponsesService.header).respond([]);
          AdminsService.all('students').getList();
          $httpBackend.flush();
          $rootScope.$apply();
        });
      });

      describe('GET #getList', function() {
        var expectedAdmins;

        beforeEach(function() {
          expectedAdmins = apiResponsesService.admins;
        });

        it('is expected to fetch the admins list from the API', function() {
          $httpBackend.expectGET('api/v1/admins', apiResponsesService.header).respond(expectedAdmins);
          AdminsService.getList().then(function() {});
          $httpBackend.flush();
          $rootScope.$apply();
        });
      });

      describe('GET #get', function() {
        var expectedAdmin;

        beforeEach(function() {
          expectedAdmin = apiResponsesService.admin;
        });

        it('is expected to fetch the admin with the given dni from the API', function() {
          $httpBackend.expectGET('api/v1/admins/123', apiResponsesService.header).respond(expectedAdmin);
          AdminsService.get(123).then(function() {});
          $httpBackend.flush();
          $rootScope.$apply();
        });
      });

      describe('POST #inscriptions', function() {
        it('is expected to toggle enable/disable inscriptions on API', function() {
          var body = {enabled: true};
          $httpBackend.expectPOST('api/v1/admins/inscriptions', body, apiResponsesService.header).respond(204, '');
          AdminsService.inscriptions(body).then(function() {});
          $httpBackend.flush();
          $rootScope.$apply();
        });
      });

      describe('POST #new', function() {
        it('is expected to create a new admin on the API', function() {
          var body = apiResponsesService.admin;
          $httpBackend.expectPOST('api/v1/admins', body, apiResponsesService.header).respond(201, '');
          AdminsService.new(body).then(function() {});
          $httpBackend.flush();
          $rootScope.$apply();
        });
      });

      describe('POST #update', function() {
        var decoratedAdmin;

        beforeEach(function() {
          decoratedAdmin = jasmine.createSpyObj('decoratedAdmin', ['customPOST']);
          AdminsService.update(decoratedAdmin);
        });

        it('is expected to update the given admin on the API', function() {
          expect(decoratedAdmin.customPOST).toHaveBeenCalledWith(decoratedAdmin);
        });
      });

      describe('DELETE #remove', function() {
        var decoratedAdmin;

        beforeEach(function() {
          decoratedAdmin = jasmine.createSpyObj('decoratedAdmin', ['remove']);
          AdminsService.remove(decoratedAdmin);
        });

        it('is expected to delete the given admin on the API', function() {
          expect(decoratedAdmin.remove).toHaveBeenCalled();
        });
      });
    });
  });
});
