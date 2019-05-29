pipeline {
    agent {
        dockerfile {
            filename 'docker/dockerfile-hugo'
            additionalBuildArgs '--build-arg JENKINS_USER_ID=`id -u jenkins` --build-arg JENKINS_GROUP_ID=`id -g jenkins`'
        }
    }

    environment {
        AWS_ACCESS_KEY_ID = credentials('AWS_ACCESS_KEY_ID')
        AWS_SECRET_ACCESS_KEY = credentials('AWS_SECRET_ACCESS_KEY')
    }

    stages {
        stage('Build') {
            steps {
                sh 'hugo -s src -d ../target'
            }
        }
        stage('Upload') {
            steps {
                s3Upload(bucket: 'xxx', acl: 'PublicRead', file: './target')
            }
        }
    }
}
