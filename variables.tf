variable instance_name {
  description = "List of EC2 instance names (used as hostname)"
  type        = list(string)
  // default     = ["k8-master1", "k8-worker1", "k8-worker2", "minikube"]
  default     = ["ansible1", "aworker1"]
}

variable "aws_region" {
  description = "AWS region to launch resources"
  default     = "us-east-1"
}

variable "instance_type" {
  description = "EC2 instance type"
  default     = "t2.micro"
}


variable "ami_id" {
  description = "Ubuntu AMI ID"
  default     = "ami-04b4f1a9cf54c11d0"  # Replace with the latest Ubuntu AMI
}

variable "security_group_id" {
  description = "Security Group ID for the EC2 instance"
  default     = "allow_access_devops"  # This will be dynamically assigned after SG creation
}

variable "key_pair_name" {
  description = "Key pair for SSH access"
  default     = "devops_demo"
}
