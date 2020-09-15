var tar = require('tar');
tar.c(
  {
    gzip: true,
    file: 'noteservice.tgz'
  },
  ['dist', 'node_modules']
).then(_ => { console.log('Tarball has been created.');});