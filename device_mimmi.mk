$(call inherit-product, $(SRC_TARGET_DIR)/product/languages_full.mk)
$(call inherit-product, $(SRC_TARGET_DIR)/product/full_base.mk)
$(call inherit-product, device/common/gps/gps_eu_supl.mk)

# proprietary side of the device
$(call inherit-product-if-exists, vendor/semc/mimmi/mimmi-vendor.mk)

# Discard inherited values and use our own instead.
PRODUCT_NAME := U20i
PRODUCT_DEVICE := mimmi
PRODUCT_MODEL := U20i

#
# Boot files
#
ifeq ($(TARGET_PREBUILT_KERNEL),)
TARGET_PREBUILT_KERNEL := $(LOCAL_PATH)/kernel
endif

# density in DPI of the LCD of this board. This is used to scale the UI
# appropriately. If this property is not defined, the default value is 160 dpi. 
PRODUCT_PROPERTY_OVERRIDES := \
    ro.sf.lcd_density=120

# These is the hardware-specific overlay, which points to the location
# of hardware-specific resource overrides, typically the frameworks and
# application settings that are stored in resourced.    
DEVICE_PACKAGE_OVERLAYS := device/semc/mimmi/overlay

-include device/semc/msm7x27-common/msm7x27.mk

PRODUCT_PACKAGES += \
    Torch \
    MimmiParts

# media configuration xml file
PRODUCT_COPY_FILES += \
    device/semc/mimmi/prebuilt/media_profiles.xml:/system/etc/media_profiles.xml

# Robyn uses low-density artwork where available
PRODUCT_AAPT_CONFIG := normal ldpi mdpi
PRODUCT_AAPT_PREF_CONFIG := mdpi

# Wifi firmware
PRODUCT_COPY_FILES += \
    device/semc/mimmi/prebuilt/tiwlan.ini:system/etc/wifi/tiwlan.ini \
    device/semc/mimmi/prebuilt/tiwlan_ap.ini:system/etc/wifi/softap/tiwlan_ap.ini

# Themes
PRODUCT_COPY_FILES += \
    device/semc/mimmi/prebuilt/minicm.png:system/usr/res/minicm.png \
    device/semc/mimmi/prebuilt/bootanimation.zip:system/media/bootanimation.zip

# Touchsceen
PRODUCT_COPY_FILES += \
    device/semc/msm7x27-common/prebuilt/cy8ctma300_ser.idc:system/usr/idc/cy8ctma300_ser.idc \
    device/semc/msm7x27-common/prebuilt/mimmi_keypad.idc:system/usr/idc/mimmi_keypad.idc

