package com.csy.live.http.dao.iface;

import org.springframework.stereotype.Repository;

import com.csy.live.http.bean.po.User;

/**
 * 为规范化dao层，禁止在service层直接调用该层。应该调用daoImpl
 */
@Repository
public interface UserDao {

	int saveUser(User user);

	User getByMobile(String mobile);

}
