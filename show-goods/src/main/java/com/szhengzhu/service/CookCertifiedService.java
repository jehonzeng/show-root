package com.szhengzhu.service;

import com.szhengzhu.bean.goods.CookCertified;
import com.szhengzhu.bean.goods.CookFollow;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.wechat.vo.Cooker;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface CookCertifiedService {

    /**
     * 获取厨师认证分页列表
     *
     * @date 2019年2月27日 下午3:11:46
     * @param cookPage
     * @return
     */
    PageGrid<CookCertified> page(PageParam<CookCertified> cookPage);

    /**
     * 修改厨师认证信息
     *
     * @date 2019年2月27日 下午3:14:43
     * @param cookCertified
     * @return
     */
    CookCertified modify(CookCertified cookCertified);

    /**
     * 手动录入厨师信息
     *
     * @date 2019年5月29日 下午4:14:28
     * @param cookCertified
     * @return
     */
    CookCertified add(CookCertified cookCertified);

    /**
     * 获取厨师销量排名前三
     *
     * @date 2019年6月6日 下午3:10:40
     * @param userId 当前登录用户
     * @return
     */
    List<Cooker> listCookerRank(String userId);

    /**
     * 获取厨师销量排行榜
     *
     * @date 2019年7月17日 下午4:56:01
     * @param cookerPage
     * @return
     */
    PageGrid<Cooker> pageCookerRank(PageParam<String> cookerPage);

    /**
     * 获取厨师详细信息及厨师菜品列表
     *
     * @date 2019年6月28日 下午5:25:55
     * @param cookerId
     * @param userId
     * @return
     */
    Cooker getCookerDetail(String cookerId, String userId);

    /**
     * 用户关注或取关厨师
     *
     * @date 2019年7月18日 上午10:33:43
     * @param cookFollow
     * @return
     */
    void followOr(CookFollow cookFollow);

    /**
     * 获取厨师下拉列表
     *
     * @date 2019年7月29日 上午11:49:15
     * @return
     */
    List<Combobox> listCombobox();

    /**
     * 根据id获取厨师信息
     *
     * @param markId
     * @return
     * @date 2019年9月10日
     */
    CookCertified getCookerById(String markId);
}
