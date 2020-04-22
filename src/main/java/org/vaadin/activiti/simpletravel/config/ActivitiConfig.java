package org.vaadin.activiti.simpletravel.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.jpa.AvailableSettings;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class ActivitiConfig {
	
    private final Environment env;

    public ActivitiConfig(Environment env) {
        this.env = env;
    }
    
    // ============================================
    // application-context-process.xml
    // ============================================
	
    @Bean(destroyMethod = "close")
    @Qualifier("dataSource")
	public DataSource dataSource(){
		org.apache.commons.dbcp.BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
		basicDataSource.setUrl(env.getProperty("spring.datasource.url"));
		basicDataSource.setUsername(env.getProperty("spring.datasource.username"));
		basicDataSource.setPassword(env.getProperty("spring.datasource.password"));
		basicDataSource.setDefaultAutoCommit(false);
		return basicDataSource;
	}
	
//    @Bean
//    @Qualifier("eclipseLinkVendorAdapter")
//	public org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter eclipseLinkVendorAdapter(){
//		EclipseLinkJpaVendorAdapter eclipseLinkJpaVendorAdapter = new EclipseLinkJpaVendorAdapter();
//		eclipseLinkJpaVendorAdapter.setDatabasePlatform("HSQL");
//		return eclipseLinkJpaVendorAdapter;
//	}
//
//    @Bean
//    @Qualifier("entityManagerFactory")
//    @DependsOn({"dataSource","eclipseLinkVendorAdapter"})
//	public org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean entityManagerFactory(){
//		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//		localContainerEntityManagerFactoryBean.setDataSource(dataSource());
//		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(eclipseLinkVendorAdapter());
//		return localContainerEntityManagerFactoryBean;
//	}
//
//    @Bean
//    @Qualifier("transactionManager")
//    @DependsOn({"entityManagerFactory"})
//	public org.springframework.orm.jpa.JpaTransactionManager transactionManager(){
//		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
//		jpaTransactionManager.setEntityManagerFactory((EntityManagerFactory) entityManagerFactory());
//		return jpaTransactionManager;
//	}
    
    // ????????????????????????????????????????

    @Bean
    @DependsOn({"dataSource"})
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
         LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
         entityManagerFactoryBean.setJpaVendorAdapter(vendorAdaptor());
         entityManagerFactoryBean.setDataSource(dataSource());
         entityManagerFactoryBean.setJpaDialect(new HibernateJpaDialect());
         //entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
         entityManagerFactoryBean.setPackagesToScan(new String[] { "org.vaadin.activiti.simpletravel.domain","org.vaadin.activiti.simpletravel.alexdp.model"});             
         entityManagerFactoryBean.setJpaProperties(jpaHibernateProperties());
         return entityManagerFactoryBean;
    	  //return (EntityManagerFactory) this.jpaEntityManagerFactory;
     }
    
    @Bean
    @Qualifier("transactionManager")
    //@DependsOn({"entityManagerFactory"})
    public org.springframework.orm.jpa.JpaTransactionManager transactionManager(){
    	JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
    	jpaTransactionManager.setEntityManagerFactory(((EntityManagerFactory)entityManagerFactory().getObject()));
    	return jpaTransactionManager;
    }
    
    private HibernateJpaVendorAdapter vendorAdaptor() {
    	HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    	vendorAdapter.setShowSql(true);
    	vendorAdapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect");
    	return vendorAdapter;
    }
    private Properties jpaHibernateProperties() {

    	Properties properties = new Properties();

//    	properties.put(PROPERTY_NAME_HIBERNATE_MAX_FETCH_DEPTH, env.getProperty(PROPERTY_NAME_HIBERNATE_MAX_FETCH_DEPTH));
//    	properties.put(PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE, env.getProperty(PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE));
//    	properties.put(PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE, env.getProperty(PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE));
//    	properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
    	properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
    	properties.put(AvailableSettings.SCHEMA_GEN_DATABASE_ACTION, "none");
    	//properties.put(AvailableSettings.USE_CLASS_ENHANCER, "false");      
    	return properties;       
    }

	//Exposing the Activiti services as beans
    
    @Bean
    @Qualifier("processEngineConfiguration")
    //@DependsOn({"dataSource","transactionManager","entityManagerFactory"})
    public org.activiti.spring.SpringProcessEngineConfiguration processEngineConfiguration(){
    	SpringProcessEngineConfiguration springProcessEngineConfiguration = new SpringProcessEngineConfiguration();
    	springProcessEngineConfiguration.setDataSource(dataSource());
    	springProcessEngineConfiguration.setTransactionManager(transactionManager());
    	springProcessEngineConfiguration.setDatabaseSchemaUpdate("true");
    	springProcessEngineConfiguration.setJobExecutorActivate(true);
    	//Mail server configuration
    	springProcessEngineConfiguration.setMailServerPort(Integer.parseInt(env.getProperty("spring.mail.port")));//9898
    	//Hook in JPA
    	//springProcessEngineConfiguration.setJpaEntityManagerFactory(entityManagerFactory());
    	springProcessEngineConfiguration.setJpaEntityManagerFactory(((EntityManagerFactory)entityManagerFactory().getObject()));
    	springProcessEngineConfiguration.setJpaHandleTransaction(false);
    	springProcessEngineConfiguration.setJpaCloseEntityManager(false);
    	return springProcessEngineConfiguration;
    	
    }
    
    @Bean
    @Qualifier("processEngine")
    @DependsOn({"processEngineConfiguration"})
    public ProcessEngineFactoryBean processEngine(){
    	ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
    	processEngineFactoryBean.setProcessEngineConfiguration(processEngineConfiguration());
    	return processEngineFactoryBean;
    }
 
    @Bean
    @Qualifier("repositoryService")
    @DependsOn({"processEngine"})
    public RepositoryService repositoryService(@Qualifier("processEngine") ProcessEngineFactoryBean processEngine) {
    	return processEngine.getProcessEngineConfiguration().getRepositoryService();
    }
    
    @Bean
    @Qualifier("runtimeService")
    @DependsOn({"processEngine"})
    public RuntimeService runtimeService(@Qualifier("processEngine") ProcessEngineFactoryBean processEngine) {
    	return processEngine.getProcessEngineConfiguration().getRuntimeService();
    }
    
    @Bean
    @Qualifier("taskService")
    @DependsOn({"processEngine"})
    public TaskService taskService(@Qualifier("processEngine") ProcessEngineFactoryBean processEngine) {
    	return processEngine.getProcessEngineConfiguration().getTaskService();
    }
    
    @Bean
    @Qualifier("historyService")
    @DependsOn({"processEngine"})
    public HistoryService historyService(@Qualifier("processEngine") ProcessEngineFactoryBean processEngine) {
    	return processEngine.getProcessEngineConfiguration().getHistoryService();
    }
    
    @Bean
    @Qualifier("managementService")
    @DependsOn({"processEngine"})
    public ManagementService managementService(@Qualifier("processEngine") ProcessEngineFactoryBean processEngine) {
    	return processEngine.getProcessEngineConfiguration().getManagementService();
    }
    
    @Bean
    @Qualifier("identityService")
    @DependsOn({"processEngine"})
    public IdentityService identityService(@Qualifier("processEngine") ProcessEngineFactoryBean processEngine) {
    	return processEngine.getProcessEngineConfiguration().getIdentityService();
    }
    
    @Bean
    @Qualifier("formService")
    @DependsOn({"processEngine"})
    public FormService formService(@Qualifier("processEngine") ProcessEngineFactoryBean processEngine) {
    	return processEngine.getProcessEngineConfiguration().getFormService();
    }


}
