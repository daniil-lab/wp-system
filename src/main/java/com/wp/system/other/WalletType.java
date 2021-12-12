package com.wp.system.other;

public enum WalletType {
    RUB("Российский рубль"),
    USD("Американский доллар"),
    UAH("Украинская гривна");

    private String walletName;

    WalletType(String walletName) {
        this.walletName = walletName;
    }

    public String getWalletName() {
        return walletName;
    }
}
