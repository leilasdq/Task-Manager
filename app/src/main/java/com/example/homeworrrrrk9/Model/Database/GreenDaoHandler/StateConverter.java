package com.example.homeworrrrrk9.Model.Database.GreenDaoHandler;

import com.example.homeworrrrrk9.State;

import org.greenrobot.greendao.converter.PropertyConverter;

public class StateConverter implements PropertyConverter<Enum<State>, String> {
    @Override
    public State convertToEntityProperty(String databaseValue) {
        if (databaseValue==null) {
            return null;
        }
        return State.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(Enum<State> entityProperty) {
        if (entityProperty==null){
            return null;
        }
        return entityProperty.name();
    }
}
