/*
 * Copyright (c) 2017 Arrow Electronics, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License 2.0
 * which accompanies this distribution, and is available at
 * http://apache.org/licenses/LICENSE-2.0
 *
 * Contributors: Arrow Electronics, Inc.
 */

package com.arrow.acn.api;

import android.support.annotation.Keep;

import com.arrow.acn.api.listeners.CheckinGatewayListener;
import com.arrow.acn.api.listeners.CommonRequestListener;
import com.arrow.acn.api.listeners.ConnectionListener;
import com.arrow.acn.api.listeners.DeleteDeviceActionListener;
import com.arrow.acn.api.listeners.FindDeviceListener;
import com.arrow.acn.api.listeners.FindDeviceStateListener;
import com.arrow.acn.api.listeners.FindGatewayListener;
import com.arrow.acn.api.listeners.GatewayCommandsListener;
import com.arrow.acn.api.listeners.GatewayRegisterListener;
import com.arrow.acn.api.listeners.GatewayUpdateListener;
import com.arrow.acn.api.listeners.GetGatewayConfigListener;
import com.arrow.acn.api.listeners.GetGatewaysListener;
import com.arrow.acn.api.listeners.ListNodeTypesListener;
import com.arrow.acn.api.listeners.ListResultListener;
import com.arrow.acn.api.listeners.MessageStatusListener;
import com.arrow.acn.api.listeners.PagingResultListener;
import com.arrow.acn.api.listeners.PostDeviceActionListener;
import com.arrow.acn.api.listeners.RegisterAccountListener;
import com.arrow.acn.api.listeners.RegisterDeviceListener;
import com.arrow.acn.api.listeners.ServerCommandsListener;
import com.arrow.acn.api.listeners.TelemetryCountListener;
import com.arrow.acn.api.listeners.TelemetryRequestListener;
import com.arrow.acn.api.listeners.UpdateDeviceActionListener;
import com.arrow.acn.api.models.AccountRequest;
import com.arrow.acn.api.models.AuditLogModel;
import com.arrow.acn.api.models.AuditLogsQuery;
import com.arrow.acn.api.models.DeviceActionModel;
import com.arrow.acn.api.models.DeviceActionTypeModel;
import com.arrow.acn.api.models.DeviceEventModel;
import com.arrow.acn.api.models.DeviceModel;
import com.arrow.acn.api.models.DeviceRegistrationModel;
import com.arrow.acn.api.models.DeviceTypeModel;
import com.arrow.acn.api.models.DeviceTypeRegistrationModel;
import com.arrow.acn.api.models.ErrorBodyModel;
import com.arrow.acn.api.models.FindDevicesRequest;
import com.arrow.acn.api.models.FindTelemetryRequest;
import com.arrow.acn.api.models.GatewayCommand;
import com.arrow.acn.api.models.GatewayModel;
import com.arrow.acn.api.models.HistoricalEventsRequest;
import com.arrow.acn.api.models.NewDeviceStateTransactionRequest;
import com.arrow.acn.api.models.NodeModel;
import com.arrow.acn.api.models.NodeRegistrationModel;
import com.arrow.acn.api.models.NodeTypeRegistrationModel;
import com.arrow.acn.api.models.TelemetryCountRequest;
import com.arrow.acn.api.models.TelemetryItemModel;
import com.arrow.acn.api.models.TelemetryModel;

import java.util.List;

/**
 * Created by osminin on 6/17/2016.
 */

@Keep
public interface AcnApiService {

    /**
     * make persistent connection to the cloud, should be called only after gateway registration
     * (connection require gateway hid to be performed)
     */

    void connect(ConnectionListener listener);

    /**
     * destroy persistent connection
     */
    void disconnect();

    /**
     * check if persistent connection is
     *
     * @return
     */
    boolean isConnected();

    /**
     * sends single telemetry request
     *
     * @param telemetry - telemetry model object
     */
    void sendSingleTelemetry(TelemetryModel telemetry, TelemetryRequestListener listener);

