var tar = require('tar');
tar.c(
  {
    gzip: true,
    file: 'webserver.tgz'
  },
  ['src', 'node_modules']
).then(_ => { console.log('Tarball has been created.');});