package goal.vo;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Alias("boardVO")
public class BoardVO {
   private int bno;
   private int uno;
   private String userid;
   private String bo_content;
   private String bo_title;
   private String bo_cate;
   private String bo_group;
   //private String replyList;
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   private Date bo_date;  
}