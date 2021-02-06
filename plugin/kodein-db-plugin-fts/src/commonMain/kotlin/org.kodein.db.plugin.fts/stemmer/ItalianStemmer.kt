// This file was generated automatically by the Snowball to Java compiler
package org.kodein.db.plugin.fts.stemmer

/**
 * This class was automatically generated by a Snowball to Java compiler
 * It implements the stemming algorithm defined by a snowball script.
 */
@Suppress("UNREACHABLE_CODE")
internal class ItalianStemmer : SnowballProgram() {
    private var I_p2 = 0
    private var I_p1 = 0
    private var I_pV = 0

    private fun r_prelude(): Boolean {
        var among_var: Int
        val v_1: Int
        var v_2: Int
        var v_3: Int
        var v_4: Int
        var v_5: Int
        // (, line 34
        // test, line 35
        v_1 = cursor
        // repeat, line 35
        replab0@ while (true) {
            v_2 = cursor
            lab1@ do {
                // (, line 35
                // [, line 36
                bra = cursor
                // substring, line 36
                among_var = find_among(a_0, 7)
                if (among_var == 0) {
                    break@lab1
                }
                // ], line 36
                ket = cursor
                when (among_var) {
                    0 -> break@lab1
                    1 ->                                     // (, line 37
                        // <-, line 37
                        slice_from("\u00E0")
                    2 ->                                     // (, line 38
                        // <-, line 38
                        slice_from("\u00E8")
                    3 ->                                     // (, line 39
                        // <-, line 39
                        slice_from("\u00EC")
                    4 ->                                     // (, line 40
                        // <-, line 40
                        slice_from("\u00F2")
                    5 ->                                     // (, line 41
                        // <-, line 41
                        slice_from("\u00F9")
                    6 ->                                     // (, line 42
                        // <-, line 42
                        slice_from("qU")
                    7 -> {
                        // (, line 43
                        // next, line 43
                        if (cursor >= limit) {
                            break@lab1
                        }
                        cursor++
                    }
                }
                continue@replab0
            } while (false)
            cursor = v_2
            break@replab0
        }
        cursor = v_1
        // repeat, line 46
        replab2@ while (true) {
            v_3 = cursor
            lab3@ do {
                // goto, line 46
                golab4@ while (true) {
                    v_4 = cursor
                    lab5@ do {
                        // (, line 46
                        if (!in_grouping(g_v, 97, 249)) {
                            break@lab5
                        }
                        // [, line 47
                        bra = cursor
                        // or, line 47
                        lab6@ do {
                            v_5 = cursor
                            lab7@ do {
                                // (, line 47
                                // literal, line 47
                                if (!eq_s(1, "u")) {
                                    break@lab7
                                }
                                // ], line 47
                                ket = cursor
                                if (!in_grouping(g_v, 97, 249)) {
                                    break@lab7
                                }
                                // <-, line 47
                                slice_from("U")
                                break@lab6
                            } while (false)
                            cursor = v_5
                            // (, line 48
                            // literal, line 48
                            if (!eq_s(1, "i")) {
                                break@lab5
                            }
                            // ], line 48
                            ket = cursor
                            if (!in_grouping(g_v, 97, 249)) {
                                break@lab5
                            }
                            // <-, line 48
                            slice_from("I")
                        } while (false)
                        cursor = v_4
                        break@golab4
                    } while (false)
                    cursor = v_4
                    if (cursor >= limit) {
                        break@lab3
                    }
                    cursor++
                }
                continue@replab2
            } while (false)
            cursor = v_3
            break@replab2
        }
        return true
    }

