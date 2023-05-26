package com.example.demo;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

//@SpringBootApplication
@Log4j2
@EnableCassandraRepositories
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
//		log.info("NEE ANATA WA ITSUMO YUME WO MITE MASU KA?");
	}
//	@Bean
//	public NewTopic topic() {
//		return TopicBuilder.name("utsup")
//				.partitions(10)
//				.replicas(1)
//				.build();
//	}
//
//	@KafkaListener(id = "Akasaka", topics = "utsup")
//	public void listen(String in) {
//		log.info("Listening: " + in);
//	}
//
//	@Bean
//	public ApplicationRunner runner(KafkaTemplate<String, String> template) {
//		return args -> {
//			template.send("utsup", "NEE ANATA WA ITSUMO YUME WO MITE MASU KA?");
//		};
//	}
}
