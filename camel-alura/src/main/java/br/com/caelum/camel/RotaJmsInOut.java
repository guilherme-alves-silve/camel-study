package br.com.caelum.camel;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaJmsInOut {

    public static void main(String[] args) throws Exception {
        final CamelContext context = new DefaultCamelContext();
        context.addComponent("activemq", ActiveMQComponent.activeMQComponent("tcp://localhost:9191"));

        context.addRoutes(new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                this.from("activemq:queue:pedidos.req")
                    .log("Pattern: ${exchange.pattern} - ${body}")
                    .setHeader(Exchange.FILE_NAME, constant("mensagem.txt"))
                    .to("file:saida");
            }
        });

        context.start();
        Thread.sleep(20_000L);
        context.stop();
    }
}
