<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" version="1.0">
    <xsl:output encoding="UTF-8" indent="yes" method="xml" standalone="no" omit-xml-declaration="no"/>
    <xsl:template match="/">
        <fo:root language="EN">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4-portrail" page-height="297mm" page-width="210mm" margin-top="5mm" margin-bottom="5mm" margin-left="5mm" margin-right="5mm">
                    <fo:region-body margin-top="25mm" margin-bottom="20mm"/>
                    <fo:region-before region-name="xsl-region-before" extent="25mm" display-align="before" precedence="true"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="A4-portrail">
                
                <fo:flow flow-name="xsl-region-body" border-collapse="collapse" reference-orientation="0">
               
                    <fo:table table-layout="fixed" width="100%" font-size="10pt" border-color="black" border-width="0.35mm" border-style="solid" text-align="center" display-align="center" space-after="5mm">
                        <fo:table-column column-width="proportional-column-width(25)"/>
                        <fo:table-column column-width="proportional-column-width(50)"/>
                       
                        <fo:table-body font-size="95%">
									
									<xsl:for-each select="(Table/Row/*)">
										<fo:table-row height="8mm" 	border-color="black" border-width="0.35mm" border-style="solid">
										<fo:table-cell  border-color="black" border-width="0.35mm" border-style="solid">
										<fo:block >
										<xsl:value-of select="name(.)"/>
										</fo:block >
										</fo:table-cell>
										<fo:table-cell border-color="black" border-width="0.35mm" border-style="solid">
										<fo:block >
											<xsl:value-of select="node()" />
										</fo:block>
										</fo:table-cell>
										</fo:table-row>
									</xsl:for-each>																	
					</fo:table-body>
                    </fo:table>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>