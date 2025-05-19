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
import com.sonicle.commons.LangUtils;
import com.sonicle.webtop.contacts.model.ContactBase;
import com.sonicle.webtop.contacts.model.ContactCompany;
import com.sonicle.webtop.contacts.model.ContactPictureWithBytes;
import com.sonicle.webtop.core.app.util.log.BufferingLogHandler;
import com.sonicle.webtop.core.app.util.log.LogEntry;
import com.sonicle.webtop.core.app.util.log.LogHandler;
import com.sonicle.webtop.core.app.util.log.LogMessage;
import com.sonicle.webtop.core.sdk.WTException;
import com.sonicle.webtop.core.util.VCardUtils;
import eu.medsea.mimeutil.MimeException;
import eu.medsea.mimeutil.MimeType;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.parameter.AddressType;
import ezvcard.parameter.EmailType;
import ezvcard.parameter.ImppType;
import ezvcard.parameter.TelephoneType;
import ezvcard.property.Address;
import ezvcard.property.Anniversary;
import ezvcard.property.Birthday;
import ezvcard.property.Email;
import ezvcard.property.FormattedName;
import ezvcard.property.Gender;
import ezvcard.property.Impp;
import ezvcard.property.Nickname;
import ezvcard.property.Note;
import ezvcard.property.Organization;
import ezvcard.property.Photo;
import ezvcard.property.Role;
import ezvcard.property.StructuredName;
import ezvcard.property.Telephone;
import ezvcard.property.TextListProperty;
import ezvcard.property.Title;
import ezvcard.property.Uid;
import ezvcard.property.Url;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import com.sonicle.webtop.core.app.io.BeanHandler;
import com.sonicle.webtop.core.app.ezvcard.XAttachmentScribe;
import com.sonicle.webtop.core.app.ezvcard.XCustomFieldValue;
import com.sonicle.webtop.core.app.ezvcard.XCustomFieldValueScribe;
import com.sonicle.webtop.core.app.ezvcard.XTag;
import com.sonicle.webtop.core.app.ezvcard.XTagScribe;
import com.sonicle.webtop.core.model.CustomFieldValueCandidate;
import com.sonicle.webtop.core.model.TagCandidate;
import ezvcard.io.text.VCardReader;
import ezvcard.property.RawProperty;
import java.util.LinkedHashSet;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;

/**
 *
 * @author malbinola
 */
public class VCardInput {
	private final MatchCategory preferredMatch;
	private final boolean relaxedMatching = true;
	private boolean ignoreAttachments = true;
	private boolean ignoreCustomFieldsValues = true;
	private boolean returnSourceObject = false;
	private Map<String, String> categoriesToTagsMap = null;
	private LogHandler logHandler = null;
	private BeanHandler<ContactInput> beanHandler = null;
	
	public VCardInput() {
		this(MatchCategory.WORK);
	}
	
	public VCardInput(MatchCategory preferredMatch) {
		this.preferredMatch = preferredMatch;
	}
	
	public VCardInput withIgnoreAttachments(boolean ignoreAttachments) {
		this.ignoreAttachments = ignoreAttachments;
		return this;
	}
	
	public VCardInput withIgnoreCustomFieldsValues(boolean ignoreCustomFieldsValues) {
		this.ignoreCustomFieldsValues = ignoreCustomFieldsValues;
		return this;
	}
	
	public VCardInput withReturnSourceObject(boolean returnSourceObject) {
		this.returnSourceObject = returnSourceObject;
		return this;
	}
	
	public VCardInput withCategoriesToTagsMap(Map<String, String> categoriesToTagsMap) {
		this.categoriesToTagsMap = categoriesToTagsMap;
		return this;
	}
	
	public VCardInput withLogHandler(LogHandler logHandler) {
		this.logHandler = logHandler;
		return this;
	}
	
	public VCardInput withBeanHandler(BeanHandler<ContactInput> beanHandler) {
		this.beanHandler = beanHandler;
		return this;
	}
	
