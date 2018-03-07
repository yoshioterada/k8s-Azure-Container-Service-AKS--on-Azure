/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoshio3;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;


@ApplicationPath("/account")
public class Main extends ResourceConfig {

    public Main() {
        super(MultiPartFeature.class);
        packages(Main.class.getPackage().getName());
    }
}