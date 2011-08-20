package com.zipwhip.util;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ali
 * Date: Mar 22, 2010
 * Time: 2:04:58 PM
 *
 * A collection of useful utilities for working with strings.
 */
public class StringUtil {


    public static final String EMPTY_STRING = "";
    private static final List<String> VALID_NUMBERS = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    private static final String PLUS_MOBIFONE = "+84";
    private static final String PLUS = "+";

    public static boolean equals(String string1, String string2) {
        if (string1 == string2) {
            return true; // covers both null, or both same instance
        }
        if (string1 == null) {
            return false; // covers 1 null, other not.
        }

        return (string1.equals(string2)); // covers equals
    }

    public static boolean equalsIgnoreCase(String string1, String... strings) {
        if (CollectionUtil.isNullOrEmpty(strings)) {
            if (StringUtil.isNullOrEmpty(string1)) {
                return true;
            }
            return false;
        }

        for (String string : strings) {

            if (string1 == string) {
                return true; // covers both null, or both same instance
            }

            if ((string1 == null) || (string1 == "") || (string == null) || (string == "")) {
                return false;
            }

            if (string1 == null) {
                return false; // covers 1 null, other not.
            }

            if (string1.equalsIgnoreCase(string)) {
                // covers equals
                return true;
            }
        }
        return false;
    }

    public static String[] split(String s, String regex) {
        if (isNullOrEmpty(s) || isNullOrEmpty(regex)) {
            String[] out = new String[1];
            out[0] = s;
            return out;
        }
        return s.split(regex);
    }

    /**
     * Strips all characters that are not numbers (0 - 9) and returns a new string.
     * Returns and empty string if the mobile number is null or empty.
     *
     * @param mobileNumber        - mobile number string to parse
     * @param appendInternational appends (1) at the beginning of mobile number (international format)
     * @return String - parsed mobile number
     */
    public static String safeCleanMobileNumber(String mobileNumber, boolean appendInternational) {
        String cleanMobileNumber = safeCleanMobileNumber(mobileNumber);

        if (isNullOrEmpty(cleanMobileNumber)) {
            return EMPTY_STRING;
        }

        if (appendInternational && (cleanMobileNumber.length() == 10)) {
            return "1" + cleanMobileNumber;
        } else if (!appendInternational && (cleanMobileNumber.length() == 11) && equals(cleanMobileNumber.charAt(0), "1")) {
            return cleanMobileNumber.substring(1);
        } else if (!appendInternational && (cleanMobileNumber.length() == 13) && cleanMobileNumber.startsWith("001")) {
            return cleanMobileNumber.substring(3);
        }

        return cleanMobileNumber;
    }

    private static boolean equals(char string1, String string2) {
        return equals(new String(new char[]{string1}), string2);
    }

    /**
     * Strips all characters that are not numbers (0 - 9) and returns a new string.
     * Returns an empty string if the mobile number is null or empty.
     *
     * @param mobileNumber - mobile number string to parse
     * @return String - parsed mobile number
     */
    public static String safeCleanMobileNumber(String mobileNumber) {
        if (isNullOrEmpty(mobileNumber)) {
            return EMPTY_STRING;
        }

        StringBuilder cleanMobileNumber = new StringBuilder();

        int index = 0;
        if (mobileNumber.startsWith(PLUS_MOBIFONE)) {
            index++;
            cleanMobileNumber.append(PLUS);
        }

        for (int i = index; i < mobileNumber.length(); i++) {
            if (contains(VALID_NUMBERS, mobileNumber.charAt(i))) {
                cleanMobileNumber.append(mobileNumber.charAt(i));
            }
        }

        return cleanMobileNumber.toString();
    }

    private static boolean contains(List<String> validNumbers, char toFind) {
        if (CollectionUtil.isNullOrEmpty(validNumbers)) {
            return false;
        }

        String string = new String(new char[]{toFind});

        return validNumbers.contains(string);
    }

    public static boolean isNullOrEmpty(String string) {
        return (string == null) || string.isEmpty() || StringUtil.equalsIgnoreCase(string, "null");
    }

    public static String defaultValue(String value, String defaultValue) {
        return isNullOrEmpty(value) ? defaultValue : value;
    }

    public static String replaceAll(String initialString, String regex, String replacement) {
        if ((initialString == null) || (regex == null) || (replacement == null)) {
            return initialString;
        }
        return initialString.replaceAll(regex, replacement);
    }

    public static String replace(String s, CharSequence target, CharSequence newChar) {
        if ((s == null) || (target == null) || (newChar == null)) {
            return s;
        }
        return s.replace(target, newChar);
    }

