package com.example.tlu_rideshare.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.R;
import com.example.tlu_rideshare.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private List<User> userList;

    private String fromLocation;

    private String toLocation;

    public UserAdapter(Context context, List<User> userList, String fromLocation, String toLocation) {
        this.context = context;
        this.userList = userList;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customer_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tvName.setText("Tên: " + user.getFullName());
        holder.tvPickup.setText("Đón: " + fromLocation);
        holder.tvDropoff.setText("Trả: " + toLocation);
        holder.tvPhone.setText("Sđt: " + user.getPhoneNumber());
        holder.tvEmail.setText("Email: " + user.getEmail());

        // Xử lý nút xóa (nếu muốn)
        holder.btnDelete.setOnClickListener(v -> {
            userList.remove(position);
            notifyItemRemoved(position);
            // Có thể thêm mã xóa trên Firebase nếu cần
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPickup, tvDropoff, tvPhone, tvEmail;
        ImageView btnDelete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPickup = itemView.findViewById(R.id.tvPickup);
            tvDropoff = itemView.findViewById(R.id.tvDropoff);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
