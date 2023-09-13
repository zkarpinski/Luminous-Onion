terraform {
  required_version = ">=1.0"
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "~>3.0"
    }
    random = {
      source  = "hashicorp/random"
      version = "~>3.0"
    }
  }
}

provider "azurerm" {
  features {}
}

resource "random_string" "container_name" {
  length  = 25
  lower   = true
  upper   = false
  special = false
}

resource "random_pet" "rg_name" {
  prefix = "rg"
}

resource "azurerm_resource_group" "rg" {
  name     = random_pet.rg_name.id
  location = "eastus"
}

resource "azurerm_service_plan" "asp" {
  name                = "${var.app_name}-appserviceplan"
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name

  os_type = "Linux"
  sku_name = "B1"
}

resource "azurerm_linux_web_app" "luminous_onion" {
  name                = var.app_name
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  service_plan_id = azurerm_service_plan.asp.id

  site_config {
    always_on = true
    application_stack {
      docker_image = var.image
	    docker_image_tag = "latest"
    }
  }

  app_settings = {
    WEBSITES_PORT = 8081
  }
}

variable "app_name" {
  type        = string
  description = "Application Name"
  default     = "luminous-onion"
}

variable "image" {
  type        = string
  description = "Container image to deploy."
  default     = "zkarpinski/luminous-onion"
}
