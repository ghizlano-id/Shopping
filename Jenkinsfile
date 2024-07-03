pipeline{
    agent any
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
    }
}