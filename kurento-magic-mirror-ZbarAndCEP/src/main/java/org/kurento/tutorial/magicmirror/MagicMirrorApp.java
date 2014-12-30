/*
 * (C) Copyright 2014 Kurento (http://kurento.org/)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 */
package org.kurento.tutorial.magicmirror;

import java.util.concurrent.ConcurrentHashMap;

import org.kurento.client.factory.KurentoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Magic Mirror main class.
 * 
 * @author Boni Garcia (bgarcia@gsyc.es)
 * @since 5.0.0
 */
@ComponentScan
@Configuration
@EnableWebSocket
@EnableAutoConfiguration
public class MagicMirrorApp implements WebSocketConfigurer {

	final static String DEFAULT_KMS_WS_URI = "ws://10.0.1.100:8888/kurento";
	final static String DEFAULT_CEP_HTTP_URI = "http://10.0.1.100:8080/ProtonOnWebServer/rest/events";
	
	
	
	//final static String DEFAULT_KMS_WS_URI = "ws://localhost:8888/kurento";

	@Bean
	public MagicMirrorHandler handler() {
		System.setProperty("DEFAULT_CEP_HTTP_URI", DEFAULT_CEP_HTTP_URI);
		return new MagicMirrorHandler();
	}

	@Bean
	public KurentoClient kurentoClient() {
		return KurentoClient.create(System.getProperty("kms.ws.uri",
				DEFAULT_KMS_WS_URI));
	}
	
	@Bean
	public ConcurrentHashMap<String, WebSocketSession> kurentoSession() {
		return new ConcurrentHashMap<String, WebSocketSession>(); 
	}

	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(handler(), "/magicmirror");
	}

	public static void main(String[] args) throws Exception {
		new SpringApplication(MagicMirrorApp.class).run(args);
	}
}
