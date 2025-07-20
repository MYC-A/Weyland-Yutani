package com.weyland.starter.service;

import com.weyland.starter.DTO.CommandDTO;

public interface CommandService {
    void processCommand(CommandDTO command);
    int getQueueSize();
}
