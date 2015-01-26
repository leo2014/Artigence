package com.artigence.smarthome.service.imp.arti;

import cn.org.rapid_framework.util.page.PageList;
import com.artigence.smarthome.persist.dao.ArtiDao;
import com.artigence.smarthome.persist.dao.UserDao;
import com.artigence.smarthome.persist.dao.UserGroupDao;
import com.artigence.smarthome.persist.model.Arti;
import com.artigence.smarthome.persist.model.Node;
import com.artigence.smarthome.persist.model.User;
import com.artigence.smarthome.persist.model.UserGroup;
import com.artigence.smarthome.service.arti.ArtiService;
import com.artigence.smarthome.service.arti.dto.ArtiSearchCriteria;
import com.artigence.smarthome.service.arti.dto.ArtiVo;
import com.artigence.smarthome.service.arti.dto.NodeVo;
import com.artigence.smarthome.service.core.dto.*;
import com.artigence.smarthome.service.exception.BusinessException;
import com.artigence.smarthome.service.user.dto.UserVo;
import com.artigence.smarthome.service.util.DtoBeanUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service
public class ArtiServiceImp implements ArtiService {

	@Resource
	private ArtiDao artiDao;
	@Resource
	private UserGroupDao userGroupDao;
	@Resource
	private UserDao userDao;
	
	@Override
	@Transactional
	public void saveArti(ArtiVo artiVo) {
		Assert.notNull(artiVo);
		Arti arti = new Arti();
		BeanUtils.copyProperties(artiVo, arti);
		this.artiDao.save(arti);

	}

	@Override
	@Transactional
	public void updateArti(ArtiVo artiVo) {
		Assert.notNull(artiVo);
		Arti arti =null;
		if(artiVo.getId()!=null){
			arti = this.artiDao.getById(artiVo.getId());
		}else if(artiVo.getMac()!=null){
			List<Arti> artis = this.artiDao.findByKey("mac", artiVo.getMac());
			arti = artis!=null?artis.isEmpty()?null:artis.get(0):null;
		}
		arti.setName(artiVo.getName());
		if(arti!=null)
			this.artiDao.update(arti);
	}

	@Override
	@Transactional
	public void deleteArti(Long artiId) {
		Assert.notNull(artiId);
		this.artiDao.deleteById(artiId);

	}

