# Hotel-Management-System

### To run this application:
1. <b>build docker image of backend app:</b></br>
In /hotel-backend directory run:</br>
<b>_docker build -t hotel-app.jar ._</br></br>
2. run docker-compose:</b></br>
<b>_docker compose up -d_</b>

Application wiil start on localhost:8080 with PostgreSQL filled up with initial data.
To edit db structure edit _hotel-backend/create_db.sql_