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
### Features
#### Security using spring boot security
* using JWT authentication
* method base authorization (@PreAuthorize annotation)
* only loggedin student and admin have access to a student's information
* roles
#### Caching using redis
* cache student data using @Cacheable
* update student when updated using @CachePut
* delete value from cache when deleted from database using @CacheEvict
    
* Configuration
  * install redis on your local if you want to use locally
  * ***brew install redis***
  * run ***redis-cli*** to open shell
  * then ***MONITOR*** to see what's going on
#### Monitoring using spring boot actuator
  * /health for health information
  * /info for information about
  * /chaosmonkey for actuator
#### Mail using spring boot mail
  * Confiration
  * [Link to smtp Configuration](https://www.jotform.com/help/392-how-to-use-your-gmail-account-as-your-email-sender-via-smtp/)
  * [Allow less secure apps](https://www.google.com/settings/security/lesssecureapps)