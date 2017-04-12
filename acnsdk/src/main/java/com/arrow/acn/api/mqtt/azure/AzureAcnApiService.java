/*
 * Copyright (c) 2017 Arrow Electronics, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License 2.0
 * which accompanies this distribution, and is available at
 * http://apache.org/licenses/LICENSE-2.0
 *
 * Contributors: Arrow Electronics, Inc.
 */

package com.arrow.acn.api.mqtt.azure;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arrow.acn.api.mqtt.AbstractMqttAcnApiService;
import com.arrow.acn.api.mqtt.azure.auth.IotHubSasToken;
import com.google.firebase.crash.FirebaseCrash;
import com.arrow.acn.api.mqtt.azure.transport.TransportUtils;


import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;

/**
 * Created by osminin on 2/2/2017.
 */

public final class AzureAcnApiService extends AbstractMqttAcnApiService {
    private static final String TAG = AzureAcnApiService.class.getName();

    @NonNull
    private static String sslPrefix = "ssl://";
    @NonNull
    private static String sslPortSuffix = ":8883";

    private final String mAccessKey;
    private final String mHost;

    public AzureAcnApiService(String gatewayUid, String accessKey, String host) {
        super(gatewayUid);
        mAccessKey = accessKey;
        mHost = host;
    }

    @NonNull
    @Override
    protected String getPublisherTopic(String deviceType, String externalId) {
        return "devices/" + mGatewayId + "/messages/events/";
    }

    @NonNull
    @Override
    protected String getHost() {
        return sslPrefix + mHost + sslPortSuffix;
    }

    @Override
    public boolean hasBatchMode() {
        return true;
    }

    @Override
    protected MqttConnectOptions getMqttOptions() {
        MqttConnectOptions options = super.getMqttOptions();
        try {
            DeviceClientConfig config = new DeviceClientConfig(mHost, mGatewayId, mAccessKey, null);
            IotHubSasToken sasToken = new IotHubSasToken(config, System.currentTimeMillis() / 1000l +
                    config.getTokenValidSecs() + 1l);
            options.setCleanSession(false);
            String clientIdentifier = "DeviceClientType="
                    + URLEncoder.encode(TransportUtils.getJavaServiceClientIdentifier()
                    + TransportUtils.getServiceVersion(), "UTF-8");
            String iotHubUserName = config.getIotHubHostname() + "/" + config.getDeviceId() + "/" + clientIdentifier;
            options.setUserName(iotHubUserName);
            options.setPassword(sasToken.toString().toCharArray());
        } catch (URISyntaxException e) {
            reportError(e);
        } catch (UnsupportedEncodingException e) {
            reportError(e);
        }
        return options;
    }

    @Override
    protected String getClientId() {
        return mGatewayId;
    }

    @NonNull
    @Override
    protected String getSubscribeTopic() {
        return "devices/" + mGatewayId + "/messages/devicebound/#";
    }

    private void reportError(@NonNull Exception e) {
        FirebaseCrash.logcat(Log.ERROR, TAG, e.getClass().getName() + " " + e.getMessage());
        FirebaseCrash.report(e);
    }
}
