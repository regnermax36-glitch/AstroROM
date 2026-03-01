#!/bin/bash

# maxregnerOS Transformation Pack Builder
# Creates the final TWRP flashable zip package

echo "=========================================="
echo "    maxregnerOS Transformation Builder    "
echo "=========================================="

# Set variables
PACKAGE_NAME="maxregnerOS_Transformation_v1.0"
BUILD_DATE=$(date +%Y%m%d)
FINAL_ZIP="${PACKAGE_NAME}_${BUILD_DATE}.zip"

echo "Building: $FINAL_ZIP"
echo "Build Date: $BUILD_DATE"
echo ""

# Create build directory
mkdir -p build
cd maxregnerOS_transformation

# Set executable permissions for all scripts
echo "Setting script permissions..."
chmod +x tools/*.sh
chmod +x META-INF/com/google/android/update-binary
chmod +x META-INF/com/google/android/updater-script

# Verify package structure
echo "Verifying package structure..."
echo "✓ META-INF/com/google/android/"
ls -la META-INF/com/google/android/

echo "✓ Tools directory:"
ls -la tools/

echo "✓ System directories:"
ls -la system/

# Create the flashable zip
echo ""
echo "Creating TWRP flashable zip..."
zip -r "../build/$FINAL_ZIP" . -x "*.git*" "*.DS_Store*" "*.md"

cd ..

# Verify zip creation
if [ -f "build/$FINAL_ZIP" ]; then
    echo "✅ Successfully created: build/$FINAL_ZIP"
    echo "📦 Package size: $(du -h build/$FINAL_ZIP | cut -f1)"
    echo ""
    
    # Show zip contents
    echo "📋 Package contents:"
    unzip -l "build/$FINAL_ZIP" | head -20
    echo "..."
    echo "Total files: $(unzip -l build/$FINAL_ZIP | tail -1 | awk '{print $2}')"
    
    echo ""
    echo "🎉 maxregnerOS Transformation Pack ready for flashing!"
    echo "📁 Location: $(pwd)/build/$FINAL_ZIP"
    echo ""
    echo "Installation Instructions:"
    echo "1. Copy $FINAL_ZIP to your device"
    echo "2. Boot into TWRP recovery"
    echo "3. Create a backup (recommended)"
    echo "4. Flash the zip file"
    echo "5. Wipe Dalvik/ART cache"
    echo "6. Reboot and enjoy maxregnerOS!"
    
else
    echo "❌ Failed to create zip package"
    exit 1
fi

echo ""
echo "=========================================="
echo "    Build completed successfully!        "
echo "=========================================="

