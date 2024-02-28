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

import com.sonicle.commons.LangUtils;
import com.sonicle.commons.time.DateTimeUtils;
import com.sonicle.webtop.contacts.model.ContactAttachment;
import com.sonicle.webtop.contacts.model.ContactAttachmentWithBytes;
import com.sonicle.webtop.contacts.model.ContactBase;
import com.sonicle.webtop.contacts.model.ContactEx;
import com.sonicle.webtop.contacts.model.ContactPicture;
import com.sonicle.webtop.contacts.model.ContactPictureWithBytes;
import com.sonicle.webtop.core.app.ezvcard.BinaryType;
import com.sonicle.webtop.core.app.ezvcard.ExtendedProperty;
import com.sonicle.webtop.core.app.ezvcard.XAttachment;
import com.sonicle.webtop.core.app.ezvcard.XAttachmentScribe;
import com.sonicle.webtop.core.app.ezvcard.XCustomFieldValue;
import com.sonicle.webtop.core.app.ezvcard.XCustomFieldValueScribe;
import com.sonicle.webtop.core.app.util.log.BufferingLogHandler;
import com.sonicle.webtop.core.app.util.log.LogEntry;
import com.sonicle.webtop.core.app.util.log.LogHandler;
import com.sonicle.webtop.core.app.util.log.LogMessage;
import com.sonicle.webtop.core.model.CustomFieldValue;
import com.sonicle.webtop.core.model.RecipientFieldCategory;
import com.sonicle.webtop.core.sdk.WTException;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardDataType;
import ezvcard.VCardVersion;
import ezvcard.io.scribe.StringPropertyScribe;
import ezvcard.io.scribe.VCardPropertyScribe;
import ezvcard.parameter.AddressType;
import ezvcard.parameter.EmailType;
import ezvcard.parameter.ImageType;
import ezvcard.parameter.ImppType;
import ezvcard.parameter.RelatedType;
import ezvcard.parameter.TelephoneType;
import ezvcard.parameter.VCardParameter;
import ezvcard.parameter.VCardParameters;
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
import ezvcard.property.Related;
import ezvcard.property.Role;
import ezvcard.property.StructuredName;
import ezvcard.property.Telephone;
import ezvcard.property.TextProperty;
import ezvcard.property.Title;
import ezvcard.property.Uid;
import ezvcard.property.Url;
import ezvcard.property.VCardProperty;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import net.sf.qualitycheck.Check;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author malbinola
 */
public class VCardOutput {
	private final String prodId;
	private TagsMappingMode tagsMappingMode = TagsMappingMode.NAME;
	private Map<String, String> tagNamesByIdMap = null;
	private LogHandler logHandler = null;
	private boolean enableCaretEncoding = true;
	private VCardVersion version = VCardVersion.V4_0;
	private RecipientFieldCategory preferredTarget = RecipientFieldCategory.WORK;
	private final AddressType otherAddressType = AddressType.POSTAL;
	private EmailType[] emailTypeMap = new EmailType[]{EmailType.WORK, EmailType.HOME, EmailType.AOL};
	private ImppType[] imppTypeMap = new ImppType[]{ImppType.WORK, ImppType.HOME, ImppType.PERSONAL};
	
	public VCardOutput(String prodId) {
		this.prodId = Check.notNull(prodId, "prodId");
	}
	
	public VCardOutput withTagsMappingMode(TagsMappingMode tagsMappingMode) {
		this.tagsMappingMode = Check.notNull(tagsMappingMode, "tagsMappingMode");
		return this;
	}
	
	public VCardOutput withTagNamesByIdMap(Map<String, String> tagNamesByIdMap) {
		this.tagNamesByIdMap = tagNamesByIdMap;
		return this;
	}
	
	public VCardOutput withLogHandler(LogHandler logHandler) {
		this.logHandler = logHandler;
		return this;
	}
	
	public VCardOutput withEnableCaretEncoding(boolean enableCaretEncoding) {
		this.enableCaretEncoding = enableCaretEncoding;
		return this;
	}
	
	public VCardOutput withVCardVersion(VCardVersion version) {
		this.version = version;
		return this;
	}
	
