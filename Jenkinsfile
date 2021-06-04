pipeline {
    agent any
    environment {
            registry = "gh1719/skhu_capstone2"
            registryCredential = 'docker-hub'
        }

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

            }
            
        }

        stage('Create Docker Image') {
            steps {
                script {
                    dockerImage = docker.build registry
                }

            }
        }

        stage('Push Docker Image') {
            steps {
                script{
                    docker.withRegistry("", registryCredential) {
                        dockerImage.push("$BUILD_NUMBER")
                        dockerImage.push('latest')
                    }
                }
            }
        }

        stage('Remove Unused docker image') {
              steps{
                sh "docker rmi $registry:$BUILD_NUMBER"
                sh "docker rmi $registry:latest"

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
                                        sourceFiles: "deploy",
                                        removePrefix: "",
                                        remoteDirectory: "/healthner",
                                        execCommand: "sh ./healthner/deploy/deploy.sh"
                                    )
                                ]
                            )
                    ]
                )
            }
        }
    }
}
