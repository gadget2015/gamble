var tar = require('tar');
tar.c(
  {
    gzip: true,
    file: 'spelbolagservice.tgz'
  },
  ['dist', 'node_modules']
).then(_ => { console.log('Tarball has been created.');});