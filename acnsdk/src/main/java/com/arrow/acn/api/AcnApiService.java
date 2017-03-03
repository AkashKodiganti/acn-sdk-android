package com.arrow.acn.api;

import android.os.Handler;

import com.arrow.acn.api.listeners.CheckinGatewayListener;
import com.arrow.acn.api.listeners.CommonRequestListener;
import com.arrow.acn.api.listeners.ConnectionListener;
import com.arrow.acn.api.listeners.DeleteDeviceActionListener;
import com.arrow.acn.api.listeners.FindDeviceListener;
import com.arrow.acn.api.listeners.FindGatewayListener;
import com.arrow.acn.api.listeners.GatewayCommandsListener;
import com.arrow.acn.api.listeners.GatewayRegisterListener;
import com.arrow.acn.api.listeners.GatewayUpdateListener;
import com.arrow.acn.api.listeners.GetGatewayConfigListener;
import com.arrow.acn.api.listeners.GetGatewaysListener;
import com.arrow.acn.api.listeners.ListNodeTypesListener;
import com.arrow.acn.api.listeners.ListResultListener;
import com.arrow.acn.api.listeners.PagingResultListener;
import com.arrow.acn.api.listeners.PostDeviceActionListener;
import com.arrow.acn.api.listeners.RegisterAccountListener;
import com.arrow.acn.api.listeners.RegisterDeviceListener;
import com.arrow.acn.api.listeners.ServerCommandsListener;
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
import com.arrow.acn.api.models.FindTelemetryRequest;
import com.arrow.acn.api.models.GatewayCommand;
import com.arrow.acn.api.models.GatewayModel;
import com.arrow.acn.api.models.NodeModel;
import com.arrow.acn.api.models.NodeRegistrationModel;
import com.arrow.acn.api.models.NodeTypeRegistrationModel;
import com.arrow.acn.api.models.TelemetryItemModel;
import com.arrow.acn.api.models.TelemetryModel;

import java.util.List;

/**
 * Created by osminin on 6/17/2016.
 */

public interface AcnApiService {

    /**
     * sets endpoint server environment url
     *
     * @param endpoint  - String url like "http://pegasuskronos01-dev.cloudapp.net:28880"
     * @param apiKey
     * @param apiSecret
     */
    void setRestEndpoint(String endpoint, String apiKey, String apiSecret);

    /**
     * sets mqtt server host and userName prefix
     *
     * @param host   String contains host like "tcp://pegasusqueue01-dev.cloudapp.net:46953"
     * @param prefix String contains prefix like "/themis.dev"
     */
    void setMqttEndpoint(String host, String prefix);

    /**
     * initialize service with a context and bind it with activity's lifecycle
     *
     * @param handler -
     */
    void initialize(Handler handler);

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
    void sendSingleTelemetry(TelemetryModel telemetry);

    /**
     * sends a scope of bundles with telemetry data
     *
     * @param telemetry - list of telemetries
     */
    void sendBatchTelemetry(List<TelemetryModel> telemetry);

    void findTelemetryByApplicationHid(FindTelemetryRequest request,
                                       PagingResultListener<TelemetryItemModel> listener);

    void findTelemetryByDeviceHid(FindTelemetryRequest request,
                                  PagingResultListener<TelemetryItemModel> listener);

    void findTelemetryByNodeHid(FindTelemetryRequest request,
                                PagingResultListener<TelemetryItemModel> listener);

    void getLastTelemetry(String deviceHid, ListResultListener<TelemetryItemModel> listener);

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

    void getDeviceActionTypes(ListResultListener<DeviceActionTypeModel> listener);

    void getDeviceActions(String deviceHid, ListResultListener<DeviceActionModel> listener);

    void postDeviceAction(String deviceHid, DeviceActionModel action, PostDeviceActionListener listener);

    void updateDeviceAction(String deviceHid, int index, DeviceActionModel model, UpdateDeviceActionListener listener);

    void deleteDeviceAction(String deviceHid, int index, DeleteDeviceActionListener listener);

    //Device api

    /**
     * register new device
     *
     * @param req      - device data
     * @param listener - listener to get the result
     */
    void registerDevice(DeviceRegistrationModel req, RegisterDeviceListener listener);

    void findAllDevices(String userHid, String uid, String type, String gatewayHid, String enabled,
                        int page, int size, PagingResultListener<DeviceModel> listener);

    void getDeviceHistoricalEvents(String deviceHid, PagingResultListener<DeviceEventModel> listener);

    void findDeviceByHid(String deviceHid, FindDeviceListener listener);

    void updateDevice(String deviceHid, DeviceRegistrationModel device, CommonRequestListener listener);

    void getDeviceAuditLogs(String deviceHid, AuditLogsQuery query, PagingResultListener<AuditLogModel> listener);

    //Core-event api

    void registerReceivedEvent(String eventHid);

    void eventHandlingSucceed(String eventHid);

    void eventHandlingFailed(String eventHid);

    //Gateways api

    void findAllGateways(GetGatewaysListener listener);

    void registerGateway(GatewayModel gatewayModel, GatewayRegisterListener listener);

    void findGateway(String hid, FindGatewayListener listener);

    void updateGateway(String hid, GatewayModel gatewayModel, GatewayUpdateListener listener);

    void checkinGateway(String hid, String gatewayUid, CheckinGatewayListener listener);

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
}