#! /bin/bash
cd SomnusBackend
DIR="deploy/"

sudo mvn clean -Pproduction package
if [ ! -d "$DIR" ]; then
  echo "Creating directory SomnusBackend/${DIR}..."
  mkdir $DIR
fi
echo "Copying execution file to SomnusBackend/${DIR}..."
cp target/somnus_server-v*.jar deploy/
cd ..
