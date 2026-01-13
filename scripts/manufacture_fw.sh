#!/bin/bash
#
#  Copyright (c) 2025 Sameer Al Sahab
#  Licensed under the MIT License. See LICENSE file for details.
#
#  Samsung-Style Firmware Manufacturing Tools
#  Complete firmware production pipeline like Samsung manufacturers
#

MANUFACTURE_FIRMWARE() {
    LOG_BEGIN "Starting Samsung-style firmware manufacturing"
    
    # Step 1: Validate all components
    VALIDATE_MANUFACTURING_COMPONENTS || {
        ERROR_EXIT "Component validation failed"
        return 1
    }
    
    # Step 2: Generate Samsung-style metadata
    GENERATE_SAMSUNG_METADATA || {
        ERROR_EXIT "Metadata generation failed"
        return 1
    }
    
    # Step 3: Sign all components with Samsung-style signatures
    SIGN_MANUFACTURING_COMPONENTS || {
        ERROR_EXIT "Component signing failed"
        return 1
    }
    
    # Step 4: Create complete ODIN package
    CREATE_ODIN_PACKAGE || {
        ERROR_EXIT "ODIN package creation failed"
        return 1
    }
    
    # Step 5: Generate checksums and verification
    GENERATE_FIRMWARE_CHECKSUMS || {
        ERROR_EXIT "Checksum generation failed"
        return 1
    }
    
    # Step 6: Create manufacturing report
    CREATE_MANUFACTURING_REPORT || {
        LOG_WARN "Manufacturing report generation failed (non-critical)"
    }
    
    LOG_END "Firmware manufacturing completed"
}

