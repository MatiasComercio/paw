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
    '$location',
    function($location) {
      return pathsService($location);
    }
  ]);

  // set the authentication filters
  // Code taken and adapted from http://arthur.gonigberg.com/2013/06/29/angularjs-role-based-auth/
  // underscore.js is being included with restangular
  paw.run(['$rootScope', '$location', 'Authentication', 'Restangular', 'Paths',
  function ($rootScope, $location, Authentication, Restangular, Paths) {
    // enumerate routes that don't need authentication
    var routesThatDontRequireAuth = [Paths.get().login().path];

    // check if current location matches route
    var routeClean = function (route) {
      return _.find(routesThatDontRequireAuth,
        function (noAuthRoute) {
          return noAuthRoute === route;
        }
      );
    };

    $rootScope.$on('$routeChangeStart', function (event, next, current) {
      // if route requires auth and user is not logged in
      if (!routeClean($location.url()) && !Authentication.isLoggedIn()) {
        // redirect back to login
        Paths.get().login().go();
      }
    });

    Restangular.setErrorInterceptor(
      function(response, deferred, responseHandler) {
        var propagateError = true;
        if (response.status === 401) {
          // should login again
          Paths.get().login().go();
          propagateError = false;
        } else if (response.status === 403) {
          // invalid permissions
          Paths.get().unauthorized().go();
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
