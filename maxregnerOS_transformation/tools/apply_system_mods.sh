#!/system/bin/sh

# maxregnerOS System Modifications Script
# This script applies core system modifications to transform One UI 8.0 to maxregnerOS

echo "Starting maxregnerOS system modifications..."

# Backup original files
cp /system/framework/framework-res.apk /data/maxregner_backup/
cp /system/framework/services.jar /data/maxregner_backup/
cp /system/build.prop /data/maxregner_backup/

# Modify SystemUI for maxregnerOS branding
echo "Modifying SystemUI..."
if [ -f /system/priv-app/SystemUI/SystemUI.apk ]; then
    # Extract SystemUI APK
    mkdir -p /tmp/systemui_mod
    unzip -q /system/priv-app/SystemUI/SystemUI.apk -d /tmp/systemui_mod/
    
    # Modify status bar layout
    sed -i 's/One UI/maxregnerOS/g' /tmp/systemui_mod/res/values*/strings.xml 2>/dev/null
    sed -i 's/Samsung/maxregner/g' /tmp/systemui_mod/res/values*/strings.xml 2>/dev/null
    
    # Modify notification panel
    if [ -f /tmp/systemui_mod/res/layout/status_bar_expanded_header.xml ]; then
        sed -i 's/#1976D2/#FF6B35/g' /tmp/systemui_mod/res/layout/status_bar_expanded_header.xml
        sed -i 's/#2196F3/#FF8C42/g' /tmp/systemui_mod/res/layout/status_bar_expanded_header.xml
    fi
    
    # Repackage SystemUI
    cd /tmp/systemui_mod
    zip -r -q /system/priv-app/SystemUI/SystemUI.apk .
    cd /
    rm -rf /tmp/systemui_mod
fi

# Modify Settings app
echo "Modifying Settings app..."
if [ -f /system/priv-app/Settings/Settings.apk ]; then
    mkdir -p /tmp/settings_mod
    unzip -q /system/priv-app/Settings/Settings.apk -d /tmp/settings_mod/
    
    # Add maxregnerOS branding
    sed -i 's/One UI/maxregnerOS/g' /tmp/settings_mod/res/values*/strings.xml 2>/dev/null
    sed -i 's/Samsung/maxregner/g' /tmp/settings_mod/res/values*/strings.xml 2>/dev/null
    
    # Add custom settings categories
    if [ -f /tmp/settings_mod/res/xml/dashboard_categories.xml ]; then
        sed -i '/<dashboard-categories/a\    <dashboard-tile android:id="@+id/maxregner_control_center" android:title="maxregnerOS Control Center" android:summary="Advanced system controls and customization" android:icon="@drawable/ic_maxregner_control" />' /tmp/settings_mod/res/xml/dashboard_categories.xml
    fi
    
    # Repackage Settings
    cd /tmp/settings_mod
    zip -r -q /system/priv-app/Settings/Settings.apk .
    cd /
    rm -rf /tmp/settings_mod
fi

# Modify framework-res.apk for system-wide changes
echo "Modifying framework resources..."
if [ -f /system/framework/framework-res.apk ]; then
    mkdir -p /tmp/framework_mod
    unzip -q /system/framework/framework-res.apk -d /tmp/framework_mod/
    
    # Change system colors to maxregnerOS theme
    if [ -f /tmp/framework_mod/res/values/colors.xml ]; then
        sed -i 's/#1976D2/#FF6B35/g' /tmp/framework_mod/res/values/colors.xml
        sed -i 's/#2196F3/#FF8C42/g' /tmp/framework_mod/res/values/colors.xml
        sed -i 's/#3F51B5/#FF6B35/g' /tmp/framework_mod/res/values/colors.xml
    fi
    
    # Modify system strings
    sed -i 's/Android/maxregnerOS/g' /tmp/framework_mod/res/values*/strings.xml 2>/dev/null
    sed -i 's/One UI/maxregnerOS/g' /tmp/framework_mod/res/values*/strings.xml 2>/dev/null
    
    # Repackage framework-res
    cd /tmp/framework_mod
    zip -r -q /system/framework/framework-res.apk .
    cd /
    rm -rf /tmp/framework_mod
fi

# Enable advanced features in build.prop
echo "Enabling maxregnerOS features..."
cat >> /system/build.prop << 'EOF'

# maxregnerOS Features
ro.maxregner.version=1.0
ro.maxregner.build.date=$(date)
ro.maxregner.features.enabled=true

# Enhanced Performance
ro.config.max_starting_bg=8
ro.sys.fw.bg_apps_limit=24
ro.config.dha_cached_max=16
ro.config.dha_empty_max=24

# Advanced UI Features
ro.surface_flinger.max_frame_buffer_acquired_buffers=3
ro.surface_flinger.running_without_sync_framework=true
debug.sf.enable_hwc_vds=1

# Custom Audio Enhancements
ro.audio.flinger_standbytime_ms=300
ro.audio.offload_wakelock=false
audio.offload.gapless.enabled=true

# maxregnerOS Branding
ro.product.brand=maxregner
ro.product.manufacturer=maxregner
ro.product.model=maxregnerOS Device
ro.build.display.id=maxregnerOS 1.0
EOF

echo "System modifications completed!"

