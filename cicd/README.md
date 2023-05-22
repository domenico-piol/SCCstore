The pipeline requires Tekton and ArgoCD to be available on the cluster. Additionally, some Tasks from the Tekton Hub are required. And as we build container-images, the SericeAccount used by the pipeline (default: pipeline) requires some additional rights to run the tasks privileged.

# Tekton/ArgoCD
Use the respective operators from the OpenShift OperatorHub to install Tekton and ArgoCD.

# Required Tasks
## git-clone
Use tkn to install this Task:

    tkn hub install task git-clone

## maven
Use tkn to install this Task:

    tkn hub install task maven

## list-directories
This is a custom Tasks, use the yaml-file in this directory.

## buildah
Use tkn to install this Task:

    tkn hub install task buildah

The buildah task will also need credentials for pushing the container image to the container registry. Docker.io in this case.
This can be done by placing the .docker/config.json file holding the credentials (encoded) to the buildah workspace. There is an example on how to do so in the buildah description on tekton hub.

# Privileges
As mentioned, buildah requires some additional privileges. If the pipeline ServiceAccount does not exist yet (unlikely,or you have a problem on your OpenShift cluster), use:

    oc create serviceaccount pipeline

Then add the required privileges to that ServiceAccount:

    oc adm policy add-scc-to-user privileged -z pipeline
    oc adm policy add-role-to-user edit -z pipeline

