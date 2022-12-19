#!/bin/bash
set -eo pipefail
ARTIFACT_BUCKET='re-api-gateway'
TEMPLATE=stack.yml
STACK_NAME='cavs-api-gateway-lambda'
AWS_PROFILE=$1

# Spring Boot 2 stack
aws s3 cp build/distributions/aws-api-gateway-1.0-SNAPSHOT.zip s3://$ARTIFACT_BUCKET/cavs/aws-api-gateway-1.0-SNAPSHOT.zip --profile $AWS_PROFILE
aws cloudformation deploy --template-file $TEMPLATE --stack-name $STACK_NAME --capabilities CAPABILITY_NAMED_IAM \
  --parameter-overrides \
  apiGatewayName=lambda-springboot-api-cavs \
  functionName=lambda-spring-boot-function-cavs \
  functionHandler=org.example.handlers.LambdaHandler \
  s3BucketKey=cavs/aws-api-gateway-1.0-SNAPSHOT.zip --profile $AWS_PROFILE
aws --region us-east-1 cloudformation describe-stacks --stack-name $STACK_NAME --query "Stacks[0].Outputs" --output json --profile $AWS_PROFILE