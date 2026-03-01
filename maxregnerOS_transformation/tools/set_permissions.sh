#!/system/bin/sh

# maxregnerOS Permissions Script
# Sets proper permissions and SELinux contexts for maxregnerOS files

echo "Setting maxregnerOS permissions and contexts..."

# Set permissions for maxregnerOS binaries
echo "Setting binary permissions..."
find /system/bin/maxregner -type f -exec chmod 755 {} \;
find /system/bin/maxregner -type d -exec chmod 755 {} \;
chown -R root:root /system/bin/maxregner

# Set permissions for maxregnerOS configuration files
echo "Setting configuration permissions..."
find /system/etc/maxregner -type f -exec chmod 644 {} \;
find /system/etc/maxregner -type d -exec chmod 755 {} \;
chown -R root:root /system/etc/maxregner

# Set permissions for maxregnerOS media files
echo "Setting media permissions..."
find /system/media -type f -exec chmod 644 {} \;
find /system/media -type d -exec chmod 755 {} \;
chown -R root:root /system/media

# Set permissions for maxregnerOS overlay files
echo "Setting overlay permissions..."
if [ -d /system/vendor/overlay/maxregner ]; then
    find /system/vendor/overlay/maxregner -type f -exec chmod 644 {} \;
    find /system/vendor/overlay/maxregner -type d -exec chmod 755 {} \;
    chown -R root:root /system/vendor/overlay/maxregner
fi

# Set permissions for maxregnerOS data directories
echo "Setting data directory permissions..."
mkdir -p /data/maxregner/{logs,cache,configs,backup}
chmod 755 /data/maxregner
chmod 755 /data/maxregner/logs
chmod 755 /data/maxregner/cache
chmod 755 /data/maxregner/configs
chmod 755 /data/maxregner/backup
chown -R system:system /data/maxregner

# Set SELinux contexts
echo "Setting SELinux contexts..."

# System binaries
if command -v restorecon >/dev/null 2>&1; then
    restorecon -R /system/bin/maxregner
    restorecon -R /system/etc/maxregner
    restorecon -R /system/media
    restorecon -R /data/maxregner
    
    # Set specific contexts for maxregnerOS files
    chcon u:object_r:system_file:s0 /system/bin/maxregner/*
    chcon u:object_r:system_file:s0 /system/etc/maxregner/*
    chcon u:object_r:media_rw_data_file:s0 /system/media/audio/notifications/maxregner_*
    chcon u:object_r:media_rw_data_file:s0 /system/media/audio/ringtones/maxregner_*
    chcon u:object_r:media_rw_data_file:s0 /system/media/audio/alarms/maxregner_*
    chcon u:object_r:media_rw_data_file:s0 /system/media/audio/ui/maxregner_*
fi

# Set init script permissions
echo "Setting init script permissions..."
if [ -f /system/etc/init/maxregner.rc ]; then
    chmod 644 /system/etc/init/maxregner.rc
    chown root:root /system/etc/init/maxregner.rc
    if command -v restorecon >/dev/null 2>&1; then
        restorecon /system/etc/init/maxregner.rc
    fi
fi

# Set build.prop permissions
echo "Setting build.prop permissions..."
chmod 644 /system/build.prop
chown root:root /system/build.prop
if command -v restorecon >/dev/null 2>&1; then
    restorecon /system/build.prop
fi

# Set framework permissions
echo "Setting framework permissions..."
if [ -d /system/framework/maxregner ]; then
    find /system/framework/maxregner -type f -exec chmod 644 {} \;
    find /system/framework/maxregner -type d -exec chmod 755 {} \;
    chown -R root:root /system/framework/maxregner
    if command -v restorecon >/dev/null 2>&1; then
        restorecon -R /system/framework/maxregner
    fi
fi

# Set app permissions
echo "Setting app permissions..."
if [ -d /system/app ]; then
    find /system/app -name "*maxregner*" -type f -exec chmod 644 {} \;
    find /system/app -name "*maxregner*" -type d -exec chmod 755 {} \;
    chown -R root:root /system/app/*maxregner*
    if command -v restorecon >/dev/null 2>&1; then
        restorecon -R /system/app/*maxregner*
    fi
fi

if [ -d /system/priv-app ]; then
    find /system/priv-app -name "*maxregner*" -type f -exec chmod 644 {} \;
    find /system/priv-app -name "*maxregner*" -type d -exec chmod 755 {} \;
    chown -R root:root /system/priv-app/*maxregner*
    if command -v restorecon >/dev/null 2>&1; then
        restorecon -R /system/priv-app/*maxregner*
    fi
fi

# Create maxregnerOS permission verification script
echo "Creating permission verification script..."
cat > /system/bin/maxregner/verify_permissions.sh << 'EOF'
#!/system/bin/sh

# maxregnerOS Permission Verification Script
# Verifies that all maxregnerOS files have correct permissions

echo "Verifying maxregnerOS permissions..."

# Check binary permissions
echo "Checking binary permissions..."
for file in /system/bin/maxregner/*; do
    if [ -f "$file" ]; then
        perm=$(stat -c %a "$file")
        if [ "$perm" != "755" ]; then
            echo "WARNING: $file has incorrect permissions: $perm (should be 755)"
            chmod 755 "$file"
        fi
    fi
done

# Check configuration permissions
echo "Checking configuration permissions..."
for file in /system/etc/maxregner/*; do
    if [ -f "$file" ]; then
        perm=$(stat -c %a "$file")
        if [ "$perm" != "644" ]; then
            echo "WARNING: $file has incorrect permissions: $perm (should be 644)"
            chmod 644 "$file"
        fi
    fi
done

# Check data directory permissions
echo "Checking data directory permissions..."
if [ -d /data/maxregner ]; then
    perm=$(stat -c %a /data/maxregner)
    if [ "$perm" != "755" ]; then
        echo "WARNING: /data/maxregner has incorrect permissions: $perm (should be 755)"
        chmod 755 /data/maxregner
    fi
fi

# Check SELinux contexts
echo "Checking SELinux contexts..."
if command -v ls >/dev/null 2>&1; then
    ls -Z /system/bin/maxregner/* 2>/dev/null | while read context file; do
        if [[ "$context" != *"system_file"* ]]; then
            echo "WARNING: $file has incorrect SELinux context: $context"
        fi
    done
fi

echo "Permission verification completed!"
EOF

chmod 755 /system/bin/maxregner/verify_permissions.sh

# Set special permissions for system modification scripts
echo "Setting special permissions for system scripts..."
chmod 755 /system/bin/maxregner/control_center.sh
chmod 755 /system/bin/maxregner/battery_manager.sh
chmod 755 /system/bin/maxregner/performance_booster.sh
chmod 755 /system/bin/maxregner/security_manager.sh
chmod 755 /system/bin/maxregner/gesture_manager.sh
chmod 755 /system/bin/maxregner/maxregner_daemon.sh
chmod 755 /system/bin/maxregner/load_props.sh

# Create symbolic links for easy access
echo "Creating symbolic links..."
ln -sf /system/bin/maxregner/control_center.sh /system/bin/maxregner-control
ln -sf /system/bin/maxregner/battery_manager.sh /system/bin/maxregner-battery
ln -sf /system/bin/maxregner/performance_booster.sh /system/bin/maxregner-performance
ln -sf /system/bin/maxregner/security_manager.sh /system/bin/maxregner-security

# Set permissions for symbolic links
chmod 755 /system/bin/maxregner-*

# Final permission check
echo "Running final permission verification..."
/system/bin/maxregner/verify_permissions.sh

echo "maxregnerOS permissions and contexts set successfully!"

