package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import java.time.LocalDate;

public class CardDTO {

    private Long id;

    private String cardholder;

    private CardType type;

    private CardColor color;

    private String number;

    private Integer cvv;

    private LocalDate dueDate;

    private LocalDate fromDate;

    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardholder = card.getClient().getFirstName() + " " + card.getClient().getLastName();
        this.type = card.getType();
        this.color = card.getColor();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.dueDate = card.getDueDate();
        this.fromDate = card.getFromDate();
    }

    public Long getId() {
        return id;
    }

    public String getCardholder() {
        return cardholder;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public String getNumber() {
        return number;
    }

    public Integer getCvv() {
        return cvv;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }
}
