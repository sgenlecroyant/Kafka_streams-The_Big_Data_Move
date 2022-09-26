#!/bin/bash
#Opening Docker, only if is not up and running
if (! docker stats --no-stream ); then
 #Wait until Docker daemon is running and has completed its initialization process
while (! docker stats --no-stream ); do
  echo "Wait for Docker to start..."

  sudo systemctl start docker
done
  echo "DOCKER HAS ALREADY STARTED !!!"
fi