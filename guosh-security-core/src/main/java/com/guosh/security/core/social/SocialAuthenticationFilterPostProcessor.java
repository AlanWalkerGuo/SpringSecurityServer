/**
 * 
 */
package com.guosh.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * @author guosh
 *
 */
public interface SocialAuthenticationFilterPostProcessor {
	
	void process(SocialAuthenticationFilter socialAuthenticationFilter);

}
