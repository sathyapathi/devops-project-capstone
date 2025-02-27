// scripts/docker-auth.groovy

// Function to return Docker Hub credentials ID
def getDockerHubCredentialsId() {
    return "docker-hub-credentials"
}

// Function to log in to DockerHub using Jenkins credentials
def dockerLogin() {
    def credentialsId = getDockerHubCredentialsId()
    withCredentials([usernamePassword(credentialsId: credentialsId, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
        sh """
            echo "${DOCKER_PASS}" | docker login -u "${DOCKER_USER}" --password-stdin
        """
        // Export environment variables for Ansible
        sh """
            echo "DOCKER_USER=${DOCKER_USER}" >> $WORKSPACE/docker_env.sh
            echo "DOCKER_PASS=${DOCKER_PASS}" >> $WORKSPACE/docker_env.sh
        """
    }
}

// Return script object for reusability
return this
