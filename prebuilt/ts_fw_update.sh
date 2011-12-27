#Touchscreen FW update
dev=/sys/bus/serio/devices/serio0
jtouch=/system/etc/firmware/cy8_truetouch_jtouch.hex
tpk=/system/etc/firmware/cy8_truetouch_tpk.hex
cy8_truetouch_loader -dev /sys/bus/serio/devices/serio0 -jtouch_fw $jtouch -tpk_fw $tpk

