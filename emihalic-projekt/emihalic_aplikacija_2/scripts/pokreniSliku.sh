#!/bin/bash
NETWORK=emihalic_mreza_1

docker run -it -d \
  -p 8070:8080 \
  --network=$NETWORK \
  --ip 200.20.0.4 \
  --name=emihalic_payara_micro \
  --hostname=emihalic_payara_micro \
  emihalic_payara_micro:6.2023.4 \
  --deploy /opt/payara/deployments/emihalic_aplikacija_2-1.0.0.war \
  --contextroot emihalic_aplikacija_2 \
  --noCluster &

wait
