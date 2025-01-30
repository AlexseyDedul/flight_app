package by.alexdedul.jdbc.utlis;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JspHelper {
    private static final String JSP_FORMAT = "/WEB-INF/jsp/%s.jsp";

    public String getPath(String jsp){
        return String.format(JSP_FORMAT, jsp);
    }
}
