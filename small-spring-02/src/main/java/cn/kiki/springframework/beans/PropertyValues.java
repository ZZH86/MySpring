package cn.kiki.springframework.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author hui cao
 * @Description: 属性集合
 */
public class PropertyValues {

    private final List<PropertyValue> propertyValueArrayList = new ArrayList<>();

    public void addPropertyValue(PropertyValue propertyValue){
        this.propertyValueArrayList.add(propertyValue);
    }

    public PropertyValue[] getPropertyValues() {
        return this.propertyValueArrayList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName){
        for (PropertyValue propertyValue : propertyValueArrayList) {
            if(propertyValue.getName().equals(propertyName)){
                return propertyValue;
            }
        }
        return null;
    }
}
