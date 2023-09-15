package com.jsp.boardcontroller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.board.model.BoardRepository;
import com.jsp.board.model.BoardVO;
import com.jsp.board.service.UpdateService;


@WebServlet("*.boards")
public class practiceBoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public practiceBoardController() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		if(request.getMethod().equals("POST")) {
			request.setCharacterEncoding("UTF-8");
		}
		
		String uri = request.getRequestURI();
		uri = uri.substring(request.getContextPath().length()+1, uri.lastIndexOf("."));
		
		System.out.println("정제된 uri : " + uri);
		
		switch(uri) {
		
		case "write" :
			System.out.println("글쓰기 페이지로 이동 요청!");
			response.sendRedirect("/JspBasic/board/board_write.jsp");
			break;
			
		case "regist" :
			System.out.println("글 등록 요청이 들어옴!");
			String writer = request.getParameter("writer");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			BoardVO vo = new BoardVO();
			vo.setWriter(writer);
			vo.setTitle(title);
			vo.setContent(content);
			vo.setRegDate(LocalDateTime.now()); 
			
			BoardRepository.getInstance().regist(vo); // 이해 안감
			
			response.sendRedirect("/JspBasic/list.board");
			
			break;
			
		case "list" :
			System.out.println("글 목록 요청이 들어옴!");
			List<BoardVO> list = BoardRepository.getInstance().getList(); // 이해안감
			
			request.setAttribute("boardList", list);
			
			RequestDispatcher dp
				= request.getRequestDispatcher("board/board_list.jsp");
			dp.forward(request, response);
			
			break;
			
		case "update" :
			System.out.println("글 수정 요청이 들어오!");
			
			
			response.sendRedirect("/JspBasic/content.board");
			
		}
				
	}

}
