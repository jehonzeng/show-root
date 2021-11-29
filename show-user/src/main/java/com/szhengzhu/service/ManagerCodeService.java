package com.szhengzhu.service;

import com.szhengzhu.bean.user.ManagerCode;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

public interface ManagerCodeService {

	/**
	 * 获取内部人员口令列表
	 *
	 * @param pageParam
	 * @return
	 */
	PageGrid<ManagerCode> page(PageParam<ManagerCode> pageParam);

	/**
	 * 添加内部人员口令记录
	 *
	 * @return
	 */
	void reflush();

	/**
	 * 更新内部人员口令信息
	 *
	 * @param code
	 * @return
	 */
	void modify(ManagerCode code);

	/**
	 * 根据口令获取记录
	 *
	 * @param code
	 * @return
	 */
	ManagerCode getInfoByCode(String code);
}