	public VCardOutput withPreferredTarget(RecipientFieldCategory preferredTarget) {
		this.preferredTarget = preferredTarget;
		return this;
	}
	
	public boolean isEnableCaretEncoding() {
		return enableCaretEncoding;
	}
	
	public VCardVersion getVCardVersion() {
		return version;
	}
	
	public RecipientFieldCategory getPreferredTarget() {
		return preferredTarget;
	}
	
	public String write(VCard vCard) {
		if (vCard == null) return null;
		return Ezvcard.write(vCard)
				.caretEncoding(enableCaretEncoding)
				.version(version)
				.register(new XCustomFieldValueScribe())
				.register(new XAttachmentScribe())
				.go();
	}
	
	public String writeVCard(ContactEx contact, VCard baseObject) throws WTException {
		return writeVCard(new ContactOutput(contact, baseObject));
	}
	
	public String writeVCard(ContactOutput output) throws WTException {
		return writeVCard(Arrays.asList(output));
	}
	
	public String writeVCard(Collection<ContactOutput> outputs) throws WTException {
		return Ezvcard.write(createCardObjectsCollection(outputs))
			.caretEncoding(enableCaretEncoding)
			.version(version)
			.register(new XCustomFieldValueScribe())
			.register(new XAttachmentScribe())
			.go();
	}
	
	public String writeVCard(VCard vCard) throws WTException {
		return Ezvcard.write(vCard)
			.caretEncoding(enableCaretEncoding)
			.version(version)
			.register(new XCustomFieldValueScribe())
			.register(new XAttachmentScribe())
			.go();
	}
	
	public Collection<VCard> createCardObjectsCollection(Collection<ContactOutput> outputs) throws WTException {
		List<VCard> ret = new ArrayList<>(outputs.size());
		
		int count = 0;
		for (ContactOutput output : outputs) {
			count++;
			final ContactEx contact = output.contact;
			final int outNo = count;
			
			BufferingLogHandler buffLogHandler = null;
			if (logHandler != null) {
				buffLogHandler = new BufferingLogHandler() {
					@Override
					public List<LogEntry> first() {
						return Arrays.asList(new LogMessage(0, LogEntry.Level.INFO, "Contact #{} [{}, {}]", outNo, contact.getPublicUid(), contact.getDisplayName(true)));
					}
				};
			}
			
			try {
				ret.add(createCardObject(output.contact, output.baseObject, logHandler));
			} catch(Throwable t) {
				log(buffLogHandler, 0, LogEntry.Level.ERROR, "Reason: {}", LangUtils.getThrowableMessage(t));
			}
			
			if (logHandler != null && buffLogHandler != null) {
				final List<LogEntry> entries = buffLogHandler.flush();
				if (entries != null) logHandler.handle(entries);
			}
		}
		return ret;
	}
	
	public VCard createCardObject(ContactEx contact, VCard baseObject) throws WTException {
		return createCardObject(contact, baseObject, null);
	}
	
