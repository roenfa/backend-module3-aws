terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.17"
    }
  }

  required_version = ">= 1.3.0"

  # It tells Terraform that we want to use an S3 backend instead of our 
  # local system to manage our state file
  # backend "s3" {
  #   bucket         = "bucket-megamind"
  #   key            = "state/terraform.tfstate"
  #   region         = "us-east-1"
  #   encrypt        = true
  #   kms_key_id     = "alias/terraform_bucket_key_megamind"
  #   dynamodb_table = "dynamo_megamind_tf_state"
  # }
}

################################################################################
# Calling Modules
################################################################################
# Tf state (Dynamo DB Tf state)
# module "dynamo_tfstate" {
#   source = "./modules/dynamo_tfstate"
#   dynamodb_table_name = "dynamo_megamind_tf_state"
#   bucket_name = var.bucket_name
#   depends_on = [
#     module.s3-csv
#   ]
# }

# Bucket
module "bucket" {
  source      = "./modules/s3"
  bucket_name = var.bucket_name
  tags = {
    bucket_name = var.bucket_name
  }
}

# Dynamo
module "dynamo_tfstate" {
  source              = "./modules/dynamo_tfstate"
  dynamodb_table_name = var.dynamodb_table_name

  depends_on = [
    module.bucket
  ]
}
