package com.roi.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Created for JavaStudy.ru on 30.05.2016.
 * Automatically register the springSecurityFilterChain Filter for every URL in your application
 * Add a ContextLoaderListener that loads the SecurityConfig.
 */
public class SecurityInit extends AbstractSecurityWebApplicationInitializer {
}
