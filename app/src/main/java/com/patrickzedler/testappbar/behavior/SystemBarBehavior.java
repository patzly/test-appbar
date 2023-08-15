package com.patrickzedler.testappbar.behavior;

import android.app.Activity;
import android.graphics.Color;
import android.view.ViewGroup;
import android.view.Window;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat.Type;
import com.google.android.material.appbar.AppBarLayout;
import com.patrickzedler.testappbar.util.ResUtil;
import com.patrickzedler.testappbar.util.UiUtil;

public class SystemBarBehavior {

  private final Activity activity;
  private final Window window;

  public SystemBarBehavior(@NonNull Activity activity) {
    this.activity = activity;
    window = activity.getWindow();

    // GOING EDGE TO EDGE
    window.setDecorFitsSystemWindows(false);
  }

  public void setUp(AppBarLayout appBarLayout, @NonNull ViewGroup scrollContent) {
    // TOP INSET
    if (appBarLayout != null) {
      ViewCompat.setOnApplyWindowInsetsListener(appBarLayout, (v, insets) -> {
        // STATUS BAR INSET
        appBarLayout.setPadding(
            0, insets.getInsets(Type.systemBars()).top, 0, appBarLayout.getPaddingBottom()
        );
        return insets;
      });
    }

    // NAV BAR INSET
    ViewCompat.setOnApplyWindowInsetsListener(scrollContent, (v, insets) -> {
      scrollContent.setPadding(
          scrollContent.getPaddingLeft(),
          scrollContent.getPaddingTop(),
          scrollContent.getPaddingRight(),
          scrollContent.getPaddingBottom() + insets.getInsets(Type.systemBars()).bottom
      );
      return insets;
    });

    // SYSTEM BAR COLORS
    boolean isDarkModeActive = UiUtil.isDarkModeActive(activity);
    window.setStatusBarColor(Color.TRANSPARENT);
    if (!isDarkModeActive) {
      UiUtil.setLightStatusBar(window.getDecorView(), true);
    }
    if (UiUtil.isNavigationModeGesture(activity)) {
      window.setNavigationBarColor(Color.TRANSPARENT);
      window.setNavigationBarContrastEnforced(true);
    } else {
      if (!isDarkModeActive) {
        UiUtil.setLightNavigationBar(window.getDecorView(), true);
      }
      window.setNavigationBarColor(
          ResUtil.getColorAttr(activity, android.R.attr.colorBackground, 0.7f) // scrim
      );
    }
  }
}
