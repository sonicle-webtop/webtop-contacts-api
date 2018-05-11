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
import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.parameter.AddressType;
import ezvcard.parameter.EmailType;
import ezvcard.parameter.ImageType;
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
import ezvcard.property.Uid;
import ezvcard.property.Url;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author malbinola
 */
public class VCardOutput {
	private final String prodId;
	private final VCardVersion version;
	private final RecipientFieldCategory preferredTarget;
	private final EmailType otherEmailType = EmailType.AOL;
	private final ImppType otherImppType = ImppType.PERSONAL;
	private final AddressType otherAddressType = AddressType.POSTAL;
	
	public VCardOutput(String prodId) {
		this(prodId, VCardVersion.V4_0, RecipientFieldCategory.WORK);
	}
	
	public VCardOutput(String prodId, VCardVersion version) {
		this(prodId, version, RecipientFieldCategory.WORK);
	}
	
	public VCardOutput(String prodId, VCardVersion version, RecipientFieldCategory preferredTarget) {
		this.prodId = prodId;
		this.version = version;
		this.preferredTarget = preferredTarget;
	}
	
	public String write(VCard vCard) {
		return write(vCard, false);
	}
	
	public String write(VCard vCard, boolean keepFileVersion) {
		if (vCard == null) return null;
		VCardVersion targetVersion = version;
		if (keepFileVersion && (vCard.getVersion() != null)) {
			targetVersion = vCard.getVersion();
		}
		return Ezvcard.write(vCard).version(targetVersion).go();
	}
	
	public VCard toVCard(Contact contact, ContactPicture picture) throws WTException {
		VCard vCard = new VCard();
		
		// UID(?)
		vCard.setUid(toUid(contact));
		
		// FN(+) -> Formatted Name (full name)
		vCard.setFormattedName(toFormattedName(contact));
		
		// N(?) -> Structured Name (name components)
		vCard.setStructuredName(toStructuredName(contact));
		
		// NICKNAME(*)
		vCard.setNickname(toNickname(contact));
		
		// GENDER(?)
		vCard.setGender(toGender(contact));
		
		// ADR(*)
		for (Address address : toAddresses(contact)) {
			vCard.addAddress(address);
		}
		
		// TEL(*)
		for (Telephone telephone : toTelephones(contact)) {
			vCard.addTelephoneNumber(telephone);
		}
		
		// EMAIL(*)
		for (Email email : toEmails(contact)) {
			vCard.addEmail(email);
		}
		
		// IMPP(*) -> InstantMsg
		for (Impp impp : toImpps(contact)) {
			vCard.addImpp(impp);
		}
		
		// ORG(*) -> Company/Department
		vCard.setOrganization(toOrganization(contact));
		
		// TITLE(*) -> ??????
		
		// ROLE(*) -> Function
		Role role = toRole(contact);
		if (role != null) vCard.addRole(role);
		
		//TODO: come riempiamo il campo manager?
		//TODO: come riempiamo il campo assistant?
		//TODO: come riempiamo il campo telephoneAssistant?
		
		// BDAY(*)
		vCard.setBirthday(toBirthday(contact));
		
		// ANNIVERSARY(*)
		vCard.setAnniversary(toAnniversary(contact));
		
		// URL(*)
		Url url = toUrl(contact);
		if (url != null) vCard.addUrl(url);
		
		// NOTE(*)
		Note note = toNotes(contact);
		if (note != null) vCard.addNote(note);
		
		// PHOTO(*)
		if (picture != null) {
			Photo photo = toPhoto(picture);
			if (photo != null) vCard.addPhoto(photo);
		}
		
		return vCard;
	}
	
	public Uid toUid(Contact contact) {
		final String uid = contact.getPublicUid();
		return !StringUtils.isBlank(uid) ? new Uid(uid) : null;
	}
	
	public FormattedName toFormattedName(Contact contact) {
		return new FormattedName(contact.getFullName(false));
	}
	
	public StructuredName toStructuredName(Contact contact) {
		StructuredName prop = null;
		if (!contact.areNamesBlank(false)) {
			prop = new StructuredName();
			prop.setGiven(deflt(contact.getFirstName()));
			prop.setFamily(deflt(contact.getLastName()));
			if (!StringUtils.isBlank(contact.getTitle())) {
				prop.getPrefixes().add(contact.getTitle());
			}
		}
		return prop;
	}
	
	public Nickname toNickname(Contact contact) {
		Nickname prop = null;
		if (!StringUtils.isBlank(contact.getNickname())) {
			prop = new Nickname();
			prop.getValues().add(contact.getNickname());
		}
		return prop;
	}
	
	public Gender toGender(Contact contact) {
		Gender prop = null;
		if (Contact.Gender.MALE.equals(contact.getGender())) {
			prop = new Gender("MALE");
		} else if (Contact.Gender.FEMALE.equals(contact.getGender())) {
			prop = new Gender("FEMALE");
		} else if (Contact.Gender.OTHER.equals(contact.getGender())) {
			prop = new Gender("OTHER");
		}
		return prop;
	}
	
