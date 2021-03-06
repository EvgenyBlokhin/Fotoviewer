package ru.uj.fotoviewer.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.uj.fotoviewer.Foto;
import ru.uj.fotoviewer.IFotoPresenter;
import ru.uj.fotoviewer.R;

/**
 * Created by Blokhin Evgeny on 23.10.2017.
 */

public class FotoViewerListAdapter extends RecyclerView.Adapter<FotoViewerListAdapter.SingleItemRowHolder> {
    private ArrayList<Foto> fotos;
    private Context mContext;
    private IFotoPresenter mPresenter;

    public FotoViewerListAdapter(ArrayList<Foto> fotos, Context context, IFotoPresenter presenter) {
        this.fotos = fotos;
        this.mContext = context;
        this.mPresenter = presenter;
    }

    public void setFoto(ArrayList<Foto> fotos) {
        this.fotos = fotos;
        notifyDataSetChanged();
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_main, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);
        SingleItemRowHolder singleItemRowHolder = new SingleItemRowHolder(v);
        return singleItemRowHolder;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int position) {
        Foto foto = fotos.get(position);
        holder.tvFotoName.setText(foto.getName());
        Picasso.with(mContext).load("hgfdjdf").fit().into(holder.ivFotoImage);
    }

    @Override
    public int getItemCount() {
        return fotos != null ? fotos.size() : 0;
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected ImageView ivFotoImage;
        protected TextView tvFotoName;

        public SingleItemRowHolder(View view) {
            super(view);
            this.tvFotoName = view.findViewById(R.id.fotoName);
            this.ivFotoImage = view.findViewById(R.id.imageView);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.openFotoView(fotos.get(getAdapterPosition()));
                }
            });
        }
    }
}
