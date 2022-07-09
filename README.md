# AMF (Apex Map Finder)

AMF is a project designed to improve the quality of life for Apex Legends players. By utilizing data from the Apex Legends API, AMF provides features not directly included in the game. Its main purpose is to keep players informed on the different map rotations for each game mode. More features are to come so stay tuned!

## Technologies

Frontend technology stack: 
- HTML5 
- CSS3
- Bootstrap 5
- Thymeleaf

Backend technology stack:
- Java
- Spring Boot
- MySQL 
- Heroku 


## Features

- View current and next maps in rotation
- A timer for each gamemode to see when the maps will change
- Player Search (WIP)
- Notification service which will notifiy users via email when their specified maps are coming up (WIP)

## Setup

(WIP)
To run this project, install it locally with git:
`git clone https://github.com/StonelnFocus/AMF`

You will also need to stand up a mySQL database 

Environment Variables:
```
APEX_API_KEY = Your own Apex Legends API key (see here to generate one: https://portal.apexlegendsapi.com/)
MYSQL_DB_USERNAME = Username for local mySQL database
MYSQL_DB_PASSWORD = Password for local mySQL database
```

## Project Status

View the current website at: https://apex-map-finder.herokuapp.com  
More features are continually being added, there is no set schedule for updates
