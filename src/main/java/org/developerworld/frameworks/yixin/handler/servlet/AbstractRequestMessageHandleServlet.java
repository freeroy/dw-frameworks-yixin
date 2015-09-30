package org.developerworld.frameworks.yixin.handler.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.developerworld.frameworks.yixin.handler.RequestMessageHandler;
import org.developerworld.frameworks.yixin.message.RequestMessage;
import org.developerworld.frameworks.yixin.message.ResponseMessage;
import org.developerworld.frameworks.yixin.message.converter.RequestMessageConverter;
import org.developerworld.frameworks.yixin.message.converter.ResponseMessageConverter;
import org.dom4j.DocumentException;

/**
 * 接收信息响应servlet
 * 
 * @author Roy Huang
 * @version 20140306
 * 
 */
public abstract class AbstractRequestMessageHandleServlet extends HttpServlet {

	private RequestMessageConverter requestMessageConverter = new RequestMessageConverter();
	private ResponseMessageConverter responseMessageConverter = new ResponseMessageConverter();
	
	private boolean isFindOtherHandlerWhenHasNotResponse=false;
	
	public boolean isFindOtherHandlerWhenHasNotResponse() {
		return isFindOtherHandlerWhenHasNotResponse;
	}

	public void setFindOtherHandlerWhenHasNotResponse(
			boolean isFindOtherHandlerWhenHasNotResponse) {
		this.isFindOtherHandlerWhenHasNotResponse = isFindOtherHandlerWhenHasNotResponse;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		if(config.getInitParameter("isFindOtherHandlerWhenHasNotResponse")!=null)
			setFindOtherHandlerWhenHasNotResponse(Boolean.valueOf(config.getInitParameter("isFindOtherHandlerWhenHasNotResponse")));
	}

	/**
	 * 处理get请求
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (checkJoinUp(request))
			response.getWriter().print(request.getParameter("echostr"));
	}

	/**
	 * 处理post请求
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 进行接入校验
			if (checkJoinUp(request))
				handleRequestMessage(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	/**
	 * 处理请求信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws DocumentException
	 */
	protected void handleRequestMessage(HttpServletRequest request,
			HttpServletResponse response) throws IOException, DocumentException {
		// 获取微信提交过来的信息
		String xml = IOUtils.toString(request.getInputStream(),
				request.getCharacterEncoding());
		if (StringUtils.isNotBlank(xml)) {
			// 构造对象
			RequestMessage reqMessage = requestMessageConverter
					.convertToObject(xml);
			if (reqMessage != null) {
				// 获取处理器
				Set<RequestMessageHandler> requestMessageHandlers = getRequestMessageHandlers(request);
				for (RequestMessageHandler requestMessageHandler : requestMessageHandlers) {
					// 判断是否支持处理信息
					if (requestMessageHandler.isSupport(reqMessage)) {
						// 处理并反馈响应信息
						ResponseMessage repMessage = requestMessageHandler
								.handle(reqMessage);
						if (repMessage != null) {
							// 转化响应信息
							String outXml = responseMessageConverter
									.convertToXml(repMessage);
							if (outXml != null) {
								response.getWriter().print(outXml);
								break;
							}
						}
						//若设置为不再继续查找，则跳出
						if(!isFindOtherHandlerWhenHasNotResponse())
							break;
					}
				}
			}
		}
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	/**
	 * 检查接入 若子类需要增加自己的验证，可以重载方法
	 * 
	 * @param request
	 * @return
	 */
	protected boolean checkJoinUp(HttpServletRequest request) {
		boolean rst = false;
		// 加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// token
		String token = getToken(request);
		if (StringUtils.isNotBlank(signature)
				&& StringUtils.isNotBlank(timestamp)
				&& StringUtils.isNotBlank(nonce)
				&& StringUtils.isNotBlank(token)) {
			// 构建验证数组
			String[] validateArr = new String[] { token, timestamp, nonce };
			// 字典排序
			Arrays.sort(validateArr);
			// 构建验证字符串
			String validateStr = "";
			for (String validate : validateArr)
				validateStr += validate;
			// sha1加密
			validateStr = DigestUtils.shaHex(validateStr);
			// 执行验证
			rst = signature.equalsIgnoreCase(validateStr);
		}
		return rst;
	}

	/**
	 * 获取token
	 * 
	 * @return
	 */
	protected abstract String getToken(HttpServletRequest request);

	/**
	 * 获取请求信息处理器集合
	 * 
	 * @param request
	 * @return
	 */
	protected abstract LinkedHashSet<RequestMessageHandler> getRequestMessageHandlers(
			HttpServletRequest request);
}
