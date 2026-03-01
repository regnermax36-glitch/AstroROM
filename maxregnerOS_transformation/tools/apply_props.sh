#!/system/bin/sh

# maxregnerOS Build Properties Script
# This script applies maxregnerOS-specific build properties and system configurations

echo "Applying maxregnerOS build properties..."

# Backup original build.prop
cp /system/build.prop /data/maxregner_backup/build.prop.bak

# Apply maxregnerOS branding and identification
echo "Applying maxregnerOS branding..."
cat >> /system/build.prop << 'EOF'

# ========================================
#           maxregnerOS Properties
# ========================================

# Device Identification
ro.product.brand=maxregner
ro.product.manufacturer=maxregner
ro.product.model=maxregnerOS Device
ro.product.device=maxregner_device
ro.build.display.id=maxregnerOS 1.0
ro.build.version.release=14
ro.build.version.sdk=34
ro.build.version.codename=maxregnerOS
ro.build.description=maxregnerOS 1.0 based on Android 14

# maxregnerOS Version Information
ro.maxregner.version=1.0
ro.maxregner.version.code=100
ro.maxregner.build.date=$(date +%Y%m%d)
ro.maxregner.build.type=OFFICIAL
ro.maxregner.maintainer=maxregner
ro.maxregner.features.enabled=true

# System Optimization
ro.config.max_starting_bg=12
ro.sys.fw.bg_apps_limit=32
ro.config.dha_cached_max=24
ro.config.dha_empty_max=32
ro.config.dha_empty_init=24
ro.config.dha_lmk_scale=0.545
ro.config.sdha_apps_bg_max=64
ro.config.sdha_apps_bg_min=8

# Performance Enhancements
ro.surface_flinger.max_frame_buffer_acquired_buffers=3
ro.surface_flinger.running_without_sync_framework=true
ro.surface_flinger.vsync_event_phase_offset_ns=2000000
ro.surface_flinger.vsync_sf_event_phase_offset_ns=6000000
debug.sf.enable_hwc_vds=1
debug.sf.latch_unsignaled=1
debug.gralloc.enable_fb_ubwc=1

# Display and Graphics
ro.surface_flinger.protected_contents=true
ro.surface_flinger.use_color_management=true
ro.surface_flinger.wcg_composition_dataspace=143261696
ro.surface_flinger.has_wide_color_display=true
ro.surface_flinger.has_HDR_display=true
ro.surface_flinger.use_content_detection_for_refresh_rate=true

# Audio Enhancements
ro.audio.flinger_standbytime_ms=300
ro.audio.offload_wakelock=false
audio.offload.gapless.enabled=true
audio.offload.multiple.enabled=false
audio.offload.passthrough=false
audio.offload.pcm.16bit.enable=true
audio.offload.pcm.24bit.enable=true
audio.deep_buffer.media=true
audio.sys.noisy.broadcast.delay=600
audio.sys.offload.pstimeout.secs=3

# Camera Enhancements
camera.disable_zsl_mode=false
ro.camera.hfr.enable=1
ro.camera.relight.enable=0
ro.camera.attr.detect.enable=1
persist.camera.gyro.android=1
persist.camera.is_type=1

# Connectivity
ro.telephony.call_ring.multiple=false
ro.telephony.call_ring.delay=3000
persist.vendor.radio.enable_voicecall=1
persist.vendor.radio.rat_on=combine
persist.vendor.radio.data_ltd_sys_ind=1

# Battery and Power Management
ro.config.small_battery=false
ro.config.hw_quickpoweron=true
ro.config.hw_power_saving=true
persist.sys.purgeable_assets=1
pm.sleep_mode=1
ro.ril.disable.power.collapse=0

# Memory Management
ro.config.fha_enable=true
ro.sys.fw.bg_apps_limit=32
ro.config.dha_cached_max=16
ro.config.dha_empty_max=42
ro.config.dha_empty_init=32
ro.config.dha_lmk_scale=0.545

