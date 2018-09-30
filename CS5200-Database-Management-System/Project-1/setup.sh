#!/bin/bash
# chmod +x filename.sh
sudo apt update && sudo apt upgrade
sudo apt-get install -y vim
sudo apt-get install -y net-tools
# jdk8
sudo apt-get install -y openjdk-8-jdk
java -version
# setup Maven
sudo apt-get install -y maven
mvn -v
# setup heroku
sudo snap install -y --classic heroku
heroku login
# setup spring boot
wget https://repo.spring.io/release/org/springframework/boot/spring-boot-cli/2.0.4.RELEASE/spring-boot-cli-2.0.4.RELEASE-bin.tar.gz
tar -xzvf spring-boot-cli-2.0.4.RELEASE-bin.tar.gz
echo "export SPRING_HOME=~/spring-2.0.4.RELEASE" >> ~/.bashrc
echo "export PATH=$SPRING_HOME/bin:$PATH" >> ~/.bashrc
source ~/.bashrc
spring init --dependencies=web myapp
cd myapp
# MySQL workbench
sudo apt-get install -y mysql-server mysql-client
sudo apt-get install -y mysql-workbench
sudo apt-get install -y libcanberra-gtk-moudule libcanberra-gtk3-module
# STS
wget https://download.springsource.com/release/STS/3.9.5.RELEASE/dist/e4.8/spring-tool-suite-3.9.5.RELEASE-e4.8.0-linux-gtk-x86_64.tar.gz
tar -xzvf spring-tool-suite-3.9.5.RELEASE-e4.8.0-linux-gtk-x86_64.tar.gz
# Node.js
sudo apt-get install -y nodejs
sudo apt-get install -y npm
node -v
npm -v
# MongoDB 
sudo apt-get install -y mongodb
mkdir -p /data/db
# Git
sudo apt-get install -y git
git config --global user.name <username>
git config --global user.email <email address>

