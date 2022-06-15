package com.esign.docusgindirect.util.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;

import com.esign.docusgindirect.client.APIService;
import com.esign.docusgindirect.client.APIServiceSoap;
import com.esign.docusgindirect.client.AnchorTab;
import com.esign.docusgindirect.client.ArrayOfDocument;
import com.esign.docusgindirect.client.ArrayOfRecipient;
import com.esign.docusgindirect.client.ArrayOfTab;
import com.esign.docusgindirect.client.Document;
import com.esign.docusgindirect.client.Envelope;
import com.esign.docusgindirect.client.EnvelopeStatus;
import com.esign.docusgindirect.client.Expirations;
import com.esign.docusgindirect.client.Notification;
import com.esign.docusgindirect.client.Recipient;
import com.esign.docusgindirect.client.RecipientTypeCode;
import com.esign.docusgindirect.client.Tab;
import com.esign.docusgindirect.client.TabTypeCode;
import com.esign.docusgindirect.client.UnitTypeCode;
import com.esign.docusgindirect.util.CustomTab;
import com.esign.docusgindirect.util.CustomTabUtil;
import com.esign.docusgindirect.valueObjects.DocuSignRequest;
import com.esign.docusgindirect.valueObjects.DocusignAPICredentials;
import com.esign.docusgindirect.valueObjects.DocusignWebserviceFactory;
import com.esign.docusgindirect.valueObjects.RecipientInfoBean;
import com.esign.docusign.info.DocResponse;
import com.esign.docusign.util.SoapESignInInterceptor;
import com.manage.managemetadata.functions.commonfunctions.DataUtils;
import com.userbo.integration.ESignConstants;
import com.util.HtmlConstants;
import com.util.InetLogger;
import com.util.SystemProperties;

public class DocuSignUtil {
	private static InetLogger logger = InetLogger.getInetLogger(DocuSignUtil.class);
	static String WSDL_LOCATION = null;
	static String ACCOUNT_ID = null;
	static String propertiesPath = null;
	static String USER_NAME = null;
	static String INTEGRATION_KEY = null;
	static String PASSWORD = null;
	static String USER_EMAIL = null;
	static String EXPIRATION_DAYS = null;
	static String BRAND_ID = null;
	static String ESIGN_MAIL_SUBJECT = null;
	private final String DATE_SIGNER_IDENTIFIER_STRING = "P1_DATE_SIGNER";

	public DocuSignUtil() throws Exception {
		try {
			WSDL_LOCATION = SystemProperties.getInstance().getString("esign.ws.url");
			ACCOUNT_ID = SystemProperties.getInstance().getString("esign.vendorIdentifier");
			USER_NAME = SystemProperties.getInstance().getString("esign.clientIdentifier");
			INTEGRATION_KEY = SystemProperties.getInstance().getString("esign.integrationKey");
			PASSWORD = SystemProperties.getInstance().getString("esign.password");
			USER_EMAIL = SystemProperties.getInstance().getString("esign.useremail");
			ESIGN_MAIL_SUBJECT = SystemProperties.getInstance().getString("docusign.mail.subject");
			EXPIRATION_DAYS = SystemProperties.getInstance().getString("esign.expiration.days");
			if (SystemProperties.getInstance().getProperty("esign.brand.id") != null
					&& !HtmlConstants.EMPTY.equals(SystemProperties.getInstance().getString("esign.brand.id")))
				BRAND_ID = SystemProperties.getInstance().getString("esign.brand.id");

		} catch (Exception e) {
			logger.error("Unbale to read property " + e.getMessage());
			throw e;
		}

  
	}

