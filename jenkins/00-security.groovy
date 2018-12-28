#!groovy

import jenkins.model.*
import hudson.security.SecurityRealm
import org.jenkinsci.plugins.GithubSecurityRealm
import org.jenkinsci.plugins.GithubAuthorizationStrategy

def github_admin_user = System.getenv('GITHUB_ADMIN_USER')
def github_client_id = System.getenv('GITHUB_CLIENT_ID')
def github_client_secret = System.getenv('GITHUB_CLIENT_SECRET')
def github_oauth_scopes = System.getenv('GITHUB_OAUTH_SCOPES') ?: GithubSecurityRealm.DEFAULT_OAUTH_SCOPES

if (!Jenkins.instance.isQuietingDown()) {
	if (github_client_id && github_client_secret && github_admin_user) {
		def realm = new GithubSecurityRealm(GithubSecurityRealm.DEFAULT_WEB_URI, GithubSecurityRealm.DEFAULT_API_URI, github_client_id, github_client_secret, github_oauth_scopes)
		if (!realm.equals(Jenkins.instance.getSecurityRealm())) {
			Jenkins.instance.setSecurityRealm(realm)
			Jenkins.instance.save()
		}
		def strategy = new GithubAuthorizationStrategy(github_admin_user, false, false, false, "", true, false, false, false)
		if (!strategy.equals(Jenkins.instance.getAuthorizationStrategy())) {
			Jenkins.instance.setAuthorizationStrategy(strategy)
			Jenkins.instance.save()
		}
	}
} else {
	println 'Shutdown mode enabled, bailing out'
}
