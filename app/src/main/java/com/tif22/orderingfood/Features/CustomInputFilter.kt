package com.tif22.orderingfood.Features
import android.text.InputFilter
import android.text.Spanned
import android.widget.EditText

// Fungsi pertama: tidak dapat menginputkan angka
fun setInputFilterNoNumbers(editText: EditText) {
    val filters = arrayOf(InputFilter.LengthFilter(Int.MAX_VALUE), NoNumbersInputFilter())
    editText.filters = filters
}

// Fungsi kedua: hanya bisa menginputkan huruf, spasi, dan petik (')
fun setInputFilterNumbersOnly(editText: EditText, maxLength: Int) {
    val filters = arrayOf(InputFilter.LengthFilter(maxLength), NumbersOnlyInputFilter())
    editText.filters = filters
}

// Fungsi ketiga: tidak dapat menginputkan spasi
fun setInputFilterNoSpaces(editText: EditText, maxLength: Int) {
    val filters = arrayOf(InputFilter.LengthFilter(maxLength),
        NumbersOnlyInputFilter.NoSpacesInputFilter()
    )
    editText.filters = filters
}

class NoNumbersInputFilter : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        source?.let {
            for (i in start until end) {
                val c = source[i]
                if (!Character.isLetter(c) && c != ' ' && c != '\'') {
                    return ""
                }
            }
        }
        return null
    }
}
class NumbersOnlyInputFilter : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        source?.let {
            for (i in start until end) {
                val c = source[i]
                if (!Character.isDigit(c)) {
                    return ""
                }
            }
        }
        return null
    }

    class NoSpacesInputFilter : InputFilter {
        override fun filter(
            source: CharSequence?,
            start: Int,
            end: Int,
            dest: Spanned?,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            source?.let {
                for (i in start until end) {
                    if (Character.isWhitespace(source[i])) {
                        return ""
                    }
                }
            }
            return null
        }
    }
}