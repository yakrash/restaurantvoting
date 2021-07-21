[![Codacy Badge](https://app.codacy.com/project/badge/Grade/9d2a6580b752440f9676825c57a1658b)](https://www.codacy.com/gh/yakrash/restaurantvoting/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=yakrash/restaurantvoting&amp;utm_campaign=Badge_Grade)

Design and implement a REST API using Hibernate/Spring-Boot without frontend.

The task is:

Build a voting system for deciding where to have lunch.

    2 types of users: admin and regular users
    Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
    Menu changes each day (admins do the updates)
    Users can vote on which restaurant they want to have lunch at
    Only one vote counted per user
    If user votes again the same day:
        If it is before 11:00 we assume that he changed his mind.
        If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.

Stack:
Maven
Spring Boot
Spring Data JPA (Hibernate)
Spring Security
Spring Cache + Caffeine
REST
JUnit 5

[Curl commands to test API:] http://localhost:8080/api/menu
-----[Menu:] 
get menu of restaurant with id=1 for today
curl -s http://localhost:8080/api/menu/1/ --user user@gmail.com:password

get all menus for today
curl -s http://localhost:8080/api/menu/ --user user@gmail.com:password

add menu today or next day if voting time is end. Restaurant with id=2
curl -s -i -X POST -d '[{"name":"New dish","priceInDollars":10},{"name": "Dish2","priceInDollars":30}]' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/api/menu/2/ --user admin@bzz.su:admin

add menu to restaurant with id=2 for different dates
curl -s -i -X POST -d '[{"name":"New dish only","priceInDollars":400, "date":"2021-07-30"}]' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/api/menu/2/every-day --user admin@bzz.su:admin

update dish with id=4
curl -s -X PUT -d '{"name": "New dish222", "priceInDollars": 200, "date":"2021-07-30"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/api/menu/dish/4 --user admin@bzz.su:admin

delete dish with id=3
curl -s -X DELETE http://localhost:8080/api/menu/dish/3 --user admin@bzz.su:admin

update dish with id=3 INVALID
curl -s -X PUT -d '{"priceInDollars": 200, "date":"2021-07-30"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/api/menu/dish/3 --user admin@bzz.su:admin

add menu INVALID
curl -s -i -X POST -d '[{"name":"New dish only","priceInDollars":1000}]' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/api/menu/2/every-day --user admin@bzz.su:admin

-----[Restaurants:]  http://localhost:8080/api/restaurant
get all restaurants
curl -s http://localhost:8080/api/restaurant --user user@gmail.com:password

get by id=1
curl -s http://localhost:8080/api/restaurant/1 --user user@gmail.com:password

add restaurant
curl -s -i -X POST -d '{"name": "NewRestaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/api/restaurant/ --user admin@bzz.su:admin

update restaurant with id=1
curl -s -X PUT -d '{"name": "UpdateRestaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/api/restaurant/1 --user admin@bzz.su:admin

delete restaurant with id=1
curl -s -X DELETE http://localhost:8080/api/restaurant/1 --user admin@bzz.su:admin

delete restaurant with user account (status 403)
curl -s -X DELETE http://localhost:8080/api/restaurant/1 --user user@gmail.com:password

-----[Vote:]  http://localhost:8080/api/vote
post vote of restaurant id=2
curl -s -i -X POST http://localhost:8080/api/vote/2 --user user@gmail.com:password

update vote of current day
curl -s -i -X PUT http://localhost:8080/api/vote/3 --user user@gmail.com:password

get restaurants with vote today (Sort Vote)
curl -s http://localhost:8080/api/vote/all --user user@gmail.com:password

post vote of restaurant id=2 (INVALID, if you are voting today)
curl -s -i -X POST http://localhost:8080/api/vote/2 --user user@gmail.com:password