    private fun r_mark_regions(): Boolean {
        val v_1: Int
        var v_2: Int
        var v_3: Int
        var v_6: Int
        val v_8: Int
        // (, line 52
        I_pV = limit
        I_p1 = limit
        I_p2 = limit
        // do, line 58
        v_1 = cursor
        lab0@ do {
            // (, line 58
            // or, line 60
            lab1@ do {
                v_2 = cursor
                lab2@ do {
                    // (, line 59
                    if (!in_grouping(g_v, 97, 249)) {
                        break@lab2
                    }
                    // or, line 59
                    lab3@ do {
                        v_3 = cursor
                        lab4@ do {
                            // (, line 59
                            if (!out_grouping(g_v, 97, 249)) {
                                break@lab4
                            }
                            // gopast, line 59
                            golab5@ while (true) {
                                lab6@ do {
                                    if (!in_grouping(g_v, 97, 249)) {
                                        break@lab6
                                    }
                                    break@golab5
                                } while (false)
                                if (cursor >= limit) {
                                    break@lab4
                                }
                                cursor++
                            }
                            break@lab3
                        } while (false)
                        cursor = v_3
                        // (, line 59
                        if (!in_grouping(g_v, 97, 249)) {
                            break@lab2
                        }
                        // gopast, line 59
                        golab7@ while (true) {
                            lab8@ do {
                                if (!out_grouping(g_v, 97, 249)) {
                                    break@lab8
                                }
                                break@golab7
                            } while (false)
                            if (cursor >= limit) {
                                break@lab2
                            }
                            cursor++
                        }
                    } while (false)
                    break@lab1
                } while (false)
                cursor = v_2
                // (, line 61
                if (!out_grouping(g_v, 97, 249)) {
                    break@lab0
                }
                // or, line 61
                lab9@ do {
                    v_6 = cursor
                    lab10@ do {
                        // (, line 61
                        if (!out_grouping(g_v, 97, 249)) {
                            break@lab10
                        }
                        // gopast, line 61
                        golab11@ while (true) {
                            lab12@ do {
                                if (!in_grouping(g_v, 97, 249)) {
                                    break@lab12
                                }
                                break@golab11
                            } while (false)
                            if (cursor >= limit) {
                                break@lab10
                            }
                            cursor++
                        }
                        break@lab9
                    } while (false)
                    cursor = v_6
                    // (, line 61
                    if (!in_grouping(g_v, 97, 249)) {
                        break@lab0
                    }
                    // next, line 61
                    if (cursor >= limit) {
                        break@lab0
                    }
                    cursor++
                } while (false)
            } while (false)
            // setmark pV, line 62
            I_pV = cursor
        } while (false)
        cursor = v_1
        // do, line 64
        v_8 = cursor
        lab13@ do {
            // (, line 64
            // gopast, line 65
            golab14@ while (true) {
                lab15@ do {
                    if (!in_grouping(g_v, 97, 249)) {
                        break@lab15
                    }
                    break@golab14
                } while (false)
                if (cursor >= limit) {
                    break@lab13
                }
                cursor++
            }
            // gopast, line 65
            golab16@ while (true) {
                lab17@ do {
                    if (!out_grouping(g_v, 97, 249)) {
                        break@lab17
                    }
                    break@golab16
                } while (false)
                if (cursor >= limit) {
                    break@lab13
                }
                cursor++
            }
            // setmark p1, line 65
            I_p1 = cursor
            // gopast, line 66
            golab18@ while (true) {
                lab19@ do {
                    if (!in_grouping(g_v, 97, 249)) {
                        break@lab19
                    }
                    break@golab18
                } while (false)
                if (cursor >= limit) {
                    break@lab13
                }
                cursor++
            }
            // gopast, line 66
            golab20@ while (true) {
                lab21@ do {
                    if (!out_grouping(g_v, 97, 249)) {
                        break@lab21
                    }
                    break@golab20
                } while (false)
                if (cursor >= limit) {
                    break@lab13
                }
                cursor++
            }
            // setmark p2, line 66
            I_p2 = cursor
        } while (false)
        cursor = v_8
        return true
    }

