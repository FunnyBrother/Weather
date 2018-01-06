package usbdrive;

import usbdrive.events.IUSBDriveListener;
import usbdrive.events.USBStorageEvent;

public class USBClass implements IUSBDriveListener {
    public USBClass () {

    }

    @Override
    public void usbDriveEvent(USBStorageEvent event) {
    }
}
