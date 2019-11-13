/**
 *
 */
package vip.sunke.common;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class AuthFlagTag extends TagSupport {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private int authFlag;//某一个
    private int authFlags;//当前用户的


    public int getAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(int authFlag) {
        this.authFlag = authFlag;
    }

    public int getAuthFlags() {
        return authFlags;
    }

    public void setAuthFlags(int authFlags) {
        this.authFlags = authFlags;
    }

    @Override
    public int doStartTag() throws JspException {


        if ((authFlag & authFlags) == authFlag)
            return 1;


        return 0;
    }

    @Override
    public Object getValue(String k) {
        return super.getValue(k);
    }
}
