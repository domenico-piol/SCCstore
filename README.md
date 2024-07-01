# The 'Sirius Cybernetics Corporation Online Store' demo application
SCCstore is a Spring Boot based, cloud native demo application using microservice design patterns.

In this project I'm demonstrating how to use Spring Boot for building microservice-based architecture for Kubernetes. This example is based on Spring Boot 2.5.


# Getting Started
This project is a multi-module Maven project based on Spring Boot 2.5.x, Quarkus, a PostgreSQL database and intended to be run on OpenShift.

## Architecture
This sample microservices-based system consists of the following modules (target state, not there yet!):
<p align="center">
  <img src="./diagrams/high-level.drawio.svg">
</p>

- **SCC UI** - the user-interface component of the application
- **SCC Complaints (quarkus)** - Java/Quarkus implementation of the complaints controller
- **SCC Complaints (python)** - Python implementation of the complaints controller
- **SCC Complaints Database** - the relational database for complaints


> **NOTE** The Service Mesh is not yet implemented and therefore the **SCC Complaints (python)** component is not yet deployed during the deployment.

On a deployment level, there is a big difference between DEV/TEST and PROD deployment. On DEV and TEST, the database is deployed within a container on OpenShift, whereas on PROD the database is running on a VM!

<p align="center">
  <img src="./diagrams/architecture-stages.jpg" width="600">
</p>

## Build and deployment process
For the build process we will use the capabilities of Tekton. A Tekton pipeline will build, package and push the images to our image-registry on docker.io.

<p align="center">
  <img src="./diagrams/workflow-dev.jpg" width="600">
</p>

Also, we will work with 3 different stages for this workshop. DEV, TEST and PROD. The difference being - beside the database running in a VM on PROD - that on DEV we use manual deployment whereas on TEST and PROD we will use ArgoCD for CI!

<p align="center">
  <img src="./diagrams/workflow-test_prod.jpg" width="600">
</p>

The VM containing the database will be set-up by Ansible during the deployment process. More details later...

This said, overall it looks like this:

<p align="center">
  <img src="./diagrams/deploy-workflow-overall.jpg" width="600">
</p>


# Usage Guide
For building the demo application you will need Maven 3.5 or newer (I am using v3.8.2), Java 17 and Quarkus and Tekton for the CI/CD pipeline. 

For running it, as said, you will need OpenShift, but it should perfectly work also on any other container platform. 

In the PROD setup, I use a PostgreSQL database which runs on a VM... In the DEV setup the database runs within a container.

## Prepare the OpenShift cluster environment (part I - DEV)
For this I use an OpenShift cluster on AWS. When choosing another cloud-provider or environment you will to change the storage-class in the helm values file for the database (for AWS I use 'gp3').

### Step 1 - create the required namespaces
We need a namespace for running the pipelines, one for running the dev/test-stage and prod-stage.

    oc new-project sccstore-pipelines
    oc new-project sccstore-dev
    oc new-project sccstore-test
    oc new-project sccstore-prod

### Step 2 - install Tekton, required Tasks and privileges
We use Tekton for building the application. First, install Tekton using the OperatorHub.

<p align="center">
  <img src="./diagrams/step2-install_Tekton.jpg" width="600">
</p>

Once the Tekton Operator is running, we need some Tasks being installed. For that let's switch to the `sccstore-pipelines` namespace.

    oc project sccstore-pipelines
There we can install the required Tasks, from Tekton Hub:

    tkn hub install task git-clone
    tkn hub install task maven
    tkn hub install task buildah
And our own custom Tasks:

    oc apply -f cicd/dpiol-skopeo-copy.yaml
    oc apply -f cicd/build-info.yaml

Our pipeline also requires some storage, for building and the registry:

    oc apply -f cicd/build-storage.yaml

 The whole pipelines are running under the `pipelines` ServiceAccount. In OpenShift, this ServiceAccounts already exists, if not create it using:

    oc create serviceaccount pipeline

Now add the required privileges to that ServiceAccount to be able to build container-images:

    oc adm policy add-scc-to-user privileged -z pipeline
    oc adm policy add-role-to-user edit -z pipeline

Buildah requires credentials to the image registry we push the container-images to. Same is true for Skopeo. Buildah ist creating and pushing the images, Skopeo will add additional Tags to them.

We use docker.io in this example, unfortunately Buildah and Skopeo require the same credential in different formats (!!!). 

Buildah can use the credentials for docker.io in the config.json format. For this, we need to add the config.json containing the access token as a Secret. For encoding the `config.json` file upfront, use:

    cat config.json | base64
Then add the generated hash to the yaml-file below.

In the `rhworkshops-docker-io-secret.yaml` file:

    apiVersion: v1
    kind: Secret
    metadata:
      name: rhworkshops-dockerconfig-secret
    data:
      config.json: ewoJImF1...

On the other hand, Skopeo expects the username and password, so in the `rhworkshops-docker-io-secret-usernamepassword.yaml` file:

    apiVersion: v1
    kind: Secret
    metadata:
      name: rhworkshops-dockerio-creds-username
      annotations:
        tekton.dev/docker-0: https://docker.io
    type: kubernetes.io/basic-auth
    stringData:
       username: USERNAME
       password: PASSWORD

Apply those 2 yaml files to the sccstore-pipelines namespace:

    oc apply -f rhworkshops-docker-io-secret.yaml 
    oc apply -f rhworkshops-docker-io-secret-usernamepassword.yaml 

