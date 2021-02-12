# Somnus Server

## Testing environment

### Using Database (MySQL)
- Create a new user on the mysql database: CREATE USER 'testuser'@'localhost' IDENTIFIED BY '12345678';
- Give permissions to new user: GRANT ALL PRIVILEGES ON *.* TO 'testuser'@'localhost' WITH GRANT OPTION;
- Flush mysql privileges: FLUSH PRIVILEGES;

### Running Backend
- Run using the created bash script file: run_backend_testing.sh

## Deployment

### Backend
To deploy the backend just run the script <i>create_backend_executable.sh</i>. 
This will create a jar file in the folder SomnusBackend/ that can
be used to run the backend application. When running th application please
make sure that the folder deploy is placed in the same directory as the 
jar file. If you want to change this configuration please go the the
<i>application-production.properties</i> and change the 
<i>firebase.service.account.file.path</i> variable

### Frontend
To deploy the frontend you can go to the SomnusFrontEnd folder and run
<i>ng build --prod</i> to build the angular project for production. This will
create a <i>/dist<i> folder containing all the necessary files for deployment. 

