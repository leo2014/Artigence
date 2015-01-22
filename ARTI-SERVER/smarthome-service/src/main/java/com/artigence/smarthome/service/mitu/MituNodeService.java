package com.artigence.smarthome.service.mitu;

import java.util.List;

import org.apache.commons.codec.DecoderException;

import com.artigence.smarthome.service.mitu.dto.PageSearch;
import com.artigence.smarthome.service.mitu.dto.Report;


public interface MituNodeService {
	
	public String getNodes(String transceiverId,PageSearch search);
	public void saveNodes(String nodeid,String status) throws DecoderException;
	public void saveNodes(List<Report> reports) throws DecoderException;
	public void endReg(List<String> ids);
}
