# [Projet TLC Fitness](tlc-tp1.pdf)

This is a working skeleton. However, it only returns dummy values and you must replace them by interacting with Google Datastore.
The only java file you need to update is `src/main/java/tlc/tracking/RunResource.java`. You will find `@FIXME` comments where you should add code.
Still, you are encouraged to read the whole project files.

## Google AppEngine related files

  * `/src/main/webapp/WEB-INF/appengine-web.xml` - you must edit the application to replace `tlcgae2` by your application id
  * `/src/main/webapp/WEB-INF/datastore-indexes.xml` - you must put your indexes here

## Running locally

### Avec Maven

```
mvn appengine:devserver
```

And go to http://127.0.0.1:8080

### Test Script

```
./test.sh http://localhost:8080/api/run lon true
```
lon : pour spécifier le nom de la variable de longitude
true : pour bloquer les requètes après l'ajout


### Avec son IDE et Tomcat

Télécharger tomcat
```bash
cd ~/opt/
wget http://mirrors.standaloneinstaller.com/apache/tomcat/tomcat-8/v8.5.37/bin/apache-tomcat-8.5.37.zip
unzip apache-tomcat-8.5.37.zip
mv apache-tomcat-8.5.37 tomcat
rm apache-tomcat-8.5.37.zip

# oui pas propre je sais
chmod 777 -R tomcat


```
Puis créer une configuration sur son IDE.\
Par exemple sur intellij Ultimate :
* Add Configuration
* \+
* tomcat server local
* name -> TLCFitness
* Configure
* tomcat home -> /home/<username\>/opt/tomcat
* tomcat base directory -> idem 
* valider
* Deployment
* \+
* tracking:war exploded
* OK

And go to http://127.0.0.1:8080




## Deploying to Google Cloud

```
mvn appengine::update
```

## Access to the deployed app

https://your-id.appspot.com/