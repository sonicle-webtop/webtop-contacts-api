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
import org.apache.commons.lang3.StringUtils;
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
	protected String title;
	protected String firstName;
	protected String lastName;
	protected String nickname;
	protected Gender gender;
	protected String workAddress;
	protected String workPostalCode;
	protected String workCity;
	protected String workState;
	protected String workCountry;
	protected String workTelephone;
	protected String workTelephone2;
	protected String workMobile;
	protected String workFax;
	protected String workPager;
	protected String workEmail;
	protected String workInstantMsg;
	protected String homeAddress;
	protected String homePostalCode;
	protected String homeCity;
	protected String homeState;
	protected String homeCountry;
	protected String homeTelephone;
	protected String homeTelephone2;
	protected String homeFax;
	protected String homePager;
	protected String homeEmail;
	protected String homeInstantMsg;
	protected String otherAddress;
	protected String otherPostalCode;
	protected String otherCity;
	protected String otherState;
	protected String otherCountry;
	protected String otherEmail;
	protected String otherInstantMsg;
	protected String company;
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
	protected boolean hasPicture;
	
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

	public String getWorkTelephone() {
		return workTelephone;
	}

	public void setWorkTelephone(String workTelephone) {
		this.workTelephone = workTelephone;
	}

	public String getWorkTelephone2() {
		return workTelephone2;
	}

	public void setWorkTelephone2(String workTelephone2) {
		this.workTelephone2 = workTelephone2;
	}

	public String getWorkMobile() {
		return workMobile;
	}

	public void setWorkMobile(String workMobile) {
		this.workMobile = workMobile;
	}

	public String getWorkFax() {
		return workFax;
	}

	public void setWorkFax(String workFax) {
		this.workFax = workFax;
	}

	public String getWorkPager() {
		return workPager;
	}

	public void setWorkPager(String workPager) {
		this.workPager = workPager;
	}

	public String getWorkEmail() {
		return workEmail;
	}

	public void setWorkEmail(String workEmail) {
		this.workEmail = workEmail;
	}

	public String getWorkInstantMsg() {
		return workInstantMsg;
	}

	public void setWorkInstantMsg(String workInstantMsg) {
		this.workInstantMsg = workInstantMsg;
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

	public String getHomeTelephone() {
		return homeTelephone;
	}

	public void setHomeTelephone(String homeTelephone) {
		this.homeTelephone = homeTelephone;
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

	public String getHomePager() {
		return homePager;
	}

	public void setHomePager(String homePager) {
		this.homePager = homePager;
	}

	public String getHomeEmail() {
		return homeEmail;
	}

	public void setHomeEmail(String homeEmail) {
		this.homeEmail = homeEmail;
	}

	public String getHomeInstantMsg() {
		return homeInstantMsg;
	}

	public void setHomeInstantMsg(String homeInstantMsg) {
		this.homeInstantMsg = homeInstantMsg;
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

	public String getOtherEmail() {
		return otherEmail;
	}

	public void setOtherEmail(String otherEmail) {
		this.otherEmail = otherEmail;
	}

	public String getOtherInstantMsg() {
		return otherInstantMsg;
	}

	public void setOtherInstantMsg(String otherInstantMsg) {
		this.otherInstantMsg = otherInstantMsg;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

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
	
	public boolean getHasPicture() {
		return hasPicture;
	}

	public void setHasPicture(boolean hasPicture) {
		this.hasPicture = hasPicture;
	}
	
	public String getFullName() {
		return getFullName(true);
	}
	
	public String getFullName(boolean firstLastOnly) {
		if (firstLastOnly) {
			return StringUtils.join(getFirstName(), " ", getLastName()).trim();
		} else {
			return StringUtils.join(getTitle(), " ", getFirstName(), " ", getLastName()).trim();
		}
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
		setTitle(trimStringLength(getTitle(), 30, trimmed));
		setFirstName(trimStringLength(getFirstName(), 255, trimmed));
		setLastName(trimStringLength(getLastName(), 255, trimmed));
		setNickname(trimStringLength(getNickname(), 60, trimmed));
		setCompany(trimStringLength(getCompany(), 60, trimmed));
		setFunction(trimStringLength(getFunction(), 50, trimmed));
		setWorkAddress(trimStringLength(getWorkAddress(), 100, trimmed));
		setWorkCity(trimStringLength(getWorkCity(), 50, trimmed));
		setWorkState(trimStringLength(getWorkState(), 30, trimmed));
		setWorkPostalCode(trimStringLength(getWorkPostalCode(), 20, trimmed));
		setWorkCountry(trimStringLength(getWorkCountry(), 30, trimmed));
		setWorkTelephone(trimStringLength(getWorkTelephone(), 50, trimmed));
		setWorkTelephone2(trimStringLength(getWorkTelephone2(), 50, trimmed));
		setWorkFax(trimStringLength(getWorkFax(), 50, trimmed));
		setWorkMobile(trimStringLength(getWorkMobile(), 50, trimmed));
		setWorkPager(trimStringLength(getWorkPager(), 50, trimmed));
		setWorkEmail(trimStringLength(getWorkEmail(), 320, trimmed));
		setWorkInstantMsg(trimStringLength(getWorkInstantMsg(), 200, trimmed));
		setAssistant(trimStringLength(getAssistant(), 30, trimmed));
		setAssistantTelephone(trimStringLength(getAssistantTelephone(), 50, trimmed));
		setDepartment(trimStringLength(getDepartment(), 200, trimmed));
		setManager(trimStringLength(getManager(), 200, trimmed));
		setHomeAddress(trimStringLength(getHomeAddress(), 100, trimmed));
		setHomeCity(trimStringLength(getHomeCity(), 50, trimmed));
		setHomeState(trimStringLength(getHomeState(), 30, trimmed));
		setHomePostalCode(trimStringLength(getHomePostalCode(), 20, trimmed));
		setHomeCountry(trimStringLength(getHomeCountry(), 30, trimmed));
		setHomeTelephone(trimStringLength(getHomeTelephone(), 50, trimmed));
		setHomeTelephone2(trimStringLength(getHomeTelephone2(), 50, trimmed));
		setHomeFax(trimStringLength(getHomeFax(), 50, trimmed));
		setHomePager(trimStringLength(getHomePager(), 50, trimmed));
		setHomeEmail(trimStringLength(getHomeEmail(), 320, trimmed));
		setHomeInstantMsg(trimStringLength(getHomeInstantMsg(), 200, trimmed));
		setPartner(trimStringLength(getPartner(), 200, trimmed));
		setOtherAddress(trimStringLength(getOtherAddress(), 100, trimmed));
		setOtherCity(trimStringLength(getOtherCity(), 50, trimmed));
		setOtherState(trimStringLength(getOtherState(), 30, trimmed));
		setOtherPostalCode(trimStringLength(getOtherPostalCode(), 20, trimmed));
		setOtherCountry(trimStringLength(getOtherCountry(), 30, trimmed));
		setOtherEmail(trimStringLength(getOtherEmail(), 320, trimmed));
		setOtherInstantMsg(trimStringLength(getOtherInstantMsg(), 200, trimmed));
		return trimmed.booleanValue();
	}
	
	private static String trimStringLength(String value, int maxLength, MutableBoolean trimmed) {
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
	
	public static String buildFullName(String firstName, String lastName) {
		return StringUtils.trim(StringUtils.defaultString(firstName) + " " + StringUtils.defaultString(lastName));
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
