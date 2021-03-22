package com.userbo.integration;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.fop.servlet.ServletContextURIResolver;


import com.esign.docusgindirect.client.EnvelopeStatus;
import com.esign.docusgindirect.util.DocuSignUtil;
import com.esign.docusgindirect.valueObjects.DocuSignRequest;
import com.esign.docusgindirect.valueObjects.RecipientInfoBean;
import com.esign.docusign.info.DocRequest;
import com.esign.docusign.info.DocResponse;
import com.esign.docusign.info.SignerInfoBean;
import com.esign.docusign.util.ESignatureUtil;
import com.manage.managecomponent.applicationworkflownew.ApplicationWorkflowResources;
import com.manage.managecomponent.applicationworkflownew.Businessobject;
import com.manage.managecomponent.applicationworkflownew.EventImpl;
import com.manage.managecomponent.applicationworkflownew.SignerImpl;
import com.manage.managecomponent.components.SetParametersForStoredProcedures;
import com.manage.managemetadata.functions.commonfunctions.DataUtils;
import com.manage.managemetadata.security.SecurityResources;
import com.manage.util.XMLUtils;
import com.ormapping.ibatis.SqlResources;
import com.pone.esign.client.OlsEsignResponse;
import com.userbo.common.DocumentUploadBO;
import com.userbo.common.ProducerOneUtils;
import com.util.Constants;
import com.util.Context;
import com.util.HtmlConstants;
import com.util.IContext;
import com.util.InetLogger;
import com.util.StringUtils;
import com.util.SystemProperties;

public class SendDocForESign extends Businessobject {
	private static InetLogger logger = InetLogger.getInetLogger(SendDocForESign.class);
	private final static String AGENT_SIGNER_IDENTIFIER = ESignConstants.AGENT_SIGNER_IDENTIFIER;
	private final static String DATE_IDENTIFIER = ESignConstants.DATE_SIGNER_IDENTFIER;
	
