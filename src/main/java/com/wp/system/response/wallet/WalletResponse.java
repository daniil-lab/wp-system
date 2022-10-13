package com.wp.system.response.wallet;

public class WalletResponse {

    private String walletSystemName;

    private String walletDisplayName;

    private String symbol;

    public WalletResponse() {};

    public WalletResponse(String walletSystemName, String walletDisplayName, String symbol) {
        this.walletSystemName = walletSystemName;
        this.walletDisplayName = walletDisplayName;
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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
