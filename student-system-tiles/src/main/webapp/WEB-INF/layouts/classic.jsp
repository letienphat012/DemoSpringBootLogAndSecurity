<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="/css/fontawesome.min.css" />
<link rel="stylesheet" type="text/css" href="/css/solid.min.css" />
<link rel="stylesheet" type="text/css" href="/css/brands.min.css" />

<script type="text/javascript" src="/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/fontawesome.min.js"></script>
<script type="text/javascript" src="/js/solid.min.js"></script>
<script type="text/javascript" src="/js/brands.min.js"></script>

<title><tiles:getAsString name="title" /></title>
</head>
<body>
	<table width="100%">
		<tr colspan="2">
			<tiles:insertAttribute name="header"></tiles:insertAttribute>
		</tr>

		<tr>
			<td width="20%" nowrap="nowrap"><tiles:insertAttribute
					name="menu" /></td>
			<td width="80%"><tiles:insertAttribute name="body" /></td>
		</tr>
		<tr>
			<td colspan="2"><tiles:insertAttribute name="footer" /></td>
		</tr>
	</table>
</body>

</html>