    private fun r_postlude(): Boolean {
        var among_var: Int
        var v_1: Int
        // repeat, line 70
        replab0@ while (true) {
            v_1 = cursor
            lab1@ do {
                // (, line 70
                // [, line 72
                bra = cursor
                // substring, line 72
                among_var = find_among(a_1, 3)
                if (among_var == 0) {
                    break@lab1
                }
                // ], line 72
                ket = cursor
                when (among_var) {
                    0 -> break@lab1
                    1 ->                                     // (, line 73
                        // <-, line 73
                        slice_from("i")
                    2 ->                                     // (, line 74
                        // <-, line 74
                        slice_from("u")
                    3 -> {
                        // (, line 75
                        // next, line 75
                        if (cursor >= limit) {
                            break@lab1
                        }
                        cursor++
                    }
                }
                continue@replab0
            } while (false)
            cursor = v_1
            break@replab0
        }
        return true
    }

    private fun r_RV(): Boolean {
        return if (I_pV > cursor) {
            false
        } else true
    }

    private fun r_R1(): Boolean {
        return if (I_p1 > cursor) {
            false
        } else true
    }

    private fun r_R2(): Boolean {
        return if (I_p2 > cursor) {
            false
        } else true
    }

    private fun r_attached_pronoun(): Boolean {
        val among_var: Int
        // (, line 86
        // [, line 87
        ket = cursor
        // substring, line 87
        if (find_among_b(a_2, 37) == 0) {
            return false
        }
        // ], line 87
        bra = cursor
        // among, line 97
        among_var = find_among_b(a_3, 5)
        if (among_var == 0) {
            return false
        }
        // (, line 97
        // call RV, line 97
        if (!r_RV()) {
            return false
        }
        when (among_var) {
            0 -> return false
            1 ->                             // (, line 98
                // delete, line 98
                slice_del()
            2 ->                             // (, line 99
                // <-, line 99
                slice_from("e")
        }
        return true
    }

