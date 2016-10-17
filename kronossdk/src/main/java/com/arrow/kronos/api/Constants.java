package com.arrow.kronos.api;

import okhttp3.MediaType;

/**
 * Created by osminin on 9/20/2016.
 */

public final class Constants {
    public interface Api {
        String X_ARROW_APIKEY = "x-arrow-apikey";
        String X_ARROW_DATE = "x-arrow-date";
        String X_ARROW_SIGNATURE = "x-arrow-signature";
        String X_ARROW_VERSION = "x-arrow-version";
        String X_ARROW_VERSION_1 = "1";
    }

    public static final String DEVICE_ID_KEY = "SynapS3_IotConnect_DeviceId";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
}