	public boolean evaluate(IContext arg0) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public Object execute(IContext ctx)  {
		try{
		logger.debug("Inside execute of SendDocForEsign.");
		if((ctx.get(HtmlConstants.INET_METHOD) != null && ctx.get(HtmlConstants.INET_METHOD).toString().equals("submit")) &&
				(ctx.get(Constants.INET_PAGE) != null && ctx.get(Constants.INET_PAGE).toString().equals("sendForReviewPopup"))){
			if(ctx.get("workflow_user") == null || ctx.get("workflow_user").toString().equals(HtmlConstants.EMPTY)){
				DataUtils.populateError((Context)ctx, "workflow_user", "AssignedToSelectErrorKey");
				return null;
			}
		}
		
		String isEsignEnabled = SystemProperties.getInstance().getString("esign.enabled");
		if(isEsignEnabled == null || !isEsignEnabled.equals("Y")){
			logger.debug("Esign Integration is not enabled.");
			//updateAppointmentRequestDataInDB(ctx);
			return null;
		}
		
		String esign_provider = ctx.get("esign_provider") != null ? ctx.get("esign_provider").toString() : null;
		logger.debug("esign_provider  " +esign_provider);
		logger.debug("workflow_event_name_context  " +ctx.get("workflow_event_name_context"));
		logger.debug("inet_method  " +ctx.get("inet_method"));
		if(esign_provider != null){
			if(esign_provider.equalsIgnoreCase("AssureEsign")){
				sendForAssureSign(ctx, new ByteArrayOutputStream(), HtmlConstants.EMPTY, HtmlConstants.EMPTY);
				return null;
			}else if(esign_provider.equalsIgnoreCase("DocuEsign")){
				setDataForDocuSign(ctx);
				return null;
			}else if(esign_provider.equalsIgnoreCase("DocuEsignDirect")){
				if(ctx.get("inet_page").toString().equals("agentBackgroundInquiry") && ctx.get("inet_method").toString().endsWith("sendBGCheckAuthorization")){
					setDataForDocuESignDirect(ctx);
				}
				//else if(ctx.get("inet_page").toString().equals("bankInformationForSelfServiceApproval") && ctx.get("inet_method").toString().endsWith("submit")){
			
				else if((ctx.get("workflow_event_name_context")!=null && ctx.get("workflow_event_name_context").toString().equals("sendForApprovalBankRequestSelfService") || ctx.get("workflow_event_name_context").toString().equals("sendForApprovalSelfService")) && (ctx.get("inet_method")!=null && ctx.get("inet_method").toString().endsWith("submit"))){
					setDataForDocuESignDirectForSelfService(ctx);
				}else if(ctx.get("workflow_name_context") != null && ctx.get("workflow_name_context").toString().equals("agencyTermination") && 
						((ctx.get("workflow_disapproved_status_event_name_context") != null && ctx.get(
						"workflow_disapproved_status_event_name_context").toString().equals("approved")) || 
						(ctx.get("workflow_event_name_context") != null && ctx.get("workflow_event_name_context").toString().equals("approved"))) 
						&& ctx.get("inet_method").toString().endsWith("submit"))
						{
					sendApplicationWorkflowDocuESign((Context)ctx);
					logger.debug("sendApplicationWorkflowDocuESign called ");
				}else{
					
					setDataForDocuESignDirectForContract(ctx);
					logger.debug("setDataForDocuESignDirectForContract called ");
				}
				
				return null;
			}
		}
		
		/*
		//updating request data in database
		updateAppointmentRequestDataInDB(ctx);
		*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	//Method created to set data for DocuSign
	public void setDataForDocuSign(IContext ctx) throws Exception {
		logger.debug("Going to set data for DocuSign");
		List generatedContractList = null;
		if(ctx.get("contract_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("contract_id"))){ 
			String contractCode = "FC";
			DocRequest docRequests = new DocRequest();
			List<DocRequest> docRequestsList = new ArrayList<DocRequest>();
			
			try{
				logger.debug("Going to get Contract attachments List");
				/*generatedContractList = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.sqlStatementsviewgetContractAttachments", ctx);*/
				new SetParametersForStoredProcedures().setParametersInContext(ctx, "contract_id");
				generatedContractList = SqlResources.getSqlMapProcessor(ctx).select("agency_document_attachments.getContractAttachmentsDataForDocusign_p", ctx);
				if(generatedContractList != null && generatedContractList.size() > 0){
					for(int i= 0; i<generatedContractList.size(); i++){
						contractCode = null;
						Map generatedContractMap = (Map) generatedContractList.get(i);
						if(generatedContractMap != null && !HtmlConstants.EMPTY.equals(generatedContractMap)){ 
							if(generatedContractMap.get("document_name") != null && !HtmlConstants.EMPTY.equals(generatedContractMap.get("document_name"))){
								String fileName = generatedContractMap.get("document_name").toString();
								if(fileName != null && !HtmlConstants.EMPTY.equals(fileName)){ 
									ctx.put("file_name", fileName);
									
									if(generatedContractMap.get("document_id") != null && !HtmlConstants.EMPTY.equals(generatedContractMap.get("document_id").toString()))
										ctx.put("document_id", generatedContractMap.get("document_id").toString());
									else
										ctx.put("document_id", null);
									
									if(generatedContractMap.get("is_uploaded_on_dms") != null && !HtmlConstants.EMPTY.equals(generatedContractMap.get("is_uploaded_on_dms").toString()))
										ctx.put("is_uploaded_on_dms", generatedContractMap.get("is_uploaded_on_dms").toString());
									else
										ctx.put("is_uploaded_on_dms", null);
									
									if(generatedContractMap.get("contractCode") != null && !HtmlConstants.EMPTY.equals(generatedContractMap.get("contractCode").toString()))
										ctx.put("contractCode", generatedContractMap.get("contractCode").toString());
									else
										continue;
									/*if(fileName.contains("FC") || fileName.contains("CS") || fileName.contains("CCS") || fileName.contains("BB") || fileName.contains("CLU")){
										contractCode = fileName.substring(fileName.indexOf("_")+1, fileName.lastIndexOf("_"));
										ctx.put("contractCode", contractCode.trim()); 
									}else{
										ctx.put("contractCode", contractCode); 
									}*/
									
									/*if(fileName.contains("_FC_"))
										contractCode = "FC";
									else if(fileName.contains("_CS_"))
										contractCode = "CS";
									else if(fileName.contains("_CCS_"))
										contractCode = "CCS";
									else if(fileName.contains("_BB_"))
										contractCode = "BB";
									else if(fileName.contains("_CLU_"))
										contractCode = "CLU";
									else
										continue;*/
									
									//ctx.put("contractCode", contractCode);
									
									logger.debug("Going to download contract from DMS for ContractCode : " + ctx.get("contractCode"));
									byte[] rb = downloadContractFromDMS((Context)ctx);
									logger.debug("Contract downloaded from DMD for ContractCode : " + ctx.get("contractCode"));
									
									if(rb != null && !HtmlConstants.EMPTY.equals(rb)){
										logger.debug("Going to generate Doc request data for Contactcode : "+ ctx.get("contractCode"));
										
										/*Context newCtx = new Context();
										newCtx.putAll(ctx);*/
										docRequests = generateDocRequestData((Context)ctx, rb);
										/*if(docRequests != null && docRequests.getSignerInfo() != null && docRequests.getSignerInfo().size() > 0){
											for(int j=0; j<docRequests.getSignerInfo().size(); j++){
												SignerInfoBean bean = (SignerInfoBean)docRequests.getSignerInfo().get(j);
												logger.debug(bean.getName() + " - " + bean.getIdentifier() + " - " + bean.getEmailAddress());
											}
										}*/

										logger.debug("Doc request data populated");
										docRequestsList.add(docRequests);
									}
								}
							}
						}
					}
					
					if(docRequestsList != null && docRequestsList.size() > 0){
						logger.debug("DocRequest List object populated with data");
						sendForDocuSign((Context)ctx,docRequestsList);
					}
				}
			}catch(Exception ex){
				DataUtils.populateError((Context)ctx, "carrierError", "Unable to send document for ESignature");
				logger.error("Unable to send document for ESignature due to error : " + DataUtils.getExceptionStackTrace(ex));
			}
		}
	}
	
	//Method created to get Signer's Identifier -- Not in Use Now
	private String getDocumentIdentifier(String contractCode){
		String identifier = null;
		if(StringUtils.isBlank(contractCode))
			return null;
		
		if("FC".equals(contractCode))
			identifier = ESignConstants.DOCUMENT_IDENTIFIER_CONTFORM;
		else if("CS".equals(contractCode))
			identifier = ESignConstants.DOCUMENT_IDENTIFIER_CONTCOMMSC;
		else if("CCS".equals(contractCode))
			identifier = ESignConstants.DOCUMENT_IDENTIFIER_CONTINCENSC;
		else if("BB".equals(contractCode))
			identifier = ESignConstants.DOCUMENT_IDENTIFIER_CONTBBSC;
		else if("CLU".equals(contractCode))
			identifier = ESignConstants.DOCUMENT_IDENTIFIER_CONTCLUST;
		return identifier ;
	}
	
	//Method created to send document for AssureSign
	public void sendForAssureSign(IContext ctx, ByteArrayOutputStream bout, String agency_name,
			String email_add) throws Exception{
		try{
			int maxPages = 3;
			
			if(ctx.get("marketdata_list_1") == null || (ctx.get("marketdata_list_1") instanceof List &&
					((List)ctx.get("marketdata_list_1")).size() == 0)){
				maxPages--;
			}else if(ctx.get("marketdata_list_1") != null && ctx.get("marketdata_list_1") instanceof List &&
					((List)ctx.get("marketdata_list_1")).size() > 6){
				maxPages++;
			}
			
			if(ctx.get("agent_list_1") != null && ctx.get("agent_list_1") instanceof List &&
					((List)ctx.get("agent_list_1")).size() > 0){
				maxPages = maxPages + ((List)ctx.get("agent_list_1")).size()*3;
			}
			
			OlsEsignResponse response = new OLSESignWS().doSignatureWithTemplate(bout.toByteArray(), agency_name, email_add, maxPages, ctx.getProject());
			if(response == null || response.getDocID() == null || response.getDocID().equals(HtmlConstants.EMPTY)){
				DataUtils.populateError((Context)ctx, "carrierError", "Error in Sending document for ESign");
				logger.error("ESign doc has not been sent successfully");
				return;
			}
			
			//going to insert esign status in database
			Context newCtx = new Context();
			newCtx.setProject(ctx.getProject());
			
			newCtx.put("status_desc", "Sent For ESignature");
			Object obj = SqlResources.getSqlMapProcessor(newCtx).findByKey("SqlStmts.UserStatementviewgetStatusIdByDesc", newCtx);
			if(obj != null){
				Map map = (Map)obj;
				newCtx.put("status", map.get("invitation_status_id"));
			}
			
			newCtx.put("document_id_var", response.getDocID());
			newCtx.put("doc_auth_token", response.getDocAuthToken());
			newCtx.put("document_type", "BGAuthorization");
			newCtx.put("last_updated_by", "publicadmin");
			newCtx.put("operationType", "I");
			newCtx.put("aar_requestid", ctx.get("aar_requestid"));
			newCtx.put("onboarding_method", ctx.get("onboarding_method")); 
			new SetParametersForStoredProcedures().setParametersInContext(newCtx, "status,aar_requestid,onboarding_method");
			SqlResources.getSqlMapProcessor(ctx).insert("agency_contracts.insert_update_esign_status_p_java", newCtx);
			
			logger.debug("Pdf Doc has sent to agency for esign.");
		}catch (Exception e) {
			DataUtils.populateError((Context)ctx, "carrierError", "Error in Sending document for ESign");
			logger.error("Unable to send document for ESignature due to error : " + e.getMessage());
		}
	}
	
	//Method created to generate DocRequest data for DocuSign
	private DocRequest generateDocRequestData(IContext ctx, byte [] bout) throws Exception {
		DocRequest docreq = new DocRequest();
		String contractCode = null;
		String identifier = null;
		Map infoMap = null;
		
		try{
			if(bout != null && !HtmlConstants.EMPTY.equals(bout)) 
				docreq.setContent(bout);
				
			if(ctx.get("contractCode") != null && !HtmlConstants.EMPTY.equals(ctx.get("contractCode"))){
				contractCode = ctx.get("contractCode").toString();
			}
			
			identifier = getDocumentIdentifier(contractCode);
			if(identifier != null && !HtmlConstants.EMPTY.equals(identifier)) 
				docreq.setIdentifier(identifier);
			
			if(ctx.get("contract_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("contract_id"))){ 
				infoMap = (Map)SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.sqlStatementsviewgetContractAgencyAgentInfo", ctx);
			}
			
			//logger.debug("Principal Admin Email Id " + infoMap.get("agentMail"));
			if(infoMap != null && !HtmlConstants.EMPTY.equals(infoMap)){
				List<SignerInfoBean> signers = new ArrayList<SignerInfoBean>();
				
				//getting Agency Signer Info
				SignerInfoBean agencySignerInfo = new SignerInfoBean();
				String adminMailURL = null;
				String adminName = null;
				
				//going to get email address from user profiles table for SD
				Context newCtx = new Context();
				newCtx.setProject(ctx.getProject());
				newCtx.put("roletobesearched", "SalesDirector");
				newCtx.put("aar_requestid", ctx.get("aar_requestid"));
				/*new SetParametersForStoredProcedures().setParametersInContext(newCtx, "aar_requestid");
				SqlResources.getSqlMapProcessor(newCtx).findByKey("agencyListPublicSubmitNInvitation.getRecentOnboardingWorkflowUser_p_java", newCtx);
				newCtx.put("workflow_user", newCtx.get("home_user_name"));*/
				
				/*if(newCtx.get("home_user_name") == null || "".equals(newCtx.get("home_user_name"))){
					newCtx.put("workflow_user", newCtx.get("user_id"));
				}*/
				newCtx.put("workflow_user", ctx.get("user_id"));
				Object obj = SqlResources.getSqlMapProcessor(newCtx).findByKey("SqlStmts.sqlStatementsviewgetWorkflowUserEmailAgencyAppointmentRequestById", newCtx);
				if(obj != null && obj instanceof Map){
					adminMailURL = ((Map)obj).get("e_mail") != null ? ((Map)obj).get("e_mail").toString() : null;
					adminName = ((Map)obj).get("user_name") != null ? ((Map)obj).get("user_name").toString() : null;
				}
				
				if(StringUtils.isBlank(adminMailURL)){
					if(SystemProperties.getInstance().getString("mail.admin.address") != null && !HtmlConstants.EMPTY.equals(SystemProperties.getInstance().getString("mail.admin.address"))) {
						adminMailURL = SystemProperties.getInstance().getString("mail.admin.address");
					}
				}
				
				if(StringUtils.isBlank(adminName)){
					adminName = ctx.get("clientName").toString();
				}
				
				agencySignerInfo.setEmailAddress(adminMailURL);
				agencySignerInfo.setName(adminName);
				
				agencySignerInfo.setIdentifier(ESignConstants.ANCHOR_TAG_AGENCY_SIGNER_IDENTIFIER);
				agencySignerInfo.setInitials("AIM");
 				signers.add(agencySignerInfo);
				
 				//getting Agent Signer info
				if(infoMap.get("firstname") != null && !HtmlConstants.EMPTY.equals(infoMap.get("firstname"))){
					SignerInfoBean agentSignerInfo = new SignerInfoBean();
					
					agentSignerInfo.setIdentifier(ESignConstants.ANCHOR_TAG_MERCURY_SIGNER_IDENTIFIER);
					if(infoMap.get("agentMail") != null && !HtmlConstants.EMPTY.equals(infoMap.get("agentMail")))
						agentSignerInfo.setEmailAddress(infoMap.get("agentMail").toString());
					
					String firstname = infoMap.get("firstname") != null ? infoMap.get("firstname").toString() : HtmlConstants.EMPTY;
					String lastname = infoMap.get("lastname") != null ? infoMap.get("lastname").toString() : HtmlConstants.EMPTY;
					agentSignerInfo.setName(firstname + " " + lastname);
					String firstnameInt = firstname.length() > 0 ? firstname.substring(0, 1) : HtmlConstants.EMPTY;
					String lastnameInt = lastname.length() > 0 ? lastname.substring(0, 1) : HtmlConstants.EMPTY;
					agentSignerInfo.setInitials(firstnameInt + lastnameInt);
				
					signers.add(agentSignerInfo);
				}
				
				docreq.setSignerInfo(signers);
			}
		}catch(Exception ex){
			
			//ex.printStackTrace();
			logger.error("Unable to execute generateDocRequestData method due to error : " + DataUtils.getExceptionStackTrace(ex));
		}
		
		return docreq;
	}
	
	//Method created to get contents from DMS of uploaded documents
	public byte[] downloadContractFromDMS(Context ctx) throws Exception {
		byte[] rb = null;
		try{
			
			String isDMSIntegrationdone = SystemProperties.getInstance().getString("dms.integrationdone");
			if(isDMSIntegrationdone != null && isDMSIntegrationdone.equals("Y")){
				//going to get DMS provider
				String dmsProvider = SystemProperties.getInstance().getString("dms.provider");
				if(dmsProvider != null){
					if(dmsProvider.equalsIgnoreCase("wss3")){
						//downloading file from sharepoint
						rb = new DocumentUploadBO().getDocumentFromSharePoint(ctx);
						
						if(rb == null){
							logger.debug("No file contents read from sharepoint");
							/*DataUtils.populateError((Context)ctx, "documentName", "downloadDocErrorKey");
							DataUtils.populateError((Context)ctx, "pageError", "downloadDocErrorKey");*/
							DataUtils.populateError((Context)ctx, "carrierError", "downloadDocErrorKey");
							return null;
						}
					}else if(dmsProvider.equalsIgnoreCase("filesystem")){
						String dmsUploadEnabled = SystemProperties.getInstance().getString("dms.enabled");
						if(dmsUploadEnabled != null && dmsUploadEnabled.equals("Y"))
							ctx.put("dmsUploadEnabled", ctx.get("is_uploaded_on_dms") != null && ctx.get("is_uploaded_on_dms").toString().equals("Y") ? "Y" : "N"); //to download document from DMS
						else
							ctx.put("dmsUploadEnabled", "N"); //to download document from filesystem
						
						//downloading file from DMS
						rb = new DocumentUploadBO().getDocumentFromDMS(ctx);
						
						if(rb == null){
							logger.debug("No file contents read from DMS");
							/*DataUtils.populateError((Context)ctx, "documentName", "downloadDocErrorKey");
							DataUtils.populateError((Context)ctx, "pageError", "downloadDocErrorKey");*/
							DataUtils.populateError((Context)ctx, "carrierError", "downloadDocErrorKey");
							return null;
						}
					} 
				}
			}else{
				logger.debug("DMS integration not done");
				/*DataUtils.populateError((Context)ctx, "documentName", "downloadDocErrorKey");
				DataUtils.populateError((Context)ctx, "pageError", "downloadDocErrorKey");*/
				DataUtils.populateError((Context)ctx, "carrierError", "downloadDocErrorKey");
				return null;
			}
		}catch(Exception e){
			logger.debug("Unable to read " + ctx.get("file_name").toString() + "file");
			logger.error("unable to read " + DataUtils.getExceptionStackTrace(e));
			/*DataUtils.populateError((Context)ctx, "documentName", "downloadDocErrorKey");
			DataUtils.populateError((Context)ctx, "pageError", "downloadDocErrorKey");*/
			DataUtils.populateError((Context)ctx, "carrierError", "downloadDocErrorKey");
			return null;
		}
		
		return rb;
	}
	
	//Method created to update database after got esign status
	public void sendForDocuSign(Context ctx,List<DocRequest> docRequestsList){
		DocResponse docRes = null; 
		ESignatureUtil esign = new ESignatureUtil();
		try{
			logger.debug("Going to hit DocuSign");
			docRes = esign.sendDocForESign(docRequestsList);
			logger.debug("Response got from DocuSign " + docRes);
		
			if(docRes == null || docRes.getCode().toString().equals(HtmlConstants.EMPTY) || 
					("ERROR".equalsIgnoreCase(docRes.getCode().toString()) || "FAILURE".equalsIgnoreCase(docRes.getCode().toString()))){
				DataUtils.populateError((Context)ctx, "carrierError", "Unable to send document for ESignature");
				logger.error("Unable to send document for ESignature due to error : " + docRes.getMessage());
				return;
			}
			
			Context newCtx = new Context();
			newCtx.setProject(ctx.getProject());
			newCtx.put("document_id_var", docRes.getIdentifier());
			newCtx.put("doc_auth_token", docRes.getToken());
			newCtx.put("document_type", "Contract");
			newCtx.put("last_updated_by", ctx.get("user_id"));
			newCtx.put("operationType", "I"); 
			if(ctx.get("agency_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("agency_id")))
				newCtx.put("agency_id", ctx.get("agency_id"));
			
			Map requestIdMap = (Map)SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.sqlStatementsviewgetRequestIdFromAgencyMaster", newCtx);
			if(requestIdMap != null && !HtmlConstants.EMPTY.equals(requestIdMap)){ 
				newCtx.put("aar_requestid", requestIdMap.get("requestid"));
			}
			if(docRes.getCode() != null && docRes.getCode().equalsIgnoreCase("SUCCESS")){
				newCtx.put("status_comment", "SENT");
			}
			
			//going to insert into database
			if(ctx.get(HtmlConstants.INET_METHOD) != null && ctx.get(HtmlConstants.INET_METHOD).toString().equals("submitContractForActiveAgency")){ //setting aar_requestid null in case of contract approval process for existing agencies
				newCtx.put("aar_requestid", null);
				newCtx.put("object_id", ctx.get("contract_id"));
			}
			newCtx.put("onboarding_method", ctx.get("onboarding_method"));
			new SetParametersForStoredProcedures().setParametersInContext(newCtx, "status,aar_requestid,onboarding_method");
			SqlResources.getSqlMapProcessor(ctx).insert("agency_contracts.insert_update_esign_status_p_java", newCtx);
		}catch (Throwable e1) {
			logger.error("Got error while DocuSign" + DataUtils.getExceptionStackTrace(e1));
			
		}
	}
	
	//Method created to update agency_appointmentrequest data
	public void updateAppointmentRequestDataInDB(IContext ctx) throws Exception {
		Map requestIdMap = (Map)SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.sqlStatementsviewgetRequestIdFromAgencyMaster", ctx);
		if(requestIdMap != null && !HtmlConstants.EMPTY.equals(requestIdMap)){ 
			ctx.put("aar_requestid", requestIdMap.get("requestid"));
		}
		
		//inserting status comment in database
		//ctx.put("comment", "Sent For ESignature");
		//ctx.put("last_updated_by", "publicadmin");
		
		logger.debug("Going to update status in database");
		Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.sqlStatementsviewgetAgencyApptRequestDataByAarRequestId", ctx);
		if(obj != null && obj instanceof Map){
			Map map = (Map)obj;
			ctx.put("workflow_function", map.get("workflow_function"));
			ctx.put("fromuser_id", map.get("workflow_user"));
			ctx.put("touser_id", map.get("workflow_user"));
			ctx.put("workflow_statusid", map.get("aar_statusid"));
		}
		
		//getting toworkflowuser_id for comment
		obj = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.sqlStatementsviewgetAarStatusId", ctx);
		if(obj != null && obj instanceof Map){
			ctx.put("toworkflow_statusid", ((Map)obj).get("aar_statusid"));
		}
		
		//inserting status comments
		SqlResources.getSqlMapProcessor(ctx).insert("SqlStmts.sqlStatementsviewinsertStatusComment", ctx);
		logger.debug("Status comment inserted");
		
		//updating aar_statusid if esign not configured
		SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.sqlStatementsviewupdateAarStatusIdToContractEsign", ctx);
	}
	
	
	//Method created to set data for DocuSign
	public void setDataForDocuESignDirect(IContext ctx) throws Exception {
		
				
		String htmlDir = SystemProperties.getInstance().getString("html.basedir");
		String templateFile = htmlDir + "foxsl/public";
		
		ctx.put("AGENT_SIGNER_IDENTIFIER", AGENT_SIGNER_IDENTIFIER+"1");
		ctx.put("DATE_IDENTIFIER", DATE_IDENTIFIER+"_"+1);
		ctx.put("applicationFormFlag", "N");
		ctx.put("backgroundCheckFormFlag", "Y");
		ctx.put("backgroundCheckFormMultipleFlag", "N");
			
		templateFile = templateFile + ".xsl"; 
		String xmlFile = htmlDir + "documenttempxml.xml";
		File file = new File(xmlFile);
		if(file.exists()){
			file.delete();
			file.createNewFile();
		}
		ProducerOneUtils.populateDataXml((Context)ctx, xmlFile);
		ByteArrayOutputStream bout = ProducerOneUtils.convertPOToPDF(templateFile, xmlFile, (Context)ctx, (ServletContextURIResolver)ctx.get("DocumentUriResolver"), (ServletContext)ctx.get("DocumentServletContext"));
					
		String personName = "";
		if(ctx.get("personName")!=null && !HtmlConstants.EMPTY.equals(ctx.get("personName").toString()))
			personName = ctx.get("personName").toString();
		
		personName = new ProducerOneUtils().excludeSpecialCharacters(personName);
		
		SimpleDateFormat sdf = new SimpleDateFormat("_yyyy-MM-dd_hh-mm-ss");
		String documentName =  "backgroundCheckESign_"+personName+sdf.format(new Date()) +".pdf"; 
		file = new File(htmlDir + documentName);
		if(file.exists()){
			file.delete();
			file.createNewFile();
		}
		FileOutputStream fout = new FileOutputStream(file);
		fout.write(bout.toByteArray());
		fout.close();
		
		logger.debug("Going to set data for DocuESign");
		try{
			DocuSignUtil docuSign = new DocuSignUtil();
			List<DocuSignRequest> docuSignRequestList = new ArrayList<DocuSignRequest>();
			if(ctx.get("bgEmail")!=null && !HtmlConstants.EMPTY.equals(ctx.get("bgEmail").toString())){
				String bgEmail = ctx.get("bgEmail").toString();
				String printName = ctx.get("printname").toString();
				String personInitial = ctx.get("personInitial").toString();
				
				DocuSignRequest docuSignRequest = new DocuSignRequest();
				List<RecipientInfoBean> recipientInfoList = new ArrayList<RecipientInfoBean>();
				RecipientInfoBean recipientInfo = new RecipientInfoBean();
				
				docuSignRequest.setContent(bout.toByteArray());
				docuSignRequest.setDocumentId(1);
				docuSignRequest.setIdentifier(ESignConstants.DOCUMENT_IDENTIFIER_APPBG);

				List<String> identiferList = new ArrayList<String>();
				identiferList.add(ESignConstants.AGENT_SIGNER_IDENTIFIER+""+1);
				identiferList.add(DATE_IDENTIFIER+"_"+1);
				recipientInfo.setRecipientIdentifierList(identiferList);
				
				recipientInfo.setRecipientId(1);
				recipientInfo.setEmailAddress(bgEmail);
				recipientInfo.setName(printName);
				recipientInfo.setInitials(personInitial);
				recipientInfoList.add(recipientInfo);
				
				docuSignRequest.setRecipientInfoList(recipientInfoList);
				
				docuSignRequest.setDocAnchorInfo(identiferList);
				
				docuSignRequestList.add(docuSignRequest);
					
			}else{
				logger.error("No Agent found with valid email");
			}
			
			
			EnvelopeStatus docRes = null; 
			String errorMsg = "";
			try{
				logger.debug("Going to hit DocuSign");
				docRes = docuSign.sendDocument(docuSignRequestList);
				logger.debug("Response got from DocuSign " + docRes);
			}catch (Throwable e1) {
				
				//e1.printStackTrace();
				logger.error("Unable to execute setDataForDocuESignDirect method due to error : " + DataUtils.getExceptionStackTrace(e1));
			}
			
			if(docRes == null || docRes.getStatus().equals(HtmlConstants.EMPTY) || 
					("ERROR".equalsIgnoreCase(docRes.getStatus().toString()) || "FAILURE".equalsIgnoreCase(docRes.getStatus().toString()))){
				
				DataUtils.populateError((Context)ctx, "bgPageError", "Unable to send document for ESignature");
				logger.error("Unable to send document for ESignature due to error : "+ errorMsg);
				return;
			}
			
			//going to insert int database
			Context newCtx = new Context();
			newCtx.setProject(ctx.getProject());
			
			newCtx.put("document_id_var", docRes.getEnvelopeID());
			//newCtx.put("doc_auth_token", docRes.);
			newCtx.put("document_type", "BGAuthorizationInPone");
			newCtx.put("last_updated_by", ctx.get("last_updated_by").toString());
			newCtx.put("operationType", "I");
			newCtx.put("object_id", ctx.get("person_id"));
			if(ctx.get("agency_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("agency_id")))
				newCtx.put("agency_id", ctx.get("agency_id"));
			
			Map requestIdMap = (Map)SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.sqlStatementsviewgetRequestIdFromAgencyMaster", newCtx);
			if(requestIdMap != null && !HtmlConstants.EMPTY.equals(requestIdMap)){
				if(requestIdMap.get("requestid") != null && !HtmlConstants.EMPTY.equals(requestIdMap.get("requestid").toString()))
					newCtx.put("aar_requestid", requestIdMap.get("requestid"));
				else
					newCtx.put("aar_requestid", "-1");
			}
			if(docRes.getStatus() != null && "SENT".equalsIgnoreCase(docRes.getStatus().toString())){
				newCtx.put("status_comment", "SENT");
			}
			newCtx.put("onboarding_method", ctx.get("onboarding_method"));
			//going to insert in database
			new SetParametersForStoredProcedures().setParametersInContext(newCtx, "status,agency_id,person_id,aar_requestid,onboarding_method");
			SqlResources.getSqlMapProcessor(ctx).insert("agency_contracts.insert_update_esign_status_p_java", newCtx);
			
			logger.debug("Pdf Doc has sent to agency for esign.");
		}catch (Exception e) {
			DataUtils.populateError((Context)ctx, "bgPageError","Error in Sending document for ESign");
			logger.error("Unable to send document for ESignature due to error : " + e.getMessage());
		}
	}
	 
	@SuppressWarnings("unchecked")
	public void setDataForDocuESignDirectForContract(IContext ctx) throws Exception {
		logger.debug("Going to set data for DocuSign in method setDataForDocuESignDirectForContract");
		
		List generatedContractList = null;
		String agentEmail = null;
		String personName = null;
		String agentInitials = null;
		
		String adminMailURL = null;
		String adminName = null;
		String adminInitials = null;
		
		boolean isSignerIsGiven=false;
		
		String identifier = null;
		String userId = null;
		 
		byte[] rb = null;
		String fileName = null;
		
		logger.debug("Going to get Contract Id.");
		
		if(ctx.get("contract_id") == null || HtmlConstants.EMPTY.equals(ctx.get("contract_id"))){
			logger.debug("Contract Id not found.");
			new SetParametersForStoredProcedures().setParametersInContext(ctx, "agency_id,AgentAddresType");
			Object ob=	SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.sqlStatementsviewGetContractIDForApproval",ctx);
			if(ob instanceof Map){
				Map map = (Map)ob;
				if(map != null && !map.isEmpty()){
					if(map.get("contract_id") != null && !HtmlConstants.EMPTY.equals(map.get("contract_id"))){
						ctx.put("contract_id", Integer.parseInt(map.get("contract_id").toString()));
						logger.debug("Going to get Contract Id :-"+map.get("contract_id"));
					}
				}
			}
		}
		
		if(ctx.get("contract_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("contract_id"))){ 
			logger.debug("Contract Id found.");
			
			String contractCode = null;
			
			//DocRequest docRequests = new DocRequest();
			//List<DocRequest> docRequestsList = new ArrayList<DocRequest>();
			DocuSignUtil docuSign = new DocuSignUtil();
			List<DocuSignRequest> docuSignRequestList = new ArrayList<DocuSignRequest>();
			
			try{
				logger.debug("Going to get Agent Info and mail ID");
				new SetParametersForStoredProcedures().setParametersInContext(ctx, "agency_id,AgentAddresType,contract_id,isSendContractToPersonSelectedInContract,contactTypeRequiredForContractDocusign");
				Object obj=	SqlResources.getSqlMapProcessor(ctx).findByKey("agency_contracts.GetAgencyPersonInfoForContract_p",ctx);
				if(obj instanceof Map){
					Map infoMap = (Map)obj;
					if(infoMap != null && !infoMap.isEmpty()){
						agentEmail = infoMap.get("email") != null ? infoMap.get("email").toString() : null;
						personName = infoMap.get("person_name") != null ? infoMap.get("person_name").toString() : null;
						agentInitials = infoMap.get("person_initials") != null ? infoMap.get("person_initials").toString() : null;
						ctx.put("person_id", infoMap.get("person_id") != null ? infoMap.get("person_id").toString() : null);
					}
				}
				
				
				
						
				String workflowName = ctx.get("workflow_name_context") != null ? ctx.get("workflow_name_context").toString() : null;
				String eventName = ctx.get("workflow_event_name_context") != null ? ctx.get("workflow_event_name_context").toString() : null;
				EventImpl eventImpl = ApplicationWorkflowResources.getInstance(ctx).getWorkflow(workflowName).getEvent(eventName);
			 	
				//Going to check if Carrier info given in signer node 	
				isSignerIsGiven=eventImpl.getSortedSignerList() != null && eventImpl.getSortedSignerList().size() > 0 ? true : false;
				
				
				
			    if(!isSignerIsGiven) {
			    	//Getting default carrier info

					if(ctx.get("user_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("user_id"))){
						String aar_statusid_desc = ctx.get("aar_statusid_desc") != null ? ctx.get("aar_statusid_desc").toString() : HtmlConstants.EMPTY;
					
						if(aar_statusid_desc.equals("ESignature Expired")){
							userId = ctx.get("user_id").toString();
							ctx.put("workflow_user_login", userId);
						}
						logger.debug("Going to get Login User mail ID");
						new SetParametersForStoredProcedures().setParametersInContext(ctx, "workflow_user_login,agency_id,carrier_based_on_mapping_for_contract_esign");
						Object obj1 = SqlResources.getSqlMapProcessor(ctx).findByKey("agencymaster_type.GetCarrierBasedOnMappingForContractEsign_p",ctx);
						if(obj1 instanceof Map){
							Map userMailMap = (Map)obj1;
							if(userMailMap != null && !userMailMap.isEmpty()){
								if(userMailMap.get("e_mail") != null && !HtmlConstants.EMPTY.equals(userMailMap.get("e_mail"))){
									adminMailURL = userMailMap.get("e_mail").toString();
									adminName = userMailMap.get("user_name") != null ? userMailMap.get("user_name").toString() : null;
								}
							}
						}
					}
					
					if(StringUtils.isBlank(adminMailURL)){
						if(SystemProperties.getInstance().getString("mail.admin.address") != null && !HtmlConstants.EMPTY.equals(SystemProperties.getInstance().getString("mail.admin.address"))) {
							adminMailURL = SystemProperties.getInstance().getString("mail.admin.address");
						}
					}
					
					if(StringUtils.isBlank(adminName)){
						adminName = ctx.get("clientName").toString();
					}
					
					adminInitials="AIM";
			    }
				
				
				
				
				
				
				
				
				
				logger.debug("Going to get Contract attachments List");
				
				new SetParametersForStoredProcedures().setParametersInContext(ctx, "contract_id");
				//generatedContractList = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.sqlStatementsviewgetContractAttachments", ctx);
				
				generatedContractList = SqlResources.getSqlMapProcessor(ctx).select("agency_document_attachments.getContractAttachmentsDataForDocusign_p", ctx);
				
				if(generatedContractList != null && generatedContractList.size() > 0){
					for(int i= 0; i<generatedContractList.size(); i++){
						contractCode = null;
						rb =  null;
						fileName =  null;
						
						Map generatedContractMap = (Map) generatedContractList.get(i);
						if(generatedContractMap != null && !HtmlConstants.EMPTY.equals(generatedContractMap)){
							if((generatedContractMap.get("esign_required") == null || HtmlConstants.EMPTY.equals(generatedContractMap.get("esign_required"))
									|| "N".equals(generatedContractMap.get("esign_required").toString()) || "0".equals(generatedContractMap.get("esign_required").toString()))
									&& (ctx.get("isSendingOnlyESignRequiredContractToDocuSign") == null || HtmlConstants.EMPTY.equals(ctx.get("isSendingOnlyESignRequiredContractToDocuSign"))
											|| ctx.get("isSendingOnlyESignRequiredContractToDocuSign").equals("Y")) ){
								
								if(!(ctx.get("isSendManuallyAttachedContractDocumentForEsign").equals("Y") && generatedContractMap.get("document_name").toString().toLowerCase().contains(".pdf")))
									continue;
							}
							
							if(generatedContractMap.get("document_name") != null && !HtmlConstants.EMPTY.equals(generatedContractMap.get("document_name"))){
								fileName = generatedContractMap.get("document_name").toString();
								
								if(generatedContractMap.get("contractCode") != null && !HtmlConstants.EMPTY.equals(generatedContractMap.get("contractCode")))
									contractCode = generatedContractMap.get("contractCode").toString();
								
								if(fileName != null && !HtmlConstants.EMPTY.equals(fileName)){ 
									ctx.put("file_name", fileName);
									
									if(generatedContractMap.get("document_id") != null && !HtmlConstants.EMPTY.equals(generatedContractMap.get("document_id").toString()))
										ctx.put("document_id", generatedContractMap.get("document_id").toString());
									else
										ctx.put("document_id", null);
									
									if(generatedContractMap.get("is_uploaded_on_dms") != null && !HtmlConstants.EMPTY.equals(generatedContractMap.get("is_uploaded_on_dms").toString()))
										ctx.put("is_uploaded_on_dms", generatedContractMap.get("is_uploaded_on_dms").toString());
									else
										ctx.put("is_uploaded_on_dms", null);
									
									ctx.put("contractCode", contractCode);
									
									logger.debug("Going to download contract from DMS for ContractCode : " + ctx.get("contractCode"));
									rb = downloadContractFromDMS((Context)ctx);
									logger.debug("Contract downloaded from DMD for ContractCode : " + ctx.get("contractCode"));
								}
							}
						}
						
					    logger.debug("Going to set data for DocuESign");
						
					    if(ctx.get("contractCode") != null && !HtmlConstants.EMPTY.equals(ctx.get("contractCode"))){
							contractCode = ctx.get("contractCode").toString();
						}
						//identifier = getDocumentIdentifierForContract(contractCode);
					    
					    identifier = contractCode+"_"+ESignConstants.DOCUMENT_IDENTIFIER_AGENCY_CONTRACT;
					    
						logger.debug("Identifier found "+identifier); 
						
						DocuSignRequest docuSignRequest = new DocuSignRequest();
						docuSignRequest.setContent(rb); 
						docuSignRequest.setDocumentId(i+1);
						
						docuSignRequest.setIdentifier(identifier);
						
						List<RecipientInfoBean> recipientInfoList = new ArrayList<RecipientInfoBean>();
						List<String> identiferList = new ArrayList<String>();
						List<String> identiferListForAnchorTab = new ArrayList<String>();
						
						
						
						
						//adding Agent recipient
						RecipientInfoBean recipientAgentInfo = new RecipientInfoBean();
						
						identiferList.add(ESignConstants.AGENT_SIGNER_IDENTIFIER);
						identiferList.add(DATE_IDENTIFIER);
						
						identiferListForAnchorTab.add(ESignConstants.AGENT_SIGNER_IDENTIFIER);
						identiferListForAnchorTab.add(DATE_IDENTIFIER);
						
						if(generatedContractMap.get("esign_required") == null || HtmlConstants.EMPTY.equals(generatedContractMap.get("esign_required"))
								|| "N".equals(generatedContractMap.get("esign_required").toString()) || "0".equals(generatedContractMap.get("esign_required").toString())){
							if(!(ctx.get("isSendManuallyAttachedContractDocumentForEsign").equals("Y") && generatedContractMap.get("document_name").toString().toLowerCase().contains(".pdf")))
								identiferList = new ArrayList<String>();
						}
						
						recipientAgentInfo.setRecipientIdentifierList(identiferList);
						
						recipientAgentInfo.setRecipientId(1);
						recipientAgentInfo.setEmailAddress(agentEmail);
						recipientAgentInfo.setName(personName);
						recipientAgentInfo.setInitials(agentInitials);
							
						if(ctx.get("isDocuSignFlowSequential") == null  || HtmlConstants.EMPTY.equals(ctx.get("isDocuSignFlowSequential"))
								|| "N".equals(ctx.get("isDocuSignFlowSequential").toString())) 
							recipientAgentInfo.setRoutingOrder(1);
						else if(ctx.get("isSendingDocuEsignToAgencyFirst") == null  || HtmlConstants.EMPTY.equals(ctx.get("isSendingDocuEsignToAgencyFirst"))
							|| "Y".equals(ctx.get("isSendingDocuEsignToAgencyFirst").toString())) {
							recipientAgentInfo.setRoutingOrder(1);
						}else {
							recipientAgentInfo.setRoutingOrder(2);
						}
							
						recipientInfoList.add(recipientAgentInfo);
						//Agent recipient Added
						
					
					
					
					
					    //adding carrier recipients
						if(isSignerIsGiven) {
							//adding carrier recipients from signer node
							
							
							setContractRecipientsFromSigner((Context)ctx, eventImpl, recipientInfoList, identiferListForAnchorTab, contractCode);
							/*Context newCtx = new Context();
							for(int j=0; j<eventImpl.getSortedSignerList().size(); j++){

								SignerImpl signerImpl = (SignerImpl)eventImpl.getSortedSignerList().get(j);
								
								
								String templateCodeValue = signerImpl.getTemplatecodevalue();
								
								if(StringUtils.isBlank(templateCodeValue))
									continue;
								
								
								
								boolean isGetUser = signerImpl.getIsgetuser();
								String name = signerImpl.getName();
								String type = signerImpl.getType();
								
								if(!type.equalsIgnoreCase("carrier"))
									continue;
								
								String carrierId = null;
								if(isGetUser) //getting value from online selection
									carrierId = ctx.get(name) != null ? ctx.get(name).toString() : null;
								else{
									String approverrole = signerImpl.getApproverrole();
									if(StringUtils.isBlank(approverrole)){
										continue;
									}
									
									if(approverrole.equalsIgnoreCase("roles"))
										carrierId = ctx.get("user_id") != null ? ctx.get("user_id").toString() : approverrole;
									else{
										approverrole = ctx.get(approverrole) != null ? ctx.get(approverrole).toString() : approverrole;
										
										newCtx.putAll(ctx);
										new DataUtils().getWorkflowAssignedToUserData(newCtx, signerImpl.getObjectid(), signerImpl.getObjecttype(), signerImpl.getObjectfunction(), "user_id", approverrole);
										carrierId = newCtx.get("user_id") != null ? newCtx.get("user_id").toString() : null; 
									}
								}
								
								if(StringUtils.isBlank(carrierId))
									continue;
								
								
								//going to get username, email, initials for signer
								newCtx = new Context();
								newCtx.setProject(ctx.getProject());
								newCtx.put("userid", carrierId);
								newCtx.put("agentEmailTypeForDocusignInContract", ctx.get("agentEmailTypeForDocusignInContract"));
								
								String params = "userid,agentEmailTypeForDocusignInContract";
								
								params = new DataUtils().checkForAdditionalParameters((Context) ctx, newCtx, params, "getDocuSignDataByUserId_p_additionalparameters");
								
								XMLUtils.generateRequestXml(newCtx, params, "inputXml");
								Map dataMap = (Map)SqlResources.getSqlMapProcessor(newCtx).findByKey("framework.getDocuSignDataByUserId_p", newCtx);
								new DataUtils().populateClobValue(dataMap);
					            DataUtils.parseDataXML(newCtx, dataMap);
								
					            String userName = newCtx.get("user_name") != null ? newCtx.get("user_name").toString() : null;
					            String email = newCtx.get("e_mail") != null ? newCtx.get("e_mail").toString() : null;
					            String initials = newCtx.get("initials") != null ? newCtx.get("initials").toString() : null;
								
								//adding signer
								RecipientInfoBean recipientInfo = new RecipientInfoBean();
								List<String> identist = new ArrayList<String>();
								
								if(templateCodeValue.equalsIgnoreCase(contractCode)) {
								identist.add("P"+templateCodeValue+"_CARRIER_SIGNER_");
								//identist.add(ESignConstants.DATE_SIGNER_IDENTFIER_1);	
								}
								
								
								
					            identiferListForAnchorTab.add("P"+templateCodeValue+"_CARRIER_SIGNER_");
								//identiferListForAnchorTab.add(ESignConstants.DATE_SIGNER_IDENTFIER_1);
					            
								
								recipientInfo.setRecipientIdentifierList(identist);
								
								recipientInfo.setRecipientId(j+2);
								recipientInfo.setEmailAddress(email);
								recipientInfo.setName(userName);
								recipientInfo.setInitials(initials);
									
								recipientInfo.setRoutingOrder(1);
								recipientInfoList.add(recipientInfo);
								
								
								
								
							}*/
							
							
						}
						else {
							//adding default carrier recipients
							RecipientInfoBean recipientInfo_Carrier = new RecipientInfoBean();
							
							List<String> identiferList2 = new ArrayList<String>();
							
							if(ctx.get("isCarrierESignRequiredonContract") == null || HtmlConstants.EMPTY.equals("isCarrierESignRequiredonContract")
									|| SecurityResources.getInstance(ctx).getAccessType("isCarrierESignRequiredonContract", (Context) ctx)==SecurityResources.SHOW){
								
								identiferListForAnchorTab.add(ESignConstants.CARRIER_SIGNER_IDENTIFIER);
								identiferListForAnchorTab.add(ESignConstants.DATE_SIGNER_IDENTFIER_1);
								
								identiferList2.add(ESignConstants.CARRIER_SIGNER_IDENTIFIER);
								identiferList2.add(ESignConstants.DATE_SIGNER_IDENTFIER_1);	
								
								recipientInfo_Carrier.setRecipientId(2);
								recipientInfo_Carrier.setEmailAddress(adminMailURL);
								recipientInfo_Carrier.setName(adminName);
								
								if(generatedContractMap.get("esign_required") == null || HtmlConstants.EMPTY.equals(generatedContractMap.get("esign_required"))
										|| "N".equals(generatedContractMap.get("esign_required").toString()) || "0".equals(generatedContractMap.get("esign_required").toString())){
								
									if(!(ctx.get("isSendManuallyAttachedContractDocumentForEsign").equals("Y") && generatedContractMap.get("document_name").toString().toLowerCase().contains(".pdf")))
									identiferList2 = new ArrayList<String>();
								}
								
								recipientInfo_Carrier.setRecipientIdentifierList(identiferList2);
								
								if(ctx.get("isDocuSignFlowSequential") == null || HtmlConstants.EMPTY.equals(ctx.get("isDocuSignFlowSequential"))
										|| "N".equals(ctx.get("isDocuSignFlowSequential").toString())) 
									recipientInfo_Carrier.setRoutingOrder(1);
								else {
									if(ctx.get("isSendingDocuEsignToAgencyFirst") == null || HtmlConstants.EMPTY.equals(ctx.get("isSendingDocuEsignToAgencyFirst"))
											|| "Y".equals(ctx.get("isSendingDocuEsignToAgencyFirst").toString())) {
										recipientInfo_Carrier.setRoutingOrder(2);
									}else {
										recipientInfo_Carrier.setRoutingOrder(1);
									}
								}
									
								recipientInfo_Carrier.setInitials(adminInitials);
								recipientInfoList.add(recipientInfo_Carrier);
							}	
						}
						
						
								
					
						
						
						
						
						docuSignRequest.setRecipientInfoList(recipientInfoList);
						docuSignRequest.setDocAnchorInfo(identiferListForAnchorTab);
						docuSignRequestList.add(docuSignRequest);
					}//ended for loop
					
					EnvelopeStatus docRes = null; 
					String errorMsg = "";
					try{
						logger.debug("Going to hit DocuSign");
						logger.debug("Values of docuSignRequestList : "+docuSignRequestList);
						docRes = docuSign.sendDocument(docuSignRequestList);
						logger.debug("Response got from DocuSign " + docRes);
					}catch (Throwable e1) {
						logger.error("Unable to execute setDataForDocuESignDirectForContract method due to error : " + DataUtils.getExceptionStackTrace(e1));
					}
					
					if(docRes == null || docRes.getStatus().equals(HtmlConstants.EMPTY) || 
							("ERROR".equalsIgnoreCase(docRes.getStatus().toString()) || "FAILURE".equalsIgnoreCase(docRes.getStatus().toString()))){
						DataUtils.populateError((Context)ctx, "carrierError", "Unable to send document for ESignature");
						logger.error("Unable to send document for ESignature due to error : "+ errorMsg);
						return;
					}
					
					//going to insert into database
					Context newCtx = new Context();
					newCtx.setProject(ctx.getProject());
					newCtx.put("document_id_var", docRes.getEnvelopeID());
					//newCtx.put("doc_auth_token", docRes.);
					newCtx.put("document_type", "Contract");
					newCtx.put("last_updated_by", ctx.get("user_id").toString());
					newCtx.put("operationType", "I");
					newCtx.put("object_id", ctx.get("contract_id"));
					
					if(ctx.get("agency_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("agency_id")))
						newCtx.put("agency_id", ctx.get("agency_id"));
					
					Map requestIdMap = (Map)SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.sqlStatementsviewgetRequestIdFromAgencyMaster", newCtx);
					if(requestIdMap != null && !HtmlConstants.EMPTY.equals(requestIdMap)){
						if(requestIdMap.get("requestid") != null && !HtmlConstants.EMPTY.equals(requestIdMap.get("requestid").toString()))
							newCtx.put("aar_requestid", requestIdMap.get("requestid"));
						else
							newCtx.put("aar_requestid", null);
					}
					
					if(docRes.getStatus() != null && "SENT".equalsIgnoreCase(docRes.getStatus().toString())){
						newCtx.put("status_comment", "SENT");
					}
					
					newCtx.put("onboarding_method", ctx.get("onboarding_method"));
					logger.debug("Going to insert in database.");
					new SetParametersForStoredProcedures().setParametersInContext(newCtx, "object_id,status,agency_id,person_id,aar_requestid,onboarding_method");
					SqlResources.getSqlMapProcessor(ctx).insert("agency_contracts.insert_update_esign_status_p_java", newCtx);
					
					ctx.put("generatedContractList",generatedContractList);
					ProducerOneUtils.convertFieldNListStringToXMLWithCustomRootElement((Context)ctx,"generatedContractList" , "Root", "Root1", "outputXML");
					newCtx.put("outputXML",ctx.get("outputXML"));
					new SetParametersForStoredProcedures().setParametersInContext(newCtx, "outputXML");
					SqlResources.getSqlMapProcessor(ctx).update("agency_contracts.updateSentForEsignatureFlagInContracts_p", newCtx);
					
					logger.debug("Pdf Doc has sent to agency for esign.");
				}
			}catch(Exception ex){
				DataUtils.populateError((Context)ctx, "carrierError", "Unable to send document for ESignature");
				logger.error("Unable to send document for ESignature due to error : " + DataUtils.getExceptionStackTrace(ex));
			}
		}
	}
	
	//Method created to get Document Identifier For Contract-- Not in Use Now
		private String getDocumentIdentifierForContract(String contractCode){
			String identifier = null;
			if(StringUtils.isBlank(contractCode))
				return null;
			
			if("01".equals(contractCode))
				identifier = ESignConstants.DOCUMENT_IDENTIFIER_AGENCY_CONTRACT;
			else if("02".equals(contractCode))
				identifier = ESignConstants.DOCUMENT_IDENTIFIER_GA_ADDENDUM;
			else if("03".equals(contractCode))
				identifier = ESignConstants.DOCUMENT_IDENTIFIER_GA_COMMISSION_CONTRACT;
			else if("04".equals(contractCode))
				identifier = ESignConstants.DOCUMENT_IDENTIFIER_AZ_COMMISSION_CONTRACT;
			else if ("05".equals(contractCode))
				identifier = ESignConstants.DOCUMENT_IDENTIFIER_CA_COMMISSION_CONTRACT;
			else if("06".equals(contractCode))
				identifier = ESignConstants.DOCUMENT_IDENTIFIER_NV_COMMISSION_CONTRACT;
			else
				identifier = contractCode+"_"+ESignConstants.DOCUMENT_IDENTIFIER_SUFFIX_COMMISSIONCONTRACT;
			
			return identifier ;
		}
		
		public void setDataForDocuESignDirectForSelfService(IContext ctx) throws Exception {
			logger.debug("Method Calling for Self Service.");

			logger.debug("Going to set data for DocuSign in method setDataForDocuESignDirectForContract");
			List generatedContractList = null;
			String agentEmail = null;
			String personName = null;
			String personInitial = null;
			String adminMailURL = null;
			String identifier = null;
			String userId = null;
			String adminName = null; 
			byte[] rb = null;
			String fileName = null;
			
			
			if(ctx.get("request_ss_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("request_ss_id"))){ 
				logger.debug("Request_ss Id found.");
				String contractCode = null;
				DocRequest docRequests = new DocRequest();
				List<DocRequest> docRequestsList = new ArrayList<DocRequest>();
				DocuSignUtil docuSign = new DocuSignUtil();
				List<DocuSignRequest> docuSignRequestList = new ArrayList<DocuSignRequest>();
				try{
					logger.debug("Going to get Agent Info and mail ID");
//					new SetParametersForStoredProcedures().setParametersInContext(ctx, "request_ss_id");
//					Object obj=	SqlResources.getSqlMapProcessor(ctx).findByKey("agency_contracts.GetAgencyPersonInfoForContract_p",ctx);
					
					if(ctx.get("requestTypeForDefaultopen") !=null && !HtmlConstants.EMPTY.equals(ctx.get("requestTypeForDefaultopen").toString()) && ctx.get("requestTypeForDefaultopen").equals("AgentRequest")){
					
						ctx.put("document_category", "51");
						Context newContext = new Context();
			    		newContext.setProject(ctx.getProject());
			    		newContext.put("agency_id", ctx.get("agency_id"));
			    		newContext.put("agentEmailAddressType", ctx.get("agentEmailAddressType"));
			    		
			    		
			    		new SetParametersForStoredProcedures().setParametersInContext(newContext, "agency_id,agentEmailAddressType");
			    		Map agentEmailDetailMap= (Map)SqlResources.getSqlMapProcessor(newContext).findByKey("person_ss.getHighestRankingPrincipalEmailSelfServiceByAgencyID_p_java", newContext);
			    		
			    		if(agentEmailDetailMap.get("email_additionalAddress") !=null && !HtmlConstants.EMPTY.equals(agentEmailDetailMap.get("email_additionalAddress").toString()))
			    			agentEmail=agentEmailDetailMap.get("email_additionalAddress").toString();
			    		
			    		if(agentEmailDetailMap.get("contact_fullName") !=null && !HtmlConstants.EMPTY.equals(agentEmailDetailMap.get("contact_fullName").toString()))
			    			personName=agentEmailDetailMap.get("contact_fullName").toString();
			    			
			    		
			    		
						
					}else if(ctx.get("requestTypeForDefaultopen") !=null && !HtmlConstants.EMPTY.equals(ctx.get("requestTypeForDefaultopen").toString()) && ctx.get("requestTypeForDefaultopen").equals("FloodInsuranceRequest")){
					
						ctx.put("document_category", "55");
						Context newContext = new Context();
			    		newContext.setProject(ctx.getProject());
			    		newContext.put("agency_id", ctx.get("agency_id"));
			    		newContext.put("agentEmailAddressType", ctx.get("agentEmailAddressType"));
			    		
			    		
			    		new SetParametersForStoredProcedures().setParametersInContext(newContext, "agency_id,agentEmailAddressType");
			    		Map agentEmailDetailMap= (Map)SqlResources.getSqlMapProcessor(newContext).findByKey("person_ss.getHighestRankingPrincipalEmailSelfServiceByAgencyID_p_java", newContext);
			    		
			    		if(agentEmailDetailMap.get("email_additionalAddress") !=null && !HtmlConstants.EMPTY.equals(agentEmailDetailMap.get("email_additionalAddress").toString()))
			    			agentEmail=agentEmailDetailMap.get("email_additionalAddress").toString();
			    		
			    		if(agentEmailDetailMap.get("contact_fullName") !=null && !HtmlConstants.EMPTY.equals(agentEmailDetailMap.get("contact_fullName").toString()))
			    			personName=agentEmailDetailMap.get("contact_fullName").toString();
			    			
			    		
			    		
						
					}else if(ctx.get("requestTypeForDefaultopen") !=null && !HtmlConstants.EMPTY.equals(ctx.get("requestTypeForDefaultopen").toString()) && ctx.get("requestTypeForDefaultopen").equals("AgencyBankRequest")){
						agentEmail = ctx.get("esign_email_address") != null ? ctx.get("esign_email_address").toString() : null;
						personName = ctx.get("bank_contact_name") != null ? ctx.get("bank_contact_name").toString() : null;
						ctx.remove("bank_contact_name");
						ctx.put("document_category", "59");
					}
					
					
					personInitial = "A G";
					
					
					if(StringUtils.isBlank(adminMailURL)){
						if(SystemProperties.getInstance().getString("mail.admin.address") != null && !HtmlConstants.EMPTY.equals(SystemProperties.getInstance().getString("mail.admin.address"))) {
							adminMailURL = SystemProperties.getInstance().getString("mail.admin.address");
						}
					}
					
					logger.debug("Going to get Requests attachments List");
					new SetParametersForStoredProcedures().setParametersInContext(ctx, "request_ss_id,document_category");
					//generatedContractList = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.sqlStatementsviewgetContractAttachments", ctx);
					
					generatedContractList = SqlResources.getSqlMapProcessor(ctx).select("agency_document_attachments.getBankInformationForSelfServiceAttachmentsDataForDocusign_p", ctx);
					
					if(generatedContractList != null && generatedContractList.size() > 0){
						for(int i= 0; i<generatedContractList.size(); i++){
							contractCode = null;
							rb =  null;
							fileName =  null;
							Map generatedContractMap = (Map) generatedContractList.get(i);
							if(generatedContractMap != null && !HtmlConstants.EMPTY.equals(generatedContractMap)){
								
								generatedContractMap.put("esign_required","Y");
								generatedContractMap.put("contractCode",(i+1)+"_code");
								if((generatedContractMap.get("esign_required") == null || HtmlConstants.EMPTY.equals(generatedContractMap.get("esign_required"))
										|| "N".equals(generatedContractMap.get("esign_required").toString()) || "0".equals(generatedContractMap.get("esign_required").toString()))
										&& (ctx.get("isSendingOnlyESignRequiredContractToDocuSign") == null || HtmlConstants.EMPTY.equals(ctx.get("isSendingOnlyESignRequiredContractToDocuSign"))
												|| ctx.get("isSendingOnlyESignRequiredContractToDocuSign").equals("Y")) ){
									continue;
								}
								
								
								if(generatedContractMap.get("document_name") != null && !HtmlConstants.EMPTY.equals(generatedContractMap.get("document_name"))){
									fileName = generatedContractMap.get("document_name").toString();
									if(generatedContractMap.get("contractCode") != null && !HtmlConstants.EMPTY.equals(generatedContractMap.get("contractCode")))
										contractCode = generatedContractMap.get("contractCode").toString();
									
									if(fileName != null && !HtmlConstants.EMPTY.equals(fileName)){ 
										ctx.put("file_name", fileName);
										
										if(generatedContractMap.get("document_id") != null && !HtmlConstants.EMPTY.equals(generatedContractMap.get("document_id").toString()))
											ctx.put("document_id", generatedContractMap.get("document_id").toString());
										else
											ctx.put("document_id", null);
										
										if(generatedContractMap.get("is_uploaded_on_dms") != null && !HtmlConstants.EMPTY.equals(generatedContractMap.get("is_uploaded_on_dms").toString()))
											ctx.put("is_uploaded_on_dms", generatedContractMap.get("is_uploaded_on_dms").toString());
										else
											ctx.put("is_uploaded_on_dms", null);
										
										
										ctx.put("contractCode", contractCode);
										
										logger.debug("Going to download contract from DMS for ContractCode : " + ctx.get("contractCode"));
										rb = downloadContractFromDMS((Context)ctx);
										logger.debug("Contract downloaded from DMD for ContractCode : " + ctx.get("contractCode"));
										}
									}
								}
							    logger.debug("Going to set data for DocuESign");
								
							    if(ctx.get("contractCode") != null && !HtmlConstants.EMPTY.equals(ctx.get("contractCode"))){
									contractCode = ctx.get("contractCode").toString();
								}
								//identifier = getDocumentIdentifierForContract(contractCode);
							    
							    identifier =  contractCode+"_"+ESignConstants.DOCUMENT_IDENTIFIER_AGENCY_REQUEST;
							    
								logger.debug("Identifier found "+identifier); 
								DocuSignRequest docuSignRequest = new DocuSignRequest();
								List<RecipientInfoBean> recipientInfoList = new ArrayList<RecipientInfoBean>();
								RecipientInfoBean recipientInfo = new RecipientInfoBean();
								
								docuSignRequest.setContent(rb); 
								docuSignRequest.setDocumentId(i+1);
								
								docuSignRequest.setIdentifier(identifier);
								List<String> identiferList = new ArrayList<String>();
								//identiferList.add(ESignConstants.AGENT_SIGNER_IDENTIFIER+""+(i+1));
								identiferList.add(ESignConstants.AGENT_SIGNER_IDENTIFIER);
								//identiferList.add(DATE_IDENTIFIER+"_"+(i+1));
								identiferList.add(DATE_IDENTIFIER);
								
								//identiferList.add(ESignConstants.CARRIER_SIGNER_IDENTIFIER+""+(i+1));
								//identiferList.add(ESignConstants.CARRIER_SIGNER_IDENTIFIER);
								
								if(generatedContractMap.get("esign_required") == null || HtmlConstants.EMPTY.equals(generatedContractMap.get("esign_required"))
										|| "N".equals(generatedContractMap.get("esign_required").toString()) || "0".equals(generatedContractMap.get("esign_required").toString())){
									identiferList = new ArrayList<String>();
								}
								recipientInfo.setRecipientIdentifierList(identiferList);
								
								recipientInfo.setRecipientId(1);
								recipientInfo.setEmailAddress(agentEmail);
								recipientInfo.setName(personName);
								recipientInfo.setInitials(personInitial);
								
								if(ctx.get("isDocuSignFlowSequential") == null  || HtmlConstants.EMPTY.equals(ctx.get("isDocuSignFlowSequential"))
										|| "N".equals(ctx.get("isDocuSignFlowSequential").toString())) 
									recipientInfo.setRoutingOrder(1);
								else if(ctx.get("isSendingDocuEsignToAgencyFirst") == null  || HtmlConstants.EMPTY.equals(ctx.get("isSendingDocuEsignToAgencyFirst"))
									|| "Y".equals(ctx.get("isSendingDocuEsignToAgencyFirst").toString())) {
									recipientInfo.setRoutingOrder(1);
									}else {
										recipientInfo.setRoutingOrder(2);
									}
									
								recipientInfoList.add(recipientInfo);
								
								docuSignRequest.setDocAnchorInfo(identiferList);
								docuSignRequest.setRecipientInfoList(recipientInfoList);
								docuSignRequestList.add(docuSignRequest);
									
							
						}
						EnvelopeStatus docRes = null; 
						String errorMsg = "";
						try{
							logger.debug("Going to hit DocuSign");
							logger.debug("Values of docuSignRequestList : "+docuSignRequestList);
							docRes = docuSign.sendDocument(docuSignRequestList);
							logger.debug("Response got from DocuSign " + docRes);
						}catch (Throwable e1) {
							
							//e1.printStackTrace();
							logger.error("Unable to execute setDataForDocuESignDirectForContract method due to error : " + DataUtils.getExceptionStackTrace(e1));
						}
						if(docRes == null || docRes.getStatus().equals(HtmlConstants.EMPTY) || 
								("ERROR".equalsIgnoreCase(docRes.getStatus().toString()) || "FAILURE".equalsIgnoreCase(docRes.getStatus().toString()))){
							
							DataUtils.populateError((Context)ctx, "carrierError", "Unable to send document for ESignature");
							logger.error("Unable to send document for ESignature due to error : "+ errorMsg);
							return;
						}
						
						//going to insert int database
						Context newCtx = new Context();
						newCtx.setProject(ctx.getProject());
						newCtx.put("document_id_var", docRes.getEnvelopeID());
						//newCtx.put("doc_auth_token", docRes.);
						newCtx.put("document_type", "Self Service");
						newCtx.put("last_updated_by", ctx.get("user_id").toString());
						newCtx.put("operationType", "I");

						newCtx.put("object_id", ctx.get("request_ss_id"));

						
						if(ctx.get("agency_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("agency_id")))
							newCtx.put("agency_id", ctx.get("agency_id"));
						
						Map requestIdMap = (Map)SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.sqlStatementsviewgetRequestIdFromAgencyMaster", newCtx);
						if(requestIdMap != null && !HtmlConstants.EMPTY.equals(requestIdMap)){
							if(requestIdMap.get("requestid") != null && !HtmlConstants.EMPTY.equals(requestIdMap.get("requestid").toString()))
								newCtx.put("aar_requestid", requestIdMap.get("requestid"));
							else
								newCtx.put("aar_requestid", null);
						}
						if(docRes.getStatus() != null && "SENT".equalsIgnoreCase(docRes.getStatus().toString())){
							newCtx.put("status_comment", "SENT");
						}
						newCtx.put("onboarding_method", ctx.get("onboarding_method"));
						logger.debug("Going to insert in database.");
						new SetParametersForStoredProcedures().setParametersInContext(newCtx, "object_id,status,agency_id,person_id,aar_requestid,onboarding_method");
						SqlResources.getSqlMapProcessor(ctx).insert("agency_contracts.insert_update_esign_status_p_java", newCtx);
						
						newCtx.get("document_type");
						
						ctx.put("generatedContractList",generatedContractList);
						ProducerOneUtils.convertFieldNListStringToXMLWithCustomRootElement((Context)ctx,"generatedContractList" , "Root", "Root1", "outputXML");
						newCtx.put("outputXML",ctx.get("outputXML"));
						new SetParametersForStoredProcedures().setParametersInContext(newCtx, "outputXML");
						SqlResources.getSqlMapProcessor(ctx).update("agency_contracts.updateSentForEsignatureFlagInContracts_p", newCtx);
						logger.debug("Pdf Doc has sent to agency for esign.");
					}
				}catch(Exception ex){
					DataUtils.populateError((Context)ctx, "carrierError", "Unable to send document for ESignature");
					logger.error("Unable to send document for ESignature due to error : " + DataUtils.getExceptionStackTrace(ex));
				}
			}
		
		}
		
	public void sendApplicationWorkflowDocuESign(Context ctx) throws Exception {
		try{
			if(logger.isDebugEnabled(ctx))
				logger.debug(ctx, "Going to send document for ESign for termination");
			
			String className = this.getClass().getName();
			if(className.contains("."))
				className = className.substring(className.lastIndexOf(".")+1, className.length());
			
			String objectId = className+"_objectid";
			String docCategory = className+"_doccategory";
			
			objectId = ctx.get(objectId) != null ? ctx.get(objectId).toString() : objectId;
			docCategory = ctx.get(docCategory) != null ? ctx.get(docCategory).toString() : docCategory;
			
			if(StringUtils.isBlank(objectId)){
				if(logger.isDebugEnabled(ctx)){
					logger.debug(ctx, "No objectid attribute found");
					return;
				}
			}
			
			if(StringUtils.isBlank(docCategory)){
				if(logger.isDebugEnabled(ctx)){
					logger.debug(ctx, "No doccategory attribute found");
					return;
				}
			}
				
			objectId = ctx.get(objectId) != null ? ctx.get(objectId).toString() : objectId;
			docCategory = ctx.get(docCategory) != null ? ctx.get(docCategory).toString() : docCategory;
			
			//if(ctx.get("termination_log_id") != null && !ctx.get("termination_log_id").toString().equals(HtmlConstants.EMPTY)){
			if(objectId != null){
				DocuSignUtil docuSign = new DocuSignUtil();
				List<DocuSignRequest> docuSignRequestList = new ArrayList<DocuSignRequest>();
				
				//ctx.put("document_category", "39");
				
				Context newCtx = new Context();
				newCtx.setProject(ctx.getProject());
				
				newCtx.put("object_id", objectId);
				newCtx.put("document_category", docCategory);
				//List docsList = SqlResources.getSqlMapProcessor(ctx).select("agency_document_attachments.getAgencyTerminationDocuments_p", ctx);

				XMLUtils.generateRequestXml(newCtx, "object_id,document_category", "inputXml");
  				new SetParametersForStoredProcedures().setParametersInContext(newCtx, "inputXml");
				List docsList = SqlResources.getSqlMapProcessor(newCtx).select("framework.getAttachedDocumentsListByObjectId_p", newCtx);
				if(docsList != null && docsList.size() > 0){
					for(int i=0; i<docsList.size(); i++){
						Map map = (Map)docsList.get(i);
						
						if(map.get("esign_required") == null || !map.get("esign_required").toString().equals("1"))
							continue;
						
						String documentCategory = map.get("document_category") != null ? map.get("document_category").toString() : null;
						String templateCode = map.get("document_template_type_code") != null ? map.get("document_template_type_code").toString() : null;
						
						String identifier = documentCategory + '_' + templateCode+"_"+ESignConstants.DOCUMENT_IDENTIFIER_AGENCY_CONTRACT;
						
						DocuSignRequest docuSignRequest = new DocuSignRequest();
						
						newCtx.put("document_id", map.get("document_id"));
		  				newCtx.put("document_name", map.get("document_name"));
		  				newCtx.put("file_name", map.get("document_name"));
		  				
		  				//reading file
		  				byte[] rb = new DataUtils().getDocumentFromDMS(newCtx);
		  				
						docuSignRequest.setContent(rb); 
						docuSignRequest.setDocumentId(i+1);
						
						docuSignRequest.setIdentifier(identifier);
						
						List<RecipientInfoBean> recipientInfoList = new ArrayList<RecipientInfoBean>();
						List<String> identiferListForAnchorTab = new ArrayList<String>();
						
						String workflowName = ctx.get("workflow_name_context").toString();
						String eventName = ctx.get("workflow_event_name_context").toString(); 
						
						//in case of <approver> nodes given
						if(ctx.get("workflow_disapproved_status_event_name_context") != null && !ctx.get("workflow_disapproved_status_event_name_context").toString().equals(HtmlConstants.EMPTY)){
							eventName =	ctx.get("workflow_disapproved_status_event_name_context").toString();
							
							EventImpl eventImpl = ApplicationWorkflowResources.getInstance(ctx).getWorkflow(workflowName).getEvent(eventName);
							if(eventImpl.getSortedSignerList() != null && eventImpl.getSortedSignerList().size() > 0){
								//checking for templatecode
								setSigner(ctx, eventImpl, recipientInfoList, identiferListForAnchorTab, templateCode, ctx.get("workflow_approvername_context").toString(), true, true);
								
								//checking w/o templatecode
								setSigner(ctx, eventImpl, recipientInfoList, identiferListForAnchorTab, null, ctx.get("workflow_approvername_context").toString(), true, false);
							}
						
							docuSignRequest.setRecipientInfoList(recipientInfoList);
							docuSignRequest.setDocAnchorInfo(identiferListForAnchorTab);
						
							docuSignRequestList.add(docuSignRequest);
						}else{ //when no <approver> nodes given
							EventImpl eventImpl = ApplicationWorkflowResources.getInstance(ctx).getWorkflow(workflowName).getEvent(eventName);
							if(eventImpl.getSortedSignerList() != null && eventImpl.getSortedSignerList().size() > 0){
								//checking for templatecode
								setSigner(ctx, eventImpl, recipientInfoList, identiferListForAnchorTab, templateCode, null, false, true);
								
								//checking w/o templatecode
								setSigner(ctx, eventImpl, recipientInfoList, identiferListForAnchorTab, null, null, false, false);
								
								docuSignRequest.setRecipientInfoList(recipientInfoList);
								docuSignRequest.setDocAnchorInfo(identiferListForAnchorTab);
							
								docuSignRequestList.add(docuSignRequest);
							}
						}
					}
				}//ended doc loop
				
				if(docuSignRequestList != null && docuSignRequestList.size() > 0){
					EnvelopeStatus docRes = null; 
					String errorMsg = "";
					try{
						logger.debug("Going to hit DocuSign");
						logger.debug("Values of docuSignRequestList : "+docuSignRequestList);
						docRes = docuSign.sendDocument(docuSignRequestList);
						logger.debug("Response got from DocuSign " + docRes);
					}catch (Throwable e1) {
						logger.error("Unable to execute setDataForDocuESignDirectForContract method due to error : " + DataUtils.getExceptionStackTrace(e1));
					}
					
					if(docRes == null || docRes.getStatus().equals(HtmlConstants.EMPTY) || 
							("ERROR".equalsIgnoreCase(docRes.getStatus().toString()) || "FAILURE".equalsIgnoreCase(docRes.getStatus().toString()))){
						DataUtils.populateError((Context)ctx, "carrierError", "Unable to send document for ESignature");
						logger.error("Unable to send document for ESignature due to error : "+ errorMsg);
						return;
					}
					
					//going to insert into database
					newCtx = new Context();
					newCtx.setProject(ctx.getProject());
					newCtx.put("document_id_var", docRes.getEnvelopeID());
					//newCtx.put("doc_auth_token", docRes.);
					newCtx.put("document_type", "Agency Termination");
					newCtx.put("last_updated_by", ctx.get("user_id").toString());
					newCtx.put("operationType", "I");
					newCtx.put("object_id", ctx.get("termination_log_id"));
					
					if(ctx.get("agency_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("agency_id")))
						newCtx.put("agency_id", ctx.get("agency_id"));
					
					Map requestIdMap = (Map)SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.sqlStatementsviewgetRequestIdFromAgencyMaster", newCtx);
					if(requestIdMap != null && !HtmlConstants.EMPTY.equals(requestIdMap)){
						if(requestIdMap.get("requestid") != null && !HtmlConstants.EMPTY.equals(requestIdMap.get("requestid").toString()))
							newCtx.put("aar_requestid", requestIdMap.get("requestid"));
						else
							newCtx.put("aar_requestid", null);
					}
					
					if(docRes.getStatus() != null && "SENT".equalsIgnoreCase(docRes.getStatus().toString())){
						newCtx.put("status_comment", "SENT");
					}
					
					newCtx.put("onboarding_method", ctx.get("onboarding_method"));
					logger.debug("Going to insert in database.");
					new SetParametersForStoredProcedures().setParametersInContext(newCtx, "object_id,status,agency_id,person_id,aar_requestid,onboarding_method");
					SqlResources.getSqlMapProcessor(ctx).insert("agency_contracts.insert_update_esign_status_p_java", newCtx);
					
					ctx.put("generatedContractList", docsList);
					ProducerOneUtils.convertFieldNListStringToXMLWithCustomRootElement((Context)ctx,"generatedContractList" , "Root", "Root1", "outputXML");
					newCtx.put("outputXML",ctx.get("outputXML"));
					new SetParametersForStoredProcedures().setParametersInContext(newCtx, "outputXML");
					SqlResources.getSqlMapProcessor(ctx).update("agency_contracts.updateSentForEsignatureFlagInContracts_p", newCtx);
					
					logger.debug("Pdf Doc has sent to agency for esign.");
				}
			}
		}catch(Exception e){
			logger.error("Unable to send document for ESign for termination due to error : " + e.getMessage());
		}
	}

	//Method created to set 
	private void setSigner(Context ctx, EventImpl eventImpl, List recipientInfoList, List identiferListForAnchorTab, String templateCode, 
			String approverName, boolean isCheckApprover, boolean isCheckTemplate){
		try{
			Context newCtx = new Context();
			
			for(int j=0; j<eventImpl.getSortedSignerList().size(); j++){
				SignerImpl signerImpl = (SignerImpl)eventImpl.getSortedSignerList().get(j);
				
				if(StringUtils.isNotBlank(signerImpl.getEval())){
					if(DataUtils.getAccessType(ctx, signerImpl.getEval()) == 0)
						continue;
				}
				
				String templateCodeValue = signerImpl.getTemplatecodevalue();
				
				if(StringUtils.isBlank(templateCodeValue))
					continue;
				
				if(isCheckApprover){
					String attachedToApprover = signerImpl.getAttachtoapprover();
					
					if(StringUtils.isBlank(attachedToApprover))
						continue;
					
					if(!approverName.equals(attachedToApprover))
						continue;
				}else{
					if(StringUtils.isNotBlank(signerImpl.getAttachtoapprover()))
						continue;
				}
					
				
				if(isCheckTemplate){
					if(StringUtils.isBlank(templateCodeValue))
						continue;
					
					templateCodeValue = "," + templateCodeValue + ",";
					
					if(!templateCodeValue.contains(","+templateCode+","))
						continue;
				}else{
					if(StringUtils.isNotBlank(signerImpl.getTemplatecodevalue()))
						continue;
				}
				
				boolean isGetUser = signerImpl.getIsgetuser();
				String name = signerImpl.getName();
				String type = signerImpl.getType();
				
				String userId = null;
				if(isGetUser) //getting value from online selection
					userId = ctx.get(name) != null ? ctx.get(name).toString() : null;
				else{
					String approverrole = signerImpl.getApproverrole();
					if(StringUtils.isBlank(approverrole)){
						if(logger.isDebugEnabled(ctx))
							logger.debug(ctx, "No approverrole attribute found");
						
						continue;
					}
					
					if(approverrole.equalsIgnoreCase("roles"))
						userId = ctx.get("user_id") != null ? ctx.get("user_id").toString() : approverrole;
					else{
						approverrole = ctx.get(approverrole) != null ? ctx.get(approverrole).toString() : approverrole;
						
						newCtx.putAll(ctx);
						new DataUtils().getWorkflowAssignedToUserData(newCtx, signerImpl.getObjectid(), signerImpl.getObjecttype(), signerImpl.getObjectfunction(), "user_id", approverrole);
						userId = newCtx.get("user_id") != null ? newCtx.get("user_id").toString() : null; 
					}
				}
				
				if(StringUtils.isBlank(userId)){
					if(logger.isDebugEnabled(ctx))
						logger.debug(ctx, "No userId found");
					
					continue;
				}
				
				//going to get username, email, initials for signer
				newCtx = new Context();
				newCtx.setProject(ctx.getProject());
				newCtx.put("userid", userId);
				newCtx.put("agentEmailTypeForDocusignInContract", ctx.get("agentEmailTypeForDocusignInContract"));
				
				String params = "userid,agentEmailTypeForDocusignInContract";
				
				params = new DataUtils().checkForAdditionalParameters(ctx, newCtx, params, "getDocuSignDataByUserId_p_additionalparameters");
				
				XMLUtils.generateRequestXml(newCtx, params, "inputXml");
				Map dataMap = (Map)SqlResources.getSqlMapProcessor(newCtx).findByKey("framework.getDocuSignDataByUserId_p", newCtx);
				new DataUtils().populateClobValue(dataMap);
	            DataUtils.parseDataXML(newCtx, dataMap);
				
	            String userName = newCtx.get("user_name") != null ? newCtx.get("user_name").toString() : null;
	            String email = newCtx.get("e_mail") != null ? newCtx.get("e_mail").toString() : null;
	            String initials = newCtx.get("initials") != null ? newCtx.get("initials").toString() : null;
				
				//adding signer
				RecipientInfoBean recipientInfo = new RecipientInfoBean();
				List<String> identiferList = new ArrayList<String>();
				
				if(type.equals("agent")){
					identiferList.add(ESignConstants.AGENT_SIGNER_IDENTIFIER);
					identiferList.add(DATE_IDENTIFIER);
					
					identiferListForAnchorTab.add(ESignConstants.AGENT_SIGNER_IDENTIFIER);
					identiferListForAnchorTab.add(DATE_IDENTIFIER);
	            }else if(type.equals("carrier")){
	            	identiferList.add(ESignConstants.CARRIER_SIGNER_IDENTIFIER);
					identiferList.add(ESignConstants.DATE_SIGNER_IDENTFIER_1);	
					
	            	identiferListForAnchorTab.add(ESignConstants.CARRIER_SIGNER_IDENTIFIER);
					identiferListForAnchorTab.add(ESignConstants.DATE_SIGNER_IDENTFIER_1);
	            }
				
				recipientInfo.setRecipientIdentifierList(identiferList);
				
				recipientInfo.setRecipientId(j+1);
				recipientInfo.setEmailAddress(email);
				recipientInfo.setName(userName);
				recipientInfo.setInitials(initials);
					
				recipientInfo.setRoutingOrder(1);
				recipientInfoList.add(recipientInfo);
			}//ended signers loop
		}catch(Exception e){
			
		}
	}
	
	
	private void setContractRecipientsFromSigner(Context ctx, EventImpl eventImpl, List recipientInfoList, List identiferListForAnchorTab, String templateCode) {
		
		Context newCtx = new Context();
		
		try{
		for(int j=0; j<eventImpl.getSortedSignerList().size(); j++){

			SignerImpl signerImpl = (SignerImpl)eventImpl.getSortedSignerList().get(j);
			
			
			String templateCodeValue = signerImpl.getTemplatecodevalue();
			
			if(StringUtils.isBlank(templateCodeValue))
				continue;
			
			
			
			boolean isGetUser = signerImpl.getIsgetuser();
			String name = signerImpl.getName();
			String type = signerImpl.getType();
			
			if(!type.equalsIgnoreCase("carrier"))
				continue;
			
			String carrierId = null;
			if(isGetUser) //getting value from online selection
				carrierId = ctx.get(name) != null ? ctx.get(name).toString() : null;
			else{
				String approverrole = signerImpl.getApproverrole();
				if(StringUtils.isBlank(approverrole)){
					continue;
				}
				
				if(approverrole.equalsIgnoreCase("roles"))
					carrierId = ctx.get("user_id") != null ? ctx.get("user_id").toString() : approverrole;
				else{
					approverrole = ctx.get(approverrole) != null ? ctx.get(approverrole).toString() : approverrole;
					
					newCtx.putAll(ctx);
					new DataUtils().getWorkflowAssignedToUserData(newCtx, signerImpl.getObjectid(), signerImpl.getObjecttype(), signerImpl.getObjectfunction(), "user_id", approverrole);
					carrierId = newCtx.get("user_id") != null ? newCtx.get("user_id").toString() : null; 
				}
			}
			
			if(StringUtils.isBlank(carrierId))
				continue;
			
			
			//going to get username, email, initials for signer
			newCtx = new Context();
			newCtx.setProject(ctx.getProject());
			newCtx.put("userid", carrierId);
			newCtx.put("agentEmailTypeForDocusignInContract", ctx.get("agentEmailTypeForDocusignInContract"));
			
			String params = "userid,agentEmailTypeForDocusignInContract";
			
			params = new DataUtils().checkForAdditionalParameters((Context) ctx, newCtx, params, "getDocuSignDataByUserId_p_additionalparameters");
			
			XMLUtils.generateRequestXml(newCtx, params, "inputXml");
			Map dataMap = (Map)SqlResources.getSqlMapProcessor(newCtx).findByKey("framework.getDocuSignDataByUserId_p", newCtx);
			new DataUtils().populateClobValue(dataMap);
            DataUtils.parseDataXML(newCtx, dataMap);
			
            String userName = newCtx.get("user_name") != null ? newCtx.get("user_name").toString() : null;
            String email = newCtx.get("e_mail") != null ? newCtx.get("e_mail").toString() : null;
            String initials = newCtx.get("initials") != null ? newCtx.get("initials").toString() : null;
			
			//adding signer
			RecipientInfoBean recipientInfo = new RecipientInfoBean();
			List<String> identist = new ArrayList<String>();
			
			if(templateCodeValue.equalsIgnoreCase(templateCode)) {
			identist.add("P1_"+templateCodeValue+"_CARRIER_SIGNER_");
			//identist.add(ESignConstants.DATE_SIGNER_IDENTFIER_1);	
			}
			
			
			
            identiferListForAnchorTab.add("P1_"+templateCodeValue+"_CARRIER_SIGNER_");
			//identiferListForAnchorTab.add(ESignConstants.DATE_SIGNER_IDENTFIER_1);
            
			
			recipientInfo.setRecipientIdentifierList(identist);
			
			recipientInfo.setRecipientId(j+2);
			recipientInfo.setEmailAddress(email);
			recipientInfo.setName(userName);
			recipientInfo.setInitials(initials);
				
			recipientInfo.setRoutingOrder(1);
			recipientInfoList.add(recipientInfo);
			
			
			
		}//end for loop
		
         }catch(Exception e){
			
		}
		
		
	}

	public void setDataForDocuESignDirectForPublicSubmission(IContext ctx) throws Exception {
		
		logger.debug("Going to set data for DocuSign in method setDataForDocuESignDirectForPublicSubmission");
		
		List generatedPublicSubmissionAttachmentsList = null;
		List agentList = null;
		
		String agentEmail = null;
		String personName = null;
		String personId = null;
		String agentInitials = null;
		
		String adminMailURL = null;
		String adminName = null;
		String adminInitials = null;
		
		
		String identifier = null;
		String userId = null;
		 
		byte[] rb = null;
		String fileName = null;
		String personFileName=null;
		
		
		String P_AGENT_SIGNER_IDENTIFIER= ESignConstants.AGENT_SIGNER_IDENTIFIER;
		String P_DATE_IDENTIFIER= DATE_IDENTIFIER;
		String DOC_IDENTIFIER= null;
		
		logger.debug("Going to get aar_requestid.");
		String aar_requestid = ctx.get("aar_requestid") != null ? ctx.get("aar_requestid").toString() : HtmlConstants.EMPTY; 
		
		if(aar_requestid != null && !HtmlConstants.EMPTY.equals(aar_requestid)){ 
		logger.debug("aar_requestid found."+aar_requestid);
		
		try{
			logger.debug("Going to get Agent Info and mail ID");
			DocuSignUtil docuSign = new DocuSignUtil();
			List<DocuSignRequest> docuSignRequestList = new ArrayList<DocuSignRequest>();
			
			new SetParametersForStoredProcedures().setParametersInContext(ctx, "aar_requestid,AgentAddresType");
			agentList=	SqlResources.getSqlMapProcessor(ctx).select("agencyListPublicSubmitNInvitation.GetAgencyPersonInfoForPublicSubmission_p",ctx);
			generatedPublicSubmissionAttachmentsList = SqlResources.getSqlMapProcessor(ctx).select("agency_document_attachments.GetPublicSubmissionAttachmentsDataForDocusign_p", ctx);
			
			if(agentList != null && agentList.size() > 0){
				for(int k= 0; k<agentList.size(); k++){
				
					
					Map agentMap = (Map) agentList.get(k);
					
					personFileName=agentMap.get("person_name").toString();
					
		logger.debug("Going to get Public Submission attachments List");
		
		if(generatedPublicSubmissionAttachmentsList != null && generatedPublicSubmissionAttachmentsList.size() > 0){
			for(int i= 0; i<generatedPublicSubmissionAttachmentsList.size(); i++){
				rb =  null;
				fileName =  null;
				
				Map generateddPublicSubmissionAttachmentsMap = (Map) generatedPublicSubmissionAttachmentsList.get(i);
				if(generateddPublicSubmissionAttachmentsMap != null && !HtmlConstants.EMPTY.equals(generateddPublicSubmissionAttachmentsMap)){
					if(generateddPublicSubmissionAttachmentsMap.get("document_name") != null && !HtmlConstants.EMPTY.equals(generateddPublicSubmissionAttachmentsMap.get("document_name"))){
						fileName = generateddPublicSubmissionAttachmentsMap.get("document_name").toString();
						
						
						
						
						if(fileName != null && !HtmlConstants.EMPTY.equals(fileName)){
							
							if((k == 0 && fileName.contains("applicationESign")) ) {
								agentEmail = agentMap.get("e_mail") != null ? agentMap.get("e_mail").toString() : HtmlConstants.EMPTY;
								personName = agentMap.get("printName") != null ? agentMap.get("printName").toString() : HtmlConstants.EMPTY;
								personId = agentMap.get("person_id") != null ? agentMap.get("person_id").toString() : HtmlConstants.EMPTY;
								agentInitials = agentMap.get("personInitial") != null ? agentMap.get("personInitial").toString() : HtmlConstants.EMPTY;
								
								P_AGENT_SIGNER_IDENTIFIER=AGENT_SIGNER_IDENTIFIER+""+1;
								P_DATE_IDENTIFIER= DATE_IDENTIFIER+"_"+1;
								DOC_IDENTIFIER= ESignConstants.DOCUMENT_IDENTIFIER_APPFORM;
								
							}
							else if(fileName.contains(personFileName)) {
								agentEmail = agentMap.get("e_mail") != null ? agentMap.get("e_mail").toString() : HtmlConstants.EMPTY;
								personName = agentMap.get("printName") != null ? agentMap.get("printName").toString() : HtmlConstants.EMPTY;
								personId = agentMap.get("person_id") != null ? agentMap.get("person_id").toString() : HtmlConstants.EMPTY;
								agentInitials = agentMap.get("personInitial") != null ? agentMap.get("personInitial").toString() : HtmlConstants.EMPTY;
								ctx.put("person_id", personId);
								
								P_AGENT_SIGNER_IDENTIFIER=AGENT_SIGNER_IDENTIFIER+""+(k+2);
								P_DATE_IDENTIFIER= DATE_IDENTIFIER+"_"+(k+2);
								DOC_IDENTIFIER= ESignConstants.DOCUMENT_IDENTIFIER_APPBG;
							}
							else {
								continue;
							}
							
							
							
							
							
							
							ctx.put("file_name", fileName);
							
							ctx.put("document_id", generateddPublicSubmissionAttachmentsMap.get("document_id").toString());
							
							
							logger.debug("Going to download file from DMS: " + fileName);
							
							
							
							
							rb = downloadContractFromDMS((Context)ctx);
							
							
							
							logger.debug("file downloaded from DMS : " + fileName);
						}
					}
					
					
					
					DocuSignRequest docuSignRequest = new DocuSignRequest();
					docuSignRequest.setContent(rb); 
					docuSignRequest.setDocumentId(i+1);
					docuSignRequest.setIdentifier(DOC_IDENTIFIER);
					
					List<RecipientInfoBean> recipientInfoList = new ArrayList<RecipientInfoBean>();
					List<String> identiferList = new ArrayList<String>();
					List<String> identiferListForAnchorTab = new ArrayList<String>();
					
					
					RecipientInfoBean recipientAgentInfo = new RecipientInfoBean();
					
					identiferList.add(P_AGENT_SIGNER_IDENTIFIER);
					identiferList.add(P_DATE_IDENTIFIER);
					identiferListForAnchorTab.addAll(identiferList);
					
					
					recipientAgentInfo.setRecipientIdentifierList(identiferList);
					recipientAgentInfo.setRecipientId(i+1);
					recipientAgentInfo.setEmailAddress(agentEmail);
					recipientAgentInfo.setRoutingOrder(1);
					recipientAgentInfo.setName(personName);
					recipientAgentInfo.setInitials(agentInitials);
					recipientAgentInfo.setRecipientDoc(docuSignRequest.getIdentifier());
					
					recipientInfoList.add(recipientAgentInfo);
					
					docuSignRequest.setRecipientInfoList(recipientInfoList);
					
					docuSignRequest.setDocAnchorInfo(identiferListForAnchorTab);
					docuSignRequestList.add(docuSignRequest);
				}
				
			
				
				
				
			}//ended for loop	
			
		}
				}
		}
					
		EnvelopeStatus docRes = null; 
		String errorMsg = "";
		try{
			logger.debug("Going to hit DocuSign");
			docRes = docuSign.sendDocument(docuSignRequestList);
			logger.debug("Response got from DocuSign " + docRes);
		}catch (Throwable e1) {
			logger.error("Unable to execute setDataForDocuESignDirect method due to error : " + DataUtils.getExceptionStackTrace(e1));
		}
		
		if(docRes == null || docRes.getStatus().equals(HtmlConstants.EMPTY) || 
				("ERROR".equalsIgnoreCase(docRes.getStatus().toString()) || "FAILURE".equalsIgnoreCase(docRes.getStatus().toString()))){
			
			DataUtils.populateError((Context)ctx, "bgPageError", "Unable to send document for ESignature");
			logger.error("Unable to send document for ESignature due to error : "+ errorMsg);
			return;
		}
		
		//going to insert int database
		Context newCtx = new Context();
		newCtx.setProject(ctx.getProject());
		
		newCtx.put("document_id_var", docRes.getEnvelopeID());
		newCtx.put("document_type", "BGAuthorization");
		newCtx.put("last_updated_by", ctx.get("last_updated_by").toString());
		newCtx.put("operationType", "I");
		newCtx.put("aar_requestid",  ctx.get("aar_requestid").toString());
		if(docRes.getStatus() != null && "SENT".equalsIgnoreCase(docRes.getStatus().toString())){
			newCtx.put("status_comment", "SENT");
		}
		
		Map requestIdMap = (Map)SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.sqlStatementsviewgetRequestIdFromAgencyMaster", newCtx);
		if(requestIdMap != null && !HtmlConstants.EMPTY.equals(requestIdMap)){
			if(requestIdMap.get("requestid") != null && !HtmlConstants.EMPTY.equals(requestIdMap.get("requestid").toString()))
				newCtx.put("aar_requestid", requestIdMap.get("requestid"));
			else
				newCtx.put("aar_requestid", "-1");
		}
		if(docRes.getStatus() != null && "SENT".equalsIgnoreCase(docRes.getStatus().toString())){
			newCtx.put("status_comment", "SENT");
		}
		newCtx.put("onboarding_method", ctx.get("onboarding_method"));
		//going to insert in database
		new SetParametersForStoredProcedures().setParametersInContext(newCtx, "status,agency_id,person_id,aar_requestid,onboarding_method");
		SqlResources.getSqlMapProcessor(ctx).insert("agency_contracts.insert_update_esign_status_p_java", newCtx);
		
		logger.debug("Pdf Doc has sent to agency for esign.");
		
	}catch (Exception e) {
		DataUtils.populateError((Context)ctx, "bgPageError","Error in Sending document for ESign");
		logger.error("Unable to send document for ESignature due to error : " + e.getMessage());
	}
}
}
	
	@Override
	public String getMetaobject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPopulate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getViewid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMetaobject(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPopulate(String arg0) {
		// TODO Auto-generated method stub
		
	}	
}