> **NOTE** Use this plain k8s yaml files the use-case with PostgreSQL in a VM using kustomize!

## Prepare
1. Get ARO Open Environment
1. Install OCP Virt
1. Install Serverless
1. Create sccstore and sccstore-dev projects
`oc new-project sccstore`
`oc new-project sccstore-dev`

## DEV stage
Use the `sccstore-dev` namespace!

1. Deploy the DEV environment with PG in a container
`oc apply -k kustomize/overlays/dev`

## PROD stage
Use the `sccstore` namespace!

### Prepare
1. Create Fedora VM
Do this in the `sccstore` namespace, add the `fedora` SSH Public Key (from ~/.ssh/) in "Scripts" section in the creation step!!! 
Or manually: `oc create secret generic fedora-key --from-file=fedora.pub=/Users/domenico/.ssh/fedora.pub`

Name the VM "pg-database" or you need to change the service-file..

1. Install Ansible
Login - `virtctl ssh -i ~/.ssh/fedora fedora@pg-database`
Then - `sudo dnf install ansible`

1. Copy the Ansible script to the VM and run it to create the DB
`virtctl ssh -i ~/.ssh/fedora fedora@pg-database`
Then paste the `DB-VM-Ansible.yaml` to a the VM into `pg.yaml` and run it as root! (pwd: get from console in OCP)
`sudo ansible-playbook pg.yaml -K`

### Deployment
1. Deploy application
`oc apply -k kustomize/overlays/prod` 