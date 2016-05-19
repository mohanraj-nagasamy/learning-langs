package com.comparejson

import org.skyscreamer.jsonassert.{JSONCompareMode, JSONCompare, JSONCompareResult, JSONAssert}

object MainCompare {
  def main(args: Array[String]) {
    val expected =
      """
        |{
        |   id:1,
        |   name:"Joe",
        |   friends:[
        |      {
        |         id:2,
        |         name:"Pat",
        |         pets:[
        |            "dog"
        |         ]
        |      },
        |      {
        |         id:3,
        |         name:"Sue",
        |         pets:[
        |            "bird",
        |            "fish"
        |         ]
        |      }
        |   ],
        |   pets:[
        |
        |   ],
        |   options: [
        |                               {
        |                                "text": "Nederland - (Netherlands)",
        |                                "value": "nl_NL"
        |                            },
        |                            {
        |                                "text": "Português - (Portugal)",
        |                                "value": "pt_PT"
        |                            },
        |                            {
        |                                "text": "中文(简体) - Chinese (Simple)",
        |                                "value": "zh_CN"
        |                            },
        |                            {
        |                                "text": "中文(繁體) - Chinese (Trad)",
        |                                "value": "zh_HK"
        |                            },
        |                            {
        |                                "text": "日本語 - (Japan)",
        |                                "value": "ja_JP"
        |                            }
        |   ]
        |}
      """.stripMargin;
    val actual =
      """
        |{
        |   id:1,
        |   name:"Joe",
        |   friends:[
        |      {
        |         id:2,
        |         name:"Pat",
        |         pets:[
        |            "dog"
        |         ]
        |      },
        |      {
        |         id:3,
        |         name:"Sue",
        |         pets:[
        |            "cat",
        |            "fish"
        |         ]
        |      }
        |   ],
        | options: [
        |                               {
        |                                "text": "Nederland - (Netherlands)",
        |                                "value": "nl_NL"
        |                            },
        |                            {
        |                                "text": "Português - (Portugal)",
        |                                "value": "pt_PT"
        |                            },
        |                            {
        |                                "text": "中文(简体) - Chinese (Simple)",
        |                                "value": "zh_CN"
        |                            },
        |                            {
        |                                "text": "中文(繁體) - Chinese (Trad)",
        |                                "value": "zh_HK"
        |                            },
        |                            {
        |                                "text": "日本語 - (Japan)",
        |                                "value": "ja_JP"
        |                            }
        |   ]
        |
        |}
      """.stripMargin

    JSONAssert.assertEquals(expected, actual, false)
    val strict = false
    val compareMode = if (strict) JSONCompareMode.STRICT else JSONCompareMode.LENIENT
    val result: JSONCompareResult = JSONCompare.compareJSON(expected, actual, compareMode)
    println("result = " + result)
  }
}
