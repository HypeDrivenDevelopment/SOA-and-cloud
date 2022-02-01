package myServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.XML;

import com.google.gson.Gson;
import services.DBWS;
import services.DBWSService;

@WebServlet("/SearchPrices")
public class SearchPrices extends HttpServlet{
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchPrices() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String searchString = request.getParameter("searchString").toString();
		System.out.println("searchstring = " + searchString );

		System.out.println("*************");
		
		DBWSService service = new DBWSService();
		DBWS obj = service.getDBWS();
		
		String result_string = obj.searchPrice(searchString);
		System.out.println("resultstring = " + result_string);

		
		if(!result_string.equals("null")){
			
		    
		    PrintWriter out = response.getWriter();
		    out.write(result_string);
		        
		}else{
			
			System.out.println("error");
		    
		    PrintWriter out = response.getWriter();
		    out.write("not found");
			
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub	
		
	}

}