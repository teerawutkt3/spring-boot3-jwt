# Build
sh push-image-to-docker-hub.sh

# Service
kubectl delete -f k8s/k8s.yaml -n app-dev
kubectl apply -f k8s/k8s.yaml -n app-dev