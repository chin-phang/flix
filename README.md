# flix

```
# build docker image
docker build -t rhb/flix .

# run MySQL first
docker-compose -f ./docker-compose.yml up -d db

# then run movie app
docker-compose -f ./docker-compose.yml up -d app

# go to API documentation
http://localhost:8080/swagger-ui.html
```
