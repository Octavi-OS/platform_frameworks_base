package com.octavi.android.systemui.dagger;

import com.android.systemui.dagger.DefaultComponentBinder;
import com.android.systemui.dagger.DependencyProvider;
import com.android.systemui.dagger.SysUISingleton;
import com.android.systemui.dagger.SystemUIBinder;
import com.android.systemui.dagger.SysUIComponent;
import com.android.systemui.dagger.SystemUIModule;

import com.octavi.android.systemui.columbus.ColumbusModule;
import com.octavi.android.systemui.gamedashboard.GameDashboardModule;
import com.octavi.android.systemui.keyguard.KeyguardSliceProviderOctavi;
import com.octavi.android.systemui.smartspace.KeyguardSmartspaceController;

import dagger.Subcomponent;

@SysUISingleton
@Subcomponent(modules = {
        ColumbusModule.class,
        DefaultComponentBinder.class,
        DependencyProvider.class,
        GameDashboardModule.class,
        SystemUIModule.class,
        SystemUIOctaviBinder.class,
        SystemUIOctaviModule.class})
public interface SysUIComponentOctavi extends SysUIComponent {
    @SysUISingleton
    @Subcomponent.Builder
    interface Builder extends SysUIComponent.Builder {
        SysUIComponentOctavi build();
    }

    /**
     * Member injection into the supplied argument.
     */
    void inject(KeyguardSliceProviderOctavi keyguardSliceProviderOctavi);

    @SysUISingleton
    KeyguardSmartspaceController createKeyguardSmartspaceController();
}
