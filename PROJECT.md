<p align="center"> Welcome to this project specific details</p>

Make sure you have read the <a href="https://github.com/sgenlecroyant/Kafka_streams-The_Big_Data_Move/blob/master/README.md"> `README` </a> file for some other reference of information related to this project.

## Here you find necessary scripts that you will need to execute to start using this project
> 0. I won't dive into explaining about bash scripting here, just the basics can help.
> 1. First and foremost, you need to have
Docker installed on your machine, if so, then you can proceeed.
> 2. Both sh and bash  shells are supported by default on any unix-like environment, so no worries.

> We need to make sure `Kafka` and `Zookeeper` are running because `Kafka Streams` is built on top of `Apache Kafka`, in fact, it's an additional layer for `stream-processing` use-cases

1. from the `scripts` folder:

    * execute the `start_docker.sh` script to start docker, you can do it with elevated privileged or let the login keyring pop up for you.

    * At the root directory of the `scripts` folder, run `docker-compose -f spin-kafka-zookeeper-servers.yml up` , make sure you do it with elevated privileged if you have not configured `Docker` to run without this being configured.

    * Next, you need to execute the `create-topics.sh` script to create some `topics` that we will need, we will create more as the needs arise.