'use strict';

define([], function() {
  return function($location, $log) {
    var pathsService = {};

    pathsService.getAdminActions = function(admin, user) {
      if (!admin) {
        $log.warn('Admin is undefined');
        return undefined;
      }

      var actions = {
        show: {
          path: pathsService.get().admins(admin).path
        },
        edit: {
          path: pathsService.get().admins(admin).edit().path
        },
        resetPassword: true,
        // delete admin endpoint is not ready yet
        delete: false
      };

      // user should be an admin
      if (!user.admin) {
        $log.warn('User should be an admin');
        return undefined;
      }

      if (admin.dni === user.dni) {
        actions.editPassword = {
          path: pathsService.get().users().editPassword().path
        };
      }

      return actions;
    };

    pathsService.getStudentActions = function(student, user) {
      if (!student) {
        $log.warn('Student is undefined');
        return undefined;
      }

      if (user.student && (user.docket !== student.docket)) {
        $log.warn('Student trying to access another student');
        return undefined;
      }

      var actions = {
        show: {
          path: pathsService.get().students(student).path
        },
        edit: {
          path: pathsService.get().students(student).edit().path
        },
        resetPassword: true,
        courses: {
          path: pathsService.get().students(student).courses().path
        },
        grades: {
          path: pathsService.get().students(student).grades().path
        },
        inscriptions: {
          path: pathsService.get().students(student).inscriptions().path
        },
        finals: {
          path: pathsService.get().students(student).finals().path
        },
        delete: true
      };

      if (student.dni === user.dni) {
        actions.editPassword = {
          path: pathsService.get().users().editPassword().path
        };
      }

      return actions;
    };

    pathsService.getCourseActions = function(course, user) {
      if (!course) {
        $log.warn('Course is undefined');
        return undefined;
      }

      return {
        show: {
          path: pathsService.get().courses(course).path
        },
        edit: {
          path: pathsService.get().courses(course).edit().path
        },
        students: {
          path: pathsService.get().courses(course).students().path
        },
        approved: {
          path: pathsService.get().courses(course).approved().path
        },
        addCorrelative: {
          path: pathsService.get().courses(course).addCorrelative().path
        },
        addFinal: {
          path: pathsService.get().courses(course).finalInscriptions().new().path
        },
        delete: true
      };
    };


    // Builder pattern for front end paths
    pathsService.get = function() {
      var _this = {
        path: '#!'
      };

      _this.absolutePath = function() {
        return _this.path.replace('#!', '');
      };

      _this.go = function() {
        return $location.path(_this.absolutePath());
      };

      var append = function(str) {
        _this.path += str;
        return _this;
      };

      // common
      _this.index = function() {
        return append('/');
      };

      _this.login = function() {
        return append('/login');
      };

      _this.logout = function() {
        return append('/logout');
      };

      _this.unauthorized = function() {
        return append('/unauthorized');
      };

      _this.serverError = function() {
        return append('/server_error');
      };

      _this.notFound = function() {
        return append('/not_found');
      };

      // users
      _this.users = function(user) {
        var updated = append('/users');
        return user === undefined ? updated : append('/' + user.dni);
      };

      _this.admins = function(admin) {
        var updated = append('/admins');
        return admin === undefined ? updated : append('/' + admin.dni);
      };

      _this.students = function(student) {
        var updated = append('/students');
        return student === undefined ? updated : append('/' + student.docket);
      };

      _this.new = function() {
        return append('/new');
      };

      _this.edit = function() {
        return append('/edit');
      };

      _this.editPassword = function() {
        return append('/change_password');
      };

      _this.grades = function() {
        return append('/grades');
      };

      _this.inscriptions = function() {
        return append('/inscriptions');
      };

      _this.finals = function(final) {
        var updated = append('/final_inscriptions');
        return final === undefined ? updated : append('/' + final.inscriptionId);
      };

      _this.finalInscriptions = function() {
        return append('/final_inscriptions');
      };

      // courses
      _this.courses = function(course) {
        var updated = append('/courses');
        return course === undefined ? updated : append('/' + course.courseId);
      };

      _this.approved = function() {
        return append('/students/passed');
      };

      _this.addCorrelative = function() {
        return append('/correlatives/new');
      };

      return _this;
    };

    return pathsService;
  };
});
