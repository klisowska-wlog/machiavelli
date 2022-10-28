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
    private MetricRegistry metricRegistry;

    @Autowired
    public DataSourceConfig(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    @Value("${machiavelli.card.datasource.meta.url}")
    private String url;
    @Value("${machiavelli.card.datasource.meta.driverClassName}")
    private String driverClassName;
    @Value("${machiavelli.card.datasource.meta.username}")
    private String username;
    @Value("${machiavelli.card.datasource.meta.password}")
    private String password;
    @Value("${machiavelli.card.datasource.connectionTimeout}")
    private int connectionTimeout;
    @Value("${machiavelli.card.datasource.idleTimeout}")
    private int IdleTimeout;
    @Value("${machiavelli.card.datasource.keepAliveTimeout}")
    private int keepAliveTimeout;
    @Value("${machiavelli.card.datasource.maxLifetime}")
    private int maxLifetime;
    @Value("${machiavelli.card.datasource.minimumIdle}")
    private int minimumIdle;
    @Value("${machiavelli.card.datasource.maximumPoolSize}")
    private int maximumPoolSize;
    @Value("${machiavelli.card.datasource.autoCommit}")
    private boolean autoCommit;

    @Bean
    @ConfigurationProperties(prefix = "machiavelli.card.datasource.meta")
    public HikariConfig hikariConfig() {
        this.setJdbcUrl(this.url);
        this.setDriverClassName(this.driverClassName);
        this.setUsername(this.username);
        this.setPassword(this.password);
        this.setConnectionTimeout(TimeUtils.minutesToMilis(this.connectionTimeout));
        this.setIdleTimeout(TimeUtils.minutesToMilis(this.IdleTimeout));
        this.setKeepaliveTime(TimeUtils.minutesToMilis(this.keepAliveTimeout));
        this.setMaxLifetime(TimeUtils.minutesToMilis(this.maxLifetime));
        this.setAutoCommit(this.autoCommit);
        this.setMinimumIdle(this.minimumIdle);
        this.setMaximumPoolSize(this.maximumPoolSize);
        this.setMetricRegistry(this.metricRegistry);
        return this;
    }

    @Bean
    public DataSource dataSource() {
        DataSource ds = new HikariDataSource(hikariConfig());
        return ds;
    }
}
