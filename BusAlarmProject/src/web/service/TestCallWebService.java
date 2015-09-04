/**
 * 
 */
package web.service;

import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

/**
 * 測試用來Call 台北市公車Web Service
 * @see <a href="http://info.cms.hinet.net/WS_CHT_HiCloud/competition/manual.htm#_Toc379977390">API文件</a>
 * @see <a href="http://info.cms.hinet.net/WS_CHT_HiCloud/competition/Service.asmx?op=GetCameraPlusJSON">GetCameraPlusJSON 其中一個Method回傳範例</a>
 * @author jeff
 */
public class TestCallWebService {
	
	private static SOAPMessage createSOAPRequest() throws SOAPException, IOException {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();
		
		//String serverURI = "http://210.71.254.198/WS_CHT_HiCloud/competition/Service.asmx/GetCameraPlusJSON";
		String serverURI = "http://info.cms.hinet.net/";
		
		// SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        //將帶有指定前綴和 URI 的名稱空間宣告添加到此 SOAPElement 物件。 
        envelope.addNamespaceDeclaration("GetCameraPlusJSON", serverURI);
		
        QName qn = new QName(serverURI, "AuthHeaderMarket");
        // SOAP Header
        SOAPHeader soapHeader = envelope.getHeader();
        SOAPHeaderElement headerEA = soapHeader.addHeaderElement(qn);
        SOAPElement headerEA1 = headerEA.addChildElement("Username");
        headerEA1.addTextNode("Jeff");
        SOAPElement headerEA2 = headerEA.addChildElement("HashKey");
        headerEA2.addTextNode("1233@3333");
        SOAPElement headerEA3 = headerEA.addChildElement("Password");
        headerEA3.addTextNode("password");
        
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPBodyElement bodyEA = soapBody.addBodyElement(qn);
        SOAPElement soapEA = bodyEA.addChildElement("Camera_ID");
        soapEA.addTextNode("150001");
        
        
        /* Print the request message */
        System.out.print("Request SOAP Message = \n");
        soapMessage.writeTo(System.out);
        System.out.println();
		
		return soapMessage;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SOAPConnectionFactory soapConnFactory;
		SOAPConnection soapConn = null;
		try {
			soapConnFactory = SOAPConnectionFactory.newInstance();
			soapConn = soapConnFactory.createConnection();
			
			String url = "http://210.71.254.198/WS_CHT_HiCloud/competition/Service.asmx/GetCameraPlusJSON";
			soapConn.call(createSOAPRequest(), url);
			
			
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (SOAPException | IOException e) {
			e.printStackTrace();
		} finally {
			if(soapConn != null) {
				try {
					soapConn.close();
				} catch (SOAPException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
