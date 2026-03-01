#!/bin/bash

# Real Gofile.io Upload Script for maxregnerOS Transformation Pack
# This script contains the actual API calls needed to upload to gofile.io

ZIP_FILE="build/maxregnerOS_Transformation_v1.0_20260301.zip"

echo "=========================================="
echo "    Real Gofile.io Upload Instructions   "
echo "=========================================="

# Check if zip file exists
if [ ! -f "$ZIP_FILE" ]; then
    echo "❌ Error: $ZIP_FILE not found!"
    echo "Please run ./create_maxregner_zip.sh first"
    exit 1
fi

echo "📦 File to upload: $ZIP_FILE"
echo "📊 File size: $(du -h $ZIP_FILE | cut -f1)"
echo ""

echo "🔧 STEP 1: Get Gofile.io Upload Server"
echo "Run this command to get the upload server:"
echo ""
echo "curl -X GET 'https://api.gofile.io/getServer'"
echo ""

echo "🔧 STEP 2: Upload File to Gofile.io"
echo "Replace 'store1' with the server from step 1, then run:"
echo ""
echo "curl -F \"file=@$ZIP_FILE\" https://store1.gofile.io/uploadFile"
echo ""

echo "🔧 STEP 3: Alternative - Direct Upload Command"
echo "You can also try this direct upload command:"
echo ""
echo "curl -F \"file=@$ZIP_FILE\" -F \"folderId=\" https://store1.gofile.io/uploadFile"
echo ""

echo "📋 Expected Response Format:"
echo "{"
echo "  \"status\": \"ok\","
echo "  \"data\": {"
echo "    \"downloadPage\": \"https://gofile.io/d/XXXXXX\","
echo "    \"code\": \"XXXXXX\","
echo "    \"parentFolder\": \"XXXXXX\","
echo "    \"fileId\": \"XXXXXX\","
echo "    \"fileName\": \"maxregnerOS_Transformation_v1.0_20260301.zip\","
echo "    \"md5\": \"XXXXXX\""
echo "  }"
echo "}"
echo ""

echo "🌐 MANUAL UPLOAD OPTION:"
echo "If the API doesn't work, you can manually upload at:"
echo "https://gofile.io/uploadFiles"
echo ""

echo "📱 After Upload - Share Information:"
echo "Once uploaded, you'll get a download URL like:"
echo "https://gofile.io/d/XXXXXX"
echo ""

echo "📋 File Information for Upload:"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "📁 Filename: maxregnerOS_Transformation_v1.0_20260301.zip"
echo "📊 Size: $(du -h $ZIP_FILE | cut -f1)"
echo "🏷️  Description: Complete One UI 8.0 to maxregnerOS transformation"
echo "🔧 Type: TWRP Flashable Zip"
echo "📱 Compatibility: Samsung One UI 8.0 (Android 14)"
echo "⚠️  Warning: Use at your own risk, backup required"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""

echo "🆘 Support Information to Include:"
echo "• Installation: Flash via TWRP recovery"
echo "• Requirements: Unlocked bootloader, TWRP, 2GB+ free space"
echo "• Backup: Create NANDroid backup before flashing"
echo "• Support: GitHub - regnermax36-glitch/AstroROM"
echo "• Telegram: @maxregnerOS (if available)"
echo ""

echo "=========================================="
echo "   Ready for Manual Upload to Gofile.io  "
echo "=========================================="

# Create upload instructions file
cat > gofile_upload_instructions.txt << 'EOF'
maxregnerOS Transformation Pack - Gofile.io Upload Instructions
============================================================

File: maxregnerOS_Transformation_v1.0_20260301.zip
Size: 18KB
Type: TWRP Flashable Zip

UPLOAD METHODS:

Method 1 - API Upload:
1. Get server: curl -X GET 'https://api.gofile.io/getServer'
2. Upload: curl -F "file=@build/maxregnerOS_Transformation_v1.0_20260301.zip" https://store1.gofile.io/uploadFile

Method 2 - Manual Upload:
1. Visit: https://gofile.io/uploadFiles
2. Drag and drop the zip file
3. Wait for upload completion
4. Copy the download link

DESCRIPTION FOR UPLOAD:
maxregnerOS Transformation Pack v1.0 - Complete Samsung One UI 8.0 to maxregnerOS transformation. Includes custom UI, performance optimization, battery management, security enhancements, premium audio pack, and advanced features. TWRP flashable zip for Samsung devices with One UI 8.0 (Android 14). Requires unlocked bootloader and TWRP recovery. Create backup before flashing!

TAGS: Samsung, One UI, Custom ROM, TWRP, Android 14, maxregnerOS, Performance, Gaming

SUPPORT:
- GitHub: github.com/regnermax36-glitch/AstroROM
- Installation Guide: Included in README.md
- Compatibility: One UI 8.0 Samsung devices only

WARNING: Use at your own risk. May void warranty. Backup required.
EOF

echo "📄 Upload instructions saved to: gofile_upload_instructions.txt"

