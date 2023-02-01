package kr.dogfoot.hwplib.tool.textextractor;

public class ExtractorHelper {

    public static void appendNormalStartTag(TextExtractOption option, StringBuffer sb) {
        insertTag(option, sb, "<Normal>");
    }

    public static void appendNormalEndTag(TextExtractOption option, StringBuffer sb) {
        insertTag(option, sb, "</Normal>");
    }

    public static void appendEquationTag(TextExtractOption option, StringBuffer sb, String data) {
        insertTag(option, sb, "<Equation>");
        sb.append(data);
        insertTag(option, sb, "</Equation>");
    }

    public static void appendTableTag(TextExtractOption option, StringBuffer sb, String data) {
        insertTag(option, sb, "<Table>\n");
        sb.append(data);
        insertTag(option, sb, "</Table>");
    }

    public static void appendImageTag(TextExtractOption option, StringBuffer sb, int data) {
        insertTag(option, sb, "<Image>\n");
        sb.append(data);
        insertTag(option, sb, "</Image>");
    }

    public static void insertTag(TextExtractOption option, StringBuffer sb, String tag) {
        if (option.isInsertTag()) {
            sb.append(tag);
        }
    }
}
