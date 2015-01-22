package com.artigence.smarthome.persist.dao;

import java.util.List;

import com.artigence.smarthome.persist.dao.base.EntityDao;
import com.artigence.smarthome.persist.model.Arti;



public interface ArtiDao extends EntityDao<Arti, Long>{
    public Arti getGateway(Long id);

    public Arti saveGateway(Arti gateway);

    public void removeGateway(Long id);

    public boolean exists(Long id);

    public List<Arti> getGateway();

    public Arti getGatewayByUser(Long userID) ;
}
