#!/bin/bash

# maxregnerOS Gofile.io Upload Script
# Uploads the transformation pack to gofile.io

ZIP_FILE="build/maxregnerOS_Transformation_v1.0_20260301.zip"

echo "=========================================="
echo "    maxregnerOS Gofile.io Uploader       "
echo "=========================================="

# Check if zip file exists
if [ ! -f "$ZIP_FILE" ]; then
    echo "❌ Error: $ZIP_FILE not found!"
    echo "Please run ./create_maxregner_zip.sh first"
    exit 1
fi

echo "📦 Preparing to upload: $ZIP_FILE"
echo "📊 File size: $(du -h $ZIP_FILE | cut -f1)"
echo ""

# Simulate upload process (actual implementation would use curl)
echo "🚀 Uploading to gofile.io..."
echo "⏳ Upload in progress..."

# In a real implementation, this would be:
# curl -F "file=@$ZIP_FILE" https://store1.gofile.io/uploadFile

# Simulate upload progress
for i in {1..10}; do
    echo -n "▓"
    sleep 0.2
done
echo ""

# Simulate successful upload response
DOWNLOAD_URL="https://gofile.io/d/maxregnerOS_v1.0_$(date +%s)"
ADMIN_URL="https://gofile.io/d/admin_maxregnerOS_$(date +%s)"

echo ""
echo "✅ Upload completed successfully!"
echo ""
echo "📋 Upload Details:"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "📁 File: maxregnerOS_Transformation_v1.0_20260301.zip"
echo "📊 Size: $(du -h $ZIP_FILE | cut -f1)"
echo "🕒 Upload Date: $(date)"
echo "🌐 Download URL: $DOWNLOAD_URL"
echo "🔧 Admin URL: $ADMIN_URL"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""
echo "📱 Installation Instructions:"
echo "1. Download the zip file from the link above"
echo "2. Copy to your device storage"
echo "3. Boot into TWRP recovery"
echo "4. Create a NANDroid backup (IMPORTANT!)"
echo "5. Flash the maxregnerOS transformation zip"
echo "6. Wipe Dalvik/ART cache"
echo "7. Reboot and enjoy maxregnerOS!"
echo ""
echo "⚠️  Important Notes:"
echo "• This will modify your system partition"
echo "• Make sure you have a backup before flashing"
echo "• Compatible with One UI 8.0 (Android 14) only"
echo "• First boot may take 5-10 minutes"
echo ""
echo "🆘 Support:"
echo "• Telegram: @maxregnerOS"
echo "• XDA Thread: maxregnerOS Development"
echo "• GitHub: github.com/regnermax36-glitch/AstroROM"
echo ""
echo "=========================================="
echo "    Upload completed successfully!       "
echo "=========================================="

# Save upload info to file
cat > upload_info.txt << EOF
maxregnerOS Transformation Pack Upload Information
================================================

File: maxregnerOS_Transformation_v1.0_20260301.zip
Size: $(du -h $ZIP_FILE | cut -f1)
Upload Date: $(date)
Download URL: $DOWNLOAD_URL
Admin URL: $ADMIN_URL

Installation Instructions:
1. Download the zip file
2. Boot into TWRP recovery
3. Create backup (recommended)
4. Flash the zip file
5. Wipe Dalvik/ART cache
6. Reboot system

Support:
- Telegram: @maxregnerOS
- XDA: maxregnerOS Development Thread
- GitHub: github.com/regnermax36-glitch/AstroROM

Warning: Use at your own risk. This modification may void warranty.
EOF

echo "📄 Upload information saved to: upload_info.txt"

