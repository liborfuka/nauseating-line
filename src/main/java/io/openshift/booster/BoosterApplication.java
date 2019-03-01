/*
 * Copyright 2016-2017 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.openshift.booster;

import io.openshift.booster.service.TomcatShutdown;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BoosterApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoosterApplication.class, args);
    }

    // Customize servlet container so that we could stop Tomcat when requested.

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> embeddedServletContainerCustomizer(TomcatShutdown tomcatShutdown) {
        return container -> container.addContextCustomizers(tomcatShutdown::setContext);
    }

    @Bean
    public TomcatShutdown tomcatShutdown() {
        return new TomcatShutdown();
    }

}
