package com.example.DoAnWebJava.controller;


import com.example.DoAnWebJava.entities.Adv;
import com.example.DoAnWebJava.repositories.UserRegistrationException;
import com.example.DoAnWebJava.service.AdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/adv")
public class AdvController {

    @Autowired
    private AdvService advService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Adv>> getAllAdvs() {
        List<Adv> all = advService.getAllAdvs();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/getByActivate/{isActivate}")
    public ResponseEntity<List<Adv>> getAdvsByActivate(@PathVariable boolean isActivate) {
        List<Adv> advs = advService.getAdvsByActivate(isActivate);
        return ResponseEntity.ok(advs);
    }


    @PostMapping("/add")
    public ResponseEntity<String> addAdv(@RequestBody Adv model) {
        if (model != null) {
            Adv addedAdv = advService.addAdv(model);
            return ResponseEntity.ok("Adv added successfully with ID: " + addedAdv.getId());
        }
        return ResponseEntity.badRequest().body("Invalid request body");
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<Adv> editAdv(@PathVariable int id) {
        try {
            Adv adv = advService.editAdv(id);
            return ResponseEntity.ok(adv);
        } catch (UserRegistrationException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<String> updateAdv(@PathVariable int id, @RequestBody Adv model) {
        if (model != null) {
            try {
                Adv updatedAdv = advService.editAdv(id, model);
                return ResponseEntity.ok("Adv updated successfully");
            } catch (UserRegistrationException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.badRequest().body("Invalid request body");
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteAdv(@PathVariable int id) {
        try {
            advService.deleteAdv(id);
            return ResponseEntity.ok("Adv deleted successfully");
        } catch (UserRegistrationException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/deleteAll")
    public ResponseEntity<String> deleteAllAdvs(@RequestParam("ids") List<Integer> ids) {
        try {
            advService.deleteAllAdvs(ids);
            return ResponseEntity.ok("All Advs deleted successfully");
        } catch (UserRegistrationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
