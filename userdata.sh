#!/bin/bash

# Replace <instance_name> with Terraform variable
INSTANCE_NAME="${instance_name}"

# Set the hostname
sudo hostnamectl set-hostname "$INSTANCE_NAME"

# Update /etc/hosts to reflect the new hostname
echo "127.0.0.1 $INSTANCE_NAME" | sudo tee -a /etc/hosts
