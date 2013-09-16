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

package org.politaktiv.communitynewsletter.application;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Date;

import javax.portlet.MimeResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.liferay.portal.model.User;

public class CommunityNewsletterPortlet extends MVCPortlet{
	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse){

		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		List<User> currentGroupUserList = null;
		try {
			currentGroupUserList= UserLocalServiceUtil.getGroupUsers(themeDisplay.getScopeGroupId());
			
			StringBuilder csvStringBuilder = new StringBuilder();
			ByteArrayOutputStream csvByteArrayOutputStream = new ByteArrayOutputStream();
			
			csvStringBuilder.append("first name");
			csvStringBuilder.append(",");
			csvStringBuilder.append("last name");
			csvStringBuilder.append(",");
			csvStringBuilder.append("email adress");
			csvStringBuilder.append("\n");
			
			for(User user: currentGroupUserList){
				csvStringBuilder.append(user.getFirstName());
				csvStringBuilder.append(",");
				csvStringBuilder.append(user.getLastName());
				csvStringBuilder.append(",");
				csvStringBuilder.append(user.getEmailAddress());
				csvStringBuilder.append("\n");
			}
			
			
			csvByteArrayOutputStream.write(csvStringBuilder.toString().getBytes());
			
			Date todayDate = new Date();
			resourceResponse.setCharacterEncoding("utf-8");
			resourceResponse.setContentType("text/csv");
			
			PortletResponseUtil.sendFile(resourceRequest,
					resourceResponse,
					themeDisplay.getScopeGroupName() + "-" + todayDate.toString() + "-CommunityMemberList.csv",
					csvByteArrayOutputStream.toByteArray());
			csvByteArrayOutputStream.close();
			
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
