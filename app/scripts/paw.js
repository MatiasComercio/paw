'use strict';
define(['routes',
'services/dependencyResolverFor',
'i18n/i18nLoader!',
'services/Authentication',
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
function(config, dependencyResolverFor, i18n, authenticationService) {
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

  // set the authentication filters
  // Code taken and adapted from http://arthur.gonigberg.com/2013/06/29/angularjs-role-based-auth/
  // underscore.js is being included with restangular
  paw.run(['$rootScope', '$location', 'Authentication', 'Restangular',
  function ($rootScope, $location, Authentication, Restangular) {
    // enumerate routes that don't need authentication
    var routesThatDontRequireAuth = ['/login'];

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
        $location.path('/login');
      }
    });

    Restangular.setErrorInterceptor(
      function(response, deferred, responseHandler) {
        var propagateError = true;
        if (response.status === 401) {
          // should login again
          $location.path('/login');
          propagateError = false;
        } else if (response.status === 403) {
          // invalid permissions
          $location.path('/unauthorized');
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
