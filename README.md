# kube-test

Provides a small application that exercises all facets of a production
Kubernetes environment including:

-   [ ] Obtaining configuration data from an external system.
-   [ ] Obtaining credentials from an external system.
-   [ ] Logging to an external system.
-   [ ] Metrics collection to an external system.
-   [x] Allowing ChaosMonkey to kill off pods.
-   [x] Providing a way to force an HTTP error response.
-   [x] "Reflecting" the IP address and status of the node that was queried.

## Current endpoints

When the created image is run via docker, use the IP address associated with
the target container.  When the image is exposed as a Kubernetes service, use
the IP address associated with the service (the IP address "reflected" by the
image will be the container's IP address).  Here are the current endpoints:

-   http://<IP>:8080/kill.* - Kill the responding service.
-   http://<IP address>:8080/error[^0-9]*[0-9]* - Respond with HTTP error
    parsed from the trailing numeric characters.
-   http://<IP address>:8080/<anything else> - Respond with status JSON.

## Building the project

Both the Uber-JAR and Docker image can be build using Maven as follows:

```
mvn clean install fabric8:build
```
The Uber-JAR will be: kube-test/target/kube-test-<project version>.one-jar.jar.

The Docker images will be: kube-test/target/docker/kube-test/tmp/docker-build.tar.

Since the build process adds the Docker image to the local registry, you should
never need the tar file above.

## Running the service

A single instance of the service can be run from the CLI as follows:

```
java -jar kube-test-<project version>.one-jar.jar
```

The image can be run from Docker as follows:

```
docker run kube-test
```

or:

```
mvn fabric8:start
```

The image can be run via Kubernetes with as follows:

```
kubectl run kube-test --image=kube-test:0.1-SNAPSHOT --replicas=5 --port=8080
kubectl expose deployment kube-test --port=8080
```
The service can then be reached at the IP address listed by the following command:

```
kubectl get svc kube-test
```
