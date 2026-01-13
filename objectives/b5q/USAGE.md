# b5q Device Configuration - Usage Guide

## Overview
This device configuration is set up to use ROM later in the build process, with support for local OneUI 8.5 firmware selection and Samsung-style manufacturing tools.

## Features

### 1. Local Firmware Selection
The device is configured to use locally stored OneUI 8.5 firmware instead of downloading from servers.

**Setup:**
1. Place your OneUI 8.5 firmware in: `firmware/local/`
2. Expected structure: `firmware/local/<MODEL>_<CSC>/AP_*.tar.md5`
3. Or set `LOCAL_FW_PATH` environment variable to point to your firmware directory

**Example:**
```bash
# Option 1: Place firmware in default location
mkdir -p firmware/local
# Copy your firmware to: firmware/local/SM-XXXXX_EUX/

# Option 2: Use custom path
export LOCAL_FW_PATH="/path/to/your/firmware"
./build.sh -b b5q
```

### 2. Samsung-Style Manufacturing
The build system includes complete manufacturing tools that produce firmware like Samsung does.

**Features:**
- Complete ODIN package creation (AP, BL, CP, CSC)
- Samsung-style metadata generation
- Component signing and verification
- Checksum generation (MD5, SHA256)
- Manufacturing reports

**Enable Manufacturing:**
Manufacturing is automatically enabled for b5q. To enable for other devices:
```bash
export MANUFACTURE_FW=true
./build.sh -b <device>
```

## Build Process

### Standard Build
```bash
./build.sh -b b5q
```

### Build with Local Firmware
```bash
# Set local firmware mode
export LOCAL_FW=true
./build.sh -b b5q
```

### Build with Custom Firmware Path
```bash
export LOCAL_FW_PATH="/path/to/oneui8.5/firmware"
./build.sh -b b5q
```

## Output Structure

After building, you'll find two outputs:

### First Output: Flashable ZIP
```
out/
└── AstroROM_*.zip              # Flashable ZIP for recovery
```

### Second Output: Complete Firmware Package (from .tar.md5)
```
out/
├── firmware/                   # Complete firmware package
│   ├── AP_*.tar.md5            # AP package (modified system)
│   ├── BL_*.tar.md5            # BP package (bootloader, from original)
│   ├── CP_*.tar.md5            # CP package (modem, from original)
│   └── HOME_CSC_*.tar.md5      # HOME_CSC package (carrier, from original)
├── metadata/                   # Samsung-style metadata
│   ├── build_info.txt          # Build information
│   ├── partition_info.txt      # Partition table
│   └── device_info.txt         # Device information
├── checksums.txt               # All checksums
└── manufacturing_report.txt    # Manufacturing report
```

**Note:** The firmware package contains:
- **AP**: Modified with AstroROM changes (from build)
- **BP, CP, HOME_CSC**: Copied directly from original firmware .tar.md5 files

## Configuration

Edit `objectives/b5q/b5q.sh` to customize:

```bash
MODEL="SM-XXXXX"              # Your device model
CSC="EUX"                      # Your region/CSC
STOCK_MODEL="SM-XXXXX"         # Stock firmware model
VNDK="33"                      # VNDK version
FILESYSTEM=erofs               # Filesystem type
PLATFORM="oneui8.5"            # OneUI version
LOCAL_FW=true                  # Enable local firmware
MANUFACTURE_FW=true            # Enable manufacturing
```

## Troubleshooting

### Local Firmware Not Found
- Ensure firmware is in `firmware/local/<MODEL>_<CSC>/`
- Check that AP_*.tar.md5 file exists
- Verify firmware is valid OneUI 8.5

### Manufacturing Errors
- Check that all required images are present
- Verify super.img was created successfully
- Ensure sufficient disk space for ODIN packages

### Build Errors
- Verify device configuration in `b5q.sh`
- Check that VNDK version matches firmware
- Ensure all dependencies are installed

## Advanced Usage

### Custom Manufacturing Options
You can customize manufacturing by modifying `scripts/manufacture_fw.sh`:
- Add custom signing keys
- Modify metadata generation
- Add additional ODIN packages
- Customize checksum algorithms

### Integration with CI/CD
The manufacturing tools are designed to work in automated environments:
```bash
export LOCAL_FW=true
export MANUFACTURE_FW=true
./build.sh -b b5q
```

## Notes
- ROM usage is configured for later in the build process
- OneUI 8.5 firmware is selected locally
- Manufacturing tools produce Samsung-compatible firmware
- All outputs are verified with checksums
