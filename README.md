# Kaspi Bank Project
Crediting decision making system for banks.

## Prerequisites

* Intellij IDEA or Eclipse
* Java 1.8
* Maven
* Mysql 5.x
* Apache tomcat 9.0.x

Ensure installation by checking environment variables

`echo $JAVA_HOME`

`echo $PATH`

## Installing
1. Clone the repo
2. Create MySQL database, configure db.properties file under /resources folder
3. Configure Tomcat web server in your IDE
4. Compile and deploy your app on Tomcat

db.properties template
```
db.driverClassName=com.mysql.jdbc.Driver
db.url=jdbc:mysql://127.0.0.1:3306/kaspilab
db.username=user
db.password=pwd
pool.initialSize=5
```

You also can change logging configuration file log4j.properties
```
# Root logger option
log4j.rootLogger=INFO, stdout, file

# Redirect log messages to console
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Target=System.out
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n

# Redirect log messages to a log file
log4j.appender.file=org.apache.log4j.RollingFileAppender
#outputs to Tomcat home
log4j.appender.file.File=${catalina.home}/logs/myapp.log
log4j.appender.file.MaxFileSize=5MB
log4j.appender.file.MaxBackupIndex=10
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n

```


## Database config
See [schema](/scheme.png) for ER diagram
or
Restore backup [backup](/kaspilab_backup.sql) 


## Author
* Yerassyl Bakytnuruly - [yerassyl](https://github.com/yeraassyl)
