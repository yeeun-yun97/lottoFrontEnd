package com.github.yeeun_yun97.main.view;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.github.yeeun_yun97.R;
import com.github.yeeun_yun97.main.view.component.SimpleDialog;
import com.github.yeeun_yun97.main.view.domain.user.FindPasswordFragment;
import com.github.yeeun_yun97.main.view.domain.home.HomeFragment;
import com.github.yeeun_yun97.main.view.domain.user.register.RegisterFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.github.yeeun_yun97.main.model.constant.Constant.SYSTEM_OFF_DIALOG_MESSAGE;
import static com.github.yeeun_yun97.main.model.constant.Constant.SYSTEM_OFF_DIALOG_TITLE;

public class MainActivity extends AppCompatActivity {

    private AlertDialog dialog;

    private BottomNavigationView bottomNavigationView;
    private int navHostFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.bottomNavigationView = findViewById(R.id.mainActivity_bottomNavigationView);
        this.navHostFragment = R.id.mainActivity_navHostFragment;

        this.initBottomNavigation();
    }

    @Override
    public void onBackPressed() {
        if (this.getFragment(HomeFragment.class)) this.showSystemOffCheckDialog();
        else if (this.getFragment(FindPasswordFragment.class) || this.getFragment(RegisterFragment.class))
            super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        this.dialog.dismiss();
        super.onDestroy();
    }

    /*
    method
     */
    private void initBottomNavigation(){
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(this.navHostFragment);
        NavController controller = navHostFragment.getNavController();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottomMenu_home:
                    controller.navigate(R.id.action_global_homeFragment);
                    break;
                case R.id.bottomMenu_save:
                    controller.navigate(R.id.action_global_notLoginedFragment);
                    break;
                case R.id.bottomMenu_pick:
                    controller.navigate(R.id.action_global_predictFragment);
                    break;
            }
            return true;
        });
    }

    private boolean getFragment(Class className) {
        NavHostFragment navHostFragment = (NavHostFragment) this.getSupportFragmentManager().getFragments().get(0);
        for (Fragment fragment : navHostFragment.getChildFragmentManager().getFragments()) {
            Log.d("디버그", fragment.getClass().getSimpleName());
            if (className.isAssignableFrom(fragment.getClass())) {
                return true;
            }
        }
        return false;
    }

    public void showSystemOffCheckDialog() {
        this.dialog = SimpleDialog.getDialog(
                this,
                SYSTEM_OFF_DIALOG_TITLE,
                SYSTEM_OFF_DIALOG_MESSAGE,
                (dialog, which) -> {
                    this.finish();
                    this.finishAffinity();
                }
        );
        this.dialog.show();
    }
}