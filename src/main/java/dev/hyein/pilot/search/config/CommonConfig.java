package dev.hyein.pilot.search.config;

import dev.hyein.pilot.search.config.factory.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource(value = "config.yml", ignoreResourceNotFound = true, factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "common")
public class CommonConfig {
    private String csvPath;
    private String indexPath;

}
