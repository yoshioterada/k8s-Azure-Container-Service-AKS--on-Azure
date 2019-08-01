#!/bin/bash
set -e

if [ "$1" = "" ]
then
    echo "./build-create.sh [version-number]"
    exit 1
fi
export VERSION=$1

DOCKER_IMAGE=tyoshio2002/front-spring-service
DOCKER_REPOSITORY=yoshio.azurecr.io

# Build docker image
#docker build -t $DOCKER_IMAGE:$VERSION . -f 1-Dockerfile
#docker tag $DOCKER_IMAGE:$VERSION $DOCKER_REPOSITORY/$DOCKER_IMAGE:$VERSION

# Push the image to Private Docker Registry
#docker push $DOCKER_REPOSITORY/$DOCKER_IMAGE:$VERSION

# Change the version for docker image inside of YAML file 
sed -i -e "s|image: .*|image: $DOCKER_REPOSITORY/$DOCKER_IMAGE:${VERSION}|g" 4-create-deployment-svc.yaml

# Apply the new Image to the Service
kubectl apply --record -f 4-create-deployment-svc.yaml


# Clean the image
docker rm $(docker ps -aq)
docker images | awk '/<none/{print $3}' | xargs docker rmi 
