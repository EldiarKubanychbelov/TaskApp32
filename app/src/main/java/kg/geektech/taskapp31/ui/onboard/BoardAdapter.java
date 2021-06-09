package kg.geektech.taskapp31.ui.onboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import kg.geektech.taskapp31.R;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private Finish finish;

    private String[] titles = new String[]{"Салам", "Привет", "Hello"};
    private int[] imgLogo = new int[]{R.drawable.shaurma, R.drawable.gamburger, R.drawable.pitsa};

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_board, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);


    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public void setOpenHome(Finish finish) {
        this.finish = finish;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        ImageView imgLogo2;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);
            imgLogo2 = itemView.findViewById(R.id.img_logo);
            button = itemView.findViewById(R.id.btn_finish);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish.btnFinishClick();
                }
            });
        }

        public void bind(int position) {
            txtTitle.setText(titles[position]);
            imgLogo2.setImageResource(imgLogo[position]);

            if (position == titles.length-1) {
                button.setVisibility(View.VISIBLE);
            } else {
                button.setVisibility(View.GONE);
            }

        }
    }
}

interface Finish {
    void btnFinishClick();
}
