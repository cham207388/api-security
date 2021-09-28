package com.abc.apidemo.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("email")
@Getter
@Setter
public class MailConfig {
	private String from;
}