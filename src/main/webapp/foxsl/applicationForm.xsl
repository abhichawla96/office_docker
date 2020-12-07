<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo" xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">
	<xsl:output method="xml" indent="no" />
	<xsl:template match="/" name="applicationForm">
		<fo:block>
			<fo:block>
				<fo:table>
					<fo:table-column />
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell>
								<fo:block font-size="12px" font-family="Arial"
									font-weight="bold" text-align="center">PRODUCER APPLICATION FOR
									APPOINTMENT</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>

			<fo:block margin-top="5mm">
				<fo:table border="0.0pt solid black">
					<fo:table-column border="0.0pt solid black"
						column-width="270pt" />
					<fo:table-column border="0.0pt solid black"
						column-width="190pt" />

					<fo:table-body>
						<fo:table-row border="0.0pt solid black" height="5mm">
							<fo:table-cell border="0.0pt solid black">
								<fo:table border="0.0pt solid black">
									<fo:table-column column-width="130pt" />
									<fo:table-column column-width="220pt" />
									<fo:table-body>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"
																	font-weight="bold" text-decoration="underline">General Information
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"></fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>

										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Agency
																	Name : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black" width="1.8in">
																<fo:block font-size="9px" font-family="Arial"
																	>
																	<xsl:value-of select="response/aar_agencyname"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>

										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	IS DBA Registered with DOI : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
															 <fo:block>
															  <xsl:if test="response/isdbaregistered=1">
																<fo:block font-size="9px" font-family="Arial"
																	wrap-option="wrap">
																	Y
																</fo:block>
															 </xsl:if>
															 
															 <xsl:if test="response/isdbaregistered=0">
																<fo:block font-size="9px" font-family="Arial"
																	wrap-option="wrap">
																	N
																</fo:block>
															 </xsl:if>
															 </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>

										</fo:table-row>
										 <fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	W-9 Legal Name : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black" width="1.9in">
																<fo:block font-size="9px" font-family="Arial"
																	wrap-option="wrap">
																	<xsl:value-of select="response/taxpayer_name"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>

										</fo:table-row>
										
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	Lines of Business : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black" height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block>
																 <xsl:if test="response/agency_lob=1">
																<fo:block font-size="9px" font-family="Arial"
																	wrap-option="wrap">
																	Personal Lines
																</fo:block>
															 </xsl:if>
															 <xsl:if test="response/agency_lob2=1">
																<fo:block font-size="9px" font-family="Arial"
																	wrap-option="wrap">
																	Commercial Lines
																</fo:block>
															 </xsl:if>
															 </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>

										</fo:table-row>
										
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">DBA :
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black" width="1.8in">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/dba"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>

										</fo:table-row>
										<xsl:if test="response/isHideISDBA='N'">
											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">IS DBA
																		Registered with DOI : </fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/isdbaregistered_pdf"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>

											</fo:table-row>
										</xsl:if>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Corporate/Parent
																	Agency Name : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black" width="1.8in">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/entity_name"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>

										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Legal
																	Entity Type : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black"
																width="100px">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/legaldescription"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>

										</fo:table-row>


										<xsl:if test="response/legaldescription ='Type Unknown'">
											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial"></fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black" width="1.9in">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/aar_producertype"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>

											</fo:table-row>
										</xsl:if>


										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">FEIN/Tax
																	ID : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_taxid_pdf"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>

										</fo:table-row>
										<xsl:if test="response/isHideTaxPayerName='N'">
											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">Tax
																		Payer Name : </fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial"
																		wrap-option="wrap">
																		<xsl:value-of select="response/taxpayer_name"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>

											</fo:table-row>
										</xsl:if>
										<!-- <fo:table-row border="0.0pt solid black" height="5mm"> <fo:table-cell 
											border="0.0pt solid black"> <fo:table border="0.0pt solid black"> <fo:table-column/> 
											<fo:table-body> <fo:table-row border="0.0pt solid black" height="5mm"> <fo:table-cell 
											border="0.0pt solid black"> <fo:block font-size="9px" font-family="Arial">NPN 
											: </fo:block> </fo:table-cell> </fo:table-row> </fo:table-body> </fo:table> 
											</fo:table-cell> <fo:table-cell border="0.0pt solid black"> <fo:table border="0.0pt 
											solid black"> <fo:table-column/> <fo:table-body> <fo:table-row border="0.0pt 
											solid black" height="5mm"> <fo:table-cell border="0.0pt solid black"> <fo:block 
											font-size="9px" font-family="Arial"><xsl:value-of select="response/npn"></xsl:value-of></fo:block> 
											</fo:table-cell> </fo:table-row> </fo:table-body> </fo:table> </fo:table-cell> 
											</fo:table-row> -->
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	Agency Web Address : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black" width="2in">
																<fo:block font-size="9px" font-family="Arial"
																	wrap-option="wrap">
																	<xsl:value-of select="response/aar_webaddress"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>

										</fo:table-row>
									<!-- 	<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	Date of Origination : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"
																	wrap-option="wrap">
																	<xsl:value-of select="response/agencyDateOf_origination"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>

										</fo:table-row> -->
										<xsl:if test="response/isHideAgencyEstablishedDate='N'">
											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/agencyEstablishedDateLabel"></xsl:value-of>
																		:
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/agencyestablished_date"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<xsl:if test="response/isHideYrsInBusiness='N'">
											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">Years in
																		Business :</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/aar_yrsinbusiness"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>

											</fo:table-row>
										</xsl:if>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Years of
																	Current Ownership : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_expinyears"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>

										</fo:table-row>
										<!-- <fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	Agency License Number : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_agencyLicenseNumber"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>

										</fo:table-row> -->
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	Agency Management System : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/agency_management_system_desc"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>

										</fo:table-row>
										<xsl:if test="contains(response/agency_management_system_desc, 'Other')">
										<!-- <xsl:if test="response/agency_association_pdf ='Other'"> -->
											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial"></fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black" width="2in">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/other_agency_management"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>

											</fo:table-row>
										</xsl:if>
										
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Number of
																	Licensed Producers : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_nooflicproducers"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>

										</fo:table-row>
										<xsl:if test="response/isHideNumberofCSR ='N'">
											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">Numbers
																		of CSRs : </fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/aar_noofliccsr"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>

											</fo:table-row>
										</xsl:if>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Total
																	Number of Employees : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"
																	wrap-option="nowrap">
																	<xsl:value-of select="response/no_of_staff"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>

										</fo:table-row>
										<!-- <fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																 <fo:block font-size="9px" font-family="Arial">
																	Multi-Rater  : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"
																	wrap-option="nowrap">
																	<xsl:value-of select="response/agency_comparativerate_pdf"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>

										</fo:table-row> -->
										<xsl:if test="contains(response/agency_comparativerate_pdf, 'Other')">
										<!-- <xsl:if test="response/agency_association_pdf ='Other'"> -->
											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial"></fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black" width="2in">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/other_agency_comparativerate"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>

											</fo:table-row>
										</xsl:if>
										
										<!-- <fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Agency
																	Association : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/agency_association_pdf"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>

										</fo:table-row> -->
										<!-- <fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Locations
																	of any sub offices : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/other_office_location"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>

										</fo:table-row> -->
										<xsl:if test="contains(response/agency_association_pdf, 'Other')">
										<!-- <xsl:if test="response/agency_association_pdf ='Other'"> -->
											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial"></fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/associatedAgencyName"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>

											</fo:table-row>
										</xsl:if>
										<xsl:if test="response/isHideLOB='N'">
											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">Lines Of
																		Business : </fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/agencyLob"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>

											</fo:table-row>
										</xsl:if>

										

									</fo:table-body>
								</fo:table>
							</fo:table-cell>


							<fo:table-cell border="0.0pt solid black">
								<fo:table border="0.0pt solid black">
									<fo:table-column column-width="80pt" />
									<fo:table-column column-width="140pt" />
									<fo:table-body>
										<fo:table-row border="0.0pt solid black" height="5mm">

											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"
																	font-weight="bold" text-decoration="underline">Business Address
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"></fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Country
																	: </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_countryDescription"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Street
																	Address #1 : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_streetaddress1"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Street
																	Address #2 : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_streetaddress2"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<xsl:if test="response/isHidePOBox='N'">
											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">P.O Box
																		: </fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/aar_poBox"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">City :
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_city"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>


										<xsl:if test="response/legaldescription ='Type Unknown'">
											<fo:table-row border="0.0pt solid black" height="5mm">


												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial"></fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>


										<fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">State :
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_stateDescription"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">County :
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_countyDescription"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Zip Code
																	: </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_zipcode"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Phone :
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-column />
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_phoneno"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
															<fo:table-cell border="0.0pt solid black"
																width="30px">
																<fo:block font-size="9px" font-family="Arial">Extn. :
																</fo:block>
															</fo:table-cell>
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_extn"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<xsl:if test="response/isHideAltPhone='N'">
											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		Alt Phone : </fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-column />
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/aar_altPhone"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell border="0.0pt solid black"
																	width="30px">
																	<fo:block font-size="9px" font-family="Arial">Extn. :
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/aar_altExtn"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<!-- <fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Fax :
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_fax"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row> -->
										<fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Email :
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_email"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"></fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"></fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"></fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"></fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"></fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"></fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<xsl:if test="response/agency_association_pdf ='Other'">
											<fo:table-row border="0.0pt solid black" height="5mm">


												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial"></fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial"></fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>
											<fo:table-row border="0.0pt solid black" height="5mm">


												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial"></fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial"></fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>
																			
										</xsl:if>
										
									</fo:table-body>
								</fo:table>
							</fo:table-cell>








						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>

			<fo:block margin-top="5mm">
				<fo:table border="0.0pt solid black">
					<fo:table-column border="0.0pt solid black"
						column-width="270pt" />
					<fo:table-column border="0.0pt solid black"
						column-width="190pt" />

					<fo:table-body>
						<fo:table-row border="0.0pt solid black" height="5mm">



							<fo:table-cell border="0.0pt solid black">
								<fo:table border="0.0pt solid black">
									<fo:table-column border="0.0pt solid black"
										column-width="130pt" />
									<fo:table-column border="0.0pt solid black"
										column-width="220pt" />
									<fo:table-body>
										<fo:table-row border="0.0pt solid black" height="5mm">

											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"
																	font-weight="bold" text-decoration="underline">Mailing Address
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"></fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Country
																	: </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_countryDescription"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Street
																	Address #1 or PO Box : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black" width ="1.8in">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_streetaddress1_ma"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Street
																	Address #2 : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black" width ="1.8in">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_streetaddress2_ma"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<xsl:if test="response/isHidePOBox='N'">
											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">P.O Box
																		: </fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/aar_poBox_ma"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">City :
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black" width ="1.8in">
																<fo:block font-size="9px" font-family="Arial" >
																	<xsl:value-of select="response/aar_city_ma"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">State :
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_stateDescription_ma"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">County :
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_countyDescription_ma"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Zip Code
																	: </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_zipcode_ma"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<xsl:if test="response/isHideAltPhone='N'">
											<fo:table-row border="0.0pt solid black" height="5mm">


												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">Phone :
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-column />
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/aar_phoneno_ma"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell border="0.0pt solid black"
																	width="30px">
																	<fo:block font-size="9px" font-family="Arial">Extn. :
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/aar_extn_ma"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		Alt Phone : </fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-column />
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/aar_altPhone_ma"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell border="0.0pt solid black"
																	width="30px">
																	<fo:block font-size="9px" font-family="Arial">Extn. :
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/aar_altExtn_ma"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>
											<fo:table-row border="0.0pt solid black" height="5mm">


												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">Fax :
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/aar_fax_ma"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>
											<fo:table-row border="0.0pt solid black" height="5mm">


												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">Email ID
																		: </fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/aar_email_ma"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"></fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"></fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"></fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"></fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"></fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"></fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<xsl:if test="response/agency_association_pdf ='Other'">
											<fo:table-row border="0.0pt solid black" height="5mm">


												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial"></fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial"></fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>
											<fo:table-row border="0.0pt solid black" height="5mm">


												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial"></fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial"></fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
									</fo:table-body>
								</fo:table>
							</fo:table-cell>



							<fo:table-cell border="0.0pt solid black">
								<fo:table border="0.0pt solid black">
									<fo:table-column border="0.0pt solid black"
										column-width="80pt" />
									<fo:table-column border="0.0pt solid black"
										column-width="140pt" />
									<fo:table-body>
										<fo:table-row border="0.0pt solid black" height="5mm">

											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"
																	font-weight="bold" text-decoration="underline">W9 Address
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"></fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Country
																	: </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_countryDescription_w9"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Street
																	Address #1 : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_streetaddress1_w9"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Street
																	Address #2 : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_streetaddress2_w9"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<xsl:if test="response/isHidePOBox='N'">
											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">P.O Box
																		: </fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/aar_poBox_w9"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">City :
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_city_w9"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>




										<fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">State :
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_stateDescription_w9"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">County :
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_countyDescription_w9"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Zip Code
																	: </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/aar_zipcode_w9"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<!-- <fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	1099 Type  : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/type1099_desscription"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row> -->
										
										<!-- <fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">W9 Legal
																	Name : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black" width="1.9in">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="response/taxpayer_name"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row> -->

										<xsl:if test="response/isHideAltPhone='N'">
											<fo:table-row border="0.0pt solid black" height="5mm">


												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">Phone :
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-column />
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/aar_phoneno_w9"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell border="0.0pt solid black"
																	width="30px">
																	<fo:block font-size="9px" font-family="Arial">Extn. :
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/aar_extn_w9"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		Alt Phone : </fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-column />
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/aar_altPhone_w9"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell border="0.0pt solid black"
																	width="30px">
																	<fo:block font-size="9px" font-family="Arial">Extn. :
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/aar_altExtn_w9"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>
											<fo:table-row border="0.0pt solid black" height="5mm">


												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">Fax :
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/aar_fax_w9"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>
											<fo:table-row border="0.0pt solid black" height="5mm">


												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">Email ID
																		: </fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="response/aar_email_w9"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>

										</xsl:if>
										<fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"></fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"></fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"></fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"></fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">


											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"></fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"></fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<xsl:if test="response/agency_association_pdf ='Other'">
											<fo:table-row border="0.0pt solid black" height="5mm">


												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial"></fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial"></fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>
											<fo:table-row border="0.0pt solid black" height="5mm">


												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial"></fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial"></fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>
									</fo:table-body>
								</fo:table>
							</fo:table-cell>




						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>



			<!-- <fo:block>
				<fo:table border="0.0pt solid black">
					<fo:table-column />
					<fo:table-body>
						<fo:table-row height="5mm">
							<fo:table-cell>
								<fo:block font-size="9px" font-family="Arial"
									font-weight="bold" text-decoration="underline">List of States selected for
									appointment/authorization</fo:block>
							</fo:table-cell>
						</fo:table-row>
						<fo:table-row height="5mm">
							<fo:table-cell>
								<fo:block font-family="Arial" font-size="8px">
									<xsl:value-of select="response/states"></xsl:value-of>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block> -->



			<fo:block break-after="page"/>
			<fo:block margin-top="5mm">
				<fo:table border="0.0pt solid black">
					<fo:table-column />
					<fo:table-column />
					<fo:table-body>
						<fo:table-row height="5mm">
							<fo:table-cell width="150pt">
								<fo:block font-size="9px" font-family="Arial"
									font-weight="bold">
									<fo:inline>Agency Background Questions</fo:inline>
								</fo:block>
							</fo:table-cell>
							<fo:table-cell>
								<fo:block></fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>
			<fo:block margin-top="5mm">
				<fo:table border="1.0pt solid black">
					<fo:table-column border="1.0pt solid black" />
					<fo:table-column border="1.0pt solid black" />
					<fo:table-body>
						<xsl:for-each select="response/agencyquestions_list_1/data">
							<fo:table-row border="1.0pt solid black">
								<fo:table-cell width="480pt" 
									padding-start="5px" padding-top="3px" padding-bottom="3px">
									<fo:block font-size="8px" font-family="Arial">
										<xsl:value-of select="position()"></xsl:value-of>
										.
										<xsl:value-of select="question" />
									</fo:block>
								</fo:table-cell>
								<fo:table-cell 
									padding-top="3px" padding-bottom="3px">
									<fo:block font-size="8px" font-family="Arial"
										text-align="center">
										<xsl:choose>
											<xsl:when test="response=1">
												Y
											</xsl:when>
											<xsl:when test="response=0">
												N
											</xsl:when>
										</xsl:choose>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							
						<xsl:if test="showComment ='Y'">
							<fo:table-row border="1.0pt solid black" >
								
								<fo:table-cell 
									padding-top="3px" padding-bottom="3px" width="460pt" number-columns-spanned="2" start-indent="14pt">
									<fo:block font-size="9px" font-family="Arial">
										<xsl:value-of select="comment"></xsl:value-of>
										
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</xsl:if>
					
						</xsl:for-each>


					</fo:table-body>
				</fo:table>
			</fo:block>
			
			
			
			<fo:block margin-top="5mm">
			<fo:block font-size="9px" font-family="Arial" font-weight="bold">E&amp;O Declaration Form: </fo:block>
			<fo:table border="1.0pt solid black" margin-top="1mm">
				<fo:table-column border="1.0pt solid black"
					 />
				<fo:table-column border="1.0pt solid black"
					 />
				<fo:table-column border="1.0pt solid black"
					 />
				<fo:table-column border="1.0pt solid black"
					 />
				<fo:table-column border="1.0pt solid black"
					 />
				<fo:table-column border="1.0pt solid black"
					 />

				<fo:table-body>
					<fo:table-row border="1.0pt solid black">
						<fo:table-cell border="0.0pt solid black"
							>
							<fo:block font-size="9px" font-family="Arial">E&amp;O Carrier Name : </fo:block>
						</fo:table-cell>
						<fo:table-cell border="1.0pt solid black"
							>
							<fo:block font-size="9px" font-family="Arial">
								<xsl:value-of select="response/carrier_other"></xsl:value-of>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell border="1.0pt solid black"
							>
							<fo:block font-size="9px" font-family="Arial">Policy
								Limit : </fo:block>
						</fo:table-cell>
						<fo:table-cell border="1.0pt solid black"
							>
							<fo:block font-size="9px" font-family="Arial">
								<xsl:value-of select="response/policy_limit"></xsl:value-of>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell border="1.0pt solid black"
							>
							<fo:block font-size="9px" font-family="Arial">Effective Date : </fo:block>
							<fo:block font-size="9px" font-family="Arial">Expiration Date : </fo:block>
						</fo:table-cell>
						<fo:table-cell border="1.0pt solid black"
							>
							<fo:block font-size="9px" font-family="Arial">
								<xsl:value-of select="response/effective_date"></xsl:value-of>
							</fo:block>
							
							<fo:block font-size="9px" font-family="Arial">
								<xsl:value-of select="response/expiration_date"></xsl:value-of>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell border="1.0pt solid black"
							padding-start="5px" padding-top="3px" padding-bottom="3px"
							number-columns-spanned="5">
							<fo:block font-size="9px" font-family="Arial"> Have any E&amp;O claims been
								made during the past five years against the agency, agency partners,
								officers or any members of the staff?
							</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" padding-start="5px"
							padding-top="3px" padding-bottom="3px" border="1.0pt solid black">
							<fo:block font-size="9px" font-family="Arial">
								<xsl:value-of select="response/eo_claims_pdf"></xsl:value-of>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<xsl:if test="response/eo_claims ='Yes'">
						<fo:table-row>
							<fo:table-cell border="1.0pt solid black"
								padding-start="5px" padding-top="3px" padding-bottom="3px">
								<fo:block font-size="9px" font-family="Arial">Comment:
								</fo:block>
							</fo:table-cell>
							<fo:table-cell border="1.0pt solid black"
								padding-start="5px" padding-top="3px" padding-bottom="3px"
								number-columns-spanned="5" width="500px">
								<fo:block font-size="9px" font-family="Arial">
									<xsl:value-of select="response/eoclaims_comments"></xsl:value-of>
									<!--<xsl:call-template name="intersperse-with-zero-spaces"> <xsl:with-param 
										name="str" select="response/eoclaims_comments"/></xsl:call-template> -->
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</xsl:if>
				</fo:table-body>
			</fo:table>

		</fo:block>
		
		<xsl:if test="response/agencyAppointmentstep1_CyberLiability_section_security ='Y'">
					<fo:block margin-top="5mm">
			<fo:block font-size="9px" font-family="Arial" font-weight="bold">Cyber Liability Declaration Form: </fo:block>
			<fo:table border="1.0pt solid black" margin-top="1mm">
				<fo:table-column border="1.0pt solid black"
					 />
				<fo:table-column border="1.0pt solid black"
					 />
				<fo:table-column border="1.0pt solid black"
					 />
				<fo:table-column border="1.0pt solid black"
					 />
				<fo:table-column border="1.0pt solid black"
					 />
				<fo:table-column border="1.0pt solid black"
					 />

				<fo:table-body>
					<fo:table-row border="1.0pt solid black">
						<fo:table-cell border="0.0pt solid black"
							>
							<fo:block font-size="9px" font-family="Arial">Cyber Liability Carrier Name : </fo:block>
						</fo:table-cell>
						<fo:table-cell border="1.0pt solid black"
							>
							<fo:block font-size="9px" font-family="Arial">
								<xsl:value-of select="response/cyber_liability_carrier_other"></xsl:value-of>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell border="1.0pt solid black"
							>
							<fo:block font-size="9px" font-family="Arial">Policy
								Limit : </fo:block>
						</fo:table-cell>
						<fo:table-cell border="1.0pt solid black"
							>
							<fo:block font-size="9px" font-family="Arial">
								<xsl:value-of select="response/cyber_liability_policy_limit"></xsl:value-of>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell border="1.0pt solid black"
							>
							<fo:block font-size="9px" font-family="Arial">Effective Date : </fo:block>
							<fo:block font-size="9px" font-family="Arial">Expiration Date : </fo:block>
						</fo:table-cell>
						<fo:table-cell border="1.0pt solid black"
							>
							<fo:block font-size="9px" font-family="Arial">
								<xsl:value-of select="response/cyber_liability_effective_date"></xsl:value-of>
							</fo:block>
							
							<fo:block font-size="9px" font-family="Arial">
								<xsl:value-of select="response/cyber_liability_expiration_date"></xsl:value-of>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell border="1.0pt solid black"
							padding-start="5px" padding-top="3px" padding-bottom="3px"
							number-columns-spanned="5">
							<fo:block font-size="9px" font-family="Arial"> Have any Cyber Liability claims been
								made during the past five years against the agency, agency partners,
								officers or any members of the staff?
							</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" padding-start="5px"
							padding-top="3px" padding-bottom="3px" border="1.0pt solid black">
							<fo:block font-size="9px" font-family="Arial">
								<xsl:value-of select="response/cyber_liability_claims"></xsl:value-of>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<xsl:if test="response/cyber_liability_claims ='Yes'">
						<fo:table-row>
							<fo:table-cell border="1.0pt solid black"
								padding-start="5px" padding-top="3px" padding-bottom="3px">
								<fo:block font-size="9px" font-family="Arial">Comment:
								</fo:block>
							</fo:table-cell>
							<fo:table-cell border="1.0pt solid black"
								padding-start="5px" padding-top="3px" padding-bottom="3px"
								number-columns-spanned="5" width="500px">
								<fo:block font-size="9px" font-family="Arial">
									<xsl:value-of select="response/cyber_liability_claims_comments"></xsl:value-of>
									<!--<xsl:call-template name="intersperse-with-zero-spaces"> <xsl:with-param 
										name="str" select="response/eoclaims_comments"/></xsl:call-template> -->
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</xsl:if>
				</fo:table-body>
			</fo:table>

		</fo:block>
		</xsl:if>
		
				<xsl:if test="response/adminBrokerageDetailsStep1_social_media_site_security ='Y'">
				<fo:block margin-top="5mm">
				<fo:table>
					<fo:table-column border="0.0pt solid black" />
					<fo:table-column border="0.0pt solid black" />
					<fo:table-column border="0.0pt solid black" />
					<fo:table-column border="0.0pt solid black" />
					<fo:table-body>
						<fo:table-row height="5mm">
							<fo:table-cell width="130pt">
								<fo:block font-size="9px" font-family="Arial"
									font-weight="bold" text-decoration="underline">Social Media Info :</fo:block>
							</fo:table-cell>
							<fo:table-cell number-columns-spanned="3">
								<fo:block></fo:block>
							</fo:table-cell>
						</fo:table-row>

						<fo:table-row border="0.0pt solid black" height="5mm">
							<fo:table-cell border="0.0pt solid black">
								<fo:table border="1.0pt solid black">
									<fo:table-column />
									<fo:table-column />
									<fo:table-column />
									<fo:table-column />
									<fo:table-column />
									<fo:table-column />
									<fo:table-body>
										<fo:table-row border="1.0pt solid black">
										<fo:table-cell width="248pt" border="1.0pt solid black"
												padding-start="2px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial"
													font-weight="bold">Socail Media Site</fo:block>
											</fo:table-cell>
											<fo:table-cell width="248pt" border="1.0pt solid black"
												padding-start="2px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial"
													font-weight="bold">Social Media User ID</fo:block>
											</fo:table-cell>
											
										</fo:table-row>
										<xsl:for-each select="response/social_media_list_FOR_PDF/data">
											<fo:table-row border="1.0pt solid black">
											<fo:table-cell width="248pt" border="1.0pt solid black"
													padding-start="2px" padding-top="3px" padding-bottom="3px">
													<fo:block font-size="8px" font-family="Arial">
														<xsl:value-of select="social_media_site_desc" />
													</fo:block>
												</fo:table-cell>
												<fo:table-cell width="248pt" border="1.0pt solid black"
													padding-start="2px" padding-top="3px" padding-bottom="3px">
													<fo:block font-size="8px" font-family="Arial">
														<xsl:value-of select="social_media_userid" />
													</fo:block>
												</fo:table-cell>
												
											</fo:table-row>
											
										</xsl:for-each>
									</fo:table-body>
								</fo:table>
							</fo:table-cell>
							<fo:table-cell number-columns-spanned="3">
								<fo:block></fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>		
			</xsl:if>
			
			<fo:block break-after="page" />
			
			<fo:block margin-top="5mm">
				<fo:table>
					<fo:table-column border="0.0pt solid black" />
					<fo:table-column border="0.0pt solid black" />
					<fo:table-column border="0.0pt solid black" />
					<fo:table-column border="0.0pt solid black" />
					<fo:table-body>
						<fo:table-row height="5mm">
							<fo:table-cell width="130pt">
								<fo:block font-size="9px" font-family="Arial"
									font-weight="bold" text-decoration="underline">Bank Reference</fo:block>
							</fo:table-cell>
							<fo:table-cell number-columns-spanned="3">
								<fo:block></fo:block>
							</fo:table-cell>
						</fo:table-row>

						<fo:table-row border="0.0pt solid black" height="5mm">
							<fo:table-cell border="0.0pt solid black">
								<fo:table border="1.0pt solid black">
									<fo:table-column />
									<fo:table-column />
									<fo:table-column />
									<fo:table-column />
									<fo:table-column />
									<fo:table-column />
									<fo:table-body>
										<fo:table-row border="1.0pt solid black">
											<fo:table-cell width="143pt" border="1.0pt solid black"
												padding-start="2px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial"
													font-weight="bold">Bank Name</fo:block>
											</fo:table-cell>
											<fo:table-cell width="120pt" border="1.0pt solid black"
												padding-start="2px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial"
													font-weight="bold">Bank Account Number (Last 4 digits)</fo:block>
											</fo:table-cell>
											<fo:table-cell width="113pt" border="1.0pt solid black"
												padding-start="2px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial"
													font-weight="bold">Account Type</fo:block>
											</fo:table-cell>
											<fo:table-cell width="122pt" border="1.0pt solid black"
												padding-start="2px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial"
													font-weight="bold">Account Use Code</fo:block>
											</fo:table-cell>
										</fo:table-row>
										<xsl:for-each select="response/agencybankinformation_list_1/data">
											<fo:table-row border="1.0pt solid black">
												<fo:table-cell width="90pt" border="1.0pt solid black"
													padding-start="2px" padding-top="3px" padding-bottom="3px">
													<fo:block font-size="8px" font-family="Arial">
														<xsl:value-of select="bank_name" />
													</fo:block>
												</fo:table-cell>
												<fo:table-cell width="100pt" border="1.0pt solid black"
													padding-start="2px" padding-top="3px" padding-bottom="3px">
													<fo:block font-size="8px" font-family="Arial">
														<xsl:value-of select="bank_account_last_four_digits" />
													</fo:block>
												</fo:table-cell>
												<fo:table-cell width="60pt" border="1.0pt solid black"
													padding-start="2px" padding-top="3px" padding-bottom="3px">
													<fo:block font-size="8px" font-family="Arial">
														<xsl:value-of select="bank_account_type_desc" />
													</fo:block>
												</fo:table-cell>
												<fo:table-cell width="60pt" border="1.0pt solid black"
													padding-start="2px" padding-top="3px" padding-bottom="3px">
													<fo:block font-size="8px" font-family="Arial">
														<xsl:value-of select="bank_account_type_usage_desc" />
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
											<xsl:if test="bank_comments! = null or bank_comments!='' ">
												<fo:table-row>
													<fo:table-cell border="0.0pt solid black"
														padding-start="5px" padding-top="3px" padding-bottom="3px">
														<fo:block font-size="9px" font-family="Arial">Comment:
														</fo:block>
													</fo:table-cell>
													<fo:table-cell border="0.0pt solid black"
														padding-start="5px" padding-top="3px" padding-bottom="3px"
														number-columns-spanned="5" width="500px">
														<fo:block font-size="9px" font-family="Arial">
															<xsl:value-of select="bank_comments" />
															<!--<xsl:call-template name="intersperse-with-zero-spaces"> 
																<xsl:with-param name="str" select="bank_comments"/></xsl:call-template> -->
														</fo:block>
													</fo:table-cell>
												</fo:table-row>
											</xsl:if>
										</xsl:for-each>
									</fo:table-body>
								</fo:table>
							</fo:table-cell>
							<fo:table-cell number-columns-spanned="3">
								<fo:block></fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>

	
		</fo:block>

		<xsl:for-each select="response/agent_list_1/data">
			<fo:block break-after="page" />
			<fo:block>
				<fo:table>
					<fo:table-column />
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell>
								<fo:block font-size="12px" font-family="Arial"
									font-weight="bold" text-align="center">AGENCY/BROKERAGE PRINCIPAL/KEY
									CONTACT/PRODUCER INFORMATION</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>

			<fo:block margin-top="5mm">
				<fo:table border="0.0pt solid black">
					<fo:table-column border="0.0pt solid black"
						column-width="270pt" />
					<fo:table-column border="0.0pt solid black"
						column-width="190pt" />

					<fo:table-body>
						<fo:table-row border="0.0pt solid black" height="5mm">
							<fo:table-cell border="0.0pt solid black">
								<fo:table border="0.0pt solid black">
									<fo:table-column column-width="100pt" />
									<fo:table-column column-width="170pt" />
									<fo:table-body>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"
																	font-weight="bold" text-decoration="underline">Contact Details
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"></fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	Contact Type :</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="contacttypedescription"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<!-- <fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Salutation
																	:</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="prefixdescription"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row> -->

										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">First
																	Name: </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial" width= "2in">
																	<xsl:value-of select="firstname"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Middle Name
																	: </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black" width= "2in">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="middlename"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>

										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Last Name
																	: </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black" width= "2in">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="lastname"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										
										
										

										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Suffix :
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black" width= "2in">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="suffix"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
									<xsl:if test="principal_contacttype != 'P' ">
										<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">Phone :
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-column />
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="phoneno"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell border="0.0pt solid black"
																	width="30px">
																	<fo:block font-size="9px" font-family="Arial">Extn. :
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="extn"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>
											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">Fax :
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="fax"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>
											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">Mobile :
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="mobile"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>
										</xsl:if>

										<!-- <fo:table-row border="0.0pt solid black" height="5mm" > <fo:table-cell 
											border="0.0pt solid black"> <fo:table border="0.0pt solid black"> <fo:table-column/> 
											<fo:table-body> <fo:table-row border="0.0pt solid black" height="5mm"> <fo:table-cell 
											border="0.0pt solid black"> <fo:block font-size="9px" font-family="Arial"><xsl:value-of 
											select="nickNameLabel"></xsl:value-of> : </fo:block> </fo:table-cell> </fo:table-row> 
											</fo:table-body> </fo:table> </fo:table-cell> <fo:table-cell border="0.0pt 
											solid black"> <fo:table border="0.0pt solid black"> <fo:table-column/> <fo:table-body> 
											<fo:table-row border="0.0pt solid black" height="5mm"> <fo:table-cell border="0.0pt 
											solid black"> <fo:block font-size="9px" font-family="Arial"><xsl:value-of 
											select="maiden_name"></xsl:value-of></fo:block> </fo:table-cell> </fo:table-row> 
											</fo:table-body> </fo:table> </fo:table-cell> </fo:table-row> -->
									<xsl:if test="principal_contacttype='P'">
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Position/Title
																	: </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="position"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
									</xsl:if>
									<xsl:if test="principal_contacttype='LC' or principal_contacttype='P' ">
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Producer
																	Type : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="producertype"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
										
										 
											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">National
																		Producer Number : </fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black" >
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="npn"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">Social
																		Security Number :</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="ssn_num"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>
											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		License Number :</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="license_number"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>
											
											
										

										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Date of
																	Birth : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial"
																	wrap-option="wrap">
																	<xsl:value-of select="dob"></xsl:value-of>
																	
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
									</xsl:if>
									<xsl:if test="principal_contacttype='P'">
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Ownership
																	: </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="ownership" />
																	<xsl:if test="ownership!=null or ownership!='' ">
																		%
																	</xsl:if>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
									</xsl:if>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">Email
																	ID : </fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.0pt solid black">
													<fo:table-column />
													<fo:table-column />
													<fo:table-column />
													<fo:table-body>
														<fo:table-row border="0.0pt solid black"
															height="5mm">
															<fo:table-cell border="0.0pt solid black">
																<fo:block font-size="9px" font-family="Arial">
																	<xsl:value-of select="e_mail"></xsl:value-of>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>


									</fo:table-body>
								</fo:table>
							</fo:table-cell>

							<xsl:if test="principal_contacttype='P'">
								<fo:table-cell border="0.0pt solid black">
									<fo:table border="0.0pt solid black">
										<fo:table-column border="0.0pt solid black"
											column-width="100pt" />
										<fo:table-column border="0.0pt solid black"
											column-width="160pt" />
										<fo:table-body>


											<fo:table-row border="0.0pt solid black" height="5mm">

												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial"
																		font-weight="bold" text-decoration="underline">Residence Address </fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial"></fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		Country : </fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black" width= "2in">
																	<fo:block font-size="9px" font-family="Arial" >
																		<xsl:value-of select="countrydescription"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black" >
																	<fo:block font-size="9px" font-family="Arial">Street
																		Address #1 : </fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black" width= "2in">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="addressline1"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">Street
																		Address #2 : </fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black" width= "2in">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="addressline2"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">City :
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black" width= "2in">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="city"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">State :
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="statedescription"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>



											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">County :
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="countydescription"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>



											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">Zip Code
																		: </fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="zipcode"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">Phone :
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-column />
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="phoneno"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell border="0.0pt solid black"
																	width="30px">
																	<fo:block font-size="9px" font-family="Arial">Extn. :
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="extn"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row border="0.0pt solid black" height="5mm">
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">Fax :
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
												<fo:table-cell border="0.0pt solid black">
													<fo:table border="0.0pt solid black">
														<fo:table-column />
														<fo:table-body>
															<fo:table-row border="0.0pt solid black"
																height="5mm">
																<fo:table-cell border="0.0pt solid black">
																	<fo:block font-size="9px" font-family="Arial">
																		<xsl:value-of select="fax"></xsl:value-of>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</fo:table-body>
													</fo:table>
												</fo:table-cell>
											</fo:table-row>

										</fo:table-body>
									</fo:table>
								</fo:table-cell>
							</xsl:if>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>

			<xsl:if test="principal_contacttype='P'">


			<fo:block margin-top="5mm">
				<fo:table border="0.0pt solid black">
					<fo:table-column />
					<fo:table-column />
					<fo:table-body>
						<fo:table-row height="5mm">
							<fo:table-cell width="150pt">
								<fo:block font-size="9px" font-family="Arial"
									font-weight="bold">
									<fo:inline>Agent Background Questions</fo:inline>
								</fo:block>
							</fo:table-cell>
							<fo:table-cell>
								<fo:block></fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>

			<fo:block margin-top="5mm">
				<fo:table border="1.0pt solid black">
					<fo:table-column />
					<fo:table-column />
					<fo:table-body>
						<xsl:for-each select="agentquestions_list_1/data">
							<fo:table-row border="1.0pt solid black">
								<fo:table-cell width="480pt" border="1.0pt solid black"
									padding-start="5px" padding-top="3px" padding-bottom="3px">
									<fo:block font-size="8px" font-family="Arial">
										<xsl:value-of select="position()"></xsl:value-of>
										.
										<xsl:value-of select="question" />
									</fo:block>
								</fo:table-cell>
								<fo:table-cell border="1.0pt solid black"
									padding-top="3px" padding-bottom="3px">
									<fo:block font-size="8px" font-family="Arial"
										text-align="center">
										<xsl:choose>
											<xsl:when test="response=1">
												Y
											</xsl:when>
											<xsl:when test="response=0">
												N
											</xsl:when>
										</xsl:choose>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<xsl:if test="response=1">
								<fo:table-row border="1.0pt solid black">
									<fo:table-cell border="1.0pt solid black" width="300pt"
										padding-start="5px" padding-top="3px" padding-bottom="3px"
										number-columns-spanned="2">
										<fo:block font-size="8px" font-family="Arial">
											Comments :
											<xsl:value-of select="comment" />
											<!--<xsl:call-template name="intersperse-with-zero-spaces"> <xsl:with-param 
												name="str" select="comment"/></xsl:call-template> -->
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</xsl:if>
						</xsl:for-each>
					</fo:table-body>
				</fo:table>
			</fo:block>
		</xsl:if>
		
			
			<!-- <fo:block margin-top="5mm">
				<fo:table>
					<fo:table-column border="0.0pt solid black" />
					<fo:table-column border="0.0pt solid black" />
					<fo:table-column border="0.0pt solid black" />
					<fo:table-column border="0.0pt solid black" />
					<fo:table-body>
						<fo:table-row height="5mm">
							<fo:table-cell width="130pt">
								<fo:block font-size="9px" font-family="Arial"
									font-weight="bold" text-decoration="underline">Beneficiary Details</fo:block>
							</fo:table-cell>
							<fo:table-cell number-columns-spanned="3">
								<fo:block></fo:block>
							</fo:table-cell>
						</fo:table-row>

						<fo:table-row border="0.0pt solid black" height="5mm">
							<fo:table-cell border="0.0pt solid black">
								<fo:table border="1.0pt solid black">
									<fo:table-column />
									<fo:table-column />
									<fo:table-column />
									<fo:table-column />
									<fo:table-column />
									<fo:table-column />
									<fo:table-body>
										<fo:table-row border="1.0pt solid black">
										<fo:table-cell width="83pt" border="1.0pt solid black"
												padding-start="2px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial"
													font-weight="bold">Beneficiary Type</fo:block>
											</fo:table-cell>
											<fo:table-cell width="100pt" border="1.0pt solid black"
												padding-start="2px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial"
													font-weight="bold">First Name</fo:block>
											</fo:table-cell>
											<fo:table-cell width="100pt" border="1.0pt solid black"
												padding-start="2px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial"
													font-weight="bold">Middle Name</fo:block>
											</fo:table-cell>
											<fo:table-cell width="93pt" border="1.0pt solid black"
												padding-start="2px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial"
													font-weight="bold">Last Name</fo:block>
											</fo:table-cell>
											<fo:table-cell width="80pt" border="1.0pt solid black"
												padding-start="2px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial"
													font-weight="bold">Date of Birth</fo:block>
											</fo:table-cell>
											<fo:table-cell width="40pt" border="1.0pt solid black"
												padding-start="2px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial"
													font-weight="bold">Gender</fo:block>
											</fo:table-cell>
										</fo:table-row>
										<xsl:for-each select="beneficiary_list_xsl/data">
											<fo:table-row border="1.0pt solid black">
											<fo:table-cell width="90pt" border="1.0pt solid black"
													padding-start="2px" padding-top="3px" padding-bottom="3px">
													<fo:block font-size="8px" font-family="Arial">
														<xsl:value-of select="beneficiary_type_desc" />
													</fo:block>
												</fo:table-cell>
												<fo:table-cell width="90pt" border="1.0pt solid black"
													padding-start="2px" padding-top="3px" padding-bottom="3px">
													<fo:block font-size="8px" font-family="Arial">
														<xsl:value-of select="beneficiary_first_name" />
													</fo:block>
												</fo:table-cell>
												<fo:table-cell width="100pt" border="1.0pt solid black"
													padding-start="2px" padding-top="3px" padding-bottom="3px">
													<fo:block font-size="8px" font-family="Arial">
														<xsl:value-of select="beneficiary_middle_name" />
													</fo:block>
												</fo:table-cell>
												<fo:table-cell width="60pt" border="1.0pt solid black"
													padding-start="2px" padding-top="3px" padding-bottom="3px">
													<fo:block font-size="8px" font-family="Arial">
														<xsl:value-of select="beneficiary_last_name" />
													</fo:block>
												</fo:table-cell>
												<fo:table-cell width="70pt" border="1.0pt solid black"
													padding-start="2px" padding-top="3px" padding-bottom="3px">
													<fo:block font-size="8px" font-family="Arial">
														<xsl:value-of select="beneficiary_dob" />
													</fo:block>
												</fo:table-cell>
												<fo:table-cell width="50pt" border="1.0pt solid black"
													padding-start="2px" padding-top="3px" padding-bottom="3px">
													<fo:block font-size="8px" font-family="Arial">
														<xsl:value-of select="beneficiary_gender_desc" />
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
											
										</xsl:for-each>
									</fo:table-body>
								</fo:table>
							</fo:table-cell>
							<fo:table-cell number-columns-spanned="3">
								<fo:block></fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block> -->		
		</xsl:for-each>
		
		
		
		<xsl:if test="response/isHideAdminBrokerageDetailsStep7= 'N' ">
			<fo:block break-after="page"></fo:block>
			<fo:block margin-top="5mm" font-size="12px" font-family="Arial"
				font-weight="bold" text-align="center"> AGENCY CURRENT OPERATIONS</fo:block>
			<fo:block margin-top="5mm">
				<fo:table border="0.0pt solid black">
					<fo:table-column border="0.0pt solid black"
						column-width="130pt" />
					<fo:table-column border="0.0pt solid black"
						column-width="150pt" />
					<fo:table-column border="0.0pt solid black"
						column-width="80pt" />
					<fo:table-column border="0.0pt solid black" />
					<fo:table-body>

						<fo:table-row border="0.0pt solid black" height="5mm">
							<fo:table-cell border="0.0pt solid black">
								<fo:table border="0.0pt solid black">
									<fo:table-column />
									<fo:table-body>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:block font-size="9px" font-family="Arial">
													Number of Locations : </fo:block>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-body>
								</fo:table>
							</fo:table-cell>
							<fo:table-cell border="0.0pt solid black">
								<fo:table border="0.0pt solid black">
									<fo:table-column />
									<fo:table-body>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:block font-size="9px" font-family="Arial">
													<xsl:value-of select="response/market_num_locations"></xsl:value-of>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-body>
								</fo:table>
							</fo:table-cell>
							<fo:table-cell border="0.0pt solid black">
								<fo:table border="0.0pt solid black">
									<fo:table-column />
									<fo:table-body>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black"
												width="200px">
												<fo:block font-size="9px" font-family="Arial">Total
													Written Premium : </fo:block>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-body>
								</fo:table>
							</fo:table-cell>
							<fo:table-cell border="0.0pt solid black">
								<fo:table border="0.0pt solid black">
									<fo:table-column />
									<fo:table-body>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black"
												width="60px" padding-start="10px">
												<fo:block font-size="9px" font-family="Arial">
													<xsl:value-of select="response/total_writtenpremium"></xsl:value-of>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-body>
								</fo:table>
							</fo:table-cell>
						</fo:table-row>
						<fo:table-row border="0.0pt solid black" height="5mm">
							<fo:table-cell border="0.0pt solid black">
								<fo:table border="0.0pt solid black">
									<fo:table-column />
									<fo:table-body>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black"
												width="200px">
												<fo:block font-size="9px" font-family="Arial"> Total
													premium for all P &amp; C : </fo:block>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-body>
								</fo:table>
							</fo:table-cell>
							<fo:table-cell border="0.0pt solid black">
								<fo:table border="0.0pt solid black">
									<fo:table-column />
									<fo:table-body>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:block font-size="9px" font-family="Arial">
													<xsl:value-of select="response/total_premium_PC"></xsl:value-of>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-body>
								</fo:table>
							</fo:table-cell>
							<fo:table-cell border="0.0pt solid black">
								<fo:table border="0.0pt solid black">
									<fo:table-column />
									<fo:table-body>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black"
												width="100px">
												<fo:block font-size="9px" font-family="Arial"
													width="100px">Total premium for all workers compensation :
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-body>
								</fo:table>
							</fo:table-cell>
							<fo:table-cell border="0.0pt solid black">
								<fo:table border="0.0pt solid black">
									<fo:table-column />
									<fo:table-body>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black"
												padding-start="10px">
												<fo:block font-size="9px" font-family="Arial">
													<xsl:value-of select="response/total_premium_compensation"></xsl:value-of>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-body>
								</fo:table>
							</fo:table-cell>
						</fo:table-row>
						<fo:table-row border="0.0pt solid black" height="5mm">
							<fo:table-cell border="0.0pt solid black">
								<fo:table border="0.0pt solid black">
									<fo:table-column />
									<fo:table-body>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:block font-size="9px" font-family="Arial"
													width="150px">Number of workers compensation policies in force :
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-body>
								</fo:table>
							</fo:table-cell>
							<fo:table-cell border="0.0pt solid black">
								<fo:table border="0.0pt solid black">
									<fo:table-column />
									<fo:table-body>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:block font-size="9px" font-family="Arial">
													<xsl:value-of select="response/no_workers_policies"></xsl:value-of>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-body>
								</fo:table>
							</fo:table-cell>
							<fo:table-cell border="0.0pt solid black">
								<fo:table border="0.0pt solid black">
									<fo:table-column />
									<fo:table-body>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:block font-size="9px" font-family="Arial"></fo:block>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-body>
								</fo:table>
							</fo:table-cell>
							<fo:table-cell border="0.0pt solid black">
								<fo:table border="0.0pt solid black">
									<fo:table-column />
									<fo:table-body>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:block font-size="9px" font-family="Arial">
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-body>
								</fo:table>
							</fo:table-cell>
						</fo:table-row>
						<fo:table-row border="0.0pt solid black">
							<fo:table-cell width="100pt" border="0.0pt solid black"
								padding-start="5px" padding-top="3px" padding-bottom="3px">
								<fo:block font-size="8px" font-family="Arial"></fo:block>
							</fo:table-cell>
						</fo:table-row>
						<fo:table-row height="5mm" margin-top="10mm">
							<fo:table-cell border="0.0pt solid black">
								<fo:table border="0.0pt solid black">
									<fo:table-column />
									<fo:table-body>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:block font-size="9px" font-family="Arial"
													width="100px">Line of Business Distribution :  </fo:block>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.1pt solid black">
													<fo:table-column />
													<fo:table-column />
													<fo:table-column />
													<fo:table-column />
													<fo:table-column />
													<fo:table-column />
													<fo:table-body>
														<xsl:for-each
															select="response/agencyBusinessDistributionStep7_list_mom_1/data">
															<fo:table-row border="0.1pt solid black">
																<fo:table-cell width="100pt" border="0.1pt solid black"
																	padding-start="5px" padding-top="3px" padding-bottom="3px">
																	<fo:block font-size="8px" font-family="Arial">
																		<xsl:value-of select="lob_description1" />
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell width="80pt" border="0.1pt solid black"
																	padding-start="5px" padding-top="3px" padding-bottom="3px">
																	<fo:block font-size="8px" font-family="Arial">
																		<xsl:value-of select="premium_percentage1" />
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell width="90pt" border="0.1pt solid black"
																	padding-start="5px" padding-top="3px" padding-bottom="3px">
																	<fo:block font-size="8px" font-family="Arial">
																		<xsl:value-of select="lob_description2" />
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell width="80pt" border="0.1pt solid black"
																	padding-start="5px" padding-top="3px" padding-bottom="3px">
																	<fo:block font-size="8px" font-family="Arial">
																		<xsl:value-of select="premium_percentage2" />
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell width="90pt" border="0.1pt solid black"
																	padding-start="5px" padding-top="3px" padding-bottom="3px">
																	<fo:block font-size="8px" font-family="Arial">
																		<xsl:value-of select="lob_description3" />
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell width="80pt" border="0.1pt solid black"
																	padding-start="5px" padding-top="3px" padding-bottom="3px">
																	<fo:block font-size="8px" font-family="Arial">
																		<xsl:value-of select="premium_percentage3" />
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</xsl:for-each>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-body>
								</fo:table>
							</fo:table-cell>
						</fo:table-row>
						<fo:table-row border="0.0pt solid black">
							<fo:table-cell width="100pt" border="0.0pt solid black"
								padding-start="5px" padding-top="3px" padding-bottom="3px">
								<fo:block font-size="8px" font-family="Arial"></fo:block>
							</fo:table-cell>
						</fo:table-row>
						<fo:table-row height="5mm" margin-top="10mm">
							<fo:table-cell border="0.0pt solid black">
								<fo:table border="0.0pt solid black">
									<fo:table-column />
									<fo:table-body>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black"
												width="400px">
												<fo:block font-size="9px" font-family="Arial">Types
													of account by premium volume : </fo:block>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.1pt solid black">
													<fo:table-column />
													<fo:table-column />
													<fo:table-column />
													<fo:table-column />
													<fo:table-column />
													<fo:table-column />
													<fo:table-body>
														<xsl:for-each
															select="response/agencyPremiumVolumeStep7_list_mom_1/data">
															<fo:table-row border="0.1pt solid black">
																<fo:table-cell width="100pt" border="0.1pt solid black"
																	padding-start="5px" padding-top="3px" padding-bottom="3px">
																	<fo:block font-size="8px" font-family="Arial">
																		<xsl:value-of select="description1" />
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell width="80pt" border="0.1pt solid black"
																	padding-start="5px" padding-top="3px" padding-bottom="3px">
																	<fo:block font-size="8px" font-family="Arial">
																		<xsl:value-of select="percentage1" />
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell width="90pt" border="0.1pt solid black"
																	padding-start="5px" padding-top="3px" padding-bottom="3px">
																	<fo:block font-size="8px" font-family="Arial">
																		<xsl:value-of select="description2" />
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell width="80pt" border="0.1pt solid black"
																	padding-start="5px" padding-top="3px" padding-bottom="3px">
																	<fo:block font-size="8px" font-family="Arial">
																		<xsl:value-of select="percentage2" />
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell width="90pt" border="0.1pt solid black"
																	padding-start="5px" padding-top="3px" padding-bottom="3px">
																	<fo:block font-size="8px" font-family="Arial">
																		<xsl:value-of select="description3" />
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell width="80pt" border="0.1pt solid black"
																	padding-start="5px" padding-top="3px" padding-bottom="3px">
																	<fo:block font-size="8px" font-family="Arial">
																		<xsl:value-of select="percentage3" />
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</xsl:for-each>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-body>
								</fo:table>
							</fo:table-cell>
						</fo:table-row>
						<fo:table-row border="0.0pt solid black">
							<fo:table-cell width="100pt" border="0.0pt solid black"
								padding-start="5px" padding-top="3px" padding-bottom="3px">
								<fo:block font-size="8px" font-family="Arial"></fo:block>
							</fo:table-cell>
						</fo:table-row>
						<fo:table-row height="5mm" margin-top="50mm">
							<fo:table-cell border="0.0pt solid black">
								<fo:table border="0.0pt solid black">
									<fo:table-column />
									<fo:table-body>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black"
												width="400px">
												<fo:block font-size="9px" font-family="Arial">Current
													Business Plan. Please mark the
													types of business which you feel your agency is most interested
													in :
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row border="0.0pt solid black" height="5mm">
											<fo:table-cell border="0.0pt solid black">
												<fo:table border="0.1pt solid black">
													<fo:table-column />
													<fo:table-column />
													<fo:table-column />
													<fo:table-column />
													<fo:table-column />
													<fo:table-column />
													<fo:table-column />
													<fo:table-column />
													<fo:table-body>
														<xsl:for-each
															select="response/agencyBusinessPlanStep7_list_mom_1/data">
															<fo:table-row border="0.1pt solid black">
																<fo:table-cell width="90pt" border="0.1pt solid black"
																	padding-start="5px" padding-top="3px" padding-bottom="3px">
																	<fo:block font-size="8px" font-family="Arial">
																		<xsl:value-of select="business_description1" />
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell width="40pt" border="0.1pt solid black"
																	padding-start="5px" padding-top="3px" padding-bottom="3px">
																	<fo:block font-size="8px" font-family="Arial">
																		<xsl:value-of select="businessplan_id11" />
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell width="90pt" border="0.1pt solid black"
																	padding-start="5px" padding-top="3px" padding-bottom="3px">
																	<fo:block font-size="8px" font-family="Arial">
																		<xsl:value-of select="business_description2" />
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell width="40pt" border="0.1pt solid black"
																	padding-start="5px" padding-top="3px" padding-bottom="3px">
																	<fo:block font-size="8px" font-family="Arial">
																		<xsl:value-of select="businessplan_id12" />
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell width="90pt" border="0.1pt solid black"
																	padding-start="5px" padding-top="3px" padding-bottom="3px">
																	<fo:block font-size="8px" font-family="Arial">
																		<xsl:value-of select="business_description3" />
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell width="40pt" border="0.1pt solid black"
																	padding-start="5px" padding-top="3px" padding-bottom="3px">
																	<fo:block font-size="8px" font-family="Arial">
																		<xsl:value-of select="businessplan_id13" />
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell width="90pt" border="0.1pt solid black"
																	padding-start="5px" padding-top="3px" padding-bottom="3px">
																	<fo:block font-size="8px" font-family="Arial">
																		<xsl:value-of select="business_description4" />
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell width="40pt" border="0.1pt solid black"
																	padding-start="5px" padding-top="3px" padding-bottom="3px">
																	<fo:block font-size="8px" font-family="Arial">
																		<xsl:value-of select="businessplan_id14" />
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
														</xsl:for-each>
													</fo:table-body>
												</fo:table>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-body>
								</fo:table>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>
		</xsl:if>



		<fo:block break-after="page" />
		<fo:block margin-top="5mm" font-size="12px" font-family="Arial"
			font-weight="bold" text-align="center">AGENCY OTHER INFORMATION</fo:block>

		<!-- <fo:block margin-top="5mm"> <fo:table border="0.0pt solid black"> 
			<fo:table-column border="0.0pt solid black" column-width="130pt"/> <fo:table-column 
			border="0.0pt solid black" column-width="150pt"/> <fo:table-column border="0.0pt 
			solid black" column-width="80pt"/> <fo:table-column border="0.0pt solid black"/> 
			<fo:table-body> <fo:table-row border="0.0pt solid black"> <fo:table-cell 
			border="0.0pt solid black" width="400px"> <fo:block font-size="8px" font-family="Arial" 
			font-weight="bold">MARKET INFORMATION</fo:block> </fo:table-cell> </fo:table-row> 
			<fo:table-row border="0.0pt solid black"> <fo:table-cell border="0.0pt solid 
			black" width="400px"> <fo:block font-size="8px" font-family="Arial"></fo:block> 
			</fo:table-cell> </fo:table-row> <fo:table-row border="0.0pt solid black" 
			height="5mm"> <fo:table-cell border="0.0pt solid black"> <fo:table border="0.1pt 
			solid black"> <fo:table-column/> <fo:table-column/> <fo:table-column/> <fo:table-column/> 
			<fo:table-column/> <fo:table-column/> <fo:table-body> <fo:table-row border="0.1pt 
			solid black"> <fo:table-cell width="120pt" border="0.1pt solid black" padding-start="5px" 
			padding-top="3px" padding-bottom="3px"> <fo:block font-size="8px" font-family="Arial" 
			font-weight="bold">Carrier</fo:block> </fo:table-cell> <fo:table-cell width="100pt" 
			border="0.1pt solid black" padding-start="5px" padding-top="3px" padding-bottom="3px"> 
			<fo:block font-size="8px" font-family="Arial" font-weight="bold">LOB</fo:block> 
			</fo:table-cell> <fo:table-cell width="80pt" border="0.1pt solid black" padding-start="5px" 
			padding-top="3px" padding-bottom="3px"> <fo:block font-size="8px" font-family="Arial" 
			font-weight="bold">Commission rate</fo:block> </fo:table-cell> <fo:table-cell 
			width="80pt" border="0.1pt solid black" padding-start="5px" padding-top="3px" 
			padding-bottom="3px"> <fo:block font-size="8px" font-family="Arial" font-weight="bold">Written 
			Premium</fo:block> </fo:table-cell> <fo:table-cell width="60pt" border="0.1pt 
			solid black" padding-start="5px" padding-top="3px" padding-bottom="3px"> 
			<fo:block font-size="8px" font-family="Arial" font-weight="bold">Loss Ratio</fo:block> 
			</fo:table-cell> <fo:table-cell width="80pt" border="0.1pt solid black" padding-start="5px" 
			padding-top="3px" padding-bottom="3px"> <fo:block font-size="8px" font-family="Arial" 
			font-weight="bold">Retention Rate </fo:block> </fo:table-cell> </fo:table-row> 
			<xsl:for-each select="response/agencyMarketInformation_list_listfreeform_1/data"> 
			<fo:table-row border="0.1pt solid black"> <fo:table-cell width="160pt" border="0.1pt 
			solid black" padding-start="5px" padding-top="3px" padding-bottom="3px"> 
			<fo:block font-size="8px" font-family="Arial"><xsl:value-of select="aar_carrierid_desc"/></fo:block> 
			</fo:table-cell> <fo:table-cell width="160pt" border="0.1pt solid black" 
			padding-start="5px" padding-top="3px" padding-bottom="3px"> <fo:block font-size="8px" 
			font-family="Arial"><xsl:value-of select="aar_lobid_desc"/></fo:block> </fo:table-cell> 
			<fo:table-cell width="160pt" border="0.1pt solid black" padding-start="5px" 
			padding-top="3px" padding-bottom="3px"> <fo:block font-size="8px" font-family="Arial"><xsl:value-of 
			select="commissionrate"/></fo:block> </fo:table-cell> <fo:table-cell width="160pt" 
			border="0.1pt solid black" padding-start="5px" padding-top="3px" padding-bottom="3px"> 
			<fo:block font-size="8px" font-family="Arial"><xsl:value-of select="writtenpremium"/></fo:block> 
			</fo:table-cell> <fo:table-cell width="160pt" border="0.1pt solid black" 
			padding-start="5px" padding-top="3px" padding-bottom="3px"> <fo:block font-size="8px" 
			font-family="Arial"><xsl:value-of select="loss_ratio"/></fo:block> </fo:table-cell> 
			<fo:table-cell width="160pt" border="0.1pt solid black" padding-start="5px" 
			padding-top="3px" padding-bottom="3px"> <fo:block font-size="8px" font-family="Arial"><xsl:value-of 
			select="retentionrate"/></fo:block> </fo:table-cell> </fo:table-row> </xsl:for-each> 
			</fo:table-body> </fo:table> </fo:table-cell> <fo:table-cell number-columns-spanned="3"><fo:block></fo:block></fo:table-cell> 
			</fo:table-row> </fo:table-body> </fo:table> </fo:block> -->


		<fo:block margin-top="5mm">
			<fo:table border="0.0pt solid black">
				<fo:table-column />
				<fo:table-column />
				<fo:table-column />
				<fo:table-column />

				<fo:table-column border="0.0pt solid black" />
				<fo:table-body>
					<fo:table-row height="5mm">
						<fo:table-cell width="200pt">
							<fo:block font-size="9px" font-family="Arial"
								font-weight="bold" text-decoration="underline">Premium Volume and Production
								Information
							</fo:block>
							
						</fo:table-cell>
						<fo:table-cell number-columns-spanned="3">
							<fo:block></fo:block>
						</fo:table-cell>
					</fo:table-row>

					<fo:table-row border="0.0pt solid black" height="5mm">
						<fo:table-cell border="0.0pt solid black">
							<fo:table border="1.0pt solid black">
								<fo:table-column />
								<fo:table-column />
								<fo:table-column />
								<fo:table-column />
								<fo:table-column />
								<fo:table-column />
								<fo:table-column />
								<fo:table-body>
									<fo:table-row border="1.0pt solid black">
										<fo:table-cell width="110pt" border="1.0pt solid black"
											padding-start="5px" padding-top="3px" padding-bottom="3px">
											<fo:block font-size="8px" font-family="Arial"
												font-weight="bold">Line of Business</fo:block>
										</fo:table-cell>
										<!--  <fo:table-cell width="90pt" border="1.0pt solid black"
											padding-start="5px" padding-top="3px" padding-bottom="3px">
											<fo:block font-size="8px" font-family="Arial"
												font-weight="bold">Current Monthly Applications</fo:block>
										</fo:table-cell>  -->
										<fo:table-cell width="90pt" border="1.0pt solid black"
											padding-start="5px" padding-top="3px" padding-bottom="3px">
											<fo:block font-size="8px" font-family="Arial"
												font-weight="bold">Annual Premium Volume</fo:block>
										</fo:table-cell>
                                     <!--  <fo:table-cell width="50pt" border="1.0pt solid black"
											padding-start="5px" padding-top="3px" padding-bottom="3px">
											<fo:block font-size="8px" font-family="Arial"
												font-weight="bold">Year 3</fo:block>
										</fo:table-cell> -->
										
                                        <fo:table-cell width="90pt" border="1.0pt solid black"
											padding-start="5px" padding-top="3px" padding-bottom="3px">
											<fo:block font-size="8px" font-family="Arial"
												font-weight="bold">Monthly Application Written</fo:block>
										</fo:table-cell>
										<fo:table-cell width="90pt" border="1.0pt solid black"
											padding-start="5px" padding-top="3px" padding-bottom="3px">
											<fo:block font-size="8px" font-family="Arial"
												font-weight="bold">Annual Application Written</fo:block>
										</fo:table-cell>
									</fo:table-row>
									<xsl:for-each
										select="response/agencyPremiumVolumeByLOBStep6_list_mom_1/data">
										<fo:table-row border="1.0pt solid black">
											<fo:table-cell width="110pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="lob_description" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell width="90pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="monthly_premium" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell width="90pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="yearly_premium" />
												</fo:block>
											</fo:table-cell>
                                           <fo:table-cell width="50pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="next3year_premium" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell width="90pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="monthly_application_written" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell width="90pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="annual_application_written" />
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:for-each>
								</fo:table-body>
							</fo:table>
						</fo:table-cell>
						<fo:table-cell number-columns-spanned="3">
							<fo:block></fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
		

		<fo:block margin-top="5mm">
			<fo:table border="0.0pt solid black">
				<fo:table-column />
				<fo:table-column />
				<fo:table-column />
				<fo:table-column />
				<fo:table-body>
					<fo:table-row height="5mm">
						<fo:table-cell number-columns-spanned="2" width="200pt">
							<fo:block font-size="9px" font-family="Arial"
								font-weight="bold" text-decoration="underline">Top Three Companies Appointed
							</fo:block>
						</fo:table-cell>
						<fo:table-cell number-columns-spanned="2">
							<fo:block></fo:block>
						</fo:table-cell>
					</fo:table-row>
					<!-- <fo:table-row height="5mm"> <fo:table-cell number-columns-spanned="4" 
						width="200pt"> <fo:block font-size="9px" font-family="Arial" font-weight="bold">Total 
						number of monthly applications written over last 3 months :</fo:block> </fo:table-cell> 
						</fo:table-row> -->
					<fo:table-row border="0.0pt solid black" height="5mm">
						<fo:table-cell border="0.0pt solid black">
							<fo:table border="1.0pt solid black">
								<fo:table-column />
								<fo:table-column />
								<fo:table-column />
								<fo:table-column />
								<fo:table-body>
									<fo:table-row border="1.0pt solid black">
										<fo:table-cell width="220pt" border="1.0pt solid black"
											padding-start="5px" padding-top="3px" padding-bottom="3px">
											<fo:block font-size="8px" font-family="Arial"
												font-weight="bold">Company Name</fo:block>
										</fo:table-cell>
										<fo:table-cell width="120pt" border="1.0pt solid black"
											padding-start="5px" padding-top="3px" padding-bottom="3px">
											<fo:block font-size="8px" font-family="Arial"
												font-weight="bold">
												Written Premium Volume
											</fo:block>
										</fo:table-cell>
										<fo:table-cell width="80pt" border="1.0pt solid black"
											padding-start="5px" padding-top="3px" padding-bottom="3px">
											<fo:block font-size="8px" font-family="Arial"
												font-weight="bold">
												Loss Ratio
											</fo:block>
										</fo:table-cell>
										<fo:table-cell width="100pt" border="1.0pt solid black"
											padding-start="5px" padding-top="3px" padding-bottom="3px">
											<fo:block font-size="8px" font-family="Arial"
												font-weight="bold">
												Year of Appointment
											</fo:block>
										</fo:table-cell>
									</fo:table-row>
									<xsl:for-each
										select="response/agencyMarketInformation_list_listfreeform_1/data">
										<fo:table-row border="1.0pt solid black">
											<fo:table-cell width="220pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="aar_carrierid_desc" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell width="120pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="class1volume" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell width="80pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="loss_ratio" />
													<xsl:if test="loss_ratio!=null or loss_ratio!='' ">
														%
													</xsl:if>
												</fo:block>
											</fo:table-cell>
											<fo:table-cell width="100pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="yearofappointment" />
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:for-each>
								</fo:table-body>
							</fo:table>
						</fo:table-cell>
						<fo:table-cell number-columns-spanned="3">
							<fo:block></fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>

		<fo:block margin-top="5mm">
			<fo:table border="0.0pt solid black">
				<fo:table-column border="0.0pt solid black"
					column-width="130pt" />
				<fo:table-column border="0.0pt solid black"
					column-width="150pt" />
				<fo:table-column border="0.0pt solid black"
					column-width="80pt" />
				<fo:table-column border="0.0pt solid black" />
				<fo:table-body>
					<fo:table-row height="5mm">
						<fo:table-cell number-columns-spanned="2" width="200pt">
							<fo:block font-size="9px" font-family="Arial"
								font-weight="bold" text-decoration="underline">Source to Generate New
								Business</fo:block>
						</fo:table-cell>
						<fo:table-cell number-columns-spanned="2">
							<fo:block></fo:block>
						</fo:table-cell>
					</fo:table-row>

					<fo:table-row border="0.0pt solid black" height="5mm">
						<fo:table-cell border="0.0pt solid black">
							<fo:table border="1.0pt solid black">
								<fo:table-column />
								<fo:table-column />
								<fo:table-column />
								<fo:table-column />
								<fo:table-column />
								<fo:table-column />
								<fo:table-body>
									<xsl:for-each select="response/agencyBusinessSource_list_1/data">
										<fo:table-row border="1.0pt solid black">
											<fo:table-cell width="120pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="business_source_description1" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell width="60pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="percentage1" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell width="110pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="business_source_description2" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell width="60pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="percentage2" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell width="110pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="business_source_description3" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell width="60pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="percentage3" />
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:for-each>
								</fo:table-body>
							</fo:table>
						</fo:table-cell>
						<fo:table-cell number-columns-spanned="3">
							<fo:block></fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
		<fo:block margin-top="5mm">
			<fo:table border="0.0pt solid black">
				<fo:table-column border="0.0pt solid black"
					column-width="130pt" />
				<fo:table-column border="0.0pt solid black"
					column-width="150pt" />
				<fo:table-column border="0.0pt solid black"
					column-width="80pt" />
				<fo:table-column border="0.0pt solid black" />
				<fo:table-body>
					<fo:table-row height="5mm">
						<fo:table-cell number-columns-spanned="4" width="200pt">
							<fo:block font-size="9px" font-family="Arial"
								font-weight="bold">Companies Terminated, Suspended, Restricted writings
								imposed in last 5 years</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row border="0.0pt solid black" height="5mm">
						<fo:table-cell border="0.0pt solid black">
							<fo:table border="1.0pt solid black">
								<fo:table-column />
								<fo:table-column />
								<fo:table-column />
								<fo:table-column />
								<fo:table-body>
									<fo:table-row border="1.0pt solid black">
										<fo:table-cell width="200pt" border="1.0pt solid black"
											padding-start="5px" padding-top="3px" padding-bottom="3px">
											<fo:block font-size="8px" font-family="Arial"
												font-weight="bold">Company Name</fo:block>
										</fo:table-cell>
										<fo:table-cell width="120pt" border="1.0pt solid black"
											padding-start="5px" padding-top="3px" padding-bottom="3px">
											<fo:block font-size="8px" font-family="Arial"
												font-weight="bold">Annual Premium Volume</fo:block>
										</fo:table-cell>
										<fo:table-cell width="90pt" border="1.0pt solid black"
											padding-start="5px" padding-top="3px" padding-bottom="3px">
											<fo:block font-size="8px" font-family="Arial"
												font-weight="bold">Date Closed</fo:block>
										</fo:table-cell>
										<fo:table-cell width="110pt" border="1.0pt solid black"
											padding-start="5px" padding-top="3px" padding-bottom="3px">
											<fo:block font-size="8px" font-family="Arial"
												font-weight="bold">Reason for Action Taken</fo:block>
										</fo:table-cell>
									</fo:table-row>
									<xsl:for-each select="response/agencyterminated_list_1/data">
										<fo:table-row border="1.0pt solid black">
											<fo:table-cell width="120pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="company_name" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell width="120pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="annual_premium_volume" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell width="120pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="date_closed" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell width="120pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="reason_for_action_taken_desc" />
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:for-each>
								</fo:table-body>
							</fo:table>
						</fo:table-cell>
						<fo:table-cell number-columns-spanned="3">
							<fo:block></fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
		<fo:block margin-top="5mm">
			<fo:table border="0.0pt solid black">
				<fo:table-column border="0.0pt solid black"
					column-width="130pt" />
				<fo:table-column border="0.0pt solid black"
					column-width="150pt" />
				<fo:table-column border="0.0pt solid black"
					column-width="80pt" />
				<fo:table-column border="0.0pt solid black" />
				<fo:table-body>
					<fo:table-row height="5mm">
						<fo:table-cell number-columns-spanned="4" width="200pt">
							<fo:block font-size="9px" font-family="Arial"
								font-weight="bold">Personal References</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row border="0.0pt solid black" height="5mm">
						<fo:table-cell border="0.0pt solid black">
							<fo:table border="1.0pt solid black">
								<fo:table-column />
								<fo:table-column />
								<fo:table-column />
								<fo:table-column />
								<fo:table-column />
								<fo:table-body>
									<fo:table-row border="1.0pt solid black">
										<fo:table-cell width="116pt" border="1.0pt solid black"
											padding-start="5px" padding-top="3px" padding-bottom="3px">
											<fo:block font-size="8px" font-family="Arial"
												font-weight="bold">Name</fo:block>
										</fo:table-cell>
										<fo:table-cell width="96pt" border="1.0pt solid black"
											padding-start="5px" padding-top="3px" padding-bottom="3px">
											<fo:block font-size="8px" font-family="Arial"
												font-weight="bold">Company and Position</fo:block>
										</fo:table-cell>
										<fo:table-cell width="116pt" border="1.0pt solid black"
											padding-start="5px" padding-top="3px" padding-bottom="3px">
											<fo:block font-size="8px" font-family="Arial"
												font-weight="bold">Address</fo:block>
										</fo:table-cell>
										<fo:table-cell width="96pt" border="1.0pt solid black"
											padding-start="5px" padding-top="3px" padding-bottom="3px">
											<fo:block font-size="8px" font-family="Arial"
												font-weight="bold">Phone</fo:block>
										</fo:table-cell>
										<fo:table-cell width="96pt" border="1.0pt solid black"
											padding-start="5px" padding-top="3px" padding-bottom="3px">
											<fo:block font-size="8px" font-family="Arial"
												font-weight="bold">Relationship</fo:block>
										</fo:table-cell>
									</fo:table-row>
									<xsl:for-each select="response/personalinformation_list_1/data">
										<fo:table-row border="1.0pt solid black">
											<fo:table-cell width="96pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="name" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell width="96pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="company" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell width="96pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="address" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell width="96pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="phone" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell width="96pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="relationship" />
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:for-each>
								</fo:table-body>
							</fo:table>
						</fo:table-cell>
						<fo:table-cell number-columns-spanned="3">
							<fo:block></fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
		<fo:block margin-top="5mm">
			<fo:table border="0.0pt solid black">
				<fo:table-column />
				<fo:table-column />
				<fo:table-column />
				<fo:table-column />
				<fo:table-body>
					<fo:table-row height="5mm">
						<fo:table-cell number-columns-spanned="4" width="200pt">
							<fo:block font-size="9px" font-family="Arial"
								font-weight="bold">List of previous entities, mergers and acquisitions
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row border="0.0pt solid black" height="5mm">
						<fo:table-cell border="0.0pt solid black">
							<fo:table border="1.0pt solid black">
								<fo:table-column />
								<fo:table-column />
								<fo:table-column />
								<fo:table-body>
									<fo:table-row border="1.0pt solid black">
										<fo:table-cell width="280pt" border="1.0pt solid black"
											padding-start="5px" padding-top="3px" padding-bottom="3px">
											<fo:block font-size="8px" font-family="Arial"
												font-weight="bold">Name</fo:block>
										</fo:table-cell>
										<fo:table-cell width="120pt" border="1.0pt solid black"
											padding-start="5px" padding-top="3px" padding-bottom="3px">
											<fo:block font-size="8px" font-family="Arial"
												font-weight="bold">Date Acquired</fo:block>
										</fo:table-cell>
										<fo:table-cell width="120pt" border="1.0pt solid black"
											padding-start="5px" padding-top="3px" padding-bottom="3px">
											<fo:block font-size="8px" font-family="Arial"
												font-weight="bold">Volume</fo:block>
										</fo:table-cell>
									</fo:table-row>
									<xsl:for-each select="response/agencymergers_list_1/data">
										<fo:table-row border="1.0pt solid black">
											<fo:table-cell width="160pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="agencymerger_name" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell width="160pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="date_acquired" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell width="160pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="volume" />
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:for-each>
								</fo:table-body>
							</fo:table>
						</fo:table-cell>
						<fo:table-cell number-columns-spanned="3">
							<fo:block></fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
		<fo:block margin-top="5mm">
			<fo:table border="0.0pt solid black">
				<fo:table-column border="0.0pt solid black"
					column-width="480pt" />
				<fo:table-body>
					<fo:table-row height="5mm">
						<fo:table-cell width="520pt">
							<fo:block font-size="9px" font-family="Arial"
								font-weight="bold">Brief profile of the agency, trade area, and any
								additional comments or remarks :</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row height="15mm" border="1.0pt solid black">
						<fo:table-cell width="520pt">
							<fo:block font-size="9px" font-family="Arial"
								margin-left="2mm" margin-top="1mm">
								<xsl:value-of select="response/agency_brief_profile" />
								<!--<xsl:call-template name="intersperse-with-zero-spaces"> <xsl:with-param 
									name="str" select="response/comments"/></xsl:call-template> -->
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>

		<fo:block break-after="page"></fo:block>
		<fo:block margin-top="5mm">
			<fo:table border="0.0pt solid black">
				<fo:table-column border="0.0pt solid black"
					column-width="130pt" />
				<fo:table-column border="0.0pt solid black"
					column-width="150pt" />
				<fo:table-column border="0.0pt solid black"
					column-width="80pt" />
				<fo:table-column border="0.0pt solid black" />
				<fo:table-body>
					<fo:table-row height="5mm">
						<fo:table-cell number-columns-spanned="4" width="200pt">
							<fo:block font-size="9px" font-family="Arial"
								font-weight="bold">Documents</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row border="0.0pt solid black" height="5mm">
						<fo:table-cell border="0.0pt solid black">
							<fo:table border="1.0pt solid black">
								<fo:table-column />
								<fo:table-column />
								<fo:table-column />
								<fo:table-body>
									<fo:table-row border="1.0pt solid black">
										<fo:table-cell width="180pt" border="1.0pt solid black"
											padding-start="5px" padding-top="3px" padding-bottom="3px">
											<fo:block font-size="8px" font-family="Arial"
												font-weight="bold">Document Name</fo:block>
										</fo:table-cell>
										<fo:table-cell width="160pt" border="1.0pt solid black"
											padding-start="5px" padding-top="3px" padding-bottom="3px">
											<fo:block font-size="8px" font-family="Arial"
												font-weight="bold">Document Type</fo:block>
										</fo:table-cell>
										<fo:table-cell width="180pt" border="1.0pt solid black"
											padding-start="5px" padding-top="3px" padding-bottom="3px">
											<fo:block font-size="8px" font-family="Arial"
												font-weight="bold">File Name</fo:block>
										</fo:table-cell>
									</fo:table-row>
									<xsl:for-each
										select="response/agencyListPublicSubmitNInvitationEdit_list_4/data">
										<fo:table-row border="1.0pt solid black">
											<fo:table-cell width="160pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:call-template name="intersperse-with-zero-spaces">
														<xsl:with-param name="str" select="document_name" />
													</xsl:call-template>
												</fo:block>
											</fo:table-cell>
											<fo:table-cell width="160pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:value-of select="document_type" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell width="160pt" border="1.0pt solid black"
												padding-start="5px" padding-top="3px" padding-bottom="3px">
												<fo:block font-size="8px" font-family="Arial">
													<xsl:call-template name="intersperse-with-zero-spaces">
														<xsl:with-param name="str" select="file_name" />
													</xsl:call-template>
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:for-each>
								</fo:table-body>
							</fo:table>
						</fo:table-cell>
						<fo:table-cell number-columns-spanned="3">
							<fo:block></fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>

		<xsl:if test="response/signature_acceptance_security='Y'">
			<fo:block margin-top="15mm">
				<fo:table border="0.0pt solid black">
					<fo:table-column />
					<fo:table-column />
					<fo:table-column />
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell>
								<fo:block font-size="10px">Please review and sign below:
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
						<fo:table-row>
							<fo:table-cell width="520pt" number-columns-spanned="3"
								padding-top="3px" padding-bottom="3px">
								<fo:block font-size="10px">
									I certify that the information contained in the Producer
									Application along with the requested attachments is true to the
									best of my knowledge.
									I understand that my appointment with
									<xsl:value-of select="response/clientName"></xsl:value-of>
									Insurance Group is based upon the accuracy of this information
									and the results of a background report.
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>
			<fo:block margin-top="7mm">
				<fo:table border="0.0pt solid black">
					<fo:table-column />
					<fo:table-column />
					<fo:table-column />
					<fo:table-column />
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell width="80pt">
								<fo:block font-size="10px">Name:</fo:block>
							</fo:table-cell>
							<fo:table-cell width="180pt">
								<fo:block font-size="10px">
									<fo:block>
										<xsl:value-of select="response/printname" />
									</fo:block>
								</fo:block>
							</fo:table-cell>
							<fo:table-cell number-columns-spanned="2">
								<fo:block font-size="10px">
									Title:&#160;&#160;&#160;
									<xsl:value-of select="response/title" />
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
						<fo:table-row>
							<fo:table-cell number-columns-spanned="4">
								<fo:block font-size="10px" margin-top="2mm">
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
						<fo:table-row>
							<fo:table-cell>
								<fo:block font-size="10px"> Signature:</fo:block>
							</fo:table-cell>
							<fo:table-cell>
								<fo:block>
									<fo:inline text-decoration="underline" font-weight="bold"
										font-size="10px">
										<fo:inline color="white">
											<fo:block>
												<xsl:value-of select="response/AGENT_SIGNER_IDENTIFIER" />
											</fo:block>
										</fo:inline>
									</fo:inline>
								</fo:block>
							</fo:table-cell>
							<fo:table-cell number-columns-spanned="2">
								<fo:block font-size="10px"> Date:
									 <xsl:value-of select="response/date" />
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>
		</xsl:if>	
	</xsl:template>
	<xsl:template match="*" name="intersperse-with-zero-spaces">
		<xsl:param name="str" />
		<xsl:variable name="spacechars"></xsl:variable>
		<xsl:if test="string-length($str) > 0">
			<xsl:variable name="c1" select="substring($str, 1, 1)" />
			<xsl:variable name="c2" select="substring($str, 2, 1)" />

			<xsl:value-of select="$c1" />
			<xsl:if
				test="$c2 != '' and
 not(contains($spacechars, $c1) or
 contains($spacechars, $c2))">
				<xsl:text>​</xsl:text>
			</xsl:if>

			<xsl:call-template name="intersperse-with-zero-spaces">
				<xsl:with-param name="str" select="substring($str, 2)" />
			</xsl:call-template>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>	