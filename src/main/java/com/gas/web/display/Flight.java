package com.gas.web.display;

import java.util.List;

public class Flight {
    private List<String> dimensions;
    private List<List<Object>> data;

    public Flight() {

    }

    public List<String> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<String> dimensions) {
        this.dimensions = dimensions;
    }

    public List<List<Object>> getData() {
        return data;
    }

    public void setData(List<List<Object>> data) {
        this.data = data;
    }
}
