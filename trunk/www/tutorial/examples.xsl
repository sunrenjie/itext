<?xml version="1.0" encoding="UTF-8"?>
<!-- $Date$ -->
<!-- author: Bruno Lowagie        -->
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:site="http://www.lowagie.com/iText/site"
exclude-result-prefixes="site">
  <xsl:output method="xml" indent="yes" media-type="text/xml" />

  <xsl:param name="root" />
  <xsl:param name="branch" />

  <xsl:template match="site:metadata" />
  <xsl:template match="site:section" />
  <xsl:template match="site:examples">
    <xsl:element name="project">
      <xsl:attribute name="name">examples</xsl:attribute>
      <xsl:attribute name="default">examples</xsl:attribute>
      <xsl:attribute name="basedir">../..<xsl:value-of select="$root" /></xsl:attribute>
      <target name="examples">
		<property name="build" value="${{basedir}}/build" />
		<property name="bin" value="${{build}}/bin" />
		<property name="release" value="${{build}}/release" />
		<property name="tutorialsrc" value="${{basedir}}/www/tutorial" />
		<property name="tutorial" value="${{build}}/tutorial" />
		<property name="examples" value="${{build}}/examples" />
        <path id="classpath">
          <pathelement location="${{examples}}" />
          <pathelement location="${{bin}}/iText.jar" />
          <xsl:for-each select="./*/site:extrajar">
            <xsl:element name="pathelement">
              <xsl:attribute name="location">${bin}/
              <xsl:value-of select="." />
              </xsl:attribute>
            </xsl:element>
          </xsl:for-each>
        </path>
		<javac srcdir="${{examples}}" destdir="${{examples}}" verbose="false">
			<classpath refid="classpath" />
		</javac>
        <xsl:for-each select="site:example">
          <xsl:if test="count(site:externalresource)!=0" >
            <xsl:element name="copy">
              <xsl:attribute name="todir">${tutorial}<xsl:value-of select="$branch" /></xsl:attribute>
              <xsl:attribute name="overwrite">no</xsl:attribute>
              <xsl:element name="fileset">
                <xsl:attribute name="dir">${tutorialsrc}<xsl:value-of select="$branch" /></xsl:attribute>
                <xsl:for-each select="site:externalresource">
                  <xsl:element name="filename">
                    <xsl:attribute name="name"><xsl:value-of select="." /></xsl:attribute>
                  </xsl:element>
                </xsl:for-each>
              </xsl:element>
		    </xsl:element>
		  </xsl:if>
          <xsl:element name="java">
            <xsl:attribute name="fork">yes</xsl:attribute>
            <xsl:attribute name="dir">${tutorial}<xsl:value-of select="$branch" /></xsl:attribute>
            <xsl:attribute name="classname">com.lowagie.examples<xsl:value-of select="translate($branch, '/', '.')" />.<xsl:value-of select="site:java/@src" /></xsl:attribute>
            <xsl:for-each select="site:argument">
              <xsl:element name="arg">
                <xsl:attribute name="value">
                  <xsl:value-of select="." />
                </xsl:attribute>
              </xsl:element>
            </xsl:for-each>
            <classpath refid="classpath" />
          </xsl:element>
        </xsl:for-each>
        <delete>
           <fileset dir="${{examples}}" includes="**/*.class"/>
        </delete>
      </target>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>

