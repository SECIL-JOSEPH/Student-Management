package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import utility.Validator;

/**
 * Servlet Filter implementation class NameFilter
 */
@WebFilter("/NameFilter")
public class NameFilter implements Filter {

    /**
     * Default constructor. 
     */
    public NameFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req =(HttpServletRequest) request;
		String studentname = (String) request.getParameter("studentname");
		int subject1 = Integer.parseInt(request.getParameter("subject1"));
		int subject2 = Integer.parseInt(request.getParameter("subject2"));
		int subject3 = Integer.parseInt(request.getParameter("subject3"));
		int subject4 = Integer.parseInt(request.getParameter("subject4"));
		int subject5 = Integer.parseInt(request.getParameter("subject5"));
		if(!Validator.isName(studentname))
		{			
			String message = studentname +" is Invalid Name";
			String url = "/Servlet/adminView.jsp";
			request.setAttribute("message", message);
			request.setAttribute("url", url);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}	
		else if (!Validator.isMark(subject1) || !Validator.isMark(subject2)
				|| !Validator.isMark(subject3) || !Validator.isMark(subject4) || !Validator.isMark(subject5))
		{
			String message = "Invalid Student Mark";
			String url = "/Servlet/adminView.jsp";
			request.setAttribute("message", message);
			request.setAttribute("url", url);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
		else
		{
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
