/*
 * Copyright (C) 2017 Sonicle S.r.l.
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
 * display the words "Copyright (C) 2017 Sonicle S.r.l.".
 */
package com.sonicle.webtop.contacts.io;

import com.sonicle.webtop.contacts.model.Contact;
import com.sonicle.webtop.contacts.model.ContactPicture;
import com.sonicle.webtop.core.model.RecipientFieldCategory;
import com.sonicle.webtop.core.sdk.WTException;
import com.sonicle.webtop.core.util.LogEntries;
import com.sonicle.webtop.core.util.LogEntry;
import com.sonicle.webtop.core.util.MessageLogEntry;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.parameter.AddressType;
import ezvcard.parameter.EmailType;
import ezvcard.parameter.ImppType;
import ezvcard.parameter.TelephoneType;
import ezvcard.property.Address;
import ezvcard.property.Email;
import ezvcard.property.Gender;
import ezvcard.property.Impp;
import ezvcard.property.Nickname;
import ezvcard.property.Note;
import ezvcard.property.Photo;
import ezvcard.property.Role;
import ezvcard.property.StructuredName;
import ezvcard.property.Telephone;
import ezvcard.property.TextListProperty;
import ezvcard.property.Title;
import ezvcard.property.Url;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;

/**
 *
 * @author malbinola
 */
public class VCardInput {
	private final RecipientFieldCategory preferredTarget;
	
	public VCardInput() {
		this(RecipientFieldCategory.WORK);
	}
	
	public VCardInput(RecipientFieldCategory preferredTarget) {
		this.preferredTarget = preferredTarget;
	}
	
	public List<ContactInput> fromVCardFile(InputStream is, LogEntries log) throws WTException {
		// See https://tools.ietf.org/html/rfc6350
		// See http://www.w3.org/TR/vcard-rdf/
		ArrayList<ContactInput> results = new ArrayList<>();
		
		try {
			List<VCard> vcards = Ezvcard.parse(is).all();
			for (VCard vc : vcards) {
				final LogEntries vclog = (log != null) ? new LogEntries() : null;
				
				try {
					results.add(fromVCard(vc, vclog));
					if ((log != null) && (vclog != null)) {
						if (!vclog.isEmpty()) {
							log.addMaster(new MessageLogEntry(LogEntry.Level.WARN, "VCARD ['{1}', {0}]", vc.getUid(), vc.getFormattedName()));
							log.addAll(vclog);
						}
					}
				} catch(Throwable t) {
					if (log != null) log.addMaster(new MessageLogEntry(LogEntry.Level.ERROR, "VCARD ['{1}', {0}]. Reason: {3}", vc.getUid(), vc.getFormattedName(), t.getMessage()));
				}
			}
		} catch(IOException ex) {
			throw new WTException(ex, "Unable to read stream");
		}
		return results;
	}
	
