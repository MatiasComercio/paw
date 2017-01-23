// Karma configuration
// Generated on Sat Jan 14 2017 12:51:36 GMT-0300 (ART)
'use strict';
module.exports = function(config) {
  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: './',

    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine', 'requirejs'],

    // list of files / patterns to load in the browser
    files: [
      {pattern: 'bower_components/**/*.js', included: false},
      {pattern: 'app/views/**/*.html', included: false},
      {pattern: 'app/scripts/**/**/*.js', included: false},
      {pattern: 'app/specs/**/*.js', included: false},
      {pattern: 'app/specs/test-main.js', included: true}
    ],

    ngHtml2JsPreprocessor: {
      stripPrefix: 'app',
      moduleName: 'directive-templates'
    },

    // list of files to exclude
    exclude: [
    ],

    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
      'app/views/**/*.html': ['ng-html2js'],
      // source files, that you wanna generate coverage for
      'app/scripts/controllers/**/*.js': ['coverage'],
      'app/scripts/directives/*.js': ['coverage'],
      'app/scripts/services/!(dependencyResolverFor).js': ['coverage']
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
