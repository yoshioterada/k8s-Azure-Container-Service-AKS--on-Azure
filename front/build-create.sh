#!/bin/bash
set -e

if [ "$1" = "" ]
then
    echo "./build-create.sh [version-number]"
    exit 1
fi
export VERSION=$1

DOCKER_IMAGE=team-hello-world/front
DOCKER_REPOSITORY=yoshio.azurecr.io

# Build docker image
docker build -t $DOCKER_IMAGE:$VERSION .
docker tag $DOCKER_IMAGE:$VERSION $DOCKER_REPOSITORY/$DOCKER_IMAGE:$VERSION

# Push the image to Private Docker Registry
docker push $DOCKER_REPOSITORY/$DOCKER_IMAGE:$VERSION

# Change the version for docker image inside of YAML file 
sed -i -e "s|image: .*|image: $DOCKER_REPOSITORY/$DOCKER_IMAGE:${VERSION}|g" create-deployment-svc.yaml

# Apply the new Image to the Service
#/usr/local/bin/kubectl apply --record -f create-deployment-svc.yaml

# Following command will be used for Istio (Service Mesh).
/usr/local/bin/kubectl apply --record -f <(/usr/local/bin/istioctl kube-inject -f ./create-deployment-svc.yaml)

# Clean the image
docker rm $(docker ps -aq)
docker images | awk '/<none/{print $3}' | xargs docker rmi 
