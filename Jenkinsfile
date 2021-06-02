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

        stage('Deploy'){
            steps {
                sshPublisher(
                     continueOnError: false, failOnError: true,
                         publishers: [
                            sshPublisherDesc(
                                configName: "healthner",
                                verbose: true,
                                transfers: [
                                    sshTransfer(
                                        sourceFiles: "/build/libs/healthner-0.0.1-SNAPSHOT.jar",
                                        removePrefix: "/build/libs",
                                        remoteDirectory: "/myweb",
                                        execCommand: "ls -al ."
                                    )
                                ]
                            )
                    ]
                )
            }
        }
    }
}
