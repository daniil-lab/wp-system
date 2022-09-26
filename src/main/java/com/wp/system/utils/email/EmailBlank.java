package com.wp.system.utils.email;

import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

public class EmailBlank {
    public static Tuple2<String, String> submitEmail(int code) {
        return Tuples.of(
                "Подтверждение электронной почты",
                "Для подтверждения электронной почты внутри системы WalletBox воспользуйтесь следующим кодом подтверждения: " + code
        );
    }
}
