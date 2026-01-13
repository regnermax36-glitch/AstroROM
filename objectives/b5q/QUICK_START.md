# b5q Quick Start Guide

## Quick Setup

### 1. Configure Device
Edit `objectives/b5q/b5q.sh`:
```bash
MODEL="SM-XXXXX"        # Your device model
CSC="EUX"               # Your region
STOCK_MODEL="SM-XXXXX"  # Stock firmware model
```

### 2. Prepare Local Firmware
```bash
# Create local firmware directory
mkdir -p firmware/local

# Place your OneUI 8.5 firmware
# Structure: firmware/local/SM-XXXXX_EUX/AP_*.tar.md5
```

### 3. Build
```bash
./build.sh -b b5q
```

## What You Get

After building, check `out/` directory:
- **Flashable ZIP:** `AstroROM_*.zip`
- **ODIN Package:** `out/odin_package/`
- **Metadata:** `out/metadata/`
- **Checksums:** `out/checksums.txt`
- **Report:** `out/manufacturing_report.txt`

## Troubleshooting

**No local firmware found?**
- Check `firmware/local/` directory
- Verify AP_*.tar.md5 file exists
- Use `LOCAL_FW_PATH` for custom location

**Manufacturing errors?**
- Ensure all images are built successfully
- Check disk space
- Verify super.img exists

**Build fails?**
- Check device configuration
- Verify VNDK version matches firmware
- Ensure dependencies are installed

## Advanced

**Custom firmware path:**
```bash
export LOCAL_FW_PATH="/path/to/firmware"
./build.sh -b b5q
```

**Disable manufacturing:**
```bash
export MANUFACTURE_FW=false
./build.sh -b b5q
```

**Debug mode:**
```bash
./build.sh -d -b b5q
```
