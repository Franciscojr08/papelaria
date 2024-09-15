package papelaria.ideal.api.utils;


public class Functions {

	public static boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}

	public static boolean isNotBlank(String value) {
		return value != null && !value.trim().isEmpty();
	}

	public static boolean isNotBlankLong(Long value) {
		return value != null;
	}

	public static boolean isNotBlankFloat(Float value) {
		return value != null;
	}
}
