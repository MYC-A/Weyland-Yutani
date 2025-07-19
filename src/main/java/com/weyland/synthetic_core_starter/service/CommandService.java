package com.weyland.synthetic_core_starter.service;

import com.weyland.synthetic_core_starter.DTO.CommandDTO;

public interface CommandService {
    void processCommand(CommandDTO command);
    int getQueueSize();
}
