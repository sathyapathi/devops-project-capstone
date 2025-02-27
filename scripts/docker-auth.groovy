// scripts/docker-auth.groovy

// Define DockerHub credentials ID in a separate variable for easy updates
def DOCKER_HUB_CREDENTIALS_ID = "docker-hub-credentials"

// Function to log in to DockerHub using Jenkins credentials
def dockerLogin() {
    withCredentials([usernamePassword(credentialsId: DOCKER_HUB_CREDENTIALS_ID, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
        sh """
            echo ${DOCKER_PASS} | docker login -u ${DOCKER_USER} --password-stdin
        """
    }
}

// Expose the variable in case other scripts need to use it
return this
