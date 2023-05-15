package com.example.testuri;

import android.text.TextUtils;

public class EditUtils {
    public static String getFormatMobile(String phoneNumber) {
        if (phoneNumber == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(phoneNumber);
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 4) {
            return phoneNumber;
        } else if (phoneNumber.length() < 8) {
            return sb.insert(3, ' ').toString();
        } else if (phoneNumber.length() <= 11) {
            return sb.insert(3, ' ').insert(8, ' ').toString();
        } else {
            return sb.insert(3, ' ').insert(8, ' ').toString().substring(0, 13);
        }
    }

    @ColorInt
    public static int getColorFromAttributeWithReveal(Context context, int attr, @ColorInt int defValue) {
        int[] attrs = {attr};
        int checkedColor = 0;
        try {
            TypedArray ta = context.obtainStyledAttributes(attrs);
            checkedColor = ta.getColor(0, defValue);
            ta.recycle();
        } catch (Exception ignore) {
            //ignore
        }
        return checkedColor;
    }

    @ColorInt
    public static int getAccentColor(Context context) {
        return getColorFromAttribute(context, R.attr.colorAccent);
    }
}
