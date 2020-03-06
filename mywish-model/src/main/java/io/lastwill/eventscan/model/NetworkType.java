package io.lastwill.eventscan.model;

import lombok.Getter;


@Getter
public enum NetworkType {
    DUCATUS_MAINNET(NetworkProviderType.DUCATUS),
    DUCATUSX_MAINNET(NetworkProviderType.DUCATUSX),
    BTC_MAINNET(NetworkProviderType.BTC),
    ETHEREUM_MAINNET(NetworkProviderType.WEB3),
    ;
    public final static String DUCATUS_MAINNET_VALUE = "DUCATUS_MAINNET";
    public final static String DUCATUSX_MAINNET_VALUE = "DUCATUSX_MAINNET";
    public final static String BTC_MAINNET_VALUE = "BTC_MAINNET";
    public final static String ETHEREUM_MAINNET_VALUE = "WEB3_MAINNET";

    private final NetworkProviderType networkProviderType;

    NetworkType(NetworkProviderType networkProviderType) {
        this.networkProviderType = networkProviderType;
    }

}
