
<div>
    <div align="center">
<<<<<<< HEAD
        <h1>Somnus Server</h1>
=======
        <h1>Somnus</h1>
>>>>>>> f9987bc051a8ac6e953321d086a1102e98dd751e
        <img src="https://drive.google.com/thumbnail?id=1qt9so1vEwYIKRboanCCEfbGqxpCz3n3p" style="filter: invert(1); height: 100pt;">
        <h4 style="width: 80%; margin: 0 auto">This is the GitHub repository for the Somnus website. Head over to our website to see more of our content.</h4>
    </div>
    <hr>
</div>

## Testing environment

### Using Database (MySQL)
#### Testing
- Create a new user on the mysql database: CREATE USER 'testuser'@'localhost' IDENTIFIED BY 'yourpassword';
- Give permissions to new user: GRANT ALL PRIVILEGES ON *.* TO 'testuser'@'localhost' WITH GRANT OPTION;
- Flush mysql privileges: FLUSH PRIVILEGES;
- Create Database: CREATE DB somnustestdb;
- Dont forget to change the password in the <i>application-dev.properties </i> file

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

<div align="center">
    <hr>
    <h4 style="width: 80%; margin: 0 auto">The Somnus Team</h4>
    <a href="https://somnus.ddns.net/team" target="_blank">
        <img src="https://img.icons8.com/material-sharp/96/ffffff/user-group-man-woman.png" style="height: 50pt"/>
    </a> 
</div>
