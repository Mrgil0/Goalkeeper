package goal.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import goal.common.CommonDownload;
import goal.service.CommonService;
import goal.service.UserFileService;
import goal.service.UserService;
import goal.util.MediaUtils;
import goal.vo.UserFileVO;
import goal.vo.UserVO;
import lombok.extern.log4j.Log4j;

@Controller
public class DropBoxController {
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserFileService userFileService;
	
	private CommonDownload commonDownload = new CommonDownload();
	
	MediaUtils mediaUtils = new MediaUtils();
    InputStream in = null;
    ResponseEntity<byte[]> entity = null;
	
	@GetMapping("/profileInfo")
	public ModelAndView openProfileInfo(HttpServletRequest request, UserVO vo) {
		vo = getLoginUser(request);
		UserFileVO file = new UserFileVO();
		file.setUno(vo.getUno());
		
		ModelAndView mv = new ModelAndView("view/ProfileDropBox/hub-profile-info");
		mv = commonService.checkLoginUser(request, mv);
		mv.addObject("uno", file.getUno());
		return mv;
	}
	
	@PostMapping("/modifyMyInfo")
	public String modifyMyInfo(UserVO vo) {
		userService.modify(vo);
		return "redirect:/profileInfo";
	}
	
	@GetMapping("/profileModify")
	public ModelAndView openProfileModify(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("view/ProfileDropBox/hub-account-password");
		mv = commonService.checkLoginUser(request, mv);
		return mv;
	}
	
	@GetMapping("/notice")
	public ModelAndView openNotice(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("view/ProfileDropBox/hub-profile-notifications");
		mv = commonService.checkLoginUser(request, mv);
		return mv;
	}
	
	@GetMapping("/massage")
	public ModelAndView openMassage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("view/ProfileDropBox/hub-profile-messages");
		mv = commonService.checkLoginUser(request, mv);
		return mv;
	}
	
	@GetMapping("/generalSetting")
	public ModelAndView openGeneralSetting(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("view/ProfileDropBox/hub-account-settings");
		mv = commonService.checkLoginUser(request, mv);
		return mv;
	}

	@PostMapping("/profileInfo/profile")
	public String insertProfile(UserVO user, HttpServletRequest request, @RequestPart("files") MultipartFile files)
			throws Exception {
		UserFileVO vo = new UserFileVO();
		user = getLoginUser(request);
		vo.setUno(user.getUno());
		int check = userFileService.checkProfile(vo.getUno());
		
		String fileUrl = "C:/profile";	
    	String fileName = files.getOriginalFilename(); 
        String uuid = RandomStringUtils.randomAlphanumeric(32)+"."+"jpg";
        String filePath = fileUrl + "/" + uuid + "/" + fileName;
        File dest = new File(filePath);
        files.transferTo(dest);

        vo.setUserFileId(uuid);
        vo.setUserFileName(fileName);
        vo.setUserFilePath(filePath);
        
    	if (dest.exists() == false) {
    		dest.mkdirs();
		}
		if(check != 0) {
			userFileService.removeUserFile(vo.getUno());
			userFileService.insertUserFile(vo);
		} else {
	        userFileService.insertUserFile(vo);
		}
		
        return "redirect:/profileInfo";
	}
	
	@RequestMapping(value="/profile/{uno}", method=RequestMethod.GET)
	public ResponseEntity<byte[]> displayImage(@PathVariable int uno) throws IOException{
	    UserFileVO groupFile = userFileService.selectFile(uno);
	    entity = commonDownload.getImageEntity(entity, mediaUtils, in, groupFile.getUserFileName(), groupFile.getUserFileId(), groupFile.getUserFilePath());
	    return entity;
	}
	
	public UserVO getLoginUser(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
	    UserVO user = (UserVO) session.getAttribute("user");
	    return user;
	}
	
}
