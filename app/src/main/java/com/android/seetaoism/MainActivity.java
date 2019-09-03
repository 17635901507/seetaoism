package com.android.seetaoism;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.seetaoism.adapter.RlvNvAdapter;
import com.android.seetaoism.adapter.VpFragmentAdapter;
import com.android.seetaoism.basef.Constants;
import com.android.seetaoism.basef.BaseActivity;
import com.android.seetaoism.entity.NvBean;
import com.android.seetaoism.fragment.HomeFragment;
import com.android.seetaoism.fragment.PersonalFragment;
import com.android.seetaoism.fragment.SpecialFragment;
import com.android.seetaoism.fragment.VideoFragment;
import com.android.seetaoism.utils.SystemFacade;
import com.android.seetaoism.weight.CustomScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.vp)
    CustomScrollViewPager mVp;
    @BindView(R.id.tab)
    TabLayout mTab;
    boolean scrollable;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        EventBus.getDefault().register(this);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        int headerCount = navigationView.getHeaderCount();
        View view = navigationView.getHeaderView(0);
        RecyclerView rv = view.findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(this,2));
        ArrayList<NvBean> nvBeans = new ArrayList<>();
        nvBeans.add(new NvBean("推荐", SystemFacade.randomColor()));
        nvBeans.add(new NvBean("战略", SystemFacade.randomColor()));
        nvBeans.add(new NvBean("一带一路", SystemFacade.randomColor()));
        nvBeans.add(new NvBean("工程", SystemFacade.randomColor()));
        nvBeans.add(new NvBean("社评", SystemFacade.randomColor()));
        nvBeans.add(new NvBean("特写", SystemFacade.randomColor()));
        nvBeans.add(new NvBean("机械", SystemFacade.randomColor()));
        nvBeans.add(new NvBean("即时", SystemFacade.randomColor()));
        nvBeans.add(new NvBean("传承", SystemFacade.randomColor()));
        RlvNvAdapter adapter = new RlvNvAdapter(this, nvBeans);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new VideoFragment());
        fragments.add(new SpecialFragment());
        fragments.add(new PersonalFragment());
        mTab.addTab(mTab.newTab().setText(R.string.home).setIcon(R.drawable.a));
        mTab.addTab(mTab.newTab().setText(R.string.video).setIcon(R.drawable.b));
        mTab.addTab(mTab.newTab().setText(R.string.special).setIcon(R.drawable.c));
        mTab.addTab(mTab.newTab().setText(R.string.personal).setIcon(R.drawable.d));
        VpFragmentAdapter vpFragmentAdapter = new VpFragmentAdapter(getSupportFragmentManager(), fragments);
        mVp.setAdapter(vpFragmentAdapter);

        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mVp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mVp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTab));



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {
            mVp.setScrollable(!scrollable);
            this.scrollable = !scrollable;
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void getNvIsOpen(String isOpen){
        if(isOpen == Constants.OPEN){
            drawer.openDrawer(Gravity.LEFT);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
