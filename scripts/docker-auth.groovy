// scripts/docker-auth.groovy

// Store the DockerHub credentials ID in a single variable for easy updates
def DOCKER_HUB_CREDENTIALS_ID = "docker-hub-credentials"

// Function to log in to DockerHub using Jenkins credentials
def dockerLogin() {
    withCredentials([usernamePassword(credentialsId: DOCKER_HUB_CREDENTIALS_ID, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
        sh """
            echo ${DOCKER_PASS} | docker login -u ${DOCKER_USER} --password-stdin
        """
    }
}

// Function to return the credentials ID (for external use if needed)
def getDockerCredentialsId() {
    return DOCKER_HUB_CREDENTIALS_ID
}

// Expose the functions to be used in Jenkinsfile
return this
