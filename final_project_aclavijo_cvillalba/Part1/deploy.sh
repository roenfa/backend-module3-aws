#!/bin/bash
set -eo pipefail
ARTIFACT_BUCKET='backend-aclavijo-cvillalba'
AWS_PROFILE=$1

#===================================================
# First lambda deployment
#===================================================
LAMBDA1_TEMPLATE=Lambda1.yml
FIRST_STACK_NAME='cvillalba-aclavijo-final-project1'

aws cloudformation package --template-file $LAMBDA1_TEMPLATE --s3-bucket $ARTIFACT_BUCKET --output-template-file first-out.yml --profile $AWS_PROFILE
aws cloudformation deploy --template-file first-out.yml --stack-name $FIRST_STACK_NAME --capabilities CAPABILITY_NAMED_IAM --profile $AWS_PROFILE