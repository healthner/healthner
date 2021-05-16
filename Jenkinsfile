pipeline {
    agent any
    tools {
            gradle 'Gradle 6.8.3'
    }
    stages {
        stage('checkout') {
            steps {
                git branch: 'dev', credentialsId: 'jghee',
                url: 'https://github.com/healthner/healthner.git'
            }

        }

        stage('Build') {
            steps {
                echo 'Build'
                sh 'gradle clean build -x test'
            }

        }

        stage('Test') {
            steps {
                echo 'Test'
                sh 'gradle test'
            }
            
        }
    }
}