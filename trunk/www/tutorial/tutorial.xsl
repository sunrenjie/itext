<?xml version="1.0" encoding="UTF-8"?>

<!-- $Header$ -->
<!-- author: Bruno Lowagie -->

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:html="http://www.w3.org/1999/xhtml"
	xmlns:site="http://www.lowagie.com/iText/site"
	exclude-result-prefixes="site html" >

<xsl:output method="html" doctype-public="-//W3C//DTD HTML 4.0 Transitional//EN" indent="no" media-type="text/html" />

<!-- Amazon related stuff -->

<xsl:template name="amazonasin">
<xsl:param name="asins"/>
<script type="text/javascript"><![CDATA[<!--
document.write('<iframe src="http://rcm.amazon.com/e/cm?t=itisacatalofwebp&o=1&p=8&l=as1&asins=]]><xsl:value-of select="$asins" /><![CDATA[&fc1=000000&lc1=0000ff&bc1=&lt1=_blank&IS2=1&bg1=ffffff&f=ifr" width="120" height="240" scrolling="no" marginwidth="0" marginheight="0" frameborder="0" align="Center"></iframe>');
//-->]]></script>
<xsl:element name="a">
	<xsl:attribute name="href">http://www.amazon.co.uk/exec/obidos/ASIN/<xsl:value-of select="substring($asins, 0, 11)" />/catloogjecom-21</xsl:attribute>
	<xsl:attribute name="class">amazonlinks</xsl:attribute>
	amazon.co.uk-link
</xsl:element><br />
</xsl:template>

<xsl:template name="amazonkeyword">
<xsl:param name="keyword"/>
<script type="text/javascript"><![CDATA[<!--
document.write('<iframe src="http://rcm.amazon.com/e/cm?t=itisacatalofwebp&o=1&p=10&l=st1&mode=books&search=]]><xsl:value-of select="$keyword" /><![CDATA[&=1&fc1=&lc1=&lt1=&bg1=&f=ifr" width="120" height="460" border="0" frameborder="0" style="border:none;" scrolling="no" marginwidth="0" marginheight="0"></iframe>');
//-->]]></script>
</xsl:template>

<!-- Keeping the html as is -->

<xsl:template match="html:*">
	<xsl:copy>
		<xsl:apply-templates select="*|text()|@*"/>
	</xsl:copy>
</xsl:template>

<xsl:template match="@*">
	<xsl:attribute name="{local-name()}"><xsl:value-of select="."/></xsl:attribute>
</xsl:template>

<!-- the examples -->
<xsl:template match="site:examples">
  <xsl:if test="count(./site:extrajar)>0">
  	<ul>Extra jars needed to compile and run these examples:
    <xsl:for-each select="./site:extrajar">
       <li><xsl:value-of select="." /></li>
    </xsl:for-each></ul>
  </xsl:if>
  <xsl:for-each select="site:example">
  	<div class="example">
  	<xsl:element name="a">
  		<xsl:attribute name="class">example</xsl:attribute>
  		<xsl:attribute name="href">/examples/com/lowagie/examples<xsl:value-of select="/site:page/site:metadata/site:tree/@branch" />/<xsl:value-of select="site:java/@src" />.java</xsl:attribute>
  		<xsl:value-of select="site:java/@src" />
    </xsl:element><br />
  	<xsl:value-of select="site:description/." /><br />
    <xsl:if test="count(site:argument)!=0" >
      <ul>Argument(s):
      <xsl:for-each select="site:argument">
  		<li><xsl:value-of select="." /></li>
      </xsl:for-each></ul>
    </xsl:if>
    <xsl:if test="count(site:externalresource)!=0" >
      <ul>Input:
      <xsl:for-each select="site:externalresource">
  		<li><xsl:value-of select="." /></li>
      </xsl:for-each></ul>
    </xsl:if>
    <ul>Output:
    <xsl:for-each select="site:result">
      <li><xsl:element name="a">
      	<xsl:attribute name="href"><xsl:value-of select="." /></xsl:attribute>
  		<xsl:value-of select="." />
  	  </xsl:element></li>
    </xsl:for-each></ul>
    </div>
  </xsl:for-each>
</xsl:template>

<!-- the actual page -->

<xsl:template match="site:page">
<html>

<head>
	<xsl:element name="title">iText by Example: <xsl:value-of select="site:metadata/site:title" />
	</xsl:element>
	<xsl:element name="meta">
		<xsl:attribute name="name">Description</xsl:attribute>
		<xsl:attribute name="content"><xsl:value-of select="site:metadata/site:summary" /></xsl:attribute>
	</xsl:element>
	<xsl:element name="meta">
		<xsl:attribute name="name">Keywords</xsl:attribute>
		<xsl:attribute name="content"><xsl:value-of select="site:metadata/site:keywords" /></xsl:attribute>
	</xsl:element>
	<xsl:element name="link">
		<xsl:attribute name="rel">stylesheet</xsl:attribute>
		<xsl:attribute name="href"><xsl:value-of select="/site:page/site:metadata/site:tree/@root" />/style.css</xsl:attribute>
		<xsl:attribute name="type">text/css</xsl:attribute>
	</xsl:element>
</head>

<body>

<xsl:element name="div">
	<xsl:attribute name="id">content</xsl:attribute>
	<xsl:apply-templates select="site:content" />
	
<div xmlns="http://www.w3.org/1999/xhtml" id="footer">
Page Updated: <xsl:value-of select="substring(site:metadata/site:updated, 8, 19)" /><br />
Copyright &#169; 1999-2004
<xsl:for-each select="/site:page/site:metadata/site:author"><xsl:value-of select="." /><xsl:if test="position()!=last()">, </xsl:if></xsl:for-each>
</div>
	
</xsl:element>

<xsl:element name="div">
  <xsl:attribute name="id">examples</xsl:attribute>
  <div class="subtitle">Examples:</div>
  <xsl:apply-templates select="site:examples" />
</xsl:element>

<div id="google">
<script type="text/javascript"><![CDATA[<!--
google_ad_client = "pub-0340380473790570";
google_ad_width = 120;
google_ad_height = 600;
google_ad_format = "120x600_as";
google_ad_channel ="0722422818";
google_ad_type = "text";
google_color_border = "000000";
google_color_bg = "FFFFFF";
google_color_link = "B31800";
google_color_url = "B31800";
google_color_text = "FF2200";
//-->]]></script>
<script type="text/javascript"
  src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>
</div>

<div id="amazon">
<a class="amazonlinks" href="amazon.html" xmlns="http://www.w3.org/1999/xhtml">Amazon books:</a>
<xsl:for-each select="/site:page/site:metadata/site:amazonbooks/site:book">
	<xsl:call-template name="amazonasin"><xsl:with-param name="asins"><xsl:value-of select="string(@asin)" /></xsl:with-param></xsl:call-template>
</xsl:for-each>
<xsl:for-each select="/site:page/site:metadata/site:amazonbooks/site:keyword">
	<xsl:call-template name="amazonkeyword"><xsl:with-param name="keyword"><xsl:value-of select="." /></xsl:with-param></xsl:call-template>
</xsl:for-each>
</div>

</body>
</html>

</xsl:template>

</xsl:stylesheet>
