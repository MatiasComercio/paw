'use strict';

// Some of the following code was taken or adapted from:
// - https://github.com/mgonto/restangular/issues/98 (sanitizeRestangular)
// - http://www.bradoncode.com/blog/2015/07/25/tip-unit-test-promise-angular/ (promises)

define(['paw', 'restangular'], function(paw) {
  paw.service('specUtils', ['Restangular', '$q',
  function(Restangular, $q) {
    var _this = this;
    this.sanitizeRestangularAll = function (items) {
      return _.map(items, function (item) {
        return _this.sanitizeRestangularOne(item);
      });
    };

    // Remove all Restangular/AngularJS added methods in order to use
    // Jasmine toEqual between the retrieve resource and the model
    this.sanitizeRestangularOne = function (item) {
      return item.plain();
    };

    // handle promises easily
    var setupPromise = function(object, method, data, resolve) {
      spyOn(object, method).and.callFake(function() {
        return resolve === true ? $q.resolve(data) : $q.reject(data);
      });
    };

    this.resolvePromise = function(object, method, data) {
      return setupPromise(object, method, data, true);
    };

    this.rejectPromise = function(object, method, data) {
      return setupPromise(object, method, data, false);
    };
  }]);
});
