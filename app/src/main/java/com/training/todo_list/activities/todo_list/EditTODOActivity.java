package com.training.todo_list.activities.todo_list;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.training.todo_list.R;
import com.training.todo_list.model.managers.TodoManager;
import com.training.todo_list.model.managers.TodoTypeManager;
import com.training.todo_list.model.models.Todo;
import com.training.todo_list.model.models.TodoType;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class EditTODOActivity extends Activity {
    private Spinner mSpinnerTodoType ;
    private List<Todo> mTODOList;
    Todo tTodoItem;
    TodoManager tTodoManager;
    private EditText mEditTODODesc ;
    private  List<TodoType> mTodoTypeListTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        Intent tIntent=getIntent();
        int tIposition=tIntent.getIntExtra("position", 0);
         tTodoManager = new TodoManager();
         mTODOList=tTodoManager.all();
         tTodoItem=mTODOList.get(tIposition);

        mEditTODODesc=(EditText)findViewById(R.id.edt_EditTodoDesc);
        mEditTODODesc.setTextSize(16);
        mEditTODODesc.setText(tTodoItem.description());

        mSpinnerTodoType= (Spinner) findViewById(R.id.spinner_TodoType);
        TodoTypeManager tTodoTypeManager = new TodoTypeManager();
        mTodoTypeListTypes=tTodoTypeManager.all();
        UUID id_type=tTodoItem.idTodoType();
        TodoType type=tTodoTypeManager.todoTypeFor(id_type);
        int tPos=0;
        for(int i=0;i<mTodoTypeListTypes.size();i++)
        {
            if(type.equals(mTodoTypeListTypes.get(i)))
            {
               tPos=i;
            }
        }

        // Setting a Custom Adapter to the Spinner
        mSpinnerTodoType.setAdapter(new MyAdapter(EditTODOActivity.this, R.layout.todotype_custom_spinner,
                mTodoTypeListTypes));
        mSpinnerTodoType.setSelection(tPos);



    }

    public void EditTODO(View pView){

        //current date to create Todo
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat();
        String formattedDate = df.format(c.getTime());
        //get the UUId of TodoType
        int tItemPosition=mSpinnerTodoType.getSelectedItemPosition();
        UUID tTodotpeUUID=mTodoTypeListTypes.get(tItemPosition).id();
        UUID tTodoUUID=tTodoItem.id();

        boolean tBOccurence=true;
        if(mEditTODODesc.getText().toString().isEmpty() )
        {
            tBOccurence=false;
            AlertDialog.Builder tBuilder = new AlertDialog.Builder(EditTODOActivity.this);
            tBuilder.setTitle(R.string.EditToDo_title);
            tBuilder.setMessage(R.string.message_error_empty);
            tBuilder.setPositiveButton(R.string.confirm, null);
            tBuilder.show();
        }
        else {
            mTODOList=tTodoManager.all();
            for (int i = 0; i < mTODOList.size(); i++) {
                if ((mEditTODODesc.getText().toString().equals(mTODOList.get(i).description()))&&(tTodoUUID!=mTODOList.get(i).id())) {

                    tBOccurence = false;
                }

            }
            if (tBOccurence) {
                tTodoManager.update(tTodoUUID, mEditTODODesc.getText().toString(),
                        new Date(formattedDate), tTodotpeUUID, tTodoItem.isDone());
                AlertDialog.Builder tBuilder = new AlertDialog.Builder(EditTODOActivity.this);
                tBuilder.setTitle(R.string.EditToDo_title);
                tBuilder.setMessage(R.string.message_editTodo);
                tBuilder.setPositiveButton(R.string.confirm, null);
                tBuilder.show();
            } else {

                AlertDialog.Builder tBuilder = new AlertDialog.Builder(EditTODOActivity.this);
                tBuilder.setTitle(R.string.EditToDo_title);
                tBuilder.setMessage(R.string.message_error_exist);
                tBuilder.setPositiveButton(R.string.confirm, null);
                tBuilder.show();
            }
        }

    }
    public void BackToList(View pView){
        Intent tIntentListTODO=new Intent(EditTODOActivity.this,ActivityTodoList.class);
        startActivity(tIntentListTODO);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }

    //--------------------------Adapter for the spinner's list items--------------------------
    public class MyAdapter extends ArrayAdapter {

        public MyAdapter(Context pcontext, int ptextViewResourceId, List<TodoType> pobjects) {
            super(pcontext, ptextViewResourceId, pobjects);
        }

        public View getCustomView(int pIposition, View pconvertView, ViewGroup parent) {

            // Inflating the layout for the custom Spinner
            LayoutInflater tLayoutinflater = getLayoutInflater();
            View tViewlayout = tLayoutinflater.inflate(R.layout.todotype_custom_spinner, parent, false);

            // Declaring and Typecasting the textview in the inflated layout
            TextView tTodoType_title = (TextView) tViewlayout.findViewById(R.id.Todo_Type_title);

            // Setting the text using the array
            tTodoType_title.setText(mTodoTypeListTypes.get(pIposition).name());

            // Declaring and Typecasting the imageview in the inflated layout
            ImageView tTodoType_img= (ImageView) tViewlayout.findViewById(R.id.Todo_Type_img);

            // Setting the spinner item (TODOtype)
            int tITodoType=mTodoTypeListTypes.get(pIposition).type();
            switch (tITodoType)
            {
                case 1:tTodoType_img.setImageResource(R.drawable.emergency_todo);break;
                case 2:tTodoType_img.setImageResource(R.drawable.yesterday_todo);break;
                case 3:tTodoType_img.setImageResource(R.drawable.normal_todo);break;

            }
            // Declaring and Typecasting the imageview in the inflated layout
            LinearLayout tLayoutSpinnerTitle=(LinearLayout)tViewlayout.findViewById(R.id.layout_spinner_title);
            // Setting the color of the Type item layout
            tLayoutSpinnerTitle.setBackgroundColor(Color.parseColor(mTodoTypeListTypes.get(pIposition).color()));
            return tViewlayout;
        }

        // It gets a View that displays in the drop down popup the data at the specified position
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        // It gets a View that displays the data at the specified position
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }
        public TodoType getItem(int position){

            return mTodoTypeListTypes.get(position);
        }
    }


}
