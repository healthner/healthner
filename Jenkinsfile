pipeline {
    agent any

    stages {
        stage('checkout') {

            steps {
                scm ' '
            }

        }

        stage('Build') {

            steps {
                echo 'Building'
                sh './gradlew build'
            }

        }

        stage('test') {

            steps {
                echo 'Testing'
                sh './gradlew test'
            }
            
        }
        stage('Deploy') {

            steps {
                echo 'Deploying'
            }

        }
    }
}