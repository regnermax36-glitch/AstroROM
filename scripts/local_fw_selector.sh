#!/bin/bash
#
#  Copyright (c) 2025 Sameer Al Sahab
#  Licensed under the MIT License. See LICENSE file for details.
#
#  Local Firmware Selector for OneUI 8.5
#  Allows selection of locally stored firmware instead of downloading
#

FW_DIR="${ASTROROM}/firmware"
FW_BASE="${FW_DIR}/downloaded"
LOCAL_FW_DIR="${FW_DIR}/local"

SELECT_LOCAL_FIRMWARE() {
    local target_fw="${1:-}"
    local model="${2:-$MODEL}"
    local csc="${3:-$CSC}"
    
    LOG_BEGIN "Selecting local OneUI 8.5 firmware"
    
    # Create local firmware directory if it doesn't exist
    mkdir -p "$LOCAL_FW_DIR"
    
    # Check if local firmware directory has any firmware
    local available_fw=()
    if [[ -d "$LOCAL_FW_DIR" ]]; then
        while IFS= read -r -d '' fw_path; do
            local fw_name=$(basename "$fw_path")
            available_fw+=("$fw_name")
        done < <(find "$LOCAL_FW_DIR" -maxdepth 1 -type d -print0 2>/dev/null)
    fi
    
    if [[ ${#available_fw[@]} -eq 0 ]]; then
        LOG_WARN "No local firmware found in $LOCAL_FW_DIR"
        LOG_INFO "Please place your OneUI 8.5 firmware in: $LOCAL_FW_DIR"
        LOG_INFO "Expected structure: $LOCAL_FW_DIR/<MODEL>_<CSC>/AP_*.tar.md5"
        
        if CONFIRM_ACTION "Would you like to specify a custom firmware path?" "false"; then
            SELECT_CUSTOM_FIRMWARE_PATH "$model" "$csc"
            return $?
        fi
        
        return 1
    fi
    
    # Display available firmware
    LOG_INFO "Available local firmware:"
    local idx=1
    for fw in "${available_fw[@]}"; do
        echo "  [$idx] $fw"
        ((idx++))
    done
    
    # Auto-select if model matches
    local selected_fw=""
    for fw in "${available_fw[@]}"; do
        if [[ "$fw" == "${model}_${csc}" ]] || [[ "$fw" == *"${model}"* ]]; then
            selected_fw="$fw"
            LOG_INFO "Auto-selected matching firmware: $selected_fw"
            break
        fi
    done
    
    # Manual selection if no auto-match
    if [[ -z "$selected_fw" ]]; then
        echo ""
        read -p "Select firmware [1-${#available_fw[@]}]: " choice
        if [[ "$choice" =~ ^[0-9]+$ ]] && [[ "$choice" -ge 1 ]] && [[ "$choice" -le ${#available_fw[@]} ]]; then
            selected_fw="${available_fw[$((choice-1))]}"
        else
            ERROR_EXIT "Invalid selection"
            return 1
        fi
    fi
    
    # Validate and link firmware
    local source_dir="${LOCAL_FW_DIR}/${selected_fw}"
    local target_dir="${FW_BASE}/${model}_${csc}"
    
    if [[ ! -d "$source_dir" ]]; then
        ERROR_EXIT "Selected firmware directory not found: $source_dir"
        return 1
    fi
    
    # Check for AP file
    local ap_file=$(find "$source_dir" -maxdepth 1 \( -name "AP_*.tar.md5" -o -name "AP_*.tar" \) | head -1)
    if [[ -z "$ap_file" ]]; then
        ERROR_EXIT "AP package not found in $source_dir"
        return 1
    fi
    
    # Validate AP file
    if ! _VALIDATE_AP_FILE "$ap_file"; then
        ERROR_EXIT "AP file validation failed: $ap_file"
        return 1
    fi
    
    # Create target directory and copy/link firmware
    mkdir -p "$target_dir"
    
    # Copy firmware files
    LOG_INFO "Linking local firmware to build directory..."
    cp -al "$source_dir"/* "$target_dir/" 2>/dev/null || {
        # Fallback to regular copy if hard link fails
        cp -a "$source_dir"/* "$target_dir/" || {
            ERROR_EXIT "Failed to copy firmware files"
            return 1
        }
    }
    
    # Detect firmware version
    local fw_version="local_oneui8.5"
    local build_prop=""
    
    # Try to extract build info if possible
    if command -v tar &>/dev/null; then
        local temp_dir=$(mktemp -d)
        if tar -xf "$ap_file" -C "$temp_dir" "system.img" 2>/dev/null; then
            # Try to get version info (simplified)
            fw_version="oneui8.5_$(date +%Y%m%d)"
            rm -rf "$temp_dir"
        fi
    fi
    
    # Create firmware info file
    echo "${fw_version}" > "${target_dir}/firmware.info"
    
    LOG_END "Local firmware selected: $selected_fw"
    LOG_INFO "Firmware location: $target_dir"
    
    return 0
}

SELECT_CUSTOM_FIRMWARE_PATH() {
    local model="$1"
    local csc="$2"
    
    echo ""
    read -p "Enter full path to firmware directory: " custom_path
    
    if [[ ! -d "$custom_path" ]]; then
        ERROR_EXIT "Path does not exist: $custom_path"
        return 1
    fi
    
    local ap_file=$(find "$custom_path" -maxdepth 1 \( -name "AP_*.tar.md5" -o -name "AP_*.tar" \) | head -1)
    if [[ -z "$ap_file" ]]; then
        ERROR_EXIT "AP package not found in $custom_path"
        return 1
    fi
    
    if ! _VALIDATE_AP_FILE "$ap_file"; then
        ERROR_EXIT "AP file validation failed"
        return 1
    fi
    
    local target_dir="${FW_BASE}/${model}_${csc}"
    mkdir -p "$target_dir"
    cp -a "$custom_path"/* "$target_dir/" || {
        ERROR_EXIT "Failed to copy firmware from $custom_path"
        return 1
    }
    
    echo "oneui8.5_custom_$(date +%Y%m%d)" > "${target_dir}/firmware.info"
    
    LOG_END "Custom firmware path configured: $custom_path"
    return 0
}

# Override DOWNLOAD_FW to use local selection when LOCAL_FW is set
if [[ "${LOCAL_FW:-false}" == "true" ]] || [[ -n "${LOCAL_FW_PATH:-}" ]]; then
    # Hook into the download process
    _ORIGINAL_DOWNLOAD_FW() {
        local target_fw="${1:-}"
        
        if [[ -n "$LOCAL_FW_PATH" ]]; then
            SELECT_CUSTOM_FIRMWARE_PATH "$MODEL" "$CSC" || return 1
        else
            SELECT_LOCAL_FIRMWARE "$target_fw" "$MODEL" "$CSC" || return 1
        fi
    }
fi
