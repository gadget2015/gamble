mkdir tmp
xcopy /s D:\NetBeansProjects\Gamble\build\classes tmp
cd tmp
mkdir properties
cd ..
xcopy /s properties tmp\properties
cd tmp
jar -cvf ../gamble.jar org properties
cd ..
rmdir /s /q tmp
rem pwd=kamera och alla jar-filer måste signeras med samma certifikat.
jarsigner -keystore robert_store -storepass kamera gamble.jar signGamble
jarsigner -keystore robert_store -storepass kamera documentation.jar signGamble
jarsigner -keystore robert_store -storepass kamera xerces.jar signGamble