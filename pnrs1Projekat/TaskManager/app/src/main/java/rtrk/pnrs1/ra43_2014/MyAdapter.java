package rtrk.pnrs1.ra43_2014;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by student on 11.4.2017.
 */

public class MyAdapter extends BaseAdapter{

    private Context myContext;
    private ArrayList<ListElement> myTaskList;

    public MyAdapter(Context context)
    {
        myContext = context;
        myTaskList = new ArrayList<ListElement>();
    }

    public void addTask(ListElement listElement)
    {
        myTaskList.add(listElement);
        notifyDataSetChanged();
    }

    public void removeTask(int position)
    {
        myTaskList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return myTaskList.size();
    }

    @Override
    public Object getItem(int position) {
        Object returnValue = null;
        try
        {
            returnValue = myTaskList.get(position);
        }
        catch(IndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }

        return returnValue;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View myView = convertView;
        if(myView == null)
        {
            LayoutInflater myInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myView = myInflater.inflate(R.layout.list_element, null);   //my .xml file
            ViewHolder myViewHolder = new ViewHolder();
            myViewHolder.name = (TextView) myView.findViewById(R.id.task_name1);
            myView.setTag(myViewHolder);
        }

        ListElement myListElementEntry = (ListElement) getItem(position);
        ViewHolder myViewHolder = (ViewHolder) myView.getTag();
        myViewHolder.name.setText(myListElementEntry.imeZadatka);
        return myView;
    }

    private class ViewHolder
    {
        public TextView name = null;
    }
}
