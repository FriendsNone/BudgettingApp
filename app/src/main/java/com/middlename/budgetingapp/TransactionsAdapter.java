package com.middlename.budgetingapp;

import static android.text.format.DateUtils.isToday;
import static com.middlename.budgetingapp.DateUtils.formatDate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder>{
    private Context context;
    private List<TransactionsTable> transactions;

    private ClickEvent clickEvent;

    public TransactionsAdapter(Context context, ClickEvent clickEvent) {
        this.context = context;
        transactions = new ArrayList<>();
        this.clickEvent = clickEvent;
    }

    public void add(TransactionsTable transactionsTable) {
        transactions.add(transactionsTable);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransactionsTable transactionsTable = transactions.get(position);

        String formattedDate = formatDate(transactionsTable.getDate());

        holder.name.setText(transactionsTable.getName());
        holder.description.setText(transactionsTable.getDescription());
        holder.date.setText(formattedDate);

        if (transactionsTable.isIncome()) {
            holder.amount.setTextColor(context.getResources().getColor(R.color.green_100));
            holder.amount.setText("+ ₱" + transactionsTable.getAmount());
        } else {
            holder.amount.setTextColor(context.getResources().getColor(R.color.red_100));
            holder.amount.setText("- ₱" + transactionsTable.getAmount());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEvent.OnClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }
    public int getTransactionId(int position) { return transactions.get(position).getId(); }
    public String getTransactionName(int position) { return transactions.get(position).getName(); }
    public long getTransactionAmount(int position) { return transactions.get(position).getAmount(); }
    public String getTransactionDescription(int position) { return transactions.get(position).getDescription(); }
//    date
    public Date getTransactionDate(int position) { return transactions.get(position).getDate(); }
    public boolean getTransactionType(int position) { return transactions.get(position).isIncome(); }

    public void delete(int position) {
        transactions.remove(position);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, amount, description, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            amount = itemView.findViewById(R.id.amount);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
        }
    }
}
