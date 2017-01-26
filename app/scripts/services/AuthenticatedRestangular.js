'use strict';

define(['paw', 'restangular', 'services/Authentication'], function(paw) {
  paw.factory('AuthenticatedRestangular', ['Restangular', 'Authentication',
  function(Restangular, Authentication) {
    return Restangular.withConfig(function(RestangularConfigurer) {
      // cannot use RestangularConfigurer.setDefaultHeaders() as it is called
      // only once and before the token is set
      // This requests the token each time a request is made
      // and in this instance it should already be set
      RestangularConfigurer.addFullRequestInterceptor(
        function(element, operation, route, url, headers, params, httpConfig) {
          return {
            element: element,
            params: params,
            headers: _.extend(headers, Authentication.getHeader()),
            httpConfig: httpConfig
          };
        }
      );
    });
  }]);
});
