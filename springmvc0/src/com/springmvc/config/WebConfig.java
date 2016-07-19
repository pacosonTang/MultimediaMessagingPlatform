package com.springmvc.config;

import java.io.IOException;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.springmvc.controller", "com.springmvc.repo", "com.springmvc.component" })
@Import({WebSocketStompConfig.class})
public class WebConfig extends WebMvcConfigurerAdapter {

	// integrate apache tiles framework.
	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tiles = new TilesConfigurer();
		tiles.setDefinitions(new String[] { "/WEB-INF/layout/tiles.xml" });
		tiles.setCheckRefresh(true);
		return tiles;
	}
	
	// config processing for static resources.
	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	// Tiles View Resolver.
	@Bean
	public ViewResolver tViewResolver() {
		TilesViewResolver resolver = new TilesViewResolver();
		return resolver;
	}

	// InternalResourceViewResolver
	@Bean
	public ViewResolver iViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setExposeContextBeansAsAttributes(true);
		resolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
		return resolver;
	}
	
	// REST == REpresentational State Transfer-表述性状态转移
	/*@Bean
	public ViewResolver cnViewResolver(ContentNegotiationManager cnm) {
		ContentNegotiatingViewResolver cnvr = 
				new ContentNegotiatingViewResolver();
		cnvr.setContentNegotiationManager(cnm);
		return cnvr;
	}*/
	
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}

	// multi media msg resolver.
	@Bean
	public MultipartResolver multipartResolver() throws IOException {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setUploadTempDir(new FileSystemResource(
				"/WEB-INF/tmp/spittr/uploads"));
		multipartResolver.setMaxUploadSize(2097152);
		multipartResolver.setMaxInMemorySize(0);
		return multipartResolver;
	}

	/*
	@Override
	public void configureContentNegotiation(
			ContentNegotiationConfigurer configurer) {
		// 渲染为 json 输出.
//		configurer.defaultContentType(MediaType.APPLICATION_JSON_UTF8);
		configurer.defaultContentType(MediaType.TEXT_HTML);  // 默认为 html 输出.
	}
	
	// 以bean 的形式查找视图.
	@Bean
	public ViewResolver bnViewResolver() {
		return new  BeanNameViewResolver();
	}
	
	//将 "spittles" 定义为 json 视图.
	@Bean
	public View spittles() {
		return new MappingJackson2JsonView();
	}
	*/
}
