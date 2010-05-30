<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" encoding="UTF-8"/>
	

	<xsl:template match="//dictionary">
		<dictionary xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.kugihan.de/DfMSchema DfMSchema.xsd ">
			<dictionaryProperties>
				<property>
					<name>infoText</name> <value>Kaufmann's 1934 Visayan-English Dictionary; maintained by Jeroen Hellingman; set up for DfM by Gert Nuber (dict@kugian.de);[todo copyright]</value>
				</property>
				<property>
					<name>dictionaryAbbreviation</name> <value>KVED</value>
				</property>
				<property>
					<name>numberOfAvailableLanguages</name> <value>2</value>
				</property>
				<property>
					<name>language1DisplayText</name> <value>Hiligaynon</value>
				</property>
				<property>
					<name>language1Icon</name> <value>Philippines</value> <!-- for future use -->
				</property>
				<property>
					<name>language1FilePostfix</name> <value>Hil</value>
				</property>
				<property>
					<name>language1GenerateIndex</name> <value>true</value>
				</property>
				<property>
					<name>language1HasSeparateDictionaryFile</name> <value>false</value>
				</property>
				<property>
					<name>language1IsSearchable</name> <value>true</value>
				</property>
				<property>
					<name>language1NormationClassName</name> <value>de.kugihan.dictionaryformids.translation.NormationFil</value>
				</property>
				<property>
					<name>language2DisplayText</name> <value>English</value>
				</property>
				<property>
					<name>language2FilePostfix</name> <value>Eng</value>
				</property>
				<property>
					<name>language2GenerateIndex</name> <value>false</value>
				</property>
				<property>
					<name>language2IsSearchable</name> <value>false</value>
				</property>
				<property>
					<name>language2NormationClassName</name> <value>de.kugihan.dictionaryformids.translation.NormationEng</value>
				</property>
				<property>
					<name>dictionaryGenerationInputCharEncoding</name> <value>UTF-8</value>
				</property>
				<property>
					<name>dictionaryCharEncoding</name> <value>UTF-8</value>
				</property>
				<property>
					<name>indexCharEncoding</name> <value>UTF-8</value>
				</property>
				<property>
					<name>searchListCharEncoding</name> <value>UTF-8</value>
				</property>
				<property>
					<name>dictionaryFileSeparationCharacter</name> <value>'\t'</value>
				</property>
				<property>
					<name>indexFileSeparationCharacter</name> <value>'\t'</value>
				</property>
				<property>
					<name>searchListFileSeparationCharacter</name> <value>'\t'</value>
				</property>
				<property>
					<name>language2NumberOfContentDeclarations</name> <value>1</value>
				</property>
				<property>
					<name>language2Content01DisplayText</name> <value>contentSampleUsage</value>
				</property>
			</dictionaryProperties>
			<dictionaryFile>
				<xsl:apply-templates/>
			</dictionaryFile>
		</dictionary>
	</xsl:template>


	<xsl:template match="entry">
	  	<translationOfDictionary>
			<xsl:apply-templates/>
		    <translationForLanguage>
				<translationForLanguagePart includeInIndex="false">
					<xsl:for-each select="text()|s">
						<xsl:if test="name()!='s'">
							<partNonContent>
								<xsl:value-of select="."/>
							</partNonContent> 
						</xsl:if>
						<xsl:if test="name()='s'">
							<partContent contentNumber="1">
								<xsl:value-of select="."/>
							</partContent> 
						</xsl:if>							
					</xsl:for-each> 
				</translationForLanguagePart>
		    </translationForLanguage>
	  	</translationOfDictionary>
	</xsl:template>

	<xsl:template match="hw">
	    <translationForLanguage>
			<translationForLanguagePart includeInIndex="true">
				<partNonContent>
					<xsl:copy-of select="child::s/text()"/>
			  	</partNonContent>
		    </translationForLanguagePart>
	    </translationForLanguage>
	</xsl:template>

	<xsl:template match="text()|@*">
	</xsl:template>

</xsl:stylesheet>