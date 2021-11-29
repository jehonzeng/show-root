package com.szhengzhu.service;

import com.szhengzhu.bean.member.*;
import com.szhengzhu.bean.member.vo.DishesImageVo;
import com.szhengzhu.bean.member.vo.DishesStageVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

/**
 * @author Administrator
 */
public interface DishesService {
    /**
     * 查询菜品信息
     *
     * @param param
     * @return 对象列表
     */
    PageGrid<DishesInfo> queryDishes(PageParam<DishesInfo> param);

    /**
     * 新增菜品信息
     *
     * @param dishesInfo 实例对象
     */
    void addDishes(DishesInfo dishesInfo);

    /**
     * 修改菜品信息
     *
     * @param dishesInfo 实例对象
     */
    void modifyDishes(DishesInfo dishesInfo);

    /**
     * 查询菜品阶段信息
     *
     * @param stage 实例对象
     * @return 对象列表
     */
    List<DishesStage> queryStage(DishesStageVo stage);

    /**
     * 新增菜品阶段信息
     *
     * @param stage 实例对象
     */
    void addStage(DishesStageVo stage);

    /**
     * 修改菜品阶段信息
     *
     * @param stage 实例对象
     */
    void modifyStage(DishesStageVo stage);

    /**
     * 查询菜品图片信息
     *
     * @param image 实例对象
     * @return 对象列表
     */
    List<DishesImage> queryImage(DishesImageVo image);

    /**
     * 新增菜品图片信息
     *
     * @param image 实例对象
     */
    void addImage(DishesImageVo image);

    /**
     * 修改菜品图片信息
     *
     * @param image
     */
    void modifyImage(DishesImageVo image);
}
