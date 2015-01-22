package com.artigence.smarthome.service.imp.arti;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.rapid_framework.util.page.PageList;

import com.artigence.smarthome.persist.dao.ArtiDao;
import com.artigence.smarthome.persist.dao.DataTransferRecordDao;
import com.artigence.smarthome.persist.dao.NodeDao;
import com.artigence.smarthome.persist.dao.UserDao;
import com.artigence.smarthome.persist.model.DataTransferRecord;
import com.artigence.smarthome.persist.model.Node;
import com.artigence.smarthome.persist.model.User;
import com.artigence.smarthome.persist.model.code.DataTransferDirection;
import com.artigence.smarthome.persist.model.code.DataType;
import com.artigence.smarthome.service.arti.DataTransferRecordService;
import com.artigence.smarthome.service.arti.dto.DataTransferRecordVo;
import com.artigence.smarthome.service.arti.vo.RecordSis;
import com.artigence.smarthome.service.core.dto.OrderColumns;
import com.artigence.smarthome.service.core.dto.Search;
import com.artigence.smarthome.service.core.dto.SearchColumns;
import com.artigence.smarthome.service.core.dto.SearchParam;
import com.artigence.smarthome.service.core.dto.SearchResult;
import com.artigence.smarthome.service.util.DtoBeanUtils;
@Service
public class DataTransferRecordServiceImp implements DataTransferRecordService {

	@Resource
	private UserDao userDao;
	@Resource
	private ArtiDao artiDao;
	@Resource
	private DataTransferRecordDao dataTransferRecordDao;
	@Resource
	private NodeDao nodeDao;
	
	@Override
	@Transactional
	public void dataRecord(String clientType, Long id,
			DataTransferDirection dtd, DataType dt,byte[] data) {
		DataTransferRecord dtr = new DataTransferRecord();
		dtr.setTransferDate(new Date());
		String nodeSerial = Hex.encodeHexString(ArrayUtils.subarray(data, 2, 4));
		dtr.setCmd(data);
		
		if (clientType.equals("ARTI")) {
			dtr.setGateway(artiDao.getById(id));
		} else if (clientType.equals("USER")) {
			dtr.setUser(userDao.getById(id));
		}
		dtr.setDataTransferDirection(dtd);
		dtr.setDataType(dt);
		Node node=nodeDao.getByUniqueField("serialNum", nodeSerial);
		if(!node.isStatus()){
			node.setStatus(true);
			nodeDao.update(node);
		}
		dataTransferRecordDao.save(dtr);

	}
	
	@Override
	@Transactional
	public List<RecordSis> tongji(String username) throws DecoderException {
		User user = this.userDao.getUserByUsername(username);
		PageList<DataTransferRecord> dataTransferRecords = null;
		List<DataTransferRecordVo> dataTransferRecordVos = null;
		List<RecordSis> rss = new ArrayList<RecordSis>();
		for(Node n : this.nodeDao.findAll()){
			DetachedCriteria dc = DetachedCriteria.forClass(DataTransferRecord.class);
			System.out.println(Hex.encodeHexString(Hex.decodeHex(n.getSerialNum().toCharArray())));
			dc.add(Restrictions.or(
					Restrictions.eq("cmd",  ArrayUtils.addAll(ArrayUtils.addAll(new byte[]{0x00,0x00}, Hex.decodeHex(n.getSerialNum().toCharArray())),new byte[]{0x00,0x00})),
					Restrictions.eq("cmd",  ArrayUtils.addAll(ArrayUtils.addAll(new byte[]{0x00,0x00}, Hex.decodeHex(n.getSerialNum().toCharArray())),new byte[]{0x00,0x01})),
					Restrictions.eq("cmd",  ArrayUtils.addAll(ArrayUtils.addAll(new byte[]{0x00,0x00}, Hex.decodeHex(n.getSerialNum().toCharArray())),new byte[]{0x00,(byte)0xff}))
			));
			dataTransferRecords = this.dataTransferRecordDao.pageQuery(1, Integer.MAX_VALUE, dc);
			dataTransferRecordVos = DtoBeanUtils.copyProperties(dataTransferRecords, DataTransferRecordVo.class,"user","gateway");
			
			if(dataTransferRecordVos.size()<=0)
				return rss;
			DataTransferRecordVo drv1 = dataTransferRecordVos.get(0); 
			RecordSis rs = new RecordSis();rs.setNodeid(n.getSerialNum());
			
			//DataTransferRecordVo drv2 = null;
			for(DataTransferRecordVo drv2 : dataTransferRecordVos){
				rs.setTotal((rs.getTotal()+1));
				if(drv1.getId() == drv2.getId()){
					continue;
				}
				if(compareDate(drv1,drv2)==0)
					rs.setErrorCount((rs.getErrorCount()+1));
				else if(compareDate(drv1,drv2)<0)
					rs.setDuibao((rs.getDuibao()+1));
				drv1 = drv2;
				
			}
			rs.setError((float)rs.getErrorCount()/(float)dataTransferRecordVos.size());
			rs.setDiubaob((float)rs.getDuibao()/(float)dataTransferRecordVos.size());
			rss.add(rs);
		}
		return rss;
	}
	
