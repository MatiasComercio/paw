'use strict';

define([], function() {
  return {
    defaultRoutePath: '/not_found',
    routes: {
      '/': {
        templateUrl: 'views/home.html',
        controller: 'HomeCtrl'
      },
      '/login': {
        templateUrl: 'views/login.html',
        controller: 'LoginCtrl'
      },
      '/not_found': {
        templateUrl: 'views/not_found.html',
        controller: 'NotFoundCtrl'
      },
      '/server_error': {
        templateUrl: 'views/server_error.html',
        controller: 'ServerErrorCtrl'
      },
      '/users/change_password': {
        templateUrl: 'views/user/change_password.html',
        controller: 'UserChangePasswordCtrl',
        relativePath: '/user'
      },
      '/students': {
        templateUrl: 'views/students/index.html',
        controller: 'StudentsIndexCtrl',
        relativePath: '/students'
      },
      '/students/new': {
        templateUrl: 'views/students/new.html',
        controller: 'StudentsNewCtrl',
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
      '/students/:docket/courses': {
        templateUrl: 'views/students/courses_index.html',
        controller: 'StudentsCoursesIndexCtrl',
        relativePath: '/students'
      },
      '/students/:docket/grades': {
        templateUrl: 'views/students/grades.html',
        controller: 'StudentsGradesCtrl',
        relativePath: '/students'
      },
      '/students/:docket/inscriptions': {
        templateUrl: 'views/students/inscriptions.html',
        controller: 'StudentsInscriptionsCtrl',
        relativePath: '/students'
      },
      '/students/:docket/final_inscriptions': {
        templateUrl: 'views/students/final_inscriptions.html',
        controller: 'StudentsFinalInscriptionsCtrl',
        relativePath: '/students'
      },
      '/courses': {
        templateUrl: 'views/courses/index.html',
        controller: 'CoursesIndexCtrl',
        relativePath: '/courses'
      },
      '/courses/new': {
        templateUrl: 'views/courses/new.html',
        controller: 'CoursesNewCtrl',
        relativePath: '/courses'
      },
      '/courses/:courseId': {
        templateUrl: 'views/courses/show.html',
        controller: 'CoursesShowCtrl',
        relativePath: '/courses'
      },
      '/courses/:courseId/edit': {
        templateUrl: 'views/courses/edit.html',
        controller: 'CoursesEditCtrl',
        relativePath: '/courses'
      },
      '/courses/:courseId/students': {
        templateUrl: 'views/courses/students_index.html',
        controller: 'CoursesStudentsIndexCtrl',
        relativePath: '/courses'
      },
      '/courses/:courseId/students/passed': {
        templateUrl: 'views/courses/students_passed.html',
        controller: 'CoursesStudentsPassedCtrl',
        relativePath: '/courses'
      },
      '/courses/:courseId/correlatives/new': {
        templateUrl: 'views/courses/correlatives_new.html',
        controller: 'CoursesCorrelativesNewCtrl',
        relativePath: '/courses'
      },
      '/courses/:courseId/final_inscriptions/new': {
        templateUrl: 'views/courses/final_inscriptions_new.html',
        controller: 'CoursesFinalInscriptionNewCtrl',
        relativePath: '/courses'
      },
      '/courses/:courseId/final_inscriptions/:inscriptionId': {
        templateUrl: 'views/courses/final_inscriptions_show.html',
        controller: 'CoursesFinalInscriptionShowCtrl',
        relativePath: '/courses'
      },
      '/admins': {
        templateUrl: 'views/admins/index.html',
        controller: 'AdminsIndexCtrl',
        relativePath: '/admins'
      },
      '/admins/new': {
        templateUrl: 'views/admins/new.html',
        controller: 'AdminsNewCtrl',
        relativePath: '/admins'
      },
      '/admins/:adminDni': {
        templateUrl: 'views/admins/show.html',
        controller: 'AdminsShowCtrl',
        relativePath: '/admins'
      },
      '/admins/:adminDni/edit': {
          templateUrl: 'views/admins/edit.html',
          controller: 'AdminsEditCtrl',
          relativePath: '/admins'
      }
      /* ===== yeoman hook ===== */
      /* Do not remove these commented lines! Needed for auto-generation */
    }
  };
});
