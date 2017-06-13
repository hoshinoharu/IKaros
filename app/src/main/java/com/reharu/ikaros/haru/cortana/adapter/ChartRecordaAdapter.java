package com.reharu.ikaros.haru.cortana.adapter;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.reharu.harubase.base.AutoInjecter;
import com.reharu.harubase.base.HaruListViewHolder;
import com.reharu.harubase.tools.AnimeTool;
import com.reharu.harubase.tools.HLog;
import com.reharu.ikaros.R;
import com.reharu.ikaros.haru.cortana.dto.ChartRecord;

import java.util.List;

/**
 * Created by hoshino on 2017/3/20.
 */

public class ChartRecordaAdapter extends BaseAdapter {

    private ListView listView ;
    private List<ChartRecord> chartRecordList  ;

    private boolean onChanged = false;

    public class ViewHolder extends HaruListViewHolder{

        @AutoInjecter.ViewInject(R.id.left)  View left ;
        @AutoInjecter.ViewInject(R.id.right) View right ;
        @AutoInjecter.ViewInject(R.id.leftContent) TextView leftContent ;
        @AutoInjecter.ViewInject(R.id.rightContent) TextView rightContent ;

        public ViewHolder(View contentView) {
            super(contentView);
        }
    }

    public ChartRecordaAdapter(ListView listView, List<ChartRecord> chartRecordList) {
        this.listView = listView;
        this.chartRecordList = chartRecordList;
        registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                onChanged = true ;
            }

            @Override
            public void onInvalidated() {
                HLog.e("TAG", "onInvalidated'");
            }
        });
    }

    @Override
    public int getCount() {
        return chartRecordList.size();
    }

    @Override
    public Object getItem(int position) {
        return chartRecordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hchart_record_list, null, false) ;
            ViewHolder viewHolder = new ViewHolder(convertView) ;
            convertView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        ChartRecord chartRecord = chartRecordList.get(position) ;
        boolean fromLeft = true ;
        if(chartRecord.speakcer == ChartRecord.CORTANA){
            holder.left.setVisibility(View.VISIBLE);
            holder.right.setVisibility(View.GONE);
            holder.leftContent.setText(chartRecord.content);
        }else {
            holder.left.setVisibility(View.GONE);
            holder.right.setVisibility(View.VISIBLE);
            holder.rightContent.setText(chartRecord.content);
            fromLeft = false ;
        }
        if(onChanged){
            if(position >= getCount()-1){
                AnimeTool.showAnime(convertView, fromLeft).start() ;
                onChanged = false ;
            }
        }else {
            AnimeTool.showAnime(convertView, fromLeft).start() ;
        }
        return convertView;
    }

    public void addRecord(ChartRecord chartRecord){
        this.chartRecordList.add(chartRecord) ;
        notifyDataSetChanged();
        listView.smoothScrollToPosition(getCount()+1);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

}
