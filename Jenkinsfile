pipeline {
    agent any

    environment {
        SONAR_TOKEN = credentials('sonarqube-token')
    }

    options {
        timestamps()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Unit Test') {
            steps {
                bat 'mvn -B clean test'
            }
        }

        stage('Coverage Check (JaCoCo >= 80% Branch)') {
            steps {
                bat 'mvn -B verify'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    def jdk21Home = tool name: 'JDK21', type: 'jdk'
                    withEnv(["JAVA_HOME=${jdk21Home}", "PATH+JDK21=${jdk21Home}\\bin"]) {
                        withSonarQubeEnv('LocalSonarQube') {
                            bat 'mvn -B sonar:sonar'
                        }
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }

    post {
        always {
            junit testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true
            archiveArtifacts artifacts: '**/target/site/jacoco/**', allowEmptyArchive: true
        }
        success {
            echo 'Build succeeded: tests passed and branch coverage is >= 80%.'
        }
        failure {
            echo 'Build failed: check test failures or coverage below the 80% branch threshold.'
        }
    }
}