	public List<ContactInput> parseAllVCards(final InputStream is) throws IOException, WTException {
		final VCardReader reader = new VCardReader(is);
		if (!ignoreAttachments) reader.registerScribe(new XAttachmentScribe());
		if (!ignoreCustomFieldsValues) reader.registerScribe(new XCustomFieldValueScribe());
		reader.registerScribe(new XTagScribe());
		return parseAllVCards(reader);
	}
	
	public List<ContactInput> parseAllVCards(final String s) throws IOException, WTException {
		final VCardReader reader = new VCardReader(s);
		if (!ignoreAttachments) reader.registerScribe(new XAttachmentScribe());
		if (!ignoreCustomFieldsValues) reader.registerScribe(new XCustomFieldValueScribe());
		reader.registerScribe(new XTagScribe());
		return parseAllVCards(reader);
	}
	
	public List<ContactInput> parseAllVCards(final VCardReader reader) throws IOException, WTException {
		ArrayList<ContactInput> results = (beanHandler == null) ? new ArrayList<>() : null;
		
		VCard vc;
		int count = 0;
		while ((vc = reader.readNext()) != null) {
			count++;
			BufferingLogHandler buffLogHandler = createBufferingLogHandler(new LogMessage(0, LogEntry.Level.INFO, "VCARD #{} [{}]", count, VCardUtils.print(vc)));
			try {
				final ContactInput result = parseCardObject(vc, buffLogHandler);
				if (result.contact.trimFieldLengths()) {
					log(buffLogHandler, 1, LogEntry.Level.WARN, "Some fields were truncated due to max-length");
				}
				if (beanHandler != null) {
					beanHandler.handle(result);
				} else if (results != null) {
					results.add(result);
				}

			} catch(Throwable t) {
				log(buffLogHandler, 0, LogEntry.Level.ERROR, "Reason: {}", LangUtils.getThrowableMessage(t));
			}
			
			flushToLogHandler(buffLogHandler);
		}
		return results;
	}
	
	@Deprecated
	public List<ContactInput> read(InputStream is) throws IOException, WTException {
		return parseVCard(is);
	}
	
	@Deprecated
	public List<ContactInput> parseVCard(InputStream is) throws IOException, WTException {
		return parseCardObjects(Ezvcard.parse(is).all());
	}
	
	@Deprecated
	public List<ContactInput> parseVCard(String s) throws WTException {
		try {
			return parseCardObjects(Ezvcard.parse(new StringReader(s)).all());
		} catch(IOException ex) {
			throw new WTException(ex, "Unable to read String");
		}
	}
	
	@Deprecated
	public List<ContactInput> parseCardObjects(Collection<VCard> vCards) throws WTException {
		ArrayList<ContactInput> results = (beanHandler == null) ? new ArrayList<>() : null;
		
		int count = 0;
		for (VCard vc : vCards) {
			count++;
			BufferingLogHandler buffLogHandler = createBufferingLogHandler(new LogMessage(0, LogEntry.Level.INFO, "VCARD #{} [{}]", count, VCardUtils.print(vc)));
			
			/*
			final VCard obj = vc;
			final int no = count;
			
			BufferingLogHandler buffLogHandler = null;
			if (logHandler != null) {
				buffLogHandler = new BufferingLogHandler() {
					@Override
					public List<LogEntry> first() {
						return Arrays.asList(new LogMessage(0, LogEntry.Level.INFO, "VCARD #{} [{}]", no, VCardUtils.print(obj)));
					}
				};
			}
			*/
			
			try {
				final ContactInput result = parseCardObject(vc, buffLogHandler);
				if (result.contact.trimFieldLengths()) {
					log(buffLogHandler, 1, LogEntry.Level.WARN, "Some fields were truncated due to max-length");
				}
				if (beanHandler != null) {
					beanHandler.handle(result);
				} else if (results != null) {
					results.add(result);
				}

			} catch(Throwable t) {
				log(buffLogHandler, 0, LogEntry.Level.ERROR, "Reason: {}", LangUtils.getThrowableMessage(t));
			}
			
			flushToLogHandler(buffLogHandler);
			/*
			if (logHandler != null && buffLogHandler != null) {
				final List<LogEntry> entries = buffLogHandler.flush();
				if (entries != null) logHandler.handle(entries);
			}
			*/
		}
		return results;
	}
	
