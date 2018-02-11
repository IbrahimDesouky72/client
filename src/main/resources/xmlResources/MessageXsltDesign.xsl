<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : MessageXsltDesign.xsl
    Created on : February 10, 2018, 7:24 PM
    Author     : bassem
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <html>
            <head>
                <title>Message</title>
            </head>
            <body>
                
                <table border="1">

                    <tr>
                        <td>Message From</td>
                        <td>Message To</td>
                        <td>Message Body</td>
                        <td>Message Date</td>
                        <td>Message Color</td>
                        <td>Message FamilyFont</td>
                        <td>Message FontSize</td>
                        <td>Message FontType</td>
                    </tr>

                    <xsl:for-each select="Messages/Message">
                        <xsl:sort/>
                        <tr>
                            <td>
                                <xsl:value-of select="@From"/>
                            </td>
                            <td>
                                <xsl:value-of select="@To"/>
                            </td>
                            <td>
                                <xsl:value-of select="Body"/>
                            </td>
                            <td>
                                <xsl:value-of select="Date"/>
                            </td>
                            <td>
                                <xsl:value-of select="color"/>
                            </td>
                            <td>
                                <xsl:value-of select="Font/@font-family"/>
                            </td>
                            <td>
                                <xsl:value-of select="Font/@font-size"/>
                            </td>
                            <td>
                                <xsl:value-of select="Font/@font-type"/>
                            </td>
                            
                        </tr>
                    </xsl:for-each>

                </table>
                
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
