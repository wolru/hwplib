package kr.dogfoot.hwplib.tool.textextractor;

import kr.dogfoot.hwplib.object.bodytext.ParagraphListInterface;
import kr.dogfoot.hwplib.object.bodytext.control.Control;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.HWPChar;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.HWPCharNormal;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.HWPCharType;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.ParaText;
import kr.dogfoot.hwplib.tool.textextractor.paraHead.ParaHeadMaker;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * 문단 리스트를 위한 텍스트 추출기 객체
 *
 * @author neolord
 */
public class ForParagraphList {
    /**
     * 문단 리스트에서 텍스트를 추출한다.
     *
     * @param paragraphList 문단 리스트
     * @param tem           텍스트 추출 방법
     * @param paraHeadMaker 문단 번호/글머리표 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    public static void extract(ParagraphListInterface paragraphList,
                               TextExtractMethod tem,
                               ParaHeadMaker paraHeadMaker,
                               StringBuffer sb) throws UnsupportedEncodingException {
        extract(paragraphList,
                new TextExtractOption(tem),
                paraHeadMaker,
                sb);
    }

    /**
     * 문단 리스트에서 텍스트를 추출한다.
     *
     * @param paragraphList 문단 리스트
     * @param option        추출 옵션
     * @param paraHeadMaker 문단 번호/글머리표 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    public static void extract(ParagraphListInterface paragraphList,
                               TextExtractOption option,
                               ParaHeadMaker paraHeadMaker,
                               StringBuffer sb) throws UnsupportedEncodingException {
        if (paragraphList == null) {
            return;
        }
        for (Paragraph p : paragraphList) {
            paragraph(p,
                    option,
                    paraHeadMaker,
                    sb);
        }
    }


    public static void extract(ParagraphListInterface paragraphList,
                               int startParaIndex,
                               int startCharIndex,
                               int endParaIndex,
                               int endCharIndex,
                               TextExtractOption option,
                               StringBuffer sb) throws UnsupportedEncodingException {
        if (startParaIndex == endParaIndex) {
            ForParagraphList.extract(paragraphList.getParagraph(startParaIndex),
                    startCharIndex, endCharIndex, option, null, sb);
        } else {
            ForParagraphList.extract(paragraphList.getParagraph(startParaIndex),
                    startCharIndex, option, null, sb);
            if (startParaIndex + 1 < endParaIndex) {
                for (int paraIndex = startParaIndex + 1; paraIndex <= endParaIndex - 1; paraIndex++) {
                    ForParagraphList.extract(paragraphList.getParagraph(paraIndex), -1, 0xffff, option, null, sb);
                }
            }

            ForParagraphList.extract(paragraphList.getParagraph(endParaIndex),
                    -1, endCharIndex, option, null, sb);
        }
    }

    public static void extract(ParagraphListInterface paragraphList,
                               int startParaIndex,
                               int startCharIndex,
                               int endParaIndex,
                               int endCharIndex,
                               TextExtractMethod tem,
                               StringBuffer sb) throws UnsupportedEncodingException {
        extract(paragraphList,
                startParaIndex,
                startCharIndex,
                endParaIndex,
                endCharIndex,
                new TextExtractOption(tem),
                sb);
    }

    /**
     * startIndex 순번 부터 끝 순번 까지의 문단의 텍스트를 추출한다.
     *
     * @param p             문단
     * @param startIndex    시작 순번
     * @param tem           텍스트 추출 방법
     * @param paraHeadMaker 문단 번호/글머리표 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    public static void extract(Paragraph p,
                               int startIndex,
                               TextExtractMethod tem,
                               ParaHeadMaker paraHeadMaker,
                               StringBuffer sb) throws UnsupportedEncodingException {

        extract(p,
                startIndex,
                new TextExtractOption(tem),
                paraHeadMaker,
                sb);
    }

    /**
     * startIndex 순번 부터 끝 순번 까지의 문단의 텍스트를 추출한다.
     *
     * @param p             문단
     * @param startIndex    시작 순번
     * @param option        추출 옵션
     * @param paraHeadMaker 문단 번호/글머리표 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    public static void extract(Paragraph p,
                               int startIndex,
                               TextExtractOption option,
                               ParaHeadMaker paraHeadMaker,
                               StringBuffer sb) throws UnsupportedEncodingException {
        ParaText pt = p.getText();
        if (pt != null) {
            extract(p,
                    startIndex,
                    pt.getCharList().size() - 1,
                    option,
                    paraHeadMaker,
                    sb);
        }
    }

    /**
     * startIndex 순번 부터 endIndex 순번 까지의 문단의 텍스트를 추출한다.
     *
     * @param p             문단
     * @param startIndex    시작 순번
     * @param endIndex      끝 순번
     * @param tem           텍스트 추출 방법
     * @param paraHeadMaker 문단 번호/글머리표 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    public static void extract(Paragraph p,
                               int startIndex,
                               int endIndex,
                               TextExtractMethod tem,
                               ParaHeadMaker paraHeadMaker,
                               StringBuffer sb) throws UnsupportedEncodingException {
        extract(p,
                startIndex,
                endIndex,
                new TextExtractOption(tem),
                paraHeadMaker,
                sb);
    }

    /**
     * startIndex 순번 부터 endIndex 순번 까지의 문단의 텍스트를 추출한다.
     *
     * @param p             문단
     * @param startIndex    시작 순번
     * @param endIndex      끝 순번
     * @param option        추출 옵션
     * @param paraHeadMaker 문단 번호/글머리표 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    public static void extract(Paragraph p,
                               int startIndex,
                               int endIndex,
                               TextExtractOption option,
                               ParaHeadMaker paraHeadMaker,
                               StringBuffer sb) throws UnsupportedEncodingException {

        ArrayList<Control> controlList = new ArrayList<Control>();
        ParaText pt = p.getText();
        if (pt != null) {
            int controlIndex = 0;

            int charCount = pt.getCharList().size();
            for (int charIndex = 0; charIndex < charCount; charIndex++) {
                HWPChar ch = pt.getCharList().get(charIndex);
                switch (ch.getType()) {
                    case Normal:
                        if (startIndex <= charIndex && charIndex <= endIndex) {
                            normalText(ch, sb);
                        }
                        break;
                    case ControlChar:
                    case ControlInline:
                        if (option.isWithControlChar()) {
                            controlText(ch, sb);
                        }
                        break;
                    case ControlExtend:
                        if (startIndex <= charIndex && charIndex <= endIndex) {
                            if (option.getMethod() == TextExtractMethod.InsertControlTextBetweenParagraphText) {
                                ForControl.extract(p.getControlList().get(controlIndex),
                                        option,
                                        paraHeadMaker,
                                        sb);
                            } else {
                                controlList.add(p.getControlList()
                                        .get(controlIndex));
                            }
                        }
                        controlIndex++;
                        break;
                    default:
                        break;
                }
            }

            if (option.isAppendEndingLF()) {
                sb.append("\n");
            }
        }

        if (option.getMethod() == TextExtractMethod.AppendControlTextAfterParagraphText) {
            controls(controlList, option, paraHeadMaker, sb);
        }
    }

    /**
     * 문단의 텍스트를 추출한다.
     *
     * @param p             문단
     * @param tem           텍스트 추출 방법
     * @param paraHeadMaker 문단 번호/글머리표 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    public static void paragraph(Paragraph p,
                                 TextExtractMethod tem,
                                 ParaHeadMaker paraHeadMaker,
                                 StringBuffer sb) throws UnsupportedEncodingException {
        paragraph(p,
                new TextExtractOption(tem),
                paraHeadMaker,
                sb);
    }

    /**
     * 문단의 텍스트를 추출한다.
     *
     * @param p             문단
     * @param option        추출 옵션
     * @param paraHeadMaker 문단 번호 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    public static void paragraph(Paragraph p,
                                 TextExtractOption option,
                                 ParaHeadMaker paraHeadMaker,
                                 StringBuffer sb) throws UnsupportedEncodingException {
        if (option.isInsertParaHead() && paraHeadMaker != null) {
            String headString = paraHeadMaker.paraHeadString(p);
            if (!headString.isEmpty()) {
                sb.append("\n");
            }
            sb.append(headString);
        }

        ParaText pt = p.getText();
        if (pt != null) {
            int controlIndex = 0;
            HWPCharType lastType = null;
            for (HWPChar ch : pt.getCharList()) {
                switch (ch.getType()) {
                    case Normal:
                        if (lastType != HWPCharType.Normal) {
                            ExtractorHelper.appendNormalStartTag(option, sb);
                        }
                        normalText(ch, sb);
                        break;
                    case ControlChar:
                    case ControlInline:
                        if (option.isWithControlChar()) {
                            if (lastType == HWPCharType.Normal) {
                                ExtractorHelper.appendNormalEndTag(option, sb);
                            }
                            controlText(ch, sb);
                        }
                        break;
                    case ControlExtend:
                        if (lastType == HWPCharType.Normal) {
                            ExtractorHelper.appendNormalEndTag(option, sb);
                        }
                        if (option.getMethod() == TextExtractMethod.InsertControlTextBetweenParagraphText) {
                            ForControl.extract(p.getControlList().get(controlIndex),
                                    option,
                                    paraHeadMaker,
                                    sb);
                            controlIndex++;
                        }
                        break;
                    default:
                        break;
                }
                lastType = ch.getType();
            }
            if (option.isAppendEndingLF()) {
                sb.append("\n").append("<br>").append("\n");
            }
        }
        if (option.getMethod() == TextExtractMethod.AppendControlTextAfterParagraphText) {
            controls(p.getControlList(), option, paraHeadMaker, sb);
        }
    }

    /**
     * 일반 문자에서 문자를 추출한다.
     *
     * @param ch 한글 문자
     * @param sb 추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    private static void normalText(HWPChar ch, StringBuffer sb) throws UnsupportedEncodingException {
        sb.append(((HWPCharNormal) ch).getCh());
    }

    private static void controlText(HWPChar ch, StringBuffer sb) {
        switch (ch.getCode()) {
            case 9:
                sb.append("\t");
                break;
            case 10:
                sb.append("\r\n");
                break;
            case 24:
                sb.append("_");
                break;
        }
    }

    /**
     * 컨트롤 리스트에 포함된 컨트롤에서 텍스트를 추출한다.
     *
     * @param controlList   컨트롤 리스트
     * @param option        추출 옵션
     * @param paraHeadMaker 문단 번호 생성기
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    private static void controls(ArrayList<Control> controlList,
                                 TextExtractOption option,
                                 ParaHeadMaker paraHeadMaker,
                                 StringBuffer sb) throws UnsupportedEncodingException {

        if (controlList != null) {
            for (Control c : controlList) {
                ForControl.extract(c, option, paraHeadMaker, sb);
            }
        }
    }

}
