package com.artigence.smarthome.service.arti;

import cn.org.rapid_framework.util.page.PageList;
import com.artigence.smarthome.persist.model.Arti;
import com.artigence.smarthome.service.arti.dto.ArtiSearchCriteria;
import com.artigence.smarthome.service.arti.dto.ArtiVo;
import com.artigence.smarthome.service.core.dto.SearchParam;
import com.artigence.smarthome.service.core.dto.SearchResult;
import com.artigence.smarthome.service.user.dto.UserVo;

/**
 * @date 2014年4月22日 下午6:03:21
 * @author Leipeng
 *	arti manager service interface
 */
public interface ArtiService {
	/**
	 * save arti
	 * @param artiVo
	 */
	public void saveArti(ArtiVo artiVo);
	public ArtiVo getArti(Long id);
	/**
	 * update arti
	 * @param artiVo
	 */
	public void updateArti(ArtiVo artiVo);
	
	/**
	 * delete arti
	 * @param artiId
	 */
	public void deleteArti(Long artiId);
	public void deleteArti(Long[] artiIds);
	
	/**
	 * find artis with username
	 * @param username
	 * @return
	 */
	public PageList<ArtiVo> getArtis(String username, int page, int pageSize);
	
	/**
	 * find artis with User
	 * @param user
	 * @return
	 */
	public PageList<ArtiVo> getArtis(UserVo user, int page, int pageSize);
	
	/**
	 * find all Artis 
	 * @return
	 */
	public PageList<ArtiVo> getArtis(int page, int pageSize);
	
	/**
	 * find Artis with ArtiSearchCriteria
	 * @param asc
	 * @return
	 */
	public PageList<ArtiVo> getArtis(ArtiSearchCriteria asc, int page, int pageSize);
	
	/**
	 * arti gateway register
	 * @param artiVo
	 */
	public void register(ArtiVo artiVo);
	
	/**
	 * arti gateway login Net system
	 * @param mac,key
	 */
	public Arti login(String mac, String key);
	
	public SearchResult<ArtiVo> search(SearchParam searchParam,String username);
	
	
	public boolean validMac(String mac);
	
	public boolean validSerialNum(String serial);
}
