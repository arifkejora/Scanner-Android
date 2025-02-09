package com.trisnasejati.qrcode;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import com.trisnasejati.qrcode.model.list.ListData; // Updated to use ListData
import com.trisnasejati.qrcode.api.ApiClient;
import com.trisnasejati.qrcode.api.ApiInterface;
import com.trisnasejati.qrcode.api.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarangAdapter extends BaseAdapter {
    private Context context;
    private List<ListData> barangList; // Use ListData here

    public BarangAdapter(Context context, List<ListData> barangList) {
        this.context = context;
        this.barangList = barangList;
    }

    @Override
    public int getCount() {
        return barangList.size();
    }

    @Override
    public Object getItem(int position) {
        return barangList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_barang, parent, false);
        }

        final ListData barang = barangList.get(position); // Updated to use ListData

        TextView tvId = convertView.findViewById(R.id.tvId);
        TextView tvNama = convertView.findViewById(R.id.tvNama);
        TextView tvKategori = convertView.findViewById(R.id.tvKategori);
        TextView tvQuantity = convertView.findViewById(R.id.tvQuantity);
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);

        tvId.setText("ID Barcode Barang : " + barang.getId());
        tvNama.setText("Nama Barang : " + barang.getNama());
        tvKategori.setText("Kategori : " + barang.getKategori());
        tvQuantity.setText("Quantity : " + barang.getQuantity());
        tvPrice.setText("Harga : " + barang.getPrice());

        Button btnEdit = convertView.findViewById(R.id.btnEdit);
        Button btnHapus = convertView.findViewById(R.id.btnHapus);

        // Edit button click listener
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog(barang);
            }
        });

        // Hapus button click listener
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog(barang.getId());
            }
        });

        return convertView;
    }

    private void showEditDialog(final ListData barang) {
        // Create a dialog to edit data
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Barang");

        // Set up the input fields
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText etNama = new EditText(context);
        etNama.setText(barang.getNama());
        layout.addView(etNama);

        final EditText etKategori = new EditText(context);
        etKategori.setText(barang.getKategori());
        layout.addView(etKategori);

        final EditText etQuantity = new EditText(context);
        etQuantity.setText(barang.getQuantity());
        layout.addView(etQuantity);

        final EditText etPrice = new EditText(context);
        etPrice.setText(barang.getPrice());
        layout.addView(etPrice);

        builder.setView(layout);

        // Set positive button to update the data
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Call API to update data
                updateBarangData(barang.getId(), etNama.getText().toString(), etKategori.getText().toString(),
                        etQuantity.getText().toString(), etPrice.getText().toString());
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void updateBarangData(String id, String nama, String kategori, String quantity, String price) {
        // Call API for updating data
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse> call = apiInterface.updateBarang(id, nama, kategori, quantity, price);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Data updated successfully", Toast.LENGTH_SHORT).show();
                    // Call reload method from the Activity (LihatBarangActivity)
                    if (context instanceof LihatBarangActivity) {
                        ((LihatBarangActivity) context).reloadList(); // Reload the list after successful update
                    }
                } else {
                    Toast.makeText(context, "Failed to update data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDeleteConfirmationDialog(final String id) {
        // Confirmation dialog for deleting item
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Item");
        builder.setMessage("Apakah anda ingin menghapus barang ini?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteBarang(id);
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    private void deleteBarang(String id) {
        // Call API to delete item
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse> call = apiInterface.deleteBarang(id);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Delete Data successfully", Toast.LENGTH_SHORT).show();
                    // Call reload method from the Activity (LihatBarangActivity)
                    if (context instanceof LihatBarangActivity) {
                        ((LihatBarangActivity) context).reloadList(); // Reload the list after successful deletion
                    }
                } else {
                    Toast.makeText(context, "Failed to delete data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
