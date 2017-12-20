package com.appdynamics.extensions.solace.semp.r8_6VMR;

import com.appdynamics.extensions.solace.semp.SempMarshaller;
import com.solacesystems.semp_jaxb.r8_6VMR.reply.RpcReply;
import com.solacesystems.semp_jaxb.r8_6VMR.request.Rpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

//
public class SempMarshaller_r8_6VMR implements SempMarshaller<Rpc, RpcReply> {
    private static final Logger logger = LoggerFactory.getLogger(SempMarshaller_r8_6VMR.class);

    public SempMarshaller_r8_6VMR() throws JAXBException {
        writer = new StringWriter();
        reqCtx = JAXBContext.newInstance(Rpc.class);
        replyCtx = JAXBContext.newInstance(RpcReply.class);
        marshaller = reqCtx.createMarshaller();
        unmarshaller = replyCtx.createUnmarshaller();
    }


    public String toRequestXml(Rpc rpc){
        String result = null;
        try {
            marshaller.marshal(rpc, writer);
            result = writer.toString();
        }
        catch (JAXBException e) {
            logger.error("Exception thrown marshaling soltr/8.6VMR request", e);
            e.printStackTrace();
        }
        buf = writer.getBuffer();
        buf.setLength(0);
        return result;
    }

    public RpcReply fromReplyXml(String response) {
        try {
            return (RpcReply) unmarshaller.unmarshal(new StringReader(response));
        } catch (Exception e) {
            logger.error("Exception thrown unmarshaling soltr/8.6VMR reply", e);
            e.printStackTrace();
        }
        return null;
    }

    private JAXBContext reqCtx;
    private JAXBContext replyCtx;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    private StringWriter writer;
    private StringBuffer buf;
}