#creating bucket
resource "aws_s3_bucket" "athena-s3" {
    bucket = var.bucket_name
    acl    = var.bucket_acl
    tags = {
        Name = var.bucket_name
    }
}

resource "aws_s3_bucket_object" "folders-in-bucket" {
    for_each = toset(var.bucket_folders)
    bucket = aws_s3_bucket.athena-s3.id
    key    = "${each.value}/"
}