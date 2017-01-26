/* global paths */
'use strict';
require.config({
    baseUrl: '/scripts',
    paths: {
        affix: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/affix',
        alert: '../../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap/alert',
        angular: '../../bower_components/angular/angular',
        'angular-route': '../../bower_components/angular-route/angular-route',
        'angular-cookies': '../../bower_components/angular-cookies/angular-cookies',
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
        'angular-animate': '../../bower_components/angular-animate/angular-animate',
        'angular-bootstrap': '../../bower_components/angular-bootstrap/ui-bootstrap-tpls',
        'jquery-mousewheel': '../../bower_components/jquery-mousewheel/jquery.mousewheel',
        'angular-material': '../../bower_components/angular-material/angular-material',
        'angular-aria': '../../bower_components/angular-aria/angular-aria',
        lodash: '../../bower_components/lodash/dist/lodash',
        restangular: '../../bower_components/restangular/dist/restangular'
    },
    shim: {
        angular: {
            deps: [
                'jquery'
            ],
            exports: 'angular'
        },
        'angular-route': {
            deps: [
                'angular'
            ]
        },
        'angular-cookies': {
            deps: [
                'angular'
            ]
        },
        'angular-animate': {
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
        'angular-bootstrap': {
            deps: [
                'angular'
            ]
        },
        'angular-material': {
            deps: [
                'angular'
            ]
        },
        'angular-aria': {
            deps: [
                'angular'
            ]
        },
        lodash: {
            exports: '_'
        },
        restangular: {
            deps: [
                'angular',
                'lodash'
            ]
        }
    },
    packages: [

    ]
});

if (paths) {
    require.config({
        paths: paths
    });
}

require([
        'angular',
        'restangular',
        'paw',
        'controllers/BodyCtrl'
    ],
    function() {
        angular.bootstrap(document, ['paw']);
    }
);
