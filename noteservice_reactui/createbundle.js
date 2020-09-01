var tar = require('tar');
tar.c(
  {
    gzip: true,
    file: 'noteservice_ui.tgz'
  },
  ['build']
).then(_ => { console.log('Tarball has been created.');});