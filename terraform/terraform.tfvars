################################################################################
# Bucket Variables
################################################################################
bucket_name         = "dj-athena-s3"
bucket_folders      = ["output/", "input/", "results/"]
bucket_input_source = "files_s3/input/"

################################################################################
# Athena Variables
################################################################################
athena_database_name = "devopsdjaureguiathenadb"
athena_glue_catalog  = "aws_data_catalog_djauregui"

################################################################################
# Tagging System
################################################################################
project_tags = {
  Owner       = "Diego Jauregui Salvatierra",
  Bootcamp    = "JU BC LAT DEVOPS 01",
  Subject     = "Backend",
  CreatedWith = "Terraform"
}