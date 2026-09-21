pipeline {
    agent any


    environment {
        SONAR_HOST_URL = 'http://localhost:9000'
        SONAR_TOKEN    = credentials('sonarqube-token')
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
                withSonarQubeEnv('LocalSonarQube') {
                    bat 'mvn -B sonar:sonar -Dsonar.host.url=%SONAR_HOST_URL% -Dsonar.login=%SONAR_TOKEN%'
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
            jacoco execPattern: '**/target/jacoco.exec', classPattern: '**/target/classes', sourcePattern: '**/src/main/java', exclusionPattern: '**/*Test*'
        }
        success {
            echo 'Build succeeded: tests passed and branch coverage is >= 80%.'
        }
        failure {
            echo 'Build failed: check test failures or coverage below the 80% branch threshold.'
        }
    }
}

