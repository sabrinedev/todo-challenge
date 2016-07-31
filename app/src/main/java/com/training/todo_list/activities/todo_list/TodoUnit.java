package com.training.todo_list.activities.todo_list;

import android.graphics.Color;
import android.support.annotation.NonNull;

import com.training.todo_list.R;
import com.training.todo_list.model.models.Todo;
import com.training.todo_list.model.models.TodoType;
import com.training.todo_list.utils.StringUtils;

import java.util.Date;
import java.util.UUID;

public class TodoUnit {

    @NonNull private String mSDescription;
    @NonNull private String mSTimeCreation;
    private Date mTimeCreation;
    private boolean mBIsDone;
    private int mIColor;
    private UUID mIdTodo;


    private TodoUnit(@NonNull String pSDescription, @NonNull String pSTimeCreation,
                     Date pTimeCreation, boolean pBIsDone, int pIColor) {
        mSDescription = pSDescription;
        mSTimeCreation = pSTimeCreation;
        mBIsDone = pBIsDone;
        mIColor = pIColor;
    }

    private TodoUnit(String pSName, String pSTimeCreation,
                     Date pTimeCreation, boolean pBIsDone, int pIColor, UUID pIdTodo) {
        this(pSName, pSTimeCreation, pTimeCreation, pBIsDone, pIColor);
        mIdTodo = pIdTodo;
    }


    public @NonNull String description() {
        return mSDescription;
    }

    public @NonNull String timeCreationFormatted() {
        return mSTimeCreation;
    }

    public Date timeCreation() {
        return mTimeCreation;
    }

    public boolean isDone() {
        return mBIsDone;
    }

    public int color() {
        return mIColor;
    }

    public UUID idTodo() {
        return mIdTodo;
    }


    public static class Builder {

        private StringUtils mStringUtils;
        private static final int defaultColor = Color.BLACK;


        public Builder(@NonNull StringUtils pStringUtils) {
            mStringUtils = pStringUtils;
        }


        public TodoUnit build(Todo pTodo, TodoType pTodoType) {
            if (null == pTodo || null == pTodoType)
                return defaultUnit(mStringUtils);
            String tSDescription = descriptionOf(pTodo);
            String tSTimeCreation = timeCreationOf(pTodo);
            Date pTimeCreation = pTodo.timeCreation();
            boolean tBIsDone = pTodo.isDone();
            int tIColor = colorOf(pTodoType);
            UUID tIdTodo = pTodo.id();
            return new TodoUnit(tSDescription,
                    tSTimeCreation, pTimeCreation, tBIsDone, tIColor, tIdTodo);
        }
        private String descriptionOf(Todo pTodo) {
            String tSDescription = pTodo.description();
            return StringUtils.isNullOrEmpty(tSDescription) ?
                    mStringUtils.stringFor(R.string.todo_default_name) :
                    tSDescription;
        }
        private String timeCreationOf(Todo pTodo) {
            Date tDateCreation = pTodo.timeCreation();
            return (null == tDateCreation) ?
                    mStringUtils.stringFor(R.string.todo_default_date) :
                    mStringUtils.timeFor(tDateCreation);
        }
        private int colorOf(TodoType pTodoType) {
            String tSColor = pTodoType.color();
            return mStringUtils.colorFor(tSColor, defaultColor);
        }


        private TodoUnit defaultUnit(StringUtils pStringUtils) {
            String tSDescription = pStringUtils.stringFor(R.string.todo_default_name);
            Date tTimeNow = new Date();
            String tSTimeCreation = pStringUtils.dayFor(tTimeNow);
            return new TodoUnit(tSDescription, tSTimeCreation, tTimeNow, false, defaultColor);
        }
    }
}
