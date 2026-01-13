/*
 * Copyright (C) 2024 Sonicle S.r.l.
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License version 3 as published by
 * the Free Software Foundation with the addition of the following permission
 * added to Section 15 as permitted in Section 7(a): FOR ANY PART OF THE COVERED
 * WORK IN WHICH THE COPYRIGHT IS OWNED BY SONICLE, SONICLE DISCLAIMS THE
 * WARRANTY OF NON INFRINGEMENT OF THIRD PARTY RIGHTS.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see http://www.gnu.org/licenses or write to
 * the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301 USA.
 *
 * You can contact Sonicle S.r.l. at email address sonicle[at]sonicle[dot]com
 *
 * The interactive user interfaces in modified source and object code versions
 * of this program must display Appropriate Legal Notices, as required under
 * Section 5 of the GNU Affero General Public License version 3.
 *
 * In accordance with Section 7(b) of the GNU Affero General Public License
 * version 3, these Appropriate Legal Notices must retain the display of the
 * Sonicle logo and Sonicle copyright notice. If the display of the logo is not
 * reasonably feasible for technical reasons, the Appropriate Legal Notices must
 * display the words "Copyright (C) 2024 Sonicle S.r.l.".
 */
package com.sonicle.webtop.contacts.io;

import com.sonicle.webtop.contacts.model.ContactBase.Gender;
import com.sonicle.webtop.contacts.model.ContactCompany;
import com.sonicle.webtop.contacts.model.ContactEx;
import com.sonicle.webtop.contacts.model.ShowBy;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.supercsv.io.CsvListWriter;

/**
 *
 * @author gabriele.bulfon
 */
public class CSVOutput {
	
	DateFormat df = new SimpleDateFormat("yyyyMMdd");
	
	String headers[] = {
			"Anniversary",
			"Assistant",
			"AssistantTelephone",
			"Birthday",
			"CategoryId",
			"CategoryDescription",
			"Company",
			"Department",
			"Email1",
			"Email2",
			"Email3",
			"FirstName",
			"Function",
			"Gender",
			"HomeAddress",
			"HomeCity",
			"HomeCountry",
			"HomeFax",
			"HomePostalCode",
			"HomeState",
			"HomeTelephone1",
			"HomeTelephone2",
			"InstantMsg1",
			"InstantMsg2",
			"InstantMsg3",
			"LastName",
			"Manager",
			"Mobile",
			"Nickname",
			"Notes",
			"OtherAddress",
			"OtherCity",
			"OtherCountry",
			"OtherPostalCode",
			"OtherState",
			"Pager1",
			"Pager2",
			"Partner",
			"Title",
			"Url",
			"TaxCode",
			"VATNumber",
			"WorkAddress",
			"WorkCity",
			"WorkCountry",
			"WorkFax",
			"WorkPostalCode",
			"WorkState",
			"WorkTelephone1",
			"WorkTelephone2",
			"TagIds",
			"TagNames"
	};
	
	public void writeHeader(CsvListWriter wr) throws IOException {
		wr.write(headers);
	}
	
	public void writeContact(ContactEx c, String categoryName, CsvListWriter wr, Map<String, String> tagNamesById) throws IOException {
		Set<String> tagIdsArray = c.getTagsOrEmpty();
		ArrayList<String> tagNamesArray = new ArrayList<>();
		for(String tagId: tagIdsArray) {
			String tagName = tagNamesById.get(tagId);
			if (tagName!=null) tagNamesArray.add(tagName);
		}
		String tagIds = StringUtils.join(tagIdsArray, ",");
		String tagNames = StringUtils.join(tagNamesArray, ",");
		
		wr.write(
			new String[] {
				d2s(c.getAnniversary()),
				n2b(c.getAssistant()),
				n2b(c.getAssistantTelephone()),
				d2s(c.getBirthday()),
				n2b(c.getCategoryId()),
				n2b(categoryName),
				n2b(c.getCompany()),
				n2b(c.getDepartment()),
				n2b(c.getEmail1()),
				n2b(c.getEmail2()),
				n2b(c.getEmail3()),
				n2b(c.getFirstName()),
				n2b(c.getFunction()),
				n2b(c.getGender()),
				n2b(c.getHomeAddress()),
				n2b(c.getHomeCity()),
				n2b(c.getHomeCountry()),
				n2b(c.getHomeFax()),
				n2b(c.getHomePostalCode()),
				n2b(c.getHomeState()),
				n2b(c.getHomeTelephone1()),
				n2b(c.getHomeTelephone2()),
				n2b(c.getInstantMsg1()),
				n2b(c.getInstantMsg2()),
				n2b(c.getInstantMsg3()),
				n2b(c.getLastName()),
				n2b(c.getManager()),
				n2b(c.getMobile()),
				n2b(c.getNickname()),
				n2b(c.getNotes()),
				n2b(c.getOtherAddress()),
				n2b(c.getOtherCity()),
				n2b(c.getOtherCountry()),
				n2b(c.getOtherPostalCode()),
				n2b(c.getOtherState()),
				n2b(c.getPager1()),
				n2b(c.getPager2()),
				n2b(c.getPartner()),
				n2b(c.getTitle()),
				n2b(c.getUrl()),
				n2b(c.getTaxCode()),
				n2b(c.getVATNumber()),
				n2b(c.getWorkAddress()),
				n2b(c.getWorkCity()),
				n2b(c.getWorkCountry()),
				n2b(c.getWorkFax()),
				n2b(c.getWorkPostalCode()),
				n2b(c.getWorkState()),
				n2b(c.getWorkTelephone1()),
				n2b(c.getWorkTelephone2()),
				n2b(tagIds),
				n2b(tagNames)
			}
		);
	}
	
	private String n2b(String s) {
		return StringUtils.defaultIfBlank(s, "");
	}
	
	private String n2b(Integer i) {
		if (i!=null) return i.toString();
		return "";
	}
	
	private String n2b(ContactCompany cc) {
		if (cc!=null) return cc.getCompanyDescription();
		return "";
	}
	
	private String n2b(Gender g) {
		if (g!=null) return g.toString();
		return "";
	}
	
	private String d2s(LocalDate d) {
		if (d!=null) return df.format(d.toDate());
		return "";
	}
}
