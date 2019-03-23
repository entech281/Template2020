#!/bin/sh

# Grab the libraries -- not sure this is necessary, but can't hurt
[ -d ~/navx-maxp ] || mkdir ~/navx-mxp
cd ~/navx-mxp
rm -f navx-mxp-libs.zip
curl -O https://www.kauailabs.com/public_files/navx-mxp/navx-mxp-libs.zip
unzip -o navx-mxp-libs.zip

# Get the vendor file
mkdir ~/frc2019/vendordeps
cd ~/frc2019/vendordeps
rm -f navx_frc.json
curl -O https://www.kauailabs.com/dist/frc/2019/navx_frc.json 

# Determine the version file the vendor file -- use the second part of the first line, and clean out garbage characters
VERSION=$(grep version navx_frc.json | head -1 | cut -f2 -d: | sed -e 's/[ ",]//g' -e 's/\n//g' -e 's/\r//g' )
echo "Version is: " ${VERSION}

# Install the C++ libraries
cd ~/frc2019/maven/com
mkdir -p kauailabs/navx/frc/navx-cpp
cd kauailabs/navx/frc/navx-cpp
rm -f maven-metadata.xml
curl -O https://repo1.maven.org/maven2/com/kauailabs/navx/frc/navx-cpp/maven-metadata.xml
rm -rf ${VERSION}
mkdir ${VERSION}
cd ${VERSION}
curl -O https://repo1.maven.org/maven2/com/kauailabs/navx/frc/navx-cpp/${VERSION}/navx-cpp-${VERSION}-headers.zip
curl -O https://repo1.maven.org/maven2/com/kauailabs/navx/frc/navx-cpp/${VERSION}/navx-cpp-${VERSION}-linuxathena.zip
curl -O https://repo1.maven.org/maven2/com/kauailabs/navx/frc/navx-cpp/${VERSION}/navx-cpp-${VERSION}-linuxathenadebug.zip
curl -O https://repo1.maven.org/maven2/com/kauailabs/navx/frc/navx-cpp/${VERSION}/navx-cpp-${VERSION}-linuxathenastatic.zip
curl -O https://repo1.maven.org/maven2/com/kauailabs/navx/frc/navx-cpp/${VERSION}/navx-cpp-${VERSION}-linuxathenastaticdebug.zip
curl -O https://repo1.maven.org/maven2/com/kauailabs/navx/frc/navx-cpp/${VERSION}/navx-cpp-${VERSION}-sources.zip
curl -O https://repo1.maven.org/maven2/com/kauailabs/navx/frc/navx-cpp/${VERSION}/navx-cpp-${VERSION}.pom

# Install the Java libraries
cd ~/frc2019/maven/com
mkdir -p kauailabs/navx/frc/navx-java
cd kauailabs/navx/frc/navx-java
rm -f maven-metadata.xml
curl -O https://repo1.maven.org/maven2/com/kauailabs/navx/frc/navx-java/maven-metadata.xml
rm -rf ${VERSION}
mkdir ${VERSION}
cd ${VERSION}
curl -O https://repo1.maven.org/maven2/com/kauailabs/navx/frc/navx-java/${VERSION}/navx-java-${VERSION}-javadoc.jar  
curl -O https://repo1.maven.org/maven2/com/kauailabs/navx/frc/navx-java/${VERSION}/navx-java-${VERSION}-sources.jar  
curl -O https://repo1.maven.org/maven2/com/kauailabs/navx/frc/navx-java/${VERSION}/navx-java-${VERSION}.jar 
curl -O https://repo1.maven.org/maven2/com/kauailabs/navx/frc/navx-java/${VERSION}/navx-java-${VERSION}.pom  

cd ~
