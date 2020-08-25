package com.redis_cache.redis_sentinel.controller;

import com.redis_cache.redis_sentinel.model.IceCream;
import com.redis_cache.redis_sentinel.service.IceCreamDataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/iceCreamController")
public class IceCreamController {

    @Autowired
    private IceCreamDataManager iceCreamDataManager;

    @RequestMapping(
            value="/allIceCreams",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<List<IceCream>> listOfAllIceCreams() {
        return new ResponseEntity<List<IceCream>>(
                iceCreamDataManager.getAllIceCream(),
                HttpStatus.OK);
    }

    @RequestMapping(
            value="/iceCream/{id}",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<IceCream> getRequiredIceCream(
            @PathVariable String id) {
        IceCream resultIceCream = iceCreamDataManager.getIceCream(id);
        if(resultIceCream != null) {
            return new ResponseEntity<IceCream>(
                    resultIceCream, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<IceCream>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(
            value="/iceCream/add",
            method= RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<?> addIceCream(@RequestBody IceCream iceCream)
    {
        iceCreamDataManager.addIceCream(iceCream);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(
            value="/iceCream/update/{id}",
            method= RequestMethod.PUT,
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<?> updateIceCream(
            @RequestBody IceCream iceCream,
            @PathVariable String id) {
        iceCreamDataManager.updateCream(iceCream, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(
            value="/iceCream/delete/{id}",
            method= RequestMethod.DELETE,
            produces = "application/json")
    public ResponseEntity<?> deleteIceCream(
            @PathVariable String id) {
        iceCreamDataManager.deleteIceCream(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
