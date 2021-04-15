
package com.userbo.common.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.ormapping.ibatis.SqlResources;
import com.util.Context;
import com.util.HtmlConstants;
import com.util.InetLogger;
import com.util.StringUtils;

public class ProducerOneUtilsImpl {
	public static String DATE_PATTERN = "MM-dd-YYYY hh:mm";
	private static InetLogger logger = InetLogger.getInetLogger("ProducerOneUtils");
	private static final String rownum = "rownum";
	private static String tempProdCode = null;
	private static String prodNumId = null;
	
	public static String getCustomizedProducerCode(Context ctx) {

		String generatedSubProducerNumber = "";
		String outputParam="";
		try {
			// call procedure

			int producerNumberId = Integer.parseInt(ctx.get("producer_number_id").toString());
			String prodNumIdTemp = ctx.get("producer_number_id").toString();
			ctx.put("producer_number_id", producerNumberId);
			ctx.put("output", outputParam);
			
			List subProdCodeDB = (List) SqlResources.getSqlMapProcessor(ctx).select("person.getLatestSubProducerCode_p",
					ctx);
			
			System.out.println(subProdCodeDB);
			String prevSubProducerCode = "";

			if (subProdCodeDB != null && subProdCodeDB.size() != 0) {
				HashMap<String, String> map = (HashMap<String, String>) subProdCodeDB.get(subProdCodeDB.size() - 1);
				prevSubProducerCode = map.get("sub_producer");
			}

			if (prodNumIdTemp == null || !prodNumIdTemp.equals(prodNumId)) {
				tempProdCode = null;
				prodNumId = prodNumIdTemp;
			}

			int temp = 0;
			if ((prevSubProducerCode != null && !prevSubProducerCode.equals("")) || tempProdCode != null) {
				if (tempProdCode == null) {
					tempProdCode = prevSubProducerCode;
				} else {
					prevSubProducerCode = tempProdCode;
				}
				/**/
				if (StringUtils.isNumeric(prevSubProducerCode)) {
					int intPrevSubProducerCode = Integer.parseInt(prevSubProducerCode);
					if (intPrevSubProducerCode == 98)
						generatedSubProducerNumber = "1A";
					else
						generatedSubProducerNumber = String.valueOf(intPrevSubProducerCode + 1);
				} else {

					if (prevSubProducerCode.charAt(0) == '1') {
						if (prevSubProducerCode.equals("1Z")) {
							generatedSubProducerNumber = "AA";
						} else {
							char c = prevSubProducerCode.charAt(1);
							c += 1;
							generatedSubProducerNumber = String.valueOf(prevSubProducerCode.charAt(0))
									+ String.valueOf(c);
						}
					} else {
						char c1 = prevSubProducerCode.charAt(0);
						char c2 = prevSubProducerCode.charAt(1);
						if (c2 == 'Z') {
							c1++;
							c2 = 'A';
						} else {
							c2++;
						}
						generatedSubProducerNumber = String.valueOf(c1) + String.valueOf(c2);
					}
				}
			} else {
				generatedSubProducerNumber = "01";
			}

			if (generatedSubProducerNumber != null && generatedSubProducerNumber.length() < 2) {
				generatedSubProducerNumber = "0" + generatedSubProducerNumber;
			}
			tempProdCode = generatedSubProducerNumber;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
		}
		ctx.put("sub_producer", generatedSubProducerNumber);
		logger.debug(ctx, "Su_producer number");
		
		return generatedSubProducerNumber;

	}
	
	public static String getSevenDigitAgencyCode(Context ctx) 
	{		
		return null;
	}
		
	
	

public static String checkContains(Context ctx){
    
	String stringFound = "false";
    String inputString=null;
    String stringToCampare="5";
    
    if(ctx.containsKey("contacttypid") && ctx.get("contacttypid")!=null && !"".equals(ctx.get("contacttypid").toString())){
    	inputString=ctx.get("contacttypid").toString();
    }
    /* else condition to check contact type on view action */
    else if(ctx.containsKey("contacttype") && ctx.get("contacttype")!=null && !"".equals(ctx.get("contacttype").toString())){
    	inputString=ctx.get("contacttype").toString();
    }
    if(inputString == null || "".equals(inputString)){
        return stringFound;
    }else{
        StringTokenizer tokens = new StringTokenizer(inputString, ",");
        
        if(tokens.countTokens() > 0){
            while (tokens.hasMoreTokens()) {
                String tokenValue = tokens.nextToken();
                if (tokenValue != null && !HtmlConstants.EMPTY.equals(tokenValue)) {
                    if (tokenValue.equals(stringToCampare)) {
                        stringFound = "true";
                        break;
                    }
                }
                
            }
              
        }
        
    }
    ctx.put("EqualsFlag", stringFound);
    return stringFound;
}
public static String generateMovingLocationLetter(Context ctx)
{
	try
	{
	List list= (List)ctx.get("uploadDocument_list_1");
	List newList=new ArrayList();
	Map<String,String> map1=new HashMap<String,String>();
	String fileName="";
	if (!list.isEmpty()) 
	{
		for (int i = 0; i < list.size(); i++) 
		{
			Map map = (Map) list.get(i);
			fileName=map.get("document_name").toString();
			map.put("file_name", fileName);
			map.put("document_type_id", "7");
			list.clear();
			list.add(map);
			ctx.put("uploadPopup_list_1", list);
		}
	}
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	
	return "";
}
}