	@Override
	public PageList<ArtiVo> getArtis(String username, int page, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageList<ArtiVo> getArtis(UserVo user, int page, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public PageList<ArtiVo> getArtis(int page, int pageSize) {
		PageList<Arti> artis = this.artiDao.pageQuery(page, pageSize, null, "");
		List<ArtiVo> artiVos = DtoBeanUtils.copyProperties(artis, ArtiVo.class, "userGroup","nodes");
		return new PageList<ArtiVo>(artiVos,page,pageSize,artis.getTotalItems());
	}

	@Override
	public PageList<ArtiVo> getArtis(ArtiSearchCriteria asc, int page, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void register(ArtiVo artiVo) {
		
		Assert.notNull(artiVo,"artiVo is not null");
		String mac = artiVo.getMac();
		Arti arti = null;
		Assert.notNull(artiVo.getUsername(),"artiVo is not null");
		User user = this.userDao.getUserByUsername(artiVo.getUsername());
		if(mac!=null){
			arti = this.artiDao.getByUniqueField("mac", mac);
			if(arti!=null)
				throw new BusinessException("artiExist","arti is Existed");
			else
				arti = new Arti();
		}
		
		BeanUtils.copyProperties(artiVo, arti);
		if(user!=null){
			artiDao.save(arti);
			List<UserGroup> lists = userGroupDao.findByKey("host", user);
			UserGroup userGroup = user.getUserGroup();
			if(userGroup == null || lists==null?true:lists.isEmpty()){
				userGroup = new UserGroup();					
				userGroup.setName(user.getUsername()+"'s group");
				userGroup.setHost(user);
				user.setUserGroup(userGroup);				
				userGroupDao.save(userGroup);
				userDao.update(user);
			}else{
				userGroup = lists.get(0);
				//userGroupDao.update(userGroup);				
			}
			arti.setUserGroup(userGroup);
			artiDao.update(arti);
		}else{
			throw new BusinessException("userExist","this user is not exist");
		}
		
	}

	
	
	public void setArtiDao(ArtiDao artiDao) {
		this.artiDao = artiDao;
	}

	public void setUserGroupDao(UserGroupDao userGroupDao) {
		this.userGroupDao = userGroupDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	@Transactional
	public Arti login(String mac, String key) {
		List<Arti> artis=this.artiDao.findByKey("mac", mac);
		Arti arti = artis.isEmpty()?null:artis.get(0);
		return arti;
	}

	@Override
	@Transactional
	public void deleteArti(Long[] artiIds) {
		this.artiDao.batchDelete(artiIds);	
	}

	@Override
	@Transactional
	public SearchResult<ArtiVo> search(SearchParam searchParam,String username) {
		User user = this.userDao.getUserByUsername(username);
		DetachedCriteria dc = DetachedCriteria.forClass(Arti.class);
		dc.add(Restrictions.eq("userGroup", user.getUserGroup()));
		Search search = searchParam.getSearch();
		if(search!=null && search.getValue()!=null && !search.getValue().trim().isEmpty()){
			dc.add(Restrictions.or(	
					Restrictions.like("mac", "%"+search.getValue()+"%"),
					Restrictions.like("serialNum", "%"+search.getValue()+"%"),
					Restrictions.like("name", "%"+search.getValue()+"%")				
			));
		}
		List<SearchColumns> scs = searchParam.getColumns();
		for(SearchColumns sc : scs){
			if(sc.isSearchable()){
				Search s = sc.getSearch();
				if(s!=null && s.getValue()!=null && !s.getValue().trim().isEmpty()){
					dc.add(Restrictions.like(sc.getName(), "%"+s.getValue()+"%"));
				}
			}
		}
		List<OrderColumns> ocs = searchParam.getOrder();
		Order[] orders = new Order[ocs.size()];
		for(int i=0; i<ocs.size(); i++){
			OrderColumns oc = ocs.get(i);
			String feildName = scs.get(oc.getColumn()).getName();
			if("asc".equals(oc.getDir()) || "ASC".equals(oc.getDir()))
				orders[i]=Order.asc(feildName);
			else
				orders[i]=Order.desc(feildName);
		}
		PageList<Arti> artis = null;
		if(searchParam.getLength() >= 0)
			artis = this.artiDao.pageQuery(searchParam.getStart(), searchParam.getLength(), dc, orders);
		else
			artis = this.artiDao.pageQuery(searchParam.getStart(), Integer.MAX_VALUE, dc, orders);
		List<ArtiVo> artiVos = DtoBeanUtils.copyProperties(artis, ArtiVo.class,"userGroup","nodes");

		Long totalCount = this.artiDao.count(Arti.class);
		SearchResult<ArtiVo> sr = new SearchResult<ArtiVo>(totalCount.intValue(),artis.getTotalItems(),artiVos);
		return sr;
	}


	
	@Override
	@Transactional
	public boolean validMac(String mac) {		
		return this.artiDao.findByKey("mac", mac).size()>0?false:true;		
	}

	@Override
	@Transactional
	public boolean validSerialNum(String serial) {
		return this.artiDao.findByKey("serialNum", serial).size()>0?false:true;	
	}

	@Override
	@Transactional
	public ArtiVo getArti(Long id) {
		Arti arti=this.artiDao.getById(id);
		ArtiVo artiVo = new ArtiVo();
		if(arti==null)
			return null;
		BeanUtils.copyProperties(arti, artiVo);

		ArrayList<NodeVo> nodeVos = new ArrayList<NodeVo>();
		if(arti.getNodes()!=null&&arti.getNodes().size()>0)
			for(Node node : arti.getNodes()){
				NodeVo nodeVo = new NodeVo();
				BeanUtils.copyProperties(node, nodeVo);
				nodeVos.add(nodeVo);
			}
		artiVo.setNodeVos(nodeVos);
		return artiVo;
	}

}
