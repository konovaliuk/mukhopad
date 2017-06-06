package ua.training.util;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.io.*;

public class TitleTag extends SimpleTagSupport {
    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    public void doTag() throws JspException, IOException {
        if (value != null) {
            JspWriter out = getJspContext().getOut();
            out.println(value);
        } else {
            StringWriter sw = new StringWriter();
            getJspBody().invoke(sw);
            getJspContext().getOut().println(sw.toString());
        }
    }
}