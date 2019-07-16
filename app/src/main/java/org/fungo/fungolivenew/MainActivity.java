package org.fungo.fungolivenew;

import android.arch.lifecycle.LifecycleOwner;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.fungo.common_core.utils.Logger;
import org.fungo.common_db.DbUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.Predicate;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        long start = System.currentTimeMillis();
        String value = DbUtils.getInstance().getString("test");
        Logger.e("yqy " + (System.currentTimeMillis() - start));
        DbUtils.getInstance().putString("test", "testetsetestttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt");
    }
}
