package com.mbc.leteatgo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ResourceConfig implements WebMvcConfigurer {
	
	/**	add resource handler to serve 
	 *  static files from specific locations under web application root, the classpath and others
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		log.info("★ ResourceConfig의 addResourceHandlers는 언제 호출될까?");
		
		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
		
		registry.addResourceHandler("/jquery/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/jquery/");
		
		registry.addResourceHandler("/bootstrap/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/bootstrap/");
		
		registry.addResourceHandler("/bootstrap-icons/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/bootstrap-icons/");
		
	}// addResourceHandlers()

}// ResourceConfig
