package br.com.caelum.camel;

import java.text.SimpleDateFormat;

import br.com.caelum.camel.dto.Negociacao;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.thoughtworks.xstream.XStream;
import org.apache.camel.CamelContext;
import org.apache.camel.dataformat.xstream.XStreamDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;

public class RotaHttpPollingNegociacoes {

	public static void main(String[] args) throws Exception {

		final SimpleRegistry registry = new SimpleRegistry();
		registry.put("mysql", createDataSource());
		final CamelContext context = new DefaultCamelContext(registry);
		context.addRoutes(CamelUtils.routerBuilder(router -> {

			final XStream xstream = new XStream();
			xstream.alias("negociacao", Negociacao.class);

			router.from("timer://negociacoes?fixedRate=true&delay=1s&period=360s")
					.to("http4://argentumws-spring.herokuapp.com/negociacoes")
					.convertBodyTo(String.class)
					.unmarshal(new XStreamDataFormat(xstream))
					.split(router.body())
					.process(exchange -> {
						final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						final Negociacao negociacao = exchange.getIn().getBody(Negociacao.class);
						exchange.setProperty("preco", negociacao.getPreco());
						exchange.setProperty("quantidade", negociacao.getQuantidade());
						exchange.setProperty("data", formatter.format(negociacao.getData().getTime()));
					})
					.setBody(router.simple("INSERT INTO negociacao(preco, quantidade, data) VALUES (${property.preco}, ${property.quantidade}, '${property.data}')"))
					.log("${body}")
					.delay(1000L)
					.to("jdbc:mysql")
					.end();
		}));

		context.start();
		Thread.sleep(20_000L);
		context.stop();
	}

	private static MysqlConnectionPoolDataSource createDataSource() {
		MysqlConnectionPoolDataSource mysqlDs = new MysqlConnectionPoolDataSource();
		mysqlDs.setDatabaseName("camel");
		mysqlDs.setServerName("localhost");
		mysqlDs.setPort(3306);
		mysqlDs.setUser("root");
		mysqlDs.setPassword("negociacao");
		return mysqlDs;
	}
}
