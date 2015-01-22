package com.artigence.smarthome.persist.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;
import cn.org.rapid_framework.util.page.PageList;

import com.artigence.smarthome.persist.dao.UserDao;
import com.artigence.smarthome.persist.dao.base.hibernate.EntityDaoSupport;
import com.artigence.smarthome.persist.model.User;

/** 
 * This class is the implementation of UserDAO using Spring's HibernateTemplate.
 *
 * @author leipeng
 */
@Repository
public class UserDaoHibernate extends EntityDaoSupport<User, Long> implements UserDao {

    public User getUser(Long id) {
        return (User) getHibernateTemplate().get(User.class, id);
    }

    public User saveUser(User user) {
        getHibernateTemplate().saveOrUpdate(user);
        getHibernateTemplate().flush();
        return user;
    }

    public void removeUser(Long id) {
        getHibernateTemplate().delete(getUser(id));
    }

    public boolean exists(Long id) {
        User user = (User) getHibernateTemplate().get(User.class, id);
        return user != null;
    }

    @SuppressWarnings("unchecked")
    public List<User> getUsers() {
        return (List<User>)getHibernateTemplate().find(
                "from User u order by u.createdDate desc");
    }

    public User getUserByUsername(String username) {
        List users = getHibernateTemplate().find("from User where username=?",
                username);
        User user = null;
        if (users != null && !users.isEmpty())
        	user = (User)users.get(0);
        return user;
    }

	@Override
	public User getUserByEmail(String email) {
		List users = getHibernateTemplate().find("from User where email=?",
                email);
        User user = null;
        if (users != null && !users.isEmpty())
        	user = (User)users.get(0);
        return user;
	}

	@Override
	public PageList<User> getUsers(int pageNumber, int pageSize) {
		List<User> users = this.findAll();
		if(users==null)
			return new PageList<User>(0,0,0);
		int total = users.size();
		pageNumber = pageNumber<=0?1:pageNumber;
		
		if(pageNumber*pageSize > total){
			pageNumber = total>0?total/pageSize+1:1;
			users = users.subList((pageNumber-1)*pageSize, total>0?total:0);
		}else{
			users = users.subList((pageNumber-1)*pageSize, pageSize>0?pageNumber*pageSize:0);
		}
		
		return new PageList<User>(users,pageNumber,pageSize,total);
	}

    //    @SuppressWarnings("unchecked")
    //    public User findUserByUsername(String username) {
    //        List users = getHibernateTemplate().find("from User where username=?",
    //                username);
    //        return (users == null || users.isEmpty()) ? null : (User) users.get(0);
    //    }

}
