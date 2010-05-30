<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" indent="no" encoding="UTF-8"/>
	<xsl:strip-space  elements="*"/>

	<xsl:variable name="newlineCharacter" select="'&#xA;'"/>
	<xsl:variable name="carriageReturnCharacter" select="'&#xD;'"/>
	<xsl:variable name="tabCharacter" select="'&#9;'"/>
	<xsl:variable name="startContentCharacter">[</xsl:variable>
	<xsl:variable name="endContentCharacter">]</xsl:variable>

 	<xsl:template match="//dictionary">
 		<xsl:apply-templates/>
 	</xsl:template>
 	
 	<xsl:template match="dictionaryProperties">
 		<!-- properties are handled by the file DfM_XML_to_DictionaryForMIDsproperties.xsl -->
 	</xsl:template>
 	
 	<xsl:template match="dictionaryFile">
 		<xsl:apply-templates/>
 	</xsl:template>

	<xsl:template match="translationOfDictionary">
		<xsl:apply-templates/>
		<xsl:if test="position()!=last()">
			<xsl:value-of select="$carriageReturnCharacter"/>
			<xsl:value-of select="$newlineCharacter"/>
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="translationForLanguage">
		<xsl:apply-templates/>
		<xsl:if test="position()!=last()">
			<xsl:value-of select="$tabCharacter"/>
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="translationForLanguagePart">
		<xsl:if test="attribute::includeInIndex='false'">
			<xsl:text>{{</xsl:text>
		</xsl:if>
		<xsl:apply-templates/>
		<xsl:if test="attribute::includeInIndex='false'">
			<xsl:text>}}</xsl:text>
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="partNonContent"> 
		<xsl:call-template name="replaceEscapeCharacters">
		 	<xsl:with-param name="characterString" select="."/>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="partContent">
		<xsl:text>[0</xsl:text> <!-- implementation for contentNumber > 9 is to be done -->
		<xsl:value-of select="attribute::contentNumber"></xsl:value-of>
		<xsl:call-template name="replaceEscapeCharacters">
		 	<xsl:with-param name="characterString" select="."/>
		</xsl:call-template>
		<xsl:text>]</xsl:text>
	</xsl:template>

	<xsl:template name="replaceEscapeCharacters">
	 	<xsl:param name="characterString"/>
		<xsl:if test="string-length($characterString)!=0">
			<xsl:variable name="firstCharacter" select="substring($characterString,1,1)"/>
			
			<!-- check for characters that needs an escape character (\) -->
			<xsl:choose>
			 	<xsl:when test="$firstCharacter=$tabCharacter">
					<xsl:text>\t</xsl:text>
				</xsl:when>
			 	<xsl:when test="$firstCharacter=$newlineCharacter">
					<xsl:text>\n</xsl:text>
				</xsl:when>
			 	<xsl:when test="$firstCharacter=$carriageReturnCharacter">
					<!-- drop CR characters -->
				</xsl:when>
			 	<xsl:when test="$firstCharacter=$startContentCharacter">
					<xsl:text>\</xsl:text>
					<xsl:value-of select="$startContentCharacter"/>
				</xsl:when>
			 	<xsl:when test="$firstCharacter=$endContentCharacter">
					<xsl:text>\</xsl:text>
					<xsl:value-of select="$endContentCharacter"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$firstCharacter"/>
				</xsl:otherwise>
			</xsl:choose>
			
			<!-- recursive call for the remaining characters of the string -->
			<xsl:call-template name="replaceEscapeCharacters">
			 	<xsl:with-param name="characterString" select="substring($characterString,2)"/>
			</xsl:call-template>
		</xsl:if>
	</xsl:template>
	
</xsl:stylesheet>




