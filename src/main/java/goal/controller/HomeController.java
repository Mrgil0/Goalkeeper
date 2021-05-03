package goal.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


import goal.service.BoardService;
import goal.service.UserDetailService;
import goal.service.UserService;
import goal.vo.BoardVO;

import goal.vo.ReplyVO;
import goal.vo.UserVO;

@RestController
public class HomeController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private UserDetailService userDetailService;
	@GetMapping("/home")
	public ModelAndView openHome(HttpSession session, BoardVO vo) {//웹에서 쿠키 같은 기록을 담는다
		ModelAndView mv = new ModelAndView("/view/home/logout_home");
		UserVO user = new UserVO();
		if(user.getUserId() == null) {   //로그인 여부 판단
	         mv.addObject("msg", "doLogin");
	      }
		List<BoardVO> boardList = boardService.selectBoardList(vo);
		mv.addObject("List", boardList);
		return mv;
	}
	
	@GetMapping("/login")
	public ModelAndView openLogin() {
		ModelAndView mv = new ModelAndView("/view/home/user_login");
		return mv;
	}
	
	@PostMapping("/login")
	public ModelAndView checkLogin(UserVO vo) {
//		String check = userService.checkLogin(vo);
		UserVO user = new UserVO();
		user = userDetailService.save(vo);
		
		if(user == null) {
			ModelAndView mv = new ModelAndView("/view/home/logout_home");
			mv.addObject("guest", "guest");
			return mv;
			
		} else {
			ModelAndView mv = new ModelAndView("/view/home/login_home");
			mv.addObject("user", vo);
			return mv;
		}
	}
	@GetMapping("/register") 
	public ModelAndView openRegister() {
		ModelAndView mv = new ModelAndView("/view/home/user_register");
		return mv;
	}
	@PostMapping("/register")
	public ModelAndView insertUser(UserVO vo, Model model) {
		String idCheck = userService.checkId(vo.getUserId());
		ModelAndView mv = new ModelAndView();
		if(idCheck == null) {
			userService.insertUser(vo);
			mv.setViewName("/view/home/user_login");
			return mv;
		} else {
			model.addAttribute("msg","중복된 아이디 입니다.");
			mv.setViewName("/view/home/user_register");
			return mv;
			 
		}
	}
	@GetMapping("/denied")
    public String deniedView() {
        return "view/error/denied";
    }
	
	
}
