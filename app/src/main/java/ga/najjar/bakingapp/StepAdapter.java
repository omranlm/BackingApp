package ga.najjar.bakingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ga.najjar.bakingapp.Utils.Step;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {
    private List<Step> steps;
    private Context mContext;

    public StepAdapter(Context context, List<Step> _steps)
    {
        mContext = context;
        steps = _steps;
    }

    @NonNull
    @Override
    public StepAdapter.StepViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.step_item, viewGroup, false);

        return new StepAdapter.StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepAdapter.StepViewHolder holder, int position) {


        Step step = steps.get(position);
        holder.bind(step);
        // TODO check again
        // TODO setup the video Exoplayer here

    }

    @Override
    public int getItemCount() {
        if (steps == null)
            return 0;
        else
            return steps.size();
    }

    public class StepViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        TextView stepOrder;
        TextView stepDescription;
        public StepViewHolder(@NonNull View itemView) {
            super(itemView);
            stepOrder = (TextView) itemView.findViewById(R.id.tv_step_order);
            stepDescription = (TextView) itemView.findViewById(R.id.tv_step_description);


            // TODO setup the video Exoplayer here


        }

        public void bind(Step step) {
            stepOrder.setText(String.valueOf(step.getId()));
            stepDescription.setText(step.getDescription());

        }
    }





}