> **NOTE** Be aware, this 2 files are the only ones NOT provided within this repository for obvious reasons. You must create them on your own!

As a next step, you need to link the generated secret containeg the username/password key-value pair to the service-account:

    oc secrets link pipeline rhworkshops-dockerio-creds-username 

That's it... the Tekton pipeline environment is now ready!

### Step 3 - start our first build
Now that the build-pipeline environment is ready to be used, let's start our first build.
First, install the pipeline:

    oc apply -f cicd/tekton-pipeline.yaml

The pipeline is ready now, but it's not running yet. In a real world-scenario the run would be triggered by an git-repository event such as a commit, merge et al.
For sake of this exercise, we will trigger the build manually by providing a PipelineRun manifest.

    oc apply -f cicd/tekton-pipeline-run.yaml

<p align="center">
  <img src="./diagrams/step3-pipeline-run.jpg" width="600">
</p>

The pipeline will build the complete project, create container-images for all components, tag the images with the build-hash, push them to docker.io and add also the `latest` tag.

### Step 4 - install the serverless environment (KNative)
Now we install Red Hat OpenShift Serverless (KNative) from OperatorHub.
<p align="center">
  <img src="./diagrams/step4-install_serverless.jpg" width="600">
</p>
We can keep all the defaults.

Once installed we need to create a KnativeServing instance in the `knative-serving` namespace!

<p align="center">
  <img src="./diagrams/step4-create_KnativeServing.jpg" width="600">
</p>

Once it's ready (see "status" labels), we are good to continue.

## Deploy to DEV stage
For DEV stage, we use simply Helm on the command line. But first we must switch to the DEV namespace!

    oc project sccstore-dev

    helm install sccstore sccstore-charts

This will pick the `latest` tag of all the images and deploy the complete application to OpenShift.

To delete the complete deployment, use:

    helm delete sccstore

<p align="center">
  <img src="./diagrams/deploy_dev.jpg" width="600">
</p>

You be seeing the `liquibase constraints` job failing initially until the PostgreSQL database is ready. That is absolutely to be expected. It will complete successfully after PostreSQL is ready.

Once every component has completed tha startup, you can access the application in the `sccstore-ui` component.

<p align="center">
  <img src="./diagrams/deploy_running_app.jpg" width="600">
</p>

To access the data in the database, click on "Complaints Department" in the menu and click on Marvin, the depressed robot.

### Run the liquibase-job standalone
In dev stage there is no persistance. In case you need to re-run the liquibase only for initializing the database, you can run:

    helm template ./sccstore-charts -s charts/database/templates/post-install-complaints.yaml | oc apply -f - 


## Prepare the OpenShift cluster environment (part II - TEST)
Other than for DEV, in the TEST stage we will use ArgoCD for the deployment and not manually invoke Helm.
### Install ArgoCD
ArgoCD can be easily installed using the Operator Hub:

<p align="center">
  <img src="./diagrams/argocd-install-1.jpg" width="600">
</p>

Once ArgoCD is operational, we have to give permission to the Argo CD service account to control the cluster:

    oc adm policy add-cluster-role-to-user cluster-admin -z openshift-gitops-argocd-application-controller -n openshift-gitops

Now we can log-in to ArgoCD using the OpenShift credentials, but before creating our SCCStore TEST application in ArgoCD, we need to push the Helm charts we use to a Helm repository.
Why that? Because ArgoCD relies on a Helm repository for obtaining the charts when using Helm for deployments. 

### Publish Helm charts to the Github Helm-Chart repository (optional)
> Why is this an optional task? Because as long as you do not plan to change the Helm charts, there is no need to re-package and publish the charts. They are already available in my SCCStore-Helm repository!

However, in case you choose to do so...

I am not going into the details of Halm usage, however, there is a `SCCStore-Helm` project in Github for this... you can create a fork as well. In `Sccstore-Helm` you can use:

    helm package ../SCCstore/sccstore-charts -d .
    helm repo index --url https://domenico-piol.github.io/SCCStore-Helm . --merge ./index.yaml

To package a new version of the helm-charts. Don't forget to update the Helm version-info's appropriately in `SCCStore`.

The new Helm-Chart version is published automatically by Github after the commit/push.

### Create ArgcoCD application for TEST
After login to ArgoCD using the OpenShift credentials, we can create an application named sccstore-test:

<p align="center">
  <img src="./diagrams/argocd-create-app.jpg" width="600">
</p>

Use:
| Attribute | Value |
| ----------- | ----------- |
| Application Name: | sccstore-test |
| Project Name | default |
| Repository URL | https://domenico-piol.github.io/SCCStore-Helm/ <br> *This is my helm-chart repository, if you choose to create your own, this need to change!*|
| Chart | sccstore-charts *(choose from list)* |
| Version | *just pick the last recent version from the list* |
| Cluster URL | https://kubernetes.default.svc *(pick from list)* |
| Namespace | sccstore-test |
| Values Files | values.yaml *(pick from list)* |

Leave the rest as it is!

> In a real world, we would link the execution of the sync to a trigger-event, such as merge to a certain branch.
However, for simplicity sake, we will trigger the sync manually!

During the sync operation you will notice all components starting and once every application component being ready (Postgres database, Spring Frontend and Quarkus backend), a Job is started which will initialize the database. After that, the application is fully operational.
