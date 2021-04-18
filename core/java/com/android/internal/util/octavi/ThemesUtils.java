/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.internal.util.octavi;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.ContentResolver;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import com.android.internal.util.octavi.clock.ClockFace;
import android.net.Uri;
import android.content.om.IOverlayManager;
import android.content.om.OverlayInfo;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ThemesUtils {

    public static final String TAG = "ThemesUtils";

    private Context mContext;

     public ThemesUtils(Context context) {
        mContext = context;
    }

    // Switch themes
    private static final String[] SWITCH_THEMES = {
        "com.android.system.switch.stock", // 0
        "com.android.system.switch.oneplus", // 1
	"com.android.system.switch.narrow", // 2
        "com.android.system.switch.contained", // 3
        "com.android.system.switch.md2", // 4
        "com.android.system.switch.retro", // 5
        "com.android.system.switch.telegram", // 6
        "com.android.system.switch.stockish", //7
    };

    public static final String[] STATUSBAR_HEIGHT = {
            "com.gnonymous.gvisualmod.sbh_m", // 1
            "com.gnonymous.gvisualmod.sbh_l", // 2
            "com.gnonymous.gvisualmod.sbh_xl", // 3
    };

    public static final String[] UI_RADIUS = {
            "com.gnonymous.gvisualmod.urm_r", // 1
            "com.gnonymous.gvisualmod.urm_m", // 2
            "com.gnonymous.gvisualmod.urm_l", // 3

    public static final String[] BRIGHTNESS_SLIDER_THEMES = {
            "com.android.systemui.brightness.slider.memeroundstroke",
            "com.android.systemui.brightness.slider.oos",
    };

    public static final String[] NAVBAR_THEMES = {
            "com.android.system.navbar.asus",
            "com.android.system.navbar.oneplus",
	    "com.android.system.navbar.oneui",
	    "com.android.system.navbar.tecno",
    };

    public static final String NAVBAR_COLOR_PURP = "com.gnonymous.gvisualmod.pgm_purp";

    public static final String NAVBAR_COLOR_ORCD = "com.gnonymous.gvisualmod.pgm_orcd";

    public static final String NAVBAR_COLOR_OPRD = "com.gnonymous.gvisualmod.pgm_oprd";

    public static final String NAVBAR_COLOR_BLUE = "com.gnonymous.gvisualmod.pgm_blue";

    public static final String NAVBAR_COLOR_ROSE = "com.gnonymous.gvisualmod.pgm_rose";

    public static void updateSwitchStyle(IOverlayManager om, int userId, int switchStyle) {
        if (switchStyle == 0) {
            stockSwitchStyle(om, userId);
        } else {
            try {
                om.setEnabled(SWITCH_THEMES[switchStyle],
                        true, userId);
            } catch (RemoteException e) {
                Log.w(TAG, "Can't change switch theme", e);
            }
        }
    }

    public static void stockSwitchStyle(IOverlayManager om, int userId) {
        for (int i = 0; i < SWITCH_THEMES.length; i++) {
            String switchtheme = SWITCH_THEMES[i];
            try {
                om.setEnabled(switchtheme,
                        false /*disable*/, userId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public List<ClockFace> getClocks() {
        ProviderInfo providerInfo = mContext.getPackageManager().resolveContentProvider("com.android.keyguard.clock",
                        PackageManager.MATCH_SYSTEM_ONLY);

        Uri optionsUri = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_CONTENT)
                .authority(providerInfo.authority)
                .appendPath("list_options")
                .build();

        ContentResolver resolver = mContext.getContentResolver();
        List<ClockFace> clocks = new ArrayList<>();
        try (Cursor c = resolver.query(optionsUri, null, null, null, null)) {
            while(c.moveToNext()) {
                String id = c.getString(c.getColumnIndex("id"));
                String title = c.getString(c.getColumnIndex("title"));
                String previewUri = c.getString(c.getColumnIndex("preview"));
                Uri preview = Uri.parse(previewUri);
                String thumbnailUri = c.getString(c.getColumnIndex("thumbnail"));
                Uri thumbnail = Uri.parse(thumbnailUri);

                ClockFace.Builder builder = new ClockFace.Builder();
                builder.setId(id).setTitle(title).setPreview(preview).setThumbnail(thumbnail);
                clocks.add(builder.build());
            }
        } catch (Exception e) {
            clocks = null;
        } finally {
            // Do Nothing
        }
        return clocks;
      }

    public static void stockBrightnessSliderStyle(IOverlayManager om, int userId) {
        for (int i = 0; i < BRIGHTNESS_SLIDER_THEMES.length; i++) {
            String brightnessSlidertheme = BRIGHTNESS_SLIDER_THEMES[i];
            try {
                om.setEnabled(brightnessSlidertheme,
                        false /*disable*/, userId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateBrightnessSliderStyle(IOverlayManager om, int userId, int brightnessSliderStyle) {
        if (brightnessSliderStyle == 0) {
            stockBrightnessSliderStyle(om, userId);
        } else {
            try {
                om.setEnabled(BRIGHTNESS_SLIDER_THEMES[brightnessSliderStyle],
                        true, userId);
            } catch (RemoteException e) {
                Log.w(TAG, "Can't change brightness slider theme", e);
            }
        }
    }

    public static void stockNavBarStyle(IOverlayManager om, int userId) {
        for (int i = 0; i < NAVBAR_THEMES.length; i++) {
            String NavBartheme = NAVBAR_THEMES[i];
            try {
                om.setEnabled(NavBartheme,
                        false /*disable*/, userId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateNavBarStyle(IOverlayManager om, int userId, int NavBarStyle) {
        if (NavBarStyle == 0) {
            stockNavBarStyle(om, userId);
        } else {
            try {
                om.setEnabled(NAVBAR_THEMES[NavBarStyle],
                        true, userId);
            } catch (RemoteException e) {
                Log.w(TAG, "Can't change Navbar theme", e);
            }
        }
    }
}
