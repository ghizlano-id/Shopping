pipeline{
    agent any
    environment {
        DOCKER_REGISTRY = 'ghizo1234'
        IMAGE_NAME = 'shopping-app'
        BUILD_NUMBER = '1.0'
        DOCKERHUB_CREDENTIALS= credentials('docker-hub')
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
        stage('Login to Docker Hub') {
          steps{
        	sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
        	echo 'Login Completed'
          }
        }
        stage('Tag Docker Image') {
          steps{
            sh 'docker tag $IMAGE_NAME:$BUILD_NUMBER $DOCKER_REGISTRY/$IMAGE_NAME:$BUILD_NUMBER'
            echo 'Tag Image Completed'
          }
        }
        stage('Push Image to Docker Hub') {
          steps{
        	sh 'docker push $DOCKER_REGISTRY/$IMAGE_NAME:$BUILD_NUMBER'
            echo 'Push Image Completed'
          }
        }
    }
}