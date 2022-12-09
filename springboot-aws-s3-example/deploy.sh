#!/bin/bash
set -eo pipefail
ARTIFACT_BUCKET='bootcamp-backend-jesus'
AWS_PROFILE=$1

#===================================================
# First lambda deployment
#===================================================
FIRST_LAMBDA_TEMPLATE=first-lambda.yml
FIRST_STACK_NAME='jesus-java'

aws cloudformation package --template-file $FIRST_LAMBDA_TEMPLATE --s3-bucket $ARTIFACT_BUCKET --output-template-file first-out.yml --profile $AWS_PROFILE
aws cloudformation deploy --template-file first-out.yml --stack-name $FIRST_STACK_NAME --capabilities CAPABILITY_NAMED_IAM --profile $AWS_PROFILE

#===================================================
# Second lambda deployment
#===================================================
SECOND_LAMBDA_TEMPLATE=second-lambda.yml
SECOND_STACK_NAME='jesus-java2'

aws cloudformation package --template-file $SECOND_LAMBDA_TEMPLATE --s3-bucket $ARTIFACT_BUCKET --output-template-file second-out.yml --profile $AWS_PROFILE
aws cloudformation deploy --template-file second-out.yml --stack-name $SECOND_STACK_NAME --capabilities CAPABILITY_NAMED_IAM --profile $AWS_PROFILE