	public ContactInput parseCardObject(VCard vCard) throws WTException {
		return parseCardObject(vCard, null);
	}
	
	private ContactInput parseCardObject(VCard vcard, LogHandler logHandler) throws WTException {
		// See https://tools.ietf.org/html/rfc6350
		// See http://www.w3.org/TR/vcard-rdf/
		ContactBase contact = new ContactBase();
		ContactCompany contactCompany = null;
		ContactPictureWithBytes contactPicture = null;
		Set<TagCandidate> candidateTags = null; // Hold any tag found in source file regardless of validity of the tagId (postprocessing is needed)
		Set<CustomFieldValueCandidate> candidateCFValues = null; // Hold any customFieldValue found in source file regardless of validity of the fieldId (postprocessing is needed)
		VCard vcCopy = new VCard(vcard);
		
		//TODO: pass string field lengths in constructor or take them from db field definitions
		
		// UID(?)
		if (vcCopy.getUid() != null) {
			Uid uid = vcCopy.getUid();
			if (!StringUtils.isBlank(uid.getValue())) {
				contact.setPublicUid(uid.getValue());
			} else {
				log(logHandler, 1, LogEntry.Level.WARN, "UID is missing");
			}
			vcCopy.removeProperty(uid);
		}
		
		// FN(+) -> displayName
		if (!vcCopy.getFormattedNames().isEmpty()) {
			if (vcCopy.getFormattedNames().size() > 1) log(logHandler, 1, LogEntry.Level.WARN, "Many FN properties found");
			FormattedName fn = vcCopy.getFormattedNames().get(0);
			contact.setDisplayName(deflt(fn.getValue()));
			vcCopy.removeProperties(FormattedName.class);
		}
		
		// N(?) -> FirstName/LastName
		if (!vcCopy.getStructuredNames().isEmpty()) {
			if (vcCopy.getStructuredNames().size() > 1) log(logHandler, 1, LogEntry.Level.WARN, "Many N properties found");
			StructuredName sn = vcCopy.getStructuredNames().get(0);
			contact.setFirstName(deflt(sn.getGiven()));
			contact.setLastName(deflt(sn.getFamily()));
			if (!sn.getPrefixes().isEmpty()) {
				contact.setTitle(sn.getPrefixes().get(0));
				if (sn.getPrefixes().size() > 1) {
					log(logHandler, 1, LogEntry.Level.WARN, "Many N(prefix) properties found");
				}
			}
			vcCopy.removeProperties(StructuredName.class);
		}
		
		// NICKNAME(*)
		if (!vcCopy.getNicknames().isEmpty()) {
			if (vcCopy.getNicknames().size() > 1) log(logHandler, 1, LogEntry.Level.WARN, "Many NICKNAME properties found");
			Nickname ni = vcCopy.getNicknames().get(0);
			contact.setNickname(deflt(flatten(ni)));
			vcCopy.removeProperties(Nickname.class);
		}
		
		// GENDER(?)
		if (vcCopy.getGender() != null) {
			Gender gender = vcCopy.getGender();
			contact.setGender(toContactGender(gender));
			vcCopy.removeProperty(gender);
		}
		
		// ADR(*)
		if (!vcCopy.getAddresses().isEmpty()) {
			HashMap<MatchCategory, LinkedList<Address>> map = analyzeAddresses(vcCopy.getAddresses());
			for (Map.Entry<MatchCategory, LinkedList<Address>> entry : map.entrySet()) { // Strict matching
				if (MatchCategory.NONE.equals(entry.getKey())) continue;
				final Address address = entry.getValue().pollFirst();
				setAddress(contact, entry.getKey(), address);
			}
			if (relaxedMatching) {
				for (Map.Entry<MatchCategory, LinkedList<Address>> entry : map.entrySet()) {
					boolean shouldBreak = false;
					for (Address address : entry.getValue()) {
						if (!setAddressRelaxed(contact, address)) {
							shouldBreak = true;
							break;
						}
					}
					if (shouldBreak) break;
				}
			}
			vcCopy.removeProperties(Address.class);
		}
		
		// TEL(*)
		if (!vcCopy.getTelephoneNumbers().isEmpty()) {
			HashMap<MatchCategory, LinkedList<Telephone>> map = analyzeTelephones(vcCopy.getTelephoneNumbers());
			for (Map.Entry<MatchCategory, LinkedList<Telephone>> entry : map.entrySet()) { // Strict matching
				if (MatchCategory.NONE.equals(entry.getKey())) continue;
				final Telephone telephone = entry.getValue().pollFirst();
				setTelephone(contact, entry.getKey(), telephone);
			}
			if (relaxedMatching) {
				for (Map.Entry<MatchCategory, LinkedList<Telephone>> entry : map.entrySet()) {
					boolean shouldBreak = false;
					for (Telephone telephone : entry.getValue()) {
						if (!setTelephoneRelaxed(contact, telephone)) {
							shouldBreak = true;
							break;
						}
					}
					if (shouldBreak) break;
				}
			}
			vcCopy.removeProperties(Telephone.class);
		}
		
		// EMAIL(*)
		if (!vcCopy.getEmails().isEmpty()) {
			HashMap<MatchCategory, LinkedList<Email>> map = analyzeEmails(vcCopy.getEmails());
			for (Map.Entry<MatchCategory, LinkedList<Email>> entry : map.entrySet()) { // Strict matching
				if (MatchCategory.NONE.equals(entry.getKey())) continue;
				final Email email = entry.getValue().pollFirst();
				setEmail(contact, entry.getKey(), email);
			}
			if (relaxedMatching) {
				for (Map.Entry<MatchCategory, LinkedList<Email>> entry : map.entrySet()) {
					boolean shouldBreak = false;
					for (Email email : entry.getValue()) {
						if (!setEmailRelaxed(contact, email)) {
							shouldBreak = true;
							break;
						}
					}
					if (shouldBreak) break;
				}
			}
			vcCopy.removeProperties(Email.class);
		}
		
		// IMPP(*) -> InstantMsg
		if (!vcCopy.getImpps().isEmpty()) {
			HashMap<MatchCategory, LinkedList<Impp>> map = analyzeIMPPs(vcCopy.getImpps());
			for (Map.Entry<MatchCategory, LinkedList<Impp>> entry : map.entrySet()) { // Strict matching
				if (MatchCategory.NONE.equals(entry.getKey())) continue;
				final Impp impp = entry.getValue().pollFirst();
				setIMPP(contact, entry.getKey(), impp);
			}
			if (relaxedMatching) {
				for (Map.Entry<MatchCategory, LinkedList<Impp>> entry : map.entrySet()) {
					boolean shouldBreak = false;
					for (Impp impp : entry.getValue()) {
						if (!setIMPPRelaxed(contact, impp)) {
							shouldBreak = true;
							break;
						}
					}
					if (shouldBreak) break;
				}
			}
			vcCopy.removeProperties(Impp.class);
		}
		
		// ORG(*) -> Company/Department
		if (vcCopy.getOrganization() != null) {
			Organization org = vcCopy.getOrganization();
			List<String> values = org.getValues();
			if (!values.isEmpty()) {
				String value = deflt(values.get(0));
				contactCompany = new ContactCompany(value, value);
				if (values.size() > 1) contact.setDepartment(deflt(values.get(1)));
			}
			vcCopy.removeProperties(Organization.class);
		}
		
		// TITLE and ROLE have a similar meaning but seems that devices 
		// writes data into TITLE field. In order to catch many cases we can,
		// it's better to give precedence to TITLE field and then fallback to
		// the similar ROLE field.
		
		// TITLE(*) -> Function
		if (!vcCopy.getTitles().isEmpty()) {
			if (vcCopy.getTitles().size() > 1) log(logHandler, 1, LogEntry.Level.WARN, "Many TITLE properties found");
			Title ti = vcCopy.getTitles().get(0);
			contact.setFunction(deflt(ti.getValue()));
			vcCopy.removeProperties(Title.class);
		}
		// ROLE(*) -> Function
		if (StringUtils.isBlank(contact.getFunction()) && !vcCopy.getRoles().isEmpty()) {
			if (vcCopy.getRoles().size() > 1) log(logHandler, 1, LogEntry.Level.WARN, "Many ROLE properties found");
			Role ro = vcCopy.getRoles().get(0);
			contact.setFunction(deflt(ro.getValue()));
			vcCopy.removeProperties(Role.class);
		}
		
		// BDAY(*)
		if (vcCopy.getBirthday() != null) {
			Birthday bday = vcCopy.getBirthday();
			contact.setBirthday(new LocalDate(bday.getDate()));
			vcCopy.removeProperties(Birthday.class);
		}
		
		// ANNIVERSARY(*)
		if (vcCopy.getAnniversary()!= null) {
			Anniversary anni = vcCopy.getAnniversary();
			contact.setAnniversary(new LocalDate(anni.getDate()));
			vcCopy.removeProperties(Anniversary.class);
		}
		
		// URL(*)
		if (!vcCopy.getUrls().isEmpty()) {
			if (vcCopy.getUrls().size() > 1) log(logHandler, 1, LogEntry.Level.WARN, "Many URL properties found");
			Url ur = vcCopy.getUrls().get(0);
			contact.setUrl(deflt(ur.getValue()));
			vcCopy.removeProperties(Url.class);
		}
		
		// NOTE(*)
		if (!vcCopy.getNotes().isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (Note no : vcCopy.getNotes()) {
				sb.append(no.getValue());
				sb.append("\n");
			}
			contact.setNotes(sb.toString());
			vcCopy.removeProperties(Note.class);
		}
		
		// PHOTO(*)
		if (!vcCopy.getPhotos().isEmpty()) {
			if (vcCopy.getPhotos().size() > 1) log(logHandler, 1, LogEntry.Level.WARN, "Many PHOTO properties found");
			final Photo pho = vcCopy.getPhotos().get(0);
			final String mtype = getMediaType(pho);
			if (mtype != null) {
				contactPicture = new ContactPictureWithBytes(pho.getData());
				contactPicture.setMediaType(mtype);
				vcCopy.removeProperties(Photo.class);
			} else {
				log(logHandler, 1, LogEntry.Level.WARN, "PHOTO skipped: unspecified content type");
			}
		}
		
		// X-WT-TAG(*)
		List<XTag> tagProps = vcCopy.getProperties(XTag.class);
		if (!tagProps.isEmpty()) {
			candidateTags = new LinkedHashSet<>();
			for (XTag prop : tagProps) {
				final String id = prop.getTagId();
				final String name = prop.getTagName();
				if (!StringUtils.isEmpty(id)) {
					candidateTags.add(new TagCandidate(id, name));
				} else if (!StringUtils.isEmpty(name)) {
					candidateTags.add(new TagCandidate(name));
				} else {
					log(logHandler, 1, LogEntry.Level.WARN, "X-WT-TAG skipped: malformed data");
				}
			}
		}
		
		// X-WT-CUSTOMFIELDVALUE(*)
		List<XCustomFieldValue> cfValueProps = vcCopy.getProperties(XCustomFieldValue.class);
		if (!cfValueProps.isEmpty()) {
			candidateCFValues = new LinkedHashSet<>();
			for (XCustomFieldValue prop : cfValueProps) {
				final String fieldId = prop.getFieldId();
				final String valueType = prop.getFieldType();
				if (!StringUtils.isEmpty(fieldId) && !StringUtils.isEmpty(valueType)) {
					try {
						candidateCFValues.add(new CustomFieldValueCandidate(fieldId, valueType, prop.getFieldValue()));
					} catch (IllegalStateOfArgumentException ex) {
						log(logHandler, 1, LogEntry.Level.WARN, "X-WT-CUSTOMFIELDVALUE skipped: malformed data (unsupported TYPE)");
					}
					
				} else {
					log(logHandler, 1, LogEntry.Level.WARN, "X-WT-CUSTOMFIELDVALUE skipped: malformed data (empty UID or TYPE)");
				}
			}
		}
		
		// X-PROPS(*)
		List<RawProperty> extProps = vcCopy.getExtendedProperties();
		if (!extProps.isEmpty()) {
			ArrayList<RawProperty> extPropsCopy = new ArrayList<>();
			for (RawProperty rp : extProps) extPropsCopy.add(rp);
			
			for (RawProperty rp : extPropsCopy) {
				if (VCardExProps.MANAGER.equals(rp.getPropertyName())) {
					contact.setAssistant(rp.getValue());
					vcCopy.removeProperty(rp);
				} else if (VCardExProps.ASSISTANT.equals(rp.getPropertyName())) {
					contact.setAssistant(rp.getValue());
					vcCopy.removeProperty(rp);
				} else if (VCardExProps.ASSISTANT_TELEPHONE.equals(rp.getPropertyName())) {
					contact.setAssistantTelephone(rp.getValue());
					vcCopy.removeProperty(rp);
				} else if (VCardExProps.HREF.equals(rp.getPropertyName())) {
					contact.setHref(rp.getValue());
					vcCopy.removeProperty(rp);
				}
			}
		}
		
		VCard sourceObject = (returnSourceObject && !vcCopy.getProperties().isEmpty()) ? vcCopy : null;
		return new ContactInput(contact, contactCompany, contactPicture, candidateTags, candidateCFValues, sourceObject);
	}
	
