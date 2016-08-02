package tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;


public final class HelloTag extends TagSupport {

    private String name = null;

    public int doStartTag() throws JspException {
        try {
            if (name == null) {
                pageContext.getOut().write("Hello, it is some ");
            } else {
                pageContext.getOut().write("Hello, " + name + ",it is some ");
            }
        } catch (IOException ioe) {
            throw new JspTagException(ioe.getMessage());
        }
        return SKIP_BODY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void release() {
        super.release();
        name = null;
    }
}
