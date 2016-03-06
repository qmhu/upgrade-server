package com.my.model;


import java.io.Serializable;
import java.util.List;

public class ReleaseModule implements Serializable {

    private static final long serialVersionUID = -7568613173795333322L;

    private String module;
    private List<ReleasePlan> plans;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public List<ReleasePlan> getPlans() {
        return plans;
    }

    public void setPlans(List<ReleasePlan> plans) {
        this.plans = plans;
    }

}
