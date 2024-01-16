package hcmute.kltn.vtv.controller.demo;


import hcmute.kltn.vtv.model.entity.shipping.Deliver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/deliver")
public class DeliverController {


    @PostMapping
    public ResponseEntity<?>  addDeliver(@RequestBody Deliver deliver) {

        System.out.println("deliver: " + deliver);
//        System.out.println(deliver.getName());
//        System.out.println(deliver.getDeliverId());

        return ResponseEntity.ok(deliver);
    }


}
