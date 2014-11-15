
register

> curl -X POST -H "Cache-Control: no-cache" -H "Postman-Token: 8ae250c2-8e3b-57df-4c27-c4472522b69c" -d '' http://api.gospry.com:8080/register/436802118976

activate

> curl -X POST -H "Cache-Control: no-cache" -H "Postman-Token: 305d459f-87bb-5e44-b99c-b26487a412e1" -d '' http://api.gospry.com:8080/activate/436802118976/1234

create event

> curl -X POST -H "Authorization: Basic NDM2ODAyMTE4OTc2OjEyMzQ1Ng==" -H "Content-Type: application/json" -H "Cache-Control: no-cache" -H "Postman-Token: 5919eda1-c9d8-a519-b5c0-4bc861757576" -d '{"start_time":"1415827547072","description":"1234","duration":"180","max_attending":"5","min_attending":"0", "price":"0","description":"Das ist bis zu 140 Zeichen lang", "isPublic":"true","keywords":["kw1","kw2"]}' http://api.gospry.com:8080/happening/

create user

> curl -X POST -H "Cache-Control: no-cache" -H "Postman-Token: dde619d6-61f5-1809-b5b9-30cc43f74f32" -d '' http://api.gospry.com:8080/register/436802118976

invite user

> curl -X POST -H "Authorization: Basic NDM2ODAyMTE4OTc2OmQ3ZWY3ODI4MWRiYjkzYzE=" -H "Content-Type: application/json" -H "Cache-Control: no-cache" -H "Postman-Token: 037f8c8c-1605-e215-8318-93210b0e3b54" -d '{"status":"INVITED"}' http://api.gospry.com:8080/happening/1/436802118978

invites of event with id 1

> curl -X GET -H "Authorization: Basic NDM2ODAyMTE4OTc2OmQ3ZWY3ODI4MWRiYjkzYzE=" -H "Content-Type: application/json" -H "Cache-Control: no-cache" -H "Postman-Token: c72a27d1-44b9-6433-2f7c-7dec37a91925" http://api.gospry.com:8080/happening/1/invited

list all events

> curl -X GET -H "Authorization: Basic NDM2ODAyMTE4OTc2OmQ3ZWY3ODI4MWRiYjkzYzE=" -H "Cache-Control: no-cache" -H "Postman-Token: d46e1d18-e953-758f-753d-77e4ca143f01" http://api.gospry.com:8080/happening/

list event with id 1

> curl -X GET -H "Authorization: Basic NDM2ODAyMTE4OTc2OjEyMzQ1Ng==" -H "Content-Type: application/json" -H "Cache-Control: no-cache" -H "Postman-Token: d2b95f6b-0f4c-fa67-764a-9ddbadf65039" http://api.gospry.com:8080/happening/1

show all users

> curl -X GET -H "Authorization: Basic NDM2ODAyMTE4OTc2OjEyMzQ1Ng==" -H "Cache-Control: no-cache" -H "Postman-Token: 13a995ba-56f5-0f94-0b53-53cb10137390" http://api.gospry.com:8080/users