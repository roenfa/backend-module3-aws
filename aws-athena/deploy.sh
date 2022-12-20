#!/bin/bash
set -eo pipefail
ARTIFACT_BUCKET='djag-api-gateway'
TEMPLATE=template.yml
STACK_NAME='djag-athena-lambda'
AWS_PROFILE="default"
REGION="us-east-1"

# Spring Boot 2 stack
aws s3 cp build/distributions/aws-athena-1.0-SNAPSHOT.zip s3://$ARTIFACT_BUCKET/aws-athena-1.0-SNAPSHOT.zip --profile $AWS_PROFILE  --region $REGION
aws cloudformation deploy --template-file $TEMPLATE --stack-name $STACK_NAME --capabilities CAPABILITY_NAMED_IAM \
  --parameter-overrides \
  apiGatewayName=lambda-springboot-api \
  functionName=djag-athena-lambda \
  functionHandler=org.example.handlers.LambdaHandler \
  s3BucketKey=aws-athena-1.0-SNAPSHOT.zip --profile $AWS_PROFILE --region $REGION
aws --region $REGION cloudformation describe-stacks --stack-name $STACK_NAME --query "Stacks[0].Outputs" --output json --profile $AWS_PROFILE