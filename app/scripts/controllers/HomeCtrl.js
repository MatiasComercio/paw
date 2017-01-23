'use strict';

define(['paw', 'services/navDataService'], function(paw) {
  paw.controller('HomeCtrl',
    ['navDataService',
    function(navDataService) {

      this.fetchCourse = function() {
        return {
          id: '123',
          name: 'Introducción a la informática y todoestechoclodetextoparaverqueandebien',
          actions: {
            show: {
              path: '/courses/123'
            },
            edit: {
              path: '/courses/123/edit'
            },
            students: {
              path: '/courses/123/students'
            },
            approved: {
              path: '/courses/123/approved'
            },
            addCorrelative: {
              path: '/courses/123/correlatives/new'
            },
            delete: {
              path: '/courses/123/delete'
            }
          }
        };
      };

      this.fetchAdmin = function() {
        return {
          dni: '38457013',
          firstName: '[ADMIN] Matías Nicolás',
          lastName: 'Comercio Vázquez asdasdasdasdasdasdasdasdadasdasdas',
          actions: {
            show: {
              path: '/admins/123'
            },
            edit: {
              path: '/admins/123/edit'
            },
            resetPassword: {
              path: '/admins/123/resetPassword'
            },
            updatePassword: {
              path: '/admins/123/updatePassword'
            }
          }
        };
      };

      this.fetchStudent = function() {
        return {
          dni: '38457013',
          firstName: 'Matías Nicolás',
          lastName: 'Comercio Vázquez asdasdasdasdasdasdasdasdadasdasdas',
          actions: {
            show: {
              path: '/students/123'
            },
            edit: {
              path: '/students/123/edit'
            },
            resetPassword: {
              path: '/students/123/resetPassword'
            },
            updatePassword: {
              path: '/students/123/updatePassword'
            },
            courses: {
              path: '/students/123/courses'
            },
            grades: {
              path: '/students/123/grades'
            },
            inscriptions: {
              path: '/students/123/inscriptions'
            },
            finals: {
              path: '/students/123/finals'
            },
            delete: {
              path: '/users/123/delete'
            }
          }
        };
      };

      var _this = this;
      function fetchData() {
        return {
          // admin: _this.fetchAdmin()
          student: _this.fetchStudent()
          // course: _this.fetchCourse()
        };
      };

      this.subSidebarUpdate = function(subSidebar) {
        this.subSidebar = subSidebar;
        var student = this.subSidebar.student;
        if (student) {
          student.fullName = student.firstName + ' ' + student.lastName;
        }

        var admin = this.subSidebar.admin;
        if (admin) {
          admin.fullName = admin.firstName + ' ' + admin.lastName;
        }
      };

      this.subSidebarUpdate(fetchData());

      navDataService.set('subSidebar', this.subSidebar);
    }]
  );
});
