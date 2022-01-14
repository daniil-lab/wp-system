package com.wp.system.other.email;

import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

public class EmailBlank {
    public static Tuple2<String, String> submitEmail(String url) {
        return Tuples.of(
                "Подтверждение электронной почты",
                "Для подтверждения электронной почты внутри системы WalletBox перейдите по следующей ссылке: %s".formatted(url)
        );
    }
}
