'use strict';

define(['paw'], function(paw) {
  paw.directive('preventScroll', function () {
    return {
      link: link,
      restrict: 'A'
    };

    function link(scope, element, attrs) {
      // using jquery-mousewheel for cross-browser support
      element.on('mousewheel', function(e) {
        var direction = e.deltaY;
        if ((this.scrollHeight === (this.offsetHeight + this.scrollTop) && direction < 0) || (this.scrollTop === 0 && direction > 0)) {
          e.preventDefault();
        }
      });
    }
  });
});
