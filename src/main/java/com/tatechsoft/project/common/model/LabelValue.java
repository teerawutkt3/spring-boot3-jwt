package com.tatechsoft.project.common.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LabelValue implements Serializable {

    private static final long serialVersionUID = -4903724438420463149L;
    private String label;
    private String value;

    public LabelValue(String label, String value) {
        this.label = label;
        this.value = value;

    }

    @Override
    public String toString() {
        return "LabelValueBean [label=" + label + ", value=" + value + "]";
    }

}