    private fun r_standard_suffix(): Boolean {
        var among_var: Int
        val v_1: Int
        val v_2: Int
        val v_3: Int
        val v_4: Int
        // (, line 103
        // [, line 104
        ket = cursor
        // substring, line 104
        among_var = find_among_b(a_6, 51)
        if (among_var == 0) {
            return false
        }
        // ], line 104
        bra = cursor
        when (among_var) {
            0 -> return false
            1 -> {
                // (, line 111
                // call R2, line 111
                if (!r_R2()) {
                    return false
                }
                // delete, line 111
                slice_del()
            }
            2 -> {
                // (, line 113
                // call R2, line 113
                if (!r_R2()) {
                    return false
                }
                // delete, line 113
                slice_del()
                // try, line 114
                v_1 = limit - cursor
                lab0@ do {
                    // (, line 114
                    // [, line 114
                    ket = cursor
                    // literal, line 114
                    if (!eq_s_b(2, "ic")) {
                        cursor = limit - v_1
                        break@lab0
                    }
                    // ], line 114
                    bra = cursor
                    // call R2, line 114
                    if (!r_R2()) {
                        cursor = limit - v_1
                        break@lab0
                    }
                    // delete, line 114
                    slice_del()
                } while (false)
            }
            3 -> {
                // (, line 117
                // call R2, line 117
                if (!r_R2()) {
                    return false
                }
                // <-, line 117
                slice_from("log")
            }
            4 -> {
                // (, line 119
                // call R2, line 119
                if (!r_R2()) {
                    return false
                }
                // <-, line 119
                slice_from("u")
            }
            5 -> {
                // (, line 121
                // call R2, line 121
                if (!r_R2()) {
                    return false
                }
                // <-, line 121
                slice_from("ente")
            }
            6 -> {
                // (, line 123
                // call RV, line 123
                if (!r_RV()) {
                    return false
                }
                // delete, line 123
                slice_del()
            }
            7 -> {
                // (, line 124
                // call R1, line 125
                if (!r_R1()) {
                    return false
                }
                // delete, line 125
                slice_del()
                // try, line 126
                v_2 = limit - cursor
                lab1@ do {
                    // (, line 126
                    // [, line 127
                    ket = cursor
                    // substring, line 127
                    among_var = find_among_b(a_4, 4)
                    if (among_var == 0) {
                        cursor = limit - v_2
                        break@lab1
                    }
                    // ], line 127
                    bra = cursor
                    // call R2, line 127
                    if (!r_R2()) {
                        cursor = limit - v_2
                        break@lab1
                    }
                    // delete, line 127
                    slice_del()
                    when (among_var) {
                        0 -> {
                            cursor = limit - v_2
                            break@lab1
                        }
                        1 -> {
                            // (, line 128
                            // [, line 128
                            ket = cursor
                            // literal, line 128
                            if (!eq_s_b(2, "at")) {
                                cursor = limit - v_2
                                break@lab1
                            }
                            // ], line 128
                            bra = cursor
                            // call R2, line 128
                            if (!r_R2()) {
                                cursor = limit - v_2
                                break@lab1
                            }
                            // delete, line 128
                            slice_del()
                        }
                    }
                } while (false)
            }
            8 -> {
                // (, line 133
                // call R2, line 134
                if (!r_R2()) {
                    return false
                }
                // delete, line 134
                slice_del()
                // try, line 135
                v_3 = limit - cursor
                lab2@ do {
                    // (, line 135
                    // [, line 136
                    ket = cursor
                    // substring, line 136
                    among_var = find_among_b(a_5, 3)
                    if (among_var == 0) {
                        cursor = limit - v_3
                        break@lab2
                    }
                    // ], line 136
                    bra = cursor
                    when (among_var) {
                        0 -> {
                            cursor = limit - v_3
                            break@lab2
                        }
                        1 -> {
                            // (, line 137
                            // call R2, line 137
                            if (!r_R2()) {
                                cursor = limit - v_3
                                break@lab2
                            }
                            // delete, line 137
                            slice_del()
                        }
                    }
                } while (false)
            }
            9 -> {
                // (, line 141
                // call R2, line 142
                if (!r_R2()) {
                    return false
                }
                // delete, line 142
                slice_del()
                // try, line 143
                v_4 = limit - cursor
                lab3@ do {
                    // (, line 143
                    // [, line 143
                    ket = cursor
                    // literal, line 143
                    if (!eq_s_b(2, "at")) {
                        cursor = limit - v_4
                        break@lab3
                    }
                    // ], line 143
                    bra = cursor
                    // call R2, line 143
                    if (!r_R2()) {
                        cursor = limit - v_4
                        break@lab3
                    }
                    // delete, line 143
                    slice_del()
                    // [, line 143
                    ket = cursor
                    // literal, line 143
                    if (!eq_s_b(2, "ic")) {
                        cursor = limit - v_4
                        break@lab3
                    }
                    // ], line 143
                    bra = cursor
                    // call R2, line 143
                    if (!r_R2()) {
                        cursor = limit - v_4
                        break@lab3
                    }
                    // delete, line 143
                    slice_del()
                } while (false)
            }
        }
        return true
    }

    private fun r_verb_suffix(): Boolean {
        val among_var: Int
        val v_1: Int
        val v_2: Int
        // setlimit, line 148
        v_1 = limit - cursor
        // tomark, line 148
        if (cursor < I_pV) {
            return false
        }
        cursor = I_pV
        v_2 = limit_backward
        limit_backward = cursor
        cursor = limit - v_1
        // (, line 148
        // [, line 149
        ket = cursor
        // substring, line 149
        among_var = find_among_b(a_7, 87)
        if (among_var == 0) {
            limit_backward = v_2
            return false
        }
        // ], line 149
        bra = cursor
        when (among_var) {
            0 -> {
                limit_backward = v_2
                return false
            }
            1 ->                             // (, line 163
                // delete, line 163
                slice_del()
        }
        limit_backward = v_2
        return true
    }

