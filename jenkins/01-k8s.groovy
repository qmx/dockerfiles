#!groovy

import jenkins.model.*
import org.csanchez.jenkins.plugins.kubernetes.KubernetesCloud
import org.csanchez.jenkins.plugins.kubernetes.PodTemplate

if (!Jenkins.instance.isQuietingDown()) {
	def kubernetes = new KubernetesCloud("kubernetes")
	def podTemplate = new PodTemplate()
	podTemplate.setName("jenkins-slave")
	podTemplate.setYaml("""
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: jnlp
    image: jenkinsci/jnlp-slave:latest
    command: ['cat']
    tty: true
    volumeMounts:
    - name: dockersock
      mountPath: /var/run/docker.sock
    - name: jenkins-keys
      readOnly: true
      mountPath: "/mnt/secrets"
  - name: docker
    image: docker:18.09.0
    command: ['cat']
    tty: true
    volumeMounts:
    - name: dockersock
      mountPath: /var/run/docker.sock
    - name: jenkins-keys
      readOnly: true
      mountPath: "/mnt/secrets"
  volumes:
  - name: dockersock
    hostPath:
      path: /var/run/docker.sock
  - name: jenkins-keys
    secret:
      secretName: jenkins
      items:
        - key: "jenkins.key"
          path: "jenkins.key"
""")
	kubernetes.addTemplate(podTemplate)
	Jenkins.instance.clouds.removeAll(KubernetesCloud)
	Jenkins.instance.clouds.add(kubernetes)
	Jenkins.instance.setNumExecutors(0)
	Jenkins.instance.save()
} else {
	println 'Shutdown mode enabled, bailing out'
}
