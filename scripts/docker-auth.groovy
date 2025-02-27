// scripts/docker-auth.groovy

// Ensure DOCKER_HUB_CREDENTIALS_ID is accessible globally
def getDockerHubCredentialsId() {
    return "docker-hub-credentials"
}

// Function to log in to DockerHub using Jenkins credentials
def dockerLogin() {
    def credentialsId = getDockerHubCredentialsId()
    withCredentials([usernamePassword(credentialsId: credentialsId, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
        sh """
            echo ${DOCKER_PASS} | docker login -u ${DOCKER_USER} --password-stdin
        """
    }
}

// Return this script so it can be used in other scripts
return this
