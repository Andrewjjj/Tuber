package com.example.aman.tuber;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SkillListAdapter extends BaseAdapter
{
    private Context myContext;
    private ArrayList<Skill> skills;

    public SkillListAdapter(ArrayList<Skill> skills, Context myContext)
    {
        this.skills = skills;
        this.myContext = myContext;
    }


    @Override
    public int getCount()
    {
        return skills.size();
    }
    @Override
    public Object getItem(int position)
    {
        return skills.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = LayoutInflater.from(myContext).inflate(R.layout.skill_item, parent, false);
        }
        TextView skillName = (TextView)convertView.findViewById(R.id.skillName);
        TextView skillDescription = (TextView)convertView.findViewById(R.id.skillDescription);
        skillName.setText("Hello");
        skillDescription.setText("Bonjour");
        /*
        skillName.setText(skills.get(position).GetSkillName());
        skillDescription.setText(skills.get(position).GetSkillDescription());*/

        return convertView;
    }

}
