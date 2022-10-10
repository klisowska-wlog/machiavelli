package pl.jellytech.machiavelli.cards;

import com.codahale.metrics.MetricRegistry;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jellytech.machiavelli.cards.utils.TimeUtils;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig extends com.zaxxer.hikari.HikariConfig {
    private MetricRegistry  metricRegistry;
    @Autowired
    public DataSourceConfig(MetricRegistry metricRegistry){
        this.metricRegistry = metricRegistry;
    }
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
    private final int minimumIdle = 10;

    private final int maximumPoolSize = this.minimumIdle;

    @Bean
    @ConfigurationProperties(prefix = "machiavelli.card.datasource")
    public HikariConfig hikariConfig() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(this.url);
        config.setDriverClassName(this.driverClassName);
        config.setUsername(this.username);
        config.setPassword(this.password);
        config.setConnectionTimeout(TimeUtils.MinutesToMilis(this.connectionTimeout));
        config.setIdleTimeout(TimeUtils.MinutesToMilis(this.IdleTimeout));
        config.setKeepaliveTime(TimeUtils.MinutesToMilis(this.keepAliveTimeout));
        config.setMaxLifetime(TimeUtils.MinutesToMilis(this.maxLifetime));
        config.setAutoCommit(true);
        config.setMinimumIdle(this.minimumIdle);
        config.setMaximumPoolSize(this.maximumPoolSize);
        config.setMetricRegistry(this.metricRegistry);
        return config;
    }

    @Bean
    public DataSource dataSource() {
        DataSource ds = new HikariDataSource(hikariConfig());
        return ds;
    }
}
