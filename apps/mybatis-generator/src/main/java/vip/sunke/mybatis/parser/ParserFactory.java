package vip.sunke.mybatis.parser;


import vip.sunke.mybatis.InputTypeEnum;

/**
 * @author sunke
 * @Date 2019-04-28 09:52
 * @description
 */
@Deprecated
public class ParserFactory {

    private static ParserFactory parseFactory;


    private ParserFactory() {

    }

    public static ParserFactory getInstance() {
        synchronized (ParserFactory.class) {
            if (parseFactory == null) {
                parseFactory = new ParserFactory();
            }
        }
        return parseFactory;
    }


    public IParser getParser(int inputType) {

        if (inputType == InputTypeEnum.TEXT.getType()) {

            return new InputParser();
        }
        if (inputType == InputTypeEnum.RADIO.getType()) {
            return new RadioParser();
        }

        if (inputType == InputTypeEnum.CHECKBOX.getType()) {
            return new CheckBoxParser();
        }


        return null;

    }


}
