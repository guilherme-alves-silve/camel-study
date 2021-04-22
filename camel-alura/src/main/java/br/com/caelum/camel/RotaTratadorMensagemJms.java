package br.com.caelum.camel;

import br.com.caelum.camel.jms.TratadorMensagemJms;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaTratadorMensagemJms {

    public static void main(String[] args) throws Exception {

        final CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                this.from("activemq:queue:pedidos.req")
                    .bean(TratadorMensagemJms.class)
                    .log("Pattern: ${exchange.pattern}")
                    .setBody(constant("novo conteudo da mensagem"))
                    .setHeader(Exchange.FILE_NAME, constant("teste.txt"))
                    .to("file:saida");
            }
        });

        context.start();
        Thread.sleep(20_000L);
        context.stop();
    }
}
