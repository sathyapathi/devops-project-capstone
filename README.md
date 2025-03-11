# DevOps Project Capstone Documentation

## Git Repositories
- **Project Repository:** [devops-project-capstone](https://github.com/sathyapathi/devops-project-capstone.git)
- **Selenium Testing Repository:** [devops-project-capstone-testing](https://github.com/sathyapathi/devops-project-capstone-testing.git)

---
## Notes

### Terraform Setup

#### Validate Terraform Installation
```sh
terraform --version
```

#### Initialize Terraform
1. Create `provider.tf` and configure the required provider (AWS/Azure/etc.).
2. Initialize Terraform:
   ```sh
   terraform init
   ```
3. Show Terraform state:
   ```sh
   terraform show
   ```
4. Run tests:
   ```sh
   terraform test
   ```

#### Running Terraform
```sh
terraform test
terraform plan
terraform apply
```

---
### Git Setup and Commands

#### Validate Git Installation
```sh
git --version
```

#### Git Repository Permissions
1. Generate a **Personal Access Token (PAT)** and assign necessary permissions.
2. Set the remote URL with authentication:
   ```sh
   git remote set-url origin https://<USERNAME>:<PAT>@github.com/sathyapathi/devops-project-capstone.git
   ```

#### Common Git Commands
```sh
git status
git add .
git commit -m "Commit message"
git remote -v
git diff origin/main
git push origin main
```

#### Generate a Personal Access Token (PAT) on GitHub
1. Log in to GitHub.
2. Navigate to **Settings → Developer Settings → Personal Access Tokens**.
3. Select **Fine-grained tokens** and click **Generate new token**.
4. Configure the following:
   - Token Name
   - Repository Access: Select specific repositories
   - Permissions: Choose the required permissions
5. Generate and copy the token.

#### Connect to Git Repository (First-Time Setup)
```sh
git clone https://github.com/sathyapathi/devops-project-capstone-testing.git
git clone https://github.com/sathyapathi/devops-project-capstone.git
```
```sh
git remote set-url origin https://<USERNAME>:<PAT>@github.com/sathyapathi/devops-project-capstone-testing.git
git remote set-url origin https://<USERNAME>:<PAT>@github.com/sathyapathi/devops-project-capstone.git
```

---

---
## Installation Guides

#### Install Maven
```sh
sudo apt update
sudo apt install maven
mvn --version
java --version
```

#### Install Java
- Java is installed automatically with Maven.
- Alternatively, install manually:
  ```sh
  sudo apt update
  sudo apt install jdk20
  ```

#### Install Jenkins
```sh
# Add Jenkins GPG Key
curl -fsSL https://pkg.jenkins.io/debian/jenkins.io-2023.key | sudo tee /usr/share/keyrings/jenkins-keyring.asc > /dev/null

# Add Jenkins Repository
echo "deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] https://pkg.jenkins.io/debian binary/" | sudo tee /etc/apt/sources.list.d/jenkins.list > /dev/null

# Update Package List and Install Jenkins
sudo apt update
sudo apt install jenkins

# Verify Installation
jenkins --version
```

#### Enable and Start Jenkins Service
```sh
sudo systemctl start jenkins
sudo systemctl enable jenkins
sudo systemctl status jenkins
```

#### Retrieve Jenkins Initial Admin Password
```sh
sudo cat /var/lib/jenkins/secrets/initialAdminPassword
```

#### Access Jenkins
- URL: `http://<server_ip>:8080`
- Enter Initial Admin Password (from the above command).
- Install Suggested Plugins.
- Create an admin user.
- Update Jenkins IP address if the server IP changes.

---
########## Jenkins Pipeline Configuration START ##########

## Test Environment

1. **Project Name:** `1.Capstone-Test-Env`
2. **Triggers:** GitHub hook trigger for GITScm polling
3. **Pipeline:**
   - **Definition:** Pipeline script from SCM
   - **SCM:** Git
   - **Repository:** `https://github.com/sathyapathi/devops-project-capstone.git`
   - **Branches:** `*/main`
   - **Script Path:** `Jenkinsfile_Rollout_Test`

## Testing - QA Check - Run Test Cases

1. **Project Name:** `2.Capstone-QA-Check`
2. **Triggers:** Build after other projects are built
   - **Projects to Watch:** `Capstone-Test-Env`
   - **Trigger only if build is stable:** ✅
3. **Pipeline:**
   - **Definition:** Pipeline script from SCM
   - **SCM:** Git
   - **Repository:** `https://github.com/sathyapathi/devops-project-capstone.git`
   - **Branches:** `*/main`
   - **Script Path:** `Jenkinsfile_QA_Check`

## Deploy to Prod Environment

1. **Project Name:** `3.Capstone-Prod-Env`
2. **Triggers:** Build after other projects are built
   - **Projects to Watch:** `Capstone-QA-Check`
   - **Trigger only if build is stable:** ✅
3. **Pipeline:**
   - **Definition:** Pipeline script from SCM
   - **SCM:** Git
   - **Repository:** `https://github.com/sathyapathi/devops-project-capstone.git`
   - **Branches:** `*/main`
   - **Script Path:** `Jenkinsfile_Rollout_Prod`

########## Jenkins Pipeline Configuration END ##########

---


########## GitHub to Invoke Pipeline in Jenkins Automatically START ##########

## Generate a New Personal Access Token (PAT)

1. Create PAT and copy the PAT key.
2. Under "Repository access":
   - Select "Only select repositories"
   - Choose your repository (`devops-project-capstone`)
3. Under "Repository permissions":
   - Enable the following:
     - Contents → Read-only ✅ (Allows Jenkins to fetch code from the repository)
     - Metadata → Read-only ✅ (Required for repository access)
     - Webhooks → Full control ✅ (Allows Jenkins to trigger builds when changes are pushed)
4. Generate and Copy the Token.

## Add GitHub Credentials to Jenkins

1. Open Jenkins: `http://54.167.216.49:8080/manage/`
2. Navigate to **Manage Jenkins → Manage Credentials**.
3. Click **(global) → Add Credentials**.
4. Fill in the details:
   - **Kind:** Username with password
   - **Username:** Your GitHub username
   - **Password:** Paste the `PAT_for_Jenkins` token
   - **ID (Optional):** `github-jenkins-token`
   - **Description:** `GitHub PAT for Jenkins`
5. Click **OK**.

## Configure Jenkins Job to Use Webhook

1. Go to **Jenkins Dashboard**.
2. Open the Job (Pipeline) you want to trigger.
3. Click **Configure**.
4. Under "Build Triggers", select:
   - ✅ **GitHub hook trigger for GITScm polling** (Enables webhook triggers)
5. Click **Save**.

## Create a GitHub Webhook

1. Go to **GitHub Repository**: `https://github.com/sathyapathi/devops-project-capstone`
2. Navigate to **Settings → Webhooks**.
3. Click **Add webhook**.
4. Enter Webhook details:
   - **URL:** `http://54.167.216.49:8080/github-webhook/`
   - **Content type:** `application/json`
   - **Triggers:** Select `Just the push event`
   - **Secret:** Leave it blank (Jenkins doesn’t need it)
   - **SSL Verification:** Keep default.
5. Click **Add Webhook**.
6. **Update IP Address** whenever the Jenkins server IP changes.

## Test the Webhook

1. In **GitHub Repository Settings → Webhooks**, find the new webhook.
2. Click **Recent Deliveries**.
   - If it shows ✔️ `200 OK`, the webhook is working!
   - If it fails (❌ `500 error`):
     - Check Jenkins logs:
       ```sh
       cat /var/log/jenkins/jenkins.log
       ```
     - Ensure Jenkins is publicly accessible at `http://54.167.216.49:8080`.
     - Verify that the **GitHub plugin** is installed in Jenkins.

## Validate the Integration

1. Make a test commit in your GitHub repo:
   ```sh
   git commit --allow-empty -m "Trigger Jenkins Webhook"
   git push origin main
   ```
2. **Jenkins should start the pipeline automatically**.

########## GitHub to Invoke Pipeline in Jenkins Automatically END ##########

---
