package zihong.wu.cloudnote.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.yml")
public class CommonConfig {
    @Value("${rsocket.server.port:8080}")
    public String port;

    @Value("${rsocket.server.address:http://127.0.0.1}")
    public String address;
}
