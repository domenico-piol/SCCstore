# Sirius Cybernetics Corporation Online Store
SCCstore is a Spring Boot based, cloud native demo application using microservice design patterns.

In this project I'm demonstrating how to use Spring Boot for building microservice-based architecture for Kubernetes. This example is based on Spring Boot 2.5.

## Getting Started
This project is a multi-module Maven project based on Spring Boot 2.5.x and intended to be run on Kubernetes.

### Usage Guide
For building the demo application you will need Maven 3.5 or newer (I am using v3.8.2) and Java 11, whereby any newer version will do the job as well.

As said, it is intended to be run on Kubernetes, but it does perfectly work also locally w/o a container environment. 

#### Run locally w/o Kubernetes
tbd.

#### Run on a Kubernetes Cluster
tbd.

## Architecture
This sample microservices-based system consists of the following modules:

![High Level Diagram](./diagrams/high-level.drawio.svg)

- **SCC UI** - the user-interface component of the application
- **SCC Identity Manager** - the account, authentication and authorization component (IAM)
- **SCC Shopping Cart** - the shopping cart 
- **SCC Database** - the relational database
