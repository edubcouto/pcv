package br.com.lebc.config;

import org.glassfish.jersey.server.ResourceConfig;

import br.com.lebc.ws.MapsWS;
import br.com.lebc.ws.SalesmanWS;

/**
 * Web Service registering class.
 * 
 * @author Luiz Couto(bittencourt.vr@gmail.com)
 */
public class PCVResourceConfig extends ResourceConfig {

    public PCVResourceConfig() {
        register(CORSResponseFilter.class);
        register(MapsWS.class);
        register(SalesmanWS.class);
    }

    /**
     * Register JAX-RS application components.
     */

}
