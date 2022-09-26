
<!-- 

<style> 
.connect > a {
        padding: 0px 10px;
}

.warming_message {
        padding: 8x;
        margin: 20px;
}

.reference {
        color: red;
        background-color: black;
        text-align: center;
        padding: 10px 10px;
        margin: 10px 10px;
}
</style>
-->


<h3 align="center" style="color: cyan; background-color: black">Kafka_streams-The_Big_Data_Move<h3>

Make sure you have read the <a href="https://github.com/sgenlecroyant/Kafka_streams-The_Big_Data_Move/blob/master/PROJECT.md"> `PROJECT.md` </a> file for some other reference of information related to this project.

***
***     
> What you need in other to run this data streaming application:

> 1. JAVA: Installed on your local machine, because I will not containerize this application, just for simplicity.

> 2. DOCKER: To get our Kafka and Zookeeper Servers up and running quickly.
            ==> You need to know a little bit about Docker and how to create persistent containers so you don't loose your data in case you wanna play with your data

> 3. Some BASH SCRIPTING knowledge to automate Kafka topics creation
        Technologies used in this project:
>
> ==> The Java Language, Apache MAVEN, The FAKER API to generate realistic data that we will need to simulate the streaming processing power of our application, Docker, Apache KAFKA and KAFKA STREAMS itself.
        
<div class="reference">

***You can refer to to this 
<a style="text-decoration: none; color: orange;" href="https://github.com/sgenlecroyant/Kafka_streams-The_Big_Data_Move/blob/master/PROJECT.md"> link
</a>
for more details about how you can seamlessly 
run this app to avoid confusion***

</div>

> In this project, i will be demonstrating how Kafka Streams fits into the Big Data area and the kinds of capabilities Kafka Streams has which can be considered as the Big pros for Kafka Streams over the other existing technologies in the game like Apache Flink, Hadoop, NiFi and Apache Spark Streaming. The language I will be using is Java and MAVEN as my project build and dependency management tool.

<br>
<br>


>
<h1 align="center" style="font-family:bold">        What you need to know about this project and KAFKA STREAMS
</h1>

## Our data streaming framework that I will be using is <span style="font-size:20px; color:orange; background-color: black">Apache Kafka Streams</span>


**Kafka Streams is available in 2 different APIs:**

1. The Low-Level API: Commonly known as the  <span style="font-size:15px; color:cyan; background-color: black"> Processor API </span> 
2. The High Level API: Where you feel the <span style="font-size:15px; color:cyan; background-color: black"> MAGIC </span>  of Kafka Streams and its <span style="font-size:15px; color:cyan; background-color: black"> POWER </span> 

<span style="border-bottom: 4px solid grey; padding-bottom: 2px"> What is Kafka Streams:<span>

<span style="color:cyan; background-color: black">Kafka Streams </span> is a stream processing library built on top of Apache Kafka, which is one of the top-choice technology when it comes to building data pipelines and and event-driven systems. <span style="color:cyan; background-color: black">Kafka Streams </span> follows the <span style="color:cyan; background-color: black"> KAPPA </span> architecture, processes data as we have any available flowing into our system, it does support <span style="color: orange; background-color: black"> per-event processing </span>ONLY, which a true streaming mechanism, whereas other big data technologies like <span style="background-color: grey; color: darkred">Apache Flink, NiFi, Hadoop and Apache Spark Streaming</span> follow the <span style="color:cyan; background-color: black"> Lambdas</span> architecture, which is kind of <span style="background-color: black"> Hybrid </span>, doing the classic <em><span style="color:orange; background-color: black"> Batch-Processing</span></em> and the <em> <span style="color:orange; background-color: black"> Micro-Batching</span> </em> which is nothing other than grouping batches within a small timespan.

<br>
<br>

> ### **About the language and programming principles for best practices to improve readability, maintainability and performance**

        
> 1. I will be using Java as the programming language
> 2. I will be coding so much to interfaces and abstraction for loose-coupling
> 3. I will be using both Object-Oriented programming and Functional Programming
> 4. I will be coding some threads using the Java multithreaded programming to improve performance whenever possible( when Speed is critical)

<h2 style="text-align:center; font-weight:bold">More importantly <h2>

***

> 1. I will be using some cool design patterns like BUILDER DESIGN PATTERN, SINGLETON DESIGN PATTERN, FACADE DESIGN PATTERN and MANY MORE whenever we find that we really need any, this can improve the flexibility and make our streaming application a lot more robust against FUTURE BREAKING CHANGES.

> 2. I will be some TDD(Test-Driven-Development) practices whenever it sounds necessary and do testing with TEST-CONTAINERS at the end of our journey.


<h2 style="background-color: black;color: green; font-weight: 900; font-size:3rem">The Licensing:  </h2> <hr>

> The entire project is under the Apache License 2.0, in compliance with Kafka and Kafka Streams License: learn more about Apache License Version 2 [HERE](https://www.apache.org/licenses/LICENSE-2.0) in case you don't know about it.

<!--Need to google to see if Kafka and Kafka Streams are GNU or MIT>
-->

<div class="warming_message" style="color:cyan; text-align:center; background-color:black"> Feel free to hit me up on any of these platforms below</div>

<div class="connect" align="center">

<a href="https://www.facebook.com/sgenlecroyant">
<img width="20" src="./src/main/resources/assets/images/facebook-svgrepo-com.svg"/>
</a>
<a href="https://www.twitter.com/sgenlecroyant">
<img width="20" src="./src/main/resources/assets/images/twitter-svgrepo-com.svg"/>
</a>
<a href="https://www.linkedin.com/in/sgenlecroyant">
<img width="20" src="./src/main/resources/assets/images/linkedin-svgrepo-com.svg"/>
</a>
<a href="mailto:sgenlecroyant@gmail.com">
<img width="20" src="./src/main/resources/assets/images/gmail-svgrepo-com.svg"/>
</a>
<a href="https://www.instagram.com/sgenlecroyant/">
<img width="20" src="./src/main/resources/assets/images/instagram-svgrepo-com.svg"/>
</a>

<img src="./readme_styles.svg" with="20px" height="20px"/>
</div>
