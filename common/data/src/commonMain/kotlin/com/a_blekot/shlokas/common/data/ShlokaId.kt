package com.a_blekot.shlokas.common.data

import kotlinx.serialization.Serializable

@Serializable
enum class ShlokaId(val id: String) {
    NONE("NONE"),
    SB_1_1_1("SB_1_1_1"),
    SB_1_1_2("SB_1_1_2"),
    SB_1_1_3("SB_1_1_3"),
    SB_1_1_10("SB_1_1_10"),
    SB_1_2_6("SB_1_2_6"),
    SB_1_2_7("SB_1_2_7"),
    SB_1_2_8("SB_1_2_8"),
    SB_1_2_9("SB_1_2_9"),
    SB_1_2_10("SB_1_2_10"),
    SB_1_2_11("SB_1_2_11"),
    SB_1_2_12("SB_1_2_12"),
    SB_1_2_13("SB_1_2_13"),
    SB_1_2_14("SB_1_2_14"),
    SB_1_2_16("SB_1_2_16"),
    SB_1_2_17("SB_1_2_17"),
    SB_1_2_18("SB_1_2_18"),
    SB_1_2_19("SB_1_2_19"),
    SB_1_2_20("SB_1_2_20"),
    SB_1_2_21("SB_1_2_21"),
    SB_1_3_28("SB_1_3_28"),
    SB_1_3_43("SB_1_3_43"),
    SB_1_5_11("SB_1_5_11"),
    SB_1_5_17("SB_1_5_17"),
    SB_1_5_18("SB_1_5_18"),
    SB_1_7_6("SB_1_7_6"),
    SB_1_7_10("SB_1_7_10"),
    SB_1_8_18("SB_1_8_18"),
    SB_1_8_26("SB_1_8_26"),
    SB_1_8_42("SB_1_8_42"),
    SB_1_13_10("SB_1_13_10"),
    SB_1_18_13("SB_1_18_13"),
    SB_2_1_5("SB_2_1_5"),
    SB_2_1_6("SB_2_1_6"),
    SB_2_1_11("SB_2_1_11"),
    SB_2_3_10("SB_2_3_10"),
    SB_2_3_17("SB_2_3_17"),
    SB_2_3_19("SB_2_3_19"),
    SB_2_4_18("SB_2_4_18"),
    SB_2_9_33("SB_2_9_33"),
    SB_2_9_34("SB_2_9_34"),
    SB_2_9_35("SB_2_9_35"),
    SB_2_9_36("SB_2_9_36"),
    SB_2_10_1("SB_2_10_1"),
    SB_3_2_23("SB_3_2_23"),
    SB_3_5_41("SB_3_5_41"),
    SB_3_9_11("SB_3_9_11"),
    SB_3_25_19("SB_3_25_19"),
    SB_3_25_20("SB_3_25_20"),
    SB_3_25_21("SB_3_25_21"),
    SB_3_25_25("SB_3_25_25"),
    SB_3_29_11_12("SB_3_29_11_12"),
    SB_3_29_13("SB_3_29_13"),
    SB_3_31_1("SB_3_31_1"),
    SB_3_33_6("SB_3_33_6"),
    SB_3_33_7("SB_3_33_7"),
    SB_4_3_23("SB_4_3_23"),
    SB_4_30_35("SB_4_30_35"),
    SB_4_31_14("SB_4_31_14"),
    SB_5_5_1("SB_5_5_1"),
    SB_5_5_2("SB_5_5_2"),
    SB_5_5_4("SB_5_5_4"),
    SB_5_5_5("SB_5_5_5"),
    SB_5_5_8("SB_5_5_8"),
    SB_5_5_18("SB_5_5_18"),
    SB_5_12_12("SB_5_12_12"),
    SB_5_18_12("SB_5_18_12"),
    SB_6_1_13_14("SB_6_1_13_14"),
    SB_6_1_15("SB_6_1_15"),
    SB_6_1_40("SB_6_1_40"),
    SB_6_3_19("SB_6_3_19"),
    SB_6_3_20_21("SB_6_3_20_21"),
    SB_6_3_31("SB_6_3_31"),
    SB_6_14_5("SB_6_14_5"),
    SB_6_17_28("SB_6_17_28"),
    NI_01("NI_01"),
    NI_02("NI_02"),
    NI_03("NI_03"),
    NI_04("NI_04"),
    NI_05("NI_05"),
    NI_06("NI_06"),
    NI_07("NI_07"),
    NI_08("NI_08"),
    NI_09("NI_09"),
    NI_10("NI_10"),
    NI_11("NI_11"),
    BG_1_1("BG_1_1"),
    BG_2_7("BG_2_7"),
    BG_2_13("BG_2_13"),
    BG_2_27("BG_2_27"),
    BG_3_27("BG_3_27"),
    BG_4_2("BG_4_2"),
    BG_4_8("BG_4_8"),
    BG_4_9("BG_4_9"),
    BG_4_34("BG_4_34"),
    BG_5_22("BG_5_22"),
    BG_5_29("BG_5_29"),
    BG_6_47("BG_6_47"),
    BG_7_14("BG_7_14"),
    BG_8_5("BG_8_5"),
    BG_9_2("BG_9_2"),
    BG_9_14("BG_9_14"),
    BG_9_26("BG_9_26"),
    BG_9_27("BG_9_27"),
    BG_10_8("BG_10_8"),
    BG_10_9("BG_10_9"),
    BG_10_10("BG_10_10"),
    BG_10_11("BG_10_11"),
    BG_13_22("BG_13_22"),
    BG_14_4("BG_14_4"),
    BG_14_26("BG_14_26"),
    BG_15_7("BG_15_7"),
    BG_15_15("BG_15_15"),
    BG_18_54("BG_18_54"),
    BG_18_55("BG_18_55"),
    BG_18_61("BG_18_61"),
    BG_18_65("BG_18_65"),
    BG_18_66("BG_18_66"),
}
