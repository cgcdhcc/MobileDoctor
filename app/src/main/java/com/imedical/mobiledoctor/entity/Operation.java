package com.imedical.mobiledoctor.entity;

import java.io.Serializable;


public class Operation implements Serializable {
    private static final long serialVersionUID = 1L;

    public Operation() {
    }

    public String RegNo;
    public String PatLoc;
    public String OpDate;
    public String OPAStatus;
    public String OpName;
    public String OPAOpRoom;
    public String OPASeq;
    public String ANAOPPreopDiag;
    public String OpDoctor;
    public String EpisodeID;
    public String PatName;

    public String opaId;
    public String opaName;
    public String opaStartTime;
    public String opaEndTime;
    public String opaLoc;
}