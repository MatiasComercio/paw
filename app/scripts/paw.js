'use strict';
define(['routes',
  'services/dependencyResolverFor',
  'i18n/i18nLoader!',
  'angular',
  'angular-route',
  'angular-cookies',
  'angular-animate',
  'bootstrap',
  'angular-translate',
  'angular-bootstrap',
  'jquery-mousewheel',
  'angular-material',
  'angular-aria'],
  function(config, dependencyResolverFor, i18n) {
    var paw = angular.module('paw', [
      'ngRoute',
      'ngCookies',
      'ngAnimate',
      'ngMaterial',
      'ui.bootstrap',
      'restangular',
      'pascalprecht.translate'
    ]);
    paw
    .config(
      ['$routeProvider',
      '$controllerProvider',
      '$compileProvider',
      '$filterProvider',
      '$provide',
      '$translateProvider',
      'RestangularProvider',
      function($routeProvider, $controllerProvider, $compileProvider, $filterProvider, $provide, $translateProvider, RestangularProvider) {

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
      }]);
      return paw;
    }
);
