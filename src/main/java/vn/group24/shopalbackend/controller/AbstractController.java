package vn.group24.shopalbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.util.UserUtils;

/**
 *
 * @author ttg
 */
@Component
public abstract class AbstractController {

    @Autowired
    protected UserUtils userUtils;
}
