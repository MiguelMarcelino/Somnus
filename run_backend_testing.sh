#! /bin/bash
cd SomnusBackend
sudo mvn clean -Pdev spring-boot:run
# uncommend next lin and comment previous one if the intent
# is to run on production database
# sudo mvn clean spring-boot:run
sudo fuser -k 3000/tcp
cd ..
