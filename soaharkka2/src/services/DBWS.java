
package services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "DBWS", targetNamespace = "http://services")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface DBWS {


    /**
     * 
     * @param param
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "searchPriceReturn", targetNamespace = "http://services")
    @RequestWrapper(localName = "searchPrice", targetNamespace = "http://services", className = "services.SearchPrice")
    @ResponseWrapper(localName = "searchPriceResponse", targetNamespace = "http://services", className = "services.SearchPriceResponse")
    public String searchPrice(
        @WebParam(name = "param", targetNamespace = "http://services")
        String param);

}