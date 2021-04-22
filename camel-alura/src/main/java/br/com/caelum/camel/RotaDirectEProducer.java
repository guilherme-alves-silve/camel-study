package br.com.caelum.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaDirectEProducer {

    public static void main(String[] args) throws Exception {

        final CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                this.from("direct:soap")
                    .routeId("rota-soap")
                    .log("chamando serviço soap ${body}")
                    .to("mock:soap");

                this.from("direct:http")
                    .routeId("rota-http")
                    .log("chamando serviço http ${body}")
                    .to("mock:http");
            }
        });

        context.start();

        final ProducerTemplate producer = context.createProducerTemplate();
        producer.sendBody("direct:soap", "<pedido>...</pedido>");
        producer.sendBody("direct:http", "date=2019-10-11&name=John%20Wick");
        Thread.sleep(10_000L);

        context.stop();
    }
}
