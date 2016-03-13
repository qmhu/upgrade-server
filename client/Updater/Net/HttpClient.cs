using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Text;
using Updater.Util;

namespace Updater.Net
{
    class HttpClient
    {

        public static string SendPost(string url, string body)
      {
            HttpWebResponse result = null;

            try
            {
                HttpWebRequest httpWebRequest = (HttpWebRequest)WebRequest.Create(url);

                httpWebRequest.Method = "POST";
                httpWebRequest.Timeout = 60000;

                byte[] btBodys = Encoding.UTF8.GetBytes(body);
                httpWebRequest.ContentLength = btBodys.Length;
                httpWebRequest.GetRequestStream().Write(btBodys, 0, btBodys.Length);
                
                result = (HttpWebResponse)httpWebRequest.GetResponse();
                StreamReader streamReader = new StreamReader(result.GetResponseStream());
                string responseContent = streamReader.ReadToEnd();
                streamReader.Close();
                return responseContent;
            }
            catch (Exception ex)
            {
                Logger.getLogger().error(String.Format("Send Http Post failed,{0} {1}", url, body), ex);
            }
            finally
            {
                if (result != null)
                {
                    result.Close();
                }
            }
 
          return null;
     }

        public static string SendGet(string url)
        {
            HttpWebResponse result = null;

            try
            {
                HttpWebRequest httpWebRequest = (HttpWebRequest)WebRequest.Create(url);

                httpWebRequest.Method = "GET";
                httpWebRequest.Timeout = 60000;


                result = (HttpWebResponse)httpWebRequest.GetResponse();
                StreamReader streamReader = new StreamReader(result.GetResponseStream());
                string responseContent = streamReader.ReadToEnd();
                streamReader.Close();
                return responseContent;
            }
            catch (Exception ex)
            {
                Logger.getLogger().error(String.Format("Send Http Get failed,{0} {1}", url), ex);
            }
            finally
            {
                if (result != null)
                {
                    result.Close();
                }
            }

            return null;
        }

        public static Stream download(string url)
        {
            HttpWebResponse result = null;

            try
            {
                HttpWebRequest httpWebRequest = (HttpWebRequest)WebRequest.Create(url);

                httpWebRequest.Method = "GET";
                httpWebRequest.Timeout = 600000;

                result = (HttpWebResponse)httpWebRequest.GetResponse();
                return result.GetResponseStream();
            }
            catch (Exception ex)
            {
                Logger.getLogger().error(String.Format("Send Http download failed,{0} {1}", url), ex);
            }
            finally
            {
                if (result != null)
                {
                    result.Close();
                }
            }

            return null;
        }

    }
}
