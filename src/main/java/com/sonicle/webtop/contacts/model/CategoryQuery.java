/*
 * Copyright (C) 2025 Sonicle S.r.l.
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
 * display the words "Copyright (C) 2025 Sonicle S.r.l.".
 */
package com.sonicle.webtop.contacts.model;

import com.sonicle.commons.qbuilders.properties.concrete.BooleanProperty;
import com.sonicle.commons.qbuilders.properties.concrete.StringProperty;
import com.sonicle.webtop.core.app.sdk.QueryBuilder;

/**
 *
 * @author malbinola
 */
public class CategoryQuery extends QueryBuilder<CategoryQuery> {
	public static final String ID = "id";
	public static final String BUILT_IN = "builtIn";
	public static final String PROVIDER = "provider";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String COLOR = "color";
	public static final String SYNC = "sync";
	public static final String IS_DEFAULT = "default";
	
	public StringProperty<CategoryQuery> id() {
		return string(ID);
	}
	
	public BooleanProperty<CategoryQuery> builtIn() {
		return bool(BUILT_IN);
	}
	
	public StringProperty<CategoryQuery> provider() {
		return string(PROVIDER);
	}

	public StringProperty<CategoryQuery> name() {
		return string(NAME);
	}
	
	public StringProperty<CategoryQuery> description() {
		return string(DESCRIPTION);
	}

	public StringProperty<CategoryQuery> color() {
		return string(COLOR);
	}

	public StringProperty<CategoryQuery> sync() {
		return string(SYNC);
	}
	
	public BooleanProperty<CategoryQuery> isDefault() {
		return bool(IS_DEFAULT);
	}
	
	
	
	
	/*
	public static Condition<CategoryQuery> createCondition(QueryObj query, DateTimeZone timezone) {
		boolean smartStringComparison = true;
		
		Condition<CategoryQuery> last = new CategoryQuery().trueCondition();
		for (Map.Entry<QueryObj.Condition, List<String>> entry : query.groupConditions().entrySet()) {
			final QueryObj.Condition key = entry.getKey();
			final List<String> values = entry.getValue();
			
			if (values.isEmpty() || values.size() == 1) {
				last = new CategoryQuery().and(last, createCondition(key, values.isEmpty() ? null : values.get(0), timezone, smartStringComparison));
			} else {
				List<Condition<CategoryQuery>> conds = new ArrayList<>();
				for (String value : values) {
					conds.add(createCondition(key, value, timezone, smartStringComparison));
				}
				last = new CategoryQuery().and(last, new CategoryQuery().or(conds));
			}
		}
		
		return last;
	}
	
	private static Condition<CategoryQuery> createCondition(QueryObj.Condition condition, String value, DateTimeZone timezone, boolean smartStringComparison) {
		if ("id".equals(condition.keyword)) {
			return new CategoryQuery().id().eq(asStringValue(value, false));
			
		} else if ("builtIn".equals(condition.keyword)) {
			return condition.negated ? new CategoryQuery().builtIn().isFalse() : new CategoryQuery().builtIn().isTrue();

		} else if ("provider".equals(condition.keyword)) {
			if (EnumUtils.forString(value, CategoryBase.Provider.class, true) == null) {
				throw new UnsupportedOperationException(condition.keyword + ":" + value);
			}
			return new CategoryQuery().provider().eq(value);

		} else if ("name".equals(condition.keyword)) {
			return new CategoryQuery().name().eq(asStringValue(value, false));

		} else if ("description".equals(condition.keyword)) {
			return new CategoryQuery().description().eq(asStringValue(value, smartStringComparison));

		} else if ("color".equals(condition.keyword)) {
			return new CategoryQuery().color().eq(asStringValue(value, false));

		} else if ("sync".equals(condition.keyword)) {
			if (EnumUtils.forString(value, CategoryBase.Sync.class, true) == null) {
				throw new UnsupportedOperationException(condition.keyword + ":" + value);
			}
			return new CategoryQuery().sync().eq(value);

		} else if ("default".equals(condition.keyword)) {
			return condition.negated ? new CategoryQuery().isDefault().isFalse() : new CategoryQuery().isDefault().isTrue();
		}
		
		throw new WTUnsupportedOperationException("Unsupported keyword '{}'", condition.keyword);
	}
	*/
}
