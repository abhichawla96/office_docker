<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">
	
	<xsl:template match="/">
		<fo:root>
			<fo:layout-master-set>
				<fo:simple-page-master master-name="A4-portrait" page-width="21cm"   
					page-height="29.7cm" margin-top="0.2in" margin-bottom="1.0in" margin-left="0.5in" 
					margin-right="0.5in">
					<fo:region-body margin-top="0.5in"/>
					<fo:region-before extent="1.0in"/> 
					<fo:region-after /> 					
				</fo:simple-page-master>
			</fo:layout-master-set>	
			<fo:page-sequence master-reference="A4-portrait">			
				<fo:static-content flow-name="xsl-region-before">
					<fo:block text-align="left" color="grey">				  	
				  		<fo:external-graphic src="images/p1Logo.png" content-height="30px" content-width="340px"/>            	
				  	</fo:block>	
				</fo:static-content>
				<fo:flow flow-name="xsl-region-body">
					<fo:block  margin-top="0.1in">
						<fo:table>
							<fo:table-column/>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell>
										<fo:block font-size="8px"><xsl:value-of select="response/date"/></fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row height="10px">
									<fo:table-cell>
										<fo:block font-size="7px"></fo:block>
									</fo:table-cell>
								</fo:table-row>
								
							 	<!--<fo:table-row>
									<fo:table-cell>
										<fo:block font-size="8px"><xsl:value-of select="response/Agent_Name"/></fo:block>
									</fo:table-cell>
								</fo:table-row>-->
							 	<fo:table-row>
									<fo:table-cell>
										<fo:block font-size="8px"><xsl:value-of select="response/Entity_Name"/></fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row>
									<fo:table-cell>
										<fo:block font-size="8px"><xsl:value-of select="response/Agent_Address1"/></fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row>
									<fo:table-cell>
										<fo:block font-size="8px"><xsl:value-of select="response/Agent_Address2"/></fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row>
									<fo:table-cell>
										<fo:block font-size="8px"><xsl:value-of select="response/agencyCityStateZip"/></fo:block>
									</fo:table-cell>
								</fo:table-row>
								
					  		</fo:table-body>
						</fo:table>
					</fo:block>
					
					<fo:block margin-top="25px">
						<fo:table>
							<fo:table-column/>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell>
										<fo:block font-size="8px">Re : Appointment\Authorization with Builders Mutual Insurance Company</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>	
						</fo:table>
					</fo:block>
					
					<fo:block margin-top="15px">
						<fo:table>
							<fo:table-column/>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell>
										<fo:block font-size="8px">The Appointment(s)\Authorization(s) for <xsl:value-of select="response/Agent_Name"/> have been sent to the following states listed below.</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>	
						</fo:table>
					</fo:block>
					
					<fo:block margin-top="15px">
						<fo:table border="0.1pt solid black">
							<fo:table-column></fo:table-column>	    		         
							<fo:table-column></fo:table-column>
							
							<fo:table-column></fo:table-column>
							<fo:table-column></fo:table-column>
						 	<fo:table-body>
						 		<fo:table-row border="0.1pt solid black">
									<fo:table-cell border="0.1pt solid black">
										<fo:block font-size="8px" font-family="Arial" font-weight="bold" padding-start="5px" padding-top="3px" padding-bottom="3px" text-align="center">Company</fo:block>
									</fo:table-cell>
									<fo:table-cell border="0.1pt solid black">
										<fo:block font-size="8px" font-family="Arial" font-weight="bold" padding-start="5px" padding-top="3px" padding-bottom="3px" text-align="center">State</fo:block>
									</fo:table-cell>
									
									<fo:table-cell border="0.1pt solid black">
										<fo:block font-size="8px" font-family="Arial" font-weight="bold" padding-start="5px" padding-top="3px" padding-bottom="3px" text-align="center">LOA</fo:block>
									</fo:table-cell>
									<fo:table-cell border="0.1pt solid black">
										<fo:block font-size="8px" font-family="Arial" font-weight="bold" padding-start="5px" padding-top="3px" padding-bottom="3px" text-align="center">Effective Date</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<xsl:for-each select="response/appointment_letter_list_01/data">
									<fo:table-row border="0.1pt solid black">
										<fo:table-cell border="0.1pt solid black">
											<fo:block font-size="8px" font-family="Arial" padding-start="5px" padding-top="3px" padding-bottom="3px" text-align="center">
												<xsl:value-of select="company"/>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="0.1pt solid black">
											<fo:block font-size="8px" font-family="Arial" padding-start="5px" padding-top="3px" padding-bottom="3px" text-align="center">
												<xsl:value-of select="abbreviation"/>
											</fo:block>
										</fo:table-cell>
										
										<fo:table-cell border="0.1pt solid black">
											<fo:block font-size="8px" font-family="Arial" padding-start="5px" padding-top="3px" padding-bottom="3px" text-align="center">
												<xsl:value-of select="description"/>
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border="0.1pt solid black">
											<fo:block font-size="8px" font-family="Arial" padding-start="5px" padding-top="3px" padding-bottom="3px" text-align="center">
												<xsl:value-of select="effective_date"/>
											</fo:block>
										</fo:table-cell>
									</fo:table-row>
								</xsl:for-each>
						 	</fo:table-body>
						</fo:table>
					</fo:block>
					
					<fo:block margin-top="35px">
						<fo:table>
							<fo:table-column/>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell>
										<!--<fo:block font-size="8px">Should you have any further questions, please do not hesitate to contact me directly at <xsl:value-of select="response/adminPhone"/></fo:block>-->
										<fo:block font-size="8px">Thanks and Regards</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row>
									<fo:table-cell>
										<!-- <fo:block font-size="8px">or e-mail at <fo:inline font-weight="bold"><xsl:value-of select="response/adminSupportMail"/></fo:inline>.</fo:block>-->
										<fo:block font-size="8px"><xsl:value-of select="response/appointment_letter_list_01/data/company"/></fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>	
						</fo:table>
					</fo:block>
					<fo:block margin-top="45px">
						<fo:table>
							<fo:table-column/>
							<fo:table-body>								
								<fo:table-row>
									<fo:table-cell>
										<!-- <fo:block font-size="8px">or e-mail at <fo:inline font-weight="bold"><xsl:value-of select="response/adminSupportMail"/></fo:inline>.</fo:block>-->
										<fo:block font-size="8px">This document shall be retained while your appointment is in effect and for at least 5 years after the termination of your appointment.</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>	
						</fo:table>
					</fo:block>
					
					<!--<fo:block margin-top="35px">
						<fo:table>
							<fo:table-column/>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell>
										<fo:block font-size="8px">Sincerely,</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>	
						</fo:table>
					</fo:block>
					
					<fo:block margin-top="20px">
						<fo:table>
							<fo:table-column/>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell>
										<fo:block font-size="8px"><xsl:value-of select="response/user_name"/></fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>	
						</fo:table>
					</fo:block>
					
					--><!--<fo:block margin-top="15px">
						<fo:table>
							<fo:table-column></fo:table-column>
							<fo:table-column></fo:table-column>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell width="15px"><fo:block font-size="8px" font-family="Arial">cc : </fo:block></fo:table-cell>
									<fo:table-cell>
										<fo:block>
											<fo:table>
												<fo:table-column/>
												<fo:table-body>
													<fo:table-row>
														<fo:table-cell>
															<fo:block font-size="8px"><xsl:value-of select="response/agentname"/></fo:block>
														</fo:table-cell>
													</fo:table-row>
												 	<fo:table-row>
														<fo:table-cell>
															<fo:block font-size="8px"><xsl:value-of select="response/agencyname"/></fo:block>
														</fo:table-cell>
													</fo:table-row>
													<fo:table-row>
														<fo:table-cell>
															<fo:block font-size="8px"><xsl:value-of select="response/addressMap/data/addressline1"/></fo:block>
														</fo:table-cell>
													</fo:table-row>
													<fo:table-row>
														<fo:table-cell>
															<fo:block font-size="8px"><xsl:value-of select="response/addressMap/data/addressline2"/></fo:block>
														</fo:table-cell>
													</fo:table-row>
													<fo:table-row>
														<fo:table-cell>
															<fo:block font-size="8px"><xsl:value-of select="response/addressMap/data/city"/>&#160;</fo:block>
														</fo:table-cell>
													</fo:table-row>
													<fo:table-row>
														<fo:table-cell>
															<fo:block font-size="8px"><xsl:value-of select="response/addressMap/data/description"></xsl:value-of>&#160;<xsl:value-of select="response/addressMap/data/zip"/></fo:block>
														</fo:table-cell>
													</fo:table-row>
										  		</fo:table-body>
											</fo:table>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
						
					</fo:block>-->
				</fo:flow>	
				
				
			</fo:page-sequence>
		</fo:root>
	</xsl:template>			
	
</xsl:stylesheet>	