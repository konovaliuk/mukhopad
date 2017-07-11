package ua.training.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

@Configuration
@ComponentScan
public class ApplicationContextConfig {
    @Bean
    public DataSource dataSource() {
        JndiDataSourceLookup ds = new JndiDataSourceLookup();
        ds.setResourceRef(true);
       return ds.getDataSource("java:comp/env/jdbc/Periodicals");
    }
    @Bean
    JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
