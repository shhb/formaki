package org.noranj.tax.v2012.shared.type;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

//XXX review the NOTE later
/**
 * This class defines the category of the simplified questions. There are a few
 * categories such as "Income, Deduction,...".
 * 
 * NOTE: There are some codes that are not used yet and will be sued later (when
 * needed). In that case, we don't need to change the codes but only the
 * descriptions. In the next release of the code, we can change Other4 with
 * appropriate name used in the system. (BA-2012-08-20)
 * 
 * This module, both source code and documentation, is in the Public Domain, and
 * comes with NO WARRANTY. See http://www.noranj.org for further information.
 * 
 * @author BA
 * @version 0.2.2012
 * @since 0.2.2012
 * @change
 */
public enum QuestionCategoryType implements Serializable, IsSerializable {

	Unknown(0), Introduction(10), // identification or registration or sign up
									// data
	Income(20), Deduction(30), Other4(40), // other will be used later
	Other5(50), // other will be used later
	Other6(60), // other will be used later
	Other7(70), // other will be used later
	Other8(80), // other will be used later
	Other9(90), // other will be used later
	Other10(100);// other will be used later

	QuestionCategoryType() {
		code = 0;
	}

	private int code;

	QuestionCategoryType(int code) {
		this.code = code;
	}

	/**
	 * It returns the enum code equivalent to the string format of the code.
	 * 
	 * NOTE: Couldn't find a better way to implement it.
	 * 
	 * @param codeStr
	 * @return
	 */
	public static QuestionCategoryType fromCodeString(String codeStr) {

		switch (Integer.valueOf(codeStr)) {
		case 10:
			return (QuestionCategoryType.Introduction);
		case 20:
			return (QuestionCategoryType.Income);
		case 30:
			return (QuestionCategoryType.Deduction);
		case 40:
			return (QuestionCategoryType.Other4);
		case 50:
			return (QuestionCategoryType.Other5);
		case 60:
			return (QuestionCategoryType.Other6);
		case 70:
			return (QuestionCategoryType.Other7);
		case 80:
			return (QuestionCategoryType.Other8);
		case 90:
			return (QuestionCategoryType.Other9);
		case 100:
			return (QuestionCategoryType.Other10);
		default:
			return (QuestionCategoryType.Unknown);
		} // switch

	} // toString

	public String codeToString() {
		return String.valueOf(code);
	}

}
