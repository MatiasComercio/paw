# SGA - Sistema de Gestión Académica


[![Build Status](https://travis-ci.org/MatiasComercio/paw.svg?branch=development)](https://travis-ci.org/MatiasComercio/paw) [![codecov](https://codecov.io/gh/MatiasComercio/paw/branch/development/graph/badge.svg)](https://codecov.io/gh/MatiasComercio/paw) [![Code Climate](https://codeclimate.com/github/MatiasComercio/paw/badges/gpa.svg)](https://codeclimate.com/github/MatiasComercio/paw)

[![alt text][2]][1]

  [1]: https://sonarqube.com/dashboard?id=ar.edu.itba.paw%3Apaw
  [2]: http://www.qatestingtools.com/sites/default/files/tools_shortcuts/sonarqube-150px.png (hover text)

![Quality Gate](https://sonarqube.com/api/badges/gate?key=ar.edu.itba.paw:paw) ![Coverage Gate](https://sonarqube.com/api/badges/measure?key=ar.edu.itba.paw:paw&metric=coverage&blinking=true) ![Code Smells Gate](https://sonarqube.com/api/badges/measure?key=ar.edu.itba.paw:paw&metric=code_smells&blinking=true) ![Bugs Gate](https://sonarqube.com/api/badges/measure?key=ar.edu.itba.paw:paw&metric=bugs&blinking=true) ![Vulnerabilities](https://sonarqube.com/api/badges/measure?key=ar.edu.itba.paw:paw&metric=vulnerabilities&blinking=true)


## Git pre push hook

You can modify the [pre-push.sh](scripts/pre-push.sh) script to run different scripts before you `git push`. Then you need to run the following:

```bash
  chmod +x scripts/pre-push.sh
  ln -s ../../scripts/pre-push.sh .git/hooks/pre-push
```

You can skip the hook by adding `--no-verify` to your `git push`.

## Font Awesome configuration

Thanks: http://likesalmon.net/use-font-awesome-on-yeoman-angularjs-projects/

- Install Font Awesome:
  - `$ bower install font-awesome --save`
- Add Bootstrap to the top of `main.scss`:
  - `@import "bootstrap-sass/lib/bootstrap";`
- Change the font path (`font-awesome/scss/_variables.scss`):
  - `$fa-font-path: "/bower_components/font-awesome/fonts";`
- Add the Font Awesome CSS to `main.scss`:
  - `@import "font-awesome/scss/font-awesome";`

## Karma & Jasmine
We are using Karma alongside with Jasmine for our Angular unit tests.
It may be useful to install karma-cli globally.

`$ npm install -g karma-cli`

Then, you can run Karma simply by `karma` from anywhere and it will always run the local version.

For example, to run Angular tests, simply run

`$ karma start`

## Accounts
- Admin:
  - DNI: 38457012
  - Password: pass

- Student:
  - DNI: 12345687
  - Password: pass

## Authors
- Matías Nicolás Comercio Vázquez
- Gonzalo Exequiel Ibars Ingman
- Matías Mercado
- Juan Martín Pascale