    /**
     * sends a scope of bundles with telemetry data
     *
     * @param telemetry - list of telemetries
     */
    void sendBatchTelemetry(List<TelemetryModel> telemetry, TelemetryRequestListener listener);

    /**
     * Finds and returns the list of telemetry by application hid
     * @param request FindTelemetryRequest with application hid, fromTimestamp and toTimestamp should
     *                have "yyyy-MM-dd'T'HH:mm:ss'Z'" format
     * @param listener listener interface implementation, should be not null
     */
    void findTelemetryByApplicationHid(FindTelemetryRequest request,
                                       PagingResultListener<TelemetryItemModel> listener);

    /**
     * Finds and returns the list of telemetry by device hid
     * @param request FindTelemetryRequest with device hid, fromTimestamp and toTimestamp should
     *                have "yyyy-MM-dd'T'HH:mm:ss'Z'" format
     * @param listener listener interface implementation, should be not null
     */
    void findTelemetryByDeviceHid(FindTelemetryRequest request,
                                  PagingResultListener<TelemetryItemModel> listener);

    /**
     * Finds and returns the list of telemetry by node hid
     * @param request FindTelemetryRequest with node hid, fromTimestamp and toTimestamp should
     *                have "yyyy-MM-dd'T'HH:mm:ss'Z'" format
     * @param listener listener interface implementation, should be not null
     */
    void findTelemetryByNodeHid(FindTelemetryRequest request,
                                PagingResultListener<TelemetryItemModel> listener);

    /**
     * returns the list of last sent telemetry
     * @param deviceHid device Hid
     * @param listener listener interface implementation, should be not null
     */
    void getLastTelemetry(String deviceHid, ListResultListener<TelemetryItemModel> listener);

    /**
     *  returns count of sent telemetries for the period from 'fromTimestamp' to 'toTimestamp'
     * @param request should contain device hid, fromTimestamp and toTimestamp should have
     *                "yyyy-MM-dd'T'HH:mm:ss'Z'" format, telemetryName like 'light' or some other. To
     *                get all sent telemetry use '*' as telemetryName
     * @param listener listener interface implementation, should be not null
     */
    void getTelemetryItemsCount(TelemetryCountRequest request, TelemetryCountListener listener);

    /**
     * check whether if current service supports sending batch telemetry
     *
     * @return true if supports, false otherwise
     */
    boolean hasBatchMode();

    /**
     * set listener for commands from server (only for mqtt)
     *
     * @param listener - listener interface
     */
    void setServerCommandsListener(ServerCommandsListener listener);

    /**
     * register new account or sign in
     *
     * @param accountRequest user's account data
     * @param listener       - listener to get the result
     */
    void registerAccount(AccountRequest accountRequest, RegisterAccountListener listener);

    //Action api

    /**
     *  Returns a list of available action types
     * @param listener listener interface implementation, should be not null
     */
    void getDeviceActionTypes(ListResultListener<DeviceActionTypeModel> listener);

    /**
     * Returns the list existing device actions for a specific device type
     * @param deviceHid hid of device
     * @param listener listener interface implementation, should be not null
     */
    void getDeviceActions(String deviceHid, ListResultListener<DeviceActionModel> listener);

    /**
     * Creates a new device action for a specific device type
     * @param deviceHid hid of device
     * @param action action model
     * @param listener listener interface implementation, should be not null
     */
    void postDeviceAction(String deviceHid, DeviceActionModel action, PostDeviceActionListener listener);

    /**
     * Updates an existing device action for a specific device type
     * @param deviceHid hid of device
     * @param index index of action to be updated
     * @param model action model
     * @param listener listener interface implementation, should be not null
     */
    void updateDeviceAction(String deviceHid, int index, DeviceActionModel model, UpdateDeviceActionListener listener);

    /**
     * Delete a device action from a specific device type
     * @param deviceHid hid of device
     * @param index index of action to be deleted
     * @param listener listener interface implementation, should be not null
     */
    void deleteDeviceAction(String deviceHid, int index, DeleteDeviceActionListener listener);

    //Device api

