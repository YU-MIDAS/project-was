package com.example.demo.bounded_context.separation.util;

import java.util.Map;

public class CategoryProvider {
    public static final Map<String, String> categoryMapper =
            Map.ofEntries(
                    Map.entry("paper", "종이"),
                    Map.entry("can", "철캔"),
                    Map.entry("pet", "알루미늄캔"),
                    Map.entry("plastic", "플라스틱"),
                    Map.entry("styrofoam", "스티로폼"),
                    Map.entry("plastic_bag", "비닐"),
                    Map.entry("glass", "유리"),
                    Map.entry("battery", "폐전지"),
                    Map.entry("f_lamp", "폐형광등")
            );
}
