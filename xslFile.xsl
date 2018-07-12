<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
   <xsl:template match="/">
      <html>
         <body>         
            <table border="1">
			<xsl:for-each select="(Table/Row/*)[4 >= position()]">
				<td>
                    <xsl:value-of select="name(.)"/>
				</td>
			</xsl:for-each>
            <xsl:for-each select="Table/Row/*">
					  <xsl:if test="name()='ID'">
						<tr>
						</tr>
					  </xsl:if>
                     <td>
                        <xsl:value-of select="node()" />
                     </td>
           </xsl:for-each>
            </table>
         </body>
      </html>
	  
   </xsl:template>
</xsl:stylesheet>