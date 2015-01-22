/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package com.artigence.smarthome.persist.dao;

import java.util.List;

import cn.org.rapid_framework.util.page.PageList;

import com.artigence.smarthome.persist.dao.base.EntityDao;
import com.artigence.smarthome.persist.model.User;

/** 
 * User DAO (Data Access Object) interface. 
 *
 * @author leipeng
 */
public interface UserDao extends EntityDao<User, Long>{

    public User getUser(Long id);

    public User saveUser(User user);

    public void removeUser(Long id);

    public boolean exists(Long id);

    public List<User> getUsers();

    public User getUserByUsername(String username);
    
    public User getUserByEmail(String email);
    
    public PageList<User> getUsers(int pageNumber, int pageSize);

}
