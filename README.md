# Classified ads (Baraholka)

Classified ads for any kind of advert (only back-end) with full soft delete for ads.

Technology:
- Spring Security
- Spring Data
- RESTful web service 
- Flyway (for sql)
- Swagger
- Microservice Architecture 
- Maven


To start app:
 1. Install PostgreSQL
 2. Create database schema the same as in application.yml file
 3. Run sql with admin permission "CREATE EXTENSION pgcrypto" (need for using UUID)
 4. Run flyway maven plugin from data module to migrate DB data
 5. package both module using maven
 6. rut them by: $ java -jar app_name.jar
 7. get token by: $ curl -X POST -vu web:web http://localhost:8000/oauth/token  -H "Accept: application/json" -d "grant_type=password&username=admin@kramar.com&password=12345"
 8. call REST with access_toke like:
    
    http://localhost:8080/adverts
    
    GET /adverts HTTP/1.1
    Host: localhost:8080
    Content-Type: application/json
    Authorization: Bearer 3ef68ea7-77a7-446e-bbdd-8d26ea252c81
    Cache-Control: no-cache
    Postman-Token: 362e61b8-2bbe-7de1-4d88-8a97a63e28e2

 9. all rest method you can find on swagger:
    http://localhost:8080/swagger-ui.html
    

REST have pagination (from Spring Data Framework), for example:
http://localhost:8080/adverts?size=10&number=0 


App have profiling for data module. 
You can enable it in application.yml file in property "profiling".
To profiling class you need add annotation "@Profiling" to class like it done on AdvertServiceImpl.class


App have full text search with pagination (more details in javadoc)
To try call:
http://localhost:8080/adverts/search?text=blue car&operator=AND&size=10&page=0



