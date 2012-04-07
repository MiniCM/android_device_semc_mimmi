-include device/semc/msm7x27-common/BoardConfigCommon.mk
-include vendor/semc/mimmi/BoardConfigVendor.mk

TARGET_BOOTLOADER_BOARD_NAME := mimmi
TARGET_OTA_ASSERT_DEVICE := U20i,U20a,mimmi
PRODUCT_BUILD_PROP_OVERRIDES += TARGET_BOOTLOADER_BOARD_NAME=mimmi

