package com.szhengzhu.bean.base;

import com.szhengzhu.bean.base.ActionItem;
import com.szhengzhu.core.Contacts;

public class ActionText extends ActionItem {

    private static final long serialVersionUID = -5378053622365953639L;

    public ActionText() {
        super(Contacts.ACTION_TEXT_TYPE);
    }

    public Integer getServerType() {
        return Contacts.ACTION_TEXT_TYPE;
    }

    public void setServerType(Integer server_type) {
        super.setServerType(Contacts.ACTION_TEXT_TYPE);
    }
}
