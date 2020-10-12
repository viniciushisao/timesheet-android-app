package br.com.hisao.timesheet.model

enum class TimeSheetDataType(val id: Int, val type: String) {
    START(0, "START"),
    STOP(1, "STOP");

    companion object {
        fun getId(type: String?): Int {
            return if (type == null || type == START.name) {
                START.id
            } else {
                STOP.id
            }
        }

        fun getName(id: Int?): String {
            return if (id == null || id == START.id) {
                START.name
            } else {
                STOP.name
            }
        }
    }
}