package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NewServlet
 */
@WebServlet("/NewServlet")
public class NewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NumberConvertor client = new NumberConvertor();

		String value1 = request.getParameter("value1").toString();
		String value2 = request.getParameter("value2").toString();
		String type = request.getParameter("type").toString();
		PrintWriter out = response.getWriter();
		if(value1.equals("") || value2.equals("")){
			out.write("error: Please, provide a value!");  
		}else{
			String result = "";
			
			if(type.equals("wealth")) {
				result = client.wealth(value1, value2);
			}else{
				result = client.pension(value1, value2);
			}
			out.write(result);
			 			
		}
					 
		out.flush();
	    out.close();
	}

}
