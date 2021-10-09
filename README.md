# api-security
This is a Student Portal API

##There are 3 roles:
1. ADMIN
   * Can view student information
   * Can register student
2. ADMIN_TRAINEE
   * Can only view all students' information 
3. STUDENT
   * Can view his/her information 
### Features
* Security using spring boot security
  * using JWT authentication
  * method base authorization (@PreAuthorize annotation)
    * only loggedin student and admin have access to a student's information
  * roles
* Caching using reedis
  * cache student data using @Cacheable
  * update student when updated using @CachePut
  * delete value from cache when deleted from database using @CacheEvict
* Monitoring using spring boot actuator
  * /health for health information
  * /info for information about
  * /chaosmonkey for actuator
