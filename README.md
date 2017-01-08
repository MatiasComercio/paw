# SGA - Sistema de Gestión Académica

[![Build Status](https://travis-ci.org/MatiasComercio/paw.svg?branch=development)](https://travis-ci.org/MatiasComercio/paw)

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

