package edu.p.lodz.pl.pas.mvc;

import static javax.faces.annotation.FacesConfig.Version.JSF_2_3;

import javax.faces.annotation.FacesConfig;

@FacesConfig(
        // Activates F*CKING CDI build-in beans. Belive or not without this sh*t it won't work.
        version = JSF_2_3
)
public class ConfigurationBean {

}