package sec.secwatchdog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import sec.secwatchdog.model.Feedback;

@Mapper
public interface FeedbackDao {

    @Select("select firstmedtime,medtotal,exhibitcycle from feedback where neckletid=#{neckletid}")
    public Feedback getFeedback(String neckletid);
}
