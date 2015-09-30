package org.developerworld.frameworks.yixin.handler.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang.StringUtils;
import org.developerworld.frameworks.yixin.api.WebAuthApi;
import org.developerworld.frameworks.yixin.api.dto.WebAuthScope;

/**
 * web授权回调servlet
 * 
 * @author Roy Huang
 * @version 20140411
 * 
 */
public abstract class AbstractWebAuthCallbackServlet extends HttpServlet {

	private ConcurrentHashMap<String, Map> accessTokenDatas = new ConcurrentHashMap<String, Map>();

	/**
	 * 回调请求接收
	 * 
	 * @throws IOException
	 * @throws HttpException
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws HttpException, IOException {
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		// 用户不禁止授权
		if (StringUtils.isNotBlank(code)) {
			Map result = getAccessToken(request, code, null);
			// 成功获取accessToken
			if (result != null) {
				String accessToken = (String) result.get("access_token");
				String openPid = (String) result.get("openpid");
//				String scope = (String) result.get("scope");
				WebAuthScope webAuthScope = WebAuthScope.SNSAPI_BASE;
				handleAuthCallback(request, response, code, state, accessToken,
						openPid, webAuthScope);
			} else
				handleUnAuthCallback(request, response, code, state);
		} else
			handleUnAuthCallback(request, response, state);
	}

	/**
	 * 根据请求，获取accessToken
	 * 
	 * @param request
	 * @param code
	 * @param state
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	protected Map getAccessToken(HttpServletRequest request, String code,
			String state) throws HttpException, IOException {
		Map rst = null;
		String appId = getAppId(request);
		String secret = getSecret(request);
		if (isAutoRefreshToken()) {
			String dataKey = appId + "_" + this.getClass().getName() + "_"
					+ secret;
			rst = accessTokenDatas.get(dataKey);
			if (rst == null) {
				rst = new HashMap();
				// 记录获取时间
				rst.put("get_date", new Date());
				// 获取授权信息
				WebAuthApi.getAccessToken(appId, secret, code,
						state, rst);
				accessTokenDatas.putIfAbsent(dataKey, rst);
			}
			// 判断信息是否已经过期
			// 判断是否存在token信息
			if (rst.get("access_token") != null) {
				long expiresIn = Long.valueOf(rst.get("expires_in").toString());
				Date getDate = (Date) rst.get("get_date");
				// 检查token是否已经过期
				if (System.currentTimeMillis() - getDate.getTime() >= expiresIn * 1000) {
					// 记录刷新时间
					rst.put("get_date", new Date());
					// 刷新token
					WebAuthApi.refreshAccessToken(appId, (String) rst.get("refresh_token"),
							rst);
					accessTokenDatas.putIfAbsent(dataKey, rst);
				}
			}
		} else {
			rst = new HashMap();
			// 获取授权信息
			WebAuthApi.getAccessToken(appId, secret, code,
					state, rst);
		}
		return rst;
	}

	/**
	 * 处理授权回调
	 * 
	 * @param request
	 * @param response
	 * @param code
	 * @param state
	 * @param accessToken
	 * @param openPid
	 * @param webAuthScope
	 * @param authUser
	 */
	protected abstract void handleAuthCallback(HttpServletRequest request,
			HttpServletResponse response, String code, String state,
			String accessToken, String openPid, WebAuthScope webAuthScope);

	/**
	 * 处理不授权回调
	 * 
	 * @param request
	 * @param response
	 * @param code
	 * @param state
	 */
	protected abstract void handleUnAuthCallback(HttpServletRequest request,
			HttpServletResponse response, String code, String state);

	/**
	 * 处理不授权回调
	 * 
	 * @param request
	 * @param response
	 * @param state
	 */
	protected abstract void handleUnAuthCallback(HttpServletRequest request,
			HttpServletResponse response, String state);

	/**
	 * 返回公众号的appsecret
	 * 
	 * @param request
	 * @return
	 */
	protected abstract String getSecret(HttpServletRequest request);

	/**
	 * 返回公众号的唯一标识
	 * 
	 * @param request
	 * @return
	 */
	protected abstract String getAppId(HttpServletRequest request);

	/**
	 * 获取重定向页面地址
	 * 
	 * @param request
	 * @return
	 */
	protected abstract String getRedirectUri(HttpServletRequest request);

	/**
	 * 是否自动刷新token
	 * 
	 * @return
	 */
	protected abstract boolean isAutoRefreshToken();
}
