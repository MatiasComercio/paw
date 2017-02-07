/*
code adapted from:
http://stackoverflow.com/questions/30149699/bootstrap-size-variable-in-angular-controllers-services
http://stackoverflow.com/questions/31622673/angularjs-watch-window-resize-inside-directive
*/

'use strict';

define(['paw', 'services/memoryFactory'], function(paw) {
  paw.service('windowSize', ['memoryFactory', '$window', '$rootScope',
  function(memoryFactory, $window, $rootScope) {
    var memory = memoryFactory.newMemory();

    setSize();
    angular.element($window).bind('resize', function() {
      // necessary to call $digest as $window is outside angular's scope
      $rootScope.$apply(function() {
        setSize();
      });
    });

    function setSize() {
      if ($window.innerWidth >= 1200) {
        setMemory('lg', false);
      } else if ($window.innerWidth < 1200 && $window.innerWidth >= 992) {
        setMemory('md', false);
      } else if ($window.innerWidth < 992 && $window.innerWidth >= 768) {
        setMemory('sm', false);
      } else { // $window.innerWidth < 768
        setMemory('xs', true);
      }
    }

    function setMemory(bootstrapWindowSize, windowIsMobile) {
      memory.set('bootstrapWindowSize', bootstrapWindowSize);
      memory.set('windowIsMobile', windowIsMobile);
    }

    return memory;
  }]);
});