	public ContactInput fromVCard(VCard vCard, LogEntries log) throws WTException {
		Contact contact = new Contact();
		ContactPicture picture = null;
		
		// UID
		if (vCard.getUid() != null) {
			contact.setPublicUid(deflt(vCard.getUid().getValue()));
		}
		
		// TITLE
		if (!vCard.getTitles().isEmpty()) {
			Title ti = vCard.getTitles().get(0);
			contact.setTitle(deflt(ti.getValue()));
			if ((log != null) && (vCard.getTitles().size() > 1)) {
				log.add(new MessageLogEntry(LogEntry.Level.WARN, "Many TITLE properties found"));
			}
		}
		
		// N -> FirstName/LastName
		if (!vCard.getStructuredNames().isEmpty()) {
			StructuredName sn = vCard.getStructuredNames().get(0);
			contact.setFirstName(deflt(sn.getGiven()));
			contact.setLastName(deflt(sn.getFamily()));
			if ((log != null) && (vCard.getStructuredNames().size() > 1)) {
				log.add(new MessageLogEntry(LogEntry.Level.WARN, "Many N properties found"));
			}
		}
		
		// NICKNAME
		if (!vCard.getNicknames().isEmpty()) {
			Nickname ni = vCard.getNicknames().get(0);
			contact.setNickname(deflt(flatten(ni)));
			if ((log != null) && (vCard.getNicknames().size() > 1)) {
				log.add(new MessageLogEntry(LogEntry.Level.WARN, "Many NICKNAME properties found"));
			}
		}
		
		// GENDER
		if (vCard.getGender() != null) {
			contact.setGender(fromGender(vCard.getGender()));
		}
		
		// ADR
		if (!vCard.getAddresses().isEmpty()) {
			for (Address add : vCard.getAddresses()) {
				Set<AddressType> types = add.getTypes();
				if (types.contains(AddressType.WORK)) {
					contact.setWorkAddress(deflt(add.getStreetAddress()));
					contact.setWorkPostalCode(deflt(add.getPostalCode()));
					contact.setWorkCity(deflt(add.getLocality()));
					contact.setWorkState(deflt(add.getRegion()));
					contact.setWorkCountry(deflt(add.getCountry()));
				} else if (types.contains(AddressType.HOME)) {
					contact.setHomeAddress(deflt(add.getStreetAddress()));
					contact.setHomePostalCode(deflt(add.getPostalCode()));
					contact.setHomeCity(deflt(add.getLocality()));
					contact.setHomeState(deflt(add.getRegion()));
					contact.setHomeCountry(deflt(add.getCountry()));
				} else if (!types.contains(AddressType.WORK) && !types.contains(AddressType.HOME)) {
					contact.setOtherAddress(deflt(add.getStreetAddress()));
					contact.setOtherPostalCode(deflt(add.getPostalCode()));
					contact.setOtherCity(deflt(add.getLocality()));
					contact.setOtherState(deflt(add.getRegion()));
					contact.setOtherCountry(deflt(add.getCountry()));
				}
			}
		}
		
		// TEL
		if (!vCard.getTelephoneNumbers().isEmpty()) {
			for (Telephone tel : vCard.getTelephoneNumbers()) {
				Set<TelephoneType> types = tel.getTypes();
				if (types.contains(TelephoneType.WORK)) {
					if (types.contains(TelephoneType.VOICE)) {
						contact.setWorkTelephone(deflt(tel.getText()));
					} else if (types.contains(TelephoneType.CELL)) {
						contact.setWorkMobile(deflt(tel.getText()));
					} else if (types.contains(TelephoneType.FAX)) {
						contact.setWorkFax(deflt(tel.getText()));
					} else if (types.contains(TelephoneType.PAGER)) {
						contact.setWorkPager(deflt(tel.getText()));
					} else if (types.contains(TelephoneType.TEXT)) {
						contact.setWorkTelephone2(deflt(tel.getText()));
					}
				} else if (types.contains(TelephoneType.HOME)) {
					if (types.contains(TelephoneType.VOICE)) {
						contact.setHomeTelephone(deflt(tel.getText()));
					} else if (types.contains(TelephoneType.FAX)) {
						contact.setHomeFax(deflt(tel.getText()));
					} else if (types.contains(TelephoneType.PAGER)) {
						contact.setHomePager(deflt(tel.getText()));
					} else if (types.contains(TelephoneType.CELL)) {
						contact.setHomeTelephone2(deflt(tel.getText()));
					} else if (types.contains(TelephoneType.TEXT)) {
						contact.setHomeTelephone2(deflt(tel.getText()));
					}
				}
			}
		}
		
		// EMAIL
		if (!vCard.getEmails().isEmpty()) {
			HashMap<RecipientFieldCategory, List<Email>> map = analyzeEmails(vCard.getEmails());
			for (RecipientFieldCategory key : map.keySet()) {
				final Email email = map.get(key).get(0);
				final String value = deflt(email.getValue());
				if (RecipientFieldCategory.WORK.equals(key)) {
					contact.setWorkEmail(value);
				} else if (map.containsKey(RecipientFieldCategory.HOME)) {
					contact.setHomeEmail(value);
				} else {
					contact.setOtherEmail(value);
				}
			}
			/*
			HashMap<RecipientFieldCategory, Email> map = fromEmails(vCard.getEmails());
			for (RecipientFieldCategory key : map.keySet()) {
				final String email = deflt(map.get(key).getValue());
				if (RecipientFieldCategory.WORK.equals(key)) {
					contact.setWorkEmail(email);
				} else if (map.containsKey(RecipientFieldCategory.HOME)) {
					contact.setHomeEmail(email);
				} else {
					contact.setOtherEmail(email);
				}
			}
			*/
			/*
			for(Email em : vCard.getEmails()) {
				Set<EmailType> types = em.getTypes();
				if (types.contains(EmailType.WORK)) {
					contact.setWorkEmail(deflt(em.getValue()));
				} else if (types.contains(EmailType.HOME)) {
					contact.setHomeEmail(deflt(em.getValue()));
				} else if (!types.contains(EmailType.WORK) && !types.contains(EmailType.HOME)) {
					contact.setOtherEmail(deflt(em.getValue()));
				}
			}
			*/
		}
		
		// IMPP -> InstantMsg
		if (!vCard.getImpps().isEmpty()) {
			for(Impp im : vCard.getImpps()) {
				Set<ImppType> types = im.getTypes();
				URI uri = im.getUri();
				if (uri == null) continue;
				if (types.contains(ImppType.WORK)) {
					contact.setWorkInstantMsg(deflt(uri.toString()));
				} else if (types.contains(ImppType.HOME)) {
					contact.setHomeInstantMsg(deflt(uri.toString()));
				} else if (!types.contains(ImppType.WORK) && !types.contains(ImppType.HOME)) {
					contact.setOtherInstantMsg(deflt(uri.toString()));
				}
			}
		}
		
		// ORG -> Company/Department
		if (vCard.getOrganization() != null) {
			List<String> values = vCard.getOrganization().getValues();
			if(!values.isEmpty()) {
				contact.setCompany(deflt(values.get(0)));
				contact.setDepartment(deflt(values.get(1)));
			}
		}
		
		// ROLE -> Function
		if (!vCard.getRoles().isEmpty()) {
			Role ro = vCard.getRoles().get(0);
			contact.setFunction(deflt(ro.getValue()));
			if ((log != null) && (vCard.getRoles().size() > 1)) {
				log.add(new MessageLogEntry(LogEntry.Level.WARN, "Many ROLE properties found"));
			}
		}
		
		//TODO: come riempiamo il campo manager?
		//TODO: come riempiamo il campo assistant?
		//TODO: come riempiamo il campo telephoneAssistant?
		
		// BDAY
		if (vCard.getBirthday() != null) {
			contact.setBirthday(new LocalDate(vCard.getBirthday().getDate()));
		}
		
		// ANNIVERSARY
		if (vCard.getAnniversary()!= null) {
			contact.setAnniversary(new LocalDate(vCard.getAnniversary().getDate()));
		}
		
		// URL
		if (!vCard.getUrls().isEmpty()) {
			Url ur = vCard.getUrls().get(0);
			contact.setUrl(deflt(ur.getValue()));
			if ((log != null) && (vCard.getUrls().size() > 1)) {
				log.add(new MessageLogEntry(LogEntry.Level.WARN, "Many URL properties found"));
			}
		}
		
		// NOTE
		if (!vCard.getNotes().isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for(Note no : vCard.getNotes()) {
				sb.append(no.getValue());
				sb.append("\n");
			}
			contact.setNotes(sb.toString());
		}
		
		// PHOTO
		if (!vCard.getPhotos().isEmpty()) {
			Photo po = vCard.getPhotos().get(0);
			picture = new ContactPicture(po.getContentType().getMediaType(), po.getData());
			if ((log != null) && (vCard.getPhotos().size() > 1)) {
				log.add(new MessageLogEntry(LogEntry.Level.WARN, "Many PHOTO properties found"));
			}
		} else {
			contact.setHasPicture(false);
		}
		
		return new ContactInput(contact, picture);
	}
	
