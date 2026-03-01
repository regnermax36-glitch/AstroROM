#!/system/bin/sh

# maxregnerOS UI Transformation Script
# This script transforms the One UI 8.0 interface to maxregnerOS style

echo "Starting maxregnerOS UI transformation..."

# Create custom overlay directory
mkdir -p /system/vendor/overlay/maxregner

# Transform status bar
echo "Transforming status bar..."
cat > /system/vendor/overlay/maxregner/StatusBarOverlay.xml << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- maxregnerOS Status Bar Colors -->
    <color name="status_bar_background">#FF1A1A1A</color>
    <color name="notification_panel_solid_background_color">#FF2D2D2D</color>
    <color name="system_accent_color">#FFFF6B35</color>
    <color name="system_neutral1_900">#FF1A1A1A</color>
    
    <!-- Custom maxregnerOS Branding -->
    <string name="status_bar_settings_settings_button">maxregnerOS Settings</string>
    <string name="quick_settings_header_onboarding_text">Customize your maxregnerOS experience</string>
</resources>
EOF

# Transform notification panel
echo "Transforming notification panel..."
cat > /system/vendor/overlay/maxregner/NotificationOverlay.xml << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- maxregnerOS Notification Colors -->
    <color name="notification_material_background_color">#FF2D2D2D</color>
    <color name="notification_default_color">#FFFF6B35</color>
    <color name="notification_action_color_filter">#FFFF8C42</color>
    
    <!-- Custom notification styles -->
    <dimen name="notification_panel_width">match_parent</dimen>
    <dimen name="notification_side_paddings">16dp</dimen>
</resources>
EOF

# Transform quick settings
echo "Transforming quick settings..."
cat > /system/vendor/overlay/maxregner/QuickSettingsOverlay.xml << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- maxregnerOS Quick Settings -->
    <color name="qs_tile_background_color">#FF3D3D3D</color>
    <color name="qs_tile_icon_enabled_color">#FFFF6B35</color>
    <color name="qs_tile_icon_disabled_color">#FF666666</color>
    <color name="qs_tile_text_color">#FFFFFFFF</color>
    
    <!-- Custom tile layout -->
    <integer name="quick_settings_num_columns">4</integer>
    <dimen name="qs_tile_height">88dp</dimen>
</resources>
EOF

# Transform settings UI
echo "Transforming settings interface..."
if [ -d /system/priv-app/Settings ]; then
    # Create custom settings overlay
    cat > /system/vendor/overlay/maxregner/SettingsOverlay.xml << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- maxregnerOS Settings Colors -->
    <color name="settings_background_color">#FF1A1A1A</color>
    <color name="cardview_default_background_color">#FF2D2D2D</color>
    <color name="accent_material_dark">#FFFF6B35</color>
    <color name="primary_material_dark">#FFFF8C42</color>
    
    <!-- Custom settings strings -->
    <string name="settings_label">maxregnerOS Settings</string>
    <string name="about_settings">About maxregnerOS</string>
</resources>
EOF
fi

# Transform lock screen
echo "Transforming lock screen..."
cat > /system/vendor/overlay/maxregner/LockScreenOverlay.xml << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- maxregnerOS Lock Screen -->
    <color name="keyguard_background_color">#FF000000</color>
    <color name="keyguard_indication_text_color">#FFFF6B35</color>
    <color name="lock_screen_clock_color">#FFFFFFFF</color>
    
    <!-- Custom lock screen text -->
    <string name="keyguard_indication_charging_time">Charging on maxregnerOS</string>
    <string name="keyguard_plugged_in">maxregnerOS is charging</string>
</resources>
EOF

# Apply custom boot animation
echo "Installing maxregnerOS boot animation..."
if [ -f /system/media/bootanimation.zip ]; then
    cp /system/media/bootanimation.zip /data/maxregner_backup/
fi

# Create custom boot animation
mkdir -p /tmp/bootanim/{part0,part1}

# Create boot animation descriptor
cat > /tmp/bootanim/desc.txt << 'EOF'
1080 1920 30
p 1 0 part0
p 0 0 part1
EOF

# Create simple maxregnerOS boot frames (text-based for demo)
for i in {00..29}; do
    cat > /tmp/bootanim/part0/${i}.txt << 'EOF'
maxregnerOS
Loading...
EOF
done

for i in {00..59}; do
    cat > /tmp/bootanim/part1/${i}.txt << 'EOF'
maxregnerOS
Welcome
EOF
done

# Package boot animation
cd /tmp/bootanim
zip -r0 /system/media/bootanimation.zip .
cd /

# Transform recent apps interface
echo "Transforming recent apps..."
cat > /system/vendor/overlay/maxregner/RecentAppsOverlay.xml << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- maxregnerOS Recent Apps -->
    <color name="recents_task_view_background_color">#FF2D2D2D</color>
    <color name="recents_task_view_header_background_color">#FF3D3D3D</color>
    <color name="recents_task_view_header_text_color">#FFFFFFFF</color>
    
    <!-- Custom recent apps layout -->
    <dimen name="recents_task_view_rounded_corners_radius">16dp</dimen>
</resources>
EOF

# Apply navigation bar customizations
echo "Customizing navigation bar..."
cat > /system/vendor/overlay/maxregner/NavigationBarOverlay.xml << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- maxregnerOS Navigation Bar -->
    <color name="navigation_bar_background_color">#FF1A1A1A</color>
    <color name="navigation_bar_icon_color">#FFFF6B35</color>
    
    <!-- Custom navigation gestures -->
    <bool name="config_navBarNeedsScrim">false</bool>
    <dimen name="navigation_bar_height">48dp</dimen>
</resources>
EOF

# Create maxregnerOS system fonts
echo "Installing maxregnerOS fonts..."
if [ -d /system/fonts ]; then
    # Backup original fonts
    cp -r /system/fonts /data/maxregner_backup/
    
    # Create custom font configuration
    cat > /system/etc/fonts.xml << 'EOF'
<?xml version="1.0" encoding="utf-8"?>
<familyset version="22">
    <!-- maxregnerOS System Font -->
    <family name="sans-serif">
        <font weight="100" style="normal">Roboto-Thin.ttf</font>
        <font weight="100" style="italic">Roboto-ThinItalic.ttf</font>
        <font weight="300" style="normal">Roboto-Light.ttf</font>
        <font weight="300" style="italic">Roboto-LightItalic.ttf</font>
        <font weight="400" style="normal">Roboto-Regular.ttf</font>
        <font weight="400" style="italic">Roboto-Italic.ttf</font>
        <font weight="500" style="normal">Roboto-Medium.ttf</font>
        <font weight="500" style="italic">Roboto-MediumItalic.ttf</font>
        <font weight="700" style="normal">Roboto-Bold.ttf</font>
        <font weight="700" style="italic">Roboto-BoldItalic.ttf</font>
        <font weight="900" style="normal">Roboto-Black.ttf</font>
        <font weight="900" style="italic">Roboto-BlackItalic.ttf</font>
    </family>
</familyset>
EOF
fi

# Apply all overlays
echo "Applying maxregnerOS overlays..."
if [ -d /system/vendor/overlay ]; then
    # Set proper permissions for overlays
    chmod 644 /system/vendor/overlay/maxregner/*.xml
    chown root:root /system/vendor/overlay/maxregner/*.xml
fi

# Cleanup temporary files
rm -rf /tmp/bootanim

echo "UI transformation completed!"

