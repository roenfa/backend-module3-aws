#!/bin/bash
set -eo pipefail
ARTIFACT_BUCKET='djag-api-gateway'
STACK_NAME='djag-api-gateway-lambda'
AWS_PROFILE=$1

echo "Deleting stacks"
aws cloudformation delete-stack --stack-name $STACK_NAME --profile $AWS_PROFILE
echo "Deletion initiated, waiting until stack deletion is complete"
aws cloudformation wait stack-delete-complete --stack-name $STACK_NAME --profile $AWS_PROFILE
echo "Deletion completed"