package bhutan.eledger.adapter.out.epayment.rma;

import bhutan.eledger.application.port.out.epayment.rma.RmaRequesterPort;
import bhutan.eledger.configuration.epayment.rma.RmaProperties;
import bhutan.eledger.domain.epayment.rma.*;
import com.jsunsoft.http.*;
import lombok.extern.log4j.Log4j2;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Log4j2
@Component
class RmaRequesterAdapter implements RmaRequesterPort {
    private final HttpRequest httpRequest;
    private final RmaProperties rmaProperties;

    public RmaRequesterAdapter(CloseableHttpClient closeableHttpClient, RmaProperties rmaProperties) {
        httpRequest = HttpRequestBuilder.create(closeableHttpClient)
                .addBodyReader(ResponseBodyReader.stringReader())
                .build();
        this.rmaProperties = rmaProperties;
    }

    @Override
    public RmaMessageResponse send(RmaMessagePart rmaMessagePart) {

        var bfsNameValuePair = rmaMessagePart.getBfsNameValuePair();

        log.trace("AS request name value pair data: {}", bfsNameValuePair);

        var path = resolveUrlPath(rmaMessagePart);

        ResponseHandler<String> rh = httpRequest.target(rmaProperties.getApi().getHost())
                .addContentType(ContentType.APPLICATION_FORM_URLENCODED)
                .path(path)
                .addParameters(bfsNameValuePair)
                .post(String.class);

        if (rh.isSuccess()) {
            if (rh.hasContent()) {
                log.trace("Request to rma with orderNo {} has been succeed. Response string: {}", rmaMessagePart.getOrderNo(), rh.get());

                return responseStringToAcDetails(rh.get());
            } else {
                log.trace("Request to rma has been succeed but response from rma has not content. Rh: {}", rh);

                throw new UnexpectedResponseException(rh.getStatusCode(), "Expected body non exists.", rh.getURI());
            }
        } else {
            log.trace("Request rma not succeed. Rh: {}", rh);

            throw new UnexpectedStatusCodeException(rh.getStatusCode(), rh.getErrorText(), rh.getURI());

        }
    }

    private String resolveUrlPath(RmaMessagePart rmaMessagePart) {
        String result;

        if (rmaMessagePart.getMsgType() == RmaMsgType.AS) {
            result = rmaProperties.getApi().getAsCheckStatusPath();
        } else {
            throw new IllegalArgumentException("No path defined for msg type: " + rmaMessagePart.getMsgType());
        }

        return result;

    }

    private RmaMessageResponse responseStringToAcDetails(String responseString) {

        var nvp = URLEncodedUtils.parse(responseString, StandardCharsets.UTF_8)
                .stream()
                .filter(nameValuePair -> StringUtils.hasLength(nameValuePair.getValue()))
                .collect(Collectors.toMap(NameValuePair::getName, NameValuePair::getValue));

        var bfsTxnTime = nvp.get(AcResponseDataElement.BFS_TXN_TIME.getValue());

        return RmaMessageResponse.withoutId(
                RmaMsgType.of(nvp.get(AcResponseDataElement.MSG_TYPE.getValue())),
                nvp.get(AcResponseDataElement.BFS_TXN_ID.getValue()),
                StringUtils.hasLength(bfsTxnTime) ? RmaMessage.txnDateTimeStringToDateTime(bfsTxnTime) : null,
                RmaMessage.txnDateTimeStringToDateTime(nvp.get(AcResponseDataElement.BENF_TXN_TIME.getValue())),
                nvp.get(AcResponseDataElement.ORDER_NO.getValue()),
                nvp.get(AcResponseDataElement.BENF_ID.getValue()),
                nvp.get(AcResponseDataElement.TXN_CURRENCY.getValue()),
                new BigDecimal(nvp.get(AcResponseDataElement.TXN_AMOUNT.getValue())),
                nvp.get(AcResponseDataElement.CHECK_SUM.getValue()),
                nvp.get(AcResponseDataElement.REMITTER_NAME.getValue()),
                nvp.get(AcResponseDataElement.REMITTER_BANK_ID.getValue()),
                DebitAuthCode.of(nvp.get(AcResponseDataElement.DEBIT_AUTH_CODE.getValue())),
                nvp.get(AcResponseDataElement.DEBIT_AUTH_NO.getValue()),
                LocalDateTime.now()
        );
    }
}
