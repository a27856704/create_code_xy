/**
 *
 */
package vip.sunke.common.format;

import org.springframework.beans.propertyeditors.PropertiesEditor;

import java.math.BigDecimal;


public class BigDecimalEditor extends PropertiesEditor {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.equals("")) {
            text = "0";
        }
        setValue(new BigDecimal(text));
    }

    @Override
    public String getAsText() {
        return getValue().toString();

    }

}
