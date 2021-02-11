# Somnus Server

### Using Database (MySQL)
- Create a new user on the mysql database: CREATE USER 'testuser'@'localhost' IDENTIFIED BY '12345678';
- Give permissions to new user: GRANT ALL PRIVILEGES ON *.* TO 'testuser'@'localhost' WITH GRANT OPTION;
- Flush mysql privileges: FLUSH PRIVILEGES;

### Using Database (Postgres)
- Create a new user on the postgresql database: CREATE USER somnususer WITH PASSWORD '12345678';

### Running Backend
- Run using the creaed script file: run_backend.sh
