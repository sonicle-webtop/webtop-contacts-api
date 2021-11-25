/*
 * Copyright (C) 2021 Sonicle S.r.l.
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
 * display the words "Copyright (C) 2021 Sonicle S.r.l.".
 */
package com.sonicle.webtop.contacts.io.input;

import com.sonicle.commons.LangUtils;
import com.sonicle.webtop.contacts.io.ContactInput;
import com.sonicle.webtop.contacts.model.ContactCompany;
import com.sonicle.webtop.contacts.model.ContactEx;
import com.sonicle.webtop.core.app.io.input.FileRowsReader;
import com.sonicle.webtop.core.app.io.input.TextFileReader;
import com.sonicle.webtop.core.app.io.input.WTReaderException;
import com.sonicle.webtop.core.app.util.log.BufferingLogHandler;
import com.sonicle.webtop.core.app.util.log.LogEntry;
import com.sonicle.webtop.core.app.util.log.LogMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;
import com.sonicle.webtop.contacts.io.ContactFileReader;
import com.sonicle.webtop.core.app.io.BeanHandler;

/**
 *
 * @author malbinola
 */
public class ContactTextFileReader extends TextFileReader implements ContactFileReader {
	
	public static final String[] MAPPING_TARGETS = new String[]{
		"Title","FirstName","LastName","DisplayName","Nickname",/*"Gender",*/
		"WorkAddress","WorkPostalCode","WorkCity","WorkState","WorkCountry",
		"WorkTelephone","WorkTelephone2","WorkMobile","WorkFax","WorkPager","WorkEmail","WorkInstantMsg",
		"HomeAddress","HomePostalCode","HomeCity","HomeState","HomeCountry",
		"HomeTelephone","HomeTelephone2","HomeFax","HomePager","HomeEmail","HomeInstantMsg",
		"OtherAddress","OtherPostalCode","OtherCity","OtherState","OtherCountry",
		"OtherEmail","OtherInstantMsg",
		"Company","Function","Department","Manager","Assistant","AssistantTelephone",
		"Partner",/*"Birthday","Anniversary",*/
		"Url","Notes"
	};
	protected List<FileRowsReader.FieldMapping> mappings = null;
	
	public ContactTextFileReader(CsvPreference pref, String charsetName) {
		super(pref, charsetName);
	}
	
	public void setMappings(List<FileRowsReader.FieldMapping> mappings) {
		this.mappings = mappings;
	}