    private fun r_vowel_suffix(): Boolean {
        val v_1: Int
        val v_2: Int
        // (, line 170
        // try, line 171
        v_1 = limit - cursor
        lab0@ do {
            // (, line 171
            // [, line 172
            ket = cursor
            if (!in_grouping_b(g_AEIO, 97, 242)) {
                cursor = limit - v_1
                break@lab0
            }
            // ], line 172
            bra = cursor
            // call RV, line 172
            if (!r_RV()) {
                cursor = limit - v_1
                break@lab0
            }
            // delete, line 172
            slice_del()
            // [, line 173
            ket = cursor
            // literal, line 173
            if (!eq_s_b(1, "i")) {
                cursor = limit - v_1
                break@lab0
            }
            // ], line 173
            bra = cursor
            // call RV, line 173
            if (!r_RV()) {
                cursor = limit - v_1
                break@lab0
            }
            // delete, line 173
            slice_del()
        } while (false)
        // try, line 175
        v_2 = limit - cursor
        lab1@ do {
            // (, line 175
            // [, line 176
            ket = cursor
            // literal, line 176
            if (!eq_s_b(1, "h")) {
                cursor = limit - v_2
                break@lab1
            }
            // ], line 176
            bra = cursor
            if (!in_grouping_b(g_CG, 99, 103)) {
                cursor = limit - v_2
                break@lab1
            }
            // call RV, line 176
            if (!r_RV()) {
                cursor = limit - v_2
                break@lab1
            }
            // delete, line 176
            slice_del()
        } while (false)
        return true
    }

    override fun stem(): Boolean {
        val v_1: Int
        val v_2: Int
        val v_3: Int
        val v_4: Int
        var v_5: Int
        val v_6: Int
        val v_7: Int
        // (, line 181
        // do, line 182
        v_1 = cursor
        lab0@ do {
            // call prelude, line 182
            if (!r_prelude()) {
                break@lab0
            }
        } while (false)
        cursor = v_1
        // do, line 183
        v_2 = cursor
        lab1@ do {
            // call mark_regions, line 183
            if (!r_mark_regions()) {
                break@lab1
            }
        } while (false)
        cursor = v_2
        // backwards, line 184
        limit_backward = cursor
        cursor = limit
        // (, line 184
        // do, line 185
        v_3 = limit - cursor
        lab2@ do {
            // call attached_pronoun, line 185
            if (!r_attached_pronoun()) {
                break@lab2
            }
        } while (false)
        cursor = limit - v_3
        // do, line 186
        v_4 = limit - cursor
        lab3@ do {
            // (, line 186
            // or, line 186
            lab4@ do {
                v_5 = limit - cursor
                lab5@ do {
                    // call standard_suffix, line 186
                    if (!r_standard_suffix()) {
                        break@lab5
                    }
                    break@lab4
                } while (false)
                cursor = limit - v_5
                // call verb_suffix, line 186
                if (!r_verb_suffix()) {
                    break@lab3
                }
            } while (false)
        } while (false)
        cursor = limit - v_4
        // do, line 187
        v_6 = limit - cursor
        lab6@ do {
            // call vowel_suffix, line 187
            if (!r_vowel_suffix()) {
                break@lab6
            }
        } while (false)
        cursor = limit - v_6
        cursor = limit_backward // do, line 189
        v_7 = cursor
        lab7@ do {
            // call postlude, line 189
            if (!r_postlude()) {
                break@lab7
            }
        } while (false)
        cursor = v_7
        return true
    }

    override fun equals(other: Any?): Boolean {
        return other is ItalianStemmer
    }

    override fun hashCode(): Int {
        return ItalianStemmer::class.qualifiedName.hashCode()
    }