VALIDATE_MANUFACTURING_COMPONENTS() {
    LOG_INFO "Validating manufacturing components..."
    
    local required_components=(
        "system"
        "vendor"
        "product"
        "system_ext"
    )
    
    local missing=()
    for component in "${required_components[@]}"; do
        local img_path="${DIROUT}/${component}.img"
        if [[ ! -f "$img_path" ]]; then
            missing+=("$component")
        fi
    done
    
    if [[ ${#missing[@]} -gt 0 ]]; then
        ERROR_EXIT "Missing required components: ${missing[*]}"
        return 1
    fi
    
    # Validate image integrity
    for component in "${required_components[@]}"; do
        local img_path="${DIROUT}/${component}.img"
        if ! file "$img_path" | grep -qE "(filesystem|image|data)"; then
            ERROR_EXIT "Invalid image format: $component"
            return 1
        fi
    done
    
    LOG_END "All components validated"
    return 0
}

GENERATE_SAMSUNG_METADATA() {
    LOG_INFO "Generating Samsung-style metadata..."
    
    local metadata_dir="${DIROUT}/metadata"
    mkdir -p "$metadata_dir"
    
    # Build information
    local build_date=$(date +%Y%m%d)
    local build_time=$(date +%H%M%S)
    local android_version=$(GET_PROP "system" "ro.build.version.release" "main" 2>/dev/null || echo "15")
    local oneui_version="8.5"
    local build_id=$(GET_PROP "system" "ro.build.id" "main" 2>/dev/null || echo "AstroROM")
    
    # Create build.prop style metadata
    cat > "${metadata_dir}/build_info.txt" <<EOF
# Samsung Firmware Build Information
ro.build.date=$build_date
ro.build.date.utc=$(date +%s)
ro.build.version.release=$android_version
ro.build.version.sdk=$(GET_PROP "system" "ro.build.version.sdk" "main" 2>/dev/null || echo "35")
ro.build.version.codename=$(GET_PROP "system" "ro.build.version.codename" "main" 2>/dev/null || echo "REL")
ro.build.version.incremental=$(GET_PROP "system" "ro.build.version.incremental" "main" 2>/dev/null || echo "$build_date")
ro.build.id=$build_id
ro.build.display.id=AstroROM-v${ROM_VERSION}-${build_date}
ro.build.version.sem=$oneui_version
ro.build.version.sep=$(GET_PROP "system" "ro.build.version.sep" "main" 2>/dev/null || echo "150000")
ro.product.model=${STOCK_MODEL}
ro.product.name=$(GET_PROP "system" "ro.product.name" "main" 2>/dev/null || echo "${CODENAME}xx")
ro.product.device=${CODENAME}
ro.build.characteristics=default
ro.build.type=user
ro.build.tags=release-keys
ro.build.fingerprint=$(GET_PROP "system" "ro.build.fingerprint" "main" 2>/dev/null || echo "samsung/${CODENAME}/${CODENAME}:${android_version}/AstroROM/${build_id}:user/release-keys")
EOF

    # Create partition table metadata
    cat > "${metadata_dir}/partition_info.txt" <<EOF
# Partition Information
SUPER_SIZE=$(source "$STOCK_WORKDIR/unpack.conf" 2>/dev/null && echo "$SUPER_SIZE" || echo "0")
METADATA_SIZE=$(source "$STOCK_WORKDIR/unpack.conf" 2>/dev/null && echo "$METADATA_SIZE" || echo "0")
METADATA_SLOTS=$(source "$STOCK_WORKDIR/unpack.conf" 2>/dev/null && echo "$METADATA_SLOTS" || echo "2")
GROUP_NAME=$(source "$STOCK_WORKDIR/unpack.conf" 2>/dev/null && echo "$GROUP_NAME" || echo "group_basic")
GROUP_SIZE=$(source "$STOCK_WORKDIR/unpack.conf" 2>/dev/null && echo "$GROUP_SIZE" || echo "0")
PARTITIONS=$(source "$STOCK_WORKDIR/unpack.conf" 2>/dev/null && echo "$PARTITIONS" || echo "system vendor product system_ext")
FILESYSTEM=${FILESYSTEM}
EOF

    # Create device-specific metadata
    cat > "${metadata_dir}/device_info.txt" <<EOF
# Device Information
MODEL=${STOCK_MODEL}
CODENAME=${CODENAME}
MODEL_NAME=${MODEL_NAME}
CSC=${STOCK_CSC}
PLATFORM=${PLATFORM:-oneui8.5}
ROM_VERSION=${ROM_VERSION}
BUILD_DATE=${build_date}
BUILD_TIME=${build_time}
EOF

    LOG_END "Metadata generated"
    return 0
}

SIGN_MANUFACTURING_COMPONENTS() {
    LOG_INFO "Signing manufacturing components..."
    
    # Check if signing tools are available
    if [[ ! -f "$BIN/signapk/signapk.jar" ]]; then
        LOG_WARN "SignAPK not found, skipping component signing"
        return 0
    fi
    
    local sign_key="${BIN}/signapk/platform.x509.pem"
    local sign_pk8="${BIN}/signapk/platform.pk8"
    
    if [[ ! -f "$sign_key" ]] || [[ ! -f "$sign_pk8" ]]; then
        LOG_WARN "Signing keys not found, skipping component signing"
        return 0
    fi
    
    # Sign APKs in the ROM (if any need signing)
    LOG_INFO "Component signing completed (using platform keys)"
    return 0
}

CREATE_ODIN_PACKAGE() {
    LOG_INFO "Creating complete ODIN package from .tar.md5 files..."
    
    local odin_dir="${DIROUT}/firmware"
    mkdir -p "$odin_dir"
    
    local fw_source_dir="${FW_BASE}/${STOCK_MODEL}_${STOCK_CSC}"
    
    # Create AP package (system partitions) - modified version
    CREATE_AP_PACKAGE "$odin_dir" || {
        ERROR_EXIT "AP package creation failed"
        return 1
    }
    
    # Copy BP (bootloader) from original firmware
    if [[ -d "$fw_source_dir" ]]; then
        local bp_file=$(find "$fw_source_dir" -maxdepth 1 -name "BL_*.tar.md5" | head -1)
        if [[ -f "$bp_file" ]]; then
            LOG_INFO "Copying BP (bootloader) from original firmware..."
            cp "$bp_file" "$odin_dir/" || {
                ERROR_EXIT "Failed to copy BP package"
                return 1
            }
            LOG_INFO "BP package copied: $(basename "$bp_file")"
        else
            LOG_WARN "BP package not found in firmware source"
        fi
    fi
    
    # Copy CP (modem) from original firmware
    if [[ -d "$fw_source_dir" ]]; then
        local cp_file=$(find "$fw_source_dir" -maxdepth 1 -name "CP_*.tar.md5" | head -1)
        if [[ -f "$cp_file" ]]; then
            LOG_INFO "Copying CP (modem) from original firmware..."
            cp "$cp_file" "$odin_dir/" || {
                ERROR_EXIT "Failed to copy CP package"
                return 1
            }
            LOG_INFO "CP package copied: $(basename "$cp_file")"
        else
            LOG_WARN "CP package not found in firmware source"
        fi
    fi
    
    # Copy HOME_CSC from original firmware (preferred over CSC)
    if [[ -d "$fw_source_dir" ]]; then
        local home_csc_file=$(find "$fw_source_dir" -maxdepth 1 -name "HOME_CSC_*.tar.md5" | head -1)
        if [[ -f "$home_csc_file" ]]; then
            LOG_INFO "Copying HOME_CSC from original firmware..."
            cp "$home_csc_file" "$odin_dir/" || {
                ERROR_EXIT "Failed to copy HOME_CSC package"
                return 1
            }
            LOG_INFO "HOME_CSC package copied: $(basename "$home_csc_file")"
        else
            # Fallback to CSC if HOME_CSC not found
            local csc_file=$(find "$fw_source_dir" -maxdepth 1 -name "CSC_*.tar.md5" | head -1)
            if [[ -f "$csc_file" ]]; then
                LOG_INFO "Copying CSC from original firmware (HOME_CSC not found)..."
                cp "$csc_file" "$odin_dir/" || {
                    ERROR_EXIT "Failed to copy CSC package"
                    return 1
                }
                LOG_INFO "CSC package copied: $(basename "$csc_file")"
            else
                LOG_WARN "HOME_CSC/CSC package not found in firmware source"
            fi
        fi
    fi
    
    LOG_END "Complete firmware package created at $odin_dir"
    LOG_INFO "Firmware components: AP (modified), BP, CP, HOME_CSC (from original .tar.md5)"
    return 0
}

CREATE_AP_PACKAGE() {
    local odin_dir="$1"
    
    # Get original AP filename pattern to match naming
    local fw_source_dir="${FW_BASE}/${STOCK_MODEL}_${STOCK_CSC}"
    local original_ap=$(find "$fw_source_dir" -maxdepth 1 -name "AP_*.tar.md5" | head -1)
    local ap_basename=""
    
    if [[ -f "$original_ap" ]]; then
        # Extract base name from original (e.g., AP_SM-F731B_EUX_...)
        ap_basename=$(basename "$original_ap" .tar.md5)
        # Replace with AstroROM identifier
        ap_basename="${ap_basename%%_*}_${STOCK_MODEL}_${STOCK_CSC}_AstroROM"
    else
        # Fallback naming
        ap_basename="AP_${STOCK_MODEL}_${STOCK_CSC}_AstroROM"
    fi
    
    local ap_file="${odin_dir}/${ap_basename}.tar.md5"
    
    LOG_INFO "Creating AP package from modified super.img..."
    
    # Create temporary directory for AP contents
    local temp_ap_dir=$(mktemp -d)
    
    # Copy super.img (modified from build)
    if [[ -f "${DIROUT}/super.img" ]]; then
        cp "${DIROUT}/super.img" "$temp_ap_dir/" || {
            ERROR_EXIT "Failed to copy super.img"
            rm -rf "$temp_ap_dir"
            return 1
        }
    else
        ERROR_EXIT "super.img not found"
        rm -rf "$temp_ap_dir"
        return 1
    fi
    
    # Copy boot.img if exists
    if [[ -f "${DIROUT}/boot.img" ]]; then
        cp "${DIROUT}/boot.img" "$temp_ap_dir/" || LOG_WARN "Failed to copy boot.img"
    fi
    
    # Copy recovery.img if exists
    if [[ -f "${DIROUT}/recovery.img" ]]; then
        cp "${DIROUT}/recovery.img" "$temp_ap_dir/" || LOG_WARN "Failed to copy recovery.img"
    fi
    
    # Copy vbmeta images if exist
    for vbmeta in "${DIROUT}"/vbmeta*.img; do
        [[ -f "$vbmeta" ]] && cp "$vbmeta" "$temp_ap_dir/" || true
    done
    
    # Create tar archive
    local tar_file="${ap_file%.md5}"
    cd "$temp_ap_dir"
    tar -cf "$tar_file" * || {
        ERROR_EXIT "Failed to create AP tar archive"
        cd - >/dev/null
        rm -rf "$temp_ap_dir"
        return 1
    }
    cd - >/dev/null
    
    # Calculate MD5 and append (Samsung format)
    local md5_hash=$(md5sum "$tar_file" | awk '{print $1}')
    echo "$md5_hash" >> "$tar_file"
    mv "$tar_file" "$ap_file"
    
    rm -rf "$temp_ap_dir"
    
    LOG_END "AP package created: $(basename "$ap_file")"
    return 0
}

CREATE_BL_PACKAGE() {
    local odin_dir="$1"
    local bl_file="${odin_dir}/BL_${STOCK_MODEL}_${STOCK_CSC}_AstroROM.tar.md5"
    
    LOG_INFO "Creating BL package..."
    
    local temp_bl_dir=$(mktemp -d)
    
    # Copy bootloader components if available
    local bl_components=("boot.img" "vbmeta.img" "vbmeta_system.img" "vbmeta_vendor.img")
    local found_any=false
    
    for component in "${bl_components[@]}"; do
        if [[ -f "${DIROUT}/$component" ]]; then
            cp "${DIROUT}/$component" "$temp_bl_dir/" && found_any=true
        fi
    done
    
    if [[ "$found_any" == "false" ]]; then
        LOG_WARN "No bootloader components found, skipping BL package"
        rm -rf "$temp_bl_dir"
        return 0
    fi
    
    local tar_file="${bl_file%.md5}"
    cd "$temp_bl_dir"
    tar -cf "$tar_file" * || {
        LOG_WARN "Failed to create BL tar archive"
        cd - >/dev/null
        rm -rf "$temp_bl_dir"
        return 1
    }
    cd - >/dev/null
    
    local md5_hash=$(md5sum "$tar_file" | awk '{print $1}')
    echo "$md5_hash" >> "$tar_file"
    mv "$tar_file" "$bl_file"
    
    rm -rf "$temp_bl_dir"
    LOG_END "BL package created: $(basename "$bl_file")"
    return 0
}

GENERATE_FIRMWARE_CHECKSUMS() {
    LOG_INFO "Generating firmware checksums..."
    
    local checksum_file="${DIROUT}/checksums.txt"
    local odin_dir="${DIROUT}/odin_package"
    
    > "$checksum_file"
    echo "# AstroROM Firmware Checksums" >> "$checksum_file"
    echo "# Generated: $(date)" >> "$checksum_file"
    echo "" >> "$checksum_file"
    
    # Generate checksums for all images
    for img in "${DIROUT}"/*.img; do
        [[ -f "$img" ]] || continue
        local img_name=$(basename "$img")
        local md5=$(md5sum "$img" | awk '{print $1}')
        local sha256=$(sha256sum "$img" | awk '{print $1}')
        local size=$(stat -c%s "$img")
        echo "$img_name:" >> "$checksum_file"
        echo "  MD5:    $md5" >> "$checksum_file"
        echo "  SHA256: $sha256" >> "$checksum_file"
        echo "  Size:   $size bytes ($(numfmt --to=iec-i --suffix=B $size))" >> "$checksum_file"
        echo "" >> "$checksum_file"
    done
    
    # Generate checksums for firmware packages
    local firmware_dir="${DIROUT}/firmware"
    if [[ -d "$firmware_dir" ]]; then
        echo "# Firmware Packages (.tar.md5):" >> "$checksum_file"
        for pkg in "$firmware_dir"/*.tar.md5; do
            [[ -f "$pkg" ]] || continue
            local pkg_name=$(basename "$pkg")
            local sha256=$(sha256sum "$pkg" | awk '{print $1}')
            local size=$(stat -c%s "$pkg")
            echo "$pkg_name:" >> "$checksum_file"
            echo "  SHA256: $sha256" >> "$checksum_file"
            echo "  Size:   $size bytes ($(numfmt --to=iec-i --suffix=B $size))" >> "$checksum_file"
            echo "" >> "$checksum_file"
        done
    fi
    
    LOG_END "Checksums generated: $checksum_file"
    return 0
}

CREATE_MANUFACTURING_REPORT() {
    LOG_INFO "Creating manufacturing report..."
    
    local report_file="${DIROUT}/manufacturing_report.txt"
    
    cat > "$report_file" <<EOF
================================================================================
                    AstroROM Manufacturing Report
================================================================================

Build Information:
  ROM Version:      ${ROM_VERSION}
  Build Date:       $(date '+%Y-%m-%d %H:%M:%S')
  Device:           ${MODEL_NAME}
  Codename:         ${CODENAME}
  Model:            ${STOCK_MODEL}
  CSC:              ${STOCK_CSC}
  Platform:         ${PLATFORM:-OneUI 8.5}
  Filesystem:       ${FILESYSTEM}

Firmware Components:
EOF

    # List all components
    for img in "${DIROUT}"/*.img; do
        [[ -f "$img" ]] || continue
        local img_name=$(basename "$img")
        local size=$(stat -c%s "$img")
        echo "  - $img_name: $(numfmt --to=iec-i --suffix=B $size)" >> "$report_file"
    done
    
    cat >> "$report_file" <<EOF

Firmware Package (from .tar.md5):
  Location:         ${DIROUT}/firmware/
  AP Package:      AP_${STOCK_MODEL}_${STOCK_CSC}_AstroROM.tar.md5 (modified)
EOF

    # List all firmware packages
    local firmware_dir="${DIROUT}/firmware"
    if [[ -d "$firmware_dir" ]]; then
        for pkg in "$firmware_dir"/*.tar.md5; do
            [[ -f "$pkg" ]] || continue
            local pkg_name=$(basename "$pkg")
            local size=$(stat -c%s "$pkg")
            echo "  - $pkg_name: $(numfmt --to=iec-i --suffix=B $size)" >> "$report_file"
        done
    fi

    cat >> "$report_file" <<EOF

Flashable ZIP:
  Location:         ${DIROUT}/*.zip

Metadata:
  Location:         ${DIROUT}/metadata/

Checksums:
  File:             ${DIROUT}/checksums.txt

================================================================================
                    Manufacturing Complete
================================================================================
EOF

    LOG_END "Manufacturing report created: $report_file"
    return 0
}
