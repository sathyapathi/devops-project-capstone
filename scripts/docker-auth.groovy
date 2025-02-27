def dockerLogin(credentialsId) {
    withCredentials([usernamePassword(credentialsId: credentialsId, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
        sh """
            echo ${DOCKER_PASS} | docker login -u ${DOCKER_USER} --password-stdin
        """
    }
}

return this
