package com.task.util;

/**
 * The Time class is a utility class , which will build web URL with dyanmic parameters
 */
public class URLBuilder {

        private StringBuilder url, params;

        private URLBuilder(){
            params = new StringBuilder();
        }

    /**
     *  To consrtuct object with url prefix
     * @urlPrefix urlPrefix
     */
        public URLBuilder(String urlPrefix) {
            this();
            this.url = new StringBuilder(urlPrefix);
        }


    /**
     *  Add String parameter as key and value to build URL.
     * @parameter parameter as param key
     * @value value as param value
     */
        public void addParameter(String parameter, String value) {
            if(parameter !=null && value!=null) {
                if (params.toString().length() > 0) {
                    params.append("&");
                }
                params.append(parameter);
                params.append("=");
                params.append(value);
            }
        }

    /**
     *  Replace or Append String parameter at end of given url to build final URL.
     * @parameter parameter as param key
     */
    public void updateURLWithExtension(String parameter) {
            params.append(parameter);
            params.append(".json");
    }


    /**
     *  Returns the final URL with Parameters if any.
     */
        public String getURL() {
            return url.append(params).toString();
        }


}
