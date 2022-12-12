#Main tf 
terraform {
  required_version = ">= 0.12.0"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = ">= 2.0"
    }
  }


}
module "athena-s3" {
  source         = "./modules"
  bucket_name    = "agb-athena-s3"
  bucket_acl     = "private"
  bucket_folders = ["input", "output", "results","queries"]
}
