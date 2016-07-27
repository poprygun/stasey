# Stasey

Command line utility to test connectivity to IBM MQ v. 7.5.0.6

Following dependencies were taken from Windows installation of MQ.

```bash
mvn install:install-file -Dpackaging=jar -Dfile=CL3Export.jar -DgroupId=com.ibm -DartifactId=CL3Export -Dversion=7.5.0.6
mvn install:install-file -Dpackaging=jar -Dfile=CL3Nonexport.jar -DgroupId=com.ibm -DartifactId=CL3NoExport -Dversion=7.5.0.6
mvn install:install-file -Dpackaging=jar -Dfile=com.ibm.mq.axis2.jar -DgroupId=com.ibm -DartifactId=com.ibm.mq.axis2 -Dversion=7.5.0.6
mvn install:install-file -Dpackaging=jar -Dfile=com.ibm.mq.commonservices.jar -DgroupId=com.ibm -DartifactId=com.ibm.mq.commonservices -Dversion=7.5.0.6
mvn install:install-file -Dpackaging=jar -Dfile=com.ibm.mq.headers.jar -DgroupId=com.ibm -DartifactId=com.ibm.mq.headers -Dversion=7.5.0.6
mvn install:install-file -Dpackaging=jar -Dfile=com.ibm.mq.jar -DgroupId=com.ibm -DartifactId=com.ibm.mq -Dversion=7.5.0.6
mvn install:install-file -Dpackaging=jar -Dfile=com.ibm.mq.jmqi.jar -DgroupId=com.ibm -DartifactId=com.ibm.mq.jmqi -Dversion=7.5.0.6
mvn install:install-file -Dpackaging=jar -Dfile=com.ibm.mq.jms.Nojndi.jar -DgroupId=com.ibm -DartifactId=com.ibm.mq.jms.Nojndi -Dversion=7.5.0.6
mvn install:install-file -Dpackaging=jar -Dfile=com.ibm.mq.pcf.jar -DgroupId=com.ibm -DartifactId=com.ibm.mq.pcf -Dversion=7.5.0.6
mvn install:install-file -Dpackaging=jar -Dfile=com.ibm.mq.soap.jar -DgroupId=com.ibm -DartifactId=com.ibm.mq.soap -Dversion=7.5.0.6
mvn install:install-file -Dpackaging=jar -Dfile=com.ibm.mq.tools.ras.jar -DgroupId=com.ibm -DartifactId=com.ibm.mq.tools.ras -Dversion=7.5.0.6
mvn install:install-file -Dpackaging=jar -Dfile=com.ibm.mqjms.jar -DgroupId=com.ibm -DartifactId=com.ibm.mqjms -Dversion=7.5.0.6
mvn install:install-file -Dpackaging=jar -Dfile=connector.jar -DgroupId=com.ibm -DartifactId=com.ibm.mq.connector -Dversion=7.5.0.6
mvn install:install-file -Dpackaging=jar -Dfile=dhbcore.jar -DgroupId=com.ibm -DartifactId=com.ibm.disthub2.dhbcore -Dversion=7.5.0.6
mvn install:install-file -Dpackaging=jar -Dfile=fscontext.jar -DgroupId=com.ibm -DartifactId=fscontext -Dversion=7.5.0.6
mvn install:install-file -Dpackaging=jar -Dfile=jms.jar -DgroupId=com.ibm -DartifactId=jms -Dversion=7.5.0.6
mvn install:install-file -Dpackaging=jar -Dfile=jndi.jar -DgroupId=com.ibm -DartifactId=jndi -Dversion=7.5.0.6
mvn install:install-file -Dpackaging=jar -Dfile=jta.jar -DgroupId=com.ibm -DartifactId=jta -Dversion=7.5.0.6
mvn install:install-file -Dpackaging=jar -Dfile=ldap.jar -DgroupId=com.ibm -DartifactId=ldap -Dversion=7.5.0.6
```