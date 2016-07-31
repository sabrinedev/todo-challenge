package com.training.todo_list.activities.todo_list;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.training.todo_list.R;
import com.training.todo_list.model.managers.TodoManager;
import com.training.todo_list.model.managers.TodoTypeManager;
import com.training.todo_list.model.models.Todo;
import com.training.todo_list.model.models.TodoType;

import java.util.List;
import java.util.UUID;

public class ActivityTodoList extends Activity {

    private List<Todo> mTODOList;
    private AppAdapter mAdapter;
    TodoManager tTodoManager;
    private SwipeMenuListView mSwipeListView;
    DisplayMetrics metrics;
    TextView mTextTodoDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lt_act_todo_list);

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        tTodoManager = new TodoManager();
        mTODOList = tTodoManager.all();

        mSwipeListView = (SwipeMenuListView) findViewById(R.id.Todo_listView);
        mTextTodoDesc=(TextView)findViewById(R.id.txt_todoDesc);

        mAdapter = new AppAdapter(this);
        mSwipeListView.setAdapter(mAdapter);

        // set SwipeMenu Creator to enable Edit and Delete items on list item swipe
        SwipeMenuCreator tSwipecreator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "Edit" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(50, 115, 148)));
                // set item width
                openItem.setWidth(dp2px(60));
                openItem.setIcon(R.drawable.ic_action_edit_l_white);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(248,
                        148, 6)));
                // set item width
                deleteItem.setWidth(dp2px(60));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_action_cancel_l_white);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        mSwipeListView.setMenuCreator(tSwipecreator);

        mSwipeListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int pIposition, SwipeMenu pMenu, int pIndex) {
                Todo item = mTODOList.get(pIposition);
                switch (pIndex) {
                    case 0:
                        // Edit TODO
                        Intent i = new Intent(getApplicationContext(), EditTODOActivity.class);
                        i.putExtra("position", pIposition);
                        startActivity(i);
                        break;
                    case 1:
                        // delete TODO
                        mTODOList.remove(pIposition);
                        mAdapter.notifyDataSetChanged();
                        delete(item);
                        break;
                }
                return false;
            }
        });

        // set SwipeListener
        mSwipeListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start

            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        // set MenuStateChangeListener
        mSwipeListView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
            }

            @Override
            public void onMenuClose(int position) {
            }
        });


    }

    public void delete(Todo pTodoItem)
    {
        tTodoManager.remove(pTodoItem);
    }
    private int dp2px(int pIdp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pIdp,
                getResources().getDisplayMetrics());
    }


    public void askAddTodo(View pView) {
        Intent tIntentAddToDo=new Intent(this,AddToDoActivity.class);
        startActivity(tIntentAddToDo);
    }


    public void askSurprise(View pView) {
        AlertDialog.Builder tBuilder = new AlertDialog.Builder(this);
        tBuilder.setTitle(R.string.dialog_title_surprise);
        tBuilder.setMessage(R.string.dialog_message_surprise);
        tBuilder.setPositiveButton(R.string.confirm, null);
        tBuilder.show();
    }

    @Override
    public void onBackPressed() {
        // do nothing.
      finish();
    }

    //--------------------------Adapter for the list --------------------------
    class AppAdapter extends BaseAdapter {
        private Context mcontext;
        public ViewHolder mViewholder;
        public AppAdapter(Context pcontext){
            this.mcontext=pcontext;
        }
        @Override
        public int getCount() {
            return mTODOList.size();
        }

        @Override
        public Todo getItem(int pIposition) {
            return mTODOList.get(pIposition);
        }

        @Override
        public long getItemId(int pIposition) {
            return pIposition;
        }

        @Override
        public View getView(int pIposition, View pconvertView, ViewGroup parent) {
            if (pconvertView == null) {
                pconvertView = View.inflate(getApplicationContext(),
                        R.layout.custom_list_item_todo, null);
                new ViewHolder(pconvertView);

            }

            mViewholder = (ViewHolder) pconvertView.getTag();

            final Todo tTODOitem = getItem(pIposition);

            mViewholder.mTextTODODesc.setText(tTODOitem.description());
            mViewholder.mTextDateTODO.setText(tTODOitem.timeCreation().toString());

            UUID TypeUUid=tTODOitem.idTodoType();
            TodoTypeManager tTodoTypeManager = new TodoTypeManager();
            TodoType tTypeTODOitem =tTodoTypeManager.todoTypeFor(TypeUUid);
            mViewholder.mLayouttypeTODO.setBackgroundColor(Color.parseColor(tTypeTODOitem.color()));

            if(tTODOitem.isDone())
            {
                mViewholder.mTextTODODesc.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                mViewholder.mCheckboxItem.setChecked(true);
                mViewholder.mLayoutItem.setAlpha((float) 0.7);

            }

            mViewholder.mCheckboxItem.setTag(pIposition);

            mViewholder.mCheckboxItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton pbuttonView, boolean pBisChecked) {
                    int position = (int) pbuttonView.getTag();

                    if (pBisChecked == true) {

                        tTODOitem.setmBIsDone(true);
                        //update item list to mark a TODO as done
                        updateView(position, pBisChecked);
                    } else {

                        tTODOitem.setmBIsDone(false);
                        //update item list to mark a TODO as undone
                        updateView(position, pBisChecked);

                    }

                }
            });

            mViewholder.mTextTODODesc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                 //  Toast.makeText(ActivityTodoList.this, "Swipe Left for options", Toast.LENGTH_SHORT).show();
                }
            });

            //set Animation to animate list Items display
            Animation animation = null;
            animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_top);
            animation.setDuration(500);
            pconvertView.startAnimation(animation);

            return pconvertView;
        }

        class ViewHolder {
            CheckBox mCheckboxItem;
            TextView mTextTODODesc;
            TextView mTextDateTODO;
            LinearLayout mLayouttypeTODO;
            RelativeLayout mLayoutItem;

            public ViewHolder(View view) {
                mCheckboxItem = (CheckBox) view.findViewById(R.id.check_listitem);
                mTextTODODesc = (TextView) view.findViewById(R.id.txt_todoDesc);
                mTextDateTODO=(TextView) view.findViewById(R.id.txt_todoDate);
                mLayouttypeTODO=(LinearLayout) view.findViewById(R.id.todotype_colorbar);
                mLayoutItem=(RelativeLayout) view.findViewById(R.id.List_item_layout);
                view.setTag(this);
            }
        }

        private void updateView(int pIndex,boolean pBstate){
            View tView = mSwipeListView.getChildAt(pIndex -
                    mSwipeListView.getFirstVisiblePosition());

            if(tView == null)
                return;

            TextView tDescTODO = (TextView) tView.findViewById(R.id.txt_todoDesc);
            RelativeLayout tLayoutItem = (RelativeLayout) tView.findViewById(R.id.List_item_layout);


            //Mark Todo done by striking the list item  and undo it by removing the strike based on the check box state

            if(pBstate==true) {
                tDescTODO.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tLayoutItem.setAlpha((float) 0.7);
            }
            else{
                tDescTODO.setPaintFlags(tDescTODO.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                tLayoutItem.setAlpha(1);
            }
        }

   }


}
