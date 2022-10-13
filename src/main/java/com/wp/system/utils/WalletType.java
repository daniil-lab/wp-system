package com.wp.system.utils;

public enum WalletType {
    RUB("Российский рубль", "₽"),
    USD("Американский доллар", "$"),
    UAH("Украинская гривна", "₴"),
    BYR("Белорусский рубль", "BYR"),
    BRL("Бразильский реал", "$"),
    MDL("Молдавский лей", "MDL"),
    TRY("Турецкая лира", "₺"),
    NOK("Норвежская крона", "NOK"),
    KZT("Казахстанский тенге", "₸"),
    SEK("Шведская крона", "SEK"),
    AZN("Азербайджанский манат", "₼"),
    GEL("Грузинский лари", "₾"),
    EUR("Евро", "€"),
    HUF("Венгерский форинт", "ƒ"),
    DKK("Датская крона", "DKK"),
    CZK("Чешская крона", "CZK"),
    BGN("Болгарский лев", "BGN"),
    PLN("Польский злотый", "PLN"),
    AUD("Австралийский доллар", "$"),
    MXN("Мексиканское песо", "$"),
    ZAR("Южноафриканский рэнд", "ZAR"),
    JPY("Японская йена", "¥"),
    CAD("Канадский доллар", "$"),
    ISK("Исландская крона", "ISK"),
    CLP("Чиллийское песо", "$"),
    GBR("Фунт стерлингов", "£"),
    INR("Индийская рупия", "₹"),
    CHF("Швейцарский франк", "CHF"),
    ARS("Аргентинское песо", "$"),
    CNY("Китайский юань", "¥"),
    NONE("Неизвестно", "?");

    private String walletName;

    private String symbol;

    WalletType(String walletName, String symbol) {
        this.walletName = walletName;
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getWalletName() {
        return walletName;
    }
}
