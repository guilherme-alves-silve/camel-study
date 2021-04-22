package br.com.caelum.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaVelocity {

    public static void main(String[] args) throws Exception {

        final CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                this.from("direct:entrada")
                    .setHeader("data", constant("8/12/2020"))
                    .to("velocity:template.vm")
                    .log("${body}");
            }
        });

        context.start();

        final ProducerTemplate producer = context.createProducerTemplate();
        producer.sendBody("direct:entrada", "Apache Camel rocks!!!");

        Thread.sleep(20_000L);
        context.stop();
    }
}
