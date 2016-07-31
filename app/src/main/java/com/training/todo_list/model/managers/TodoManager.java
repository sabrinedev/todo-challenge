package com.training.todo_list.model.managers;

import com.training.todo_list.model.models.Todo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * This is just a very simple implementation of a manager allowing to create, save and retrieve
 * a list of Todos. Nothing is saved into a database or file, it is initialized
 * in the method onCreate of TodoListApplication.
 * Just create a `new TodoManager()`, and you will be able to :
 * - retrieve all Todos,
 * - retrive a Todos by Id
 * - create a new Todo
 * The save method is not really useful.
 */
public class TodoManager {

    private static final Set<Todo> mTodos = new HashSet<>();


    public List<Todo> all() {
        return new ArrayList<>(mTodos);
    }


    public Todo todoFor(UUID pIdTodo) {
        for (Todo tTodo : mTodos)
            if (tTodo.id().equals(pIdTodo))
                return tTodo;
        return null;
    }


    public void save(Todo pTodo) {
        if (null != pTodo && null != pTodo.id())
            mTodos.add(pTodo);
    }


    public boolean create(String pSDescription, Date pDateCreation, UUID pIdTodoType, boolean pBIsDone) {

        List<Todo> mTODOList=all();
        boolean test=true;
        for(int i=0;i<mTODOList.size();i++)
        {
            if(pSDescription.equals(mTODOList.get(i).description())){
            test= false;
            }
        }
        if(test==true) {
            UUID tIdNew = UUID.randomUUID();
            Todo tTodo = new Todo(pSDescription, pDateCreation, pIdTodoType, pBIsDone, tIdNew);
            mTodos.add(tTodo);
            return true;
        }
        else
            return false;

    }
    public void remove(Todo tTodo) {
        mTodos.remove(tTodo);
    }
    public Todo update(UUID pIdTodo, String pSDescription, Date pDateCreation, UUID pIdTodoType, boolean pBIsDone) {

        Todo tTodo = todoFor(pIdTodo);

        mTodos.remove(tTodo);
        Todo tTodonew = new Todo(pSDescription, pDateCreation, pIdTodoType, pBIsDone, pIdTodo);

        mTodos.add(tTodonew);
        return tTodonew;
    }
}