    companion object {
        private val a_0 = arrayOf(
            Among("", -1, 7),
            Among("qu", 0, 6),
            Among("\u00E1", 0, 1),
            Among("\u00E9", 0, 2),
            Among("\u00ED", 0, 3),
            Among("\u00F3", 0, 4),
            Among("\u00FA", 0, 5)
        )
        private val a_1 = arrayOf(
            Among("", -1, 3),
            Among("I", 0, 1),
            Among("U", 0, 2)
        )
        private val a_2 = arrayOf(
            Among("la", -1, -1),
            Among("cela", 0, -1),
            Among("gliela", 0, -1),
            Among("mela", 0, -1),
            Among("tela", 0, -1),
            Among("vela", 0, -1),
            Among("le", -1, -1),
            Among("cele", 6, -1),
            Among("gliele", 6, -1),
            Among("mele", 6, -1),
            Among("tele", 6, -1),
            Among("vele", 6, -1),
            Among("ne", -1, -1),
            Among("cene", 12, -1),
            Among("gliene", 12, -1),
            Among("mene", 12, -1),
            Among("sene", 12, -1),
            Among("tene", 12, -1),
            Among("vene", 12, -1),
            Among("ci", -1, -1),
            Among("li", -1, -1),
            Among("celi", 20, -1),
            Among("glieli", 20, -1),
            Among("meli", 20, -1),
            Among("teli", 20, -1),
            Among("veli", 20, -1),
            Among("gli", 20, -1),
            Among("mi", -1, -1),
            Among("si", -1, -1),
            Among("ti", -1, -1),
            Among("vi", -1, -1),
            Among("lo", -1, -1),
            Among("celo", 31, -1),
            Among("glielo", 31, -1),
            Among("melo", 31, -1),
            Among("telo", 31, -1),
            Among("velo", 31, -1)
        )
        private val a_3 = arrayOf(
            Among("ando", -1, 1),
            Among("endo", -1, 1),
            Among("ar", -1, 2),
            Among("er", -1, 2),
            Among("ir", -1, 2)
        )
        private val a_4 = arrayOf(
            Among("ic", -1, -1),
            Among("abil", -1, -1),
            Among("os", -1, -1),
            Among("iv", -1, 1)
        )
        private val a_5 = arrayOf(
            Among("ic", -1, 1),
            Among("abil", -1, 1),
            Among("iv", -1, 1)
        )
        private val a_6 = arrayOf(
            Among("ica", -1, 1),
            Among("logia", -1, 3),
            Among("osa", -1, 1),
            Among("ista", -1, 1),
            Among("iva", -1, 9),
            Among("anza", -1, 1),
            Among("enza", -1, 5),
            Among("ice", -1, 1),
            Among("atrice", 7, 1),
            Among("iche", -1, 1),
            Among("logie", -1, 3),
            Among("abile", -1, 1),
            Among("ibile", -1, 1),
            Among("usione", -1, 4),
            Among("azione", -1, 2),
            Among("uzione", -1, 4),
            Among("atore", -1, 2),
            Among("ose", -1, 1),
            Among("ante", -1, 1),
            Among("mente", -1, 1),
            Among("amente", 19, 7),
            Among("iste", -1, 1),
            Among("ive", -1, 9),
            Among("anze", -1, 1),
            Among("enze", -1, 5),
            Among("ici", -1, 1),
            Among("atrici", 25, 1),
            Among("ichi", -1, 1),
            Among("abili", -1, 1),
            Among("ibili", -1, 1),
            Among("ismi", -1, 1),
            Among("usioni", -1, 4),
            Among("azioni", -1, 2),
            Among("uzioni", -1, 4),
            Among("atori", -1, 2),
            Among("osi", -1, 1),
            Among("anti", -1, 1),
            Among("amenti", -1, 6),
            Among("imenti", -1, 6),
            Among("isti", -1, 1),
            Among("ivi", -1, 9),
            Among("ico", -1, 1),
            Among("ismo", -1, 1),
            Among("oso", -1, 1),
            Among("amento", -1, 6),
            Among("imento", -1, 6),
            Among("ivo", -1, 9),
            Among("it\u00E0", -1, 8),
            Among("ist\u00E0", -1, 1),
            Among("ist\u00E8", -1, 1),
            Among("ist\u00EC", -1, 1)
        )
        private val a_7 = arrayOf(
            Among("isca", -1, 1),
            Among("enda", -1, 1),
            Among("ata", -1, 1),
            Among("ita", -1, 1),
            Among("uta", -1, 1),
            Among("ava", -1, 1),
            Among("eva", -1, 1),
            Among("iva", -1, 1),
            Among("erebbe", -1, 1),
            Among("irebbe", -1, 1),
            Among("isce", -1, 1),
            Among("ende", -1, 1),
            Among("are", -1, 1),
            Among("ere", -1, 1),
            Among("ire", -1, 1),
            Among("asse", -1, 1),
            Among("ate", -1, 1),
            Among("avate", 16, 1),
            Among("evate", 16, 1),
            Among("ivate", 16, 1),
            Among("ete", -1, 1),
            Among("erete", 20, 1),
            Among("irete", 20, 1),
            Among("ite", -1, 1),
            Among("ereste", -1, 1),
            Among("ireste", -1, 1),
            Among("ute", -1, 1),
            Among("erai", -1, 1),
            Among("irai", -1, 1),
            Among("isci", -1, 1),
            Among("endi", -1, 1),
            Among("erei", -1, 1),
            Among("irei", -1, 1),
            Among("assi", -1, 1),
            Among("ati", -1, 1),
            Among("iti", -1, 1),
            Among("eresti", -1, 1),
            Among("iresti", -1, 1),
            Among("uti", -1, 1),
            Among("avi", -1, 1),
            Among("evi", -1, 1),
            Among("ivi", -1, 1),
            Among("isco", -1, 1),
            Among("ando", -1, 1),
            Among("endo", -1, 1),
            Among("Yamo", -1, 1),
            Among("iamo", -1, 1),
            Among("avamo", -1, 1),
            Among("evamo", -1, 1),
            Among("ivamo", -1, 1),
            Among("eremo", -1, 1),
            Among("iremo", -1, 1),
            Among("assimo", -1, 1),
            Among("ammo", -1, 1),
            Among("emmo", -1, 1),
            Among("eremmo", 54, 1),
            Among("iremmo", 54, 1),
            Among("immo", -1, 1),
            Among("ano", -1, 1),
            Among("iscano", 58, 1),
            Among("avano", 58, 1),
            Among("evano", 58, 1),
            Among("ivano", 58, 1),
            Among("eranno", -1, 1),
            Among("iranno", -1, 1),
            Among("ono", -1, 1),
            Among("iscono", 65, 1),
            Among("arono", 65, 1),
            Among("erono", 65, 1),
            Among("irono", 65, 1),
            Among("erebbero", -1, 1),
            Among("irebbero", -1, 1),
            Among("assero", -1, 1),
            Among("essero", -1, 1),
            Among("issero", -1, 1),
            Among("ato", -1, 1),
            Among("ito", -1, 1),
            Among("uto", -1, 1),
            Among("avo", -1, 1),
            Among("evo", -1, 1),
            Among("ivo", -1, 1),
            Among("ar", -1, 1),
            Among("ir", -1, 1),
            Among("er\u00E0", -1, 1),
            Among("ir\u00E0", -1, 1),
            Among("er\u00F2", -1, 1),
            Among("ir\u00F2", -1, 1)
        )
        private val g_v = charArrayOf(
            17.toChar(),
            65.toChar(),
            16.toChar(),
            0.toChar(),
            0.toChar(),
            0.toChar(),
            0.toChar(),
            0.toChar(),
            0.toChar(),
            0.toChar(),
            0.toChar(),
            0.toChar(),
            0.toChar(),
            0.toChar(),
            0.toChar(),
            128.toChar(),
            128.toChar(),
            8.toChar(),
            2.toChar(),
            1.toChar()
        )
        private val g_AEIO = charArrayOf(
            17.toChar(),
            65.toChar(),
            0.toChar(),
            0.toChar(),
            0.toChar(),
            0.toChar(),
            0.toChar(),
            0.toChar(),
            0.toChar(),
            0.toChar(),
            0.toChar(),
            0.toChar(),
            0.toChar(),
            0.toChar(),
            0.toChar(),
            128.toChar(),
            128.toChar(),
            8.toChar(),
            2.toChar()
        )
        private val g_CG = charArrayOf(17.toChar())
    }
}