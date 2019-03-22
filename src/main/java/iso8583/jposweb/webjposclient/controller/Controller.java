package iso8583.jposweb.webjposclient.controller;

import iso8583.jposweb.webjposclient.dto.resultRequest;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.q2.iso.QMUX;
import org.jpos.util.NameRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class Controller {

    @Autowired
    public QMUX qmux;

    private SimpleDateFormat formatterBit7 = new SimpleDateFormat("MMddHHmmss");

    @PostMapping("/results")
    public Map<String, String> result(@RequestBody @Valid resultRequest request) throws Exception{
        Map<String, String> hasil =  new LinkedHashMap<>();

        try {
            QMUX qmux = NameRegistrar.get("mux.kisel");

            ISOMsg msgRequest = new ISOMsg("200");
            msgRequest.set(4, request.getNilai().setScale(0).toString());
            msgRequest.set(7, formatterBit7.format(new Date()));
            msgRequest.set(11,"000001");
            msgRequest.set(63,"131001");

            // TODO: Get bit48 from..
            String bit48 = request.getMsidn().substring(0,4);
            bit48 += String.format("%1$" + 13 + "s", request.getMsidn().substring(4));
            System.out.println("BIT_48: [" +bit48+"]");
            msgRequest.set(48, bit48);

            ISOMsg isoMsg = qmux.request(msgRequest, 20 * 1000);

            if (isoMsg == null){
                hasil.put("success", "false");
                hasil.put("error", "timeout");
                return hasil;
            }
                String response = new String(isoMsg.pack());
                hasil.put("success", "true");
                hasil.put("Response_Code", isoMsg.getString(39));
                hasil.put("Raw_Message", response);

        } catch (ISOException e) {
            e.printStackTrace();
        }
        return hasil;
    }
}
