package com.jsp.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/session/Login")
public class sessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public sessionServlet() {
        super();
 
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // 문자열 인코딩
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		if(id.equals("sindyjj") && pw.equals("jj1023jj1023")) {
			
			HttpSession session =  request.getSession();
			session.setAttribute("user_id", id);
			
			response.sendRedirect("/JspBasic/session/session_welcome.jsp");
			
		}else {
			response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            
            PrintWriter w = response.getWriter();
            
            String htmlCode = "";
            htmlCode += "<script>\r\n"
                    + "        alert('로그인에 실패했습니다.');\r\n"
                    + "        history.back();\r\n"
                    + "    </script>\r\n"
                    + "";
            
            w.write(htmlCode);
            w.flush();
            w.close();
		}
		
	}

}
