package pl.jellytech.machiavelli.users;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig extends HikariConfig {
    @Value("${machiavelli.user.datasource.url}")
    private String url;
    @Value("${machiavelli.user.datasource.driverClassName}")
    private String driverClassName;
    @Value("{machiavelli.user.datasource.username}")
    private String username;
    @Value("{machiavelli.user.datasource.password}")
    private String password;
    @Bean
    public HikariConfig hikariConfig(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(this.url);
        config.setDriverClassName(this.driverClassName);
        config.setUsername(this.username);
        config.setPassword(this.password);
        return config;
    }
    @Bean
    public DataSource dataSource(){
        DataSource ds = new HikariDataSource(hikariConfig());
        return ds;
    }
}
