#!/bin/bash
set -eo pipefail
ARTIFACT_BUCKET='dj-ag-final-project/artifacts'
STACK_NAME='djag-athena-lambda'
AWS_PROFILE="default"

echo "Deleting stacks"
aws cloudformation delete-stack --stack-name $STACK_NAME --profile $AWS_PROFILE
echo "Deletion initiated, waiting until stack deletion is complete"
aws cloudformation wait stack-delete-complete --stack-name $STACK_NAME --profile $AWS_PROFILE
echo "Deletion completed"