
resource "aws_instance" "EC2-Creation" {
  for_each = toset(var.instance_name)  # Iterate over the list of instance names

  ami                    = var.ami_id  # Ubuntu AMI
  instance_type          = var.instance_type
  key_name               = var.key_pair_name
  vpc_security_group_ids = [var.security_group_id]

  tags = {
    Name = each.key  # Assigns the unique instance name
  }

  # Pass the instance name to userdata.sh dynamically
  user_data = templatefile("ec2_userdata.sh", { instance_name = each.key })
}



