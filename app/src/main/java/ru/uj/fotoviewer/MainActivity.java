package ru.uj.fotoviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import ru.uj.fotoviewer.Adapters.FotoViewerListAdapter;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, IFotoView {

    private FloatingActionButton fab;
    private RecyclerView mRecyclerView;
    private FotoViewerListAdapter mAdapter;
    private IFotoPresenter mPresenter;
    public static final int FOTO_CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            mPresenter = new FotoPresenter();
        } else {
            mPresenter = PresenterHolder.getInstance().restorePresenter(savedInstanceState);
        }
        setContentView(R.layout.main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.rvFotos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new FotoViewerListAdapter(new ArrayList<Foto>(), this, mPresenter);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Intent intent = new Intent(this, Main2Activity.class);
                startActivityForResult(intent, FOTO_CAMERA);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FOTO_CAMERA){
            if (resultCode == RESULT_OK){
                Foto foto = (Foto) data.getExtras().get("data");
                mPresenter.addFoto(foto);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.bindView(this);
        mPresenter.getFotoList();
    }

    @Override
    protected void onPause() {
        mPresenter.unbindView();
        super.onPause();
    }

    @Override
    public void onGetFotos(ArrayList<Foto> fotos) {
        mAdapter.setFoto(fotos);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterHolder.getInstance().savePresenter((BasePresenter<?>) mPresenter, outState);
    }

}
