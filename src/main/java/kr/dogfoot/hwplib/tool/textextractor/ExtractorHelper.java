package kr.dogfoot.hwplib.tool.textextractor;

public class ExtractorHelper {

    public static void appendNormalStartTag(StringBuffer sb) {
        sb.append("<Normal>");
    }

    public static void appendNormalEndTag(StringBuffer sb) {
        sb.append("</Normal>");
    }

    public static void appendEquationTag(StringBuffer sb, String data) {
        sb.append("<Equation>").append(data).append("</Equation>");
    }

    public static void appendTableTag(StringBuffer sb, String data) {
        sb.append("<Table>\n").append(data).append("</Table>");
    }

    public static void appendImageTag(StringBuffer sb, int data) {
        sb.append("<Image>\n").append(data).append("</Image>");
    }
}
