# Terraform Deployment

## Prerequisites

- [Terraform](https://developer.hashicorp.com/terraform/tutorials/azure-get-started/install-cli)
- [Azure CLI](https://learn.microsoft.com/en-us/cli/azure/install-azure-cli)

## Deployment

Deployment steps. To read more, follow the guide on [Microsoft Learn](https://learn.microsoft.com/en-us/azure/container-instances/container-instances-quickstart-terraform).

### Validation

```
cd deployment/terraform
terraform init
terraform validate
terraform plan
```

### Deploy

```
terraform apply
terraform show
```

### Destroy

```
terraform plan -destroy -out main.destroy.tfplan
terraform apply main.destroy.tfplan
```
