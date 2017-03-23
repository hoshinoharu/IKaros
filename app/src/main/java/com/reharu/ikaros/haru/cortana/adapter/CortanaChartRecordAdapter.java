package com.reharu.ikaros.haru.cortana.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reharu.harubase.base.AutoInjecter;
import com.reharu.harubase.base.HaruViewHolder;
import com.reharu.harubase.tools.HLog;
import com.reharu.ikaros.R;
import com.reharu.ikaros.haru.cortana.dto.ChartRecord;

import java.util.List;

/**
 * Created by hoshino on 2017/3/20.
 */

public class CortanaChartRecordAdapter extends RecyclerView.Adapter<CortanaChartRecordAdapter.ViewHolder> {

    public class ViewHolder extends HaruViewHolder{

        @AutoInjecter.ViewInject(R.id.left)  View left ;
        @AutoInjecter.ViewInject(R.id.right) View right ;
        @AutoInjecter.ViewInject(R.id.leftContent) TextView leftContent ;
        @AutoInjecter.ViewInject(R.id.rightContent) TextView rightContent ;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private List<ChartRecord> chartRecordList  ;
    private RecyclerView recyclerView ;

    public CortanaChartRecordAdapter(RecyclerView recyclerView, List<ChartRecord> chartRecordList) {
        this.recyclerView = recyclerView;
        this.chartRecordList = chartRecordList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hchart_record_list, parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChartRecord chartRecord = chartRecordList.get(position) ;
        HLog.e("TAG", "onShow :" + chartRecord.content);
        if(chartRecord.speakcer == ChartRecord.CORTANA){
            holder.left.setVisibility(View.VISIBLE);
            holder.right.setVisibility(View.GONE);
            holder.leftContent.setText(chartRecord.content);
        }else {
            holder.left.setVisibility(View.GONE);
            holder.right.setVisibility(View.VISIBLE);
            holder.rightContent.setText(chartRecord.content);
        }
    }

    @Override
    public int getItemCount() {
        return chartRecordList.size();
    }

    public void addRecord(ChartRecord chartRecord){
        int position = getItemCount() ;
        this.chartRecordList.add(position, chartRecord) ;
        notifyItemInserted(position);
        notifyItemChanged(position);
        if (position != getItemCount()) {
            notifyItemRangeChanged(position, getItemCount() - position);
        }
        recyclerView.scrollToPosition(chartRecordList.size()-1);
    }


}
