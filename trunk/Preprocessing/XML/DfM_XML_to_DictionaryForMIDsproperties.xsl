<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" indent="no" encoding="UTF-8"/>
	<xsl:strip-space  elements="*"/>

	<xsl:variable name="newlineCharacter" select="'&#xA;'"/>
	<xsl:variable name="carriageReturnCharacter" select="'&#xD;'"/>

 	<xsl:template match="//dictionary">
 		<xsl:apply-templates/>
 	</xsl:template>
 	
 	<xsl:template match="dictionaryProperties">
 		<xsl:apply-templates/>
 	</xsl:template>
 	
 	<xsl:template match="property">
 		<xsl:apply-templates/>
		<xsl:value-of select="$carriageReturnCharacter"/>
		<xsl:value-of select="$newlineCharacter"/>
 	</xsl:template>
 	
 	<xsl:template match="name">
		<xsl:value-of select="."/>
 		<xsl:text>: </xsl:text>
 	</xsl:template>

 	<xsl:template match="value">
		<xsl:value-of select="."/>
 	</xsl:template>

 	<xsl:template match="dictionaryFile">
 		<!-- the inputdictionaryfile is handled by the file DfM_XML_to_inputdictionaryfile.xsl -->
 	</xsl:template>
 		
</xsl:stylesheet>




