package com.springmvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	  public void configureMessageBroker(MessageBrokerRegistry config) {
	      config.enableSimpleBroker("/topic", "/queue");
	      config.setApplicationDestinationPrefixes("/app");
	      // 应用程序以 /app 为前缀，而 代理目的地以 /topic 为前缀.
	      // js.url = "/spring13/app/hello" -> @MessageMapping("/hello") 注释的方法.
	  }

	  @Override
	  public void registerStompEndpoints(StompEndpointRegistry registry) {
	      registry.addEndpoint("/chatlog").withSockJS();
	      // 在网页上我们就可以通过这个链接 /server/hello 来和服务器的WebSocket连接
	  }
}
