package com.training.todo_list.model.managers;

import com.training.todo_list.model.models.TodoType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * This is just a very simple implementation of a manager allowing to create, save and retrieve
 * a list of TodoTypes. Nothing is saved into a database or file, it is initialized
 * in the method onCreate of TodoListApplication.
 * Just create a `new TodoTypeManager()`, and you will be able to :
 * - retrieve all TodoTypes,
 * - retrive a TodoType by Id
 * - create a new TodoType
 * The save method is not really useful.
 */
public class TodoTypeManager {

    private static final Set<TodoType> mTodoTypes = new HashSet<>();


    public List<TodoType> all() {
        return new ArrayList<>(mTodoTypes);
    }


    public TodoType todoTypeFor(UUID pIdTodo) {
        for (TodoType tTodoType : mTodoTypes)
            if (tTodoType.id().equals(pIdTodo))
                return tTodoType;
        return null;
    }


    public void save(TodoType pTodoType) {
        if (null != pTodoType)
            mTodoTypes.add(pTodoType);
    }

    public TodoType create(String pSName, String pSColor, int pIcon) {
        UUID tIdNew = UUID.randomUUID();
        TodoType tTodoType = new TodoType(pSName, pSColor, tIdNew, pIcon);
        mTodoTypes.add(tTodoType);
        return tTodoType;
    }
}
