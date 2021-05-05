package goal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import goal.mapper.BoardMapper;
import goal.vo.BoardVO;
import goal.vo.UserVO;

@Service
@Component
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardMapper boardMapper;
	
	@Override
	public void insertBoard(BoardVO vo) {
		boardMapper.insertBoard(vo);
		
	}

	public List<BoardVO> selectBoardList(UserVO uservo) {
	      return boardMapper.selectBoardList(uservo);
	}
	@Override
	public BoardVO recentBoard() {
		return boardMapper.recentBoard();
	}

	@Override
	public List<BoardVO> getBoardList() {
		return boardMapper.getBoardList();
	}

	@Override
	public List<BoardVO> searchBoard(BoardVO vo) {
		return boardMapper.searchBoard(vo);	
	}
	
}