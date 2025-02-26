pipeline {
    agent any

    environment {
        GIT_REPO = "https://github.com/sathyapathi/devops-project-capstone.git"
        DOCKER_IMAGE = "sathyapathi/capstone-image:1.0"
        DOCKERFILE_NAME = "Dockerfile_capstone"
        DOCKER_CREDENTIALS = "docker-hub-credentials" // Jenkins credentials ID for DockerHub
        INVENTORY_SERVERS_TEST = "Server_Inventory_Test.txt"
		INVENTORY_SERVERS_PROD = "Server_Inventory_Prod.txt"
        ANSIBLE_PLAYBOOK_TEST = "ansible_playbook_configure-test-server.yml"
		ANSIBLE_PLAYBOOK_PROD = "ansible_playbook_configure-test-server.yml"

    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    echo "Cloning the repository..."
                    git branch: 'main', url: "${GIT_REPO}"
                }
            }
        }

        stage('Build Package') {
            steps {
                script {
                    echo "Building the Maven package..."
                    sh "mvn clean package -Ddockerfile=${DOCKERFILE_NAME}"
                }
            }
        }

        stage('Build & Containerize') {
            steps {
                script {
                    echo "Building Docker image..."
                    sh "docker build -t ${DOCKER_IMAGE} -f ${DOCKERFILE_NAME} ."
                }
            }
        }

        stage('Release to DockerHub') {
            steps {
                script {
                    echo "Pushing Docker image to DockerHub..."
                    withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS}", usernameVariable: "DOCKER_USER", passwordVariable: "DOCKER_PASS")]) {
                        sh "echo ${DOCKER_PASS} | docker login -u ${DOCKER_USER} --password-stdin"
                        sh "docker push ${DOCKER_IMAGE}"
                    }
                }
            }
        }

        stage('Deploy to Test Servers') {
            steps {
                script {
                    echo "Deploying to test server using Ansible..."
                    withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS}", usernameVariable: "DOCKER_USER", passwordVariable: "DOCKER_PASS")]) {
                        sh """
                            export DOCKER_USERNAME=${DOCKER_USER}
                            export DOCKER_PASSWORD=${DOCKER_PASS}
                            ansible-playbook -i ${INVENTORY_SERVERS_TEST} ${ANSIBLE_PLAYBOOK_TEST}
                        """
                    }
                }
            }
        }
		
		stage('Deploy to Prod Servers') {
            steps {
                script {
                    echo "Deploying to test server using Ansible..."
                    withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS}", usernameVariable: "DOCKER_USER", passwordVariable: "DOCKER_PASS")]) {
                        sh """
                            export DOCKER_USERNAME=${DOCKER_USER}
                            export DOCKER_PASSWORD=${DOCKER_PASS}
                            ansible-playbook -i ${INVENTORY_SERVERS_PROD} ${ANSIBLE_PLAYBOOK_PROD}
                        """
                    }
                }
            }
        }
    }

    post {
        success {
            echo "Pipeline completed successfully!"
        }
        failure {
            echo "Pipeline failed. Please check the logs."
        }
    }
}
