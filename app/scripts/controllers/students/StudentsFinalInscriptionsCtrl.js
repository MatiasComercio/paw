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
      return Paths.get().courses({courseId: courseId}).path;
    };

		this.otherInscriptionTaken = function(inscription) {
			var otherInscriptionDefined = _this.student.finalInscriptionsTaken.find(function(takenInscription) {
				return takenInscription.courseId === inscription.courseId;
			});
			return otherInscriptionDefined ? true : false;
		};

		this.isAvailable = function(inscriptionInfo) {
			var inscription = inscriptionInfo.inscription;
			if (!inscriptionInfo.available) {
				return false;
			}
			if (inscription.vacancy === inscription.maxVacancy || inscription.state === 'CLOSED') {
				return false;
			}
			if (this.otherInscriptionTaken(inscription)) {
				return false;
			}
			return true;
		};

		this.getErrorMessage = function(inscriptionInfo) {
			var inscription = inscriptionInfo.inscription;
			if (!inscriptionInfo.available) {
				return 'i18nCorrelativesMissing';
			}
			if (inscription.state === 'CLOSED') {
				return 'i18nFinalInscriptionClosed';
			}
			if (inscription.vacancy === inscription.maxVacancy) {
				return 'i18nMaxVacancyReached';
			}
			if (this.otherInscriptionTaken(inscription)) {
				return 'i18nOtherInscriptionEnrolled';
			}
			return 'i18nCorrelativesConflict';
		};

  }]);
});
