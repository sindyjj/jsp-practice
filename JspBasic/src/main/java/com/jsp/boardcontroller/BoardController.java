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

@WebServlet("*.board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public BoardController() {
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
			vo.setRegDate(LocalDateTime.now()); // 나중에 DB쓰면 이런거 안해여.
			
			BoardRepository.getInstance().regist(vo);
			
			response.sendRedirect("/JspBasic/list.board");
			
			break;
			
		case "list" :
			System.out.println("글 목록 요청이 들어옴!");
			List<BoardVO> list = BoardRepository.getInstance().getList();
			
			//DB로부터 전달받은 글 목록을 세션에 넣기는 좀 아깝습니다.
			//세션 -> 데이터를 계속 유지하기 위한 수단. -> 글 목록을 계속 유지해? 왜?
			//글 목록은 한 번 응답하면 더 이상 필요 없다. -> 계속 갱신되는 데이터이기 때문
			//응답이 나가면 자동으로 소멸하는 request 객체를 사용하자.
			request.setAttribute("boardList", list);
			
			/*
			  밑에서 sendRedirect 를 하면 안되는 이유.
			  request 객체에 list를 담아서 전달하려 하는데, sendRedirect를 사용하면
			  응답이 바로 나가면서 기존의 request 객체가 소멸해 버립니다.
			  여기서는 forward방식을 사용해서 request를 원하는 jsp 파일로 전달해서 
			  그쪽에서 응답이 나갈 수 있도록 처리해야 합니다.
			 */
//			response.sendRedirect("board/board_list.jsp");
			
			//request 객체를 다음 화면까지 운반하기 위한 forward 기능을 제공하는 객체
			//-> RequestDispatcher
			RequestDispatcher dp
				=  request.getRequestDispatcher("board/board_list.jsp");
			dp.forward(request, response);
			
			break;
		
		}
	}

}
