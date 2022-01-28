package pos.lab.SoapModule;

import io.spring.guides.gs_producing_web_service.GetEBookResponse;
import io.spring.guides.gs_producing_web_service.GetProofOfPayRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ContentDeliveryEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private ContentDeliveryRepository contentDeliveryRepository;

    @Autowired
    public ContentDeliveryEndpoint(ContentDeliveryRepository contentDeliveryRepository) {
        this.contentDeliveryRepository = contentDeliveryRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProofOfPayRequest")
    @ResponsePayload
    public GetEBookResponse getEBook(@RequestPayload GetProofOfPayRequest request) {
        GetEBookResponse response = new GetEBookResponse();
        response.setBook(contentDeliveryRepository.findBook(request.getIsbn()));
        return response;
    }
}
