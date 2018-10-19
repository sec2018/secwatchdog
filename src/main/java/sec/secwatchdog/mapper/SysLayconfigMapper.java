package sec.secwatchdog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import sec.secwatchdog.model.SysLayconfig;
import sec.secwatchdog.service.impl.SysLayconfigExample;

@Mapper
public interface SysLayconfigMapper {
    int countByExample(SysLayconfigExample example);

    int deleteByExample(SysLayconfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysLayconfig record);

    int insertSelective(SysLayconfig record);

    List<SysLayconfig> selectByExample(SysLayconfigExample example);

    SysLayconfig selectByPrimaryKey(Integer id);
    
    SysLayconfig selectLayConfigByMid(String mid);

    int updateByExampleSelective(@Param("record") SysLayconfig record, @Param("example") SysLayconfigExample example);

    int updateByExample(@Param("record") SysLayconfig record, @Param("example") SysLayconfigExample example);

    int updateByPrimaryKeySelective(SysLayconfig record);

    int updateByPrimaryKey(SysLayconfig record);
}