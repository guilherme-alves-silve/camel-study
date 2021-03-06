package br.com.caelum.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.common.HttpMethods;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaPedidosHttpPost {

	public static void main(String[] args) throws Exception {

		final CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				this.from("file:pedidos?delay=5s&noop=true")
				.split()
						.xpath("/pedido/itens/item")
				.filter()
						.xpath("/item/formato[text()='EBOOK']")
				.marshal()
				.xmljson()
				.log("${id} \n ${body}")
				.setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.POST))
				.to("http4://localhost:8080/webservices/ebook/item");
			}
		});

		context.start();
		Thread.sleep(20_000L);
		context.stop();
	}	
}
