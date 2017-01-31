// Karma configuration
// Generated on Sat Jan 14 2017 12:51:36 GMT-0300 (ART)
'use strict';
module.exports = function(config) {

  // Create symlink to bower_components from the app's folder
  // Why do we need this?
  // basePath is used as a starting path for all other paths inside this
  // config file.
  // For example, with the following config
  //   basePath: 'app', files: [{pattern: index.js}, {pattern: ../other.js}], port: 9876
  // the files that will be match will be
  //   '<project_path>/app/index.html'
  //   '<project_path>/other.html'
  //
  // However, the files will be served as:
  //    http://localhost:9876/base/index.js
  //    http://localhost:9876/absolute/absoultePathToProject/other.js
  //
  // Due to we need to set basePath to 'app' to be able to test directives with templateUrl,
  // and to avoid the case of the 'other.js' example of above with bower_components, we need
  // to make reference to the bower_components folder somehow.
  // This 'somehow' was resolved to be a symlink.
  var fs = require('fs');
  // First arg: reference for the symlink
  // Second arg: where the symlink will be created
  // Third arg: type of symlink
  // Fourth arg: callback function
  fs.symlink('../bower_components', './app/bower_components', 'dir', function() {});

  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: 'app',

    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine', 'requirejs'],

    // list of files / patterns to load in the browser
    files: [

      {pattern: 'bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/affix.js', included: false},
      {pattern: 'bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/alert.js', included: false},
      {pattern: 'bower_components/angular/angular.js', included: false},
      {pattern: 'bower_components/angular-route/angular-route.js', included: false},
      {pattern: 'bower_components/angular-cookies/angular-cookies.js', included: false},
      {pattern: 'bower_components/angular-translate/angular-translate.js', included: false},
      {pattern: 'bower_components/angular-mocks/angular-mocks.js', included: false},
      {pattern: 'bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/button.js', included: false},
      {pattern: 'bower_components/bootstrap/dist/js/bootstrap.js', included: false},
      {pattern: 'bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/carousel.js', included: false},
      {pattern: 'bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/collapse.js', included: false},
      {pattern: 'bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/dropdown.js', included: false},
      {pattern: 'bower_components/es5-shim/es5-shim.js', included: false},
      {pattern: 'bower_components/jquery/dist/jquery.js', included: false},
      {pattern: 'bower_components/json3/lib/json3.js', included: false},
      {pattern: 'bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/modal.js', included: false},
      {pattern: 'bower_components/moment/moment.js', included: false},
      {pattern: 'bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/popover.js', included: false},
      {pattern: 'bower_components/requirejs/require.js', included: false},
      {pattern: 'bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/scrollspy.js', included: false},
      {pattern: 'bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/tab.js', included: false},
      {pattern: 'bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/tooltip.js', included: false},
      {pattern: 'bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/transition.js', included: false},
      {pattern: 'bower_components/bootstrap-sass-official/assets/javascripts/bootstrap.js', included: false},
      {pattern: 'bower_components/angular-animate/angular-animate.js', included: false},
      {pattern: 'bower_components/angular-bootstrap/ui-bootstrap-tpls.js', included: false},
      {pattern: 'bower_components/jquery-mousewheel/jquery.mousewheel.js', included: false},
      {pattern: 'bower_components/angular-material/angular-material.js', included: false},
      {pattern: 'bower_components/angular-aria/angular-aria.js', included: false},
      {pattern: 'bower_components/lodash/dist/lodash.js', included: false},
      {pattern: 'bower_components/restangular/dist/restangular.js', included: false},
      {pattern: 'bower_components/angular-messages/angular-messages.js', included: false},
      {pattern: 'views/**/*.html', included: false},
      {pattern: 'scripts/**/*.js', included: false},
      {pattern: 'specs/**/*.js', included: false},
      {pattern: 'specs/test-main.js', included: true}
    ],

    ngHtml2JsPreprocessor: {
      moduleName: 'directive-templates'
    },

    // list of files to exclude
    exclude: [
    ],

    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
      'views/**/*.html': ['ng-html2js'],
      // source files, that you wanna generate coverage for
      'scripts/controllers/**/*.js': ['coverage'],
      'scripts/directives/*.js': ['coverage'],
      'scripts/services/!(dependencyResolverFor).js': ['coverage']
    },

    // optionally, configure the reporter
    coverageReporter: {
      includeAllSources: true,
      reporters: [
        {type: 'html', dir: 'coverage/'},
        {type: 'lcov', dir: 'coverage/'}
      ],
      check: {
        global: {
          statements: 90
        }
      }
    },

    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['progress', 'coverage'],

    // web server port
    port: 9876,

    // enable / disable colors in the output (reporters and logs)
    colors: true,

    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,

    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,

    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    browsers: ['PhantomJS'],

    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false,

    // Concurrency level
    // how many browser should be started simultaneous
    concurrency: Infinity
  });
};
