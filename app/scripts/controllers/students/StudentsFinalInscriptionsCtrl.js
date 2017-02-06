'use strict';

define(['paw','services/Students','services/Paths',
'controllers/modals/FinalEnrollController',
'controllers/modals/FinalUnenrollController'],
function(paw) {
  paw.controller('StudentsFinalInscriptionsCtrl',
  ['$routeParams', 'Students', '$log', 'Paths',
  function($routeParams, Students, $log, Paths) {
    var _this = this;
    var docket = $routeParams.docket;

    Students.get(docket).then(function(student) {
      _this.student = student;
      Students.setOnSubSidebar(student);

      _this.student.getList('finalInscriptions').then(function(finalInscriptions) {
        _this.student.finalInscriptionsTaken = finalInscriptions;
      });

      _this.student.all('finalInscriptions').customGET('available').then(function(finalInscriptions) {
        _this.student.finalInscriptionsAvailable = finalInscriptions;
      });
    });

    this.getFinalInscriptionPath = function(courseId, finalInscriptionId) {
      return Paths.get().courses({courseId: courseId}).finals({inscriptionId: finalInscriptionId}).path;
    };
  }]);
});
