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
            },
            '/students/:docket/edit': {
              templateUrl: 'views/students/edit.html',
              controller: 'StudentsEditCtrl',
              relativePath: '/students'
            },
            '/courses': {
                templateUrl: 'views/courses/index.html',
                controller: 'CoursesIndexCtrl',
                relativePath: '/courses'
            },
            '/courses/:courseId': {
              templateUrl: 'views/courses/show.html',
              controller: 'CoursesShowCtrl',
              relativePath: '/courses'
            },
            '/courses/:courseId/students': {
                templateUrl: 'views/courses/students_index.html',
                controller: 'CoursesStudentsIndexCtrl',
                relativePath: '/courses'
            }
            /* ===== yeoman hook ===== */
            /* Do not remove these commented lines! Needed for auto-generation */
        }
    };
});
