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
	podTemplate.setInheritFrom("")
	podTemplate.setLabel("")
	podTemplate.setNodeSelector("")
	podTemplate.setNodeUsageMode(hudson.model.Node.Mode.NORMAL)
	podTemplate.setCustomWorkspaceVolumeEnabled(false)
	podTemplate.getContainers().add(slaveContainer)
	kubernetes.setDefaultsProviderTemplate("")
	kubernetes.addTemplate(podTemplate)
	kubernetes.setServerUrl("")
	Jenkins.instance.clouds.removeAll(KubernetesCloud)
	Jenkins.instance.clouds.add(kubernetes)
	Jenkins.instance.setNumExecutors(0)
	Jenkins.instance.setQuietPeriod(5)
	Jenkins.instance.setSlaveAgentPort(50000)
	Jenkins.instance.save()
} else {
	println 'Shutdown mode enabled, bailing out'
}
