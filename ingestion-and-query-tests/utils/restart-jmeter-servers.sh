#!/bin/bash
servers=(10.1.0.4 10.1.0.5 10.1.0.6 10.1.0.7)
for server in "${servers[@]}"
do
  echo "restarting jmeter-server on: ${server}"
  pid="$(ssh -n ${server} 'lsof -ti tcp:1099')"
  echo $pid
  ssh -n $server "kill -9 ${pid}"
done
