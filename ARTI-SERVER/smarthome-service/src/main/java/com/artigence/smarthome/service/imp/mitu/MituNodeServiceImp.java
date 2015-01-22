package com.artigence.smarthome.service.imp.mitu;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.rapid_framework.util.page.PageList;

import com.artigence.smarthome.persist.dao.ArtiDao;
import com.artigence.smarthome.persist.dao.DataTransferRecordDao;
import com.artigence.smarthome.persist.dao.NodeDao;
import com.artigence.smarthome.persist.model.Arti;
import com.artigence.smarthome.persist.model.DataTransferRecord;
import com.artigence.smarthome.persist.model.Node;
import com.artigence.smarthome.persist.model.code.DataTransferDirection;
import com.artigence.smarthome.persist.model.code.DataType;
import com.artigence.smarthome.service.mitu.MituNodeService;
import com.artigence.smarthome.service.mitu.dto.PageSearch;
import com.artigence.smarthome.service.mitu.dto.Report;

@Service
public class MituNodeServiceImp implements MituNodeService {

	@Resource
	private NodeDao nodeDao;
	@Resource
	private DataTransferRecordDao dataTransferRecordDao;
	@Resource
	private ArtiDao artiDao;
	@Override
	@Transactional
	public String getNodes(String transceiverId,PageSearch search) {
		String switchIds = null;
		PageList<Node> nodes = null;
		DetachedCriteria dc = DetachedCriteria.forClass(Node.class);
		dc.add(Restrictions.eq("isArtiAuth", false));
		Arti arti = artiDao.getByUniqueField("mac", transceiverId);
		if(arti == null){
			switchIds = "";
			search.setTotalPage(0);
		}else{
			dc.add(Restrictions.eq("arti", arti));
			nodes = this.nodeDao.pageQuery(
					search.getCurrentPage(), 
					search.getPageSize(), 
					dc);
			
			switchIds = nodes.size()>0?"\""+nodes.get(0).getSerialNum()+"\"":"";
			for(int i=1;i<nodes.size();i++){
				switchIds += (","+"\""+nodes.get(i).getSerialNum()+"\"");
			}
			search.setTotalPage((int)Math.ceil((double)nodes.getTotalItems()/nodes.getPageSize()));
		}
		String result = "{\"currentPage\":"+search.getCurrentPage()
				+",\"rowsPerPage\":"+search.getPageSize()
				+",\"totalPages\":"+search.getTotalPage()
				+",\"switchIds\":["+switchIds
				+"]}";
		return result;
	}
	
	@Transactional
	public void saveNodes(String nodeid,String status) throws DecoderException{
		DataTransferRecord dtr = new DataTransferRecord();
		dtr.setTransferDate(new Date());

		dtr.setDataTransferDirection(DataTransferDirection.ArtiToServer);
		dtr.setDataType(DataType.CMD);
//		Node node=nodeDao.getByUniqueField("serialNum", nodeSerial);
//		if(!node.isStatus()){
//			node.setStatus(true);
//			nodeDao.update(node);
//		}
		dtr.setGateway(artiDao.getById(1l));
		byte[] node = Hex.decodeHex(nodeid.toCharArray());
		byte[] data = ArrayUtils.addAll(new byte[]{0x00,0x00}, node);
		ArrayUtils.addAll(data,new byte[]{0x00,0x00});
		if(status.toUpperCase().equals("ON"))
			dtr.setCmd(ArrayUtils.addAll(data,new byte[]{0x00,0x00}));
		if(status.toUpperCase().equals("OFF"))
			dtr.setCmd(ArrayUtils.addAll(data,new byte[]{0x00,0x01}));
		
		dataTransferRecordDao.save(dtr);

	}

	public void setNodeDao(NodeDao nodeDao) {
		this.nodeDao = nodeDao;
	}

	public void setDataTransferRecordDao(DataTransferRecordDao dataTransferRecordDao) {
		this.dataTransferRecordDao = dataTransferRecordDao;
	}

	public void setArtiDao(ArtiDao artiDao) {
		this.artiDao = artiDao;
	}

	@Override
	@Transactional
	public void saveNodes(List<Report> reports) throws DecoderException {
		
		String status = "";
		String nodeid = "";
		for(Report report:reports){
			nodeid = report.getId();
			status = report.getStatus();
			
			DataTransferRecord dtr = new DataTransferRecord();
			dtr.setTransferDate(new Date());
	
			dtr.setDataTransferDirection(DataTransferDirection.ArtiToServer);
			dtr.setDataType(DataType.CMD);
	//		Node node=nodeDao.getByUniqueField("serialNum", nodeSerial);
	//		if(!node.isStatus()){
	//			node.setStatus(true);
	//			nodeDao.update(node);
	//		}
			dtr.setGateway(artiDao.getById(1l));
			byte[] node = Hex.decodeHex(nodeid.toCharArray());
			byte[] data = ArrayUtils.addAll(new byte[]{0x00,0x00}, node);
			ArrayUtils.addAll(data,new byte[]{0x00,0x00});
			if(status.toUpperCase().equals("ON"))
				dtr.setCmd(ArrayUtils.addAll(data,new byte[]{0x00,0x00}));
			if(status.toUpperCase().equals("OFF"))
				dtr.setCmd(ArrayUtils.addAll(data,new byte[]{0x00,0x01}));
			
			dataTransferRecordDao.save(dtr);
		}
	}

	@Override
	@Transactional
	public void endReg(List<String> ids) {
		for(String id : ids){
			Node node = this.nodeDao.getByUniqueField("serialNum", id);
			node.setArtiAuth(true);
			this.nodeDao.update(node);
		}
	}

	
	
	
}
