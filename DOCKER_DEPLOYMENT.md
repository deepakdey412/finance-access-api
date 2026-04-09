# Docker Deployment Guide - Finance System

## Prerequisites
- Docker installed
- Docker Hub account (or other container registry)

## Build Docker Image

```bash
# Build the image
docker build -t finance-system:latest .

# Or use docker-compose
docker-compose build
```

## Run Locally

```bash
# Run with docker
docker run -p 8080:8080 finance-system:latest

# Or use docker-compose
docker-compose up -d
```

## Access Swagger API

Once the container is running, access:
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/v3/api-docs
- H2 Console: http://localhost:8080/h2-console

## Push to Docker Hub

```bash
# Login to Docker Hub
docker login

# Tag the image with your Docker Hub username
docker tag finance-system:latest YOUR_DOCKERHUB_USERNAME/finance-system:latest
docker tag finance-system:latest YOUR_DOCKERHUB_USERNAME/finance-system:1.0.0

# Push to Docker Hub
docker push YOUR_DOCKERHUB_USERNAME/finance-system:latest
docker push YOUR_DOCKERHUB_USERNAME/finance-system:1.0.0
```

## Deploy on Cloud

### Deploy on AWS ECS/Fargate
```bash
# Tag for AWS ECR
docker tag finance-system:latest AWS_ACCOUNT_ID.dkr.ecr.REGION.amazonaws.com/finance-system:latest

# Login to ECR
aws ecr get-login-password --region REGION | docker login --username AWS --password-stdin AWS_ACCOUNT_ID.dkr.ecr.REGION.amazonaws.com

# Push to ECR
docker push AWS_ACCOUNT_ID.dkr.ecr.REGION.amazonaws.com/finance-system:latest
```

### Deploy on Azure Container Instances
```bash
# Login to Azure
az login

# Create container registry
az acr create --resource-group myResourceGroup --name myRegistry --sku Basic

# Login to ACR
az acr login --name myRegistry

# Tag and push
docker tag finance-system:latest myregistry.azurecr.io/finance-system:latest
docker push myregistry.azurecr.io/finance-system:latest
```

### Deploy on Google Cloud Run
```bash
# Tag for GCR
docker tag finance-system:latest gcr.io/PROJECT_ID/finance-system:latest

# Push to GCR
docker push gcr.io/PROJECT_ID/finance-system:latest

# Deploy to Cloud Run
gcloud run deploy finance-system --image gcr.io/PROJECT_ID/finance-system:latest --platform managed --region us-central1 --allow-unauthenticated
```

## Environment Variables

You can customize the deployment with environment variables:

```bash
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e JWT_SECRET=your-secret-key \
  -e JWT_EXPIRATION=86400000 \
  finance-system:latest
```

## Health Check

```bash
# Check container health
docker ps

# View logs
docker logs finance-system-app

# Check health endpoint
curl http://localhost:8080/actuator/health
```

## Stop and Remove

```bash
# Stop container
docker-compose down

# Remove image
docker rmi finance-system:latest
```
