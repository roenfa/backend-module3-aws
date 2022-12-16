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

# Upload Files
resource "aws_s3_object" "input_files" {
  for_each = fileset(var.bucket_input_source, "*")
  bucket   = module.s3_bucket.s3_bucket_id
  key      = join("", ["input/",each.value])
  source   = format("%s%s", var.bucket_input_source, each.value)
  etag     = filemd5(format("%s%s", var.bucket_input_source, each.value))

	# This configuration combines some "default" tags with optionally provided additional tags
  tags = merge(
    var.additional_tags,
    var.project_tags
  )

  depends_on = [
    module.s3_bucket
  ]

}