	private APIServiceSoap getWSClient() throws Exception {
		try {

   
			String wsdlLocation = WSDL_LOCATION;
			logger.debug("Going to make connection to docuesign URL : " + wsdlLocation);
					 

			URL url = null;
			URL baseUrl;

			logger.debug("Going to define DocuEsign service");
			baseUrl = APIServiceSoap.class.getResource(".");
			url = new URL(baseUrl, wsdlLocation);

			APIService apiService = new APIService(url, new QName("http://www.docusign.net/API/3.0", "APIService"));

														 
									   
					
   
			APIServiceSoap delegate = apiService.getAPIServiceSoap();

   
			logger.debug("Got Client");

			logger.debug("Going to get Client Proxy");
			Client client = ClientProxy.getClient(delegate);

   
   
			logger.debug("Got Client Proxy");

			client.getInInterceptors().add(new SAAJInInterceptor());
			client.getInInterceptors().add(new SoapESignInInterceptor());

			client.getOutInterceptors().add(new SAAJOutInterceptor());

   
			logger.debug("Interceptor added for Client Proxy");
			((BindingProvider) delegate).getRequestContext().put("set-jaxb-validation-event-handler", "false");

   
			BindingProvider bp = (BindingProvider) delegate;

			logger.debug(
					"EndPoint from WSDL :" + bp.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));

			String endPoint = wsdlLocation;

			if (endPoint.contains("?"))
				endPoint = endPoint.substring(0, (endPoint.length() - 5));

			bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPoint);
			   

   
			logger.debug("Got ESign URL connection");

   
   
