# SGA - Sistema de Gestión Académica


[![Build Status](https://travis-ci.org/MatiasComercio/paw.svg?branch=development)](https://travis-ci.org/MatiasComercio/paw) [![Coverage Status](https://coveralls.io/repos/github/MatiasComercio/paw/badge.svg)](https://coveralls.io/github/MatiasComercio/paw) [![Code Climate](https://codeclimate.com/github/MatiasComercio/paw/badges/gpa.svg)](https://codeclimate.com/github/MatiasComercio/paw)

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
