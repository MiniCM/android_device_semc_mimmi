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
PRODUCT_BUILD_PROP_OVERRIDES += PRODUCT_NAME=LT30p_1269-0608 BUILD_FINGERPRINT=Sony/LT30p_1269-0608/LT30p:4.0.4/7.0.A.3.195/PPP_xw:user/release-keys PRIVATE_BUILD_DESC="LT30p-user 4.0.4 7.0.A.3.195 PPP_xw test-keys"
