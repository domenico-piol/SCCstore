# The 'Sirius Cybernetics Corporation Online Store' demo application
SCCstore is a Spring Boot based, cloud native demo application using microservice design patterns.

In this project I'm demonstrating how to use Spring Boot for building microservice-based architecture for Kubernetes. This example is based on Spring Boot 2.5.

# Getting Started
This project is a multi-module Maven project based on Spring Boot 2.5.x and intended to be run on Kubernetes.

## Usage Guide
For building the demo application you will need Maven 3.5 or newer (I am using v3.8.2) and Java 11, whereby any newer version will do the job as well.

As said, it is intended to be run on Kubernetes, but it does perfectly work also locally w/o a container environment. 

### Certificates
First of all, you will need a certificate. Ideally in p12 format (jks works as well). So let's create a self-signed certificate and create a p12 from it:

    openssl req -newkey rsa:2048 -nodes -keyout key.pem -x509 -days 365 -out certificate.pem
    openssl pkcs12 -inkey key.pem -in certificate.pem -export -out certificate.p12

For the Kubernetes use-case, we need to base64 encode the p12 certificate and store it into a secret. On a Mac you do:

    base64 -i certificate.p12 -o base64.txt

### Run locally w/o Kubernetes
tbd.

### Run on a Kubernetes Cluster
This project uses Helm for the deployment. This said, from the root directory use either:

    helm install sccstore sccstore-charts

or

    helm upgrade sccstore sccstore-charts

# Architecture
This sample microservices-based system consists of the following modules:
<p align="center">
  <img src="./diagrams/high-level.drawio.svg">
</p>

- **SCC UI** - the user-interface component of the application
- **SCC Identity Manager** - the account, authentication and authorization component (IAM)
- **SCC Shopping Cart** - the shopping cart 
- **SCC Database** - the relational database
- **SCC Complaints** - the complaints controller
