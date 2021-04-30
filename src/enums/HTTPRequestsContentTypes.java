package enums;

/**
 * Enum used to declare the HTTP Requests Content Type property
 */
public enum HTTPRequestsContentTypes {
    JSON{
    public String toString(){
        return "application/json";
        }
    },
    TEXT{
        public String toString(){
            return "text/html";
        }
    },
    MULTIPART{
        public String toString(){
            return "multipart/form-data";
        }
    }



}
