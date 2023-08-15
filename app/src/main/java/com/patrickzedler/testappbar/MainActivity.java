package com.patrickzedler.testappbar;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.material.color.DynamicColors;
import com.patrickzedler.testappbar.behavior.SystemBarBehavior;
import com.patrickzedler.testappbar.databinding.ActivityMainBinding;
import com.patrickzedler.testappbar.util.ResUtil;

public class MainActivity extends AppCompatActivity {

  ActivityMainBinding binding;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    DynamicColors.applyToActivityIfAvailable(this);

    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    new SystemBarBehavior(this).setUp(binding.appBar, binding.container);

    binding.appBar.setLiftOnScroll(true);

    binding.toggleLiftOnScroll.setChecked(true);
    binding.toggleLiftOnScroll.setOnCheckedChangeListener((buttonView, isChecked) -> {
      binding.toggleLifted.setEnabled(!isChecked);
      binding.appBar.setLiftOnScroll(isChecked);
      if (!isChecked) {
        binding.appBar.setLifted(binding.toggleLifted.isChecked());
      }
    });

    binding.toggleLifted.setChecked(true);
    binding.toggleLifted.setEnabled(false);
    binding.toggleLifted.setOnCheckedChangeListener(
        (buttonView, isChecked) -> binding.appBar.setLifted(isChecked)
    );

    binding.text.setText(ResUtil.getRawText(this, R.raw.lorem_ipsum));
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    binding = null;
  }
}
