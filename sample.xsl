<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
   <xsl:template match="/">
      <html>
         <body>
            <h1>Details</h1>
            <table border="1">
               <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Surname</th>
                  <th>Grade</th>
               </tr>
               <xsl:for-each select="Results/Row">
                  <tr>
                     <td>
                        <xsl:value-of select="ID" />
                     </td>
                     <td>
                        <xsl:value-of select="Name" />
                     </td>
                     <td>
                        <xsl:value-of select="Surname" />
                     </td>
                     <td>
                        <xsl:value-of select="Grade" />
                     </td>
                  </tr>
               </xsl:for-each>
            </table>
         </body>
      </html>
   </xsl:template>
</xsl:stylesheet>