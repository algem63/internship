pipeline {
    agent any

    tools {
        gradle 'Gradle'
    }

    environment {
        ENV = 'staging'
        TESTCONTAINERS_HOST_OVERRIDE = 'host.docker.internal'
        TESTCONTAINERS_RYUK_DISABLED = 'true'
    }

    stages {
        stage('Build') {
            steps {
                sh 'gradle build -x test'
            }
        }

        stage('Run Tests') {
            steps {
                sh '''
                    rm -rf build/allure-results
                    echo "Running tests on ENV=$ENV"
                    gradle test \\
                        --tests "internship.tests.week1.DBTest" \\
                        --tests "internship.tests.week3.task1.InsertNewUserTest" \\
                        -DENV=$ENV
                '''
            }
        }
    }

    post {
        always {
            echo "=== Generating Allure report (always block) ==="
            sh 'allure generate build/allure-results -o build/allure-report --clean'
            allure includeProperties: false,
                   jdk: '',
                   results: [[path: 'build/allure-results']]
        }
    }
}