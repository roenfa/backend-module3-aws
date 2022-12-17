# To prevent two team members from writing to the state file at the same time
resource "aws_dynamodb_table" "terraform_state" {
  name           = var.dynamodb_table_name
  read_capacity  = 20
  write_capacity = 20
  hash_key       = "LockID"

  attribute {
    name = "LockID"
    type = "S"
  }
}