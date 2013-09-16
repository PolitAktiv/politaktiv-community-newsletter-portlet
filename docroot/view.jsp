<%
/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 *        
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
%>

<%@ page import="java.util.List" %>
<%@ page import="com.liferay.portal.service.UserLocalServiceUtil" %>
<%@ page import="com.liferay.portal.model.User" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects />
<portlet:defineObjects />

<% List<User> currentGroupUserList = UserLocalServiceUtil.getGroupUsers(themeDisplay.getScopeGroupId()); %>


<table class="taglib-search-iterator">
	<tr class="portlet-section-header results-header">
		<th><liferay-ui:message key="first-name"/></th>
	    <th><liferay-ui:message key="last-name"/></th>
	    <th><liferay-ui:message key="email"/></th>
	 </tr>
<% for(User currentGroupUser: currentGroupUserList){ %>
	<tr>
		<td><%= currentGroupUser.getFirstName() %> </td>
		<td><%= currentGroupUser.getLastName() %> </td>
		<td><a href="mailto:<%= currentGroupUser.getEmailAddress() %>" >
				<%= currentGroupUser.getEmailAddress() %>
			</a> </td>
	</tr>
<% } %>
</table>

<a href="<portlet:resourceURL />">
	CSV <liferay-ui:message key="download"/>
</a>