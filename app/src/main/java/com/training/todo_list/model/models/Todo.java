package com.training.todo_list.model.models;

import android.support.annotation.Nullable;

import com.training.todo_list.utils.StringUtils;

import java.util.Date;
import java.util.UUID;

/**
 * A "Todo" is uniquely identified by an id and a description (2 Todos cannot
 * have the same description). The description is entered by user. The timeCreation correspond to
 * the moment where the user asked to create the "Todo". IdTodoType is a link to a todoType.
 * IsDone is also choosen by user through interface.
 */
public class Todo {

    public void setmSDescription(String mSDescription) {
        this.mSDescription = mSDescription;
    }

    private String mSDescription;

    public void setmTimeCreation(Date mTimeCreation) {
        this.mTimeCreation = mTimeCreation;
    }

    private Date mTimeCreation;
    @Nullable private UUID mIdTodoType;

    public void setmBIsDone(boolean mBIsDone) {
        this.mBIsDone = mBIsDone;
    }

    private boolean mBIsDone;
    private UUID mId;

    public Todo(String pSDescription, Date pDayCreation,
                UUID pIdTodoType, boolean pBIsDone, UUID pId) {
        mSDescription = pSDescription;
        mTimeCreation = pDayCreation;
        mIdTodoType = pIdTodoType;
        mBIsDone = pBIsDone;
        mId = pId;
    }

    public String description() {
        return mSDescription;
    }

    public Date timeCreation() {
        return mTimeCreation;
    }

    @Nullable public UUID idTodoType() {
        return mIdTodoType;
    }

    public boolean isDone() {
        return mBIsDone;
    }


    public UUID id() {
        return mId;
    }

    @Override
    public int hashCode() {
        String tSName = (null == mSDescription) ? "desc" : mSDescription;
        UUID tId = (null == mId) ? UUID.randomUUID() : mId;
        return tSName.hashCode() * 20 + tId.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Todo))
            return false;
        Todo tOther = (Todo) o;
        boolean tBNameEquals = StringUtils.areEquals(mSDescription, tOther.mSDescription);
        boolean tBIdEquals = (null == mId && null == tOther.mId) ||
                (null != mId && null != tOther.mId && mId.equals(tOther.mId));
        return tBNameEquals && tBIdEquals;
    }
}
