package ja.com

/**
  * Created by Ja on 21/05/2017.
  */
object Common {
   case class elasticSearchDetails(timstamp: String,
      content_Type:String,URL : String, date : String, Text : String)
   case class cdxItem(massaged_url:String,
                        b_date:String,
                        a_origina_url :String,
                        m_mime_type_of_original_document:String,
                        s_response_code : String,
                        K_FBIS: String,
                        r_redirect :String
                      ,
                        code_abrevation : String,
                        V_compressed_arc_file_offset : String,
                        file_name:String
   )

}
