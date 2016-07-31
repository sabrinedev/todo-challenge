package com.training.todo_list.model.models;

import com.training.todo_list.utils.StringUtils;

import java.util.UUID;

/**
 * TodoTypes can provide additional information about a Todo. The name should provide information
 * about the emergency of a given Todo, and color add a visual impact to distinguish todo.
 */
public class TodoType {

    private String mSName;
    private String mSColor;
    private UUID mId;
    private int mIcon; // to help choose the appropriate color to the TODO type


    public TodoType(String pSName, String pSColor, UUID pId,int pIcon) {
        mSName = pSName;
        mSColor = pSColor;
        mId = pId;
        mIcon=pIcon;
    }


    public String name() {   return mSName;}

    public int type() {
        return mIcon;
    }

    public String color() {
        return mSColor;
    }

    public UUID id() {
        return mId;
    }


    @Override
    public int hashCode() {
        String tSName = (null == mSName) ? "name" : mSName;
        UUID tId = (null == mId) ? UUID.randomUUID() : mId;
        return tSName.hashCode() * 20 + tId.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TodoType))
            return false;
        TodoType tOther = (TodoType) o;
        boolean tBNameEquals = StringUtils.areEquals(mSName, tOther.mSName);
        boolean tBIdEquals = (null == mId && null == tOther.mId) ||
                (null != mId && null != tOther.mId && mId.equals(tOther.mId));
        return tBNameEquals && tBIdEquals;
    }
}
