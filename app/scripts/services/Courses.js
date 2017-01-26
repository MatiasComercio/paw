'use strict';

define(['paw', 'services/AuthenticatedRestangular'], function(paw) {
  paw.service('Courses', ['AuthenticatedRestangular',
  function(AuthenticatedRestangular) {
    // this is needed as an array is expected from Restangular own methods
    // not needed if we are going to use Restangular's custom* methods
    var rest = AuthenticatedRestangular.withConfig(function(RestangularConfigurer) {
      RestangularConfigurer.addResponseInterceptor(
        function(data, operation, what, url, response, deferred) {
          return operation === 'getList' ? data.courses : data;
        }
      );
      RestangularConfigurer.setRestangularFields({
        id: 'courseId'
      });
    });

    // add own methods as follows
    rest.getList = function() {
      return rest.all('courses').getList();
    };

    return rest;
  }]);
});
