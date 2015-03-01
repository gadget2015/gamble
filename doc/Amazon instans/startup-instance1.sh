export CATALINA_BASE=/var/local/tomee_instance1
export JAVA_OPTS="-Xms25m -Xmx128m -XX:MaxPermSize=64m"
cd /usr/local/tomee/apache-tomee-plus-1.5.0/bin
./startup.sh
