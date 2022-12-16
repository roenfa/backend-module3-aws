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

variable "bucket_input_source" {
  type        = string
  description = "Source of input file"
}

################################################################################
# Athena Variables
################################################################################
variable "athena_glue_catalog" {
  type        = string
  description = "Name of athena catalog"
}

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
