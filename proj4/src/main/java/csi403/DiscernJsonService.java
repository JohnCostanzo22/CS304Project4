package csi403;

// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import javax.json.*;

// Extend HttpServlet class
public class DiscernJsonService extends HttpServlet {

  // Standard servlet method 
    public void init() throws ServletException { 
        // Do any required initialization here - likely none
    }
    
    // Standard servlet method - we will handle a POST operation
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException { 
        doService(request, response); 
    }

    // Standard Servlet method
    public void destroy() { 
        // Do any required tear-down here, likely nothing.
    }

    // Standard servlet method - we will not respond to GET
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException { 
        // Set response content type and return an error message
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        // We can always create JSON by hand just concating a string.  
        out.println("{ 'message' : 'Use POST!'}");
    }
    
    // Our main worker method
	/* Takes in Json with a list Points that make a polygon
	* Then it calculates how many integer points are within the polygon
	* And returns, as Json, the number of points within the polygon excluding points on the boundary of the polygon
	*/
    private void doService(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException { 
    
		PrintWriter out = response.getWriter();
		//handle any errors in receiving the Json and processing it
		try {
			//get the input
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			//Use a Stringbuffer to append multiple lines of input
			StringBuffer bufferedJson = new StringBuffer();
			String line = null;
			while((line = br.readLine()) != null) {
				bufferedJson.append(line);
			}
			String jsonStr = bufferedJson.toString();
        
			// Create inList object from the Json input
			InList inList = new JsonClassDiscerner().discern(jsonStr); 
			
			//Get the arrayList of Points from inList
			ArrayList<Point> polygon = inList.getInList();
			
			//need at least 3 points for a polygon
			if(polygon.size() < 3) {
				//if given less than 3 points then return an error
				response.setContentType("application/json");
				//send back the outList of collisions
				out.println("{\"message\": " + "\"A polygon needs at least 3 Points\""  + "}"); 
			} else {
				//Otherwise the points form a polygon
				
				//Calculate object hold calculation methods
				Calculate calculate = new Calculate();
				//get the count of points inside the polygon
				int count = calculate.getCountInsidePoints(polygon);
			
				// Set response content type to be JSON
				response.setContentType("application/json");
				//send back the count
				out.println("{\"count\": " + count + "}"); 
			}
		
		} catch(Exception e) {
			 response.setContentType("application/json");
			//An error occurred (probably bad Json) so send an error message
			out.println("{\"message\":" + "\"Malformed or invalid Json\"" + "}"); 
		}
    }
}

