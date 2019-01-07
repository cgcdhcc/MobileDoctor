package com.imedical.mobiledoctor.entity.homegrid;

import java.io.Serializable;

/**
 * @author LED
 */
public class funcState implements Serializable {
    private static final long serialVersionUID = 1L;

    public funcState() {
    }

    public int isdisplay[] = new int[]{0, 0, 0, 0, 0, 0, 0, 0};//1显示,0不显示
}
