'use strict';

define(
  ['paw',
  'services/memoryFactory',
  'services/Authentication',
  'services/AuthenticatedRestangular'],
  function(paw) {
    paw.service('navDataService',
    ['memoryFactory',
    'Authentication',
    'AuthenticatedRestangular',
    'Paths',
    function(memoryFactory, Authentication, AuthenticatedRestangular, Paths) {
      var memory = memoryFactory.newMemory();
      var userKey = 'user';

      var rest = AuthenticatedRestangular.withConfig(function(RestangularConfigurer) {
        RestangularConfigurer.extendModel('user', function(user) {
          user.fullName = user.firstName + ' ' + user.lastName;

          user.authorities = {
            students: true,
            courses: true
          };

          if (user.role === 'ADMIN') {
            user.authorities.admins = true;
            user.profileUrl = Paths.get().admins(user).path;
          } else { // role === 'STUDENT'
            user.authorities.admins = false;
            user.profileUrl = Paths.get().students(user).path;
          };

          return user;
        });
      });

      memory.fetchUser = function() {
        var user = memory.get(userKey);
        if (user !== undefined || !Authentication.isLoggedIn()) {
          // user already fetched or not yet logged in => nothing to fetch
          return;
        }

        // user === undefined but it is logged in ==> we should fetch the user for the first time
        //
        // This case can be reached when the user closes all navigation tabs, but token cookie
        // is still stored, and the user directly access an endpoint rather than '/login'

        // fetch the user
        rest.one('user').get().then(function(fetchedUser) {
          // and set it on the navDataService
          memory.set(userKey, fetchedUser);
        });
      };

      return memory;
    }]);
  }
);
