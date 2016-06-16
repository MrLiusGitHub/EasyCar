package bean;

import android.text.TextUtils;

public class Car implements Groupable {
	public String name;
	public String logoUrl;
	public String initial;

	public String getGroupName() {
		if (TextUtils.isEmpty(name)) {
			return "";
		}
		return initial;
	}
}
