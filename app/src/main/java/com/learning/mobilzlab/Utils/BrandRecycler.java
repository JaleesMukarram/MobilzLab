package com.learning.mobilzlab.Utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.mobilzlab.Models.UtilModels.Brand;
import com.learning.mobilzlab.R;

import java.util.ArrayList;
import java.util.List;

public class BrandRecycler {

    private RecyclerView mBrandRecycler;
    private Activity homeScreen;
    private BrandAdapter mBrandAdapter;

    public BrandRecycler(RecyclerView mBrandRecycler, Activity homeScreen) {

        this.mBrandRecycler = mBrandRecycler;
        this.homeScreen = homeScreen;
    }

    public void startShowing() {

        mBrandAdapter = new BrandAdapter();
        mBrandRecycler.setAdapter(mBrandAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(homeScreen);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mBrandRecycler.setLayoutManager(layoutManager);


    }

    private class BrandAdapter extends RecyclerView.Adapter<BrandView> {

        private List<Brand> brandList;

        public BrandAdapter() {
            initializeBrands();
        }

        @NonNull
        @Override
        public BrandView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_view, parent, false);
            return new BrandView(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BrandView holder, int position) {

            holder.setData(brandList.get(position));

        }

        @Override
        public int getItemCount() {
            return brandList.size();
        }

        private void initializeBrands() {

            Brand iPhone = new Brand("iPhone", ContextCompat.getDrawable(homeScreen, R.drawable.iphone));
            Brand Oppo = new Brand("Oppo", ContextCompat.getDrawable(homeScreen, R.drawable.oppo));
            Brand Huawei = new Brand("Huawei", ContextCompat.getDrawable(homeScreen, R.drawable.huawei));
            Brand MI = new Brand("MI", ContextCompat.getDrawable(homeScreen, R.drawable.mi));
            Brand Nokia = new Brand("Nokia", ContextCompat.getDrawable(homeScreen, R.drawable.nokia));
            Brand Samsung = new Brand("Samsung", ContextCompat.getDrawable(homeScreen, R.drawable.samsung));


            brandList = new ArrayList<>();
            brandList.add(iPhone);
            brandList.add(Oppo);
            brandList.add(Huawei);
            brandList.add(MI);
            brandList.add(Nokia);
            brandList.add(Samsung);

        }
    }

    private class BrandView extends RecyclerView.ViewHolder {

        private ImageView brandImage;
        private TextView brandName;

        public BrandView(@NonNull View itemView) {
            super(itemView);

            brandImage = itemView.findViewById(R.id.BrandViewBrandImageShowing);
            brandName = itemView.findViewById(R.id.TVBrandViewBrandNameShowing);
        }

        void setData(Brand brand) {

            brandName.setText(brand.getBrandName());
            brandImage.setImageDrawable(brand.getBrandImage());
        }
    }

}
