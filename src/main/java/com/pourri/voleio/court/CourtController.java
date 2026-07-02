package com.pourri.voleio.court;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Controller
@RequestMapping("/courts")
public class CourtController {
    @Autowired
    private CourtService courtService;

    @PostMapping("/add")
    public ResponseEntity<Void> registerCourt(@RequestBody CreateCourtDTO court) {
        courtService.createCourt(court);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ListAllCourtsDTO>> selectAllActiveCourts() {

        return ResponseEntity.ok(courtService.listAllActiveCourts());

    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCourt(@PathVariable Long id, @RequestBody CreateCourtDTO court) {
        courtService.updateCourt(id, court);
        return ResponseEntity.ok().build();
    }
}
