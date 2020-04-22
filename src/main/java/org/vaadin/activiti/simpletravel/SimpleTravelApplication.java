package org.vaadin.activiti.simpletravel;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ServletComponentScan

//@EnableSpringConfigured
//@EnableTransactionManagement
//@ImportResource({
//    //"classpath:application-context-process.xml", 
//    //"classpath:application-context-ui.xml"
//    "application-context.xml"
//})
@SpringBootApplication(scanBasePackages = { "org.vaadin.activiti.simpletravel"} )
//https://stackoverflow.com/questions/29221645/cant-autowire-repository-annotated-interface-in-spring-boot
@EnableJpaRepositories(basePackages = { "org.vaadin.activiti.simpletravel"})
@EntityScan(basePackages = { "org.vaadin.activiti.simpletravel"})
public class SimpleTravelApplication  { //extends SpringBootServletInitializer {

    private static final Logger log = LoggerFactory.getLogger(SimpleTravelApplication.class);

    private final Environment env;

    public SimpleTravelApplication(Environment env) {
        this.env = env;
    }
    
	public static void main(String[] args) {
		try {
			//MOD 4535992
			//SpringApplication.run(MyApp.class, args);
			SpringApplication app = new SpringApplication(SimpleTravelApplication.class);
	        app.setBannerMode(Banner.Mode.OFF);
	        app.setLogStartupInfo(false);
	        Environment env = app.run(args).getEnvironment();
	        logApplicationStartup(env);
		}catch(Throwable ex) {
			ex.printStackTrace();
		}
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        if (StringUtils.isBlank(contextPath)) {
            contextPath = "/";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info("\n----------------------------------------------------------\n\t" +
                "Application '{}' is running! Access URLs:\n\t" +
                "Local: \t\t{}://localhost:{}{}\n\t" +
                "External: \t{}://{}:{}{}\n\t" +
                "Profile(s): \t{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            protocol,
            serverPort,
            contextPath,
            protocol,
            hostAddress,
            serverPort,
            contextPath,
            env.getActiveProfiles());
    }

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(MyApp.class);
//	}

	@Bean
	InitializingBean usersAndGroupsInitializer(final IdentityService identityService) {
		return new InitializingBean() {
			public void afterPropertiesSet() throws Exception {
				Group attendees = identityService.newGroup("applicants");
				identityService.saveGroup(attendees);
				Group staff = identityService.newGroup("staff");
				identityService.saveGroup(staff);

				User kermit = identityService.newUser("kermit");
				identityService.saveUser(kermit);
				identityService.createMembership("kermit", "applicants");

				User gonzo = identityService.newUser("gonzo");
				identityService.saveUser(gonzo);
				identityService.createMembership("gonzo", "staff");
			}
		};
	}

	@Bean
	public BeanPostProcessor activitiConfigurer() {
		return new BeanPostProcessor() {
			@Override
			public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
				if (bean instanceof SpringProcessEngineConfiguration) {
					SpringProcessEngineConfiguration config = (SpringProcessEngineConfiguration) bean;
					//MOD 4535992
//					config.setMailServerHost("smtp.gmail.com");
//					config.setMailServerPort(587);
//					config.setMailServerUseSSL(false);
//					config.setMailServerUseTLS(true);
//					config.setMailServerDefaultFrom("alexandre.de.pellegrin@gmail.com");
//					config.setMailServerUsername("alexandre.de.pellegrin@gmail.com");
//					config.setMailServerPassword("xxxxxxx");
					
					config.setMailServerHost(env.getProperty("spring.mail.host"));
					config.setMailServerPort(Integer.parseInt(env.getProperty("spring.mail.port")));
					config.setMailServerUseSSL(false);
					config.setMailServerUseTLS(true);
					config.setMailServerDefaultFrom(env.getProperty("spring.mail.username"));
					config.setMailServerUsername(env.getProperty("spring.mail.username"));
					config.setMailServerPassword(env.getProperty("spring.mail.password"));
				}
				return bean;
			}

			@Override
			public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
				return bean;
			}

		};
	}

}
