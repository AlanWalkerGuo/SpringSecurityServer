/**
 * 
 */
package com.guosh.basic.repository;

import com.guosh.demo.web.config.MixPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guoshaohua
 *
 */
@Component
@ConfigurationProperties(prefix="nativesql")
@PropertySource(value = {"classpath:sql.yml"},factory = MixPropertySourceFactory.class)
public class SqlMap {
	private Map<String, String> sqls = new HashMap<>();
	
	public Map<String, String> getSqls() {
		return sqls;
	}
}