	@Override
	public void read(File file, BeanHandler beanHandler) throws IOException, WTReaderException {
		HashMap<String, Integer> columnsIndexes = listColumnIndexes(file);
		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			CsvListReader lr = new CsvListReader(new InputStreamReader(fis, charset), pref);
			
			List<String> line = null;
			while ((line = lr.read()) != null) {
				if (lr.getLineNumber() < firstDataRow) continue;
				if ((lastDataRow != -1) && (lr.getLineNumber() > lastDataRow)) break;
				try {
					readRow(columnsIndexes, beanHandler, lr.getLineNumber(), line);
				} catch(Exception ex) {
					break;
				}
			}
		} finally {
			IOUtils.closeQuietly(fis);
		}
	}
	
	protected void readRow(HashMap<String, Integer> columnIndexes, BeanHandler beanHandler, int row, List<String> rowValues) throws Exception {
		BufferingLogHandler buffLogHandler = null;
		ContactEx contact = null;
		
		try {
			buffLogHandler = createBufferingLogHandler(new LogMessage(0, LogEntry.Level.INFO, "ROW #{}", row+1));
			contact = new ContactEx();
			for (FileRowsReader.FieldMapping mapping : mappings) {
				if (StringUtils.isBlank(mapping.source)) continue;
				if (!columnIndexes.containsKey(mapping.source)) throw new Exception("Index not found");
				Integer index = columnIndexes.get(mapping.source);
				fillContactByMapping(contact, mapping.target, rowValues.get(index));
			}
			if (contact.trimFieldLengths()) {
				log(buffLogHandler, 1, LogEntry.Level.WARN, "Some fields were truncated due to max-length");
			}
			
		} catch(Throwable t) {
			log(buffLogHandler, 0, LogEntry.Level.ERROR, "ROW [{}]. Reason: {}", row+1, LangUtils.getThrowableMessage(t));
			
		} finally {
			flushToLogHandler(buffLogHandler);
			beanHandler.handle(new ContactInput(contact, contact != null ? contact.getCompany() : null, null, null, null));
		}
	}
	
	private void fillContactByMapping(ContactEx contact, String target, String value) {
		if (target.equals("Title")) {
			contact.setTitle(value);
		} else if (target.equals("FirstName")) {
			contact.setFirstName(value);
		} else if (target.equals("LastName")) {
			contact.setLastName(value);
		} else if (target.equals("DisplayName")) {
			contact.setDisplayName(value);
		} else if (target.equals("Nickname")) {
			contact.setNickname(value);
		} else if (target.equals("Gender")) {
			//TODO: gestire sesso
			//contact.setGender(value);
		} else if (target.equals("Mobile") || target.equals("WorkMobile")) {
			contact.setMobile(value);	
		} else if (target.equals("Pager1") || target.equals("WorkPager")) {
			contact.setPager1(value);
		} else if (target.equals("Pager2") || target.equals("HomePager")) {
			contact.setPager2(value);
		} else if (target.equals("Email1") || target.equals("WorkEmail")) {
			contact.setEmail1(value);
		} else if (target.equals("Email2") || target.equals("HomeEmail")) {
			contact.setEmail2(value);
		} else if (target.equals("Email3") || target.equals("OtherEmail")) {
			contact.setEmail3(value);
		} else if (target.equals("InstantMsg1") || target.equals("WorkInstantMsg")) {
			contact.setInstantMsg1(value);
		} else if (target.equals("InstantMsg2") || target.equals("HomeInstantMsg")) {
			contact.setInstantMsg2(value);
		} else if (target.equals("InstantMsg3") || target.equals("OtherInstantMsg")) {
			contact.setInstantMsg3(value);
		} else if (target.equals("WorkAddress")) {
			contact.setWorkAddress(value);
		} else if (target.equals("WorkPostalCode")) {
			contact.setWorkPostalCode(value);
		} else if (target.equals("WorkCity")) {
			contact.setWorkCity(value);
		} else if (target.equals("WorkState")) {
			contact.setWorkState(value);
		} else if (target.equals("WorkCountry")) {
			contact.setWorkCountry(value);
		} else if (target.equals("WorkTelephone1") || target.equals("WorkTelephone")) {
			contact.setWorkTelephone1(value);
		} else if (target.equals("WorkTelephone2")) {
			contact.setWorkTelephone2(value);
		} else if (target.equals("WorkFax")) {
			contact.setWorkFax(value);
		} else if (target.equals("HomeAddress")) {
			contact.setHomeAddress(value);
		} else if (target.equals("HomePostalCode")) {
			contact.setHomePostalCode(value);
		} else if (target.equals("HomeCity")) {
			contact.setHomeCity(value);
		} else if (target.equals("HomeState")) {
			contact.setHomeState(value);
		} else if (target.equals("HomeCountry")) {
			contact.setHomeCountry(value);
		} else if (target.equals("HomeTelephone1") || target.equals("HomeTelephone")) {
			contact.setHomeTelephone1(value);
		} else if (target.equals("HomeTelephone2")) {
			contact.setHomeTelephone2(value);
		} else if (target.equals("HomeFax")) {
			contact.setHomeFax(value);
		} else if (target.equals("OtherAddress")) {
			contact.setOtherAddress(value);
		} else if (target.equals("OtherPostalCode")) {
			contact.setOtherPostalCode(value);
		} else if (target.equals("OtherCity")) {
			contact.setOtherCity(value);
		} else if (target.equals("OtherState")) {
			contact.setOtherState(value);
		} else if (target.equals("OtherCountry")) {
			contact.setOtherCountry(value);
		} else if (target.equals("Company")) {
			if (!StringUtils.isBlank(value)) {
				contact.setCompany(new ContactCompany(null, value));
			}
		} else if (target.equals("Function")) {
			contact.setFunction(value);
		} else if (target.equals("Department")) {
			contact.setDepartment(value);
		} else if (target.equals("Manager")) {
			contact.setManager(value);
		} else if (target.equals("Assistant")) {
			contact.setAssistant(value);
		} else if (target.equals("AssistantTelephone")) {
			contact.setAssistantTelephone(value);
		} else if (target.equals("Partner")) {
			contact.setPartner(value);
		} else if (target.equals("Birthday")) {
			//TODO: gestire compleanno
		} else if (target.equals("Anniversary")) {
			//TODO: gestire anniversario
		} else if (target.equals("Url")) {
			contact.setUrl(value);
		} else if (target.equals("Notes")) {
			contact.setNotes(value);
		}
	}
}
