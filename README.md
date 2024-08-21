## ANDROID TLV PARSER

This Android application, written in Kotlin, provides a user-friendly way to decode and analyze TLV (Tag-Length-Value) data, commonly used in EMV transactions and other areas where data is encoded in this format.

## Requirements

- Parse TLV data into its components (Tag, Length, and Value)
- Display the parsed information in a user-friendly format.
- Handle various TLV encoding formats commonly used in EMV transactions.
- Provide error handling for invalid or malformed TLV data.

## What I did

- Created a kotlin APP that decodes the two examples given or grabs a custom example from a text field.

- Integrated with the decoding library from [ber-tlv](https://github.com/evsinev/ber-tlv)

- Wrote a simple parser that decodes the data and parses it to an List of a custom model instance (Tag)

- Shows an error alert if the input data is invalid for decoding or show a list with the decoded tags.

## Run the project
- Android Studio (latest stable version recommended)

- Java Development Kit (JDK) 11 or higher

- Clone this repository:
 ```
 git clone https://github.com/HenryUdorji/TLV-Parser.git
 ```
- Run the project in Android Studio

## Rationale for Using an External Library

- Instead of implementing TLV parsing from scratch, this project leverages the ber-tlv library for the following reasons:
  
- Efficiency: The ber-tlv library provides a well-tested and optimized implementation of TLV parsing, saving significant development time and effort.
  
- Reliability: By using a mature library, we can rely on its robustness and avoid potential issues with custom parsing logic.

## How to test app

- Sample Data: The app provides two sample TLV strings for testing. Click on the overflow menu (three dots) on the right side of the toolbar and select either "TLV Data 1" or "TLV Data 2" to see the parsed information.
  
- Custom Input: Enter your own TLV string in the text field and press the "Parse" button to parse it.
  
- Results: The parsed TLV data will be displayed in a list, showing the tag, length, value, and description (if available) for each element.
  
- Error Handling: If the input TLV string is invalid or malformed, an error message will be displayed.
