'use strict';

define(['paw',
'services/navDataService',
'services/Students',
'services/Courses'],
function(paw) {
  paw.controller('HomeCtrl',
    ['navDataService', 'Students', 'Courses',
    function(navDataService, Students, Courses) {
      this.courses = [];
      this.students = [];

      var _this = this;

      this.toggleCourses = function() {
        if (_this.courses.length === 0) {
          Courses.getList().then(function(courses) {
            _this.courses = _this.courses.concat(courses);
          });
        } else {
          _this.courses = [];
        }
      };

      this.toggleCourse = function(course) {
        course.get().then(function(courseData) {
          _this.course = courseData;
        });
      };

      this.toggleStudents = function() {
        if (_this.students.length === 0) {
          Students.getList().then(function(students) {
            _this.students = _this.students.concat(students);
          });
        } else {
          _this.students = [];
        }
      };

      this.toggleStudent = function(student) {
        student.get().then(function(studentData) {
          _this.student = studentData;
        });
      };

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
