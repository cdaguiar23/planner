package com.rocketseat.planner.trip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rocketseat.planner.participant.ParticipantService;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private TripRepository repository;

//    @PostMapping
//    public ResponseEntity<String> createTrip(@RequestBody TripRequestPayload payload) {
//        Trip newTrip = new Trip(payload);
//
//        this.repository.save(newTrip);
//
//        this.participantService.registerParticipantsToEvent(payload.emails_to_invite(), newTrip.getId());
//
//        return ResponseEntity.ok("Sucesso");
//    }

    @PostMapping
    public ResponseEntity<TripCraetorResponse> createTrip(@RequestBody TripRequestPayload payload) {
        Trip newTrip = new Trip(payload);

        this.repository.save(newTrip);

        this.participantService.registerParticipantsToEvent(payload.emails_to_invite(), newTrip.getId());

        return ResponseEntity.ok(new TripCraetorResponse(newTrip.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTRipDetails(@PathVariable UUID id) {
        Optional<Trip>  trip = this.repository.findById(id);

        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
 }
