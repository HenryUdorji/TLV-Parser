package com.ifechukwu.tlvparser.data

import com.ifechukwu.tlvparser.data.model.EmvTag
import com.ifechukwu.tlvparser.data.model.Tag
import com.payneteasy.tlv.BerTlvParser
import com.payneteasy.tlv.HexUtil


/**
 * @Author: ifechukwu.udorji
 * @Date: 8/14/2024
 */

sealed class ParserState {
    data object Idle : ParserState()
    data object Parsing : ParserState()
    data class Error(val message: String) : ParserState()
    data class Success(val tags: List<Tag>) : ParserState()
}
object TLVParser {

    /**
     * Decodes a TLV (Tag-Length-Value) string into a list of Tag objects.
     *
     * This function parses the input TLV string, extracts individual TLV elements, and maps them to Tag objects
     * containing the tag, length, value, and a description (if available from the EmvTag enum).
     *
     * The parsing process is communicated through the `parserState` callback, which receives updates on the parsing
     * progress, including success with the list of parsed tags or an error message in case of failure.
     *
     * @param tlv The TLV string to decode.
     * @param parserState A callback function to receive updates on the parsing state.
     */
    fun decodeTLV(tlv: String, parserState: (ParserState) -> Unit) {
        parserState(ParserState.Parsing)

        try {
            val bytes = HexUtil.parseHex(tlv)

            val parser = BerTlvParser()
            val tlvs = parser.parse(bytes, 0, bytes.size)
            val tags = tlvs.list.map { berTlv ->
                val emvTag = EmvTag.emvTags().firstOrNull { emvTag -> emvTag.tag.uppercase() == HexUtil.toHexString(berTlv.tag.bytes).uppercase() }
                Tag(
                    tag = HexUtil.toHexString(berTlv.tag.bytes),
                    length = berTlv.bytesValue.size,
                    value = berTlv.hexValue,
                    tagDescription = emvTag?.description ?: "N/A",
                )
            }.toList()

            parserState(ParserState.Success(tags))
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            parserState(ParserState.Error("Invalid TLV string"))
        } catch (e: Exception) {
            e.printStackTrace()
            parserState(ParserState.Error(e.message ?: "Unknown error"))
        }
    }
}