	public HashMap<RecipientFieldCategory, Email> fromEmails(List<Email> emails) {
		HashMap<RecipientFieldCategory, Email> map = new HashMap<>();
		
		for (Email email : emails) {
			final Set<EmailType> types = email.getTypes();
			RecipientFieldCategory key = null;
			if (types.contains(EmailType.WORK)) {
				key = RecipientFieldCategory.WORK;
			} else if (types.contains(EmailType.HOME)) {
				key = RecipientFieldCategory.HOME;
			} else if (types.contains(EmailType.INTERNET)) {
				key = preferredTarget;
			} else {
				key = RecipientFieldCategory.OTHER;
			}
			
			if (!map.containsKey(key)) {
				map.put(key, email);
			} else {
				if (ObjectUtils.compare(map.get(key).getPref(), email.getPref()) > 0) {
					map.put(key, email);
				}
			}
		}
		return map;
	}
	
	public HashMap<RecipientFieldCategory, List<Email>> analyzeEmails(List<Email> emails) {
		HashMap<RecipientFieldCategory, List<Email>> map = new HashMap<>();
		
		for (Email email : emails) {
			final Set<EmailType> types = email.getTypes();
			RecipientFieldCategory key = null;
			if (types.contains(EmailType.WORK)) {
				key = RecipientFieldCategory.WORK;
			} else if (types.contains(EmailType.HOME)) {
				key = RecipientFieldCategory.HOME;
			} else if (types.contains(EmailType.INTERNET)) {
				key = preferredTarget;
			} else {
				key = RecipientFieldCategory.OTHER;
			}
			
			if (!map.containsKey(key)) {
				map.put(key, Arrays.asList(email));
			} else {
				map.get(key).add(email);
				Collections.sort(map.get(key), new Comparator<Email>() {
					@Override
					public int compare(final Email o1, final Email o2) {
						int i1 = (o1.getPref() != null) ? o1.getPref() : 100;
						int i2 = (o2.getPref() != null) ? o2.getPref() : 100;
						return Integer.compare(i1, i2);
					}
				});
			}
		}
		return map;
	}
	
	public Contact.Gender fromGender(Gender gender) {
		if (gender.isMale()) return Contact.Gender.MALE;
		if (gender.isFemale()) return Contact.Gender.FEMALE;
		if (gender.isOther()) return Contact.Gender.OTHER;
		return null;
	}
	
	private String deflt(String s) {
		return StringUtils.defaultIfEmpty(s, null);
	}
	
	private String flatten(TextListProperty textListProp) {
		return flatten(textListProp, " ");
	}
	
	private String flatten(TextListProperty textListProp, String separator) {
		return StringUtils.join(textListProp.getValues(), separator);
	}
}
