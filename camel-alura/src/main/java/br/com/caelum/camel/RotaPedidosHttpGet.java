package br.com.caelum.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaPedidosHttpGet {

	public static void main(String[] args) throws Exception {

		final CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				this.from("file:pedidos?delay=5s&noop=true")
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
			}
		});

		context.start();
		Thread.sleep(20_000L);
		context.stop();
	}	
}
