package vip.sunke.common.format;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SKCustomDateEditor extends PropertyEditorSupport {

    private DateFormat dateFormat;// 当前格式化

    private boolean allowEmpty = false;


    private List<DateFormat> formatList = new ArrayList();


    public SKCustomDateEditor(DateFormat dateFormat, boolean allowEmpty) {
        this.formatList.add(dateFormat);
        this.dateFormat = dateFormat;
        this.allowEmpty = allowEmpty;

    }


    public void addDateFormat(DateFormat dateFormat) {
        this.formatList.add(dateFormat);
    }

    /**
     * Parse the Date from the given text, using the specified DateFormat.
     */

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && !StringUtils.hasText(text)) {
            // Treat empty String as null value.
            setValue(null);
            return;
        } else {
            for (DateFormat format : formatList) {
                try {
                    setValue(format.parse(text));
                    this.dateFormat = format;
                    return;
                } catch (ParseException ex) {
                    setValue(null);
                    //ex.printStackTrace();
                }
            }
        }


    }

    /**
     * Format the Date as String, using the specified DateFormat.
     */

    @Override
    public String getAsText() {
        Date value = (Date) getValue();
        return (value != null ? this.dateFormat.format(value) : "");
    }

}
