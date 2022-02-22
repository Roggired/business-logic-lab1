# RESTful API Kickstoper v.1

## Application

POST /api/v1/application - create an application.  
BODY:  
{  
"projectName": "Test",  
"categoryId": 1,  
"subcategoryId": 1,  
"shortDescription": "Test test test",  
"targetBudget": 1000,  
"projectEndDate": 1645553175  
}
RESPONSE:  
4001 - project with such name already exists  
4002 - project end date is before the current date  
4004 - category or subcategory cannot be found  
5000 - internal server error

----

GET /api/v1/application - get all applications.
RESPONSE: list of  
{  
"id": 1,  
"applicationStatus": "NEW",  
"projectName": "Test",  
"category": "Video Game",  
"subcategory": "rpg"  
}  
RESPONSE:
5000 - internal server error

----