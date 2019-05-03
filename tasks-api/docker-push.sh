#!/bin/bash -e

# the registry should have been created already
# you could just paste a given url from AWS but I'm
# parameterising it to make it more obvious how its constructed
REGISTRY_URL=${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com
# this is most likely namespaced repo name like myorg/veryimportantimage
SOURCE_IMAGE="${DOCKER_REPO}"
# using it as there will be 2 versions published
TARGET_IMAGE="${REGISTRY_URL}/${ECS_APP_NAME}"
# lets make sure we always have access to latest image
TARGET_IMAGE_LATEST="${TARGET_IMAGE}:latest"
TIMESTAMP=$(date '+%Y%m%d%H%M%S')
# using datetime as part of a version for versioned image
VERSION="${TIMESTAMP}-${TRAVIS_COMMIT}"
# using specific version as well
# it is useful if you want to reference this particular version
# in additional commands like deployment of new Elasticbeanstalk version
TARGET_IMAGE_VERSIONED="${TARGET_IMAGE}:${VERSION}"

# making sure correct region is set
aws configure set default.region ${AWS_REGION}

# Push image to ECR
###################

# I'm speculating it obtains temporary access token
# it expects aws access key and secret set
# in environmental vars
$(aws ecr get-login --no-include-email)

# update latest version
docker tag ${SOURCE_IMAGE} ${TARGET_IMAGE_LATEST}
docker push ${TARGET_IMAGE_LATEST}

# push new version
docker tag ${SOURCE_IMAGE} ${TARGET_IMAGE_VERSIONED}
docker push ${TARGET_IMAGE_VERSIONED}


export ECS_TASK_FAMILY_NAME=${ECS_APP_NAME}-dev-tasks
export ECS_CLUSTER_NAME=task-api-cluster
export ECS_SERVICE_NAME=${ECS_APP_NAME}-service-dev

echo "family: $ECS_TASK_FAMILY_NAME"
echo "cluster: $ECS_CLUSTER_NAME"
echo "service: $ECS_SERVICE_NAME"
echo $TARGET_IMAGE_VERSIONED

export TASK_DEFINITION=$(aws ecs describe-task-definition --task-definition $ECS_TASK_FAMILY_NAME | jq ".taskDefinition.containerDefinitions[0].image = \"$TARGET_IMAGE_VERSIONED\"")
echo "task-definition: $TASK_DEFINITION"
# Extract container definitions from task definition.
export CONTAINER_DEFINITION=$(echo $TASK_DEFINITION | jq --raw-output .taskDefinition.containerDefinitions)
# Extract volumes form task definition
export VOLUMES=$(echo $TASK_DEFINITION | jq --raw-output .taskDefinition.volumes)
# Register task definition and get the new task version
export TASK_VERSION=$(aws ecs register-task-definition --family $ECS_TASK_FAMILY_NAME --volumes "${VOLUMES}" --container-definitions "${CONTAINER_DEFINITION}" | jq --raw-output '.taskDefinition.revision')
# Update the ECS service to use the updated Task version CAUTION: If you copy paste this block of code.. be aware of hardcoded service and cluster name
aws ecs update-service --deployment-configuration "maximumPercent=100,minimumHealthyPercent=0"  --cluster $ECS_CLUSTER_NAME --service $ECS_SERVICE_NAME  --task-definition $ECS_TASK_FAMILY_NAME:$TASK_VERSION
