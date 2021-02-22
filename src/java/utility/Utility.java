/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

/**
 *
 * @author Amila_10367
 */
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Utility {

    private static int daysInMonth[] = {
        31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
        30, 31
    };
    private static String loanList[] = {
        "1AF", "1PL", "1ST", "2AF", "3AF", "IL"
    };
    private static String strMonths[] = {
        "January", "February", "March", "April", "May", "June", "July", "August", "September", "October",
        "November", "December"
    };

    public Utility() {
    }

    public static int getBankCode() {
        return 7214;
    }

    public static int getBranchCode() {
        return 900;
    }

    public static String getLanguage(int languageID) {
        switch (languageID) {
            case 1: // '\001'
                return "ENGLISH";

            case 2: // '\002'
                return "SINHALA";

            case 3: // '\003'
                return "TAMIL";
        }
        return "ENGLISH";
    }

    public static int getLanguageID(char languageCode) {
        switch (languageCode) {
            case 69: // 'E'
                return 1;

            case 83: // 'S'
                return 2;

            case 84: // 'T'
                return 3;
        }
        return 1;
    }

    public static int getNumericMonth(String month) {
        String months[] = {
            "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT",
            "NOV", "DEC"
        };
        for (int i = 0; i < 12; i++) {
            if (months[i].equalsIgnoreCase(month)) {
                return i + 1;
            }
        }

        return 0;
    }

    public static String getMonthName(int month) {
        String strMonths[] = {
            "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT",
            "NOV", "DEC"
        };
        return strMonths[month - 1];
    }

    public static String getMonthFullName(int month, int language) {
        return strMonths[month - 1];
    }

    public static String getMonthFullName(String month) {
        return getMonthFullName(Integer.parseInt(month));
    }

    public static String getMonthFullName(int month) {
        return strMonths[month - 1];
    }

    public static int getLastDayOfMonth(int year, int month) {
        if (month == 2) {
            return (year % 4 != 0 || year % 100 == 0) && year % 400 != 0 ? 28 : 29;
        } else {
            return daysInMonth[month - 1];
        }
    }

    public static String addZeroRJ(String str, int length) {
        return str.length() >= length ? str : addZeroRJ((new StringBuilder("0")).append(str).toString(), length);
    }

    public static String paddSpaceLJ(String str, int length) {
        return str.length() > length ? str.substring(0, length) : paddSpaceLJ((new StringBuilder(String.valueOf(str))).append(" ").toString(), length);
    }

    public static String paddSpaceLJ2(String str, int length) {
        return str.length() >= length ? str : paddSpaceLJ2((new StringBuilder(String.valueOf(str))).append("  ").toString(), length + 1);
    }

    public static String paddSpaceRJ(String str, int length) {
        return str.length() >= length ? str : paddSpaceRJ((new StringBuilder(" ")).append(str).toString(), length);
    }

    public static String paddSpaceRJ2(String str, int length) {
        return str.length() >= length ? str : paddSpaceRJ2((new StringBuilder("   ")).append(str).toString(), length + 1);
    }

    public static String paddSpaceRJ3(String str, int length) {
        return str.length() >= length ? str : paddSpaceRJ3((new StringBuilder("       ")).append(str).toString(), length + 2);
    }

    public static String paddSpaceCenter(String str, int length) {
        return str.length() >= length ? str : paddSpaceCenter((new StringBuilder(" ")).append(str).append(" ").toString(), length);
    }

    public static String formatCurrency(String value) {
        BigDecimal bg = new BigDecimal(value);
        return formatCurrency(bg.doubleValue());
    }

    public static String formatCurrency(double value) {
        DecimalFormat df = new DecimalFormat("###,##0.00;(###,##0.00)");
        return df.format(value);
    }

    public static String formatCurrency(String value, String delim) {
        BigDecimal bg = new BigDecimal(value);
        return formatCurrency(bg.doubleValue(), delim);
    }

    public static String formatCurrency(double value, String delim) {
        DecimalFormat df = new DecimalFormat((new StringBuilder("###,##0.00;")).append(delim).append("###,##0.00").toString());
        return df.format(value);
    }

    public static String formatCurrency(double value, String delim, String decimal) {
        DecimalFormat df = new DecimalFormat((new StringBuilder("###,##0.")).append(decimal).append(";").append(delim).append("###,##0.").append(decimal).toString());
        return df.format(value);
    }

    public static String formatCurrency(String value, String delim, String decimal) {
        BigDecimal bg = new BigDecimal(value);
        return formatCurrency(bg.doubleValue(), delim, decimal);
    }

    public static String formatCurrency(BigDecimal value) {
        String output = "";
        String decimal = "00";
        String tempStr = value.toString();
        int index = tempStr.indexOf(".");
        if (index > 0) {
            decimal = tempStr.substring(index + 1);
            tempStr = tempStr.substring(0, index);
        }
        index = tempStr.length();
        for (int x = 0; x < tempStr.length(); x++) {
            if (x % 3 == 0 && x != 0) {
                output = (new StringBuilder(",")).append(output).toString();
            }
            output = (new StringBuilder(String.valueOf(tempStr.charAt(index - x - 1)))).append(output).toString();
        }

        return (new StringBuilder(String.valueOf(output))).append(".").append(decimal).toString();
    }

    public static String formatCurrency2(String value, String decimal) {
        return formatCurrency2(Double.parseDouble(value), decimal);
    }

    public static String formatCurrency2(double value, String decimal) {
        DecimalFormat df = new DecimalFormat((new StringBuilder("#####0.")).append(decimal).append(";-#####0.").append(decimal).toString());
        return df.format(value);
    }

    public static String formatNumber(int value) {
        DecimalFormat df = new DecimalFormat("###,##0;(###,##0)");
        return df.format(value);
    }

    public static String formatNumber(String value) {
        BigInteger bi = new BigInteger(value);
        return formatNumber(bi.intValue());
    }

    public static String formatNumber(long value) {
        BigInteger bi = new BigInteger(String.valueOf(value));
        return formatNumber(bi.intValue());
    }

    public static String formatNumber(BigDecimal value) {
        String output = "";
        String tempStr = value.toString();
        int index = tempStr.indexOf(".");
        if (index > 0) {
            tempStr = tempStr.substring(0, index);
        }
        index = tempStr.length();
        for (int x = 0; x < tempStr.length(); x++) {
            if (x % 3 == 0 && x != 0) {
                output = (new StringBuilder(",")).append(output).toString();
            }
            output = (new StringBuilder(String.valueOf(tempStr.charAt(index - x - 1)))).append(output).toString();
        }

        return output;
    }

    public static String reFormatCurrency(String value) {
        StringBuffer buf = new StringBuffer();
        int length = value.length();
        for (int i = 0; i < length; i++) {
            char ch = value.charAt(i);
            if (ch == ',') {
                buf.append("");
            } else {
                buf.append(ch);
            }
        }

        return buf.toString();
    }

    public static String toMySqlDate(String inputDate) {
        String mysqlDate = "";
        mysqlDate = (new StringBuilder("20")).append(inputDate.substring(7, 9)).append("-").append(addZeroRJ(String.valueOf(getNumericMonth(inputDate.substring(3, 6))), 2)).append("-").append(inputDate.substring(0, 2)).toString();
        return mysqlDate;
    }

    public static String formatHTMLText(String text) {
        StringBuffer buf = new StringBuffer();
        int length = text.length();
        for (int i = 0; i < length; i++) {
            switch (text.charAt(i)) {
                case 38: // '&'
                    buf.append("and");
                    break;

                case 60: // '<'
                    buf.append("&lt;");
                    break;

                case 62: // '>'
                    buf.append("&gt;");
                    break;

                case 34: // '"'
                    buf.append("&quot;");
                    break;

                case 39: // '\''
                    buf.append("&quot;");
                    break;

                default:
                    buf.append(text.charAt(i));
                    break;
            }
        }

        return buf.toString();
    }

    public static String formatURL(String url) {
        StringBuffer buf = new StringBuffer();
        int len = url.length();
        for (int i = 0; i < len; i++) {
            switch (url.charAt(i)) {
                case 32: // ' '
                    buf.append("%20");
                    break;

                case 58: // ':'
                    buf.append("%3a");
                    break;

                case 47: // '/'
                    buf.append("%2f");
                    break;

                default:
                    buf.append(url.charAt(i));
                    break;
            }
        }

        return buf.toString();
    }

    public static String formatFileUploadURL(String url) {
        StringBuffer buf = new StringBuffer();
        int len = url.length();
        for (int i = 0; i < len; i++) {
            switch (url.charAt(i)) {
                case 92: // '\\'
                    buf.append("/");
                    break;

                default:
                    buf.append(url.charAt(i));
                    break;
            }
        }

        return buf.toString();
    }

    public static String formatDBString(String text) {
        StringBuffer buf = new StringBuffer();
        if (!text.equals("") && text != null) {
            int length = text.length();
            for (int i = 0; i < length; i++) {
                switch (text.charAt(i)) {
                    case 34: // '"'
                        buf.append("~");
                        break;

                    case 39: // '\''
                        buf.append("~");
                        break;

                    default:
                        buf.append(text.charAt(i));
                        break;
                }
            }

        }
        return buf.toString();
    }

    public static String reFormatDBString(String text) {
        StringBuffer buf = new StringBuffer();
        if (!text.equals("") && text != null) {
            int length = text.length();
            for (int i = 0; i < length; i++) {
                switch (text.charAt(i)) {
                    case 126: // '~'
                        buf.append("'");
                        break;

                    default:
                        buf.append(text.charAt(i));
                        break;
                }
            }

        }
        return buf.toString();
    }

    private static String javaDate(String fmt) {
        TimeZone gmt530 = TimeZone.getTimeZone("GMT");
        gmt530.setRawOffset(0x12e1fc0);
        SimpleDateFormat formatter = new SimpleDateFormat(fmt);
        formatter.setTimeZone(gmt530);
        String dd = formatter.format(new Date());
        return dd;
    }

    public static String sysDate() {
        return javaDate("yyyy-MM-dd");
    }

    public static String sysDate(String dateFormat) {
        return javaDate(dateFormat);
    }

    public static String sysTime() {
        return javaDate("HH.mm.ss");
    }

    public static boolean isLoan(String accountType) {
        boolean status = false;
        for (int i = 0; i < loanList.length; i++) {
            if (!loanList[i].equals(accountType)) {
                continue;
            }
            status = true;
            break;
        }

        return status;
    }

    public static String getFullSystemUserType(char type) {
        type = Character.toUpperCase(type);
        switch (type) {
            case 79: // 'O'
                return "OPERATOR";

            case 83: // 'S'
                return "OFFICER";

            case 77: // 'M'
                return "MANAGER";

            case 78: // 'N'
            case 80: // 'P'
            case 81: // 'Q'
            case 82: // 'R'
            default:
                return "";
        }
    }

    public static String getFullSystemUserStatus(char userStatus) {
        userStatus = Character.toUpperCase(userStatus);
        switch (userStatus) {
            case 65: // 'A'
                return "ACTIVE";

            case 68: // 'D'
                return "DISABLED";

            case 76: // 'L'
                return "LOCKED";

            case 80: // 'P'
                return "PENDING LOGIN";
        }
        return "";
    }

    public static String getFullBranchStatus(char status) {
        status = Character.toUpperCase(status);
        switch (status) {
            case 65: // 'A'
                return "ACTIVE";

            case 67: // 'C'
                return "CLOSED";

            case 68: // 'D'
                return "DISABLED";

            case 66: // 'B'
            default:
                return "";
        }
    }

    public static String getFullAlertType(char type) {
        type = Character.toUpperCase(type);
        switch (type) {
            case 65: // 'A'
                return "ACCOUNT";

            case 66: // 'B'
                return "BALANCE";

            case 67: // 'C'
                return "CREDIT";

            case 68: // 'D'
                return "DEBIT";

            case 84: // 'T'
                return "TRANSACTION";
        }
        return "";
    }

    public static String getFullUtilityProviderStatus(char status) {
        status = Character.toUpperCase(status);
        switch (status) {
            case 65: // 'A'
                return "ACTIVE";

            case 67: // 'C'
                return "CLOSED";

            case 68: // 'D'
                return "DISABLED";

            case 66: // 'B'
            default:
                return "";
        }
    }

    public static boolean validateSLTInvoice(String invoice) {
        invoice = invoice.toUpperCase().trim();
        char tempCh = ' ';
        String tempStr = "";
        for (int x = 0; x < invoice.length(); x++) {
            tempCh = invoice.charAt(x);
            if (tempCh != ' ' && tempCh != '-') {
                tempStr = (new StringBuilder(String.valueOf(tempStr))).append(tempCh).toString();
            }
        }

        invoice = tempStr.trim();
        int iLength = invoice.length();
        if (iLength != 14) {
            return false;
        }
        int aWeights1[] = {
            2, 3, 5, 7, 2, 3, 5, 7, 2
        };
        int aWeights2[] = {
            5, 7, 2, 3, 7, 2, 3, 5, 2, 3,
            5, 7, 2
        };
        char iCalcCheckDigit1 = '\0';
        char iCalcCheckDigit2 = 'X';
        char iCheckDigit1 = invoice.charAt(9);
        char iCheckDigit2 = invoice.charAt(13);
        int iSum = 0;
        int iMod = 0;
        char aNum[] = invoice.toCharArray();
        for (int i = 0; i < 9; i++) {
            iSum += aWeights1[i] * (aNum[i] - 48);
        }

        iMod = iSum % 11;
        if (iMod == 10) {
            iCalcCheckDigit1 = 'X';
        } else {
            iCalcCheckDigit1 = String.valueOf(iMod).charAt(0);
        }
        iMod = iSum = 0;
        for (int i = 0; i < 13; i++) {
            if (i == 9 && aNum[i] == 'X') {
                iSum += 8;
            } else {
                iSum += aWeights2[i] * (aNum[i] - 48);
            }
        }

        iMod = iSum % 11;
        if (iMod == 10) {
            iCalcCheckDigit2 = 'X';
        } else {
            iCalcCheckDigit2 = String.valueOf(iMod).charAt(0);
        }
        return iCheckDigit1 == iCalcCheckDigit1 && iCheckDigit2 == iCalcCheckDigit2;
    }
}