    /**
     * Case insensitive.
     *
     * @param source
     * @param toFind
     * @return
     */
    public static boolean startsWith(String source, String toFind) {
        if (source == null) {
            return false;
        }
        if (toFind == null) {
            return false;
        }

        return (source.toLowerCase().startsWith(toFind.toLowerCase()));
    }

    public static boolean contains(String source, String toFind) {
        if (source == null) {
            return false;
        }
        if (toFind == null) {
            return false;
        }

        return (source.toLowerCase().contains(toFind.toLowerCase()));
    }

    public static String join(Collection<Object> parts) {
        if (CollectionUtil.isNullOrEmpty(parts)) {
            return null;
        }
        if (parts.size() > 5) {
            StringBuilder sb = new StringBuilder();
            for (Object part : parts) {
                sb.append(part);
            }
            return sb.toString();
        } else {
            String result = "";
            for (Object part : parts) {
                result += String.valueOf(part);
            }
            return result;
        }
    }

    //adding this method because the default Boolean.ParseBoolean(string s) method sucks and will interpret anything other than true as false
    public static Boolean parseBoolean(String s) {
        if (equals(s, "true")) {
            return true;
        } else if (equals(s, "false")) {
            return false;
        }
        return null;
    }

    public static <T> String join(String joiner, List<T> parts) {
        if (CollectionUtil.isNullOrEmpty(parts)) {
            return null;
        }
        int i = 0;
        if (parts.size() > 5) {
            StringBuilder sb = new StringBuilder();
            for (T part : parts) {
                if (part == null) {
                    continue;
                }

                if (i != 0) {
                    sb.append(joiner);
                }
                sb.append(part);
                i++;
            }
            return sb.toString();
        } else {
            String result = "";
            for (T part : parts) {
                if (part == null) {
                    continue;
                }

                if (i != 0) {
                    result += joiner;
                }
                result += String.valueOf(part);
                i++;
            }
            return result;
        }
    }

    private static String valueOf(Object p) {
        if (p == null) {
            return null;
        }
        if (p instanceof String) {
            return (String) p;
        }
        return String.valueOf(p);
    }

    public static String join(Object... parts) {
        if (CollectionUtil.isNullOrEmpty(parts)) {
            return null;
        }

        if (parts.length == 1) {
            return valueOf(parts[0]);
        }

        if (parts.length > 5) {
            StringBuilder sb = new StringBuilder();
            for (Object part : parts) {
                if (part == null) {
                    continue;
                }

                sb.append(part);
            }
            return sb.toString();
        } else {
            String result = null;
            for (Object part : parts) {
                if (part == null) {
                    continue;
                }

                if (result == null) {
                    result = valueOf(part);
                    continue;
                }

                result += valueOf(part);
            }
            return result;
        }
    }

    public static boolean containsExtendedChars(String data) {

        if (data == null) {
            return false;
        }
        byte[] bytes = data.getBytes();
        for (byte b : bytes) {
            int dec = b & 0xff;

            if ((dec >= 128) && (dec <= 255)) {
                return true;
            }
        }

        return false;
    }

    public static String stripNonValidXMLCharacters(String in) {
        StringBuffer out = new StringBuffer(); // Used to hold the output.
        char current; // Used to reference the current character.

        if ((in == null) || ("".equals(in))) {
            return ""; // vacancy test.
        }
        for (int i = 0; i < in.length(); i++) {
            current = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught here; it should not happen.
            if ((current == 0x9) ||
                    (current == 0xA) ||
                    (current == 0xD) ||
                    ((current >= 0x20) && (current <= 0xD7FF)) ||
                    ((current >= 0xE000) && (current <= 0xFFFD)) ||
                    ((current >= 0x10000) && (current <= 0x10FFFF))) {
                out.append(current);
            }
        }
        return out.toString();
    }

    public static String convertPatterns(String contents, Map<String, String> keyVals) {

        if (contents == null) {
            throw new NullPointerException("Cannot convert null pattern");
        }

        for (Map.Entry<String, String> entry : keyVals.entrySet()) {
            contents = contents.replaceAll(entry.getKey(), entry.getValue());
        }

        return contents;
    }

    public static String convertPatterns(
            final String contents,
            final String hostnamePattern,
            final String string
    ) {

        final Map<String, String> keyVals = new HashMap<String, String>();
        keyVals.put(hostnamePattern, string);

        return convertPatterns(contents, keyVals);

    }

    public static String joinAfter(List<String> arguments, int index) {
        if (CollectionUtil.isNullOrEmpty(arguments)) {
            return null;
        }

        if (arguments.size() <= index + 1) {
            return null;
        }

        // todo: make this safe
        return join(arguments.subList(index + 1, arguments.size() - 1));
    }

    public static String stripStringNull(String body) {
        if (equals(body, "null")) {
            return null;
        }
        return body;
    }
}