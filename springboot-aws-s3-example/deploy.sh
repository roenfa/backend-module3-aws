#!/bin/bash
set -eo pipefail
ARTIFACT_BUCKET='backend-module-3-djauregui'
TEMPLATE=template.yml
AWS_PROFILE=$1
STACK_NAME=$2

aws cloudformation package --template-file $TEMPLATE --s3-bucket $ARTIFACT_BUCKET --output-template-file out.yml --profile $AWS_PROFILE
aws cloudformation deploy --template-file out.yml --stack-name $STACK_NAME --capabilities CAPABILITY_NAMED_IAM --profile $AWS_PROFILE
