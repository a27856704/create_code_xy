package vip.sunke.createdb.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "pgsql")
@Data
public class SchemaListConfiguration {
    private List<String> schemaList;
}
