package com.wumin.boot152.cxf;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.NodeList;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import java.util.List;

/**
 * 别人请求本地的webservice接口拦截
 */
@Component
public class AuthInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	private SAAJInInterceptor saa = new SAAJInInterceptor();
	
	private static final String USER_NAME = "MAP";
	private static final String USER_PASSWORD = "MAP";

	public AuthInterceptor() {
		super(Phase.PRE_PROTOCOL);
		getAfter().add(SAAJInInterceptor.class.getName());
		WSS4JInInterceptor wss4JInInterceptor=new WSS4JInInterceptor();
		wss4JInInterceptor.
		getAfter().add(WSS4JInInterceptor.class.getName());
	}

	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		List<Header> list=message.getHeaders();
		SOAPMessage mess = message.getContent(SOAPMessage.class);
		if (mess == null) {
			saa.handleMessage(message);
			mess = message.getContent(SOAPMessage.class);
		}
		SOAPHeader head = null;
		try {
			head = mess.getSOAPHeader();
		} catch (Exception e) {
			logger.error("getSOAPHeader error: {}", e.getMessage(), e);
		}
		if (head == null) {
			throw new Fault(new IllegalArgumentException("找不到Header，无法验证用户信息"));
		}

		NodeList users = head.getElementsByTagName("username");
		NodeList passwords = head.getElementsByTagName("password");
		if (users.getLength() < 1) {
			throw new Fault(new IllegalArgumentException("找不到用户信息"));
		}
		if (passwords.getLength() < 1) {
			throw new Fault(new IllegalArgumentException("找不到密码信息"));
		}

		String userName = users.item(0).getTextContent().trim();
		String password = passwords.item(0).getTextContent().trim();
		if (USER_NAME.equals(userName) && USER_PASSWORD.equals(password)) {
			logger.debug("admin auth success");
		} else {
			SOAPException soapExc = new SOAPException("认证错误");
			logger.debug("admin auth failed");
			throw new Fault(soapExc);
		}
	}
}
