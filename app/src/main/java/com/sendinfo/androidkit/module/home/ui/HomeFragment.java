package com.sendinfo.androidkit.module.home.ui;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sendinfo.androidkit.R;
import com.sendinfo.androidkit.base.BaseMVPFragment;
import com.sendinfo.androidkit.bean.LoginVo;
import com.sendinfo.androidkit.module.home.adapter.TestAdapter;
import com.sendinfo.androidkit.mvp.BaseResponse;
import com.sendinfo.androidkit.mvp.CommonResponseP;
import com.sendinfo.androidkit.mvp.ICommonResponseView;
import com.sendinfo.androidkit.widget.refreshHeader.LottiRefreshHeader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * <pre>
 *     author : ghwang
 *     e-mail : 429329513@qq.com
 *     time   : 2019/04/03
 *     desc   :
 * </pre>
 */

public class HomeFragment extends BaseMVPFragment<CommonResponseP> implements ICommonResponseView {

    @BindView(R.id.sr_refresh)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    List<LoginVo>datas = new ArrayList<>();
    private TestAdapter adapter;

    @Override
    protected void initArgs(Bundle bundle) {

        for (int i=0;i<20;i++){

            LoginVo vo = new LoginVo();
            vo.setUser("测试"+i);
            datas.add(vo);
        }
    }

    @Override
    protected void initView(Bundle bundle) {

        setContentView(R.layout.fragment_home);
        mPresenter = new CommonResponseP(this);
        initRecyc();
    }

    @Override
    protected void initData() {


    }

    private void initRecyc(){

        adapter = new TestAdapter(R.layout.cell_item_layout,datas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        //adapter.bindToRecyclerView(recyclerView);

        refreshLayout.setRefreshHeader(new LottiRefreshHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        refreshLayout.finishRefresh();
                    }
                },2000);

            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                refreshLayout.finishLoadMore(2000);

            }
        });


    }
    @Override
    public void Success(BaseResponse response) {


    }

    @Override
    public void onHiddenChanged(boolean hidden) {

        if (hidden){

            LogUtils.d("fragment hided");

        }else {

            LogUtils.d("fragment showed");

        }
        super.onHiddenChanged(hidden);
    }
}
