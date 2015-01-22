package com.artigence.smarthome.service.arti;

import java.util.List;

import org.apache.commons.codec.DecoderException;

import com.artigence.smarthome.persist.model.code.DataTransferDirection;
import com.artigence.smarthome.persist.model.code.DataType;
import com.artigence.smarthome.service.arti.dto.DataTransferRecordVo;
import com.artigence.smarthome.service.arti.vo.RecordSis;
import com.artigence.smarthome.service.core.dto.SearchParam;
import com.artigence.smarthome.service.core.dto.SearchResult;

public interface DataTransferRecordService {
	public void dataRecord(String clientType,Long id,
			DataTransferDirection dtd,DataType dt,byte[] data);
	
	public List<RecordSis> tongji(String username)throws DecoderException;
	
	public SearchResult<DataTransferRecordVo> search(SearchParam searchParam,String username) throws DecoderException;
	
	public SearchResult<DataTransferRecordVo> filter(SearchParam searchParam,String username) throws DecoderException;
}
