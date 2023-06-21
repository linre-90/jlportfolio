package com.juholindemark.jlportfolio.util

/**
 * Provides some utility methods to escape/filter user input from database.
 * */
class OutputFilter {

    /**
     * Filter text. Allows letters, digits, whitespace and ['.', '?', ',' , '!'] characters.
     * */
    fun filterText(text: String):String {
        return text.filter {
            it.isLetterOrDigit()
                    || it.isWhitespace()
                    || it.equals('.', true)
                    || it.equals('?', true)
                    || it.equals(',', true)
                    || it.equals('!', true)

        }
    }

    /**
     * Filter email addresses. Allow letters, digits, '.' and '@'.
     * This is very strict method compared to what characters emails can actually contain.
     * */
    fun filterEmail(email: String): String{
        return email.filter {
            it.isLetterOrDigit()
                    || it.equals('.', true)
                    || it.equals('@', true)
        }
    }

    /**
     * Filter timestamps. Specific for this application and its format "dd.mm.yyyy-hh.mm".
     * */
    fun filterTimeStamp(timeStamp: String): String{
        return timeStamp.filter{
            it.isDigit()
                    || it.equals('.', true)
                    || it.equals('-', true)
        }
    }
}