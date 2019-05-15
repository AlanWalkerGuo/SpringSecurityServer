/**
 * 
 */
package com.guosh.basic.repository;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author audin
 *
 */
@Component
@ConfigurationProperties(prefix="nativesql")
@PropertySource("classpath:sql.yml")
public class SqlMap {
	private Map<String, String> sqls = new HashMap<>();
	
	public Map<String, String> getSqls() {
		return sqls;
	}
}
