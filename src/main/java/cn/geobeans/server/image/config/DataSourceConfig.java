package cn.geobeans.server.image.config;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    private AppProperty appProperty;

    @Autowired
    public DataSourceConfig(AppProperty appProperty) {
        this.appProperty = appProperty;
    }

    @Bean
    DataSource getDataSource(){
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:" + appProperty.getStorePath()+"/h2data");
        ds.setUser("sa");
        ds.setPassword("sa");

        return ds;
    }
}
