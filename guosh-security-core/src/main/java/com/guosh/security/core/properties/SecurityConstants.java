/**
 * 
 */
package com.guosh.security.core.properties;

/**
 * @author guoshaohua
 *
 */
public interface SecurityConstants {

	/**
	 * 默认登录页面
	 */
	public static final String DEFAULT_LOGIN_PAGE_URL = "/defaultLogin.html";

	/**
	 * 当请求需要身份认证时，默认跳转的url
	 *
	 */
	public static final String  DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";

	/**
	 * 默认的用户名密码登录请求处理url
	 */
	public static final String DEFAULT_SIGN_IN_PROCESSING_URL_FORM = "/authentication/form";
	/**
	 * 默认的手机验证码登录请求处理url
	 */
	public static final String DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE = "/authentication/mobile";

	/**
	 * 默认的OPENID登录请求处理url
	 */
	public static final String DEFAULT_SIGN_IN_PROCESSING_URL_OPENID = "/authentication/openid";

	/**
	 * 默认的处理验证码的url前缀
	 */
	public static final String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";

	/**
	 * 绑定第三方查看用户信息
	 */
	public static final String DEFAULT_SOCIAL_USER_INFO="/social/user";


	/**
	 * 绑定第三方登陆
	 */
	public static final String DEFAULT_SOCIAL_USER_REGIST="/user/regist";

	/**
	 * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
	 */
	public static final String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";
	/**
	 * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
	 */
	public static final String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";

	/**
	 * openid参数名
	 */
	public static final String DEFAULT_PARAMETER_NAME_OPENID = "openId";
	/**
	 * providerId参数名
	 */
	public static final String DEFAULT_PARAMETER_NAME_PROVIDERID = "providerId";
}
