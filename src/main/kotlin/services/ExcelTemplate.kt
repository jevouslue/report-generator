package org.example.services

import org.jxls.builder.JxlsOutputFile
import org.jxls.transform.poi.JxlsPoiTemplateFillerBuilder
import java.io.File

class ExcelTemplate {
    val templateFile: String

    constructor(templateFile: String) {
        this.templateFile = templateFile
    }

    fun build(data: MutableMap<String, Any?>, outputFile: String) {
        JxlsPoiTemplateFillerBuilder.newInstance()
            .withTemplate("resources/xlsx_templates/$templateFile")
            .buildAndFill(
                data,
                JxlsOutputFile(File("storage/artifacts/$outputFile"))
            )
    }
}