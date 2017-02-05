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

      function areInscriptionsEnabled(user) {
        var disableInscriptionsDefined = user.authorities.find(function(a) {
          return a === 'ADD_INSCRIPTION';
        });
        return disableInscriptionsDefined ? true : false;
      }

      var rest = AuthenticatedRestangular.withConfig(function(RestangularConfigurer) {
        RestangularConfigurer.extendModel('user', function(user) {
          user.fullName = user.firstName + ' ' + user.lastName;

          if (user.authorities === undefined) {
            return user;
          }

          user.authorities.students = true;
          user.authorities.courses = true;

          var inscriptionsEnabled = areInscriptionsEnabled(user);
          memory.set('inscriptionsEnabled', inscriptionsEnabled);

          user.authorities.viewInscriptions = inscriptionsEnabled;
          user.authorities.addInscription = inscriptionsEnabled;
          user.authorities.deleteInscription = inscriptionsEnabled;

          if (user.role === 'ADMIN') {
            user.admin = true;
            user.authorities.admins = true;
            user.profileUrl = Paths.get().admins(user).path;
            user.locationUrl = Paths.get().admins(user).absolutePath();
            user.authorities.disableInscriptions = inscriptionsEnabled;
          } else { // role === 'STUDENT'
            user.student = true;
            user.authorities.admins = false;
            user.profileUrl = Paths.get().students(user).path;
            user.locationUrl = Paths.get().students(user).absolutePath();
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
