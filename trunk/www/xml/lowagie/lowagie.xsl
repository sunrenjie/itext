<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:html="http://www.w3.org/1999/xhtml"
	xmlns:site="http://www.lowagie.com/iText/site"
	exclude-result-prefixes="site html" >

<xsl:output method="html" doctype-public="-//W3C//DTD HTML 4.0 Transitional//EN" indent="yes" media-type="text/html" />

<xsl:template match="site:page">
<html>
<head>
	<xsl:text disable-output-escaping="yes">
	<![CDATA[<!-- $Header$ -->]]>
	</xsl:text>
	<xsl:element name="title">iText, a Free Java-PDF Library: <xsl:value-of select="site:metadata/site:title" />
	</xsl:element>
	<xsl:element name="meta">
		<xsl:attribute name="name">Description</xsl:attribute>
		<xsl:attribute name="content"><xsl:value-of select="site:metadata/site:summary" /></xsl:attribute>
	</xsl:element>
	<xsl:element name="meta">
		<xsl:attribute name="name">Keywords</xsl:attribute>
		<xsl:attribute name="content"><xsl:value-of select="site:metadata/site:keywords" /></xsl:attribute>
	</xsl:element>
	<link rel="stylesheet" href="style.css" type="text/css" />
</head>
<body>
<xsl:element name="div">
	<xsl:attribute name="id">content</xsl:attribute>
	<xsl:apply-templates select="site:content" />
<div id="footer">Page Updated: $Date$<br />
Copyright <![CDATA[&copy;]]> 1999-2004 by Bruno Lowagie, Adolf Baeyensstraat 121, 9040 Gent, BELGIUM<br />
Read the <A HREF="privacypolicy.html" CLASS="verysmall">Privacy Policy</A> at lowagie.com;
mailto:<A HREF="mailto:itext-questions@lists.sourceforge.net">itext-questions@lists.sourceforge.net</A></div>
</xsl:element>

<div id="navigation">
	<div id="itext"><a href="http://www.lowagie.com/iText/"><img class="logo" src="http://www.lowagie.com/iText/logo.gif" border="0" /></a><br />
	a Free Java-PDF library<br />by <a class="author" HREF="http://www.lowagie.com/">Bruno Lowagie</a><br />
	and <a class="author" HREF="http://itextpdf.sourceforge.net/">Paulo Soares</a>
	</div>
	<div id="links">
	<a class="navigation" href="index.html">Home @ lowagie.com</a>
	<a class="navigation" href="http://sourceforge.net/projects/itext/">Home @ Sourceforge.net</a>
	<a class="navigation" href="http://itextpdf.sourceforge.net/">Early Access (Paulo)</a>
	<br />
	<a class="navigation" href="download.html">Download <![CDATA[&amp;]]> Compile</a>
	<a class="navigation" href="http://itext.sourceforge.net/src/">Browse Source Code</a>
	<a class="navigation" href="cvs.html">CVS repository</a>
	<br />
	<a class="navigation" href="docs.html">Documentation</a>
	<a class="navigation" href="faq.html">FAQ</a>
	<a class="navigation" href="http://lists.sourceforge.net/lists/listinfo/itext-questions">Mailing List Registration</a>
	<a class="navigation" href="http://news.gmane.org/gmane.comp.java.lib.itext.general">Mailing List Archives</a>
	<br />
	<a class="navigation" href="links.html">Links</a>
	</div>
</div>


</body>
<div id="sourceforge"><a href="http://sourceforge.net"><img src="http://sourceforge.net/sflogo.php?group_id=group_id=15255&amp;type=6" width="210" height="62" border="0" alt="SourceForge.net Logo" /></a></div>
<div id="commercial">
<xsl:if test="count(/site:page/site:metadata/site:amazonbooks/site:book)>0">
	<a class="amazonlinks" href="amazon.html">Amazon books:</a><br />
	<xsl:element name="iframe">
		<xsl:attribute name="source"><xsl:copy-of select="$amazonlink" /></xsl:attribute>
		<xsl:attribute name="width">120</xsl:attribute>
		<xsl:attribute name="height">240</xsl:attribute>
		<xsl:attribute name="scrolling">no</xsl:attribute>
		<xsl:attribute name="marginwidth">0</xsl:attribute>
		<xsl:attribute name="marginheight">0</xsl:attribute>
		<xsl:attribute name="frameborder">0</xsl:attribute>
	</xsl:element>
</xsl:if>

<script type="text/javascript"><![CDATA[<!--
document.write('<iframe src="http://rcm.amazon.com/e/cm?t=itisacatalofwebp&o=1&p=8&l=as1&asins=]]><xsl:value-of select="$amazonlink" /><![CDATA[&fc1=000000&lc1=0000ff&bc1=&lt1=_blank&IS2=1&bg1=ffffff&f=ifr" width="120" height="240" scrolling="no" marginwidth="0" marginheight="0" frameborder="0"></iframe>');

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
</html>
</xsl:template>

<!--
		copies the html tags into the output stream
-->
<xsl:template match="html:*">
	<xsl:copy>
		<xsl:apply-templates select="*|text()|@*"/>
	</xsl:copy>
</xsl:template>
<xsl:template match="@*">
		<xsl:attribute name="{local-name()}"><xsl:value-of select="."/></xsl:attribute>
</xsl:template>

<xsl:variable name="amazonlink"><xsl:for-each select="/site:page/site:metadata/site:amazonbooks/site:book"><xsl:value-of select="string(@asin)" /><xsl:if test="position()!=last()">,</xsl:if></xsl:for-each></xsl:variable>

</xsl:stylesheet>