	/*
	private void setFallbackTelephone(Contact contact, TelephoneType type, String value) {
		if (TelephoneType.VOICE.equals(type)) {
			if (StringUtils.isBlank(contact.getWorkTelephone1())) {
				contact.setWorkTelephone1(value);
				return;
			}
			if (StringUtils.isBlank(contact.getHomeTelephone1())) {
				contact.setHomeTelephone1(value);
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
			if (StringUtils.isBlank(contact.getPager1())) {
				contact.setPager1(value);
				return;
			}
			if (StringUtils.isBlank(contact.getPager2())) {
				contact.setPager2(value);
			}
		} else if (TelephoneType.CELL.equals(type)) {
			if (StringUtils.isBlank(contact.getMobile())) {
				contact.setMobile(value);
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
			if (StringUtils.isBlank(contact.getWorkTelephone1())) {
				contact.setWorkTelephone1(value);
				return;
			}
			if (StringUtils.isBlank(contact.getHomeTelephone1())) {
				contact.setHomeTelephone1(value);
			}
		}
	}
	*/
	
	public HashMap<MatchCategory, LinkedList<Address>> analyzeAddresses(List<Address> addresses) {
		HashMap<MatchCategory, LinkedList<Address>> map = new LinkedHashMap<>();
		
		for (Address address : addresses) {
			final List<AddressType> types = address.getTypes();
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
			final List<TelephoneType> types = telephone.getTypes();
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
			final List<EmailType> types = email.getTypes();
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
			final List<ImppType> types = email.getTypes();
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
	
	public ContactBase.Gender toContactGender(Gender gender) {
		if (gender.isMale()) return ContactBase.Gender.MALE;
		if (gender.isFemale()) return ContactBase.Gender.FEMALE;
		if (gender.isOther()) return ContactBase.Gender.OTHER;
		return null;
	}
	
	private boolean setAddress(ContactBase contact, MatchCategory category, Address address) {
		if (MatchCategory.WORK.equals(category)) {
			contact.setWorkAddress(deflt(address.getStreetAddress()));
			contact.setWorkPostalCode(deflt(address.getPostalCode()));
			contact.setWorkCity(deflt(address.getLocality()));
			contact.setWorkState(deflt(address.getRegion()));
			contact.setWorkCountry(deflt(address.getCountry()));
			return true;
		} else if (MatchCategory.HOME.equals(category)) {
			contact.setHomeAddress(deflt(address.getStreetAddress()));
			contact.setHomePostalCode(deflt(address.getPostalCode()));
			contact.setHomeCity(deflt(address.getLocality()));
			contact.setHomeState(deflt(address.getRegion()));
			contact.setHomeCountry(deflt(address.getCountry()));
			return true;
		} else if (MatchCategory.OTHER.equals(category)) {
			contact.setOtherAddress(deflt(address.getStreetAddress()));
			contact.setOtherPostalCode(deflt(address.getPostalCode()));
			contact.setOtherCity(deflt(address.getLocality()));
			contact.setOtherState(deflt(address.getRegion()));
			contact.setOtherCountry(deflt(address.getCountry()));
			return true;
		}
		return false;
	}
	
	private boolean setAddressRelaxed(ContactBase contact, Address address) {
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
	
	private boolean setTelephone(ContactBase contact, MatchCategory category, Telephone telephone) {
		final List<TelephoneType> types = telephone.getTypes();
		if (MatchCategory.WORK.equals(category)) {
			if (types.contains(TelephoneType.VOICE)) {
				contact.setWorkTelephone1(deflt(telephone.getText()));
				return true;
			} else if (types.contains(TelephoneType.FAX)) {
				contact.setWorkFax(deflt(telephone.getText()));
				return true;
			} else if (types.contains(TelephoneType.PAGER)) {
				contact.setPager1(deflt(telephone.getText()));
				return true;
			} else if (types.contains(TelephoneType.CELL)) {
				contact.setMobile(deflt(telephone.getText()));
				return true;
			} else if (types.contains(TelephoneType.TEXT)) {
				contact.setWorkTelephone2(deflt(telephone.getText()));
				return true;
			} else {
				contact.setWorkTelephone1(deflt(telephone.getText()));
				return true;
			}
		} else if (MatchCategory.HOME.equals(category)) {
			if (types.contains(TelephoneType.VOICE)) {
				contact.setHomeTelephone1(deflt(telephone.getText()));
				return true;
			} else if (types.contains(TelephoneType.FAX)) {
				contact.setHomeFax(deflt(telephone.getText()));
				return true;
			} else if (types.contains(TelephoneType.PAGER)) {
				contact.setPager2(deflt(telephone.getText()));
				return true;
			} else if (types.contains(TelephoneType.TEXT)) {
				contact.setHomeTelephone2(deflt(telephone.getText()));
				return true;
			} else {
				contact.setHomeTelephone1(deflt(telephone.getText()));
				return true;
			}
		}
		return false;
	}
	
	private boolean setTelephoneRelaxed(ContactBase contact, Telephone telephone) {
		final List<TelephoneType> types = telephone.getTypes();
		if (types.contains(TelephoneType.CELL)) {
			if (StringUtils.isBlank(contact.getMobile())) {
				contact.setMobile(deflt(telephone.getText()));
				return true;
			} else if (StringUtils.isBlank(contact.getWorkTelephone1())) {
				contact.setWorkTelephone1(deflt(telephone.getText()));
				return true;
			} else if (StringUtils.isBlank(contact.getWorkTelephone2())) {
				contact.setWorkTelephone2(deflt(telephone.getText()));
				return true;
			} else if (StringUtils.isBlank(contact.getHomeTelephone1())) {
				contact.setHomeTelephone1(deflt(telephone.getText()));
				return true;
			} else if (StringUtils.isBlank(contact.getHomeTelephone2())) {
				contact.setHomeTelephone2(deflt(telephone.getText()));
				return true;
			}			
		}
		if (types.contains(TelephoneType.VOICE)) {
			if (StringUtils.isBlank(contact.getWorkTelephone1())) {
				contact.setWorkTelephone1(deflt(telephone.getText()));
				return true;
			} else if (StringUtils.isBlank(contact.getHomeTelephone1())) {
				contact.setHomeTelephone1(deflt(telephone.getText()));
				return true;
			}
		}
		if (types.contains(TelephoneType.FAX)) {
			if (StringUtils.isBlank(contact.getWorkFax())) {
				contact.setWorkFax(deflt(telephone.getText()));
				return true;
			} else if (StringUtils.isBlank(contact.getHomeFax())) {
				contact.setHomeFax(deflt(telephone.getText()));
				return true;
			}
		}
		if (types.contains(TelephoneType.PAGER)) {
			if (StringUtils.isBlank(contact.getPager1())) {
				contact.setPager1(deflt(telephone.getText()));
				return true;
			} else if (StringUtils.isBlank(contact.getPager2())) {
				contact.setPager2(deflt(telephone.getText()));
				return true;
			}
		}
		if (types.contains(TelephoneType.TEXT)) {
			if (StringUtils.isBlank(contact.getWorkTelephone2())) {
				contact.setWorkTelephone2(deflt(telephone.getText()));
				return true;
			} else if (StringUtils.isBlank(contact.getHomeTelephone2())) {
				contact.setHomeTelephone2(deflt(telephone.getText()));
				return true;
			}
		}
		if (StringUtils.isBlank(contact.getWorkTelephone1())) {
			contact.setWorkTelephone1(deflt(telephone.getText()));
			return true;
		} else if (StringUtils.isBlank(contact.getWorkTelephone2())) {
			contact.setWorkTelephone2(deflt(telephone.getText()));
			return true;
		} else if (StringUtils.isBlank(contact.getHomeTelephone1())) {
			contact.setHomeTelephone1(deflt(telephone.getText()));
			return true;
		} else if (StringUtils.isBlank(contact.getHomeTelephone2())) {
			contact.setHomeTelephone2(deflt(telephone.getText()));
			return true;
		}		
		return false;
	}
	
	private boolean setEmail(ContactBase contact, MatchCategory category, Email email) {
		if (MatchCategory.WORK.equals(category)) {
			contact.setEmail1(deflt(email.getValue()));
			return true;
		} else if (MatchCategory.HOME.equals(category)) {
			contact.setEmail2(deflt(email.getValue()));
			return true;
		} else if (MatchCategory.OTHER.equals(category)) {
			contact.setEmail3(deflt(email.getValue()));
			return true;
		}
		return false;
	}
	
	private boolean setEmailRelaxed(ContactBase contact, Email email) {
		if (StringUtils.isBlank(contact.getEmail1())) {
			contact.setEmail1(deflt(email.getValue()));
			return true;
		} else if (StringUtils.isBlank(contact.getEmail2())) {
			contact.setEmail2(deflt(email.getValue()));
			return true;
		} else if (StringUtils.isBlank(contact.getEmail3())) {
			contact.setEmail3(deflt(email.getValue()));
			return true;
		}
		return false;
	}
	
	private boolean setIMPP(ContactBase contact, MatchCategory category, Impp impp) {
		if (MatchCategory.WORK.equals(category)) {
			contact.setInstantMsg1(deflt(impp.getUri().toString()));
			return true;
		} else if (MatchCategory.HOME.equals(category)) {
			contact.setInstantMsg2(deflt(impp.getUri().toString()));
			return true;
		} else if (MatchCategory.OTHER.equals(category)) {
			contact.setInstantMsg3(deflt(impp.getUri().toString()));
			return true;
		}
		return false;
	}
	
	private boolean setIMPPRelaxed(ContactBase contact, Impp impp) {
		if (StringUtils.isBlank(contact.getInstantMsg1())) {
			contact.setInstantMsg1(deflt(impp.getUri().toString()));
			return true;
		} else if (StringUtils.isBlank(contact.getInstantMsg2())) {
			contact.setInstantMsg2(deflt(impp.getUri().toString()));
			return true;
		} else if (StringUtils.isBlank(contact.getInstantMsg3())) {
			contact.setInstantMsg3(deflt(impp.getUri().toString()));
			return true;
		}
		return false;
	}
	
	private String getMediaType(Photo photo) {
		if (photo.getContentType() == null) return null;
		try {
			return new MimeType(photo.getContentType().getMediaType()).toString();
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
	
	private BufferingLogHandler createBufferingLogHandler(LogMessage firstLogMessage) {
		if (logHandler != null) {
			return new BufferingLogHandler() {
				@Override
				public List<LogEntry> first() {
					return Arrays.asList(firstLogMessage);
				}
			};
		} else {
			return null;
		}
	}
	
	private void flushToLogHandler(BufferingLogHandler bufferingLogHandler) {
		if (logHandler != null && bufferingLogHandler != null) {
			final List<LogEntry> entries = bufferingLogHandler.flush();
			if (entries != null) logHandler.handle(entries);
		}
	}
	
	private void log(LogHandler handler, int depth, LogEntry.Level level, String message, Object... arguments) {
		if (handler != null) {
			try {
				handler.handle(new LogMessage(depth, level, message, arguments));
			} catch(Throwable t) {}
		}
	}
	
	public static enum MatchCategory {
		@SerializedName("work") WORK,
		@SerializedName("home") HOME,
		@SerializedName("other") OTHER,
		@SerializedName("none") NONE
	}
}
