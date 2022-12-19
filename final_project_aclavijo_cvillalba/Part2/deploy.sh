#!/bin/bash
set -eo pipefail
ARTIFACT_BUCKET='re-api-gateway'
TEMPLATE=stack.yml
STACK_NAME='api-gateway-lambda-cvillalba-aclavijo'
AWS_PROFILE=$1

# Spring Boot 2 stack
aws s3 cp build/distributions/aws-api-gateway-cvillalba-aclavijo-1.0-SNAPSHOT.zip s3://$ARTIFACT_BUCKET/aws-api-gateway-cvillalba-aclavijo-1.0-SNAPSHOT.zip --profile $AWS_PROFILE
aws cloudformation deploy --template-file $TEMPLATE --stack-name $STACK_NAME --capabilities CAPABILITY_NAMED_IAM \
  --parameter-overrides \
  apiGatewayName=lambda-springboot-api-cvillalba-aclavijo \
  functionName=lambda-spring-boot-function-cvillalba-aclavijo \
  functionHandler=org.example.handlers.LambdaHandler \
  s3BucketKey=aws-api-gateway-cvillalba-aclavijo-1.0-SNAPSHOT.zip --profile $AWS_PROFILE
aws --region us-east-1 cloudformation describe-stacks --stack-name $STACK_NAME --query "Stacks[0].Outputs" --output json --profile $AWS_PROFILE