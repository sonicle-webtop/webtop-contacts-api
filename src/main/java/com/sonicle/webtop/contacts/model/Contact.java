/* 
 * Copyright (C) 2014 Sonicle S.r.l.
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
 * display the words "Copyright (C) 2014 Sonicle S.r.l.".
 */
package com.sonicle.webtop.contacts.model;

import com.google.gson.annotations.SerializedName;
import com.sonicle.commons.LangUtils;
import com.sonicle.webtop.core.model.CustomFieldValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.sf.qualitycheck.Check;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.joda.time.LocalDate;

/**
 *
 * @author malbinola
 */
public class Contact {
	protected Integer contactId;
	protected Integer categoryId;
	protected RevisionStatus revisionStatus;
	protected String publicUid;
	protected String displayName;
	protected String title;
	protected String firstName;
	protected String lastName;
	protected String nickname;
	protected Gender gender;
	protected String mobile;
	protected String pager1;
	protected String pager2;
	protected String email1;
	protected String email2;
	protected String email3;
	protected String instantMsg1;
	protected String instantMsg2;
	protected String instantMsg3;
	protected String workAddress;
	protected String workPostalCode;
	protected String workCity;
	protected String workState;
	protected String workCountry;
	protected String workTelephone1;
	protected String workTelephone2;
	protected String workFax;
	protected String homeAddress;
	protected String homePostalCode;
	protected String homeCity;
	protected String homeState;
	protected String homeCountry;
	protected String homeTelephone1;
	protected String homeTelephone2;
	protected String homeFax;
	protected String otherAddress;
	protected String otherPostalCode;
	protected String otherCity;
	protected String otherState;
	protected String otherCountry;
	protected String function;
	protected String department;
	protected String manager;
	protected String assistant;
	protected String assistantTelephone;
	protected String partner;
	protected LocalDate birthday;
	protected LocalDate anniversary;
	protected String url;
	protected String notes;
	protected String href;
	protected String etag;
	protected ContactCompany company;
	protected ContactPicture picture;
	protected Set<String> tags;
	protected List<ContactAttachment> attachments = new ArrayList<>();
	protected Map<String, CustomFieldValue> customValues = new LinkedHashMap<>();
	
	public Contact() {}
	
	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public RevisionStatus getRevisionStatus() {
		return revisionStatus;
	}

	public void setRevisionStatus(RevisionStatus revisionStatus) {
		this.revisionStatus = revisionStatus;
	}
	
	public String getPublicUid() {
		return publicUid;
	}

