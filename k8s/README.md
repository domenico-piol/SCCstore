> **NOTE** Use this plain k8s yaml files the use-case with PostgreSQL in a VM!

### Steps
1. Get ARO Open Environment
1. Install OCP Virt
1. Install Serverless
1. Create sccstore and sccstore-dev projects
`oc new-project sccstore`
`oc new-project sccstore-dev`

1. Deploy the DEV environment with PG in a container
Use the `sccstore-dev` namespace!! 
`helm install sccstore sccstore-charts`

1. Create Fedora VM
Do this in the `sccstore` namespace, add the `fedora` SSH Private Key (in "Scripts")in the creation step!!! Name the VM "pg-database"

1. Install Ansible
`sudo dnf install ansible`

1. Copy the Ansible script to the VM and run it to create the DB
`virtctl ssh -i ~/.ssh/fedora fedora@pg-database`
Then paste the `DB-VM-Ansible.yaml` to a the VM into `pg.yaml` and run it as root!
`sudo ansible-playbook pg.yaml -K`
1. Deploy DB-VM.yaml (service)
1. Deploy QCOMPLAINTS.yaml (serverless)
1. Deploy UI.yaml (UI)


