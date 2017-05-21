package com.roi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Polina on 16.04.2017.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException extends RuntimeException {
}