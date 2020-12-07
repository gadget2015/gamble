rem pwd=kamera och alla jar-filer måste signeras med samma certifikat.
jarsigner -keystore robert_store.jks -storepass kamera ext-libs\xercesImpl-2.6.2.jar stryktipsalias
jarsigner -keystore robert_store.jks -storepass kamera target\tips-1.0.jar stryktipsalias
jarsigner -keystore robert_store.jks -storepass kamera ext-libs\xmlParserAPIs-2.0.0.jar stryktipsalias