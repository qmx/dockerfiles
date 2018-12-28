#!groovy

import jenkins.model.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.common.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.plugins.credentials.impl.*
import com.cloudbees.jenkins.plugins.sshcredentials.impl.*

global_domain = Domain.global()
	credentials_store =
	Jenkins.instance.getExtensionList(
			'com.cloudbees.plugins.credentials.SystemCredentialsProvider'
			)[0].getStore()
	credentials = new BasicSSHUserPrivateKey(
			CredentialsScope.GLOBAL,
			"jenkins-hyrule",
			"jenkins",
			new BasicSSHUserPrivateKey.FileOnMasterPrivateKeySource("/mnt/secrets/jenkins.key"),
			"",
			""
			)
credentials_store.addCredentials(global_domain, credentials)
