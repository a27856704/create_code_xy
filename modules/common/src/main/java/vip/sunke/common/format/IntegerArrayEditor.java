/**
 *
 */
package vip.sunke.common.format;

import org.springframework.beans.propertyeditors.PropertiesEditor;

public class IntegerArrayEditor extends PropertiesEditor {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.equals("")) {
            setValue(null);
        } else {
            setValue(text);
        }


    }

    @Override
    public String getAsText() {
        if (getValue() != null)
            return getValue().toString();
        return "";
    }
}
