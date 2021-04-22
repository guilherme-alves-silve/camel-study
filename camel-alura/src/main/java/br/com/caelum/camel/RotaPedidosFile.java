package br.com.caelum.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaPedidosFile {

	public static void main(String[] args) throws Exception {

		final CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				this.from("file:pedidos?delay=5s&noop=true")
				.log("${exchange.pattern}")
				//.log("Before: ${id} - ${body}")
				.split()
						.xpath("/pedido/itens/item")
				.filter()
						.xpath("/item/formato[text()='EBOOK']")
				.marshal()
				.xmljson()
				.log("After: ${id} - ${body}")
				.setHeader(Exchange.FILE_NAME, simple("${file:name.noext}-${header.CamelSplitIndex}.json"))
				.to("file:saida");
			}
		});

		context.start();
		Thread.sleep(20_000L);
		context.stop();
	}	
}
