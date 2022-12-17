variable "bucket_name" {
  type        = string
  description = "Bucket name for creation"
}
variable "tags" {
  description = "Map of tags to assign to all resources supporting tags."

  type = map(string)
}
variable "resource_specific_tags" {
  description = "Map of tags to assign to specific resources supporting tags. Merged with `tags`."

  type = map(map(string))

  default = {}
}
variable "workspace_bucket_expiration_days" {
  default     = 60
  description = "The expiration days for objects in the workspace bucket in days. By default objects are expired 30 days after their creation. If set to null, expiration is disabled."
}