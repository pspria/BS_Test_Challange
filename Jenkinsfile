 pipeline {
   agent any
   stages {
       stage('setup') {
         steps {
             browserstack(credentialsId: '4658b79d-b061-4f60-8a58-56db75ddf78e') {
                // For Linux-based systems, add the following commands in the given console to download the binary, run it, and stop its execution after the test has been executed.
                sh 'wget "https://www.browserstack.com/browserstack-local/BrowserStackLocal-linux-x64.zip"'
                sh 'unzip BrowserStackLocal-linux-x64.zip'
                sh './BrowserStackLocal --key $BROWSERSTACK_ACCESS_KEY --daemon start'
                sh '<your_test_commands>'
                sh './BrowserStackLocal --key $BROWSERSTACK_ACCESS_KEY --daemon stop'

                // For macOS-based systems, add the following commands in the given console to download the binary, run it, and stop its execution after the test has been executed.
                sh 'wget "https://www.browserstack.com/browserstack-local/BrowserStackLocal-darwin-x64.zip"'
                sh 'unzip BrowserStackLocal-darwin-x64.zip'
                sh './BrowserStackLocal --key $BROWSERSTACK_ACCESS_KEY --daemon start'
                sh '<your_test_commands>'
                sh './BrowserStackLocal --key $BROWSERSTACK_ACCESS_KEY --daemon stop'

                // For Windows-based systems, add the following commands in the given console to download the binary, run it, and stop its execution after the test has been executed.
                sh 'wget "https://www.browserstack.com/browserstack-local/BrowserStackLocal-win32.zip"'
                sh 'powershell.exe D:\BrowserStackLocal.exe'
                sh '<your-test-commands>'
                sh './BrowserStackLocal --key $BROWSERSTACK_ACCESS_KEY --daemon stop'
            }
         }
       }
     }
   }
