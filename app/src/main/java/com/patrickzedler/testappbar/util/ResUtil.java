package com.patrickzedler.testappbar.util;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import androidx.core.graphics.ColorUtils;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResUtil {

  private final static String TAG = ResUtil.class.getSimpleName();

  @NonNull
  public static String getRawText(Context context, @RawRes int resId) {
    InputStream inputStream = context.getResources().openRawResource(resId);
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    StringBuilder text = new StringBuilder();
    try {
      for (String line; (line = bufferedReader.readLine()) != null; ) {
        text.append(line).append('\n');
      }
      text.deleteCharAt(text.length() - 1);
      inputStream.close();
    } catch (Exception e) {
      Log.e(TAG, "getRawText", e);
    }
    return text.toString();
  }

  public static int getColorAttr(Context context, @AttrRes int attr) {
    TypedValue typedValue = new TypedValue();
    context.getTheme().resolveAttribute(attr, typedValue, true);
    return typedValue.data;
  }

  public static int getColorAttr(Context context, @AttrRes int resId, float alpha) {
    return ColorUtils.setAlphaComponent(getColorAttr(context, resId), (int) (alpha * 255));
  }
}