# Network Optimizations
net.tcp.buffersize.default=4096,87380,256960,4096,16384,256960
net.tcp.buffersize.wifi=4096,87380,256960,4096,16384,256960
net.tcp.buffersize.umts=4096,87380,256960,4096,16384,256960
net.tcp.buffersize.gprs=4096,87380,256960,4096,16384,256960
net.tcp.buffersize.edge=4096,87380,256960,4096,16384,256960

# Gaming Optimizations
ro.config.enable.hw_accel=true
video.accelerate.hw=1
debug.sf.hw=1
debug.performance.tuning=1
debug.egl.profiler=1
debug.egl.hw=1
debug.composition.type=c2d
persist.sys.composition.type=c2d
debug.enabletr=true

# Security Enhancements
ro.config.knox=v30
ro.build.selinux=1
ro.boot.selinux=enforcing
persist.security.ams.enforcing=1
ro.securestorage.support=true

# UI and Animation Enhancements
ro.config.hw_menu_unlockscreen=false
ro.config.hw_navigationbar=true
persist.sys.ui.hw=1
ro.config.disable_hw_accel=false
ro.product.ui.theme=maxregnerOS

# Dalvik VM Optimizations
dalvik.vm.heapstartsize=16m
dalvik.vm.heapgrowthlimit=256m
dalvik.vm.heapsize=512m
dalvik.vm.heaptargetutilization=0.75
dalvik.vm.heapminfree=4m
dalvik.vm.heapmaxfree=8m
dalvik.vm.dex2oat-flags=--no-watch-dog
dalvik.vm.dex2oat-swap=false

# Storage Optimizations
ro.sys.fw.trim_enable_memory=2147483648
ro.sys.fw.use_trim_settings=true
ro.sys.fw.empty_app_percent=50
ro.sys.fw.trim_empty_percent=100
ro.sys.fw.trim_cache_percent=100
ro.sys.fw.bg_apps_limit=32

# Miscellaneous Enhancements
ro.config.hw_fast_dormancy=1
ro.config.hw_power_saving=true
persist.sys.scrollingcache=3
persist.sys.purgeable_assets=1
ro.config.hw_quickpoweron=true
ro.telephony.call_ring.multiple=false

# maxregnerOS Feature Flags
ro.maxregner.control_center=true
ro.maxregner.performance_mode=true
ro.maxregner.battery_optimization=true
ro.maxregner.security_enhanced=true
ro.maxregner.ui_customization=true
ro.maxregner.gesture_navigation=true
ro.maxregner.audio_enhanced=true
ro.maxregner.gaming_mode=true

# Developer Options
persist.sys.usb.config=mtp,adb
ro.adb.secure=0
ro.debuggable=1
persist.service.adb.enable=1
persist.service.debuggable=1
persist.sys.root_access=3

# ========================================
#        End maxregnerOS Properties
# ========================================
EOF

# Update system properties
echo "Updating system properties database..."
if [ -f /system/etc/prop.default ]; then
    cp /system/etc/prop.default /data/maxregner_backup/prop.default.bak
    
    # Add maxregnerOS properties to prop.default
    cat >> /system/etc/prop.default << 'EOF'

# maxregnerOS System Properties
ro.maxregner.version=1.0
ro.maxregner.features.enabled=true
persist.maxregner.performance=true
persist.maxregner.battery_opt=true
EOF
fi

# Create maxregnerOS property files
echo "Creating maxregnerOS property files..."
mkdir -p /system/etc/maxregner/props

cat > /system/etc/maxregner/props/performance.prop << 'EOF'
# maxregnerOS Performance Properties
ro.config.max_starting_bg=12
ro.sys.fw.bg_apps_limit=32
debug.sf.enable_hwc_vds=1
debug.sf.latch_unsignaled=1
ro.surface_flinger.max_frame_buffer_acquired_buffers=3
EOF

cat > /system/etc/maxregner/props/battery.prop << 'EOF'
# maxregnerOS Battery Properties
ro.config.hw_power_saving=true
pm.sleep_mode=1
ro.ril.disable.power.collapse=0
persist.sys.purgeable_assets=1
EOF

