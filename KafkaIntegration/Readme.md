
* **DeliveryboyKafkaIntergration:**  It has been created to produces message.
* **EndUserKafkaIntergration:**  It has been created to consumes message.


# STEP 1: GET KAFKA OR DOWNLOAD KAFKA
<a href="https://kafka.apache.org/downloads" target="_blank">Download</a> the latest Kafka release and extract it:


* **For Linux/Ubuntu and Macos**
```
tar -xzf kafka_2.13-3.7.0.tgz //Rename 'kafka_2.13-3.7.0' to 'kafka'
cd kafka
```
* **For Windows**
```
Extract file manually and rename 'kafka_2.13-3.7.0' to 'kafka'
```

# Step 2: START THE KAFKA ENVIRONMENT
First of all, we should navigate to the Kafka directory and execute the commands below in the terminal.
### # Start the ZooKeeper service
* **For Linux/Ubuntu and Macos**
```
bin/zookeeper-server-start.sh config/zookeeper.properties
```
* **For Windows**
```
bin\windows\zookeeper-server-start.bat config\zookeeper.properties
```


### # Start the Kafka broker service
* **For Linux/Ubuntu and Macos**
```
bin/kafka-server-start.sh config/server.properties
```
* **For Windows**
```
bin\windows\kafka-server-start.bat config/server.properties
```
Once all services have successfully launched, you will have a basic Kafka environment up and running, ready to be used

# STEP 3: CREATE A TOPIC TO STORE YOUR EVENTS
So, before you can write your first events, you must create a topic. Open another terminal session and execute the following command:

* **For Linux/Ubuntu and Macos**
```
bin/kafka-topics.sh --create --topic your-topic-name --bootstrap-server localhost:9092
```

* **For Windows**
```
bin\windows\kafka-topics.bat --create --topic your-topic-name --bootstrap-server localhost:9092
```

# STEP 4: WRITE SOME EVENTS INTO THE TOPIC
A Kafka client communicates with the Kafka brokers over the network for writing (or reading) events. Once received, the brokers store the events in a durable and fault-tolerant manner for as long as necessary, even indefinitely.

Execute the console producer client to write a few events into your topic. By default, each line you enter will produce a separate event in the topic.
* **For Linux/Ubuntu and Macos**
```
bin/kafka-console-producer.sh --topic your-topic-name --bootstrap-server localhost:9092
```

* **For Windows**
```
bin\windows\kafka-console-producer.bat --topic your-topic-name --bootstrap-server localhost:9092
```

# STEP 5: READ THE EVENTS
Open another terminal session and execute the console consumer client to read the events you just created:
* **For Linux/Ubuntu and Macos**
```
bin/kafka-console-consumer.sh --topic your-topic-name --from-beginning --bootstrap-server localhost:9092
```

* **For Windows**
```
bin\windows\kafka-console-consumer.bat --topic your-topic-name --from-beginning --bootstrap-server localhost:9092
```
You can stop the consumer client with Ctrl-C at any time.


# KAFKA INTEGRATION WITH SPRINGBOOT PROJECT
Here we have created two project first one is DeliveryboyKafkaIntergration which produce messages and second one is EndUseKafkaIntergration which consume messages

## For Produce service
Inside the Config package, we will create two java classes **(AppConstant and KafkaConfig)**.</br>

**AppConstant:** In this class, we will create constant variables and their respective values.</br>
**KafkaConfig:** In this class, We will create topics.

#### AppConstant.class
```
package com.apache.kafka.config;
public class AppConstant {
    public static final String LOCATION_TOPIC_NAME="location-update-topic";
}
```

#### KafkaConfig.class
```
@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic topic(){
        return TopicBuilder.name(AppConstant.LOCATION_TOPIC_NAME).build();
    }
}
```


#### KafkaService.class
We will create a **KafkaService** class in service package to produce messages.
```
@Service
public class KafkaService {
    private Logger log = LoggerFactory.getLogger(KafkaService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public boolean updateLocation(String location){
        this.kafkaTemplate.send(AppConstant.LOCATION_TOPIC_NAME, location);
        this.log.info("Message produced");
        return true;
    }
}
```

#### KafkaController.class
We will create a **KafkaController** class in the controller package. Inside this class, we will define REST APIs using the **@RestController** annotation to send messages.
```
@RestController
@RequestMapping("/location")
public class KafkaController {

    @Autowired
    private KafkaService kafkaService;

    @PostMapping("/update")
    public ResponseEntity<?> updateLocation(){
        this.kafkaService.updateLocation("("+Math.round(Math.random() * 100)+", "+Math.round(Math.random() * 100)+")");
        return new ResponseEntity<>(Map.of("message","Message updated"), HttpStatus.OK);
    }
}
```

### application.properties for producer
```
# Kafka Producer Configuration
spring.kafka.producer.bootstrap-servers=localhost:9092
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer
```
In this example:

* **spring.kafka.producer.bootstrap-servers** specifies the Kafka brokers to which the producer will connect.
* **spring.kafka.producer.key-serializer** specifies the serializer for keys.
* **spring.kafka.producer.value-serializer** specifies the serializer for values.


## For Consume service
Inside the Config package, we will create two java classes **(AppConstant and KafkaConfig)**.</br>

**AppConstant:** In this class, we will create constant variables and their respective values.</br>
**KafkaConfig:** In this class, We will listen topics.

#### AppConstant.class
```
package com.apache.kafka.config;
public class AppConstant {
    public static final String LOCATION_TOPIC_NAME="location-update-topic";
    public static final String LOCATION_GROUP_ID="group-1";
}
```
#### KafkaConfig.class
```
package com.apache.kafka.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class KafkaConfig {
    private Logger log = LoggerFactory.getLogger(KafkaConfig.class);
    @KafkaListener(topics = AppConstant.LOCATION_TOPIC_NAME, groupId = AppConstant.LOCATION_GROUP_ID)
    public void updateLocation(String value){
        log.info(value);
    }
}
```
### application.properties for consumer
```
# Kafka Consumer Configuration
spring.kafka.consumer.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=my-group
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer
```
In this configuration:

* **spring.kafka.consumer.bootstrap-servers** specifies the Kafka brokers to which the consumer will connect.
* **spring.kafka.consumer.group-id** specifies the consumer group id.
* **spring.kafka.consumer.auto-offset-reset** specifies the offset reset behavior.
* **spring.kafka.consumer.key-deserializer** specifies the deserializer for keys.
* **spring.kafka.consumer.value-deserializer** specifies the deserializer for values.



# KAFKA INTEGRATION WITH SPRINGBOOT-CAMEL PROJECT