			return delegate;
		} catch (Exception e) {

			// e.printStackTrace();
			logger.error("Unable to execute getWSClient method due to error : " + DataUtils.getExceptionStackTrace(e));
					   
			throw e;
		}

	}
	
	
  
	public EnvelopeStatus sendDocument(List<DocuSignRequest> docusignRequestList) throws Exception {
		EnvelopeStatus enveolpeStatus = null;

		logger.debug("Going to hit sendDocument");

		try {
			if (docusignRequestList != null && docusignRequestList.size() > 0) {
				APIServiceSoap apiServiceSoap = getAPI();
				Envelope envolope = new Envelope();

				logger.debug("...................1");
				// envolope.setEmailBlurb("mohita@outlinesys.com");
				String subjectline="e-Signature";
				try 
				{
					if(docusignRequestList.get(0).getSubjectline()!=null && !docusignRequestList.get(0).getSubjectline().isEmpty())
						subjectline=docusignRequestList.get(0).getSubjectline();
				}
				catch(Exception e)
				{
					subjectline="e-Signature";
					logger.error("Unable to set subjectline while sending doc::" + e.getMessage());
					//e.printStackTrace();
				}
				envolope.setSubject(subjectline);
				envolope.setAccountId(ACCOUNT_ID);
				envolope.setDocuments(getDocumentList(docusignRequestList));
				envolope.setRecipients(getRecipientList(docusignRequestList));
				envolope.setTabs(getArrayOfTab(docusignRequestList));

				if (BRAND_ID != null && !HtmlConstants.EMPTY.equals(BRAND_ID)) {
					envolope.setBrandId(BRAND_ID);
				}

				logger.debug("...................2");

				Notification notification = new Notification();
				Expirations expirations = new Expirations();
				expirations.setExpireEnabled(true);
				expirations.setExpireAfter(new BigInteger(EXPIRATION_DAYS));

				logger.debug("...................3");

				int setDaysForWraning = Integer.parseInt(EXPIRATION_DAYS) - 1;

				expirations.setExpireWarn(new BigInteger(Integer.toString(setDaysForWraning)));
				notification.setExpirations(expirations);

				logger.debug("...................4");

				envolope.setNotification(notification);
				envolope.setEnableWetSign(false);
				String requestXMLString = getRequestXml(envolope);
				logger.debug("Request XML :" + requestXMLString);
				enveolpeStatus = apiServiceSoap.createAndSendEnvelope(envolope);
				String responseXMLString = getResponseXml(enveolpeStatus);
				logger.debug("Response XML :" + responseXMLString);

				logger.debug("...................5");

			} else {
				logger.debug("Not Found any DocuSign Request");
			}

			return enveolpeStatus;

		} catch (Exception e) {

			// e.printStackTrace();
			logger.error("Unable to execute sendDocument method due to error : " + DataUtils.getExceptionStackTrace(e));
																		 
					   
			throw e;
		}

	}



	public EnvelopeStatus checkStatusESignDoc(String identifier) throws Exception {
		EnvelopeStatus envelopeStatus = null;
		try {
			// Esignature service = new Esignature();

			APIServiceSoap apiServiceSoap = getAPI();



			logger.debug("Going to check esign status");
			envelopeStatus = apiServiceSoap.requestStatus(identifier);

   
			logger.debug("Got esign status");

		} catch (Exception e) {

			// e.printStackTrace();
			logger.error("Unable to execute checkStatusESignDoc method due to error : "
					+ DataUtils.getExceptionStackTrace(e));
			throw e;
		}
		return envelopeStatus;
	}

 
	public Envelope retreiveDocument(String identifier) throws Exception {
		Envelope envelope = null;
		try {
			APIServiceSoap apiServiceSoap = getAPI();
			envelope = apiServiceSoap.requestEnvelope(identifier, true);
		} catch (Exception e) {

			// e.printStackTrace();
			logger.error(
					"Unable to execute retreiveDocument method due to error : " + DataUtils.getExceptionStackTrace(e));
			throw e;
		}

		return envelope;
	}

	private ArrayOfTab getArrayOfTab(List<DocuSignRequest> docuSignRequestList) throws Exception {


		
		ArrayOfTab arrayOfTab = new ArrayOfTab();
		List<RecipientInfoBean> finalRecipientInfoList = new ArrayList<RecipientInfoBean>();
		if(docuSignRequestList != null && docuSignRequestList.size() > 0){
			
			for(int i=0;i<docuSignRequestList.size();i++){
				
				DocuSignRequest docuSignRequest = docuSignRequestList.get(i);
				List<RecipientInfoBean> recipientInfoList =  docuSignRequest.getRecipientInfoList();
				checkForDuplicateRecipient(recipientInfoList,finalRecipientInfoList,docuSignRequest);
				
			}
			
			if(finalRecipientInfoList != null && finalRecipientInfoList.size() > 0){
				for(int i=0; i<finalRecipientInfoList.size(); i++){
					RecipientInfoBean recipientInfoObj = finalRecipientInfoList.get(i);
					
					if(recipientInfoObj.getRecipientIdentifierList() != null ){
						for(int k=0; k<docuSignRequestList.size();k++){
							DocuSignRequest docuSignRequestObj = docuSignRequestList.get(k);
							List<String> recipientDocList = recipientInfoObj.getRecipientDocList();
							List<String> recipientIdentifierList = recipientInfoObj.getRecipientIdentifierList();
							if(recipientDocList.contains(docuSignRequestObj.getIdentifier())){
								for(int l=0; l<recipientIdentifierList.size(); l++){
									String recipientIdentifier = recipientIdentifierList.get(l);
									if(docuSignRequestObj.getDocAnchorInfo().contains(recipientIdentifier)){
										boolean flag=true;
										Tab tab = new Tab();
										AnchorTab anchorTab = new AnchorTab();
										
										String anchorString = recipientIdentifier; 
										logger.debug("anchorString:: "+anchorString);
										if (anchorString.contains(ESignConstants.CUSTOM_TAB_IDENTFIER))
										{
											CustomTabUtil custtab=new CustomTabUtil();
											arrayOfTab=custtab.getCustomTab(arrayOfTab,docuSignRequestObj,recipientInfoObj);
										}
										else {
											anchorTab.setAnchorTabString(anchorString);
											anchorTab.setIgnoreIfNotPresent(true);
											anchorTab.setUnit(UnitTypeCode.PIXELS);
											// anchorTab.setXOffset(new Double(location));
											// anchorTab.setYOffset(new Double(location));

											tab.setAnchorTabItem(anchorTab);
											if (anchorString.contains("DATE")) {
												tab.setType(TabTypeCode.DATE_SIGNED);

												int count = 0;
												/*
												 * try{
												 * 
												 * if(arrayOfTab != null && arrayOfTab.getTab() != null &&
												 * arrayOfTab.getTab().size()>0){ List<Tab> tablListTemp =
												 * arrayOfTab.getTab(); for(int n=0; n<tablListTemp.size(); n++){ count
												 * =0; Tab tabTemp = tablListTemp.get(n); if(tabTemp!=null){
												 * if(tabTemp.getType().equals(TabTypeCode.DATE_SIGNED)){ count =1;
												 * break; } }
												 * 
												 * } if(count == 1) continue; } }catch(Exception e){
												 * e.printStackTrace(); }
												 */

											} else {
												tab.setType(TabTypeCode.SIGN_HERE);
												tab.setTabLabel("Sign Here");
											}
											tab.setDocumentID(new BigInteger(
													Integer.toString(docuSignRequestObj.getDocumentId())));
											tab.setRecipientID(new BigInteger(
													Integer.toString(recipientInfoObj.getRecipientId())));
											tab.setUnderline(true);

											// tab.setPageNumber(new BigInteger(Integer.toString(1)));
											// tab.setXPosition(new BigInteger("100"));
											// tab.setYPosition(new BigInteger("100"));
											// tab.setFont(Font.TIMES_NEW_ROMAN);
											// tab.setFontColor(FontColor.GREEN);
											for(int check=0;check<arrayOfTab.getTab().size();check++)
											{
												AnchorTab anchorTabcheck = new AnchorTab();
												anchorTabcheck=arrayOfTab.getTab().get(check).getAnchorTabItem();
												if(anchorTabcheck.getAnchorTabString().equalsIgnoreCase(anchorString))
												{
													flag=false;
													break;
												}
											}
											if(flag)
												arrayOfTab.getTab().add(tab);
										}
									}
								}
							}
						}
					}
					
				}
			}
		}
		return arrayOfTab;
	}

	private ArrayOfRecipient getRecipientList(List<DocuSignRequest> docuSignRequestList) throws Exception {
		ArrayOfRecipient recipientList = null;
		List<RecipientInfoBean> finalRecipientInfoList = new ArrayList<RecipientInfoBean>();
		if (docuSignRequestList.size() > 0)
			recipientList = new ArrayOfRecipient();

		for (int i = 0; i < docuSignRequestList.size(); i++) {
			DocuSignRequest docuSignRequest = docuSignRequestList.get(i);
			List<RecipientInfoBean> recipientInfoList = docuSignRequest.getRecipientInfoList();
			List<RecipientInfoBean> reciepientInfoListTemp = checkForDuplicateRecipient(recipientInfoList,
					finalRecipientInfoList, docuSignRequest);

			/*
			 * if(reciepientInfoListTemp != null){ for(int k=0;
			 * k<reciepientInfoListTemp.size(); k++){
			 * finalRecipientInfoList.add(reciepientInfoListTemp.get(k)); } }
			 */
		}
	  
   
		if (finalRecipientInfoList != null) {
			for (int i = 0; i < finalRecipientInfoList.size(); i++) {
				RecipientInfoBean recipientInfo = finalRecipientInfoList.get(i);
				Recipient recipient = new Recipient();
				recipient.setEmail(recipientInfo.getEmailAddress());
				recipient.setUserName(recipientInfo.getName());
				recipient.setID(new BigInteger(Integer.toString(recipientInfo.getRecipientId())));
				recipient.setType(RecipientTypeCode.SIGNER);
				recipient.setRequireIDLookup(false);
				recipient.setRoutingOrder(recipientInfo.getRoutingOrder());
				recipientList.getRecipient().add(recipient);

			}
		}
		return recipientList;
	}

	private ArrayOfDocument getDocumentList(List<DocuSignRequest> docuSignRequestList) throws Exception {
		ArrayOfDocument docs = null;

		if (docuSignRequestList.size() > 0)
			docs = new ArrayOfDocument();

		for (int i = 0; i < docuSignRequestList.size(); i++) {
			DocuSignRequest docuSignRequest = docuSignRequestList.get(i);
			byte[] pdfContent = docuSignRequest.getContent();
			Document doc = new Document();
			doc.setPDFBytes(pdfContent);
			doc.setName(docuSignRequest.getIdentifier());
			doc.setID(new BigInteger(Integer.toString(docuSignRequest.getDocumentId())));
			doc.setFileExtension("pdf");
			docs.getDocument().add(doc);

		}
		return docs;
	}

	public byte[] getBytesFromFile(String pdfFilePath) throws Exception {
		try {
			File file = new File(pdfFilePath);
			java.io.InputStream is = null;
			try {
				is = new java.io.FileInputStream(file);
			} catch (FileNotFoundException e) {
				// e.printStackTrace();
				logger.error("Unable to execute getBytesFromFile method due to error : "
						+ DataUtils.getExceptionStackTrace(e));
			}
			// Get the size of the file
			long length = file.length();
			// Create the byte array to hold the data
			byte[] bytes = new byte[(int) length];
			// Read in the bytes
			int offset = 0;
			int numRead = 0;
			try {
				while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
																		 
					offset += numRead;
				}
			} catch (IOException e) {
				// e.printStackTrace();
				logger.error("Unable to execute getBytesFromFile method due to error : "
						+ DataUtils.getExceptionStackTrace(e));
			}
			// Close the input stream and return bytes
			try {
				is.close();
			} catch (IOException e) {
				// e.printStackTrace();
				logger.error("Unable to execute getBytesFromFile method due to error : "
						+ DataUtils.getExceptionStackTrace(e));
			}

  
			return bytes;

		} catch (Exception e1) {
			// e1.printStackTrace();
			logger.error(
					"Unable to execute getBytesFromFile method due to error : " + DataUtils.getExceptionStackTrace(e1));
			throw e1;
		}

	}

 
	public APIServiceSoap getAPI() throws Exception {
		APIServiceSoap apiservicSoap = getWSClient();
		DocusignAPICredentials apiCreds = new DocusignAPICredentials();
		apiCreds.setAccountId(ACCOUNT_ID);
		apiCreds.setUserId(USER_NAME);
		apiCreds.setIntegratorsKey(INTEGRATION_KEY);
		apiCreds.setPassword(PASSWORD);
		apiCreds.setUserEmail(USER_EMAIL);

  
		String serviceEndPoint = "";
		if (WSDL_LOCATION.contains("?"))
			serviceEndPoint = WSDL_LOCATION.substring(0, (WSDL_LOCATION.length() - 5));

		apiCreds.setDocusignWebserviceEndpoint(serviceEndPoint);

		DocusignWebserviceFactory wsFactory = new DocusignWebserviceFactory();
		wsFactory.setEmail(apiCreds.getUserEmail());
		wsFactory.setIntegratorsId(apiCreds.getIntegratorsKey());
		wsFactory.setUserId(apiCreds.getUserId());

		return wsFactory.setupClient(serviceEndPoint).authorizeAPI(apiCreds);
	}

	public String getRequestXml(Envelope requestObject) throws Exception {
		try {
			final JAXBContext context = JAXBContext.newInstance(Envelope.class);
			final Marshaller marshaller = context.createMarshaller();
			final StringWriter stringWriter = new StringWriter();
			JAXBElement jaxbElement = new JAXBElement(new QName("Envelope"), Object.class, requestObject);
			marshaller.marshal(jaxbElement, stringWriter);

			return stringWriter.toString();
		} catch (Exception e) {

			// e.printStackTrace();
			logger.error(
					"Unable to execute getRequestXml method due to error : " + DataUtils.getExceptionStackTrace(e));
			throw e;
		}

  
	}

	public String getResponseXml(EnvelopeStatus resObject) throws Exception {
		try {
			final JAXBContext context = JAXBContext.newInstance(EnvelopeStatus.class);
			final Marshaller marshaller = context.createMarshaller();
			final StringWriter stringWriter = new StringWriter();
			JAXBElement jaxbElement = new JAXBElement(new QName("EnvelopeStatus"), Object.class, resObject);
			marshaller.marshal(jaxbElement, stringWriter);

			return stringWriter.toString();
		} catch (Exception e) {

			// e.printStackTrace();
			logger.error(
					"Unable to execute getResponseXml method due to error : " + DataUtils.getExceptionStackTrace(e));
			throw e;
		}

  
	}

	private List<RecipientInfoBean> checkForDuplicateRecipient(List<RecipientInfoBean> recipientInfoBeanList,
			List<RecipientInfoBean> finalRecipientInfoList, DocuSignRequest docuSignRequest) {
		Map<String,CustomTab> finalcustomtabmap=new LinkedHashMap<String, CustomTab>();
		if (recipientInfoBeanList != null && recipientInfoBeanList.size() > 0) {

			if (finalRecipientInfoList != null && finalRecipientInfoList.size() > 0) {
				
				for (int i = 0; i < recipientInfoBeanList.size(); i++) {
					boolean matchFound = false;
					RecipientInfoBean recipientInfoObj = recipientInfoBeanList.get(i);
					List<String> recDocListTempList = new ArrayList<String>();
					//recDocListTempList.add(recipientInfoObj.getRecipientDoc());
					recDocListTempList.add(docuSignRequest.getIdentifier());
					recipientInfoObj.setRecipientDocList(recDocListTempList);

					for (int k = 0; k < finalRecipientInfoList.size(); k++) {
						RecipientInfoBean finalRecipientInfoObj = finalRecipientInfoList.get(k);

						String emailCompareFrom = recipientInfoObj.getEmailAddress();
						String emailCompareTo = finalRecipientInfoObj.getEmailAddress();
						if (emailCompareFrom != null && emailCompareTo != null
								&& (emailCompareFrom.equals(emailCompareTo))) {
							String userNameCompareFrom = recipientInfoObj.getName();
							String initialsCompareFrom = recipientInfoObj.getInitials();
							String userNameCompareTo = finalRecipientInfoObj.getName();
							String initialsCompareTo = finalRecipientInfoObj.getInitials();

							if (userNameCompareFrom != null && initialsCompareFrom != null && userNameCompareTo != null
									&& initialsCompareTo != null && userNameCompareFrom.equals(userNameCompareTo)
									&& initialsCompareFrom.equals(initialsCompareTo)) {
								List<String> recipientIdentifierList = new ArrayList<String>();
								List<String> recipientDocList = new ArrayList<String>();
								List<String> recipientIdentifierListTemp = finalRecipientInfoObj
										.getRecipientIdentifierList();
								List<String> recipientIdentifierListTemp1 = recipientInfoObj
										.getRecipientIdentifierList();
								List<String> recipientDocListTemp = finalRecipientInfoObj.getRecipientDocList();
								//List<String> recipientDocListTemp1 = recipientInfoObj.getRecipientDocList();
								 String docIndetifier =  docuSignRequest.getIdentifier();
								 //finalRecipientInfoObj.setRecipien
								
								// docuSignRequest.setDocAnchorInfo(finalRecipientInfoObj.getRecipientIdentifierList());

								for (int l = 0; l < recipientIdentifierListTemp.size(); l++) {
									recipientIdentifierList.add(recipientIdentifierListTemp.get(l));
								}
								for (int l = 0; l < recipientDocListTemp.size(); l++) {
									recipientDocList.add(recipientDocListTemp.get(l));
								}

								for (int m = 0; m < recipientIdentifierListTemp1.size(); m++) {
									if (!recipientIdentifierList.contains(recipientIdentifierListTemp1.get(m))) {
										recipientIdentifierList.add(recipientIdentifierListTemp1.get(m));
									}
								}
//								for (int m = 0; m < recipientDocListTemp1.size(); m++) {
//									if (!recipientDocList.contains(recipientDocListTemp1.get(m))) {
//										recipientDocList.add(recipientDocListTemp1.get(m));
//									}
//								}
								recipientDocList.add(docIndetifier);
								
								if(recipientInfoObj.getCustomtabmap() != null && finalRecipientInfoObj.getCustomtabmap() != null) {
									finalcustomtabmap.putAll(recipientInfoObj.getCustomtabmap());
									finalcustomtabmap.putAll(finalRecipientInfoObj.getCustomtabmap());
								}
		 
																							  
								finalRecipientInfoObj.setRecipientIdentifierList(recipientIdentifierList);
								finalRecipientInfoObj.setRecipientDocList(recipientDocList);
		
								if(finalcustomtabmap != null && finalcustomtabmap.size() > 0 )									  
									finalRecipientInfoObj.setFinalcustomtabmap(finalcustomtabmap);
		
								matchFound = true;
								break;
							}
						}
					}
					if (matchFound == false) {
						if(recipientInfoObj.getCustomtabmap() != null)							
							recipientInfoObj.setFinalcustomtabmap(recipientInfoObj.getCustomtabmap());
						finalRecipientInfoList.add(recipientInfoObj);
					}
				}
			} else {
				
				for (int i = 0; i < recipientInfoBeanList.size(); i++) {

					String recipientDoc = docuSignRequest.getIdentifier();
					List<String> recipientDocList = new ArrayList<String>();

					recipientDocList.add(recipientDoc);
					recipientInfoBeanList.get(i).setRecipientDoc(recipientDoc);
					recipientInfoBeanList.get(i).setRecipientDocList(recipientDocList);
					// docuSignRequest.setDocAnchorInfo(recipientInfoBeanList.get(0).getRecipientIdentifierList());
					if(recipientInfoBeanList.get(i).getCustomtabmap() != null && recipientInfoBeanList.get(i).getCustomtabmap().size() >0)																									  
						recipientInfoBeanList.get(i).setFinalcustomtabmap(recipientInfoBeanList.get(i).getCustomtabmap());
	
					finalRecipientInfoList.add(recipientInfoBeanList.get(i));
				}
			}

		}

		return finalRecipientInfoList;
	}

	public static void main(String[] a) throws Exception {
		try {
			propertiesPath = "D:/workspace_AccidentFund/ProducerOneEsign/ProducerOneESign.properties";
			SystemProperties.setPropertyFileName(propertiesPath);

			// new ESignatureUtil().getWSClient();

			DocuSignUtil docuSign = new DocuSignUtil();

			List<DocuSignRequest> docuSignRequestList = new ArrayList<DocuSignRequest>();

	
	  
			DocuSignRequest docuSignRequest = new DocuSignRequest();

			byte[] bout = new DocuSignUtil().getBytesFromFile("C:/Users/mohita/Desktop/soap-api-guide.pdf");
			docuSignRequest.setContent(bout);
			docuSignRequest.setDocumentId(1);
			List<RecipientInfoBean> recipientInfoList = new ArrayList<RecipientInfoBean>();
			docuSignRequest.setIdentifier("APPFORM");

			RecipientInfoBean recipientInfo = new RecipientInfoBean();

			// recipientInfo.setIdentifier("P1_AGENT_SIGNER_1");
			List<String> recipientIdentifierList = new ArrayList<String>();
			recipientIdentifierList.add("P1_AGENT_SIGNER_1");
			recipientIdentifierList.add("P1_DATE_SIGNER");
			recipientInfo.setRecipientIdentifierList(recipientIdentifierList);
			recipientInfo.setRecipientDoc(docuSignRequest.getIdentifier());
			recipientInfo.setRecipientId(2);
			recipientInfo.setRecipientId(1);
			recipientInfo.setEmailAddress("mohita@outlinesys.com");

			recipientInfo.setName("Second Print Name");

			recipientInfo.setInitials("SPN");

			recipientInfoList.add(recipientInfo);
			docuSignRequest.setRecipientInfoList(recipientInfoList);
			docuSignRequest.setDocAnchorInfo(recipientIdentifierList);
			docuSignRequestList.add(docuSignRequest);

	 
	
	  
	  
			docuSignRequest = new DocuSignRequest();
			///////////////////
			bout = new DocuSignUtil().getBytesFromFile("C:/Users/mohita/Desktop/soap-api-guide.pdf");
			docuSignRequest.setContent(bout);
			docuSignRequest.setDocumentId(2);
			recipientInfoList = new ArrayList<RecipientInfoBean>();
			docuSignRequest.setIdentifier("APPBG");

			recipientInfo = new RecipientInfoBean();
			// recipientInfo.setIdentifier("P1_AGENT_SIGNER_2");
			recipientIdentifierList = new ArrayList<String>();
			recipientIdentifierList.add("P1_AGENT_SIGNER_2");
			recipientIdentifierList.add("P1_DATE_SIGNER");
			recipientInfo.setRecipientIdentifierList(recipientIdentifierList);
			recipientInfo.setRecipientDoc(docuSignRequest.getIdentifier());

			recipientInfo.setRecipientId(2);
			recipientInfo.setEmailAddress("mohita@outlinesys.com");

	   
			recipientInfo.setName("Second Print Name");
			recipientInfo.setInitials("SPN");

			recipientInfoList.add(recipientInfo);
			docuSignRequest.setRecipientInfoList(recipientInfoList);
			docuSignRequest.setDocAnchorInfo(recipientIdentifierList);
			docuSignRequestList.add(docuSignRequest);

			docuSignRequest = new DocuSignRequest();
			/////////////////
			bout = new DocuSignUtil().getBytesFromFile("C:/Users/mohita/Desktop/soap-api-guide.pdf");
			docuSignRequest.setContent(bout);
			docuSignRequest.setDocumentId(3);
			recipientInfoList = new ArrayList<RecipientInfoBean>();
			docuSignRequest.setIdentifier("APPBG");

			recipientInfo = new RecipientInfoBean();
			// recipientInfo.setIdentifier("P1_AGENT_SIGNER_3");
			recipientIdentifierList = new ArrayList<String>();
			recipientIdentifierList.add("P1_AGENT_SIGNER_3");
			recipientIdentifierList.add("P1_DATE_SIGNER");
			recipientInfo.setRecipientIdentifierList(recipientIdentifierList);
			recipientInfo.setRecipientDoc(docuSignRequest.getIdentifier());
			recipientInfo.setRecipientId(3);
			recipientInfo.setEmailAddress("nipung@outlinesys.com");

	   
			recipientInfo.setName("Second Print Name1");
			recipientInfo.setInitials("SPN1");

			recipientInfoList.add(recipientInfo);
			docuSignRequest.setDocAnchorInfo(recipientIdentifierList);
			docuSignRequest.setRecipientInfoList(recipientInfoList);
			docuSignRequestList.add(docuSignRequest);

	  
	  
	
   
   
   
			DocResponse docRes = null;
			try {
				logger.debug("Going to hit DocuSign");
				docuSign.sendDocument(docuSignRequestList);
				logger.debug("Response got from DocuSign " + docRes);
			} catch (Throwable e1) {

				// e1.printStackTrace();
				logger.error("Unable to execute main method due to error : " + DataUtils.getExceptionStackTrace(e1));
			}

   
   
		} catch (Exception e) {
   
			logger.error("Unable to send document for ESignature due to error : " + e.getMessage());
		}
  

	}
}
