package com.csy.live.http.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csy.live.http.bean.po.User;
import com.csy.live.http.dao.iface.UserDao;

@Repository
public class UserDaoImpl {
	@Autowired
	private UserDao userDao;

	public int saveUser(User user) {
		return userDao.saveUser(user);
	}

	public User getByMobile(String mobile) {
		return userDao.getByMobile(mobile);
	}
}
