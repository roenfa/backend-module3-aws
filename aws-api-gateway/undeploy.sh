#!/bin/bash
set -eo pipefail
ARTIFACT_BUCKET='aws-authorizer-api-gateway-bucket'
STACK_NAME='authorizer-lambda'
AWS_PROFILE=$1

echo "Deleting stacks"
aws cloudformation delete-stack --stack-name $STACK_NAME --profile $AWS_PROFILE
echo "Deletion initiated, waiting until stack deletion is complete"
aws cloudformation wait stack-delete-complete --stack-name $STACK_NAME --profile $AWS_PROFILE
echo "Deletion completed"