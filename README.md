# Estudo de Apache Camel

Estudo do curso da Alura de [Apache Camel](https://camel.apache.org/), 
aonde contém o código de estudo do Apache Camel e também um web-service utilizado
nos exercícios, que é tanto um servidor [API Rest](https://pt.wikipedia.org/wiki/REST) 
quanto um servidor [SOAP](https://pt.wikipedia.org/wiki/SOAP).
No projeto, é utilizado também o [XSLT](https://www.w3schools.com/xml/xsl_intro.asp).

## Bibliotecas e Ferramentas
* [Java 11+](https://www.java.com/pt-BR/download/help/java8_pt-br.html)
* [Apache Camel](https://camel.apache.org/)
* [XStream](https://x-stream.github.io/)
* [Log4j](https://logging.apache.org/log4j/2.x/)
* [MySQL](https://www.mysql.com/)
* [Docker](https://www.docker.com/)
* [Apache Tomcat 8+](http://tomcat.apache.org/)
* [SoapUI](https://www.soapui.org/)
* [Velocity Apache](http://velocity.apache.org/)
* [Apache ActiveMQ 5+](https://activemq.apache.org/)

* [Apache Camel: O framework de integração entre sistemas](https://cursos.alura.com.br/course/camel)
* Aulas
    * A primeira rota com Camel Ver primeiro vídeo
    * Separando e filtrando mensagens
    * Conectando endpoints HTTP
    * Melhor legibilidade com sub-rotas
    * Transformação com XSLT e integração com serviço SOAP
    * Validação de mensagens e tratamento de erros
    * Enviando e recebendo mensagens JMS

## Configuração

Executar `docker-compose up` dentro da pasta camel-study, e após isso dar build
na aplicação webservices, e realizar o deploy do war, no Apache Tomcat, ou o
servidor container de preferência.
É necessário criar no [endereço](http://localhost:8161/admin/queues.jsp), 
acesso com o usuário e senha admin para ambos, após isso, é necessário
criar as filas pedidos e pedidos.DLQ.

Arquitetura do projeto (Retirado do curso da Alura)
![Arquitetura Projeto](rota_pedidos.png?raw=true "Arquitetura do projeto (Retirado do curso da Alura)")
