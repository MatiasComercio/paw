'use strict';

define([], function() {
    return {
        defaultRoutePath: '/',
        routes: {
            '/': {
                templateUrl: 'views/home.html',
                controller: 'HomeCtrl'
            },
            '/login': {
                templateUrl: 'views/login.html',
                controller: 'LoginCtrl'
            },
            '/students': {
                templateUrl: 'views/students/index.html',
                controller: 'StudentsIndexCtrl',
                relativePath: '/students'
            },
            '/students/:docket': {
                templateUrl: 'views/students/show.html',
                controller: 'StudentsShowCtrl',
                relativePath: '/students'
            }
            /* ===== yeoman hook ===== */
            /* Do not remove these commented lines! Needed for auto-generation */
        }
    };
});
