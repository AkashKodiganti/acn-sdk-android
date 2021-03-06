/*
 * Copyright (c) 2017 Arrow Electronics, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License 2.0
 * which accompanies this distribution, and is available at
 * http://apache.org/licenses/LICENSE-2.0
 *
 * Contributors: Arrow Electronics, Inc.
 */

package com.arrow.acn.api.listeners;

import com.arrow.acn.api.models.ApiError;

/**
 * Created by batrakov on 26.01.18.
 */

public interface DownloadSoftwareReleaseFileListener {
    /**
     * Call request was successfully done and provide received software release file as byte array.
     * @param bytes - received file as byte array
     * @param md5 - checksum for received file
     */
    void onDownloadSoftwareReleaseFileListenerSuccess(long fileLength,byte[] bytes, String md5);

    /**
     * Call on failure
     * @param apiError - received ApiError
     */
    void onDownloadSoftwareReleaseFileListenerError(ApiError apiError);
}
