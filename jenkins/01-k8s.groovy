#!groovy

import jenkins.model.*
import org.csanchez.jenkins.plugins.kubernetes.KubernetesCloud
import org.csanchez.jenkins.plugins.kubernetes.PodTemplate
import org.csanchez.jenkins.plugins.kubernetes.ContainerTemplate

if (!Jenkins.instance.isQuietingDown()) {
	def kubernetes = new KubernetesCloud("kubernetes")
	def slaveContainer = new ContainerTemplate("jenkins-slave", "jenkinsci/jnlp-slave:latest")
	slaveContainer.setTtyEnabled(true)
	def podTemplate = new PodTemplate()
	podTemplate.setName("jenkins-slave")
	podTemplate.setNamespace("default")
	podTemplate.getContainers().add(slaveContainer)
	kubernetes.addTemplate(podTemplate)
	Jenkins.instance.clouds.removeAll(KubernetesCloud)
	Jenkins.instance.clouds.add(kubernetes)
	Jenkins.instance.setNumExecutors(0)
	Jenkins.instance.save()
} else {
	println 'Shutdown mode enabled, bailing out'
}
