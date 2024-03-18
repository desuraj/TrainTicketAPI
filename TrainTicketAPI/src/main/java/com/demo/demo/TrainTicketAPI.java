package com.demo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
@RestController
public class TrainTicketAPI {

    private final Map<String, TicketPurchase> ticketPurchases = new HashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(TrainTicketAPI.class, args);
    }

    @PostMapping("/purchase")
    public TicketPurchase submitPurchase(@RequestBody TicketPurchaseRequest request) {
        String ticketId = UUID.randomUUID().toString();
        TicketPurchase ticketPurchase = new TicketPurchase(
                ticketId,
                request.getFrom(),
                request.getTo(),
                new User(request.getUser().getFirstName(), request.getUser().getLastName(), request.getUser().getEmail()),
                request.getPricePaid(),
                request.getSection()
        );
        ticketPurchases.put(ticketId, ticketPurchase);
        return ticketPurchase;
    }

    @GetMapping("/receipt/{ticketId}")
    public TicketPurchase getReceiptDetails(@PathVariable String ticketId) {
        return ticketPurchases.get(ticketId);
    }

    @GetMapping("/users/{section}")
    public Map<String, String> getUsersBySection(@PathVariable String section) {
        Map<String, String> usersBySection = new HashMap<>();
        for (Map.Entry<String, TicketPurchase> entry : ticketPurchases.entrySet()) {
            TicketPurchase ticketPurchase = entry.getValue();
            if (ticketPurchase.getSection().equalsIgnoreCase(section)) {
                usersBySection.put(ticketPurchase.getUser().getEmail(), ticketPurchase.getSeat());
            }
        }
        return usersBySection;
    }

    @DeleteMapping("/remove/{ticketId}")
    public void removeUser(@PathVariable String ticketId) {
        ticketPurchases.remove(ticketId);
    }

    @PutMapping("/modify/{ticketId}")
    public TicketPurchase modifySeat(@PathVariable String ticketId, @RequestParam String newSeat) {
        TicketPurchase ticketPurchase = ticketPurchases.get(ticketId);
        if (ticketPurchase != null) {
            ticketPurchase.setSeat(newSeat);
        }
        return ticketPurchase;
    }

    static class TicketPurchaseRequest {
        private String from;
        private String to;
        private User user;
        private double pricePaid;
        private String section;

        // Getters and setters

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public double getPricePaid() {
            return pricePaid;
        }

        public void setPricePaid(double pricePaid) {
            this.pricePaid = pricePaid;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }
    }

    static class User {
        private String firstName;
        private String lastName;
        private String email;

        public User(String firstName, String lastName, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }
        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    static class TicketPurchase {
        private String ticketId;
        private String from;
        private String to;
        private User user;
        private double pricePaid;
        private String section;
        private String seat;
        public TicketPurchase(String ticketId, String from, String to, User user, double pricePaid, String section) {
            this.ticketId = ticketId;
            this.from = from;
            this.to = to;
            this.user = user;
            this.pricePaid = pricePaid;
            this.section = section;
            // Assign a default seat based on section
            this.seat = (section.equalsIgnoreCase("A")) ? "A1" : "B1";
        }
        public String getTicketId() {
            return ticketId;
        }

        public void setTicketId(String ticketId) {
            this.ticketId = ticketId;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public double getPricePaid() {
            return pricePaid;
        }

        public void setPricePaid(double pricePaid) {
            this.pricePaid = pricePaid;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public String getSeat() {
            return seat;
        }

        public void setSeat(String seat) {
            this.seat = seat;
        }
    }
}