	private VCard createCardObject(ContactEx contact, VCard baseObject, LogHandler logHandler) throws WTException {
		VCard vcard = baseObject != null ? baseObject : new VCard();
		
		// UID(?)
		vcard.setUid(toUid(contact));
		
		// FN(+) -> Formatted Name (full name)
		vcard.setFormattedName(toFormattedName(contact));
		
		// FN(+) -> Formatted Name (display name)
		vcard.setFormattedName(toFormattedName(contact));
		
		// N(?) -> Structured Name (name components)
		vcard.setStructuredName(toStructuredName(contact));
		
		// NICKNAME(*)
		vcard.setNickname(toNickname(contact));
		
		// GENDER(?)
		vcard.setGender(toGender(contact));
		
		// ADR(*)
		for (Address address : toAddresses(contact)) {
			vcard.addAddress(address);
		}
		
		// TEL(*)
		for (Telephone telephone : toTelephones(contact)) {
			vcard.addTelephoneNumber(telephone);
		}
		
		// EMAIL(*)
		for (Email email : toEmails(contact)) {
			vcard.addEmail(email);
		}
		
		// IMPP(*) -> InstantMsg
		for (Impp impp : toImpps(contact)) {
			vcard.addImpp(impp);
		}
		
		// ORG(*) -> Company/Department
		vcard.setOrganization(toOrganization(contact));
		
		// TITLE and ROLE have a similar meaning but seems that devices 
		// display info taking data from TITLE field.
		// So, we choose TITLE in vCard outputting!
		
		// TITLE(*) -> Function
		Title title = toTitle(contact);
		if (title != null) vcard.addTitle(title);
		
		// RELATED(*)
		Related spouse = toSpouse(contact);
		if (spouse != null) vcard.addRelated(spouse);
		
		// BDAY(*)
		vcard.setBirthday(toBirthday(contact));
		
		// ANNIVERSARY(*)
		vcard.setAnniversary(toAnniversary(contact));
		
		// URL(*)
		Url url = toUrl(contact);
		if (url != null) vcard.addUrl(url);
		
		// NOTE(*)
		Note note = toNotes(contact);
		if (note != null) vcard.addNote(note);
		
		// PHOTO(*)
		if (contact.hasPicture()) {
			ContactPicture picture = contact.getPicture();
			if (picture instanceof ContactPictureWithBytes) {
				Photo photo = toPhoto((ContactPictureWithBytes)picture);
				if (photo != null) vcard.addPhoto(photo);
			}
		}
		
		// ATTACHMENTS
		if (contact.hasAttachments()) {
			for(ContactAttachment att: contact.getAttachmentsOrEmpty()) {
				if (att instanceof ContactAttachmentWithBytes) {
					ContactAttachmentWithBytes attb = (ContactAttachmentWithBytes)att;
					XAttachment xatt = new XAttachment(
							attb.getBytes(), 
							BinaryType.get(null, attb.getMediaType(), null),
							att.getFilename()
					);
					vcard.addProperty(xatt);
				}
			}
		}
		
		// EXTENDED PROPERTIES
		for(ExtendedProperty extp: toExtendedProperties(contact)) {
			vcard.addExtendedProperty(extp.getName(), extp.getValue());
		}
		
		// CUSTOM FIELDS AS EXTENDED PROPERTIES
		Map<String, CustomFieldValue> cv = contact.getCustomValues();
		if (cv!=null) {
			for (Entry<String, CustomFieldValue> e: cv.entrySet()) {
				CustomFieldValue cfv = e.getValue();
				String uid = e.getKey();
				DateTimeFormatter dtf = DateTimeUtils.createYmdHmsFormatter();
				Boolean bv = cfv.getBooleanValue();
				DateTime dv = cfv.getDateValue();
				Double nv = cfv.getNumberValue();
				String sv = cfv.getStringValue();
				String tv = cfv.getTextValue();
				if (bv!=null) vcard.addProperty(new XCustomFieldValue(uid, XCustomFieldValue.TYPE_BOOLEAN, ""+bv));
				else if (dv!=null) vcard.addProperty(new XCustomFieldValue(uid, XCustomFieldValue.TYPE_DATE, dtf.print(dv)));
				else if (nv!=null) vcard.addProperty(new XCustomFieldValue(uid, XCustomFieldValue.TYPE_NUMBER, String.valueOf(nv)));
				else if (!StringUtils.isEmpty(sv)) vcard.addProperty(new XCustomFieldValue(uid, XCustomFieldValue.TYPE_STRING, sv));
				else if (!StringUtils.isEmpty(tv)) vcard.addProperty(new XCustomFieldValue(uid, XCustomFieldValue.TYPE_TEXT, tv));
			}
		}
		
		return vcard;
	}
	
	public Uid toUid(ContactEx contact) {
		final String uid = contact.getPublicUid();
		return !StringUtils.isBlank(uid) ? new Uid(uid) : null;
	}
	
	public FormattedName toFormattedName(ContactEx contact) {
		return new FormattedName(contact.getDisplayName(true));
	}
	
	public StructuredName toStructuredName(ContactEx contact) {
		StructuredName prop = null;
		if (!contact.areNamesBlank(false)) {
			prop = new StructuredName();
			prop.setGiven(deflt(contact.getFirstName()));
			prop.setFamily(deflt(contact.getLastName()));
			prop.addParameter("displayName", contact.getDisplayName());
			if (!StringUtils.isBlank(contact.getTitle())) {
				prop.getPrefixes().add(contact.getTitle());
			}
		}
		return prop;
	}
	