	public List<Address> toAddresses(Contact contact) {
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
		}
		return props;
	}
	
	public List<Telephone> toTelephones(Contact contact) {
		List<Telephone> props = new ArrayList<>();
		if (!StringUtils.isBlank(contact.getWorkTelephone())) {
			Telephone tel = new Telephone(contact.getWorkTelephone());
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
		if (!StringUtils.isBlank(contact.getWorkMobile())) {
			Telephone tel = new Telephone(contact.getWorkMobile());
			tel.getTypes().addAll(Arrays.asList(TelephoneType.WORK, TelephoneType.CELL));
			if (RecipientFieldCategory.WORK.equals(preferredTarget)) {
				tel.setPref(1);
			}
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
		if (!StringUtils.isBlank(contact.getWorkPager())) {
			Telephone tel = new Telephone(contact.getWorkPager());
			tel.getTypes().addAll(Arrays.asList(TelephoneType.WORK, TelephoneType.PAGER));
			if (RecipientFieldCategory.WORK.equals(preferredTarget)) {
				tel.setPref(1);
			}
			props.add(tel);
		}
		if (!StringUtils.isBlank(contact.getHomeTelephone())) {
			Telephone tel = new Telephone(contact.getHomeTelephone());
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
		if (!StringUtils.isBlank(contact.getHomePager())) {
			Telephone tel = new Telephone(contact.getHomePager());
			tel.getTypes().addAll(Arrays.asList(TelephoneType.HOME, TelephoneType.PAGER));
			if (RecipientFieldCategory.HOME.equals(preferredTarget)) {
				tel.setPref(1);
			}
			props.add(tel);
		}
		return props;
	}
	
	public List<Email> toEmails(Contact contact) {
		List<Email> props = new ArrayList<>();
		if (!StringUtils.isBlank(contact.getWorkEmail())) {
			Email em = new Email(contact.getWorkEmail());
			em.getTypes().add(EmailType.WORK);
			if (RecipientFieldCategory.WORK.equals(preferredTarget)) {
				em.setPref(1);
			}
			props.add(em);
		}
		if (!StringUtils.isBlank(contact.getHomeEmail())) {
			Email em = new Email(contact.getHomeEmail());
			em.getTypes().add(EmailType.HOME);
			if (RecipientFieldCategory.HOME.equals(preferredTarget)) {
				em.setPref(1);
			}
			props.add(em);
		}
		if (!StringUtils.isBlank(contact.getOtherEmail())) {
			Email em = new Email(contact.getOtherEmail());
			em.getTypes().add(otherEmailType);
			if (RecipientFieldCategory.OTHER.equals(preferredTarget)) {
				em.setPref(1);
			}
			props.add(em);
		}
		return props;
	}
	
	public List<Impp> toImpps(Contact contact) {
		List<Impp> props = new ArrayList<>();
		if (!StringUtils.isBlank(contact.getWorkInstantMsg())) {
			Impp impp = new Impp(contact.getWorkInstantMsg());
			impp.getTypes().add(ImppType.WORK);
			props.add(impp);
		}
		if (!StringUtils.isBlank(contact.getHomeInstantMsg())) {
			Impp impp = new Impp(contact.getHomeInstantMsg());
			impp.getTypes().add(ImppType.HOME);
			props.add(impp);
		}
		if (!StringUtils.isBlank(contact.getOtherInstantMsg())) {
			Impp impp = new Impp(contact.getOtherInstantMsg());
			impp.getTypes().add(otherImppType);
			props.add(impp);
		}
		return props;
	}
	
	public Organization toOrganization(Contact contact) {
		Organization prop = null;
		if (!StringUtils.isBlank(contact.getCompany()) || !StringUtils.isBlank(contact.getDepartment())) {
			prop = new Organization();
			prop.getValues().add(deflt(contact.getCompany()));
			prop.getValues().add(deflt(contact.getDepartment()));
		}
		return prop;
	}
	
	public Role toRole(Contact contact) {
		Role prop = null;
		if (!StringUtils.isBlank(contact.getFunction())) {
			prop = new Role(contact.getFunction());
		}
		return prop;
	}
	
	public Birthday toBirthday(Contact contact) {
		Birthday prop = null;
		if (contact.getBirthday() != null) {
			prop = new Birthday(contact.getBirthday().toDate());
		}
		return prop;
	}
	
	public Anniversary toAnniversary(Contact contact) {
		Anniversary prop = null;
		if (contact.getAnniversary() != null) {
			prop = new Anniversary(contact.getAnniversary().toDate());
		}
		return prop;
	}
	
	public Url toUrl(Contact contact) {
		Url prop = null;
		if (!StringUtils.isBlank(contact.getUrl())) {
			prop = new Url(contact.getUrl());
		}
		return prop;
	}
	
	public Note toNotes(Contact contact) {
		Note prop = null;
		if (!StringUtils.isBlank(contact.getNotes())) {
			prop = new Note(contact.getNotes());
		}
		return prop;
	}
	
	public Photo toPhoto(ContactPicture picture) {
		Photo prop = null;
		if (picture != null) {
			ImageType it = null;
			if (StringUtils.equals(picture.getMediaType(), ImageType.GIF.getMediaType())) {
				it = ImageType.GIF;
			} else if (StringUtils.equals(picture.getMediaType(), ImageType.JPEG.getMediaType())) {
				it = ImageType.JPEG;
			} else if (StringUtils.equals(picture.getMediaType(), ImageType.PNG.getMediaType())) {
				it = ImageType.PNG;
			}
			if (it != null) prop = new Photo(picture.getBytes(), it);
		}
		return prop;
	}
	
	private String deflt(String s) {
		return StringUtils.defaultIfEmpty(s, null);
	}
}