	public void setPublicUid(String publicUid) {
		this.publicUid = publicUid;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getPager1() {
		return pager1;
	}

	public void setPager1(String pager1) {
		this.pager1 = pager1;
	}
	
	public String getPager2() {
		return pager2;
	}

	public void setPager2(String pager2) {
		this.pager2 = pager2;
	}
	
	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	
	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	
	public String getEmail3() {
		return email3;
	}

	public void setEmail3(String email3) {
		this.email3 = email3;
	}
	
	public String getInstantMsg1() {
		return instantMsg1;
	}

	public void setInstantMsg1(String instantMsg1) {
		this.instantMsg1 = instantMsg1;
	}
	
	public String getInstantMsg2() {
		return instantMsg2;
	}

	public void setInstantMsg2(String instantMsg2) {
		this.instantMsg2 = instantMsg2;
	}
	
	public String getInstantMsg3() {
		return instantMsg3;
	}

	public void setInstantMsg3(String instantMsg3) {
		this.instantMsg3 = instantMsg3;
	}

	public String getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	public String getWorkPostalCode() {
		return workPostalCode;
	}

	public void setWorkPostalCode(String workPostalCode) {
		this.workPostalCode = workPostalCode;
	}

	public String getWorkCity() {
		return workCity;
	}

	public void setWorkCity(String workCity) {
		this.workCity = workCity;
	}

	public String getWorkState() {
		return workState;
	}

	public void setWorkState(String workState) {
		this.workState = workState;
	}

	public String getWorkCountry() {
		return workCountry;
	}

	public void setWorkCountry(String workCountry) {
		this.workCountry = workCountry;
	}
	
	public String getWorkTelephone1() {
		return workTelephone1;
	}

	public void setWorkTelephone1(String workTelephone1) {
		this.workTelephone1 = workTelephone1;
	}

	public String getWorkTelephone2() {
		return workTelephone2;
	}

	public void setWorkTelephone2(String workTelephone2) {
		this.workTelephone2 = workTelephone2;
	}

	public String getWorkFax() {
		return workFax;
	}

	public void setWorkFax(String workFax) {
		this.workFax = workFax;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getHomePostalCode() {
		return homePostalCode;
	}

	public void setHomePostalCode(String homePostalCode) {
		this.homePostalCode = homePostalCode;
	}

	public String getHomeCity() {
		return homeCity;
	}

	public void setHomeCity(String homeCity) {
		this.homeCity = homeCity;
	}

	public String getHomeState() {
		return homeState;
	}

	public void setHomeState(String homeState) {
		this.homeState = homeState;
	}

	public String getHomeCountry() {
		return homeCountry;
	}

	public void setHomeCountry(String homeCountry) {
		this.homeCountry = homeCountry;
	}

	public String getHomeTelephone1() {
		return homeTelephone1;
	}

	public void setHomeTelephone1(String homeTelephone1) {
		this.homeTelephone1 = homeTelephone1;
	}

	public String getHomeTelephone2() {
		return homeTelephone2;
	}

	public void setHomeTelephone2(String homeTelephone2) {
		this.homeTelephone2 = homeTelephone2;
	}

	public String getHomeFax() {
		return homeFax;
	}

	public void setHomeFax(String homeFax) {
		this.homeFax = homeFax;
	}

	public String getOtherAddress() {
		return otherAddress;
	}

	public void setOtherAddress(String otherAddress) {
		this.otherAddress = otherAddress;
	}

	public String getOtherPostalCode() {
		return otherPostalCode;
	}

	public void setOtherPostalCode(String otherPostalCode) {
		this.otherPostalCode = otherPostalCode;
	}

	public String getOtherCity() {
		return otherCity;
	}

	public void setOtherCity(String otherCity) {
		this.otherCity = otherCity;
	}

	public String getOtherState() {
		return otherState;
	}

	public void setOtherState(String otherState) {
		this.otherState = otherState;
	}

	public String getOtherCountry() {
		return otherCountry;
	}

	public void setOtherCountry(String otherCountry) {
		this.otherCountry = otherCountry;
	}

	/*
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	*/

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getAssistant() {
		return assistant;
	}

	public void setAssistant(String assistant) {
		this.assistant = assistant;
	}

	public String getAssistantTelephone() {
		return assistantTelephone;
	}

	public void setAssistantTelephone(String assistantTelephone) {
		this.assistantTelephone = assistantTelephone;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public LocalDate getAnniversary() {
		return anniversary;
	}

	public void setAnniversary(LocalDate anniversary) {
		this.anniversary = anniversary;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
	
	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}
	
	public ContactCompany getCompany() {
		return company;
	}

	public void setCompany(ContactCompany company) {
		this.company = company;
	}
	
	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}
	
	public Contact addTag(String tagId) {
		if (tags != null) {
			tags.add(Check.notNull(tagId, "tagId"));
		}
		return this;
	}
	
	public Contact removeTag(String tagId) {
		if (tags != null) {
			tags.remove(Check.notNull(tagId, "tagId"));
		}
		return this;
	}
	
	public ContactPicture getPicture() {
		return picture;
	}

	public void setPicture(ContactPicture picture) {
		this.picture = picture;
	}
	
	public List<ContactAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<ContactAttachment> attachments) {
		this.attachments = attachments;
	}
	
	public Map<String, CustomFieldValue> getCustomValues() {
		return customValues;
	}
	
	public void setCustomValues(Map<String, CustomFieldValue> customValues) {
		this.customValues = customValues;
	}
	
	public void setCustomValues(Collection<CustomFieldValue> customValues) {
		this.customValues = customValues.stream()
				.filter(item -> item.getFieldId() != null)
				.collect(Collectors.toMap(item -> item.getFieldId(), item -> item, (ov, nv) -> nv, LinkedHashMap::new));
	}
	
	public boolean hasCompany() {
		return company != null;
	}
	
	public boolean hasTags() {
		return tags != null;
	}
	
	public boolean hasPicture() {
		return picture != null;
	}
	
	public boolean hasAttachments() {
		return attachments != null;
	}
	
	public boolean hasCustomValues() {
		return customValues != null;
	}
	
	public String getDisplayName(boolean fallback) {
		String dn = getDisplayName();
		if (fallback) {
			return !StringUtils.isBlank(dn) ? dn : getFullName(true);
		} else {
			return dn;
		}
	}
	
	public String getComputedDisplayName(ShowBy showBy) {
		if (ShowBy.FIRST_LAST.equals(showBy)) {
			return BaseContact.buildFullName(getFirstName(), getLastName());
		} else if (ShowBy.LAST_FIRST.equals(showBy)) {
			return BaseContact.buildFullName(getLastName(), getFirstName());
		} else if (ShowBy.DISPLAY.equals(showBy)) {
			return getDisplayName(true);
		} else {
			return null;
		}
	}
	
	public String getFullName() {
		return getFullName(true);
	}
	
	public String getFullName(boolean firstLastOnly) {
		if (firstLastOnly) {
			return BaseContact.buildFullName(getFirstName(), getLastName());
		} else {
			return StringUtils.join(getTitle(), " ", getFirstName(), " ", getLastName()).trim();
		}
	}
	
	public String getWorkFullAddress() {
		return LangUtils.joinStrings(
				", ",
				getWorkAddress(),
				LangUtils.joinStrings(" ", getWorkPostalCode(), getWorkCity(), getWorkState()),
				getWorkCountry()
		);
	}
	
	public String getHomeFullAddress() {
		return LangUtils.joinStrings(
				", ",
				getHomeAddress(),
				LangUtils.joinStrings(" ", getHomePostalCode(), getHomeCity(), getHomeState()),
				getHomeCountry()
		);
	}
	
	public String getOtherFullAddress() {
		return LangUtils.joinStrings(
				", ",
				getOtherAddress(),
				LangUtils.joinStrings(" ", getOtherPostalCode(), getOtherCity(), getOtherState()),
				getOtherCountry()
		);
	}
	
	public boolean areNamesBlank(boolean firstLastOnly) {
		if (firstLastOnly) {
			return StringUtils.isBlank(getFirstName())
				&& StringUtils.isBlank(getLastName());
		} else {
			return StringUtils.isBlank(getFirstName())
				&& StringUtils.isBlank(getLastName())
				&& StringUtils.isBlank(getTitle());
		}
	}
	
	public boolean isNameEmpty() {
		return StringUtils.isEmpty(getFirstName())
			&& StringUtils.isEmpty(getLastName());
	}
	
	public boolean isWorkAddressEmpty() {
		return StringUtils.isBlank(getWorkAddress())
			&& StringUtils.isBlank(getWorkPostalCode())
			&& StringUtils.isBlank(getWorkCity())
			&& StringUtils.isBlank(getWorkState())
			&& StringUtils.isBlank(getWorkCountry());
	}
	
	public boolean isHomeAddressEmpty() {
		return StringUtils.isBlank(getHomeAddress())
			&& StringUtils.isBlank(getHomePostalCode())
			&& StringUtils.isBlank(getHomeCity())
			&& StringUtils.isBlank(getHomeState())
			&& StringUtils.isBlank(getHomeCountry());
	}
	
	public boolean isOtherAddressEmpty() {
		return StringUtils.isBlank(getOtherAddress())
			&& StringUtils.isBlank(getOtherPostalCode())
			&& StringUtils.isBlank(getOtherCity())
			&& StringUtils.isBlank(getOtherState())
			&& StringUtils.isBlank(getOtherCountry());
	}
	
	public boolean trimFieldLengths() {
		MutableBoolean trimmed = new MutableBoolean(false);
		setTitle(trimStringLength(trimmed, getTitle(), 30));
		setFirstName(trimStringLength(trimmed, getFirstName(), 255));
		setLastName(trimStringLength(trimmed, getLastName(), 255));
		setNickname(trimStringLength(trimmed, getNickname(), 60));
		setMobile(trimStringLength(trimmed, getMobile(), 50));
		setPager1(trimStringLength(trimmed, getPager1(), 50));
		setPager2(trimStringLength(trimmed, getPager2(), 50));
		setEmail1(trimStringLength(trimmed, getEmail1(), 320));
		setEmail2(trimStringLength(trimmed, getEmail2(), 320));
		setEmail3(trimStringLength(trimmed, getEmail3(), 320));
		setInstantMsg1(trimStringLength(trimmed, getInstantMsg1(), 200));
		setInstantMsg2(trimStringLength(trimmed, getInstantMsg2(), 200));
		setInstantMsg3(trimStringLength(trimmed, getInstantMsg3(), 200));
		setWorkAddress(trimStringLength(trimmed, getWorkAddress(), 100));
		setWorkCity(trimStringLength(trimmed, getWorkCity(), 50));
		setWorkState(trimStringLength(trimmed, getWorkState(), 30));
		setWorkPostalCode(trimStringLength(trimmed, getWorkPostalCode(), 20));
		setWorkCountry(trimStringLength(trimmed, getWorkCountry(), 30));
		setWorkTelephone1(trimStringLength(trimmed, getWorkTelephone1(), 50));
		setWorkTelephone2(trimStringLength(trimmed, getWorkTelephone2(), 50));
		setWorkFax(trimStringLength(trimmed, getWorkFax(), 50));
		setHomeAddress(trimStringLength(trimmed, getHomeAddress(), 100));
		setHomeCity(trimStringLength(trimmed, getHomeCity(), 50));
		setHomeState(trimStringLength(trimmed, getHomeState(), 30));
		setHomePostalCode(trimStringLength(trimmed, getHomePostalCode(), 20));
		setHomeCountry(trimStringLength(trimmed, getHomeCountry(), 30));
		setHomeTelephone1(trimStringLength(trimmed, getHomeTelephone1(), 50));
		setHomeTelephone2(trimStringLength(trimmed, getHomeTelephone2(), 50));
		setHomeFax(trimStringLength(trimmed, getHomeFax(), 50));
		setOtherAddress(trimStringLength(trimmed, getOtherAddress(), 100));
		setOtherCity(trimStringLength(trimmed, getOtherCity(), 50));
		setOtherState(trimStringLength(trimmed, getOtherState(), 30));
		setOtherPostalCode(trimStringLength(trimmed, getOtherPostalCode(), 20));
		setOtherCountry(trimStringLength(trimmed, getOtherCountry(), 30));
		//setCompanyDescription(trimStringLength(trimmed, getCompanyDescription(), 60));
		setFunction(trimStringLength(trimmed, getFunction(), 50));
		setAssistant(trimStringLength(trimmed, getAssistant(), 30));
		setAssistantTelephone(trimStringLength(trimmed, getAssistantTelephone(), 50));
		setDepartment(trimStringLength(trimmed, getDepartment(), 200));
		setManager(trimStringLength(trimmed, getManager(), 200));
		setPartner(trimStringLength(trimmed, getPartner(), 200));
		return trimmed.booleanValue();
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append(getContactId())
				.append(getFirstName())
				.append(getLastName())
				.toString();
	}
	
	private static String trimStringLength(MutableBoolean trimmed, String value, int maxLength) {
		if (StringUtils.length(value) > maxLength) {
			trimmed.setTrue();
			return StringUtils.left(value, maxLength);
		} else {
			return value;
		}
	}
	
	public static String buildUid(Object contactId) {
		return contactId.toString();
	}
	
	public static enum RevisionStatus {
		@SerializedName("N") NEW,
		@SerializedName("M") MODIFIED,
		@SerializedName("D") DELETED;
	}
	
	public static enum Gender {
		@SerializedName("male") MALE,
		@SerializedName("female") FEMALE,
		@SerializedName("other") OTHER;
	}
}
