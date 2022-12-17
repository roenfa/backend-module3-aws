# Bucket
variable "bucket_name" {
  type        = string
  description = "Bucket name for creation"
}
# Dynamo
variable "dynamodb_table_name" {
  type        = string
  description = "Dynamo table name"
}