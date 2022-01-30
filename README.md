# api-security
This is a Student Portal API

## There are 3 roles
1. ADMIN
   * Can view student information
   * Can register student

2. ADMIN_TRAINEE
   * Can only view all students' information 
3. STUDENT
   * Can view his/her information
   * Can update some information
## Features
### Security using spring boot security
* using JWT authentication
* method base authorization (@PreAuthorize annotation)
* only loggedin student and admin have access to a student's information
* roles
### Using Mongo DB
* You can simply install mongo on your machine or
* Run mongo image from docker hub (***docker run -d -p 27017:27017 mongo***)
  * make sure to clean up after running an image. Docker leaves behind a lot of dump files
### Caching using redis
* cache student data using @Cacheable
* update student when updated using @CachePut
* delete value from cache when deleted from database using @CacheEvict
    
* Configuration
  * install redis on your local if you want to use locally
  * ***brew install redis***
  * run ***redis-cli*** to open shell
  * then ***MONITOR*** to see what's going on
### Monitoring using spring boot actuator
  * /health for health information
  * /info for information about
  * /chaosmonkey for actuator
### Mail using spring boot mail
  * Confiration
  
    * **I switch to AWS SES**
## References:
  * [AWS SES Configuration](https://mmafrar.medium.com/sending-emails-in-spring-boot-with-amazon-ses-smtp-interface-185fc7ca3725)
  * [Link to smtp Configuration](https://www.jotform.com/help/392-how-to-use-your-gmail-account-as-your-email-sender-via-smtp/)
  * [Allow less secure apps](https://www.google.com/settings/security/lesssecureapps)
  * [Amigoscode Spring Security](https://www.youtube.com/watch?v=her_7pa0vrg)

#### Database
I am using mongodb. At the beginning of this project, I used an installed version of \
mongodb on my local machine. \
On January 9th 2022, I learned to use mongodb container from docker. \
 * [Link to use mongodb container](https://www.thepolyglotdeveloper.com/2019/01/getting-started-mongodb-docker-container-deployment/)