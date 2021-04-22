package br.com.caelum.camel;

import org.apache.camel.builder.RouteBuilder;

/**
 * @author Guilherme Alves Silveira
 */
public class CamelUtils {

    private CamelUtils() {
        throw new IllegalStateException("No CamelUtils!");
    }

    public static RouteBuilder routerBuilder(UncheckedConsumer<RouteBuilder> consumer) {
        return new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                consumer.apply(this);
            }
        };
    }

    @FunctionalInterface
    public interface UncheckedConsumer<T> {

        void apply(T routeBuilder) throws Exception;
    }
}
