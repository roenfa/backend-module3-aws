################################################################################
# Bucket Variables
################################################################################
variable "bucket_name" {
  type        = string
  description = "Name of Bucket"
}

variable "bucket_folders" {
  type        = list(string)
  description = "List of folder structure"
}

################################################################################
# Athena Variables
################################################################################
variable "athena_database_name" {
  type        = string
  description = "Name of athena database"
}

################################################################################
# Tagging System
################################################################################
# Terraform 0.12 and later syntax
variable "additional_tags" {
  default     = {}
  description = "Additional resource tags"
  type        = map(string)
}

variable "project_tags" {
  default     = {}
  description = "Additional resource tags"
  type        = map(string)
}
