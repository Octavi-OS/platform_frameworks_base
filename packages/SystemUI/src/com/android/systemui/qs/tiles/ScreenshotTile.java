/*
 * Copyright (C) 2017 ABC rom
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

package com.android.systemui.qs.tiles;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.quicksettings.Tile;
import android.view.View;

import androidx.annotation.Nullable;

import com.android.internal.logging.MetricsLogger;

import com.android.internal.util.octavi.OctaviUtils;
import com.android.internal.logging.nano.MetricsProto.MetricsEvent;
import com.android.systemui.qs.QSHost;
import com.android.systemui.plugins.qs.QSTile.BooleanState;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.R;
import com.android.systemui.dagger.qualifiers.Background;
import com.android.systemui.dagger.qualifiers.Main;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.logging.QSLogger;

import javax.inject.Inject;

/** Quick settings tile: Screenshot **/
public class ScreenshotTile extends QSTileImpl<BooleanState> {

    private boolean mRegion = false;

    @Inject
    public ScreenshotTile(
            QSHost host,
            @Background Looper backgroundLooper,
            @Main Handler mainHandler,
            FalsingManager falsingManager,
            MetricsLogger metricsLogger,
            StatusBarStateController statusBarStateController,
            ActivityStarter activityStarter,
            QSLogger qsLogger
    ) {
        super(host, backgroundLooper, mainHandler, falsingManager, metricsLogger,
                statusBarStateController, activityStarter, qsLogger);
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.OCTAVI;
    }

    @Override
    public BooleanState newTileState() {
        return new BooleanState();
    }

    @Override
    public void handleSetListening(boolean listening) {
    }

    @Override
    protected void handleClick(@Nullable View view) {
        mRegion = !mRegion;
        refreshState();
    }

    @Override
    public void handleLongClick(@Nullable View view) {
        mHost.collapsePanels();

        //finish collapsing the panel
        try {
             Thread.sleep(1000); //1s
        } catch (InterruptedException ie) {}
        OctaviUtils.takeScreenshot(mRegion ? false : true);
    }

    @Override
    public Intent getLongClickIntent() {
        return null;
    }

    @Override
    public CharSequence getTileLabel() {
        return mContext.getString(R.string.quick_settings_screenshot_label);
    }

    @Override
    protected void handleUpdateState(BooleanState state, Object arg) {
        state.label = mContext.getString(R.string.quick_settings_screenshot_label);
        state.icon = ResourceIcon.get(R.drawable.ic_qs_screenshot);
        state.state = Tile.STATE_INACTIVE;
        if (mRegion) {
            state.secondaryLabel = mContext.getString(R.string.quick_settings_region_screenshot_label);
            state.contentDescription = mContext.getString(
                    R.string.quick_settings_region_screenshot_label);
        } else {
            state.secondaryLabel = mContext.getString(R.string.quick_settings_full_screenshot_label);
            state.contentDescription = mContext.getString(
                    R.string.quick_settings_full_screenshot_label);
        }
    }
}
