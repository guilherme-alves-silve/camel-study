package br.com.caelum.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaPedidosDeadLetterChannelFile {

	public static void main(String[] args) throws Exception {

		final CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {

				this.errorHandler(deadLetterChannel("file:erro")
					.logExhaustedMessageHistory(true)
					.useOriginalMessage()
					.maximumRedeliveries(3)
					.redeliveryDelay(2000L)
					.onRedelivery(exchange -> {
						final int counter = (int) exchange.getIn().getHeader(Exchange.REDELIVERY_COUNTER);
						final int max = (int) exchange.getIn().getHeader(Exchange.REDELIVERY_MAX_COUNTER);
						System.out.println("Redelivery - " + counter + "/" + max );
					}));

				this.from("file:pedidos?delay=5s&noop=true")
					.routeId("rota-pedidos")
					.to("validator:pedido.xsd")
					.multicast()
						.to("direct:soap")
						.to("direct:http");

				this.from("direct:http")
					.routeId("rota-http")
					.setProperty("pedidoId", xpath("/pedido/id/text()"))
					.setProperty("clienteId", xpath("/pedido/pagamento/email-titular/text()"))
					.split()
							.xpath("/pedido/itens/item")
					.filter()
							.xpath("/item/formato[text()='EBOOK']")
							.setProperty("ebookId", xpath("/item/livro/codigo/text()"))
					.marshal()
						.xmljson()
					.log("${id} \n ${body}")
					.setHeader(Exchange.HTTP_QUERY, simple("pedidoId=${property.pedidoId}&clienteId=${property.clienteId}&ebookId=${property.ebookId}"))
					.to("http4://localhost:8080/webservices/ebook/item");

				this.from("direct:soap")
					.routeId("rota-soap")
					.log("teste")
					.to("xslt:pedido-para-soap.xslt")
					.log("Resultado do template: ${body}")
					.setHeader(Exchange.CONTENT_TYPE, constant("text/xml"))
					.to("http4://localhost:8080/webservices/financeiro");
			}
		});

		context.start();
		Thread.sleep(20_000L);
		context.stop();
	}	
}
