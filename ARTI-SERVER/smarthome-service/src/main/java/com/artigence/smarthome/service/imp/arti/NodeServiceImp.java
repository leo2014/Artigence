package com.artigence.smarthome.service.imp.arti;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.org.rapid_framework.util.page.PageList;

import com.artigence.smarthome.persist.dao.ArtiDao;
import com.artigence.smarthome.persist.dao.DataTransferRecordDao;
import com.artigence.smarthome.persist.dao.NodeDao;
import com.artigence.smarthome.persist.dao.UserDao;
import com.artigence.smarthome.persist.model.Arti;
import com.artigence.smarthome.persist.model.DataTransferRecord;
import com.artigence.smarthome.persist.model.Node;
import com.artigence.smarthome.persist.model.User;
import com.artigence.smarthome.persist.model.code.DataType;
import com.artigence.smarthome.service.arti.NodeService;
import com.artigence.smarthome.service.arti.dto.NodeVo;
import com.artigence.smarthome.service.core.dto.OrderColumns;
import com.artigence.smarthome.service.core.dto.Search;
import com.artigence.smarthome.service.core.dto.SearchColumns;
import com.artigence.smarthome.service.core.dto.SearchParam;
import com.artigence.smarthome.service.core.dto.SearchResult;
import com.artigence.smarthome.service.exception.BusinessException;
import com.artigence.smarthome.service.util.DtoBeanUtils;
@Service
public class NodeServiceImp implements NodeService{
	@Resource
	private NodeDao nodeDao;
	@Resource
	private ArtiDao artiDao;
	@Resource
	private DataTransferRecordDao dataTransferRecordDao;
	@Resource
	private UserDao userDao;
	@Override
	@Transactional
	public void saveNode(NodeVo nodeVo) {
		Assert.notNull(nodeVo);
		Node node = new Node();
		node.setDelete(false);
		BeanUtils.copyProperties(nodeVo, node);
		if(nodeVo.getArti()==null && nodeVo.getArtiMac()!=null)
			node.setArti(artiDao.getByUniqueField("mac", nodeVo.getArtiMac()));
		if(node.getArti()!=null)
			this.nodeDao.save(node);
		else
			throw new BusinessException("node is must need 'arti' property");
	}

	@Override
	@Transactional
	public void updateNode(NodeVo nodeVo) {
		Assert.notNull(nodeVo);
		Node node =null;
		if(nodeVo.getId()!=null){
			node = this.nodeDao.getById(nodeVo.getId());
		}else if(nodeVo.getSerialNum()!=null){
			node = this.nodeDao.getByUniqueField("serialNum", nodeVo.getSerialNum());
		}
		if(node!=null){
			node.setArtiAuth(nodeVo.isArtiAuth());
			node.setStatus(nodeVo.isStatus());
			node.setDescr(nodeVo.getDescr());
			node.setName(nodeVo.getName());
			this.nodeDao.update(node);
		}
	}
	@Override
	@Transactional
	public void markDelete(Long[] nodeIds){
		for(int i=0; i<nodeIds.length;i++){
			Node node = this.nodeDao.getById(nodeIds[i]);
			node.setDelete(true);
			this.nodeDao.update(node);
			if(i % 20 == 0){
				this.nodeDao.flush();
				this.nodeDao.clear();
			}
		}
	}
	@Override
	@Transactional
	public void deleteNode(NodeVo nodeVO) {
		Assert.notNull(nodeVO);
		this.nodeDao.deleteById(nodeVO.getId());
	}
	
	@Override
	@Transactional
	public void deleteNode(Long id) {
		Assert.notNull(id);
		this.nodeDao.deleteById(id);
	}
	
	@Override
	@Transactional
	public void deleteNode(Long[] nodeIds) {
		this.nodeDao.batchDelete(nodeIds);
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public PageList<NodeVo> getNodes(int page, int pageSize) {
		PageList<Node> list = this.nodeDao.pageQuery(page, pageSize, null, "");
		List<NodeVo> nodes = DtoBeanUtils.copyProperties(list, NodeVo.class, "arti");
		return new PageList<NodeVo>(nodes,page,pageSize,list.getTotalItems());
	}
	@Override
	@Transactional
	public String getDeleteNode(){
		List<Node> deleteNodes = this.nodeDao.findByKey("isDelete", true);
		String deleteNodeSerial = (deleteNodes!=null&&deleteNodes.size()>0)?(String)deleteNodes.get(0).getSerialNum():null;
		
		return deleteNodeSerial;
	}
	@Override
	@Transactional
	public void deleteNode(String nodeSerialNum){
		Node node=this.nodeDao.getByUniqueField("serialNum", nodeSerialNum);
		if(node!=null && node.isDelete())
			this.nodeDao.delete(node);
	}
	@Override
	@Transactional
	public SearchResult<NodeVo> search(SearchParam searchParam,String username) {
		User user = this.userDao.getUserByUsername(username);
		DetachedCriteria dc = DetachedCriteria.forClass(Node.class);
		List<Arti> artis = user.getUserGroup().getArtis();
		dc.add(Restrictions.in("arti", artis));
		dc.add(Restrictions.eq("isDelete", false));
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
			if(feildName.equals("artiMac"))
				feildName = "arti";
			if(feildName.equals("artiAuth"))
				feildName = "isArtiAuth";
			if("asc".equals(oc.getDir()) || "ASC".equals(oc.getDir()))
				orders[i]=Order.asc(feildName);
			else
				orders[i]=Order.desc(feildName);
		}
		PageList<Node> nodes = null;
		if(searchParam.getLength() >= 0)
			nodes = this.nodeDao.pageQuery(searchParam.getStart(), searchParam.getLength(), dc, orders);
		else
			nodes = this.nodeDao.pageQuery(searchParam.getStart(), Integer.MAX_VALUE, dc, orders);
		List<NodeVo> nodeVos = DtoBeanUtils.copyProperties(nodes, NodeVo.class,"arti");
		int i=0;
		for(NodeVo nv:nodeVos){
			nv.setArtiMac(nodes.get(i++).getArti().getMac());
		}
		Long totalCount = this.artiDao.count(Node.class);
		SearchResult<NodeVo> sr = new SearchResult<NodeVo>(totalCount.intValue(),nodes.getTotalItems(),nodeVos);
		return sr;
	}


	private SearchResult<DataTransferRecord> getNodeData(SearchParam searchParam,String username){
		DetachedCriteria dc = DetachedCriteria.forClass(DataTransferRecord.class);
		Restrictions.eq("dataType", DataType.CMD);
		
		return null;
	}
	
	
	@Override
	@Transactional
	public boolean validMac(String mac) {		
		return this.nodeDao.findByKey("mac", mac).size()>0?false:true;		
	}

	@Override
	@Transactional
	public boolean validSerialNum(String serial) {
		return this.nodeDao.findByKey("serialNum", serial).size()>0?false:true;	
	}

	public void setNodeDao(NodeDao nodeDao) {
		this.nodeDao = nodeDao;
	}

	public void setArtiDao(ArtiDao artiDao) {
		this.artiDao = artiDao;
	}


	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setDataTransferRecordDao(DataTransferRecordDao dataTransferRecordDao) {
		this.dataTransferRecordDao = dataTransferRecordDao;
	}


}
