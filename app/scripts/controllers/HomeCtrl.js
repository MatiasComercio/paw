'use strict';

define(['paw',
'services/navDataService',
'services/Students',
'services/Courses'],
function(paw) {
  paw.controller('HomeCtrl',
    ['navDataService', 'Students', 'Courses', 'Paths',
    function(navDataService, Students, Courses, Paths) {
      var _this = this;

      this.fetchCourse = function() {
        var course = {
          courseId: '123',
          name: 'Introducción a la informática y todoestechoclodetextoparaverqueandebien'
        };
        course.actions = Paths.getCourseActions(course, _this.user);
        return course;
      };

      this.fetchAdmin = function() {
        var admin = {
          dni: 38457013,
          firstName: '[ADMIN] Matías Nicolás',
          lastName: 'Comercio Vázquez asdasdasdasdasdasdasdasdadasdasdas'
        };
        return admin;
      };

      this.fetchStudent = function() {
        var student = {
          dni: '38457013',
          docket: 5,
          firstName: 'Matías Nicolás',
          lastName: 'Comercio Vázquez asdasdasdasdasdasdasdasdadasdasdas'
        };
        return student;
      };

      function fetchData() {
        // this should be called on /admins/:dni views
        _this.admin = _this.fetchAdmin();

        // should be removed then
        var admin = _this.admin;
        if (admin) {
          admin.fullName = admin.firstName + ' ' + admin.lastName;
        }

        // this should be called on /students/:docket views
        _this.student = _this.fetchStudent();

        // should be removed then
        var student = _this.student;
        if (student) {
          student.fullName = student.firstName + ' ' + student.lastName;
        }

        // this should be called on /courses/:docket views
        _this.course = _this.fetchCourse();
      };
      fetchData();

      var getUserCallback = function() {
        // get the user
        _this.user = navDataService.get('user');
        if (!_this.user) {
          return; // nothing to set
        }

        var subSidebar = {};

        // should be set on /admins/:dni views
        subSidebar.admin = _this.admin;
        subSidebar.admin.actions = Paths.getAdminActions(_this.admin, _this.user);

        // should be set on /admins/:dni views
        subSidebar.student = _this.student;
        subSidebar.student.actions = Paths.getStudentActions(_this.student, _this.user);

        // should be set on /admins/:dni views
        subSidebar.course = _this.course;
        subSidebar.course.actions = Paths.getCourseActions(_this.course, _this.user);

        // register the current sidebar
        navDataService.set('subSidebar', subSidebar);
      };
      navDataService.registerObserverCallback('user', getUserCallback);
      getUserCallback();
    }]
  );
});
