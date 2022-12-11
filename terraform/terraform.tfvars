################################################################################
# Bucket Variables
################################################################################
bucket_name    = "dj-athena-s3"
bucket_folders = ["output/", "input/", "results/"]

################################################################################
# Tagging System
################################################################################
project_tags = {
  Owner       = "Diego Jauregui Salvatierra",
  Bootcamp    = "JU BC LAT DEVOPS 01",
  Subject     = "Backend",
  CreatedWith = "Terraform"
}