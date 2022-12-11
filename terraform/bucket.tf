################################################################################
# S3 bucket
################################################################################
# Bucket creation
module "s3_bucket" {
  source = "terraform-aws-modules/s3-bucket/aws"

  bucket = var.bucket_name
  acl    = "private"

  versioning = {
    enabled = true
  }

  # This configuration combines some "default" tags with optionally provided additional tags
  tags = merge(
    var.additional_tags,
		var.project_tags
  )
}
# Folder structure creation
resource "aws_s3_object" "folder_structure" {
  count = length(var.bucket_folders)

  bucket = module.s3_bucket.s3_bucket_id
  acl    = "private"
  key    = var.bucket_folders[count.index]
  source = "nul"

  # This configuration combines some "default" tags with optionally provided additional tags
  tags = merge(
    var.additional_tags,
		var.project_tags
  )
}