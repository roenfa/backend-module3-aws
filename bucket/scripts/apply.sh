# Description: Apply terraform changes
#-auto-approve: Skip interactive approval of plan before applying
#-var-file: Load variables from a file
terraform apply -auto-approve -var-file=envs/dev.tfvars