package com.artigence.smarthome.service.arti;

import cn.org.rapid_framework.util.page.PageList;

import com.artigence.smarthome.service.arti.dto.ArtiVo;
import com.artigence.smarthome.service.arti.dto.NodeVo;
import com.artigence.smarthome.service.core.dto.SearchParam;
import com.artigence.smarthome.service.core.dto.SearchResult;

/**
 * @date 2014年4月23日 上午11:30:36
 * @author Leipeng
 *	node manager interface
 */
public interface NodeService {

	/**
	 * save Node
	 * @param nodeVo
	 */
	public void saveNode(NodeVo nodeVo);
	
	/**
	 * update Node
	 * @param nodeVo
	 */
	public void updateNode(NodeVo nodeVo);
	
	/**
	 * delete Node
	 * @param nodeVO
	 */
	public void deleteNode(Long id);
	public void deleteNode(NodeVo nodeVO);
	public void deleteNode(Long[] nodeIds);
	public void deleteNode(String nodeSerialNum);
	/**
	 * get all Nodes
	 * @return
	 */
	public PageList<NodeVo> getNodes(int page, int pageSize);
	
	public String getDeleteNode();
	
	public SearchResult<NodeVo> search(SearchParam searchParam,String username);
	
	public void markDelete(Long[] nodeIds);
	
	public boolean validMac(String mac);
	
	public boolean validSerialNum(String serial);
}
