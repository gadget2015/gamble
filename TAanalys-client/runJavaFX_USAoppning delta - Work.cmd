rem set JAVA_HOME=C:\Program Files (x86)\Java\jdk1.6.0_30
rem set Path=C:\Program Files (x86)\Java\jdk1.6.0_30\bin;%Path%
java -version
java -Dhttp.proxyHost=localhost -Dhttp.proxyPort=3128 -cp target\taclient-1.0.jar;lib\jfxrt.jar;lib\slf4j-api-1.6.1.jar;lib\slf4j-jdk14-1.6.1.jar org.robert.taanalys.ria.MainApplication