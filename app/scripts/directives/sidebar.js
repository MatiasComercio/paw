'use strict';

define(
  ['paw',
  'services/navDataService',
  'services/windowSize',
  'services/Paths',
  'directives/sidebarItem',
  'directives/preventScroll',
  'directives/backdrop',
  'controllers/modals/InscriptionsController',
  'controllers/modals/ResetPasswordController',
  'controllers/modals/DeleteUserController',
  'controllers/modals/DeleteCourseController'],
  function(paw) {
    paw.directive('xsidebar', ['navDataService', 'windowSize', 'Paths',
    function(navDataService, windowSize, Paths) {
      function controller(navDataService) {
        this.sidebar = {};

        var _this = this;

        // handle user updates
        var userUpdateCallback = function() {
          _this.user = navDataService.get('user');
          if (_this.user !== undefined) {
            if (_this.user.authorities.admins) {
              _this.sidebar.admins = sidebarAdmins();
              _this.sidebar.admins.actions.inscriptions = {
                enabled: _this.user.authorities.disableInscriptions
              };
            }
            if (_this.user.authorities.students) {
              _this.sidebar.students = sidebarStudents();
            }
            if (_this.user.authorities.courses) {
              _this.sidebar.courses = sidebarCourses();
            }
          } else {
            _this.sidebar = {};
          }
        };
        navDataService.registerObserverCallback('user', userUpdateCallback);
        userUpdateCallback();

        // handle inscription enable/disable changes
        var inscriptionsEnabledCallback = function() {
          var inscriptionsEnabled = navDataService.get('inscriptionsEnabled');
          if (_this.user) {
            _this.user.authorities.viewInscriptions = inscriptionsEnabled;
            _this.user.authorities.addInscription = inscriptionsEnabled;
            _this.user.authorities.deleteInscription = inscriptionsEnabled;
            if (_this.user.admin) {
              _this.user.authorities.disableInscriptions = inscriptionsEnabled;
            }
          }
          if (_this.sidebar.admins) {
            _this.sidebar.admins.actions.inscriptions.enabled = inscriptionsEnabled;
          }
        };
        navDataService.registerObserverCallback('inscriptionsEnabled', inscriptionsEnabledCallback);
        inscriptionsEnabledCallback();

        // handle subSidebar changes
        var subSidebarCallback = function() {
          _this.subSidebar = navDataService.get('subSidebar');
        };
        navDataService.registerObserverCallback('subSidebar', subSidebarCallback);
        subSidebarCallback();
      };

      return {
        restrict: 'E',
        templateUrl: 'views/directives/sidebar.html',
        controller: controller,
        controllerAs: 'controller',
        scope: {},
        bindToController: true,
        link: function(scope, element, attributes) {
          // handle window size changes
          var windowIsMobileCallback = function() {
            scope.windowIsMobile = windowSize.get('windowIsMobile');
          };
          windowSize.registerObserverCallback('windowIsMobile', windowIsMobileCallback);
          windowIsMobileCallback();

          // handle subSidebar changes
          var sidebarOpenCallback = function() {
            scope.sidebarOpen = navDataService.get('sidebarOpen');
            if (scope.sidebarOpen === undefined) {
              scope.sidebarOpen = false;
            }
          };
          navDataService.registerObserverCallback('sidebarOpen', sidebarOpenCallback);
          sidebarOpenCallback();

          scope.closeSidebar = function() {
            navDataService.set('sidebarOpen', false);
          };
        }
      };

      function sidebarAdmins() {
        return {
          actions: {
            index: {
              path: Paths.get().admins().path
            },
            new: {
              path: Paths.get().admins().new().path
            }
          }
        };
      }

      function sidebarStudents() {
        return {
          actions: {
            index: {
              path: Paths.get().students().path
            },
            new: {
              path: Paths.get().students().new().path
            }
          }
        };
      }


      function sidebarCourses() {
        return {
          actions: {
            index: {
              path: Paths.get().courses().path
            },
            new: {
              path: Paths.get().courses().new().path
            }
          }
        };
      }
    }]);
  }
);