	public Nickname toNickname(ContactEx contact) {
		Nickname prop = null;
		if (!StringUtils.isBlank(contact.getNickname())) {
			prop = new Nickname();
			prop.getValues().add(contact.getNickname());
		}
		return prop;
	}
	
	public Gender toGender(ContactEx contact) {
		Gender prop = null;
		if (ContactBase.Gender.MALE.equals(contact.getGender())) {
			prop = new Gender(Gender.MALE);
		} else if (ContactBase.Gender.FEMALE.equals(contact.getGender())) {
			prop = new Gender(Gender.FEMALE);
		} else if (ContactBase.Gender.OTHER.equals(contact.getGender())) {
			prop = new Gender(Gender.OTHER);
		}
		return prop;
	}
	
	public List<Address> toAddresses(ContactEx contact) {
		List<Address> props = new ArrayList<>();
		if (!contact.isWorkAddressEmpty()) {
			Address addr = new Address();
			addr.getTypes().add(AddressType.WORK);
			addr.setStreetAddress(deflt(contact.getWorkAddress()));
			addr.setPostalCode(deflt(contact.getWorkPostalCode()));
			addr.setLocality(deflt(contact.getWorkCity()));
			addr.setRegion(deflt(contact.getWorkState()));
			addr.setCountry(deflt(contact.getWorkCountry()));
			if (RecipientFieldCategory.WORK.equals(preferredTarget)) {
				addr.setPref(1);
			}
			props.add(addr);
		}
		if (!contact.isHomeAddressEmpty()) {
			Address addr = new Address();
			addr.getTypes().add(AddressType.HOME);
			addr.setStreetAddress(deflt(contact.getHomeAddress()));
			addr.setPostalCode(deflt(contact.getHomePostalCode()));
			addr.setLocality(deflt(contact.getHomeCity()));
			addr.setRegion(deflt(contact.getHomeState()));
			addr.setCountry(deflt(contact.getHomeCountry()));
			if (RecipientFieldCategory.HOME.equals(preferredTarget)) {
				addr.setPref(1);
			}
			props.add(addr);
		}
		if (!contact.isOtherAddressEmpty()) {
			Address addr = new Address();
			addr.getTypes().add(otherAddressType);
			addr.setStreetAddress(deflt(contact.getOtherAddress()));
			addr.setPostalCode(deflt(contact.getOtherPostalCode()));
			addr.setLocality(deflt(contact.getOtherCity()));
			addr.setRegion(deflt(contact.getOtherState()));
			addr.setCountry(deflt(contact.getOtherCountry()));
			if (RecipientFieldCategory.OTHER.equals(preferredTarget)) {
				addr.setPref(1);
			}
			props.add(addr);
		}
		return props;
	}
	
