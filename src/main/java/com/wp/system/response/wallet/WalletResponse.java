package com.wp.system.response.wallet;

public class WalletResponse {

    private String walletSystemName;

    private String walletDisplayName;

    public WalletResponse() {};

    public WalletResponse(String walletSystemName, String walletDisplayName) {
        this.walletSystemName = walletSystemName;
        this.walletDisplayName = walletDisplayName;
    }

    public String getWalletSystemName() {
        return walletSystemName;
    }

    public void setWalletSystemName(String walletSystemName) {
        this.walletSystemName = walletSystemName;
    }

    public String getWalletDisplayName() {
        return walletDisplayName;
    }

    public void setWalletDisplayName(String walletDisplayName) {
        this.walletDisplayName = walletDisplayName;
    }
}
