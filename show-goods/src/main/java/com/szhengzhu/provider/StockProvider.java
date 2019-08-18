package com.szhengzhu.provider;

import java.util.Map;

public class StockProvider {

    public String selectInfos(Map<String, String> map) {
        String markId = map.get("markId");
        StringBuilder sql = new StringBuilder();
        sql.append(
                "SELECT markId,depotName,attrList,serverStatus,basePrice,salePrice,totalStock,currentStock,");
        sql.append(
                "goodsName,goodsStyle,supplyType,goodsStatus,singleBasePrice,singleSalePrice,unit,");
        sql.append(
                "upperTime,downTime,preUpperTime,preDownTime,createTime,modifyTime,modifierName,creatorName,");
        sql.append(
                "cookerName,typeName,categoryName,brandName,province,city,area FROM v_goods_base ");
        sql.append("WHERE markId = '" + markId + "' ");
        return sql.toString();
    }

}
