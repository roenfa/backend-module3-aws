provider "aws" {
  region = "us-east-1"
}

resource "aws_iam_role" "lambda_athena_role" {
  name               = "athena_lambda_cavs_role"
  assume_role_policy = jsonencode(
{
 "Version": "2012-10-17",
 "Statement": [
   {
     "Action": "sts:AssumeRole",
     "Principal": {
       "Service": "lambda.amazonaws.com"
     },
     "Effect": "Allow",
     "Sid": ""
   }
 ]
}
)
}

resource "aws_iam_role" "lambda_invocation_role" {
  name               = "invoke_lambda_cavs_role"
  assume_role_policy = <<EOF
{
 "Version": "2012-10-17",
 "Statement": [
   {
     "Action": "sts:AssumeRole",
     "Principal": {
       "Service": "lambda.amazonaws.com"
     },
     "Effect": "Allow",
     "Sid": ""
   }
 ]
}
EOF
}

resource "aws_iam_policy" "iam_policy_for_lambda" {

  name        = "aws_iam_policy_for_products_cavs_lambda_role"
  path        = "/"
  description = "AWS IAM Policy for managing aws athena lambda role"
  policy      = jsonencode(
{
 "Version": "2012-10-17",
 "Statement": [
   {
     "Action": [
       "logs:CreateLogGroup",
       "logs:CreateLogStream",
       "logs:PutLogEvents"
     ],
     "Resource": "arn:aws:logs:*:*:*",
     "Effect": "Allow"
   },
   {
            "Effect": "Allow",
            "Action": [
                "athena:*"
            ],
            "Resource": [
                "*"
            ]
        },
        {
            "Effect": "Allow",
            "Action": [
                "glue:CreateDatabase",
                "glue:DeleteDatabase",
                "glue:GetDatabase",
                "glue:GetDatabases",
                "glue:UpdateDatabase",
                "glue:CreateTable",
                "glue:DeleteTable",
                "glue:BatchDeleteTable",
                "glue:UpdateTable",
                "glue:GetTable",
                "glue:GetTables",
                "glue:BatchCreatePartition",
                "glue:CreatePartition",
                "glue:DeletePartition",
                "glue:BatchDeletePartition",
                "glue:UpdatePartition",
                "glue:GetPartition",
                "glue:GetPartitions",
                "glue:BatchGetPartition"
            ],
            "Resource": [
                "*"
            ]
        },
        {
            "Effect": "Allow",
            "Action": [
                "s3:GetBucketLocation",
                "s3:GetObject",
                "s3:ListBucket",
                "s3:ListBucketMultipartUploads",
                "s3:ListMultipartUploadParts",
                "s3:AbortMultipartUpload",
                "s3:CreateBucket",
                "s3:PutObject",
                "s3:PutBucketPublicAccessBlock"
            ],
            "Resource": [
                "*"
            ]
        },
        {
            "Effect": "Allow",
            "Action": [
                "s3:GetObject",
                "s3:ListBucket"
            ],
            "Resource": [
                "*"
            ]
        },
        {
            "Effect": "Allow",
            "Action": [
                "s3:ListBucket",
                "s3:GetBucketLocation",
                "s3:ListAllMyBuckets"
            ],
            "Resource": [
                "*"
            ]
        },
        {
            "Effect": "Allow",
            "Action": [
                "sns:ListTopics",
                "sns:GetTopicAttributes"
            ],
            "Resource": [
                "*"
            ]
        },
        {
            "Effect": "Allow",
            "Action": [
                "cloudwatch:PutMetricAlarm",
                "cloudwatch:DescribeAlarms",
                "cloudwatch:DeleteAlarms",
                "cloudwatch:GetMetricData"
            ],
            "Resource": [
                "*"
            ]
        },
        {
            "Effect": "Allow",
            "Action": [
                "lakeformation:GetDataAccess"
            ],
            "Resource": [
                "*"
            ]
        },
        {
          "Action": "lambda:InvokeFunction",
          "Effect": "Allow",
          "Resource": ["*"]
        }
 ]
 
}
)
}

resource "aws_iam_role_policy_attachment" "attach_iam_policy_to_iam_role_athena_lambda" {
  role       = aws_iam_role.lambda_athena_role.name
  policy_arn = aws_iam_policy.iam_policy_for_lambda.arn
}

resource "aws_iam_role_policy_attachment" "attach_iam_policy_to_iam_role_invocation_lambda" {
  role       = aws_iam_role.lambda_invocation_role.name
  policy_arn = aws_iam_policy.iam_policy_for_lambda.arn
}

resource "aws_lambda_function" "terraform_athena_lambda_func" {
  filename = "../lambda-verify-stock/build/distributions/lambda-verify-stock-1.0-SNAPSHOT.zip"
  function_name    = "verify_stock_cavs"
  role             = aws_iam_role.lambda_athena_role.arn
  handler          = "lambda.verify.stock.handlers.LambdaHandler"
  runtime          = "java11"
  timeout          = 100
  memory_size      = 1024
  depends_on       = [aws_iam_role_policy_attachment.attach_iam_policy_to_iam_role_athena_lambda]
  publish          = false
}


resource "aws_lambda_function" "terraform_invocation_lambda_func" {
  filename = "../lambda-shoping-cart/build/distributions/lambda-shoping-cart-1.0-SNAPSHOT.zip"
  function_name    = "shoping_cart_cavs"
  role             = aws_iam_role.lambda_invocation_role.arn
  handler          = "lambda.shoping.handlers.ShopingCartHandler"
  runtime          = "java11"
  timeout          = 100
  memory_size      = 1024
  depends_on       = [aws_iam_role_policy_attachment.attach_iam_policy_to_iam_role_invocation_lambda]
  publish          = false
}

