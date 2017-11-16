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

import com.google.gson.annotations.SerializedName;
import com.sonicle.webtop.contacts.model.Contact;
import com.sonicle.webtop.contacts.model.ContactPicture;
import com.sonicle.webtop.core.sdk.WTException;
import com.sonicle.webtop.core.util.LogEntries;
import com.sonicle.webtop.core.util.LogEntry;
import com.sonicle.webtop.core.util.MessageLogEntry;
import eu.medsea.mimeutil.MimeException;
import eu.medsea.mimeutil.MimeType;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;

/**
 *
 * @author malbinola
 */
public class VCardInput {
	private final MatchCategory preferredMatch;
	private final boolean relaxedMatching = true;
	
	public VCardInput() {
		this(MatchCategory.WORK);
	}
	
	public VCardInput(MatchCategory preferredMatch) {
		this.preferredMatch = preferredMatch;
	}
	
	public List<ContactInput> fromVCardFile(InputStream is, LogEntries log) throws WTException {
		try {
			final List<VCard> vCards = Ezvcard.parse(is).all();
			return fromVCardFile(vCards, log);
		} catch(IOException ex) {
			throw new WTException(ex, "Unable to read stream");
		}
	}
	
	public List<ContactInput> fromVCardFile(Collection<VCard> vCards, LogEntries log) throws WTException {
		// See https://tools.ietf.org/html/rfc6350
		// See http://www.w3.org/TR/vcard-rdf/
		ArrayList<ContactInput> results = new ArrayList<>();
		
		for (VCard vc : vCards) {
			final LogEntries vclog = (log != null) ? new LogEntries() : null;

			try {
				final ContactInput result = fromVCard(vc, vclog);
				if (result.contact.trimFieldLengths()) {
					if (vclog != null) vclog.add(new MessageLogEntry(LogEntry.Level.WARN, "Some fields were truncated due to max-length"));
				}
				results.add(result);
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
		return results;
	}
	
	private void setFallbackTelephone(Contact contact, TelephoneType type, String value) {
		if (TelephoneType.VOICE.equals(type)) {
			if (StringUtils.isBlank(contact.getWorkTelephone())) {
				contact.setWorkTelephone(value);
				return;
			}
			if (StringUtils.isBlank(contact.getHomeTelephone())) {
				contact.setHomeTelephone(value);
			}
		} else if (TelephoneType.FAX.equals(type)) {
			if (StringUtils.isBlank(contact.getWorkFax())) {
				contact.setWorkFax(value);
				return;
			}
			if (StringUtils.isBlank(contact.getHomeFax())) {
				contact.setHomeFax(value);
			}
		} else if (TelephoneType.PAGER.equals(type)) {
			if (StringUtils.isBlank(contact.getWorkPager())) {
				contact.setWorkPager(value);
				return;
			}
			if (StringUtils.isBlank(contact.getHomePager())) {
				contact.setHomePager(value);
			}
		} else if (TelephoneType.CELL.equals(type)) {
			if (StringUtils.isBlank(contact.getWorkMobile())) {
				contact.setWorkMobile(value);
			}
		} else if (TelephoneType.TEXT.equals(type)) {
			if (StringUtils.isBlank(contact.getWorkTelephone2())) {
				contact.setWorkTelephone2(value);
				return;
			}
			if (StringUtils.isBlank(contact.getHomeTelephone2())) {
				contact.setHomeTelephone2(value);
			}
		} else {
			if (StringUtils.isBlank(contact.getWorkTelephone())) {
				contact.setWorkTelephone(value);
				return;
			}
			if (StringUtils.isBlank(contact.getHomeTelephone())) {
				contact.setHomeTelephone(value);
			}
		}
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
			HashMap<MatchCategory, LinkedList<Address>> map = analyzeAddresses(vCard.getAddresses());
			for (MatchCategory key : map.keySet()) { // Strict matching
				if (MatchCategory.NONE.equals(key)) continue;
				final Address address = map.get(key).pollFirst();
				setAddress(contact, key, address);
			}
			if (relaxedMatching) {
				for (MatchCategory key : map.keySet()) {
					boolean shouldBreak = false;
					final int count = map.get(key).size();
					for (int i=1; i<=count; i++) {
						final Address address = map.get(key).pollFirst();
						if (!setAddressRelaxed(contact, address)) {
							shouldBreak = true;
							break;
						}
					}
					if (shouldBreak) break;
				}
			}
		}
		
		// TEL
		if (!vCard.getTelephoneNumbers().isEmpty()) {
			HashMap<MatchCategory, LinkedList<Telephone>> map = analyzeTelephones(vCard.getTelephoneNumbers());
			for (MatchCategory key : map.keySet()) { // Strict matching
				if (MatchCategory.NONE.equals(key)) continue;
				final Telephone telephone = map.get(key).pollFirst();
				setTelephone(contact, key, telephone);
			}
			if (relaxedMatching) {
				for (MatchCategory key : map.keySet()) {
					boolean shouldBreak = false;
					final int count = map.get(key).size();
					for (int i=1; i<=count; i++) {
						final Telephone telephone = map.get(key).pollFirst();
						if (!setTelephoneRelaxed(contact, telephone)) {
							shouldBreak = true;
							break;
						}
					}
					if (shouldBreak) break;
				}
			}
		}
		
		/*
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
					} else {
						if (StringUtils.isBlank(contact.getWorkTelephone())) {
							contact.setWorkTelephone(deflt(tel.getText()));
						}
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
					} else {
						if (StringUtils.isBlank(contact.getHomeTelephone())) {
							contact.setHomeTelephone(deflt(tel.getText()));
						}
					}
				} else {
					final String value = deflt(tel.getText());
					for (TelephoneType type : types) {
						setFallbackTelephone(contact, type, value);
					}
				}
			}
		}
		*/
		
		// EMAIL
		if (!vCard.getEmails().isEmpty()) {
			HashMap<MatchCategory, LinkedList<Email>> map = analyzeEmails(vCard.getEmails());
			for (MatchCategory key : map.keySet()) { // Strict matching
				if (MatchCategory.NONE.equals(key)) continue;
				final Email email = map.get(key).pollFirst();
				setEmail(contact, key, email);
			}
			if (relaxedMatching) {
				for (MatchCategory key : map.keySet()) {
					boolean shouldBreak = false;
					final int count = map.get(key).size();
					for (int i=1; i<=count; i++) {
						final Email email = map.get(key).pollFirst();
						if (!setEmailRelaxed(contact, email)) {
							shouldBreak = true;
							break;
						}
					}
					if (shouldBreak) break;
				}
			}
		}
		
		// IMPP -> InstantMsg
		if (!vCard.getImpps().isEmpty()) {
			HashMap<MatchCategory, LinkedList<Impp>> map = analyzeIMPPs(vCard.getImpps());
			for (MatchCategory key : map.keySet()) { // Strict matching
				if (MatchCategory.NONE.equals(key)) continue;
				final Impp impp = map.get(key).pollFirst();
				setIMPP(contact, key, impp);
			}
			if (relaxedMatching) {
				for (MatchCategory key : map.keySet()) {
					boolean shouldBreak = false;
					final int count = map.get(key).size();
					for (int i=1; i<=count; i++) {
						final Impp impp = map.get(key).pollFirst();
						if (!setIMPPRelaxed(contact, impp)) {
							shouldBreak = true;
							break;
						}
					}
					if (shouldBreak) break;
				}
			}
		}
		
		// ORG -> Company/Department
		if (vCard.getOrganization() != null) {
			List<String> values = vCard.getOrganization().getValues();
			if (!values.isEmpty()) {
				contact.setCompany(deflt(values.get(0)));
				if (values.size() > 1) contact.setDepartment(deflt(values.get(1)));
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
			for (Note no : vCard.getNotes()) {
				sb.append(no.getValue());
				sb.append("\n");
			}
			contact.setNotes(sb.toString());
		}
		
		// PHOTO
		contact.setHasPicture(false);
		if (!vCard.getPhotos().isEmpty()) {
			if ((log != null) && (vCard.getPhotos().size() > 1)) {
				log.add(new MessageLogEntry(LogEntry.Level.WARN, "Many PHOTO properties found"));
			}
			final Photo pho = vCard.getPhotos().get(0);
			final String mtype = getMediaType(pho);
			if (mtype != null) {
				picture = new ContactPicture(mtype, pho.getData());
				contact.setHasPicture(true);
			} else {
				if (log != null) log.add(new MessageLogEntry(LogEntry.Level.WARN, "PHOTO skipped: unspecified content type"));
			}
		}
		
		return new ContactInput(contact, picture);
	}
	
	public HashMap<MatchCategory, LinkedList<Address>> analyzeAddresses(List<Address> addresses) {
		HashMap<MatchCategory, LinkedList<Address>> map = new LinkedHashMap<>();
		
		for (Address address : addresses) {
			final Set<AddressType> types = address.getTypes();
			MatchCategory key = null;
			if (types.contains(AddressType.WORK)) {
				key = MatchCategory.WORK;
			} else if (types.contains(AddressType.HOME)) {
				key = MatchCategory.HOME;
			} else {
				key = MatchCategory.NONE;
			}
			
			if (!map.containsKey(key)) {
				map.put(key, new LinkedList<>(Arrays.asList(address)));
			} else {
				map.get(key).add(address);
				Collections.sort(map.get(key), new Comparator<Address>() {
					@Override
					public int compare(final Address o1, final Address o2) {
						int i1 = (o1.getPref() != null) ? o1.getPref() : 100;
						int i2 = (o2.getPref() != null) ? o2.getPref() : 100;
						return Integer.compare(i1, i2);
					}
				});
			}
		}
		return map;
	}
	
	public HashMap<MatchCategory, LinkedList<Telephone>> analyzeTelephones(List<Telephone> telephones) {
		HashMap<MatchCategory, LinkedList<Telephone>> map = new LinkedHashMap<>();
		
		for (Telephone telephone : telephones) {
			final Set<TelephoneType> types = telephone.getTypes();
			MatchCategory key = null;
			if (types.contains(TelephoneType.WORK)) {
				key = MatchCategory.WORK;
			} else if (types.contains(TelephoneType.HOME)) {
				key = MatchCategory.HOME;
			} else {
				key = MatchCategory.NONE;
			}
			
			if (!map.containsKey(key)) {
				map.put(key, new LinkedList<>(Arrays.asList(telephone)));
			} else {
				map.get(key).add(telephone);
				Collections.sort(map.get(key), new Comparator<Telephone>() {
					@Override
					public int compare(final Telephone o1, final Telephone o2) {
						int i1 = (o1.getPref() != null) ? o1.getPref() : 100;
						int i2 = (o2.getPref() != null) ? o2.getPref() : 100;
						return Integer.compare(i1, i2);
					}
				});
			}
		}
		return map;
	}
	
	public HashMap<MatchCategory, LinkedList<Email>> analyzeEmails(List<Email> emails) {
		HashMap<MatchCategory, LinkedList<Email>> map = new LinkedHashMap<>();
		
		for (Email email : emails) {
			final Set<EmailType> types = email.getTypes();
			MatchCategory key = null;
			if (types.contains(EmailType.WORK)) {
				key = MatchCategory.WORK;
			} else if (types.contains(EmailType.HOME)) {
				key = MatchCategory.HOME;
			} else if (types.contains(EmailType.INTERNET)) {
				key = preferredMatch;
			} else {
				key = MatchCategory.NONE;
			}
			
			if (!map.containsKey(key)) {
				map.put(key, new LinkedList<>(Arrays.asList(email)));
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
	
	public HashMap<MatchCategory, LinkedList<Impp>> analyzeIMPPs(List<Impp> emails) {
		HashMap<MatchCategory, LinkedList<Impp>> map = new LinkedHashMap<>();
		
		for (Impp email : emails) {
			final Set<ImppType> types = email.getTypes();
			MatchCategory key = null;
			if (types.contains(ImppType.WORK)) {
				key = MatchCategory.WORK;
			} else if (types.contains(ImppType.HOME)) {
				key = MatchCategory.HOME;
			} else {
				key = MatchCategory.NONE;
			}
			
			if (!map.containsKey(key)) {
				map.put(key, new LinkedList<>(Arrays.asList(email)));
			} else {
				map.get(key).add(email);
				Collections.sort(map.get(key), new Comparator<Impp>() {
					@Override
					public int compare(final Impp o1, final Impp o2) {
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
	
	private void setAddress(Contact contact, MatchCategory category, Address address) {
		if (MatchCategory.WORK.equals(category)) {
			contact.setWorkAddress(deflt(address.getStreetAddress()));
			contact.setWorkPostalCode(deflt(address.getPostalCode()));
			contact.setWorkCity(deflt(address.getLocality()));
			contact.setWorkState(deflt(address.getRegion()));
			contact.setWorkCountry(deflt(address.getCountry()));
		} else if (MatchCategory.HOME.equals(category)) {
			contact.setHomeAddress(deflt(address.getStreetAddress()));
			contact.setHomePostalCode(deflt(address.getPostalCode()));
			contact.setHomeCity(deflt(address.getLocality()));
			contact.setHomeState(deflt(address.getRegion()));
			contact.setHomeCountry(deflt(address.getCountry()));
		} else if (MatchCategory.OTHER.equals(category)) {
			contact.setOtherAddress(deflt(address.getStreetAddress()));
			contact.setOtherPostalCode(deflt(address.getPostalCode()));
			contact.setOtherCity(deflt(address.getLocality()));
			contact.setOtherState(deflt(address.getRegion()));
			contact.setOtherCountry(deflt(address.getCountry()));
		}
	}
	
	private boolean setAddressRelaxed(Contact contact, Address address) {
		if (contact.isWorkAddressEmpty()) {
			contact.setWorkAddress(deflt(address.getStreetAddress()));
			contact.setWorkPostalCode(deflt(address.getPostalCode()));
			contact.setWorkCity(deflt(address.getLocality()));
			contact.setWorkState(deflt(address.getRegion()));
			contact.setWorkCountry(deflt(address.getCountry()));
			return true;
		} else if (contact.isHomeAddressEmpty()) {
			contact.setHomeAddress(deflt(address.getStreetAddress()));
			contact.setHomePostalCode(deflt(address.getPostalCode()));
			contact.setHomeCity(deflt(address.getLocality()));
			contact.setHomeState(deflt(address.getRegion()));
			contact.setHomeCountry(deflt(address.getCountry()));
			return true;
		} else if (contact.isOtherAddressEmpty()) {
			contact.setOtherAddress(deflt(address.getStreetAddress()));
			contact.setOtherPostalCode(deflt(address.getPostalCode()));
			contact.setOtherCity(deflt(address.getLocality()));
			contact.setOtherState(deflt(address.getRegion()));
			contact.setOtherCountry(deflt(address.getCountry()));
			return true;
		}
		return false;
	}
	
	private void setTelephone(Contact contact, MatchCategory category, Telephone telephone) {
		final Set<TelephoneType> types = telephone.getTypes();
		if (MatchCategory.WORK.equals(category)) {
			if (types.contains(TelephoneType.VOICE)) {
				contact.setWorkTelephone(deflt(telephone.getText()));
			} else if (types.contains(TelephoneType.FAX)) {
				contact.setWorkFax(deflt(telephone.getText()));
			} else if (types.contains(TelephoneType.PAGER)) {
				contact.setWorkPager(deflt(telephone.getText()));
			} else if (types.contains(TelephoneType.CELL)) {
				contact.setWorkMobile(deflt(telephone.getText()));
			} else if (types.contains(TelephoneType.TEXT)) {
				contact.setWorkTelephone2(deflt(telephone.getText()));
			} else {
				contact.setWorkTelephone(deflt(telephone.getText()));
			}
		} else if (MatchCategory.HOME.equals(category)) {
			if (types.contains(TelephoneType.VOICE)) {
				contact.setHomeTelephone(deflt(telephone.getText()));
			} else if (types.contains(TelephoneType.FAX)) {
				contact.setHomeFax(deflt(telephone.getText()));
			} else if (types.contains(TelephoneType.PAGER)) {
				contact.setHomePager(deflt(telephone.getText()));
			} else if (types.contains(TelephoneType.TEXT)) {
				contact.setHomeTelephone2(deflt(telephone.getText()));
			} else {
				contact.setHomeTelephone(deflt(telephone.getText()));
			}
		}
	}
	
	private boolean setTelephoneRelaxed(Contact contact, Telephone telephone) {
		final Set<TelephoneType> types = telephone.getTypes();
		if (types.contains(TelephoneType.VOICE)) {
			if (StringUtils.isBlank(contact.getWorkTelephone())) {
				contact.setWorkTelephone(deflt(telephone.getText()));
				return true;
			} else if (StringUtils.isBlank(contact.getHomeTelephone())) {
				contact.setHomeTelephone(deflt(telephone.getText()));
				return true;
			}
		} else if (types.contains(TelephoneType.FAX)) {
			if (StringUtils.isBlank(contact.getWorkFax())) {
				contact.setWorkFax(deflt(telephone.getText()));
				return true;
			} else if (StringUtils.isBlank(contact.getHomeFax())) {
				contact.setHomeFax(deflt(telephone.getText()));
				return true;
			}
		} else if (types.contains(TelephoneType.PAGER)) {
			if (StringUtils.isBlank(contact.getWorkPager())) {
				contact.setWorkPager(deflt(telephone.getText()));
				return true;
			} else if (StringUtils.isBlank(contact.getHomePager())) {
				contact.setHomePager(deflt(telephone.getText()));
				return true;
			}
		} else if (types.contains(TelephoneType.CELL)) {
			if (StringUtils.isBlank(contact.getWorkMobile())) {
				contact.setWorkMobile(deflt(telephone.getText()));
				return true;
			} else if (StringUtils.isBlank(contact.getWorkTelephone())) {
				contact.setWorkTelephone(deflt(telephone.getText()));
				return true;
			} else if (StringUtils.isBlank(contact.getWorkTelephone2())) {
				contact.setWorkTelephone2(deflt(telephone.getText()));
				return true;
			} else if (StringUtils.isBlank(contact.getHomeTelephone())) {
				contact.setHomeTelephone(deflt(telephone.getText()));
				return true;
			} else if (StringUtils.isBlank(contact.getHomeTelephone2())) {
				contact.setHomeTelephone2(deflt(telephone.getText()));
				return true;
			}			
		} else if (types.contains(TelephoneType.TEXT)) {
			if (StringUtils.isBlank(contact.getWorkTelephone2())) {
				contact.setWorkTelephone2(deflt(telephone.getText()));
				return true;
			} else if (StringUtils.isBlank(contact.getHomeTelephone2())) {
				contact.setHomeTelephone2(deflt(telephone.getText()));
				return true;
			}
		} else {
			if (StringUtils.isBlank(contact.getWorkTelephone())) {
				contact.setWorkTelephone(deflt(telephone.getText()));
				return true;
			} else if (StringUtils.isBlank(contact.getWorkTelephone2())) {
				contact.setWorkTelephone2(deflt(telephone.getText()));
				return true;
			} else if (StringUtils.isBlank(contact.getHomeTelephone())) {
				contact.setHomeTelephone(deflt(telephone.getText()));
				return true;
			} else if (StringUtils.isBlank(contact.getHomeTelephone2())) {
				contact.setHomeTelephone2(deflt(telephone.getText()));
				return true;
			}
		}
		return false;
	}
	
	private void setEmail(Contact contact, MatchCategory category, Email email) {
		if (MatchCategory.WORK.equals(category)) {
			contact.setWorkEmail(deflt(email.getValue()));
		} else if (MatchCategory.HOME.equals(category)) {
			contact.setHomeEmail(deflt(email.getValue()));
		} else if (MatchCategory.OTHER.equals(category)) {
			contact.setOtherEmail(deflt(email.getValue()));
		}
	}
	
	private boolean setEmailRelaxed(Contact contact, Email email) {
		if (StringUtils.isBlank(contact.getWorkEmail())) {
			contact.setWorkEmail(deflt(email.getValue()));
			return true;
		} else if (StringUtils.isBlank(contact.getHomeEmail())) {
			contact.setHomeEmail(deflt(email.getValue()));
			return true;
		} else if (StringUtils.isBlank(contact.getOtherEmail())) {
			contact.setOtherEmail(deflt(email.getValue()));
			return true;
		}
		return false;
	}
	
	private void setIMPP(Contact contact, MatchCategory category, Impp impp) {
		if (MatchCategory.WORK.equals(category)) {
			contact.setWorkEmail(deflt(impp.getUri().toString()));
		} else if (MatchCategory.HOME.equals(category)) {
			contact.setHomeEmail(deflt(impp.getUri().toString()));
		} else if (MatchCategory.OTHER.equals(category)) {
			contact.setOtherEmail(deflt(impp.getUri().toString()));
		}
	}
	
	private boolean setIMPPRelaxed(Contact contact, Impp impp) {
		if (StringUtils.isBlank(contact.getWorkEmail())) {
			contact.setWorkEmail(deflt(impp.getUri().toString()));
			return true;
		} else if (StringUtils.isBlank(contact.getHomeEmail())) {
			contact.setHomeEmail(deflt(impp.getUri().toString()));
			return true;
		} else if (StringUtils.isBlank(contact.getOtherEmail())) {
			contact.setOtherEmail(deflt(impp.getUri().toString()));
			return true;
		}
		return false;
	}
	
	private String getMediaType(Photo photo) {
		if (photo.getContentType() == null) return null;
		try {
			return new MimeType(photo.getContentType().getValue()).toString();
		} catch(MimeException ex) {
			return null;
		}
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
	
	public static enum MatchCategory {
		@SerializedName("work") WORK,
		@SerializedName("home") HOME,
		@SerializedName("other") OTHER,
		@SerializedName("none") NONE
	}
}
