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
  }]);
});
