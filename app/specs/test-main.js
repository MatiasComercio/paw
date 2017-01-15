'use strict';

var allTestFiles = [];
var TEST_REGEXP = /(spec|test)\.js$/i;

// Get a list of all the test files to include
Object.keys(window.__karma__.files).forEach(function (file) {
  if (TEST_REGEXP.test(file)) {
    allTestFiles.push(file);
  }
});

require.config({
  // Karma serves files under /base, which is the basePath from your config file
  baseUrl: '/base/app/scripts',

  // this is from where requirejs will search dependencies, prepending the baseUrl to
  // the specified files
  paths: {
    'paw': 'paw',
    'routes': 'routes',
    'services/dependencyResolverFor': 'services/dependencyResolverFor',
    'i18n/i18nLoader': 'i18n/i18nLoader',
    'i18n/translations.es': 'i18n/translations.es',
    affix: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/affix',
    alert: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/alert',
    angular: '../../bower_components/angular/angular',
    'angular-route': '../../bower_components/angular-route/angular-route',
    'angular-translate': '../../bower_components/angular-translate/angular-translate',
    'angular-mocks': '../../bower_components/angular-mocks/angular-mocks',
    button: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/button',
    bootstrap: '../../bower_components/bootstrap/dist/js/bootstrap',
    carousel: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/carousel',
    collapse: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/collapse',
    dropdown: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/dropdown',
    'es5-shim': '../../bower_components/es5-shim/es5-shim',
    jquery: '../../bower_components/jquery/dist/jquery',
    json3: '../../bower_components/json3/lib/json3',
    modal: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/modal',
    moment: '../../bower_components/moment/moment',
    popover: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/popover',
    requirejs: '../../bower_components/requirejs/require',
    scrollspy: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/scrollspy',
    tab: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/tab',
    tooltip: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/tooltip',
    transition: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/transition',
    'font-awesome': '../../bower_components/font-awesome',
    'bootstrap-sass-official': '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap',
    'navbar-template': '../views/navbar.html'
  },
  shim: {
    angular: {
      deps: [
        'jquery'
      ]
    },
    'angular-route': {
      deps: [
        'angular'
      ]
    },
    'angular-mocks': {
      deps: [
        'angular'
      ]
    },
    bootstrap: {
      deps: [
        'jquery',
        'modal'
      ]
    },
    modal: {
      deps: [
        'jquery'
      ]
    },
    tooltip: {
      deps: [
        'jquery'
      ]
    },
    'angular-translate': {
      deps: [
        'angular'
      ]
    },
    // all directives templates should be declared as follows
    'navbar-template': {
      deps: [
        'angular'
      ]
    }
  },
  packages: [

  ],

  // dynamically load all test files
  deps: allTestFiles,

  // we have to kickoff jasmine, as it is asynchronous
  callback: function() {
    console.log('load complete');
    window.__karma__.start();
  }
});
