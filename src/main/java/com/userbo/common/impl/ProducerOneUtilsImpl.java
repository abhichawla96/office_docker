
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
				generatedSubProducerNumber = "1";				
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
		System.out.println(ctx.get("sub_producer"));
		return generatedSubProducerNumber;

	}
}
