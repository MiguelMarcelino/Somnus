#! /bin/bash
cd SomnusBackend
sudo mvn clean -Pproduction package
cp target/somnus_server-v*.jar .
cd ..
