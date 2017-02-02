'use strict';

define(['paw'], function(paw) {
  paw.service('apiResponses', [
    function() {
      var _this = this;

      // Authentication header
      this.authHeader = {'x-auth-token': _this.token};

      // login responses
      // login token taken from: https://scotch.io/tutorials/the-anatomy-of-a-json-web-token
      this.token = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzY290Y2guaW8iLCJleHAiOjEzMDA4MTkzODAsIm5hbWUiOiJDaHJpcyBTZXZpbGxlamEiLCJhZG1pbiI6dHJ1ZX0.03f329983b86f7d9a9f5fef85305880101d5e302afafa20154d094b229f7577';

      this.invalidLoginResponse = {
        data: 'Login with privileges required',
        status: 401,
        statusText: 'Unauthorized'
      };

      // courses responses
      this.courses = [
        {
          'courseId': '72.03',
          'credits': 3,
          'name': 'Introducción a Informática',
          'semester': 1
        },
        {
          'courseId': '94.21',
          'credits': 6,
          'name': 'Formacion General I',
          'semester': 5
        }
      ];

      this.course = {
        'courseId': '72.03',
        'credits': 3,
        'name': 'Introducción a Informática',
        'semester': 1
      };


      // students responses
      this.students = [
        {
          'docket': 5,
          'firstName': 'Nicole',
          'lastName': 'Carabajal'
        },
        {
          'docket': 3,
          'firstName': 'Esteban',
          'lastName': 'Fuentes'
        },
        {
          'docket': 2,
          'firstName': 'Florencia Camila',
          'lastName': 'Gomez'
        },
        {
          'docket': 4,
          'firstName': 'Lucia',
          'lastName': 'Perez Lombardi'
        },
        {
          'docket': 6,
          'firstName': 'Juanasa',
          'lastName': 'Rodriguez'
        },
        {
          'docket': 10,
          'firstName': 'Nicolas',
          'lastName': 'Vazquez'
        }
      ];

      this.student = {
        'docket': 5,
        'firstName': 'Nicole',
        'lastName': 'Carabajal'
      };

      // admins responses
      this.admins = [
        {
          'dni': 38457013,
          'firstName': 'Matias Nicolas',
          'lastName': 'Comercio Vazquez'
        },
        {
          'dni': 12345686,
          'firstName': 'Gonzalo Exequiel',
          'lastName': 'Ibars Ingman'
        }
      ];

      this.admin = {
        'dni': 38457013,
        'firstName': 'Matias Nicolas',
        'lastName': 'Comercio Vazquez'
      };

      this.currentAdmin = {
        'birthday': '1995-06-10',
        'dni': 38457013,
        'email': 'a38457012@bait.edu.ar',
        'firstName': 'Matias Nicolas',
        'genre': 'M',
        'lastName': 'Comercio Vazquez',
        'role': 'ADMIN',
        'address': {
          'city': 'CABA',
          'country': 'Argentina',
          'door': '',
          'neighborhood': 'Puerto Madero',
          'number': 399,
          'street': 'E. Madero'
        },
        'authorities': {
          students: true,
          courses: true,
          admins: true
        },
        profileUrl: '#!/admins/38457013'
      };
      this.currentAdmin.fullName = this.currentAdmin.firstName + ' ' + this.currentAdmin.lastName;
    }
  ]);
});
