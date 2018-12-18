package com.dreamer.basic.common.generator.dao;

import com.dreamer.basic.common.generator.entity.BizToJoin;
import com.dreamer.basic.common.generator.entity.BizToJoinExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BizToJoinMapper {
    long countByExample(BizToJoinExample example);

    int deleteByExample(BizToJoinExample example);

    int deleteByPrimaryKey(String id);

    int insert(BizToJoin record);

    int insertSelective(BizToJoin record);

    List<BizToJoin> selectByExample(BizToJoinExample example);

    BizToJoin selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") BizToJoin record, @Param("example") BizToJoinExample example);

    int updateByExample(@Param("record") BizToJoin record, @Param("example") BizToJoinExample example);

    int updateByPrimaryKeySelective(BizToJoin record);

    int updateByPrimaryKey(BizToJoin record);
}