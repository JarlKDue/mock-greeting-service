# mock-greetings-resource
This is the mock-greetings-resource, it is a simple application with two endpoints.

/v1/greeting
/v1/external

/v1/greeting sends a request to the mock-greeting-provider service and returns the reply.
/v1/external mocks an external provider that cannot be instrumented with jaeger, and as such has been wrapped in its own span.

This application contains a jaeger instrumentation using the [opentelemetry-javaagent-all.jar](https://github.com/open-telemetry/opentelemetry-java-instrumentation)

## Running on Kubernetes
Use the Dockerfile.jvm to build an image and deploy the container on kubernetes.

Create a single service that points to 8080 as this is the open port.

Remember to annotate the deployment with "sidecar.jaegertracing.io/inject": "true"

it should look something like this.
apiVersion: apps/v1
kind: Deployment
metadata:
name: mock-greetings-resource
annotations:
"sidecar.jaegertracing.io/inject": "true" #

## Testing
There is a postman collection in the folder postman, import this using postman.

You will have to change the address from 127.0.0.1:8081 to the address on Kubernetes, but other than that, it should run.

Send a few requests and test out if the kubernetes jaeger setup has located it.