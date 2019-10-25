package custom_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amal.mybabyhealthcare.R;

import java.util.ArrayList;
import java.util.HashMap;

import model.ChildSkill;

/**
 * Created by Amal on 4/26/2016.
 */
public class SkillsExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<String> titles;
    private HashMap<String, ArrayList<ChildSkill>> skills_data;

    public SkillsExpandableListAdapter(Context context, ArrayList<String> titles, HashMap<String, ArrayList<ChildSkill>> skills_data)
    {
        this.context = context;
        this.titles = titles;
        this.skills_data = skills_data;
    }

    @Override
    public int getGroupCount() {
        return titles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return skills_data.get(titles.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return skills_data.get(titles.get(groupPosition));
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return skills_data.get(titles.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;//((ChildSkill) getChild(groupPosition, childPosition)).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = titles.get(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.skills_expand_list_head, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.skill_expandable_head_title);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String skill_txt = ((ChildSkill) getChild(groupPosition, childPosition)).skill.getSkill();
        final String level_txt = ((ChildSkill) getChild(groupPosition, childPosition)).skill.getKind();
        final String mark = ((ChildSkill) getChild(groupPosition, childPosition)).getId();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.skill_expandable_list_item, null);
        }

        TextView skill = (TextView) convertView
                .findViewById(R.id.skills_expandable_list_skill);
        skill.setText(skill_txt);

        TextView level = (TextView) convertView
                .findViewById(R.id.skills_expandable_list_item_level);
        level.setText(level_txt);

        ImageView mark_img = (ImageView) convertView
                .findViewById(R.id.skills_expandable_list_item_mark);

        if (mark.equals("none"))
            mark_img.setImageResource(R.drawable.false_ico);
        else
            mark_img.setImageResource(R.drawable.good);

        final String rate = ((ChildSkill) getChild(groupPosition, childPosition)).getRate();

        switch (rate) {
            case ChildSkill.Rate.GOOD:
                convertView.setBackgroundColor(context.getResources().getColor(R.color.good_rate));
                break;
            case ChildSkill.Rate.VERY_GOOD:
                convertView.setBackgroundColor(context.getResources().getColor(R.color.very_good_rate));
                break;
            case ChildSkill.Rate.GREAT:
                convertView.setBackgroundColor(context.getResources().getColor(R.color.great_rate));
                break;
            case ChildSkill.Rate.EXCELLENT:
                convertView.setBackgroundColor(context.getResources().getColor(R.color.excellent_rate));
                break;
            case ChildSkill.Rate.OK:
                convertView.setBackgroundColor(context.getResources().getColor(R.color.ok_rate));
                break;
            case ChildSkill.Rate.VERY_BAD:
                convertView.setBackgroundColor(context.getResources().getColor(R.color.very_bad_rate));
                break;
            case ChildSkill.Rate.BAD:
                convertView.setBackgroundColor(context.getResources().getColor(R.color.bad_rate));
                break;
            case ChildSkill.Rate.NOT_BAD:
                convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
                break;
        }

        if(rate.equals(ChildSkill.Rate.NOT_BAD))
        {
            skill.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
            level.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        }
        else {
            skill.setTextColor(context.getResources().getColor(R.color.white));
            level.setTextColor(context.getResources().getColor(R.color.white));
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
