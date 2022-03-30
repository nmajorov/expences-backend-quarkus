#!/usr/bin/env bash 

oc new-project nm-demo

PROJECT=$(oc  project -q)

DIRNAME=`dirname "$0"`

echo "deploy pipelines in project $PROJECT"


echo "delete existing pipelines and tasks first if exists in project already"

#CUR_BRANCH="$(git rev-parse --abbrev-ref HEAD)"

oc delete pipelineresources.tekton.dev backend-image --ignore-not-found=true

oc delete tasks s2i-quarkus-maven deploy-test-containers backend-mvn deploy-sso clean-up-backend deploy-native-backend --ignore-not-found

oc delete pipelines.tekton.dev build-and-deploy-backend --ignore-not-found=true


echo "deploy resource workspace pvc"
oc create -f resources/pvc-workspace.yaml

echo "deploy resource image"
# need some tricks to set right namespace for image
cat "$DIRNAME/resources/image-resource.yaml" | sed -e "s/nm-demo/$PROJECT/g" | oc create -f -


echo "deploy tasks"
oc create -f tasks/

echo "deploy pipeline"
oc create -f pipeline.yaml


