package com.artigence.smarthome.persist.dao;

import java.util.List;

import com.artigence.smarthome.persist.dao.base.EntityDao;
import com.artigence.smarthome.persist.model.DataTransferRecord;



public interface DataTransferRecordDao extends EntityDao<DataTransferRecord, Long>{
    public DataTransferRecord getCommand(Long id);

    public DataTransferRecord saveCommand(DataTransferRecord command);

    public void removeCommand(Long id);

    public boolean exists(Long id);

    public List<DataTransferRecord> getCommand();

}
