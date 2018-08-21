package sec.secwatchdog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import sec.secwatchdog.model.Managers;

@Mapper
public interface UserDao {
    @Select("select * from managers where username = #{name}")
    Managers findUserByName(String name);

/*    @Select("select password from managers where managerid = #{uid}")
    String getUserPassword(Integer uid);

    @Select("select * from role where rid = (select rid from user_role where uid = #{uid})")
    Role findLoginUserRole(Integer uid);*/
}
