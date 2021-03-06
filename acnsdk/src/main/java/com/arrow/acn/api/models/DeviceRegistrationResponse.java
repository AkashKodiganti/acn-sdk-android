/*
 * Copyright (c) 2017 Arrow Electronics, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License 2.0
 * which accompanies this distribution, and is available at
 * http://apache.org/licenses/LICENSE-2.0
 *
 * Contributors: Arrow Electronics, Inc.
 */

package com.arrow.acn.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by osminin on 14.10.2016.
 */

public final class DeviceRegistrationResponse extends CommonResponse implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DeviceRegistrationResponse> CREATOR = new Parcelable.Creator<DeviceRegistrationResponse>() {
        @Override
        public DeviceRegistrationResponse createFromParcel(Parcel in) {
            return new DeviceRegistrationResponse(in);
        }

        @Override
        public DeviceRegistrationResponse[] newArray(int size) {
            return new DeviceRegistrationResponse[size];
        }
    };
    @SerializedName("externalId")
    private String externalId;

    public DeviceRegistrationResponse() {
    }

    protected DeviceRegistrationResponse(@NonNull Parcel in) {
        super(in);
        externalId = in.readString();
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DeviceRegistrationResponse that = (DeviceRegistrationResponse) o;

        return externalId != null ? externalId.equals(that.externalId) : that.externalId == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (externalId != null ? externalId.hashCode() : 0);
        return result;
    }

    @Override

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(externalId);
    }
}