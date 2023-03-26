# Kafka

## O que é um tópico?
- Um tópico é um canal de comunicação que gerencia as mensagens por meio dos serviços assinados nele.

## O que são produtores?
- Produtores são qualquer aplicação que produza mensagens para serem armazenadas no Apache do Kafka.

## O que são consumidores?
- Consumidores são qualquer serviço responsável por consumir os eventos produzidos pelos produtores e armazenados pelos brokers em um tópico.

## Observações Gerais
- Em instâncias do mesmo micro serviço que possui o mesmo grupo de consumo, apenas um micro serviço irá consumir a mensagem para não haver processamento redundante daquela mensagem, porque o paralelismo de consumo é feito por meio das partições. Não adianta ter mais de um consumidor no mesmo grupo de consumo do que o número de partições.
A key é a identificadora responsável por balancear o envio para determinadas partições. A chave é usada para distribuir a mensagem entre partições existentes e consequentemente entre as instâncias de um serviço dentro de um consumer group. Outro ponto importante é o offset que é a ordem da mensagem dentro da partição.

## Observações sobre balanceamento realizado pelo kafka no momento de consumo de mensagens:
- Para evitar problemas com balanceamento no momento de consumo, podemos configurar o max poll records com o valor 1, pois assim ele vai consumir de 1 e 1 e commitar o consumo.

## Como instalar e rodar o Kafka
- [Baixe o kafka apache binário ](https://kafka.apache.org/downloads)

Inicie o Zookeeper:
```
bin/zookeeper-server-start.sh config/zookeeper.properties 
```
Inicie o Kafka:
```
bin/kafka-server-start.sh config/server.properties
```
Crie um consumidor:
```
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic ECOMMERCE_NEW_ORDER –from-beginning
```
Crie um produtor:
```
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic LOJA_NOVO_PEDIDO
```
Descreva tópicos:
```
bin/kafka-topics.sh --bootstrap-server localhost:9092 –describe
```
Altere partições de tópicos:
```
bin/kafka-topics.sh --alter --bootstrap-server localhost:9092 --topic ECOMMERCE_NEW_ORDER --part
```

## Rodando o projeto
- Para isso, basta ir até a classe `newOrderMain` e rodá-la.


## Referências
[Formação Alura](https://www.alura.com.br/)