    /**
     * register new device
     * @param req      - device data
     * @param listener - listener to get the result
     */
    void registerDevice(DeviceRegistrationModel req, RegisterDeviceListener listener);

    /**
     *
     * @param request
     * @param listener
     */
    void findAllDevices(FindDevicesRequest request, PagingResultListener<DeviceModel> listener);

    /**
     *
     * @param request
     * @param listener
     */
    void getDeviceHistoricalEvents(HistoricalEventsRequest request, PagingResultListener<DeviceEventModel> listener);

    /**
     *
     * @param deviceHid
     * @param listener
     */
    void findDeviceByHid(String deviceHid, FindDeviceListener listener);

    /**
     *
     * @param deviceHid
     * @param device
     * @param listener
     */
    void updateDevice(String deviceHid, DeviceRegistrationModel device, CommonRequestListener listener);

    /**
     *
     * @param deviceHid
     * @param query
     * @param listener
     */
    void getDeviceAuditLogs(String deviceHid, AuditLogsQuery query, PagingResultListener<AuditLogModel> listener);

    /**
     *
     * @param deviceHid
     * @param error
     * @param listener
     */
    void sendDeviceError(String deviceHid, ErrorBodyModel error, CommonRequestListener listener);

    //Core-event api

    void registerReceivedEvent(String eventHid, CommonRequestListener listener);

    void eventHandlingSucceed(String eventHid, CommonRequestListener listener);

    void eventHandlingFailed(String eventHid, CommonRequestListener listener);

    //Gateways api

    void findAllGateways(GetGatewaysListener listener);

    void registerGateway(GatewayModel gatewayModel, GatewayRegisterListener listener);

    void findGateway(String hid, FindGatewayListener listener);

    void updateGateway(String hid, GatewayModel gatewayModel, GatewayUpdateListener listener);

    void checkinGateway(String hid, String gatewayUid, CheckinGatewayListener listener);

    void sendGatewayError(String hid, ErrorBodyModel error, CommonRequestListener listener);

    @Deprecated
    void sendCommandGateway(String hid, GatewayCommand command, GatewayCommandsListener listener);

    void getDevicesList(String gatewayHid, ListResultListener<DeviceModel> listener);

    void getGatewayConfig(String hid, GetGatewayConfigListener listener);

    void gatewayHeartbeat(String hid, CommonRequestListener listener);

    void getGatewayLogs(String hid, AuditLogsQuery query, PagingResultListener<AuditLogModel> listener);

    //Node api

    void getNodesList(ListResultListener<NodeModel> listener);

    void createNewNode(NodeRegistrationModel node, CommonRequestListener listener);

    void updateExistingNode(String nodeHid, NodeRegistrationModel node, CommonRequestListener listener);

    //Node-type api

    void getListNodeTypes(ListNodeTypesListener listener);

    void createNewNodeType(NodeTypeRegistrationModel nodeType, CommonRequestListener listener);

    void updateExistingNodeType(String hid, NodeTypeRegistrationModel nodeType, CommonRequestListener listener);

    //device-type api

    void getListDeviceTypes(ListResultListener<DeviceTypeModel> listener);

    void createNewDeviceType(DeviceTypeRegistrationModel deviceType, CommonRequestListener listener);

    void updateExistingDeviceType(String hid,
                                  DeviceTypeRegistrationModel deviceType, CommonRequestListener listener);

    //device-state-api

    void findDeviceState(String deviceHid, FindDeviceStateListener listener);

    void createNewDeviceStateTransaction(String hid,
                                         NewDeviceStateTransactionRequest request,
                                         CommonRequestListener listener);

    void deviceStateTransactionSucceeded(String hid, String transHid, MessageStatusListener listener);

    void deviceStateTransactionFailed(String hid, String transHid, ErrorBodyModel error,
                                      MessageStatusListener listener);

    void deviceStateTransactionReceived(String hid, String transHid, MessageStatusListener listener);

    void updateDeviceStateTransaction(String hid,
                                      NewDeviceStateTransactionRequest request,
                                      CommonRequestListener listener);
}