cat > /system/etc/maxregner/props/audio.prop << 'EOF'
# maxregnerOS Audio Properties
ro.audio.flinger_standbytime_ms=300
audio.offload.gapless.enabled=true
audio.deep_buffer.media=true
audio.sys.noisy.broadcast.delay=600
EOF

cat > /system/etc/maxregner/props/display.prop << 'EOF'
# maxregnerOS Display Properties
ro.surface_flinger.use_color_management=true
ro.surface_flinger.has_wide_color_display=true
ro.surface_flinger.has_HDR_display=true
ro.surface_flinger.use_content_detection_for_refresh_rate=true
EOF

# Update default.prop for early boot properties
echo "Updating early boot properties..."
if [ -f /system/etc/ramdisk/default.prop ]; then
    cp /system/etc/ramdisk/default.prop /data/maxregner_backup/default.prop.bak
    
    cat >> /system/etc/ramdisk/default.prop << 'EOF'

# maxregnerOS Early Boot Properties
ro.maxregner.early_boot=true
persist.maxregner.init=true
EOF
fi

# Create property loading script
echo "Creating property loading script..."
cat > /system/bin/maxregner/load_props.sh << 'EOF'
#!/system/bin/sh

# maxregnerOS Property Loader
# Loads maxregnerOS-specific properties at runtime

echo "Loading maxregnerOS properties..."

# Load performance properties
if [ -f /system/etc/maxregner/props/performance.prop ]; then
    while IFS='=' read -r key value; do
        if [ ! -z "$key" ] && [ "${key#\#}" = "$key" ]; then
            setprop "$key" "$value"
        fi
    done < /system/etc/maxregner/props/performance.prop
fi

# Load battery properties
if [ -f /system/etc/maxregner/props/battery.prop ]; then
    while IFS='=' read -r key value; do
        if [ ! -z "$key" ] && [ "${key#\#}" = "$key" ]; then
            setprop "$key" "$value"
        fi
    done < /system/etc/maxregner/props/battery.prop
fi

# Load audio properties
if [ -f /system/etc/maxregner/props/audio.prop ]; then
    while IFS='=' read -r key value; do
        if [ ! -z "$key" ] && [ "${key#\#}" = "$key" ]; then
            setprop "$key" "$value"
        fi
    done < /system/etc/maxregner/props/audio.prop
fi

# Load display properties
if [ -f /system/etc/maxregner/props/display.prop ]; then
    while IFS='=' read -r key value; do
        if [ ! -z "$key" ] && [ "${key#\#}" = "$key" ]; then
            setprop "$key" "$value"
        fi
    done < /system/etc/maxregner/props/display.prop
fi

echo "maxregnerOS properties loaded successfully!"
EOF

chmod 755 /system/bin/maxregner/load_props.sh

# Update system settings
echo "Updating system settings..."
settings put system maxregner_version "1.0"
settings put global maxregner_features_enabled 1
settings put secure maxregner_security_enhanced 1

# Create maxregnerOS system info
echo "Creating maxregnerOS system information..."
cat > /system/etc/maxregner/system_info.txt << 'EOF'
maxregnerOS System Information
==============================

Version: 1.0
Build Date: $(date)
Base: One UI 8.0 / Android 14
Maintainer: maxregner

Features:
- Advanced Performance Optimization
- Intelligent Battery Management
- Enhanced Security & Privacy
- Custom UI Theming Engine
- Advanced Gesture Controls
- Premium Audio Enhancements
- Gaming Mode Optimization
- Developer Tools Integration

System Modifications:
- Custom SystemUI with maxregnerOS branding
- Enhanced Settings app with advanced controls
- Optimized framework for better performance
- Custom media pack with exclusive sounds
- Advanced gesture navigation system
- Intelligent background app management
- Enhanced security and privacy controls
- Custom boot animation and wallpapers

For support and updates, visit: maxregnerOS.com
EOF

echo "maxregnerOS build properties applied successfully!"

