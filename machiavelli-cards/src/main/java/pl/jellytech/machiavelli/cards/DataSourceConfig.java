package pl.jellytech.machiavelli.cards;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jellytech.machiavelli.cards.utils.TimeUtils;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig extends com.zaxxer.hikari.HikariConfig {

    @Value("${machiavelli.card.datasource.url}")
    private String url;
    @Value("${machiavelli.card.datasource.driverClassName}")
    private String driverClassName;
    @Value("{machiavelli.card.datasource.username}")
    private String username;
    @Value("{machiavelli.card.datasource.password}")
    private String password;
    private final int connectionTimeout = 5;
    private final int IdleTimeout = 2;
    private final int keepAliveTimeout = 10;
    private final int maxLifetime = 30;


    @Bean
    @ConfigurationProperties(prefix ="machiavelli.card.datasource")
    public HikariConfig hikariConfig() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(this.url);
        config.setDriverClassName(this.driverClassName);
        config.setUsername(this.username);
        config.setPassword(this.password);
        config.setConnectionTimeout(TimeUtils.MinutesToMilis(connectionTimeout));
        config.setIdleTimeout(TimeUtils.MinutesToMilis(IdleTimeout));
        config.setKeepaliveTime(TimeUtils.MinutesToMilis(keepAliveTimeout));
        config.setMaxLifetime(TimeUtils.MinutesToMilis(maxLifetime));
        return config;
    }

    @Bean
    public DataSource dataSource() {
        DataSource ds = new HikariDataSource(hikariConfig());
        return ds;
    }
}
