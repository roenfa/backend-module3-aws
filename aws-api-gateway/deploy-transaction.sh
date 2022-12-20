#!/bin/bash
set -eo pipefail
ARTIFACT_BUCKET='djag-api-gateway'
TEMPLATE=stack-transaction.yml
STACK_NAME='djag-api-gateway-lambda'
FUNCTION_NAME='djag-api-gateway-lambda'
AWS_PROFILE="default"

# Spring Boot 2 stack
aws s3 cp build/distributions/aws-api-gateway-1.0-SNAPSHOT.zip s3://$ARTIFACT_BUCKET/aws-api-gateway-1.0-SNAPSHOT.zip --profile $AWS_PROFILE
aws cloudformation deploy --template-file $TEMPLATE --stack-name $STACK_NAME --capabilities CAPABILITY_NAMED_IAM \
  --parameter-overrides \
  apiGatewayName=lambda-springboot-api-djag \
  functionName=$FUNCTION_NAME \
  functionHandler=org.example.handlers.LambdaTransactionHandler \
  s3BucketKey=aws-api-gateway-1.0-SNAPSHOT.zip --profile $AWS_PROFILE
aws --region us-east-1 cloudformation describe-stacks --stack-name $STACK_NAME --query "Stacks[0].Outputs" --output json --profile $AWS_PROFILE