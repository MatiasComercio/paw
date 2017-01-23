'use strict';
define(['routes',
  'services/dependencyResolverFor',
  'i18n/i18nLoader!',
  'angular',
  'angular-route',
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
      'ngAnimate',
      'ngMaterial',
      'ui.bootstrap',
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
      function($routeProvider, $controllerProvider, $compileProvider, $filterProvider, $provide, $translateProvider) {

        paw.controller = $controllerProvider.register;
        paw.directive = $compileProvider.directive;
        paw.filter = $filterProvider.register;
        paw.factory = $provide.factory;
        paw.service = $provide.service;

        if (config.routes !== undefined) {
          angular.forEach(config.routes, function(route, path) {
            $routeProvider.when(path, {templateUrl: route.templateUrl, resolve: dependencyResolverFor(['controllers/' + route.controller]), controller: route.controller, gaPageTitle: route.gaPageTitle});
          });
        }
        if (config.defaultRoutePath !== undefined) {
          $routeProvider.otherwise({redirectTo: config.defaultRoutePath});
        }

        $translateProvider.translations('preferredLanguage', i18n);
        $translateProvider.preferredLanguage('preferredLanguage');
        $translateProvider.useSanitizeValueStrategy('escape');
      }]);
      return paw;
    }
);
