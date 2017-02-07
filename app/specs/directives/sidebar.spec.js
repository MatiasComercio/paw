// disable max-nested-callbacks linter for this test
// it is good to have a good separation of contexts inside tests

/* eslint-disable max-nested-callbacks */

'use strict';

define(['paw',
  'angular-mocks',
  'directives/sidebar',
  'api-responses',
  'sidebar-template',
  'sidebar-item-template'], function() {
  describe('Sidebar Directive', function() {
    beforeEach(module('paw'));
    beforeEach(module('directive-templates'));

    var $compile, $rootScope, scope, sidebarContainer, controller;
    var element = angular.element('<xsidebar></xsidebar>');

    var user;

    beforeEach(inject(function(_$compile_, _$rootScope_, apiResponses) {
      $compile = _$compile_;
      $rootScope = _$rootScope_;
      user = apiResponses.currentAdmin;
      scope = $rootScope.$new();
      sidebarContainer = compileDirective(scope);
      controller = sidebarContainer.controller('xsidebar');
    }));

    function compileDirective(thisScope) {
      var compiledElement = $compile(element)(thisScope);
      thisScope.$digest();
      return compiledElement.find('nav.sidebar-container');
    };


    describe('when inspecting the sidebar section', function() {
      it('contains the sidebar', function() {
        expect(sidebarContainer.find('.sidebar').length).toBe(1);
      });

      describe('when inspecting the sidebar admins section', function() {
        var section;

        beforeEach(function() {
          section = sidebarContainer.find("[name='sidebar-admins-section']");
        });

        it('contains this section', function() {
          expect(section.length).toBe(1);
        });

        it('contains an index item', function() {
          expect(section.find("[name='sidebar-admins-section-index']").length).toBe(1);
        });

        it('contains a new admin item', function() {
          expect(section.find("[name='sidebar-admins-section-new']").length).toBe(1);
        });

        it('contains an enable inscriptions item', function() {
          expect(section.find("[name='sidebar-admins-section-enable-inscriptions']").length).toBe(1);
        });

        it('contains an disable inscriptions item', function() {
          expect(section.find("[name='sidebar-admins-section-disable-inscriptions']").length).toBe(1);
        });

        describe('when analyzing the inscriptions logic', function() {
          describe('when inscriptions are disabled', function() {
            // we are implicitly testing that the controller is subscribed to inscriptionsEnabled
            // changes event
            beforeEach(inject(function(navDataService) {
              scope.$apply(function() {
                navDataService.set('user', user);
                navDataService.set('inscriptionsEnabled', false);
              });
            }));

            it('hides the disable inscriptions item', function() {
              expect(section.find("[name='sidebar-admins-section-disable-inscriptions']").hasClass('ng-hide')).toBe(true);
            });

            it('shows the inscriptions enabled item', function() {
              expect(section.find("[name='sidebar-admins-section-enable-inscriptions']").hasClass('ng-hide')).toBe(false);
            });
          });

          describe('when inscriptions are enabled', function() {
            beforeEach(inject(function(navDataService) {
              scope.$apply(function() {
                navDataService.set('user', user);
                navDataService.set('inscriptionsEnabled', true);
              });
            }));

            it('hides the disable inscriptions item', function() {
              expect(section.find("[name='sidebar-admins-section-disable-inscriptions']").hasClass('ng-hide')).toBe(false);
            });

            it('shows the inscriptions enabled item', function() {
              expect(section.find("[name='sidebar-admins-section-enable-inscriptions']").hasClass('ng-hide')).toBe(true);
            });
          });
        });
      });

      describe('when inspecting the sidebar students section', function() {
        var section;

        beforeEach(function() {
          section = sidebarContainer.find("[name='sidebar-students-section']");
        });

        it('contains this section', function() {
          expect(section.length).toBe(1);
        });

        it('contains an index item', function() {
          expect(section.find("[name='sidebar-students-section-index']").length).toBe(1);
        });

        it('contains a new student item', function() {
          expect(section.find("[name='sidebar-students-section-new']").length).toBe(1);
        });
      });

      describe('when inspecting the sidebar courses section', function() {
        var section;

        beforeEach(function() {
          section = sidebarContainer.find("[name='sidebar-courses-section']");
        });

        it('contains this section', function() {
          expect(section.length).toBe(1);
        });

        it('contains an index item', function() {
          expect(section.find("[name='sidebar-courses-section-index']").length).toBe(1);
        });

        it('contains a new course item', function() {
          expect(section.find("[name='sidebar-courses-section-new']").length).toBe(1);
        });
      });
    });

    describe('when inspecting the sub-sidebar section', function() {
      it('contains the sidebar', function() {
        expect(sidebarContainer.find('.sub-sidebar-container').length).toBe(1);
      });

      describe('when inspecting the admin section', function() {
        var section;
        var sectionSelector = function (suffix) {
          return "[ng-show='controller.subSidebar.admin.actions" + (suffix || '') + "']";
        };

        beforeEach(function() {
          section = sidebarContainer.find(sectionSelector());
        });

        it('contains this section', function() {
          expect(section.length).toBe(1);
        });

        it('contains a show option if action is present', function() {
          expect(section.find(sectionSelector('.show')).length).toBe(1);
        });

        it('contains an edit option if action is present', function() {
          expect(section.find(sectionSelector('.edit')).length).toBe(1);
        });

        it('contains an reset password option if action is present', function() {
          expect(section.find(sectionSelector('.resetPassword')).length).toBe(1);
        });

        it('contains an edit password option if action is present', function() {
          expect(section.find(sectionSelector('.editPassword')).length).toBe(1);
        });
      });

      describe('when inspecting the student section', function() {
        var section;
        var sectionSelector = function (suffix) {
          return "[ng-show='controller.subSidebar.student.actions" + (suffix || '') + "']";
        };

        beforeEach(function() {
          section = sidebarContainer.find(sectionSelector());
        });

        it('contains this section', function() {
          expect(section.length).toBe(1);
        });

        it('contains a show option if action is present', function() {
          expect(section.find(sectionSelector('.show')).length).toBe(1);
        });

        it('contains an edit option if action is present', function() {
          expect(section.find(sectionSelector('.edit')).length).toBe(1);
        });

        it('contains an reset password option if action is present and user is admin', function() {
          expect(section.find(sectionSelector('.resetPassword && controller.user.admin')).length).toBe(1);
        });

        it('contains an update password option if action is present', function() {
          expect(section.find(sectionSelector('.editPassword')).length).toBe(1);
        });

        it('contains a courses option if action is present', function() {
          expect(section.find(sectionSelector('.courses')).length).toBe(1);
        });

        it('contains a grades option if action is present', function() {
          expect(section.find(sectionSelector('.grades')).length).toBe(1);
        });

        it('contains an inscriptions option if action is present && inscriptions are enabled', function() {
          expect(section.find(sectionSelector('.inscriptions && controller.user.authorities.viewInscriptions')).length).toBe(1);
        });

        it('contains a finals option if action is present', function() {
          expect(section.find(sectionSelector('.finals')).length).toBe(1);
        });

        it('contains a delete option if action is present and if user is an admin', function() {
          expect(section.find(sectionSelector('.delete && controller.user.admin')).length).toBe(1);
        });
      });

      describe('when inspecting the course section', function() {
        var section;
        var sectionSelector = function (suffix) {
          return "[ng-show='controller.subSidebar.course.actions" + (suffix || '') + "']";
        };

        beforeEach(function() {
          section = sidebarContainer.find(sectionSelector());
        });

        it('contains this section', function() {
          expect(section.length).toBe(1);
        });

        it('contains a show option if action is present', function() {
          expect(section.find(sectionSelector('.show')).length).toBe(1);
        });

        it('contains an edit option if action is present and user is an admin', function() {
          expect(section.find(sectionSelector('.edit && controller.user.admin')).length).toBe(1);
        });

        it('contains a students option if action is present', function() {
          expect(section.find(sectionSelector('.students')).length).toBe(1);
        });

        it('contains an approved option if action is present', function() {
          expect(section.find(sectionSelector('.approved')).length).toBe(1);
        });

        it('contains a delete option if action is present and user is admin', function() {
          expect(section.find(sectionSelector('.delete && controller.user.admin')).length).toBe(1);
        });
      });
    });

    describe('when testing the controller', function() {
      var navDataServiceService;

      beforeEach(inject(function(navDataService) {
        navDataServiceService = navDataService;
      }));

      describe('when checking inscriptions enabled callback', function() {
        describe('when inscriptions are enabled', function() {
          describe('when user is defined', function() {
            var thisUser;

            beforeEach(inject(function(apiResponses) {
              thisUser = apiResponses.currentAdmin;
              scope.$apply(function() {
                navDataServiceService.set('user', thisUser);
                navDataServiceService.set('inscriptionsEnabled', true);
              });
            }));

            it('sets him the view inscriptions authority', function() {
              expect(thisUser.authorities.viewInscriptions).toBe(true);
            });

            it('sets him the add inscription authority', function() {
              expect(thisUser.authorities.addInscription).toBe(true);
            });

            it('sets him the delete inscription authority', function() {
              expect(thisUser.authorities.deleteInscription).toBe(true);
            });

            describe('when user is an admin', function() {
              it('sets him the disable inscriptions authority', function() {
                expect(thisUser.authorities.disableInscriptions).toBe(true);
              });
            });

            describe('when user is a student', function() {
              beforeEach(inject(function(apiResponses) {
                thisUser = apiResponses.currentStudent;
                scope.$apply(function() {
                  navDataServiceService.set('user', thisUser);
                  navDataServiceService.set('inscriptionsEnabled', true);
                });
              }));

              it('does not set him the disable inscriptions authority', function() {
                expect(thisUser.authorities.disableInscriptions).toBeUndefined();
              });
            });
          });
        });

        describe('when inscriptions are disabled', function() {
          describe('when user is defined', function() {
            var thisUser;

            beforeEach(inject(function(apiResponses) {
              thisUser = apiResponses.currentAdmin;
              scope.$apply(function() {
                navDataServiceService.set('user', thisUser);
                navDataServiceService.set('inscriptionsEnabled', false);
              });
            }));

            it('sets him the view inscriptions authority', function() {
              expect(thisUser.authorities.viewInscriptions).toBe(false);
            });

            it('sets him the add inscription authority', function() {
              expect(thisUser.authorities.addInscription).toBe(false);
            });

            it('sets him the delete inscription authority', function() {
              expect(thisUser.authorities.deleteInscription).toBe(false);
            });

            describe('when user is an admin', function() {
              it('sets him the disable inscriptions authority', function() {
                expect(thisUser.authorities.disableInscriptions).toBe(false);
              });
            });

            describe('when user is a student', function() {
              beforeEach(inject(function(apiResponses) {
                thisUser = apiResponses.currentStudent;
                scope.$apply(function() {
                  navDataServiceService.set('user', thisUser);
                  navDataServiceService.set('inscriptionsEnabled', false);
                });
              }));

              it('does not set him the disable inscriptions authority', function() {
                expect(thisUser.authorities.disableInscriptions).toBeUndefined();
              });
            });
          });
        });
      });



      // we are implicitly testing that the controller is subscribed to user changes event
      describe('when there is no user defined', function() {
        beforeEach(inject(function(navDataService) {
          scope.$apply(function() {
            navDataService.set('user', undefined);
          });
        }));

        it('sets no section to sidebar', function() {
          expect(controller.sidebar).toEqual({});
        });
      });

      describe('when user is defined', function() {
        describe('when user has an unexpected authority', function() {
          beforeEach(inject(function(navDataService) {
            scope.$apply(function() {
              var thisUser = user;
              thisUser.authorities = {unexpected: true};
              navDataService.set('user', thisUser);
            });
          }));

          it('sets no section to sidebar', function() {
            expect(controller.sidebar).toEqual({});
          });
        });

        describe('when user has admins authority', function() {
          var thisUser;
          beforeEach(inject(function(navDataService) {
            scope.$apply(function() {
              thisUser = user;
              thisUser.admin = true;
              navDataService.set('user', thisUser);
            });
          }));

          it('sets admins sidebar section', function() {
            expect(controller.sidebar.admins).toBeDefined();
          });

          it('sets index admins sidebar section', function() {
            expect(controller.sidebar.admins.actions.index.path).toBeDefined();
          });

          it('sets new admins sidebar section', function() {
            expect(controller.sidebar.admins.actions.new.path).toBeDefined();
          });
        });

        describe('when user has students authority', function() {
          beforeEach(inject(function(navDataService) {
            scope.$apply(function() {
              var thisUser = user;
              thisUser.authorities.students = true;
              navDataService.set('user', thisUser);
            });
          }));

          it('sets students sidebar section', function() {
            expect(controller.sidebar.students).toBeDefined();
          });

          it('sets index students sidebar section', function() {
            expect(controller.sidebar.students.actions.index.path).toBeDefined();
          });

          it('sets new students sidebar section', function() {
            expect(controller.sidebar.students.actions.new.path).toBeDefined();
          });
        });

        describe('when user has courses authority', function() {
          beforeEach(inject(function(navDataService) {
            scope.$apply(function() {
              var thisUser = user;
              thisUser.authorities.courses = true;
              navDataService.set('user', thisUser);
            });
          }));

          it('sets courses sidebar section', function() {
            expect(controller.sidebar.courses).toBeDefined();
          });

          it('sets index courses sidebar section', function() {
            expect(controller.sidebar.courses.actions.index.path).toBeDefined();
          });

          it('sets new courses sidebar section', function() {
            expect(controller.sidebar.courses.actions.new.path).toBeDefined();
          });
        });

        describe('when user has courses authority', function() {
          beforeEach(inject(function(navDataService) {
            scope.$apply(function() {
              var thisUser = user;
              thisUser.authorities.courses = true;
              navDataService.set('user', thisUser);
            });
          }));

          it('sets courses sidebar section', function() {
            expect(controller.sidebar.courses).toBeDefined();
          });

          it('sets index courses sidebar section', function() {
            expect(controller.sidebar.courses.actions.index.path).toBeDefined();
          });

          it('sets new courses sidebar section', function() {
            expect(controller.sidebar.courses.actions.new.path).toBeDefined();
          });
        });
      });

      describe('when checking subscriptions', function() {
        var expectedSidebar, spiedNavDataService;

        beforeEach(inject(function(navDataService) {
          spiedNavDataService = navDataService;
          expectedSidebar = {hello: 'test'};
          scope.$apply(function() {
            controller.subSidebar = {}; // clean any possible data
            spiedNavDataService.set('subSidebar', expectedSidebar);
          });
        }));

        it('is subscribed to subSidebar changes event', function() {
          expect(controller.subSidebar).toEqual(expectedSidebar);
        });
      });
    });

    describe('when executing closeSidebar method', function() {
      var navDataServiceService;

      beforeEach(inject(function(navDataService) {
        // sets data so as to be sure we are testing what we should
        // not using the navDataService as we are not sure up to now if this
        // is linked to the sidebarOpen property of the scope
        scope.$apply(function() {
          sidebarContainer.scope().sidebarOpen = true;
        });

        navDataServiceService = navDataService;
        spyOn(navDataServiceService, 'get').and.callThrough();

        // we are implicitly testing that there should be at least one element with this action
        var elementWithCloseSidebar = sidebarContainer.find("[ng-click='closeSidebar()']").first();
        elementWithCloseSidebar.triggerHandler('click');
      }));


      it('gets its value from the navDataService', function() {
        expect(navDataServiceService.get).toHaveBeenCalledWith('sidebarOpen');
      });

      it('sets sidebarOpen to false', function() {
        expect(sidebarContainer.scope().sidebarOpen).toBe(false);
      });
    });

    describe('when receiving an undefined sidebarOpen status', function() {
      var navDataServiceService;

      beforeEach(inject(function(navDataService) {
        navDataServiceService = navDataService;
        navDataService.set('sidebarOpen', undefined);
      }));

      it('sets sidebarOpen to false', function() {
        expect(sidebarContainer.scope().sidebarOpen).toBe(false);
      });
    });

    describe('when updating windowSize', function() {
      beforeEach(inject(function(windowSize) {
        scope.$apply(function() {
          // so as to reset the value
          sidebarContainer.scope().windowIsMobile = 'invalid-value';
        });
        windowSize.set('windowIsMobile', 'sm');
      }));

      it('updates windowIsMobile scope variable', function() {
        expect(sidebarContainer.scope().windowIsMobile).toBe('sm');
      });
    });
  });
});
