package io.lastwill.eventscan.model;

import lombok.Getter;


@Getter
public enum TransactionStatus {
    DONE,
    ERROR,
    OK,
    WAITING_FOR_CONFIRMATION
}
