'use strict';
define(['routes',
'services/dependencyResolverFor',
'i18n/i18nLoader!',
'services/Authentication',
'services/Paths',
'angular',
'angular-route',
'angular-cookies',
'angular-animate',
'bootstrap',
'angular-translate',
'angular-bootstrap',
'jquery-mousewheel',
'angular-material',
'angular-aria',
'angular-messages'],
function(config, dependencyResolverFor, i18n, authenticationService, pathsService) {
  var paw = angular.module('paw', [
    'ngRoute',
    'ngCookies',
    'ngAnimate',
    'ngMaterial',
    'ngMessages',
    'ui.bootstrap',
    'restangular',
    'pascalprecht.translate'
  ]);

  // define authentication service
  paw.service('Authentication', [
    'Restangular',
    '$cookies',
    function(Restangular, $cookies) {
      return authenticationService(Restangular, $cookies);
    }
  ]);

  // define Paths service
  paw.service('Paths', [
    '$location', '$log',
    function($location, $log) {
      return pathsService($location, $log);
    }
  ]);

  // set the authentication filters
  // Code taken and adapted from http://arthur.gonigberg.com/2013/06/29/angularjs-role-based-auth/
  // underscore.js is being included with restangular
  paw.run(['$rootScope', '$location', 'Authentication', 'Restangular', 'Paths',
  function ($rootScope, $location, Authentication, Restangular, Paths) {
    // enumerate routes that don't need authentication
    var routesThatDontRequireAuth = [Paths.get().login().path];
    var adminRoutes = [Paths.get().admins().path];

    // check if current location matches route
    var routeClean = function (route) {
      return _.find(routesThatDontRequireAuth,
        function (noAuthRoute) {
          return noAuthRoute === route;
        }
      );
    };

    // check if current location matches route
    var adminRoute = function (route) {
      return _.find(adminRoutes,
        function (adminRoute) {
          return adminRoute === route;
        }
      );
    };

    $rootScope.$on('$routeChangeStart', function (event, next, current) {
      // if logged in and trying to access loggin => redirect to students
      if (Authentication.isLoggedIn() && ($location.url() === Paths.get().login().absolutePath())) {
        Paths.get().go();
      }

      // if route requires auth and user is not logged in
      if (!routeClean($location.url()) && !Authentication.isLoggedIn()) {
        // redirect back to login
        Paths.get().login().go();
      }
    });

    // Code taken & adapted from:
    // http://stackoverflow.com/questions/24088610/restangular-spinner
    var pendingRequests = 0;

    Restangular.addRequestInterceptor(
      function (element, operation, what, url) {
        if (pendingRequests === 0) {
          $rootScope.showLoader = true;
        }
        pendingRequests++;
        return element;
      }
    );

    Restangular.addResponseInterceptor(
      function (data, operation, what, url, response, deferred) {
        pendingRequests--;
        if (pendingRequests === 0) {
          $rootScope.showLoader = false;
        }
        return data;
      }
    );

    Restangular.addErrorInterceptor(
      function(response, deferred, responseHandler) {
        pendingRequests--;
        if (pendingRequests === 0) {
          $rootScope.showLoader = false;
        }
        return true; // error not handled
      }
    );

    Restangular.setErrorInterceptor(
      function(response, deferred, responseHandler) {
        var propagateError = true;
        if (response.status === 401) {
          // it could happen that the token is invalid and that's causing the 401 status
          // user will be removed at the LoginCtrl
          Authentication.logout();
          // should login again
          Paths.get().login().go();
          propagateError = false;
        } else if (response.status === 403) {
          // invalid permissions
          Paths.get().notFound().go();
          propagateError = false;
        } else if (response.status === 404) {
          // invalid permissions
          Paths.get().notFound().go();
          propagateError = false;
        } else if (response.status === 500 || response.status === 503) {
          // invalid permissions
          Paths.get().serverError().go();
          propagateError = false;
        }

        return propagateError;
      }
    );
  }]);

  paw
  .config(
    ['$routeProvider',
    '$controllerProvider',
    '$compileProvider',
    '$filterProvider',
    '$provide',
    '$translateProvider',
    'RestangularProvider',
    function($routeProvider, $controllerProvider, $compileProvider,
      $filterProvider, $provide, $translateProvider, RestangularProvider) {

        paw.controller = $controllerProvider.register;
        paw.directive = $compileProvider.directive;
        paw.filter = $filterProvider.register;
        paw.factory = $provide.factory;
        paw.service = $provide.service;

        if (config.routes !== undefined) {
          angular.forEach(config.routes, function(route, path) {
            $routeProvider.when(path, {
              templateUrl: route.templateUrl,
              resolve: dependencyResolverFor(
                ['controllers' + (route.relativePath || '') + '/' + route.controller]
              ),
              controller: route.controller,
              gaPageTitle: route.gaPageTitle
            });
          });
        }
        if (config.defaultRoutePath !== undefined) {
          $routeProvider.otherwise({redirectTo: config.defaultRoutePath});
        }

        $translateProvider.translations('preferredLanguage', i18n);
        $translateProvider.preferredLanguage('preferredLanguage');
        $translateProvider.useSanitizeValueStrategy('escape');

        RestangularProvider.setBaseUrl('api/v1/');
      }]
    );
    return paw;
  }
);
