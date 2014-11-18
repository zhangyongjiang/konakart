<%--
//
// (c) 2012 DS Data Systems UK Ltd, All rights reserved.
//
// DS Data Systems and KonaKart and their respective logos, are 
// trademarks of DS Data Systems UK Ltd. All rights reserved.
//
// The information in this document is free software; you can redistribute 
// it and/or modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
// 
// This software is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
--%>
<%@include file="Taglibs.jsp" %>
<% com.konakart.al.KKAppEng kkEng = (com.konakart.al.KKAppEng) session.getAttribute("konakartKey");%>
<% com.konakart.appif.MiscItemIf[] miscItems = kkEng.getCategoryMgr().getCurrentCat().getMiscItems();%>
<% boolean hideRow1 =  kkEng.getPropertyAsBoolean("category.page.hide.banner.row1", false);%>
<% boolean hideRow2 =  kkEng.getPropertyAsBoolean("category.page.hide.banner.row2", false);%>

<%if (miscItems != null && miscItems.length > 2){%>
	<% if (!hideRow1) { %>
		<% com.konakart.appif.MiscItemIf banner1 = miscItems[0];%>
		<% com.konakart.appif.MiscItemIf banner1Medium = miscItems[1];%>
		<% com.konakart.appif.MiscItemIf banner1Small = miscItems[2];%>

	<% if (banner1.getCustom1() != null && banner1.getCustom1().length() > 0){ %>
		
		<a href="<%=banner1.getCustom1()%>">
			<picture class="rounded-corners">
				<!--[if IE 9]><video style="display: none;"><![endif]-->
				<source srcset="<%=kkEng.getImageBase()%>/<%=banner1.getItemValue()%>" media="(min-width: 750px)">
				<source srcset="<%=kkEng.getImageBase()%>/<%=banner1Medium.getItemValue()%>" media="(min-width: 440px)">
				<source srcset="<%=kkEng.getImageBase()%>/<%=banner1Small.getItemValue()%>" >
				<!--[if IE 9]></video><![endif]-->
				<img srcset="<%=kkEng.getImageBase()%>/<%=banner1.getItemValue()%>">
			</picture>
		</a> 	
		<% }else{ %>
		<picture class="rounded-corners">
			<!--[if IE 9]><video style="display: none;"><![endif]-->
			<source srcset="<%=kkEng.getImageBase()%>/<%=banner1.getItemValue()%>" media="(min-width: 750px)">
			<source srcset="<%=kkEng.getImageBase()%>/<%=banner1Medium.getItemValue()%>" media="(min-width: 440px)">
			<source srcset="<%=kkEng.getImageBase()%>/<%=banner1Small.getItemValue()%>" >
			<!--[if IE 9]></video><![endif]-->
			<img srcset="<%=kkEng.getImageBase()%>/<%=banner1.getItemValue()%>">
		</picture>
		<% } %>

	<% } %>
	<% if (!hideRow2) { %>
		<%if (miscItems.length > 8){%>
			<% com.konakart.appif.MiscItemIf banner2 = miscItems[3];%>
			<% com.konakart.appif.MiscItemIf banner2Medium = miscItems[4];%>
			<% com.konakart.appif.MiscItemIf banner2Small = miscItems[5];%>
			<% com.konakart.appif.MiscItemIf banner3 = miscItems[6];%>
			<% com.konakart.appif.MiscItemIf banner3Medium = miscItems[7];%>
			<% com.konakart.appif.MiscItemIf banner3Small = miscItems[8];%>

			<div id="banners">
			<%if (banner2.getCustom1() != null && banner2.getCustom1().length() > 0){%>

				<a href="<%=banner2.getCustom1()%>">
					<picture class="banner-double rounded-corners">	
						<!--[if IE 9]><video style="display: none;"><![endif]-->		
						<source srcset="<%=kkEng.getImageBase()%>/<%=banner2.getItemValue()%>" media="(min-width: 750px)" >
						<source srcset="<%=kkEng.getImageBase()%>/<%=banner2Medium.getItemValue()%>" media="(min-width: 440px)" >
						<source srcset="<%=kkEng.getImageBase()%>/<%=banner2Small.getItemValue()%>" >
						<!--[if IE 9]></video><![endif]-->
						<img srcset="<%=kkEng.getImageBase()%>/<%=banner2.getItemValue()%>">
					</picture>
				</a> 	
			
			<% } else { %>

				<picture class="banner-double rounded-corners">		
					<!--[if IE 9]><video style="display: none;"><![endif]-->	
					<source srcset="<%=kkEng.getImageBase()%>/<%=banner2.getItemValue()%>" media="(min-width: 750px)" >
					<source srcset="<%=kkEng.getImageBase()%>/<%=banner2Medium.getItemValue()%>" media="(min-width: 440px)" >
					<source srcset="<%=kkEng.getImageBase()%>/<%=banner2Small.getItemValue()%>" >
					<!--[if IE 9]></video><![endif]-->
					<img srcset="<%=kkEng.getImageBase()%>/<%=banner2.getItemValue()%>">
				</picture>

			<% } %>	
			<%if (banner3.getCustom1() != null && banner3.getCustom1().length() > 0){%>
				<a href="<%=banner3.getCustom1()%>">
					<picture class="banner-double rounded-corners last-child">	
						<!--[if IE 9]><video style="display: none;"><![endif]-->		
						<source srcset="<%=kkEng.getImageBase()%>/<%=banner3.getItemValue()%>" media="(min-width: 750px)" >
						<source srcset="<%=kkEng.getImageBase()%>/<%=banner3Medium.getItemValue()%>" media="(min-width: 440px)" >
						<source srcset="<%=kkEng.getImageBase()%>/<%=banner3Small.getItemValue()%>" >
						<!--[if IE 9]></video><![endif]-->
						<img srcset="<%=kkEng.getImageBase()%>/<%=banner2.getItemValue()%>">
					</picture>
				</a> 	
			<% } else { %>

				<picture class="banner-double rounded-corners last-child">	
					<!--[if IE 9]><video style="display: none;"><![endif]-->		
					<source srcset="<%=kkEng.getImageBase()%>/<%=banner3.getItemValue()%>" media="(min-width: 750px)" >
					<source srcset="<%=kkEng.getImageBase()%>/<%=banner3Medium.getItemValue()%>" media="(min-width: 440px)" >
					<source srcset="<%=kkEng.getImageBase()%>/<%=banner3Small.getItemValue()%>" >
					<!--[if IE 9]></video><![endif]-->
					<img srcset="<%=kkEng.getImageBase()%>/<%=banner2.getItemValue()%>">
				</picture>
			<% } %>	
			</div>
		<% } %>
	<% } %>	
<% } else if (miscItems != null && miscItems.length > 0 && !hideRow1){  %>
	<% com.konakart.appif.MiscItemIf banner1 = miscItems[0];%>
	<% if (banner1.getCustom1() != null && banner1.getCustom1().length() > 0){ %>		
		<a href="<%=banner1.getCustom1()%>">
			<picture class="rounded-corners">
				<!--[if IE 9]><video style="display: none;"><![endif]-->	
				<source srcset="<%=kkEng.getImageBase()%>/<%=banner1.getItemValue()%>" >
				<!--[if IE 9]></video><![endif]-->
				<img srcset="<%=kkEng.getImageBase()%>/<%=banner1.getItemValue()%>">
			</picture>
		</a> 	
	<% }else{ %>
		<picture class="rounded-corners">
			<!--[if IE 9]><video style="display: none;"><![endif]-->	
			<source srcset="<%=kkEng.getImageBase()%>/<%=banner1.getItemValue()%>" >
			<!--[if IE 9]></video><![endif]-->
			<img srcset="<%=kkEng.getImageBase()%>/<%=banner1.getItemValue()%>">
		</picture>
	<% } %>
<% } %>

