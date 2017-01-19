'use strict';

define(function() {
  // Add languages here
  var userLang, listOfSupportedLanguages = ['es'];

  // to avoid being called in non browser environments
  if (typeof navigator === 'object') {
    userLang = navigator.language || navigator.userLanguage;
    userLang = userLang.split('-')[0];
  }

  // Check the usage of `use` method to change language at runtime at:
  // https://angular-translate.github.io/

  // Set Spanish as default language
  if (userLang === undefined || listOfSupportedLanguages.indexOf(userLang) < 0) {
    userLang = 'es';
  }
  return {
    load: function (name, require, load) {
      require(['i18n/translations.' + userLang], function (value) {
        load(value);
        return value;
      });
    }
  };
});
