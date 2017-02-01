'use strict';

define(['paw',
'services/navDataService',
'services/Students',
'services/Courses'],
function(paw) {
  paw.controller('HomeCtrl',
    ['navDataService', 'Students', 'Courses', 'Paths',
    function(navDataService, Students, Courses, Paths) {
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
        var course = {
          courseId: '123',
          name: 'Introducción a la informática y todoestechoclodetextoparaverqueandebien'
        };
        course.actions = Paths.getCourseActions(course);
        return course;
      };

      this.fetchAdmin = function() {
        var admin = {
          dni: '38457013',
          firstName: '[ADMIN] Matías Nicolás',
          lastName: 'Comercio Vázquez asdasdasdasdasdasdasdasdadasdasdas'
        };
        admin.actions = Paths.getAdminActions(admin);
        return admin;
      };

      this.fetchStudent = function() {
        var student = {
          dni: '38457013',
          docket: '55',
          firstName: 'Matías Nicolás',
          lastName: 'Comercio Vázquez asdasdasdasdasdasdasdasdadasdasdas'
        };
        student.actions = Paths.getStudentActions(student);
        return student;
      };



      function fetchData() {
        return {
          admin: _this.fetchAdmin(),
          student: _this.fetchStudent(),
          course: _this.fetchCourse()
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
