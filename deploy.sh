#!/bin/bash
echo "===================================="
echo "===== Somnus deployment script ====="
echo "===================================="
echo 
echo "This script will prepare the Frontend and Backend files for deployment on the somnus server."
echo "You will be asked to enter your ssh username. Later, you will also be prompted to enter your public key password."
echo "Have fun deploying!"
echo

# Vars
RED='\033[0;31m'
GREEN='\033[0;32m'
WHITE='\033[0;37m'
SERVER_DIR="somnus.ddns.net"
SERVER_START_SECONDS=20
DIR="deploy/"

read -p 'ssh username: ' ssh_username

if [ ! -d "$DIR" ]; then
  echo "Creating directory ${DIR}..."
  mkdir $DIR
fi
###############################
echo
echo "===================================="
echo "Exporting Backend"
sleep 2
cd SomnusBackend

sudo mvn clean -Pproduction package

echo "Copying execution file from SomnusBackend/target to ${DIR}..."
cp target/somnus_server-v*.jar ../${DIR}

###############################
cd ../SomnusFrontEnd
echo
echo "===================================="
echo "Exporting Frontend"
sleep 2

npm run build

echo "Copying frontend files from SomnusFrontend/target to ${DIR}..."
cp -r dist/ServerWebsite ../${DIR}

cd ..

###############################
echo
echo "===================================="
echo "Updating server files"

# Prep server
ssh "$ssh_username"@"$SERVER_DIR" 'mkdir -p ~/services/deploy;pkill -9 -f somnus_server-v*'

scp -r deploy/ServerWebsite "$ssh_username@$SERVER_DIR":~/services/deploy
scp deploy/somnus_server-v*.jar "$ssh_username@$SERVER_DIR":~/services/SomnusBackend
# Frontend
ssh -t "$ssh_username@$SERVER_DIR" ' 
    cd /var/www/html/;
    sudo su -c "rm -f *.txt *.js *.png *.html *.css -r assets/; cp -R /home/somnus/services/deploy/ServerWebsite/* /var/www/html; service apache2 restart"; 
    rm -r ~/services/deploy'
# Backend
ssh "$ssh_username@$SERVER_DIR" 'cd ~/services/SomnusBackend; java -jar somnus_server-v*.jar &> somnus_backend.log &'

procs_before="$(ssh $ssh_username@$SERVER_DIR 'ps ax | grep somnus_server-v* | wc -l')"

echo "Waiting $SERVER_START_SECONDS seconds for backend to start"
for (( c=0; c<=$SERVER_START_SECONDS; c++ ))
do
    printf "."
    sleep 1
done
echo ""

procs_after="$(ssh $ssh_username@$SERVER_DIR 'ps ax | grep somnus_server-v* | wc -l')"
if [[ $((procs_after - procs_before)) == 0 ]] 
then
    echo "Backend Running"
    printf "${GREEN}Deployment successful!${WHITE}\n"
else
    echo "Backend not Running"
    printf "${RED}Deployment Failed!${WHITE}\n"
fi

echo "Current Server Status:"
ssh "$ssh_username@$SERVER_DIR" 'free -m | cat /proc/loadavg'

###############################
echo "Cleaning Up!"
rm -r deploy/