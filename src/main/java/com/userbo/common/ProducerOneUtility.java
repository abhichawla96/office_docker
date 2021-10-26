package com.userbo.common;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.fop.servlet.ServletContextURIResolver;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellUtil;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import com.esign.docusgindirect.valueObjects.DocuSignRequest;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.RadioCheckField;
import com.itextpdf.text.pdf.TextField;
import com.manage.managebusinessrules.rules.RuleImpl;
import com.manage.managebusinessrules.rules.RulesResources;
import com.manage.managecomponent.components.EmailImpl;
import com.manage.managecomponent.components.SetParametersForStoredProcedures;
import com.manage.managecomponent.components.WorkflowImpl;
import com.manage.managecomponent.fields.FieldImpl;
import com.manage.managecomponent.fields.FieldsResources;
import com.manage.managecomponent.fields.WebservicecallImpl;
import com.manage.managemetadata.functions.commonfunctions.DataUtils;
import com.manage.managemetadata.messages.MessageImpl;
import com.manage.managemetadata.messages.MessageResources;
import com.manage.managemetadata.metadata.MetaDataResources;
import com.manage.managemetadata.metadata.PropertyImpl;
import com.manage.managemetadata.security.SecurityResources;
import com.manage.parser.TemplateBuilderUtils;
import com.manage.util.Html2PdfGenerator;
import com.manage.util.XML2PDF;
import com.manage.util.XMLUtils;
import com.mercuryinsurance.p3.ws.RoutingNumberValidation;
import com.mercuryinsurance.p3.ws.schema.responsevalidateecheckpayment.ResponseValidateEcheckPayment;
import com.mercuryinsurance.p3.ws.schema.validateecheckpayment.ValidateEcheckPayment;
import com.ormapping.ibatis.SqlResources;
import com.osi.dms.valueobject.DocumentInfo;
import com.osi.dms.ws.DocumentManagementService;
import com.osi.integration.client.IntegrationClient;
import com.osi.nipr.ApplicantER;
import com.osi.nipr.ApplicantFR;
import com.osi.nipr.ApplicantLicenseFR;
import com.osi.nipr.ArrayOfApplicantLicenseFR;
import com.osi.nipr.ArrayOfLicenseFR;
import com.osi.nipr.ArrayOfLicenseLoaFR;
import com.osi.nipr.ArrayOfNonResidentLicenseER;
import com.osi.nipr.EligibilityRequest;
import com.osi.nipr.EligibilityResponse;
import com.osi.nipr.FeeRequest;
import com.osi.nipr.FeeResponse;
import com.osi.nipr.LicenseFR;
import com.osi.nipr.LicenseLoaFR;
import com.osi.nipr.NonResidentLicenseER;
import com.osi.nipr.ResidentLicenseER;
import com.osi.nipr.TransEntitiesStatus;
import com.osi.nipr.TransactionEntityStatus;
import com.osi.nipr.TransactionInfo;
import com.processor.TabsConfiguration;
import com.userbo.TemplateBuilderBO;
import com.userbo.compliance.LicensingConstant;
import com.userbo.compliance.NIPRONEService;
import com.userbo.integration.SendDocForESign;
import com.userbo.onboarding.SendBGCheckRequest;
import com.util.CacheManager;
import com.util.Constants;
import com.util.Context;
import com.util.DateUtils;
import com.util.HtmlConstants;
import com.util.IContext;
import com.util.IOUtils;
import com.util.InetLogger;
import com.util.ResourceLoader;
import com.util.StringUtils;
import com.util.SystemProperties;
import com.util.TripleDESEncryptionDecryption;

/**
 * @author Vikas Kundra
 * @description Class created to make utility methods
 * @updation Vikas Kundra created on Aug 19th 2015 
 * @updation Mohit Anand added new methods
 * @updation Deepak Sharma added new methods
 * @updation Mohit Anand added new method convertMomListFieldsIntoXml on Jan 15th 2016
 */
public class ProducerOneUtility {
	private static InetLogger logger = InetLogger.getInetLogger("ProducerOneUtility");
	private static StringBuilder json = null;

	/**
	 * @author Vikas Kundra
	 * @description Method created to generate addressXml for Add New Agency page address fields
	 * @updation Vikas Kundra created on Aug 19th 2015
	 */
	public static void generateOutputXML(Context ctx, String fieldsList, String outputXMLName) throws Exception{
		if(fieldsList== null)
			return;

    	Element rootElement = new Element("root");

    	StringTokenizer tokens = new StringTokenizer(fieldsList, ",");
    	while(tokens.hasMoreTokens()){
    		String token = tokens.nextToken();

    		Element ele = new Element(token);
    		ele.setText(ctx.get(token) != null ? ctx.get(token).toString() : HtmlConstants.EMPTY);
    		rootElement.addContent(ele.detach());
    	}

    	ctx.put(outputXMLName, new XMLOutputter(Format.getPrettyFormat()).outputString(rootElement));
    }

	/**
	 * @author Vikas Kundra
	 * @description Method created to reload messages xml and update in Cache
	 * @updation Vikas Kundra created on Jan 31st 2016
	 */
    public static Object reloadMessagesConfigurationXML(Context ctx) throws Exception{
        try{
        	if(logger.isDebugEnabled(ctx))
        		logger.debug(ctx, "Going to reload messages XML");
            String rulesPath = SystemProperties.getInstance().getString("xml.basedir") + ctx.getProject() + "//messages.xml";
            ResourceLoader.load(null, rulesPath, "messages", ctx.getProject());
            if(logger.isDebugEnabled(ctx))
            	logger.debug(ctx, "Messages XML reloaded successfully");
        }catch (Exception e) {
            logger.error("Unable to reload messages xml due to error : " + e.getMessage());
        }

        return null;
    }

    /**
	 * @author Vikas Kundra
	 * @description Method created to reload reports xml and update in Cache
	 * @updation Vikas Kundra created on Jan 31st 2016
	 */
    public static Object reloadReportsConfigurationXML(Context ctx) throws Exception{
        try{
        	if(logger.isDebugEnabled(ctx))
        		logger.debug(ctx, "Going to reload reports XML");
            String reportsPath = SystemProperties.getInstance().getString("xml.basedir") + ctx.getProject() + "//reports.xml";
            ResourceLoader.load(null, reportsPath, "reports", ctx.getProject());
            if(logger.isDebugEnabled(ctx))
            	logger.debug(ctx, "ProducerOne reports XML reloaded successfully");

            Context localCtx = new Context();
            localCtx.setProject("ProducerOnePerformance");
            if(logger.isDebugEnabled(ctx))
            	logger.debug(ctx, "Going to reload reports XML");
            String reportsPerfPath = SystemProperties.getInstance().getString("xml.basedir") + ctx.getProject() + "//reports.xml";
            ResourceLoader.load(null, reportsPerfPath, "reports", localCtx.getProject());
            if(logger.isDebugEnabled(ctx))
            	logger.debug(ctx, "ProducerOnePerformance reports XML reloaded successfully");

        }catch (Exception e) {
            logger.error("Unable to reload reports xml due to error : " + e.getMessage());
        }

        return null;
    }

    /**
	 * @author Vikas Kundra
	 * @description Method created to reload components xml and update in Cache
	 * @updation Vikas Kundra created on Jan 31st 2016
	 */
    public static Object reloadComponentsConfigurationXML(Context ctx) throws Exception{
        try{
        	if(logger.isDebugEnabled(ctx))
        		logger.debug(ctx, "Going to reload components XML");
            String rulesPath = SystemProperties.getInstance().getString("xml.basedir") + ctx.getProject() + "//components//components.xml";
            ResourceLoader.load(null, rulesPath, "components", ctx.getProject());
            if(logger.isDebugEnabled(ctx))
            	logger.debug(ctx, "Components XML reloaded successfully");
        }catch (Exception e) {
            logger.error("Unable to reload components xml due to error : " + e.getMessage());
        }

        return null;
    }

    /**
	 * @author Vikas Kundra
	 * @description Method created to reload metadata xml and update in Cache
	 * @updation Vikas Kundra created on Feb 3rd 2016
	 */
    public static Object reloadMetadataConfigurationXML(Context ctx) throws Exception{
        try{
        	if(logger.isDebugEnabled(ctx))
        		logger.debug(ctx, "Going to reload metadata XML");
            String rulesPath = SystemProperties.getInstance().getString("xml.basedir") + ctx.getProject() + "//metadata//metadata.xml";
            ResourceLoader.load(null, rulesPath, "metadata", ctx.getProject());
            if(logger.isDebugEnabled(ctx))
            	logger.debug(ctx, "Metadata XML reloaded successfully");
        }catch (Exception e) {
            logger.error("Unable to reload metadata xml due to error : " + e.getMessage());
        }

        return null;
    }

    /**
	 * @author Vikas Kundra
	 * @description Method created to reload functions xml and update in Cache
	 * @updation Vikas Kundra created on Feb 3rd 2016
	 */
    public static Object reloadFunctionsConfigurationXML(Context ctx) throws Exception{
        try{
        	if(logger.isDebugEnabled(ctx))
        		logger.debug(ctx, "Going to reload functions XML");
            String rulesPath = SystemProperties.getInstance().getString("xml.basedir") + ctx.getProject() + "//functions.xml";
            ResourceLoader.load(null, rulesPath, "functions", ctx.getProject());
            if(logger.isDebugEnabled(ctx))
            	logger.debug(ctx, "Functions XML reloaded successfully");
        }catch (Exception e) {
            logger.error("Unable to reload functions xml due to error : " + e.getMessage());
        }

        return null;
    }

    /**
	 * @author Mohit Anand
	 * @description Method created to generate security roles xml
	 * @updation Mohit Anand created on Feb 13th 2016
	 * @updation Vikas Kundra added code on Jun 23rd 2016
	 */
    public static void generateSecurityRolesXML(Context ctx, String outputXMLName) throws Exception{
    	try {
    		String listName = null;

    		if(ctx.get(Constants.INET_PAGE).toString().equals("addMasterLookupObject")){
    			listName = "addMasterLookupObject_list_listfreeform_1";

    			ProducerOneUtils.convertListDataToXML(ctx, listName, outputXMLName);
    			return;
			}

    		if(ctx.get(Constants.INET_PAGE).toString().equals("addObjectHierarchyMapping")){
    			listName = "addObjectHierarchyMapping_list_listfreeform_1";

    			ProducerOneUtils.convertListDataToXML(ctx, listName, outputXMLName);
    			return;
			}

    		if(ctx.get(Constants.INET_PAGE).toString().equals("addManageScreens")){
    			listName = "addManageScreens_list_listfreeform_1";

    			ProducerOneUtils.convertListDataToXML(ctx, listName, outputXMLName);
    			return;
			}

    		if(ctx.get(Constants.INET_PAGE).toString().equals("addManageScreensRule")){
    			listName = "addManageScreensRule_list_listfreeform_1";

    			ProducerOneUtils.convertListDataToXML(ctx, listName, outputXMLName);
    			return;
			}

    		if(ctx.get(Constants.INET_PAGE).toString().equals("addManageUsers")){
    			listName = "addManageUsers_list_listfreeform_1";

    			ProducerOneUtils.convertListDataToXML(ctx, listName, outputXMLName);
    			return;
			}

    		if(ctx.get(Constants.INET_PAGE).toString().equals("addManageScreensEventsInner")){
    			listName = "addManageScreensEventsInner_list_listfreeform_1";

    			ProducerOneUtils.convertListDataToXML(ctx, listName, outputXMLName);
    			return;
			}

    		if(ctx.get(Constants.INET_PAGE).toString().equals("massHierarchyStep2")){
    			listName = "massHierarchyStep2_list_mom_1";

    			ProducerOneUtils.convertListDataToXML(ctx, listName, outputXMLName);
    			return;
			}

    		if((ctx.get(Constants.INET_PAGE).toString().equals("bobtransfersStep2") || ctx.get(Constants.INET_PAGE).toString().equals("bobtransfersStep3"))&&
    				ctx.get(HtmlConstants.INET_METHOD).toString().equals("sendforreview")){
    			if(ctx.get("transfer_type") != null && ctx.get("transfer_type").toString().equals("WTR")){
    				List finalList = new ArrayList();

    				listName = "transfersStep3_final_list";

    				Map map = new HashMap();
    				map.put("numberofpolicies", ctx.get("numberofpolicies"));

    				finalList.add(map);
	    			ctx.put(listName, finalList);

	    			ProducerOneUtils.convertListDataToXML(ctx, listName, "xml");

    				listName = "transfersStep2_list_mom_2";

	    			ProducerOneUtils.convertListDataToXML(ctx, listName, "filterXml");
    				return;
    			}

    			if(ctx.get("transfersStep3_list_mom_2") != null && ctx.get("transfersStep3_list_mom_2") instanceof List){
    				List list = (List)ctx.get("transfersStep3_list_mom_2");
    				List finalList = new ArrayList();

    				String validations = ctx.get("validations").toString();

    				for(int i=0; i<list.size(); i++){
    					Map map = (Map)list.get(i);

    					if(map.get("Policy_Number") != null && (","+validations+",").contains(","+map.get("Policy_Number").toString()+",")){
    						map.put("is_checked", "Y");
    					}

    					finalList.add(map);
    				}

    				ctx.put("transfersStep3_final_list", finalList);
    			}

    			listName = "transfersStep3_final_list";

    			ProducerOneUtils.convertListDataToXML(ctx, listName, outputXMLName);

    			listName = "transfersStep3_filters_list";
    			List filtersList = new ArrayList();
    			Map map = new HashMap();
    			map.put("family_number", ctx.get("family_number"));
    			map.put("first_name", ctx.get("first_name"));
    			map.put("policy_number", ctx.get("policy_number"));
    			map.put("agent_number", ctx.get("agent_number"));
    			/*map.put("", ctx.get(""));
    			map.put("", ctx.get(""));
    			map.put("", ctx.get(""));*/

    			filtersList.add(map);
    			ctx.put(listName, filtersList);

    			ProducerOneUtils.convertListDataToXML(ctx, listName, "filterXml");
    			return;
			}

    		if(ctx.get(Constants.INET_PAGE).toString().equals("bobtransfersStep2")){
    			if(ctx.get(HtmlConstants.INET_METHOD) != null && ctx.get(HtmlConstants.INET_METHOD).toString().equals("addWholesaleTransferFilters")){
	    			if(ctx.get("transfersStep2_list_mom_2") != null && ctx.get("transfersStep2_list_mom_2") instanceof List){
	    				List oldList = (List)ctx.get("transfersStep2_list_mom_2");
	    				for(int i=0; i<oldList.size(); i++){
	    					Map map = (Map)oldList.get(i);

	    					map.put("bob_id", map.get("bob_id") == null ? "" : map.get("bob_id"));
	    					map.put("state_abbreviation", map.get("state_abbreviation") == null ? "" : map.get("state_abbreviation"));
	    					map.put("company_abbreviation", map.get("company_abbreviation") == null ? "" : map.get("company_abbreviation"));
	    					map.put("transfer_option", map.get("transfer_option") == null ? "" : map.get("transfer_option"));
	    					map.put("transfer_option_from", map.get("transfer_option_from") == null ? "" : map.get("transfer_option_from"));
	    					map.put("transfer_option_to", map.get("transfer_option_to") == null ? "" : map.get("transfer_option_to"));
	    					map.put("transfer_option1", map.get("transfer_option") == null ? "" : map.get("transfer_option"));
	    					map.put("lob_code", map.get("lob_code") == null ? "" : map.get("lob_code"));

	    					if(ctx.get("transfer_option") != null && ctx.get("transfer_option").toString().equals("zipcode")){
	    						ctx.put("transfer_option_from", ctx.get("transfer_option_from_zipcode"));
	    						map.put("transfer_option1", ctx.get("transfer_option"));
	    					}
	    					if(ctx.get("transfer_option") != null && ctx.get("transfer_option").toString().equals("alphasplit")){
	    						map.put("transfer_option1", ctx.get("transfer_option"));
	    					}

	    					if(map.get("bob_id").equals(ctx.get("bob_id")) &&
    							map.get("state_abbreviation").equals(ctx.get("state_abbreviation")) &&
    							map.get("company_abbreviation").equals(ctx.get("company_abbreviation")) &&
    							map.get("transfer_option1").equals(ctx.get("transfer_option")) &&
    							map.get("transfer_option_from").equals(ctx.get("transfer_option_from")) &&
    							map.get("lob_code").equals(ctx.get("lob_code")) &&
    							map.get("transfer_option_to").equals(ctx.get("transfer_option_to"))){
	    						DataUtils.populateError(ctx, "pageError", "policyTransferFilterDuplicateErrorKey");
	    						return;
	    					}
	    				}
	    			}

    				List filtersList = new ArrayList();

	    			Map map = new HashMap();
	    			map.put("agent_number", ctx.get("producer_number_from_varchar"));
	    			map.put("bob_id", ctx.get("bob_id"));
	    			map.put("state_abbreviation", ctx.get("state_abbreviation"));
	    			map.put("company_abbreviation", ctx.get("company_abbreviation"));
	    			map.put("transfer_option", ctx.get("transfer_option"));
	    			map.put("transfer_option_from", ctx.get("transfer_option_from"));
	    			map.put("lob_code", ctx.get("lob_code"));

	    			if(ctx.get("transfer_option") != null && ctx.get("transfer_option").toString().equals("zipcode"))
	    				map.put("transfer_option_from", ctx.get("transfer_option_from_zipcode"));

	    			map.put("transfer_option_to", ctx.get("transfer_option_to"));

	    			filtersList.add(map);

	    			listName = "transfersStep3_filters_list";
	    			ctx.put(listName, filtersList);

	    			ctx.put("validationsId", ctx.get("validations"));

	    			ProducerOneUtils.convertListDataToXML(ctx, listName, "filterXml");
	    			return;
    			}

    			/*if(ctx.get(HtmlConstants.INET_METHOD) != null && ctx.get(HtmlConstants.INET_METHOD).toString().equals("sendforapproval")){
    				List finalList = new ArrayList();

    				listName = "transfersStep3_final_list";

    				Map map = new HashMap();
    				map.put("numberofpolicies", ctx.get("numberofpolicies"));

    				finalList.add(map);
	    			ctx.put(listName, finalList);

	    			ProducerOneUtils.convertListDataToXML(ctx, listName, "xml");

    				listName = "transfersStep2_list_mom_2";

	    			ProducerOneUtils.convertListDataToXML(ctx, listName, "filterXml");
    				return;
    			}*/

    			if(ctx.get("transfer_type") != null && ctx.get("transfer_type").toString().equals("WTR")){
	    			List filtersList = new ArrayList();

	    			Map map = new HashMap();
	    			map.put("state_abbreviation", ctx.get("state_abbreviation"));
	    			map.put("company_abbreviation", ctx.get("company_abbreviation"));
	    			map.put("transfer_option", ctx.get("transfer_option"));
	    			map.put("transfer_option_value", ctx.get("transfer_option_value"));
	    			map.put("transfer_option_from", ctx.get("transfer_option_from"));
	    			map.put("transfer_option_to", ctx.get("transfer_option_to"));
	    			map.put("lob_code", ctx.get("lob_code"));

	    			filtersList.add(map);

	    			listName = "transfersStep3_filters_list";
	    			ctx.put(listName, filtersList);

	    			ProducerOneUtils.convertListDataToXML(ctx, listName, "filterXml");

	    			if(ctx.get("row_num") != null && ctx.get("transfersStep2_list_mom_2") != null && ctx.get("transfersStep2_list_mom_2") instanceof List){
	    				List list = (List)ctx.get("transfersStep2_list_mom_2");

	    				for(int i=0; i<list.size(); i++){
	    					map = (Map)list.get(i);

	    					if(map.get("row_num") != null && !map.get("row_num").toString().equals(HtmlConstants.EMPTY) &&
	    							ctx.get("row_num") != null && !ctx.get("row_num").toString().equals(HtmlConstants.EMPTY) &&
	    							Integer.parseInt(ctx.get("row_num").toString()) == Integer.parseInt(map.get("row_num").toString())){
	    						ctx.put("validations", map.get("validations"));
	    						break;
	    					}
	    				}
	    			}

	    			return;
    			}
			}

    		if(ctx.get(Constants.INET_PAGE).toString().equals("addObjectHierarchyMapping")){
    			listName = "addObjectHierarchyMapping_list_listfreeform_1";

    			ProducerOneUtils.convertListDataToXML(ctx, listName, outputXMLName);
    			return;
			}

    		if(ctx.get("updatemanagerule") == null || HtmlConstants.EMPTY.equals(ctx.get("updatemanagerule"))){
    			listName = "roles_list";

    			new SetParametersForStoredProcedures().setParametersInContext(ctx, "actualhtml_id,actualhtml_fields_mapping_id,client_id,releaseno_id,releaseversionno_id,clientclient_id,clientreleaseno_id,clientreleaseversionno_id");
    			List rolesList = SqlResources.getSqlMapProcessor(ctx).select("manageScreens.getConfigurationRolesList_p",ctx);
    			if(rolesList != null && rolesList.size() >0)
    				ctx.put(listName, rolesList);
			}else if(ctx.get("updatemanagerule") != null && !HtmlConstants.EMPTY.equals(ctx.get("updatemanagerule")) && "Y".equals(ctx.get("updatemanagerule"))){
    			listName = "rules_list";

    			new SetParametersForStoredProcedures().setParametersInContext(ctx, "actualhtml_id,actualhtml_fields_mapping_id,client_id,releaseno_id,releaseversionno_id,clientclient_id,clientreleaseno_id,clientreleaseversionno_id");
    			List rulesList = SqlResources.getSqlMapProcessor(ctx).select("manageScreens.getConfigurationRuleData_p",ctx);
    			if(rulesList != null && rulesList.size() >0)
    				ctx.put(listName, rulesList);
    		}/*else if(ctx.get("updatemanagerule") != null && !HtmlConstants.EMPTY.equals(ctx.get("updatemanagerule")) && "U".equals(ctx.get("updatemanagerule"))){
    			listName = "user_list";

    			new SetParametersForStoredProcedures().setParametersInContext(ctx, "actualhtml_id,actualhtml_fields_mapping_id,client_id,releaseno_id,releaseversionno_id,clientclient_id,clientreleaseno_id,clientreleaseversionno_id");
    			List userList = SqlResources.getSqlMapProcessor(ctx).select("manageScreens.getConfigurationUsersList_p",ctx);
    			if(userList != null && userList.size() >0)
    				ctx.put(listName, userList);
    		}*//*else{
    			listName = "roles_list";

    			List rolesList = SqlResources.getSqlMapProcessor(ctx).select("manageScreens.getManageRolesData_p",ctx);
    			if(rolesList != null && rolesList.size() >0)
    				ctx.put(listName, rolesList);
    		}*/

    		ProducerOneUtils.convertListDataToXML(ctx, listName, outputXMLName);
		} catch (Exception e) {
			logger.error("Unable to generate Security Roles  xml due to error : " + e.getMessage());
		}
    }

    /**
	 * @author Mohit Anand
	 * @description Method created to update mom list data to context map
	 * @updation Mohit Anand created on Aug 23rd 2016
	 */
    public static Object updateMomListDataFromContextToMap(Context ctx,String listNamesString) throws Exception{
        try{
        	String keysArray[] = ctx.get("MOMLISTKEYS").toString().split(",");
        	 if(listNamesString != null && !HtmlConstants.EMPTY.equals(listNamesString)){
                StringTokenizer tokens = new StringTokenizer(listNamesString, "|");

                while(tokens.hasMoreTokens()){
                 	String objectName = tokens.nextToken();
                 	if(objectName == null || HtmlConstants.EMPTY.equals(objectName)
                 			|| ctx.get(objectName) == null || HtmlConstants.EMPTY.equals(ctx.get(objectName).toString())){
                 		continue;
                 	}
                 	if(ctx.get(objectName) instanceof List){
                 		List dataList = ctx.get(objectName) != null ? (List)ctx.get(objectName) : null;
                         if(dataList != null && dataList.size() > 0){
                             for(int i =0; i<dataList.size(); i++){
                                 Map map = (Map)dataList.get(i);
                                 if(map != null){
                                     Set set = map.keySet();
                                     Iterator<String> itr = set.iterator();

                                     while(itr.hasNext()){
                                         String key = itr.next();
                                         org.jdom.Element rowElement = new org.jdom.Element(key);

                                         if(keysArray!=null){
	                                         for(int j=0;j<keysArray.length;j++){
	                                        	 if(key.equals(keysArray[j].toString())){
	                                        		 if(ctx.get(key+"_"+i)==null){
	                                        			 ctx.put(key+"_"+i,"");
	                                        		 }

	                                        		 map.put(key, ctx.get(key+"_"+i).toString());
	                                        		 }
	                                        	 }
                                         	}


                                         if(ctx.get(key+"_"+i) != null){
                                             if(!HtmlConstants.EMPTY.equals(ctx.get(key+"_"+i).toString())){
                                                 map.put(key, ctx.get(key+"_"+i).toString());
                                             }
                                         }
                                     }
                                 }
                             }

                             ctx.put(objectName, dataList);

                         }
                 	}
                 }

             }


        }catch (Exception e) {
            logger.error("Unable to update MomListData  From Context To Map due to error : " + e.getMessage());
        }

        return null;
    }

    /**
	 * @author Vikas kundra
	 * @description Method created to generate user logs
	 * @updation Vikas kundra created on Aug 30th 2016
	 */

    /*//Method created to download debug file
    public static void downloadDebugXML(Context ctx) throws Exception{
    	try{
    		XSSFWorkbook book = new XSSFWorkbook();
    		XSSFSheet sheet = book.createSheet("Logs");

    		XSSFRow row = sheet.createRow(0);
    		XSSFCell cell = row.createCell(0);
    		cell.setCellValue("User Id");
    		sheet.setColumnWidth(0, 5000);

    		cell = row.createCell(1);
    		cell.setCellValue("Log Message");
    		sheet.setColumnWidth(1, 50000);

    		cell = row.createCell(2);
    		cell.setCellValue("TimeStamp");
    		sheet.setColumnWidth(2, 5000);

    		if(ctx.get("userlogs_list_1") != null && ctx.get("userlogs_list_1") instanceof List){
    			List list = (List)ctx.get("userlogs_list_1");

    			List finalList = new ArrayList();

    			for(int i=0; i<list.size(); i++){
    				Map map = (Map)list.get(i);

    				if(map.get("logs") != null && !map.get("logs").toString().equals(HtmlConstants.EMPTY)){
    					String logs = map.get("logs").toString();

    					StringTokenizer tokens = new StringTokenizer(logs, "\n");
    					while(tokens.hasMoreTokens()){
    						String token = tokens.nextToken();

    						Map newMap = new HashMap();

    						newMap.put("user_id", map.get("user_id") != null ? map.get("user_id").toString() : HtmlConstants.EMPTY);
    						newMap.put("last_updated_ts", map.get("last_updated_ts") != null ? map.get("last_updated_ts").toString() : HtmlConstants.EMPTY);
    						newMap.put("logs", token);

    						finalList.add(newMap);
    					}
    				}
    			}

    			if(finalList.size() == 0)
    				finalList = list;

    			for(int i=0; i<finalList.size(); i++){
    				Map map = (Map)finalList.get(i);

    				row = sheet.createRow(i+1);

    				cell = row.createCell(0);
    				cell.setCellValue(map.get("user_id") != null ? map.get("user_id").toString() : HtmlConstants.EMPTY);

    	    		cell = row.createCell(1);
    	    		cell.setCellValue(map.get("logs") != null ? map.get("logs").toString() : HtmlConstants.EMPTY);

    	    		cell = row.createCell(2);
    	    		cell.setCellValue(map.get("last_updated_ts") != null ? map.get("last_updated_ts").toString() : HtmlConstants.EMPTY);
    			}
    		}

    		ByteArrayOutputStream bout = new ByteArrayOutputStream();
    		book.write(bout);

    		String finalLogs = HtmlConstants.EMPTY;

    		if(ctx.get("userlogs_list_1") != null && ctx.get("userlogs_list_1") instanceof List){
    			List list = (List)ctx.get("userlogs_list_1");

    			for(int i=0; i<list.size(); i++){
    				Map map = (Map)list.get(i);

    				if(map.get("logs") != null && !map.get("logs").toString().equals(HtmlConstants.EMPTY)){
    					String logs = map.get("logs").toString();

    					finalLogs = finalLogs + logs;
    				}
    			}
    		}

    		HttpServletResponse resp = (HttpServletResponse)ctx.get("DocumentResponse");
    		//resp.setContentType("application/vnd.ms-excel");
			byte[] rb = finalLogs.getBytes();
			resp.setContentLength(rb.length);

			String fileName = "Logs_"+ctx.get("jsessionid")+"_"+System.currentTimeMillis()+".txt";
			resp.setContentType(((ServletContext)ctx.get("DocumentServletContext")).getMimeType(fileName));
			resp.setHeader("content-disposition", "attachment;filename="+fileName);

			ServletOutputStream sout = resp.getOutputStream();
			sout.write(rb);
			sout.close();
    	}catch(Exception e){
    		logger.error("Unable to download user logs due to error : " + e.getMessage());
    	}
    }*/
  //Method created to download security matrix file
    public static void getConfigurationSecurityMatrix(Context ctx) throws Exception{
    	try{
    		HSSFWorkbook book = new HSSFWorkbook();
    		HSSFSheet sheet = book.createSheet("Roles Matrix");
    		Font font = book.createFont();
    		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

    		if(ctx.get("security_list_1") != null && ctx.get("security_list_1") instanceof List){
    			List list = (List)ctx.get("security_list_1");

    			HSSFRow row = sheet.createRow(0);

    			Map map = (LinkedHashMap)list.get(0);
    			Set keySet = map.keySet();
    			Iterator<String> itr = keySet.iterator();
    			int i = 0;
    			String[] keysArray = new String[keySet.size()];
    			while(itr.hasNext()){
    				String key = itr.next();
    				if(key.equalsIgnoreCase("Resource"))
    					continue;

    				keysArray[i] = key;

    				HSSFCell cell = row.createCell(i);
    				sheet.setColumnWidth(i, 5000);
    				cell.setCellValue(key);
    				CellUtil.setFont(cell, book, font);
    				i++;
    			}

    			for(i=0; i<list.size(); i++){
    				map = (LinkedHashMap)list.get(i);

    				row = sheet.createRow(i+1);

    				for(int j=0; j<keysArray.length; j++){
    					HSSFCell cell = row.createCell(j);
        				cell.setCellValue(map.get(keysArray[j]) != null ? map.get(keysArray[j]).toString() : HtmlConstants.EMPTY);
    				}
    			}
    		}

    		ByteArrayOutputStream bout = new ByteArrayOutputStream();
			book.write(bout);

    		HttpServletResponse resp = (HttpServletResponse)ctx.get("DocumentResponse");
			byte[] rb = bout.toByteArray();
			resp.setContentLength(rb.length);

			String fileName = ctx.getProject()+"_SecurityMatrix.xls";

			resp.setContentType(((ServletContext)ctx.get("DocumentServletContext")).getMimeType(fileName));
			resp.setHeader("content-disposition", "attachment;filename="+fileName);
			ServletOutputStream sout = resp.getOutputStream();
			sout.write(rb);
			sout.close();
    	}catch(Exception e){
    		logger.error("Unable to download user logs due to error : " + e.getMessage());
    	}
    }
    public static void checkAgencyStatusFromLookup(Context ctx) throws Exception{
    	try {

    		if(ctx.get("add_new_location_true") == null || HtmlConstants.EMPTY.equals(ctx.get("add_new_location_true"))){
    			ctx.put("add_new_location_true","N");
    		}

    		if(ctx.get("isOnboardingWorkflowFound") != null && ctx.get("isOnboardingWorkflowFound").toString().equals("Y")){
    			if(ctx.get("registration_type_desc") == null || HtmlConstants.EMPTY.equals(ctx.get("registration_type_desc")) || !"PRERG".equals(ctx.get("registration_type_desc"))) {
    				ctx.put("pendingAppointmentStatus", "Y");
    				ctx.put("allAgencyStatus", "N");
    			}else
    				ctx.put("allAgencyStatus", "Y");
    			//ctx.put("allAgencyStatus", "N");
    			return;
    		}else{
    			//ctx.put("pendingAppointmentStatus", "N");
    			ctx.put("allAgencyStatus", "Y");
    			return;
    		}


    		/*
    			if(ctx.get("isShowAgencyStatusWithoutOnHold") != null && !HtmlConstants.EMPTY.equals(ctx.get("isShowAgencyStatusWithoutOnHold")) && "Y".equals(ctx.get("isShowAgencyStatusWithoutOnHold"))){
    				RuleImpl isNotClientId1013ImplI = RulesResources.getInstance(ctx).findRule("ProducerOneRule.isNotClientId1013");
    				RuleImpl isClientId1013 = RulesResources.getInstance(ctx).findRule("ProducerOneRule.isClientId1013");
    				RuleImpl isClientId1016 = RulesResources.getInstance(ctx).findRule("ProducerOneRule.isClientId1016");
    				if(isNotClientId1013ImplI.evaluate(ctx, null)){
    					RuleImpl pendingAgencyStatusForOnBoarding =RulesResources.getInstance(ctx).findRule("ProducerOneRule.isShowPendingAgencyStatusForOnBoarding");
    					if(pendingAgencyStatusForOnBoarding.evaluate(ctx, null)){
    						ctx.put("pendingAppointmentStatus", "Y");
    					}
    					RuleImpl isShowDeclinedAgencyStatusForOnBoarding =RulesResources.getInstance(ctx).findRule("ProducerOneRule.isShowDeclinedAgencyStatusForOnBoarding");
    					if(isShowDeclinedAgencyStatusForOnBoarding.evaluate(ctx, null)){
    						ctx.put("declinedStatus", "Y");
    					}
    					RuleImpl isShowAgencyStatusForExistingAgency =RulesResources.getInstance(ctx).findRule("ProducerOneRule.isShowAgencyStatusForExistingAgency");
    					if(isShowAgencyStatusForExistingAgency.evaluate(ctx, null)){
    						ctx.put("allAgencyStatus", "Y");
    					}
    				}
    				if(isClientId1013.evaluate(ctx, null)){
    					RuleImpl isShowPendingAgencyStatusForOnBoarding =RulesResources.getInstance(ctx).findRule("ProducerOneRule.isShowPendingAgencyStatusForOnBoarding");
    					if(isShowPendingAgencyStatusForOnBoarding.evaluate(ctx, null)){
    						ctx.put("pendingAppointmentStatus", "Y");
    					}else{
    						ctx.put("statusForExistingAgency", "Y");
    					}
    					RuleImpl isShowAgencyStatusForExistingAgency =RulesResources.getInstance(ctx).findRule("ProducerOneRule.isShowAgencyStatusForExistingAgency");
    					if(isShowAgencyStatusForExistingAgency.evaluate(ctx, null)){
    						ctx.put("statusForExistingAgency", "Y");
    					}else{
    						ctx.put("statusForExistingAgency", "Y");
    					}
    				}
    				if(isClientId1016.evaluate(ctx, null)){
    						ctx.put("allAgencyStatus", "Y");
    						ctx.put("pendingAppointmentStatus", "N");
    						ctx.put("declinedStatus", "N");

    				}
    			}
    		if(ctx.get("isShowOnHoldBy") != null && !HtmlConstants.EMPTY.equals(ctx.get("isShowOnHoldBy")) && "Y".equals(ctx.get("isShowOnHoldBy"))){
    			RuleImpl isBGContractApptOnboardingStatusFound =RulesResources.getInstance(ctx).findRule("ProducerOneRule.isBGContractApptOnboardingStatusFound");
    			if(isBGContractApptOnboardingStatusFound.evaluate(ctx, null)){
    				ctx.put("pendingStatusOnly", "Y");
    			}
    			RuleImpl isBGContractApptOnboardingStatusNotFound =RulesResources.getInstance(ctx).findRule("ProducerOneRule.isBGContractApptOnboardingStatusNotFound");
    			if(isBGContractApptOnboardingStatusNotFound.evaluate(ctx, null)){
    				ctx.put("alltatusWithProspectOnHold", "Y");
    			}

    		}*/
        } catch (Exception e) {
        		logger.error("Unable to populate Agency Status From Lookup due to error : " + e.getMessage());
    	}
    }

public static void getCommissionScheduleAttachmentHeaderDynamic(Context ctx) throws Exception{
	try {
		if(ctx.get("agency_id") == null || HtmlConstants.EMPTY.equals(ctx.get("agency_id")))
			return;

		new SetParametersForStoredProcedures().setParametersInContext(ctx, "agency_id");
		List list = SqlResources.getSqlMapProcessor(ctx).select("commission.GetAttachedRatesDynamicHeader_p", ctx);
		if(list != null && list.size() >0){
			for(int i=0;i< list.size(); i++){
				Map map = (Map)list.get(i);
				if(map != null && !map.isEmpty()){
					if(map.get("showTermPercentage") != null && !HtmlConstants.EMPTY.equals(map.get("showTermPercentage")))
						ctx.put("showTermPercentage", map.get("showTermPercentage"));
					if(map.get("showCoverage") != null && !HtmlConstants.EMPTY.equals(map.get("showCoverage")))
						ctx.put("showCoverage", map.get("showCoverage"));
					if(map.get("showTier") != null && !HtmlConstants.EMPTY.equals(map.get("showTier")))
						ctx.put("showTier", map.get("showTier"));
					if(map.get("showCompetitorBusiness") != null && !HtmlConstants.EMPTY.equals(map.get("showCompetitorBusiness")))
						ctx.put("showCompetitorBusiness", map.get("showCompetitorBusiness"));
					if(map.get("showGroupKey") != null && !HtmlConstants.EMPTY.equals(map.get("showGroupKey")))
						ctx.put("showGroupKey", map.get("showGroupKey"));
					if(map.get("showSpecialCommission") != null && !HtmlConstants.EMPTY.equals(map.get("showSpecialCommission")))
						ctx.put("showSpecialCommission", map.get("showSpecialCommission"));
					if(map.get("showCommissionScheduleTransactionType") != null && !HtmlConstants.EMPTY.equals(map.get("showCommissionScheduleTransactionType")))
						ctx.put("showCommissionScheduleTransactionType", map.get("showCommissionScheduleTransactionType"));
					if(map.get("showCommissionScheduleRadius") != null && !HtmlConstants.EMPTY.equals(map.get("showCommissionScheduleRadius")))
						ctx.put("showCommissionScheduleRadius", map.get("showCommissionScheduleRadius"));
					if(map.get("showCommissionScheduleVenture") != null && !HtmlConstants.EMPTY.equals(map.get("showCommissionScheduleRadius")))
						ctx.put("showCommissionScheduleVenture", map.get("showCommissionScheduleVenture"));
					if(map.get("showCommissionScheduleOperation") != null && !HtmlConstants.EMPTY.equals(map.get("showCommissionScheduleOperation")))
						ctx.put("showCommissionScheduleOperation", map.get("showCommissionScheduleOperation"));
				}
			}
		}
	} catch (Exception e) {
		// TODO: handle exception
		logger.error("Unable to Get Commission Schedule Attachment Header Dynamic due to error : " + e.getMessage());
	}
}

	//Method created to generate commission schedule dynamic attachment history
	public static void generateCommissionScheduleDynamicAttachmentHistory(Context ctx) throws Exception{
		try{
			if(ctx.get("template_schema") == null || ctx.get("template_schema").toString().equals(HtmlConstants.EMPTY))
				return;

			String commission_schedule = ctx.get("commission_schedule").toString();
			String template_schema = ctx.get("template_schema").toString();

			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(new StringReader(template_schema));
			Element rootElement = doc.getRootElement();

			LinkedHashMap schemaMap = new LinkedHashMap();

			if(rootElement.getChildren() != null && rootElement.getChildren().size() > 0){
				Element tableElement = new Element(HtmlConstants.TABLE);
				tableElement.setAttribute(HtmlConstants.WIDTH, "100%");
				tableElement.setAttribute(HtmlConstants.CELLSPACING, "0");
				tableElement.setAttribute(HtmlConstants.CELLPADDING, "0");

				for(int i=0; i<rootElement.getChildren().size(); i++){
					Element tablesElement = (Element)rootElement.getChildren().get(i);

					if(tablesElement.getChildren() != null && tablesElement.getChildren().size() > 0){
						for(int j=0; j<tablesElement.getChildren().size(); j++){
							Element tableEle = (Element)tablesElement.getChildren().get(j);

							if(tableEle.getChildren() != null && tableEle.getChildren().size() > 0){
								LinkedHashMap tableMap = new LinkedHashMap();

								Element trElement = new Element(HtmlConstants.TR);
								Element tdElement = new Element(HtmlConstants.TD);

								for(int k=0; k<tableEle.getChildren().size(); k++){
									Element parametersElement = (Element)tableEle.getChildren().get(k);

									if(parametersElement.getChildren() != null && parametersElement.getChildren().size() > 0){
										for(int l=0; l<parametersElement.getChildren().size(); l++){
											Element parameterElement = (Element)parametersElement.getChildren().get(l);

											if(parameterElement.getAttributeValue("isfilter") != null && parameterElement.getAttributeValue("isfilter").equals("Y")){
												tableMap.put(parameterElement.getAttributeValue(HtmlConstants.NAME), parameterElement);

												String fielddescription = parameterElement.getAttributeValue("fielddescription");
												fielddescription = DataUtils.getLabelFromLabelConf(fielddescription);

												tdElement = new Element(HtmlConstants.TD);
												tdElement.setAttribute(HtmlConstants.CSS_CLASS, "HeaderStyle");
												tdElement.setText(fielddescription);

												trElement.addContent(tdElement.detach());
											}
										}

										tdElement = new Element(HtmlConstants.TD);
										tdElement.setAttribute(HtmlConstants.CSS_CLASS, "HeaderStyle");
										tdElement.setText(ProducerOneUtils.getLabelFromLabelConf("schedules_effective_date_label"));

										trElement.addContent(tdElement.detach());

										tdElement = new Element(HtmlConstants.TD);
										tdElement.setAttribute(HtmlConstants.CSS_CLASS, "HeaderStyle");
										tdElement.setText(ProducerOneUtils.getLabelFromLabelConf("schedules_termination_date_label"));

										trElement.addContent(tdElement.detach());

										tdElement = new Element(HtmlConstants.TD);
										tdElement.setAttribute(HtmlConstants.CSS_CLASS, "HeaderStyle");
										tdElement.setText(DataUtils.getLabelFromLabelConf("producernumberstablabel"));

										trElement.addContent(tdElement.detach());

										tdElement = new Element(HtmlConstants.TD);
										tdElement.setAttribute(HtmlConstants.CSS_CLASS, "HeaderStyle");
										tdElement.setText("NB Total(%)");

										trElement.addContent(tdElement.detach());

										tdElement = new Element(HtmlConstants.TD);
										tdElement.setAttribute(HtmlConstants.CSS_CLASS, "HeaderStyle");
										tdElement.setText("RB Total(%)");

										trElement.addContent(tdElement.detach());

										tdElement = new Element(HtmlConstants.TD);
										tdElement.setAttribute(HtmlConstants.CSS_CLASS, "HeaderStyle");
										tdElement.setText("User ID");

										trElement.addContent(tdElement.detach());

										tdElement = new Element(HtmlConstants.TD);
										tdElement.setAttribute(HtmlConstants.CSS_CLASS, "HeaderStyle");
										tdElement.setText("Modified Date/Time");

										trElement.addContent(tdElement.detach());
									}
								}

								tableElement.addContent(trElement.detach());

								schemaMap.put(tableEle.getAttributeValue(HtmlConstants.ID), tableMap);
							}
						}
					}
				}

				builder = new SAXBuilder();
				doc = builder.build(new StringReader(commission_schedule));
				rootElement = doc.getRootElement();

				LinkedHashMap commissionSchedulesMap = new LinkedHashMap();

				if(rootElement.getChildren() != null && rootElement.getChildren().size() > 0){
					for(int i=0; i<rootElement.getChildren().size(); i++){
						Element ele = (Element)rootElement.getChildren().get(i);

						if(ele.getName().equals("commission_schedules")){
							if(ele.getChildren() != null && ele.getChildren().size() > 0){
								LinkedHashMap commissionScheduleMap = new LinkedHashMap();

								for(int j=0; j<ele.getChildren().size(); j++){
									Element commissionElement = (Element)ele.getChildren().get(j);

									if(commissionElement.getName().equals("commission_schedule")){
										commissionScheduleMap.put(((Element)commissionElement.getChildren("rate_id").get(0)).getText(), commissionElement);
									}
								}

								commissionSchedulesMap.put(ele.getAttributeValue("table_id"), commissionScheduleMap);
							}
						}
					}
				}

				if(ctx.get("GetCommissionAttachmentHistory_list_01") != null && ctx.get("GetCommissionAttachmentHistory_list_01") instanceof List){
					List list = (List)ctx.get("GetCommissionAttachmentHistory_list_01");

					Element trElement = null;
					Element tdElement = null;

					if(list != null && list.size() > 0){
						for(int i=0; i<list.size(); i++){
							Map map = (Map)list.get(i);

							new WorkflowImpl().checkForDateFields(ctx, map);
							new WorkflowImpl().populateClobValue(map);

							String rate_id = map.get("rate_id").toString();

							trElement = new Element(HtmlConstants.TR);
							if(i%2 == 0)
								trElement.setAttribute(HtmlConstants.CSS_CLASS, "listRow2CSS");
							else
								trElement.setAttribute(HtmlConstants.CSS_CLASS, "listRow1CSS");

							LinkedHashMap commissionScheduleMap = (LinkedHashMap)commissionSchedulesMap.get("table_1");
							Element commissionScheduleElement = (Element)commissionScheduleMap.get(rate_id);

							LinkedHashMap tableMap = (LinkedHashMap)schemaMap.get("table_1");
							Set keySet = tableMap.keySet();
							Iterator<String> itr = keySet.iterator();
							while(itr.hasNext()){
								String key = itr.next();

								String value = ((Element)commissionScheduleElement.getChildren(key).get(0)).getText() != null ? ((Element)commissionScheduleElement.getChildren(key).get(0)).getText() : HtmlConstants.EMPTY;

								value = new TemplateBuilderUtils().checkForTemplateSchemaFilter(ctx, key, value, "table_1", false, null);

								if(value.contains("="))
									value = value.substring(0, value.indexOf("="));

								/*Element parameterElement = (Element)tableMap.get(key);
								if(parameterElement != null && parameterElement.getAttributeValue("displaytype") != null &&
									parameterElement.getAttributeValue("displaytype").equals("multiplecheckbox")){

									if(value.equals("0"))
										value = "All";
								}*/

								tdElement = new Element(HtmlConstants.TD);
								tdElement.setText(value);
								trElement.addContent(tdElement.detach());
							}

							tdElement = new Element(HtmlConstants.TD);
							tdElement.setText(map.get("effective_date") != null ? map.get("effective_date").toString() : HtmlConstants.EMPTY);
							trElement.addContent(tdElement.detach());

							tdElement = new Element(HtmlConstants.TD);
							tdElement.setText(map.get("terminate_date") != null ? map.get("terminate_date").toString() : HtmlConstants.EMPTY);
							trElement.addContent(tdElement.detach());

							tdElement = new Element(HtmlConstants.TD);
							tdElement.setText(map.get("agency_codes") != null ? map.get("agency_codes").toString() : HtmlConstants.EMPTY);
							trElement.addContent(tdElement.detach());

							tdElement = new Element(HtmlConstants.TD);
							tdElement.setText(map.get("NBTotal") != null ? map.get("NBTotal").toString() : HtmlConstants.EMPTY);
							trElement.addContent(tdElement.detach());

							tdElement = new Element(HtmlConstants.TD);
							tdElement.setText(map.get("RBNewTotal") != null ? map.get("RBNewTotal").toString() : HtmlConstants.EMPTY);
							trElement.addContent(tdElement.detach());

							tdElement = new Element(HtmlConstants.TD);
							tdElement.setText(map.get("action_uuid") != null ? map.get("action_uuid").toString() : HtmlConstants.EMPTY);
							trElement.addContent(tdElement.detach());

							tdElement = new Element(HtmlConstants.TD);
							tdElement.setText(map.get("action_ts") != null ? map.get("action_ts").toString() : HtmlConstants.EMPTY);
							trElement.addContent(tdElement.detach());

							tableElement.addContent(trElement.detach());
						}
					}else{
						trElement = new Element(HtmlConstants.TR);
						trElement.setAttribute(HtmlConstants.CSS_CLASS, "listRow2CSS");

						tdElement = new Element(HtmlConstants.TD);
						tdElement.setText("No Record Found");
						tdElement.setAttribute("class", "EmptyRowStyle");
						tdElement.setAttribute("colspan", "20");

						trElement.addContent(tdElement.detach());
						tableElement.addContent(trElement.detach());
					}

					String dynamicContent = new XMLOutputter(Format.getPrettyFormat()).outputString(tableElement);
					ctx.put("#dynamicContent#", dynamicContent);
				}
			}
		}catch(Exception e){
			logger.error("Unable to generate commission schedule attachment history due to error : " + e.getMessage());
		}
	}

	public static void verifyAddressFromWebService(Context ctx) throws Exception{
		String wsURL = SystemProperties.getInstance().getProperty(
				"integration.ws.deprecated.baseURL"); // "http://localhost:8080/intws/api/";
		String username = SystemProperties.getInstance().getProperty(
				"integration.ws.deprecated.username"); // "pone";
		String password = SystemProperties.getInstance().getProperty(
				"integration.ws.deprecated.password");// "Secure$1";

		if(logger.isDebugEnabled(ctx))
			logger.debug(ctx, "VerifyAddressFromWebService " + wsURL + " >> " + username + " >> " + password);
		if(ctx.get("serviceName") != null && !HtmlConstants.EMPTY.equals(ctx.get("serviceName").toString())
				&& ctx.get("intgerationInputXml") != null && !HtmlConstants.EMPTY.equals(ctx.get("intgerationInputXml").toString())){
			IntegrationClient integrationClient = new IntegrationClient(wsURL,username, password);

			JSONObject verifyAddressRequest = new JSONObject();
			verifyAddressRequest.put("serviceName", ctx.get("serviceName").toString());
			verifyAddressRequest.put("clientRole", "PONE");
			JSONObject payload = new JSONObject();
			JSONObject requestParams = new JSONObject();
			//requestParams.put("intgerationInputXml", "<Root><Root1><state_abbreviation>NJ</state_abbreviation><phone>013-234-5678</phone><zip>11111-1111</zip><state_id>29</state_id><city>Somerset</city><addressline1>220 Davidson Avenue</addressline1><addressline2>Suite 105</addressline2><country_code>US</country_code><addressVerificationUserId>299OUTLI6458</addressVerificationUserId><Zip4>111</Zip4><Zip5>11111</Zip5></Root1></Root>");
			requestParams.put("intgerationInputXml", ctx.get("intgerationInputXml").toString());
			payload.put("request", requestParams);
			verifyAddressRequest.put("payload", payload);

			if(logger.isDebugEnabled(ctx))
				logger.debug(ctx, "Verify Address Request : " + verifyAddressRequest.toString());
			String verifyAddressResponse = (String) integrationClient.sendAndReceive(verifyAddressRequest);

			if(logger.isDebugEnabled(ctx))
				logger.debug(ctx, "Verify Address Response : " + verifyAddressResponse);
			JSONObject jsonObject = new JSONObject(verifyAddressResponse);

	        if(jsonObject.has("payload") && jsonObject.get("payload") instanceof JSONObject) {
	        	JSONObject payloadObject = (JSONObject)jsonObject.get("payload");

	        	if("failed".equals(jsonObject.get("status").toString())){
					String message = payloadObject.get("message").toString();
	        		DataUtils.populateError(ctx, ctx.get("errorDisplayKey").toString(), message.replaceAll("usps", "USPS"));
					return;
				}

	        	if(payloadObject.has("message")) {
					List<Map<String,String>> resultList = convertElementFromXml(payloadObject.get("message").toString());
					if(resultList != null && resultList.size() >0){
						if(resultList.size() ==1){
							Map<String,String> resultMap = resultList.get(0);
							if(resultMap.containsKey("Description")){
								DataUtils.populateError(ctx, ctx.get("errorDisplayKey").toString(), resultMap.get("Description"));
								return;
							}else{
								List addressSuggestionList = new ArrayList();
								resultMap.put("row_id", "0");
								String zip=resultMap.get("Zip5");
								if(resultMap.get("Zip4") != null && !HtmlConstants.EMPTY.equals(resultMap.get("Zip4")))
									zip = zip + "-"+resultMap.get("Zip4").toString();
								resultMap.put("zip", zip);
								if(resultMap.get("Address1") == null  || HtmlConstants.EMPTY.equals(resultMap.get("Address1"))){
									resultMap.put("Address1",resultMap.get("Address2"));
									resultMap.put("Address2",null);
								}else{
									String address2  = resultMap.get("Address2");
									resultMap.put("Address2",resultMap.get("Address1"));
									resultMap.put("Address2",address2);
								}
								addressSuggestionList.add(resultMap);
								ctx.put("addressSuggestion_list_0", addressSuggestionList);
								DataUtils.setValueInSession((Context)ctx, "addressSuggestion_list_0");
							}
						}else{
							List addressSuggestionList = new ArrayList();
							for(int i=0; i<resultList.size(); i++){
								Map<String,String> resultMap = resultList.get(i);
								resultMap.put("row_id",String.valueOf(i));
								String zip = resultMap.get("Zip5");
								if(resultMap.get("Zip4") != null && !HtmlConstants.EMPTY.equals(resultMap.get("Zip4")))
									zip = "-" + resultMap.get("Zip4").toString();
								resultMap.put("zip", zip);
								if(resultMap.get("Address1") == null  || HtmlConstants.EMPTY.equals(resultMap.get("Address1"))){
									resultMap.put("Address1",resultMap.get("Address2"));
									resultMap.put("Address2",null);
								}else{
									String address2  = resultMap.get("Address2");
									resultMap.put("Address2",resultMap.get("Address1"));
									resultMap.put("Address2",address2);
								}
								addressSuggestionList.add(resultMap);
							}

							ctx.put("addressSuggestion_list_0", addressSuggestionList);
							DataUtils.setValueInSession((Context)ctx, "addressSuggestion_list_0");
						}
					}
	        	}
	        }
		}



	}

	public static List<Map<String,String>> convertElementFromXml(String xml){
		List<Map<String,String>> finalList = new ArrayList<Map<String,String>>();
		SAXBuilder builder = new SAXBuilder();
		StringReader reader = null;
		try {
			reader = new StringReader(xml);
			org.jdom.Document doc = builder.build(reader);
			Element rootElement =  doc.getRootElement();

			finalList = createMap(rootElement,finalList);
		} catch (JDOMException e) {
			//e.printStackTrace();
			 logger.error("Unable to execute convertElementFromXml method due to error : " + DataUtils.getExceptionStackTrace(e));
		} catch (IOException e) {
			//e.printStackTrace();
			logger.error("Unable to execute convertElementFromXml method due to error : " + DataUtils.getExceptionStackTrace(e));
		}finally {
			reader.close();
		}

		return finalList;
	}

	public static List<Map<String,String>> createMap(Element element,List<Map<String,String>> finalList){
		Map<String, String> map = new HashMap<String, String>();
		List list = element.getChildren();
		for(int i =0;i<list.size();i++){
			Element childElement = ((Element)list.get(i));
			String name =  childElement.getName()!= null && !"".equals(childElement.getName())?childElement.getName():null;
			String value =  childElement.getText()!= null && !"".equals(childElement.getText())?childElement.getText():null;
			if(map.containsKey(name)
					&& ((childElement.getChildren() != null && value != null) || childElement.getChildren() == null)){
				finalList.add(map);
				map = new HashMap<String, String>();
				map.put(name, value);
			}else if(childElement.getChildren() != null ){
				if(value != null)
					map.put(name, value);
			}else{
				map.put(name, value);
			}

			if(childElement.getChildren() != null && childElement.getChildren().size() > 0){
				createMap(childElement,finalList);
			}
		}

		if(map != null && map.keySet() != null && map.keySet().size()>0 )
			finalList.add(map);

		return finalList;
	}

	public static void populateVerifiedAddress(Context ctx) throws Exception{
		if(ctx.get("verfyingAddressType")!= null && !HtmlConstants.EMPTY.equals(ctx.get("verfyingAddressType").toString())){

			if(ctx.get("selected_row_id") ==null || HtmlConstants.EMPTY.equals(ctx.get("selected_row_id").toString())) {
        		DataUtils.populateError(ctx, "addressSuggestionPageError", "Please select atleast one Record");
        		return;
        	}

			if(ctx.get("verfyingAddressType").equals("agencyBusinessAddress") || ctx.get("verfyingAddressType").equals("agentBusinessAddress")){
				if(ctx.get("DocumentRequest") != null && ctx.get("DocumentRequest") instanceof HttpServletRequest){
		            HttpServletRequest req = (HttpServletRequest)ctx.get("DocumentRequest");
		            HttpSession sess = req.getSession();

		            if(sess.getAttribute("addressSuggestion_list_0") != null){
		            	List addressSuggestionList= (List)sess.getAttribute("addressSuggestion_list_0");
		            	if(addressSuggestionList != null && addressSuggestionList.size() >0){
		            		int selectedRowId =  Integer.parseInt(ctx.get("selected_row_id").toString());
		            		Map map = (Map)addressSuggestionList.get(selectedRowId);
		            		if(map != null){
		            			Context newCtx = new Context();
		            			newCtx.setProject("ProducerOne");
		            			ctx.put("addressline1_business", map.get("Address1"));
		            			ctx.put("addressline2_business", map.get("Address2"));
		            			ctx.put("city_business", map.get("City"));


		            			req.setAttribute("addressline1_business", map.get("Address1"));
		            			req.setAttribute("addressline2_business", map.get("Address2"));
		            			req.setAttribute("city_business", map.get("City"));

		            			newCtx.put("State", map.get("State"));
		            			new SetParametersForStoredProcedures().setParametersInContext(newCtx, "State");
		            			Object obj = SqlResources.getSqlMapProcessor(newCtx).findByKey("SqlStmts.sqlStatementsviewgetStateIdByAbbreviation", newCtx);

		            			if(obj != null && obj instanceof Map){
                                    Map map1 = (Map)obj;
                                    ctx.put("state_id_business", map1.get("stateId").toString());
                                    req.setAttribute("state_id_business", map1.get("stateId"));
                                }

		            			ctx.put("zip_business", map.get("zip").toString().replace("-",""));
		            			req.setAttribute("zip_business",map.get("zip").toString().replace("-",""));
		            		}
		            	}
		            }

		            if(ctx.get("verfyingAddressType").equals("agentBusinessAddress")){
		            	req.setAttribute("contacttype", ctx.get("contacttype"));
		            }

				}
			}else if(ctx.get("verfyingAddressType").equals("agencyMailingAddress") || ctx.get("verfyingAddressType").equals("agentMailingAddress")){
				if(ctx.get("DocumentRequest") != null && ctx.get("DocumentRequest") instanceof HttpServletRequest){
		            HttpServletRequest req = (HttpServletRequest)ctx.get("DocumentRequest");
		            HttpSession sess = req.getSession();

		            if(sess.getAttribute("addressSuggestion_list_0") != null){
		            	List addressSuggestionList= (List)sess.getAttribute("addressSuggestion_list_0");
		            	if(addressSuggestionList != null && addressSuggestionList.size() >0){
		            		int selectedRowId =  Integer.parseInt(ctx.get("selected_row_id").toString());
		            		Map map = (Map)addressSuggestionList.get(selectedRowId);
		            		if(map != null){
		            			Context newCtx = new Context();
		            			newCtx.setProject("ProducerOne");
		            			ctx.put("addressline1_mailing", map.get("Address1"));
		            			ctx.put("addressline2_mailing", map.get("Address2"));
		            			ctx.put("city_mailing", map.get("City"));

		            			req.setAttribute("addressline1_mailing", map.get("Address1"));
		            			req.setAttribute("addressline2_mailing", map.get("Address2"));
		            			req.setAttribute("city_mailing", map.get("City"));

		            			newCtx.put("State", map.get("State"));
		            			new SetParametersForStoredProcedures().setParametersInContext(newCtx, "State");
		            			Object obj = SqlResources.getSqlMapProcessor(newCtx).findByKey("SqlStmts.sqlStatementsviewgetStateIdByAbbreviation", newCtx);

		            			if(obj != null && obj instanceof Map){
                                   Map map1 = (Map)obj;
                                   ctx.put("state_id_mailing", map1.get("stateId").toString());
                                   req.setAttribute("state_id_mailing", map1.get("stateId").toString());
		            			}

		            			ctx.put("zip_mailing", map.get("zip").toString().replace("-",""));
		            			req.setAttribute("zip_mailing", map.get("zip").toString().replace("-",""));
		            		}
		            	}
		            }

		            if(ctx.get("verfyingAddressType").equals("agentMailingAddress")){
		            	req.setAttribute("contacttype", ctx.get("contacttype"));
		            }
				}

			}else if(ctx.get("verfyingAddressType").equals("agencyW9Address") || ctx.get("verfyingAddressType").equals("agentW9Address")){
				if(ctx.get("DocumentRequest") != null && ctx.get("DocumentRequest") instanceof HttpServletRequest){
		            HttpServletRequest req = (HttpServletRequest)ctx.get("DocumentRequest");
		            HttpSession sess = req.getSession();

		            if(sess.getAttribute("addressSuggestion_list_0") != null){
		            	List addressSuggestionList= (List)sess.getAttribute("addressSuggestion_list_0");
		            	if(addressSuggestionList != null && addressSuggestionList.size() >0){
		            		int selectedRowId =  Integer.parseInt(ctx.get("selected_row_id").toString());
		            		Map map = (Map)addressSuggestionList.get(selectedRowId);
		            		if(map != null){
		            			Context newCtx = new Context();
		            			newCtx.setProject("ProducerOne");
		            			ctx.put("addressline1_w9", map.get("Address1"));
		            			ctx.put("addressline2_w9", map.get("Address2"));
		            			ctx.put("city_w9", map.get("City"));

		            			req.setAttribute("addressline1_w9", map.get("Address1"));
		            			req.setAttribute("addressline2_w9", map.get("Address2"));
		            			req.setAttribute("city_w9", map.get("City"));

		            			newCtx.put("State", map.get("State"));
		            			new SetParametersForStoredProcedures().setParametersInContext(newCtx, "State");
		            			Object obj = SqlResources.getSqlMapProcessor(newCtx).findByKey("SqlStmts.sqlStatementsviewgetStateIdByAbbreviation", newCtx);

		            			if(obj != null && obj instanceof Map){
		            				Map map1 = (Map)obj;
                                    ctx.put("state_id_w9", map1.get("stateId").toString());
                                    req.setAttribute("state_id_w9", map1.get("stateId").toString());
                                }

		            			ctx.put("zip_w9", map.get("zip").toString().replace("-",""));
		            			req.setAttribute("zip_w9", map.get("zip").toString().replace("-",""));
		            		}
		            	}
		            }
		            if(ctx.get("verfyingAddressType").equals("agentW9Address")){
		            	req.setAttribute("contacttype", ctx.get("contacttype"));
		            }
				}
			}else if(ctx.get("verfyingAddressType").equals("vendorAddress") || ctx.get("verfyingAddressType").equals("producerBillingTermAddress")){
				if(ctx.get("DocumentRequest") != null && ctx.get("DocumentRequest") instanceof HttpServletRequest){
		            HttpServletRequest req = (HttpServletRequest)ctx.get("DocumentRequest");
		            HttpSession sess = req.getSession();

		            if(sess.getAttribute("addressSuggestion_list_0") != null){
		            	List addressSuggestionList= (List)sess.getAttribute("addressSuggestion_list_0");
		            	if(addressSuggestionList != null && addressSuggestionList.size() >0){
		            		int selectedRowId =  Integer.parseInt(ctx.get("selected_row_id").toString());
		            		Map map = (Map)addressSuggestionList.get(selectedRowId);
		            		if(map != null){
		            			Context newCtx = new Context();
		            			newCtx.setProject("ProducerOne");
		            			ctx.put("addressline1", map.get("Address1"));
		            			ctx.put("addressline2", map.get("Address2"));
		            			ctx.put("city", map.get("City"));

		            			req.setAttribute("addressline1", map.get("Address1"));
		            			req.setAttribute("addressline2", map.get("Address2"));
		            			req.setAttribute("city", map.get("City"));

		            			newCtx.put("State", map.get("State"));
		            			new SetParametersForStoredProcedures().setParametersInContext(newCtx, "State");
		            			Object obj = SqlResources.getSqlMapProcessor(newCtx).findByKey("SqlStmts.sqlStatementsviewgetStateIdByAbbreviation", newCtx);
		            			if(obj != null && obj instanceof Map){
		            				Map map1 = (Map)obj;
		            				ctx.put("state_id", map1.get("stateId").toString());
		            				req.setAttribute("state_id", map1.get("stateId").toString());
		            			}

		            			ctx.put("zip", map.get("zip").toString().replace("-",""));
		            			req.setAttribute("zip", map.get("zip").toString().replace("-",""));
		            		}
		            	}
		            }
				}
			}else if(ctx.get("verfyingAddressType").equals("addNewAgencyAddress")){
				if(ctx.get("DocumentRequest") != null && ctx.get("DocumentRequest") instanceof HttpServletRequest){
		            HttpServletRequest req = (HttpServletRequest)ctx.get("DocumentRequest");
		            HttpSession sess = req.getSession();

		            if(sess.getAttribute("addressSuggestion_list_0") != null){
		            	List addressSuggestionList= (List)sess.getAttribute("addressSuggestion_list_0");
		            	if(addressSuggestionList != null && addressSuggestionList.size() >0){
		            		int selectedRowId =  Integer.parseInt(ctx.get("selected_row_id").toString());
		            		Map map = (Map)addressSuggestionList.get(selectedRowId);
		            		if(map != null){
		            			Context newCtx = new Context();
		            			newCtx.setProject("ProducerOne");
		            			ctx.put("addressline1_business", map.get("Address1"));
		            			ctx.put("addressline2_business", map.get("Address2"));
		            			ctx.put("city_business", map.get("City"));

		            			req.setAttribute("addressline1_business", map.get("Address1"));
		            			req.setAttribute("addressline2_business", map.get("Address2"));
		            			req.setAttribute("city_business", map.get("City"));

		            			newCtx.put("State", map.get("State"));
		            			new SetParametersForStoredProcedures().setParametersInContext(newCtx, "State");
		            			Object obj = SqlResources.getSqlMapProcessor(newCtx).findByKey("SqlStmts.sqlStatementsviewgetStateIdByAbbreviation", newCtx);

		            			if(obj != null && obj instanceof Map){
                                    Map map1 = (Map)obj;
                                    ctx.put("state_id_business", map1.get("stateId").toString());
                                    req.setAttribute("state_id_business", map1.get("stateId").toString());
                                }

		            			ctx.put("mailingzip", map.get("zip").toString().replace("-",""));
		            			req.setAttribute("mailingzip", map.get("zip").toString().replace("-",""));
		            		}
		            	}
		            }
				}
			}
			else if(ctx.get("verfyingAddressType").equals("agencyDECAddress")){
				if(ctx.get("DocumentRequest") != null && ctx.get("DocumentRequest") instanceof HttpServletRequest){
		            HttpServletRequest req = (HttpServletRequest)ctx.get("DocumentRequest");
		            HttpSession sess = req.getSession();

		            if(sess.getAttribute("addressSuggestion_list_0") != null){
		            	List addressSuggestionList= (List)sess.getAttribute("addressSuggestion_list_0");
		            	if(addressSuggestionList != null && addressSuggestionList.size() >0){
		            		int selectedRowId =  Integer.parseInt(ctx.get("selected_row_id").toString());
		            		Map map = (Map)addressSuggestionList.get(selectedRowId);
		            		if(map != null){
		            			Context newCtx = new Context();
		            			newCtx.setProject("ProducerOne");
		            			ctx.put("addressline1_DEC", map.get("Address1"));
		            			ctx.put("addressline2_DEC", map.get("Address2"));
		            			ctx.put("city_DEC", map.get("City"));

		            			req.setAttribute("addressline1_DEC", map.get("Address1"));
		            			req.setAttribute("addressline2_DEC", map.get("Address2"));
		            			req.setAttribute("city_DEC", map.get("City"));

		            			newCtx.put("State", map.get("State"));
		            			new SetParametersForStoredProcedures().setParametersInContext(newCtx, "State");
		            			Object obj = SqlResources.getSqlMapProcessor(newCtx).findByKey("SqlStmts.sqlStatementsviewgetStateIdByAbbreviation", newCtx);

		            			if(obj != null && obj instanceof Map){
                                    Map map1 = (Map)obj;
                                    ctx.put("state_id_DEC", map1.get("stateId").toString());
                                    req.setAttribute("state_id_DEC", map1.get("stateId").toString());
                                }

		            			  ctx.put("zip_DEC", map.get("zip").toString().replace("-",""));
			            			req.setAttribute("zip_DEC", map.get("zip").toString().replace("-",""));
		            		}
		            	}
		            }
				}
			}
		}
	}

	public static void subStringValueFromCtx(Context ctx,String inputFieldName,String startIndex,String endIndex,String isRemoveSpecialCharacter, String outputFieldName){
    	if(ctx.get(inputFieldName) != null && !HtmlConstants.EMPTY.equals(ctx.get(inputFieldName))){
    		try{
	    		String inputfieldData = ctx.get(inputFieldName).toString();
	    		if(isRemoveSpecialCharacter != null && !HtmlConstants.EMPTY.equals(isRemoveSpecialCharacter)
	    				&& "Y".equals(isRemoveSpecialCharacter)){
	    			inputfieldData = inputfieldData.toString().replaceAll("[^a-zA-Z0-9]","");
	    		}

	    		String outputData = inputfieldData.substring(Integer.parseInt(startIndex),Integer.parseInt(endIndex));
	    		if(outputFieldName != null && !HtmlConstants.EMPTY.equals(outputFieldName))
	    			ctx.put(outputFieldName, outputData);
	    		else
	    			ctx.put(inputFieldName, outputData);
    		}catch(Exception e){
    			logger.error(e.getLocalizedMessage());
    			ctx.put(inputFieldName, ctx.get(inputFieldName));
    		}
    	}
    }

	public static Object validateUploadDocument(Context ctx) throws Exception {
    	try{
    		if(ctx.get("isDocumentUploadNew") != null && ctx.get("isDocumentUploadNew").toString().equals("Y"))
    			new DocumentUploadBO().updateDocumentToSessionList(ctx);

    		String document_name = ctx.get("document_name") != null ? ctx.get("document_name").toString() : null;
    		String document_type_id = ctx.get("document_type_id") != null ? ctx.get("document_type_id").toString() : null;
    		String file = null;
    		String file_name = null;

    		HttpServletRequest req = (HttpServletRequest) ctx.get("DocumentRequest");
			Map docMap = new HashMap();
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List fileItems = ctx.get("fileItems") != null ? (List) ctx.get("fileItems") : null;
			if(fileItems != null && fileItems.size() > 0) {
				for (int i=0; i<fileItems.size(); i++) {
					FileItem item = (FileItem) fileItems.get(i);
					if (!item.isFormField()) {
						if(item.getName()!=null && !HtmlConstants.EMPTY.equals(item.getName().toString()) && item.get() != null && item.get().length >0){
							file = "File Found";
							//return null;
							file_name = item.getName();
						}
						if(item.getName()!=null && !HtmlConstants.EMPTY.equals(item.getName().toString()) && item.get() != null && item.get().length <=0){
							DataUtils.populateError((Context) ctx, "file", "fileSizeErrorKey");
							return null;
						}

					if(!new DocumentUploadBO().isValidFileToBeLoaded(ctx, item.getName()) && !ctx.get(Constants.INET_PAGE).toString().equals("addIDPDetails")){
    						String msg = "documentInvalidExtensionErrorKey";
    						MessageImpl msgImpl = MessageResources.getInstance(ctx).getMessage(msg);
    						if(msgImpl != null){
    							msg = MessageResources.getInstance(ctx).getMessage("documentInvalidExtensionErrorKey").getMessage() + " " +
    								((ctx.get("validFileExtensions") != null && !ctx.get("validFileExtensions").toString().equals(HtmlConstants.EMPTY) ? ctx.get("validFileExtensions").toString() : HtmlConstants.EMPTY));
    						}

    						DataUtils.populateError((Context)ctx, "documentName", msg);
    						return null;
    					}

						if(ctx.get("isDocumentUploadNew") != null && ctx.get("isDocumentUploadNew").toString().equals("Y")){
							//do nothing
						}else
							break;
					}
				}
			}

    		boolean isErrorFound = false;

    		if(ctx.get(HtmlConstants.INET_METHOD) != null && ctx.get(HtmlConstants.INET_METHOD).toString().equals("addDocumentToList") ){
    			if(StringUtils.isBlank(document_name) && !ctx.get(Constants.INET_PAGE).toString().equals("addIDPDetails")){
	    			DataUtils.populateError((Context) ctx, "document_name", "documentNameErrorKey");
	    			isErrorFound = true;
	    		}

	    		if(StringUtils.isBlank(document_type_id)){
	    			DataUtils.populateError((Context) ctx, "document_type_id", "documentTypeErrorKey");
	    			isErrorFound = true;
	    		}

	    		if(StringUtils.isBlank(file)){
	    			DataUtils.populateError((Context) ctx, "file", "documentErrorKey");
	    			isErrorFound = true;
	    		}

	    		if(isErrorFound)
	    			return null;

	    		List documentList = null;
	    		if(ctx.get("uploadDocument_list_1") != null && !HtmlConstants.EMPTY.equals("uploadDocument_list_1")){
	    			documentList = (List) ctx.get("uploadDocument_list_1");
	    		}

	    		if(documentList != null && documentList.size() > 0){
	    			for(int i=0; i<documentList.size(); i++){
	    				Map rowMap = (Map)documentList.get(i);

	    				//going to check for duplicacy for document_name
						if(rowMap!=null && rowMap.get("document_name")!=null && !HtmlConstants.EMPTY.equals(rowMap.get("document_name").toString())
								&& ctx.get("document_name")!=null && !HtmlConstants.EMPTY.equals(ctx.get("document_name").toString())){
							String documentName = rowMap.get("document_name").toString();
							String documentNameCtx = ctx.get("document_name").toString();
							if(documentName.equalsIgnoreCase(documentNameCtx)){
								DataUtils.populateError((Context) ctx, "documentName", "adminDuplicateError");
								break;
							}
						}

						//going to check for duplicacy for upload file name
						if(rowMap != null && rowMap.get("actual_file_name") != null && !HtmlConstants.EMPTY.equals(rowMap.get("actual_file_name").toString())
								&& file_name != null && !HtmlConstants.EMPTY.equals(file_name)){

							String documentName = rowMap.get("actual_file_name").toString();
							if(file_name.contains("\\"))
								file_name = file_name.substring(file_name.lastIndexOf("\\")+1, file_name.length());

							String documentNameCtx = file_name;
							if(documentName.equalsIgnoreCase(documentNameCtx)){
								DataUtils.populateError((Context) ctx, "documentName", "adminDuplicateError");
								break;
							}
						}
					}
	    		}
    		}else {
	    		//if(!StringUtils.isBlank(document_name) || !StringUtils.isBlank(document_type_id) || !StringUtils.isBlank(file)){
    			if(!StringUtils.isBlank(document_name) || !StringUtils.isBlank(document_type_id) && !ctx.get(Constants.INET_PAGE).toString().equals("addIDPDetails")){
		    		if(StringUtils.isBlank(document_name)){
		    			DataUtils.populateError((Context) ctx, "document_name", "documentNameErrorKey");
		    			isErrorFound = true;
		    		}

		    		if(StringUtils.isBlank(document_type_id)){
		    			DataUtils.populateError((Context) ctx, "document_type_id", "documentTypeErrorKey");
		    			isErrorFound = true;
		    		}

		    		if(StringUtils.isBlank(file)){
		    			DataUtils.populateError((Context) ctx, "file", "documentErrorKey");
		    			isErrorFound = true;
		    		}

		    		if(isErrorFound)
		    			return null;

		    		List documentList = null;
		    		if(ctx.get("uploadDocument_list_1") != null && !HtmlConstants.EMPTY.equals("uploadDocument_list_1")){
		    			documentList = (List) ctx.get("uploadDocument_list_1");
		    		}

		    		if(documentList != null && documentList.size() > 0){
		    			for(int i=0; i<documentList.size(); i++){
		    				Map rowMap = (Map)documentList.get(i);

		    				//going to check for duplicacy for document_name
							if(rowMap!=null && rowMap.get("document_name")!=null && !HtmlConstants.EMPTY.equals(rowMap.get("document_name").toString())
									&& ctx.get("document_name")!=null && !HtmlConstants.EMPTY.equals(ctx.get("document_name").toString())){
								String documentName = rowMap.get("document_name").toString();
								String documentNameCtx = ctx.get("document_name").toString();
								if(documentName.equalsIgnoreCase(documentNameCtx)){
									DataUtils.populateError((Context) ctx, "documentName", "adminDuplicateError");
									break;
								}
							}

							//going to check for duplicacy for upload file name
							if(rowMap != null && rowMap.get("actual_file_name") != null && !HtmlConstants.EMPTY.equals(rowMap.get("actual_file_name").toString())
									&& file_name != null && !HtmlConstants.EMPTY.equals(file_name)){

								String documentName = rowMap.get("actual_file_name").toString();
								if(file_name.contains("\\"))
									file_name = file_name.substring(file_name.lastIndexOf("\\")+1, file_name.length());

								String documentNameCtx = file_name;
								if(documentName.equalsIgnoreCase(documentNameCtx)){
									DataUtils.populateError((Context) ctx, "documentName", "adminDuplicateError");
									break;
								}
							}
						}
		    		}
	    		}
    		}
    		//checkMandatoryDocumentUpload(ctx);
    		return null;
    	}catch(Exception ex){
    		logger.error(ex.getMessage());
    		return null;
    	}
	}
	public static void generateDocumentUploadXML(Context ctx,String outputXMLName) throws Exception{
    	try {
    		String listName = null;
    		listName = "uploadDocument_list_1";
		if(listName != null && !HtmlConstants.EMPTY.equals(listName))
			ProducerOneUtils.convertListDataToXML(ctx, listName, outputXMLName);

	}catch (Exception e) {
			logger.error("Unable to generate Document Upload  xml due to error : " + e.getMessage());
		}
	}
	public static void encryptString(Context ctx,String npn) throws Exception{
		try {
			if(npn == null || HtmlConstants.EMPTY.equals(npn))
				return;
			ctx.put("npn", npn);
			new SetParametersForStoredProcedures().setParametersInContext(ctx, "npn");
			List feinSSNList = (List) SqlResources.getSqlMapProcessor(ctx).select("person.GetListofFEINByNPN_p",ctx);
			if(feinSSNList != null && feinSSNList.size()>0){
				for(int i=0;i<feinSSNList.size();i++){
					String NPN = null;
					String appointmentInformationId = null;
					String FEIN = null;
					String encryptedFEIN = null;
					Object obj= null;
					Map listMap = (Map)feinSSNList.get(i);
					if(listMap != null && !listMap.isEmpty()){
						if(listMap.get("FEIN") != null && !HtmlConstants.EMPTY.equals(listMap.get("FEIN"))){
							//obj = listMap.get("FEIN");
							//if(obj instanceof Integer){
								FEIN = listMap.get("FEIN").toString();
								NPN = listMap.get("NPN").toString();
								appointmentInformationId = listMap.get("appointment_information_details_id").toString();

								if(FEIN != null && !HtmlConstants.EMPTY.equals(FEIN)){
									encryptedFEIN = ProducerOneUtils.encryptString(FEIN,SafeNetCodes.SSE.getCode(),ctx);
									ctx.put("npn", NPN);
									ctx.put("FEIN", encryptedFEIN);
									ctx.put("appointment_information_details_id", appointmentInformationId);
									new SetParametersForStoredProcedures().setParametersInContext(ctx, "appointment_information_details_id,npn,FEIN");
									SqlResources.getSqlMapProcessor(ctx).update("person.UpdateAppointmentInformationDetailsFEINSSN_p",ctx);
								}
							//}
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error("Error in  encrypt FEIN/SSN Value : "+e.getMessage());
		}
	}

	//Method created to reload applicationworkflow xml and update in Cache
    public static Object reloadApplicationWorkflowConfigurationXML(Context ctx) throws Exception{
        try{
        	if(logger.isDebugEnabled(ctx))
        		logger.debug(ctx, "Going to reload applicationworkflow XML");
            String rulesPath = SystemProperties.getInstance().getString("xml.basedir") + ctx.getProject() + "//applicationworkflownew.xml";
            ResourceLoader.load(null, rulesPath, "applicationworkflownew", ctx.getProject());
            if(logger.isDebugEnabled(ctx))
            	logger.debug(ctx, "Application Workflow XML reloaded successfully");
        }catch (Exception e) {
            logger.error("Unable to reload applicationworkflow xml due to error : " + e.getMessage());
        }

        return null;
    }
    public static void  generateProductValidationXML(Context ctx) throws Exception {
		StringBuilder xmlString = null;
		List processedList = null;
		if(ctx.get("productValidationDetail_list_1") != null && !HtmlConstants.EMPTY.equals(ctx.get("productValidationDetail_list_1"))){
			processedList = (List) ctx.get("productValidationDetail_list_1");
			}
			if(processedList != null && processedList.size() > 0){
				xmlString = new StringBuilder("<Root>");
				for(int i=0; i<processedList.size(); i++){
					xmlString.append("<Root1>");
					Map map = (HashMap) processedList.get(i);

					if(ctx.get("is_included_"+i) != null && !HtmlConstants.EMPTY.equals(ctx.get("is_included_"+i)))
						xmlString.append("<is_included>").append(ctx.get("is_included_"+i)).append("</is_included>");
					else
						xmlString.append("<is_included>N</is_included>");


					if(map.get("product_validation_id") != null && !HtmlConstants.EMPTY.equals(map.get("product_validation_id")))
						xmlString.append("<product_validation_id>").append(map.get("product_validation_id")).append("</product_validation_id>");
					else
						xmlString.append("<product_validation_id/>");


					if(ctx.get("effective_date_"+i) != null && !HtmlConstants.EMPTY.equals(ctx.get("effective_date_"+i)))
						xmlString.append("<effective_date>").append(ctx.get("effective_date_"+i)).append("</effective_date>");
					else
						xmlString.append("<effective_date/>");


					if(ctx.get("expire_new_date_"+i) != null && !HtmlConstants.EMPTY.equals(ctx.get("expire_new_date_"+i)))
						xmlString.append("<expire_new_date>").append(ctx.get("expire_new_date_"+i)).append("</expire_new_date>");
					else
						xmlString.append("<expire_new_date/>");

					if(ctx.get("expire_renew_date_"+i) != null && !HtmlConstants.EMPTY.equals(ctx.get("expire_renew_date_"+i)))
						xmlString.append("<expire_renew_date>").append(ctx.get("expire_renew_date_"+i)).append("</expire_renew_date>");
					else
						xmlString.append("<expire_renew_date/>");


					xmlString.append("</Root1>");
				}
				xmlString.append("</Root>");
				}

			if(xmlString != null && !HtmlConstants.EMPTY.equals(xmlString) && !xmlString.toString().equals("<Root></Root>")){
				ctx.put("validations", xmlString);
				ctx.put("last_updated_by", ctx.get("last_updated_by"));
				new SetParametersForStoredProcedures().setParametersInContext(ctx,"validations,last_updated_by");
				SqlResources.getSqlMapProcessor(ctx).update("producer_org_mapping.UpdateProductValdiations_p", ctx);
			}
	}

    public static void  checkPropValidationForSubTasks(Context ctx) throws Exception {

    	new SetParametersForStoredProcedures().setParametersInContext(ctx,"status");
    	Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("agency_document_attachments.getSelectedStatusDesc_p", ctx);



		if(obj != null && obj instanceof Map){
            Map map1 = (Map)obj;
            ctx.putAll(map1);
        }
    	if (SecurityResources.getInstance(ctx).getAccessType("subTask_section_security", (Context) ctx) == SecurityResources.SHOW) {
    		HttpSession sess = ((HttpServletRequest)ctx.get(HtmlConstants.DOCUMENTREQUEST)).getSession();
    		if(sess.getAttribute("subTask_list_01") != null && !HtmlConstants.EMPTY.equals(sess.getAttribute("subTask_list_01"))){
    			List subTaskList = (List)sess.getAttribute("subTask_list_01");

    			if(subTaskList != null && subTaskList.size() > 0){
    				for(int i=0;i<subTaskList.size();i++){
    					if(ctx.get("subTask_assigned_to_user_"+i) == null || HtmlConstants.EMPTY.equals(ctx.get("subTask_assigned_to_user_"+i).toString())){
    						DataUtils.populateError(ctx, "subTaskError", MessageResources.getInstance(ctx).getMessage("subTaskUserNotSelectedErrorKey").getMessage());
    						return;
    					}
    				}

    			}
    		}
    	}
    }
    public static void validateTerminatedAgentNumber(Context ctx) throws Exception {
    	try {
			if(ctx.get("terminated_agent_number") != null && !HtmlConstants.EMPTY.equals(ctx.get("terminated_agent_number"))){
				String terminatedAgentNumber = ctx.get("terminated_agent_number").toString();
				SqlResources.getSqlMapProcessor(ctx).select("agent_group_insurance_details.checkAgentNumberExist_p", ctx);

				if(ctx.get("returnVal")!=null && !HtmlConstants.EMPTY.equals(ctx.get("returnVal"))){
					int returnVal = 0;
					returnVal = Integer.parseInt(ctx.get("returnVal").toString());
					if(returnVal !=1){
						DataUtils.populateError(ctx, "terminated_agent_number", "agentNumberDoesnotExistErrorKey");
						return;
					}
				}
			}

			if(ctx.get("terminated_agent_name") != null && !HtmlConstants.EMPTY.equals(ctx.get("terminated_agent_name"))){
				ctx.put("NAME", ctx.get("terminated_agent_name"));
				Map map = (Map)SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.enrollmentrefreshAgentNumbergetAgentNumberByAgentName", ctx);

				if(map == null || map.get("terminated_agent_number") == null && map.get("terminated_agent_number").toString().equals(HtmlConstants.EMPTY)){
					DataUtils.populateError(ctx, "terminated_agent_name", "agentNameDoesnotExistErrorKey");
					return;
				}
			}

		if(ctx.get("estimated_annual_earning") != null && !HtmlConstants.EMPTY.equals(ctx.get("estimated_annual_earning")))
			ctx.put("estimated_annual_earning", Double.parseDouble((DataUtils.removeAmountFormat(ctx.get("estimated_annual_earning")).toString())));
		else
			ctx.put("estimated_annual_earning", null);

		} catch (Exception e) {
			logger.error("Exception in validate Terminated Agent Number "+e.getMessage());
		}
    }

    /**
     * This method is used as helping method to get the Data From BIG XML
     * @param ctx
     * @param object_id
     * @param object_type
     *
     * @throws Exception
     */
    public static String getPopulatedDataXml(Context ctx, int object_id,String object_type) throws Exception {
    	String dataXML = "";
    	String compliationPath = "";
    	try{
    		if(!HtmlConstants.EMPTY.equals(object_id) &&
        			object_type != null && !HtmlConstants.EMPTY.equals(object_type)){

        		if(object_type.equals("Contract")){
        			compliationPath = "Agencies/Agency/Contracts/Contract[contract_id='"+object_id+"']";
            	}else if(object_type.equalsIgnoreCase("AgencyTasks")){
        			compliationPath = "Agencies/Agency/AgencyTaskDetails/AgencyTask[agency_task_id='"+object_id+"']";
        		}else if(object_type.equalsIgnoreCase("AgentSelfService")){
        			compliationPath = "Agencies/Agency/Requests/person_ss[person_SS_id='"+object_id+"']";
        		}else if(object_type.equalsIgnoreCase("AgencyFunction")){
        			compliationPath = "Agencies/Agency/AgencyFunctions/AgencyFunction";
        		}


        		new SetParametersForStoredProcedures().setParametersInContext(ctx, "agency_id");
        		Map map = (Map)SqlResources.getSqlMapProcessor(ctx).findByKey("agency_master.getNationalCodeByAgencyId_p", ctx);

        		if(map != null && map.get("obj_code") != null && !HtmlConstants.EMPTY.equals(map.get("obj_code").toString())){
        			ctx.put("object_code",map.get("obj_code").toString());
        		}


        			//FIXME Add prolog in StoredProcedure
        			String bigXmlResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + getBigXmlData(ctx);

         			if(bigXmlResult != null && !HtmlConstants.EMPTY.equals(bigXmlResult)
        					&& (compliationPath != null && !HtmlConstants.EMPTY.equals(compliationPath))){
        					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        			        factory.setNamespaceAware(true);
        			        DocumentBuilder builder;
        			        org.w3c.dom.Document doc = null;

        			            builder = factory.newDocumentBuilder();

        			            doc = builder.parse(new ByteArrayInputStream(bigXmlResult.getBytes()));

        			            XPathFactory xpathFactory = XPathFactory.newInstance();
        			            XPath xpath = xpathFactory.newXPath();
        			            XPathExpression expr =  xpath.compile(compliationPath);


        			            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        			            org.w3c.dom.Document newXmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

        			            Node node = nodes.item(0);
        			            Node copyNode = newXmlDocument.importNode(node, true);
        			            newXmlDocument.appendChild(copyNode);

        			            DOMImplementationLS domImplementationLS =
        			                    (DOMImplementationLS) newXmlDocument.getImplementation();
        			                LSSerializer lsSerializer =
        			                    domImplementationLS.createLSSerializer();
        			                dataXML = lsSerializer.writeToString(newXmlDocument);


        			}
        			else{
        				dataXML =  getBigXmlData(ctx);
        			}



        	}
    	}catch(Exception e){
    		if(logger.isDebugEnabled(ctx))
    			logger.debug(ctx, "Exception While fetching data for "+object_type+"  : Method Name - getPopulatedDataXml , Message -"  + e.getMessage());
    	}


    	return dataXML;
    }



    /**
     *
     * This method is used to get Big XML using Web Service. But for now we are simply using StoredProcedure Call
     *
     * FIXME This method is used to get Big XML using Web Service. But for now we are simply using StoredProcedure Call
     *
     * @param ctx
     * @return
     * @throws Exception
     */

    public static String getBigXmlData(Context ctx) throws Exception{
    	String bigXmlResult= "";
    	try{
    		if(ctx.get("includeLicenseAppointments") == null || HtmlConstants.EMPTY.equals(ctx.get("includeLicenseAppointments").toString())){
    			ctx.put("includeLicenseAppointments","0");
        	}


    		new SetParametersForStoredProcedures().setParametersInContext(ctx, "includeLicenseAppointments,agency_id,object_code,bigDataOutputFormat");
    		Map map = (Map)SqlResources.getSqlMapProcessor(ctx).findByKey("agency_master.usp_GetAgencyDetailsByNationalNumber", ctx);
    		new WorkflowImpl().populateClobValue(map);
    		if(map != null && map.get("Result") != null && !HtmlConstants.EMPTY.equals(map.get("Result").toString())){
    			bigXmlResult = map.get("Result").toString();
    		}


    	}catch(Exception e){
    		if(logger.isDebugEnabled(ctx))
    			logger.debug(ctx, "Exception While fetching data from Big XML : " + e.getMessage());

    	}


    	return bigXmlResult;

    }
  //Method created to create policy transfer template
    public static void createTransfersTemplateUrl(Context ctx) throws Exception{
    	String contextUrl = SystemProperties.getInstance().getString("context.url");
    	contextUrl = contextUrl + File.separator + "LifeTransferForm.dot";

    	ctx.put("templateUrl", contextUrl);
    }

    public static Object getClaimPolicyDetailList(Context ctx)
    {
        try {
            Context localContext = new Context();
            if(ctx.get("agent_number") != null && !ctx.get("agent_number").toString().equals(HtmlConstants.EMPTY)){
                localContext.putAll(ctx);
                localContext.setProject("ProducerOnePerformance");
                new SetParametersForStoredProcedures().setParametersInContext(localContext, "PAGE_SIZE,PAGE_NUMBER,flag_for_pagination,policy_number,Source_System_ID,Policy_Effective_Date,Product_ID,Premium_State_ID,Company_ID,Account_ID,Commissionable_Producer_ID,Lob_ID,Policy_Create_Date,Policy_Expiration_Date,agent_number,family_number,zipcode,insured_name,agency_id,Accounting_Month,output_type");
                List agentLicenseDetailList = SqlResources.getSqlMapProcessor(localContext).select("f_Policy_Transaction.GetPolicylevelList_p", localContext);
                if(agentLicenseDetailList != null && agentLicenseDetailList.size() > 0){
                    ctx.put("agency_claim_policy_list_01", agentLicenseDetailList);
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        	logger.error(ctx, "Unable to execute getClaimPolicyDetailList method due to error : " + DataUtils.getExceptionStackTrace(e));
        }
        return null;
    }

    public static Object getPolicyTransactionDetailList(Context ctx)
    {
        try {
            Context localContext = new Context();

            //if(ctx.get("agent_number") != null && !ctx.get("agent_number").toString().equals(HtmlConstants.EMPTY)){
                localContext.putAll(ctx);
                localContext.setProject("ProducerOnePerformance");
                new SetParametersForStoredProcedures().setParametersInContext(localContext, "Policy_ID,Accounting_Month");

                List agentLicenseDetailList = SqlResources.getSqlMapProcessor(localContext).select("f_Policy_Transaction.GetPolicytransList_dropdown_p", localContext);
                if(agentLicenseDetailList != null && agentLicenseDetailList.size() > 0){
                    ctx.put("agency_policy_list_01", agentLicenseDetailList);
                }

        } catch (Exception e) {
            // TODO Auto-generated catch block
           // e.printStackTrace();
        	  logger.error(ctx, "Unable to execute getPolicyTransactionDetailList method due to error : " + DataUtils.getExceptionStackTrace(e));
        }
        return null;
    }

    public static Object getBillingTransactionDetailList(Context ctx)
    {
        try {
            Context localContext = new Context();

            //if(ctx.get("agent_number") != null && !ctx.get("agent_number").toString().equals(HtmlConstants.EMPTY)){
                localContext.putAll(ctx);
                localContext.setProject("ProducerOnePerformance");
                new SetParametersForStoredProcedures().setParametersInContext(localContext, "Policy_ID,Accounting_Month");
                List agentLicenseDetailList = SqlResources.getSqlMapProcessor(localContext).select("f_Policy_Transaction.GetPolicybillingList_dropdown_p", localContext);
                if(agentLicenseDetailList != null && agentLicenseDetailList.size() > 0){
                    ctx.put("agency_billing_list_01", agentLicenseDetailList);
                }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        	  logger.error(ctx, "Unable to execute getBillingTransactionDetailList method due to error : " + DataUtils.getExceptionStackTrace(e));
        }
        return null;
    }

    public static Object getReinsuranceTransactionDetailList(Context ctx)
    {
        try {
            Context localContext = new Context();

            //if(ctx.get("agent_number") != null && !ctx.get("agent_number").toString().equals(HtmlConstants.EMPTY)){
                localContext.putAll(ctx);
                localContext.setProject("ProducerOnePerformance");
                new SetParametersForStoredProcedures().setParametersInContext(localContext, "Policy_ID,Accounting_Month");
                List agentLicenseDetailList = SqlResources.getSqlMapProcessor(localContext).select("f_Policy_Transaction.GetPolicyreinsList_dropdown_p", localContext);
                if(agentLicenseDetailList != null && agentLicenseDetailList.size() > 0){
                    ctx.put("agency_reinsurance_list_01", agentLicenseDetailList);
                }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        	  logger.error(ctx, "Unable to execute getReinsuranceTransactionDetailList method due to error : " + DataUtils.getExceptionStackTrace(e));
        }
        return null;
    }


    public static Object validateAttachContractTemplate(Context ctx){

    	try {

    		if(ctx.get("uploadPopup_list_1") != null && !HtmlConstants.EMPTY.equals(ctx.get("uploadPopup_list_1"))){

    			List docsList = (List)ctx.get("uploadPopup_list_1");

    			if(docsList != null && docsList.size()>1){
    				DataUtils.populateError(ctx, "contractTemplateAttachmentError", MessageResources.getInstance(ctx).getMessage("onlyOneContractTemplateCanBeAttached").getMessage());
    				return null;
    			}

    			for(int i=0; i<docsList.size(); i++){
    				Map rowMap = (Map)docsList.get(i);
    				if(rowMap != null ){
    					if(rowMap.get("document_name") != null && !HtmlConstants.EMPTY.equals(rowMap.get("document_name"))){
    						String fileName = rowMap.get("document_name").toString();
    						String extn = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
    						/*if(!extn.equals("xsl")){
    							DataUtils.populateError(ctx, "contractTemplateAttachmentError", MessageResources.getInstance(ctx).getMessage("onlyXSLContractTemplateCanBeAttached").getMessage());
    		    				return null;
    						}*/
    					}
    				}
    			}
    			Context newCtx = new Context();
    			newCtx.setProject(ctx.getProject());
    			for(int i=0; i<docsList.size(); i++){
    				Map rowMap = (Map)docsList.get(i);
    				if(rowMap != null ){
    					if(rowMap.get("document_name") != null && !HtmlConstants.EMPTY.equals(rowMap.get("document_name"))){
    						newCtx.put("document_name",rowMap.get("document_name"));
    						newCtx.put("contract_Template_Type_id",ctx.get("contract_Template_Type_id"));

    						new SetParametersForStoredProcedures().setParametersInContext(newCtx, "contract_Template_Type_id,document_name");

    						Object obj = SqlResources.getSqlMapProcessor(newCtx).findByKey("agency_document_attachments.validateAttachedContractTemplate_p", newCtx);

	            			if(obj != null && obj instanceof Map){
	            				 Map map = (Map)obj;
	            				 if(map != null && map.get("is_error_found") != null && "Y".equals(map.get("is_error_found"))){
	            					 DataUtils.populateError(ctx, "contractTemplateAttachmentError", MessageResources.getInstance(ctx).getMessage(map.get("errorMessage").toString()).getMessage());
	     		    				return null;
	            				 }
	            			}
    					}
    				}

    			}




    		}



    	}catch(Exception e){
    		//e.printStackTrace();

    		  logger.error(ctx, "Unable to execute validateAttachContractTemplate method due to error : " + DataUtils.getExceptionStackTrace(e));

    	}


    	return null;
    }
    public static Object getAgencyFeeDetailList(Context ctx)
    {
        try {
            Context localContext = new Context();
            localContext.putAll(ctx);
                localContext.setProject("ProducerOnePerformance");
                //new SetParametersForStoredProcedures().setParametersInContext(localContext, "Policy_ID,Accounting_Month");
                List agencyChargesList = SqlResources.getSqlMapProcessor(localContext).select("f_Policy_Transaction.GetAgencyChargesList_dropdown_p", localContext);
                if(agencyChargesList != null && agencyChargesList.size() > 0){
                    ctx.put("agency_fees_list_01", agencyChargesList);
                }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        	  logger.error(ctx, "Unable to execute getAgencyFeeDetailList method due to error : " + DataUtils.getExceptionStackTrace(e));
        }
        return null;
    }

    public static Object getPolicytranscoverageList(Context ctx)
    {
        try {
            Context localContext = new Context();
            localContext.putAll(ctx);
                localContext.setProject("ProducerOnePerformance");
                new SetParametersForStoredProcedures().setParametersInContext(localContext, "PAGE_SIZE,PAGE_NUMBER,flag_for_pagination,Policy_Transaction_ID,policy_number,accounting_month,producer_number,producer_number_id,output_type");
                List agencyChargesList = SqlResources.getSqlMapProcessor(localContext).select("f_Policy_Transaction.GetPolicytranscovList_dropdown_p", localContext);
                if(agencyChargesList != null && agencyChargesList.size() > 0){
                    ctx.put("policytranscoverage_list_01", agencyChargesList);
                }
        } catch (Exception e) {
            // TODO Auto-generated catch block
           // e.printStackTrace();
        	  logger.error(ctx, "Unable to execute getPolicytranscoverageList method due to error : " + DataUtils.getExceptionStackTrace(e));
        }
        return null;
    }



    public static Object getClaimListList(Context ctx)
    {
        try {
            Context localContext = new Context();
            localContext.putAll(ctx);
                localContext.setProject("ProducerOnePerformance");
                new SetParametersForStoredProcedures().setParametersInContext(localContext, "policy_id,policy_number,Policy_ID,Accounting_Month");
                List claimList = SqlResources.getSqlMapProcessor(localContext).select("f_Policy_Transaction.GetClaimList_dropdown_p", localContext);
                if(claimList != null && claimList.size() > 0){
                    ctx.put("agency_claim_list_01", claimList);
                }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        	  logger.error(ctx, "Unable to execute getClaimListList method due to error : " + DataUtils.getExceptionStackTrace(e));
        }
        return null;
    }

    public static Object getAgencyClaimItemsList(Context ctx)
    {
        try {
            Context localContext = new Context();
            localContext.putAll(ctx);
                localContext.setProject("ProducerOnePerformance");
                new SetParametersForStoredProcedures().setParametersInContext(localContext, "policy_id,policy_number,Policy_ID,accounting_month");
                List claimItemsList = SqlResources.getSqlMapProcessor(localContext).select("f_Policy_Transaction.getPolicyTransactionItembypolicy_P", localContext);
                if(claimItemsList != null && claimItemsList.size() > 0){
                    ctx.put("agency_claimItems_list_01", claimItemsList);
                }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        	  logger.error(ctx, "Unable to execute getAgencyClaimItemsList method due to error : " + DataUtils.getExceptionStackTrace(e));
        }
        return null;
    }
    public static Object getCoverageCodeDescbyPolicy(Context ctx)
    {
        try {
            Context localContext = new Context();
            localContext.putAll(ctx);
                localContext.setProject("ProducerOnePerformance");
                new SetParametersForStoredProcedures().setParametersInContext(localContext, "policy_id,policy_number,Policy_ID,accounting_month");
                List coverageCodeDescByPolicyList = SqlResources.getSqlMapProcessor(localContext).select("f_Policy_Transaction.getCoverageCodeDescbyPolicy_p", localContext);
                if(coverageCodeDescByPolicyList != null && coverageCodeDescByPolicyList.size() > 0){
                    ctx.put("agency_coverageCodeDescByPolicy_list_01", coverageCodeDescByPolicyList);
                }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        	  logger.error(ctx, "Unable to execute getCoverageCodeDescbyPolicy method due to error : " + DataUtils.getExceptionStackTrace(e));
        }
        return null;
    }
    public static Object getSummaryByPolicyId(Context ctx)
    {
        try {
            Context localContext = new Context();
            List policyTransactionDetailsList = null;
            List policyCoverageDetailsList = null;
            List policyBillingDetailsList = null;
            List policyReinsuranceDetailsList = null;
            List claimDetailList = null;
            localContext.putAll(ctx);
                localContext.setProject("ProducerOnePerformance");
                new SetParametersForStoredProcedures().setParametersInContext(localContext, "PAGE_SIZE,PAGE_NUMBER,flag_for_pagination,policy_number,Policy_ID,Accounting_Month");
                List coverageCodeDescByPolicyList = SqlResources.getSqlMapProcessor(localContext).select("f_Policy_Transaction.getpolicysummarybypolicyid_p", localContext);
                if(coverageCodeDescByPolicyList != null && coverageCodeDescByPolicyList.size() > 0){
                    //ctx.put("policysummarybypolicy_list", coverageCodeDescByPolicyList);
                	if(coverageCodeDescByPolicyList != null && coverageCodeDescByPolicyList.size() >0){
                		policyTransactionDetailsList = (List)coverageCodeDescByPolicyList.get(0);
                		policyCoverageDetailsList = (List)coverageCodeDescByPolicyList.get(1);
                		policyBillingDetailsList = (List) coverageCodeDescByPolicyList.get(2);
                		policyReinsuranceDetailsList = (List) coverageCodeDescByPolicyList.get(3);
                		claimDetailList = (List) coverageCodeDescByPolicyList.get(4);

                		if(policyTransactionDetailsList != null && policyTransactionDetailsList.size() >0)
                			ctx.put("policysummarybypolicy_list_0_0", policyTransactionDetailsList);
                		else
                			ctx.put("policysummarybypolicy_list_0_0", null);
                		if(policyCoverageDetailsList != null && policyCoverageDetailsList.size() >0)
                			ctx.put("policysummarybypolicy_list_0_1", policyCoverageDetailsList);
                		else
                			ctx.put("policysummarybypolicy_list_0_1", null);
                		if(policyBillingDetailsList != null && policyBillingDetailsList.size()>0)
                			ctx.put("policysummarybypolicy_list_0_2", policyBillingDetailsList);
                		else
                			ctx.put("policysummarybypolicy_list_0_2", null);
                		if(policyReinsuranceDetailsList != null && policyReinsuranceDetailsList.size()>0)
                			ctx.put("policysummarybypolicy_list_0_3", policyReinsuranceDetailsList);
                		else
                			ctx.put("policysummarybypolicy_list_0_3", null);
                		if(claimDetailList != null && claimDetailList.size()>0)
                			ctx.put("policysummarybypolicy_list_0_4", claimDetailList);
                		else
                			ctx.put("policysummarybypolicy_list_0_4", null);

                	}
                }
        } catch (Exception e) {
        	logger.error("Unable to get get Summary By Policy Id due to error : " + e.getMessage());
        }
        return null;
    }

    //Method created to validate rule expression
  	/*public static void validateRuleExpression(Context ctx) throws Exception{
  		try{
  			if(ctx.get("ruleexpression") != null){
  				RuleImpl ruleImpl = new RuleImpl();
  				ruleImpl.setId("XYZ");
  				ruleImpl.setExpression(ctx.get("ruleexpression").toString());
  				if(ruleImpl.execute(ctx, null) == null){
  					DataUtils.populateError(ctx, "ruleexpression", "invalidRuleErrorKey");
  					return;
  				}

  				ctx.put("ruleexpression_message", DataUtils.getMessage(ctx, "validRuleMsgKey"));
  			}
  		}catch(Exception e){
  			DataUtils.populateError(ctx, "ruleexpression", "invalidRuleErrorKey");
  			return;
  		}
  	}*/

  //Method created to add master lookup object data to session list
  	public static void addDeleteMasterLookupObjectDataToList(Context ctx) throws Exception{
  		try{
  			List sessionList = new ArrayList();

  			if(ctx.get("addMasterLookupObject_list_listfreeform_1") != null)
  				sessionList = (List)ctx.get("addMasterLookupObject_list_listfreeform_1");

  			if(ctx.get(HtmlConstants.INET_METHOD) != null && ctx.get(HtmlConstants.INET_METHOD).toString().equals("deleteValue")){
  				if(ctx.get("rownum") != null && !ctx.get("rownum").toString().equals(HtmlConstants.EMPTY)){
  					List finalList = new ArrayList();

  					for(int i=0; i<sessionList.size(); i++){
  						Map map = (Map)sessionList.get(i);

  						if(map.get("rownum") != null && !map.get("rownum").toString().equals(HtmlConstants.EMPTY) &&
  								Integer.parseInt(map.get("rownum").toString()) == Integer.parseInt(ctx.get("rownum").toString())){

  							if(map.get("isNew") != null && map.get("isNew").toString().equals("Y")){
  								//do nothing
  							}else{
  								new SetParametersForStoredProcedures().setParametersInContext(ctx, "master_lookup_data_id");
  								SqlResources.getSqlMapProcessor(ctx).delete("SqlStmts.sqlStatementsviewdeleteMasterLookupObjectDataById", ctx);
  							}

  							continue;
  						}

  						finalList.add(map);
  					}

  					ctx.put("addMasterLookupObject_list_listfreeform_1", finalList);
  				}

  				return;
  			}

  			if(ctx.get("master_lookup_data_desc") == null || ctx.get("master_lookup_data_desc").toString().equals(HtmlConstants.EMPTY)){
  				DataUtils.populateError(ctx, "master_lookup_data_desc", "Please enter Description");
  				return;
  			}

  			Map map = new HashMap();
  			map.put("isNew", "Y");
  			map.put("rownum", sessionList.size()+1);
  			map.put("master_lookup_data_code", ctx.get("master_lookup_data_code"));
  			map.put("master_lookup_data_desc", ctx.get("master_lookup_data_desc"));
  			map.put("is_applicable", ctx.get("is_applicable"));
  			map.put("parent_master_lookup_detail_id", ctx.get("parent_master_lookup_detail_id"));
  			map.put("parent_master_lookup_data_id", ctx.get("parent_master_lookup_data_id"));
  			map.put("effective_date", ctx.get("effective_date"));
  			map.put("expiration_date", ctx.get("expiration_date"));

  			sessionList.add(map);

  			ctx.put("addMasterLookupObject_list_listfreeform_1", sessionList);
  		}catch(Exception e){
  			logger.error("Unable to add master lookpup object data to list due to error : " + e.getMessage());
  		}
  	}

  	//Method created to add object hierarchy mapping data to session list
  	public static void addDeleteObjectHierarchyDataToList(Context ctx) throws Exception{
  		try{
  			List sessionList = new ArrayList();

  			if(ctx.get("addObjectHierarchyMapping_list_listfreeform_1") != null)
  				sessionList = (List)ctx.get("addObjectHierarchyMapping_list_listfreeform_1");

  			if(ctx.get(HtmlConstants.INET_METHOD) != null && ctx.get(HtmlConstants.INET_METHOD).toString().equals("deleteValue")){
  				if(ctx.get("rownum") != null && !ctx.get("rownum").toString().equals(HtmlConstants.EMPTY)){
  					List finalList = new ArrayList();

  					for(int i=0; i<sessionList.size(); i++){
  						Map map = (Map)sessionList.get(i);

  						if(map.get("rownum") != null && !map.get("rownum").toString().equals(HtmlConstants.EMPTY) &&
  								Integer.parseInt(map.get("rownum").toString()) == Integer.parseInt(ctx.get("rownum").toString())){

  							if(map.get("isNew") != null && map.get("isNew").toString().equals("Y")){
  								//do nothing
  							}else{
  								new SetParametersForStoredProcedures().setParametersInContext(ctx, "html_extendedfields_objects_mapping_id");
  								SqlResources.getSqlMapProcessor(ctx).delete("SqlStmts.sqlStatementsviewdeleteObjectHierarchyMappingDataById", ctx);
  							}

  							continue;
  						}

  						finalList.add(map);
  					}

  					ctx.put("addObjectHierarchyMapping_list_listfreeform_1", finalList);
  				}

  				return;
  			}

  			boolean isErrorFound = false;

  			Map map = new HashMap();
  			map.put("isNew", "Y");
  			map.put("rownum", sessionList.size()+1);
  			map.put("module_list", ctx.get("module"));
  			map.put("metaobject_list", ctx.get("metaobject"));
  			map.put("metaobject_field_id_list", ctx.get("metaobject_field_id"));
  			sessionList.add(map);

  			ctx.put("addObjectHierarchyMapping_list_listfreeform_1", sessionList);
  		}catch(Exception e){
  			logger.error("Unable to add object hierarchy data to list due to error : " + e.getMessage());
  		}
  	}

  	//Method created to validate object hierarchy mapping data to session list
  	public static void validateObjectHierarchyDataToList(Context ctx) throws Exception{
  		try{
  			boolean isErrorFound = false;

  			if(ctx.get(HtmlConstants.INET_METHOD) != null && !ctx.get(HtmlConstants.INET_METHOD).toString().equals("update")){
  				if(ctx.get("module") == null || ctx.get("module").toString().equals(HtmlConstants.EMPTY)){
  					DataUtils.populateError(ctx, "module", "Please select Module");
  					isErrorFound = true;
  				}

  				if(ctx.get("metaobject") == null || ctx.get("metaobject").toString().equals(HtmlConstants.EMPTY)){
  					DataUtils.populateError(ctx, "metaobject", "Please select Metaobject");
  					isErrorFound = true;
  				}

  				/*if(ctx.get("metaobject_field_id") == null || ctx.get("metaobject_field_id").toString().equals(HtmlConstants.EMPTY)){
  					DataUtils.populateError(ctx, "metaobject_field_id", "Please select Metaobject Field");
  					isErrorFound = true;
  				}*/

  				if(isErrorFound)
  					return;
  			}

  			List sessionList = new ArrayList();

  			if(ctx.get("addObjectHierarchyMapping_list_listfreeform_1") != null)
  				sessionList = (List)ctx.get("addObjectHierarchyMapping_list_listfreeform_1");

  			for(int i=0; i<sessionList.size(); i++){
  				Map map = (Map)sessionList.get(i);

  				if(ctx.get("module_list_"+i) == null || ctx.get("module_list_"+i).toString().equals(HtmlConstants.EMPTY))
  					isErrorFound = true;

  				if(ctx.get("metaobject_list_"+i) == null || ctx.get("metaobject_list_"+i).toString().equals(HtmlConstants.EMPTY))
  					isErrorFound = true;

  				/*if(ctx.get("metaobject_field_id_list_"+i) == null || ctx.get("metaobject_field_id_list_"+i).toString().equals(HtmlConstants.EMPTY))
  					isErrorFound = true;*/

  				if(isErrorFound)
  					break;
  			}

  			if(isErrorFound){
  				DataUtils.populateError(ctx, "pageError2", DataUtils.getMessage(ctx, "fillRequiredErrorKey"));
  				return;
  			}

  			isErrorFound = false;

  			//checking for duplicate values in list -- 1099TypeRowCount
  			for(int i=0; i<sessionList.size(); i++){
  				Map map = (Map)sessionList.get(i);

  				for(int j=i+1; j<sessionList.size(); j++){
  					//Map nextMap = (Map)sessionList.get(j);

  					if((ctx.get("module_list_"+i) != null && ctx.get("module_list_"+j) != null && ctx.get("module_list_"+i).toString().equals(ctx.get("module_list_"+j).toString()))
  						&& (ctx.get("metaobject_list_"+i) != null && ctx.get("metaobject_list_"+j) != null && ctx.get("metaobject_list_"+i).toString().equals(ctx.get("metaobject_list_"+j).toString()))
  						&& (ctx.get("metaobject_field_id_list_"+i) != null && ctx.get("metaobject_field_id_list_"+j) != null && ctx.get("metaobject_field_id_list_"+i).toString().equals(ctx.get("metaobject_field_id_list_"+j).toString()))){
  						isErrorFound = true;
  					}
  				}

  				if(ctx.get(HtmlConstants.INET_METHOD) != null && !ctx.get(HtmlConstants.INET_METHOD).toString().equals("update")){
  					if((ctx.get("module_list_"+i) != null && ctx.get("module") != null && ctx.get("module_list_"+i).toString().equals(ctx.get("module").toString()))
  						&& (ctx.get("metaobject_list_"+i) != null && ctx.get("metaobject") != null && ctx.get("metaobject_list_"+i).toString().equals(ctx.get("metaobject").toString()))
  						&& (ctx.get("metaobject_field_id_list_"+i) != null && ctx.get("metaobject_field_id") != null && ctx.get("metaobject_field_id_list_"+i).toString().equals(ctx.get("metaobject_field_id").toString()))){
  						isErrorFound = true;
  					}
  				}
  			}

  			if(isErrorFound){
  				DataUtils.populateError(ctx, "pageError", DataUtils.getMessage(ctx, "adminDuplicateError"));
  				return;
  			}
  		}catch(Exception e){
  			logger.error("Unable to validate object hierarchy data to list due to error : " + e.getMessage());
  		}
  	}

  	//Method created to validate master lookup data to session list
  	public static void validateMasterLookupObjectDataToList(Context ctx) throws Exception{
  		try{
  			List sessionList = new ArrayList();

  			if(ctx.get("addMasterLookupObject_list_listfreeform_1") != null)
  				sessionList = (List)ctx.get("addMasterLookupObject_list_listfreeform_1");

  			boolean isErrorFound = false;

  			for(int i=0; i<sessionList.size(); i++){
  				Map map = (Map)sessionList.get(i);

  				if(ctx.get("master_lookup_data_code_"+i) == null || ctx.get("master_lookup_data_code_"+i).toString().equals(HtmlConstants.EMPTY))
  					isErrorFound = true;
  				
  				if(ctx.get("master_lookup_data_desc_"+i) == null || ctx.get("master_lookup_data_desc_"+i).toString().equals(HtmlConstants.EMPTY))
  					isErrorFound = true;

  				if(ctx.get("parent_master_lookup_detail_id_"+i) != null && !ctx.get("parent_master_lookup_detail_id_"+i).toString().equals(HtmlConstants.EMPTY)){
  					if(ctx.get("parent_master_lookup_data_id_"+i) == null || ctx.get("parent_master_lookup_data_id_"+i).toString().equals(HtmlConstants.EMPTY))
  	  					isErrorFound = true;
  				}

  				if(isErrorFound)
  					break;
  			}

  			if(isErrorFound){
  				DataUtils.populateError(ctx, "pageError2", DataUtils.getMessage(ctx, "fillRequiredErrorKey"));
  				return;
  			}
  	        String actionId = ctx.get(HtmlConstants.INET_METHOD) == null?null:ctx.get(HtmlConstants.INET_METHOD).toString();
  			//checking for duplicate values in list
  			for(int i=0; i<sessionList.size(); i++){
  				Map map = (Map)sessionList.get(i);
  				if(actionId != null && actionId.equals("saveValue")){
  					if(ctx.get("master_lookup_data_code_"+i) != null && ctx.get("master_lookup_data_code") != null && ctx.get("master_lookup_data_code_"+i).toString().equals(ctx.get("master_lookup_data_code").toString())){
 			           if((ctx.get("parent_master_lookup_detail_id_"+i) == null && ctx.get("parent_master_lookup_detail_id") == null)){
 					     isErrorFound=true;
 					   }else if(ctx.get("parent_master_lookup_detail_id_"+i) != null && ctx.get("parent_master_lookup_detail_id") != null && ctx.get("parent_master_lookup_detail_id_"+i).toString().equals(ctx.get("parent_master_lookup_detail_id").toString())){  	                 
 						   if(ctx.get("parent_master_lookup_data_id_"+i) != null && ctx.get("parent_master_lookup_data_id") != null && ctx.get("parent_master_lookup_data_id_"+i).toString().equals(ctx.get("parent_master_lookup_data_id").toString())){  					
 							   isErrorFound=true;

 						   }
 					   }
 					}
  				}else {
  	  				for(int j=i+1; j<sessionList.size(); j++){
  	  					//Map nextMap = (Map)sessionList.get(j);
  	  					if(ctx.get("master_lookup_data_code_"+i) != null && ctx.get("master_lookup_data_code_"+j) != null && ctx.get("master_lookup_data_code_"+i).toString().equals(ctx.get("master_lookup_data_code_"+j).toString())){
  	  			           if((ctx.get("parent_master_lookup_detail_id_"+i) == null && ctx.get("parent_master_lookup_detail_id_"+j) == null)){
  	  					     isErrorFound=true;
  	  					   }else if(ctx.get("parent_master_lookup_detail_id_"+i) != null && ctx.get("parent_master_lookup_detail_id_"+j) != null && ctx.get("parent_master_lookup_detail_id_"+i).toString().equals(ctx.get("parent_master_lookup_detail_id_"+j).toString())){  	                 
  	  						   if(ctx.get("parent_master_lookup_data_id_"+i) != null && ctx.get("parent_master_lookup_data_id_"+j) != null && ctx.get("parent_master_lookup_data_id_"+i).toString().equals(ctx.get("parent_master_lookup_data_id_"+j).toString())){  					
  	  							   isErrorFound=true;

  	  						   }
  	  					   }
  	  					}
  	  					   
  	  				}
  				}
  			}

  			if(isErrorFound){
  				DataUtils.populateError(ctx, "pageError2", DataUtils.getMessage(ctx, "adminDuplicateError"));
  				return;
  			}
  		}catch(Exception e){
  			logger.error("Unable to validate master lookup data to list due to error : " + e.getMessage());
  		}
  	}

  	//Method created to generate policy data sheet from object hierarchy data
  	public static void generatePolicyDataSheet(Context ctx) throws Exception{
  		try{
  			HSSFWorkbook book = new HSSFWorkbook();
  			Font font = book.createFont();
  			font.setBoldweight(Font.BOLDWEIGHT_BOLD);

  			HSSFCellStyle style = book.createCellStyle();
  			style.setFont(font);

  			if(ctx.get("XML") != null){
  				String xml = ctx.get("XML").toString();

  				SAXBuilder builder = new SAXBuilder();
  				Document document = builder.build(new StringReader(xml));
  				Element rootElement = document.getRootElement();

  				if(rootElement.getChildren() != null && rootElement.getChildren().size() > 0){
  					for(int i=0; i<rootElement.getChildren().size(); i++){
  						Element metaobjectElement = (Element)rootElement.getChildren().get(i);

  						if(metaobjectElement.getName().equals("metaobject")){
  							HSSFSheet sheet = null;
  							if(book.getSheet(metaobjectElement.getAttributeValue("name")) != null)
  								sheet = book.createSheet(metaobjectElement.getAttributeValue("name")+"_"+i+"");
  							else
  								sheet = book.createSheet(metaobjectElement.getAttributeValue("name"));

  							if(metaobjectElement.getChildren() != null && metaobjectElement.getChildren().size() > 0){
  								HSSFRow row = sheet.createRow(0);

  								for(int j=0; j<metaobjectElement.getChildren().size(); j++){
  									Element fieldElement = (Element)metaobjectElement.getChildren().get(j);

  									HSSFCell cell = row.createCell(j);
  									cell.setCellValue(fieldElement.getAttributeValue("name"));
  									cell.setCellStyle(style);

  									sheet.setColumnWidth(j, 7000);
  								}
  							}

  							if(metaobjectElement.getChildren() != null && metaobjectElement.getChildren().size() > 0){
  								HSSFRow row = sheet.createRow(1);

  								for(int j=0; j<metaobjectElement.getChildren().size(); j++){
  									Element fieldElement = (Element)metaobjectElement.getChildren().get(j);

  									HSSFCell cell = row.createCell(j);
  									cell.setCellValue(fieldElement.getAttributeValue("description"));

  									sheet.setColumnWidth(j, 7000);
  								}
  							}
  						}
  					}
  				}
  			}

  			ByteArrayOutputStream bout = new ByteArrayOutputStream();
  			book.write(bout);

      		HttpServletResponse resp = (HttpServletResponse)ctx.get("DocumentResponse");
  			byte[] rb = bout.toByteArray();
  			resp.setContentLength(rb.length);

  			String fileName = ctx.getProject()+"_PolicyData.xls";

  			resp.setContentType(((ServletContext)ctx.get("DocumentServletContext")).getMimeType(fileName));
  			resp.setHeader("content-disposition", "attachment;filename="+fileName);
  			ServletOutputStream sout = resp.getOutputStream();
  			sout.write(rb);
  			sout.close();
  		}catch(Exception e){
  			logger.error("Unable to generate policy data sheet due to error : " + e.getMessage());
  		}
  	}

  	public static void showETLErrorLog(Context ctx) throws Exception{
		try{
			Context newCtx = new Context();
			newCtx.putAll(ctx);
			newCtx.setProject("ProducerOnePerformance");

			newCtx.put("PAGE_SIZE", 100);
			newCtx.put("PAGE_NUMBER", 1);
			newCtx.put("ispaginationrequired", "Y");
			newCtx.put("reportID", "errorlog");
			newCtx.put("groupby", null);
			newCtx.put("ETL_TRANS_ERROR_P", ctx.get("ETL_TRANS_ERROR_P"));
			newCtx.put("load_type", ctx.get("load_type"));
			newCtx.put("reportoutputtype", "xml1");
			new SetParametersForStoredProcedures().setParametersInContext(newCtx, "reportoutputtype,PAGE_SIZE,PAGE_NUMBER,ispaginationrequired,reportID,load_type,groupby,ETL_TRANS_ERROR_P,");
			List list = (List)SqlResources.getSqlMapProcessor(newCtx).select("report_search_columns.GetErrorLogReport_P", newCtx);
			ctx.put("showETLErrorLog_list_1", list.get(0));
		}catch(Exception e){
			//do nothing
		}
	}

	public static void showETLControlTotalsLog(Context ctx) throws Exception{
		try{
			Context newCtx = new Context();
			newCtx.putAll(ctx);
			newCtx.setProject("ProducerOnePerformance");

			newCtx.put("PAGE_SIZE", 100);
			newCtx.put("PAGE_NUMBER", 1);
			newCtx.put("ispaginationrequired", "Y");
			newCtx.put("reportID", "prePostLoadControl");
			newCtx.put("groupby", null);
			newCtx.put("audit_date_post_V", ctx.get("audit_date_post_V"));
			newCtx.put("load_type", ctx.get("load_type"));
			newCtx.put("reportoutputtype", "xml1");
			new SetParametersForStoredProcedures().setParametersInContext(newCtx, "reportoutputtype,PAGE_SIZE,PAGE_NUMBER,ispaginationrequired,reportID,load_type,groupby,ETL_TRANS_ERROR_P,");
			List list = (List)SqlResources.getSqlMapProcessor(newCtx).select("report_search_columns.GetPostLoadControlReport_P", newCtx);
			ctx.put("showETLControlTotalsLog_list_1", list.get(0));
		}catch(Exception e){
			//do nothing
		}
	}


	/**
	 *
	 * Common Method to generate document and upload or Print after generation
	 *
	 * @param ctx   (Get the document for dynamic fields)
	 * @param data_container_name (Like ListName,xmlStringName or null for complete Context)
	 * @param data_container_type (Like list,xml or null for complete Context)
	 * @param template_name (template name from which documents need to be generated)
	 * @param output_document_type (Like PDF or Excel, for now its only PDF)
	 * @param template_type_id (template id from which documents need to be generated)
	 * @param document_operation  (Set value to 'upload' docuement on dms or 'pirnt' )
	 * @param object_id (set object_id for which document need to be uploaded)
	 * @param document_category (document category need to be set for uploading document)
	 * @param is_confidential (set is document confidential or not)
	 * @param isUsingLookup (set value for isUsingLookup 'Y' for lookup and 'N' from resource folder)
	 * @param last_updated_by  (Reqauired in case of uploading document)
	 * @param is_editable (Set Value to '1' or '0' , This is not a tested feature)
	 * @throws Exception
	 */
	public static void generateNUploadDocumentFromTemplate(Context ctx,String data_container_name,String data_container_type
			,String template_name ,String output_document_type ,String template_type_id,String document_operation
			,String object_id,String document_category,String is_confidential ,String isUsingLookup
			,String last_updated_by,String is_editable) throws Exception{

		if(logger.isDebugEnabled(ctx))
			logger.debug(ctx, "Method Name:generateNUploadDocumentFromTemplate , Info:Inside the method to generate document");
		try {
			String templateFile = "";
			String templatePath = "";
 			String document_name = "";
			String last_updated_ts = null;

			if(isUsingLookup == null || HtmlConstants.EMPTY.equals(isUsingLookup))
				isUsingLookup = "N";

			if(isUsingLookup.equals("Y"))
				templatePath = SystemProperties.getInstance().getString("contract.template.dms.url");
			else
				templatePath = SystemProperties.getInstance().getString("html.basedir")+"foxsl";



			if(template_name != null && !HtmlConstants.EMPTY.equals(template_name)
						&& output_document_type != null && !HtmlConstants.EMPTY.equals(output_document_type)
						&& ((document_operation != null && !HtmlConstants.EMPTY.equals(document_operation)
								&& "upload".equalsIgnoreCase(document_operation)
								&& object_id != null && !HtmlConstants.EMPTY.equals(object_id)
								&& document_category != null && !HtmlConstants.EMPTY.equals(document_category)
								&& last_updated_by != null && !HtmlConstants.EMPTY.equals(last_updated_by))
								|| ((document_operation != null && !HtmlConstants.EMPTY.equals(document_operation)
										&& "print".equalsIgnoreCase(document_operation)) )
								|| ((document_operation != null && !HtmlConstants.EMPTY.equals(document_operation)
										&& "addInSession".equalsIgnoreCase(document_operation)) )		)
						){

				String dataInXmlFormat = "";

				if(data_container_type.equalsIgnoreCase("xml")){
					if(data_container_name == null || HtmlConstants.EMPTY.equals(data_container_name)){
						if(logger.isDebugEnabled(ctx))
							logger.debug(ctx, "Method Name:generateNUploadDocumentFromTemplate , Info: 'data Container Name' is not provided to Generate Document");
						return;
					}

					dataInXmlFormat = ctx.get(data_container_name).toString();
				}else if(data_container_type.equalsIgnoreCase("list")){

					ctx.put("list_name",data_container_name);
					dataInXmlFormat = ProducerOneUtils.convertListToXML(ctx);

				}else if(data_container_type == null || data_container_type.equalsIgnoreCase("") || data_container_type.equalsIgnoreCase("context") || data_container_type.equalsIgnoreCase("ctx") ){
					Context localCtx = new Context();
					localCtx.putAll(ctx);
					dataInXmlFormat =  new XMLUtils().generateResponseXml(localCtx, ctx);
				}

				if(StringUtils.isBlank(dataInXmlFormat)){
					Context localCtx = new Context();
					localCtx.putAll(ctx);
					dataInXmlFormat =  new XMLUtils().generateResponseXml(localCtx, ctx);
				}

				if(logger.isDebugEnabled(ctx))
					logger.debug(ctx, "Method Name:generateNUploadDocumentFromTemplate , Info: Going to Generate Document");
				ByteArrayOutputStream bout = new ByteArrayOutputStream();


				ServletContextURIResolver uriResolver;
				ServletContext servletContext;

				uriResolver = (ServletContextURIResolver)ctx.get("DocumentUriResolver");
				servletContext = (ServletContext)ctx.get("DocumentServletContext");

				// configure fopFactory as desired
				FopFactory fopFactory = FopFactory.newInstance();
				//tell the FOPFactory object where to look for resources
			    fopFactory.setURIResolver(uriResolver);

			    new XML2PDF().registerFonts(ctx, fopFactory);	
			    
				FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
				foUserAgent.setBaseURL("file:///" + servletContext.getRealPath("/") );

				// configure foUserAgent as desired
				// Setup output
				bout = new ByteArrayOutputStream();

				// Construct fop with desired output format
				Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, bout);

				// Setup XSLT
				TransformerFactory factory = TransformerFactory.newInstance();

				//tell the TransformerFactory where to find resources
			    factory.setURIResolver(uriResolver);

			    Source xsltSrc = null;


			    templateFile = templatePath+File.separator+template_name;

			    String templateContent = new XML2PDF().parseReportTemplate(ctx, templateFile);
	        	xsltSrc = new StreamSource(new StringReader(templateContent));

			    Transformer transformer = factory.newTransformer(xsltSrc);

			    //tell the Transformer object where to find resources
			    transformer.setURIResolver(uriResolver);

				// Setup input for XSLT transformation


			    InputStream is =  new ByteArrayInputStream(dataInXmlFormat.getBytes("UTF-16"));

			    Source src = new StreamSource(is);

				// Resulting SAX events (the generated FO) must be piped through to FOP
				Result result = new SAXResult(fop.getDefaultHandler());

				// Start XSLT transformation and FOP processing
				transformer.transform(src, result);

				byte[] content = bout.toByteArray();
				if(is_editable != null && !HtmlConstants.EMPTY.equals(is_editable) && is_editable.equals("1")){
					content = placeAcroFields(content);
				}


				if(logger.isDebugEnabled(ctx))
					logger.debug(ctx, "Method Name:generateNUploadDocumentFromTemplate , Info: Document Generation Done");


				String fileName = template_name.substring(0, template_name.lastIndexOf("."));


				fileName = new ProducerOneUtils().excludeSpecialCharacters(fileName);

				if(fileName!=null && fileName.length()>44){
					fileName = fileName.substring(0, 43);
				}
				String extn = template_name.substring(template_name.lastIndexOf(".")+1,template_name.length());

				SimpleDateFormat sdf = new SimpleDateFormat("_yyyy-MM-dd_hh-mm");

				if(ctx.get("locationNumber") != null && !HtmlConstants.EMPTY.equals(ctx.get("locationNumber").toString()))
					template_name = new ProducerOneUtils().excludeSpecialCharacters(fileName)+"_"+ctx.get("locationNumber").toString()+ sdf.format(new Date()) + ".pdf";
				else
					template_name = new ProducerOneUtils().excludeSpecialCharacters(fileName) + sdf.format(new Date()) + ".pdf";

				try{
					if(ctx.get("producer_number_id") != null && !ctx.get("producer_number_id").toString().equals(HtmlConstants.EMPTY)){
						ctx.put("producer_number_ids", ctx.get("producer_number_id"));
						new SetParametersForStoredProcedures().setParametersInContext(ctx, "producer_number_ids");
						List list = (List)SqlResources.getSqlMapProcessor(ctx).select("massChange.getProducerNumberByIds_p_java", ctx);
						if(list != null){
							Map map = (Map)list.get(0);

							if(map != null && map.get("producer_number") != null)
								template_name = new ProducerOneUtils().excludeSpecialCharacters(fileName)+"_"+map.get("producer_number").toString()+ sdf.format(new Date()) + ".pdf";
						}
					}
				}catch(Exception e){
					logger.error(ctx, "Unable to get producer number due to error : " + DataUtils.getExceptionStackTrace(e));
				}

				document_name = template_name;

				if(document_operation != null && !HtmlConstants.EMPTY.equals(document_operation)
						&& "upload".equalsIgnoreCase(document_operation)){

					if(logger.isDebugEnabled(ctx))
						logger.debug(ctx, "Method Name:generateNUploadDocumentFromTemplate , Info: Going to upload document");


					if(is_confidential != null && !HtmlConstants.EMPTY.equals(is_confidential)
							&& (is_confidential.equals("Y") || is_confidential.equals("1")))
						is_confidential = "1";
					else
						is_confidential = "0";



					last_updated_ts = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a").format(new Date());

					Context newCtx = new Context();
					newCtx.setProject("ProducerOne");

					String isDMSIntegrationdone = SystemProperties.getInstance().getString("dms.integrationdone");
					if(isDMSIntegrationdone != null && isDMSIntegrationdone.equals("Y")){
						if(logger.isDebugEnabled(ctx))
							logger.debug(ctx, "Method Name:generateNUploadDocumentFromTemplate , Info: Going to insert Document into DB");
						try{

							String documentComments =null;

							if(ctx.get("commingFromGenerateContract") != null && !HtmlConstants.EMPTY.equals(ctx.get("commingFromGenerateContract").toString()) && ctx.get("commingFromGenerateContract").toString().equals("Y"))
							{
								documentComments = "Document Generated by ProducerOne";
							}
							else
							{
								documentComments = ctx.get("document_comments")!=null && !HtmlConstants.EMPTY.equals(ctx.get("document_comments").toString())?ctx.get("document_comments").toString():"Document Generated by ProducerOne";
							}

							newCtx.put("comments", documentComments);
							newCtx.put("document_name", document_name);
							newCtx.put("confidential", is_confidential);
							newCtx.put("last_updated_by", last_updated_by);
							newCtx.put("last_updated_ts", last_updated_ts);
							newCtx.put("content", content);
							newCtx.put("document_category", document_category);
							newCtx.put("object_id",object_id);
							newCtx.put("template_type_id",template_type_id);

							if(ctx.get("associated_object_id") != null)
								newCtx.put("associated_object_id", ctx.get("associated_object_id"));
							
							new SetParametersForStoredProcedures().setParametersInContext(newCtx, "comments,document_name,confidential,last_updated_by,last_updated_ts,object_id,document_category,template_type_id");
							
							//If it is selfService Request
							if(ctx.get("requestTypeForDefaultopen") != null && !HtmlConstants.EMPTY.equals(ctx.get("requestTypeForDefaultopen").toString())){
								newCtx.put("Template_type_id",template_type_id);	
								SqlResources.getSqlMapProcessor(ctx).insert("selfservice.agency_document_attachments_ss.insert", newCtx);
								newCtx.put("document_id",newCtx.get("agency_document_attachments_ss_id").toString());
							}
							else {
							SqlResources.getSqlMapProcessor(newCtx).insert("agency_document_attachments.insert", newCtx);
							}
							if(logger.isDebugEnabled(ctx))
								logger.debug(ctx, "Method Name:generateNUploadDocumentFromTemplate , Info: Document inserted into DB");

						}catch(Exception e){
							logger.error("Method Name:generateNUploadDocumentFromTemplate , Info: Unable to insert file in Database due to error : - " + e.getMessage());

						    DataUtils.populateError((Context)ctx, "documentName", "downloadDocErrorKey");
						    return;
						}


						String dmsProvider = SystemProperties.getInstance().getString("dms.provider");
						if(dmsProvider != null){
							if(dmsProvider.equalsIgnoreCase("wss3")){

								try{
									if(logger.isDebugEnabled(ctx))
										logger.debug(ctx, "Method Name:generateNUploadDocumentFromTemplate , Info: Going to upload file at sharepoint ---- ");
									new DocumentUploadBO().uploadDocumentToSharePoint(newCtx);
									if(logger.isDebugEnabled(ctx))
										logger.debug(ctx, "Method Name:generateNUploadDocumentFromTemplate , Info: File Uploaded.");
								}catch (Exception e1) {
									logger.error("Method Name:generateNUploadDocumentFromTemplate , Info: Unable to upload file at sharepoint due to error : - " + e1.getMessage());
								    DataUtils.populateError((Context)ctx, "documentName", "downloadDocErrorKey");
								    return;
								}
							}else if(dmsProvider.equalsIgnoreCase("filesystem")){

								try{
									String dmsUploadEnabled = SystemProperties.getInstance().getString("dms.enabled");
									newCtx.put("dmsUploadEnabled", dmsUploadEnabled);
									if(logger.isDebugEnabled(ctx))
										logger.debug(ctx, "Method Name:generateNUploadDocumentFromTemplate , Info: Going to upload file at DMS");
									new DocumentUploadBO().uploadDocumentToDMS(newCtx);
								}catch (Exception e1) {
									logger.error("Method Name:generateNUploadDocumentFromTemplate , Info: Unable to upload file at DMS due to error : - " + e1.getMessage());
									DataUtils.populateError((Context)ctx, "documentName", "downloadDocErrorKey");
									try{
										if(logger.isDebugEnabled(ctx))
											logger.debug(ctx, "Method Name:generateNUploadDocumentFromTemplate , Info: Going to remove document from database");
										SqlResources.getSqlMapProcessor(newCtx).delete("agency_document_attachments.delete", newCtx);
										if(logger.isDebugEnabled(ctx))
											logger.debug(ctx, "Method Name:generateNUploadDocumentFromTemplate , Info: Document removed.");
										DataUtils.populateError((Context)ctx, "documentName", "downloadDocErrorKey");
									}catch (Exception e2) {
										logger.error("Method Name:generateNUploadDocumentFromTemplate , Info: Unable to remove document from database due to error : " + e2.getMessage());
										DataUtils.populateError((Context)ctx, "documentName", "downloadDocErrorKey");
									}

								    DataUtils.populateError((Context)ctx, "documentName", "downloadDocErrorKey");
								    return;
								}
							}
						}
					}



				}else if(document_operation != null && !HtmlConstants.EMPTY.equals(document_operation)
						&& "print".equalsIgnoreCase(document_operation)){
					if(logger.isDebugEnabled(ctx))
						logger.debug(ctx, "Method Name:generateNUploadDocumentFromTemplate , Info: Downloading Document");
					if(content != null && ctx.get("DocumentResponse") != null && ctx.get("DocumentResponse") instanceof HttpServletResponse){
						HttpServletResponse resp = (HttpServletResponse)ctx.get("DocumentResponse");
						resp.setContentLength(content.length);

						resp.setContentType(((ServletContext)ctx.get("DocumentServletContext")).getMimeType(document_name));
						resp.setHeader("content-disposition", "attachment;filename="+document_name);
						ServletOutputStream sout = resp.getOutputStream();
						sout.write(content);
						sout.close();
					}

					if(logger.isDebugEnabled(ctx))
						logger.debug(ctx, "Method Name:generateNUploadDocumentFromTemplate , Info: Downloading Document Completed");
				}else if(document_operation != null && !HtmlConstants.EMPTY.equals(document_operation)
						&& "addInSession".equalsIgnoreCase(document_operation)){
					if(logger.isDebugEnabled(ctx))
						logger.debug(ctx, "Method Name:generateNUploadDocumentFromTemplate , Info: Add Document in session");
					List list = null;
					Map docMap = new HashMap();
					if(content != null && ctx.get("DocumentResponse") != null
							&& ctx.get("DocumentResponse") instanceof HttpServletResponse
							&& ctx.get("documentListNameForSession")!= null && !HtmlConstants.EMPTY.equals(ctx.get("documentListNameForSession").toString())){
						String listName = ctx.get("documentListNameForSession").toString();
						List documentList = new ArrayList();
						int listSize = 0;

						if(ctx.get(listName) != null && ctx.get(listName) instanceof List){
							list = (List)ctx.get(listName);
							listSize = list.size();
						}else{
							list = new ArrayList();
						}

						FileItemFactory fileItemfactory = new DiskFileItemFactory();
						ServletFileUpload upload = new ServletFileUpload(fileItemfactory);
						//List fileItems = upload.parseRequest(req);
						//List fileItems = ctx.get("fileItems") != null ? (List)ctx.get("fileItems") : null;

						if(is_confidential != null && !HtmlConstants.EMPTY.equals(is_confidential)
								&& (is_confidential.equals("Y") || is_confidential.equals("1")))
							is_confidential = "1";
						else
							is_confidential = "0";
						docMap.put("contents", content);

						docMap.put("document_name", document_name);
						docMap.put("last_updated_ts", new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a").format(new Date()));

						docMap.put("document_category", document_category);
						docMap.put("isNewFile", "Y");
						docMap.put("rownum", (listSize+1));


						list.add(docMap);
						ctx.put(listName, list);
						if(ctx.get("DocumentRequest") != null && ctx.get("DocumentRequest") instanceof HttpServletRequest){
				            HttpServletRequest req = (HttpServletRequest)ctx.get("DocumentRequest");
				            HttpSession sess = req.getSession();
				            sess.setAttribute(listName, list);
						}
					}
					if(logger.isDebugEnabled(ctx))
						logger.debug(ctx, "Method Name:generateNUploadDocumentFromTemplate , Info: Add Document in session Completed");
				}
			}
			else{
				if(logger.isDebugEnabled(ctx))
					logger.debug(ctx, "Method Name:generateNUploadDocumentFromTemplate, Info: Complete Info is not provided to Generate Document");
				DataUtils.populateError((Context)ctx, "documentName", "downloadDocErrorKey");
			}

			} catch(Exception e) {
				 logger.error("Method Name:generateNUploadDocumentFromTemplate , Info: " + e.getMessage());
				 DataUtils.populateError((Context)ctx, "documentName", "downloadDocErrorKey");
			}

		if(logger.isDebugEnabled(ctx))
			logger.debug(ctx, "Method Name:generateDocumentFromTemplate , Info:Execution Finish");
	}

	/**
	 *
	 * @param ctx   (Get the document for dynamic fields)
	 * @param data_container_name (Like ListName,xmlStringName or null for complete Context)
	 * @param data_container_type (Like list,xml or null for complete Context)
	 * @param template_name (template name from which documents need to be generated)
	 * @param output_document_type (Like PDF or Excel, for now its only PDF)
	 * @param template_type_id (template id from which documents need to be generated)
	 * @param listNameTobeUpdated (list name which need to be updated in session)
	 * @param document_category (document category need to be set for uploading document)
	 * @param is_confidential (set is document confidential or not)
	 * @param isUsingLookup (set value for isUsingLookup 'Y' for lookup and 'N' from resource folder)
	 * @param last_updated_by  (Reqauired in case of uploading document)
	 * @param is_editable (Set Value to '1' or '0' , This is not a tested feature)
	 * @throws Exception
	 */
	public static void generateNUploadDocumentInSessionFromTemplate(Context ctx,String data_container_name,String data_container_type
			,String template_name ,String output_document_type ,String template_type_id
			,String listNameTobeUpdated,String document_category,String is_confidential ,String isUsingLookup
			,String last_updated_by,String is_editable) throws Exception{

		ctx.put("documentListNameForSession", listNameTobeUpdated);

		generateNUploadDocumentFromTemplate(ctx,data_container_name,data_container_type
				,template_name ,output_document_type ,template_type_id,"addInSession"
				,null,document_category,is_confidential ,isUsingLookup
				,last_updated_by,is_editable);



	}

	public static void genratedNUploadDocumentsForOnboardingTaskGenerated(Context ctx){
		if(ctx.get("onboarding_agency_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("onboarding_agency_id").toString())){

			try {
				List tasksList = SqlResources.getSqlMapProcessor(ctx).select("agency_taskDetails.getListOfTaskGeneratedNDocumentTemplate_p", ctx);

				if(tasksList != null && tasksList.size() > 0){

					for(int i=0;i<tasksList.size();i++){
						Map taskMap = (Map)tasksList.get(i);

						if(taskMap != null && taskMap.get("is_document_generated") != null && !HtmlConstants.EMPTY.equals(taskMap.get("is_document_generated"))
								&& taskMap.get("document_name") != null && !HtmlConstants.EMPTY.equals(taskMap.get("document_name"))){

							int agency_task_id = Integer.parseInt(taskMap.get("agency_task_id").toString());
							String is_confidential = taskMap.get("is_confidential")!=null && !HtmlConstants.EMPTY.equals(taskMap.get("is_confidential").toString())?taskMap.get("is_confidential").toString():"0";

							RuleImpl rImpl = (RuleImpl)RulesResources.getInstance(ctx).findRule("ProducerOneRule.assignDateFields");
	                        rImpl.evaluate(ctx, null);

							String xmlData  = getPopulatedDataXml(ctx,agency_task_id,"AgencyTasks");
							ctx.put("xmlData",xmlData);
							generateNUploadDocumentFromTemplate(ctx,"xmlData","xml",taskMap.get("document_name").toString(),"pdf",taskMap.get("template_type_id").toString(),"upload",taskMap.get("agency_task_id").toString(),ctx.get("document_category").toString()
									,is_confidential,"Y",ctx.get("last_updated_by").toString(),taskMap.get("is_editable").toString());


						}

					}
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				  logger.error(ctx, "Unable to execute genratedNUploadDocumentsForOnboardingTaskGenerated method due to error : " + DataUtils.getExceptionStackTrace(e));
			}


		}
	}


	private static byte[] placeAcroFields(byte[] data) throws Exception {
		PdfReader reader = new PdfReader(data);
		File tempFile = File.createTempFile("Acro", "pdf");
		OutputStream out = new FileOutputStream(tempFile);
		PdfStamper stamper = new PdfStamper(reader, out);

		String placeHolderStart = new String(new byte[] {'@','{'});
		String placeHolderEnd = new String(new byte[] {'}','@'});


		int lastIndexOfPlaceHolderStart = 0;
		int lastIndexOfPlaceHolderEnd = 0;


		int pages = reader.getNumberOfPages();
		for(int i=1;i<=pages;i++) {
			String content = new String(reader.getPageContent(i));
			int fieldIndex = 0;
			while(true) {
				String fieldName = "field";

				lastIndexOfPlaceHolderEnd = lastIndexOfPlaceHolderStart = content.indexOf(placeHolderStart, lastIndexOfPlaceHolderStart);

				if(lastIndexOfPlaceHolderStart < 0) break; // while loop ends on this condition.

				lastIndexOfPlaceHolderEnd = content.indexOf(placeHolderEnd,lastIndexOfPlaceHolderEnd);

				//Value between place holder @{<<Value>>}@
				String placeHolderText = content.substring(lastIndexOfPlaceHolderStart+2, lastIndexOfPlaceHolderEnd);
				//TODO Debug -- remove this line

				if(logger.isDebugEnabled())
					logger.debug("Placeholder : " + placeHolderText);

				String[] placeHolderValues = placeHolderText.split(",");

				//Validation on place holder value
				if(placeHolderValues==null || placeHolderValues.length<4) continue;

				String text = placeHolderValues[1]; // 2nd element is default value in place holder

				if(text!=null)
					text = text.replace("#COMMA#", ",");

				String color = placeHolderValues[2]; // 1st element is color (RGB) in place holder

				//Last four elements in place holder are coordinates
				String[] cordinates = placeHolderValues[3].split(" ");

				//Validation for coordinates passed in place holder
				if(cordinates.length < 4) break;

				float llx = Integer.parseInt(cordinates[0]); // lower left x
				float lly = Integer.parseInt(cordinates[1]); // lower left y
				float urx = Integer.parseInt(cordinates[2]); // upper right x
				float ury = Integer.parseInt(cordinates[3]); // upper right y


				Rectangle rectangle = new Rectangle(llx, lly, urx, ury);
				fieldName = fieldName + i + fieldIndex++;
				//Color bgColor = new Color(186,211,227); // Default Color
				BaseColor bgColor = new BaseColor(186,211,227);

				//Validation for color(r,g,b) passed in placeholder
				if(!"".equals(color) || null !=color) {
					String[] rgb = color.split(" ");
					if(rgb.length < 3) continue;
					int r = Integer.parseInt(rgb[0]);
					int g = Integer.parseInt(rgb[1]);
					int b = Integer.parseInt(rgb[2]);
					if((r >=0 && r <=255) && (g >=0 && g <=255) && (b >=0 && b <=255)) {
						//bgColor = new Color(r,g,b);
						bgColor = new BaseColor(r,g,b);
					}
				}
				//Creating field
				PdfFormField pff = null;
				if(placeHolderValues[0]!=null || !"".equals(placeHolderValues[0])) {
					if(placeHolderValues[0].equals("TXT")) {
						TextField textField = new TextField(stamper.getWriter(), rectangle, fieldName);
						textField.setBackgroundColor(bgColor);
						textField.setText(text);
						textField.setFontSize(10);
						pff = textField.getTextField();
					}else if (placeHolderValues[0].equals("TXTA")) {
						TextField textField = new TextField(stamper.getWriter(), rectangle, fieldName);
						textField.setBackgroundColor(bgColor);
						textField.setText(text);
						textField.setOptions(TextField.MULTILINE);
						pff = textField.getTextField();
						textField.setFontSize(10);
					}else if (placeHolderValues[0].equals("CHK")) {
						RadioCheckField checkbox = new RadioCheckField(
								stamper.getWriter(), rectangle, fieldName, text);
						checkbox.setBackgroundColor(bgColor);

						if(text.equalsIgnoreCase("yes"))
						checkbox.setChecked(true);
						checkbox.setCheckType(RadioCheckField.TYPE_CHECK);
						checkbox.setFontSize(15);
						pff = checkbox.getCheckField();
					}
				}
				if(pff!=null) stamper.addAnnotation(pff, i);


				lastIndexOfPlaceHolderStart = lastIndexOfPlaceHolderEnd;
			}
			content = null;
		}
		stamper.close();
		reader.close();
		out.close();
		byte[] dataBytes = IOUtils.readFileInBinary(tempFile.getAbsolutePath());
		IOUtils.deleteFile(tempFile.getAbsolutePath());
		System.gc();
		return dataBytes;
	}

	public static void getNPNForApplyAlerts(Context ctx) throws Exception{
		try{
			List alertList = null;
			String selectedNPN = null;
			String selectedAlertId = null;
			int checkCount = 0;

			if(ctx.get("niprPDBAlerts_list_01") != null && !HtmlConstants.EMPTY.equals(ctx.get("niprPDBAlerts_list_01"))){
				alertList = (List) ctx.get("niprPDBAlerts_list_01");
				if(alertList != null && alertList.size() >0){
					for(int i=0;i<alertList.size();i++){
						Map alertMap=(Map)alertList.get(i);
						if (ctx.get("ajax_field_chkTermination_"+i)!=null && "Y".equals(ctx.get("ajax_field_chkTermination_"+i))) {
							checkCount++;
							if(alertMap.get("NPN") != null && !"".equals(alertMap.get("NPN"))){
								if (selectedNPN==null){
									selectedNPN = alertMap.get("NPN").toString();
								} else {
									selectedNPN = selectedNPN.concat(",").concat(alertMap.get("NPN").toString());
								}
							}

							if(alertMap.get("AlertId") != null && !"".equals(alertMap.get("AlertId"))){
								if (selectedAlertId==null){
									selectedAlertId = alertMap.get("AlertId").toString();
								} else {
									selectedAlertId = selectedAlertId.concat(",").concat(alertMap.get("AlertId").toString());
								}
							}
						}
					}
					if(checkCount==0){
						DataUtils.populateError(ctx, "pageError2", "msgselectrecordforletter2ErrorKey");
						return;
					}
					if(checkCount !=0 && checkCount<=10){
						ctx.put("NPN", selectedNPN);
						ctx.put("AlertId", null);
						ctx.put("output", null);
						new SetParametersForStoredProcedures().setParametersInContext(ctx, "AlertId,NPN,output");
						SqlResources.getSqlMapProcessor(ctx).update("PDBAlerts.ImportPDBAlerts_p",ctx);
					}else if(selectedNPN != null && selectedAlertId != null){
						String npnArray[] = selectedNPN.split(",");
						String alertsArray[] = selectedAlertId.split(",");
						if(npnArray != null && alertsArray != null){
							for(int i=0;i<npnArray.length;i++){
								if(!npnArray[i].equals("") && !alertsArray[i].equals("")){
									ctx.put("NPN", npnArray[i]);
									ctx.put("AlertId", alertsArray[i]);
									ctx.put("output", null);
									ctx.put("last_updated_by", ctx.get("user_id").toString());
									new SetParametersForStoredProcedures().setParametersInContext(ctx, "AlertId,NPN,output,last_updated_by");
									SqlResources.getSqlMapProcessor(ctx).update("PDBAlerts.InsertPDBAlertsBatchRequests_p",ctx);
								}
							}
						}
					}

					ctx.put("NPN", selectedNPN);
				}
			}
		}catch(Exception e){
			logger.error("Unable to get NPN For Apply Alerts due to error : " + e.getMessage());
		}
	}

	//Method created to validate html field security rules data to session list
  	public static void validateHtmlFieldSecurityRulesDataToList(Context ctx) throws Exception{
  		try{
  			boolean isErrorFound = false;

  			if(ctx.get(HtmlConstants.INET_METHOD) != null && !ctx.get(HtmlConstants.INET_METHOD).toString().equals("update")){
  				/*if(ctx.get("ruleid") == null || ctx.get("ruleid").toString().equals(HtmlConstants.EMPTY)){
  					DataUtils.populateError(ctx, "ruleid", "Please enter Rule Id");
  					isErrorFound = true;
  				}*/

  				if(ctx.get("expression") == null || ctx.get("expression").toString().equals(HtmlConstants.EMPTY)){
  					DataUtils.populateError(ctx, "expression", "Please enter Expression");
  					isErrorFound = true;
  				}

  				if(isErrorFound)
  					return;
  			}

  			List sessionList = new ArrayList();

  			if(ctx.get("addManageScreensRule_list_listfreeform_1") != null)
  				sessionList = (List)ctx.get("addManageScreensRule_list_listfreeform_1");

  			for(int i=0; i<sessionList.size(); i++){
  				Map map = (Map)sessionList.get(i);

  				/*if(ctx.get("ruleid_"+i) == null || ctx.get("ruleid_"+i).toString().equals(HtmlConstants.EMPTY))
  					isErrorFound = true;*/

  				if(ctx.get("expression_"+i) == null || ctx.get("expression_"+i).toString().equals(HtmlConstants.EMPTY))
  					isErrorFound = true;

  				if(isErrorFound)
  					break;
  			}

  			if(isErrorFound){
  				DataUtils.populateError(ctx, "pageError2", DataUtils.getMessage(ctx, "fillRequiredErrorKey"));
  				return;
  			}

  			isErrorFound = false;

  			//checking for duplicate values in list -- 1099TypeRowCount
  			for(int i=0; i<sessionList.size(); i++){
  				Map map = (Map)sessionList.get(i);

  				for(int j=i+1; j<sessionList.size(); j++){
  					//Map nextMap = (Map)sessionList.get(j);

  					if(ctx.get("expression_"+i) != null && ctx.get("expression_"+j) != null && ctx.get("expression_"+i).toString().equals(ctx.get("expression_"+j).toString())){
  						isErrorFound = true;
  					}

  					/*if((ctx.get("ruleid_"+i) != null && ctx.get("ruleid_"+j) != null && ctx.get("ruleid_"+i).toString().equals(ctx.get("ruleid_"+j).toString()))
  						&& (ctx.get("expression_"+i) != null && ctx.get("expression_"+j) != null && ctx.get("expression_"+i).toString().equals(ctx.get("expression_"+j).toString()))
  						){
  						isErrorFound = true;
  					}*/
  				}

  				if(ctx.get(HtmlConstants.INET_METHOD) != null && !ctx.get(HtmlConstants.INET_METHOD).toString().equals("update")){
  					if(ctx.get("expression_"+i) != null && ctx.get("expression") != null && ctx.get("expression_"+i).toString().equals(ctx.get("expression").toString())){
  						isErrorFound = true;
  					}

  					/*if((ctx.get("ruleid_"+i) != null && ctx.get("ruleid") != null && ctx.get("ruleid_"+i).toString().equals(ctx.get("ruleid").toString()))
  						&& (ctx.get("expression_"+i) != null && ctx.get("expression") != null && ctx.get("expression_"+i).toString().equals(ctx.get("expression").toString()))
  						){
  						isErrorFound = true;
  					}*/
  				}
  			}

  			if(isErrorFound){
  				DataUtils.populateError(ctx, "pageError2", DataUtils.getMessage(ctx, "adminDuplicateError"));
  				return;
  			}
  		}catch(Exception e){
  			logger.error("Unable to validate html field security rules data to list due to error : " + e.getMessage());
  		}
  	}

	//Method created to add html field security rules data to session list
  	public static void addDeleteHtmlFieldSecurityRulesDataToList(Context ctx) throws Exception{
  		try{
  			List sessionList = new ArrayList();

  			if(ctx.get("addManageScreensRule_list_listfreeform_1") != null)
  				sessionList = (List)ctx.get("addManageScreensRule_list_listfreeform_1");

  			if(ctx.get(HtmlConstants.INET_METHOD) != null && ctx.get(HtmlConstants.INET_METHOD).toString().equals("deleteValue")){
  				if(ctx.get("rownum") != null && !ctx.get("rownum").toString().equals(HtmlConstants.EMPTY)){
  					List finalList = new ArrayList();

  					for(int i=0; i<sessionList.size(); i++){
  						Map map = (Map)sessionList.get(i);

  						if(map.get("rownum") != null && !map.get("rownum").toString().equals(HtmlConstants.EMPTY) &&
  								Integer.parseInt(map.get("rownum").toString()) == Integer.parseInt(ctx.get("rownum").toString())){

  							if(map.get("isNew") != null && map.get("isNew").toString().equals("Y")){
  								//do nothing
  							}else{
  								/*if(ctx.get("html_extendedfields_mapping_id") != null && !ctx.get("html_extendedfields_mapping_id").toString().equals(HtmlConstants.EMPTY))
  									ctx.put("html_extendedfields_objects_mapping_id", ctx.get("html_extendedfields_mapping_id"));
  								if(ctx.get("html_fields_mapping_id") != null && !ctx.get("html_fields_mapping_id").toString().equals(HtmlConstants.EMPTY))
  									ctx.put("html_extendedfields_objects_mapping_id", ctx.get("html_fields_mapping_id"));*/

  								new SetParametersForStoredProcedures().setParametersInContext(ctx, "actualhtml_id,actualhtml_fields_mapping_id,actualhtml_fields_security_rules_mapping_id,client_id,releaseno_id,releaseversionno_id,clientclient_id,clientreleaseno_id,clientreleaseversionno_id");
  								//SqlResources.getSqlMapProcessor(ctx).delete("SqlStmts.sqlStatementsviewdeleteHtmlFieldSecurityRuleDataById", ctx);
  								SqlResources.getSqlMapProcessor(ctx).delete("manageScreens.insertUpdateDeleteConfigurationRulesData_p", ctx);
  							}

  							continue;
  						}

  						finalList.add(map);
  					}

  					ctx.put("addManageScreensRule_list_listfreeform_1", finalList);
  				}

  				return;
  			}

  			boolean isErrorFound = false;

  			Map map = new HashMap();
  			map.put("isNew", "Y");
  			map.put("rownum", sessionList.size()+1);
  			try{
  				if(ctx.get("expression") != null){
  					RuleImpl ruleImpl = new RuleImpl();
  					ruleImpl.setId("XYZ");
  					ruleImpl.setExpression(ctx.get("expression").toString());
  					if(ruleImpl.execute(ctx, null) == null){
  						DataUtils.populateError(ctx, "expression", "invalidRuleErrorKey");
  						return;
  					}
  				}
  			}catch(Exception e){
  				DataUtils.populateError(ctx, "expression", "invalidRuleErrorKey");
  				return;
  			}

  			map.put("expression", ctx.get("expression"));
  			map.put("accesstype", ctx.get("accesstype"));
  			map.put("isencrypt", ctx.get("isencrypt"));
  			map.put("isdecrypt", ctx.get("isdecrypt"));
  			map.put("unmaskdigitssymbol", ctx.get("unmaskdigitssymbol"));
  			map.put("unmaskdigits", ctx.get("unmaskdigits"));
  			map.put("isskip", ctx.get("isskip"));
  			map.put("executionsequence", ctx.get("executionsequence"));
  			/*if(ctx.get("ruleid").toString().contains("."))
  				map.put("ruleid", ctx.get("ruleid"));
  			else
  				map.put("ruleid", "ConfigurationRule."+ctx.get("ruleid"));*/

  			map.put("protectedresourceuniquename", ctx.get("protectedresourceuniquename"));
  			sessionList.add(map);

  			ctx.put("addManageScreensRule_list_listfreeform_1", sessionList);
  		}catch(Exception e){
  			logger.error("Unable to add/delete html field security rules data to list due to error : " + e.getMessage());
  		}
  	}

  	//Method created to validate html field security users data to session list
  	public static void validateHtmlFieldSecurityUsersDataToList(Context ctx) throws Exception{
  		try{
  			boolean isErrorFound = false;

  			if(ctx.get(HtmlConstants.INET_METHOD) != null && !ctx.get(HtmlConstants.INET_METHOD).toString().equals("update")){
  				if(ctx.get("name") == null || ctx.get("name").toString().equals(HtmlConstants.EMPTY)){
  					DataUtils.populateError(ctx, "name", "Please enter User Id");
  					isErrorFound = true;
  				}

  				if(isErrorFound)
  					return;
  			}

  			List sessionList = new ArrayList();

  			if(ctx.get("addManageUsers_list_listfreeform_1") != null)
  				sessionList = (List)ctx.get("addManageUsers_list_listfreeform_1");

  			for(int i=0; i<sessionList.size(); i++){
  				Map map = (Map)sessionList.get(i);

  				if(ctx.get("name_"+i) == null || ctx.get("name_"+i).toString().equals(HtmlConstants.EMPTY))
  					isErrorFound = true;

  				if(isErrorFound)
  					break;
  			}

  			if(isErrorFound){
  				DataUtils.populateError(ctx, "pageError2", DataUtils.getMessage(ctx, "fillRequiredErrorKey"));
  				return;
  			}

  			isErrorFound = false;

  			//checking for duplicate values in list -- 1099TypeRowCount
  			for(int i=0; i<sessionList.size(); i++){
  				Map map = (Map)sessionList.get(i);

  				for(int j=i+1; j<sessionList.size(); j++){
  					//Map nextMap = (Map)sessionList.get(j);

  					if(ctx.get("name_"+i) != null && ctx.get("name_"+j) != null && ctx.get("name_"+i).toString().equals(ctx.get("name_"+j).toString())){
  						isErrorFound = true;
  					}
  				}

  				if(ctx.get(HtmlConstants.INET_METHOD) != null && !ctx.get(HtmlConstants.INET_METHOD).toString().equals("update")){
  					if(ctx.get("name_"+i) != null && ctx.get("name") != null && ctx.get("name_"+i).toString().equals(ctx.get("name").toString())){
  						isErrorFound = true;
  					}
  				}
  			}

  			if(isErrorFound){
  				DataUtils.populateError(ctx, "pageError2", DataUtils.getMessage(ctx, "adminDuplicateError"));
  				return;
  			}
  		}catch(Exception e){
  			logger.error("Unable to validate html field security users data to list due to error : " + e.getMessage());
  		}
  	}

  	//Method created to add html field security users data to session list
  	public static void addDeleteHtmlFieldSecurityUsersDataToList(Context ctx) throws Exception{
  		try{
  			List sessionList = new ArrayList();

  			if(ctx.get("addManageUsers_list_listfreeform_1") != null)
  				sessionList = (List)ctx.get("addManageUsers_list_listfreeform_1");

  			if(ctx.get(HtmlConstants.INET_METHOD) != null && ctx.get(HtmlConstants.INET_METHOD).toString().equals("deleteValue")){
  				if(ctx.get("rownum") != null && !ctx.get("rownum").toString().equals(HtmlConstants.EMPTY)){
  					List finalList = new ArrayList();

  					for(int i=0; i<sessionList.size(); i++){
  						Map map = (Map)sessionList.get(i);

  						if(map.get("rownum") != null && !map.get("rownum").toString().equals(HtmlConstants.EMPTY) &&
  								Integer.parseInt(map.get("rownum").toString()) == Integer.parseInt(ctx.get("rownum").toString())){

  							if(map.get("isNew") != null && map.get("isNew").toString().equals("Y")){
  								//do nothing
  							}else{
  								/*if(ctx.get("html_extendedfields_mapping_id") != null && !ctx.get("html_extendedfields_mapping_id").toString().equals(HtmlConstants.EMPTY))
  									ctx.put("html_extendedfields_objects_mapping_id", ctx.get("html_extendedfields_mapping_id"));
  								if(ctx.get("html_fields_mapping_id") != null && !ctx.get("html_fields_mapping_id").toString().equals(HtmlConstants.EMPTY))
  									ctx.put("html_extendedfields_objects_mapping_id", ctx.get("html_fields_mapping_id"));*/

  								new SetParametersForStoredProcedures().setParametersInContext(ctx, "actualhtml_id,actualhtml_fields_mapping_id,actualhtml_fields_security_users_mapping_id,client_id,releaseno_id,releaseversionno_id,clientclient_id,clientreleaseno_id,clientreleaseversionno_id");
  								//SqlResources.getSqlMapProcessor(ctx).delete("SqlStmts.sqlStatementsviewdeleteHtmlFieldSecurityRuleDataById", ctx);
  								SqlResources.getSqlMapProcessor(ctx).delete("manageScreens.insertUpdateDeleteConfigurationUsersData_p", ctx);
  							}

  							continue;
  						}

  						finalList.add(map);
  					}

  					ctx.put("addManageUsers_list_listfreeform_1", finalList);
  				}

  				return;
  			}

  			boolean isErrorFound = false;

  			Map map = new HashMap();
  			map.put("isNew", "Y");
  			map.put("rownum", sessionList.size()+1);
  			map.put("name", ctx.get("name"));
  			map.put("accesstype", ctx.get("accesstype"));
  			map.put("isencrypt", ctx.get("isencrypt"));
  			map.put("isdecrypt", ctx.get("isdecrypt"));
  			map.put("unmaskdigits", ctx.get("unmaskdigits"));
  			map.put("unmaskdigitssymbol", ctx.get("unmaskdigitssymbol"));
  			map.put("isskip", ctx.get("isskip"));
  			map.put("protectedresourceuniquename", ctx.get("protectedresourceuniquename"));
  			sessionList.add(map);

  			ctx.put("addManageUsers_list_listfreeform_1", sessionList);
  		}catch(Exception e){
  			logger.error("Unable to add/delete html field security users data to list due to error : " + e.getMessage());
  		}
  	}

  	//Method created to download configuration data xmls
	public static void downloadConfigurationHtmlDataXmls(Context ctx) throws Exception{
		FileInputStream fin = null;
        byte[] rb = null;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ZipOutputStream zout = new ZipOutputStream(bout);

		try{
			if(logger.isDebugEnabled(ctx))
				logger.debug(ctx, "Going to download configuration html data xmls");

			String xmlPath = SystemProperties.getInstance().getString("xml.basedir") + ctx.getProject();
            xmlPath = xmlPath.replace("//", File.separator).replace("\\", File.separator).replace("/", File.separator);

			ZipEntry entry = null;

			if(logger.isDebugEnabled(ctx))
				logger.debug(ctx, "Going to get fields XML");
            entry = new ZipEntry("fieldsclient.xml");
            zout.putNextEntry(entry);
            fin = new FileInputStream(xmlPath + File.separator + "fieldsclient.xml");
            rb = new byte[fin.available()];
            fin.read(rb);
            fin.close();
            zout.write(rb);
            zout.closeEntry();
            if(logger.isDebugEnabled(ctx))
            	logger.debug(ctx, "Fields XML got successfully");

            if(logger.isDebugEnabled(ctx))
            	logger.debug(ctx, "Going to get tabsconfiguration XML");
            entry = new ZipEntry("tabsconfigurationclient.xml");
            zout.putNextEntry(entry);
            fin = new FileInputStream(xmlPath + File.separator + "tabsconfigurationclient.xml");
            rb = new byte[fin.available()];
            fin.read(rb);
            fin.close();
            zout.write(rb);
            zout.closeEntry();

            if(logger.isDebugEnabled(ctx))
            	logger.debug(ctx, "Tabsconfiguration XML got successfully");

            if(logger.isDebugEnabled(ctx))
            	logger.debug(ctx, "Going to get labelconf XML");
            entry = new ZipEntry("labelconfclient.xml");
            zout.putNextEntry(entry);
            fin = new FileInputStream(xmlPath + File.separator + "labelconfclient.xml");
            rb = new byte[fin.available()];
            fin.read(rb);
            fin.close();
            zout.write(rb);
            zout.closeEntry();

            if(logger.isDebugEnabled(ctx))
            	logger.debug(ctx, "Labelconf XML got successfully");

            if(logger.isDebugEnabled(ctx))
            	logger.debug(ctx, "Going to get security XML");
            entry = new ZipEntry("securityclient.xml");
            zout.putNextEntry(entry);
            fin = new FileInputStream(xmlPath + File.separator + "security" + File.separator + "securityclient.xml");
            rb = new byte[fin.available()];
            fin.read(rb);
            fin.close();
            zout.write(rb);
            zout.closeEntry();

            if(logger.isDebugEnabled(ctx))
            	logger.debug(ctx, "Security XML got successfully");

            if(logger.isDebugEnabled(ctx))
            	logger.debug(ctx, "Going to get rules XML");
            entry = new ZipEntry("rulesclient.xml");
            zout.putNextEntry(entry);
            fin = new FileInputStream(xmlPath + File.separator + "rules" + File.separator + "impl" + File.separator + "rulesclient.xml");
            rb = new byte[fin.available()];
            fin.read(rb);
            fin.close();
            zout.write(rb);
            zout.closeEntry();
            if(logger.isDebugEnabled(ctx))
            	logger.debug(ctx, "Rules XML got successfully");
		}catch(Exception e){
			logger.error("Unable to download configuration html data xmls due to error : " + e.getMessage());
		}

		HttpServletResponse resp = (HttpServletResponse)ctx.get("DocumentResponse");

        ctx.put("client_id", SystemProperties.getInstance().getString("appl.configuration.clientid"));
		ctx.put("releaseno_id", SystemProperties.getInstance().getString("appl.configuration.releaseno"));
		ctx.put("releaseversionno_id", SystemProperties.getInstance().getString("appl.configuration.releaseversionno"));

		ctx.put("clientclient_id", SystemProperties.getInstance().getString("appl.configuration.client.clientid"));
		ctx.put("clientreleaseno_id", SystemProperties.getInstance().getString("appl.configuration.client.releaseno"));
		ctx.put("clientreleaseversionno_id", SystemProperties.getInstance().getString("appl.configuration.client.releaseversionno"));

		String fileName = ctx.getProject()+ "_" + ctx.get("client_id") + "." + ctx.get("releaseno_id") + "." + ctx.get("releaseversionno_id");
		if(ctx.get("clientclient_id") != null && !ctx.get("clientclient_id").equals("0"))
			fileName = fileName + "_" + ctx.get("clientclient_id") + "." + ctx.get("clientreleaseno_id") + "." + ctx.get("clientreleaseversionno_id");

		fileName = fileName + ".zip";

		resp.setContentType(((ServletContext)ctx.get("DocumentServletContext")).getMimeType(fileName));
		resp.setHeader("content-disposition", "attachment;filename="+fileName);

		zout.close();

		ServletOutputStream sout = resp.getOutputStream();
		sout.write(bout.toByteArray());
		sout.close();
		fin.close();
		bout.close();
	}

	public static void getMessageDataByIndex(Context ctx, int index) throws Exception{
		try{
			ctx.put("msgkey", ctx.get("msgkey_"+(index-1)));
			ctx.put("message", ctx.get("message_"+(index-1)));
		}catch(Exception e){
			logger.error("Unable to get message by index due to error : " + e.getMessage());
		}
	}

	/*public static String getJson(Context ctx) throws Exception {
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(new File(SystemProperties.getInstance().getProperty("appl.home.dir")+File.separator+"questions.xml"));
		Element root = doc.getRootElement();

		json = "var htmlText = '';";
		json = json + "\nmyObj = {";

		if(root.getChildren() != null && root.getChildren().size() > 0){
			json = json + "\"backgroundQ\": [";
			for(int i=0; i<root.getChildren().size(); i++){
				Element parent = (Element)root.getChildren().get(i);
				json = json + "\n\t{";
				parseXML(parent, i+"", "0");
				json = json + "\n\t},";
			}
		}

		json = json + "\n]";
		json = json + "}";

		try{
			FileOutputStream fout = new FileOutputStream(SystemProperties.getInstance().getProperty("appl.home.dir")+File.separator+ "js"+ File.separator+"data1.js");
			fout.write(json.getBytes());
			fout.close();
		}catch(Exception e){
			//e.printStackTrace();
			  logger.error(ctx, "Unable to execute getJson method due to error : " + DataUtils.getExceptionStackTrace(e));
		}

		return json;
	}*/

	/*private static void parseXML(Element parent, String parentIndex, String childIndexId) throws Exception {
		if(parent.getChildren() != null){
			for(int i=0; i<parent.getChildren().size(); i++){
				Element ele = (Element)parent.getChildren().get(i);

				String name = "ck"+parentIndex;
				String yesid = "ajax_field_ck"+parentIndex+"y_0";//"ck"+parentIndex+"y";
				String noid = "ajax_field_ck"+parentIndex+"n_1";//"ck"+parentIndex+"n";

				if(ele.getName().equals("questionnum"))
					json = json + "\n\t\"Q\": " + "\""+ ele.getText() + "\",";
				else if(ele.getName().equals("question"))
					json = json + "\n\t\"Questions\": " + "\""+ ele.getText() + "\",";
				else if(ele.getName().equals("value")){
					String yesOpenChild = "0";
					String noOpenChild = "0";

					if(ele.getAttributeValue("openchild") != null && ele.getAttributeValue("openchild").equals("Y"))
						yesOpenChild = "1";
					else if(ele.getAttributeValue("openchild") != null && ele.getAttributeValue("openchild").equals("Y"))
						noOpenChild = "1";

					if(ele.getText().equals("Y")){
						json = json + "\n\t\"Yes\": " + "\""+ "<input type='radio' name='"+name+"' id='"+yesid+"' onclick='openchild("+yesid+", "+yesOpenChild+")' value='Y' checked='checked'/>" + "\",";
						json = json + "\n\t\"No\": " + "\""+ "<input type='radio' name='"+name+"' id='"+noid+"' onclick='openchild("+noid+", "+noOpenChild+")' value='N'/>" + "\",";
					}else if(ele.getText().equals("N")){
						json = json + "\n\t\"Yes\": " + "\""+ "<input type='radio' name='"+name+"' id='"+yesid+"' onclick='openchild("+yesid+", "+yesOpenChild+")' value='Y'/>" + "\",";
						json = json + "\n\t\"No\": " + "\""+ "<input type='radio' name='"+name+"' id='"+noid+"' onclick='openchild("+noid+", "+noOpenChild+")' value='N' checked='checked'/>" + "\",";
					}else{
						json = json + "\n\t\"Yes\": " + "\""+ "<input type='radio' name='"+name+"' id='"+yesid+"' onclick='openchild("+yesid+", "+yesOpenChild+")' value='Y'/>" + "\",";
						json = json + "\n\t\"No\": " + "\""+ "<input type='radio' name='"+name+"' id='"+noid+"' onclick='openchild("+noid+", "+noOpenChild+")' value='N'/>" + "\",";
					}
				}else if(ele.getName().equals("comment"))
					json = json + "\n\t\"comment\": " + "\""+ "<textarea name='' id=''>"+ele.getText()+"</textarea>" + "\",";
				else if(ele.getName().equals("childquestion")){
					json = json + "\n\t\"subQ"+childIndexId+"\": [";

					for(int j=0; j<ele.getChildren().size(); j++){
						json = json + "\n\t{";
						parseXML((Element)ele.getChildren().get(j), parentIndex+j, childIndexId+0);
						json = json + "\n\t},";
					}

					json = json + "]";
				}
			}
		}
	}*/

	public static String validateJson(Context ctx) throws Exception {
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(new File(SystemProperties.getInstance().getProperty("appl.home.dir")+File.separator+"questions.xml"));
		Element root = doc.getRootElement();

		if(root.getChildren() != null && root.getChildren().size() > 0){
			for(int i=0; i<root.getChildren().size(); i++){
				Element parent = (Element)root.getChildren().get(i);
				validateXML(parent, i+"", "0", ctx);
			}
		}

		ctx.put("xml", new XMLOutputter(Format.getPrettyFormat()).outputString(root));
		return null;
	}

	private static void validateXML(Element parent, String parentIndex, String childIndexId, Context ctx) throws Exception {
		if(parent.getChildren() != null){
			for(int i=0; i<parent.getChildren().size(); i++){
				Element ele = (Element)parent.getChildren().get(i);

				String name = "ck"+parentIndex;
				String yesid = "ck"+parentIndex+"y";
				String noid = "ck"+parentIndex+"n";

				if(ctx.get(name) == null || ctx.get(name).equals("")){
					DataUtils.populateError(ctx, "pageError", "allQuestionsSelectErrorKey");
					return;
				}

				if(ele.getName().equals("value") && ctx.get(name) != null)
					ele.setText(ctx.get(name).toString());
				else if(ele.getName().equals("comment")){

				}else if(ele.getName().equals("childquestion")){
					for(int j=0; j<ele.getChildren().size(); j++){
						validateXML((Element)ele.getChildren().get(j), parentIndex+j, childIndexId+0, ctx);
					}
				}
			}
		}
	}

	public static void submitBGCheckInformation(Context ctx) throws Exception{

		if((ctx.get("isShowOnboardingDashboardBGCheck") != null && ctx.get("isShowOnboardingDashboardBGCheck").toString().equals("N")) ||
				(ctx.get("isSkipBGCheck") != null && (ctx.get("isSkipBGCheck").toString().equals("Y")
					|| ctx.get("isSkipBGCheck").toString().equals("on")))){

			if(logger.isDebugEnabled(ctx))
				logger.debug(ctx, "Going to import agency into ProducerOne with background check skip");

		}else{
			if(logger.isDebugEnabled(ctx))
				logger.debug(ctx, "Going to import agency into ProducerOne with background check on");

			if(ctx.get("isBGOneWorking") != null && ctx.get("isBGOneWorking").toString().equals("Y")){
				ctx.put("agency_id", ctx.get("returnVal"));
				if(ctx.get("product_ids_agent")!=null && !HtmlConstants.EMPTY.equals(ctx.get("product_ids_agent").toString())){
					if (ctx.get("agencyBGCheck_list_1") != null && !HtmlConstants.EMPTY.equals(ctx.get("agencyBGCheck_list_1"))) {
						List agentBGCheckList = (List) ctx.get("agencyBGCheck_list_1");
						if (agentBGCheckList != null && agentBGCheckList.size() > 0) {
							for(int i = 0; i<agentBGCheckList.size(); i++){
								Map map = (Map)agentBGCheckList.get(i);
								if(ctx.get("response_"+i)!= null && !HtmlConstants.EMPTY.equals(ctx.get("response_"+i)) && ctx.get("response_"+i).equals("1")){
									if(map!=null && map.get("person_id")!=null && !HtmlConstants.EMPTY.equals(map.get("person_id").toString())){
										ctx.put("bgrequestfrom", "person");
										ctx.put("public_person_id", map.get("person_id").toString());

										if(logger.isDebugEnabled(ctx))
											logger.debug(ctx, "Going to hit background check service for agent");
										new SendBGCheckRequest().sendBGCheckRequest(ctx);
										if(logger.isDebugEnabled(ctx))
											logger.debug(ctx, "New vendor id generated in database");
										if(ctx.get("inet_errors_list")!=null && !"".equals(ctx.get("inet_errors_list"))){
											return;
										}
									}
								}
							}
						}
					}
				}
				if(ctx.get("product_ids")!=null && !HtmlConstants.EMPTY.equals(ctx.get("product_ids").toString())){
					ctx.put("bgrequestfrom", "entity");
					ctx.put("person_id",null);
					if(logger.isDebugEnabled(ctx))
						logger.debug(ctx, "Going to hit background check service for agency");
					new SendBGCheckRequest().sendBGCheckRequest(ctx);
					if(logger.isDebugEnabled(ctx))
						logger.debug(ctx, "New vendor id generated in database");
					if(ctx.get("inet_errors_list")!=null && !"".equals(ctx.get("inet_errors_list"))){
						return;
					}
				}
			}
		}
	}


	public static void sendDocForEsign(Context ctx){
		try{
			new SendDocForESign().execute(ctx);
		}catch(Exception e){
			//e.printStackTrace();
			  logger.error(ctx, "Unable to execute sendDocForEsign method due to error : " + DataUtils.getExceptionStackTrace(e));
		}
	}

	//Method created to filter audit history data
	public static void filterAuditHistory(Context ctx) throws Exception{
		if(ctx.get("audit_History_New_list_01") != null && ctx.get("audit_History_New_list_01") instanceof List){
			List list = (List)ctx.get("audit_History_New_list_01");
			if(list != null && list.size() > 0){
				List fieldsList = null;

				try{
					fieldsList = FieldsResources.getInstance(ctx).getFieldListByScreen(ctx, ctx.get("report_id").toString());
				}catch(Exception e){
					//do nothing
				}

				for(int i=0; i<list.size(); i++){
					Map map = (Map)list.get(i);

					if(map.get("Field_Id") != null && !map.get("Field_Id").toString().equals(HtmlConstants.EMPTY)){
						String fieldId = map.get("Field_Id").toString();
						String fieldLabel = null;

						if(fieldsList != null && fieldsList.size() > 0){
							for(int j=0; j<fieldsList.size(); j++){
								FieldImpl fldImpl = (FieldImpl)fieldsList.get(j);

								String fieldName = fldImpl.getName() != null ? fldImpl.getName() : null;
								if(fieldName.equals(fieldId)){
									fieldLabel = fldImpl.getLabeldesc();
								}
							}
						}

						if(fieldLabel == null){
							com.manage.managemetadata.metadata.FieldImpl fldImpl = MetaDataResources.getInstance(ctx).getField(fieldId);
							if(fldImpl != null){
								fieldLabel = DataUtils.getLabelFromLabelConf(fldImpl.getDisplayname());
							}
						}

						if(fieldLabel != null && map.get("HistoryText") != null)
							map.put("HistoryText", map.get("HistoryText").toString().replace(fieldId+" ", fieldLabel+" "));
					}
				}
			}
		}
	}
	public static void validateProductValidationList(Context ctx) throws Exception {
		String nbThresholdrate = null;
		String rbThresholdrate = null;
		String poliservfeepercentage = null;
		String mvrcluepercentage = null;
		String clue_Threshold = null;
		if(ctx.get("productValidationDetail_list_1") != null && ctx.get("productValidationDetail_list_1") instanceof List){
			List productValidationList = (List) ctx.get("productValidationDetail_list_1");
			if(productValidationList != null && !HtmlConstants.EMPTY.equals(productValidationList) && productValidationList.size()>0){
				for(int i=0; i<productValidationList.size();i++){
					 Map productValidationListMap = (Map) productValidationList.get(i);

					 if(ctx.get("effective_date_"+i) != null && StringUtils.isNotBlank(ctx.get("effective_date_"+i).toString())){
						 if(DateUtils.isDateBefore(ctx.get("effective_date_"+i), new Date("01/01/1900"))){
							 DataUtils.populateError((Context)ctx, "pageError", "InvalidDateErrorKey");
							 return;
						 }
					 }
					 if(ctx.get("effective_date_"+i) != null && StringUtils.isNotBlank(ctx.get("effective_date_"+i).toString()) && !ProducerOneUtils.validateDate(ctx.get("effective_date_"+i).toString())){
						DataUtils.populateError((Context)ctx, "pageError", "InvalidDateErrorKey");
						return;
					 }
					 if(ctx.get("expire_new_date_"+i) != null && StringUtils.isNotBlank(ctx.get("expire_new_date_"+i).toString())){
						 if(DateUtils.isDateBefore(ctx.get("expire_new_date_"+i), new Date("01/01/1900"))){
							 DataUtils.populateError((Context)ctx, "pageError", "InvalidDateErrorKey");
							 return;
						 }
					 }
					 if(ctx.get("expire_new_date_"+i) != null && StringUtils.isNotBlank(ctx.get("expire_new_date_"+i).toString()) && !ProducerOneUtils.validateDate(ctx.get("expire_new_date_"+i).toString())){
						DataUtils.populateError((Context)ctx, "pageError", "InvalidDateErrorKey");
						return;
					 }
					 if(ctx.get("expire_renew_date_"+i) != null && StringUtils.isNotBlank(ctx.get("expire_renew_date_"+i).toString())){
						if(DateUtils.isDateBefore(ctx.get("expire_renew_date_"+i), new Date("01/01/1900"))){
							 DataUtils.populateError((Context)ctx, "pageError", "InvalidDateErrorKey");
							 return;
						 }
					 }
					 if(ctx.get("expire_renew_date_"+i) != null && StringUtils.isNotBlank(ctx.get("expire_renew_date_"+i).toString()) && !ProducerOneUtils.validateDate(ctx.get("expire_renew_date_"+i).toString())){
						DataUtils.populateError((Context)ctx, "pageError", "InvalidDateErrorKey");
						return;
					 }
					 if(ctx.get("expire_new_date_" +i)!= null && !HtmlConstants.EMPTY.equals(ctx.get("expire_new_date_" +i))
							&& ctx.get("expire_renew_date_" +i)!= null && !HtmlConstants.EMPTY.equals(ctx.get("expire_renew_date_" +i))){
						 if(DateUtils.isDateBefore(ctx.get("expire_renew_date_"+i),ctx.get("expire_new_date_"+i))){
                             DataUtils.populateError((Context)ctx,"pageError",DataUtils.getMessage(ctx, "ExpirationNewReNewDateErrorKey"));
                             return;
						 }
					}
					if(ctx.get("effective_date_"+i) != null && StringUtils.isNotBlank(ctx.get("effective_date_"+i).toString())
							 && ctx.get("expire_renew_date_"+i) != null && StringUtils.isNotBlank(ctx.get("expire_renew_date_"+i).toString())){

						 if(DateUtils.isDateBefore(ctx.get("expire_renew_date_"+i),ctx.get("effective_date_"+i))){
                             DataUtils.populateError((Context)ctx,"pageError",DataUtils.getMessage(ctx, "ExpireRenewEffectiveDateMsgKey"));
                             return;
						 }
					 }
					if(!HtmlConstants.EMPTY.equals(ctx.get("expire_new_date_" +i)) && !HtmlConstants.EMPTY.equals(ctx.get("effective_date_" +i))){
						if(DateUtils.isDateAfter(ctx.get("effective_date_" +i), ctx.get("expire_new_date_" +i))){
							DataUtils.populateError((Context)ctx,"pageError",DataUtils.getMessage(ctx, "expireNewDatemustbegreaterthanEffectiveDateErrorKey"));
                            return;
						}
					}
					if(ctx.get("nb_threshold_rate_"+i) != null && !HtmlConstants.EMPTY.equals(ctx.get("nb_threshold_rate_"+i))){
						nbThresholdrate = ctx.get("nb_threshold_rate_"+i).toString();
						Double ndb = null;
						try {
							ndb = Double.parseDouble(nbThresholdrate);
							 if(ndb >100){
                                 DataUtils.populateError((Context)ctx, "pageError", "notMoreThan100NBRateThresholdErrorMsg");
                                 return;
                             }
						} catch (Exception e) {
							DataUtils.populateError((Context)ctx, "pageError", "validNBRateThresholdErrorMsg");
							return;
						}

					}
					if(ctx.get("rb_threshold_rate_"+i) != null && !HtmlConstants.EMPTY.equals(ctx.get("rb_threshold_rate_"+i))){
						rbThresholdrate = ctx.get("rb_threshold_rate_"+i).toString();
						Double rdb =null;
						try {
							rdb = Double.parseDouble(rbThresholdrate);
							if(rdb >100){
                                DataUtils.populateError((Context)ctx, "pageError", "notMoreThan100RBRateThresholdErrorMsg");
                                return;
                            }
						} catch (Exception e) {
							DataUtils.populateError((Context)ctx, "pageError", "validRBRateThresholdErrorMsg");
							return;
						}

					}
					if(ctx.get("mvr_clue_threshold_"+i) != null && !HtmlConstants.EMPTY.equals(ctx.get("mvr_clue_threshold_"+i))){
						mvrcluepercentage = ctx.get("mvr_clue_threshold_"+i).toString();
						Double mvrcluepercentageVal = null;
						try {
							mvrcluepercentageVal = Double.parseDouble(mvrcluepercentage);
							if(mvrcluepercentageVal >100){
                                DataUtils.populateError((Context)ctx, "pageError", "notMoreThan100MVRThresholdErrorMsg");
                                return;
                            }
						} catch (Exception e) {
							DataUtils.populateError((Context)ctx, "pageError", "validMVRThresholdErrorMsg");
							return;
						}
					}
					if(ctx.get("Clue_Threshold_"+i) != null && !HtmlConstants.EMPTY.equals(ctx.get("Clue_Threshold_"+i))){
						clue_Threshold = ctx.get("Clue_Threshold_"+i).toString();
						Double clue_ThresholdVal = null;
						try {
							clue_ThresholdVal = Double.parseDouble(clue_Threshold);
							if(clue_ThresholdVal >100){
                                DataUtils.populateError((Context)ctx, "pageError", "notMoreThan100ClueThresholdErrorMsg");
                                return;
                            }
						} catch (Exception e) {
							DataUtils.populateError((Context)ctx, "pageError", "validClueThresholdErrorMsg");
							return;
						}
					}
				}
			}
		}
	}

	//Method created to check agency management system is other or not
	public static Boolean isAgencyManagementSystemIsOther(Context ctx) throws Exception{
		try{
			Map map = (Map)SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.sqlStatementsviewgetAgencyManagementSystemDescription", ctx);
			if(map != null && map.get("master_lookup_data_desc") != null && map.get("master_lookup_data_desc").toString().equalsIgnoreCase("Other"))
				return Boolean.TRUE;
		}catch(Exception e){
			logger.error("Unable to execute isAgencyManagementSystemIsOther due to error : " + DataUtils.getExceptionStackTrace(e));
		}

		return Boolean.FALSE;
	}

	//Method created to check agency comparative rate is other or not
	public static Boolean isAgencyComparativeRateIsOther(Context ctx) throws Exception{
		try{
			Map map = (Map)SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.sqlStatementsviewgetAgencyComparativeRateDescription", ctx);
			if(map != null && map.get("master_lookup_data_desc") != null && map.get("master_lookup_data_desc").toString().equalsIgnoreCase("Other"))
				return Boolean.TRUE;
		}catch(Exception e){
			logger.error("Unable to execute isAgencyComparativeRateIsOther due to error : " + DataUtils.getExceptionStackTrace(e));
		}

		return Boolean.FALSE;
	}

	//Method created to set objectid/type for object history
	public static void setObjectTypeForHistory(Context ctx){
		if(ctx.get("NEXT_PAGE") != null){
			String objectType = "";
			String objectId = "";

			String nextPage = ctx.get("NEXT_PAGE").toString();

			if("entityGeneralDetails".equals(nextPage)){
				objectType = "entityGeneralDetails";
				objectId = ctx.get("agency_id") != null ? ctx.get("agency_id").toString() : "";
			}else if("agentGeneralDetails".equals(nextPage)){
				objectType = "agentGeneralDetails";
				objectId = ctx.get("person_id") != null ? ctx.get("person_id").toString() : "";
			}else if("agencyProducer".equals(nextPage)){
				objectType = "agencyProducer";
				objectId = ctx.get("person_id") != null ? ctx.get("person_id").toString() : "";
			}else if("producerAssociation".equals(nextPage)){
				objectType = "producerAssociation";
				objectId = ctx.get("person_id") != null ? ctx.get("person_id").toString() : "";
			}else if("producerDetail".equals(nextPage)){
				objectType ="producerDetail";
				objectId = ctx.get("producer_number_id") != null ? ctx.get("producer_number_id").toString() : "";
			}else if("producerBankInformationDetail".equals(nextPage)){
				objectType = "producerBankInformationDetail";
				objectId = ctx.get("producer_number_id") != null ? ctx.get("producer_number_id").toString() : "";
			}else if("productValidationDetail".equals(nextPage)){
				objectType = "productValidationDetail";
				objectId = ctx.get("producer_number_id") != null ? ctx.get("producer_number_id").toString() : "";
			}

			if(!"".equals(objectType)){
				ctx.put("objecttype_desc", objectType);
				ctx.put("object_id", objectId);
			}

			//new DataUtils().getExtendedFieldXMLForScreen(ctx, nextPage, "xml");
		}
	}

	//Method created to get forward page while click on administration link
	public static void getAdminForwardPage(Context ctx) throws Exception{
		try{
			String roles = ctx.get("roles").toString();

			if(roles.equalsIgnoreCase("CarrierAdmin") || roles.equalsIgnoreCase("MarketingAssistant") || roles.equalsIgnoreCase("MarketingRep") ||
					roles.equalsIgnoreCase("acturial") || roles.equalsIgnoreCase("accountinganalyst")){
				ctx.put("tabs1next_page", "acquistions");
				ctx.put("tabs2next_page", "mergeracquistions");
				ctx.put(HtmlConstants.WORKFLOW_FORWARD, "mergeracquistions");
				return;
			}else if(roles.equalsIgnoreCase("SalesDirector") || roles.equalsIgnoreCase("treasury") || roles.equalsIgnoreCase("vpmarketing")){
				ctx.put("tabs1next_page", "commissionManagement");
				ctx.put("tabs2next_page", "incentiveSchedules");
				ctx.put(HtmlConstants.WORKFLOW_FORWARD, "compensationIncentiveSchedule");
				return;
			}else if(roles.equalsIgnoreCase("compensationanalyst")){
				ctx.put("tabs1next_page", "commissionManagement");
				ctx.put("tabs2next_page", "commissionSchedules");
				ctx.put(HtmlConstants.WORKFLOW_FORWARD, "compensationCommissionSchedule");
				return;
			}else if(roles.equalsIgnoreCase("it")){
				ctx.put("tabs1next_page", "lookupTables");
				ctx.put("tabs2next_page", null);
				ctx.put(HtmlConstants.WORKFLOW_FORWARD, "lookupTables");
				return;
			}else if(roles.equalsIgnoreCase("accountantadmin")){
				ctx.put("tabs1next_page", "flexiInfoMapping");
				ctx.put("tabs2next_page", null);
				ctx.put(HtmlConstants.WORKFLOW_FORWARD, "flexiInfoMapping");
				return;
			}
		}catch(Exception e){
			logger.error(ctx, "Unable to get admin forward page due to error : " + e.getMessage());
		}
	}

	//Method created to get forward page while click on compensation tab in admin
	public static void getAdminCompensationForwardPage(Context ctx) throws Exception{
		try{
			String roles = ctx.get("roles").toString();

			if(roles.equalsIgnoreCase("CarrierAdmin") || roles.equalsIgnoreCase("MarketingAssistant") || roles.equalsIgnoreCase("SalesDirector")
					|| roles.equalsIgnoreCase("accountantadmin") || roles.equalsIgnoreCase("acturial") || roles.equalsIgnoreCase("accountinganalyst")
					|| roles.equalsIgnoreCase("treasury") || roles.equalsIgnoreCase("it")){
				ctx.put("tabs1next_page", "commissionManagement");
				ctx.put("tabs2next_page", "incentiveSchedules");

				if(DataUtils.getAccessType(ctx, "adminTabs2_commissionSchedule_security") != 0){
					ctx.put("tabs1next_page", "commissionManagement");
					ctx.put("tabs2next_page", "commissionSchedules");
					ctx.put(HtmlConstants.WORKFLOW_FORWARD, "compensationCommissionSchedule");
					return;
				}

				ctx.put(HtmlConstants.WORKFLOW_FORWARD, "compensationIncentiveSchedule");
				return;
			}else if(roles.equalsIgnoreCase("MarketingRep") || roles.equalsIgnoreCase("compensationanalyst")){
				ctx.put("tabs1next_page", "commissionManagement");
				ctx.put("tabs2next_page", "commissionSchedules");
				ctx.put(HtmlConstants.WORKFLOW_FORWARD, "compensationCommissionSchedule");
				return;
			}
		}catch(Exception e){
			logger.error(ctx, "Unable to get admin forward page due to error : " + e.getMessage());
		}
	}
	public static void checkAddNewBillingTermDuplicateRecord(Context ctx) throws Exception{
		try {
			Map billingTermCountMap = null;
			if(ctx.get("producer_number_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("producer_number_id"))
						&& ctx.get("billing_delivery_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("billing_delivery_id"))
						&& ctx.get("billing_method_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("billing_method_id"))){

				if(ctx.get("billing_terms_id") == null || HtmlConstants.EMPTY.equals(ctx.get("billing_terms_id")))
					billingTermCountMap = (HashMap) SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.sqlStatementsviewgetAddNewBillingTermCount", ctx);
				else
					billingTermCountMap = (HashMap) SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.sqlStatementsviewgetUpdateNewBillingTermCount", ctx);
				if(billingTermCountMap != null && !billingTermCountMap.isEmpty()){
					if(billingTermCountMap.get("billing_term_count") != null && !HtmlConstants.EMPTY.equals(billingTermCountMap.get("billing_term_count"))){
						int billingTermCount =0;
						billingTermCount = Integer.parseInt(billingTermCountMap.get("billing_term_count").toString());
						if(billingTermCount >0){
							DataUtils.populateError((Context)ctx, "pageError", "adminDuplicateError");
							return;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(ctx, "Unable to check Add New Billing Term Duplicate Record : " + e.getMessage());
		}
	}
	public static void checkMassReassign(Context ctx) throws Exception{
		try {

			/*int count=0;
			 if(ctx.get("massReassign_list_mom_1")!= null){
		            List list = (List) ctx.get("massReassign_list_mom_1");
		            for(int i=0;i<list.size();i++){
		            if (ctx.get("request_id_"+i)!=null && "Y".equals(ctx.get("request_id_"+i))){
		                count=count+1;
		                    }
		                }
		            }

			 if(count<=0){
					DataUtils.populateError((Context)ctx, "pageError", "msgselectrecordforletter1ErrorKey");
					return;
				}*/

			boolean errorFlag=false;

			if(ctx.get("request_ids")== null || HtmlConstants.EMPTY.equals(ctx.get("request_ids"))){
				DataUtils.populateError((Context)ctx, "pageError", "msgselectrecordforletter1ErrorKey");
				errorFlag=true;
			}



			if(ctx.get("massreassign_workflow_user")== null || HtmlConstants.EMPTY.equals(ctx.get("massreassign_workflow_user"))){
				DataUtils.populateError((Context)ctx, "massreassign_workflow_user", "massReassignWorkflowUserSelectErrorKey");
				errorFlag=true;
			}

			if(errorFlag)
			{
				return;
			}else{
				ctx.put("showMassReAssignPopup", "Y");
			}

		} catch (Exception e) {
			logger.error(ctx, "Unable to check Mass Reassign : " + e.getMessage());
		}
	}

	public static void checkAppointmentTerminationButtons(Context ctx) throws Exception{
		try {
			List momList=null;
			List appointmentList = null;
			int listSize =0;
			ctx.put("NiprStates", ctx.get("NiprStates") != null && !HtmlConstants.EMPTY.equals(ctx.get("NiprStates")) ? Integer.parseInt(ctx.get("NiprStates").toString()) : null);
			ctx.put("AuthStates", ctx.get("AuthStates") != null && !HtmlConstants.EMPTY.equals(ctx.get("AuthStates")) ?  Integer.parseInt(ctx.get("AuthStates").toString()) : null);
			ctx.put("allLicense", ctx.get("allLicense") != null && !HtmlConstants.EMPTY.equals(ctx.get("allLicense")) ?  Integer.parseInt(ctx.get("allLicense").toString()) : null);
			ctx.put("selCompanyID", null);
			if(ctx.get("isApppointPage").equals("entityAppoint")){
				
				//momList = (List)SqlResources.getSqlMapProcessor(ctx).select("agencymaster_type.GetAgencyLicensesAppointmentsWithManual_p_java", ctx);
				momList = ctx.get("AgencyLicensesAppointments_mom_list_0") != null && !HtmlConstants.EMPTY.equals(ctx.get("AgencyLicensesAppointments_mom_list_0")) ? (List) ctx.get("AgencyLicensesAppointments_mom_list_0") : null;
				if(momList != null && momList.size() >0){
					//appointmentList = (List)momList.get(0);
					if(momList != null && momList.size()>0){
						listSize = momList.size();
						ctx.put("appointmentTerminationListSize", listSize);
					}else{
						ctx.put("appointmentTerminationListSize", listSize);
					}
				}else{
					ctx.put("appointmentTerminationListSize", listSize);
				}
			}else if(ctx.get("isApppointPage").equals("agentAppoint")){
				 
				//momList = (List)SqlResources.getSqlMapProcessor(ctx).select("agencymaster_type.GetAgentLicensesAppointmentsWithManual_p_java", ctx);
				momList = ctx.get("getAgentLicesneDetails_mom_list_0") != null && !HtmlConstants.EMPTY.equals(ctx.get("getAgentLicesneDetails_mom_list_0")) ? (List) ctx.get("getAgentLicesneDetails_mom_list_0") : null;
				if(momList != null && momList.size() >0){
						//appointmentList = (List)momList.get(0);
						if(momList != null && momList.size()>0){
							listSize = momList.size();
							ctx.put("appointmentTerminationListSize", listSize);
						}else{
							ctx.put("appointmentTerminationListSize", listSize);
						}
					}else{
						ctx.put("appointmentTerminationListSize", listSize);
					}
			}
		} catch (Exception e) {
			logger.error(ctx, "Unable to check Appointment Termination Buttons : " + e.getMessage());
		}
	}

	public static void selectAllProducerNumber(Context ctx){
		if("on".equals(ctx.get("select_all"))){
			List<Object> massChangeList= (ArrayList<Object>)ctx.get("massChange1_list_mom_1");
			List<Object> massContractList = (ArrayList<Object>)ctx.get("massContractGenerationStep1_list_mom_1");
			String pnids=(String)ctx.get("producer_number_ids");
			if(massChangeList != null && massChangeList.size()>0){
				for(Object prdcr : massChangeList){
					HashMap<Object, Object> map = (HashMap<Object, Object>) prdcr;
					if(pnids.indexOf(map.get("producer_number_id").toString())== -1)
					pnids = pnids.concat(","+map.get("producer_number_id").toString());
				}
				ctx.put("producer_number_ids", pnids.toString());
			}else if(massContractList != null && massContractList.size()>0) {
				String agencyIds=(String)ctx.get("agency_ids");
				for(Object prdcr : massContractList){
					HashMap<Object, Object> map = (HashMap<Object, Object>) prdcr;
					if(pnids.indexOf(map.get("producer_number_id").toString())== -1)
						pnids = pnids.concat(","+map.get("producer_number_id").toString());
					
					if(agencyIds.indexOf(map.get("agency_id").toString())== -1)
						agencyIds = agencyIds.concat(","+map.get("agency_id").toString());
				}
				ctx.put("producer_number_ids", pnids.toString());
				ctx.put("agency_ids", agencyIds.toString());
			}
		}

	}


	public static Object validateSubscribersForm(Context ctx) throws Exception{
		if(ctx.get("subscriber_master_code") == null || "".equals(ctx.get("subscriber_master_code"))){
			DataUtils.populateError(ctx, "subscriber_master_code", "subscriber_keyErrorKey");
		}
		if(ctx.get("subscriber_master_description") == null || "".equals(ctx.get("subscriber_master_description"))){
			DataUtils.populateError(ctx, "subscriber_master_description", "subscriber_DescriptionErrorKey");
		}
		return null;
	}
	public static Object validateSubscriberAssociation(Context ctx) throws Exception{
		if(ctx.get("hidden_ajax_field_subscriber_id") == null || "".equals(ctx.get("hidden_ajax_field_subscriber_id"))){
			DataUtils.populateError(ctx, "lblmessagesubscriber", "subscriberSelect_keyErrorKey");
		}
		if(ctx.get("hidden_ajax_field_ce_id") == null || "".equals(ctx.get("hidden_ajax_field_ce_id"))){
			DataUtils.populateError(ctx, "pageMsg", "subscriber_customEventsSelectErrorKey");
		}
		return null;
	}
	/**
  	 * This Function help us Update Licensing Batch Status
  	 * @param ctx
  	 * @throws Exception
  	 */
	public static void updateLicensingBatchStatus(Context ctx) throws Exception{
  		try {
  			NIPRONEService service=new NIPRONEService();
			int ClientID=Integer.parseInt(ctx.get("Client_ID").toString());
			if(ctx.get("licensing_batch_transaction_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("licensing_batch_transaction_id"))){
				long batchID=Long.parseLong(ctx.get("licensing_batch_transaction_id").toString());
				TransEntitiesStatus transLicensingStatus = service.getBatchStatusLic(ctx, batchID, true,ClientID);
				if(transLicensingStatus!=null){
					if(transLicensingStatus.getTransactions()!=null){
						for(int i=0;i<transLicensingStatus.getTransactions().getTransactionEntityStatus().size();i++){
							TransactionEntityStatus licensingStatus= transLicensingStatus.getTransactions().getTransactionEntityStatus().get(i);
							if(licensingStatus != null){
						    	if(licensingStatus.getComments() != null && !HtmlConstants.EMPTY.equals(licensingStatus.getComments()))
						    		ctx.put("comments", licensingStatus.getComments());
						    	else
						    		ctx.put("comments", null);
						    	if(licensingStatus.getErrorCode() != null && !HtmlConstants.EMPTY.equals(licensingStatus.getErrorCode()))
						    		ctx.put("errorcode", licensingStatus.getErrorCode());
						    	else
						    		ctx.put("errorcode", null);
						    	if(licensingStatus.getStateFee() != null && !HtmlConstants.EMPTY.equals(licensingStatus.getStateFee()))
						    		ctx.put("nipr_state_licensing_fee", licensingStatus.getStateFee());
						    	else
						    		ctx.put("nipr_state_licensing_fee", null);
						    	if(licensingStatus.getNIPRFee() != null && !HtmlConstants.EMPTY.equals(licensingStatus.getNIPRFee()))
						    		ctx.put("nipr_txn_fee_licensing", licensingStatus.getNIPRFee());
						    	else
						    		ctx.put("nipr_txn_fee_licensing", null);
						    	if(!HtmlConstants.EMPTY.equals(licensingStatus.getStatus()))
						    		ctx.put("nipr_licensing_status", licensingStatus.getStatus());

						    	if(licensingStatus.getTransactionID() != null && !HtmlConstants.EMPTY.equals(licensingStatus.getTransactionID()))
						    		ctx.put("licensing_transaction_id", licensingStatus.getTransactionID());
						    	else
						    		ctx.put("licensing_transaction_id", null);

						    	if(ctx.get("nipr_licensing_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("nipr_licensing_id")))
						    		ctx.put("nipr_licensing_id", Integer.parseInt(ctx.get("nipr_licensing_id").toString()));

						    	if(ctx.get("object_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("object_id")))
						    		ctx.put("object_id", Integer.parseInt(ctx.get("object_id").toString()));

						    	if(ctx.get("user_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("user_id")))
						    		ctx.put("last_updated_by", ctx.get("user_id").toString());

						    	new SetParametersForStoredProcedures().setParametersInContext(ctx, "last_updated_by,object_id,nipr_licensing_id,nipr_licensing_status,nipr_state_licensing_fee,nipr_txn_fee_licensing,errorcode,comments,licensing_batch_transaction_id,licensing_transaction_id");

						    	SqlResources.getSqlMapProcessor(ctx).update("licensing.UpdateLicensingTxnlicenseClassLoa_p",ctx);
						    	SqlResources.getSqlMapProcessor(ctx).update("licensing.UpdateLicensingStatus_p",ctx);

							}
						}
					}
				}
  			}
		} catch (Exception e) {
			logger.error("Error While update Licensing Batch Status : "+ e.getMessage());
			DataUtils.populateError((Context) ctx,"msgserver","unableToProcessRequestMsgKey");
		}
  	}
	/**
  	 * This Function help us generate Licensing List freeform xml and put it in Context
  	 * @param ctx
  	 * @param firstxmlname
  	 * @param secondxmlname
  	 * @throws Exception
  	 */
	public static void generateLicensingListFreeFormXmls(Context ctx, String outputXMLName,String outputXMLNameNew) throws Exception{
  		try {
  			String listName = null;
  	    	String listNameNew = null;

  	    		if(ctx.get(Constants.INET_PAGE).toString().equals("licensingRequestProducersEmploymentHistory")){
  	    			listName = "designatedresponsibleproducers_list_listfreeform_1";
  	    			listNameNew = "employmenthistory_list_listfreeform_1";
  	    			if(ctx.get(listName) != null ){
  	    				ArrayList<Object> list = (ArrayList<Object>)ctx.get(listName);
  	    				if(list.size() > 0){
  	    					ProducerOneUtils.convertListDataToXML(ctx, listName, outputXMLName);
  	    				}else{
  	    					DataUtils.populateError(ctx, listName, "Please enter details for Responsible Producers");
  	    				}
  	    			}else{
  	    				DataUtils.populateError(ctx, listName, "Please enter details for Responsible Producers");
  	    			}
  	    			if(ctx.get(listName) != null ){
  	    				ArrayList<Object> list = (ArrayList<Object>)ctx.get(listNameNew);
  	    				if(list.size() > 0){
  	    					ProducerOneUtils.convertListDataToXML(ctx, listNameNew, outputXMLNameNew);
  	    				}else{
  	    					DataUtils.populateError(ctx, listNameNew, "Please enter details for Employment History");
  	    				}
  	    			}else{
  	    				DataUtils.populateError(ctx, listNameNew, "Please enter details for Employment History");
  	    			}

  	    			return;
  				}

  	    		if(ctx.get(Constants.INET_PAGE).toString().equals("licensingOwnersPartnersOfficersDirectorsStep")){
  	    			listName = "ownersPartnersOfficersAndDirectors_list_listfreeform_1";
  	    			if(ctx.get(listName) != null ){
  	    				ArrayList<Object> list = (ArrayList<Object>)ctx.get(listName);
  	    				if(list.size() > 0 && isOwnerDetailPresent(list)){
  	    					ProducerOneUtils.convertListDataToXML(ctx, listName, outputXMLName);
  	    				}else{
  	    					DataUtils.populateError(ctx, listName, "Please enter details for Owners, Partners, Officers and Directors");
  	    				}
  	    			}else{
  	    				DataUtils.populateError(ctx, listName, "Please enter details for  Owners, Partners, Officers and Directors");
  	    			}
  	    			return;
  				}
  	    		if(ctx.get(Constants.INET_PAGE).toString().equals("licensingDetailStep5")){
  	    			listName = "aliases_list_listfreeform_1";
  	    			if(ctx.get(listName) != null ){
  	    				ArrayList<Object> list = (ArrayList<Object>)ctx.get(listName);
  	    				if(list.size() > 0){
  	    					ProducerOneUtils.convertListDataToXML(ctx, listName, outputXMLName);
  	    				}else{
  	    					DataUtils.populateError(ctx, listName, "Please enter alias details");
  	    				}
  	    			}else{
  	    				DataUtils.populateError(ctx, listName, "Please enter alias details");
  	    			}
  	    			return;
  	    		}
  	    		if(ctx.get(Constants.INET_PAGE).toString().equals("addConferenceAwards")){
  	    			listName = "ConferenceAwards_list_listfreeform_1";
  	    			ProducerOneUtils.convertListDataToXML(ctx, listName, outputXMLName);
  	    			return;
  	    		}


  		}catch(Exception e){
  			if(logger.isDebugEnabled(ctx))
  				logger.debug(ctx, "Error While generating Licensing List Free Form Xmls : "+ e.getMessage() );
  		}
  	}
	public static boolean isOwnerDetailPresent(ArrayList<Object> list){
  		for(int i = 0;i<list.size();i++){
  			Map map = (HashMap<Object, Object>)list.get(i);
  			if(map.get("is_owner")!= null){
  				if(map.get("is_owner").equals("1")){
  					return true;
  				}
  			}
  		}
  		return false;
  	}
	public static ArrayOfLicenseFR setArrayOfLicenseFR(Context ctx, List list, Map map, String applicationType, String licenseType) throws Exception{
  		ArrayOfLicenseFR fr = new ArrayOfLicenseFR();
  		ArrayOfLicenseLoaFR loafr = null;
  		LicenseLoaFR llfr = null;
  		LicenseFR lfr = null;
  		for (int i=0;i<list.size();i++){
  			lfr = new LicenseFR();
  			loafr =new ArrayOfLicenseLoaFR();
  			String licenseTypeLOA[] = null;
  			Map listMap = (HashMap) list.get(i);
  			if(listMap != null && !listMap.isEmpty()){
  				if("2".equals(applicationType) && map.get("license_num_renewal") != null){
  					lfr.setId((String)map.get("license_num_renewal"));
  				}
  				if(listMap.get("license_type_code") != null && !HtmlConstants.EMPTY.equals(listMap.get("license_type_code")))
  					lfr.setClassCode(listMap.get("license_type_code").toString());
  				if(listMap.get("expireDate") != null && !HtmlConstants.EMPTY.equals(listMap.get("expireDate")))
  					lfr.setExpireDate(listMap.get("expireDate").toString());
  				else
  					lfr.setExpireDate("2000-01-01");
  				if(listMap.get("issueDate") != null && !HtmlConstants.EMPTY.equals(listMap.get("issueDate")))
  					lfr.setIssueDate(listMap.get("issueDate").toString());
  				else
  					lfr.setIssueDate("2000-01-01");
  				if(listMap.get("state") != null && !HtmlConstants.EMPTY.equals(listMap.get("state")))
  					lfr.setStateCode(listMap.get("state").toString());
  				if(map.get("lic_residency_type") != null && !HtmlConstants.EMPTY.equals(map.get("lic_residency_type")))
  					lfr.setType(map.get("lic_residency_type").toString());
  				if(listMap.get("license_type_loa") != null && !HtmlConstants.EMPTY.equals(listMap.get("license_type_loa"))){
  					licenseTypeLOA =listMap.get("license_type_loa").toString().split(",");
  					for(int loaid=0;loaid<licenseTypeLOA.length;loaid++){
  						llfr = new LicenseLoaFR();
  						llfr.setLoaCode(licenseTypeLOA[loaid]);
  						loafr.getLicenseLoaFR().add(llfr);
  					}
  					lfr.setLoas(loafr);
				}
  			}
  			fr.getLicenseFR().add(lfr);
  		}
  		return fr;
  	}
	/**
  	 * This Function validate License Estimated Fees on Step4
  	 * @param ctx
  	 * @throws Exception
  	 */
	public static String validateLicenseEstimatedFees(Context ctx) throws Exception{
  		try {
  			String URL = null;
  			int ClientID =0;
  			ClientID=TabsConfiguration.Client_ID;
  			TripleDESEncryptionDecryption tripleDESEncryptionDecryption=new TripleDESEncryptionDecryption();
  			NIPRONEService niprService=new NIPRONEService();
  			FeeRequest feeRequest = new FeeRequest();
  			FeeResponse feeResponse = new FeeResponse();
  			new SetParametersForStoredProcedures().setParametersInContext(ctx, "nipr_licensing_id,object_id,TYPE");
  			Map map = (HashMap) SqlResources.getSqlMapProcessor(ctx).findByKey("licensing.GetLicensingDetailStep2_p", ctx);
  			List  licensingList = (List) SqlResources.getSqlMapProcessor(ctx).select("licensing.GetLicensingLicenseClassLoa_p", ctx);
  			ctx.put("feeLicenseList", licensingList);
  			ctx.put("feeStep2Map", map);
  			if(map != null && !map.isEmpty()){
   				String NPN = map.get("txtEntityNPN") != null && !HtmlConstants.EMPTY.equals(map.get("txtEntityNPN")) ? map.get("txtEntityNPN").toString(): null;
  	  			String FEIN = map.get("txtFEIN") != null && !HtmlConstants.EMPTY.equals(map.get("txtFEIN")) ? tripleDESEncryptionDecryption.decrypt(map.get("txtFEIN").toString()) : null;
  	  			String agencyOfficeName = map.get("txtEntityOfficeName") != null && !HtmlConstants.EMPTY.equals(map.get("txtEntityOfficeName")) ? map.get("txtEntityOfficeName").toString(): null;
  	  			String license_num= map.get("license_num") != null && !HtmlConstants.EMPTY.equals(map.get("license_num")) ? map.get("license_num").toString(): null;;
  	  			String residentstate_code = map.get("residentstate_code") != null && !HtmlConstants.EMPTY.equals(ctx.get("residentstate_code")) ? map.get("residentstate_code").toString(): null;
  	  			String preparer = map.get("preparer") != null && !HtmlConstants.EMPTY.equals(map.get("preparer")) ? map.get("preparer").toString(): null;
  	  			String states = map.get("states") != null && !HtmlConstants.EMPTY.equals(map.get("states")) ? ctx.get("states").toString(): null;
  	  			String licenseType = map.get("residency_type") != null && !HtmlConstants.EMPTY.equals(map.get("residency_type"))? map.get("residency_type").toString(): null;
  	  			String applicationType = ctx.get("application_type") != null && !HtmlConstants.EMPTY.equals(ctx.get("application_type"))? ctx.get("application_type").toString(): null;

  	  			String requestType =LicensingConstant.LICENSE_TRANSACTION_FEE_TASK;
  	  			boolean isNonResident = "2".equals(licenseType);
  	  			if(logger.isDebugEnabled(ctx))
  	  				logger.debug(ctx, "NPN :"+NPN+" FEIN :"+FEIN+" EntityOfficeName :"+agencyOfficeName+" License Num :"+license_num+" Resident State Code :"+residentstate_code);
  	  			URL = SystemProperties.getInstance().getString("appl."+ctx.getProject()+".niprone.URL");
  	  			feeRequest.setApplicant(ProducerOneUtility.setLicensingFeeRequestApplicantInfo(ctx, NPN, FEIN, agencyOfficeName,isNonResident));

  	  			feeRequest.setTransactionInfo(ProducerOneUtility.setLicensingTransactionInfo(ctx, licenseType, states,requestType, applicationType));
  	  			ArrayOfLicenseFR afr =  null;
  	  			if(isNonResident)
  	  			 afr = setArrayOfLicenseFR(ctx, licensingList, map,applicationType,licenseType);
  	  			else{
  	  			 afr = setArrayOfLicenseFR(ctx, licensingList, map,applicationType,licenseType);
  	  			}
  	  			feeRequest.setFeeLicenseList(afr);


  			}
  			try {
  				if(logger.isDebugEnabled(ctx))
  					logger.debug(ctx, "Going to Hit Service URL : "+URL);

  				String feexml=getXMLOutput("com.osi.nipr", feeRequest, "http://CNIPROne.org/");
  				if(logger.isDebugEnabled(ctx))
  					logger.debug(ctx, "XML---> "+feexml);
  				feeResponse = niprService.CheckFeeDetailRequest(ctx, feeRequest, ClientID);
  				if(logger.isDebugEnabled(ctx))
  					logger.debug(ctx, "FEE REQUEST XML: "+feeResponse.getRequestXml());
  				if(logger.isDebugEnabled(ctx))
  					logger.debug(ctx, "FEE RESPONSE XML: "+feeResponse.getResponseXml());
			} catch (Exception e) {
				e.printStackTrace();
			}

  			if(feeResponse != null && feeResponse.getErrorCode() != null){
  				if(logger.isDebugEnabled(ctx))
  					logger.debug(ctx, "Error code : "+feeResponse.getErrorCode());
  				if(logger.isDebugEnabled(ctx))
  					logger.debug(ctx, "Error Description : "+feeResponse.getErrorDesc());
  				if(feeResponse.getErrorDesc() != null){
  					DataUtils.populateError(ctx,"feeRequestErrorlbl" , feeResponse.getErrorDesc());
  					return null;
  				}

  			}else{
  					if(feeResponse.getRequestXml() != null && !HtmlConstants.EMPTY.equals(feeResponse.getRequestXml()))
  						ctx.put("feeRequestXML", feeResponse.getRequestXml());
  					else
  						ctx.put("feeRequestXML", null);
  					if(feeResponse.getResponseXml() != null && !HtmlConstants.EMPTY.equals(feeResponse.getResponseXml()))
  						ctx.put("feeResponseXML", checkForXMLFormat(feeResponse.getResponseXml()));
  					else
  						ctx.put("feeResponseXML", null);

  					ctx.put("last_updated_by", ctx.get("user_id") != null ? ctx.get("user_id") :null);
  					new SetParametersForStoredProcedures().setParametersInContext(ctx, "object_id,TYPE,license_num,feeRequestXML,feeResponseXML,nipr_licensing_id");
  					SqlResources.getSqlMapProcessor(ctx).insert("licensing.Licensing_Process_FeeXML",ctx);
			}
  			/*List estimatedFeeList = new ArrayList();
  			Map estimatedFeeListMap = new HashMap();


  			estimatedFeeListMap.put("state", "California");
  			estimatedFeeListMap.put("state_id", 4);
  			estimatedFeeListMap.put("license_type", "Resident Insurance Producer");
  			estimatedFeeListMap.put("license_type_code", 5);
  			estimatedFeeListMap.put("LOA", "Personal Lines(1216)");
  			estimatedFeeListMap.put("loa_code", "1216");
  			estimatedFeeListMap.put("state_fee", "$170.00");
  			estimatedFeeListMap.put("trans_fee", "$175.00");

  			estimatedFeeList.add(estimatedFeeListMap);
  			if(estimatedFeeList != null && !HtmlConstants.EMPTY.equals(estimatedFeeList))
  				ctx.put("GetEstimatedFeesDetail_list_01", estimatedFeeList);

  			ctx.put("total_state_fees", "$170.00");
  			ctx.put("total_trans_fees", "$175.00");
  			ctx.put("grand_total_fees", "$345.00");*/

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while validate License Estimated Fees Request on Step2");
		}
  		return null;
  	}
	/**
  	 * This Function validate License Eligibility Request
  	 * @param ctx
  	 * @throws Exception
  	 */
	public static String validateLicenseEligibilityRequest(Context ctx) throws Exception{
  		try {
  			String URL = null;
  			String eligibilityRequestXML = null;
  			String eligibilityResponseXML = null;
  			String FEIN = null;
  			String agencyOfficeName = null;
  			String license_num=null;
  			TripleDESEncryptionDecryption tripleDESEncryptionDecryption=new TripleDESEncryptionDecryption();
  			int ClientID =0;
  			ClientID=TabsConfiguration.Client_ID;
  			String NPN = ctx.get("txtEntityNPN") != null && !HtmlConstants.EMPTY.equals(ctx.get("txtEntityNPN")) ? ctx.get("txtEntityNPN").toString(): null;

  			if(ctx.get("carrier_tab") != null && !HtmlConstants.EMPTY.equals(ctx.get("carrier_tab")) && "Agency".equals(ctx.get("carrier_tab")))
  				FEIN = ctx.get("txtFEIN") != null && !HtmlConstants.EMPTY.equals(ctx.get("txtFEIN")) ? ctx.get("txtFEIN").toString().replaceAll("-", "") : null;
  			else
  				FEIN = ctx.get("txtSSN") != null && !HtmlConstants.EMPTY.equals(ctx.get("txtSSN")) ? ctx.get("txtSSN").toString().replaceAll("-", "") : null;

  				if(ctx.get("carrier_tab") != null && !HtmlConstants.EMPTY.equals(ctx.get("carrier_tab")) && "Agency".equals(ctx.get("carrier_tab"))){
  					agencyOfficeName = ctx.get("txtEntityOfficeName") != null && !HtmlConstants.EMPTY.equals(ctx.get("txtEntityOfficeName")) ? ctx.get("txtEntityOfficeName").toString(): null;
  				}else{
  					agencyOfficeName = ctx.get("txtLastName") != null && !HtmlConstants.EMPTY.equals(ctx.get("txtLastName")) ? ctx.get("txtLastName").toString(): null;
  				}

  			String residentstate_code = ctx.get("residentstate_code") != null && !HtmlConstants.EMPTY.equals(ctx.get("residentstate_code")) ? ctx.get("residentstate_code").toString(): null;
  			String preparer = ctx.get("preparer") != null && !HtmlConstants.EMPTY.equals(ctx.get("preparer")) ? ctx.get("preparer").toString(): null;
  			String states = ctx.get("states") != null && !HtmlConstants.EMPTY.equals(ctx.get("states")) ? ctx.get("states").toString(): null;
  			String licenseType = ctx.get("residency_type") != null && !HtmlConstants.EMPTY.equals(ctx.get("residency_type"))? ctx.get("residency_type").toString(): null;
  			String applicationType = ctx.get("application_type") != null && !HtmlConstants.EMPTY.equals(ctx.get("application_type"))? ctx.get("application_type").toString(): null;

  			String SSN = ctx.get("txtSSN") != null && !HtmlConstants.EMPTY.equals(ctx.get("txtSSN")) ? ctx.get("txtSSN").toString(): null;
  			String firstName = ctx.get("txtFirstName") != null && !HtmlConstants.EMPTY.equals(ctx.get("txtFirstName")) ? ctx.get("txtFirstName").toString(): null;
  			String lastName = ctx.get("txtLastName") != null && !HtmlConstants.EMPTY.equals(ctx.get("txtLastName")) ? ctx.get("txtLastName").toString(): null;
  			boolean isNonResident = "2".equals(licenseType);
  			String agencyType = ctx.get("agency_type") != null && !HtmlConstants.EMPTY.equals(ctx.get("agency_type")) ? ctx.get("agency_type").toString(): "";
  			String license_class_renewal= ctx.get("license_class_renewal") != null && !HtmlConstants.EMPTY.equals(ctx.get("license_class_renewal")) ? ctx.get("license_class_renewal").toString(): null;;
  			ctx.put("license_class_code", license_class_renewal);
  			ctx.put("license_class_renewal", license_class_renewal);
  			if(applicationType != null){
  				license_num =ctx.get("license_num_renewal") != null && !HtmlConstants.EMPTY.equals(ctx.get("license_num_renewal")) ? ctx.get("license_num_renewal").toString(): null;
  				ctx.put("license_num_renewal", license_num);
  				ctx.put("license_num", license_num);
  			}else{
  				license_num= ctx.get("license_num") != null && !HtmlConstants.EMPTY.equals(ctx.get("license_num")) ? ctx.get("license_num").toString(): null;;
  				ctx.put("license_num", license_num);
  			}
  			if(NPN == null || HtmlConstants.EMPTY.equals(NPN))
  				DataUtils.populateError(ctx, "txtEntityNPN", "NPNErrorKey");


  			if(ctx.get("carrier_tab") != null && !HtmlConstants.EMPTY.equals(ctx.get("carrier_tab")) && "Agency".equals(ctx.get("carrier_tab"))){
  				if(FEIN == null || HtmlConstants.EMPTY.equals(FEIN)){
  					DataUtils.populateError(ctx, "txtFEIN", "FEINTaxIdErrorKey");
  				}else{
  					ctx.put("txtFEIN", FEIN.replaceAll("-",""));
  				}
  			}else{
  				if(FEIN == null || HtmlConstants.EMPTY.equals(FEIN)){
  					DataUtils.populateError(ctx, "txtSSN", "SSNErrorKey");
  				}else{
  					ctx.put("txtFEIN", FEIN.replaceAll("-",""));
  				}
  			}
  			if(ctx.get("carrier_tab") != null && !HtmlConstants.EMPTY.equals(ctx.get("carrier_tab")) && "Agency".equals(ctx.get("carrier_tab"))){
  				if(agencyOfficeName == null || HtmlConstants.EMPTY.equals(agencyOfficeName))
					DataUtils.populateError(ctx, "txtEntityOfficeName", "adminEntityNameErrorKey");
  			}else{
  				if(agencyOfficeName == null || HtmlConstants.EMPTY.equals(agencyOfficeName))
					DataUtils.populateError(ctx, "txtLastName", "BGIndivdualLNameEmptyMessage");
  			}

  			/*if(ctx.get("carrier_tab") != null && !HtmlConstants.EMPTY.equals(ctx.get("carrier_tab")) && "Agency".equals(ctx.get("carrier_tab"))){
  				if(FEIN == null || HtmlConstants.EMPTY.equals(FEIN))
  					DataUtils.populateError(ctx, "txtFEIN", "FEINTaxIdErrorKey");

  			}else{
  				if(SSN == null || HtmlConstants.EMPTY.equals(SSN))
  					DataUtils.populateError(ctx, "txtSSN", "SSNErrorKey");
  			}
  			*/
  			if(ctx.get("carrier_tab") != null && !HtmlConstants.EMPTY.equals(ctx.get("carrier_tab")) && "Agent".equals(ctx.get("carrier_tab"))){
  				if(firstName == null || HtmlConstants.EMPTY.equals(firstName))
  					DataUtils.populateError(ctx, "txtFirstName", "BGIndivdualFNameEmptyMessage");
  			}
  				/*if(lastName == null || HtmlConstants.EMPTY.equals(lastName))
  					DataUtils.populateError(ctx, "txtLastName", "BGIndivdualLNameEmptyMessage");*/

  			if("".equals(agencyType))
  				DataUtils.populateError(ctx, "agencyType", "AgenctTypeErrorKey");
  			else
  				ctx.put("agency_type", agencyType);
  			if(license_num == null || HtmlConstants.EMPTY.equals(license_num))
  				DataUtils.populateError(ctx, "license_num", "LicenseNumberErrorKey");
  			if(residentstate_code == null || HtmlConstants.EMPTY.equals(residentstate_code))
  				DataUtils.populateError(ctx, "residentstate_code", "bulkResidentStateSelectErrorKey");
  			if(preparer == null || HtmlConstants.EMPTY.equals(preparer))
  				DataUtils.populateError(ctx, "preparer", "preparerErrorKey");

  			if(preparer == null || HtmlConstants.EMPTY.equals(preparer))
  				DataUtils.populateError(ctx, "preparer", "preparerErrorKey");

  			if(applicationType != null){
  				if(license_class_renewal == null || HtmlConstants.EMPTY.equals(license_class_renewal))
  	  				DataUtils.populateError(ctx, "license_class_renewal", "LicenseClassErrorKey");

  			}

  			String requestType =LicensingConstant.LICENSE_TRANSACTION_TASK;

  			if(ctx.get(Constants.INET_ERRORS_LIST) != null && !HtmlConstants.EMPTY.equals(ctx.get(Constants.INET_ERRORS_LIST)))
  				return null;

  			if(logger.isDebugEnabled(ctx))
  				logger.debug(ctx, "NPN :"+NPN+" FEIN :"+FEIN+" EntityOfficeName :"+agencyOfficeName+" License Num :"+license_num+" Resident State Code :"+residentstate_code);
  			URL = SystemProperties.getInstance().getString("appl."+ctx.getProject()+".niprone.URL");

  			NIPRONEService niprService=new NIPRONEService();
  			EligibilityResponse eligibilityResponse = new EligibilityResponse();
  			EligibilityRequest eligibilityRequest = new EligibilityRequest();
  			ResidentLicenseER rlER = new ResidentLicenseER();
  			eligibilityRequest.setApplicant(ProducerOneUtility.setLicensingApplicantInfo(ctx, NPN, FEIN, agencyOfficeName, isNonResident));
  			eligibilityRequest.setTransactionInfo(ProducerOneUtility.setLicensingTransactionInfo(ctx,licenseType,states,requestType,applicationType));
  			if(applicationType != null){
  				rlER.setClassCode(license_class_renewal);
  				rlER.setStateCode(residentstate_code);
  				rlER.setId(license_num);
  				rlER.setType(LicensingConstant.RESIDENT_TRANSACTION_TYPE);
  				eligibilityRequest.setResidentLicense(rlER);
  			}else{
  				eligibilityRequest.setResidentLicense(null);
  			}
  		/*To be added with actual	if("1".equals(applicationType) && "2".equals(licenseType)){
  				eligibilityRequest.setNonResidentLicenseList(ProducerOneUtility.setNonResidentInformation(ctx));
  			}*/
  			if(isNonResident){
  				eligibilityRequest.setNonResidentLicenseList(ProducerOneUtility.setNonResidentInformation(ctx));
  			}
  			else{
  			eligibilityRequest.setNonResidentLicenseList(null);
  			}
  			/*if("1".equals(applicationType) && "2".equals(licenseType)){
  				eligibilityRequest.setNonResidentLicenseList(ProducerOneUtility.setDemoData());
  			}*/
  			try {
  				String elgxml=getXMLOutput("com.osi.nipr", eligibilityRequest, "http://CNIPROne.org/");
  				if(logger.isDebugEnabled(ctx))
  					logger.debug(ctx, "Eligibility Request on Step2 :- "+elgxml);
  				if(logger.isDebugEnabled(ctx))
  					logger.debug(ctx, "Going to Hit Service URL : "+URL);
  				if(ctx.get(Constants.INET_ERRORS_LIST) == null || HtmlConstants.EMPTY.equals(ctx.get(Constants.INET_ERRORS_LIST))){
  					eligibilityResponse = niprService.CheckEligibilityRequest(ctx, eligibilityRequest, ClientID);
  					if(logger.isDebugEnabled(ctx))
  						logger.debug(ctx, "ELIGIBILITY REQUEST XML:  "+eligibilityResponse.getRequestXml());
  					if(logger.isDebugEnabled(ctx))
  						logger.debug(ctx, "ELIGIBILITY RESPONSE XML: "+eligibilityResponse.getResponseXml());
  				}
			} catch (Exception e) {
				e.printStackTrace();
			}
  			if(eligibilityResponse != null && eligibilityResponse.getErrorCode() != null){
  				if(logger.isDebugEnabled(ctx))
  					logger.debug(ctx, "Error code : "+eligibilityResponse.getErrorCode());
  				if(logger.isDebugEnabled(ctx))
  					logger.debug(ctx, "Error Description : "+eligibilityResponse.getErrorDesc());
  				if(eligibilityResponse.getErrorDesc() != null){
  					DataUtils.populateError(ctx,"eligibilityRequestErrorlbl" , eligibilityResponse.getErrorDesc());
  					return null;
  				}

  			}else{
  					if(eligibilityResponse.getRequestXml() != null && !HtmlConstants.EMPTY.equals(eligibilityResponse.getRequestXml()))
  						ctx.put("eligibilityRequestXML", eligibilityResponse.getRequestXml());
  					else
  						ctx.put("eligibilityRequestXML", null);
  					if(eligibilityResponse.getResponseXml() != null && !HtmlConstants.EMPTY.equals(eligibilityResponse.getResponseXml()))
  						ctx.put("eligibilityResponseXML",checkForXMLFormat(eligibilityResponse.getResponseXml()));
  					else
  						ctx.put("eligibilityResponseXML", null);
  			}
  		} catch (Exception e) {
			logger.error("Error while validate License Eligibility Request on Step2");
		}
  		return null;
  	}
	private static ArrayOfNonResidentLicenseER setDemoData(){
  		ArrayOfNonResidentLicenseER erArray = new ArrayOfNonResidentLicenseER();
  		NonResidentLicenseER erLicense =  new NonResidentLicenseER();
  		erLicense.setClassCode("3");
  		erLicense.setStateCode("AK");
  		erLicense.setType("nonresident");
  		erLicense.setId("23367");
  		erArray.getNonResidentLicenseER().add(erLicense);
  		return erArray;
  	}
	private static ArrayOfNonResidentLicenseER setNonResidentInformation(Context ctx) throws Exception{
  		ArrayOfNonResidentLicenseER nonResidentLicenses = null;
  		NonResidentLicenseER nrLicense = null;
  		if(ctx.get("nr_license_class") != null){
  			nonResidentLicenses = new ArrayOfNonResidentLicenseER();
  			nrLicense =  new NonResidentLicenseER();
  			nrLicense.setClassCode((String)ctx.get("nr_license_class"));
  		}else{
  			DataUtils.populateError(ctx,"nr_license_class", "Please Select Non-Resident License Class");
  		}
  		if(ctx.get("states")!= null){
  			nrLicense.setStateCode((String)(ctx.get("states")));
  		}else{
  			DataUtils.populateError(ctx, "states", "Please select State");
  		}
  		nrLicense.setType("nonresident");
  		nonResidentLicenses.getNonResidentLicenseER().add(nrLicense);
  		return nonResidentLicenses;
  	}
	/**
  	 * method checks format of
  	 * @param responseXml from NIPR
  	 * @throws Exception
  	 */
	private static String checkForXMLFormat(String respXml){
  		String a = "response xmlns="+'"'+"https://pdb.nipr.com/abp-portal/transaction-assistant/"+'"';
  		if(respXml.indexOf("response xmlns=")>0 || respXml.indexOf(a) > 0){
  			respXml = respXml.replace(a, "response");
  		}
   		if(respXml.indexOf("<?xml version") >0 || respXml.indexOf("encoding=") >0 ){
				return respXml;
		}
		else{
			respXml =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>".concat(respXml);
		}
   		return respXml;

	}
	/**
  	 * This Function validate Licensing States on Step1
  	 * @param ctx
  	 * @throws Exception
  	 */
	public static String validateLicensingStatesonStep1(Context ctx) throws Exception{
  		try {
  			String stateId;
            String abbreviations = "";
  			List stateList = (List) SqlResources.getSqlMapProcessor(ctx).select("licensing.GetLicensingStateList_p", ctx);
  			boolean stateFlag = true;
			if(stateList != null && !HtmlConstants.EMPTY.equals(stateList)){
				 for (int i = 0; i < stateList.size(); i++) {
					 if (ctx.get("abbreviation_" + i)!= null && !HtmlConstants.EMPTY.equals(ctx.get("abbreviation_" + i))){
                         stateId = ctx.get("abbreviation_" + i).toString();
                         if (stateFlag) {
                             abbreviations += stateId;
                             stateFlag = false;
                         } else {
                             abbreviations += "," + stateId;
                         }
                     }
				 }


			if(abbreviations != null && !HtmlConstants.EMPTY.equals(abbreviations) && abbreviations.split(",").length !=1){
				DataUtils.populateError((Context)ctx, "adminStateSelectErrorlbl", "licensingAtLeastOneStateErrorKey");
                return null;
			}
			if (abbreviations.equals("")) {
                DataUtils.populateError((Context)ctx, "adminStateSelectErrorlbl", "adminStateSelectErrorKey");
                return null;
            }
			if(ctx.get(Constants.INET_ERRORS_LIST) == null || HtmlConstants.EMPTY.equals(ctx.get(Constants.INET_ERRORS_LIST))) {
				 ctx.put("states", abbreviations);
				 ctx.put("last_updated_by", ctx.get("user_id") != null ? ctx.get("user_id") :null);
				 new SetParametersForStoredProcedures().setParametersInContext(ctx, "nipr_licensing_id,object_id,last_updated_by");
				 if(ctx.get("nipr_licensing_id") == null || HtmlConstants.EMPTY.equals(ctx.get("nipr_licensing_id"))){
					 SqlResources.getSqlMapProcessor(ctx).insert("licensing.InsertUpdateLicensingDetail_p", ctx);
				 }
				 if(ctx.get("nipr_licensing_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("nipr_licensing_id")))
					 ctx.put("nipr_licensing_id", Integer.parseInt(ctx.get("nipr_licensing_id").toString()));
				 else
					 ctx.put("nipr_licensing_id", null);
				}
			}
		} catch (Exception e) {
			logger.error("Error while validate Licensing States on Step1");
		}
  		return null;
  	}
	/**
  	 * This Function set  Licensing Applicant Info
  	 * @param ctx
  	 * @param NPN
  	 * @param FEIN
  	 * @param agencyOfficeName
  	 * @throws Exception
  	 */
	private static ApplicantER setLicensingApplicantInfo(Context ctx,String NPN,String FEIN,String agencyOfficeName,boolean isNonResident){
  		ApplicantER applicant = new ApplicantER();
		applicant.setAgencyType(ctx.get("agency_type") != null && !"".equals(ctx.get("agency_type")) ? (String)ctx.get("agency_type") : "Unknown");
  		applicant.setNPN(NPN);
  		applicant.setTaxId(FEIN);
  		if(ctx.get("carrier_tab") != null && !HtmlConstants.EMPTY.equals(ctx.get("carrier_tab")) && "Agency".equals(ctx.get("carrier_tab"))){
  			applicant.setType(LicensingConstant.APPLICANT_TYPE);
  		}else{
  			applicant.setType(LicensingConstant.APPLICANT_TYPE_INDIVIDUAL);
  		}
  		applicant.setFirmOrLastName(agencyOfficeName);
  		applicant.setBiographicRetiredVeteran(LicensingConstant.APPLICANT_LICENSE_BIOGRAPHICRETIREDVETERAN);
  		return applicant;
  	}
	/**
  	 * This Function Set Licensing Transaction Info
  	 * @param ctx
  	 * @param License Type i.e RL,NRL
  	 * @param State Codes
  	 * @param ctx
  	 * @throws Exception
  	 */
	private static TransactionInfo setLicensingTransactionInfo(Context ctx, String licenseType, String states,String requestType, String applicationType){
  		TransactionInfo transactionInfo = new TransactionInfo();
  		transactionInfo.setTask(requestType);
  		transactionInfo.setStateCode(states);
  		if("1".equals(applicationType ) || "3".equals(applicationType)){
	  		if(licenseType != null && "1".equals(licenseType))
	  			transactionInfo.setType(LicensingConstant.RESIDENT_LICENSE_TRANSACTION_TYPE);
	  		else
	  			transactionInfo.setType(LicensingConstant.NON_RESIDENT_LICENSE_TRANSACTION_TYPE);
  		}else{
  			if(licenseType != null && "1".equals(licenseType))
	  			transactionInfo.setType(LicensingConstant.RENEWAL_RESIDENT_LICENSE_TRANSACTION_TYPE);
	  		else
	  			transactionInfo.setType(LicensingConstant.RENEWAL_NON_RESIDENT_LICENSE_TRANSACTION_TYPE);
  		}
  		return transactionInfo;
  	}
	/**
  	 * This Function set  Licensing Applicant Info
  	 * @param ctx
  	 * @param NPN
  	 * @param FEIN
  	 * @param agencyOfficeName
  	 * @throws Exception
  	 * @return ApplicantFR object
  	 */
	private static ApplicantFR setLicensingFeeRequestApplicantInfo(Context ctx,String NPN,String FEIN,String agencyOfficeName, boolean isNonResident){
  		ApplicantFR applicant = new ApplicantFR();
  		if(isNonResident){
  			if(ctx.get("agency_type") != null && !"".equals(ctx.get("agency_type")))
  				applicant.setAgencyType("Partnership");
  			else
  				applicant.setAgencyType("Partnership");
  		}
  		else
  			applicant.setAgencyType(LicensingConstant.APPLICANT_RESIDENT_LICENSE_AGENCY_TYPE);
  		applicant.setNPN(NPN);
  		applicant.setTaxId(FEIN);
  		if(ctx.get("carrier_tab") != null && !HtmlConstants.EMPTY.equals(ctx.get("carrier_tab")) && "Agency".equals(ctx.get("carrier_tab"))){
  			applicant.setType(LicensingConstant.APPLICANT_TYPE);
  		}else{
  			applicant.setType(LicensingConstant.APPLICANT_TYPE_INDIVIDUAL);
  		}
  		applicant.setFirmOrLastName(agencyOfficeName);
  		applicant.setBiographicRetiredVeteran(LicensingConstant.APPLICANT_LICENSE_BIOGRAPHICRETIREDVETERAN);

  		ArrayOfApplicantLicenseFR aalfr = new ArrayOfApplicantLicenseFR();
  		ApplicantLicenseFR lfr = null;
  		List list = (ArrayList)ctx.get("feeLicenseList");
  		String applicationType = (String)ctx.get("application_type");
  		Map map = (HashMap) ctx.get("feeStep2Map");
  		for (int i=0;i<list.size();i++){
  			lfr = new ApplicantLicenseFR();
  			Map listMap = (HashMap) list.get(i);
  			if(listMap != null && !listMap.isEmpty()){
  				if(applicationType != null){
  					if(map.get("license_num") != null && !HtmlConstants.EMPTY.equals(map.get("license_num")))
  	  					lfr.setId(map.get("license_num").toString());
  	  			}
  				if(listMap.get("license_type_code") != null && !HtmlConstants.EMPTY.equals(listMap.get("license_type_code")))
  					lfr.setClassCode(listMap.get("license_type_code").toString());
  				if(map.get("residentstate_code") != null && !HtmlConstants.EMPTY.equals(map.get("residentstate_code")))
  					lfr.setStateCode(map.get("residentstate_code").toString());
  				if(map.get("lic_residency_type") != null && !HtmlConstants.EMPTY.equals(map.get("lic_residency_type")))
  					lfr.setType("resident");
  			}
  			aalfr.getApplicantLicenseFR().add(lfr);
  		}
  		applicant.setApplicantLicenseList(aalfr);

  		return applicant;
  	}
	/**
  	 *
  	 *
  	 */
	public static String checkLicensingIsPreviouslyLicensed(Context ctx) throws Exception{
		List licensingInfoList= null;
		String selectlicenseTypeCode = null;
        String licenseTypeCode = "";
		try {
			ctx.put("nipr_license_eligibility_request_id", ctx.get("nipr_license_eligibility_request_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("nipr_license_eligibility_request_id")) ? Integer.parseInt(ctx.get("nipr_license_eligibility_request_id").toString()) : null);
			new SetParametersForStoredProcedures().setParametersInContext(ctx, "nipr_license_eligibility_request_id,nipr_licensing_id,agency_id,object_id,TYPE");
			licensingInfoList = (List) SqlResources.getSqlMapProcessor(ctx).select("licensing.GetLicensingDetailStep3List_p", ctx);

			if(licensingInfoList != null && licensingInfoList.size() >0){
				for (int i=0;i<licensingInfoList.size();i++){
					Map licensingInfoListMap = (HashMap) licensingInfoList.get(i);
					if(licensingInfoListMap != null && !licensingInfoListMap.isEmpty()){
						if (ctx.get("license_type_code_"+i)!=null && !HtmlConstants.EMPTY.equals(ctx.get("license_type_code_"+i))) {
							if(licensingInfoListMap.get("license_type_code") != null && !"".equals(licensingInfoListMap.get("license_type_code"))){
								if (selectlicenseTypeCode==null){
                                	selectlicenseTypeCode = licensingInfoListMap.get("license_type_code").toString();
                                }else{
                                	 selectlicenseTypeCode = selectlicenseTypeCode.concat(",").concat(licensingInfoListMap.get("license_type_code").toString());
                                 }
                            }
						}
					}
				}
			}
			if (selectlicenseTypeCode ==null || HtmlConstants.EMPTY.equals(selectlicenseTypeCode)) {
				ctx.put("isHideShowLOAPage", null);
                DataUtils.populateError((Context)ctx, "adminStateSelectErrorlbl", "msgselectrecordforletter1ErrorKey");
                return null;
            }else{
            	ctx.put("isHideShowLOAPage", "Y");
            	ctx.put("hidden_license_types", selectlicenseTypeCode);
            }
		} catch (Exception e) {
			logger.error("Error while is Previously Licensed. "+e.getMessage());
		}
		return null;
	}
	public static String checkValidateLicensingClassLOAPage(Context ctx) throws Exception{
		List licensingInfoList= null;
		List licensingArrList = new ArrayList<Map>();
		try {
			ctx.put("nipr_license_eligibility_request_id", ctx.get("nipr_license_eligibility_request_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("nipr_license_eligibility_request_id")) ? Integer.parseInt(ctx.get("nipr_license_eligibility_request_id").toString()) : null);
			new SetParametersForStoredProcedures().setParametersInContext(ctx, "nipr_license_eligibility_request_id,nipr_licensing_id,agency_id,object_id,TYPE");
			licensingInfoList = (List) SqlResources.getSqlMapProcessor(ctx).select("licensing.GetLicensingDetailStep3List_p", ctx);
			if(licensingInfoList != null && licensingInfoList.size() >0){
				for (int i=0;i<licensingInfoList.size();i++){
					Map licensingInfoListMap = (HashMap) licensingInfoList.get(i);
					if(licensingInfoListMap != null && !licensingInfoListMap.isEmpty()){
						if (ctx.get("license_type_code_"+i)!=null && !HtmlConstants.EMPTY.equals(ctx.get("license_type_code_"+i))) {
							if(licensingInfoListMap.get("license_type_code") != null && !"".equals(licensingInfoListMap.get("license_type_code"))){
								if(ctx.get("is_previously_licensed_"+i) != null && !HtmlConstants.EMPTY.equals(ctx.get("is_previously_licensed_"+i))){
									licensingInfoListMap.put("is_previously_licensed", ctx.get("is_previously_licensed_"+i));
								}
								if(ctx.get("cost_center_code_"+i) != null && !HtmlConstants.EMPTY.equals(ctx.get("cost_center_code_"+i))){
									licensingInfoListMap.put("cost_center_code", ctx.get("cost_center_code_"+i));
								}
								licensingArrList.add(licensingInfoListMap);
							}
						}
					}
				}
			}
			if(licensingArrList != null && licensingArrList.size() >0){
				ctx.put("licensingArr_list_01", licensingArrList);
			}
		} catch (Exception e) {
			logger.error("Error while is Validate Licensing Class LOA Page. "+e.getMessage());
		}
		return null;
	}
	public static String validateLicensingLicenseClassLOA(Context ctx) throws Exception{
		try {
				List loaList = null;
				if(ctx.get("GetLicensingAvailableLOA_list_01")  == null || HtmlConstants.EMPTY.equals(ctx.get("GetLicensingAvailableLOA_list_01")))
					return null;


				if(ctx.get("GetLicensingAvailableLOA_list_01")  != null && !HtmlConstants.EMPTY.equals(ctx.get("GetLicensingAvailableLOA_list_01"))){
					loaList = (List)ctx.get("GetLicensingAvailableLOA_list_01");
					if(loaList != null && loaList.size()>0){
						if(ctx.get("loas") == null || HtmlConstants.EMPTY.equals(ctx.get("loas"))){
							DataUtils.populateError((Context)ctx, "feeRequestErrorlbl", "selectAtLeastOneErrorKey");
			                return null;
						}
					}
				}
		} catch (Exception e) {
			logger.error("Error while validate Licensing License Class LOA. "+e.getMessage());
		}
		return null;
	}
	public static void assignLicensingObjectID(Context ctx) throws Exception{
		try {
			if(ctx.get("carrier_tab") != null && !HtmlConstants.EMPTY.equals(ctx.get("carrier_tab")) && "Agency".equals(ctx.get("carrier_tab"))){
				ctx.put("object_id", ctx.get("agency_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("agency_id")) ? Integer.parseInt(ctx.get("agency_id").toString()) : null);
				ctx.put("TYPE", "A");
			}else{
				ctx.put("object_id", ctx.get("person_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("person_id")) ? Integer.parseInt(ctx.get("person_id").toString()) : null);
				ctx.put("TYPE", "I");
			}
		} catch (Exception e) {
			logger.error("Error while assign Licensing Object ID. "+e.getMessage());
		}
	}
	public static String validateLicensingStep7(Context ctx) throws Exception{
		try {
			if(ctx.get("first_name") == null || HtmlConstants.EMPTY.equals(ctx.get("first_name")))
				DataUtils.populateError((Context)ctx, "first_name", "FirstNameErrorKey");

			if(ctx.get("last_name") == null || HtmlConstants.EMPTY.equals(ctx.get("last_name")))
				DataUtils.populateError((Context)ctx, "last_name", "LastNameErrorKey");

			if(ctx.get("domicile_country") == null || HtmlConstants.EMPTY.equals(ctx.get("domicile_country")))
				DataUtils.populateError((Context)ctx, "domicile_country", "CountryResidenceErrorKey");

			if(ctx.get("title") == null || HtmlConstants.EMPTY.equals(ctx.get("title")))
				DataUtils.populateError((Context)ctx, "title", "titleErrorKey");

			if(ctx.get("addressline1_business") == null || HtmlConstants.EMPTY.equals(ctx.get("addressline1_business")))
				DataUtils.populateError((Context)ctx, "addressline1_business", "niprAuthorizerAddressLine1");

			if(ctx.get("addressline2_business") == null || HtmlConstants.EMPTY.equals(ctx.get("addressline2_business")))
				DataUtils.populateError((Context)ctx, "addressline2_business", "niprAuthorizerAddressLine2");

			if(ctx.get("city_business") == null || HtmlConstants.EMPTY.equals(ctx.get("city_business")))
				DataUtils.populateError((Context)ctx, "city_business", "CityW9ErrorKey");

			if(ctx.get("state_id_business") == null || HtmlConstants.EMPTY.equals(ctx.get("state_id_business")))
				DataUtils.populateError((Context)ctx, "state_id_business", "stateCommissionRecalculationSelectErrorKey");

			if(ctx.get("phone_business") == null || HtmlConstants.EMPTY.equals(ctx.get("phone_business")))
				DataUtils.populateError((Context)ctx, "phone_business", "adminPhoneErrorKey");


			if(ctx.get("zip_business") == null || HtmlConstants.EMPTY.equals(ctx.get("zip_business")))
				DataUtils.populateError((Context)ctx, "zip_business", "ZipCodeEmptyErrorKey");

			if(ctx.get("entity_name") == null || HtmlConstants.EMPTY.equals(ctx.get("entity_name")))
				DataUtils.populateError((Context)ctx, "entity_name", "FirmNameErrorKey");

			if(ctx.get("business_email") == null || HtmlConstants.EMPTY.equals(ctx.get("business_email")))
				DataUtils.populateError((Context)ctx, "phone_business", "businessEmailErrorKey");

			if(ctx.get("is_accepted") == null || HtmlConstants.EMPTY.equals(ctx.get("is_accepted")))
				DataUtils.populateError((Context)ctx, "is_accepted", "licensingIsAcceptedErrorKey");
		} catch (Exception e) {
			logger.error("Error while validate Licensing Step7. "+e.getMessage());
		}
		return null;
	}
	public static String getXMLOutput(String packageName, Object resultObject,String namespace) throws Exception{
        if(resultObject == null){
              return "No Record Found";
        }
        JAXBContext jc = JAXBContext.newInstance(packageName);
        Marshaller m = jc.createMarshaller();
        OutputStream outputStream = new OutputStream()
        {
        	private StringBuilder string = new StringBuilder();
        	@Override
        	public void write(int b) throws IOException {
        		this.string.append((char) b );
        }
      public String toString(){
          return this.string.toString();
      }
    };
   // new JAXBElement(new QName("http://service.agency.ponews.com/", "GetAgencyAddressResult"), resultObject.getClass(), resultObject);
    m.marshal(new JAXBElement(new QName(namespace, resultObject.getClass().getSimpleName()), resultObject.getClass(), resultObject), outputStream);
  //  m.marshal(resultObject, outputStream);
    return outputStream.toString();
  }
/**
   * This Function Get Licensing Background Question Json
   * @param ctx
   * @throws Exception
   */
	public static String getLicensingStateSpecificBackgroundQuestionJson(Context ctx) throws Exception {
		String xml = null;
		String identifier = "s";
	  	SAXBuilder builder = new SAXBuilder();
	  	json = new StringBuilder();
	  	new SetParametersForStoredProcedures().setParametersInContext(ctx, "nipr_licensing_id,agency_id,object_id,TYPE");
		Map map = (HashMap) SqlResources.getSqlMapProcessor(ctx).findByKey("licensing.GetLicensingDetailStep1_p", ctx);
		ctx.putAll(map);
		ctx.put("residency_type_code", ctx.get("residency_type_code") != null && !HtmlConstants.EMPTY.equals(ctx.get("residency_type_code")) ? ctx.get("residency_type_code").toString(): "");
	  	//ctx.put("residency_type_code", "RL");
		if("RR".equals(ctx.get("residency_type_code")))
			ctx.put("residency_type_code", "RLR");
		else if("NRR".equals(ctx.get("residency_type_code")))
			ctx.put("residency_type_code", "NRLR");
		if(ctx.get("carrier_tab") != null && !HtmlConstants.EMPTY.equals(ctx.get("carrier_tab")) && "Agency".equals(ctx.get("carrier_tab"))){
			ctx.put("applicant_type", "F");
		}else if ("Agent".equals(ctx.get("carrier_tab"))) {
			ctx.put("applicant_type", "I");
		}else{
		ctx.put("applicant_type", "B");
		};
		ctx.put("q_type", "S");
		ctx.put("state_code", ctx.get("state_code") != null && !HtmlConstants.EMPTY.equals(ctx.get("state_code")) ? ctx.get("state_code").toString() : null);
		//ctx.put("state_code", "AR");
		new SetParametersForStoredProcedures().setParametersInContext(ctx, "nipr_licensing_id,applicant_type,");
		Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("licensing.getLicensingStateSpecificBackgroundQuestionsXML", ctx);
		if(obj != null && obj instanceof Map){
			Map ctxMap = (HashMap)obj;
			if(ctxMap != null){
				//Document doc = builder.build(new File(SystemProperties.getInstance().getProperty("appl.home.dir")+File.separator+"questions.xml"));
				if(ctxMap.get("Licensing_state_Background_Questions") != null && ctxMap.get("Licensing_state_Background_Questions") instanceof Clob){
					xml = ProducerOneUtility.clobStringConversion((Clob) ctxMap.get("Licensing_state_Background_Questions"));
				}else{
					xml = ctxMap.get("Licensing_state_Background_Questions") != null && !HtmlConstants.EMPTY.equals(ctxMap.get("Licensing_state_Background_Questions")) ? ctxMap.get("Licensing_state_Background_Questions").toString() : "";
				}
				//String xml = ctxMap.get("Licensing_Background_Questions") != null && !HtmlConstants.EMPTY.equals(ctxMap.get("Licensing_Background_Questions")) ? ctxMap.get("Licensing_Background_Questions").toString() : null;

				if(xml == null || "".equals(xml))
					return "";
				if(ctx.get("state_background_question_xml") != null && ctx.get(Constants.INET_ERRORS_LIST) != null){
					xml = (String)ctx.get("state_background_question_xml");
				}
				Document doc = builder.build(new StringReader(xml));
				//xml = ProducerOneUtility.clobStringConversion((Clob) ctxMap.get("Licensing_Background_Questions"));
				//logger.debug(ctx, "--> "+xml);
				//System.out.println("--> "+xml);
				//System.out.println(ProducerOneUtility.clobStringConversion((Clob) ctxMap.get("Licensing_Background_Questions")));
				Element root = doc.getRootElement();

			//	json = "var htmlText = '';";
			//	json =json + "\nvar myObj1 ='';";
			//	json = json + "\nmyObj1 = {";
				//json += "{";
				json.append("{");
				if(root.getChildren() != null && root.getChildren().size() > 0){
					//json = json + "\"backgroundQ\": [";
					json.append("\"backgroundQ\": [");
					for(int i=0; i<root.getChildren().size(); i++){
						Element parent = (Element)root.getChildren().get(i);
						//json = json + "\n\t{";
						json.append("\n\t{");
						parseBackgroundQuestionXML(ctx,parent, i+"", "0",identifier);
						if(i  != root.getChildren().size()-1 )
						//json = json + "\n\t},";
							json.append("\n\t},");
						else
							//json = json + "\n\t}";
							json.append("\n\t}");
					}
				}

				json.append("\n]}");
				//json = json + "\n]";
				//json = json + "}";

				try{
					/*FileOutputStream fout = new FileOutputStream(SystemProperties.getInstance().getProperty("appl.home.dir")+File.separator+ "js"+ File.separator+"data2.js");
					fout.write(json.getBytes());
					fout.close();*/
					ctx.put("bgqdata2json", json.toString());
					if(ctx.get(Constants.INET_ERRORS_LIST) != null)
						((HttpServletRequest)ctx.get(HtmlConstants.DOCUMENTREQUEST)).setAttribute("bgqdata2json", json.toString());
				}catch(Exception e){
					logger.error("Error while geting Licensing Background Question Json. "+e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return json.toString();
	}
	/**
	   * This Function Get Licensing Background Question Json
	   * @param ctx
	   * @throws Exception
	   */
	public static String getLicensingBackgroundQuestionJson(Context ctx) throws Exception {
		String xml = null;
	  	SAXBuilder builder = new SAXBuilder();
	  	String identifier = "";
	  	new SetParametersForStoredProcedures().setParametersInContext(ctx, "nipr_licensing_id,agency_id,object_id,TYPE");
		json = new StringBuilder();
	  	Map map = (HashMap) SqlResources.getSqlMapProcessor(ctx).findByKey("licensing.GetLicensingDetailStep1_p", ctx);
		ctx.putAll(map);
		ctx.put("residency_type_code", ctx.get("residency_type_code") != null && !HtmlConstants.EMPTY.equals(ctx.get("residency_type_code")) ? ctx.get("residency_type_code").toString(): null);
		//ctx.put("residency_type_code", "RL");
		if("RR".equals(ctx.get("residency_type_code")))
			ctx.put("residency_type_code", "RLR");
		else if("NRR".equals(ctx.get("residency_type_code")))
			ctx.put("residency_type_code", "NRLR");
		if(ctx.get("carrier_tab") != null && !HtmlConstants.EMPTY.equals(ctx.get("carrier_tab")) && "Agency".equals(ctx.get("carrier_tab"))){
			ctx.put("applicant_type", "B");
		}else if ("Agent".equals(ctx.get("carrier_tab"))) {
			ctx.put("applicant_type", "I");
		}else{
		ctx.put("applicant_type", "B");
		}
		ctx.put("q_type", "B");
		new SetParametersForStoredProcedures().setParametersInContext(ctx, "nipr_licensing_id,applicant_type,");
		Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("licensing.getLicensingBackgroundQuestionsXML", ctx);
		if(obj != null && obj instanceof Map){
			Map ctxMap = (HashMap)obj;
			if(ctxMap != null){
				//Document doc = builder.build(new File(SystemProperties.getInstance().getProperty("appl.home.dir")+File.separator+"questions.xml"));
				if(ctxMap.get("Licensing_Background_Questions") != null && ctxMap.get("Licensing_Background_Questions") instanceof Clob){
					xml = ProducerOneUtility.clobStringConversion((Clob) ctxMap.get("Licensing_Background_Questions"));
				}else{
					xml = ctxMap.get("Licensing_Background_Questions") != null && !HtmlConstants.EMPTY.equals(ctxMap.get("Licensing_Background_Questions")) ? ctxMap.get("Licensing_Background_Questions").toString() : null;
				}
				//String xml = ctxMap.get("Licensing_Background_Questions") != null && !HtmlConstants.EMPTY.equals(ctxMap.get("Licensing_Background_Questions")) ? ctxMap.get("Licensing_Background_Questions").toString() : null;
				if(xml == null)
					return "";
				if(ctx.get("background_question_xml") != null && ctx.get(Constants.INET_ERRORS_LIST) != null){
					xml = (String)ctx.get("background_question_xml");
				}
				Document doc = builder.build(new StringReader(xml));
				//xml = ProducerOneUtility.clobStringConversion((Clob) ctxMap.get("Licensing_Background_Questions"));
				//logger.debug(ctx, "--> "+xml);
				//System.out.println("--> "+xml);
				//System.out.println(ProducerOneUtility.clobStringConversion((Clob) ctxMap.get("Licensing_Background_Questions")));
				Element root = doc.getRootElement();
				//json = "var htmlText = '';";
			//	json =json + "\nvar myObj ='';";
				// = json + "\nmyObj = {";
				//json +=  "{";
				json.append("{");
				if(root.getChildren() != null && root.getChildren().size() > 0){
					//json = json + "\"backgroundQ\": [";
					json.append( "\"backgroundQ\": [");
					for(int i=0; i<root.getChildren().size(); i++){
						Element parent = (Element)root.getChildren().get(i);
						//json = json + "\n\t{";
						json.append("\n\t{");
						parseBackgroundQuestionXML(ctx,parent, i+"", "0",identifier);
						if(i != root.getChildren().size()-1)
							json.append("\n\t},");
						else
							json.append("\n\t}");
					}
				}

				//json = json + "\n]";
				//json = json + "}";
				json.append("\n]}");

				try{
					/*FileOutputStream fout = new FileOutputStream(SystemProperties.getInstance().getProperty("appl.home.dir")+File.separator+ "js"+ File.separator+"data1.js");
					fout.write(json.getBytes());
					fout.close();*/
					ctx.put("bgqdata1json", json.toString());
					if(ctx.get(Constants.INET_ERRORS_LIST) != null)
						((HttpServletRequest)ctx.get(HtmlConstants.DOCUMENTREQUEST)).setAttribute("bgqdata1json", json.toString());
				}catch(Exception e){
					logger.error("Error while geting Licensing Background Question Json. "+e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return json.toString();
	}
	/**
	 * This Function Licensing parse Background Question XML
	 * @param ctx
	 * @param Element
	 * @param parentIndex
	 * @param childIndexId
	 * @throws Exception
	 */
	private static void parseBackgroundQuestionXML(Context ctx,Element parent, String parentIndex, String childIndexId, String identifier) throws Exception {
		if(parent.getChildren() != null){
			String disabled = "";
			String readOnly = "";
			if("licensingDetailStep6".equals(ctx.get(HtmlConstants.NEXT_PAGE_FOR_VIEW))){
				readOnly = "readonly =\'readonly\'";
				disabled = "disabled =\'disabled\'";
			}
			for(int i=0; i<parent.getChildren().size(); i++){
				Element ele = (Element)parent.getChildren().get(i);

				String name = identifier+"ck"+parentIndex;
				String yesid = "ajax_field_"+identifier+"ck"+parentIndex+"y_0";//"ck"+parentIndex+"y";
				String noid = "ajax_field_"+identifier+"ck"+parentIndex+"n_1";//"ck"+parentIndex+"n";
				String commentname = identifier+"ck"+parentIndex+"_comment";
				String commentid = "ajax_field_"+identifier+"ck"+parentIndex+"_comment";

				if(ele.getName().equals("questionnum"))
				json.append("\n\t\"Q\": " + "\""+ ele.getText().replace("\"", "'") + "\",");
				else if(ele.getName().equals("question"))
				json.append("\n\t\"Questions\": " + "\""+ ele.getText().replace("\"", "'") + "\",");
 				else if(ele.getName().equals("value")){
					String yesOpenChild = "0";
					String noOpenChild = "0";
					String commentopenon = ele.getAttributeValue("commentopenon") != null ? ele.getAttributeValue("commentopenon") : "";
					String commentOpenOnYes = "0" , commentOpenOnNo = "0";

					if(ele.getAttributeValue("childopenon") != null && ele.getAttributeValue("childopenon").equals("Y"))
						yesOpenChild = "1";
					else if(ele.getAttributeValue("childopenon") != null && ele.getAttributeValue("childopenon").equals("N"))
						noOpenChild = "1";

					/*if(ele.getAttributeValue("commentopenon") != null && ele.getAttributeValue("commentopenon").equals("Y"))
						yesOpenChild = "1";
					else if(ele.getAttributeValue("commentopenon") != null && ele.getAttributeValue("commentopenon").equals("N"))
						noOpenChild = "1";*/
					if("Y".equals(commentopenon))
							commentOpenOnYes = "1";
					else if("N".equals(commentopenon))
							commentOpenOnNo = "1";
					else{
						commentOpenOnYes = "0";
						commentOpenOnNo = "0";
					}
					if(ele.getText().equals("Y")){
						json.append("\n\t\"Yes\": " + "\""+ "<input type='radio' name='"+name+"' id='"+yesid+"' onclick='openchild("+yesid+", "+commentOpenOnYes+")' value='Y' checked='checked' "+disabled+"/>" + "\",");
						json.append("\n\t\"No\": " + "\""+ "<input type='radio' name='"+name+"' id='"+noid+"' onclick='openchild("+noid+", "+commentOpenOnNo+")' value='N' "+disabled+"/>" + "\",");
						json.append("\n\t\"hidden\": " + "\""+ "<input type='hidden' name='"+name+"' id='ajax_field_"+name+"_hidden' value='"+commentopenon+"'"+readOnly+"/>" + "\"");
					}else if(ele.getText().equals("N")){
						/*
						 * json = json + "\n\t\"Yes\": " + "\""+ "<input type='radio' name='"+name+"' id='"+yesid+"' onclick='openchild("+yesid+", "+yesOpenChild+")' value='Y'/>" + "\",";
						json = json + "\n\t\"No\": " + "\""+ "<input type='radio' name='"+name+"' id='"+noid+"' onclick='openchild("+noid+", "+noOpenChild+")' value='N' checked='checked'/>" + "\",";
						json = json + "\n\t\"hidden\": " + "\""+ "<input type='hidden' name='"+name+"' id='ajax_field_"+name+"_hidden' value='"+commentopenon+"'/>" + "\"";
						*/
						json.append("\n\t\"Yes\": " + "\""+ "<input type='radio' name='"+name+"' id='"+yesid+"' onclick='openchild("+yesid+", "+commentOpenOnYes+")' value='Y' "+disabled+"/>" + "\",");
						json.append("\n\t\"No\": " + "\""+ "<input type='radio' name='"+name+"' id='"+noid+"' onclick='openchild("+noid+", "+commentOpenOnNo+")' value='N' checked='checked'"+disabled+"/>" + "\",");
						json.append("\n\t\"hidden\": " + "\""+ "<input type='hidden' name='"+name+"' id='ajax_field_"+name+"_hidden' value='"+commentopenon+"' "+readOnly+"/>" + "\"");
					}else{
						/*json = json + "\n\t\"Yes\": " + "\""+ "<input type='radio' name='"+name+"' id='"+yesid+"' onclick='openchild("+yesid+", "+yesOpenChild+")' value='Y'/>" + "\",";
						json = json + "\n\t\"No\": " + "\""+ "<input type='radio' name='"+name+"' id='"+noid+"' onclick='openchild("+noid+", "+noOpenChild+")' value='N'/>" + "\",";
						json = json + "\n\t\"hidden\": " + "\""+ "<input type='hidden' name='"+name+"' id='ajax_field_"+name+"_hidden' value='"+commentopenon+"'/>" + "\"";
						*/
						json.append("\n\t\"Yes\": " + "\""+ "<input type='radio' name='"+name+"' id='"+yesid+"' onclick='openchild("+yesid+", "+commentOpenOnYes+")' value='Y' "+disabled+" />" + "\",");
						json.append("\n\t\"No\": " + "\""+ "<input type='radio' name='"+name+"' id='"+noid+"' onclick='openchild("+noid+", "+commentOpenOnNo+")' value='N' "+disabled+" />" + "\",");
						json.append("\n\t\"hidden\": " + "\""+ "<input type='hidden' name='"+name+"' id='ajax_field_"+name+"_hidden' value='"+commentopenon+"' "+readOnly+" />" + "\"");
					}
				}else if(ele.getName().equals("comment"))
					//json = ","+json + "\n\t\"comment\": " + "\""+ "<textarea name='"+commentname+"' id='"+commentid+"'>"+ele.getText()+"</textarea>" + "\"";
					json.append(","+"\n\t\"comment\": " + "\""+ "<textarea name='"+commentname+"' id='"+commentid+"' "+readOnly+">"+ele.getText()+"</textarea>" + "\"");
				else if(ele.getName().equals("childquestion")){
					//json = ","+json + "\n\t\"subQ"+childIndexId+"\": [";
					json.append(","+"\n\t\"subQ"+childIndexId+"\": [");
					for(int j=0; j<ele.getChildren().size(); j++){
						//json = json + "\n\t{";
						json.append("\n\t{");
						parseBackgroundQuestionXML(ctx,(Element)ele.getChildren().get(j), parentIndex+j, childIndexId+0,identifier);
						if(j != ele.getChildren().size()-1 )
							//json = json + "\n\t},";
							json.append( "\n\t},");
						else
							//json = json + "\n\t}";
							json.append("\n\t}");
					}

					//json = json + "]";
					json.append("]");
				}
			}
		}
	}
	/**
  	 * This Function Validate Licensing Background Question Json
  	 * @param ctx
  	 * @throws Exception
  	 */
	public static String validateLicensingBackgroundQuestionJson(Context ctx) throws Exception {
		try {
 			String xml = null;
			String identifier="";
			SAXBuilder builder = new SAXBuilder();
			new SetParametersForStoredProcedures().setParametersInContext(ctx, "nipr_licensing_id,agency_id,object_id,TYPE");
			Map map = (HashMap) SqlResources.getSqlMapProcessor(ctx).findByKey("licensing.GetLicensingDetailStep1_p", ctx);
			ctx.putAll(map);
			/*ctx.put("residency_type_code", "RL");
			ctx.put("applicant_type", "B");
			ctx.put("q_type", "B");*/
			ctx.put("residency_type_code", ctx.get("residency_type_code") != null && !HtmlConstants.EMPTY.equals(ctx.get("residency_type_code")) ? ctx.get("residency_type_code").toString(): "");
			if("RR".equals(ctx.get("residency_type_code")))
				ctx.put("residency_type_code", "RLR");
			else if("NRR".equals(ctx.get("residency_type_code")))
				ctx.put("residency_type_code", "NRLR");
			if(ctx.get("carrier_tab") != null && !HtmlConstants.EMPTY.equals(ctx.get("carrier_tab")) && "Agency".equals(ctx.get("carrier_tab"))){
				ctx.put("applicant_type", "B");
			}else if ("Agent".equals(ctx.get("carrier_tab"))) {
				ctx.put("applicant_type", "I");
			}else{
			ctx.put("applicant_type", "B");
			}
			ctx.put("q_type", "B");

			new SetParametersForStoredProcedures().setParametersInContext(ctx, "nipr_licensing_id,applicant_type,residency_type_code,q_type");
			Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("licensing.getLicensingBackgroundQuestionsXML", ctx);
			if(obj instanceof Map){
				Map ctxMap = (HashMap)obj;
				if(ctxMap != null){
					//Document doc = builder.build(new File(SystemProperties.getInstance().getProperty("appl.home.dir")+File.separator+"questions.xml"));
					//String xml = ctxMap.get("Licensing_Background_Questions") != null && !HtmlConstants.EMPTY.equals(ctxMap.get("Licensing_Background_Questions")) ? ctxMap.get("Licensing_Background_Questions").toString() : null;
					//Document doc = builder.build(new File(SystemProperties.getInstance().getProperty("appl.home.dir")+File.separator+"questions.xml"));
					if(ctxMap.get("Licensing_Background_Questions") != null && ctxMap.get("Licensing_Background_Questions") instanceof Clob){
						xml = ProducerOneUtility.clobStringConversion((Clob) ctxMap.get("Licensing_Background_Questions"));
					}else{
						xml = ctxMap.get("Licensing_Background_Questions") != null && !HtmlConstants.EMPTY.equals(ctxMap.get("Licensing_Background_Questions")) ? ctxMap.get("Licensing_Background_Questions").toString() : null;
					}
					Document doc = builder.build(new StringReader(xml));
					Element root = doc.getRootElement();
					if(root.getChildren() != null && root.getChildren().size() > 0){
						for(int i=0; i<root.getChildren().size(); i++){
							Element parent = (Element)root.getChildren().get(i);
							validateLicensingBackgroundQuestionXML(parent, i+"", "0", ctx,identifier);
						}
					}
					ctx.put("background_question_xml", new XMLOutputter(Format.getPrettyFormat()).outputString(root));
				}
			}

		} catch (Exception e) {
				logger.error("Error while validate Licensing Background Question Json. "+e.getMessage());
		}
		return null;
	}
	/**
  	 * This Function Validate Licensing Background Question Json
  	 * @param ctx
  	 * @throws Exception
  	 */
	public static String validateLicensingStateBackgroundQuestionJson(Context ctx) throws Exception {
		try {
			String xml = null;
			String identifier = "s";
			SAXBuilder builder = new SAXBuilder();
			new SetParametersForStoredProcedures().setParametersInContext(ctx, "nipr_licensing_id,agency_id,object_id,TYPE");
			Map map = (HashMap) SqlResources.getSqlMapProcessor(ctx).findByKey("licensing.GetLicensingDetailStep1_p", ctx);
			ctx.putAll(map);
			ctx.put("residency_type_code", ctx.get("residency_type_code") != null && !HtmlConstants.EMPTY.equals(ctx.get("residency_type_code")) ? ctx.get("residency_type_code").toString(): null);
			if("RR".equals(ctx.get("residency_type_code")))
				ctx.put("residency_type_code", "RLR");
			else if("NRR".equals(ctx.get("residency_type_code")))
				ctx.put("residency_type_code", "NRLR");
			if(ctx.get("carrier_tab") != null && !HtmlConstants.EMPTY.equals(ctx.get("carrier_tab")) && "Agency".equals(ctx.get("carrier_tab"))){
				ctx.put("applicant_type", "F");
			}else if ("Agent".equals(ctx.get("carrier_tab"))) {
				ctx.put("applicant_type", "I");
			}else{
			ctx.put("applicant_type", "B");
			}
			ctx.put("q_type", "S");
			ctx.put("state_code", ctx.get("state_code") != null && !HtmlConstants.EMPTY.equals(ctx.get("state_code")) ? ctx.get("state_code").toString() : null);
			new SetParametersForStoredProcedures().setParametersInContext(ctx, "nipr_licensing_id,applicant_type,state_code,q_type,residency_type_code");
			Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("licensing.getLicensingStateSpecificBackgroundQuestionsXML", ctx);

			if(obj instanceof Map){
				Map ctxMap = (HashMap)obj;
				if(ctxMap != null){
					//Document doc = builder.build(new File(SystemProperties.getInstance().getProperty("appl.home.dir")+File.separator+"questions.xml"));
					//String xml = ctxMap.get("Licensing_Background_Questions") != null && !HtmlConstants.EMPTY.equals(ctxMap.get("Licensing_Background_Questions")) ? ctxMap.get("Licensing_Background_Questions").toString() : null;
					//Document doc = builder.build(new File(SystemProperties.getInstance().getProperty("appl.home.dir")+File.separator+"questions.xml"));
					if(ctxMap.get("Licensing_state_Background_Questions") != null && ctxMap.get("Licensing_state_Background_Questions") instanceof Clob){
						xml = ProducerOneUtility.clobStringConversion((Clob) ctxMap.get("Licensing_state_Background_Questions"));
					}else{
						xml = ctxMap.get("Licensing_state_Background_Questions") != null && !HtmlConstants.EMPTY.equals(ctxMap.get("Licensing_state_Background_Questions")) ? ctxMap.get("Licensing_state_Background_Questions").toString() : null;
					}
					Document doc = builder.build(new StringReader(xml));
					Element root = doc.getRootElement();
					if(root.getChildren() != null && root.getChildren().size() > 0){
						for(int i=0; i<root.getChildren().size(); i++){
							Element parent = (Element)root.getChildren().get(i);
							validateLicensingBackgroundQuestionXML(parent, i+"", "0", ctx,identifier);
						}
					}
					ctx.put("state_background_question_xml", new XMLOutputter(Format.getPrettyFormat()).outputString(root));
				}
			}
		} catch (Exception e) {
				logger.error("Error while validate Licensing Background Question Json. "+e.getMessage());
		}
		return null;
	}
	/**
  	 * This Function validate Licensing Background Question XML
  	 * @param Element
  	 * @param parentIndex
  	 * @param childIndexId
  	 * @param ctx
  	 * @throws Exception
  	 */
	private static void validateLicensingBackgroundQuestionXML(Element parent, String parentIndex, String childIndexId, Context ctx, String identifier) throws Exception {
		try {
			if(parent.getChildren() != null){
				boolean isCommentRequired =false;
				for(int i=0; i<parent.getChildren().size(); i++){
					Element ele = (Element)parent.getChildren().get(i);

					String name = identifier+"ck"+parentIndex;
					String yesid = identifier+"ck"+parentIndex+"y";
					String noid = identifier+"ck"+parentIndex+"n";

					if(ctx.get(name) == null || ctx.get(name).equals("")){
						DataUtils.populateError(ctx, "pageError", "allQuestionsSelectErrorKey");
					}

					if(ele.getName().equals("value") && ctx.get(name) != null){
						ele.setText(ctx.get(name).toString());
						isCommentRequired = isCommentRequired(ele, ctx.get(name).toString());
					}
					else if(ele.getName().equals("comment") && isCommentRequired){
						if((ctx.get(name+"_comment") == null || ctx.get(name+"_comment").equals("")) && ctx.get(name) != null){
							DataUtils.populateError(ctx, "pageError", "allQuestionsSelectErrorKey");
						}

						ele.setText(ctx.get(name+"_comment").toString());
					}else if(ele.getName().equals("childquestion")){
						for(int j=0; j<ele.getChildren().size(); j++){
							validateLicensingBackgroundQuestionXML((Element)ele.getChildren().get(j), parentIndex+j, childIndexId+0, ctx,identifier);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error while validateLicensingBackgroundQuestion XML. "+e.getMessage());
		}
	}
	public static boolean isCommentRequired(Element element , String selected){
		Attribute attr =  null;
		if(element!= null && element.getAttributes().size() > 0){
			if(element.getAttribute("commentopenon") != null){
				attr = element.getAttribute("commentopenon");
				if(attr.getValue() != null && attr.getValue().equals(selected))
					return true;
				else
					return false;
			}
		}
		return false;
	}
	public static String clobStringConversion(Clob clb) throws IOException, SQLException
    {
      if (clb == null)
    	  return  "";
      StringBuffer str = new StringBuffer();
      StringBuffer strng;
      BufferedReader bufferRead = new BufferedReader(clb.getCharacterStream());
     /* while ((strng=bufferRead .readLine())!=null){
       str.append(strng);
      }*/

      boolean endFlag = true;
      String line = null;
      strng = new StringBuffer(300);
      while (endFlag){
      	line = bufferRead.readLine();
          if (line != null)
        	  strng.append(line).append("\n");
          if (line == null)
          	endFlag = false;
      }

      bufferRead.close();
      return strng.toString();
    }

	public static Object convertMomListFieldsIntoXml(Context ctx, String momListFieldsString, Object listSize, String additionalFieldsString, String outputXmlName) throws Exception{

		int actulaListSize = 0;
		if((momListFieldsString == null || HtmlConstants.EMPTY.equals(momListFieldsString))
				|| listSize == null)
			return null;
		try{
			actulaListSize = Integer.parseInt(String.valueOf(listSize));
		}catch(Exception e){
			return null;
		}


		 org.jdom.Element root = null;
		 org.jdom.Element child = null;

		 root = new org.jdom.Element("Root");

		 org.jdom.Element contextFieldElement = null;
		 org.jdom.Element additionalFieldElement = null;
		 if(additionalFieldsString != null && !HtmlConstants.EMPTY.equals(additionalFieldsString)){
			 StringTokenizer additionaltokens = new StringTokenizer(additionalFieldsString, "|");

			 additionalFieldElement = new org.jdom.Element("additionalFields");
			 while(additionaltokens.hasMoreTokens()){
	             String objectName = additionaltokens.nextToken();

	             if(objectName == null || HtmlConstants.EMPTY.equals(objectName)
	                     || ctx.get(objectName) == null || HtmlConstants.EMPTY.equals(ctx.get(objectName).toString())){
	                 continue;
	             }


	             contextFieldElement = new org.jdom.Element(objectName);
	             contextFieldElement.addContent(ctx.get(objectName).toString());

	             additionalFieldElement.addContent(contextFieldElement);
             }

		 }

		 List dataList = null;
		 if(ctx.get("ISCREATEFULLLISTNAME") != null && ctx.get("ISCREATEFULLLISTNAME") instanceof List){
			 dataList = (List)ctx.get("ISCREATEFULLLISTNAME");
		 }

		 for(int i=0; i<actulaListSize; i++){
			 child = new org.jdom.Element("Root1");
			 boolean dataFound = false;
			 StringTokenizer tokens = new StringTokenizer(momListFieldsString, "|");
			 while(tokens.hasMoreTokens()){
	             String objectName = tokens.nextToken();

	             if(ctx.get("ISCREATEFULLLISTNAME") == null){
		             if(objectName == null || HtmlConstants.EMPTY.equals(objectName)
		                     || ctx.get(objectName+"_"+i) == null || HtmlConstants.EMPTY.equals(ctx.get(objectName+"_"+i).toString())){
		                 continue;
		             }
	             }

	             String value = ctx.get(objectName+"_"+i) != null ? ctx.get(objectName+"_"+i).toString() : null;
	             if(StringUtils.isBlank(value)){
	            	 if(dataList.get(i) != null && ((Map)dataList.get(i)) != null && ((Map)dataList.get(i)).get(objectName) != null){
	            		 value = ((Map)dataList.get(i)).get(objectName).toString();
	            	 }
	             }

	             org.jdom.Element fieldElement = null;
                 fieldElement = new org.jdom.Element(objectName);
                 fieldElement.addContent(value);
                 child.addContent(fieldElement);
                 dataFound = true;
			 }

			 if(dataFound)
				 root.addContent(child);

		 }

		 if(additionalFieldElement != null){
			 root.addContent(additionalFieldElement.detach());
		 }


		 XMLOutputter output = new XMLOutputter();
         String xml = output.outputString(root);
         if(outputXmlName == null || HtmlConstants.EMPTY.equals(outputXmlName)){
             ctx.put("outputXML", xml);
         }else{
             ctx.put(outputXmlName, xml);
         }


		return null;

	}
	public static String subscribeUnSubscribeNPN(Context ctx) throws Exception{
		String str= null;
		String NPN = null;
		String subscribe = null;
		String URL = null;
		try {
			URL = SystemProperties.getInstance().getString("appl."+ctx.getProject()+".niprone.URL");
			NIPRONEService niprService=new NIPRONEService();
			NPN = ctx.get("NPN") != null && !HtmlConstants.EMPTY.equals(ctx.get("NPN"))? ctx.get("NPN").toString(): null;

			if(NPN == null || HtmlConstants.EMPTY.equals(NPN))
				return null;

			subscribe = ctx.get("IsSubscribe") != null && !HtmlConstants.EMPTY.equals(ctx.get("IsSubscribe"))? ctx.get("IsSubscribe").toString(): null;
			if(logger.isDebugEnabled(ctx))
				logger.debug(ctx, "Going to Hit Service URL : "+URL);
			if(subscribe != null && subscribe.equals("1"))
				str = niprService.subsribeNPN(ctx, NPN);
			else
				str = niprService.unSubsribeNPN(ctx, NPN);

			if(logger.isDebugEnabled(ctx))
				logger.debug(ctx, "Subscribing and UnSubscribing NPN Done. "+str);

		}catch (Exception e) {
			logger.error("Error while Subscribing and UnSubscribing NPN. "+e.getMessage());
			DataUtils.populateError((Context)ctx, "pageError1", "unableToProcessRequestMsgKey");
            return null;
		}
		return str;
	}

	public static Object checkValidBankAccount(Context ctx) throws Exception{
		String bankAccountNumber = null;
		if (ctx.get("bank_account") != null && !HtmlConstants.EMPTY.equals(ctx.get("bank_account"))) {
			boolean flag = false;
			bankAccountNumber = ctx.get("bank_account").toString();
			if (bankAccountNumber.length() >= 18) {
				DataUtils.populateError((Context) ctx, "bank_account", "bankAccountNumberRequiredDigitErrorkey");
			}
			if (bankAccountNumber.length() < 5) {
				DataUtils.populateError((Context) ctx, "bank_account", "invalidBankAccountNumberErrorKey");
			}

			if (bankAccountNumber != null && bankAccountNumber.length() == 1 && bankAccountNumber.equals("0")) {
				DataUtils.populateError((Context) ctx, "bank_account", "allBankAccountNumberDigitsShouldNotbeSameMsg");
			}
			if (bankAccountNumber != null && bankAccountNumber.length() > 1) {
				flag = ProducerOneUtils.validateBankAccountNumberWithSameDigit(bankAccountNumber);
				if (!flag) {
					DataUtils.populateError((Context) ctx, "bank_account", "allBankAccountNumberDigitsShouldNotbeSameMsg");
				}
			}
		}
		return null;
	}
	public static Object checkValidBankRoutingNumber(Context ctx) throws Exception{
		String bankRoutingNumber = "";
		if (ctx.get("bank_routing") != null && !HtmlConstants.EMPTY.equals(ctx.get("bank_routing"))) {
			boolean flag = false;
			bankRoutingNumber = ctx.get("bank_routing").toString();
			if (bankRoutingNumber.length() < 9) {
				DataUtils.populateError((Context) ctx, "bank_routing", "validRoutingNumberMsg");
			}
			if (bankRoutingNumber.equals("00") || bankRoutingNumber.equals("000000000")) {
				DataUtils.populateError((Context) ctx, "bank_routing", "validRoutingNumberMsg");
			}
			bankRoutingNumber = ctx.get("bank_routing").toString();
			if (bankRoutingNumber != null && bankRoutingNumber.length() > 1) {
				flag = ProducerOneUtils.validateBankAccountNumberWithSameDigit(bankRoutingNumber);
				if (!flag) {
					DataUtils.populateError((Context) ctx, "bank_routing", "validRoutingNumberMsg");
				}
			}
		}
		return null;
	}

	public static Object validateBankRoutingNumberFromWebService(Context ctx) throws Exception{
		ResponseValidateEcheckPayment response = new ResponseValidateEcheckPayment();

		String bankAccountNumber = null;
		String bankRoutingNumber = null;
		String userID = null;
		String reEnterBankAccountNumber = null;

		String isRoutingNumberValidationRequired = "N";
		try{
			isRoutingNumberValidationRequired = SystemProperties.getInstance().getString("appl.producerone.rounting.number.validation.required");
		}catch(Exception e){
			isRoutingNumberValidationRequired = "N";
		}

		if (ctx.get(Constants.INET_ERRORS_LIST) == null || HtmlConstants.EMPTY.equals(ctx.get(Constants.INET_ERRORS_LIST))) {
			if (ctx.get("bank_routing") != null && !HtmlConstants.EMPTY.equals(ctx.get("bank_routing"))
					&& ctx.get("bank_account") != null && !HtmlConstants.EMPTY.equals(ctx.get("bank_account"))
					&& "Y".equalsIgnoreCase(isRoutingNumberValidationRequired)) {

				bankAccountNumber = ctx.get("bank_account").toString();
				bankRoutingNumber = ctx.get("bank_routing").toString();
				userID = ctx.get("user_id") != null ? ctx.get("user_id").toString() : null;
				if(logger.isDebugEnabled(ctx))
					logger.debug(ctx, "User ID : " + ctx.get("user_id"));
				if(logger.isDebugEnabled(ctx))
					logger.debug(ctx, "Going to hit Routing Number Validation Service.");
				RoutingNumberValidation routingNumberValidation = new RoutingNumberValidation();
				ValidateEcheckPayment validateEcheckPayment = routingNumberValidation.createValidateEcheckPayment(userID, bankAccountNumber, bankRoutingNumber);
				try {
					response = routingNumberValidation.validate(validateEcheckPayment);
				} catch (Exception ex) {
					logger.error(ctx, "Unable to getting response from  Routing Number Validation due to error : " + DataUtils.getExceptionStackTrace(ex));
				}
				if(logger.isDebugEnabled(ctx))
					logger.debug(ctx, "Validation flag ::: " + response.getValidationFlg());
				if(logger.isDebugEnabled(ctx))
					logger.debug(ctx, "Bank Name ::: " + response.getBankName());
				if (response != null && response.getValidationFlg() != null && !HtmlConstants.EMPTY.equals(response.getValidationFlg())
						&& "Y".equals(response.getValidationFlg().toString())) {
					if(logger.isDebugEnabled(ctx))
						logger.debug(ctx, "Validation flag : " + response.getValidationFlg());
					if(logger.isDebugEnabled(ctx))
						logger.debug(ctx, "Bank Name : " + response.getBankName());
					ctx.put("bank_name", response.getBankName());
				} else {
					DataUtils.populateError((Context) ctx, "bank_routing","RoutingNumberServiceAccessFailed");
					return null;
				}

			}
		}
		return null;
	}


	public static void removeHyphenFromCommaSeperatedStringInContext(Context ctx, Object obj){
	    String str = obj.toString();

	    StringTokenizer tokens = new StringTokenizer(str, ",");
        while(tokens.hasMoreTokens()){
              String token = tokens.nextToken();
              if(ctx.get(token) != null && !HtmlConstants.EMPTY.equals(ctx.get(token).toString().trim())){
       	            ctx.put(token ,ctx.get(token).toString().trim().replaceAll("-", ""));
              }
        }
	}
	public static void checkAppointmentAffiliationRequired(Context ctx) throws Exception {
		try {
			if(ctx.get("tobeProcessed_mom_list_01") != null){
				List appointmentList = (List)ctx.get("tobeProcessed_mom_list_01");
			if(appointmentList != null && appointmentList.size()>0){
				for(int i=0;i<appointmentList.size();i++){
					Map listMap = (HashMap) appointmentList.get(i);
					if(listMap  != null && !listMap.isEmpty()){
						if(listMap.get("affiliation_required") != null && !HtmlConstants.EMPTY.equals(listMap.get("affiliation_required")) && "Y".equals(listMap.get("affiliation_required"))){
							ctx.put("affiliation_required", "Y");
							break;
						}else{
							ctx.put("affiliation_required", "N");
							break;
						}
					}
				}
			}else{
				ctx.put("affiliation_required", "N");
			}
			}
		} catch (Exception ex) {
			logger.error(ctx, "Unable to check Appointment Affiliation Required due to error : " + DataUtils.getExceptionStackTrace(ex));
		}
	}
	public static void getAppointmentIdForAppointmentTerminationLetter(Context ctx) throws Exception{
		try {
			List arrAppointmentLicensesList=null;
			String entityAgentLabel = null;
			boolean flag = false;
			int count = 0;
			Map cmap=null;
			String selectedRecords=null;
			ctx.put("BulkAppointmentMode","Y");
			if(ctx.get("LetterType").equals("agencyletter")){
				if(ctx.get("BulkAppointmentMode").equals("Y")){
					arrAppointmentLicensesList=(List)ctx.get("getBulkAppointTerminationLetter_mom_list_01");
				}else{
					arrAppointmentLicensesList=(List)ctx.get("AgencyLicensesAppointments_mom_list_1");
				}
			}else{
				if(ctx.get("BulkAppointmentMode").equals("Y")){
					arrAppointmentLicensesList=(List)ctx.get("getBulkAppointTerminationLetter_mom_list_01");
				}else{
					 arrAppointmentLicensesList=(List)ctx.get("getAgentLicesneDetails_mom_list_1");
				}
			}
			if(ctx.get("method") != null && ctx.get("method").equals("downloadBulkAppointmentLetter")){
				Integer id=0;
				for( int c=0;c<arrAppointmentLicensesList.size();c++){
					cmap=null;
					cmap=(HashMap) arrAppointmentLicensesList.get(c);
					if(cmap!=null){
						if(ctx.get("BulkAppointmentMode").equals("Y") && ctx.get("isApppointPage").equals("entityAppoint")){
							id=(Integer) cmap.get("agency_licenses_appointment_id");
							Map<String,String> map = CacheManager.get("Labels_Conf") == null ? null : (HashMap)CacheManager.get("Labels_Conf");
					        if(map != null && map.containsKey("entitytablabel"))
					        	entityAgentLabel = map.get("entitytablabel");

					        ctx.put("entityAgentLabel", entityAgentLabel);
						}
						 else if(ctx.get("BulkAppointmentMode").equals("Y") && ctx.get("isApppointPage").equals("agentAppoint")){
							 id=(Integer) cmap.get("agent_licenses_appointment_id");
							 Map<String,String> map = CacheManager.get("Labels_Conf") == null ? null : (HashMap)CacheManager.get("Labels_Conf");
						        if(map != null && map.containsKey("individualtablabel"))
						        	entityAgentLabel = map.get("individualtablabel");

						     ctx.put("entityAgentLabel", entityAgentLabel);

						 }
						String ctxID=(String)ctx.get("ajax_field_chkLetter_"+c);
							if(ctxID!=null ){
								if(ctxID.equals("Y") && cmap.get("termination_transaction_id")!=null && Integer.parseInt(cmap.get("termination_transaction_id").toString())==0){
									if(count>0){
										selectedRecords=selectedRecords.concat(",").concat(String.valueOf(id));
									}else{
										selectedRecords=String.valueOf(id);
										if(cmap.get("is_terminated")!=null ){
											//flag=true;
										}
									}
									count++;
								}
							}
						}
					}
				ctx.put("commaseperatedAppointedID", selectedRecords);
				}else if(ctx.get("method") != null && ctx.get("method").equals("downloadBulkTerminationLetter")){
					Integer id=0;
					for( int c=0;c<arrAppointmentLicensesList.size();c++){
						cmap=(HashMap) arrAppointmentLicensesList.get(c);
						if(cmap!=null){
							if(ctx.get("BulkAppointmentMode").equals("Y") && ctx.get("isApppointPage").equals("entityAppoint")){
								id=(Integer) cmap.get("agency_licenses_appointment_id");
								Map<String,String> map = CacheManager.get("Labels_Conf") == null ? null : (HashMap)CacheManager.get("Labels_Conf");
						        if(map != null && map.containsKey("entitytablabel"))
						        	entityAgentLabel = map.get("entitytablabel");

						        ctx.put("entityAgentLabel", entityAgentLabel);
							}else if(ctx.get("BulkAppointmentMode").equals("Y") && ctx.get("isApppointPage").equals("agentAppoint")){
								 id=(Integer) cmap.get("agent_licenses_appointment_id");

								 Map<String,String> map = CacheManager.get("Labels_Conf") == null ? null : (HashMap)CacheManager.get("Labels_Conf");
							        if(map != null && map.containsKey("individualtablabel"))
							        	entityAgentLabel = map.get("individualtablabel");

							     ctx.put("entityAgentLabel", entityAgentLabel);
							}
							String ctxID=(String)ctx.get("ajax_field_chkLetter_"+c);
							if(ctxID!=null ){
								if(ctxID.equals("Y") &&  cmap.get("termination_transaction_id")!=null ){
									if(count>0){
										selectedRecords=selectedRecords.concat(",").concat(String.valueOf(id));
									}else{
										selectedRecords=String.valueOf(id);
										if(cmap.get("is_terminated")==null){
											//flag=true;
										}
									}
									count++;
								}
							}
						}
					}
					ctx.put("commaseperatedTerminationID", selectedRecords);
			}
			DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);
			ctx.put("date", df.format(new Date()));
			/*if(ctx.get("LetterType").equals("agencyletter")){
				HashMap agencyMap = (HashMap)SqlResources.getSqlMapProcessor(ctx).findByKey("agency_master.findByKey",ctx);
				if(agencyMap != null){
					String agencyName = (String) agencyMap.get("name");
					//ctx.put("agencyname", agencyName);
					ctx.put("agencyname", agencyName);
				}

				Map addressMap = (Map)SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesGetAgencyAddressForAppointmentsaddress",ctx);
				//ctx.put("addressMap", addressMap);

				ctx.put("addressMap", addressMap);
				Map<String,String> map = CacheManager.get("Labels_Conf") == null ? null : (HashMap)CacheManager.get("Labels_Conf");
		        if(map != null && map.containsKey("entitytablabel"))
		        	entityAgentLabel = map.get("entitytablabel");

		       ctx.put("entityAgentLabel", entityAgentLabel);
			}else{
				HashMap agentMap = (HashMap)SqlResources.getSqlMapProcessor(ctx).findByKey("person.findByKey",ctx);
				if(agentMap != null){
					String agentFirstName = (String) agentMap.get("prdcr_first_name");
					String agentLastName = (String) agentMap.get("prdcr_last_name");
					String agentName=agentFirstName+" "+agentLastName;
					//ctx.put("agencyname", agentName);
					ctx.put("agentname", agentName);
				}
				HashMap agencyMap = (HashMap)SqlResources.getSqlMapProcessor(ctx).findByKey("agency_master.findByKey",ctx);
				if(agencyMap != null){
					String agencyName = (String) agencyMap.get("name");
					//ctx.put("agencyname", agencyName);
					ctx.put("agencyname", agencyName);
				}


				Map addressMap = (Map)SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesGetAgentAddressForAppointmentsAppointmentLetter",ctx);
				//ctx.put("addressMap", addressMap);

				ctx.put("addressMap", addressMap);
				Map<String,String> map = CacheManager.get("Labels_Conf") == null ? null : (HashMap)CacheManager.get("Labels_Conf");
		        if(map != null && map.containsKey("individualtablabel"))
		        	entityAgentLabel = map.get("individualtablabel");

		        ctx.put("entityAgentLabel", entityAgentLabel);
			}*/
		} catch (Exception e) {
			logger.error(ctx, "Unable to get Appointment Id For Appointment Termination Letter due to error : " + DataUtils.getExceptionStackTrace(e));
		}
	}

	public static boolean checkIfDuplicateEmail(Context ctx) throws Exception{
		String listName = "additionalEmailAddress_list_listfreeform_1";
		boolean result = false;
			if(ctx.get(listName) != null){
				List<Object> addressList = (List<Object>)ctx.get(listName);
				if(addressList.size() > 0){
					for(Object obj : addressList){
						if(obj != null){
							Map<Object, Object> map = (HashMap<Object, Object>) obj;
							boolean sameCategory = ctx.get("email_category") != null && map.get("email_category").equals(ctx.get("email_category"));
							boolean sameEmail = ctx.get("email_additionalAddress") != null && map.get("email_additionalAddress").equals(ctx.get("email_additionalAddress"));
							if(sameCategory && sameEmail){
								DataUtils.populateError(ctx, "additionalEmailAddressError", "adminDuplicateError");
								result = true;
								break;
							}
						}
					}
				}
			}
		return result;
	}

	public static Object assignValueinVariable(Context ctx, String fieldName, String fieldValue){
  		if(ctx.get(fieldValue) != null)
  			fieldValue = (String)ctx.get(fieldValue);

  		ctx.put(ctx.get(fieldName), fieldValue);

  		return fieldValue;
  	}

	public static Object validateAllFieldsForAwardNRecognitionsNotEmpty(Context ctx) throws Exception{

		if(ctx.get("DocumentRequest") != null && ctx.get("DocumentRequest") instanceof HttpServletRequest){
            HttpServletRequest req = (HttpServletRequest)ctx.get("DocumentRequest");
            HttpSession sess = req.getSession();

            boolean checkError=false;
            List finalList = new ArrayList();
            if(sess.getAttribute("awardsNRecognitionsTab_list_listfreeform_1") != null &&
                    sess.getAttribute("awardsNRecognitionsTab_list_listfreeform_1") instanceof List){
                finalList = (List)sess.getAttribute("awardsNRecognitionsTab_list_listfreeform_1");

            	if( ctx.get("inet_method").equals("updateAwardsNRecognition") && (ctx.get("awardsNRecognitionsTab_producer_num_id_security").equals("N") || (ctx.get("producer_num_id_1") == null || HtmlConstants.EMPTY.equals(ctx.get("producer_num_id_1").toString())))
                		&& (ctx.get("awardsNRecognitionsTab_conference_type_id_security").equals("N") || (ctx.get("conference_type_id") == null || HtmlConstants.EMPTY.equals(ctx.get("conference_type_id").toString())))
                        && (ctx.get("awardsNRecognitionsTab_award_name_recognition_id_security").equals("N") || (ctx.get("award_name_recognition_id") == null || HtmlConstants.EMPTY.equals(ctx.get("award_name_recognition_id").toString())))
                        && (ctx.get("awardsNRecognitionsTab_award_calendar_year_security").equals("N") ||(ctx.get("award_calendar_year") == null || HtmlConstants.EMPTY.equals(ctx.get("award_calendar_year").toString())))
                        && (ctx.get("awardsNRecognitionsTab_quarter_of_the_year_security").equals("N") || (ctx.get("quarter_of_the_year") == null || HtmlConstants.EMPTY.equals(ctx.get("quarter_of_the_year").toString())))
                        && (ctx.get("awardsNRecognitionsTab_trip_location_security").equals("N") || (ctx.get("trip_location") == null || HtmlConstants.EMPTY.equals(ctx.get("trip_location").toString())))
                        && (ctx.get("awardsNRecognitionsTab_rank_security").equals("N") || (ctx.get("rank") == null || HtmlConstants.EMPTY.equals(ctx.get("rank").toString())))
                        && (ctx.get("awardsNRecognitionsTab_effective_date_security").equals("N") || (ctx.get("effective_date") == null || HtmlConstants.EMPTY.equals(ctx.get("effective_date").toString())))
                        && (ctx.get("awardsNRecognitionsTab_expiration_date_security").equals("N") || (ctx.get("expiration_date") == null || HtmlConstants.EMPTY.equals(ctx.get("expiration_date").toString())))){

                         return null;
                	}


                if(ctx.get("inet_method").equals("addNewAward") || ctx.get("inet_method").equals("updateAwardsNRecognition")){
                	if(ctx.get("awardsNRecognitionsTab_producer_num_id_security").equals("Y") && ctx.get("awardsNRecognitionsTab_producer_num_id_mandatory_security").equals("Y") && (ctx.get("producer_num_id_1") == null || HtmlConstants.EMPTY.equals(ctx.get("producer_num_id_1").toString()))){
                		DataUtils.populateError((Context)ctx, "producer_num_id_1", "awardProducerNumErrorKey");
                		checkError=true;
                	}
                	if(ctx.get("awardsNRecognitionsTab_conference_type_id_security").equals("Y") && ctx.get("awardsNRecognitionsTab_conference_type_id_mandatory_security").equals("Y") && (ctx.get("conference_type_id") == null || HtmlConstants.EMPTY.equals(ctx.get("conference_type_id").toString()))){
                		DataUtils.populateError((Context)ctx, "conference_type_id", "awardTypeErrorKey");
                		checkError=true;
                	}
                	if(ctx.get("awardsNRecognitionsTab_award_name_recognition_id_security").equals("Y") && ctx.get("awardsNRecognitionsTab_award_name_recognition_id_mandatory_security").equals("Y") && (ctx.get("award_name_recognition_id") == null || HtmlConstants.EMPTY.equals(ctx.get("award_name_recognition_id").toString()))){
                		DataUtils.populateError((Context)ctx, "award_name_recognition_id", "awardNameErrorKey");
                		checkError=true;
                	}
                	if(ctx.get("awardsNRecognitionsTab_award_calendar_year_security").equals("Y") && ctx.get("awardsNRecognitionsTab_award_calendar_year_mandatory_security").equals("Y") && (ctx.get("award_calendar_year") == null || HtmlConstants.EMPTY.equals(ctx.get("award_calendar_year").toString()))){
                		DataUtils.populateError((Context)ctx, "award_calendar_year", "awardYearErrorKey");
                		checkError=true;
                	}
                	if(ctx.get("awardsNRecognitionsTab_quarter_of_the_year_security").equals("Y") && ctx.get("awardsNRecognitionsTab_quarter_of_the_year_mandatory_security").equals("Y") && (ctx.get("quarter_of_the_year") == null || HtmlConstants.EMPTY.equals(ctx.get("quarter_of_the_year").toString()))){
                		DataUtils.populateError((Context)ctx, "quarter_of_the_year", "awardQuaterOfYearErrorKey");
                		checkError=true;
                	}
                	if(ctx.get("awardsNRecognitionsTab_trip_location_security").equals("Y") && ctx.get("awardsNRecognitionsTab_trip_location_mandatory_security").equals("Y") && (ctx.get("trip_location") == null || HtmlConstants.EMPTY.equals(ctx.get("trip_location").toString()))){
                		DataUtils.populateError((Context)ctx, "trip_location", "awardTripLocationErrorKey");
                		checkError=true;
                	}
                	if(ctx.get("awardsNRecognitionsTab_rank_security").equals("Y") && ctx.get("awardsNRecognitionsTab_rank_mandatory_security").equals("Y") && (ctx.get("rank") == null || HtmlConstants.EMPTY.equals(ctx.get("rank").toString()))){
                		DataUtils.populateError((Context)ctx, "rank", "awardRankErrorKey");
                		checkError=true;
                	}
                	if(ctx.get("awardsNRecognitionsTab_effective_date_security").equals("Y") && ctx.get("awardsNRecognitionsTab_effective_date_mandatory_security").equals("Y") && (ctx.get("effective_date") == null || HtmlConstants.EMPTY.equals(ctx.get("effective_date").toString()))){
                		DataUtils.populateError((Context)ctx, "effective_date", "awardEffectiveDateErrorKey");
                		checkError=true;
                	}
                	if(ctx.get("awardsNRecognitionsTab_expiration_date_security").equals("Y") && ctx.get("awardsNRecognitionsTab_expiration_date_mandatory_security").equals("Y") && (ctx.get("expiration_date") == null || HtmlConstants.EMPTY.equals(ctx.get("expiration_date").toString()))){
                		DataUtils.populateError((Context)ctx, "expiration_date", "awardExpirationDateErrorKey");
                		checkError=true;
                	}
                	if(checkError)
                    	return null;

                }

                String producer_num_id = ctx.get("producer_num_id_1") != null ? ctx.get("producer_num_id_1").toString() : null;
                String conference_type_id = ctx.get("conference_type_id") != null  ? ctx.get("conference_type_id").toString() :  null;
                String award_name_recognition_id = ctx.get("award_name_recognition_id") != null ? ctx.get("award_name_recognition_id").toString() :  null;
                String award_calendar_year = ctx.get("award_calendar_year") != null ? ctx.get("award_calendar_year").toString() :  null;
                String quarter_of_the_year = ctx.get("quarter_of_the_year") != null ? ctx.get("quarter_of_the_year").toString() :  null;
                String trip_location = ctx.get("trip_location") != null ? ctx.get("trip_location").toString() :  null;
                String rank = ctx.get("rank") != null ? ctx.get("rank").toString() :  null;
                String effectiveDate = (ctx.get("effective_date") != null && !HtmlConstants.EMPTY.equals(ctx.get("effective_date"))) ? ctx.get("effective_date").toString() : null;
                String expirationDate = (ctx.get("expiration_date") != null && !HtmlConstants.EMPTY.equals(ctx.get("expiration_date"))) ? ctx.get("expiration_date").toString() : null;

                for(int i=0; i<finalList.size(); i++){
                    Map row = (Map)finalList.get(i);

                    if((ctx.get("awardsNRecognitionsTab_producer_num_id_security").equals("N") || (row.get("producer_num_id_1") != null && row.get("producer_num_id_1").toString().equals(producer_num_id))) &&
                            (ctx.get("awardsNRecognitionsTab_conference_type_id_security").equals("N") || (row.get("conference_type_id") != null && row.get("conference_type_id").toString().equals(conference_type_id))) &&
                            (ctx.get("awardsNRecognitionsTab_award_name_recognition_id_security").equals("N") || (row.get("award_name_recognition_id") != null && row.get("award_name_recognition_id").toString().equals(award_name_recognition_id))) &&
                            (ctx.get("awardsNRecognitionsTab_award_calendar_year_security").equals("N") || (row.get("award_calendar_year") != null && row.get("award_calendar_year").toString().equals(award_calendar_year))) &&
                            (ctx.get("awardsNRecognitionsTab_quarter_of_the_year_security").equals("N") || (row.get("quarter_of_the_year").toString().equals(quarter_of_the_year))) &&
                            (ctx.get("awardsNRecognitionsTab_trip_location_security").equals("N") || (row.get("trip_location") != null && row.get("trip_location").toString().equals(trip_location))) &&
                            (ctx.get("awardsNRecognitionsTab_rank_security").equals("N") || (row.get("rank") != null && row.get("rank").toString().equals(rank))) &&
                            (ctx.get("awardsNRecognitionsTab_effective_date_security").equals("N") || (row.get("effective_date") != null && row.get("effective_date").toString().equals(effectiveDate))) &&
                            (ctx.get("awardsNRecognitionsTab_expiration_date_security").equals("N") || (row.get("expiration_date") != null && row.get("expiration_date").toString().equals(expirationDate)))){
                    	   DataUtils.populateError((Context)ctx, "awardsNRecognitionsError", "adminDuplicateError");
                        return null;
                    }
                }
            }
        }

		return null;
	}

public static Object validateAllFieldsForAwardNRecognitionsNotEmptyOnProducerAsscoaitionContact(Context ctx) throws Exception{

		if(ctx.get("DocumentRequest") != null && ctx.get("DocumentRequest") instanceof HttpServletRequest){
            HttpServletRequest req = (HttpServletRequest)ctx.get("DocumentRequest");
            HttpSession sess = req.getSession();

            boolean checkError=false;
            List finalList = new ArrayList();
            if(sess.getAttribute("awardsNRecognitions_list_listfreeform_1") != null &&
                    sess.getAttribute("awardsNRecognitions_list_listfreeform_1") instanceof List){
                finalList = (List)sess.getAttribute("awardsNRecognitions_list_listfreeform_1");
                ctx.put("checkFlag", "Y");
            	if( ctx.get("inet_method").equals("updateProducerAssociationData") && (ctx.get("awardsNRecognitions_producer_num_id_security").equals("N") || (ctx.get("producer_num_id_1") == null || HtmlConstants.EMPTY.equals(ctx.get("producer_num_id_1").toString())))
                        && (ctx.get("awardsNRecognitions_conference_type_id_security").equals("N") || (ctx.get("conference_type_id") == null || HtmlConstants.EMPTY.equals(ctx.get("conference_type_id").toString())))
                        && (ctx.get("awardsNRecognitions_award_name_recognition_id_security").equals("N") || (ctx.get("award_name_recognition_id") == null || HtmlConstants.EMPTY.equals(ctx.get("award_name_recognition_id").toString())))
                        && (ctx.get("awardsNRecognitions_award_calendar_year_security").equals("N") || (ctx.get("award_calendar_year") == null || HtmlConstants.EMPTY.equals(ctx.get("award_calendar_year").toString())))
                        && (ctx.get("awardsNRecognitions_quarter_of_the_year_security").equals("N") || (ctx.get("quarter_of_the_year") == null || HtmlConstants.EMPTY.equals(ctx.get("quarter_of_the_year").toString())))
                        && (ctx.get("awardsNRecognitions_trip_location_security").equals("N") || (ctx.get("trip_location") == null || HtmlConstants.EMPTY.equals(ctx.get("trip_location").toString())))
                        && (ctx.get("awardsNRecognitions_rank_security").equals("N") || (ctx.get("rank") == null || HtmlConstants.EMPTY.equals(ctx.get("rank").toString())))
                        && (ctx.get("awardsNRecognitions_effective_date_security").equals("N") || (ctx.get("awards_effective_date") == null || HtmlConstants.EMPTY.equals(ctx.get("awards_effective_date").toString())))
                        && (ctx.get("awardsNRecognitions_expiration_date_security").equals("N") || (ctx.get("expiration_date") == null || HtmlConstants.EMPTY.equals(ctx.get("expiration_date").toString())))){
            		    ctx.put("checkFlag", "N");
            		  return null;
                     }


                if(ctx.get("inet_method").equals("addNewAward") || ctx.get("inet_method").equals("updateProducerAssociationData")){

                	if(ctx.get("awardsNRecognitions_producer_num_id_security").equals("Y") && ctx.get("awardsNRecognitions_producer_num_id_mandatory_security").equals("Y") && (ctx.get("producer_num_id_1") == null || HtmlConstants.EMPTY.equals(ctx.get("producer_num_id_1").toString()))){
                		DataUtils.populateError((Context)ctx, "producer_num_id_1", "awardProducerNumErrorKey");
                		checkError=true;
                	}
                	if(ctx.get("awardsNRecognitions_conference_type_id_security").equals("Y") && ctx.get("awardsNRecognitions_conference_type_id_mandatory_security").equals("Y") && (ctx.get("conference_type_id") == null || HtmlConstants.EMPTY.equals(ctx.get("conference_type_id").toString()))){
                		DataUtils.populateError((Context)ctx, "conference_type_id", "awardTypeErrorKey");
                		checkError=true;
                	}
                	if(ctx.get("awardsNRecognitions_award_name_recognition_id_security").equals("Y") && ctx.get("awardsNRecognitions_award_name_recognition_id_mandatory_security").equals("Y") && (ctx.get("award_name_recognition_id") == null || HtmlConstants.EMPTY.equals(ctx.get("award_name_recognition_id").toString()))){
                		DataUtils.populateError((Context)ctx, "award_name_recognition_id", "awardNameErrorKey");
                		checkError=true;
                	}
                	if(ctx.get("awardsNRecognitions_award_calendar_year_security").equals("Y") && ctx.get("awardsNRecognitions_award_calendar_year_mandatory_security").equals("Y") && (ctx.get("award_calendar_year") == null || HtmlConstants.EMPTY.equals(ctx.get("award_calendar_year").toString()))){
                		DataUtils.populateError((Context)ctx, "award_calendar_year", "awardYearErrorKey");
                		checkError=true;
                	}
                	if(ctx.get("awardsNRecognitions_quarter_of_the_year_security").equals("Y") && ctx.get("awardsNRecognitions_quarter_of_the_year_mandatory_security").equals("Y") && (ctx.get("quarter_of_the_year") == null || HtmlConstants.EMPTY.equals(ctx.get("quarter_of_the_year").toString()))){
                		DataUtils.populateError((Context)ctx, "quarter_of_the_year", "awardQuaterOfYearErrorKey");
                		checkError=true;
                	}
                	if(ctx.get("awardsNRecognitions_trip_location_security").equals("Y") && ctx.get("awardsNRecognitions_trip_location_mandatory_security").equals("Y") && (ctx.get("trip_location") == null || HtmlConstants.EMPTY.equals(ctx.get("trip_location").toString()))){
                		DataUtils.populateError((Context)ctx, "trip_location", "awardTripLocationErrorKey");
                		checkError=true;
                	}
                	if(ctx.get("awardsNRecognitions_rank_security").equals("Y") && ctx.get("awardsNRecognitions_rank_mandatory_security").equals("Y") && (ctx.get("rank") == null || HtmlConstants.EMPTY.equals(ctx.get("rank").toString()))){
                		DataUtils.populateError((Context)ctx, "rank", "awardRankErrorKey");
                		checkError=true;
                	}
                	if(ctx.get("awardsNRecognitions_effective_date_security").equals("Y") && ctx.get("awardsNRecognitions_effective_date_mandatory_security").equals("Y") && (ctx.get("awards_effective_date") == null || HtmlConstants.EMPTY.equals(ctx.get("awards_effective_date").toString()))){
                		DataUtils.populateError((Context)ctx, "awards_effective_date", "awardEffectiveDateErrorKey");
                		checkError=true;
                	}
                	if(ctx.get("awardsNRecognitions_expiration_date_security").equals("Y") && ctx.get("awardsNRecognitions_expiration_date_mandatory_security").equals("Y") && (ctx.get("expiration_date") == null || HtmlConstants.EMPTY.equals(ctx.get("expiration_date").toString()))){
                		DataUtils.populateError((Context)ctx, "expiration_date", "awardExpirationDateErrorKey");
                		checkError=true;
                	}
                	if(checkError)
                    	return null;

                }

                String producer_num_id = ctx.get("producer_num_id_1") != null ? ctx.get("producer_num_id_1").toString() : null;
                String conference_type_id = ctx.get("conference_type_id") != null  ? ctx.get("conference_type_id").toString() :  null;
                String award_name_recognition_id = ctx.get("award_name_recognition_id") != null ? ctx.get("award_name_recognition_id").toString() :  null;
                String award_calendar_year = ctx.get("award_calendar_year") != null ? ctx.get("award_calendar_year").toString() :  null;
                String quarter_of_the_year = ctx.get("quarter_of_the_year") != null ? ctx.get("quarter_of_the_year").toString() :  null;
                String trip_location = ctx.get("trip_location") != null ? ctx.get("trip_location").toString() :  null;
                String rank = ctx.get("rank") != null ? ctx.get("rank").toString() :  null;
                String effectiveDate = (ctx.get("awards_effective_date") != null && !HtmlConstants.EMPTY.equals(ctx.get("awards_effective_date"))) ? ctx.get("awards_effective_date").toString() : null;
                String expirationDate = (ctx.get("expiration_date") != null && !HtmlConstants.EMPTY.equals(ctx.get("expiration_date"))) ? ctx.get("expiration_date").toString() : null;

                for(int i=0; i<finalList.size(); i++){
                    Map row = (Map)finalList.get(i);

                    if((ctx.get("awardsNRecognitions_producer_num_id_security").equals("N") || (row.get("producer_num_id_1") != null && row.get("producer_num_id_1").toString().equals(producer_num_id))) &&
                            (ctx.get("awardsNRecognitions_conference_type_id_security").equals("N") || (row.get("conference_type_id") != null && row.get("conference_type_id").toString().equals(conference_type_id))) &&
                            (ctx.get("awardsNRecognitions_award_name_recognition_id_security").equals("N") || (row.get("award_name_recognition_id") != null && row.get("award_name_recognition_id").toString().equals(award_name_recognition_id))) &&
                            (ctx.get("awardsNRecognitions_award_calendar_year_security").equals("N") || (row.get("award_calendar_year") != null && row.get("award_calendar_year").toString().equals(award_calendar_year))) &&
                            (ctx.get("awardsNRecognitions_quarter_of_the_year_security").equals("N") || (row.get("quarter_of_the_year").toString().equals(quarter_of_the_year))) &&
                            (ctx.get("awardsNRecognitions_trip_location_security").equals("N") || (row.get("trip_location") != null && row.get("trip_location").toString().equals(trip_location))) &&
                            (ctx.get("awardsNRecognitions_rank_security").equals("N") || (row.get("rank") != null && row.get("rank").toString().equals(rank))) &&
                            (ctx.get("awardsNRecognitions_effective_date_security").equals("N") || (row.get("awards_effective_date") != null && row.get("awards_effective_date").toString().equals(effectiveDate))) &&
                            (ctx.get("awardsNRecognitions_expiration_date_security").equals("N") || (row.get("expiration_date") != null && row.get("expiration_date").toString().equals(expirationDate)))){
                    	   DataUtils.populateError((Context)ctx, "awardsNRecognitionsError", "adminDuplicateError");
                        return null;
                    }
                }
            }
        }

		return null;




	}

public static Object getAllFieldDataFromContextAsXMLBasedOnFieldXML(Context ctx,String screensName, String additionalFieldsForFieldDataXml)
        throws Exception {

	org.jdom.Element root = null;
    root = new org.jdom.Element("Root");
    org.jdom.Element root1 = new org.jdom.Element("Root1");

    String outputFieldName = ctx.get("outputFieldDataXmlName") != null && !HtmlConstants.EMPTY.equals(ctx.get("outputFieldDataXmlName").toString()) ? ctx.get("outputFieldDataXmlName").toString() : null;

    if(screensName == null || HtmlConstants.EMPTY.equals(screensName))
    	screensName = ctx.get("inet_page").toString();

    screensName =  ctx.get(screensName).toString();
	StringTokenizer screenTokenizer = new StringTokenizer(screensName,",");

	while (screenTokenizer.hasMoreTokens()) {
        String screenName = screenTokenizer.nextToken();

	List fieldList = FieldsResources.getInstance(ctx).getFieldListByScreen(ctx, screenName);
	if(fieldList != null && fieldList.size() > 0){

			for(int i=0; i<fieldList.size(); i++){
				FieldImpl fieldImpl = (FieldImpl)fieldList.get(i);

				String fieldName = fieldImpl.getName();
				if(fieldName != null && ctx.get(fieldName)!= null
							&& !HtmlConstants.EMPTY.equals(ctx.get(fieldImpl.getName()))){

					org.jdom.Element childElement = new org.jdom.Element(fieldName);
	                String fieldData = ctx.get(fieldName) != null && !HtmlConstants.EMPTY.equals(ctx.get(fieldName).toString()) ? ctx.get(fieldName).toString() : null;
	                childElement.addContent(fieldData);
	                root1.addContent(childElement);
				}
			}
		}
	}
	 if(additionalFieldsForFieldDataXml != null && !HtmlConstants.EMPTY.equals(additionalFieldsForFieldDataXml)
             && ctx.get(additionalFieldsForFieldDataXml) != null && !HtmlConstants.EMPTY.equals(ctx.get(additionalFieldsForFieldDataXml).toString())){
		 int listCount =0;
		 StringTokenizer tokens = new StringTokenizer(ctx.get(additionalFieldsForFieldDataXml).toString(), ",");

         while(tokens.hasMoreTokens()){
             String objectName = tokens.nextToken();

             if(objectName == null || HtmlConstants.EMPTY.equals(objectName)
                     || ctx.get(objectName) == null || HtmlConstants.EMPTY.equals(ctx.get(objectName).toString())){
                 continue;
             }

             if(ctx.get(objectName) instanceof List){
                 org.jdom.Element listRoot = null;

                 List dataList = ctx.get(objectName) != null ? (List)ctx.get(objectName) : null;
                 if(dataList != null && dataList.size() > 0){
                     listCount++;

                     for(int i =0; i<dataList.size(); i++){
                         Map map = (Map)dataList.get(i);
                         listRoot = new org.jdom.Element("listRow_"+ listCount);
                         if(map != null){
                             Set set = map.keySet();
                             Iterator<String> itr = set.iterator();

                             while(itr.hasNext()){
                                 String key = itr.next();
                                 org.jdom.Element rowElement = new org.jdom.Element(key);

                                 if(ctx.get(key+"_"+i) != null){
                                     if(!HtmlConstants.EMPTY.equals(ctx.get(key+"_"+i).toString())){
                                         rowElement.addContent(ctx.get(key+"_"+i).toString());
                                         listRoot.addContent(rowElement);
                                     }else{
                                         rowElement.addContent("");
                                         listRoot.addContent(rowElement);
                                     }

                                 }else{

                                    if(map.get(key) != null){
                                         rowElement.addContent(map.get(key).toString());
                                         listRoot.addContent(rowElement);
                                     }else if(map.get(key) == null){
                                         rowElement.addContent("");
                                         listRoot.addContent(rowElement);
                                     }
                                 }
                             }
                             root1.addContent(listRoot);
                         }

                     }

                 }
             }else{
                 org.jdom.Element fieldElement = null;
                 fieldElement = new org.jdom.Element(objectName);
                 fieldElement.addContent(ctx.get(objectName).toString());
                 root1.addContent(fieldElement);
             }

         }

	 }
     if(root != null){
         root.addContent(root1);
     }else if(root1 != null){
         root = new org.jdom.Element("Root");
         root.addContent(root1);
     }

     XMLOutputter output = new XMLOutputter();
     String resultXML = output.outputString(root);
     ctx.put(outputFieldName, resultXML);
     return null;
	}

	public static void checkDuplicateSubProducerCode(Context ctx, String subProducerCode) throws Exception {
		if(ctx.get("listfreeformid") != null && !ctx.get("listfreeformid").toString().equals(HtmlConstants.EMPTY)){
			String listfreeformid = ctx.get("listfreeformid").toString();
			if(ctx.get("DocumentRequest") != null && ctx.get("DocumentRequest") instanceof HttpServletRequest){
				HttpServletRequest req = (HttpServletRequest)ctx.get("DocumentRequest");
				HttpSession sess = req.getSession();
				if(subProducerCode != null && (Integer.parseInt(subProducerCode) <=99) && Integer.parseInt(subProducerCode) >=02){
				List finalList = new ArrayList();
				if(sess.getAttribute(listfreeformid) != null &&
						sess.getAttribute(listfreeformid) instanceof List){
					finalList = (List)sess.getAttribute(listfreeformid);
				}
				for(int i=0; i<finalList.size(); i++){
					Map rowMap = (Map)finalList.get(i);
					if(rowMap.get("sub_producer") != null)
					{
						if(rowMap.get("sub_producer").equals(subProducerCode))
							DataUtils.populateError(ctx, "subProducerAssociationError", "subProducerAssociationDuplicateErrorKey");
					}
				}
				}else{
					if(subProducerCode != null)
					DataUtils.populateError(ctx, "sub_producer", "subProducerLengthErrorKey");
				}
			}
		}

}

	public static void checkAtleatst1DocumentMandatoryWhileSendInvitationValidation(Context ctx) throws Exception {


		if(!ctx.get(HtmlConstants.INET_METHOD).toString().equals("step1Next") )
			return;

		if(ctx.get("DocumentRequest") != null && ctx.get("DocumentRequest") instanceof HttpServletRequest){
			HttpServletRequest req = (HttpServletRequest)ctx.get("DocumentRequest");
			HttpSession sess = req.getSession();

			List documentList = (List)sess.getAttribute("uploadPopup_list_1");
			if(documentList == null || documentList.size() ==0)
				DataUtils.populateError(ctx, "uploadDocumentpageError", "atleast1DocumentShouldBeAttachedErrorKey");

		}
	}

	public static void  validateExistingPrimaryAgency(Context ctx) throws Exception {
		String primaryAgencyExists = ctx.get("primaryAgencyExists") != null ? ctx.get("primaryAgencyExists").toString(): "0";
		if(Integer.valueOf(primaryAgencyExists)== 0){
			DataUtils.populateError(ctx, "national_agency_desc", "validAgencyNotFoundErrorKey");
		}
	}

public static Object getAttachmentListForSendInvitationEmail(Context ctx) throws Exception {

		new SetParametersForStoredProcedures().setParametersInContext(ctx, "aar_requestid");
		List documentList = SqlResources.getSqlMapProcessor(ctx).select("tbl_address.getDocumentListAttachedForInvitation_p",ctx);

		if(documentList != null && documentList.size() > 0){
			List documentAttachmentList = new ArrayList();
			for(int i=0;i<documentList.size();i++){
				Map map = (Map)documentList.get(i);
				String file_name  = (String)map.get("document_name");
				String document_id = map.get("object_id")+"";

				ctx.put("file_name",file_name);
				ctx.put("document_id",document_id);

				 byte[] rb = null;

	            String isDMSIntegrationdone = SystemProperties.getInstance().getString("dms.integrationdone");
	            if(isDMSIntegrationdone != null && isDMSIntegrationdone.equals("Y")){
	                //going to get DMS provider
	                String dmsProvider = SystemProperties.getInstance().getString("dms.provider");
	                if(dmsProvider != null){
	                    if(dmsProvider.equalsIgnoreCase("wss3")){
	                        //downloading file from sharepoint
	                        rb = new DocumentUploadBO().getDocumentFromSharePoint(ctx);

	                        if(rb == null){
	                        	if(logger.isDebugEnabled(ctx))
	                        		logger.debug(ctx, "No file contents read from sharepoint");
	                            DataUtils.populateError((Context)ctx, "documentName", "downloadDocErrorKey");
	                            DataUtils.populateError((Context)ctx, "pageError", "downloadDocErrorKey");
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
	                        	if(logger.isDebugEnabled(ctx))
	                        		logger.debug(ctx, "No file contents read from DMS");
	                            DataUtils.populateError((Context)ctx, "documentName", "downloadDocErrorKey");
	                            DataUtils.populateError((Context)ctx, "pageError", "downloadDocErrorKey");
	                            return null;
	                        }
	                        map.put("content", rb);
	                        map.put("filename", file_name);
	                    }
	                }
	            }else{
	            	if(logger.isDebugEnabled(ctx))
	            		logger.debug(ctx, "DMS integration not done");
	                DataUtils.populateError((Context)ctx, "documentName", "downloadDocErrorKey");
	                DataUtils.populateError((Context)ctx, "pageError", "downloadDocErrorKey");
	                return null;
	            }


	            documentAttachmentList.add(map);

			}
			ctx.put("attachmentcontent", documentAttachmentList);
		}

		return null;
	}

	public static Object getUsertoAssignOnboardingApplication(Context ctx,String additionalFieldsForUserAssignment) throws Exception{

		String assignedUserForOnboarding = ctx.get("user_id").toString();
		String assigned_e_mail = "";

		if(additionalFieldsForUserAssignment != null && !HtmlConstants.EMPTY.equals(additionalFieldsForUserAssignment)){
			ProducerOneUtils.convertFieldNListStringToXMLWithCustomRootElement((Context)ctx,additionalFieldsForUserAssignment , "Root", "Root1", "inputXml");

		}

		ctx.put("aar_requestid", ctx.get("aar_requestid") != null && !HtmlConstants.EMPTY.equals(ctx.get("aar_requestid")) ? Integer.parseInt(ctx.get("aar_requestid").toString()): null);
		new SetParametersForStoredProcedures().setParametersInContext(ctx, "aar_requestid,agency_id,roles,user_id,inputXml");
		Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("onboarding_person.getUsertoAssignOnboardingApplication_p",ctx);

		if(obj!= null){
			Map map = (Map)obj;
			if(map != null && map.get("assignedUserForOnboarding") != null && !HtmlConstants.EMPTY.equals(map.get("assignedUserForOnboarding"))){
				assignedUserForOnboarding = map.get("assignedUserForOnboarding").toString();
				assigned_e_mail = map.get("assigned_e_mail") != null && !HtmlConstants.EMPTY.equals(map.get("assigned_e_mail")) ? map.get("assigned_e_mail").toString() : null;
			}
		}
		ctx.put("assignedUserForOnboarding",assignedUserForOnboarding);
		ctx.put("assigned_e_mail", assigned_e_mail);
		return null;
	}
	public static void setQuestionResponsesInSession(Context ctx,String listName,String fieldName) throws Exception {
		if(ctx.get(listName) != null && ctx.get(listName) instanceof List){
			List list = (ArrayList)ctx.get(listName);
			List tempList = new ArrayList();
			for(int i = 0;i<list.size();i++){
				Map row = (HashMap) list.get(i);
				if(!fieldName.contains("|")){
					 HttpServletRequest req = (HttpServletRequest)ctx.get("DocumentRequest");
			          HttpSession sess = req.getSession();
			          sess.setAttribute(fieldName+"_"+i, ctx.get(fieldName+"_"+i));
			          if(ctx.get("isComingFromBack") != null && !"".equals(ctx.get("isComingFromBack"))){
			        	  /*ctx.put(fieldName+"_"+i, sess.getAttribute(fieldName+"_"+i));*/
			        	  row.put(fieldName, sess.getAttribute(fieldName+"_"+i));
			        	  tempList.add(row);
			          }
				}else{
					//split and execute
				}
			}
			if((ctx.get("isComingFromBack") != null && "Y".equals(ctx.get("isComingFromBack"))) ){
				ctx.put(listName, tempList);
			}
		}
	}
	public static String checkDuplicateAdditionalEmailAddress(Context ctx) throws Exception {
		List list = null;
		if (ctx.get("additionalEmailAddress_list_listfreeform_1") != null && !HtmlConstants.EMPTY.equals(ctx.get("additionalEmailAddress_list_listfreeform_1"))) {
			list = (List) ctx.get("additionalEmailAddress_list_listfreeform_1");
		}
		if (list == null || HtmlConstants.EMPTY.equals(list) || list.size() == 0)
			return null;

		if (list != null && !HtmlConstants.EMPTY.equals(list)) {
			int listEmailCategoryVal = 0;
			int ctxEmailCategoryVal = 0;
			for (int i = 0; i < list.size(); i++) {
				Map listMap = (Map) list.get(i);
				if (listMap.get("email_category") != null && !HtmlConstants.EMPTY.equals(listMap.get("email_category"))){
					if (ctx.get("email_category") != null && !HtmlConstants.EMPTY.equals(ctx.get("email_category"))){
						listEmailCategoryVal = Integer.parseInt(listMap.get("email_category").toString());
						ctxEmailCategoryVal = Integer.parseInt(ctx.get("email_category").toString());
						if (listEmailCategoryVal == ctxEmailCategoryVal ) {
							DataUtils.populateError((Context)ctx, "additionalEmailAddressError", "additionalEmailAddressDuplicateErrorKey");
							return null;
						}
					}
				}
			}

		}
		return null;
	}

	public static void checkAgencyFunctionsFilterDateValidation(Context ctx) throws Exception{
        try {

          String filterDate=null;

          filterDate=(ctx.get("suspension_date") != null && !HtmlConstants.EMPTY.equals(ctx.get("suspension_date"))) ? ctx.get("suspension_date").toString() : null;

            if(filterDate==null){

                if(ctx.get("selectFunctionType_code") != null && !HtmlConstants.EMPTY.equals(ctx.get("selectFunctionType_code").toString()) && ctx.get("selectFunctionType_code").toString().equals("functionAS")){
                	DataUtils.populateError(ctx, "suspension_date","functionsASDateEmptyErrorKey");

                }

                if(ctx.get("selectFunctionType_code") != null && !HtmlConstants.EMPTY.equals(ctx.get("selectFunctionType_code").toString()) && ctx.get("selectFunctionType_code").toString().equals("functionRS")){
                	DataUtils.populateError(ctx, "suspension_date","functionsRSDateEmptyErrorKey");

                }

                if(ctx.get("selectFunctionType_code") != null && !HtmlConstants.EMPTY.equals(ctx.get("selectFunctionType_code").toString()) && ctx.get("selectFunctionType_code").toString().equals("functionCAC")){
                	DataUtils.populateError(ctx, "suspension_date","functionsCACDateEmptyErrorKey");

                }

                if(ctx.get("selectFunctionType_code") != null && !HtmlConstants.EMPTY.equals(ctx.get("selectFunctionType_code").toString()) && ctx.get("selectFunctionType_code").toString().equals("functionCL")){
                	DataUtils.populateError(ctx, "suspension_date","functionsCLDateEmptyErrorKey");

                }

                if(ctx.get("selectFunctionType_code") != null && !HtmlConstants.EMPTY.equals(ctx.get("selectFunctionType_code").toString()) && ctx.get("selectFunctionType_code").toString().equals("functionDC")){
                	DataUtils.populateError(ctx, "suspension_date","functionsDCDateEmptyErrorKey");

                }

            }


            if (ctx.get("suspension_date") != null && !HtmlConstants.EMPTY.equals(ctx.get("suspension_date").toString())) {
                if (!DateUtils.parseDate(ctx, "suspension_date")) {
                    DataUtils.populateError(ctx, "suspension_date",
                            "InvalidDateErrorKey");
                }
            }


            if(ctx.get("selectFunctionType_code") != null && !HtmlConstants.EMPTY.equals(ctx.get("selectFunctionType_code").toString())
            		&& !ctx.get("selectFunctionType_code").toString().equals("functionRS")){
            	RuleImpl rImpl = (RuleImpl)RulesResources.getInstance(ctx).findRule("AgencyFunctionsRule.isDateBeforeCurrentDate");
                rImpl.evaluate(ctx, null);
            }

            if(ctx.get("selectFunctionType_code") != null && !HtmlConstants.EMPTY.equals(ctx.get("selectFunctionType_code").toString())
            		&& ctx.get("selectFunctionType_code").toString().equals("functionRS")
            		&& ctx.get("toBeProcessed_agency_id") != null){

            	new SetParametersForStoredProcedures().setParametersInContext(ctx, "toBeProcessed_agency_id");
    			Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("agency_function.getSuspensionDataOfAgencyId_p", ctx);

    			if(obj != null && obj instanceof Map){
                    Map map = (Map)obj;
                    if(map != null && map.get("suspension_date_agency") != null){
                    	if(DateUtils.isDateBefore(filterDate, map.get("suspension_date_agency"))){
       					 DataUtils.populateError((Context)ctx, "pageError", "ReactivationDateCannotBeGreaterThanSuspensionDateErrorKey");
                    	}
                    }

                }
            }


      } catch (Exception e) {
      	logger.error("Unable to execute checkAgencyFunctionsFilterDateValidation method due to error : " + DataUtils.getExceptionStackTrace(e));
      }


  }

	//Method created to generate attachment content for Agent in Contracts
	public static void generateAttachmentContentForAgent(Context ctx){
		try{
			new SetParametersForStoredProcedures().setParametersInContext(ctx, "contract_id,agentEmailAddressType");
			ctx.put("RequestFor", "Primary");
			List documentList = SqlResources.getSqlMapProcessor(ctx).select("agency_contracts.getGeneratedContractListAttachedWhileSendtoAgent_p",ctx);

			if(documentList != null && documentList.size() >= 2){
				List primaryDocumentsList = (List)documentList.get(0);
				List secondaryDocumentsList = (List)documentList.get(1);

				List documentAttachmentList = new ArrayList();
				String primaryMailTo = null;
				String primaryPersonName = null;
				String secondaryMailTo = null;
				String secondaryPersonName = null;

				if(primaryDocumentsList != null && primaryDocumentsList.size() > 0){
					for(int i=0; i<primaryDocumentsList.size(); i++){
						Map map = (Map)primaryDocumentsList.get(i);

						String file_name = (String)map.get("document_name");
						String document_id = map.get("object_id")+"";

						if(i == 0 && map.get("e_mail") != null){
							primaryMailTo = map.get("e_mail").toString();
							primaryPersonName = map.get("person_name") != null ? map.get("person_name").toString() : HtmlConstants.EMPTY;
						}

						ctx.put("file_name", file_name);
						ctx.put("document_id", document_id);

						byte[] rb = null;

			            String isDMSIntegrationdone = SystemProperties.getInstance().getString("dms.integrationdone");
			            if(isDMSIntegrationdone != null && isDMSIntegrationdone.equals("Y")){
			                //going to get DMS provider
			                String dmsProvider = SystemProperties.getInstance().getString("dms.provider");
			                if(dmsProvider != null){
			                    if(dmsProvider.equalsIgnoreCase("wss3")){
			                        //downloading file from sharepoint
			                        rb = new DocumentUploadBO().getDocumentFromSharePoint(ctx);

			                        if(rb == null){
			                        	if(logger.isDebugEnabled(ctx))
			                        		logger.debug(ctx, "No file contents read from sharepoint");
			                            DataUtils.populateError((Context)ctx, "documentName", "downloadDocErrorKey");
			                            DataUtils.populateError((Context)ctx, "pageError", "downloadDocErrorKey");
			                            continue;
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
			                        	if(logger.isDebugEnabled(ctx))
			                        		logger.debug(ctx, "No file contents read from DMS");
			                            DataUtils.populateError((Context)ctx, "documentName", "downloadDocErrorKey");
			                            DataUtils.populateError((Context)ctx, "pageError", "downloadDocErrorKey");
			                           continue;
			                        }

			                        map.put("contents", rb);
			                        map.put("content", rb);
			                        map.put("filename", file_name);
			                        map.put("filetype", new DataUtils().getMimeType(file_name));
			                    }
			                }
			            }else{
			            	if(logger.isDebugEnabled(ctx))
			            		logger.debug(ctx, "DMS integration not done");
			                DataUtils.populateError((Context)ctx, "documentName", "downloadDocErrorKey");
			                DataUtils.populateError((Context)ctx, "pageError", "downloadDocErrorKey");
			                return;
			            }

			            documentAttachmentList.add(map);
					}

					ctx.put("primaryMailTo", primaryMailTo);
					ctx.put("primaryPersonName", primaryPersonName);
					ctx.put("primaryAttachmentContent", documentAttachmentList);
				}

				documentAttachmentList = new ArrayList();

				if(secondaryDocumentsList != null && secondaryDocumentsList.size() > 0){
					for(int i=0; i<secondaryDocumentsList.size(); i++){
						Map map = (Map)secondaryDocumentsList.get(i);

						String file_name = (String)map.get("document_name");
						String document_id = map.get("object_id")+"";

						if(i == 0 && map.get("e_mail") != null){
							secondaryMailTo = map.get("e_mail").toString();
							secondaryPersonName = map.get("person_name") != null ? map.get("person_name").toString() : HtmlConstants.EMPTY;
						}

						ctx.put("file_name", file_name);
						ctx.put("document_id", document_id);

						byte[] rb = null;

			            String isDMSIntegrationdone = SystemProperties.getInstance().getString("dms.integrationdone");
			            if(isDMSIntegrationdone != null && isDMSIntegrationdone.equals("Y")){
			                //going to get DMS provider
			                String dmsProvider = SystemProperties.getInstance().getString("dms.provider");
			                if(dmsProvider != null){
			                    if(dmsProvider.equalsIgnoreCase("wss3")){
			                        //downloading file from sharepoint
			                        rb = new DocumentUploadBO().getDocumentFromSharePoint(ctx);

			                        if(rb == null){
			                        	if(logger.isDebugEnabled(ctx))
			                        		logger.debug(ctx, "No file contents read from sharepoint");
			                            DataUtils.populateError((Context)ctx, "documentName", "downloadDocErrorKey");
			                            DataUtils.populateError((Context)ctx, "pageError", "downloadDocErrorKey");
			                            continue;
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
			                        	if(logger.isDebugEnabled(ctx))
			                        		logger.debug(ctx, "No file contents read from DMS");
			                            DataUtils.populateError((Context)ctx, "documentName", "downloadDocErrorKey");
			                            DataUtils.populateError((Context)ctx, "pageError", "downloadDocErrorKey");
			                           continue;
			                        }

			                        map.put("contents", rb);
			                        map.put("content", rb);
			                        map.put("filename", file_name);
			                    }
			                }
			            }else{
			            	if(logger.isDebugEnabled(ctx))
			            		logger.debug(ctx, "DMS integration not done");
			                DataUtils.populateError((Context)ctx, "documentName", "downloadDocErrorKey");
			                DataUtils.populateError((Context)ctx, "pageError", "downloadDocErrorKey");
			                return;
			            }

			            documentAttachmentList.add(map);
					}

					ctx.put("secondaryMailTo", secondaryMailTo);
					ctx.put("secondaryPersonName", secondaryPersonName);
					ctx.put("secondaryAttachmentContent", documentAttachmentList);
				}
			}
		}catch(Exception e){
			logger.error(ctx, "Unable to generate attachment content for agent due to error : " + DataUtils.getExceptionStackTrace(e));
		}
	}

	public static Object checkAllFieldsForAwardNRecognitionsEmpty(Context ctx) throws Exception{
		if(( (ctx.get("awardsNRecognitionsTab_producer_num_id_security")!=null && ctx.get("awardsNRecognitionsTab_producer_num_id_security").equals("N")) || (ctx.get("producer_num_id") == null || HtmlConstants.EMPTY.equals(ctx.get("producer_num_id").toString())))
	            && ((ctx.get("awardsNRecognitionsTab_conference_type_id_security") !=null && ctx.get("awardsNRecognitionsTab_conference_type_id_security").equals("N")) || (ctx.get("conference_type_id") == null || HtmlConstants.EMPTY.equals(ctx.get("conference_type_id").toString())))
	            && ((ctx.get("awardsNRecognitionsTab_award_name_recognition_id_security") !=null && ctx.get("awardsNRecognitionsTab_award_name_recognition_id_security").equals("N")) || (ctx.get("award_name_recognition_id") == null || HtmlConstants.EMPTY.equals(ctx.get("award_name_recognition_id").toString())))
	            && ((ctx.get("awardsNRecognitionsTab_award_calendar_year_security") !=null && ctx.get("awardsNRecognitionsTab_award_calendar_year_security").equals("N")) ||(ctx.get("award_calendar_year") == null || HtmlConstants.EMPTY.equals(ctx.get("award_calendar_year").toString())))
	            && ((ctx.get("awardsNRecognitionsTab_quarter_of_the_year_security") !=null && ctx.get("awardsNRecognitionsTab_quarter_of_the_year_security").equals("N")) || (ctx.get("quarter_of_the_year") == null || HtmlConstants.EMPTY.equals(ctx.get("quarter_of_the_year").toString())))
	            && ((ctx.get("awardsNRecognitionsTab_trip_location_security") !=null && ctx.get("awardsNRecognitionsTab_trip_location_security").equals("N")) || (ctx.get("trip_location") == null || HtmlConstants.EMPTY.equals(ctx.get("trip_location").toString())))
	            && ((ctx.get("awardsNRecognitionsTab_rank_security") !=null && ctx.get("awardsNRecognitionsTab_rank_security").equals("N")) || (ctx.get("rank") == null || HtmlConstants.EMPTY.equals(ctx.get("rank").toString())))){
	            return "N";
	         }else{
	        	return "Y";
	         }
	}

	public static void validateNBRBExpirationDates(Context ctx) throws Exception{
		if("CPV".equals(ctx.get("object_type_v"))){
			if((ctx.get("nb_expiration_date") != null && !"".equals(ctx.get("nb_expiration_date").toString())) && (ctx.get("rb_expiration_date") != null && !"".equals(ctx.get("rb_expiration_date").toString()))){
				if(DateUtils.isDateBefore(ctx.get("rb_expiration_date"), ctx.get("nb_expiration_date"))){
					 DataUtils.populateError((Context)ctx, "pageError", "ExpirationNewReNewDateErrorKey");
				}
			}
		}
	}

	//Method create to generate agency termination letter
	public static void generateAgencyTerminationLetter(Context ctx){
		try{
			if(ctx.get("selected_producer_number_ids") == null || ctx.get("selected_producer_number_ids").toString().equals(HtmlConstants.EMPTY))
				return;

			List docsList = new ArrayList();
			if(ctx.get("uploadPopup_list_1") != null && ctx.get("uploadPopup_list_1") instanceof List)
				docsList = (List)ctx.get("uploadPopup_list_1");

			StringTokenizer tokens = new StringTokenizer(ctx.get("selected_producer_number_ids").toString(), ",");
			while(tokens.hasMoreTokens()){
				String token = tokens.nextToken();

				Context newCtx = new Context();
				newCtx.setProject(ctx.getProject());
				newCtx.putAll(ctx);

				newCtx.put("contract_Template_Type_code", token);
				new SetParametersForStoredProcedures().setParametersInContext(newCtx, "contract_Template_Type_code");
				Map map = (Map)SqlResources.getSqlMapProcessor(newCtx).findByKey("SqlStmts.sqlStatementsviewgetContractTemplateDataByCode", newCtx);
				if(map == null || map.get("contract_Template_Type_id") == null)
					continue;

				newCtx.put("object_id", map.get("contract_Template_Type_id"));
				newCtx.put("document_category", "41");

				XMLUtils.generateRequestXml(newCtx, "object_id,document_category", "inputXml");
  				new SetParametersForStoredProcedures().setParametersInContext(newCtx, "inputXml");
				map = (Map)SqlResources.getSqlMapProcessor(newCtx).findByKey("agency_document_attachments.getAttachedDocumentByObjectId_p", newCtx);
				newCtx.put("inputXml", null);

				if(map == null || map.get("document_id") == null)
					continue;

				newCtx.put("document_id", map.get("document_id"));
				newCtx.put("document_name", map.get("document_name"));
				newCtx.put("file_name", map.get("document_name"));

				Context localCtx = new Context();
				localCtx.setProject(newCtx.getProject());
				localCtx.putAll(newCtx);

				byte[] rb = null;
				byte[] pdfRB = null;
				try{
					rb = new DataUtils().getDocumentFromDMS(newCtx);
					pdfRB = (new XML2PDF().generatePDFFromTemplateContent(new String(rb), newCtx, localCtx, (HttpServletRequest)ctx.get(HtmlConstants.DOCUMENTREQUEST),
							(HttpServletResponse)ctx.get(HtmlConstants.DOCUMENTRESPONSE), (ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER), (ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT))).toByteArray();
				}catch(Exception e1){
					logger.error(newCtx, "Unable to get document "+newCtx.get("file_name")+" from DMS due to error : " + DataUtils.getExceptionStackTrace(e1));
					continue;
				}

				Map fileMap = new HashMap();

				String docName = map.get("document_name").toString();

				String fileName = docName.substring(docName.lastIndexOf("\\")+1, docName.lastIndexOf("."));
				fileName = new ProducerOneUtils().excludeSpecialCharacters(fileName);
				if(fileName != null && fileName.length() > 44){
					fileName = fileName.substring(0, 43);
				}

				String extn = docName.substring(docName.lastIndexOf(".")+1, docName.length());
				extn = "pdf";

				SimpleDateFormat sdf = new SimpleDateFormat("_yyyy-MM-dd_hh-mm");
				docName = fileName + sdf.format(new Date()) + "." + extn;

				fileMap.put("rownum", docsList.size()+1);
				fileMap.put("isNewFile", "Y");
				fileMap.put("document_name", docName);
				fileMap.put("contents", pdfRB); //will be used in DocumentUploadBO
				fileMap.put("content", pdfRB); //will be used in Email attachment
				fileMap.put("document_category", 39);
				fileMap.put("last_updated_ts", new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a").format(new Date()));

				docsList.add(fileMap);

				//getting list to edit pdf only
				if(ctx.get(HtmlConstants.IS_SHOW_EDIT_PDF_TEMPLATE) != null && ctx.get(HtmlConstants.IS_SHOW_EDIT_PDF_TEMPLATE).toString().equals("Y")){
					String html = null;
					String templateType = null;

					if(map.get("document_name").toString().endsWith(".html"))
						templateType = "html";
					else
						templateType = "xsl";

					//html = new DataUtils().generateHtmlFromPdfTemplate(localCtx, new String(rb), templateType);
					html = new Html2PdfGenerator().generateHtmlFromPdfTemplate(localCtx, new String(rb), templateType);

					pdfRB = html.getBytes();
					fileMap.put("htmlcontent", pdfRB);
					fileMap.put("htmlcontents", pdfRB);

					if(docsList.size() == 1){
						ctx.put("document_name", docName);
						ctx.put("document_name", templateType);
					}
				}
			}

			ctx.put("uploadPopup_list_1", docsList);
		}catch(Exception e){
			logger.error(ctx, "Unable to generate agency termination letter due to error : " + DataUtils.getExceptionStackTrace(e));
		}
	}

	//Method created to get data for Agency termination
	public static void getDataForAgencyTermination(Context ctx){
		try{
			ctx.put("object_id", ctx.get("termination_log_id"));
			ctx.put("object_type_v", "Agency Termination");
			ctx.put("document_category", "39");

			Map map = (Map)SqlResources.getSqlMapProcessor(ctx).findByKey("person.getPersonDataById_p", ctx);
			if(map != null && map.get("xml") != null){
				new DataUtils().populateClobValue(map);
				new XMLUtils().populateContextFromXML(ctx, map.get("xml").toString());
				ctx.put("mailTo", ctx.get("e_mail"));
			}

			new SetParametersForStoredProcedures().setParametersInContext(ctx, "object_id,object_type_v,document_category");
			List documentList = SqlResources.getSqlMapProcessor(ctx).select("agency_document_attachments.getAttachedDocumentsList_p", ctx);

			if(documentList != null && documentList.size() > 0){
				List attachmentList = new ArrayList();

				for(int i=0; i<documentList.size(); i++){
					map = (Map)documentList.get(i);

					String file_name = (String)map.get("document_name");
					String document_id = map.get("object_id")+"";

					ctx.put("file_name", file_name);
					ctx.put("document_id", document_id);

					byte[] rb = null;

		            String isDMSIntegrationdone = SystemProperties.getInstance().getString("dms.integrationdone");
		            if(isDMSIntegrationdone != null && isDMSIntegrationdone.equals("Y")){
		                //going to get DMS provider
		                String dmsProvider = SystemProperties.getInstance().getString("dms.provider");
		                if(dmsProvider != null){
		                    if(dmsProvider.equalsIgnoreCase("wss3")){
		                        //downloading file from sharepoint
		                        rb = new DocumentUploadBO().getDocumentFromSharePoint(ctx);

		                        if(rb == null){
		                        	if(logger.isDebugEnabled(ctx))
		                        		logger.debug(ctx, "No file contents read from sharepoint");
		                            DataUtils.populateError((Context)ctx, "documentName", "downloadDocErrorKey");
		                            DataUtils.populateError((Context)ctx, "pageError", "downloadDocErrorKey");
		                            continue;
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
		                        	if(logger.isDebugEnabled(ctx))
		                        		logger.debug(ctx, "No file contents read from DMS");
		                            DataUtils.populateError((Context)ctx, "documentName", "downloadDocErrorKey");
		                            DataUtils.populateError((Context)ctx, "pageError", "downloadDocErrorKey");
		                           continue;
		                        }

		                        map.put("content", rb);
		                        map.put("contents", rb);
		                        map.put("filename", file_name);
		                        map.put("filetype", new DataUtils().getMimeType(file_name));
		                    }
		                }
		            }else{
		            	if(logger.isDebugEnabled(ctx))
		            		logger.debug(ctx, "DMS integration not done");
		                DataUtils.populateError((Context)ctx, "documentName", "downloadDocErrorKey");
		                DataUtils.populateError((Context)ctx, "pageError", "downloadDocErrorKey");
		                return;
		            }

		            attachmentList.add(map);
				}

				ctx.put("attachmentContent", attachmentList);
			}
		}catch(Exception e){
			logger.error(ctx, "Unable to get agency termination data due to error : " + DataUtils.getExceptionStackTrace(e));
		}
	}
	//Check the size of list
	public static void validateUploadPopupListSize(Context ctx) throws Exception{
		if(ctx.get("uploadPopup_list_1") != null && ctx.get("uploadPopup_list_1") instanceof List){
			List uploadList= (List)ctx.get("uploadPopup_list_1");
			if(uploadList.size()==0){
				 DataUtils.populateError((Context)ctx, "selectedTemplateType", "UploadListEmptyKey");
			}
		}
	}

	public static Object validateDocumentUploadDataBeforeUpload(Context ctx) throws Exception {
    	try{
    		boolean addDataFromGridToSessionList=true;
    		boolean isErrorFound = false;
    		String document_name = ctx.get("document_name") != null ? ctx.get("document_name").toString() : null;
    		String document_type_id = ctx.get("document_type_id") != null ? ctx.get("document_type_id").toString() : null;
    		String file = null;
    		String file_name = null;

    		HttpServletRequest req = (HttpServletRequest) ctx.get("DocumentRequest");
			Map docMap = new HashMap();
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List fileItems = ctx.get("fileItems") != null ? (List) ctx.get("fileItems") : null;
			if(fileItems != null && fileItems.size() > 0) {
				for (int i=0; i<fileItems.size(); i++) {
					FileItem item = (FileItem) fileItems.get(i);
					if (!item.isFormField()) {
						if(item.getName()!=null && !HtmlConstants.EMPTY.equals(item.getName().toString()) && item.get() != null && item.get().length >0){
							file = "File Found";
							//return null;
							file_name = item.getName();
						}
						if(item.getName()!=null && !HtmlConstants.EMPTY.equals(item.getName().toString()) && item.get() != null && item.get().length <=0){
							DataUtils.populateError((Context) ctx, "file", "fileSizeErrorKey");
							return null;
						}

						if(!new DocumentUploadBO().isValidFileToBeLoaded(ctx, item.getName()) && !ctx.get(Constants.INET_PAGE).toString().equals("addIDPDetails")){
    						String msg = "documentInvalidExtensionErrorKey";
    						MessageImpl msgImpl = MessageResources.getInstance(ctx).getMessage(msg);
    						if(msgImpl != null){
    							msg = MessageResources.getInstance(ctx).getMessage("documentInvalidExtensionErrorKey").getMessage() + " " +
    								((ctx.get("validFileExtensions") != null && !ctx.get("validFileExtensions").toString().equals(HtmlConstants.EMPTY) ? ctx.get("validFileExtensions").toString() : HtmlConstants.EMPTY));
    						}
    						isErrorFound=true;
    						DataUtils.populateError((Context)ctx, "documentName", msg);
    						return null;
    					}

						if(ctx.get("isDocumentUploadNew") != null && ctx.get("isDocumentUploadNew").toString().equals("Y")){
							//do nothing
						}else
							break;
					}
				}
			}



    		if(StringUtils.isBlank(document_name)&&StringUtils.isBlank(document_type_id)&&StringUtils.isBlank(file)){
    			addDataFromGridToSessionList=false;

    		}else {
	    		//if(!StringUtils.isBlank(document_name) || !StringUtils.isBlank(document_type_id) || !StringUtils.isBlank(file)){
    			if((!StringUtils.isBlank(document_name) || !StringUtils.isBlank(document_type_id))){
		    		if(StringUtils.isBlank(document_name) && !ctx.get(Constants.INET_PAGE).toString().equals("addIDPDetails")){
		    			DataUtils.populateError((Context) ctx, "document_name", "documentNameErrorKey");
		    			isErrorFound = true;
		    		}

		    		if(StringUtils.isBlank(document_type_id)){
		    			DataUtils.populateError((Context) ctx, "document_type_id", "documentTypeErrorKey");
		    			isErrorFound = true;
		    		}

		    		if(StringUtils.isBlank(file)){
		    			DataUtils.populateError((Context) ctx, "file", "documentErrorKey");
		    			isErrorFound = true;
		    		}

		    		if(isErrorFound)
		    			return null;

		    		List documentList = null;
		    		if(ctx.get("uploadDocument_list_1") != null && !HtmlConstants.EMPTY.equals("uploadDocument_list_1")){
		    			documentList = (List) ctx.get("uploadDocument_list_1");
		    		}

		    		if(documentList != null && documentList.size() > 0){
		    			for(int i=0; i<documentList.size(); i++){
		    				Map rowMap = (Map)documentList.get(i);

		    				//going to check for duplicacy for document_name
							if(rowMap!=null && rowMap.get("document_name")!=null && !HtmlConstants.EMPTY.equals(rowMap.get("document_name").toString())
									&& ctx.get("document_name")!=null && !HtmlConstants.EMPTY.equals(ctx.get("document_name").toString())  ){
								String documentName = rowMap.get("document_name").toString();
								String documentNameCtx = ctx.get("document_name").toString();
								if(documentName.equalsIgnoreCase(documentNameCtx)){
									DataUtils.populateError((Context) ctx, "documentName", "adminDuplicateError");
									break;
								}
							}

							//going to check for duplicacy for upload file name
							if(rowMap != null && rowMap.get("actual_file_name") != null && !HtmlConstants.EMPTY.equals(rowMap.get("actual_file_name").toString())
									&& file_name != null && !HtmlConstants.EMPTY.equals(file_name)){

								String documentName = rowMap.get("actual_file_name").toString();
								if(file_name.contains("\\"))
									file_name = file_name.substring(file_name.lastIndexOf("\\")+1, file_name.length());

								String documentNameCtx = file_name;
								if(documentName.equalsIgnoreCase(documentNameCtx)){
									DataUtils.populateError((Context) ctx, "documentName", "adminDuplicateError");
									break;
								}
							}
						}
		    		}
	    		}
    		}
    		return null;
    	}catch(Exception ex){
    		logger.error(ex.getMessage());
    		return null;
    	}
	}

	//Method created to filter send invitation input field xml
	public static void filterSendInvitationInputFieldXML(Context ctx, String xml){
		try{
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(new StringReader(xml));
			Element root1 = doc.getRootElement().getChild("Root1");
			xml = new XMLOutputter(Format.getPrettyFormat()).outputString(root1);
			ctx.put("step1InputFieldXml", xml);
		}catch(Exception e){
			logger.error(ctx, "Unable to filter send invitation input field xml due to error : " + DataUtils.getExceptionStackTrace(e));
		}
	}

	//Method created to get roles dropdown from database
	public static List getRoleDropDownFromDB(Context ctx, List<String> list){
		List<Map<String,String>> rolesDisplayList = null;
		String rolesString = Arrays.toString(list.toArray());
		if(rolesString.length() > 2)
			rolesString = rolesString.substring(1,rolesString.length()-1);

		try {
			ctx.put("rolesString",rolesString);
			rolesDisplayList = SqlResources.getSqlMapProcessor(ctx).select("agency_master.getRolesDropdownForSelectedRoles_p",ctx);
		} catch (Exception e) {
			logger.error("Unable to get Roles List due to error : "+ e.getMessage());
		}

		return rolesDisplayList;
	}

	public static void validateRoutingNumberWS(Context ctx) throws Exception{
		 String wsURL = SystemProperties.getInstance().getProperty(
					"integration.ws.deprecated.baseURL"); // "http://localhost:8080/intws/api/";
			String username = SystemProperties.getInstance().getProperty(
					"integration.ws.deprecated.username"); // "pone";
			String password = SystemProperties.getInstance().getProperty(
					"integration.ws.deprecated.password");// "Secure$1";
			if(logger.isDebugEnabled(ctx))
				logger.debug(ctx, "Validate Routing Number " + wsURL + " >> " + username + " >> " + password);
			if(ctx.get("isBankRoutingNumberValidationRequired") != null && "Y".equals(ctx.get("isBankRoutingNumberValidationRequired").toString())){
			if(ctx.get("serviceName") != null && !HtmlConstants.EMPTY.equals(ctx.get("serviceName").toString())){
				if(ctx.get("bank_routing") != null && !"".equals(ctx.get("bank_routing").toString())){
					JSONObject respP =null;
					JSONObject routingNumberJson = new JSONObject();
					routingNumberJson.put("routingNumber", ctx.get("bank_routing").toString());
					IntegrationClient integrationClient = new IntegrationClient(wsURL,username, password);
					JSONObject validateRoutingNumberReq = new JSONObject();
					validateRoutingNumberReq.put("serviceName", ctx.get("serviceName").toString());
					validateRoutingNumberReq.put("clientRole", "PONE");
					JSONObject payload = new JSONObject();
					JSONObject requestParams = new JSONObject();
					requestParams.put(ProducerOneConstants.INTEGRATION_INPUT_JSON, routingNumberJson);
					payload.put("request", requestParams);
					validateRoutingNumberReq.put("payload", payload);
					if(logger.isDebugEnabled(ctx)) {
//						logger.debug(ctx, "Verify Routing Number : " + validateRoutingNumberReq.toString());
						String request = new WebservicecallImpl().parseJsonResponseForMaskedKeysInLog(ctx, validateRoutingNumberReq.toString(), null);
						logger.debug(ctx, "Verify Routing Number : " + request);
					}
					String validateRoutingNumberResponse = (String) integrationClient.sendAndReceive(validateRoutingNumberReq);
					if(logger.isDebugEnabled(ctx)) {
//						logger.debug(ctx, "Verify Routing Number : " + validateRoutingNumberResponse);
						String response = new WebservicecallImpl().parseJsonResponseForMaskedKeysInLog(ctx, validateRoutingNumberResponse, null);
						logger.debug(ctx, "Verify Routing Number : " + response);
					}
					if(validateRoutingNumberResponse != null && !"".equals(validateRoutingNumberResponse)){
						JSONObject respJson = new JSONObject(validateRoutingNumberResponse);
						if(respJson != null && "success".equalsIgnoreCase(respJson.getString("status"))){
							respP = respJson.getJSONObject("payload").getJSONObject("response");
							if(respP.getString("Status") != null){
								if("Success".equals(respP.getString("Status"))){
									String bankName= respJson.getJSONObject("payload").getJSONObject("response").getString("BankName");
									ctx.put("bank_name", bankName);
								}else if("Fail".equals(respP.getString("Status"))){
									DataUtils.populateError((Context) ctx, "bank_routing", "validRoutingNumberMsg");
								}
							}
						}else if("failed".equalsIgnoreCase(respP.getString("Status"))){
							DataUtils.populateError((Context) ctx, "bank_routing", "validRoutingNumberMsg");
							}
					}else{
						DataUtils.populateError((Context) ctx, "bank_routing", "validRoutingNumberMsg");
					}
				}
			}
			}

	    }
	public static void validateNPNChangedWithActiveAppointments(Context ctx) throws Exception{
		try {
			String NPN = ctx.get("npn") != null && !HtmlConstants.EMPTY.equals(ctx.get("npn")) ? ctx.get("npn").toString(): null;
			String agencyId =ctx.get("agency_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("agency_id")) ? ctx.get("agency_id").toString(): null;
			ctx.put("agency_npn", NPN);
			ctx.put("agencyId", agencyId);
			new SetParametersForStoredProcedures().setParametersInContext(ctx, "agency_npn,agency_id");
			HashMap map = (HashMap)SqlResources.getSqlMapProcessor(ctx).findByKey("agency_master.checkNPNChangedWithActiveAppointments_p", ctx);
			if(map != null && !map.isEmpty()){
				if(map.get("is_show_npn_changed_with_active_appointment") != null && !HtmlConstants.EMPTY.equals(map.get("is_show_npn_changed_with_active_appointment")) && "Y".equals(map.get("is_show_npn_changed_with_active_appointment"))){
					ctx.put("is_show_npn_changed_with_active_appointment", map.get("is_show_npn_changed_with_active_appointment").toString());
					//DataUtils.populateError((Context) ctx, "", "");
					ctx.put("ISSKIPWORKFLOW", "Y");
					ctx.put("ISSHOWREQUESTDATA", "npn");
				}else{
					ctx.put("is_show_npn_changed_with_active_appointment", "N");
				}
			}else{
				ctx.put("is_show_npn_changed_with_active_appointment", "N");
			}
		} catch (Exception e) {
			ctx.put("is_show_npn_changed_with_active_appointment", "N");
			logger.error("Unable to validate NPN Changed With Active Appointments due to error : "+ e.getMessage());
		}
	}
	public static void validateEmptyFieldsLexisNexis(Context ctx) throws Exception{
		final String stateId = "ln_state_id";
		final String lobId= "ln_lob_id";
		final String nodeId= "lexis_nexis_node_id";
		final String accountId= "lexis_nexis_account_id";
		if(ctx.get("entityGeneralDetails_lexis_nexis_security") != null && "Y".equals(ctx.get("entityGeneralDetails_lexis_nexis_security").toString())){
		if((ctx.get(stateId) != null && !HtmlConstants.EMPTY.equals(ctx.get(stateId).toString())) || (ctx.get(lobId) != null && !HtmlConstants.EMPTY.equals(ctx.get(lobId).toString()))|| (ctx.get(nodeId) != null && !HtmlConstants.EMPTY.equals(ctx.get(nodeId).toString())) || (ctx.get(accountId) != null && !HtmlConstants.EMPTY.equals(ctx.get(accountId).toString()))){
			ProducerOneUtility.checkDuplicateLexisNexisDetails(ctx);
			//do nothing
		/*	boolean flag = false;
			if((ctx.get(stateId) == null || HtmlConstants.EMPTY.equals(ctx.get(stateId).toString())) && (ctx.get(lobId) == null || HtmlConstants.EMPTY.equals(ctx.get(lobId).toString()))){
				if(ctx.get(nodeId).toString().trim().equals("") && (ctx.get("lexis_nexis_node_id_security") != null  && "Y".equals(ctx.get("lexis_nexis_node_id_security").toString()))){
					DataUtils.populateError(ctx, nodeId, "lexisNexisEmptyNodeIdError");
					flag = true;
				}
				if(ctx.get(accountId).toString().trim().equals("") && (ctx.get("lexis_nexis_state_security") != null  && "Y".equals(ctx.get("lexis_nexis_state_security").toString()))){
					DataUtils.populateError(ctx, accountId, "lexisNexisEmptyAccountIdError");
					flag = true;
				}
				if(flag){
					if(ctx.get("lexis_nexis_lob_security") != null  && "Y".equals(ctx.get("lexis_nexis_lob_security").toString()) && (ctx.get(lobId) == null || HtmlConstants.EMPTY.equals(ctx.get(lobId).toString()))){
						DataUtils.populateError(ctx, lobId, "lexisNexisEmptyLobError");
					}
					if(ctx.get("lexis_nexis_state_security") != null  && "Y".equals(ctx.get("lexis_nexis_state_security").toString()) && (ctx.get(stateId) == null || HtmlConstants.EMPTY.equals(ctx.get(stateId).toString()))){
						DataUtils.populateError(ctx, stateId, "lexisNexisEmptyStateError");
					}
				}
			}*/

		}
			/*else if(!"updateAgencyDetails".equals(ctx.get("inet_method").toString())){
				if(ctx.get("lexis_nexis_lob_security") != null  && "Y".equals(ctx.get("lexis_nexis_lob_security").toString()) && (ctx.get(lobId) == null || HtmlConstants.EMPTY.equals(ctx.get(lobId).toString()))){
						DataUtils.populateError(ctx, lobId, "lexisNexisEmptyLobError");
				}
				if(ctx.get("lexis_nexis_node_id_security") != null  && "Y".equals(ctx.get("lexis_nexis_node_id_security").toString()) && (ctx.get(nodeId) == null || HtmlConstants.EMPTY.equals(ctx.get(nodeId).toString()))){
						DataUtils.populateError(ctx, nodeId, "lexisNexisEmptyNodeIdError");
				}
				if(ctx.get("lexis_nexis_account_id_security") != null  && "Y".equals(ctx.get("lexis_nexis_account_id_security").toString()) && (ctx.get(accountId) == null || HtmlConstants.EMPTY.equals(ctx.get(accountId).toString()))){
						DataUtils.populateError(ctx, accountId, "lexisNexisEmptyAccountIdError");
				}
				if(ctx.get("lexis_nexis_state_security") != null  && "Y".equals(ctx.get("lexis_nexis_state_security").toString()) && (ctx.get(stateId) == null || HtmlConstants.EMPTY.equals(ctx.get(stateId).toString()))){
						DataUtils.populateError(ctx, stateId, "lexisNexisEmptyStateError");
				}
				}*/
		}

	}
	public static String checkDuplicateLexisNexisDetails(Context ctx) throws Exception {
		boolean dupsFlag = false;
		List list = null;
		if (ctx.get("lexisNexis_list_listfreeform_1") != null && !HtmlConstants.EMPTY.equals(ctx.get("lexisNexis_list_listfreeform_1"))) {
			list = (List) ctx.get("lexisNexis_list_listfreeform_1");
		}
		if (list == null || HtmlConstants.EMPTY.equals(list) || list.size() == 0)
			return null;

		if (list != null && !HtmlConstants.EMPTY.equals(list)) {
			for (int i = 0; i < list.size(); i++) {
				Map listMap = (Map) list.get(i);
				if (listMap.get("ln_state_id") != null && !HtmlConstants.EMPTY.equals(listMap.get("ln_state_id"))){
					if (ctx.get("ln_state_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("ln_state_id"))){
						int listStateVal = Integer.parseInt(listMap.get("ln_state_id").toString());
						int ctxStateVal = Integer.parseInt(ctx.get("ln_state_id").toString());
						if (listStateVal == ctxStateVal ) {
							dupsFlag = true;
						}else{
							dupsFlag = false;
						}
					}
				}
				if (listMap.get("ln_lob_id") != null && !HtmlConstants.EMPTY.equals(listMap.get("ln_lob_id"))){
					if (ctx.get("ln_lob_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("ln_lob_id"))){
						int listLobVal = Integer.parseInt(listMap.get("ln_lob_id").toString());
						int ctxLobVal = Integer.parseInt(ctx.get("ln_lob_id").toString());
						if (listLobVal == ctxLobVal && dupsFlag) {
							dupsFlag = true;
						}else{
							dupsFlag = false;
						}
					}
				}
				if (listMap.get("lexis_nexis_node_id") != null && !HtmlConstants.EMPTY.equals(listMap.get("lexis_nexis_node_id"))){
					if (ctx.get("lexis_nexis_node_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("lexis_nexis_node_id"))){
						String listNodeIdVal = listMap.get("lexis_nexis_node_id").toString();
						String ctxNodeIdVal = ctx.get("lexis_nexis_node_id").toString();
						if (listNodeIdVal.equals(ctxNodeIdVal) && dupsFlag) {
							dupsFlag = true;
						}else if(dupsFlag){
							dupsFlag = false;
						}
					}
				}
				if (listMap.get("lexis_nexis_account_id") != null && !HtmlConstants.EMPTY.equals(listMap.get("lexis_nexis_account_id"))){
					if (ctx.get("lexis_nexis_account_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("lexis_nexis_account_id"))){
						String listAccountIdVal = listMap.get("lexis_nexis_account_id").toString();
						String ctxAccountIdVal = ctx.get("lexis_nexis_account_id").toString();
						if (listAccountIdVal.equals(ctxAccountIdVal) && dupsFlag) {
							dupsFlag = true;
						}else if(dupsFlag){
							dupsFlag = false;
						}
					}
				}
				if(dupsFlag)
					break;
			}

		}
		if(dupsFlag){
			DataUtils.populateError((Context)ctx, "lexisNexisError", "lexisNexisDuplicateErrorKey");
		}
		return null;
	}

	public static Object iterateListForLocationHierarchy(Context ctx,String listName) throws Exception{
        try{
        	List list = null;
    		if (ctx.get(listName) != null && !HtmlConstants.EMPTY.equals(ctx.get(listName))) {
    			list = (List) ctx.get(listName);
    		}
    		if (list == null || HtmlConstants.EMPTY.equals(list) || list.size() == 0)
    			return null;

    		if (list != null && !HtmlConstants.EMPTY.equals(list)) {
    			for (int i = 0; i < list.size(); i++) {
    				Map listMap = (Map) list.get(i);
    				if (listMap.get("tax_id") != null && !HtmlConstants.EMPTY.equals(listMap.get("tax_id"))){
    					listMap.put("tax_id", ProducerOneUtils.decryptString(listMap.get("tax_id").toString()));
    				}
    			}
    		}

        }catch (Exception e) {
            logger.error("Unable to iterate iterateListForLocationHierarchy due to error : " + e.getMessage());
        }

        return null;
    }



	public static void checkMandatoryHierarchcyTypeForProducer(Context ctx) throws Exception{

		if(SecurityResources.getInstance(ctx).getAccessType("isShowProdNumSubAgencyCodeTrue", (Context) ctx) == SecurityResources.SHOW
				&& (ctx.get("subproducer_indicator") == null || HtmlConstants.EMPTY.equals(ctx.get("subproducer_indicator"))
				|| ctx.get("subproducer_indicator").toString().equals("false"))){
			return;
		}


		if(ctx.get(Constants.INET_ERRORS_LIST) == null && (ctx.get("inet_method").equals("update") || ctx.get("inet_method").equals("save"))
		           && ctx.get("mendatory_hierarchy_type_for_ProducerCode") != null
		           && !HtmlConstants.EMPTY.equals(ctx.get("mendatory_hierarchy_type_for_ProducerCode").toString())
		           && !"N".equals(ctx.get("mendatory_hierarchy_type_for_ProducerCode").toString())){

			   boolean hierarchyRequiredVal = false;
		       String errorDescription = null;
			   String mendatotyHierarchies = ctx.get("mendatory_hierarchy_type_for_ProducerCode").toString();
		       StringTokenizer tokens = new StringTokenizer(mendatotyHierarchies ,",");
		       String hierarchyCodes = null;
		       while (tokens.hasMoreTokens()){
		       	String hierarchyCode =  tokens.nextToken();

				 if(hierarchyCodes == null)
					 hierarchyCodes = hierarchyCode;
					else
						hierarchyCodes = hierarchyCodes+","+hierarchyCode;
		       	 }

		       if(hierarchyCodes == null || hierarchyCodes=="N")
		    	   return ;

			    if(ctx.get("masterCodes_list_01") == null || HtmlConstants.EMPTY.equals(ctx.get("masterCodes_list_01")) || ((List)ctx.get("masterCodes_list_01")).size() == 0){
					 if(hierarchyCodes != "N"){
						 hierarchyRequiredVal=true;
						 ctx.put("hierarchy_type_code", hierarchyCodes);
						 Map descriptionMap= (HashMap)SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.sqlStatementsviewgetAllHierarchyTypeDescription", ctx);
						 errorDescription = descriptionMap.get("hierarchy_type_description") != null && !HtmlConstants.EMPTY.equals(descriptionMap.get("hierarchy_type_description")) ? descriptionMap.get("hierarchy_type_description").toString(): "";
					}else{
						 hierarchyRequiredVal=false;
					}
			   }else{
				   if(ctx.get("masterCodes_list_01") != null && ((List)ctx.get("masterCodes_list_01")).size() > 0){
					   List hierrahcyList = (List)ctx.get("masterCodes_list_01");
					   String[] args = hierarchyCodes.split(",");
					   for(int i=0; i<args.length;i++){
						   if(!ProducerOneUtils.checkHierarchyCodeID(hierrahcyList,args[i])){
							   hierarchyRequiredVal = true;
							   ctx.put("hierarchy_type_code", hierarchyCodes);
							   Map descriptionMap= (HashMap)SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.sqlStatementsviewgetAllHierarchyTypeDescription", ctx);
							   errorDescription = descriptionMap.get("hierarchy_type_description") != null && !HtmlConstants.EMPTY.equals(descriptionMap.get("hierarchy_type_description")) ? descriptionMap.get("hierarchy_type_description").toString(): "";
							}
					   }
					}
				}
			    if(hierarchyRequiredVal){
			           DataUtils.populateError((Context)ctx, "hierarchyError", errorDescription+" "+MessageResources.getInstance(ctx).getMessage("mandatoryHierarchyNotAddedErrorKey").getMessage());
			    }
			 }
	}


	public static void removeMomListFieldFromContext(Context ctx,String listName,String commaSeperatedFieldsName) throws Exception{

		if(listName != null && !HtmlConstants.EMPTY.equals(listName)
				&& commaSeperatedFieldsName != null && !HtmlConstants.EMPTY.equals(commaSeperatedFieldsName)){

            StringTokenizer fieldsTokenizer = new StringTokenizer(commaSeperatedFieldsName,",");
             	if(ctx.get(listName) instanceof List){
             		List dataList = ctx.get(listName) != null ? (List)ctx.get(listName) : null;
                     if(dataList != null && dataList.size() > 0){
                         for(int i =0; i<dataList.size(); i++){
                        	 while (fieldsTokenizer.hasMoreElements()) {
								String fieldsName = (String) fieldsTokenizer.nextElement();
								ctx.put(fieldsName +"_"+i, null);
							}
                        }

                     }
             	}
         }

	}

	public static Object checkDateValidation(Context ctx,String fieldName,String messageFieldName) throws Exception{

		if(fieldName != null && !HtmlConstants.EMPTY.equals(fieldName)) {
			if(ctx.get(fieldName) != null && !HtmlConstants.EMPTY.equals(ctx.get(fieldName))) {
				try {
					new PropertyImpl().validateDateField(ctx, ctx.get(fieldName).toString(), fieldName, null);
				}catch (Exception e) {
					DataUtils.populateError(ctx, messageFieldName, "InvalidDateErrorKey");
				}

			}
		}
		return null;
	}

	 public static void putAgencyProfileResultSetDataIntoContext(Context ctx) throws Exception{

			if(ctx.get("agencyAssociationSummary_list_18") != null && ctx.get("agencyAssociationSummary_list_18") instanceof List){
				List oldList = (List)ctx.get("agencyAssociationSummary_list_18");
				for(int i=0; i<oldList.size(); i++){
					Map map = (Map)oldList.get(i);
					ctx.putAll(map);
				}
			}

			if(ctx.get("producerNumberData_list_0") != null && ctx.get("producerNumberData_list_0") instanceof List){
				List oldList = (List)ctx.get("producerNumberData_list_0");
				for(int i=0; i<oldList.size(); i++){
					Map map = (Map)oldList.get(i);
					ctx.putAll(map);
				}
			}
		}

	 public static void getBannerSearchStateFilterList(Context ctx) throws Exception{
		 String commaSeperatedAgencyId = "";
		 List stateFilterList=null;
		 if(ctx.get("advanceSearchEntityResult_list_1") != null && ctx.get("advanceSearchEntityResult_list_1") instanceof List){
			 List bannerSearchList = (List)ctx.get("advanceSearchEntityResult_list_1");
			 for(int i=0; i<bannerSearchList.size(); i++){
				 Map map = (Map)bannerSearchList.get(i);
				 commaSeperatedAgencyId =  commaSeperatedAgencyId + map.get("agency_id") +",";
			 }

			 if(null != commaSeperatedAgencyId && !HtmlConstants.EMPTY.equals(commaSeperatedAgencyId)){
				 ctx.put("commaSeperatedAgencyId", commaSeperatedAgencyId);
				 ProducerOneUtils.convertFieldNListStringToXMLWithCustomRootElement((Context)ctx,"commaSeperatedAgencyId" , "Root", "Root1", "commaSeperatedAgencyIdXml");
				 new SetParametersForStoredProcedures().setParametersInContext(ctx, "commaSeperatedAgencyIdXml");
				 stateFilterList = SqlResources.getSqlMapProcessor(ctx).select("agency_master.GetStatesListForAdvanceSearch_p", ctx);
				 ctx.put("stateFilter_list", stateFilterList);
			 }
		 }
	 }

	 public static boolean agencyFunctionActionWithoutBatch(Context ctx) throws Exception {
		 	String defaultAssignMasterAgencyCode="Y";
			String defaultClosedAgencyStatusCode ="10";
			String defaultTerminatedAgencyCodeStatusCode ="2";
			String isAgencyCodeClosureOnRenewalDate= "N";
		 	new SetParametersForStoredProcedures().setParametersInContext(ctx, "object_id");
			Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.sqlStatementsviewgetRequestsSSBasedOnId", ctx);
			if(obj != null && obj instanceof Map){
       Map map = (Map)obj;
       int request_ss_id  = Integer.parseInt(map.get("request_ss_id").toString());
				String operation_type  = map.get("operation_type").toString();
				String object_type  = map.get("object_type").toString();
				int agency_id = map.get("agency_id") != null && !HtmlConstants.EMPTY.equals(map.get("agency_id")) ? Integer.parseInt(map.get("agency_id").toString()): null;
				ctx.put("object_id",request_ss_id);
				ctx.put("operation_type",operation_type);
				ctx.put("objecttype",object_type);
				try{

					if(object_type != null && !HtmlConstants.EMPTY.equals(object_type) && ("functionCL".equals(object_type) || ("functionDC".equals(object_type)))){
							return false;
					}

					try{
						isAgencyCodeClosureOnRenewalDate= SystemProperties.getInstance().getString("is.agency.code.closure.on.renewal.date") != null ? SystemProperties.getInstance().getString("is.agency.code.closure.on.renewal.date").toString():"N";
						ctx.put("is_agency_code_closure_on_renewal_date", isAgencyCodeClosureOnRenewalDate);
					} catch (Exception e) {
						logger.debug("Default Assign Master Agency Code Property not found");
						ctx.put("is_agency_code_closure_on_renewal_date", isAgencyCodeClosureOnRenewalDate);
					}

					try {
						defaultAssignMasterAgencyCode = SystemProperties.getInstance().getString("DEFAULT.ASSIGN.MASTER.AGENCY.CODE") != null ? SystemProperties.getInstance().getString("DEFAULT.ASSIGN.MASTER.AGENCY.CODE").toString():"1";
						ctx.put("default_assign_master_agency_code", defaultAssignMasterAgencyCode);
					} catch (Exception e) {
						logger.debug("Default Assign Master Agency Code Property not found");
						ctx.put("default_assign_master_agency_code", defaultAssignMasterAgencyCode);
					}

					try {
						defaultClosedAgencyStatusCode = SystemProperties.getInstance().getString("DEFAULT.AGENCY.STATUS.CODE") != null ? SystemProperties.getInstance().getString("DEFAULT.AGENCY.STATUS.CODE").toString():"10";
						ctx.put("default_closed_agency_status_code", defaultClosedAgencyStatusCode);
					} catch (Exception e) {
						logger.debug("Default Agency Status Code Property not found");
						ctx.put("default_closed_agency_status_code", defaultClosedAgencyStatusCode);
					}

					try {
						defaultTerminatedAgencyCodeStatusCode = SystemProperties.getInstance().getString("DEFAULT.AGENCY.CODE.STATUS.CODE") != null ? SystemProperties.getInstance().getString("DEFAULT.AGENCY.CODE.STATUS.CODE").toString():"2";
						ctx.put("default_terminated_agency_code_status_code", defaultTerminatedAgencyCodeStatusCode);
					} catch (Exception e) {
						logger.debug("Default Agency Code Status Code Property not found");
						ctx.put("default_terminated_agency_code_status_code", defaultTerminatedAgencyCodeStatusCode);
					}

					SqlResources.getSqlMapProcessor(ctx).insert("requests_ss.updateApprovedRequestData_p",ctx);
					if(ctx.get("returnStatus")!= null && ctx.get("returnStatus").toString().contains("Success")){
						List list = null;
						ctx.put("request_ss_id",request_ss_id);
						if(ctx.get("objecttype").equals("functionCL") && "1491".equals(ctx.get("request_ss_id"))){
							System.out.println("Here");
						}
						if(ctx.get("objecttype") != null && !HtmlConstants.EMPTY.equals(ctx.get("objecttype"))
								&&	(ctx.get("objecttype").equals("functionAS") || ctx.get("objecttype").equals("functionRS")
										||
										ctx.get("objecttype").equals("functionCAC") || ctx.get("objecttype").equals("functionCL")
										//|| ctx.get("objecttype").equals("functionDC")
										)){
							int requestid = Integer.parseInt(ctx.get("request_ss_id").toString());
							//int requestid = 1448;
							String objectType =ctx.get("objecttype").toString();
							int documentCategory = 0;
							if(ctx.get("objecttype").equals("functionAS"))
								documentCategory =  47;
							else if(ctx.get("objecttype").equals("functionCL"))
								documentCategory =  49;
							else if(ctx.get("objecttype").equals("functionCAC"))
								documentCategory =  50;
							else
								documentCategory =  48;

							new SetParametersForStoredProcedures().setParametersInContext(ctx, "document_category,document_id");
							list =  getUploadedDocumentForRequests(ctx,requestid,documentCategory,objectType);
						}


						new SetParametersForStoredProcedures().setParametersInContext(ctx, "request_ss_id");
						Object obj1 = SqlResources.getSqlMapProcessor(ctx).findByKey("email_notifications.getEmailEventByRequestid_p",ctx);
						String emailEventName = null;
						if(obj1 != null){
							Map map1 = (Map)obj1;

							if(map1 != null && map1.get("email_event_name")!= null && !HtmlConstants.EMPTY.equals(map1.get("email_event_name"))){
								emailEventName = map1.get("email_event_name").toString();
							}
						}
						ctx.put("attachmentcontent", list);
						if(emailEventName != null && !HtmlConstants.EMPTY.equals(emailEventName)){
							EmailImpl emailImpl = new EmailImpl();
							emailImpl.setEventname(emailEventName);
							if(list != null && list.size() > 0){
								emailImpl.setAttachmentcontent("attachmentcontent");
							}
							emailImpl.setObjectid(ctx.get("request_ss_id").toString());
							emailImpl.setObjecttype("Request");
							emailImpl.sendMail(ctx);
						}
						return true;
					}
				}
				catch (Exception e){
					e.printStackTrace();
					logger.debug("Unable to process request for request_ss_id = "+ request_ss_id);
				}
   }
		    return false;
		}

	 private static List getUploadedDocumentForRequests(Context ctx,int object_id,int document_category,String object_type_v){
			List list = new ArrayList();
			try{
				if (object_id != 0 && document_category != 0)  {
					ctx.put("object_id",object_id);
					ctx.put("document_category",document_category);
					ctx.put("object_type_v",object_type_v);
					new SetParametersForStoredProcedures().setParametersInContext(ctx, "document_category,object_id");
					List documentList = (List)SqlResources.getSqlMapProcessor(ctx).select("agency_document_attachments.getAttachedDocumentsList_p",ctx);

					if(documentList.size() > 0){
						for (Object object : documentList) {
							if(object != null){
								Map map = (Map)object;
								if(map != null){
									String file_name = map.get("document_name").toString();
									String document_id = map.get("object_id").toString();
									Context newCtx = new Context();
									newCtx.putAll(ctx);
									newCtx.put("file_name", file_name);
									newCtx.put("document_id", document_id);

									String dmsUploadEnabled = SystemProperties.getInstance().getString("osi.isDmsEnabled");
			                        if(dmsUploadEnabled != null && dmsUploadEnabled.equals("Y"))
			                        	newCtx.put("dmsUploadEnabled", map.get("is_uploaded_on_dms") != null && map.get("is_uploaded_on_dms").toString().equals("Y") ? "Y" : "N"); //to download document from DMS
			                        else
			                        	newCtx.put("dmsUploadEnabled", "N"); //to download document from filesystem


			                        byte[] rb = getDocumentFromDMS(newCtx);
									map.put("content", rb);
									map.put("contents", rb);
									map.put("filename", file_name);

									list.add(map);
								}
							}

						}
					}
				}
			}catch(Exception e){
				logger.debug("Error While Fetching Data of Uploaded Documents : " + e.getMessage());
			}

			return list;
		}

	 public static byte[] getDocumentFromDMS(IContext ctx) throws Exception {
			DocumentInfo docInfo = new DocumentInfo();
			boolean uploadByDMS;
			if(ctx.get("document_category") != null && !HtmlConstants.EMPTY.equals(ctx.get("document_category").toString())
				&& ("41".equals(ctx.get("document_category").toString()) || "42".equals(ctx.get("document_category").toString()) ||
						"43".equals(ctx.get("document_category").toString()))){
				docInfo.setDocLibraryPath(SystemProperties.getInstance().getString("contract.template.dms.url"));
				uploadByDMS = false;
			}else{
				docInfo.setDocLibraryPath(SystemProperties.getInstance().getString("dms.store.url"));
				uploadByDMS = true;
			}

			docInfo.setFileName(ctx.get("file_name").toString());
			docInfo.setProvider(SystemProperties.getInstance().getString("dms.provider"));
			docInfo.setDocumentId(ctx.get("document_id").toString());

			if(ctx.get("dmsUploadEnabled") != null && ctx.get("dmsUploadEnabled").toString().equals("Y")){
				logger.debug("Getting document from DMS");
				docInfo.setSourceFileSystem(false);
			}else {
				logger.debug("Getting document from FileSystem");
				docInfo.setSourceFileSystem(true);
			}


			if (uploadByDMS) {
				DocumentManagementService service = new DocumentManagementService();
				docInfo = service.downloadDocument(docInfo, ctx.getProject());
			} else {
				byte byteArray[];
				try {
					byteArray = IOUtils.readFileInBinary(docInfo.getDocLibraryPath() + "/" + docInfo.getFileName());
					docInfo.setFileContent(byteArray);
				} catch (Exception e) {
					logger.error("Exception " + e.toString());
					throw new Exception(e.toString());
				}
			}


			String content = new String(docInfo.getFileContent());

			if(content.startsWith("?")){
				String start = content.substring(3, content.length());
				content = start;
				return content.getBytes();
			}

			return docInfo.getFileContent();
		}
	 
	 
	 public static Object convertIVANSDownloadTypeDataIntoXML(Context ctx) throws Exception{
		 	List downloadTypeList= null;
		 	if(ctx.get("downloadType_list_mom_1") != null && !HtmlConstants.EMPTY.equals("downloadType_list_mom_1")){
		 		downloadTypeList = (List) ctx.get("downloadType_list_mom_1");
		 	}
			if(downloadTypeList == null || downloadTypeList.size()==0)
				return null;
			
			StringBuilder xmlString = null;
			if(downloadTypeList != null && downloadTypeList.size()>0){
				xmlString = new StringBuilder("<ivansdownloadtype>");
				for(int i=0;i<downloadTypeList.size();i++){
					Map map = (HashMap)downloadTypeList.get(i);
					if(map != null && !map.isEmpty()){
						if(ctx.get("isChecked_"+i) != null && !HtmlConstants.EMPTY.equals(ctx.get("isChecked_"+i)) && "Y".equals(ctx.get("isChecked_"+i))) {  
							xmlString.append("<downloadtype>");
							if(ctx.get("ivans_detail_id") != null && !HtmlConstants.EMPTY.equals("ivans_detail_id"))
								xmlString.append("<ivans_detail_id>").append(ctx.get("ivans_detail_id")).append("</ivans_detail_id>");
							else
								xmlString.append("<ivans_detail_id/>");
							
							if(map.get("ivans_download_type_id") != null && !HtmlConstants.EMPTY.equals(map.get("ivans_download_type_id")))
								xmlString.append("<ivans_download_type_id>").append(map.get("ivans_download_type_id")).append("</ivans_download_type_id>");
							else
								xmlString.append("<ivans_download_type_id/>");
							
							if(map.get("initial_load_required") != null && !HtmlConstants.EMPTY.equals(map.get("initial_load_required")))
								xmlString.append("<initial_load_required>").append(map.get("initial_load_required")).append("</initial_load_required>");
							else
								xmlString.append("<initial_load_required/>");
							
							if(map.get("transaction_type_required") != null && !HtmlConstants.EMPTY.equals(map.get("transaction_type_required")))
								xmlString.append("<transaction_type_required>").append(map.get("transaction_type_required")).append("</transaction_type_required>");
							else
								xmlString.append("<transaction_type_required/>");
							
							if(ctx.get("initial_load_id_"+i) != null && !HtmlConstants.EMPTY.equals(ctx.get("initial_load_id_"+i)))
								xmlString.append("<initial_load_id>").append(ctx.get("initial_load_id_"+i)).append("</initial_load_id>");
							else
								xmlString.append("<initial_load_id/>");
							
							xmlString.append("</downloadtype>");
						
						}
					}
				}
				xmlString.append("</ivansdownloadtype>");
				if(xmlString != null && !HtmlConstants.EMPTY.equals(xmlString)){ 
					ctx.put("downloadTypeXml", xmlString);
				}else{
					ctx.put("downloadTypeXml", null);
				}
			}
		 	return null;
	}
	 
	 
	 public static String getRoleDescriptionFromdbByRole(Context ctx) throws Exception{
	        String userName = null;
	        String roles = null;
	        String userNameDisplay = null;
	        //String marketrep_username_to_be_visible_as = "MarketingRep";
	        
	      
	        if(ctx.get("roles") != null && !HtmlConstants.EMPTY.equals(ctx.get("roles")))
	            roles =ctx.get("roles").toString();
	        
	       
	        if(roles != null && !HtmlConstants.EMPTY.equals(roles)){
	        	Map map = (Map)SqlResources.getSqlMapProcessor(ctx).findByKey("agency_master.getRoleDescriptionByRole_p", ctx);
	            if(map != null && !map.isEmpty() && map.get("roles_description")!= null && !HtmlConstants.EMPTY.equals(map.get("roles_description"))){
	              String roles_description =  map.get("roles_description").toString();
	              logger.debug(ctx, "roles "+roles_description);
	              return roles_description;
	            }
	        } 
	        logger.debug(ctx, "roles "+roles);
	        return roles;
	    }
	 public static Object checkValidEmailUsingIntg(Context ctx,String fieldName) throws Exception{
		 
		 if(ctx.get("isEmailValidationIntgEnabled") == null || HtmlConstants.EMPTY.equals(ctx.get("isEmailValidationIntgEnabled").toString()) 
				 || ctx.get("isEmailValidationIntgEnabled").toString().equals("N")){
			 return true;
		 }
		 
		 boolean isEmailValid =false; 
		  String wsURL = SystemProperties.getInstance().getProperty(
					"integration.ws.deprecated.baseURL"); // "http://localhost:8080/intws/api/";
			String username = SystemProperties.getInstance().getProperty(
					"integration.ws.deprecated.username"); // "pone";
			String password = SystemProperties.getInstance().getProperty(
					"integration.ws.deprecated.password");// "Secure$1";
		  
		 
		  IntegrationClient integrationClient = new IntegrationClient(wsURL,username, password);
		  try {
			  JSONObject verifyAddressRequest = new JSONObject();
			  verifyAddressRequest.put("serviceName", "hippoverifyemail");
			  verifyAddressRequest.put("clientRole", "PONE"); JSONObject payload = new
			  JSONObject(); JSONObject requestParams = new JSONObject();
			  requestParams.put("intgerationInputJSON", new JSONObject().put("email",ctx.get(fieldName))); 
			  payload.put("request", requestParams);
			  verifyAddressRequest.put("payload", payload);
			  
			  if(logger.isDebugEnabled(ctx)) logger.debug(ctx, "Verify Email Request : " +
			  verifyAddressRequest.toString()); String verifyAddressResponse = (String)
			  integrationClient.sendAndReceive(verifyAddressRequest);
			  
			  if(logger.isDebugEnabled(ctx)) logger.debug(ctx, "Verify Email Response : " +
			  verifyAddressResponse); JSONObject jsonObject = new
			  JSONObject(verifyAddressResponse);
			  
			  if(jsonObject.has("payload") && jsonObject.get("payload") instanceof
			  JSONObject) { JSONObject payloadObject =(JSONObject)jsonObject.get("payload"); 
			  String status =jsonObject.getString("status");
			  
			  if(status.equalsIgnoreCase("success")) { 
				  String isEmailValidResponse=payloadObject.getJSONObject("emailVerifyResponse").
			                                        getString("isEmailValid"); 
				  logger.debug(ctx, "Response from API : " + isEmailValidResponse);
			      isEmailValid =Boolean.parseBoolean(isEmailValidResponse); 
			  } else if (status.equalsIgnoreCase("failed")) {
				  String isEmailValidResponse=payloadObject.getJSONObject("emailVerifyResponse").getString("error");
			     logger.debug(ctx, "Error from API : " + isEmailValidResponse); 
			  }
			  }
		  }catch (JSONException e) {
			logger.error("Exception while parsing Json Object " + e.getMessage());
			isEmailValid = true;
		}
		  return isEmailValid;
	    }
	 
	 public static void getCommissionAttachmentApproved(Context ctx) throws Exception{
		 try {
			 		
					new SetParametersForStoredProcedures().setParametersInContext(ctx, "commission_schedule_id,Producer_Number_Commission_Schedule_ID");
					Map map = (Map)SqlResources.getSqlMapProcessor(ctx).findByKey("commission.getCommissionScheduleAndTemplateSchemaXML_p", ctx);
					new WorkflowImpl().populateClobValue(map);
					if(map != null && map.get("template_schema") != null && !map.get("template_schema").toString().equals(HtmlConstants.EMPTY)
							&& map.get("commission_schedule") != null && !map.get("commission_schedule").toString().equals(HtmlConstants.EMPTY)){
						
						ctx.put("template_schema", map.get("template_schema"));
						
						String commission_schedule = map.get("commission_schedule").toString();
						
						SAXBuilder builder = new SAXBuilder();
						Document doc = builder.build(new StringReader(commission_schedule));
						Element root = doc.getRootElement();
						
						Element rootElement = new Element("root");
						
						if(root != null && root.getChildren().size() > 0){
							for(int i=0; i<root.getChildren().size(); i++){
								Element ele = (Element)root.getChildren().get(i);
								
								if(ele.getName().equals("commission_schedules")){
									if(ele.getChildren() != null && ele.getChildren().size() > 0){
										int index = 0;
										for(int j=0; j<ele.getChildren().size(); j++){
											Element row = (Element)ele.getChildren().get(j);
											
											if(row.getName().equals("commission_schedule")){
												Element root1Element = new Element("root1");
												
												Element rate_idElement = new Element("rate_id");
												rate_idElement.setText(row.getChildText("rate_id"));
												root1Element.addContent(rate_idElement.detach());
												
												Element nb_varianceElement = new Element("nb_variance");
												nb_varianceElement.setText(ctx.get("comm_rate_NB_variance_"+index) != null ? ctx.get("comm_rate_NB_variance_"+index).toString() : HtmlConstants.EMPTY);
												root1Element.addContent(nb_varianceElement.detach());
												
												Element rb_varianceElement = new Element("rb_variance");
												rb_varianceElement.setText(ctx.get("comm_rate_RB_variance_"+index) != null ? ctx.get("comm_rate_RB_variance_"+index).toString() : HtmlConstants.EMPTY);
												root1Element.addContent(rb_varianceElement.detach());
												
												Element effective_dateElement = new Element("effective_date");
												effective_dateElement.setText(ctx.get("effective_date_"+index).toString());
												root1Element.addContent(effective_dateElement.detach());
												
												Element expiration_dateElement = new Element("expiration_date");
												expiration_dateElement.setText(ctx.get("expiration_date_"+index).toString());
												root1Element.addContent(expiration_dateElement.detach());
												
												rootElement.addContent(root1Element.detach());
												index++;
											}
										}
									}
								}
							}
						}
						
						String xml = new XMLOutputter(Format.getPrettyFormat()).outputString(rootElement);
						ctx.put("operationType", "U");
						ctx.put("commission_schedule", xml);
						ctx.put("approved_variance", 3);
						
					}
				
		} catch (Exception e) { 
			// TODO: handle exception
		}
		 
	 }

	public static Object validateProducerDetailAddtionalEmailAddress(Context ctx) throws Exception {
		if (ctx.get("additional_email_address_producerDetails_security") != null
				&& ctx.get("additional_email_address_producerDetails_security").equals("Y")) {
			boolean isErrorFound = false;
			String email_category = ctx.get("email_category") != null
					&& !ctx.get("email_category").toString().equals(HtmlConstants.EMPTY)
							? ctx.get("email_category").toString()
							: null;
			String email_additionalAddress = ctx.get("email_additionalAddress") != null
					&& !ctx.get("email_additionalAddress").toString().equals(HtmlConstants.EMPTY)
							? ctx.get("email_additionalAddress").toString()
							: null;
			String email_category_producerDetails_security = ctx.get("email_category_producerDetails_security") != null
					? ctx.get("email_category_producerDetails_security").toString()
					: null;
			String email_additionalAddress_producerDetails_security = ctx
					.get("email_additionalAddress_producerDetails_security") != null
							? ctx.get("email_additionalAddress_producerDetails_security").toString()
							: null;

			if (ctx.get(HtmlConstants.INET_METHOD).toString().equals("modify")) {
				if ((email_category != null && !HtmlConstants.EMPTY.equals(email_category.toString()))
						|| (email_additionalAddress != null
								&& !HtmlConstants.EMPTY.equals(email_additionalAddress.toString()))) {
					if (email_category_producerDetails_security == "Y"
							&& (email_category == null || HtmlConstants.EMPTY.equals(email_category.toString()))) {
						DataUtils.populateError(ctx, "email_category", "email_category_prospect_agency_Error");
						return null;
					}
					if (email_additionalAddress_producerDetails_security == "Y" && (email_additionalAddress == null
							|| HtmlConstants.EMPTY.equals(email_additionalAddress.toString()))) {
						DataUtils.populateError(ctx, "email_additionalAddress",
								"email_additionalAddress_prospect_agency_Error");
						return null;
					}
				}
			}

			checkDuplicateAdditionalEmailAddress(ctx);
			List list = null;
			if (ctx.get(Constants.INET_ERRORS_LIST) != null && !HtmlConstants.EMPTY.equals(Constants.INET_ERRORS_LIST))
				return null;

			if ((email_category == null || HtmlConstants.EMPTY.equals(email_category))
					|| (email_additionalAddress == null || HtmlConstants.EMPTY.equals(email_additionalAddress))) {
				if (!ctx.get("inet_method").equals("addEmailAdditionalEmailAddress")) {
					list = (List) ctx.get("additionalEmailAddress_list_listfreeform_1");
					if (list == null || HtmlConstants.EMPTY.equals(list) || list.size() == 0) {
						DataUtils.populateError(ctx, "additionalEmailAddressError", "atleastOnePreferredEmailErrorKey");
						return null;
					}
				}
			}
			if (email_additionalAddress != null && !ProducerOneUtils.checkEmail(ctx, "email_additionalAddress")) {
				DataUtils.populateError(ctx, "additionalEmailAddressError", "ValidEmailIdErrorKey");
				return null;
			}

			HttpSession sess = null;
			if (ctx.get("DocumentRequest") != null && ctx.get("DocumentRequest") instanceof HttpServletRequest) {
				sess = ((HttpServletRequest) ctx.get("DocumentRequest")).getSession();
			}
			if (sess == null)
				return null;
		}
			return null;
		
	}
	public static void assignDbaSearchValue(Context ctx) throws Exception{
		if(((java.lang.Object)ctx.get("searchField")).equals("15") && ((java.lang.Object)ctx.get("comingFromDbaNameSearch")).equals("Y")){
			DataUtils.assign(ctx, "isdbasearch", (java.lang.Object)"Y");
		}
	}
	
	public void refreshProductValidation(Context ctx) {
		
		List validationList=new ArrayList();
		validationList=(List)ctx.get("productValidationDetailForSelfService_list_1");
		
		if(validationList==null)
			return;
		
		for(int i=0; i<validationList.size();i++) {
			Map map=(Map)validationList.get(i);
			map.put("effective_date",ctx.get("effective_date_"+i));
			map.put("expire_new_date",ctx.get("expire_new_date_"+i));
			map.put("expire_renew_date",ctx.get("expire_renew_date_"+i));
			map.put("nb_threshold_rate",ctx.get("nb_threshold_rate_"+i));
			map.put("rb_threshold_rate",ctx.get("rb_threshold_rate_"+i));
			map.put("poliservfee",ctx.get("poliservfee_"+i));
			map.put("mvr_clue_threshold",ctx.get("mvr_clue_threshold_"+i));
			map.put("Clue_Threshold",ctx.get("Clue_Threshold_"+i));
			map.put("CAT_EP_Factor",ctx.get("CAT_EP_Factor_"+i));
		}
	}
	
	public static void checkWholeSaleTransferAddedOrNot(Context ctx) throws Exception
	 {	
		 if(ctx.get("transfersStep2_list_mom_2") != null && ctx.get("transfersStep2_list_mom_2") instanceof List &&
					((List)ctx.get("transfersStep2_list_mom_2")).size() > 0) {

	        Map map = (Map)((List)ctx.get("producer_number_transfer_filters_list_1")).get(0);
	        Map map1 = (Map)((List)ctx.get("transfersStep2_list_mom_2")).get(0);
	 
	        String policyFiltered[]= ((map1.get("totalpolicies").toString()).trim()).toLowerCase().split(",");
	        String policyToBeFiltered[]= ((map.get("totalpolicies").toString()).trim()).toLowerCase().split(",");
	        Arrays.sort(policyFiltered);
	        Arrays.sort(policyToBeFiltered);
	        if(Arrays.equals(policyFiltered,policyToBeFiltered))
	        {
	        	DataUtils.populateError((Context)ctx, "pageError", DataUtils.getMessage((Context)ctx, "PoliciesRepeatedErrorKey"));
	        }
	        
	    }
		
	 }
	
	//Method created to set description for history report for object history
    public static void setObjectHistoryReportDescription(Context ctx){
          if(ctx.get("objecttype_desc") != null){
                String objecttype_desc = ctx.get("objecttype_desc").toString();
                String description = null;
                
                if("entityGeneralDetails".equals(objecttype_desc)){
                      description = "entityGeneralDetails_history_label";
                }else if("agentGeneralDetails".equals(objecttype_desc)){
                      description = "agentGeneralDetails_history_label";
                }else if("agencyProducer".equals(objecttype_desc)){
                      description = "agencyProducer_history_label";
                }else if("producerAssociation".equals(objecttype_desc)){
                      description = "producerAssociation_history_label";
                }else if("producerDetail".equals(objecttype_desc)){
                      description ="producerDetail_history_label";
                }else if("producerBankInformationDetail".equals(objecttype_desc)){
                      description = "producerBankInformationDetail_history_label";
                }else if("productValidationDetail".equals(objecttype_desc)){
                      description = "productValidationDetail_history_label";
                }
                
                ctx.put("REPORT_DESCRIPTION", description);
          }
    }
    
    //Method created to validateAdditionalEmailAddressForSelfService in agent detail for self service screen
    public static Object validateAdditionalEmailAddressForSelfService(Context ctx) throws Exception {
    	if(ctx.get("agentGeneralDetailsForSelfServiceApproval_additional_email_address_security") != null && ctx.get("agentGeneralDetailsForSelfServiceApproval_additional_email_address_security").equals("Y")){
        boolean isErrorFound = false;
        String requiredEmailType = ctx.get("requiredEmailTypeCode") != null && !HtmlConstants.EMPTY.equals(ctx.get("requiredEmailTypeCode")) ? ctx.get("requiredEmailTypeCode").toString(): null;
        
        String email_category = ctx.get("email_category") != null && !ctx.get("email_category").toString().equals(HtmlConstants.EMPTY) ? ctx.get("email_category").toString() : null;
        String email_additionalAddress = ctx.get("email_additionalAddress") != null && !ctx.get("email_additionalAddress").toString().equals(HtmlConstants.EMPTY) ? ctx.get("email_additionalAddress").toString() : null;
       
        
        if(ctx.get("inet_page") != null  && ctx.get("inet_page").equals("entityGeneralDetailsForSelfServiceApproval"))
            ProducerOneUtility.checkDuplicateAdditionalEmailAddressWithProducerNumber(ctx);
        else
        	ProducerOneUtility.checkDuplicateAdditionalEmailAddress(ctx);
        
        if(ctx.get(Constants.INET_ERRORS_LIST) != null && !HtmlConstants.EMPTY.equals(Constants.INET_ERRORS_LIST)) 
        	return null;
        
        HttpSession sess = null;
        if(ctx.get("DocumentRequest") != null && ctx.get("DocumentRequest") instanceof HttpServletRequest){
            sess = ((HttpServletRequest)ctx.get("DocumentRequest")).getSession();
        }
        
        if(sess == null)
            return null;
        
        List list = null;
        if((email_category == null || HtmlConstants.EMPTY.equals(email_category)) ||  (email_additionalAddress== null || HtmlConstants.EMPTY.equals(email_additionalAddress)) ){
        	if (!ctx.get("inet_method").equals("addEmailAdditionalEmailAddress")) {
        		list = (List) ctx.get("additionalEmailAddress_list_listfreeform_1");
        		if (list == null || HtmlConstants.EMPTY.equals(list) || list.size() == 0){
            		DataUtils.populateError(ctx, "additionalEmailAddressError", "atleastOnePreferredEmailErrorKey");
                    return null;
            	}
        	}
        }
    	
        
        
        if(email_additionalAddress != null && !ProducerOneUtils.checkEmail(ctx, "email_additionalAddress")){
            DataUtils.populateError(ctx, "additionalEmailAddressError", "ValidEmailIdErrorKey");
            return null;
        }
        
        

        Context newCtx = new Context();
        newCtx.setProject(ctx.getProject());
        
        
        
        newCtx.put("email_category", email_category);
        
        newCtx.put("email_additionalAddress", email_additionalAddress);
        newCtx.put("person_id", ctx.get("person_id"));
        newCtx.put("object_type_code", ctx.get("object_type_code"));
        newCtx.put("last_updated_by", ctx.get("last_updated_by"));
        
        new SetParametersForStoredProcedures().setParametersInContext(newCtx, 
                "last_updated_by,object_type_code,person_id,email_category,email_additionalAddress");
        
            Object obj = SqlResources.getSqlMapProcessor(newCtx).findByKey("producer_org_mapping.validateAdditionalEmailAddressData_p", newCtx);
            
            if(obj != null){
                Map map = (Map)obj;
                if(map.get("isAdditionalEmailAddressFound") != null && 
                        !HtmlConstants.EMPTY.equals(map.get("isAdditionalEmailAddressFound").toString())
                        && "Y".equals(map.get("isAdditionalEmailAddressFound").toString())){
                    DataUtils.populateError((Context)ctx, "additionalEmailAddressError", "adminDuplicateError");
                    
                    return null;
                }
                
            }
            
            
            List additionalEmailAddressList = null;
            
            
            
            
            if(sess.getAttribute("additionalEmailAddress_list_listfreeform_1") != null &&
                    sess.getAttribute("additionalEmailAddress_list_listfreeform_1") instanceof List){
                additionalEmailAddressList = (List)sess.getAttribute("additionalEmailAddress_list_listfreeform_1");
                if(additionalEmailAddressList != null && additionalEmailAddressList.size() > 0){
                	
                String 	requiredEmailTypeId="";
                newCtx.put("requiredEmailType", requiredEmailType);
                new SetParametersForStoredProcedures().setParametersInContext(newCtx,"requiredEmailType");
                    
                Object requiredEmailDescObj = SqlResources.getSqlMapProcessor(newCtx).findByKey("SqlStmts.sqlStatementsviewgetEmailCategoryValByDesc", newCtx);
                if(requiredEmailDescObj != null && requiredEmailDescObj instanceof Map) {
                	Map requiredEmailDescMap=(Map)requiredEmailDescObj;
                	if(requiredEmailDescMap != null)
                		requiredEmailTypeId=requiredEmailDescMap.get("email_category_val").toString();
                }
                	
                	
                
                  
                  
                  
                    int preferredMailCount =0;
                 	for(int i=0; i<additionalEmailAddressList.size(); i++){
                        Map rowMap = (Map)additionalEmailAddressList.get(i);
                        if((rowMap.get("isNew") != null && rowMap.get("isNew").equals("Y"))){
                            Context localCtx = new Context();
                            localCtx.setProject(ctx.getProject());
                            localCtx.putAll(rowMap);
                            
                            localCtx.put("email_category", rowMap.get("email_category"));
                            localCtx.put("email_additionalAddress", rowMap.get("email_additionalAddress"));
                            localCtx.put("person_id", rowMap.get("person_id"));
                            localCtx.put("object_type_code", rowMap.get("object_type_code"));
                            
                            
                            
                             new SetParametersForStoredProcedures().setParametersInContext(localCtx, "last_updated_by,object_type_code,person_id,email_category,email_additionalAddress");
                                
                                    obj = SqlResources.getSqlMapProcessor(localCtx).findByKey("producer_org_mapping.validateAdditionalEmailAddressData_p", localCtx);
                                    
                                    if(obj != null){
                                        Map map = (Map)obj;
                                        if(map.get("isAdditionalEmailAddressFound") != null && 
                                                !HtmlConstants.EMPTY.equals(map.get("isAdditionalEmailAddressFound").toString())
                                                && "Y".equals(map.get("isAdditionalEmailAddressFound").toString())){
                                            DataUtils.populateError((Context)ctx, "additionalEmailAddressError", "adminDuplicateError");
                                            
                                            return null;
                                        }
                                        
                                    }
                            
                        }
                        if(requiredEmailType != null){
                        	if(rowMap.get("email_category") != null && !HtmlConstants.EMPTY.equals(rowMap.get("email_category"))){
                        		String listEmailCategory = rowMap.get("email_category").toString();
                        		if(requiredEmailTypeId.equals(listEmailCategory) 
                        				//&& (ctx.get("email_category_desc") != null && !"Preferred_Email".equals(ctx.get("email_category_desc").toString())) && (ctx.get("email_additionalAddress") != null && !"".equals(ctx.get("email_additionalAddress").toString()))
                        				){
                        			preferredMailCount = preferredMailCount+1;	
                        		}
                        	}
                        }
                    }
                 	if(ctx.get("email_category_desc") != null && !HtmlConstants.EMPTY.equals(ctx.get("email_category_desc"))){
                		String listEmailCategory = ctx.get("email_category_desc").toString();
                		if(requiredEmailType.equals(listEmailCategory) ){
                			preferredMailCount = preferredMailCount+1;	
                		}
                	}
                	if(!ctx.get("inet_method").equals("addEmailAdditionalEmailAddress") && preferredMailCount ==0){
                		DataUtils.populateError((Context)ctx, "additionalEmailAddressError", "atleastOnePreferredEmailErrorKey");
                        return null;
                	}
                }
            }
    	}
    	return null;
    }
    
    public static String checkDuplicateAdditionalEmailAddressWithProducerNumber(Context ctx) throws Exception {
  		List list = null;
  		if (ctx.get("additionalEmailAddress_list_listfreeform_1") != null && !HtmlConstants.EMPTY.equals(ctx.get("additionalEmailAddress_list_listfreeform_1"))) {
  			list = (List) ctx.get("additionalEmailAddress_list_listfreeform_1");
  		}
  		if (list == null || HtmlConstants.EMPTY.equals(list) || list.size() == 0)
  			return null;

  		if (list != null && !HtmlConstants.EMPTY.equals(list)) {
  			int listEmailCategoryVal = 0;
  			int ctxEmailCategoryVal = 0;
  			
  			int listProducerNumId = 0;
  			int ctxProducerNumId = 0;
  			
  			int producerNumberSecurityVal=SecurityResources.getInstance(ctx).getAccessType("entityGeneralDetailsForSelfServiceApproval_agency_code_security", (Context) ctx);
  			
  			for (int i = 0; i < list.size(); i++) {
  				Map listMap = (Map) list.get(i);
  				if (listMap.get("email_category") != null && !HtmlConstants.EMPTY.equals(listMap.get("email_category"))){
  					if (ctx.get("email_category") != null && !HtmlConstants.EMPTY.equals(ctx.get("email_category"))){
  						listEmailCategoryVal = Integer.parseInt(listMap.get("email_category").toString());
  						ctxEmailCategoryVal = Integer.parseInt(ctx.get("email_category").toString());
  						if ((listEmailCategoryVal == ctxEmailCategoryVal) && producerNumberSecurityVal != SecurityResources.SHOW) {
  							DataUtils.populateError((Context)ctx, "additionalEmailAddressError", "additionalEmailAddressDuplicateErrorKey");
  							return null;
  						}
  					}
  				}
  				
  				if (listMap.get("producer_num_id") != null && !HtmlConstants.EMPTY.equals(listMap.get("producer_num_id")) && (producerNumberSecurityVal == SecurityResources.SHOW) && (listEmailCategoryVal == ctxEmailCategoryVal)){
  					if (ctx.get("producer_num_id") != null && !HtmlConstants.EMPTY.equals(ctx.get("producer_num_id"))){
  						listProducerNumId = Integer.parseInt(listMap.get("producer_num_id").toString());
  						ctxProducerNumId = Integer.parseInt(ctx.get("producer_num_id").toString());
  						if (listProducerNumId == ctxProducerNumId ) {
  							DataUtils.populateError((Context)ctx, "additionalEmailAddressError", "additionalEmailAddressDuplicateErrorKey");
  							return null;
  						}
  					}
  				}
  				
  				
  			}

  		}
  		return null;
  	}
    
    public static void refreshFloodInsuranceDocumentList(Context ctx){		
		List docsList = new ArrayList();
		List finalList = new ArrayList();
		String templateDocName = ctx.get("template_document_name") != null
				? ctx.get("template_document_name").toString()
				: "";
		if (ctx.get("uploadPopup_list_1") != null && !HtmlConstants.EMPTY.equals(ctx.get("uploadPopup_list_1")))
			docsList = (List) ctx.get("uploadPopup_list_1");
		for (int i = 0; i < docsList.size(); i++) {
			Map rowMap = (Map) docsList.get(i);
			String docmentName=rowMap.get("document_name").toString();
			if (!docmentName.startsWith(templateDocName)) {
				finalList.add(rowMap);
			}
		}
		ctx.put("uploadPopup_list_1",finalList);
	}
    
	public static void getParentProducerNumberId(Context ctx) throws Exception {
		if (ctx.get("masterCodes_list_01") != null && ((List) ctx.get("masterCodes_list_01")).size() > 0) {
			List list = (List) ctx.get("masterCodes_list_01");
			for (int i = 0; i < list.size(); i++) {
				Map listMap = (Map) list.get(i);
				if (listMap.get("hierarchy_type_code") != null && listMap.get("hierarchy_type_code").toString().equalsIgnoreCase("RPTH")) {
					String parent_producer_number = listMap.get("masterProducer_hierarchy_desc").toString();
					ctx.put("parent_producer_number", parent_producer_number);
					new SetParametersForStoredProcedures().setParametersInContext(ctx, "parent_producer_number");

					Object obj = SqlResources.getSqlMapProcessor(ctx)
							.findByKey("producer_number.getProducerDetailsByProducerNumber_p", ctx);
					if(obj != null && obj instanceof Map) {
	                	Map mapObj=(Map)obj;
	                	if(mapObj != null)
	                		ctx.put("parent_producer_number_id",mapObj.get("producer_number_id").toString());
	                }
				}

			}
		}
	}
	
	public void getSubjectLine(DocuSignRequest docuSignRequest,IContext ctx)
	{
		try {
			String subjectline="e-Signature";
			logger.debug("Set subjectline:"+ctx.get("eSignSubjectLine"));
			if(ctx.get("eSignSubjectLine")!=null)
			{
				subjectline=ctx.get("eSignSubjectLine").toString();
				if(subjectline.isEmpty())
				{
					subjectline=getPropSubject();
				}
			}
			else
			{
				subjectline=getPropSubject();
			}
			docuSignRequest.setSubjectline(subjectline);
		}
		catch(Exception e) {
			docuSignRequest.setSubjectline("e-Signature");
			logger.error("Error in setting subjectline eSign setting default value ='e-Signature' " + e.getMessage());
			e.printStackTrace();
		}
	}
	private String getPropSubject()
	{
		String esignemailsubject = null;
		try {
			esignemailsubject = SystemProperties.getInstance().getString("docusign.mail.subject");
			if(esignemailsubject!=null && !esignemailsubject.isEmpty())
				return esignemailsubject;
			else
				return "e-Signature";
		}
		catch(Exception e)
		{
			logger.error("Error in setting subjectline eSign setting default value ='e-Signature' " + e.getMessage());
			e.printStackTrace();
			return "e-Signature";
		}
	}
	
}