package com.bci.integration.controller.dto;

import java.util.UUID;

public record UserDto(UUID id, String username, String name, String email, String role) {


}