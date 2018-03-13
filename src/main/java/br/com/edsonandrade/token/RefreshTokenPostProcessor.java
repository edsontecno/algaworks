package br.com.edsonandrade.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import br.com.edsonandrade.config.property.AlgamoneyApiProperty;

@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {
	
	@Autowired
	private AlgamoneyApiProperty property;

	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter arg1, MediaType arg2,
			Class<? extends HttpMessageConverter<?>> arg3, ServerHttpRequest serverRequest, ServerHttpResponse serverRespose) {
		
		HttpServletRequest request = ((ServletServerHttpRequest)serverRequest).getServletRequest();
		HttpServletResponse response= ((ServletServerHttpResponse)serverRespose).getServletResponse();
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;
		String refresh = body.getRefreshToken().getValue();
		adicionarRefreshAoCookie(refresh, request, response );
		
		removerRefreshDoBody(token);
		
		return body;
	}

	private void removerRefreshDoBody(DefaultOAuth2AccessToken token) {
		token.setRefreshToken(null);
		
	}

	@Override
	public boolean supports(MethodParameter arg0, Class<? extends HttpMessageConverter<?>> arg1) {
		return arg0.getMethod().getName().equals("postAccessToken");
	}
	
	private void adicionarRefreshAoCookie(String refresh, HttpServletRequest request, HttpServletResponse response) {
		Cookie refreshCookie = new Cookie("refresh", refresh);
		refreshCookie.setHttpOnly(true);
		refreshCookie.setSecure(property.getSeguranca().isEnableHttps()); 
		refreshCookie.setPath(request.getContextPath() + "/oauth/token");
		refreshCookie.setMaxAge(2592000);
		response.addCookie(refreshCookie);
		
		
	}

}
