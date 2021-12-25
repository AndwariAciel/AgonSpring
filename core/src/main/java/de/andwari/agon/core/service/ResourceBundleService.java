package de.andwari.agon.core.service;

import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.ResourceBundle;

@Service
public class ResourceBundleService {

    public ResourceBundle getBundle() {
        return ResourceBundle.getBundle("lang.lang", new Locale("DE"));
    }
}