	@Override
	@Transactional
	public SearchResult<DataTransferRecordVo> search(SearchParam searchParam,String username) throws DecoderException {
		User user = this.userDao.getUserByUsername(username);
		
		DetachedCriteria dc = DetachedCriteria.forClass(DataTransferRecord.class);
		dc.add(Restrictions.in("gateway", user.getUserGroup().getArtis()));
		dc.add(Restrictions.eq("dataType", DataType.CMD));
		Search search = searchParam.getSearch();
		if(search!=null && search.getValue()!=null && !search.getValue().trim().isEmpty()){
			if(search.getValue().toCharArray().length%2 == 0)
				dc.add(Restrictions.or(
						Restrictions.eq("cmd",  ArrayUtils.addAll(ArrayUtils.addAll(new byte[]{0x00,0x00}, Hex.decodeHex(search.getValue().toCharArray())),new byte[]{0x00,0x00})),
						Restrictions.eq("cmd",  ArrayUtils.addAll(ArrayUtils.addAll(new byte[]{0x00,0x00}, Hex.decodeHex(search.getValue().toCharArray())),new byte[]{0x00,0x01})),
						Restrictions.eq("cmd",  ArrayUtils.addAll(ArrayUtils.addAll(new byte[]{0x00,0x00}, Hex.decodeHex(search.getValue().toCharArray())),new byte[]{0x00,(byte)0xff}))
				));
			else
				dc.add(Restrictions.or(	
						Restrictions.eq("cmd",  new byte[]{0x11,0x11,0x00,0x00,0x00,0x00})
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
		Order[] orders = null;
		if(ocs.size()>0)orders = new Order[ocs.size()];
		else{
			orders = new Order[0];
			orders[0] = Order.asc("transferDate");
		}
		for(int i=0; i<ocs.size(); i++){
			OrderColumns oc = ocs.get(i);
			String feildName = scs.get(oc.getColumn()).getName();
			if(feildName.equals("transferDateString"))
				feildName = "transferDate";
			if("asc".equals(oc.getDir()) || "ASC".equals(oc.getDir()))
				orders[i]=Order.asc(feildName);
			else
				orders[i]=Order.desc(feildName);
		}
		PageList<DataTransferRecord> dataTransferRecords = null;
		if(searchParam.getLength() >= 0)
			dataTransferRecords = this.dataTransferRecordDao.pageQuery(searchParam.getStart(), searchParam.getLength(), dc, orders);
		else
			dataTransferRecords = this.dataTransferRecordDao.pageQuery(searchParam.getStart(), Integer.MAX_VALUE, dc, orders);
		List<DataTransferRecordVo> dataTransferRecordVos = DtoBeanUtils.copyProperties(dataTransferRecords, DataTransferRecordVo.class,"user","gateway");
		int i = 0;
		for(DataTransferRecordVo drv:dataTransferRecordVos){
			drv.setArtiMac(dataTransferRecords.get(i).getGateway().getMac());
		}
		Long totalCount = this.dataTransferRecordDao.count(DataTransferRecord.class);
		SearchResult<DataTransferRecordVo> sr = new SearchResult<DataTransferRecordVo>(totalCount.intValue(),dataTransferRecords.getTotalItems(),dataTransferRecordVos);

		return sr;
	}

	@Override
	@Transactional
	public SearchResult<DataTransferRecordVo> filter(SearchParam searchParam,String username) throws DecoderException {
		SearchResult<DataTransferRecordVo> sr = search(searchParam,username);
		if(sr.getRecordsFiltered()<=0 || searchParam.getSearch().getValue()==null ||searchParam.getSearch().getValue().trim().isEmpty())
			return sr;
		DataTransferRecordVo drv1 = sr.getData().get(0); 
		List<DataTransferRecordVo> dataTransferRecordVos = new ArrayList<DataTransferRecordVo>();
		//DataTransferRecordVo drv2 = null;
		for(DataTransferRecordVo drv2 : sr.getData()){
			if(drv1.getId() == drv2.getId()){
				dataTransferRecordVos.add(drv2);
				continue;
			}
			if(compareDate(drv1,drv2)>0 || compareDate(drv1,drv2)<0)
				dataTransferRecordVos.add(drv2);
			drv1 = drv2;
		}
		SearchResult<DataTransferRecordVo> srf = new SearchResult<DataTransferRecordVo>(sr.getRecordsTotal(),sr.getRecordsFiltered(),dataTransferRecordVos);
		return srf;
	}
	
	private int compareDate(DataTransferRecordVo drv1,DataTransferRecordVo drv2){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(drv1.getTransferDate());
		long d1 = calendar.getTimeInMillis();
		calendar.setTime(drv2.getTransferDate());
		long d2 = calendar.getTimeInMillis();
		if(drv1.getStatus() == drv2.getStatus()){			
			if((d2-d1)<=20000)
				return 0;
			else
				return -1;
		}
			
		return 1;
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setArtiDao(ArtiDao artiDao) {
		this.artiDao = artiDao;
	}

	public void setDataTransferRecordDao(
			DataTransferRecordDao dataTransferRecordDao) {
		this.dataTransferRecordDao = dataTransferRecordDao;
	}

	public void setNodeDao(NodeDao nodeDao) {
		this.nodeDao = nodeDao;
	}





}