	public List<Telephone> toTelephones(ContactEx contact) {
		List<Telephone> props = new ArrayList<>();
		if (!StringUtils.isBlank(contact.getMobile())) {
			Telephone tel = new Telephone(contact.getMobile());
			tel.getTypes().addAll(Arrays.asList(TelephoneType.CELL));
			tel.setPref(1);
			props.add(tel);
		}
		int pagerCount = 0;
		if (!StringUtils.isBlank(contact.getPager1())) {
			Telephone tel = new Telephone(contact.getPager1());
			tel.getTypes().addAll(Arrays.asList(TelephoneType.PAGER));
			tel.setPref(1);
			props.add(tel);
			pagerCount++;
		}
		if (!StringUtils.isBlank(contact.getPager2())) {
			Telephone tel = new Telephone(contact.getPager2());
			tel.getTypes().addAll(Arrays.asList(TelephoneType.PAGER));
			if (pagerCount == 0) tel.setPref(1);
			props.add(tel);
			pagerCount++;
		}
		if (!StringUtils.isBlank(contact.getWorkTelephone1())) {
			Telephone tel = new Telephone(contact.getWorkTelephone1());
			tel.getTypes().addAll(Arrays.asList(TelephoneType.WORK, TelephoneType.VOICE));
			if (RecipientFieldCategory.WORK.equals(preferredTarget)) {
				tel.setPref(1);
			}
			props.add(tel);
		}
		if (!StringUtils.isBlank(contact.getWorkTelephone2())) {
			Telephone tel = new Telephone(contact.getWorkTelephone2());
			tel.getTypes().addAll(Arrays.asList(TelephoneType.WORK, TelephoneType.TEXT));
			props.add(tel);
		}
		if (!StringUtils.isBlank(contact.getWorkFax())) {
			Telephone tel = new Telephone(contact.getWorkFax());
			tel.getTypes().addAll(Arrays.asList(TelephoneType.WORK, TelephoneType.FAX));
			if (RecipientFieldCategory.WORK.equals(preferredTarget)) {
				tel.setPref(1);
			}
			props.add(tel);
		}
		if (!StringUtils.isBlank(contact.getHomeTelephone1())) {
			Telephone tel = new Telephone(contact.getHomeTelephone1());
			tel.getTypes().addAll(Arrays.asList(TelephoneType.HOME, TelephoneType.VOICE));
			if (RecipientFieldCategory.HOME.equals(preferredTarget)) {
				tel.setPref(1);
			}
			props.add(tel);
		}
		if (!StringUtils.isBlank(contact.getHomeTelephone2())) {
			Telephone tel = new Telephone(contact.getHomeTelephone2());
			tel.getTypes().addAll(Arrays.asList(TelephoneType.HOME, TelephoneType.TEXT));
			props.add(tel);
		}
		if (!StringUtils.isBlank(contact.getHomeFax())) {
			Telephone tel = new Telephone(contact.getHomeFax());
			tel.getTypes().addAll(Arrays.asList(TelephoneType.HOME, TelephoneType.FAX));
			if (RecipientFieldCategory.HOME.equals(preferredTarget)) {
				tel.setPref(1);
			}
			props.add(tel);
		}
		return props;
	}
	
	public List<Email> toEmails(ContactEx contact) {
		List<Email> props = new ArrayList<>();
		
		int count = 0;
		if (!StringUtils.isBlank(contact.getEmail1())) {
			Email em = new Email(contact.getEmail1());
			//em.getTypes().add(emailTypeMap[0]);
			if (count == 0) em.setPref(1);
			props.add(em);
		}
		if (!StringUtils.isBlank(contact.getEmail2())) {
			Email em = new Email(contact.getEmail2());
			//em.getTypes().add(emailTypeMap[1]);
			if (count == 0) em.setPref(1);
			props.add(em);
		}
		if (!StringUtils.isBlank(contact.getEmail3())) {
			Email em = new Email(contact.getEmail3());
			//em.getTypes().add(emailTypeMap[2]);
			if (count == 0) em.setPref(1);
			props.add(em);
		}
		return props;
	}
	
	public List<Impp> toImpps(ContactEx contact) {
		List<Impp> props = new ArrayList<>();
		
		int count = 0;
		if (!StringUtils.isBlank(contact.getInstantMsg1())) {
			Impp impp = new Impp(contact.getInstantMsg1());
			impp.getTypes().add(imppTypeMap[0]);
			if (count == 0) impp.setPref(1);
			props.add(impp);
		}
		if (!StringUtils.isBlank(contact.getInstantMsg2())) {
			Impp impp = new Impp(contact.getInstantMsg2());
			impp.getTypes().add(imppTypeMap[1]);
			if (count == 0) impp.setPref(1);
			props.add(impp);
		}
		if (!StringUtils.isBlank(contact.getInstantMsg3())) {
			Impp impp = new Impp(contact.getInstantMsg3());
			impp.getTypes().add(imppTypeMap[2]);
			if (count == 0) impp.setPref(1);
			props.add(impp);
		}
		return props;
	}
	
