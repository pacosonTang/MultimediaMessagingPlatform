package com.springmvc.repo;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class RepositoryBase {

	@Bean
	public JdbcTemplate getJdbcTemplate(DataSource ds) {
		return new JdbcTemplate(ds);
	}

	// 使用数据源连接池 配置数据源
	@Bean
	public BasicDataSource getDataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/openfire3");
		ds.setUsername("root");
		ds.setPassword("root");
		return ds;
	}

	// 使用JNDI 数据源.
/*	@Bean
	public DataSource dataSource() {
		JndiTemplate jndiTemplate = new JndiTemplate();
		DataSource dataSource = null;

		try {
			dataSource = (DataSource) jndiTemplate
					.lookup("java:comp/env/jdbc/spring");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return dataSource;
	}*/

	/*
	 * @Bean public LocalEntityManagerFactoryBean entityManagerFactoryBean() {
	 * LocalEntityManagerFactoryBean emfb = new LocalEntityManagerFactoryBean();
	 * emfb.setPersistenceUnitName("spitterPU"); return emfb; }
	 */

	/*
	 * @Bean public LocalContainerEntityManagerFactoryBean entityManagerFactory(
	 * DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
	 * LocalContainerEntityManagerFactoryBean emfb = new
	 * LocalContainerEntityManagerFactoryBean(); emfb.setDataSource(dataSource);
	 * emfb.setJpaVendorAdapter(jpaVendorAdapter); return emfb; }
	 */

}
