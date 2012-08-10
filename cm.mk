## Specify phone tech before including full_phone
$(call inherit-product, vendor/cm/config/gsm.mk)

# Release name
PRODUCT_RELEASE_NAME := X10mini

# Inherit device configuration
$(call inherit-product, device/semc/mimmi/device_mimmi.mk)

#
# Setup device specific product configuration.
#
PRODUCT_DEVICE := mimmi
PRODUCT_NAME := cm_mimmi
PRODUCT_BRAND := SEMC
PRODUCT_MODEL := U20i
PRODUCT_MANUFACTURER := Sony Ericsson
PRODUCT_BUILD_PROP_OVERRIDES += PRODUCT_NAME=U20i BUILD_FINGERPRINT="google/yakju/maguro:4.1.1/JRO03C/398337:user/release-keys" PRIVATE_BUILD_DESC="yakju-user 4.1.1 JRO03C 398337 release-keys"
