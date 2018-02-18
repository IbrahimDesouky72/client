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
    <xsl:variable name="owner" select="/Messages/@owner"/>
    <xsl:template match="/">
        <html>
            <head>
                <title>
                    <xsl:value-of select="$owner"/>
                </title>
            </head>
            <body>
                <div class="menu">
                    <div class="back">
                        <i class="fa fa-chevron-left"></i> 
                        <img src="https://i.imgur.com/DY6gND0.png" draggable="false"/>
                    </div>
                    <div class="name">ChatName</div>
                    <div class="last">18:09</div>
                </div>
                <ol class="chat">
                    
                    <xsl:for-each select="Message">
                        <xsl:choose>
                            <xsl:when test="@From = $owner">
                                <li class="self">
                                    <div class="avatar">
                                        <img src="https://i.imgur.com/HYcn9xO.png" draggable="false"/>
                                    </div>
                                    <div class="msg">
                                        <p>
                                            <xsl:value-of select="Body"/>
                                        </p>
                                        <time>
                                            <xsl:value-of select="Date"/>
                                        </time>
                                    </div>
                                </li>
                            </xsl:when>
                            <xsl:otherwise>
                                <li class="other">
                                    <div class="avatar">
                                        <img src="https://i.imgur.com/DY6gND0.png" draggable="false"/>
                                    </div>
                                    <div class="msg">
                                        <p>
                                            <xsl:value-of select="Body"/>
                                        </p>
                                        <time>
                                            <xsl:value-of select="Date"/>
                                        </time>
                                    </div>
                                </li>
                            </xsl:otherwise>
                        </xsl:choose>   
                    </xsl:for-each>
                </ol>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
