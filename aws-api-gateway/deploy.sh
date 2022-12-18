#!/bin/bash
set -eo pipefail
ARTIFACT_BUCKET='jvallecilla-s3'
TEMPLATE=stack.yml
STACK_NAME='api-jesus'
AWS_PROFILE=$1

# Spring Boot 2 stack
aws s3 cp build/distributions/aws-api-gateway-1.0-SNAPSHOT.zip s3://$ARTIFACT_BUCKET/aws-api-gateway-1.0-SNAPSHOT.zip --profile $AWS_PROFILE
aws cloudformation deploy --template-file $TEMPLATE --stack-name $STACK_NAME --capabilities CAPABILITY_NAMED_IAM \
  --parameter-overrides \
  apiGatewayName=lambda-api-jesus \
  functionName=lambda-api-jesus \
  functionHandler=org.example.handlers.LambdaHandler \
  s3BucketKey=aws-api-gateway-1.0-SNAPSHOT.zip --profile $AWS_PROFILE
aws --region us-east-1 cloudformation describe-stacks --stack-name $STACK_NAME --query "Stacks[0].Outputs" --output json --profile $AWS_PROFILE