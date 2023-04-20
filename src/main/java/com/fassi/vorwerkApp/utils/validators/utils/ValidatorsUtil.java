package com.fassi.vorwerkApp.utils.validators.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.EmailValidator;

public class ValidatorsUtil {
    public static boolean isFirstName(String pattern) {
        return ValidatorsUtil.validate(pattern, "([a-zA-Z]{3,30}\s*)+");
    }

    public static boolean isLastName(String pattern) {
        return ValidatorsUtil.validate(pattern, "[a-zA-Z]{3,30}");
    }

    public static boolean isPhoneNumber(String pattern) {
        return ValidatorsUtil.isDecimalNumber(pattern) && pattern.length()==12;
        //ValidatorsUtil.validate(pattern,"^([1-9]\\d{2})([- .])(\\d{3})$2(\\d{4})$");
    }

    public static boolean isEmail(String pattern) {
        return EmailValidator.getInstance(true).isValid(pattern);
    }

    public static boolean isNumber(String pattern) {
        return ValidatorsUtil.validate(pattern, "/[1-9][0-9]*(?:\\/[1-9][0-9])*/g");
    }

    public static boolean isDecimalNumber(String pattern) {
        return ValidatorsUtil.validate(pattern, "^[0-9]+$");
    }

    public static boolean isFractionalNumber(String pattern) {
        return ValidatorsUtil.validate(pattern, "/[1-9][0-9]*\\/[1-9][0-9]*/g");
    }

    public static boolean validate(String pattern, String regex) {
        Pattern script = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        return script.matcher(pattern).find();
    }

    public static boolean isAddress(String pattern) {
        return ValidatorsUtil.validate(pattern, "[A-Za-z0-9'\\.\\-\\s\\,]");
    }
}
