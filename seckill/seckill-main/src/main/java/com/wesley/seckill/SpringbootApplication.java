package com.wesley.seckill;

import com.wesley.seckill.util.MyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@EnableRetry
@MapperScan(basePackages = "com.wesley.seckill.dao", markerInterface = MyMapper.class)
//@EnableDiscoveryClient  //服务发现
//@EnableFeignClients     //feign调用
public class SpringbootApplication {

	private final Logger logger = LoggerFactory.getLogger(SpringbootApplication.class);


	public static void main(String[] args) {

		SpringApplication.run(SpringbootApplication.class, args);
	}


	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			logger.info("Let's inspect the beans provided by Spring Boot:");

			//String[] beanNames = ctx.getBeanDefinitionNames();
			//Arrays.sort(beanNames);
			//for (String beanName : beanNames) {
			//	logger.info(beanName);
			//}

			logger.info("服务启动完成");


		};

	}


}
