pipeline{
    agent any
    environment {
        DOCKER_REGISTRY = 'ghizo1234'
        IMAGE_NAME = 'my-shopping-app'
    }
    tools {
        maven 'local_maven'
    }
    stages {
        stage('Build'){
            steps {
                sh 'mvn clean package -Dmaven.test.skip=true'
                echo 'Build project Completed'
            }
            post {
                success {
                    echo 'Archiving the artifacts'
                    archiveArtifacts artifacts: '**/target/*.jar'
                }
            }
        }
        stage('Build Docker Image') {
          steps{
        	sh 'docker-compose build'
            echo 'Build Image Completed'
          }
        }
    }
}