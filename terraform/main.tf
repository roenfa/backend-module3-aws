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
  backend "s3" {
    bucket         = "dj-ag-final-project"
    key            = "state/terraform.tfstate"
    region         = "us-east-1"
    encrypt        = true
    kms_key_id     = "alias/terraform_bucket_dj_ag_final"
    dynamodb_table = "dynamo_dj_ag_final_project_tfstate"
  }
}

################################################################################
# Calling Modules
################################################################################
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
