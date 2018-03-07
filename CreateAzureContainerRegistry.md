# Create Azure Container Registry (Private Docker Registry)

1. Logint to the Azure Portal  
At first, please login to the [Azure Portal](http://portal.azure.com "Azure Portal") ? After login to the portal, you can see following screen.
![Azure Login Screen](https://c1.staticflickr.com/5/4252/34052009354_9e7cac4c48_z.jpg "Azure Login Screen")

2. Create New "Azure Container Registry"  
In order to create the "Azure Container Registry", please push the "+ New" on the left side of panel? Then you can see following screen.  
![Azure Login Screen](https://c1.staticflickr.com/5/4199/34854811456_de1a3b98e8_z.jpg "Create new Azure Container Registry")

3. Search "Azure Container Registry"  
In order to create the "Azure Container Registry", please input **[Container]** on the input form of Search Field?  
![Search Azure Container Registry](https://c1.staticflickr.com/5/4251/34052009684_e4dffa05ff_z.jpg "Search Azure Container Registry")

4. Search Result  
After you enter the search field, you can see following screen. And you can confirm the **[Azure Container Registry]** on the result of the search.  
![Search Result](https://c1.staticflickr.com/5/4270/34052009954_3d87ca38ac_z.jpg "Search Result")

5. Create Azure Container Registry   
After push the link of the **[Azure Container Registry]**, you can see following screen. Please push the **"Create"** button?  
![Create Azure Container Registry](https://c1.staticflickr.com/5/4251/34052010074_45ede90967_z.jpg "Create Azure Container Registry")

6. Input information to create the Azure Container Registry  
After push the button, you can see the following screen. Then please fill out to the form? If you changed the “Admin user” from Disable to Enable, you can use the registry name as username and admin user access key as password to docker login to your container registry. So please change it to "Enable"?
Finally, please push the "create" button?  
![Input information to create the Azure Container Registry](https://c1.staticflickr.com/5/4221/34052010224_14830d0f38_z.jpg "Input information to create the Azure Container Registry")

7. Confirmation of the created Azure Container Registry  
After you created the "Azure Container Registry", please goto the resource group for it? Then you can see the following screen. Please push the link of the created "Azure Container Registry"?  
![Confirmation of the created Azure Container Registry](https://c1.staticflickr.com/5/4246/34052010404_caa4584a88_z.jpg "Confirmation of the created Azure Container Registry")

8. Confirmation of the created Azure Container Registry  
After push the link, you can see following screen. In this screen, you can manage your own Private Docker Registry. In this time, please confirm your account to access to the registry? Please push the link of the **"Access Keys"** on left side of the panel?  
![Confirmation of the created Azure Container Registry](https://c1.staticflickr.com/5/4248/34052010794_31cdc1ea50_z.jpg "Confirmation of the created Azure Container Registry")

9.  Confirm the Account to access to the Registry
After you push the link, you can see the following screen. Then please confirm your Username and Password.  
![](https://c1.staticflickr.com/5/4202/34052011004_5992f514a9_z.jpg "Confirm the Account to access to the Registry")

10. Reset your Password  
In the right side of the password field, you can see the recycle button. If you push the button, you can recreate the password.  
![Rest Password](https://c1.staticflickr.com/5/4228/34052011154_35104feb88_z.jpg "Reset your Password ")

11. Reset your Password Confirmation  
After push the button, following confirmation dialog will be showed on the screen. please push the **Yes** button?  
![](https://c1.staticflickr.com/5/4271/34052011474_d0ec45f0a6_z.jpg "Reset your Password Confirmation")

12. Password Change confirmation  
After push the "Yes" buttion, you can see the changed password on the screen.  
![Password Change confirmation](https://c1.staticflickr.com/5/4276/34854813086_feaef742d1_z.jpg "Password Change confirmation")

## Connection Informations
After created the above **Azure Container Registry**, you can access to the Registry Server with following informations.

|Container Registry Connection Info|Value |  
|---|---|  
|HostName：|yosshi.azurecr.io|  
|User Name：|yosshi|  
|Password：|[Password]|


## Push Docker image to Azure Container Registry

In this section, we will upload the Docker image from local environment to the Azure Container Service.

## Login to the Azure Container Registry
At first, please login to the Azure Container Registry by using **"docker login"** command like follows.  

```
local$ docker login -u yosshi -p "[password]"  yosshi.azurecr.io
Login Succeeded
```



## Create your own Docker image on local environment?
At first, please create your own Docker image on your local environment? Especially, in this time, I prefer to create the HTTP available service to confirm it more easily. In my example, I will show two  sample as nginx and Uber executable jar file example as follows.


### Example of nginx
If you would like to confirm the behavior by using Nginx, please create following Docker file?

```
FROM ubuntu:latest

# Install Nginx.
RUN \
  apt-get update && \
  apt-get install -y nginx && \
  rm -rf /var/lib/apt/lists/* && \
  echo "\ndaemon off;" >> /etc/nginx/nginx.conf && \
  chown -R www-data:www-data /var/lib/nginx

# Define mountable directories.
VOLUME ["/etc/nginx/sites-enabled", "/etc/nginx/certs", "/etc/nginx/conf.d", "/var/log/nginx", "/var/www/html"]

# Define working directory.
WORKDIR /etc/nginx

# Define default command.
CMD ["nginx"]

# Expose ports.
EXPOSE 80
EXPOSE 443
```

After created the Dockerfile, please execute the following command on the same directory with the Dockerfile? 

```
local$ docker build -t tyoshio2002/mynginx:1.0 .  
Sending build context to Docker daemon 10.24 kB
Step 1/7 : FROM ubuntu:latest
 ---> ebcd9d4fca80
Step 2/7 : RUN apt-get update &&   apt-get install -y nginx &&   rm -rf /var/lib/apt/lists/* &&   echo "\ndaemon off;" >> /etc/nginx/nginx.conf &&   chown -R www-data:www-data /var/lib/nginx
 ---> Using cache
 ---> 73a6473c70ce
Step 3/7 : VOLUME /etc/nginx/sites-enabled /etc/nginx/certs /etc/nginx/conf.d /var/log/nginx /var/www/html
 ---> Using cache
 ---> a0c8e84cf63a
Step 4/7 : WORKDIR /etc/nginx
 ---> Using cache
 ---> c3edc756b77b
Step 5/7 : CMD nginx
 ---> Using cache
 ---> a09cb843c082
Step 6/7 : EXPOSE 80
 ---> Using cache
 ---> c148e6d3a008
Step 7/7 : EXPOSE 443
 ---> Using cache
 ---> 411376d14702
Successfully built 411376d14702

local$ docker tag tyoshio2002/mynginx:1.0 yosshi.azurecr.io/tyoshio2002/mynginx:1.0  

local$ docker push yosshi.azurecr.io/tyoshio2002/mynginx  
The push refers to a repository [yosshi.azurecr.io/tyoshio2002/mynginx]
a68e1f63aedf: Pushed 
33f1a94ed7fc: Pushed 
b27287a6dbce: Pushed 
47c2386f248c: Pushed 
2be95f0d8a0c: Pushed 
2df9b8def18a: Pushed 
1.0: digest: sha256:cbe1e993987b67caead53e7b338fdc11e11566569fcb6fd71e42689aae9e15f0 size: 1569
```
After finished the above command, you can push the Docker image to the Azure Container Registry.

### Example of uber executable HelloWorld JAR
If you would like to confirm the behavior with executable JAR (like Spring-boot, WildFly Swarm, Payara Micro and so on: I used Payara Micro 4.1.2.172), please try follows?   
**Note: Please install maven and git before your execution?**

```
$ curl -L -o master.zip https://github.com/yoshioterada/DEIS-on-k8s-on-ACS/archive/master.zip
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   136    0   136    0     0     48      0 --:--:--  0:00:02 --:--:--    48
100 10825    0 10825    0     0   2846      0 --:--:--  0:00:03 --:--:-- 77730
$ ls
DEIS-on-k8s-on-ACS-master

$ cd DEIS-on-k8s-on-ACS-master/HelloWorld-Java/

$ mvn clean package
Picked up JAVA_TOOL_OPTIONS: -Dfile.encoding=UTF-8
[INFO] Scanning for projects...
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] Building HelloWorld 1.0
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ HelloWorld ---
[INFO] Deleting /private/tmp/DEIS-on-k8s-on-ACS-master/HelloWorld-Java/target
[INFO] 
[INFO] --- maven-dependency-plugin:2.6:copy (copy-payara-micro) @ HelloWorld ---
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ HelloWorld ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /private/tmp/DEIS-on-k8s-on-ACS-master/HelloWorld-Java/src/main/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ HelloWorld ---
[INFO] No sources to compile
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ HelloWorld ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /private/tmp/DEIS-on-k8s-on-ACS-master/HelloWorld-Java/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:testCompile (default-testCompile) @ HelloWorld ---
[INFO] No sources to compile
[INFO] 
[INFO] --- maven-surefire-plugin:2.12.4:test (default-test) @ HelloWorld ---
[INFO] No tests to run.
[INFO] 
[INFO] --- maven-war-plugin:3.1.0:war (default-war) @ HelloWorld ---
[INFO] Packaging webapp
[INFO] Assembling webapp [HelloWorld] in [/private/tmp/DEIS-on-k8s-on-ACS-master/HelloWorld-Java/target/HelloWorld-1.0]
[INFO] Processing war project
[INFO] Copying webapp resources [/private/tmp/DEIS-on-k8s-on-ACS-master/HelloWorld-Java/src/main/webapp]
[INFO] Webapp assembled in [127 msecs]
[INFO] Building war: /private/tmp/DEIS-on-k8s-on-ACS-master/HelloWorld-Java/target/HelloWorld-1.0.war
[INFO] 
[INFO] --- exec-maven-plugin:1.6.0:exec (default) @ HelloWorld ---
Picked up JAVA_TOOL_OPTIONS: -Dfile.encoding=UTF-8
5 26, 2017 11:29:16 午後 fish.payara.micro.impl.UberJarCreator buildUberJar
情報: Building Uber Jar... HelloWorld-1.0.jar
5 26, 2017 11:29:19 午後 fish.payara.micro.impl.UberJarCreator buildUberJar
情報: Built Uber Jar /private/tmp/DEIS-on-k8s-on-ACS-master/HelloWorld-Java/target/HelloWorld-1.0.jar in 2174 (ms)
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 8.699 s
[INFO] Finished at: 2017-05-26T23:29:19+09:00
[INFO] Final Memory: 17M/304M
[INFO] ------------------------------------------------------------------------

$ ls
Dockerfile		nb-configuration.xml	pom.xml			src			target

$ cat Dockerfile 
FROM java:8-jdk-alpine
MAINTAINER Yoshio Terada

VOLUME /tmp

ADD ./target/HelloWorld-1.0.jar /app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""

RUN chmod 755 /app.jar
EXPOSE 8080 8181
ENTRYPOINT java -jar app.jar --autoBindHttp --autoBindSsl 


$ docker build -t tyoshio2002/helloworld:1.0 .  
Sending build context to Docker daemon 307.2 MB
Step 1/9 : FROM java:8-jdk-alpine
 ---> 3fd9dd82815c
Step 2/9 : MAINTAINER Yoshio Terada
 ---> Using cache
 ---> c4444b169e40
Step 3/9 : VOLUME /tmp
 ---> Using cache
 ---> 131f1de1869e
Step 4/9 : ADD ./target/HelloWorld-1.0.jar /app.jar
 ---> 06208ff32bc8
Removing intermediate container 860c895fdc42
Step 5/9 : RUN sh -c 'touch /app.jar'
 ---> Running in c6a7c96cdca9
 ---> 93ace422ae52
Removing intermediate container c6a7c96cdca9
Step 6/9 : ENV JAVA_OPTS ""
 ---> Running in d7206e00a7b4
 ---> 2dc808ed4e46
Removing intermediate container d7206e00a7b4
Step 7/9 : RUN chmod 755 /app.jar
 ---> Running in ad26df0a6457
 ---> ed6e5716b308
Removing intermediate container ad26df0a6457
Step 8/9 : EXPOSE 8080 8181
 ---> Running in 17091fe8c5ce
 ---> 1375ca61053b
Removing intermediate container 17091fe8c5ce
Step 9/9 : ENTRYPOINT java -jar app.jar --autoBindHttp --autoBindSsl
 ---> Running in 330916ced0a5
 ---> 62fa29474f03
Removing intermediate container 330916ced0a5
Successfully built 62fa29474f03

$ docker tag tyoshio2002/helloworld:1.0 yosshi.azurecr.io/tyoshio2002/helloworld:1.0

$ docker push yosshi.azurecr.io/tyoshio2002/helloworld
68f69cbe48b9: Pushed 
706d06e42c55: Pushed 
9dafc64da18d: Pushed 
a1e7033f082e: Pushed 
78075328e0da: Pushed 
9f8566ee5135: Pushed 
1.0: digest: sha256:5ae7c1d2deb69537cb89f716a1c77a1c49b0b3e3f0493109de04d6645b5b304a size: 1586
```



Finished the above command, you can push the Docker image to the Azure Container Registry.  

## Confrim the uploaded Docker images on Azure Container Registry

After uploaded the Docker images to the Azure Docker Registry on Azure Portal, you can see the uploaed image like follows.  

![Pushed Docker Image to ACR](https://c1.staticflickr.com/5/4221/34094612943_ec5f8d1b5e_z.jpg "Pushed Docker Image to ACR")

