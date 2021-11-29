package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.ReceiveRecord;
import com.szhengzhu.bean.ordering.vo.UserTicketVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author makejava
 * @since 2020-12-10 14:07:32
 */
public interface ReceiveRecordMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param markId 主键
     * @return 实例对象
     */
    ReceiveRecord queryById(@Param("markId") String markId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param receiveRecord 实例对象
     * @return 对象列表
     */
    List<ReceiveRecord> queryAll(ReceiveRecord receiveRecord);

    /**
     * 新增数据
     *
     * @param receiveRecord 实例对象
     * @return 影响行数
     */
    int insert(ReceiveRecord receiveRecord);

    /**
     * 修改数据
     *
     * @param receiveRecord 实例对象
     * @return 影响行数
     */
    int update(ReceiveRecord receiveRecord);

    /**
     * 通过主键删除数据
     *
     * @param markId 主键
     * @return 影响行数
     */
    int deleteById(@Param("markId") String markId);

    /**
     * 查询用户券
     *
     * @param userId     用户id
     * @param templateId 券模板id
     * @param time       时间
     * @return 实例对象
     */
    List<UserTicketVo> userTicket(@Param("userId") String userId, @Param("templateId") String templateId,
                                  @Param("time") Date time);
}