	public Organization toOrganization(ContactEx contact) {
		// The property value is a structured type consisting of the 
		// organization name, followed by zero or more levels of organizational 
		// unit names.
		Organization prop = null;
		boolean hasCompany = contact.hasCompany();
		boolean hasDept = !StringUtils.isBlank(contact.getDepartment());
		if (hasCompany || hasDept) {
			prop = new Organization();
			if (hasCompany) prop.getValues().add(StringUtils.defaultString(contact.getCompany().getCompanyDescription()));
			if (hasDept) prop.getValues().add(contact.getDepartment());
		}
		return prop;
	}
	
	public Title toTitle(ContactEx contact) {
		Title prop = null;
		if (!StringUtils.isBlank(contact.getFunction())) {
			prop = new Title(contact.getFunction());
		}
		return prop;
	}
	
	public Role toRole(ContactEx contact) {
		Role prop = null;
		if (!StringUtils.isBlank(contact.getFunction())) {
			prop = new Role(contact.getFunction());
		}
		return prop;
	}
	
	public Related toSpouse(ContactEx contact) {
		Related prop = null;
		if (!StringUtils.isBlank(contact.getPartner())) {
			prop = new Related(contact.getPartner());
			prop.getTypes().add(RelatedType.SPOUSE);
		}
		return prop;
	}
	
	public Birthday toBirthday(ContactEx contact) {
		Birthday prop = null;
		if (contact.getBirthday() != null) {
			prop = new Birthday(contact.getBirthday().toDate());
		}
		return prop;
	}
	
	public Anniversary toAnniversary(ContactEx contact) {
		Anniversary prop = null;
		if (contact.getAnniversary() != null) {
			prop = new Anniversary(contact.getAnniversary().toDate());
		}
		return prop;
	}
	
	public Url toUrl(ContactEx contact) {
		Url prop = null;
		if (!StringUtils.isBlank(contact.getUrl())) {
			prop = new Url(contact.getUrl());
		}
		return prop;
	}
	
	public Note toNotes(ContactEx contact) {
		Note prop = null;
		if (!StringUtils.isBlank(contact.getNotes())) {
			prop = new Note(contact.getNotes());
		}
		return prop;
	}
	
	public Photo toPhoto(ContactPictureWithBytes picture) {
		Photo prop = null;
		if (picture != null) {
			String mtype = picture.getMediaType();
			ImageType it = null;
			if (StringUtils.equals(mtype, ImageType.GIF.getMediaType()) || StringUtils.contains(mtype, "gif")) {
				it = ImageType.GIF;
			} else if (StringUtils.equals(mtype, ImageType.JPEG.getMediaType()) || StringUtils.contains(mtype, "jpeg")) {
				it = ImageType.JPEG;
			} else if (StringUtils.equals(mtype, ImageType.PNG.getMediaType()) || StringUtils.contains(mtype, "png")) {
				it = ImageType.PNG;
			}
			if (it != null) prop = new Photo(picture.getBytes(), it);
		}
		return prop;
	}
	
	public List<ExtendedProperty> toExtendedProperties(ContactEx c) {
		List<ExtendedProperty> exProps = new ArrayList<>();
		addPropIfValued(exProps, VCardExProps.MANAGER, c.getManager());
		addPropIfValued(exProps, VCardExProps.ASSISTANT, c.getAssistant());
		addPropIfValued(exProps, VCardExProps.ASSISTANT_TELEPHONE, c.getAssistantTelephone());
		addPropIfValued(exProps, VCardExProps.HREF, c.getHref());
		addPropIfValued(exProps, VCardExProps.TAGS, StringUtils.join(c.getTagsOrEmpty(), ","));
		//addPropIfValued(exProps, VCardExProps.CATEGORY_ID, String.valueOf(c.getCategoryId()));
		return exProps;
	}
	
	private void addPropIfValued(List<ExtendedProperty> props, String name, String value) {
		if (!StringUtils.isBlank(value))
			props.add(new ExtendedProperty(name, value));
	}
	
	private String deflt(String s) {
		return StringUtils.defaultIfEmpty(s, null);
	}
	
	private void log(LogHandler logHandler, int depth, LogEntry.Level level, String message, Object... arguments) {
		if (logHandler != null) {
			try {
				logHandler.handle(new LogMessage(depth, level, message, arguments));
			} catch(Throwable t) {}
		}
	}
	
	public static enum TagsMappingMode {
		NAME, ID, NONE
	}
}
