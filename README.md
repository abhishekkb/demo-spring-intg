

# Using http gateway
curl --location --request POST 'localhost:8080/gateway/inbound' \
--header 'sample-header: yay' \
--header 'Content-Type: text/plain' \
--data-raw '"yaya"'

# Using channel based example
- Run JmsChannelConfigTest
  ## Flow
    - test will pass data to channel1
    - `jmsMessageHandler` gets the data from `channel1`
    - and `jmsMessageHandler` sends it to the queue (in this case it is an embedded ActiveMQ)
    - `jmsMessageDrivenEndpoint` gets the data from queue and sends it to `channel2`
    - 