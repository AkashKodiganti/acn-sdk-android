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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NodeTypeRegistrationModel implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<NodeTypeRegistrationModel> CREATOR = new Parcelable.Creator<NodeTypeRegistrationModel>() {
        @NonNull
        @Override
        public NodeTypeRegistrationModel createFromParcel(@NonNull Parcel in) {
            return new NodeTypeRegistrationModel(in);
        }

        @NonNull
        @Override
        public NodeTypeRegistrationModel[] newArray(int size) {
            return new NodeTypeRegistrationModel[size];
        }
    };
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("enabled")
    @Expose
    private boolean enabled;
    @SerializedName("name")
    @Expose
    private String name;

    public NodeTypeRegistrationModel() {

    }

    protected NodeTypeRegistrationModel(@NonNull Parcel in) {
        description = in.readString();
        enabled = in.readByte() != 0x00;
        name = in.readString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodeTypeRegistrationModel that = (NodeTypeRegistrationModel) o;

        if (enabled != that.enabled) return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = description != null ? description.hashCode() : 0;
        result = 31 * result + (enabled ? 1 : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled The enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeByte((byte) (enabled ? 0x01 : 0x00));
        dest.writeString(name);
    }
}