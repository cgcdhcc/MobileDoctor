package com.imedical.mobiledoctor.entity;

/**
 * <AntBodyPart>
 * <bodyId>身体部位Id</bodyId>
 * <bodyCode>身体部位代码</bodyCode>
 * <bodyDesc>身体部位</bodyDesc>
 * </AntBodyPart>
 *
 * @author sszvip
 */
public class AntBodyPart {
    public AntBodyPart() {
    }

    public String bodyId;
    public String bodyCode;
    public String bodyDesc;


    @Override
    public String toString() {
        return bodyDesc;
    }

}
