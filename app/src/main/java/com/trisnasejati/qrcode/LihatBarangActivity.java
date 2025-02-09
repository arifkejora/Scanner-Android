package com.trisnasejati.qrcode;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.trisnasejati.qrcode.api.ApiClient;
import com.trisnasejati.qrcode.api.ApiInterface;
import com.trisnasejati.qrcode.model.list.BarangResponse;
import com.trisnasejati.qrcode.model.list.ListData; // Import ListData here

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LihatBarangActivity extends AppCompatActivity {

    ListView listView;
    SessionManager sessionManager;
    BarangAdapter barangAdapter; // Adapter for the ListView
    List<ListData> barangList; // Hold the list of Barang items

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_barang);

        listView = findViewById(R.id.listViewBarang);
        sessionManager = new SessionManager(LihatBarangActivity.this);

        // Initial call to load the barang list
        getListBarang();
    }

    private void getListBarang() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BarangResponse> call = apiInterface.getBarangList();

        call.enqueue(new Callback<BarangResponse>() {
            @Override
            public void onResponse(Call<BarangResponse> call, Response<BarangResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Extract data from the response body
                    barangList = response.body().getData(); // Populate the barang list
                    updateListView(); // Update the ListView with the fetched data
                } else {
                    Toast.makeText(LihatBarangActivity.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BarangResponse> call, Throwable t) {
                Toast.makeText(LihatBarangActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to update the ListView with the latest barang list
    private void updateListView() {
        // Initialize the adapter with the updated barang list
        barangAdapter = new BarangAdapter(LihatBarangActivity.this, barangList);
        listView.setAdapter(barangAdapter); // Set the adapter to the ListView
    }

    // Call this method whenever you perform add, update, or delete operations
    public void reloadList() {
        getListBarang(); // Reload the list by fetching the latest data
    }
}
