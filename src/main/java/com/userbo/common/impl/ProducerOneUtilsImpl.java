
package com.userbo.common.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.util.Context;
import com.util.InetLogger;
import com.util.StringUtils;

public class ProducerOneUtilsImpl {
	public static String DATE_PATTERN = "MM-dd-YYYY hh:mm";
	private static InetLogger logger = InetLogger.getInetLogger("ProducerOneUtils");
	private static final String rownum = "rownum";
	private static String tempProdCode=null;
	public static String getCustomizedProducerCode(Context ctx) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String generatedSubProducerNumber = "";
		try {
			// call procedure
			int agencyId = Integer.parseInt(ctx.get("agency_id").toString());
			int producerNumberId = Integer.parseInt(ctx.get("producer_number_id").toString());
			int personId = Integer.parseInt(ctx.get("person_id").toString());

			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:jtds:sqlserver://10.10.106.253:1433/PRODUCERONE_SECURE_BUILDER",
					"sudhirj", "Sudhirj@2020");

			String sql = "EXEC getSubProducerAssociations_data_p ?,?,?,?,?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, personId);
			ps.setInt(2, producerNumberId);
			ps.setInt(3, 0);
			ps.setString(4, null);
			ps.setString(5, null);
			rs = ps.executeQuery();
			String subProducerNumber = "";
			while (rs != null && rs.next()) {
				System.out.println(rs.getInt("sub_producer"));
				subProducerNumber = String.valueOf(rs.getString("sub_producer"));
			}
			
			int temp = 0;
			if ((subProducerNumber != null & !subProducerNumber.equals("")) ||tempProdCode!=null ) {
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
		ctx.put("sub_producer", generatedSubProducerNumber);
	
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
    
	String stringFound = "true";
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
                        stringFound = "false";
                        break;
                    }
                }
                
            }
              
        }
        
    }
    ctx.put("EqualsFlag", stringFound);
    return stringFound;
}
	
}
