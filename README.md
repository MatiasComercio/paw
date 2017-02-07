# SGA - Sistema de Gestión Académica

[![Build Status](https://travis-ci.org/MatiasComercio/paw.svg?branch=development)](https://travis-ci.org/MatiasComercio/paw)


<!-- TOC depthFrom:1 depthTo:3 withLinks:1 updateOnSave:1 orderedList:0 -->

- [SGA - Sistema de Gestión Académica](#sga-sistema-de-gestin-acadmica)
	- [Git pre push hook](#git-pre-push-hook)
	- [Dependencies for contributing on front-end app](#dependencies-for-contributing-on-front-end-app)
		- [Node.js & NPM](#nodejs-npm)
		- [Yeoman](#yeoman)
		- [Bower](#bower)
		- [Grunt](#grunt)
		- [Generator to build projects with Require + Angular](#generator-to-build-projects-with-require-angular)
		- [Ruby & Compass](#ruby-compass)
		- [Karma & Jasmine](#karma-jasmine)
	- [Build](#build)
	- [Accounts](#accounts)
	- [Authors](#authors)

<!-- /TOC -->

## Git pre push hook

You can modify the [pre-push.sh](scripts/pre-push.sh) script to run different scripts before you `git push`. Then you need to run the following:

```bash
  chmod +x scripts/pre-push.sh
  ln -s ../../scripts/pre-push.sh .git/hooks/pre-push
```

You can skip the hook by adding `--no-verify` to your `git push`.


## Dependencies for contributing on front-end app
### Node.js & NPM
#### Download
https://nodejs.org/en/
#### Installation guide
https://github.com/nodejs/help/wiki/Installation
##### Additional step
Add this line to your .bashrc
`export NODE_PATH=$NODE_PATH:/path/to/node/lib/node_modules`
##### Note
`npm install -g <package>` install on the globally npm source. Do not abuse about this. Prefer local installations over global ones.
### Yeoman
`npm install -g yo`
### Bower
`npm install -g bower`
### Grunt
`npm install -g grunt-cli`
### Generator to build projects with Require + Angular
Source: https://github.com/Monits/generator-angular-require-fullstack
#### Install all necesary dependencies
`npm install -g generator-angular-require-fullstack`

### Ruby & Compass
Necessary to run a grunt serve task: https://github.com/gruntjs/grunt-contrib-compass
- Download and install [Rbenv](https://github.com/rbenv/rbenv#installation).
- Download and install [Ruby-Build](https://github.com/rbenv/ruby-build#ruby-build).
- Install the appropriate Ruby version by running `rbenv install <version>` where `<version>` is the desired one (prefer the last stable one).
- Install compass gem: `gem install compass`


That's it! you should be able to start coding.

**Test your front-end app**
`grunt serve`

**Build your front-end app**
`grunt build`

### Karma & Jasmine
We are using Karma alongside with Jasmine for our Angular unit tests.
It may be useful to install karma-cli globally.

`$ npm install -g karma-cli`

Then, you can run Karma simply by `karma` from anywhere and it will always run the local version.

For example, to run Angular tests, simply run

`$ karma start`

## Build
For building the whole web app, just run

`mvn clean package -DurlPath:'/deploy_path'`

Examples:

  `mvn clean package -DurlPath=''` if deployng app to '/'

  `mvn clean package -DurlPath='/grupo1'` if deployng app to '/grupo1'

## Accounts
- Admin:
  - DNI: 38457012
  - Password: password01

- Student:
  - DNI: 12345687
  - Password: password01

## Authors
- Matías Nicolás Comercio Vázquez
- Gonzalo Exequiel Ibars Ingman
- Matías Mercado
- Juan Martín Pascale
