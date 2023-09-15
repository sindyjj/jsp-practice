package com.jsp.boardcontroller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.board.model.BoardRepository;
import com.jsp.board.model.BoardVO;
import com.jsp.board.service.DeleteService;
import com.jsp.board.service.GetListService;
import com.jsp.board.service.IBoardService;
import com.jsp.board.service.ModifyService;
import com.jsp.board.service.RegistService;
import com.jsp.board.service.SearchService;
import com.jsp.board.service.UpdateService;
import com.jsp.board.service.contentService;

@WebServlet("*.board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IBoardService sv;
	private RequestDispatcher dp;


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
			sv = new RegistService();
			sv.execute(request, response);
			BoardVO vo = new BoardVO();
			BoardRepository.getInstance().regist(vo);

			response.sendRedirect("/JspBasic/list.board");

			break;

		case "list" :
			System.out.println("글 목록 요청이 들어옴!");
			List<BoardVO> list = BoardRepository.getInstance().getList();
			sv = new GetListService();
			sv.execute(request, response);

			//DB로부터 전달받은 글 목록을 세션에 넣기는 좀 아깝습니다.
			//세션 -> 데이터를 계속 유지하기 위한 수단. -> 글 목록을 계속 유지해? 왜?
			//글 목록은 한 번 응답하면 더 이상 필요 없다. -> 계속 갱신되는 데이터이기 때문
			//응답이 나가면 자동으로 소멸하는 request 객체를 사용하자.
			

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
			dp =  request.getRequestDispatcher("board/board_list.jsp");
			dp.forward(request, response);

			break;

		case "content" :
			System.out.println("글 상세보기 요청이 들어옴!");
			sv = new contentService();
			sv.execute(request,response);
			
			dp = request.getRequestDispatcher("board/board_content.jsp");
			dp.forward(request, response);
			break;
			
		case "modify" :
			System.out.println("글 수정 페이지로 이동 요청!");
			sv = new ModifyService();
			sv.execute(request, response);
			
			dp = request.getRequestDispatcher("board/board_modify.jsp");
			dp.forward(request,response);
			break;
			
		case "update" :
			System.out.println("글 수정 요청이 들어옴!");
			sv = new UpdateService();
			//새롭게 입력받은 수정값으로 BoardVO 객체를 생성해서 수정을 진행하세요.
			//기존 리스트에 존재하는 객체를 새로운 객체로 교체
			// 수정이 완료되면 수정된 글의 상세보기 페이지로 응답이 나가야합니다.
			sv.execute(request,response);
			
			response.sendRedirect("/JspBasic/content.board?bId=" + request.getParameter("boardNo"));
			break;
			
		case "delete" :
			System.out.println("글 삭제 요청이 들어옴!");
			sv = new DeleteService();
			sv.execute(request, response);
			
			response.sendRedirect("/JspBasic/list.board");
			break;
			
		case "search" :
			System.out.println("글 검색 요청이 들어옴!");
			sv = new SearchService();
			sv.execute(request, response);
			
			dp = request.getRequestDispatcher("board/board_list.jsp");
			dp.forward(request, response);
			break;
		}
	}

}
