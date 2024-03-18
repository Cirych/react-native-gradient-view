package com.gradientview;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.facebook.react.bridge.ColorPropConverter;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.text.NumberFormat;
import java.text.ParseException;

public class GradientViewViewManager extends SimpleViewManager<View> {
  public static final String REACT_CLASS = "GradientViewView";

  private GradientDrawable gradient = new GradientDrawable();
  private GradientDrawable.Orientation[] orientations = GradientDrawable.Orientation.values();
  private NumberFormat numberFormat = NumberFormat.getPercentInstance();

  @Override
  @NonNull
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  @NonNull
  public View createViewInstance(ThemedReactContext reactContext) {
    return new View(reactContext);
  }

@ReactProp(name = "colors", customType = "ColorArray")
  public void setColors(View view, @Nullable ReadableArray colors) {
    if (colors != null && colors.size() > 1) {
      int[] colorValues = new int[colors.size()];
      for (int i = 0; i < colors.size(); i++) {
        if (colors.getType(i) == ReadableType.Map) {
          colorValues[i] = ColorPropConverter.getColor(colors.getMap(i), view.getContext());
        } else {
          colorValues[i] = colors.getInt(i);
        }
      }
      gradient.setColors(colorValues);
      view.setBackground(gradient);
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.Q)
  @ReactProp(name = "locations")
  public void setLocations(View view, @Nullable ReadableArray locations) {
    int[] colors = gradient.getColors();
    if (locations != null) {
      int locationsLastIndex = locations.size() - 1;
      float[] floatLocations = new float[colors.length];
      for (int i = 0; i < colors.length; i++) {
        if (i < locationsLastIndex) {
          floatLocations[i] = (float) locations.getDouble(i);
        } else {
          floatLocations[i] = 1.0f;
        }
      }
      gradient.setColors(colors, floatLocations);
    } else {
      gradient.setColors(colors, null);
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.Q)
  @ReactProp(name = "colorList")
  public void setColorList(View view, @Nullable ReadableArray colorList) throws ParseException {
    if (colorList != null) {
      int colorListSize = colorList.size();
      int[] colors = new int[colorListSize];
      float[] locations = new float[colorListSize];
      for (int i = 0; i < colorListSize; i++) {
        ReadableMap pair = colorList.getMap(i);
        if (pair.getType("color") == ReadableType.Map) {
          colors[i] = ColorPropConverter.getColor(pair.getMap("color"), view.getContext());
        } else {
          colors[i] = pair.getInt("color");
        }
        Number offset = numberFormat.parse(pair.getString("offset"));
        locations[i] = offset.floatValue();
      }
      gradient.setColors(colors, locations);
    }
  }

  @ReactProp(name = "angle", defaultFloat = 0f)
  public void setAngle(View view, float angle) {
    int fixedAngle = ((Math.round(angle/45) * 45) % 360) / 45;
    gradient.setOrientation(orientations[fixedAngle]);
  }
}
