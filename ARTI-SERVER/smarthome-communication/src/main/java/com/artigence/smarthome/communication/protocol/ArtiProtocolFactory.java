package com.artigence.smarthome.communication.protocol;

import com.artigence.smarthome.communication.session.CID;
import com.artigence.smarthome.persist.model.code.DataType;

/**
 * Created by 鹏 on 2015/1/27.
 */
public class ArtiProtocolFactory {

    public static ArtiProtocol artiProtocolInstance(DataType dataType, byte[] data, CID client){

        ArtiProtocol artiProtocol = new ArtiProtocol();
        artiProtocol.setDataType(dataType);
        artiProtocol.setData(data);
        artiProtocol.setLength(data.length);

        switch (dataType){
            case AUTH_REPLY:
            case HEARTBEAT:
            case PLAIN_REPLY:
                artiProtocol = getResult(artiProtocol, client);
                break;
            default:
                break;
        }

        return artiProtocol;
    }


    private static ArtiProtocol getResult(ArtiProtocol artiProtocol, CID client){
        if(client == null){
            artiProtocol.setDestinationType(DestinationType.ALL);
            artiProtocol.setDestination(0l);
        }else {
            switch (client.getClientType()) {
                case ARTI:
                    artiProtocol.setDestinationType(DestinationType.ARTI);
                    break;
                case USER:
                    artiProtocol.setDestinationType(DestinationType.USER);
                    break;
                default:
                    artiProtocol.setDestinationType(DestinationType.USER);
            }
            artiProtocol.setDestination(client.getClientId());
        }

        return artiProtocol;
    }


}
