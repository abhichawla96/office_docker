
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
	private static String tempProdCode=null;
	private static String agencyCode = null;
	
	public static String getCustomizedProducerCode(Context ctx) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String generatedSubProducerNumber = "";
		try {
			// call procedure
			
			int producerNumberId = Integer.parseInt(ctx.get("producer_number_id").toString());
			String agencyCodeTemp=ctx.get("agency_id").toString();
			ctx.put("producer_number_id",producerNumberId);
			List prodCode = (List) SqlResources.getSqlMapProcessor(ctx).select("person.getSubProducerAssociations_data_p",ctx);
			System.out.println(prodCode);
			String subProducerNumber = "";
			
			if(prodCode!=null && prodCode.size()!=0){
				HashMap<String, String> map=(HashMap<String, String>)prodCode.get(prodCode.size()-1);
				subProducerNumber=map.get("sub_producer");
			}
			
			if(agencyCodeTemp==null || !agencyCodeTemp.equals(agencyCode)){
				tempProdCode=null;
				agencyCode=agencyCodeTemp;
			}
			
			int temp = 0;
			if ((subProducerNumber != null && !subProducerNumber.equals("")) || tempProdCode != null) {
				if(tempProdCode==null){
					tempProdCode=subProducerNumber;
				}else{
					subProducerNumber=tempProdCode;
				}
				
				if (StringUtils.isNumeric(subProducerNumber)) {
					temp = Integer.parseInt(subProducerNumber) + 1;
					if (temp > 98) {
						generatedSubProducerNumber = "1A";
					} else {
						generatedSubProducerNumber = String.valueOf(temp);
					}
				} else {
					String digit = String.valueOf(subProducerNumber.charAt(0));
					temp = Integer.parseInt(digit);
					char character = subProducerNumber.charAt(1);
					if (character == 'Z' || character == 'z') {
						temp += 1;
						generatedSubProducerNumber = String.valueOf(temp) + "A";
					} else {
						character += 1;
						generatedSubProducerNumber = String.valueOf(temp) + character;

					}
				}
			} else {
				generatedSubProducerNumber = "01";
			}
			if (generatedSubProducerNumber != null && generatedSubProducerNumber.length() < 2) {
				generatedSubProducerNumber = "0" + generatedSubProducerNumber;
			}
			tempProdCode=generatedSubProducerNumber;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
		} finally {
			try {
				con.close();
				rs.close();
				ps.close();
			} catch (Exception e) {
				// TODO: handle exception
				logger.info(e.getMessage());
			}
		}
		ctx.put("sub_producer", generatedSubProducerNumber);
		logger.debug(ctx, "Su_producer number");;
		return generatedSubProducerNumber;

	}
	
	public static String getSevenDigitAgencyCode(Context ctx) 
	{		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		String sqlstmt="select format(case when len(producer_number) < 7 then 2425 when len(producer_number) >= 7 then (convert(int,producer_number)+1)  end,'0000000') as producer_number from producer_number  where producer_number_id  in (select max(producer_number_id)  from producer_number)";
		String sqlAgencyName="select  name from agency_master where   agency_id ='"+ctx.get("agency_id").toString()+"'";
		String sevenDigitAgencyCode = "";
		String agencyName="";
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:jtds:sqlserver://10.10.106.253:1433/PRODUCERONE_SECURE_BUILDER",
					"sudhirj", "Sudhirj@2020");
			ps = con.prepareStatement(sqlstmt);
			
			rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				sevenDigitAgencyCode = String.valueOf(rs.getString("producer_number"));
			}
			ps1 = con.prepareStatement(sqlAgencyName);
			rs1 = ps1.executeQuery();
			while (rs1 != null && rs1.next()) {
				agencyName = String.valueOf(rs1.getString("name"));
			}	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		} finally {
			try {
				con.close();
				rs.close();
				ps.close();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
			}
		}
		ctx.put("producer_number", sevenDigitAgencyCode);
		ctx.put("producer_name", agencyName);
		return sevenDigitAgencyCode;
	}
		
	
	

public static String checkContains(Context ctx){
    
	String stringFound = "false";
    String inputString=null;
    String stringToCampare="5";
    
    if(ctx.get("contacttypid")!=null || !"".equals(ctx.get("contacttypid").toString())){
    	inputString=ctx.get("contacttypid").toString();
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
