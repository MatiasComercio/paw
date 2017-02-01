'use strict';

define([], function() {
  return function($location) {
    var pathsService = {};

    pathsService.getAdminActions = function(admin) {
      return {
        show: {
          path: pathsService.get().admins(admin).path
        },
        edit: {
          path: pathsService.get().admins(admin).edit().path
        },
        resetPassword: {
          // +++xremove: should be on the Admins/Users Service
          path: pathsService.get().admins(admin).path + '/resetPassword'
        },
        editPassword: {
          path: pathsService.get().admins(admin).editPassword().path
        }
      };
    };

    pathsService.getStudentActions = function(student) {
      return {
        show: {
          path: pathsService.get().students(student).path
        },
        edit: {
          path: pathsService.get().students(student).edit().path
        },
        // +++xremove: should be on the Students/Users Service
        resetPassword: {
          path: pathsService.get().students(student).path + '/resetPassword'
        },
        editPassword: {
          path: pathsService.get().students(student).editPassword().path
        },
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
        // +++xremove: should be on the Students/Users Service
        delete: {
          path: '/users/123/delete'
        }
      };
    };

    pathsService.getCourseActions = function(course) {
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
        delete: {
          // +++xremove: should be on the Courses Service
          path: pathsService.get().courses(course).path + '/delete'
        }
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

      // users
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
        return append('/password');
      };

      _this.grades = function() {
        return append('/grades');
      };

      _this.inscriptions = function() {
        return append('/inscriptions');
      };

      _this.finals = function() {
        return append('/finals');
      };

      // courses
      _this.courses = function(course) {
        var updated = append('/courses');
        return course === undefined ? updated : append('/' + course.courseId);
      };

      _this.approved = function() {
        return append('/approved');
      };

      _this.addCorrelative = function() {
        return append('/correlatives');
      };

      return _this;
    };

    return pathsService;
  };
});
