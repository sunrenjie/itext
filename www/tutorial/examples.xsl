<?xml version="1.0" encoding="UTF-8"?>
<!-- $Date$ -->
<!-- author: Bruno Lowagie        -->
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:site="http://www.lowagie.com/iText/site"
xmlns:ant="antlib:org.apache.tools.ant"
exclude-result-prefixes="site ant">
  <xsl:output method="xml" indent="yes" media-type="text/xml" />
  <xsl:template match="site:metadata" />
  <xsl:template match="site:content" />
  <xsl:template match="site:examples">
<project name="examples" default="examples" basedir=".">
	<target name="examples">
    	<xsl:element name="property">
	        <xsl:attribute name="name">build</xsl:attribute>
    	    <xsl:attribute name="value">../../<xsl:value-of select="/site:page/site:metadata/site:tree/@root" />/build</xsl:attribute>
        </xsl:element>
    	<xsl:element name="property">
	        <xsl:attribute name="name">bin</xsl:attribute>
    	    <xsl:attribute name="value">${build}/bin</xsl:attribute>
        </xsl:element>
    	<xsl:element name="property">
	        <xsl:attribute name="name">release</xsl:attribute>
    	    <xsl:attribute name="value">${build}/release</xsl:attribute>
        </xsl:element>
    	<xsl:element name="property">
	        <xsl:attribute name="name">examples</xsl:attribute>
    	    <xsl:attribute name="value">../<xsl:value-of select="/site:page/site:metadata/site:tree/@root" />/www/examples</xsl:attribute>
        </xsl:element>
        <path id="classpath">
          <pathelement location="${{examples}}" />
          <pathelement location="${{bin}}/iText.jar" />
          <xsl:for-each select="./site:extrajar">
          	<xsl:element name="pathelement">
          		<xsl:attribute name="location">${bin}/<xsl:value-of select="." /></xsl:attribute>
          	</xsl:element>
          </xsl:for-each>
        </path>
        <xsl:for-each select="site:example">
          <xsl:element name="javac">
          	<xsl:attribute name="srcdir">${examples}/com/lowagie/examples/<xsl:value-of select="/site:page/site:metadata/site:tree/@branch" />/<xsl:value-of select="site:java/@src" />.java</xsl:attribute>
          	<xsl:attribute name="destdir">${examples}</xsl:attribute>
          	<xsl:attribute name="verbose">false</xsl:attribute>
          	<classpath refid="classpath" />
          </xsl:element>
          <xsl:element name="java">
            <xsl:attribute name="fork">yes</xsl:attribute>
            <xsl:attribute name="dir">.</xsl:attribute>
            <xsl:attribute name="classname">${examples}/com.lowagie.examples.<xsl:value-of select="/site:page/site:metadata/site:tree/@branch" />.<xsl:value-of select="site:java/@src" />
            </xsl:attribute>
            <xsl:for-each select="site:argument">
              <xsl:element name="arg">
                <xsl:attribute name="value">
                  <xsl:value-of select="." />
                </xsl:attribute>
              </xsl:element>
            </xsl:for-each>
            <classpath refid="classpath" />
          </xsl:element>
          <xsl:element name="delete">
          	<xsl:attribute name="file">${examples}/com/lowagie/examples/<xsl:value-of select="/site:page/site:metadata/site:tree/@branch" />/<xsl:value-of select="site:java/@src" />.class</xsl:attribute>
          </xsl:element>
        </xsl:for-each>
      </target>
    </project>
  </xsl:template>
</xsl:stylesheet>