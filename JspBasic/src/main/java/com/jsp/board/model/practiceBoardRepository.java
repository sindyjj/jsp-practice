package com.jsp.board.model;

import java.util.ArrayList;
import java.util.List;

public class practiceBoardRepository {

	private List<BoardVO> boardList = new ArrayList<>();
	
	public practiceBoardRepository() {}
	
	private static practiceBoardRepository repository = new practiceBoardRepository();
	
	public static practiceBoardRepository getInstance() {
		return repository;
	}
	
	public void regist(BoardVO vo) {
		boardList.add(vo);
	}
	
	public List<BoardVO> getList(){
		return boardList;
	}
}
